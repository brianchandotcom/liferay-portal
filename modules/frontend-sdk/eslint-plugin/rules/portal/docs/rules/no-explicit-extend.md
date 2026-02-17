# Disallow unnecessary extensions in configuration files (no-explicit-extends)

This rule guards against unnecessary extensions in configuration files.

For example, in `.eslintrc.js` files, it is not necessary to include the "liferay/portal" or "liferay/react" configurations in the "extends" property, because these apply by default in liferay-portal.

Likewise, in `.babelrc.js` files, it is not necessary to include "@babel/preset-env" or "@babel/preset-react" in the "presets" property, because they also apply by default.

## Rule Details

Examples of **incorrect** code for this rule:

```js
// .eslintrc.js
module.exports = {
	extends: ['liferay/metal', 'liferay/portal', 'liferay/react'],
};

// .babelrc.js
module.exports = {
	presets: ['@babel/preset-env', '@babel/preset-react', 'fancy-preset'],
};
```

Examples of **correct** code for this rule:

```js
// .eslintrc.js
module.exports = {
	extends: ['liferay/metal'],
};

// .babelrc.js
module.exports = {
	presets: ['fancy-preset'],
};
```

## Further Reading

-   [eslint-config-liferay/#53](https://github.com/liferay/eslint-config-liferay/pull/53)
-   [eslint-config-liferay/#130](https://github.com/liferay/eslint-config-liferay/issues/130)
-   [IFI-1194](https://issues.liferay.com/browse/IFI-1194)
