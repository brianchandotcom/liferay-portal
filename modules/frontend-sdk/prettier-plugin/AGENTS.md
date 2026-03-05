# AGENTS.md

## Purpose

`@liferay/prettier-plugin` extends Prettier with Liferay-specific formatting behavior that standard Prettier does not provide, especially:

- blank-line handling around comments
- newline placement before `else`/`catch`/`finally` blocks

## Repository Map

- `index.mjs`: plugin entry (`options`, `parsers`, `printers`, `defaultOptions`).
- `options.mjs`: custom plugin options for comment ignore patterns.
- `parsers.mjs`: wraps Prettier babel/typescript parsers and applies custom transforms.
- `printers.mjs`: prints transformed formatted text from custom AST format.
- `rules/lines-around-comments.mjs`: comment newline normalization.
- `rules/newline-before-block-statements.mjs`: newline enforcement for block transitions.
- `utils/visitNode.mjs`: bottom-up AST traversal helper.
- `test/*.test.mjs`: node:test coverage for babel and typescript parsers.
- `dist/index.js`: bundled output (build artifact).

## How It Works

1. The parser runs Prettier formatting first.

1. Plugin strips itself from `plugins` before recursive `format()` call to avoid self-recursion.

1. Formatted text is reparsed, transformed by custom rules, then returned as a synthetic `FormattedText` AST.

1. Custom printer emits the transformed text.

## How It Interacts With `liferay-portal`

- `modules/.prettierrc.js` uses `plugins: ['@liferay/prettier-plugin']`.
- `modules/test/playwright/.prettierrc.js` also uses the plugin.
- `modules/package.json` pins plugin version in dev dependencies.
- Multiple `workspaces/*/package.json` files also depend on this plugin, so changes have broad formatting impact beyond `modules/`.

## Contribution Workflow

When adding/changing behavior:

1. Implement logic in `rules/*.mjs` (or parser/printer glue if needed).

1. Add/adjust tests in `test/*.test.mjs` for both babel and typescript paths.

1. Run:
   - `yarn test`
   - `yarn run format:check`

1. Build distributable bundle when needed:
   - `yarn build`

## Guardrails

- Prefer rule changes over printer hacks when possible.
- Keep transforms idempotent (formatting same file twice should not keep changing output).
- Treat `dist/` as generated output; source-of-truth lives in `*.mjs` files.
- Be careful when modifying parser recursion in `parsers.mjs`; plugin filtering there prevents infinite loops.

## Release Notes

- Release scripts: `preversion` runs format check + tests; `postversion` publishes.
- `.yarnrc` defines tag prefix and release commit message format.