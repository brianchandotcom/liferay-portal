<#include "../init.ftl">

<#if (fieldValue == "")>
	<#assign fieldValue = fieldStructure.predefinedValue>
</#if>

<#if (fieldRawValue?is_date)>
	<#assign fieldValue = fieldRawValue?string("MM/dd/yyyy")>
</#if>

<div class="aui-field-wrapper-content lfr-forms-field-wrapper">
	<@aui.input label=label name=namespacedFieldName type="hidden" value=fieldRawValue!"" />

	<@aui.input cssClass=cssClass helpMessage=fieldStructure.tip label=label name="${namespacedFieldName}formattedDate" type="text" value=fieldValue>
		<@aui.validator name="date" />

		<#if required>
			<@aui.validator name="required" />
		</#if>
	</@aui.input>

	${fieldStructure.children}
</div>

<@aui.script use="aui-datepicker">
	var fieldValueInput = A.one('#${portletNamespace}${namespacedFieldName}');
	var formattedDateInput = A.one('#${portletNamespace}${namespacedFieldName}formattedDate');

	var updateFieldValue = function(value) {
		var timestamp = '';

		try {
			var date = A.DataType.Date.parse(value);

			timestamp = date.getTime()
		}
		catch (e) {
		}

		fieldValueInput.val(timestamp);
	};

	formattedDateInput.on(
		{
			change: function(event) {
				var value = formattedDateInput.val();

				updateFieldValue(value);
			}
		}
	);

	updateFieldValue('${fieldValue}');

	new A.DatePicker(
		{
			after: {
				'calendar:select': function(event) {
					var date = event.date;
					var formatted = date.formatted;

					if (formatted.length) {
						formatted = formatted[0];

						this.get('currentNode').val(formatted);
						updateFieldValue(formatted);
					}
				}
			},
			calendar: {
				dateFormat: '%m/%d/%Y',

				<#if (fieldValue != "")>
					dates: ['${fieldValue}'],
				</#if>

				selectMultipleDates: false
			},
			setValue: false,
			trigger: formattedDateInput
		}
	).render();
</@aui.script>