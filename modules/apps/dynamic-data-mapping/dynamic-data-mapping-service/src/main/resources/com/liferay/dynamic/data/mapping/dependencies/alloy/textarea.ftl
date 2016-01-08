<#include "../init.ftl">

<@aui["field-wrapper"] data=data>
	<div class="form-group">
		<@aui.input cssClass=cssClass dir=requestedLanguageDir helpMessage=escape(fieldStructure.tip) label=escape(label) name=namespacedFieldName required=required type="textarea" value=fieldValue>
			<#if required>
				<@aui.validator name="required" />
			</#if>
		</@aui.input>
	</div>

	${fieldStructure.children}
</@>