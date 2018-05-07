<#include "../init.ftl">

<@liferay_aui["field-wrapper"] data=data>
	<@liferay_aui.input
		cssClass=cssClass
		helpMessage=escape(fieldStructure.tip)
		label=escape(label)
		name=namespacedFieldName
		type="button"
		value=fieldStructure.predefinedValue
	/>

	${fieldStructure.children}
</@>