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

<%@ include file="/publications/init.jsp" %>

<liferay-portlet:renderURL var="backURL" />

<%
String redirect = ParamUtil.getString(request, "redirect");

CTCollectionTemplate ctCollectionTemplate = (CTCollectionTemplate)request.getAttribute("ctCollectionTemplate");

long ctCollectionTemplateId = 0;
String description = StringPool.BLANK;
String name = StringPool.BLANK;
String saveButtonLabel = "create";

if (ctCollectionTemplate != null) {
	ctCollectionTemplateId = ctCollectionTemplate.getCtCollectionTemplateId();
	description = ctCollectionTemplate.getDescription();
	name = ctCollectionTemplate.getName();
	saveButtonLabel = "save";

	renderResponse.setTitle(StringBundler.concat(LanguageUtil.format(resourceBundle, "edit-x", new Object[] {ctCollectionTemplate.getName()})));
}
else {
	renderResponse.setTitle(LanguageUtil.get(request, "create-a-new-publication-template"));
}

portletDisplay.setURLBack(backURL);
portletDisplay.setShowBackIcon(true);
%>

<clay:container-fluid
	cssClass="container-form-lg edit-publication-template-container"
>
	<liferay-portlet:actionURL name="/change_tracking/edit_template" var="actionURL">
		<liferay-portlet:param name="redirect" value="<%= redirect %>" />
	</liferay-portlet:actionURL>

	<react:component
		module="publications/js/views/PublicationTemplateEditView"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"actionUrl", actionURL
			).put(
				"collaboratorsProps", publicationsDisplayContext.getCollaboratorsReactData(ctCollectionTemplateId)
			).put(
				"ctCollectionTemplateId", ctCollectionTemplateId
			).put(
				"description", description
			).put(
				"name", name
			).put(
				"namespace", liferayPortletResponse.getNamespace()
			).put(
				"publicationDescription", description
			).put(
				"publicationName", name
			).put(
				"redirect", redirect
			).put(
				"saveButtonLabel", LanguageUtil.get(request, saveButtonLabel)
			).build()
		%>'
	/>
</clay:container-fluid>