# AGENTS.md

## Purpose

`@liferay/node-scripts` is the shared frontend toolchain for `liferay-portal/modules`. It is the operational layer behind build, format, lint, test, preflight, TypeScript checks, and generated config workflows across the monorepo.

This package is not just project-local tooling. Changes here can affect hundreds of modules at once.

## Where It Is Used In `liferay-portal`

Primary integration points:

-   `modules/package.json`
    -   Root scripts call `node-scripts check:ci`, `node-scripts format:ci`, and `node-scripts test`.
-   `modules/**/package.json`
    -   Most JS modules use `node-scripts` commands directly.
-   `modules/node-scripts.config.js`
    -   Global generated import/export map is consumed by build and lint flows.

## Command Router And Contracts

-   Command registration and dispatch: `bin.js`
-   Monorepo path contracts: `util/locations.mjs`

Important:

-   Many commands are cwd-sensitive.
-   Some commands are valid only from `modules/` (for example `check:ci`, `generate:tsconfig`).
-   `locations.mjs` assumes this package lives at `modules/frontend-sdk/node-scripts` under portal.

## High-Level Architecture

-   Build pipeline: `bundle/index.mjs`
-   Format/lint pipeline: `format/index.mjs`, `util/format/doFormat.mjs`
-   CI checks: `check/ci.mjs`, `check/preflight.mjs`, `format/ci.mjs`
-   Preflight policies: `util/preflight/doPreflight.mjs`
-   Test orchestration: `test/index.mjs`, `util/jest/runJest.mjs`
-   TypeScript checks and generated tsconfig: `util/tsc/*`, `util/tsconfig/*`, `generate/tsconfig.mjs`
-   Global config generation: `generate/globalConfig.mjs`, `util/createGlobalConfig.mjs`

## Build Internals Developers Must Know

Main build entry:

-   `bundle/index.mjs`

Core phases:

1. Load global/project configuration values by reading project config files (especially each module's `node-scripts.config.js`) through helpers in `util/configuration/*`.
2. Clean selected output directories to avoid stale artifacts.
3. Process CSS and Sass (`util/css/*`, `util/sass/*`).
4. Run esbuild bundles for main/export entry points (`util/esbuild/*`).
5. Generate bridges and metadata (`util/amd/*`).

Linking and import behavior is centralized in

-   `util/esbuild/plugins/getLinkerPlugin.mjs`

This is where project imports, global imports, submodule imports, CSS loader stubs, and runtime URL rewrites are enforced.

## Config Model

Project-level input:

-   `node-scripts.config.js` per module
-   Resolved through helper readers in `util/configuration/*` and `util/projectScopeRequire.mjs`

Global generated config:

-   `modules/node-scripts.config.js`
-   Rebuild with: `node-scripts generate:global-config`
-   Freshness check: `util/preflight/checkGlobalNodeScriptsConfig.mjs`

If global configuration is stale, preflight fails and build/lint assumptions may drift.

## Generated Artifacts (Do Not Hand-Maintain)

-   Build output: `build/node/packageRunBuild/resources/**`
-   Work artifacts: `build/node-scripts/**`
-   Generated global config: `modules/node-scripts.config.js`
-   Generated tsconfig files under source/test trees
-   Temporary Jest config during test runs: `TEMP_jest.config.json`

If behavior is generator-owned, update generator code, not generated files.

## Preflight And Consistency Checks

Preflight runner:

-   `util/preflight/doPreflight.mjs`

Includes checks for:

-   forbidden config names
-   package.json format/policy
-   `yarn.lock` policy
-   node-scripts hash consistency (`util/preflight/checkNodeScriptsHash.mjs`)
-   global node-scripts config freshness
-   API submodule constraints

If hash fails, run formatting in this package:

-   `yarn format` from `modules/frontend-sdk/node-scripts`

`format/self` updates `package.json` `com.liferay.sha256` automatically when needed.

## Test Pipeline Behavior

Workspace test orchestration:

-   `test/index.mjs`

Notable behavior:

-   Detects testable projects by checking if script contains `node-scripts test`.
-   Preserves env flags embedded in script value (for example `USE_REACT_16=true`).
-   Runs one project directly, multiple projects in parallel or serial (`--sync`).

Jest execution details:

-   Base config + module mapper + user config are merged in `util/jest/runJest.mjs`.
-   Temporary config file `TEMP_jest.config.json` is created and removed per run.

## How To Add A New Feature

When adding a command

1. Register command in `bin.js` (description, params, script path).
2. Add a dedicated `.mjs` entry with a default `main()` export.
3. Parse flags with `util/getNamedArguments.mjs`.
4. Enforce cwd contracts explicitly when needed.

When adding build behavior

1. Keep orchestration in `bundle/index.mjs` small.
2. Put new logic into focused modules (`bundle/*`, `util/*`); treat `util/configuration/*` as config-reader/derivation helpers, not as the canonical source of configuration.
3. If changing import/link semantics, modify `getLinkerPlugin.mjs` carefully.
4. Validate generated bridge/manifest/package outputs.

When adding policy checks

1. Add check in `util/preflight/`.
2. Register it in `doPreflight.mjs`.
3. Keep runtime fast (preflight must remain lightweight).

When adding tsconfig generation behavior:

1. Update `util/tsconfig/visitProjectTsconfig.mjs`.
2. Preserve `@generated` hash semantics.
3. Verify stale detection and regeneration behavior.

## Common Failure Modes

-   `BAD - node-scripts.config.js is out of date`
    -   Run `node-scripts generate:global-config` from `modules/`.
-   `BAD - node-scripts sha256 field is not up-to-date`
    -   Run `yarn format` in `modules/frontend-sdk/node-scripts`.
-   Cross-submodule import build errors
    -   Import through allowed submodule entrypoints only.
-   `generate:tsconfig` / CI stale tsconfig failures
    -   Regenerate from `modules/` with `node-scripts generate:tsconfig`.
-   `--current-branch` and `--local-changes` not working outside `modules/`
    -   Run command from `modules/` or use project-local mode.

## Safety And Compatibility Rules

-   Preserve deterministic output order for generated JSON/config files.
-   Avoid ad-hoc path logic; use `util/locations.mjs` and existing helpers.
-   Keep preflight checks fast and side-effect free.
-   Treat this package as monorepo infrastructure: prefer backward-compatible behavior and staged rollouts for risky changes.
-   When changing import/linking behavior, validate both build correctness and downstream lint/policy interactions.
