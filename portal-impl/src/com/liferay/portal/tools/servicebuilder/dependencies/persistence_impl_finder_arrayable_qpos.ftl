<#list finderColsList as finderCol>
	<#if finderCol.hasArrayableOperator()>
		if (${finderCol.names} != null) {
			<#if finderCol.type == "String">
				for (int i = 0; i < ${finderCol.names}.length; i++) {
					if ((${finderCol.names}[i] != null) && !${finderCol.names}[i].equals(StringPool.BLANK)) {
						qPos.add(${finderCol.names});
					}
				}
			<#else>
				qPos.add(${finderCol.names});
			</#if>
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