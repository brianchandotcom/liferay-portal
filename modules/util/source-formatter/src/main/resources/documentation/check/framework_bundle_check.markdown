## FrameworkBundleCheck

Use `org.osgi.framework.Bundle.getHeaders(java.lang.String locale)` instead of
`org.osgi.framework.Bundle.getHeaders()`.

The logic in `org.osgi.framework.Bundle.getHeaders()` is the same as
`org.osgi.framework.Bundle.getHeaders(java.util.Locale.getDefault().toString())`.
When bundle header localization is not used, this is not necessary and it is
faster to use `org.osgi.framework.Bundle.getHeaders(StringPool.BLANK)`.

When bundle header localization is used, explicitly call
`org.osgi.framework.Bundle.getHeaders(java.util.Locale.getDefault().toString())`
to look up the default locale, or pass the `Locale.toString()` that is required.