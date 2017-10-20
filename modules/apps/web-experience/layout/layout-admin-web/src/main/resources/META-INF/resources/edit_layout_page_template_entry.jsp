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
LayoutPageTemplateDisplayContext layoutPageTemplateDisplayContext = new LayoutPageTemplateDisplayContext(renderRequest, renderResponse, request);

String redirect = layoutPageTemplateDisplayContext.getEditLayoutPageTemplateEntryRedirect();

LayoutPageTemplateEntry layoutPageTemplateEntry = layoutPageTemplateDisplayContext.getLayoutPageTemplateEntry();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(layoutPageTemplateDisplayContext.getLayoutPageTemplateEntryTitle());
%>

<portlet:actionURL name="/layout/edit_layout_page_template_entry" var="editLayoutPageTemplateEntryURL">
	<portlet:param name="mvcPath" value="/edit_layout_page_template_entry.jsp" />
</portlet:actionURL>

<aui:form action="<%= editLayoutPageTemplateEntryURL %>" cssClass="container-fluid-1280" enctype="multipart/form-data" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="layoutPageTemplateEntryId" type="hidden" value="<%= layoutPageTemplateDisplayContext.getLayoutPageTemplateEntryId() %>" />
	<aui:input name="layoutPageTemplateCollectionId" type="hidden" value="<%= layoutPageTemplateDisplayContext.getLayoutPageTemplateCollectionId() %>" />

	<aui:model-context bean="<%= layoutPageTemplateEntry %>" model="<%= LayoutPageTemplateEntry.class %>" />

	<aui:input autoFocus="<%= true %>" name="name" placeholder="name" />

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>