<#include "../init.ftl">

<@aui["field-wrapper"] data=data>
	<div class="form-group">
		<@aui.input cssClass=cssClass dir=requestedLanguageDir helpMessage=escape(fieldStructure.tip) label=escape(label) name=namespacedFieldName required=required type="text" value=fieldValue>
			<@aui.validator name="number" />
		</@aui.input>
	</div>

	${fieldStructure.children}
</@>