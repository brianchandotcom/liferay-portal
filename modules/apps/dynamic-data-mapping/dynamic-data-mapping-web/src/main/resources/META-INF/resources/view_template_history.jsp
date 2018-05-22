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

long templateId = ParamUtil.getLong(request, "templateId");

DDMTemplate template = DDMTemplateServiceUtil.getTemplate(templateId);

String title = LanguageUtil.format(request, "x-history", template.getName(locale), false);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/view_template_history.jsp");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("templateId", String.valueOf(templateId));

PortletURL backURL = renderResponse.createRenderURL();

backURL.setParameter("mvcPath", "/edit_template.jsp");
backURL.setParameter("redirect", redirect);
backURL.setParameter("templateId", String.valueOf(templateId));
%>

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
			cssClass="container-fluid-1280"
			title="<%= title %>"
		/>
	</c:otherwise>
</c:choose>

<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm">
	<liferay-ui:search-container
		searchContainer="<%= new TemplateSearch(renderRequest, portletURL) %>"
		total="<%= DDMTemplateVersionServiceUtil.getTemplateVersionsCount(templateId) %>"
	>
		<liferay-ui:search-container-results
			results="<%= DDMTemplateVersionServiceUtil.getTemplateVersions(templateId, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.dynamic.data.mapping.model.DDMTemplateVersion"
			keyProperty="templateVersionId"
			modelVar="templateVersion"
		>
			<portlet:renderURL var="rowURL">
				<portlet:param name="mvcPath" value="/view_template_version.jsp" />
				<portlet:param name="redirect" value="<%= redirect %>" />
				<portlet:param name="templateVersionId" value="<%= String.valueOf(templateVersion.getTemplateVersionId()) %>" />
				<portlet:param name="formBuilderReadOnly" value="<%= Boolean.TRUE.toString() %>" />
			</portlet:renderURL>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="id"
				property="templateVersionId"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="version"
				property="version"
			/>

			<liferay-ui:search-container-column-status
				href="<%= rowURL %>"
				name="status"
				status="<%= templateVersion.getStatus() %>"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="author"
				value="<%= HtmlUtil.escape(PortalUtil.getUserName(templateVersion)) %>"
			/>

			<liferay-ui:search-container-column-jsp
				align="right"
				cssClass="entry-action"
				path="/template_version_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>