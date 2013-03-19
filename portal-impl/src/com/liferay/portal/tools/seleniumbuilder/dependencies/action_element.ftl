<#assign action = actionElement.attributeValue("action")>

<#assign x = action?last_index_of("#")>

${seleniumBuilderFileUtil.getVariableName(action?substring(0, x))}Action.${seleniumBuilderFileUtil.getVariableName(action?substring(x + 1))}("", "", "")