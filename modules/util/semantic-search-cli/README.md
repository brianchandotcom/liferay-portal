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
`package` line, and the entire `import` block.

## CLI contract

```
search ingest <dir> [--force]
search query "<text>" [--top N] [--format text|json]
search similar <path> [--top N] [--format text|json]
search status
```

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
Default layout, computed by `DataHome.compute()` from the project
root:

```
<parent-of-project>/<project-basename>-tools[-<worktree-suffix>]/search/
├── qdrant-data/   (vector index)
└── model-cache/   (TEI's model cache)
```

Override the whole path with `SEARCH_DATA_HOME`. Use different
override values to keep separate indices for different corpora.

## Use cases

### Technical writer in `liferay-learn`

Index the English docs corpus, then surface related articles when
writing or reviewing.

```bash
cd ~/projects/liferay-learn
search ingest docs/dxp/latest/en/                            # ~14 min first run, seconds incremental
search query "client extension deployment"                   # natural-language → article
search similar docs/dxp/latest/en/some-article.md            # related-article surface
```

Default extension (`.md`) is the right setting; no env var needed.

### Developer working in `liferay-portal`

Index the Java source of one or more modules, then find code by
intent or by example.

```bash
cd ~/projects/liferay-portal
SEARCH_FILE_EXTS=.java search ingest modules/apps/headless/headless-delivery/

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
search ingest docs/dxp/latest/en/

# Index 2: liferay-portal Java for the same feature area
cd ~/projects/liferay-portal
SEARCH_FILE_EXTS=.java search ingest modules/apps/headless/headless-delivery/

# Query each in its own checkout
( cd ~/projects/liferay-learn   && search query "client extension manifest" )
( cd ~/projects/liferay-portal  && search query "client extension manifest" )
```

The pattern generalizes to any number of repos: cd into the
checkout that contains the corpus you want to search, run the same
CLI. The data-home resolution handles separation; you don't need to
juggle collection names.