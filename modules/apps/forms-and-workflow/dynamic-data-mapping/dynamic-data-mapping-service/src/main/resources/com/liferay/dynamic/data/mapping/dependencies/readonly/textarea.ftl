<#include "../init.ftl">

<@liferay_aui["field-wrapper"]
	cssClass="form-builder-field"
	data=data
>
	<div class="form-group">
		<@liferay_aui.input
			cssClass=cssClass
			dir=requestedLanguageDir
			helpMessage=escape(fieldStructure.tip)
			label=escape(label)
			name=namespacedFieldName
			readonly=readonly
			type="textarea"
			value=fieldValue
		>
		</@liferay_aui.input>
	</div>

	${fieldStructure.children}
</@>