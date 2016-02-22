<#include "../init.ftl">

<#assign multiple = false>

<#if fieldStructure.multiple?? && (fieldStructure.multiple == "true")>
	<#assign multiple = true>
</#if>

<@liferay_aui["field-wrapper"] data=data>
	<div class="form-group">
		<@liferay_aui.select cssClass=cssClass helpMessage=escape(fieldStructure.tip) label=escape(label) multiple=multiple name=namespacedFieldName required=required>
			${fieldStructure.children}
		</@liferay_aui.select>
	</div>
</@>