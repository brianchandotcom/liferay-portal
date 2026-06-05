# Theme Build

## Trigger

A shared CSS input that themes consume changed: `frontend-css-common`, `frontend-js-clay-web/clay/clay-css`, or the `frontend-theme-styled` or `frontend-theme-unstyled` parent themes. A theme's own `packageRunBuild` already runs when the theme is deployed (Per-Module Deploy covers that), but a change to a shared input recompiles no theme on its own.

## Match

`^modules/apps/frontend-css/frontend-css-common/|^modules/apps/frontend-js/frontend-js-clay-web/clay/clay-css/|^modules/apps/frontend-theme/frontend-theme-(styled|unstyled)/`

## Selection

A shared-CSS change fans out to every theme. Select each module under `modules/apps/frontend-theme` whose `package.json` declares a `liferayTheme` block, and convert each to a Gradle project path:

```bash
grep --files-with-matches --include=package.json --recursive '"liferayTheme"' \
	"${REPO_ROOT}/modules/apps/frontend-theme" | sed "s#/package.json##"
```

## Command

Run `packageRunBuild` (not `deploy`) per theme:

```bash
("${REPO_ROOT}/gradlew" \
	--project-dir "${REPO_ROOT}/modules" \
	:apps:frontend-theme:<theme>:packageRunBuild)
```

The build needs `node` and an `npm`/`yarn` install and builds both parent themes once; that work amortizes across the theme set.

## Checklist

```
- [ ] (One subitem per theme:) packageRunBuild <theme path>
```

## Time Estimate

~30 sec - 2 min per theme; the first also pays node download, install, and parent-theme builds.