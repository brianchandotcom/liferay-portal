<#assign macro = macroElement.attributeValue("macro")>

<#assign x = macro?last_index_of("#")>

${seleniumBuilderFileUtil.getVariableName(macro?substring(0, x))}Macro.${macro?substring(x + 1)}()