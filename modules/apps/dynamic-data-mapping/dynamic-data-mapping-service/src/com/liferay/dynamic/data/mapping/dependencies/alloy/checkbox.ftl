<#include "../init.ftl">

<@aui["field-wrapper"] data=data>
	<@aui.input
		cssClass=cssClass
		helpMessage=escape(fieldStructure.tip)
		label=escape(label)
		name=namespacedFieldName
		type="checkbox"
		value=fieldValue
	/>

	${fieldStructure.children}
</@>