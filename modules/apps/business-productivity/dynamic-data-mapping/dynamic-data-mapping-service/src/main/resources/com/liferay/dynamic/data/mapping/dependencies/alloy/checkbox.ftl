<#include "../init.ftl">

<@liferay_aui["field-wrapper"] data=data>
	<div class="form-group">
		<@liferay_aui.input
			cssClass=cssClass
			helpMessage=escape(fieldStructure.tip)
			label=escape(label)
			name=namespacedFieldName
			required=required
			type="checkbox"
			value=fieldValue
		/>
	</div>

	${fieldStructure.children}
</@>