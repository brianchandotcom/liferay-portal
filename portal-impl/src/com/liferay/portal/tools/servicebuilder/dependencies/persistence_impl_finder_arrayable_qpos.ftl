<#list finderColsList as finderCol>
	<#if finderCol.hasArrayableOperator()>
		if (${finderCol.names} != null) {
			for (int i = 0; i < ${finderCol.names}.length; i++) {
				if (Validator.isNotNull(${finderCol.names}[i])) {
					qPos.add(${finderCol.names}[i]);
				}
			}
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