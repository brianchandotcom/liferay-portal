# osb-faro-web Migration Plan

## [x] LPD-77745, LPD-77746, LPD-77747, LPD-81379 — Phase 1 — Build Tooling Upgrade

Upgrade React 16 → 18, TypeScript → 5.x, Jest → 29.x, and Apollo Client → 3.x.
Also introduce `react-router-dom` type augmentation via `custom-types/react-router-dom.d.ts`, and add `compilerOptions.paths` overrides in `tsconfig.json` for `react`, `react-dom`, `react-dom/client`, and `react/jsx-runtime` — a workaround for nested `@types/react` directories (inside `@types/react-router`, `@types/react-router-dom`, and `@types/react-redux`) that pin older type definitions and produce `ReactNode`/`bigint` mismatches. The override forces every module to resolve against the top-level `@types/react@18.x`. The override stays until Phase 4 retires `@types/react-router*` and Phase 9 retires `@types/react-redux`.

## [x] Phase 2 — TypeScript Strict Mode & Lint Cleanup

Enable `strict: true` and fix all 2464 type errors across 14 incremental phases with no `@ts-ignore` / `any` escape hatches, and set `allowJs: true` in `tsconfig.test.json` so `.jsx` tests compile.
Bump `@typescript-eslint/{parser,eslint-plugin}` v4 → v5.62 to fix a parser bug that silently dropped legacy decorators from the AST and produced 196 false-positive `no-unused-vars` errors; clear the remaining lint errors down to zero.

## [x] Phase 3 — Test Suite Rewrite & Fixes (React 18 + Apollo v3)

Replace ~90 `container.firstChild` assertions across 71 files with meaningful queries, migrate `@apollo/react-testing` → `@apollo/client/testing`, extend the `jspdf` mock, and normalize `rangeEnd/rangeStart` across helpers and mapper tests.
Also fix pre-existing failures (`SuppressedUsers`, `DownloadStaticCSVReport`, `LifecycleContext`, `ExportLogModal`, `DateInput`) by adjusting mocks, moving snapshots to `document.body` for portal-rendered modals, and swapping `@testing-library/react-hooks` for the React 18-compatible `renderHook` from `@testing-library/react`.

## [ ] LPD-87323 — Phase 4 — React Router v5 → v7 Migration

Migrate `react-router-dom` from v5 to v7, replacing `<Switch>` / `<Route component>` / `useHistory` with the v7 data router APIs (`<Routes>`, element-based routes, `useNavigate`).
Retire the `custom-types/react-router-dom.d.ts` augmentation once v7 types ship correctly and update all route-middleware and lazy-loaded route definitions.
Drop `@types/react-router` and `@types/react-router-dom` from `package.json` (v7 ships types in-package) to eliminate 3 of the 4 nested `@types/react` copies that required the `tsconfig.json` `paths` workaround.

## [ ] LPD-87324 — Phase 5 — Coverage Improvement & Snapshot Removal

Raise project coverage thresholds by adding unit tests for modules currently below the bar, prioritizing hooks, reducers, and shared utilities.
Replace brittle `toMatchSnapshot` assertions with explicit DOM/behavior checks and delete the corresponding `.snap` files to stop masking regressions.

## [ ] LPD-87325 — Phase 6 — JavaScript → TypeScript Conversion

Convert remaining `.js` / `.jsx` files under `src/main/js/` to `.ts` / `.tsx`, prioritizing hot paths and files touched often by feature work.
Once the last `.js`/`.jsx` is gone, remove `allowJs` from `tsconfig.json` and `tsconfig.test.json` so TypeScript enforces strict types across the whole module.

## [ ] LPD-87326 — Phase 7 — React Class Components → Functional Components

Convert remaining `class X extends React.Component` declarations to function components with hooks (`useState`, `useEffect`, `useMemo`, `useRef`), dropping legacy lifecycle methods.
This unblocks Phase 8 (most HOCs wrap class components) and aligns the codebase with the React 18 concurrent-mode model.

## [ ] LPD-87327 — Phase 8 — HOCs → Hooks Migration

Replace HOC wrappers in `shared/hoc/` and `*/hoc/` (e.g. `withHistory`, `withRouter`, `withOnboarding`, `withAdminPermission`, `withQueryPagination`, `withBaseResults`) with equivalent custom hooks under `shared/hooks/`.
Remove the legacy `autobind-decorator`, `autoCancel`, `hasRequest`, `debounce` decorator toolkit once no class component references them.

## [ ] LPD-87328 — Phase 9 — Redux → React Context API

Replace Redux-managed slices (`shared/reducers/`, `shared/actions/`, `connect` / `mapStateToProps` bindings) with React Context + hook-based state, keeping the Apollo cache for all GraphQL data.
Retire `redux`, `react-redux`, `redux-immutable`, and related middleware once no component imports them, and drop the `Immutable.Map` root-state assumption from selectors and tests.
Removing `@types/react-redux` also eliminates the last nested `@types/react` copy — after this phase the `compilerOptions.paths` workaround in `tsconfig.json` (from Phase 1) can be deleted.

## [ ] LPD-87329, LPD-43280 — Phase 10 — Drop Internet Explorer Support

Bump Webpack/Babel targets to a modern browserslist (remove IE11 from `package.json` / `.browserslistrc`), drop `core-js` polyfills that only exist for IE, and change `tsconfig.json` `target` from `ES5` to `ES2020`+.
Remove IE-specific workarounds scattered across the codebase (CSS `-ms-*` prefixes, `Object.assign` polyfills, feature-detection branches).

## [ ] LPD-87330 — Phase 11 — Align ESLint Config with Liferay DXP

Migrate `.eslintrc.js` to extend the shared ESLint configuration used across Liferay DXP frontend modules, replacing the module-local rule set.
This reduces rule conflicts ahead of the upcoming site-initializer migration and keeps code formatting consistent with the rest of the Liferay portal modules.

## [ ] LPD-87331 — Phase 12 — Re-enable Pedantic Console Mode in Tests

Fix the underlying deprecations currently silenced in `src/main/js/test/setup.js` so the `pedantic.enable()` guard can be restored (throws on any `console.error` / `console.warn` during tests). Known sources:
- Apollo Client v3.14 deprecations: `addTypename` on `InMemoryCache` and `MockedProvider`, `connectToDevTools` option, `graphql` HOC and `<Query />` component (covered by Phase 8), cache merge policy warnings for entities like `SiteMetric` / `Query.site`.
- React 18 `ReactDOMTestUtils.act` warning — resolve by upgrading `@testing-library/react` to v14+ which uses `React.act`.
- Residual component-level warnings surfaced only after the silence is lifted.

## [ ] LPD-43282 — Phase 13 — Disable SASS Source Maps in Production

Disable SASS / CSS source map generation for production builds. Source maps help during development but inflate the production bundle and leak implementation details; flip `sass-loader`, `css-loader`, and `postcss-loader` to `sourceMap: false` in the production webpack config and keep them on for `yarn start` / `yarn webpack:dev`.

## [ ] LPD-43283 — Phase 14 — Replace `moment-timezone`

Remove the `moment-timezone` dependency — currently one of the largest single entries in the bundle — in favor of a lighter alternative (native `Intl.DateTimeFormat` or `date-fns-tz` with a pre-filtered zone list).
Audit every call site, migrate it to the replacement API, and delete the package from `package.json`. Timezone-sensitive tests must still pass under `TZ=Etc/GMT`.

## [ ] LPD-43284 — Phase 15 — Exclude the `ui-kit` Page from Production

Guard the internal `ui-kit` preview route so its chunks are not shipped to production. Gate the entry (and any lazy-loaded components it pulls) behind a `DEVELOPER_MODE` / `FARO_DEV_MODE` check so webpack tree-shakes it in production, while keeping the page reachable under `yarn start` for developers.

## [ ] LPD-43286 — Phase 16 — Shrink `sprite.svg`

Audit `assets/icons/` and remove icons that no longer appear in any component. The `svg-sprite-loader` currently emits every icon in the folder into a single sprite referenced by `ClayIconSpriteContext`; unused icons inflate the asset for no benefit. Verify no broken `<svg><use xlink:href=...>` references remain after the cleanup.

## [ ] LPD-43287 — Phase 17 — Clean Up `main.scss`

Remove dead selectors, duplicated imports, and legacy overrides from the root `main.scss` file. Relocate component-scoped rules into the owning component stylesheets so `main.scss` is limited to genuinely global styles. The final production CSS should be smaller with no visual regressions.

## [ ] LPD-77748 — Phase 18 — Remove `react-loadable`

Retire the `react-loadable` dependency. `React.lazy` + `Suspense` is already the dominant lazy-loading pattern across `App.tsx` and the workspace sub-app, making `react-loadable` redundant. Migrate any remaining call sites to `React.lazy` and drop the package from `package.json`.

Completed build-performance items already shipped under LPD-43277: Webpack filesystem cache (`LPD-43278`), `thread-loader` parallelization (`LPD-43279`), and `optimization.splitChunks` tuning (`LPD-43281`).