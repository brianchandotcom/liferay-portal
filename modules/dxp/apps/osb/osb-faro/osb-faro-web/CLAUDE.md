# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Overview

`osb-faro-web` is the frontend module for **Liferay Analytics Cloud (Faro)**. It is a Liferay OSGi bundle that ships a single-page React application loaded inside a Liferay portlet (`view.jsp` / `init.jsp`). The app is bundled with Webpack and served from `src/main/resources/META-INF/resources/dist/`.

This module is part of the larger `osb-faro` Gradle project. Typical day-to-day work happens here with `yarn`, but the canonical build and deploy commands are Gradle tasks that wrap this directory (run from `osb-faro/`): `./gradlew packageRunStart`, `packageRunTest`, `formatSource`, `deploy`.

## Migration status

See [`MIGRATION_PLAN.md`](./MIGRATION_PLAN.md) for the full phase breakdown (build tooling upgrade, TypeScript strict mode + lint cleanup, test suite rewrite, and upcoming react-router v7 + coverage work). Consult it before starting structural work to see what's already done and what's pending — do not duplicate effort or regress completed phases.

## Commands

All `yarn` commands are run from this directory.

| Task | Command |
| --- | --- |
| Dev server (proxies to `FARO_URL` or `http://0.0.0.0:8080`, port 3000) | `yarn start` |
| Production build | `yarn webpack` |
| Dev build (no server) | `yarn webpack:dev` |
| Type-check (strict) | `yarn check-types` |
| Lint | `yarn lint` |
| Lint + auto-fix | `yarn format` |
| Test suite (coverage off, 4 workers) | `yarn test` |
| Run a single test file | `yarn test path/to/file.test.tsx` |
| Run a single test by name | `yarn test -t "test name pattern"` |
| Build MJML email templates | `yarn email` |

Tests force `TZ=Etc/GMT` and `LC_ALL=en-US` — time/locale-sensitive tests assume that environment.

## Architecture

### Entry point and providers

`src/main/js/main.jsx` mounts `<App />` into `#faroApp`. `App.tsx` wires up the stack of providers (top-down):

1. `ApolloProvider` — `shared/apollo/client` (GraphQL)

1. Redux `Provider` — `shared/store` (backed by `immutable` via `redux-immutable`)

1. Clay UI contexts — `ClayIconSpriteContext`, `ClayLinkContext` (bridges Clay's `href` to react-router `<Link>`), `ClayTooltipProvider`

1. App contexts — `UnassignedSegmentsProvider`, `OnboardingContext`, `ChannelProvider`

1. `BrowserRouter` with a custom `getUserConfirmation` that opens a Redux-managed confirmation modal for unsaved-changes navigation

1. `RoutesContainer` — fetches the current user + project for the `:groupId` param and bootstraps Pendo analytics

1. `<Switch>` of lazy-loaded top-level routes; everything not matched falls through to `WorkspaceLayer` (the workspace-scoped sub-app) and then `RouteNotFound`

### Routing

- Top-level routes are declared in `App.tsx`; workspace-scoped routes live under `shared/components/WorkspaceLayer`.
- `Routes` constants are in `shared/util/router`.
- `route-middleware/BundleRouter.tsx` is a `<Route>` wrapper that destructures `params` + query into props (via `useQueryParams`). When `destructured={false}`, it instead injects a `router={{params, query}}` prop — some legacy pages depend on this shape.
- Most pages are `React.lazy` + `webpackChunkName` code-split; `<Suspense>` shows `<Loading />` during chunk load.

### State management

Redux state is an `Immutable.Map`. Access state with `state.getIn([...])`, not dot-access — selectors returning immutable records are the norm. Reducers live in `shared/reducers/` and are combined in `shared/reducers/index.js`. The store lazy-picks `store-dev` vs `store-prod` based on `DEVELOPER_MODE`. Local storage persistence is wired via `store.subscribe(throttle(saveState, 1000))` in `App.tsx`.

GraphQL state is managed separately by Apollo (`shared/apollo/client`, cache in `shared/apollo/cache.js`). New data-fetching code should prefer Apollo hooks over adding to Redux.

### Module layout and path aliases

Feature areas are top-level folders under `src/main/js/`: `commerce`, `contacts`, `event-analysis`, `experiments`, `individual`, `segment`, `settings`, `sites`, `ui-kit`, plus cross-cutting `shared`, `cerebro-shared`, `custom-types`, `route-middleware`, `test`, `assets`.

Each of these is an import alias (resolved in both `webpack.common.js` and `jest.config.js` `moduleNameMapper`) — always import via alias, never relative paths that cross a feature boundary:

```ts
import Loading from 'shared/components/Loading';  // not '../../shared/components/Loading'
```

Keep the webpack alias list and the Jest `moduleNameMapper` in sync when adding a new top-level folder.

### TypeScript

- `strict: true` is enabled and the codebase is at 0 type errors. Keep it that way — fix type errors, don't silence them.
- Ambient/override types live in `src/main/js/custom-types/*.d.ts`. Notably `react-router-dom.d.ts` augments `useParams()` return typing — do not fix typing issues by pinning/resolving `react-router-dom`, augment here instead.
- Target is `ES5` with `jsx: react-jsx`. `baseUrl` is `src/main/js`, so imports match the webpack aliases.

### Testing

- Jest + `@testing-library/react` (React 18). `fakeTimers.enableGlobally = true` — use `jest.advanceTimersByTime` / `runAllTimers` rather than real waits.
- Setup file: `src/main/js/test/setup.js`. Mocks live in `src/main/js/test/__mocks__` (notably `jspdf`, `@react-dnd/invariant`, `react-dnd` cjs entries).
- Shared test utilities in `src/main/js/test/`: `mock-store.jsx`, `mock-router.jsx`, `mock-channel-context.jsx`, `helpers.js`, `data.js`, `graphql-data.js`. Prefer these over hand-rolling providers in each test.
- `jest.config.js` defines a massive `globals.faroConstants` that mirrors runtime constants injected by Liferay — adding a new constant key used in tests may require updating this.
- Coverage thresholds: branches 60 / functions 60 / lines 75 / statements 70 (global).
- Import `renderHook` from `@testing-library/react` (React 18-compatible), not from the legacy `@testing-library/react-hooks`.
- Clay modals render via React portal into `document.body`. Snapshot portal-rendered UIs with `expect(document.body).toMatchSnapshot()` — `container` captures the render root only.

### Build details

- Webpack externalizes `react`, `react-dom`, and all `@clayui/*` (except `@clayui/css`) as ES modules — they are provided by Liferay at runtime. Don't bundle them.
- `FARO_ENV`, `FARO_DEV_MODE`, `FARO_PENDO_API_KEY` are injected via `DefinePlugin`. `moment` locales are ignored (`IgnorePlugin`) to shrink the bundle.
- SVG handling uses `svg-sprite-loader` — all SVGs are emitted to a single `sprite.svg` referenced via `ClayIconSpriteContext`.
- `ts-loader` runs with `transpileOnly: true`; type errors surface through `ForkTsCheckerWebpackPlugin` during webpack builds (and via `yarn check-types` in CI).

## Conventions

### ESLint (strict, auto-fixable)

`eslint-config-liferay` + a long list of stylistic rules. `@typescript-eslint/{parser,eslint-plugin}` is pinned at `^5.62.0` — this version preserves legacy decorators in the AST, which is required for `no-unused-vars` to see decorator-only references. Rules most likely to trip you up:

- `sort-imports-es6-autofix/sort-imports-es6` — imports are sorted. Let `yarn format` handle it.
- `sort-keys` — object keys must be alphabetized (case-insensitive).
- `sort-destructure-keys` — destructured keys too.
- `react/jsx-sort-props` — JSX props are alphabetized (case-insensitive).
- `react/jsx-no-literals` — no bare string literals in JSX; wrap user-facing strings in `Liferay.Language.get(...)` or a constant.
- `no-console: error` — remove before committing.
- `react/jsx-handler-names` — handlers must match `handleX` / `onX` conventions.

### Prettier

Tabs, width 4, single quotes (JS and JSX), no bracket spacing (`{foo}` not `{ foo }`), no trailing commas, LF line endings. Mirrors the settings you see across the codebase — running `yarn format` is the fastest way to stay in sync.

### i18n

User-facing strings go through `Liferay.Language.get('key-with-dashes')`. Keys are extracted to the `osb-faro-service` module — the dev loader (`liferay-lang-key-dev-loader`, wired only in `webpack.dev.js`) surfaces missing keys during development.

## Working rules

- **TypeScript errors must be fixed, not silenced.** Resolve errors by correcting types (narrow, augment in `custom-types/*.d.ts`, or fix the implementation). Do not use `@ts-ignore`, `@ts-expect-error`, or `any` as escape hatches.
- **Failing unit tests must be rewritten from scratch.** Keep only each test's `describe` / `it` description (the intent). Discard the old setup, mocks, assertions, and rewrite the body against the current implementation using the shared utilities in `src/main/js/test/`. Do not patch the old test body to make it pass.

## Legacy code

The codebase is mid-transition. Several patterns still exist but are on the way out — do not extend them in new code:

- **`.js` / `.jsx` files** — legacy, pending migration to TypeScript. When touching one for a real change, migrate it to `.ts` / `.tsx`. Do not create new `.js` / `.jsx` files.
- **HOCs** (`shared/hoc/`, `*/hoc/`) — legacy, pending migration. Do not write new HOCs. Use hooks instead (`shared/hooks/` for shared ones).
- **Redux** — deprecated. Do not add new reducers, actions, or `connect` / `mapStateToProps` wiring. Existing Redux code stays until migrated piecemeal.
- **`react-router-dom` v5** — migration to v7 is planned (see `MIGRATION_PLAN.md`, Phase 3). Do not add new `<Switch>` / `useHistory` usages beyond what the current route tree already requires.