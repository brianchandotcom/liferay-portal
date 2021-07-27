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

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

if (ddmTemplate != null) {
	renderResponse.setTitle(LanguageUtil.format(request, "edit-x", HtmlUtil.escape(ddmTemplate.getName(locale))));
}
else {
	renderResponse.setTitle(LanguageUtil.format(request, "add-x", HtmlUtil.escape(templateDisplayContext.getTemplateType(classNameId))));
}
%>

<portlet:actionURL name="/template/update_ddm_template" var="updateDDMTemplateURL">
	<portlet:param name="mvcPath" value="/edit_ddm_template.jsp" />
</portlet:actionURL>

<aui:form action="<%= updateDDMTemplateURL %>" enctype="multipart/form-data" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="ddmTemplateId" type="hidden" value="<%= ddmTemplateId %>" />
	<aui:input name="groupId" type="hidden" value="<%= scopeGroupId %>" />
	<aui:input name="classPK" type="hidden" value="<%= classPK %>" />
	<aui:input name="classNameId" type="hidden" value="<%= classNameId %>" />
	<aui:input name="resourceClassNameId" type="hidden" value="<%= resourceClassNameId %>" />
	<aui:input name="saveAndContinue" type="hidden" value="<%= false %>" />

	<aui:model-context bean="<%= ddmTemplate %>" model="<%= DDMTemplate.class %>" />

	<clay:container-fluid>
		<liferay-ui:error exception="<%= TemplateNameException.class %>" message="please-enter-a-valid-name" />
		<liferay-ui:error exception="<%= TemplateScriptException.class %>" message="please-enter-a-valid-script" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:input cssClass="form-control-inline" defaultLanguageId="<%= (ddmTemplate == null) ? LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()): ddmTemplate.getDefaultLanguageId() %>" label="" name="name" wrapperCssClass="article-content-title mb-0" />
			</aui:fieldset>

			<aui:fieldset>
				<aui:input name="script" placeholder="scriptContent" type="textarea" />
			</aui:fieldset>

			<aui:button-row>
				<aui:button cssClass="btn-secondary btn-sm mr-3" type="cancel" />

				<aui:button cssClass="btn-sm mr-3" type="submit" value="save-and-continue" />

				<aui:button cssClass="btn-sm mr-3" type="submit" value="save" />
			</aui:button-row>
		</aui:fieldset-group>
	</clay:container-fluid>
</aui:form>