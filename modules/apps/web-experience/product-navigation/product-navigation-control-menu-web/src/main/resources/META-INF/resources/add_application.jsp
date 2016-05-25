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
PortletURL refererURL = renderResponse.createActionURL();

refererURL.setParameter("updateLayout", "true");
%>

<aui:form action='<%= themeDisplay.getPathMain() + "/portal/update_layout?p_auth=" + AuthTokenUtil.getToken(request) + "&p_l_id=" + plid + "&p_v_l_s_g_id=" + themeDisplay.getSiteGroupId() %>' method="post" name="addApplicationForm">
	<aui:input name="doAsUserId" type="hidden" value="<%= themeDisplay.getDoAsUserId() %>" />
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="template" />
	<aui:input name="<%= WebKeys.REFERER %>" type="hidden" value="<%= refererURL.toString() %>" />
	<aui:input name="refresh" type="hidden" value="<%= true %>" />

	<div id="<portlet:namespace />applicationList">
		<c:if test="<%= layout.isTypePortlet() %>">
			<div class="input-group search-bar">
				<input class="form-control" id="<portlet:namespace />searchApplication" name="<portlet:namespace />searchApplication" placeholder="<%= LanguageUtil.get(request, "search") + StringPool.TRIPLE_PERIOD %>" type="text" />

				<span class="input-group-btn">
					<liferay-ui:icon icon="search" markupView="lexicon" />
				</span>
			</div>
		</c:if>

		<%
		int portletCategoryIndex = 0;

		PortletCategory portletCategory = (PortletCategory)WebAppPool.get(company.getCompanyId(), WebKeys.PORTLET_CATEGORY);

		portletCategory = PortletCategoryUtil.getRelevantPortletCategory(permissionChecker, user.getCompanyId(), layout, portletCategory, layoutTypePortlet);

		List<PortletCategory> categories = ListUtil.fromCollection(portletCategory.getCategories());

		categories = ListUtil.sort(categories, new PortletCategoryComparator(locale));

		for (PortletCategory curPortletCategory : categories) {
			if (curPortletCategory.isHidden()) {
				continue;
			}

			request.setAttribute(WebKeys.PORTLET_CATEGORY, curPortletCategory);
			request.setAttribute(WebKeys.PORTLET_CATEGORY_INDEX, String.valueOf(portletCategoryIndex));
		%>

			<liferay-util:include page="/view_category.jsp" servletContext="<%= application %>" />

		<%
			portletCategoryIndex++;
		}
		%>

		<c:if test="<%= layout.isTypePortlet() %>">
			<div class="add-portlet-message">
				<h4><liferay-ui:message key="adding-applications" /></h4>

				<h6 class="info-message"><liferay-ui:message key="to-add-a-portlet-to-the-page-just-drag-it" /></h6>

				<h6><aui:icon image="live" label="can-be-added-once" markupView="lexicon" /></h6>

				<h6><aui:icon image="grid" label="can-be-added-several-times" markupView="lexicon" /></h6>
			</div>
		</c:if>

		<c:if test="<%= !layout.isTypePanel() && permissionChecker.isOmniadmin() && PortletLocalServiceUtil.hasPortlet(themeDisplay.getCompanyId(), PortletKeys.MARKETPLACE_STORE) %>">

			<%
			PortletURL marketplaceURL = PortalUtil.getControlPanelPortletURL(request, PortletKeys.MARKETPLACE_STORE, PortletRequest.RENDER_PHASE);
			%>

			<div class="button-holder">
				<aui:button href="<%= marketplaceURL.toString() %>" primary="<%= true %>" value="install-more-applications" />
			</div>
		</c:if>
	</div>
</aui:form>

<aui:script use="liferay-product-navigation-control-menu-add-application">
	var ControlMenu = Liferay.ControlMenu;

	var addApplicationCollapse = A.one('#<portlet:namespace />addApplicationCollapse');
	var searchApplication = A.one('#<portlet:namespace />searchApplication');

	if (addApplicationCollapse && searchApplication) {
		var addApplication = new ControlMenu.AddApplication(
			{
				focusItem: searchApplication,
				inputNode: searchApplication,
				namespace: '<portlet:namespace />',
				nodeList: A.one('#<portlet:namespace />applicationList'),
				nodeSelector: '.drag-content-item',
				panelBody: addApplicationCollapse
			}
		);

		if (ControlMenu.PortletDragDrop) {
			addApplication.plug(
				ControlMenu.PortletDragDrop,
				{
					on: {
						dragEnd: function(event) {
							addApplication.addPortlet(
								event.portletNode,
								{
									item: event.appendNode
								}
							);
						}
					},
					srcNode: '#<portlet:namespace />applicationList'
				}
			);
		}

		Liferay.component('<portlet:namespace />addApplication', addApplication);
	}
</aui:script>