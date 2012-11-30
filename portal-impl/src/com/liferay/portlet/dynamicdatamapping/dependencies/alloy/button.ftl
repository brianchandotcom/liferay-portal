<#include "../init.ftl">

<@aui["field-wrapper"] data=data helpMessage=escape(fieldStructure.tip)>
	<@aui.input cssClass=cssClass label=escape(label) name=namespacedFieldName type="button" value=fieldStructure.predefinedValue />

	${fieldStructure.children}
</@>