<#include "init.ftl">

<#assign encodedName = stringUtil.replace(name, ".", "_") />

${r"<#assign"} ${encodedName}_Data = getterUtil.getString(${variableName})>

${r"<#if"} validator.isNotNull(${encodedName}_Data)>
	${r"<#assign"} ${encodedName}_DateObj = dateUtil.parseDate("yyyy-MM-dd", ${encodedName}_Data, locale)>

	${r"${"}dateUtil.getDate(${encodedName}_DateObj, "dd MMM yyyy - HH:mm:ss", locale)}
${r"</#if>"}