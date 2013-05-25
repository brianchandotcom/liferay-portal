<#include "../init.ftl">

<@aui["field-wrapper"] data=data helpMessage=escape(fieldStructure.tip)>
	<@aui.input cssClass=cssClass dir=requestedLocaleLangDir helpMessage=escape(fieldStructure.tip) label=escape(label) name=namespacedFieldName type="text" value=fieldValue>
		<@aui.validator name="number" />

		<#if required>
			<@aui.validator name="required" />
		</#if>
	</@aui.input>

	${fieldStructure.children}
</@>