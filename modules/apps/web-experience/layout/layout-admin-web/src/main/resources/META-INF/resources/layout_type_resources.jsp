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
String id = ParamUtil.getString(request, "id", "portlet");
long prototypeId = ParamUtil.getLong(request, "prototypeId");
String type = ParamUtil.getString(request, "type", "portlet");

Layout selLayout = layoutsAdminDisplayContext.getSelLayout();

Theme selTheme = null;

if (layout.isTypeControlPanel()) {
	if (layoutsAdminDisplayContext.getSelPlid() != 0) {
		selLayout = LayoutLocalServiceUtil.getLayout(layoutsAdminDisplayContext.getSelPlid());

		selTheme = selLayout.getTheme();
	}
	else {
		LayoutSet selLayoutSet = layoutsAdminDisplayContext.getSelLayoutSet();

		selTheme = selLayoutSet.getTheme();
	}
}
else {
	selLayout = layout;

	selTheme = selLayout.getTheme();
}

String layoutTemplateId = PropsValues.DEFAULT_LAYOUT_TEMPLATE_ID;

if (selLayout != null) {
	LayoutTypePortlet curLayoutTypePortlet = (LayoutTypePortlet)selLayout.getLayoutType();

	layoutTemplateId = curLayoutTypePortlet.getLayoutTemplateId();
}
%>

<c:choose>
	<c:when test='<%= Objects.equals(id, "portlet") %>'>
		<div class="layout-type">
			<p class="small text-muted">
				<liferay-ui:message key="empty-page-description" />
			</p>

			<liferay-ui:layout-templates-list
				layoutTemplateId="<%= layoutTemplateId %>"
				layoutTemplateIdPrefix="addLayout"
				layoutTemplates="<%= LayoutTemplateLocalServiceUtil.getLayoutTemplates(selTheme.getThemeId()) %>"
			/>
		</div>
	</c:when>
	<c:when test='<%= Objects.equals(id, "copy") %>'>
		<div class="layout-type">
			<p class="small text-muted">
				<liferay-ui:message key="copy-of-a-page-description" />
			</p>

			<liferay-util:include page="/html/portal/layout/edit/portlet_applications.jsp">
				<liferay-util:param name="copyLayoutIdPrefix" value="addLayout" />
			</liferay-util:include>
		</div>
	</c:when>
	<c:when test='<%= Objects.equals(id, "layout-prototype") %>'>

		<%
		LayoutPrototype layoutPrototype = LayoutPrototypeServiceUtil.fetchLayoutPrototype(prototypeId);
		%>

		<c:if test="<%= layoutPrototype != null %>">
			<div class="layout-type">
				<p class="small text-muted">
					<%= HtmlUtil.escape(layoutPrototype.getDescription(locale)) %>
				</p>

				<aui:input helpMessage="if-enabled-this-page-will-inherit-changes-made-to-the-page-template" id='<%= "addLayoutLayoutPrototypeLinkEnabled" + layoutPrototype.getUuid() %>' label="inherit-changes" name='<%= "layoutPrototypeLinkEnabled" + layoutPrototype.getUuid() %>' type="toggle-switch" value="<%= PropsValues.LAYOUT_PROTOTYPE_LINK_ENABLED_DEFAULT %>" />
			</div>
		</c:if>
	</c:when>
	<c:otherwise>

		<%
		liferayPortletRequest.setAttribute(WebKeys.LAYOUT_DESCRIPTIONS, layoutsAdminDisplayContext.getLayoutDescriptions());

		LayoutTypeController layoutTypeController = LayoutTypeControllerTracker.getLayoutTypeController(type);

		ResourceBundle layoutTypeResourceBundle = ResourceBundleUtil.getBundle("content.Language", locale, layoutTypeController.getClass());
		%>

		<div class="layout-type">
			<p class="small text-muted">
				<%= LanguageUtil.get(request, layoutTypeResourceBundle, "layout.types." + type + ".description") %>
			</p>

			<%= layoutTypeController.includeEditContent(request, response, selLayout) %>
		</div>
	</c:otherwise>
</c:choose>