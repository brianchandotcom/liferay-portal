<#include "../init.ftl">

<#if parentFieldStructure.predefinedValue?has_content>
	<#assign parentFieldRawValue = parentFieldStructure.predefinedValue />
<#else>
	<#assign parentFieldRawValue = "[]" />
</#if>

<#if fields?? && fields.get(parentName)??>
	<#assign
		parentValueIndex = getterUtil.getInteger(parentFieldStructure.valueIndex)

		field = fields.get(parentName)

		parentFieldRawValue = field.getValue(requestedLocale, parentValueIndex)!"[]"
	/>
</#if>

<#assign parentFieldNamespace = "" />

<#if parentFieldStructure.fieldNamespace??>
	<#assign parentFieldNamespace = "_INSTANCE_" + parentFieldStructure.fieldNamespace />
</#if>

<#assign
	namespacedParentFieldName = namespacedParentName + parentFieldNamespace
/>

<#if stringUtil.equals(parentType, "select")>
	<#assign
		parentFieldRawValues = getterUtil.getStringValues(jsonFactoryUtil.looseDeserialize(parentFieldRawValue))

		selected = paramUtil.getParameterValues(request, namespacedParentFieldName, parentFieldRawValues)?seq_contains(fieldStructure.value)
	/>

	<@liferay_aui.option
		cssClass=cssClass
		label=escape(fieldStructure.label)
		selected=selected
		value=escapeAttribute(fieldStructure.value)
	/>
<#else>
	<#assign
		checked = paramUtil.getString(request, namespacedParentFieldName, parentFieldRawValue) == fieldStructure.value
	/>
	<@liferay_aui.input
		checked=checked
		cssClass=cssClass
		label=escape(fieldStructure.label)
		name="${namespacedParentFieldName}"
		required=required
		type="radio"
		value=fieldStructure.value
	>
		<#if stringUtil.equals(parentFieldStructure.required, "true")>
			<@liferay_aui.validator name="required" />
		</#if>
	</@liferay_aui.input>
</#if>