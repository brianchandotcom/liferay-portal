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
Group group = layoutsAdminDisplayContext.getGroup();

Layout selLayout = layoutsAdminDisplayContext.getSelLayout();

LayoutType selLayoutType = selLayout.getLayoutType();

Locale defaultLocale = LocaleUtil.getDefault();

String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="basic-info"
/>

<aui:model-context bean="<%= selLayout %>" model="<%= Layout.class %>" />

<c:choose>
	<c:when test="<%= !group.isLayoutPrototype() %>">
		<c:if test="<%= !layoutsAdminDisplayContext.isDraft() && !selLayout.isSystem() %>">
			<aui:input ignoreRequestValue="<%= SessionErrors.isEmpty(liferayPortletRequest) %>" label="name" localized="<%= true %>" name="nameMapAsXML" required="<%= true %>" type="text" value="<%= layoutsAdminDisplayContext.getNameMapAsXML() %>" />

			<aui:input aria-describedby='<%= liferayPortletResponse.getNamespace() + "hiddenDescription" %>' label="hidden-from-menu-display" labelCssClass="font-weight-normal" name="hidden" type="checkbox" value="<%= selLayout.isHidden() %>" wrapperCssClass="mb-2" />

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
	</c:when>
	<c:otherwise>
		<aui:input name='<%= "nameMapAsXML_" + defaultLanguageId %>' type="hidden" value="<%= selLayout.getName(defaultLocale) %>" />
	</c:otherwise>
</c:choose>