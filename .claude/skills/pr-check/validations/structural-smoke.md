# Structural Smoke

## Trigger

Runs when the diff touches a file that one of the three scanners reads; see **Selection** for the path-to-scanner mapping.

## Match

`/configuration/.*Configuration\.java$|^portal-impl/src/portal-osgi-configuration\.properties$|^lib/|^\.classpath$|\.iml$|^\.idea/|/nbproject/project\.(properties|xml)$|\.gradle$|(^|/)(bnd|app)\.bnd$|^modules/.+/\.gitignore$|^modules/.+/README\.md$|portal-log4j(-ext)?\.xml$|\.lfrbuild|^\.github/`

## Command

### Install Portal Snapshots

Run before the scanners:

```bash
(cd "${REPO_ROOT}" && ant compile install-portal-snapshots)
```

### Selection

Run only the scanners whose inputs the diff touches:

| Diff Touches | Run |
| --- | --- |
| `*Configuration.java` under a `configuration` package, or `portal-impl/src/portal-osgi-configuration.properties` | `ConfigurationEnvBuilderTest` |
| `lib/**`, `.classpath`, `*.iml`, `.idea/**`, `nbproject/project.{properties,xml}` | `LibraryReferenceTest` |
| `*.gradle`, `bnd.bnd`, `app.bnd`, a module `.gitignore` or `README.md`, `portal-log4j*.xml`, `.lfrbuild*`, `.github/**` | `ModulesStructureTest` |

### Scanners

Run the selected scanners:

```bash
(cd "${REPO_ROOT}/portal-impl" && ant test-class -Dtest.class="ConfigurationEnvBuilderTest")
```

```bash
(cd "${REPO_ROOT}/portal-kernel" && ant test-class -Dtest.class="LibraryReferenceTest")
```

```bash
(cd "${REPO_ROOT}/portal-kernel" && ant test-class -Dtest.class="ModulesStructureTest")
```

## Checklist

One subitem per selected scanner:

```
- [ ] portal-impl: ConfigurationEnvBuilderTest
- [ ] portal-kernel: LibraryReferenceTest
- [ ] portal-kernel: ModulesStructureTest
```

## Notes

Only these three scanners belong here. Do not add `Log4jConfigUtilTest` or `SampleSQLBuilderTest` — they do not run in CI, and **Java Unit Tests** skips them.

## Time Estimate

~1-2 min for the scanners, plus the `install-portal-snapshots` build (fast when already built, a few minutes on a fresh checkout).