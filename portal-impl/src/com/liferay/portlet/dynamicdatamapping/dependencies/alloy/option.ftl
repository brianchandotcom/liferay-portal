<#include "../init.ftl">

<#if parentFieldStructure.predefinedValue?has_content>
	<#assign parentFieldRawValue = parentFieldStructure.predefinedValue>
<#else>
	<#assign parentFieldRawValue = "[]">
</#if>

<#if fields?? && fields.get(parentName)??>
	<#assign parentValueIndex = getterUtil.getInteger(parentFieldStructure.valueIndex)>

	<#assign field = fields.get(parentName)>

	<#assign parentFieldRawValue = field.getValue(requestedLocale, parentValueIndex)!"[]">
</#if>

<#assign parentFieldNamespace = "">

<#if parentFieldStructure.fieldNamespace??>
	<#assign parentFieldNamespace = "_INSTANCE_" + parentFieldStructure.fieldNamespace>
</#if>

<#assign namespacedParentFieldName = namespacedParentName + parentFieldNamespace>

<#assign parentFieldRawValues = jsonFactoryUtil.looseDeserialize(parentFieldRawValue)>

<#assign selected = paramUtil.getParameterValues(request, namespacedParentFieldName, parentFieldRawValues)?seq_contains(fieldStructure.value)>

<#if parentType == "select">
	<@aui.option cssClass=cssClass label=escapeAttribute(fieldStructure.label) selected=selected value=escape(fieldStructure.value) />
<#else>
	<@aui.input checked=selected cssClass=cssClass label=escape(fieldStructure.label) name="${namespacedParentFieldName}" type="radio" value=fieldStructure.value>
		<#if parentFieldStructure.required?? && (parentFieldStructure.required == "true")>
			<@aui.validator name="required" />
		</#if>
	</@aui.input>
</#if>