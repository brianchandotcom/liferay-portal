<#list finderColsList as finderCol>
	<#if finderCol.isPrimitiveType()>
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