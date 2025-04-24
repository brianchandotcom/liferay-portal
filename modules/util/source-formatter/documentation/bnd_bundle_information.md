## BND Bundle information

`bnd.bnd` should always contain the following headers:
`Bundle-Version`, `Bundle-Name` and `Bundle-SymbolicName`

The `Bundle-Name`, `Bundle-SymbolicName` and `Web-ContextPath` should match the
directory path of the module.

### Example

`\apps\marketplace\marketplace-api\bnd.bnd` should contain the following
`Bundle-Name` and `Bundle-SymbolicName`:

    Bundle-Name: Liferay Marketplace API
    Bundle-SymbolicName: com.liferay.marketplace.api

---

For modules ending with `-impl`, the `Bundle-Name` should end with
`Implementation`.

### Example

    Bundle-Name: Liferay Users Admin Implementation
    Bundle-SymbolicName: com.liferay.users.admin.impl

For modules ending with `-util`, the `Bundle-Name` should end with `Utilities`.

### Example

    Bundle-Name: Liferay Users Admin Utilities
    Bundle-SymbolicName: com.liferay.users.admin.util

---

For web modules (modules ending with `-web`), the `Web-ContextPath` should also
match the directory path of the module.

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