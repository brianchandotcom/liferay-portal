<#include "../init.ftl">

<#assign parentFieldRawValue = parentFieldStructure.predefinedValue!"[]">

<#if fields?? && fields.get(parentName)??>
	<#assign parentValueIndex = getterUtil.getInteger(parentFieldStructure.valueIndex)>

	<#assign field = fields.get(parentName)>

	<#assign parentFieldRawValue = field.getValue(requestedLocale, parentValueIndex)>
</#if>

<#assign selected = jsonFactoryUtil.looseDeserialize(parentFieldRawValue)?seq_contains(fieldStructure.value)>

<#if parentType == "select">
	<@aui.option cssClass=cssClass label=escapeAttribute(fieldStructure.label) selected=selected value=escape(fieldStructure.value) />
<#else>
	<#assign parentFieldNamespace = "">

	<#assign parentFieldNamespace = "">

	<#if parentFieldStructure.fieldNamespace??>
		<#assign parentFieldNamespace = "_INSTANCE_" + parentFieldStructure.fieldNamespace>
	</#if>

	<@aui.input checked=selected cssClass=cssClass label=escape(fieldStructure.label) name="${namespacedParentName}${parentFieldNamespace}" type="radio" value=fieldStructure.value>
		<#if parentFieldStructure.required?? && (parentFieldStructure.required == "true")>
			<@aui.validator name="required" />
		</#if>
	</@aui.input>
</#if>