<#macro FinderQPos
	_arrayable = false
>
	<#list finderColsList as finderCol>
		<#if _arrayable && finderCol.hasArrayableOperator()>
			if (${finderCol.names} != null) {
				qPos.add(${finderCol.names});
			}
		<#elseif finderCol.isPrimitiveType()>
			qPos.add(${finderCol.name}${serviceBuilder.getPrimitiveObjValue("${finderCol.type}")});

		<#else>
			if (bind${finderCol.methodName}) {
				qPos.add(
					<#if finderCol.type == "Date">
						new Timestamp(${finderCol.name}.getTime())
					<#elseif finderCol.type == "String" && !finderCol.isCaseSensitive()>
						StringUtil.toLowerCase(${finderCol.name})
					<#else>
						${finderCol.name}
					</#if>
				);
			}
		</#if>
	</#list>
</#macro>