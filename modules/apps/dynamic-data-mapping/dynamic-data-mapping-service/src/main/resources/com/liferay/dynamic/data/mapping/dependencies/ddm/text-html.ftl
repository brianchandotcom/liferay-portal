<#include "../init.ftl">

<#assign cssClass = "">

<#assign editorName = propsUtil.get("editor.wysiwyg.portal-impl.portlet.ddm.text_html.ftl")>

<#assign inputEditorName = "${namespacedFieldName}Editor">

<#assign fieldValue = paramUtil.getString(request, "${inputEditorName}", fieldValue)>

<#if editorName == "alloy-editor">
	<#assign cssClass = "form-control">
</#if>

<div class="lfr-ddm-field-group">
	<@aui["field-wrapper"] cssClass="field-wrapper-html" data=data helpMessage=escape(fieldStructure.tip) label=escape(label) name=inputEditorName required=required>
		<#assign skipEditorLoading = paramUtil.getBoolean(request, "p_p_isolated")>

		<@liferay_ui["input-editor"]
			contentsLanguageId="${requestedLocale}"
			cssClass="${cssClass}"
			editorName="${editorName}"
			initMethod=""
			name="${namespacedFieldName}Editor"
			onChangeMethod="${namespacedFieldName}OnChangeEditor"
			skipEditorLoading=skipEditorLoading
		/>

		<@aui.input name=namespacedFieldName type="hidden" value=fieldValue>
			<#if required>
				<@aui.validator name="required" />
			</#if>
		</@>
	</@>

	${fieldStructure.children}
</div>