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

String redirect = layoutPageTemplateDisplayContext.getLayoutPageTemplateCollectionRedirect();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(layoutPageTemplateDisplayContext.getLayoutPageTemplateCollectionTitle());
%>

<portlet:actionURL name="/layout/edit_layout_page_template_collection" var="editLayoutPageTemplateCollectionURL">
	<portlet:param name="mvcPath" value="/edit_layout_page_template_collection.jsp" />
	<portlet:param name="tabs1" value="page-templates" />
</portlet:actionURL>

<aui:form action="<%= editLayoutPageTemplateCollectionURL %>" cssClass="container-fluid-1280" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="layoutPageTemplateCollectionId" type="hidden" value="<%= layoutPageTemplateDisplayContext.getLayoutPageTemplateCollectionId() %>" />

	<liferay-ui:error exception="<%= DuplicateLayoutPageTemplateCollectionException.class %>" message="please-enter-a-unique-name" />
	<liferay-ui:error exception="<%= LayoutPageTemplateCollectionNameException.class %>" message="please-enter-a-valid-name" />

	<aui:model-context bean="<%= layoutPageTemplateDisplayContext.getLayoutPageTemplateCollection() %>" model="<%= LayoutPageTemplateCollection.class %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<aui:input autoFocus="<%= true %>" label="name" name="name" placeholder="name" />

			<aui:input name="description" placeholder="description" />
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>