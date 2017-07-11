<#include "../init.ftl">

<#assign
	DATE = staticUtil["java.util.Calendar"].DATE
	MONTH = staticUtil["java.util.Calendar"].MONTH
	YEAR = staticUtil["java.util.Calendar"].YEAR
/>

<#if validator.isNotNull(fieldValue)>
	<#if hasFieldValue>
		<#assign
			dateValue = fieldRawValue?date["yyyy-MM-dd"]

			fieldValue = calendarFactory.getCalendar(requestedLocale)

			void = fieldValue.setTimeInMillis(dateValue?long)
		/>
	<#elseif validator.isNotNull(predefinedValue)>
		<#assign
			dateValue = predefinedValue?date["MM/dd/yyyy"]

			fieldValue = calendarFactory.getCalendar(requestedLocale)

			void = fieldValue.setTimeInMillis(dateValue?long)
		/>
	<#else>
		<#assign
			calendar = calendarFactory.getCalendar(timeZone)

			fieldValue = calendarFactory.getCalendar(calendar.get(YEAR), calendar.get(MONTH), calendar.get(DATE))
		/>
	</#if>

	<#assign
		day = fieldValue.get(DATE)
		month = fieldValue.get(MONTH)
		year = fieldValue.get(YEAR)
	/>
<#else>
	<#if required>
		<#assign
			calendar = calendarFactory.getCalendar(timeZone)

			day = calendar.get(DATE)
			month = calendar.get(MONTH)
			year = calendar.get(YEAR)
		/>
	<#else>
		<#assign
			day = 0
			month = -1
			year = 0
		/>
	</#if>
</#if>

<#assign
	dayValue = paramUtil.getInteger(request, "${namespacedFieldName}Day", day)
	monthValue = paramUtil.getInteger(request, "${namespacedFieldName}Month", month)
	yearValue = paramUtil.getInteger(request, "${namespacedFieldName}Year", year)
/>

<@liferay_aui["field-wrapper"]
	cssClass="form-builder-field"
	data=data
	helpMessage=escape(fieldStructure.tip)
	label=escape(label)
	name=namespacedFieldName
>
	<div class="form-group">
		<@liferay_ui["input-date"]
			cssClass=cssClass
			dayParam="${namespacedFieldName}Day"
			dayValue=dayValue
			disabled=false
			monthParam="${namespacedFieldName}Month"
			monthValue=monthValue
			name="${namespacedFieldName}"
			nullable=true
			required=required
			yearParam="${namespacedFieldName}Year"
			yearValue=yearValue
		/>
	</div>

	${fieldStructure.children}
</@>