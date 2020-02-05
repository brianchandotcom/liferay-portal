## BNDBundleInformationCheck

`bnd.bnd` should always contain the following headers:
`Bundle-Version`, `Bundle-Name` and `Bundle-SymbolicName`

The `Bundle-Name` and `Bundle-SymbolicName` should match the directory path of
the module.

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