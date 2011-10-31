<#include "../init.ftl">

<#assign fileName = "">
<#assign recordId = "">

<#if (fields??) && (fieldValue != "")>
	<#assign fileJSONObject = getFileJSONObject(fieldRawValue)>

	<#assign fileName = fileJSONObject.getString("name")>
	<#assign recordId = fileJSONObject.getLong("recordId")>
</#if>

<@aui["field-wrapper"] label=label>
	<a href="<@liferay_portlet.actionURL windowState="exclusive">
				<@liferay_portlet.param name="struts_action" value="/dynamic_data_lists/get_file_upload" />
				<@liferay_portlet.param name="recordId" value=recordId?c />
				<@liferay_portlet.param name="fieldName" value=fieldName />
				<@liferay_portlet.param name="fileName" value=fileName />
			</@>">

		${fileName}
	</a>
</@>