<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

long ddmTemplateId = ParamUtil.getLong(request, "ddmTemplateId");

DDMTemplate ddmTemplate = DDMTemplateLocalServiceUtil.fetchDDMTemplate(ddmTemplateId);

long classNameId = BeanParamUtil.getLong(ddmTemplate, request, "classNameId");
long classPK = BeanParamUtil.getLong(ddmTemplate, request, "classPK");
long resourceClassNameId = BeanParamUtil.getLong(ddmTemplate, request, "resourceClassNameId");

long templateEntryId = ParamUtil.getLong(request, "templateEntryId");

EditDDMTemplateDisplayContext editDDMTemplateDisplayContext = (EditDDMTemplateDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

if (ddmTemplate != null) {
	renderResponse.setTitle(LanguageUtil.format(request, "edit-x", HtmlUtil.escape(ddmTemplate.getName(locale))));
}
else {
	renderResponse.setTitle(LanguageUtil.format(request, "add-x", HtmlUtil.escape(editDDMTemplateDisplayContext.getTemplateTypeLabel())));
}
%>

<aui:form action="<%= editDDMTemplateDisplayContext.getUpdateDDMTemplateURL() %>" enctype="multipart/form-data" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="ddmTemplateId" type="hidden" value="<%= ddmTemplateId %>" />
	<aui:input name="groupId" type="hidden" value="<%= scopeGroupId %>" />
	<aui:input name="classPK" type="hidden" value="<%= classPK %>" />
	<aui:input name="classNameId" type="hidden" value="<%= classNameId %>" />
	<aui:input name="resourceClassNameId" type="hidden" value="<%= resourceClassNameId %>" />
	<aui:input name="templateEntryId" type="hidden" value="<%= templateEntryId %>" />
	<aui:input name="saveAndContinue" type="hidden" value="<%= false %>" />

	<liferay-ui:error exception="<%= TemplateNameException.class %>" message="please-enter-a-valid-name" />
	<liferay-ui:error exception="<%= TemplateScriptException.class %>" message="please-enter-a-valid-script" />
	<liferay-ui:error exception="<%= TemplateSmallImageContentException.class %>" message="the-small-image-file-could-not-be-saved" />

	<liferay-ui:error exception="<%= TemplateSmallImageNameException.class %>">

		<%
		String[] imageExtensions = editDDMTemplateDisplayContext.smallImageExtensions();
		%>

		<liferay-ui:message key="image-names-must-end-with-one-of-the-following-extensions" /> <%= HtmlUtil.escape(StringUtil.merge(imageExtensions, StringPool.COMMA)) %>.
	</liferay-ui:error>

	<liferay-ui:error exception="<%= TemplateSmallImageSizeException.class %>">

		<%
		long imageMaxSize = editDDMTemplateDisplayContext.smallImageMaxSize();
		%>

		<liferay-ui:message arguments="<%= LanguageUtil.formatStorageSize(imageMaxSize, locale) %>" key="please-enter-a-small-image-with-a-valid-file-size-no-larger-than-x" translateArguments="<%= false %>" />
	</liferay-ui:error>

	<aui:model-context bean="<%= ddmTemplate %>" model="<%= DDMTemplate.class %>" />

	<nav class="component-tbar subnav-tbar-light tbar tbar-template">
		<clay:container-fluid>
			<ul class="tbar-nav">
				<li class="tbar-item tbar-item-expand">
					<aui:input cssClass="form-control-inline" defaultLanguageId="<%= (ddmTemplate == null) ? LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()): ddmTemplate.getDefaultLanguageId() %>" label="" name="name" placeholder='<%= LanguageUtil.format(request, "untitled-x", "template") %>' wrapperCssClass="mb-0" />
				</li>
				<li class="tbar-item">
					<div class="tbar-section text-right">
						<aui:button cssClass="btn-outline-borderless btn-outline-secondary btn-sm mr-3" href="<%= redirect %>" type="cancel" />

						<%
						String taglibOnClickSaveAndContinue = "Liferay.fire('" + liferayPortletResponse.getNamespace() + "saveAndContinue');";
						%>

						<aui:button cssClass="btn-secondary btn-sm mr-3" onClick="<%= taglibOnClickSaveAndContinue %>" primary="<%= false %>" type="submit" value="save-and-continue" />

						<%
						String taglibOnClickSaveTemplate = "Liferay.fire('" + liferayPortletResponse.getNamespace() + "saveTemplate');";
						%>

						<aui:button cssClass="btn-sm" onClick="<%= taglibOnClickSaveTemplate %>" type="submit" value="save" />
					</div>
				</li>
			</ul>
		</clay:container-fluid>
	</nav>

	<div>
		<div id="<portlet:namespace />ddmTemplateEditor">
			<div class="inline-item my-5 p-5 w-100">
				<span aria-hidden="true" class="loading-animation"></span>
			</div>

			<react:component
				componentId="ddmTemplateEditor"
				data="<%= editDDMTemplateDisplayContext.getDDMTemplateEditorContext() %>"
				module="js/ddm_template_editor/components/App"
			/>
		</div>
	</div>
</aui:form>

<aui:script>
	Liferay.after('<portlet:namespace />saveAndContinue', () => {
		document.<portlet:namespace />fm.<portlet:namespace />saveAndContinue.value = true;

		Liferay.fire('<portlet:namespace />saveTemplate');
	});

	Liferay.after('<portlet:namespace />saveTemplate', () => {
		submitForm(document.<portlet:namespace />fm);
	});
</aui:script>