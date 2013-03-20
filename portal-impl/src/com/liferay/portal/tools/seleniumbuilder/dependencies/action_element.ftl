<#assign action = actionElement.attributeValue("action")>

<#if actionElement.getName() == "execute" && action?starts_with("Is")>
	return
</#if>

<#assign x = action?last_index_of("#")>

${seleniumBuilderFileUtil.getVariableName(action?substring(0, x))}Action.${seleniumBuilderFileUtil.getVariableName(action?substring(x + 1))}("", "", "")

<#if actionElement.getName() == "execute">
;
</#if>