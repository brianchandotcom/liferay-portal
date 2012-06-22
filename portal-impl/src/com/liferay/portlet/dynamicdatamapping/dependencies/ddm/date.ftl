<#include "../init.ftl">

<#if (fieldRawValue?is_date)>
	<#assign fieldDateValue = fieldRawValue>
<#else>
	<#assign fieldDateValue = dateUtil.newDate()>
</#if>

<#assign day = fieldDateValue?string("dd")?number>
<#assign month = fieldDateValue?string("MM")?number-1>
<#assign year = fieldDateValue?string("yyyy")?number>
<#assign yearStart = fieldDateValue?string("yyyy")?number + 100>
<#assign yearEnd = fieldDateValue?string("yyyy")?number - 100>

<@aui["field-wrapper"] helpMessage=fieldStructure.tip label=label>
	<#if required>
		<@aui.validator name="required" />
	</#if>

	<@liferay_ui["input-date"] dayParam="${namespacedFieldName}Day" dayValue=day disabled=false monthParam="${namespacedFieldName}Month" monthValue=month yearParam="${namespacedFieldName}Year" yearRangeEnd=yearStart yearRangeStart=yearEnd yearValue=year cssClass=cssClass helpMessage=fieldStructure.tip/>

	${fieldStructure.children}
</@>
