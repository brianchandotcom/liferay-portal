---

allowed-tools: [Bash, Edit, Glob, Grep, Read, Write]
argument-hint: '<path/to/rest-openapi.yaml | module-name>'
description: Apply the MCP-ready documentation pass (S1-S15) to a Liferay REST OpenAPI source file. Adds descriptions to every operation, parameter, schema, and property; fixes typos and grammar; documents enums and response gaps; extracts every shared parameter into components.parameters. Documentation-only; no breaking changes to the generated Java surface.
name: mcp-ready-openapi

---

# MCP-Ready REST OpenAPI Documentation Pass

Apply the S1-S15 checks defined in the audit to a single `rest-openapi.yaml`, so an MCP server or LLM agent that reads it can navigate the API without other sources.

The pass is **documentation-only and additive**. The generated Java surface (DTOs, Resource interfaces, client classes, method signatures, JSON field order, JSON-Schema `type`, `format`, `required`) MUST NOT change. Annotation parameters such as `@Schema(description = ...)` are allowed because they do not alter the public API or ABI contract.

## Input

`${ARGUMENTS}` is either:

1. An absolute or relative path to a source `rest-openapi.yaml`.

1. A module name (with or without the `-impl` suffix) — resolved by globbing `modules/**/<name>-impl/rest-openapi.yaml`.

When `${ARGUMENTS}` is empty, abort with a clear message asking for one of the two forms.

## Preconditions

Verify once at the start of the run. Fail fast with a clear message if any is missing.

1. The current working directory is a Git checkout of `liferay-portal`.

1. The working tree is clean (`git status --porcelain` is empty), or only contains the target `rest-openapi.yaml`. Refuse to proceed when unrelated changes exist.

1. The resolved YAML path exists, is readable, and does not contain `/build/resources/main/` — that path identifies the generated mirror, which MUST NOT be edited.

1. The corresponding `<module>-impl/src/main/java/.../internal/resource/v1_0/*ResourceImpl.java` exists. When it does not, the YAML is not a recognized rest-builder source and the pass cannot proceed.

## Workflow

The pass runs in five steps. Apply them in order.

### Step 1: Resolve and Validate Input

1. Resolve `${ARGUMENTS}` to an absolute path to the source `rest-openapi.yaml`.

1. Confirm the path does not contain `/build/resources/main/`.

1. Identify the module root as the parent directory of the YAML file.

1. Identify the sibling modules `<module>-api`, `<module>-client`, and `<module>-test` (when present).

### Step 2: Map the Surface (Research)

Before editing, gather enough material to write accurate descriptions. Implementation-driven descriptions are mandatory; do not paraphrase from `operationId` or `summary` alone.

1. Read the source `rest-openapi.yaml` in full.

1. Read every `*ResourceImpl.java` under `<module>-impl/src/main/java/.../internal/resource/v1_0` (skip `Base*ResourceImpl.java` and `OpenAPIResourceImpl.java`). Each public method maps to one OpenAPI operation; the method name is the operation ID. Capture, per method: the service calls it makes, the validation it performs and the exception class that maps to each response code, the addressing scheme (`/{id}` versus `/by-externalReferenceCode/{externalReferenceCode}` versus a multi-key path), the upsert semantics, the side effects (workflow transitions, cascade deletes, inventory or scheduling implications), and the filter or sort behavior for `*Page` list methods. The output of this research drives the S7 descriptions.

1. Read every `*DTOConverter.java` under `<module>-impl/src/main/java/.../internal/dto/v1_0/converter`. Each converter maps backend model fields to OpenAPI DTO properties; capture the mapping so property descriptions are accurate.

1. Read the relevant backend models under the domain-specific `*-api` modules. Note required-on-create fields, computed fields, and FK relationships.

1. Read the relevant `*Constants.java` classes. Capture integer-to-meaning mappings for enum-like fields. Some constants reference `WorkflowConstants` indirectly; resolve those to the underlying integer values.

1. Read every `*EntityModel.java` under `<module>-impl/src/main/java/.../internal/odata/entity/v1_0` to know which fields support `filter` and `sort`.

### Step 3: Apply S1-S15

Apply every rule in the **S1-S15 Reference** section below. Each rule has an explicit acceptance check. Skip a rule only when the **Skip When** clause matches.

The order matters: apply the systemic fixes (S1-S4) first so the surface is correct, then the structural changes (S5-S6, S15), then the content-rich descriptions (S7-S11), then the response gaps (S12), then the deprecation and naming notes (S13-S14).

Before writing any description, **Rule SE** below dictates the YAML emission format that keeps the file out of SF1 territory in the first place. After every batch and once more at the end, run **Rule SF** to clean up any hazard the writer or an earlier pass let through.

#### Rule SE — Description Emission Format (Write-Time Blocking)

**Why:** This skill writes long descriptions on `info`, every operation, every shared parameter, every schema, and every property. A naive YAML emitter quotes any value that contains a YAML control token, and historically this skill's automation has picked **single-quoted** scalars whenever quoting was needed. That choice plants SF1 hazards across the file on every run, so even when Rule SF cleans them up the next pass plants them again. The fix is at the writing side: never emit a single-quoted scalar, and prefer to rephrase prose so it can sit as a plain block scalar with no quoting at all. This rule is **write-time blocking** — apply it inside every S-rule that writes a description (S5, S7, S8, S9, S10, S11, S12, S13, S14, the parameter descriptions extracted by S15, and the tag descriptions added by S6).

**How To Apply:**

1. **Plain block scalar is the default.** Write each `description:` as a plain block scalar — the key on one line, the value as continuation lines indented one level deeper, with no opening `"` or `'`. This is what rest-builder's `_formatDescription` round-trips cleanly and what the source-formatter never has to quote-rewrite.

	```yaml
	description:
	    Versioned product catalog scoped to a company.
	```

1. **Rephrase to keep the value unquoted.** A plain block scalar is impossible whenever the value contains a YAML control token in a position YAML would interpret. The most frequent offender is ` : ` (colon-space), which YAML treats as a key/value separator in a mapping context — so a sentence like `Filterable: every entry-model column.` cannot sit as a plain scalar. Rephrase the prose to drop the control token:

	- Replace ` : ` (colon-space) with ` -- ` (space, two hyphens, space) or split the sentence into two — `Filterable. Every entry-model column.` except for `Common workflows:` section.

	- Replace a leading `-`, `?`, `:`, `&`, `*`, `!`, `|`, `>`, `%`, `@`, or backtick with a wording that starts with a normal alphanumeric — `Description...` not `-- Description...`.

	- Replace internal `#` (which YAML treats as a comment when preceded by whitespace) with the word it stands for.

	The rephrasing is mechanical and never changes meaning: ` -- ` reads as a dash break, two sentences read as two sentences, and the value emits as a plain block scalar without quoting.

1. **When quoting is unavoidable, use double-quoted, never single-quoted.** A small minority of values must remain quoted because the prose carries a literal `: ` that cannot be rewritten (for example, a regex pattern, an OData expression sample, or an explicit `key: value` example). In those cases, emit the value as a double-quoted block scalar:

	```yaml
	description:
	    "OData v4 filter expression. Example -- name eq \"Hand Saw\" and createDate gt 2024-01-01T00:00:00Z."
	```

	Never emit `'...'`. Even one single-quoted value per file is one SF1 hit. When the underlying YAML library defaults to single-quoted output, override the emitter's `default_style` parameter (PyYAML: `default_style='"'`, ruamel.yaml: `yaml.preserve_quotes = False` and explicitly mark the node) or post-process every single-quoted scalar to double-quoted before the file is written.

1. **Brace placeholders are written as `<placeholder>`, never `{placeholder}`, when they sit inside what would become a quoted scalar.** Even when the surrounding scalar emits as a plain block scalar today, a later edit that introduces a control token could force it to be quoted on the next round-trip — and a quoted scalar with two or more `{...}` tokens triggers the SF2 catastrophic backtrack. Write `<channelId>`, `<productId>`, `<id>` in prose from the start.

**Acceptance Check:**

- Every description this pass writes lands as a plain block scalar in the source YAML (the line after `description:` does not start with `"` or `'`).

- Across the entire file after this pass, `grep -cE "^ +'[^']" <yaml>` returns only literal-apostrophe false positives (verified by a state-machine pass), and `grep -cE ": '" <yaml>` returns zero hits on the lines this pass added or rewrote.

- After buildREST, the regenerated file preserves the plain-or-double-quoted style on every value this pass wrote.

**Skip When:** Never.

#### Rule SF — Source Formatter Compatibility (Blocking)

**Why:** Liferay's source-formatter is invoked by every CI build and by `ant format-source-local-changes` locally. Two YAML patterns make the file unformattable: every commit that introduces or leaves either pattern in place takes minutes to hang format-source, exhausts the JVM heap, and fails CI. This rule is **blocking** — the pass cannot proceed to Step 4 until both sweeps return zero hits on the entire file (not just the lines this pass edited).

**How To Apply:** Run the two sweeps below over the entire `rest-openapi.yaml` after every edit batch and again at the end of Step 3. Each sweep both verifies and, when violations exist, rewrites the offending tokens to the safe form. The sweeps must operate on the whole file because earlier work in the same module may have left hazards behind that this pass is now obligated to fix; format-source will block the commit regardless of which pass introduced the token.

Both sweeps require **stateful, quoted-scalar-aware parsing**. A naive regex pass that does not track when the cursor is inside an open multi-line scalar will misidentify literal `'...'` characters inside an open double-quoted block scalar as YAML single-quoted scalars, and will corrupt the YAML when it tries to "convert" them. Use a Python state-machine parser; do not attempt these conversions with `sed` or single-pass grep substitutions.

1. **Sweep SF1 — Single-quoted scalars.** Locate every YAML single-quoted scalar in the file. Three patterns occur in rest-builder output:

	- **Inline keyed scalar** — `<indent>key: '<text>'` on a single line. Convert to `<indent>key: "<text>"`.

	- **Quoted response-code keys** — `<indent>'<digits>':` (for example, `'403':`, `'422':`). The keys must remain quoted because YAML otherwise parses them as integers; convert to `"<digits>":`.

	- **Multi-line single-quoted block scalars** — a continuation line starting with `<indent>'<text...>` whose value spans multiple lines and closes on a later line at the same indent with `<...text>'`. These are emitted whenever a description contains a `: ` (colon-space) sequence that YAML would otherwise treat as a key/value separator. Convert the whole span to a double-quoted block scalar: replace the opening `'` with `"`, replace the closing `'` with `"`, unescape every internal `''` to `'`, and escape every internal `"` to `\"`.

	Every hit is a single-quoted scalar that `YMLStylingCheck._formatQuotes` rewrites one at a time via full-file `replaceFirst`; on a multi-thousand-line YAML the cost is O(n²) and the JVM exhausts the heap. After conversion, `rest-builder` preserves the new quote style on every subsequent `buildREST` run, so the fix is durable. The preferred long-term remedy is to rephrase the offending description so it can sit as a plain block scalar (replace ` : ` with ` -- ` or split into two sentences, except for `Common workflows:` section); the conversion to double-quoted is the safe immediate fix.

	**False positive to avoid:** a literal apostrophe sequence inside a double-quoted multi-line scalar — for example, `'com.liferay'` written inside the `info.description` quoted block — is not a YAML single-quoted scalar and must not be converted. Only a state-machine parser that tracks open quote context can distinguish the two.

1. **Sweep SF2 — Brace placeholders inside quoted strings.** Locate every `{placeholder}` token whose position is inside a YAML quoted scalar (single-line or multi-line, double-quoted or single-quoted). The hazard is `YMLDefinitionOrderCheck._pathPattern1`, whose `[^{}"]*\{[^}]+\}[^{}"]*` chunks match across newlines and catastrophically backtrack when two or more `{...}` tokens appear inside the same quoted string. Inside any quoted scalar, rewrite every `{placeholder}` to `<placeholder>`. Plain block scalars (the leading line carries no `"` or `'`) and YAML path keys that end with `:` (such as `/products/{id}:`) are unaffected and must be left alone. The replacement is mechanical and never changes meaning; `<channelId>` and `{channelId}` are equivalent placeholders in prose.

**Acceptance Check (Whole-File, State-Machine Aware):**

- After running the sweeps, parse the file with a state machine and assert: every YAML quoted scalar (single- or double-) contains zero `{` characters; and zero single-quoted scalars remain.

- For a quick command-line sanity check, the following pair is necessary but not sufficient:

	```bash
	grep -nE "^ *[a-zA-Z_]+: '" <yaml>      # SF1 inline; expect zero hits
	grep -nE "^ +'[^']" <yaml>               # SF1 block-start; expect only literal-apostrophe false positives
	```

	The block-start grep can return one or two false positives that are actually literal `'<text>'` substrings sitting on a continuation line of a double-quoted multi-line scalar (most commonly inside `info.description`). The state-machine pass must confirm those hits are not real single-quoted scalars before approving the sweep.

A failed Acceptance Check is a **hard stop** — do not run buildREST, do not commit. Fix the YAML, rerun both sweeps, and only then continue.

**Verification That The Sweeps Worked:** After Step 4's full module build passes, run `ant format-source-local-changes` from `portal-impl`. The expected outcome is a clean run in under one minute with `Formatting with SF took 0 seconds` in the log; a run that hangs past two minutes confirms the sweeps missed a hazard and must be re-applied.

**Skip When:** Never.

### Step 4: Run buildREST and Verify

1. From the `modules` directory (the repository root has no `settings.gradle`), run:

	```bash
	cd modules
	../gradlew \
		:apps:<gradle-path>:<module>-impl:buildREST \
		--no-daemon
	```

	`<gradle-path>` is the colon-separated path from `modules/apps` to the parent of the module.

1. Inspect the resulting Git diff for the `<module>-api` DTOs, the `<module>-impl` graphql `Query.java` and `Mutation.java`, and the `<module>-impl` `Base*ResourceImpl.java`. The expected change is annotation-only: existing `@Schema`, `@GraphQLField`, `@GraphQLName`, `@Operation`, and `@Tag` annotations gain a `description = ...` parameter. **Any change to a method signature, field name, type, class name, or annotation other than the addition of a `description = ...` parameter is a regression and must be reverted.** The most common cause is an S15 component parameter whose `schema.type` (or `format`) does not match the value the `*ResourceImpl` already uses — correct the component definition rather than reverting the extraction.

1. Run the full build for the module (skip the broken tools when running locally):

	```bash
	../gradlew \
		:apps:<gradle-path>:<module>-impl:build \
		:apps:<gradle-path>:<module>-api:build \
		:apps:<gradle-path>:<module>-client:build \
		--exclude-task findSecurityBugs \
		--exclude-task pmdMain \
		--exclude-task pmdTest \
		--exclude-task spotbugsMain \
		--no-daemon
	```

	**Build Successful** is the gate. The excluded tasks are environment-specific tool failures unrelated to this pass.

1. Confirm the regenerated mirror at `<module>-impl/build/resources/main/META-INF/liferay/rest/rest-openapi.yaml` is byte-identical to the source (`diff` exits with `0`). When the source and mirror diverge (for example, because rest-builder rewrites paragraph breaks in `info.description` differently in each output path), run `processResources --rerun-tasks` to refresh the mirror, then re-verify.

1. Remove any `derived-config.txt` build artifacts left in the module roots; they are not tracked.

### Step 5: Commit

Split the work into two commits so the YAML edits and the generated-Java churn stay reviewable independently. Use the `commit` skill, which prefixes the message with the current branch's LPD ticket (or a placeholder `LPD-XXXXX` when no ticket exists).

1. **Commit 1 — Documentation pass:** the `rest-openapi.yaml` source only. Everything from S1-S15: typos, grammar, title, `info.description`, top-level `tags`, operation descriptions, parameter descriptions, schema descriptions, property descriptions, enum mappings, response gaps, deprecation pointers, schema-name aliases, and the `components.parameters` block (with every inline path or query parameter replaced by a `$ref`).

1. **Commit 2 — Generated artifacts:** every file rest-builder regenerated from the new YAML. This is the api and impl Java (DTOs, `Base*ResourceImpl.java`, `Mutation.java`, `Query.java`, `OpenAPIResourceImpl.java`). The mirror at `<module>-impl/build/resources/main/...` is gitignored and is not part of the commit. The diff is strictly additive annotation parameters.

Never bypass signing (`--no-gpg-sign`, `-c commit.gpgsign=false`) or hooks (`--no-verify`). When signing fails because the SSH agent is unreachable, stop and surface the failure to the user.

## S1-S15 Reference

### S1 — Fix the "Succesfully" Typo

**Why:** The misspelling appears in operation response descriptions and propagates into the generated client.

**How to Apply:** In the YAML, replace every occurrence of `Succesfully` with `Successfully`.

**Acceptance Check:** `grep -c 'Succesfully' <yaml>` returns `0`.

**Skip When:** The string does not appear.

### S2 — Collapse Duplicated Words in info.title

**Why:** Some titles include a duplicated token (for example, `Admin Admin`) introduced when a module name was concatenated into the title twice.

**How to Apply:** In `info.title`, collapse any duplicated word.

**Acceptance Check:** `info.title` contains no repeated whitespace-separated tokens.

**Skip When:** The YAML does not have a duplicated word in `info.title`.

### S3 — Fix Article/Noun Agreement in Summaries

**Why:** Operation summaries often use the wrong indefinite article, and the misagreement propagates into the generated client and Swagger UI.

**How to Apply:** For every `summary:` line, audit the article/noun agreement:

1. `Gets an <X>`, `Updates an <X>`, `Deletes an <X>`, `Replaces an <X>` when `<X>` starts with a consonant sound: replace `an` with `a`.

1. `Gets a <X>`, `Updates a <X>`, `Deletes a <X>`, `Creates or updates a <X>` when `<X>` starts with a vowel sound: replace `a` with `an`. Leave the `u`-words alone (`a unit` is correct because `unit` starts with a `y` sound) and `eu`/`uni`-prefixed words.

**Acceptance Check:** Re-grep the YAML for `(an [b-df-hj-np-tv-z]|a [aeiou])` patterns on `summary:` lines and confirm each remaining occurrence is grammatical.

**Skip When:** No affected summaries are present.

### S4 — Normalize info.title

**Why:** Some titles diverge from the project's canonical pattern (for example, `Headless Delivery <Domain> API` instead of `Liferay <Family> Delivery <Domain> API`). The MCP consumer uses the title as a primary identifier; a consistent shape makes the surface easier to enumerate.

**How to Apply:** Set `info.title` to the canonical pattern used by sibling modules in the same family. Inspect a few peer modules to confirm the prefix and family token before rewriting.

**Acceptance Check:** `info.title` matches the canonical pattern used by every sibling module in the same family.

**Skip When:** The title already matches the pattern.

### S5 — Rewrite info.description

**Why:** Today `info.description` carries only the JAR coordinates. An MCP agent needs the API's purpose, primary entities, perspective (admin versus buyer, or the equivalent split in the relevant domain), and a few worked workflows.

**How to Apply:** Replace `info.description` with a multi-paragraph description that covers, in this order:

1. What the API does (one or two sentences).

1. Primary entities, named explicitly.

1. Perspective (which audience the API targets, and which slice of the domain it owns).

1. Cross-references to sibling modules ("for the X side of this flow see <sibling-module>").

1. A `Common workflows:` section listing the most useful end-to-end flows for an agent. Use plain prose with `--` separators; do not rely on Markdown paragraph breaks because rest-builder collapses them on round-trip (see the **Known rest-builder Behavior** section below). End the last workflow with a period and no trailing space, so the auto-appended JAR coordinates sentence reads cleanly.

1. Java client JAR coordinates: group ID, artifact ID, version. rest-builder strips any text from the `A Java client JAR is available for use with the group ID '...'` substring to the end of the description and re-appends the canonical sentence on every `buildREST` run (`RESTBuilder._addClientVersionDescription`). Therefore the JAR coordinates MUST be the last content in `info.description`; anything written after them is silently deleted on the next build. Place the `Common workflows:` section immediately before the JAR coordinates sentence, not after.

Avoid breaking hyphenated words (`shop-by-diagram`, `cross-reference`) across lines in the source: `_formatDescription` wraps at a column limit, and a folded newline mid-hyphenation turns into a literal space (`shop-by- diagram`). Either keep the word on a single line in the source or rephrase so the wrap lands on a whitespace boundary.

**Acceptance Check:** `info.description` is at least four sentences. The `Common workflows:` section precedes the JAR coordinates sentence, which is the last sentence in the description.

**Skip When:** Never.

### S6 — Add the Top-Level Tags Block

**Why:** Operations declare inline `tags: -   <Tag>` but the top-level `tags:` list is missing. Tag descriptions are the entry point for an MCP consumer browsing the API.

**How to Apply:** Add a `tags:` block at the very end of the YAML, alphabetically after `servers`. Each tag referenced by any operation gets one entry with a one-sentence description of what the tag groups.

**Acceptance Check:** Every tag string referenced under `tags:` inside an operation has a matching top-level entry with a non-empty description.

**Skip When:** Never.

### S7 — Add Implementation-Driven Operation Descriptions

**Why:** Today operations carry a summary but rarely a description. An agent needs the full behavior, addressing scheme, idempotency, side effects, and sync versus async semantics — sourced from the implementation, not paraphrased from the operation ID.

**How to Apply:** For every operation in `paths:`, add a `description:` key (alphabetically before `operationId:`) that captures:

1. The service call(s) the method makes. Source these by reading the matching method on the matching `*ResourceImpl.java` from Step 2.

1. The validation the method performs and the exception class that maps to each response code.

1. The addressing scheme: `/{id}` (internal PK) versus `/by-externalReferenceCode/{externalReferenceCode}` (idempotency key) versus a multi-key path.

1. POST upsert: explicit when a POST is upsert-by-ERC.

1. PATCH semantics: JSON Merge Patch (only supplied fields are modified).

1. Side effects on related resources.

1. Sync versus async: when 202 is declared alongside 200/201/204, explain what triggers async.

1. For `*Page` list operations, name the supported `filter` fields (sourced from `*EntityModel.java`), the search corpus (the indexed fields), and the sortable fields.

A practical pattern is to run a research subagent over every `*ResourceImpl.java` and emit a JSON map from `operationId` to a 1-3 sentence description, then a small Python pass that injects each description before its matching `operationId:` line in the YAML.

**Acceptance Check:** Every operation under `paths:` has a non-empty `description:` field, and the description names a concrete service call, validation path, or filterable field set.

**Skip When:** Never.

### S8 — Document Query Parameters

**Why:** `filter`, `search`, `sort`, `page`, and `pageSize` are routinely unannotated.

**How to Apply:** Document each shared query parameter inside its `components.parameters` entry (S15 extracts the parameters first, so the description is written exactly once). For list endpoints, document:

1. `filter`: OData v4 expression syntax plus the list of filterable fields (sourced from `*EntityModel.java`).

1. `search`: the matched fields (sourced from the indexer or the entity model).

1. `sort`: the list of sortable fields (sourced from `*EntityModel.java`).

1. `page`: indexing convention (one-based) and the relationship with `pageSize`.

1. `pageSize`: default value, when omitted.

**Acceptance Check:** Every query parameter on every list endpoint has a non-empty `description:` (either on the `components.parameters` entry or, when extraction is impossible, on the inline schema).

**Skip When:** Never.

### S9 — Add Schema Descriptions

**Why:** Top-level schemas have no `description:` key today. The schema description is the canonical statement of what an entity is.

**How to Apply:** For each schema under `components.schemas`, add a `description:` key (alphabetically before `properties:`) with a one-sentence statement of the schema's role in the domain.

**Acceptance Check:** Every schema under `components.schemas` has a non-empty `description:`.

**Skip When:** Never.

### S10 — Add Property Descriptions

**Why:** Properties are the highest-value documentation surface for an MCP agent and the most commonly missing.

**How to Apply:** Add a `description:` to every property of every schema under `components.schemas`. Apply these recurring patterns:

1. **FK IDs**: `Reference to the <Entity> entity (FK).`

1. **External reference code fields**: `Idempotency key for create and update; must be unique per <entity> within the company.`

1. **I18n maps** (any `additionalProperties: type: string` map keyed by locale): `Localized text. Map keys are locale codes (for example, en_US, it_IT); values are the translated strings.`

1. **HATEOAS `actions` map**: `Map of HATEOAS actions available to the current user, keyed by action name. Each value carries the href template and HTTP method, computed dynamically from user permissions. Read-only.`

1. **Non-obvious booleans**: a per-field description that explains the user-visible effect.

1. **bigdecimal money fields**: clarify the currency context (inherited from the parent), tax-inclusive versus exclusive, and computed versus stored.

1. **Dates and timestamps**: clarify timezone, format (ISO 8601), and whether the field is read-only.

**Acceptance Check:** No property in any schema is missing `description:` (excluding properties that already use `$ref:` exclusively — see **Known rest-builder Behavior** below).

**Skip When:** Never.

### S11 — Document Enum-Like Integer Fields

**Why:** Integer status and type fields are opaque without their integer-to-meaning mapping.

**How to Apply:** Document the integer-to-meaning mapping in the property `description:` text. Do NOT add an explicit `enum:` key. The default is description-only because `enum:` restricts the wire format and makes future status additions a breaking change for clients. Source the mappings from the matching `*Constants.java` class; resolve any indirection (for example, through `WorkflowConstants`) to the underlying integer values.

For each documented enum field, include the mapping in `description:` using the format:

```text
"Integer status: 0=<First>, 1=<Second>, 2=<Third>, ..."
```

**Acceptance Check:** Every integer field that has an associated `*Constants.java` mapping carries the mapping in its `description:`.

**Skip When:** A field is genuinely a free-form integer with no constants class (rare).

### S12 — Close Response Coverage Gaps

**Why:** Operations typically declare 200/201/202/204 plus 400/401/404/500, leaving 403, 409, and 422 implicit.

**How to Apply:** For every operation in `paths:`, add the response codes that apply to the operation's behavior, sourced from `*ResourceImpl.java`:

1. **403 Forbidden**: add to every operation that operates on a specific resource (`/{id}`, `/by-externalReferenceCode/{externalReferenceCode}`, and any sub-resource endpoint). Description: `The authenticated user lacks permission for the requested action on this <entity>.` Skip for list endpoints, which return a permission-filtered subset rather than denying outright.

1. **409 Conflict**: add only when a module-local exception mapper explicitly returns `Response.Status.CONFLICT`. Enumerate the candidates by listing `src/main/java/.../internal/jaxrs/exception/mapper/*ExceptionMapper.java` in the impl module and grepping for `Response.Status.CONFLICT`; each match names the exception that produces a 409. Then trace each exception back to the `*ResourceImpl.java` methods that can raise it (directly or through the service it delegates to), and add a 409 only on those operations. The description names the specific conflict (for example, the duplicate key collision or the forbidden transition). Do not assume duplicate external reference codes produce a 409 — Vulcan's global `DuplicateExternalReferenceCodeExceptionMapper` returns 400 Bad Request, so POST upserts and any create that only risks an ERC collision stay on 400.

1. **422 Unprocessable Entity**: add to every create or update endpoint that can raise domain validation errors (invalid lookup key, missing required cross-reference, out-of-range value, broken cross-domain binding). Description names the kind of validation.

Each new response uses the standard `Error` schema envelope. rest-builder does not propagate response codes into the Java annotations, so adding these is pure YAML metadata — the mirror updates, but no Java file changes.

**Acceptance Check:** Every per-resource operation declares 403; every operation that can raise a `CONFLICT`-mapped exception declares 409; every create or update endpoint declares 422.

**Skip When:** Never.

### S13 — Add Deprecation Pointers

**Why:** Deprecation is invisible to an MCP agent unless the YAML carries a pointer. Clients still depend on deprecated definitions, so they cannot simply be removed; they need a successor pointer instead.

**How to Apply:** For every deprecated schema or property:

1. Add `deprecated: true` at the schema or property level when it is not already present.

1. Prefix `description:` with `Deprecated -- use <Successor> instead.` and include a one-line explanation of why.

Do not remove deprecated definitions — clients still depend on them.

**Acceptance Check:** Every schema or property the team has flagged as deprecated carries both `deprecated: true` and a `description:` that points to the successor.

**Skip When:** The module has no deprecated entities.

### S14 — Document Schema Name Aliases

**Why:** Some modules use a non-standard schema name (for example, prefixed with the module's audience) to avoid colliding with an identically named schema in a sibling module. Renaming the schema would change the generated Java class name and break clients.

**How to Apply:** Keep the schema name as-is. In the schema `description:`, document the alias: `Corresponds to the standard <Project> <CanonicalName>; the <ActualName> name is preserved for client compatibility.` Normalize human-readable strings (summary, tag description, prose) to use the conventional name with a space.

**Acceptance Check:** The schema name is unchanged. The schema description names the alias.

**Skip When:** The module has no schema-name divergence.

### S15 — Extract Every Shared Parameter Into components.parameters

**Why:** Path and query parameters are duplicated across operations. Each duplication is another place where the description can drift, and inline collisions confuse generated docs. rest-builder honors `$ref` to `components.parameters` on round-trip, so the extraction survives every subsequent `buildREST` run.

**How to Apply:**

1. Build a `components.parameters` block alphabetically before `components.schemas`. Define one entry per unique parameter that any operation references. Standard entries that virtually every module needs:

	1. `externalReferenceCodePathParam` (path, required, string).

	1. `idPathParam` (path, required, int64).

	1. `filterQueryParam`, `pageQueryParam`, `pageSizeQueryParam`, `searchQueryParam`, `sortQueryParam` (query, optional).

	Module-specific entries follow the same naming convention: an entity-typed path parameter named `xxxId` becomes `xxxIdPathParam`; a domain query parameter named `xxx` becomes `xxxQueryParam`.

1. Match each new component definition's `in`, `name`, `required`, `schema.type`, and `schema.format` to the values the `*ResourceImpl.java` already uses for that parameter — a mismatch on `type` or `format` (for example, `string` versus `integer`, or `int32` versus `int64`) breaks the impl override and fails compilation in Step 4.

1. Replace every inline parameter block under `paths:` with `$ref: "#/components/parameters/<name>"`. This is a hard requirement; no inline `in: path` or `in: query` block remains under any operation.

1. After the replacement pass, scan the `components.parameters` block again and remove any entry that is not referenced by `$ref` in `paths:` — unused entries are dead weight.

**Acceptance Check:** No inline `in: path` or `in: query` parameter block remains under any operation. Every parameter is a `$ref`. Every `components.parameters` entry is referenced at least once.

**Skip When:** Never.

## Known rest-builder Behavior

These behaviors are not bugs to fight; document them so the pass produces predictable output.

1. **rest-builder rewrites the source `rest-openapi.yaml` on every `buildREST` run.** The normalized output uses double-quoted scalars and alphabetizes keys. Treat the source as machine-rewritten; do not rely on hand-formatted whitespace surviving the round-trip.

1. **`info.description` paragraph breaks are collapsed** because double-quoted YAML scalars fold consecutive newlines into spaces. To preserve a paragraph break, separate sections with `--` rather than blank lines. After `buildREST` runs, the source and the mirror can disagree on paragraph layout for one round; running `processResources --rerun-tasks` after the first `buildREST` brings them back into parity.

1. **rest-builder auto-appends the JAR coordinates sentence to `info.description`** (`RESTBuilder._addClientVersionDescription`). On every `buildREST` run, the tool searches the description for the literal substring `A Java client JAR is available for use with the group ID '`, deletes everything from that substring to the end of the description, and re-appends the canonical sentence with the current `version`. Any prose written after the JAR coordinates is silently destroyed. The `Common workflows:` section (S5) and every other narrative element MUST sit before the JAR coordinates sentence to survive the round-trip.

1. **`info.description` line-wrapping can split hyphenated words** because `_formatDescription` wraps at a fixed column and the folded YAML scalar turns a mid-word newline into a space (`shop-by-\n diagram` becomes `shop-by- diagram`). Keep hyphenated tokens on a single source line, or rephrase the sentence so the wrap lands on a whitespace boundary.

1. **rest-builder propagates descriptions into three places**: the YAML mirror at `<module>-impl/build/resources/main/META-INF/liferay/rest/rest-openapi.yaml`; the `<module>-api` DTO Java classes (as `@Schema`, `@GraphQLField`, and `@GraphQLName` annotation parameters); and the `<module>-impl` `Base*ResourceImpl.java` (as `@Operation` annotation parameters). The api and client method signatures do not change as long as the YAML `schema.type` and `schema.format` match the impl's parameter types.

1. **rest-builder honors `$ref` to `components.parameters`** when the entry has the same `in`, `name`, `required`, and `schema.type`/`schema.format` as the inline block it replaces. The generated Java rebuilds the same parameter list with the description sourced from the component entry.

1. **rest-builder does NOT propagate response codes into Java annotations.** S12 additions affect only the YAML and the mirror.

1. **rest-builder strips unknown `x-*` extensions** except `x-json-map`, `x-json-string`, and `x-merge-properties`. Do not rely on any other custom extension to survive the build.

1. **Sibling keys to `$ref:` are dropped** in OpenAPI 3.0 components. Do not add a sibling `description:` to a property that uses `$ref:` to point at another schema; describe the referent schema instead.

1. **A property defined purely as `{ $ref: "#/components/schemas/X" }` cannot carry its own description.** When a property's role in the parent schema needs explanation, document it in the parent schema's description text rather than as a property `description:`.

## Expected Output

When the pass completes, report:

1. The module name and the resolved YAML path.

1. Per-rule status: `applied`, `skipped (reason)`, or `n/a`.

1. The number of operations, schemas, and properties that gained a `description:`.

1. The Step 4 verification result: `parity gate passed` or `parity gate failed (details)`.

1. The two commit IDs created in Step 5, in order.

1. Any open items the maintainer needs to confirm (for example, an enum mapping that could not be sourced authoritatively).