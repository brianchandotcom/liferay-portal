<#include "../init.ftl">

<@aui["field-wrapper"] data=data>
	<div class="form-group">
		<@aui.input
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