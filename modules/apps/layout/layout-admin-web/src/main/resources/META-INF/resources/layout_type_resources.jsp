<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			<p class="small text-secondary">
				<liferay-ui:message key="empty-page-description" />
			</p>

			<liferay-layout:layout-templates-list
				layoutTemplateId="<%= layoutTemplateId %>"
				layoutTemplateIdPrefix="addLayout"
				layoutTemplates="<%= LayoutTemplateLocalServiceUtil.getLayoutTemplates(selTheme.getThemeId()) %>"
			/>
		</div>
	</c:when>
	<c:when test='<%= Objects.equals(id, "layout-prototype") %>'>

		<%
		LayoutPrototype layoutPrototype = LayoutPrototypeServiceUtil.fetchLayoutPrototype(prototypeId);
		%>

		<c:if test="<%= layoutPrototype != null %>">
			<div class="layout-type">
				<p class="small text-secondary">
					<%= HtmlUtil.escape(layoutPrototype.getDescription(locale)) %>
				</p>

				<aui:input helpMessage="if-enabled-this-page-will-inherit-changes-made-to-the-page-template" id='<%= "addLayoutLayoutPrototypeLinkEnabled" + layoutPrototype.getUuid() %>' inlineLabel="right" label="inherit-changes" labelCssClass="simple-toggle-switch" name='<%= "layoutPrototypeLinkEnabled" + layoutPrototype.getUuid() %>' type="toggle-switch" value="<%= PropsValues.LAYOUT_PROTOTYPE_LINK_ENABLED_DEFAULT %>" />
			</div>
		</c:if>
	</c:when>
	<c:otherwise>

		<%
		LayoutTypeController layoutTypeController = LayoutTypeControllerTracker.getLayoutTypeController(type);
		%>

		<div class="layout-type">
			<p class="small text-secondary">
				<liferay-ui:message key='<%= "layout.types." + type + ".description" %>' />
			</p>

			<%= layoutTypeController.includeEditContent(request, response, selLayout) %>
		</div>
	</c:otherwise>
</c:choose>