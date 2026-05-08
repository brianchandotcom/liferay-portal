# Per-Module Deploy

## Trigger

A module is in the deploy set AND **Full Portal Build** did not deploy it. The latter holds when:

- **Full Portal Build** did not fire.

- OR **Full Portal Build** fired but the module lacks `.lfrbuild-portal` (so `ant all` did not deploy it).

A module is in the deploy set when it has changed sources or resources — Java, JS, TS, frontend resources (`*.{css,sass,scss}`, `*.ftl`, `*.jsp`, `*.jspf`), lockfiles (`package-lock.json`, `yarn.lock`), module `*.properties`, or OSGi configuration (`bnd.bnd`, `gradle.properties`, `package.json` keys other than `test`).

Exclude modules whose **only** Java change is under `src/testIntegration`. Integration Test Compile already runs `compileTestIntegrationJava` for those, and `-test` modules do not deploy a runtime bundle — `gradlew :path:deploy` would be redundant. A diff that touches `src/testIntegration` *and* anything else in the same module still puts the module in the deploy set.

Expand by API consumers: for each `*-api/**/*.java` with added or removed `public` method signatures (`^[-+]\s*(public|protected).*\(.*\)`), grep `modules/**/build.gradle` for `project(":<api-path>")` and add those modules. The deploy set size N is used by [full-portal-build.md](full-portal-build.md)'s cost comparison.

Both behavior-change and surface-only edits fire this validation — the build verifies compile and resource bundling regardless of intent.

## Match

`^modules/.+\.(java|js|jsx|ts|tsx|css|scss|sass|ftl|jsp|jspf|properties)$|^modules/.+/(bnd\.bnd|gradle\.properties|package-lock\.json|yarn\.lock|package\.json)$`

## Command

Set up once, then deploy each module:

```bash
(cd "${REPO_ROOT}/portal-impl" && ant compile install-portal-snapshots)
```

```bash
("${REPO_ROOT}/gradlew" \
	--parallel \
	--project-dir "${REPO_ROOT}/modules" \
	:<path>:deploy)
```

## Checklist

```
- [ ] Setup: ant compile install-portal-snapshots
- [ ] (One subitem per deploy-set module:) Deploy <module path>
```

## Time Estimate

3 min setup + 1 min × ⌈N/4⌉.