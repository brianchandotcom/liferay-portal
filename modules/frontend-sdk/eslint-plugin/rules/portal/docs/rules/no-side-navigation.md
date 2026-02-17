# Disallow use of deprecated jQuery `sideNavigation` plugin (no-side-navigation)

This rule guards against the use of the deprecated jQuery `sideNavigation()` API, which was ported to vanilla JavaScript in [LPS-95476](https://github.com/brianchandotcom/liferay-portal/pull/74090).

## Rule Details

Examples of **incorrect** code for this rule:

```js
$(toggler).sideNavigation();

$(toggler).sideNavigation('hide');

<span data-toggle="sidenav"></span>;
```

Examples of **correct** code for this rule:

```js
Liferay.SideNavigation.initialize(toggler);

Liferay.SideNavigation.hide(toggler);

<span data-toggle="liferay-sidenav"></span>;
```

## Further Reading

-   [LPS-95476 Port side-navigation.js jQuery plugin from Clay to vanilla JS](https://github.com/brianchandotcom/liferay-portal/pull/74090)
-   [LPS-96486 Add lint to enforce use of new SideNavigation API](https://issues.liferay.com/browse/LPS-96486)
