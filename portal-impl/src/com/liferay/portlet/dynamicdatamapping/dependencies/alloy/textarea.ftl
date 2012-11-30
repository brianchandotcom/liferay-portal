<#include "../init.ftl">

<#assign width = escapeAttribute(fieldStructure.width!25)>

<#assign cssClass = cssClass +  " aui-w" + width>

<@aui["field-wrapper"] data=data helpMessage=escape(fieldStructure.tip)>
	<@aui.input cssClass=cssClass label=escape(label) name=namespacedFieldName type="textarea" value=fieldValue>
		<#if required>
			<@aui.validator name="required" />
		</#if>
	</@aui.input>

	${fieldStructure.children}
</@>