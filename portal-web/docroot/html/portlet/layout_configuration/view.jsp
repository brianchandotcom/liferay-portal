<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/layout_configuration/init.jsp" %>

<c:if test="<%= themeDisplay.isSignedIn() && (layout != null) && (layout.isTypePortlet() || layout.isTypePanel()) %>">

	<%
	PortletURL refererURL = renderResponse.createActionURL();

	refererURL.setParameter("updateLayout", "true");
	%>

	<div id="portal_add_content">
		<div class="portal-add-content">
			<aui:form action='<%= themeDisplay.getPathMain() + "/portal/update_layout?p_auth=" + AuthTokenUtil.getToken(request) + "&p_l_id=" + plid + "&p_v_l_s_g_id=" + themeDisplay.getParentGroupId() %>' method="post" name="fm" useNamespace="<%= false %>">
				<aui:input name="doAsUserId" type="hidden" value="<%= themeDisplay.getDoAsUserId() %>" />
				<aui:input name="<%= Constants.CMD %>" type="hidden" value="template" />
				<aui:input name="<%= WebKeys.REFERER %>" type="hidden" value="<%= refererURL.toString() %>" />
				<aui:input name="refresh" type="hidden" value="<%= true %>" />

				<c:if test="<%= layout.isTypePortlet() %>">
					<div class="portal-add-content-search" id="portal_add_content_title">
						<aui:input cssClass="lfr-add-content-search lfr-auto-focus" id="layout_configuration_content" label="search-applications-searches-as-you-type" name="layout_configuration_content" onKeyPress="if (event.keyCode == 13) { return false; }" />
					</div>
				</c:if>

				<%
				UnicodeProperties typeSettingsProperties = layout.getTypeSettingsProperties();

				Set panelSelectedPortlets = SetUtil.fromArray(StringUtil.split(typeSettingsProperties.getProperty("panelSelectedPortlets")));

				PortletCategory portletCategory = (PortletCategory)WebAppPool.get(company.getCompanyId(), WebKeys.PORTLET_CATEGORY);

				portletCategory = _getRelevantPortletCategory(permissionChecker, portletCategory, panelSelectedPortlets, layoutTypePortlet, layout, user);

				List<PortletCategory> categories = ListUtil.fromCollection(portletCategory.getCategories());

				categories = ListUtil.sort(categories, new PortletCategoryComparator(locale));

				int portletCategoryIndex = 0;

				for (PortletCategory curPortletCategory : categories) {
					if (curPortletCategory.isHidden()) {
						continue;
					}

					request.setAttribute(WebKeys.PORTLET_CATEGORY, curPortletCategory);
					request.setAttribute(WebKeys.PORTLET_CATEGORY_INDEX, String.valueOf(portletCategoryIndex));
				%>

					<liferay-util:include page="/html/portlet/layout_configuration/view_category.jsp" />

				<%
					portletCategoryIndex++;
				}
				%>

				<c:if test="<%= layout.isTypePortlet() %>">
					<div class="portlet-msg-info">
						<liferay-ui:message key="to-add-a-portlet-to-the-page-just-drag-it" />
					</div>
				</c:if>

				<c:if test="<%= !layout.isTypePanel() && permissionChecker.isOmniadmin() && PortletLocalServiceUtil.hasPortlet(themeDisplay.getCompanyId(), PortletKeys.MARKETPLACE_STORE) %>">

					<%
					long controlPanelPlid = PortalUtil.getControlPanelPlid(company.getCompanyId());

					PortletURLImpl marketplaceURL = new PortletURLImpl(request, PortletKeys.MARKETPLACE_STORE, controlPanelPlid, PortletRequest.RENDER_PHASE);
					%>

					<p class="lfr-install-more">
						<aui:a href='<%= HttpUtil.removeParameter(marketplaceURL.toString(), "controlPanelCategory") %>' label="install-more-applications" />
					</p>
				</c:if>
			</aui:form>
		</div>
	</div>
</c:if>

<c:if test="<%= !themeDisplay.isSignedIn() %>">
	<liferay-ui:message key="please-sign-in-to-continue" />
</c:if>

<%!
private static PortletCategory _getRelevantPortletCategory(PermissionChecker permissionChecker, PortletCategory portletCategory, Set panelSelectedPortlets, LayoutTypePortlet layoutTypePortlet, Layout layout, User user) throws Exception {
	PortletCategory relevantPortletCategory = new PortletCategory(portletCategory.getName(), portletCategory.getPortletIds());

	for (PortletCategory curPortletCategory : portletCategory.getCategories()) {
		Set<String> portletIds = new HashSet<String>();

		if (curPortletCategory.isHidden()) {
			continue;
		}

		for (String portletId : curPortletCategory.getPortletIds()) {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(user.getCompanyId(), portletId);

			if (portlet != null) {
				if (portlet.isSystem()) {
				}
				else if (!portlet.isActive() || portlet.isUndeployedPortlet()) {
				}
				else if (layout.isTypePanel() && panelSelectedPortlets.contains(portlet.getRootPortletId())) {
					portletIds.add(portlet.getPortletId());
				}
				else if (layout.isTypePanel() && !panelSelectedPortlets.contains(portlet.getRootPortletId())) {
				}
				else if (!PortletPermissionUtil.contains(permissionChecker, layout, portlet, ActionKeys.ADD_TO_PAGE)) {
				}
				else if (!portlet.isInstanceable() && layoutTypePortlet.hasPortletId(portlet.getPortletId())) {
					portletIds.add(portlet.getPortletId());
				}
				else {
					portletIds.add(portlet.getPortletId());
				}
			}
		}

		PortletCategory curRelevantPortletCategory = _getRelevantPortletCategory(permissionChecker, curPortletCategory, panelSelectedPortlets, layoutTypePortlet, layout, user);

		curRelevantPortletCategory.setPortletIds(portletIds);

		if (!curRelevantPortletCategory.getCategories().isEmpty() || !portletIds.isEmpty()) {
			relevantPortletCategory.addCategory(curRelevantPortletCategory);
		}
	}

	return relevantPortletCategory;
}
%>