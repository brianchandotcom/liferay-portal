<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
Group group = layoutsAdminDisplayContext.getGroup();

Layout selLayout = layoutsAdminDisplayContext.getSelLayout();

LayoutType selLayoutType = selLayout.getLayoutType();
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="basic-info"
/>

<aui:model-context bean="<%= selLayout %>" model="<%= Layout.class %>" />

<c:if test="<%= !layoutsAdminDisplayContext.isDraft() && !selLayout.isSystem() %>">
	<aui:input ignoreRequestValue="<%= SessionErrors.isEmpty(liferayPortletRequest) %>" label="name" localized="<%= true %>" name="nameMapAsXML" required="<%= true %>" type="text" value="<%= layoutsAdminDisplayContext.getNameMapAsXML() %>" />

	<aui:input aria-describedby='<%= liferayPortletResponse.getNamespace() + "hiddenDescription" %>' label="hidden-from-menu-display" labelCssClass="font-weight-normal" name="hidden" type="checkbox" value="<%= selLayout.isHidden() %>" wrapperCssClass="c-mb-2" />

	<p class="text-3 text-secondary" id="<portlet:namespace />hiddenDescription">
		<liferay-ui:message key="hidden-from-navigation-menu-widget-help-message" />
	</p>
</c:if>

<c:if test="<%= group.isLayoutSetPrototype() %>">

	<%
	LayoutSetPrototype layoutSetPrototype = LayoutSetPrototypeLocalServiceUtil.fetchLayoutSetPrototype(group.getClassPK());
	%>

	<c:if test='<%= (layoutSetPrototype != null) && GetterUtil.getBoolean(layoutSetPrototype.getSettingsProperty("layoutsUpdateable"), true) %>'>
		<aui:input helpMessage="allow-site-administrators-to-modify-this-page-for-their-site-help" label="allow-site-administrators-to-modify-this-page-for-their-site" name="TypeSettingsProperties--layoutUpdateable--" type="checkbox" value='<%= GetterUtil.getBoolean(selLayoutType.getTypeSettingsProperty("layoutUpdateable"), true) %>' />
	</c:if>
</c:if>