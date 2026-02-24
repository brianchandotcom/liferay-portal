# AGENTS.md

## Purpose

`@liferay/prettier-config` is the shareable Prettier config package for Liferay style defaults. In this repo it primarily acts as the canonical preset object, while `liferay-portal` itself usually references the same options directly in project `.prettierrc.js` files.

## Repository Map

- `index.js`: ESM config export (uses `@liferay/prettier-plugin` object directly).
- `index.cjs`: CommonJS export compatibility.
- `package.json`: exports map, dependency on `@liferay/prettier-plugin`, release scripts.
- `CONTRIBUTING.md`: release process for publishing.

## Current Formatting Defaults

The default config enforces:

- `useTabs: true`
- `tabWidth: 4`
- `singleQuote: true`
- `quoteProps: 'consistent'`
- `trailingComma: 'es5'`
- `bracketSpacing: false`
- `plugins: [@liferay/prettier-plugin]` (CJS version references plugin by string)

## How It Interacts With `liferay-portal`

- `liferay-portal/modules/.prettierrc.js` currently defines equivalent options and loads `@liferay/prettier-plugin` directly.
- `modules/package.json` includes `@liferay/prettier-plugin` as a dev dependency.
- This package is useful for consumers that want a single shared config import, but portal modules currently keep explicit local config files.

## Contribution Workflow

When changing defaults:

1. Update both `index.js` and `index.cjs` consistently.
2. Verify downstream impact in `modules/.prettierrc.js` and other local Prettier configs that mirror these values.
3. Run:
   - `yarn run format:check`
4. If changing plugin behavior assumptions, coordinate with `frontend-sdk/prettier-plugin`.

## Release Notes

- Versioning/publish uses `preversion` and `postversion` scripts in `package.json`.
- Tag/message formatting is controlled by `.yarnrc`.
- Follow `CONTRIBUTING.md` for changelog generation and release steps.
