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
LayoutPageTemplateDisplayContext layoutPageTemplateDisplayContext = (LayoutPageTemplateDisplayContext)request.getAttribute(LayoutAdminWebKeys.LAYOUT_PAGE_TEMPLATE_DISPLAY_CONTEXT);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

LayoutPageTemplateEntry layoutPageTemplateEntry = (LayoutPageTemplateEntry)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= LayoutPageTemplateEntryPermission.contains(permissionChecker, layoutPageTemplateEntry, ActionKeys.UPDATE) %>">
		<liferay-ui:icon
			message="edit"
			url="<%= layoutPageTemplateDisplayContext.getEditLayoutPageTemplateEntryURL(layoutPageTemplateEntry) %>"
		/>
	</c:if>

	<c:if test="<%= LayoutPageTemplateEntryPermission.contains(permissionChecker, layoutPageTemplateEntry, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="configureDisplayPageURL">
			<portlet:param name="mvcRenderCommandName" value="/layout/edit_layout" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="backURL" value="<%= currentURL %>" />
			<portlet:param name="selPlid" value="<%= String.valueOf(layoutPageTemplateEntry.getPlid()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="configure"
			url="<%= configureDisplayPageURL %>"
		/>
	</c:if>

	<c:if test="<%= LayoutPageTemplateEntryPermission.contains(permissionChecker, layoutPageTemplateEntry, ActionKeys.UPDATE) %>">
		<portlet:actionURL name="/layout/update_layout_page_template_entry" var="updateDisplayPageURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="layoutPageTemplateCollectionId" value="<%= String.valueOf(layoutPageTemplateEntry.getLayoutPageTemplateCollectionId()) %>" />
			<portlet:param name="layoutPageTemplateEntryId" value="<%= String.valueOf(layoutPageTemplateEntry.getLayoutPageTemplateEntryId()) %>" />
		</portlet:actionURL>

		<%
		Map<String, Object> updateDisplayPageData = new HashMap<String, Object>();

		updateDisplayPageData.put("form-submit-url", updateDisplayPageURL.toString());
		updateDisplayPageData.put("id-field-value", layoutPageTemplateEntry.getLayoutPageTemplateEntryId());
		updateDisplayPageData.put("main-field-value", layoutPageTemplateEntry.getName());
		%>

		<liferay-ui:icon
			cssClass='<%= renderResponse.getNamespace() + "update-display-page-action-option" %>'
			data="<%= updateDisplayPageData %>"
			message="rename"
			url="javascript:;"
		/>
	</c:if>

	<c:if test="<%= LayoutPageTemplateEntryPermission.contains(permissionChecker, layoutPageTemplateEntry, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= LayoutPageTemplateEntry.class.getName() %>"
			modelResourceDescription="<%= layoutPageTemplateEntry.getName() %>"
			resourcePrimKey="<%= String.valueOf(layoutPageTemplateEntry.getLayoutPageTemplateEntryId()) %>"
			var="displayPagePermissionsURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			message="permissions"
			method="get"
			url="<%= displayPagePermissionsURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= layoutPageTemplateEntry.isApproved() && Objects.equals(layoutPageTemplateEntry.getType(), LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE) && (layoutPageTemplateEntry.getClassNameId() > 0) && LayoutPageTemplateEntryPermission.contains(permissionChecker, layoutPageTemplateEntry, ActionKeys.UPDATE) %>">
		<portlet:actionURL name="/layout/edit_layout_page_template_settings" var="editLayoutPageTemplateSettingsURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="layoutPageTemplateEntryId" value="<%= String.valueOf(layoutPageTemplateEntry.getLayoutPageTemplateEntryId()) %>" />
			<portlet:param name="defaultTemplate" value="<%= layoutPageTemplateEntry.getDefaultTemplate() ? Boolean.FALSE.toString() : Boolean.TRUE.toString() %>" />
		</portlet:actionURL>

		<%
		String taglibOnClickPrimary = "submitForm(document.hrefFm, '" + editLayoutPageTemplateSettingsURL + "');";

		LayoutPageTemplateEntry defaultLayoutPageTemplateEntry = LayoutPageTemplateEntryServiceUtil.fetchDefaultLayoutPageTemplateEntry(layoutPageTemplateEntry.getGroupId(), layoutPageTemplateEntry.getClassNameId(), layoutPageTemplateEntry.getClassTypeId());

		if ((defaultLayoutPageTemplateEntry != null) && (defaultLayoutPageTemplateEntry.getLayoutPageTemplateEntryId() != layoutPageTemplateEntry.getLayoutPageTemplateEntryId())) {
			taglibOnClickPrimary = "if (confirm('" + UnicodeLanguageUtil.format(request, "do-you-want-to-replace-x-for-x-as-the-default-display-page", new String[] {layoutPageTemplateEntry.getName(), defaultLayoutPageTemplateEntry.getName()}) + "')) { submitForm(document.hrefFm, '" + editLayoutPageTemplateSettingsURL + "'); } ";
		}
		else if (layoutPageTemplateEntry.getDefaultTemplate()) {
			taglibOnClickPrimary = "if (confirm('" + LanguageUtil.get(request, "unmark-default-confirmation") + "')) { submitForm(document.hrefFm, '" + editLayoutPageTemplateSettingsURL + "'); } ";
		}
		%>

		<liferay-ui:icon
			icon='<%= layoutPageTemplateEntry.getDefaultTemplate() ? "check" : StringPool.BLANK %>'
			iconCssClass="pull-right"
			markupView="lexicon"
			message="mark-as-default"
			onClick="<%= taglibOnClickPrimary %>"
			url="javascript:;"
		/>
	</c:if>

	<c:if test="<%= LayoutPageTemplateEntryPermission.contains(permissionChecker, layoutPageTemplateEntry, ActionKeys.DELETE) %>">
		<portlet:actionURL name="/layout/delete_layout_page_template_entry" var="deleteDisplayPageURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="layoutPageTemplateEntryId" value="<%= String.valueOf(layoutPageTemplateEntry.getLayoutPageTemplateEntryId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteDisplayPageURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>