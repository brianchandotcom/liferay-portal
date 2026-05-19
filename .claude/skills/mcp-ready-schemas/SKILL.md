---

allowed-tools: [Bash, Edit, Glob, Grep, Read, Write]
argument-hint: '<path/to/rest-openapi.yaml | module-name>'
description: Rewrite every schema and property description in a Liferay REST OpenAPI source file from the underlying implementation, and set a realistic example value on every property. Reads ResourceImpls, DTOConverters, EntityModels, Constants, backend model interfaces, ServiceImpls, persistence, and indexers so each description reflects actual behavior and each example matches what the runtime would produce or accept. Documentation-only; no breaking changes to the generated Java surface.
name: mcp-ready-schemas

---

# Implementation-Driven Schema Descriptions And Examples

Rewrite every entry under `components.schemas` so the schema and property descriptions are sourced from the Java implementation, and set the `example:` value on every property so it carries a realistic value the runtime would produce or accept. Both passes draw from the same source of truth — the rest module bundle, the sibling api and client, and the backend service api and impl — and share a single research step. The descriptions follow the patterns of rules D1 and D2; the examples follow the patterns of rule E1. This skill is the deep counterpart to S9 and S10 from `mcp-ready-openapi`; reach for `mcp-ready-openapi` when the broader S1-S15 sweep is the goal.

The pass is **documentation-only**. The generated Java surface (DTOs, Resource interfaces, client classes, method signatures, JSON field order, JSON-Schema `type`, `format`, `required`) MUST NOT change. Annotation parameters such as `@Schema(description = ...)` and `@Schema(example = ...)` are allowed to change because they do not alter the public API or ABI contract.

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

### Step 1: Resolve And Validate Input

1. Resolve `${ARGUMENTS}` to an absolute path to the source `rest-openapi.yaml`.

1. Confirm the path does not contain `/build/resources/main/`.

1. Identify the module root as the parent directory of the YAML file.

1. Identify the sibling modules `<module>-api`, `<module>-client`, and `<module>-test` (when present).

### Step 2: Map The Domain (Research)

The quality of every description and the realism of every example in this pass depend on Step 2. Read each layer below before writing any description or example; do not paraphrase from the property name, and do not invent example values when the implementation already supplies a concrete one. Capture findings in a per-schema map so Step 3 can cite a concrete file and method for each entry.

#### Layer 1: REST Module Bundle

1. Read the source `rest-openapi.yaml` in full so the complete list of schemas, properties, types, formats, enums, and `$ref` chains is in scope. Note every property that already carries an `example:` — those values are the in-repo style guide **only when they satisfy the property's declared type, format, enum, and other constraints, and match what the converter actually emits**. Reuse the value verbatim when an identical property recurs on another schema (the same `id`, `externalReferenceCode`, `dateCreated`, or localized `name` map should not vary across schemas). When an existing example violates a constraint, contradicts the converter, embeds a placeholder, uses the wrong wire format, or diverges from the canonical project-wide value for an obviously shared field, mark the example for replacement under **Rule E1**.

1. Read every `*DTOConverter.java` under `<module>-impl/src/main/java/.../internal/dto/v1_0/converter`. The converter maps a backend model field to one OpenAPI DTO property. Capture, per property: the source field name on the backend model, any computed expression, any conditional (for example, `_isEmbeddedFieldsFlagSet`), and any locale or permission lookup. The converter is the authoritative bridge between the schema and the model; it also pins the wire-shape decision (single string versus localized map, integer versus enum, raw URL versus templated URL) that the example must match.

1. Read every `*ResourceImpl.java` under `<module>-impl/src/main/java/.../internal/resource/v1_0` (skip `Base*ResourceImpl.java` and `OpenAPIResourceImpl.java`). The resource methods reveal which properties are accepted on create versus update, which are read-only outputs, and which trigger workflow or cascade behavior on write. They also identify which properties are accepted as client input (where a client-supplied example is most useful) and which are returned as computed output (where a server-emitted example is most useful).

1. Read every `*EntityModel.java` under `<module>-impl/src/main/java/.../internal/odata/entity/v1_0`. The entity model declares which fields support `filter` and `sort`; mention those capabilities on the matching property description when present.

1. Read every `*Constants.java` and helper class referenced by the converter or the resource. Some constants resolve transitively (for example, through `WorkflowConstants`); follow the chain until the underlying integer values are known.

1. Read the sibling `<module>-api` exported DTO sources. The hand-written `@Schema` annotation comments (when present) sometimes preserve domain knowledge that did not make it into the YAML. Existing `@Schema(example = ...)` annotations (when present) preserve realistic values worth reusing.

1. Read the sibling `<module>-client` DTO classes only when a property's serialization shape is non-obvious (custom `@JsonAdapter` or unwrapped wrapper). Otherwise the client mirrors the api and adds no new information.

1. Read the sibling `<module>-test` integration tests (`*ResourceTest.java`) when present. The test fixtures construct realistic instances and assert against realistic values; both sides are reusable as example material.

#### Layer 2: Backend Service Api

For each backend entity the converter touches, locate the service api module (typically a sibling `*-api` outside the rest bundle, often named after the entity family — for example, `commerce-product-api` for catalog flows). Read:

1. The model interface (for example, `CPDefinition.java`) and its generated `*Model.java` parent. The Javadoc on getters often documents the field's domain meaning and the wire format (ISO 8601 timestamp, ISO 4217 currency code, BCP 47 locale tag), which constrains the example shape.

1. The `*Constants.java` classes in the same module. These define type codes, status codes, and string keys that the converter maps into the DTO. Pick a representative integer or string from the constants set for the example — for an integer status, use the value that maps to the most common active state (typically `Approved` for entity workflow status).

1. The `*LocalService.java` and `*Service.java` interfaces. Method names and Javadoc reveal the canonical action vocabulary (`addX`, `updateX`, `deleteXByExternalReferenceCode`) the DTO participates in. The argument names on finder methods also reveal canonical key formats — `getXByExternalReferenceCode(String externalReferenceCode)` confirms the field is a free-form string whose example should match the project's `AB-34098-789-N` style rather than a UUID or a numeric ID.

1. The `*Util.java` helper classes for any normalization rule applied to a field (trim, lowercase, locale fallback).

#### Layer 3: Backend Service Impl

The api module declares what; the impl module declares why, when, and what realistic values look like. For every entity backing a non-trivial DTO, read the corresponding service impl module (typically a sibling `*-service`) and capture:

1. `*LocalServiceImpl.java` and `*ServiceImpl.java`. The validation methods (`_validate`, `validate`, or inline checks in `addX`/`updateX`) name the rules that an API client must satisfy and the exception classes thrown when they fail. Use these to justify property descriptions that mention "required on create", "must be unique", or "must reference an existing X". They also encode allowed shapes (length limits, regex patterns, allowed enum subsets); pick an example value that satisfies every rule.

1. `*ModelImpl.java`. Default values for columns, generated identifiers, and computed flags surface here. A property whose default value differs from `0` or `null` on the database should mention the default in its description and may carry that default as its example.

1. The persistence layer entries in `service.xml` and the matching `*ModelImpl.java` column annotations. They confirm column nullability and length limits, which justify required-on-create and max-length notes; for a `VARCHAR(75)` column, do not emit a 200-character example.

1. The matching `*Indexer.java` under `*-service` (or `*-search`). The fields the indexer registers feed the `search` semantic on list endpoints; cross-link these to the matching DTO property description when the property is search-driven.

1. The matching workflow handler under `*-service` (when present). A property like `status` whose values come from `WorkflowConstants` needs the workflow transition vocabulary, not just the integer mapping.

1. Existing integration tests under `*-service`. Hard-coded values in `add*` and `update*` test paths are reusable example material.

The output of this layer drives mentions of validation, defaults, immutability, and side effects in the descriptions Step 3 writes, and the realism of the example values. When a layer is silent on what a value looks like, fall back to the canonical project-wide patterns listed under **Rule E1** below.

#### Research Output

Before moving to Step 3, produce an in-memory map of the form:

```text
<SchemaName>
  description: <one-sentence statement of the entity's role in the domain>
  source:
    converter: <relative path to *DTOConverter.java>
    model: <relative path to backend model interface>
    serviceImpl: <relative path to backend *LocalServiceImpl.java>
  properties:
    <propertyName>:
      type: <YAML type, format, and $ref summary>
      description: <implementation-derived description>
      existingExample: <value already in the YAML, or "none">
      exampleAction: <add | replace | keep>
      replacementCriterion: <matching criterion when exampleAction is replace, or "n/a">
      example: <chosen example value>
      source:
        converterLine: <file:line>
        modelField: <backend getter name>
        validation: <_validate method name, or "none">
        constants: <fully qualified constants class, or "none">
        siblingExample: <existing example value reused from another schema, when applicable>
```

This map is not committed; it is the working artifact Step 3 cites. A property whose `source` block is empty means Step 2 missed a layer — go back and read it before writing the description or choosing the example. A property whose `exampleAction` is `replace` must record the matching **Replacement Criterion** so the audit trail is explicit.

### Step 3: Rewrite Descriptions And Set Example Values

Apply rules **D1**, **D2**, and **E1** to every entry under `components.schemas`. **D1** and **D2** **replace** existing descriptions; do not preserve prior text when the implementation says something different. **E1** **adds** missing examples and **replaces** existing examples that fail a Replacement Criterion; an existing example that already passes is left in place. Then run **Rule SF** as a blocking gate before Step 4.

The order matters within a schema: write the schema description first (D1) so the property descriptions can use the same vocabulary, then walk the properties in YAML order applying D2 and E1 together so each property's diff stays sequential.

#### Voice And Style For Descriptions (Applies To D1 And D2)

The description is for an MCP agent that has never read the source. Use the implementation as a fact source, not as the vocabulary. Pure behavior; no code surface.

1. **Forbidden vocabulary.** Do not name a Java class, interface, enum, method, field, exception, package, constants identifier, gradle module, or column. No `CPDefinition`, no `CommerceCatalogLocalServiceImpl._validateAccountEntry`, no `AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT`, no `AccountEntryTypeException`, no `com.liferay.commerce.product.model`, no `service.xml`, no `*ModelImpl`. Replace each one with the user-visible noun, verb, or rule it stands for. The implementation tells you that updating the field triggers a reindex; the description says "updating this field triggers a reindex," not "calls `Indexer.reindex`."

1. **Talk about behavior, not code paths.** "The linked account must be a supplier" instead of "validated by `_validateAccountEntry`." "Must be unique per catalog" instead of "throws `DuplicateCPInstanceException`." "Reindexes on save" instead of "calls `CPDefinitionLocalServiceImpl.reindex`."

1. **Plain English.** Use natural names ("account," "catalog," "SKU," "product") rather than the backend class noun. Backtick a name only when it is the actual field name on the wire (`actions`, `externalReferenceCode`, `priceType`).

1. **Numeric mappings stay numeric, label words stay human.** "Integer status: 0=Approved, 1=Pending, 2=Draft" is fine — the labels are the user-facing meanings, not the constants identifiers.

1. **Never repeat example values in the description.** Every property under `components.schemas` carries its own `example:` value (set by Rule E1). A parenthetical like "(for example, USD, EUR)", "(for example, en_US, it_IT)", "(for example, site, external)", or "(for example, available, lowStock)" is now redundant — the `example:` field next to the description provides the concrete value. Describe what the field is and what shape it takes; leave the sample value to `example:`. Strip every existing `(for example, <list>)` parenthetical from descriptions and rephrase the surrounding clause to read as a complete sentence. Enum mappings such as "Integer status: 0=Approved, 1=Pending, 2=Draft" and format identifiers such as "ISO 4217 currency code" or "ISO 8601 timestamp in UTC" are structural facts, not example values, and stay in the description.

#### Voice And Style For Examples (Applies To E1)

The example is for an MCP agent that has never seen the runtime. Choose values that an agent could paste into a request body without further substitution. Pure data; no code surface.

1. **Reuse repo-wide canonical values.** When the same conceptual field already carries an example elsewhere in the file (or in a sibling REST module under the same domain), reuse that value verbatim. A repo-wide `externalReferenceCode` of `AB-34098-789-N` should appear the same way on every schema that exposes the field. Consistency across schemas is more valuable than per-schema novelty.

1. **Realistic, not random.** A product name is `Hand Saw`, not `lorem ipsum`. A currency code is `USD`, not `XYZ`. A locale-keyed map uses `en_US`, `hr_HR`, `hu_HU` (the project's canonical locales), not arbitrary ones.

1. **Match the wire format exactly.** An `int64` ID is a bare integer in YAML (`30130`), not a quoted string. A `date-time` value is an ISO 8601 string in UTC (`2017-07-21T19:30:00Z`). A `bigdecimal` is a decimal number (`33.54`), not a string. A boolean is `true` or `false`, not `"true"`.

1. **No placeholders in the data.** Do not emit `<id>`, `{id}`, or `TODO` as the example value. The example is consumed by validators and documentation rendering tools as a literal value, and a placeholder breaks both.

#### Rule D1 — Schema Description

**Why:** A schema description is the canonical one-sentence statement of what the entity is, sourced from the backend model and converter behavior rather than the property name.

**How To Apply:** For each schema under `components.schemas`, set `description:` (alphabetically before `properties:`) to a one- or two-sentence statement of what the entity is in the domain, the perspective the schema represents (admin write versus delivery read, full record versus summary projection), and the primary lifecycle action (created on POST, updated on PATCH, returned by the matching list endpoint). Use the schema name itself or its plain-English equivalent; do not introduce backend class names.

When the schema name diverges from the underlying entity (for example, the rest layer exposes `Sku` for what the backend calls a product instance), state the relationship in plain prose: "Represents a single purchasable variant of a product." Do not paste the backend class name.

**Acceptance Check:** Every schema under `components.schemas` has a non-empty `description:` written in plain prose, with no Java class, method, constant, exception, package, or column name in the text.

**Skip When:** Never.

#### Rule D2 — Property Description

**Why:** Property descriptions are the highest-value documentation surface for an MCP agent and the most error-prone when paraphrased from the name. Sourcing them from the converter, model, and service impl behavior keeps them accurate; phrasing them in plain English keeps them useful to a reader who does not have access to the codebase.

**How To Apply:** For every property of every schema under `components.schemas`, set `description:` to a single sentence that states what the property carries plus any of the following that the implementation makes true. Each pattern below is a behavior contract, not a code citation — apply the wording verbatim where the pattern fits.

1. **FK identifiers.** State the referenced entity and the FK semantics in plain prose. Pattern: `Reference to the <entity> (FK identifier).` Use the natural noun (account, catalog, product, SKU, channel), not the backend class name.

1. **External reference codes.** State idempotency and uniqueness scope. Pattern: `Idempotency key for create and update; must be unique per <entity> within the company.`

1. **Localized maps.** Any property whose schema is `additionalProperties: type: string` keyed by locale gets: `Localized text. Map keys are locale codes; values are the translated strings.` The `example:` block under Rule E1 supplies the concrete locale keys.

1. **HATEOAS `actions` map.** Pattern: `Map of HATEOAS actions available to the current user, keyed by action name. Each value carries the href template and HTTP method, computed dynamically from user permissions. Read-only.`

1. **Booleans whose meaning is non-obvious.** Describe the user-visible effect, not the database flag. "When true, the catalog is hidden from storefront browsing." Not "set by `_isHidden` in `CatalogLocalServiceImpl`."

1. **Monetary fields.** Describe the currency context (inherited from the parent), tax-inclusive versus exclusive, and computed versus stored. "Cost in the catalog's default currency, tax-exclusive, stored as supplied."

1. **Dates and timestamps.** Describe the wire format (ISO 8601), the timezone, and whether the field is read-only (set by the service rather than the client). "Creation timestamp in ISO 8601 (UTC). Read-only; set when the record is first persisted."

1. **Integer status or type fields.** State the mapping in prose: `Integer status: 0=<Label1>, 1=<Label2>, ...` using the human labels (Approved, Pending, Draft), not the constants identifiers. Do not add an explicit `enum:` key — an explicit enum makes future status additions a breaking change. Reaching for the enum mapping is a sign Rule D2 is bleeding into S11 from `mcp-ready-openapi`; capture the mapping in prose only and stop there.

1. **Validation rules.** When the service rejects a value, state the rule in plain English: required on create, maximum 75 characters, must be a positive number, must match the unit-of-measure pattern, must reference an existing channel. Do not name the validation method or the exception class.

1. **Defaults.** When a non-`null`/non-zero default applies, state it: "Defaults to true," "Defaults to 1," "Defaults to the catalog's base currency." Do not point to the column or model.

1. **Side effects on write.** When updating the property triggers a workflow transition, a search reindex, or a cascade write, say so in plain prose: "Updating this field reindexes the product," "Setting this to false moves the product out of any active workflow," "Removing this entry cascades to all SKUs that reference it."

1. **Search and filter contribution.** When the matching entity model registers the property as filterable or sortable, state it: `Filterable and sortable via the OData query parameter.` When the indexer registers the property as searchable, state it: `Matched by the search query parameter.`

A property's description is one English sentence; when more than one of the patterns above applies, chain them with semicolons or short clauses, not bullet points. The goal is a description an agent can use to fill in a request body without ever seeing the Java source.

**Acceptance Check:** No property in any schema is missing `description:` (excluding properties that already use `$ref:` exclusively — see **Known rest-builder Behavior** below). Every description reads as plain English prose; a grep for `LocalServiceImpl`, `ModelImpl`, `Constants.`, `Exception`, `com.liferay.`, or a dotted Java identifier across the description block returns zero matches.

**Skip When:** A property defined purely as `{ $ref: "#/components/schemas/X" }` cannot carry its own description; describe the referent schema instead.

#### Rule E1 — Property Example

**Why:** Examples are the single most-copied surface in REST documentation. An agent or human assembling a request body reaches for the example first, the description second, and the schema type third. A missing example forces every consumer to invent a value, and a wrong example actively misleads — both cases lead to drift between docs and runtime expectations.

**How To Apply:** For every property of every schema under `components.schemas`:

1. When the property has no `example:` key, insert one immediately after the `description:` block (or, when no description exists, immediately before the YAML keys that follow).

1. When the property already carries an `example:` key, evaluate it against the **Replacement Criteria** below. When any criterion matches, overwrite the existing value with the canonical one chosen from the patterns. When none matches, leave the existing value in place — even when a different value would also be defensible, do not churn examples that already pass.

**Replacement Criteria** — overwrite an existing `example:` value when any of the following holds. Cite the matching criterion in the per-property research map so the maintainer can audit the replacement.

1. The value does not parse as the property's declared `type` (a quoted string on an `int64`, a non-numeric token on a `bigdecimal`, `"true"` instead of `true` on a `boolean`).

1. The value violates the property's declared constraints (`enum:` list, `minimum`, `maximum`, `pattern`, length cap implied by the backing column).

1. The wire format is wrong for the declared `format:` — a `date-time` example missing the timezone or the `T` separator, a `date` example carrying a time component, an ISO 4217 code that is not three uppercase letters, a locale code that does not match the BCP 47 or `xx_XX` shape the converter emits.

1. The value contradicts what the converter actually produces — a single string on a property the converter emits as a localized map, a templated `{id}` URL on a property the converter emits as a resolved absolute URL, an integer status whose label does not exist in the matching constants set.

1. The value is a placeholder (`TODO`, `xxx`, `string`, `0` on a non-identifier numeric field, `null`, an empty string).

1. The value diverges from the project's canonical in-file value for an obviously shared field — the same `externalReferenceCode`, `id`, `dateCreated`, `dateModified`, or localized name map should not differ across schemas in the same file. The canonical value is whichever value the **majority of existing examples** already use (typically the values listed under the patterns below); when no majority exists, prefer the pattern below.

1. The value embeds an internal-only token surface — a Java class name, a constants identifier, a package name, a placeholder like `Test Test Test`, or any string that would not appear in production data.

Each pattern below pairs a property shape with the canonical example value to write. Apply the first pattern that matches; when more than one applies, prefer the more specific one.

1. **FK identifiers** (`type: integer, format: int64, minimum: 0`, named `id`, `<entity>Id`, `parent<Entity>Id`, etc.). Reuse the canonical ID used elsewhere in the file for the same entity family — typically `30130` for the primary record and another small offset (`10130`, `10131`, `20078`) for related records. Match the value to the entity: an `accountId` and a primary `id` on the Account schema should share a value; a `logoId` should not.

1. **Other numeric identifiers** (`type: integer, format: int32`). Use a small positive integer that reflects the property's domain — `1` for `priority`, `100` for `quantity`, `10` for a percentage-style integer.

1. **External reference codes.** Reuse `AB-34098-789-N` verbatim. This value appears across the repo and matches the project's canonical pattern.

1. **Localized maps** (`additionalProperties: type: string` with no inner `$ref`, keyed by locale). Emit a three-entry map using the project's canonical locales:

	```yaml
	example:
	    en_US: Hand Saw
	    hr_HR: Product Name HR
	    hu_HU: Product Name HU
	```

	When the property is not a generic name but a structured value (a `formatPattern` map, a `friendlyURL` map), substitute the realistic per-locale value while keeping the same three locale keys.

1. **Arrays of strings** (tags, domains, channel kinds). Emit a three-item list of realistic values:

	```yaml
	example:
	    -   tag1
	    -   tag2
	    -   tag3
	```

	For `domains`, use `example.com`, `example.org`. For `keywords`, use realistic short tokens from the same family.

1. **Arrays of integers** (FK lists like `organizationIds`, `categoryIds`). Emit a two- or three-item list of realistic IDs aligned with the FK identifier values used elsewhere in the file.

1. **Boolean properties.** Choose the value that exercises the field's documented behavior. When the description says "When true, the catalog is hidden," use `true`. When the description says "Defaults to false," use `true` so the example shows the non-default. Default to `true` when both directions are equally informative.

1. **Monetary values** (`type: number, format: bigdecimal` for `price`, `discount`, `tierPrice`). Use a realistic decimal — `10.1` for unit prices, `33.54` for exchange rates, `1.2` for small priorities. Reuse the same value when the same monetary slot recurs across schemas.

1. **Currency codes.** Use `USD`. The example must be a real ISO 4217 code so validators that resolve the code do not fail.

1. **Dates** (`type: string, format: date`). Use `2017-07-21`. The example must be a real ISO 8601 date.

1. **Date-times** (`type: string, format: date-time`). Use `"2017-07-21T19:30:00Z"`. The example must be a real ISO 8601 timestamp in UTC; do not omit the `Z` suffix. Quote the value so rest-builder's YAML parser does not coerce it to a `java.util.Date` and truncate the time component on emission.

1. **URLs** (resolved CDN URLs, logo URLs, attachment `src`). Use `https://example.com/logo.png`, `https://cdn.example.com/attachment.png`, or another `https://example.com/...` host. Never use a placeholder host or a local file path.

1. **Friendly URLs and slugs.** Use a short kebab-case string like `hand-saw` or `default-currency` that matches the entity's natural English label.

1. **Free-form names and titles.** Use a short realistic noun phrase — `Account Name`, `Hand Saw`, `Default Vocabulary`. Avoid lorem ipsum and avoid Liferay-internal placeholder values like `Test Test Test`.

1. **Free-form descriptions, short descriptions, and meta text.** Use a short realistic sentence — `Cordless circular saw with brushless motor.` for product descriptions, `Hand Saw, 10-inch blade` for meta titles. Stay under 80 characters so the example renders well in documentation tools.

1. **Tax identifiers and other typed string codes.** Use `Abcd1234` for tax IDs, `available` for availability label keys, `Available` for the localized counterpart. Match the casing the converter emits.

1. **Symbols and single-character display tokens.** Use the actual symbol — `$` for a currency symbol, `%` for a percent indicator.

1. **Enum-typed strings** (the property declares an explicit `enum:` list). Use the enum value that represents the most common runtime case — `HALF_EVEN` for rounding mode, `business` for an account type, `available` for an availability state. Never invent a value outside the declared `enum:` list.

1. **Integer status fields** (the description names a `0=<Label>, 1=<Label>, ...` mapping). Use `0` (the value that maps to the active or approved state in the project's convention).

1. **Localized text resolved for the request locale** (`type: string` whose description states the property is the request-locale projection of a localized map). Use the English value that would appear in the corresponding map — `Available` for `label_i18n` when `label` is `available`; `Hand Saw` for a localized name projection.

When none of the patterns above matches a property, fall back to the property's `type` and `format` plus the source-of-truth value the converter produces. The example must satisfy every constraint declared in the same property block (`minimum`, `maximum`, `enum`, `pattern`, length limits implied by the backing column).

**Acceptance Check:** Every property under every schema in `components.schemas` either carries an `example:` key, uses `$ref:` exclusively, or matches one of the **Skip When** clauses below. No example value is a placeholder string (`TODO`, `xxx`, `<id>`, `{id}`); no example value violates the property's `enum:`, `minimum`, `maximum`, `pattern`, or `format`; no example value is single-quoted in the YAML emission. For each `example:` value already present at the start of the run, either no **Replacement Criterion** matches, or the value has been overwritten with the canonical one chosen from the patterns.

**Skip When:**

1. The property uses `$ref:` exclusively. Sibling keys to `$ref:` are dropped in OpenAPI 3.0 components; the referent schema carries the example surface instead.

1. The property is an array of complex objects (`items: $ref: ...` or `items: type: <SchemaName>`). The referent schema carries the example surface; an inline array example would duplicate it.

1. The property is the HATEOAS `actions` map (an `additionalProperties: additionalProperties: type: string` shape keyed by action name). The map is computed from per-user permissions at response time and existing convention omits the example.

1. The property is the expando `customFields` array (`items: type: customField`). The platform's expando mechanism populates this array dynamically and existing convention omits the example.

#### Emission Format

**Descriptions.** Write the source YAML so each `description:` value lands as a plain block scalar on the next line, indented one level deeper than the key — never as a double-quoted scalar on the same line. This is the canonical rest-builder format and the readable shape the maintainers expect.

Right (plain block scalar):

```yaml
        Catalog:
            description:
                Versioned product catalog scoped to a company.
            properties:
                accountId:
                    description:
                        Reference to the account that owns the catalog (FK identifier). The linked account must be
                        a supplier with approved status; other account types are rejected.
                    example: 30130
                    format: int64
                    minimum: 0
                    type: integer
```

Wrong (double-quoted single-line, with `\`-continuation):

```yaml
        Catalog:
            description: "Versioned product catalog scoped to a company."
            properties:
                accountId:
                    description: "Reference to the account that owns the catalog (FK identifier). The linked account\
                        \ must be a supplier with approved status; other account types are rejected."
```

A description that is pure prose (no colons, backticks, quotes, leading dashes, or characters YAML would treat as control tokens) emits naturally as a plain block scalar. If a tool produces double-quoted output instead, the Voice And Style rules above are usually being violated — strip the offending punctuation or rephrase so the value can sit unquoted. Backticks around a literal field name are fine when needed; the YAML emitter still accepts the value as plain.

**Examples.** Scalar example values land on the same line as the `example:` key — this is rest-builder's emission default for scalars and matches the existing in-file style. Map and list example values land as block scalars indented one level deeper than the `example:` key:

```yaml
                name:
                    additionalProperties:
                        type: string
                    description:
                        Localized text. Map keys are locale codes; values are the translated names.
                    example:
                        en_US: Hand Saw
                        hr_HR: Product Name HR
                        hu_HU: Product Name HU
                    type: object
```

The `example:` key sits alphabetically between `description:` and `format:` (or between `description:` and `type:` when no `format:` exists). Match the surrounding indentation exactly. Never single-quote the value; double-quote only when the value contains a leading colon, an ISO 8601 `date-time` (to prevent YAML coercion to a `java.util.Date`), or another YAML control token.

#### Rule SF — Source Formatter Compatibility (Blocking)

**Why:** Liferay's source-formatter is invoked by every CI build and by `ant format-source-local-changes` locally. Two YAML patterns make the file unformattable: every commit that introduces or leaves either pattern in place takes minutes to hang format-source, exhausts the JVM heap, and fails CI. This rule is **blocking** — the pass cannot proceed to Step 4 until both sweeps return zero hits on the entire file (not just the lines this pass edited).

**How To Apply:** Run the two sweeps below over the entire `rest-openapi.yaml` after every edit batch and again at the end of Step 3. Each sweep both verifies and, when violations exist, rewrites the offending tokens to the safe form. The sweeps must operate on the whole file because earlier work in the same module may have left hazards behind that this pass is now obligated to fix; format-source will block the commit regardless of which pass introduced the token.

Both sweeps require **stateful, quoted-scalar-aware parsing**. A naive regex pass that does not track when the cursor is inside an open multi-line scalar will misidentify literal `'...'` characters inside an open double-quoted block scalar as YAML single-quoted scalars, and will corrupt the YAML when it tries to "convert" them. Use a Python state-machine parser; do not attempt these conversions with `sed` or single-pass grep substitutions.

1. **Sweep SF1 — Single-quoted scalars.** Locate every YAML single-quoted scalar in the file. Three patterns occur in rest-builder output:

	- **Inline keyed scalar** — `<indent>key: '<text>'` on a single line. Convert to `<indent>key: "<text>"`.

	- **Quoted response-code keys** — `<indent>'<digits>':` (for example, `'403':`, `'422':`). The keys must remain quoted because YAML otherwise parses them as integers; convert to `"<digits>":`.

	- **Multi-line single-quoted block scalars** — a continuation line starting with `<indent>'<text...>` whose value spans multiple lines and closes on a later line at the same indent with `<...text>'`. These are emitted by `mcp-ready-openapi` whenever a description contains a `: ` (colon-space) sequence that YAML would otherwise treat as a key/value separator. Convert the whole span to a double-quoted block scalar: replace the opening `'` with `"`, replace the closing `'` with `"`, unescape every internal `''` to `'`, and escape every internal `"` to `\"`.

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

### Step 4: Run buildREST And Verify

1. From the `modules` directory (the repository root has no `settings.gradle`), run:

	```bash
	cd modules
	../gradlew \
		:apps:<gradle-path>:<module>-impl:buildREST \
		--no-daemon
	```

	`<gradle-path>` is the colon-separated path from `modules/apps` to the parent of the module.

1. Inspect the resulting Git diff for the `<module>-api` DTOs and the `<module>-impl` graphql `Query.java` and `Mutation.java`. The expected change is annotation-only: existing `@Schema`, `@GraphQLField`, and `@GraphQLName` annotations gain or update a `description = ...` or `example = ...` parameter. **Any change to a method signature, field name, type, class name, or annotation other than the addition or update of a `description = ...` or `example = ...` parameter is a regression and must be reverted.**

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

1. Confirm the regenerated mirror at `<module>-impl/build/resources/main/META-INF/liferay/rest/rest-openapi.yaml` is byte-identical to the source (`diff` exits with `0`). When the source and mirror diverge, run `processResources --rerun-tasks` to refresh the mirror, then re-verify.

1. Remove any `derived-config.txt` build artifacts left in the module roots; they are not tracked.

### Step 5: Commit

Split the work into two commits so the YAML edits and the generated-Java churn stay reviewable independently. Use the `commit` skill, which prefixes the message with the current branch's LPD ticket (or a placeholder `LPD-XXXXX` when no ticket exists).

1. **Commit 1 — Schema descriptions and examples:** the `rest-openapi.yaml` source only. Every D1 and D2 description rewrite, every E1 example added or replaced, and every SF sweep that this pass had to apply.

1. **Commit 2 — Generated artifacts:** every file rest-builder regenerated from the new YAML. This is the api and impl Java (DTOs, `Base*ResourceImpl.java`, `Mutation.java`, `Query.java`, `OpenAPIResourceImpl.java`). The mirror at `<module>-impl/build/resources/main/...` is gitignored and is not part of the commit. The diff is strictly added or updated `description = ...` and `example = ...` annotation parameters.

Never bypass signing (`--no-gpg-sign`, `-c commit.gpgsign=false`) or hooks (`--no-verify`). When signing fails because the SSH agent is unreachable, stop and surface the failure to the user.

## Known rest-builder Behavior

These behaviors apply equally to this pass; document them so the output is predictable.

1. **rest-builder normalizes the source `rest-openapi.yaml` on every `buildREST` run.** Keys are alphabetized inside each block (so `example:` always lands between `description:` and `format:` regardless of where the hand edit placed it), and the value of each `description:` is wrapped onto multiple lines at a fixed column with a tab-indented continuation. The scalar style of each value is preserved (a plain block scalar stays plain; a double-quoted string stays double-quoted) — so write each `description:` as a plain block scalar from the start and the canonical output will be plain prose. Do not rely on hand-formatted whitespace surviving the round-trip beyond what `_formatDescription` produces.

1. **rest-builder propagates descriptions and examples into the api DTOs.** The `@Schema(description = ...)` and `@Schema(example = ...)` parameters on each DTO class and field are the visible manifestation of D1, D2, and E1 in the generated Java. A description or example change in the YAML that does not show up on the matching `@Schema` parameter after `buildREST` means the YAML edit landed on the wrong key.

1. **rest-builder emits map and list examples as toString-form Java strings on the annotation.** A YAML map `{en_US: Hand Saw, hr_HR: ...}` becomes `example = "{en_US=Hand Saw, hr_HR=..., hu_HU=...}"` on the annotation. This is the same shape the existing in-file examples produce; no extra escaping is required at the YAML level.

1. **Sibling keys to `$ref:` are dropped** in OpenAPI 3.0 components. Do not add a sibling `description:` or `example:` to a property that uses `$ref:` to point at another schema; describe the referent schema instead.

1. **A property defined purely as `{ $ref: "#/components/schemas/X" }` cannot carry its own description or example.** When a property's role in the parent schema needs explanation, document it in the parent schema's description text rather than as a property `description:`. When the parent schema needs to illustrate the referent's shape, place the example on the referent schema's matching property instead.

1. **rest-builder does NOT propagate response codes or response descriptions into Java annotations.** This pass touches neither; the constraint is listed only to clarify that any divergence between the YAML responses and the Java surface is expected and not caused by this skill.

## Expected Output

When the pass completes, report:

1. The module name and the resolved YAML path.

1. Per-rule status: `applied` or `n/a`, plus the count of schemas (D1) and properties (D2) rewritten, and the count of examples added and replaced under E1 (broken down by pattern: FK identifiers, external reference codes, localized maps, arrays, booleans, monetary, dates, URLs, names, enums, status). For E1 replacements, also list the matching **Replacement Criterion** counts so the maintainer can audit the rationale.

1. The count of SF sweep fixes applied (single-quoted scalars converted; brace placeholders rewritten).

1. The list of properties skipped, grouped by skip clause.

1. The list of backend api and service-impl modules read in Step 2, so the reviewer can confirm research depth.

1. The Step 4 verification result: `parity gate passed` or `parity gate failed (details)`, plus the format-source-local-changes runtime as a final confirmation that the SF sweeps held.

1. The two commit IDs created in Step 5, in order.

1. Any open items the maintainer needs to confirm (for example, a property whose backend source could not be located authoritatively or an enum mapping whose canonical example value is ambiguous).