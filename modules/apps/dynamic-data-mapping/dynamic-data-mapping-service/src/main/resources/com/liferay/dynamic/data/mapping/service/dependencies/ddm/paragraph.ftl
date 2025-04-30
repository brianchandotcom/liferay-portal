<#include "../init.ftl">

<#assign style = fieldStructure.style!"" />

<@liferay_ui.csp>
	<p style="${escapeAttribute(style)}">
		${escape(label)}

		${fieldStructure.children}
	</p>
</@liferay_ui.csp>