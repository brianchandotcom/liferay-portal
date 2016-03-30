<#assign Date_Data = getterUtil.getLong(Date.getData())>

<#if (Date_Data > 0)>
	<#assign Date_DateObj = dateUtil.newDate(Date_Data)>

	${dateUtil.getDate(Date_DateObj, "dd MMM yyyy - HH:mm:ss", locale)}
</#if>