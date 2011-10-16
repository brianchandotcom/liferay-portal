<#include "../init.ftl">

<#assign width = fieldStructure.width!25>

<#assign cssClass = cssClass +  " aui-w" + width>

<div class="aui-field-wrapper-content lfr-forms-field-wrapper">
	<@aui.input cssClass=cssClass helpMessage=fieldStructure.tip label=label name=namespacedFieldName type="textarea" value=fieldValue>
		<#if required>
			<@aui.validator name="required" />
		</#if>
	</@aui.input>

	${fieldStructure.children}
</div>