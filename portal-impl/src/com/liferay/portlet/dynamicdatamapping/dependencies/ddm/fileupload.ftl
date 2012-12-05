<#include "../init.ftl">

<@aui["field-wrapper"] data=data helpMessage=escape(fieldStructure.tip)>
	<@aui.input cssClass=cssClass helpMessage=escape(fieldStructure.tip) label=escape(label) name=namespacedFieldName type="file">
		<@aui.validator name="acceptFiles">
			'${escapeJS(fieldStructure.acceptFiles)}'
		</@aui.validator>

		<#if required && !(fields??)>
			<@aui.validator name="required" />
		</#if>
	</@aui.input>

	<#if (fields??) && (fieldValue != "")>
		<#assign fileJSONObject = getFileJSONObject(fieldRawValue)>

		<#assign fileName = fileJSONObject.getString("name")>
		<#assign className = fileJSONObject.getString("className")>
		<#assign classPK = fileJSONObject.getString("classPK")>

		<a href="/documents/ddm/${className}/${classPK}/${fieldName}/${valueIndex}">${fileName}</a>

		<#if !required>
			-

			<a href="
				<@liferay_portlet.actionURL>
					<@liferay_portlet.param name="struts_action" value="/dynamic_data_lists/edit_record_file" />
					<@liferay_portlet.param name="cmd" value="delete" />
					<@liferay_portlet.param name="redirect" value=portalUtil.getCurrentURL(request) />
					<@liferay_portlet.param name="recordId" value=recordId />
					<@liferay_portlet.param name="fieldName" value=fieldName />
					<@liferay_portlet.param name="valueIndex" value=valueIndex?string />
				</@>">

				<@liferay_ui.message key="delete" />
			</a>
		</#if>
	</#if>

	${fieldStructure.children}
</@>