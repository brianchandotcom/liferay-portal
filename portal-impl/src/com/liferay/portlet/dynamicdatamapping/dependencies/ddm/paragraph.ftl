<#include "../init.ftl">

<#assign style = field.style!"">

<p style="${htmlUtil.escape(style)}">
	${label}

	${field.children}
</p>