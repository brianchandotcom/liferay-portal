<#include "../init.ftl">

<#if (fieldValue == "")>
	<#assign fieldValue = field.predefinedValue>
</#if>

<div class="aui-field-wrapper-content lfr-forms-field-wrapper">
	<@aui.input label=label name=namespacedFieldName type="hidden" value=fieldValue />

	<@aui.input cssClass=cssClass helpMessage=field.tip label=label name="${namespacedFieldName}dateFormat" type="text">
		<@aui.validator name="date" />

		<#if required>
			<@aui.validator name="required" />
		</#if>
	</@aui.input>

	${field.children}
</div>

<@aui.script use="aui-datepicker">
	new A.DatePicker(
		{
			calendar:
			{
				dates:
				[
					<#if (fieldValue != "")>
						'${fieldValue}'
					</#if>
				],
				on: {
					select: function(event) {
						A.one('#${portletNamespace}${namespacedFieldName}').val(A.DataType.Date.parse(event.date.formatted).getTime());
					}
				},
			},
			trigger: '#${portletNamespace}${namespacedFieldName}dateFormat'
		}
	).render();
</@aui.script>