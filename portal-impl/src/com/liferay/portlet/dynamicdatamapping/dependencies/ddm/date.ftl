<#include "../init.ftl">

<#assign DATE = staticUtil["java.util.Calendar"].DATE>
<#assign MONTH = staticUtil["java.util.Calendar"].MONTH>
<#assign YEAR = staticUtil["java.util.Calendar"].YEAR>

<#if (fieldRawValue?is_date)>
	<#assign fieldValue = calendarFactory.getCalendar(fieldRawValue?long)>

<#elseif (validator.isNotNull(predefinedValue))>
	<#assign predefinedDate = dateUtil.parseDate(predefinedValue, requestedLocale)>

	<#assign fieldValue = calendarFactory.getCalendar(predefinedDate?long)>
<#else>
	<#assign calendar = calendarFactory.getCalendar(timeZone)>

	<#assign fieldValue = calendarFactory.getCalendar(calendar.get(YEAR), calendar.get(MONTH), calendar.get(DATE))>
</#if>

<@aui["field-wrapper"] data=data helpMessage=escape(fieldStructure.tip) label=escape(label) required=required>
	<@liferay_ui["input-date"]
		cssClass=cssClass
		dayParam="${namespacedFieldName}Day"
		dayValue=fieldValue.get(DATE)
		disabled=false
		monthParam="${namespacedFieldName}Month"
		monthValue=fieldValue.get(MONTH)
		name="${namespacedFieldName}"
		yearParam="${namespacedFieldName}Year"
		yearValue=fieldValue.get(YEAR)
	/>

	${fieldStructure.children}
</@>