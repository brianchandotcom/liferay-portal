<#include "../init.ftl">

<div class="aui-field-wrapper-content lfr-forms-field-wrapper">
	<@aui.input cssClass=cssClass helpMessage=fieldStructure.tip label=label name=namespacedFieldName type="file">
		<@aui.validator name="acceptFiles">
			'${fieldStructure.acceptFiles}'
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

		<a href="/documents/ddm/${className}/${classPK}/${fieldName}">${fileName}</a>

		<#if !required>
			-

			<a href="
				<@liferay_portlet.actionURL>
					<@liferay_portlet.param name="struts_action" value="/dynamic_data_lists/edit_record_file" />
					<@liferay_portlet.param name="cmd" value="delete" />
					<@liferay_portlet.param name="redirect" value=portalUtil.getCurrentURL(request) />
					<@liferay_portlet.param name="recordId" value=recordId />
					<@liferay_portlet.param name="fieldName" value=fieldName />
				</@>">

				<@liferay_ui.message key="delete" />
			</a>
		</#if>
	</#if>

	${fieldStructure.children}
</div>