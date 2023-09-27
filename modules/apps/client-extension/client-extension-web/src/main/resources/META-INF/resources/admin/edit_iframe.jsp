<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/admin/init.jsp" %>

<%
EditClientExtensionEntryDisplayContext<IFrameCET> editClientExtensionEntryDisplayContext = (EditClientExtensionEntryDisplayContext)renderRequest.getAttribute(ClientExtensionAdminWebKeys.EDIT_CLIENT_EXTENSION_ENTRY_DISPLAY_CONTEXT);

IFrameCET iFrameCET = editClientExtensionEntryDisplayContext.getCET();
%>

<aui:field-wrapper cssClass="form-group">
	<aui:input label="url" name="url" required="<%= true %>" type="text" value="<%= iFrameCET.getURL() %>" />

	<div class="form-text">
		<liferay-ui:message key="specify-the-url-that-will-be-rendered-in-the-iframe" />
	</div>
</aui:field-wrapper>

<c:choose>
	<c:when test="<%= editClientExtensionEntryDisplayContext.isNew() %>">
		<aui:input label="instanceable" name="instanceable" type="checkbox" value="<%= iFrameCET.isInstanceable() %>" />
	</c:when>
	<c:otherwise>
		<aui:input disabled="<%= true %>" label="instanceable" name="instanceable-disabled" type="checkbox" value="<%= iFrameCET.isInstanceable() %>" />

		<aui:input name="instanceable" type="hidden" value="<%= iFrameCET.isInstanceable() %>" />
	</c:otherwise>
</c:choose>

<aui:field-wrapper cssClass="form-group">
	<aui:input label="friendly-url-mapping" name="friendlyURLMapping" type="text" value="<%= iFrameCET.getFriendlyURLMapping() %>" />

	<div class="form-text">
		<liferay-ui:message key="define-the-widgets-friendly-url-mapping-so-you-can-refer-to-it-using-a-more-user-readable-url" />
	</div>
</aui:field-wrapper>

<clay:select
	label="widget-category-name"
	name="portletCategoryName"
	options="<%= editClientExtensionEntryDisplayContext.getPortletCategoryNameSelectOptions(iFrameCET.getPortletCategoryName()) %>"
/>