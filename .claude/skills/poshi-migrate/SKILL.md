---

argument-hint: "<component-name-or-testcase-path>"
description: Migrate a Liferay Poshi .testcase file to its modern test layer (Playwright, Jest, JUnit unit, Java integration). Routes each test to the right layer, consolidates compatible tests at write-time, and follows existing Playwright patterns from a configurable reference folder. Use when the user asks to migrate, port, or convert a Poshi .testcase to Playwright, Jest, or JUnit.
name: poshi-migrate

---

# Migrate Poshi Tests

A playbook for migrating a Poshi `.testcase` file to the appropriate modern test layer. Each test is routed to **Playwright**, **Jest**, **JUnit unit**, or **Java integration** based on what it actually validates. Compatible Playwright tests sharing setup may be consolidated into a single test at write-time.

This skill assumes the Poshi suite has already been triaged and shrunk if needed (`/poshi-shrink`). It does not run a Poshi-side consolidation pass — consolidation happens only when writing the modern test, and only when the merged form is clearly cleaner than separate tests.

## Preconditions

The working tree must be clean. Abort and ask the user to commit or stash first when dirty.

## Inputs

### Argument

`${ARGUMENTS}` is either:

1. A `@component-name` value. Scan the Poshi tests root for `.testcase` files whose `@component-name` matches, list them with their test counts, and ask the user to pick one.

1. An absolute or repo-relative path to a single `.testcase` file. Use it directly.

When `${ARGUMENTS}` is empty, resolve both the `@component-name` and the `.testcase` file interactively in two steps.

#### Step 1: Resolve @component-name

1. Read the root `test.properties` file at the repository root. Parse the `component.names=...` property, which is multi-line and uses `\` continuations with comma-separated values. Strip whitespace and trailing backslashes; the result is a flat list of component identifiers (`portal-acceptance`, `portal-analytics-cloud`, `portal-bpm`, …).

1. Read `config.json` (when it exists) and check for `defaultComponentName`.

	- When the value is set and still appears in the parsed list, ask the user with `AskUserQuestion` whether to keep `${defaultComponentName}` or pick another. When the user keeps the default, skip to Step 2.

	- When the value is missing or no longer appears in the list, skip the question.

1. Present the full parsed list to the user as plain text, one component per line, sorted alphabetically, prefixed by an index (`1. portal-acceptance`, `2. portal-analytics-cloud`, …). Append a final option such as `0. Type a custom component-name (not in the list)`. Ask the user to either pick an index or type a custom value. Validate the entered value against the regex `^[a-z0-9-]+$`; do not reject when the value is not in `test.properties` because product teams sometimes add component-names that are still pending registration.

1. Persist the choice to `config.json` under `defaultComponentName` so the next invocation can suggest it as the default. Create `config.json` from `config.example.json` when it does not exist yet.

#### Step 2: Resolve the .testcase File

1. Search `${poshiTestsRoot}` recursively for `.testcase` files containing the line `@component-name = "${chosenComponent}"`. Capture, for each match, the repo-relative path and the test count (number of `test <Name> {` blocks).

1. Sort the matches alphabetically by repo-relative path.

1. Present the first 20 matches to the user as plain text with `<index>. <relative-path> (<N> tests)`. Append a final option such as `0. Type a repo-relative path (not in the list)`. Ask the user to either pick an index or type a repo-relative path manually (for files nested under a different root or not yet committed). Validate that the entered path resolves to an existing `.testcase` file before continuing.

Use the chosen file path as the input for Phase 0 onwards.

### Configuration

`config.json` lives at `.claude/skills/poshi-migrate/config.json` but is intentionally not committed to the repository — each developer maintains their own. A `config.example.json` sits next to it with the suggested defaults; copy it and personalize when the defaults do not fit.

Resolve configuration in this order:

1. Read `config.json` when it exists.

1. Otherwise, fall back to these defaults:

	- **patternsReference**: `modules/test/playwright/tests/layout-content-page-editor-web` — the Playwright folder used as the style reference. Read at least one spec from here before writing the first migrated spec.

	- **playwrightTestsRoot**: `modules/test/playwright/tests` — the root for Playwright specs.

	- **poshiTestsRoot**: `portal-web/test/functional/com/liferay/portalweb/tests/enduser` — the root for Poshi `.testcase` files.

	- **componentPlaywrightModules**: `{}` (empty). Phase 1 will ask the user which Playwright modules to scan when the mapping for the source `@component-name` is missing.

	- **defaultComponentName**: unset. The argument resolver will prompt the user with the full list from `test.properties` and remember the choice from then on.

The keys are:

- **componentPlaywrightModules**: a mapping from `@component-name` to the list of Playwright modules under `playwrightTestsRoot` that own its existing specs. Phase 1 scans these modules to detect tests that already cover, or partially cover, the Poshi tests being migrated.

- **defaultComponentName**: the last `@component-name` the user chose interactively. Suggested as the default on subsequent runs when `${ARGUMENTS}` is empty.

- **patternsReference**: the Playwright folder used as the style reference.

- **playwrightTestsRoot**: the root for Playwright specs.

- **poshiTestsRoot**: the root for Poshi `.testcase` files.

After resolving the values, show them to the user via `AskUserQuestion` and offer to keep them or override any value for this run. Persist overrides only when the user explicitly asks (write `config.json`, never modify `config.example.json`).

## Workflow

The skill runs in five phases: feasibility, inventory, plan, implement, validate. Each phase is gated on user approval.

### Phase 0: Feasibility

Before classifying anything, screen each `test` in the file for blockers that would make a migration to Playwright unviable today. Record the result for use in Phase 1.

For each `test`, scan the body and every macro it transitively calls. Flag the test when it depends on any of the following:

- **Synthetic events on a backend ingestion pipeline.** The test produces signals through real UI interactions (page visits, asset views, submissions) and waits for a flush job to surface them in the destination system. Without an API to seed those events directly, the migration cannot run in the Playwright budget.

- **Multi-user sign-out / sign-in flows.** The test asserts behavior under a different identity using a chain of `logoutPG` / `loginPG` calls, often relying on seed users that are not isolated per run.

- **Fresh tenants, workspaces, or instances created during the test.** The test provisions a new top-level container (workspace, virtual instance, isolated portal) as part of its setup, which the existing fixtures do not provision.

- **Specialized seed sites or catalogs.** The test depends on an accelerator-provisioned site, a commerce catalog, or a template configuration that is not exposed through `apiHelpers`.

- **Wizard UI shape as the assertion.** The test exists to verify the wizard UI itself (button labels, step order, banner copy). Replacing the wizard with an API setup defeats the test, so the migration must still drive the wizard — confirm that the wizard interactions are stable.

Output of this phase, per test:

| Classification | Meaning | Phase 1 follow-up |
| --- | --- | --- |
| **Migrate** | No blockers, or the only blockers are addressable by an API helper that already exists or that the migration will add. | Route to a layer in Phase 1. |
| **Skip** | Blocked on a feature the modern test layer cannot reproduce today and that needs an API endpoint that does not exist yet. | Document the blocker and leave the test in the `.testcase`. Surface it in the plan so the team can prioritize the endpoint. |
| **Drop** | Obsolete: already covered by an existing spec, or the product contract changed and the assertion no longer applies. | Remove the test from the `.testcase` in Phase 3 with a commit that names the covering spec or the contract change. |

When a test is **Skip**, propose what the missing endpoint should look like (HTTP verb, path, payload, what UI flow it would replace) so the team can decide whether to build it. Do not improvise UI workarounds.

### Phase 1: Inventory

1. Read the `.testcase` file. Capture the `setUp`, `tearDown`, and every `test <Name>` block with its `@description`, `@priority`, and the macros or helper calls it invokes.

1. Scan the Playwright modules listed in `componentPlaywrightModules` for the source `@component-name`. For every Poshi `test` not already marked **Skip** in Phase 0, classify the existing coverage as one of:

	- **Already covered** — an existing spec exercises the same behavior end to end. Record the spec path. The test becomes **Drop** in the plan; it does not become a migration.

	- **Partially covered** — an existing spec exercises part of the behavior or shares the same setup. Record the spec path so Phase 2 can plan an extension of that spec instead of a new file.

	- **Not covered** — no existing spec touches the behavior. Continue to routing.

	Compare each Poshi test against candidate specs across these signals (the more that align, the higher the confidence):

	- **Description and comments** — does the Poshi `@description` or any header comment match the spec's `test(...)` title or section comments?

	- **Functionality** — do both validate the same feature path (same panel, same flow, same final state)?

	- **Selectors** — do the Poshi macro arguments (IDs, classes, and labels passed into `Click`, `AssertTextEquals`, and friends) overlap with the spec's `getByRole`, `getByLabel`, and `getByText` arguments? Resolve the macro to its `.macro` definition when the selector is not inline.

	- **Structure** — same setup, same step order, same assertions in roughly the same place?

	This is the most fragile part of the workflow; expect to iterate on the heuristic as false positives and false negatives surface. When the signals disagree, default to **Partially covered** and let the user resolve the call in Phase 2.

	When `componentPlaywrightModules` does not list the source `@component-name`, ask the user which Playwright modules to scan before continuing. Do not skip this step.

1. For every test whose `@description` references an LPS or LPD ticket, recover the original code change and use its diff as the primary signal for the layer decision. From the repo root:

	```bash
	git log \
		--all \
		--format="%H %s" \
		--grep="^${TICKET}"
	```

	Run `git show <sha>` on each match to read the diff, and let the changed files drive the layer choice in the next step:

	- Java `*Util`, formatter, parser, or pure-logic class with no service or runtime touch → JUnit unit.

	- `.js`, `.jsx`, `.ts`, or `.tsx` module that does not import portal-runtime globals → Jest.

	- `*LocalServiceImpl`, persistence layer, listener, or OSGi `@Component` → Java integration.

	- JSP, taglib output, multi-module UI wiring, or anything that only manifests in the rendered page → Playwright.

	Record the matching commit shas and the touched files in the per-test notes so Phase 2 can cite them in the plan. When `git log` returns no match (test predates the convention or the ticket never landed in this repo) record `ticket not in tree` and fall back to the heuristic table in the next step.

1. For every `test` not classified as **Drop** or **Skip**, decide the routing in this order. Pick the first match. When the previous step recovered a ticket diff, treat its signal as the layer choice unless the diff and the test's actual assertions clearly disagree — in that case, record both signals and mark the test `unsure`.

	| Layer | Pick When |
	| --- | --- |
	| **JUnit unit** | The test validates pure Java logic that does not need the portal runtime, services, or persistence. Common signal: assertions over plain values produced by a utility class, or behavior already reproducible at the package level. |
	| **Jest** | The test validates frontend behavior that lives in a JavaScript or React module and can be reproduced without the portal — component rendering, state transitions, formatting, validation logic. |
	| **Java integration** | The test exercises services, persistence, OSGi components, listeners, or portal-runtime behavior. Anything that needs a deployed bundle but does not need a real browser. |
	| **Playwright** | Anything left: complete UI flows, multi-page navigation, browser-only behavior, JavaScript-driven interactions that a unit or integration test cannot cover. |

	If you cannot place a test confidently, mark it `unsure` and flag it for the user in the plan. Do not invent a destination.

1. For each test routed to Playwright, locate the destination spec folder under `playwrightTestsRoot`. The destination is the folder whose owning module renders the feature under test. When in doubt, search the Playwright tests root for an existing spec that imports or interacts with the same UI surface.

### Phase 2: Plan

Build the plan with `EnterPlanMode`. Format:

```markdown
## Source

`<.testcase path>` — `@component-name = <value>`, `testray.main.component.name = <value>`, <N> tests.

## Feasibility

| Test | LPS/LPD | Decision | Blocker / Reason |
| --- | --- | --- | --- |
| `<TestName>` | `<TICKET>` | Migrate / Skip / Drop | <missing API endpoint / obsolete contract / etc> |

## Existing Coverage

| Test | LPS/LPD | Status | Covered By | Action |
| --- | --- | --- | --- | --- |
| `<TestName>` | `<TICKET>` | Already covered / Partially covered / Not covered | `<repo-relative spec path or —>` | Drop / Extend `<spec>` / Migrate |

When every test is **Not covered**, write `None — no overlap with existing Playwright specs`.

## Per-Test Routing

Only includes tests classified as **Migrate** or **Partially covered** in earlier phases.

| Test | LPS/LPD | Layer | Destination | Notes |
| --- | --- | --- | --- | --- |
| `<TestName>` | `<TICKET>` | Playwright / Jest / JUnit unit / Java integration | `<repo-relative path>` | <Consolidates with X / extends `<spec>` / new fixture needed / etc> |

## Consolidations (Playwright)

> **`<final-spec-name>.spec.ts`** (consolidates `<TestA>` + `<TestB>`):
>
> - Shared setup: <site, page, user>.
> - Sequence: <step list>, ending with assertions for both LPS tickets.
> - Tags: `@<TICKET-A>`, `@<TICKET-B>`.

When no consolidation applies, write `None — one spec per test`.

## New Helpers, Utils, or Fixtures

List any new file under `helpers/`, `utils/`, or `fixtures/` you plan to add or extend, and the reason. When existing files cover the need, write `None — reuse existing`.

## Proposed API Endpoints

List every backend endpoint the migration would need but that does not exist yet. For each one, capture the HTTP verb, the path, the payload, and the UI flow it replaces. Mark dependent tests as **Skip** until the endpoint lands.

## Skipped Tests

| Test | LPS/LPD | Blocker | Proposed Unblocker |
| --- | --- | --- | --- |
| `<TestName>` | `<TICKET>` | <missing API / multi-tenant setup / etc> | <propose endpoint or fixture> |

## Cleanup

- Remove the **Drop** `test` blocks from `<.testcase path>` in their own commits, before any migration commits.
- Remove the migrated `test` blocks from `<.testcase path>`.
- Delete the `.testcase` file when it ends up empty.

## Commits

In order:

1. One commit per **Drop** test (or per group sharing a covering spec), removing the test from the `.testcase` file. The commit message names the covering spec or the contract change that retired the test.

1. New helper, util, or fixture commits, when any.

1. One commit per migrated test or consolidated group.
```

The plan must be exhaustive: every `test` from Phase 1 appears in either the **Feasibility**, **Existing Coverage**, or **Per-Test Routing** table. Tests marked `unsure` go on their own row and the user must confirm a layer before continuing.

### Phase 3: Implement

After `ExitPlanMode` returns the user's approval, execute the plan in this order:

1. **Drop cleanup commits** first. For every test classified as **Drop** in earlier phases, remove the `test` block from the source `.testcase` file (delete the file when it ends up empty), run `/format-source` on every changed file, and commit. The commit message must name the spec that already covers the test or the contract change that retired it. One commit per drop, or per group of drops sharing a single cause.

1. **New helper, util, or fixture commits**, one per artifact. Verify the artifact does not already exist by scanning `<playwrightTestsRoot>/../{helpers,fixtures,utils}` before creating it.

1. **Migration commits**, one per row in the routing table or per consolidation group. Each commit:

	1. Writes the new test file at the planned destination.

	1. Removes the migrated `test` blocks from the `.testcase` file. When the file ends up empty after the removals, delete the file in the same commit.

	1. Runs `/format-source` on every changed file before staging.

	1. Uses `/commit` to write the commit message.

When implementing each layer, follow the rules below.

#### Playwright

Follow `.claude/rules/playwright.md` for the general conventions (layout, spec placement, anatomy, page classes, locators, setup, cleanup, flake-proofing utilities, feature flags). Do not duplicate or restate those rules here — read the file and apply it.

Read at least one spec from the configured `patternsReference` folder before writing the first migrated spec to internalize the local idiom.

The subsections below cover decisions that are specific to migrating from Poshi.

##### API-First Setup Substitution

When the Poshi `setUp` drives a UI flow (wizard, OAuth, sync between two services, signing in as another user, populating a configuration page), the migration must replace it with an API call. Climb the decision tree top-down, stopping at the first level that applies:

1. **Existing Playwright helper.** Look under `<playwrightTestsRoot>/../{helpers,fixtures,utils}` for a helper that already replaces the flow (look for the verb in the macro name and synonyms: `connect`, `sync`, `assign`, `add`, `enable`). Use it. Compose with `mergeTests(...)` when a fixture exists.

1. **No helper, but a backend endpoint exists.** When the product exposes a REST resource, a JSON web service, a headless endpoint, or a GraphQL mutation that performs the same effect as the UI flow, write a new helper in `<playwrightTestsRoot>/../helpers/<X>ApiHelper.ts` (or extend the existing one), register it on `ApiHelpers`, and consume it from the spec. Ship the helper in its own commit before the migration commit that uses it. Source the URL, payload shape, headers, and any envelope encoding from the backend code that owns the endpoint, not from a guess — wrong payloads typically surface as 500 errors with non-obvious messages.

1. **No endpoint either.** Stop. Propose to the team adding the headless / JSON-web-services / REST endpoint that would unblock the test. Document the proposed contract (HTTP verb, path, payload, response shape) and what UI flow it replaces, then mark the test as **Skip** in the plan and return to the caller. Do not improvise UI workarounds — clicking through the wizard from the spec piles up flake and rots the moment the UI changes.

Validate every new helper end-to-end against the running backend (`yarn test` on a one-test spike spec) before committing it. The connection envelope, payload shape, and headers must match what the backend expects.

##### State Reset

Modern specs cannot assume a clean starting state. Whatever the Poshi `tearDown` was clearing (preferences, configurations, per-tenant settings, sessions, lingering data sources from previous runs), the Playwright spec must reset before the assertion, not after — a previous run might have left residue.

Two patterns work:

1. **API reset in `test.beforeEach`.** Call the same REST / GraphQL mutation the UI uses to wipe the setting. Source the mutation name and shape from the frontend code that drives the UI.

1. **Filter by dynamic state instead of hardcoded identifiers.** When the system creates entities with auto-incrementing names or environment-specific suffixes, do not assert on the name. Find the entity by its current state (status badge, "active" flag, freshly created flag) and read the name back from there.

##### Cascading Fixtures

When a migration needs site + user + connection between two services, compose existing isolated fixtures with `mergeTests(...)` instead of duplicating the setup. A common shape:

```typescript
import {mergeTests} from '@playwright/test';
import {isolatedFooTest} from './isolatedFooTest';
import {isolatedBarTest} from './isolatedBarTest';

const test = mergeTests(isolatedFooTest, isolatedBarTest);

const isolatedSyncedFooBarTest = test.extend<{
	syncedFooBar: void;
}>({
	syncedFooBar: [
		async ({apiHelpers, bar, foo}, use) => {
			await connectFooAndBar({apiHelpers, bar, foo});

			await use();

			await disconnectFooAndBar({apiHelpers});
		},
		{auto: true},
	],
});
```

When the underlying connection is company-wide or otherwise non-isolated, force `workers: 1` and document the constraint in the fixture's doc comment.

##### Setup Mappings

Map Poshi setup actions to API helpers:

- **Content page** (Poshi `JSONLayout.addLayout` or UI page creation) → `apiHelpers.headlessDelivery.createSitePage`. Pass a `pageDefinition` from the patternsReference utilities when fragments are needed.

- **Mid-test login** (Poshi `User.logoutPG` plus `User.loginPG`) → `performLogout` followed by `performLogin` from `utils/performLogin`. Use a fresh user when the test mutates a per-user preference that would leak across runs.

- **Site** (Poshi `JSONGroup.addGroup` or `Site.add`) → `apiHelpers.headlessAdminSite.postSite`. Prefer the `isolatedSiteTest` fixture, which provides a `site` with auto-cleanup.

- **User** (Poshi `JSONUser.addUser`) → `apiHelpers.headlessAdminUser.postUserAccount`, then `assignUserToSite` with the role from `getRoleByName('Site Administrator')`. Register the user in `userData` so `performLogin` can sign in as that user.

##### New Helpers, Utils, Fixtures

When the migration requires a new artifact under `<playwrightTestsRoot>/../{helpers,fixtures,utils}`, scan the existing files for one that already covers the need before adding it. Extend an existing file rather than forking a new one. When a new artifact is genuinely needed, place it in the same folder structure and ship it in its own commit before the migration commit that uses it.

##### Tagging

Every migrated test carries `{tag: '@<TICKET>'}` where `<TICKET>` is the LPS or LPD identifier recovered in Phase 1. Consolidations carry every ticket they cover: `tag: ['@<TICKET-A>', '@<TICKET-B>']`.

When the Poshi test has no ticket in its `@description` block and no LPS or LPD ticket surfaces from `git log --grep`, the migrated test goes without a tag. Do not invent a placeholder.

#### Jest

1. Place the new test under `<module-root>/test`, mirroring the path of the source file under test.

1. Run with `cd <module-root> && yarn test <relative-path>`.

1. Mock only what the JavaScript module under test cannot exercise directly. Avoid mocking the portal runtime — if you need it, the test belongs in Playwright or integration.

1. Frontend conventions: `*.test.js`, `*.test.ts`, or `*.test.tsx` matching the module's existing pattern.

#### JUnit Unit

1. Place the new test under `<module-root>/src/test/java`, in the same package as the class under test.

1. Run with `cd <module-root> && <gradlew> test --tests <TestClassName>`.

1. The test must not require the portal runtime, services, or persistence.

#### Java Integration

1. Place the new test under `<module-root>/src/testIntegration/java`, in the same package as the class under test.

1. Run with `cd <module-root> && <gradlew> testIntegration --tests <TestClassName>`.

1. Follow the team conventions for Liferay integration tests: auto-cleanup via test rules and annotations rather than `@After` or manual deletion, `@FeatureFlags` per method when needed, trigger artifacts through real listeners.

1. Reuse `*TestUtil` classes for fixtures.

1. Use `TransformUtil.transformToArray` plus `ArrayUtil.contains` for "is `X` in list" assertions instead of manual loops.

### Phase 4: Validate

Tests against the runtime are not done until they pass against a live backend.

For Playwright, run from the repo root:

```bash
cd modules/test/playwright && yarn test <relative-spec-path>
```

For JUnit and Jest, use the commands listed in the per-layer sections above.

When a spec fails, follow the protocol below before claiming the migration done. Never `xfail`, comment out, or weaken an assertion to land the commit.

#### When a Spec Fails

Before retrying, gather the artifacts and decide:

1. Open the screenshot at `test-results/<spec>/test-failed-N.png` and the `error-context.md` next to it. Compare them against what the spec expected.

1. Identify the failure mode:

	- **UI changed.** The assertion no longer matches the product contract. Drop the test (commit explaining the new behavior) or rewrite the assertion if the new behavior is still valuable.

	- **Framework race.** The locator resolves correctly but the page is mid-render. Replace fixed waits with auto-retrying expectations (`expect(...).toHaveText`, `expect(...).toBeEnabled`) or use the existing wait helpers from `<playwrightTestsRoot>/../utils`.

	- **Stale identifier.** The test assumes an environment-specific value (auto-incrementing name, seed user). Read the value from the current page state instead.

	- **Real contract change.** The product behavior differs from what the Poshi test specified. Bring it to the feature owner before changing the assertion.

	- **Missing API.** The setup landed but a downstream assertion fails because a state the Poshi test took for granted (synced data, populated metrics) is not seeded by the API path. Re-evaluate the test as **Skip** in the plan and surface the missing endpoint.

#### Flake Check (Playwright Only)

After the first green run, repeat each migrated Playwright spec ten times to surface flakiness from timing, viewport, or network races:

```bash
cd modules/test/playwright && yarn test --repeat-each=10 <relative-spec-path>
```

All ten runs must pass. When any run fails, fix the underlying race before declaring the migration done. Do not apply the flake check to Jest, JUnit unit, or Java integration tests — they are deterministic enough that the extra runs only burn time.

## Output

After the last commit, report:

- The source `.testcase` (deleted or surviving with a tail of remaining tests).

- The list of new files added, by layer, with their repo-relative paths and the LPS or LPD ticket they cover.

- The list of new helpers, utils, or fixtures introduced, with one line each describing what they expose.

- The list of tests classified as **Skip**, each with the blocker and the proposed endpoint or fixture that would unblock it.

- Total commits made, in order.

- Any test marked `unsure` that the user routed manually, plus the reasoning recorded in the plan.