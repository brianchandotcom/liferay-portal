<#include "../init.ftl">

<div class="aui-field-wrapper-content lfr-forms-field-wrapper">
	<@aui.input cssClass=cssClass helpMessage=field.tip label=label name=namespacedFieldName type="file">
		<@aui.validator name="acceptFiles">
			'${field.acceptFiles}'
		</@aui.validator>

		<#if required && !(fields??)>
			<@aui.validator name="required" />
		</#if>
	</@aui.input>

	${field.children}
</div>