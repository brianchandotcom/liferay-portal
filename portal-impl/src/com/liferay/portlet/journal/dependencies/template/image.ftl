<#include "init.ftl">

<#assign variableAltName = name + ".getAttribute(\"alt\")">

<img alt='${getVariableReferenceCode(variableAltName)}' src="${getVariableReferenceCode(variableName)}" />