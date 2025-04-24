## BNDWebContextPathCheck

For web modules (modules ending with `-web`), the `Web-ContextPath` should match
the directory path of the module.

### Example

`\apps\marketplace\marketplace-web\bnd.bnd` contains the following
`Web-ContextPath`:

    Web-ContextPath: /marketplace-web

---

Frontend modules that have a `name` property in the `package.json` file should
have the `Web-ContextPath` header. The header should match the `name` in the
`package.json` file.

### Example

`\apps\frontend-js\frontend-js-web\bnd.bnd` contains the following
`Web-ContextPath`:

    Web-ContextPath: /frontend-js-web