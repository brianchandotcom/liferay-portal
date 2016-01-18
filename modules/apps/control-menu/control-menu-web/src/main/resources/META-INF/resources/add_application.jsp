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
				<input class="form-control" id="<portlet:namespace />searchApplication" name="<portlet:namespace />searchApplication" placeholder='<%= LanguageUtil.get(request, "search") + StringPool.TRIPLE_PERIOD %>' type="text" />

				<span class="input-group-btn">
					<liferay-ui:icon icon="search" markupView="lexicon" />
				</span>
			</div>
		</c:if>

		<%
		List<Portlet> portlets = new ArrayList<Portlet>();

		for (String portletId : PropsValues.DOCKBAR_ADD_PORTLETS) {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(user.getCompanyId(), portletId);

			if ((portlet != null) && portlet.isInclude() && portlet.isActive() && PortletPermissionUtil.contains(permissionChecker, layout, portlet, ActionKeys.ADD_TO_PAGE)) {
				portlets.add(portlet);
			}
		}

		int portletCategoryIndex = 0;
		%>

		<c:if test="<%= portlets.size() > 0 %>">

			<%
			String panelId = renderResponse.getNamespace() + "portletCategory" + portletCategoryIndex;
			%>

			<div class="lfr-content-category panel-page-category">
				<a class="collapse-icon list-group-heading" data-toggle="collapse" href="#<%= panelId %>">
					<liferay-ui:message key="highlighted" />

					<aui:icon cssClass="collapse-icon-closed" image="angle-right" markupView="lexicon" />

					<aui:icon cssClass="collapse-icon-open" image="angle-down" markupView="lexicon" />
				</a>

				<div class="collapse in list-group-panel" id="<%= panelId %>">
					<div class="list-group-item">
						<ul class="nav nav-equal-height">

							<%
							for (Portlet portlet : portlets) {
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

								String cssClass = "drag-content-item";

								if (portletLocked) {
									cssClass += " lfr-portlet-used";
								}
							%>

								<li class="lfr-content-item">
									<aui:icon cssClass="<%= cssClass %>" data="<%= data %>" image='<%= portletInstanceable ? "grid" : "live" %>' label="<%= PortalUtil.getPortletTitle(portlet, application, locale) %>" markupView="lexicon" />

									<%
									data.remove("draggable");
									%>

									<span <%= AUIUtil.buildData(data) %> class='add-content-item <%= portletLocked ? "lfr-portlet-used" : StringPool.BLANK %>'>
										<liferay-ui:message key="add" />
									</span>
								</li>

							<%
							}
							%>

						</ul>
					</div>
				</div>
			</div>

			<%
			portletCategoryIndex++;
			%>

		</c:if>

		<%
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

			<aui:button-row>
				<aui:button cssClass="btn-lg btn-primary" href="<%= marketplaceURL.toString() %>" value="install-more-applications" />
			</aui:button-row>
		</c:if>
	</div>
</aui:form>

<aui:script use="liferay-control-menu-add-application">
	var ControlMenu = Liferay.ControlMenu;

	var searchApplication = A.one('#<portlet:namespace />searchApplication');

	var addApplication = new ControlMenu.AddApplication(
		{
			focusItem: searchApplication,
			inputNode: searchApplication,
			namespace: '<portlet:namespace />',
			nodeList: A.one('#<portlet:namespace />applicationList'),
			nodeSelector: '.drag-content-item',
			selected: !A.one('#<portlet:namespace />addApplicationForm').ancestor().hasClass('hide')
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
</aui:script>