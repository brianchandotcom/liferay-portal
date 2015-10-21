<#if finderCol.comparator == "=">
	<#if finderCol.isPrimitiveType(false)>
		(${finderCol.name} != ${entity.varName}.get${finderCol.methodName}())
	<#else>
		!Validator.equals(${finderCol.name}, ${entity.varName}.get${finderCol.methodName}())
	</#if>
<#elseif finderCol.comparator == "!=">
	<#if finderCol.isPrimitiveType(false)>
		(${finderCol.name} == ${entity.varName}.get${finderCol.methodName}())
	<#else>
		Validator.equals(${finderCol.name}, ${entity.varName}.get${finderCol.methodName}())
	</#if>
<#elseif finderCol.comparator == ">">
	<#if finderCol.type == "Date">
		(${finderCol.name}.getTime() >= ${entity.varName}.get${finderCol.methodName}().getTime())
	<#else>
		(${finderCol.name} >= ${entity.varName}.get${finderCol.methodName}())
	</#if>
<#elseif finderCol.comparator == ">=">
	<#if finderCol.type == "Date">
		(${finderCol.name}.getTime() > ${entity.varName}.get${finderCol.methodName}().getTime())
	<#else>
		(${finderCol.name} > ${entity.varName}.get${finderCol.methodName}())
	</#if>
<#elseif finderCol.comparator == "<">
	<#if finderCol.type == "Date">
		(${finderCol.name}.getTime() <= ${entity.varName}.get${finderCol.methodName}().getTime())
	<#else>
		(${finderCol.name} <= ${entity.varName}.get${finderCol.methodName}())
	</#if>
<#elseif finderCol.comparator == "<=">
	<#if finderCol.type == "Date">
		(${finderCol.name}.getTime() < ${entity.varName}.get${finderCol.methodName}().getTime())
	<#else>
		(${finderCol.name} < ${entity.varName}.get${finderCol.methodName}())
	</#if>
<#elseif finderCol.comparator == "LIKE">
	!StringUtil.wildcardMatches(${entity.varName}.get${finderCol.methodName}(), ${finderCol.name}, CharPool.UNDERLINE, CharPool.PERCENT, CharPool.BACK_SLASH,
	<#if finderCol.isCaseSensitive()>
		true
	<#else>
		false
	</#if>
	)
</#if>