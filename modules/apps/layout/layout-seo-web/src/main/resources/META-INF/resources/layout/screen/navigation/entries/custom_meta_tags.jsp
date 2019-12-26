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
LayoutsSEODisplayContext layoutsSEODisplayContext = (LayoutsSEODisplayContext)request.getAttribute(LayoutSEOWebKeys.LAYOUT_PAGE_LAYOUT_SEO_DISPLAY_CONTEXT);

String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL", redirect);

if (Validator.isNull(backURL)) {
	backURL = PortalUtil.getLayoutFullURL(layoutsSEODisplayContext.getSelLayout(), themeDisplay);
}
%>

<portlet:actionURL name="/layout/edit_open_graph" var="editOpenGraphURL" />

<aui:form action="<%= editOpenGraphURL %>" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="portletResource" type="hidden" value='<%= ParamUtil.getString(request, "portletResource") %>' />
	<aui:input name="groupId" type="hidden" value="<%= layoutsSEODisplayContext.getGroupId() %>" />
	<aui:input name="privateLayout" type="hidden" value="<%= layoutsSEODisplayContext.isPrivateLayout() %>" />
	<aui:input name="layoutId" type="hidden" value="<%= layoutsSEODisplayContext.getLayoutId() %>" />

	<div class="sheet sheet-lg">
		<div class="sheet-header">
			<h2 class="sheet-title"><liferay-ui:message key="custom-meta-tags" /></h2>

			<p class="text-muted">
				<liferay-ui:message key="custom-meta-tags-description" />
			</p>
		</div>

		<liferay-ddm:html
			classNameId="<%= PortalUtil.getClassNameId(com.liferay.dynamic.data.mapping.model.DDMStructure.class) %>"
			classPK="<%= layoutsSEODisplayContext.getDDMStructurePrimaryKey() %>"
			ddmFormValues="<%= layoutsSEODisplayContext.getDDMFormValues() %>"
			defaultEditLocale="<%= PortalUtil.getSiteDefaultLocale(layoutsSEODisplayContext.getGroupId()) %>"
			defaultLocale="<%= PortalUtil.getSiteDefaultLocale(layoutsSEODisplayContext.getGroupId()) %>"
			fieldsNamespace="<%= String.valueOf(layoutsSEODisplayContext.getDDMStructurePrimaryKey()) %>"
			groupId="<%= layoutsSEODisplayContext.getGroupId() %>"
			localizable="<%= true %>"
			requestedLocale="<%= locale %>"
		/>

		<div class="sheet-footer">
			<aui:button primary="<%= true %>" type="submit" />

			<aui:button href="<%= backURL %>" type="cancel" />
		</div>
	</div>
</aui:form>