# Formatting and linting JS in JSP

This document provides a brief table of contents for the main items in [the npm-scripts/src/format directory](../format), which is responsible for formatting and linting JS inside JSP files.

The main workhorse is [the `processJSP()` function](./processJSP.js), which takes a JSP source string and extracts the contents of any script tags contained within so that they can be processed by Prettier, ESLint, or Terser. Formatting is handled by calling the Prettier API from an `onFormat` callback passed to `processJSP()`; linting is handled by calling the ESLint API from an `onLint` callback; minifying is handled by calling the Terser API from an `onMinify` callback. The overall sequence is:

1. Extract blocks of JS (ie. code inside `<script>` and `<aui:script>` tags) using [the `extractJS()` function](./extractJS.js).

1. For each block of JS:

    1. Trim leading and trailing blank lines with [the `trim()` function](./trim.js).

    1. Strip the "base indent" (ie. the common indent that all lines within the block share) using [the `dedent()` function](./dedent.js).

    1. Turn JSP tags, expressions, scriptlets (etc) — which are not JavaScript — into valid JS placeholders that allow Prettier and ESLint to parse each block as JavaScript without errors. Peculiar Unicode characters are used in replacements to ensure that the substitutions can be reversed after Prettier has finished formatting. This is done by [the `substituteTags()` function](./substituteTags.js).

    1. Strip indents inside JSP tags (eg. `<c:if>`/`</c:if>` tags) using [the `stripIndents()` function](./stripIndents.js).

    1. Pad the block with a number of lines containing empty statements so that any errors reported by Prettier or ESLint correspond to the source line numbers in the original JSP input, using [the `padLines()` function](./padLines.js).

    1. Via the `onFormat` callback, actually format the code via the Prettier API; via the `onLint` callback pass the code to the ESLint API for linting; via the `onMinify` callback pass the code to the Terser API for minification. All callbacks are optional, but in practice we always pass at least one so that `processJSP()` can actually do something useful.

    1. Remove the padding lines inserted in Step 5.

    1. Swap out the placeholder items inserted in Step 3 for the original JSP tags, expressions and scriptlets, using [the `restoreTags()` function](./restoreTags.js).

    1. Restore the base indent that was removed in Step 2, using [the `indent()` function](./indent.js).

1. Generate a new source string by inserting formatted script blocks in place of the original blocks.

## Other files of interest

- [`Lexer.js`](./Lexer.js): A generic class for defining lexers (tokenizers) that read an input string and produce a stream of tokens. This is used in the following places:
    - [`lex.js`](./lex.js) Defines a `lex()` function specifically for lexing JSP input based on the JSP and XML specifications.
    - `stripIndents()` (linked above) uses a small lexer to find opening and closing JSP tags and strip away the indenting of their contents.
    - `restoreTags()` (also linked above) uses another small lexer to scan the formatted JS looking for placeholders that should be replaced with their original contents.
- [`toFiller.js`](./toFiller.js): A function for transforming strings into replacement strings of a corresponding shape. This is used, for example, to create same-shaped JS comments that replace multi-line JSP tags.
- [`ReversibleMap.js`](./ReversibleMap.js): An ES6 `Map` subclass that provides `checkpoint()`, `rollback()`, and `commit()` methods. It is used in the lexer to record arbitrary metadata, and rollback mutations made as the lexer explores possible alternative branches.