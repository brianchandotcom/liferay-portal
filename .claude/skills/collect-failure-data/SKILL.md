---
name: collect-failure-data
description: Run the project's `collect-failure-data` script for a given Testray case result ID, producing a JSON snapshot of that single failure at `output/test-failure-<caseResultId>-<YYYY-MM-DD>.json` with `name`, `type`, `errorTrace`, `lastPassSha`, and `firstFailSha`. The SHA fields are computed over the case's history filtered to the same routine as the supplied case result. Aborts with exit code 2 when the supplied case result has status `PASSED` (nothing to fix). Use this skill when the user wants the failure JSON for one specific Testray case result without running the full `/fix-test-failures` workflow.
---

# Collect failure data

Produce a fresh JSON snapshot of a single Testray case result's failure by running the project's `collect-failure-data` script. `/fix-test-failures` calls this same script under the hood for each input case result ID; invoke this skill directly when you only want the JSON and not the resolution.

## Invocation

```
/collect-failure-data <caseResultId>
```

- `<caseResultId>` (required, positive integer) — Testray case result to collect.

## Hard preconditions

Verify before running. Fail fast with a clear message if any is missing.

- The current working directory is the `liferay-test-fixer` project root (a `package.json` with the `collect-failure-data` script must exist).
- `.env.local` exists and exports `TESTRAY_CLIENT_ID` and `TESTRAY_CLIENT_SECRET`.

## Workflow

```
npm run collect-failure-data -- <caseResultId>
```

Possible outcomes:

- **Exit 0** — failure data fetched. The script writes `output/test-failure-<caseResultId>-<YYYY-MM-DD>.json` and prints its absolute path on stdout. Surface that path to the user.
- **Exit 2** — the supplied case result has status `PASSED`, so there is nothing to collect. The script writes the explanatory message to stderr (e.g. `Case result <id> for test "X" has status PASSED. Nothing to fix.`). Surface that message to the user and stop.
- **Other non-zero exit** — fetch error (case result not found, network failure, …). Surface the script's stderr verbatim.

If a snapshot for the same `<caseResultId>` and today's date already exists, the script overwrites it. That is intentional: one snapshot per case result per day.

## Hard rules

- Do **not** run any analysis or fix on the JSON. That is `/fix-test-failures`.
- Never commit or push.
- Fail fast on missing preconditions — do not silently substitute defaults for missing env vars.
- Read the path from the script's stdout; do not reconstruct the filename yourself.
