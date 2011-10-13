<#include "../init.ftl">

<#assign style = fieldStructure.style!"">

<p style="${htmlUtil.escape(style)}">
	${label}

	${fieldStructure.children}
</p>