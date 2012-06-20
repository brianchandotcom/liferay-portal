<#include "../init.ftl">

<#if (fieldValue == "")>
	<#assign fieldValue = fieldStructure.predefinedValue>
</#if>

<#if (fieldRawValue?is_date)>
	<#assign day = fieldRawValue?string("dd")?number>
	<#assign month = defaultValueDate?string("MM")?number-1>
	<#assign year = defaultValueDate?string("yyyy")?number>
</#if>

<#function getDate >
	<#return dateUtil.newDate()>
</#function>

<div class="aui-field-wrapper-content lfr-forms-field-wrapper">
	<@aui.input label=label name=namespacedFieldName type="hidden" value=fieldRawValue!"" />
	
	<#assign defaultValueDate = getDate()>
	<#assign day = defaultValueDate?string("dd")?number>
	<#assign month = defaultValueDate?string("MM")?number-1>
	<#assign year = defaultValueDate?string("yyyy")?number>
	<#assign yearStart = defaultValueDate?string("yyyy")?number + 100>
	<#assign yearEnd = defaultValueDate?string("yyyy")?number - 100>

	<@liferay_ui["input-date"] dayParam="defaultValueDay" dayValue=day disabled=false monthParam="defaultValueMonth" monthValue=month yearParam="defaultValueYear" yearRangeEnd=yearStart yearRangeStart=yearEnd yearValue=year />


	${fieldStructure.children}
</div>

<@aui.script>
	var A = AUI();

	var fieldValueInput = A.one('#${portletNamespace}${namespacedFieldName}');
	var dayInput = A.one('#${portletNamespace}defaultValueDay');
	var monthInput = A.one('#${portletNamespace}defaultValueMonth');
	var yearInput = A.one('#${portletNamespace}defaultValueYear');

	var dayValue = ${day?c};
	var monthValue = ${month?c}+1;
	var yearValue = ${year?c};

	var updateFieldValue = function(value) {
		var timestamp = '';

		try {
			var date = A.DataType.Date.parse(value);

			timestamp = date.getTime();
		}
		catch (e) {
		}

		fieldValueInput.val(timestamp);
	};

	dayInput.on(
		{
			change: function(event) {
				var value = dayInput.val();

				dayValue = parseInt(value)+1;
				
				updateFieldValue(''+monthValue+'/'+dayValue+'/'+yearValue);

			}
		}
	);

	monthInput.on(
		{
			change: function(event) {
				var value = monthInput.val();

				monthValue = parseInt(value)+1;
				
				updateFieldValue(''+monthValue+'/'+dayValue+'/'+yearValue);

			}
		}
	);

	yearInput.on(
		{
			change: function(event) {
				var value = yearInput.val();

				yearValue = value;
				
				updateFieldValue(''+monthValue+'/'+dayValue+'/'+yearValue);

			}
		}
	);

	updateFieldValue(monthValue+'/'+dayValue+'/'+yearValue);

</@aui.script>