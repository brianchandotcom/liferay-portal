<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/dockbar/init.jsp" %>

<%
PortletURL refererURL = renderResponse.createActionURL();

refererURL.setParameter("updateLayout", "true");
%>

<aui:form action='<%= themeDisplay.getPathMain() + "/portal/update_layout?p_auth=" + AuthTokenUtil.getToken(request) + "&p_l_id=" + plid + "&p_v_l_s_g_id=" + themeDisplay.getSiteGroupId() %>' method="post" name="addApplicationForm">
	<aui:input name="doAsUserId" type="hidden" value="<%= themeDisplay.getDoAsUserId() %>" />
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="template" />
	<aui:input name="<%= WebKeys.REFERER %>" type="hidden" value="<%= refererURL.toString() %>" />
	<aui:input name="refresh" type="hidden" value="<%= true %>" />

	<div class="row-fluid">
		<c:if test="<%= layout.isTypePortlet() %>">
			<div class="search-panel btn-toolbar">
				<aui:input cssClass="search-query span12" label="" name="searchApplication" type="text"  />
			</div>
		</c:if>

		<%
		int portletCategoryIndex = 0;

		List<Portlet> portlets = new ArrayList<Portlet>();

		for (String portletId : PropsValues.DOCKBAR_ADD_PORTLETS) {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(portletId);

			if ((portlet != null) && portlet.isInclude() && portlet.isActive() && PortletPermissionUtil.contains(permissionChecker, layout, portlet, ActionKeys.ADD_TO_PAGE)) {
				portlets.add(portlet);
			}
		}
		%>

		<c:if test="<%= portlets.size() > 0 %>">

			<%
			String panelId = renderResponse.getNamespace() + "portletCategory" + portletCategoryIndex;
			%>

			<div class="lfr-add-content">
				<liferay-ui:panel collapsible="<%= layout.isTypePortlet() %>" cssClass="lfr-content-category lfr-component panel-page-category" extended="<%= true %>" id="<%= panelId %>" persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, "highlighted") %>'>

					<aui:nav cssClass="nav-list">

						<%
						for (Portlet portlet : portlets) {
							if (!PortletPermissionUtil.contains(permissionChecker, layout, portlet.getPortletId(), ActionKeys.ADD_TO_PAGE)) {
								continue;
							}

							boolean portletInstanceable = portlet.isInstanceable();

							boolean portletUsed = layoutTypePortlet.hasPortletId(portlet.getPortletId());

							boolean portletLocked = (!portletInstanceable && portletUsed);

							Map<String, Object> data = new HashMap<String, Object>();

							data.put("draggable", "true");
							data.put("id", renderResponse.getNamespace() + "portletItem" + portlet.getPortletId());
							data.put("instanceable", portletInstanceable);
							data.put("plid", plid);
							data.put("portlet-id", portlet.getPortletId());
							data.put("title", PortalUtil.getPortletTitle(portlet, application, locale));

							String cssClass = "lfr-content-item";

							if (portletLocked) {
								cssClass += " lfr-portlet-used";
							}
						%>

						<aui:nav-item cssClass='<%= cssClass %>'
							data='<%= data %>'
							href=""
							iconClass='<%= portletInstanceable ? "icon-th-large" : "icon-stop" %>'
							label="<%= PortalUtil.getPortletTitle(portlet, application, locale) %>">

							<span <%= AUIUtil.buildData(data) %> class='add-content-item <%= portletLocked ? "lfr-portlet-used" : StringPool.BLANK %>'>
								<liferay-ui:message key="add" />
							</span>
						</aui:nav-item>

						<%
						}
						%>

					</aui:nav>

				</liferay-ui:panel>
			</div>

			<%
			portletCategoryIndex++;
			%>

		</c:if>

		<%
		UnicodeProperties typeSettingsProperties = layout.getTypeSettingsProperties();

		Set panelSelectedPortlets = SetUtil.fromArray(StringUtil.split(typeSettingsProperties.getProperty("panelSelectedPortlets")));

		PortletCategory portletCategory = (PortletCategory)WebAppPool.get(company.getCompanyId(), WebKeys.PORTLET_CATEGORY);

		portletCategory = _getRelevantPortletCategory(permissionChecker, portletCategory, panelSelectedPortlets, layoutTypePortlet, layout, user);

		List<PortletCategory> categories = ListUtil.fromCollection(portletCategory.getCategories());

		categories = ListUtil.sort(categories, new PortletCategoryComparator(locale));

		for (PortletCategory curPortletCategory : categories) {
			if (curPortletCategory.isHidden()) {
				continue;
			}

			request.setAttribute(WebKeys.PORTLET_CATEGORY, curPortletCategory);
			request.setAttribute(WebKeys.PORTLET_CATEGORY_INDEX, String.valueOf(portletCategoryIndex));
		%>

		<liferay-util:include page="/html/portlet/dockbar/view_category.jsp" />

		<%
			portletCategoryIndex++;
		}
		%>

		<c:if test="<%= layout.isTypePortlet() %>">
			<div class="alert alert-info">
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
	</div>
</aui:form>

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