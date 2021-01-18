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

long layoutPageTemplateCollectionId = ParamUtil.getLong(request, "layoutPageTemplateCollectionId");

LayoutPageTemplateCollection layoutPageTemplateCollection = LayoutPageTemplateCollectionLocalServiceUtil.fetchLayoutPageTemplateCollection(layoutPageTemplateCollectionId);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((layoutPageTemplateCollection != null) ? layoutPageTemplateCollection.getName() : LanguageUtil.get(request, "add-collection"));
%>

<portlet:actionURL name="/layout_page_template_admin/edit_layout_page_template_collection" var="editLayoutPageTemplateCollectionURL">
	<portlet:param name="mvcRenderCommandName" value="/layout_page_template_admin/edit_layout_page_template_collection" />
	<portlet:param name="tabs1" value="page-templates" />
</portlet:actionURL>

<liferay-frontend:edit-form
	action="<%= editLayoutPageTemplateCollectionURL %>"
	cssClass="container-form-lg"
	name="fm"
>
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="layoutPageTemplateCollectionId" type="hidden" value="<%= layoutPageTemplateCollectionId %>" />

	<liferay-frontend:edit-form-body>
		<liferay-ui:error exception="<%= DuplicateLayoutPageTemplateCollectionException.class %>" message="please-enter-a-unique-name" />
		<liferay-ui:error exception="<%= LayoutPageTemplateCollectionNameException.class %>" message="please-enter-a-valid-name" />

		<aui:model-context bean="<%= layoutPageTemplateCollection %>" model="<%= LayoutPageTemplateCollection.class %>" />

		<liferay-frontend:fieldset-group>
			<liferay-frontend:fieldset>
				<aui:input autoFocus="<%= true %>" label="name" name="name" placeholder="name" />

				<aui:input name="description" placeholder="description" />
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>