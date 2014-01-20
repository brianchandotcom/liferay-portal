<#list finderColsList as finderCol>
	<#if finderCol.hasArrayableOperator()>
		if (${finderCol.names} != null) {
			qPos.add(${finderCol.names});
		}
	<#else>
		<#if !finderCol.isPrimitiveType()>
			if (bind${finderCol.methodName}) {
		</#if>

		qPos.add(

		<#if finderCol.type == "Date">
			CalendarUtil.getTimestamp(
		</#if>

		${finderCol.name}${serviceBuilder.getPrimitiveObjValue("${finderCol.type}")}

		<#if finderCol.type == "Date">
			)
		</#if>

		);

		<#if !finderCol.isPrimitiveType()>
			}
		</#if>
	</#if>
</#list>