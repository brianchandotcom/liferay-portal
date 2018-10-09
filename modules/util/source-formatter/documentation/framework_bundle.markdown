## org.osgi.framework.Bundle

Use `Bundle.getHeaders(java.lang.String locale)` instead of
`Bundle.getHeaders()`.

The logic in `Bundle.getHeaders()` is the same as
`Bundle.getHeaders(java.util.Locale.getDefault().toString())`.
When bundle header localization is not used, this is not necessary and it is
faster to use `Bundle.getHeaders(StringPool.BLANK)`.

When bundle header localization is used, explicitly call
`Bundle.getHeaders(java.util.Locale.getDefault().toString())` to look up the
default locale, or pass the `Locale.toString()` that is required.