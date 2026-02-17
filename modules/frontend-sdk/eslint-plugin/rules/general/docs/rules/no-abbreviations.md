# Avoid abbreviations (no-abbreviations)

Using abbreviations can make code harder to understand, especially on an international team where English may not be everybody's first language.

## Rule Details

This rule complains if it finds a registered abbreviation in an identifier.

Examples of **bad** abbreviations for this rule and their preferred replacements:

```js
| Abbreviations  | Preferred alternatives    |
| -------------- | ------------------------- |
| `arr`          |  `array`                  |
| `btn`          |  `button`                 |
| `cb`           |  `callback`               |
| `desc`         |  `description`            |
| `e`/`err`      |  `error`                  |
| `el`           |  `element`                |
| `evt`          |  `event`                  |
| `fm`           |  `form`                   |
| `fmt`          |  `format`                 |
| `idx`          |  `index`                  |
| `img`          |  `image`                  |
| `obj`          |  `object`                 |
| `opts`         |  `options`                |
| `prj`          |  `project`                |
| `sm`/`md`/`lg` |  `small`/`medium`/`large` |
```
