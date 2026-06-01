# modules/util/semantic-search-cli

A jar-based CLI for semantic search over a project's text corpus.
Talks HTTP to Hugging Face's text-embeddings-inference (TEI) for
embeddings, and uses the Qdrant Java client for vector ops. The
bundled `docker-compose.yml` brings up both containers; the jar
itself stays small and dependency-light.

Skills and other agents invoke this jar by shell command — the
subcommands, flags, and JSON output schema are the stable interface.

## What gets indexed

Files are selected by extension. The default is `.md`; set the
`SEARCH_FILE_EXTS` env var to a comma-separated list to widen the
walk:

| Setting | Effect |
| :--- | :--- |
| (default) | Markdown only (`.md`). |
| `SEARCH_FILE_EXTS=.java` | Java source only. |
| `SEARCH_FILE_EXTS=.md,.java` | Markdown plus Java. |

A language-appropriate chunker handles each extension. Markdown is
split by `##` sections (heading-path becomes `[H1, H2]`); Java is
split at top-level method signatures (heading-path becomes
`[ClassName, methodName]`), after stripping the license header,
`package` line, and the entire `import` block. A method larger than
the size limit is split further at top-level statement boundaries, so
a chunk never cuts mid-statement. Each Java chunk's embedded text is
prefixed with a metadata header (file path and class/method scope) so
the vector reflects where the code lives; the stored snippet stays the
raw body.

## Embedding model

The model is a `docker-compose` concern, set by `SEARCH_MODEL_ID`
(default `nomic-ai/CodeRankEmbed` — code-tuned, MIT, 768-dim — used for
both Markdown and Java). A 384-dim model is a lighter, faster option
where code-search quality is not needed.

The model is asymmetric: `SEARCH_QUERY_PREFIX` supplies its query
instruction (`Represent this query for searching relevant code: `),
applied to query text only, never to indexed documents.

Switching models forces a rebuild — the next ingest detects the changed
vector dimension and drops the index automatically.

## CLI contract

```
search ingest <dir> [--force]
search query "<text>" [--top N] [--format text|json] [--path <dir-prefix>]
search similar <file> [--top N] [--format text|json] [--path <dir-prefix>]
search status
```

### Scoping a Search with `--path`

`--path <dir-prefix>` restricts `query` and `similar` to chunks whose file lives under that directory prefix, relative to the index's ingest root. For example, against a whole-repo index:

```
search query "permission check on create" --path modules/apps/headless
```

returns only hits under `modules/apps/headless`, ignoring identical patterns elsewhere in the repo.

This exists because retrieval precision falls as an index grows: similarity scores do not change, but a large corpus holds more near-duplicate chunks (generated `Base*` classes, repeated CRUD and permission patterns across modules) that can outrank the specific one you want. The filter is applied *inside* the vector search — Qdrant returns the closest chunks **under the prefix**, not the closest overall then filtered — so a focused query stays precise even against a whole-repo index. The intended pattern is **index wide, query narrow**: keep one comprehensive index and scope each query to the subtree in question.

The prefix is matched against each chunk's ancestor directories (stored as `dir_prefixes` at ingest), so it must be relative to the root the index was built from. An index built from the liferay-portal checkout root takes repo-relative prefixes like `modules/apps/headless`. `--path` requires an index built with this field; re-ingest an older index to populate it.

Exit codes:

| Code | Meaning |
| ---: | :--- |
| 0 | Success. |
| 1 | Bad input. |
| 2 | Bad CLI usage. |
| 3 | Index does not exist. |
| 4 | Target file for `similar` has no extractable content. |
| 5 | Qdrant unreachable. |
| 6 | Internal error. |

JSON output schema for `query` and `similar`:

```json
{
	"chunk_id": "...",
	"heading_path": [
		"H1",
		"H2"
	],
	"path": "...",
	"score": 0.0,
	"snippet": "..."
}
```

## Runtime data location

Persistent state — the Qdrant index and the downloaded embedding
model — lives outside the calling project's checkout, as a sibling.
The caller sets `SEARCH_DATA_HOME`; the default layout is:

```
<parent-of-project>/<project-basename>-tools/search/
├── qdrant-data/   (vector index)
└── model-cache/   (TEI's model cache)
```

Each worktree of a repo gets its own data home automatically because
each worktree has a unique basename. Override the whole path with
`SEARCH_DATA_HOME` to point at a shared or per-corpus directory.

## Ports

The stack binds three host ports, all on `127.0.0.1`. The defaults
avoid Liferay's (notably Tomcat's 8080):

| Service | Default host port | Override |
| :--- | ---: | :--- |
| TEI (HTTP) | 7997 | `SEARCH_TEI_PORT` |
| Qdrant (HTTP) | 6333 | `SEARCH_QDRANT_HTTP_PORT` |
| Qdrant (gRPC) | 6334 | `SEARCH_QDRANT_GRPC_PORT` |

Only one stack runs at a time (fixed container names), so repeated runs
never collide with each other. Override a port if another local service
already holds it (for example, a Qdrant you run on 6333). The container
ports and the CLI's connection target move together.

## Acceleration

The default `cpu-1.6` TEI image works on every supported platform —
Intel/AMD x86, Apple Silicon (via ARM), any laptop with Docker. If
your hardware can do better, override `TEI_IMAGE_TAG` before bringing
the stack up:

| Hardware | Recommended | Speedup over default |
| :--- | :--- | :--- |
| Intel CPU (10th gen+, Core Ultra, Xeon) | `TEI_IMAGE_TAG=cpu-ipex-1.6` | ~2–4× |
| Apple Silicon (M1/M2/M3) | default (`cpu-1.6`) | already fast via native ARM + AMX |
| NVIDIA discrete GPU | `TEI_IMAGE_TAG=89-1.6` (match your compute capability) + add a `deploy.resources.reservations.devices` block to docker-compose.yml | 10–50× |
| Intel Arc iGPU (Arc 140T etc.) / Apple Metal | not supported by TEI today | — |
| AMD ROCm | not supported by TEI today | — |

```bash
TEI_IMAGE_TAG=cpu-ipex-1.6 docker compose up --detach
```

Intel Arc and Apple Silicon GPU acceleration would require switching
the inference backend (Optimum-Intel / OpenVINO, or CoreML); not in
scope for this module. Until that lands, the practical answer for
laptops without NVIDIA is: index once on the fastest available
machine, share the result. The data layout supports this — see
`SEARCH_DATA_HOME` above; the index is a self-contained directory
that can be tarred up and distributed.

## Use cases

### Technical writer in `liferay-learn`

Index the English docs corpus, then surface related articles when
writing or reviewing.

```bash
cd ~/projects/liferay-learn
search ingest docs/dxp/latest/en                            # ~14 min first run, seconds incremental
search query "client extension deployment"                   # natural-language → article
search similar docs/dxp/latest/en/some-article.md            # related-article surface
```

Default extension (`.md`) is the right setting; no env var needed.

### Developer working in `liferay-portal`

Index the Java source of one or more modules, then find code by
intent or by example.

```bash
cd ~/projects/liferay-portal
SEARCH_FILE_EXTS=.java search ingest modules/apps/headless/headless-delivery

search query "REST endpoint that lists comments for a blog posting"
search similar modules/apps/headless/headless-delivery/headless-delivery-impl/src/main/java/com/liferay/headless/delivery/internal/resource/v1_0/CommentResourceImpl.java
```

`search query` returns method-level hits with a `Class > method`
heading path. `search similar <path>` finds peer implementations
across the indexed subtree.

Scope each ingest to what you actually need (a module or two);
indexing the entire `modules/apps/` tree is possible but takes
hours and is rarely what you want.

### Content engineer cross-referencing docs against portal source

Maintain two indices side by side — one per repo, one per corpus.
Because `SEARCH_DATA_HOME` defaults to a per-checkout sibling
directory, the two indices stay isolated automatically.

```bash
# Index 1: liferay-learn docs (default extension)
cd ~/projects/liferay-learn
search ingest docs/dxp/latest/en

# Index 2: liferay-portal Java for the same feature area
cd ~/projects/liferay-portal
SEARCH_FILE_EXTS=.java search ingest modules/apps/headless/headless-delivery

# Query each in its own checkout
( cd ~/projects/liferay-learn   && search query "client extension manifest" )
( cd ~/projects/liferay-portal  && search query "client extension manifest" )
```

The pattern generalizes to any number of repos: cd into the
checkout that contains the corpus you want to search, run the same
CLI. The data-home resolution handles separation; you do not need to
juggle collection names.