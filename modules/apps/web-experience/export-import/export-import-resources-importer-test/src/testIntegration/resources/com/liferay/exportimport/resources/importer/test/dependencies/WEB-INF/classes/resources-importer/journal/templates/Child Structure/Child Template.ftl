${Title.getData()}

${Description.getData()}

<#assign Datecdah_Data = getterUtil.getString(Datecdah.getData())>

<#if validator.isNotNull(Datecdah_Data)>
	<#assign Datecdah_DateObj = dateUtil.parseDate("yyyy-MM-dd", Datecdah_Data, locale)>

	${dateUtil.getDate(Datecdah_DateObj, "dd MMM yyyy - HH:mm:ss", locale)}
</#if>