<#include "../init.ftl">

<#assign
	DATE = staticUtil["java.util.Calendar"].DATE
	MONTH = staticUtil["java.util.Calendar"].MONTH
	YEAR = staticUtil["java.util.Calendar"].YEAR
/>

<#if (hasFieldValue)>
	<#assign
		dateValue = fieldRawValue?date["yyyy-MM-dd"]

		fieldValue = calendarFactory.getCalendar(requestedLocale)

		void = fieldValue.setTimeInMillis(dateValue?long)
	/>
<#elseif validator.isNotNull(predefinedValue)>
	<#assign
		predefinedDate = dateUtil.parseDate(predefinedValue, requestedLocale)

		fieldValue = calendarFactory.getCalendar(predefinedDate?long)
	/>
<#else>
	<#assign
		calendar = calendarFactory.getCalendar(timeZone)

		fieldValue = calendarFactory.getCalendar(calendar.get(YEAR), calendar.get(MONTH), calendar.get(DATE))
	/>
</#if>

<#assign
	dayValue = paramUtil.getInteger(request, "${namespacedFieldName}Day", fieldValue.get(DATE))
	monthValue = paramUtil.getInteger(request, "${namespacedFieldName}Month", fieldValue.get(MONTH))
	yearValue = paramUtil.getInteger(request, "${namespacedFieldName}Year", fieldValue.get(YEAR))
/>

<@liferay_aui["field-wrapper"] cssClass="form-builder-field" data=data helpMessage=escape(fieldStructure.tip) label=escape(label) name=namespacedFieldName>
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