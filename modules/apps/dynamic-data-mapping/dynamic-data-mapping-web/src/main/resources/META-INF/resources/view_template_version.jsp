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

long templateVersionId = ParamUtil.getLong(request, "templateVersionId");

DDMTemplateVersion templateVersion = DDMTemplateVersionServiceUtil.getTemplateVersion(templateVersionId);

DDMTemplate template = templateVersion.getTemplate();

String title = LanguageUtil.format(request, "x-version-x", new Object[] {templateVersion.getName(locale), templateVersion.getVersion()});

PortletURL backURL = renderResponse.createRenderURL();

backURL.setParameter("mvcPath", "/view_template_history.jsp");
backURL.setParameter("redirect", redirect);
backURL.setParameter("templateId", String.valueOf(template.getTemplateId()));
%>

<div class="container-fluid-1280">
	<c:choose>
		<c:when test="<%= ddmDisplay.isShowBackURLInTitleBar() %>">

			<%
			portletDisplay.setShowBackIcon(true);
			portletDisplay.setURLBack(backURL.toString());

			renderResponse.setTitle(title);
			%>

		</c:when>
		<c:otherwise>
			<liferay-ui:header
				backURL="<%= backURL.toString() %>"
				localizeTitle="<%= false %>"
				title="<%= title %>"
			/>
		</c:otherwise>
	</c:choose>

	<aui:model-context bean="<%= templateVersion %>" model="<%= DDMTemplateVersion.class %>" />

	<aui:input disabled="<%= true %>" name="name" />

	<aui:input disabled="<%= true %>" name="language" />

	<aui:input disabled="<%= true %>" name="description" />

	<c:choose>
		<c:when test="<%= template.getType().equals(DDMTemplateConstants.TEMPLATE_TYPE_FORM) %>">

			<%
			DDMStructure structure = ddmDisplayContext.fetchStructure(template);

			String portletResourceNamespace = ParamUtil.getString(request, "portletResourceNamespace", renderResponse.getNamespace());

			String script = templateVersion.getScript();

			JSONArray fieldsJSONArray = _getFormTemplateFieldsJSONArray(structure, script);

			String fieldsJSONArrayString = fieldsJSONArray.toString();
			%>

			<%@ include file="/form_builder.jspf" %>
		</c:when>
		<c:otherwise>
			<aui:input cssClass="script" disabled="<%= true %>" name="script" type="textarea" />
		</c:otherwise>
	</c:choose>

	<aui:button-row>
		<aui:button href="<%= backURL.toString() %>" type="cancel" />
	</aui:button-row>
</div>