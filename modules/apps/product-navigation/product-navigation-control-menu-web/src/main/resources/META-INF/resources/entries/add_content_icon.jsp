<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String portletNamespace = PortalUtil.getPortletNamespace(ProductNavigationControlMenuPortletKeys.PRODUCT_NAVIGATION_CONTROL_MENU);
%>

<li class="control-menu-nav-item">
	<clay:button
		aria-label='<%= LanguageUtil.get(request, "add") %>'
		cssClass="lfr-portal-tooltip product-menu-toggle sidenav-toggler"
		data-content="body"
		data-open-class="open-admin-panel"
		data-qa-id="add"
		data-target='<%= "#" + portletNamespace + "addPanelId" %>'
		data-title='<%= LanguageUtil.get(request, "add") %>'
		data-toggle="liferay-sidenav"
		data-type="fixed-push"
		data-type-mobile="fixed"
		data-url='<%=
			PortletURLBuilder.create(
				PortletURLFactoryUtil.create(request, ProductNavigationControlMenuPortletKeys.PRODUCT_NAVIGATION_CONTROL_MENU, PortletRequest.RESOURCE_PHASE)
			).setMVCPath(
				"/add_panel.jsp"
			).setParameter(
				"stateMaximized", themeDisplay.isStateMaximized()
			).setWindowState(
				LiferayWindowState.EXCLUSIVE
			).buildString()
		%>'
		displayType="unstyled"
		icon="plus"
		id='<%= portletNamespace + "addToggleId" %>'
		small="<%= true %>"
	/>
</li>

<%
AssetRenderer<?> assetRenderer = null;

String portletResourceNamespace = PortalUtil.getPortletNamespace(ParamUtil.getString(request, "portletResource"));

String className = ParamUtil.getString(request, portletResourceNamespace + "className");
long classPK = ParamUtil.getLong(request, portletResourceNamespace + "classPK");

String portletId = PortletProviderUtil.getPortletId(className, PortletProvider.Action.ADD);

if (Validator.isNotNull(className) && (classPK > 0)) {
	AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(className);

	assetRenderer = assetRendererFactory.getAssetRenderer(classPK);
}
%>

<c:if test="<%= (assetRenderer != null) && PortletPermissionUtil.contains(permissionChecker, layout, portletId, ActionKeys.ADD_TO_PAGE) %>">
	<aui:script>
		Liferay.once('updatedLayout', () => {
			Liferay.Util.navigate(
				'<%= PortalUtil.getLayoutFullURL(layout, themeDisplay) %>'
			);
		});
	</aui:script>
</c:if>