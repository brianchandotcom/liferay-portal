<#include "../init.ftl">

<@aui["field-wrapper"] data=data helpMessage=escape(fieldStructure.tip)>
	<@aui.input cssClass=cssClass dir=requestedLocaleLangDir label=escape(label) name=namespacedFieldName type="textarea" value=fieldValue>
		<#if required>
			<@aui.validator name="required" />
		</#if>
	</@aui.input>

	${fieldStructure.children}
</@>