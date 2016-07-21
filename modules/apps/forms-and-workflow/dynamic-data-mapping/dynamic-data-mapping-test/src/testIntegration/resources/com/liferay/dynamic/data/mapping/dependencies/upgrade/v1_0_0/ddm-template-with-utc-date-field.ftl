<#assign date1_Data = getterUtil.getLong(Date.getData()) />

<#if date1_Data > 0>
	<#assign date1_DateObj = dateUtil.newDate(date1_Data) />

	${dateUtil.getDate(date1_DateObj, "dd MMM yyyy - HH:mm:ss", locale, timeZoneUtil.getTimeZone("UTC"))}
</#if>