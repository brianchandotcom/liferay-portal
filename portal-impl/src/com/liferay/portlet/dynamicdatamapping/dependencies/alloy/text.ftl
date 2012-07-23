<#include "../init.ftl">

<#assign width = escapeAttribute(fieldStructure.width!25)>

<#assign cssClass = cssClass +  " aui-w" + width>

<div class="aui-field-wrapper-content lfr-forms-field-wrapper">
	<@aui.input cssClass=cssClass helpMessage=escape(fieldStructure.tip) label=escape(label) name=namespacedFieldName type="text" value=fieldValue>
		<#if required>
			<@aui.validator name="required" />
		</#if>
	</@aui.input>

	${fieldStructure.children}
</div>