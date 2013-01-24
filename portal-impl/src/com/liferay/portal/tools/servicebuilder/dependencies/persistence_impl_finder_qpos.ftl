<#list finderColsList as finderCol>
	<#if !finderCol.isPrimitiveType()>
		if (bind${finderCol.methodName}) {
	</#if>

	qPos.add(

	<#if finderCol.type == "Date">
		CalendarUtil.getTimestamp(
	</#if>

	${finderCol.name}${serviceBuilder.getPrimitiveObjValue("${finderCol.type}")}

	<#if finderCol.type == "String" && !finderCol.isCaseSensitive()>
		.toLowerCase()
	</#if>

	<#if finderCol.type == "Date">
		)
	</#if>

	);

	<#if !finderCol.isPrimitiveType()>
		}
	</#if>
</#list>