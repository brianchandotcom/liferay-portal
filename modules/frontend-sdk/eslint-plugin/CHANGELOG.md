## [eslint-plugin/v1.9.1](https://github.com/liferay/liferay-frontend-projects/tree/eslint-plugin/v1.9.1) (2025-05-15)

[Full changelog](https://github.com/liferay/liferay-frontend-projects/compare/eslint-plugin/v1.9.0...eslint-plugin/v1.9.1)

### :wrench: Bug fixes

-   fix(eslint-plugin): add the name of the current package.json to the no-extraneous-dependencies rule ([\#1253](https://github.com/liferay/liferay-frontend-projects/pull/1253))

## [eslint-plugin/v1.9.0](https://github.com/liferay/liferay-frontend-projects/tree/eslint-plugin/v1.9.0) (2025-05-15)

[Full changelog](https://github.com/liferay/liferay-frontend-projects/compare/eslint-plugin/v1.8.1...eslint-plugin/v1.9.0)

### :new: Features

-   feat(eslint-plugin): add rule for deep imports across modules ([\#1252](https://github.com/liferay/liferay-frontend-projects/pull/1252))

## [eslint-plugin/v1.8.1](https://github.com/liferay/liferay-frontend-projects/tree/eslint-plugin/v1.8.1) (2025-05-14)

[Full changelog](https://github.com/liferay/liferay-frontend-projects/compare/eslint-plugin/v1.8.0...eslint-plugin/v1.8.1)

### :wrench: Bug fixes

-   fix(eslint-plugin): fix path ([\#1251](https://github.com/liferay/liferay-frontend-projects/pull/1251))

## [eslint-plugin/v1.8.0](https://github.com/liferay/liferay-frontend-projects/tree/eslint-plugin/v1.8.0) (2025-05-14)

[Full changelog](https://github.com/liferay/liferay-frontend-projects/compare/eslint-plugin/v1.7.0...eslint-plugin/v1.8.0)

### :new: Features

-   feat(eslint-plugin): add new rule for api submodule bundles ([\#1250](https://github.com/liferay/liferay-frontend-projects/pull/1250))

## [eslint-plugin/v1.7.0](https://github.com/liferay/liferay-frontend-projects/tree/eslint-plugin/v1.7.0) (2025-01-30)

[Full changelog](https://github.com/liferay/liferay-frontend-projects/compare/eslint-plugin/v1.6.0...eslint-plugin/v1.7.0)

### :new: Features

-   feat(eslint-plugin): add rule for new lines after copyright header ([\#1245](https://github.com/liferay/liferay-frontend-projects/pull/1245))

## [eslint-plugin/v1.6.0](https://github.com/liferay/liferay-frontend-projects/tree/eslint-plugin/v1.6.0) (2024-10-18)

[Full changelog](https://github.com/liferay/liferay-frontend-projects/compare/eslint-plugin/v1.5.0...eslint-plugin/v1.6.0)

### :new: Features

-   feat(eslint-plugin): add rule to sort named exports from a source ([\#1233](https://github.com/liferay/liferay-frontend-projects/pull/1233))

## [eslint-plugin/v1.5.0](https://github.com/liferay/liferay-frontend-projects/tree/eslint-plugin/v1.5.0) (2023-07-26)

[Full changelog](https://github.com/liferay/liferay-frontend-projects/compare/eslint-plugin/v1.4.0...eslint-plugin/v1.5.0)

### :new: Features

-   feat: ignore node_modules when minifying files and add new eslint rule for dependency check ([\#1156](https://github.com/liferay/liferay-frontend-projects/pull/1156))

## [eslint-plugin/v1.4.0](https://github.com/liferay/liferay-frontend-projects/tree/eslint-plugin/v1.4.0) (2022-10-12)

[Full changelog](https://github.com/liferay/liferay-frontend-projects/compare/eslint-plugin/v1.3.0...eslint-plugin/v1.4.0)

### :new: Features

-   feat(eslint-plugin): add ESLint rule to disable non-wrapped web storage API ([\#1016](https://github.com/liferay/liferay-frontend-projects/pull/1016))
-   feat(eslint-plugin): add ESLint rule to prevent usage of document.cookie ([\#1008](https://github.com/liferay/liferay-frontend-projects/pull/1008))

## [eslint-plugin/v1.3.0](https://github.com/liferay/liferay-frontend-projects/tree/eslint-plugin/v1.3.0) (2022-06-15)

[Full changelog](https://github.com/liferay/liferay-frontend-projects/compare/eslint-plugin/v1.2.0...eslint-plugin/v1.3.0)

### :new: Features

-   feat(eslint-plugin): handful of additions ([\#949](https://github.com/liferay/liferay-frontend-projects/pull/949))
-   feat(eslint-plugin): add new rule against A.Url ([\#807](https://github.com/liferay/liferay-frontend-projects/pull/807))
-   feat(eslint-plugin): Add new rule that preferes length checks ([\#815](https://github.com/liferay/liferay-frontend-projects/pull/815))

### :book: Documentation

-   docs(eslint-plugin): Update eslint docs ([\#835](https://github.com/liferay/liferay-frontend-projects/pull/835))

## [eslint-plugin/v1.2.0](https://github.com/liferay/liferay-frontend-projects/tree/eslint-plugin/v1.2.0) (2021-12-08)

[Full changelog](https://github.com/liferay/liferay-frontend-projects/compare/eslint-plugin/v1.1.0...eslint-plugin/v1.2.0)

### :wrench: Bug fixes

-   fix(eslint-plugin): update aui rules to only error if used outside of an aui module ([\#781](https://github.com/liferay/liferay-frontend-projects/pull/781))
-   fix(eslint-plugin): dataset uses camelCased keys instead of kebab-case. ([\#754](https://github.com/liferay/liferay-frontend-projects/pull/754))

## [eslint-plugin/v1.1.0](https://github.com/liferay/liferay-frontend-projects/tree/eslint-plugin/v1.1.0) (2021-11-04)

[Full changelog](https://github.com/liferay/liferay-frontend-projects/compare/eslint-plugin/v1.0.0...eslint-plugin/v1.1.0)

### :new: Features

-   add new rule to check for null before typeof object
-   add new rule to disalllow anonymous functions as exports
-   add new rule to disallow 'use strict' in es modules
-   add one-var rule to our rulelist
-   add plugin to enforce 'catch' for every promise
-   add rule for enforcing useState naming pattern
-   add rule for useRef naming pattern
-   add rule to avoid explicit references to localhost
-   add rule to make sure expect() has an assertion
-   add rule to prefer dataset
-   apply new sorting rule
-   enable eqeqeq rule to enforce type-safe equality operators
-   enable rule for sorting interface keys
-   enable valid-typeof rule
