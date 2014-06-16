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

	<span class="added-message hide" id="<portlet:namespace />addedMessage">
		<span class="alert-success message"><liferay-ui:icon iconCssClass="icon-ok-sign" /> <span id="<portlet:namespace />portletName"></span> <liferay-ui:message key="added" /></span>
	</span>

	<div id="<portlet:namespace />applicationList">
		<c:if test="<%= layout.isTypePortlet() %>">
			<div class="btn-toolbar search-panel">
				<aui:input cssClass="col-md-12 search-query" label="" name="searchApplication" type="text" />
			</div>
		</c:if>

		<%
		String panelContainerId = "addApplicationPanelContainer";

		List<Portlet> portlets = new ArrayList<Portlet>();

		for (String portletId : PropsValues.DOCKBAR_ADD_PORTLETS) {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(user.getCompanyId(), portletId);

			if ((portlet != null) && portlet.isInclude() && portlet.isActive() && PortletPermissionUtil.contains(permissionChecker, layout, portlet, ActionKeys.ADD_TO_PAGE)) {
				portlets.add(portlet);
			}
		}

		int portletCategoryIndex = 0;
		%>

		<liferay-ui:panel-container id="<%= panelContainerId %>">
			<c:if test="<%= portlets.size() > 0 %>">

				<%
				String panelId = renderResponse.getNamespace() + "portletCategory" + portletCategoryIndex;
				%>

				<div class="lfr-add-content">
					<liferay-ui:panel collapsible="<%= layout.isTypePortlet() %>" cssClass="lfr-component lfr-content-category panel-page-category" extended="<%= true %>" id="<%= panelId %>" persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, "highlighted") %>'>
						<aui:nav collapsible="<%= false %>">

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

							<aui:nav-item cssClass="lfr-content-item" href="">
								<span <%= AUIUtil.buildData(data) %> class="<%= cssClass %>">
									<i class="<%= portletInstanceable ? "icon-th-large" : "icon-stop" %>"></i>

									<liferay-ui:message key="<%= PortalUtil.getPortletTitle(portlet, application, locale) %>" />
								</span>

								<%
								data.remove("draggable");
								%>

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

			<liferay-util:include page="/html/portlet/dockbar/view_category.jsp">
				<liferay-util:param name="panelContainerId" value="<%= panelContainerId %>" />
			</liferay-util:include>

			<%
				portletCategoryIndex++;
			}
			%>

		</liferay-ui:panel-container>

		<c:if test="<%= layout.isTypePortlet() %>">
			<ul class="lfr-add-apps-legend list-unstyled">
				<li>
					<aui:icon image="stop" label="can-be-added-once" />
				</li>
				<li>
					<aui:icon image="th-large" label="can-be-added-several-times" />
				</li>
			</ul>

			<div class="alert alert-info lfr-drag-portlet-message">
				<liferay-ui:message key="to-add-a-portlet-to-the-page-just-drag-it" />
			</div>
		</c:if>

		<c:if test="<%= !layout.isTypePanel() && permissionChecker.isOmniadmin() && PortletLocalServiceUtil.hasPortlet(themeDisplay.getCompanyId(), PortletKeys.MARKETPLACE_STORE) %>">

			<%
			long controlPanelPlid = PortalUtil.getControlPanelPlid(company.getCompanyId());

			PortletURLImpl marketplaceURL = new PortletURLImpl(request, PortletKeys.MARKETPLACE_STORE, controlPanelPlid, PortletRequest.RENDER_PHASE);
			%>

			<p class="lfr-install-more">
				<aui:a cssClass="btn btn-primary" href='<%= HttpUtil.removeParameter(marketplaceURL.toString(), "controlPanelCategory") %>' label="install-more-applications" />
			</p>
		</c:if>
	</div>
</aui:form>

<aui:script use="liferay-dockbar-add-application,liferay-dockbar-portlet-dd">
	var searchApplication = A.one('#<portlet:namespace />searchApplication');

	var addApplication = new Liferay.Dockbar.AddApplication(
		{
			focusItem: searchApplication,
			inputNode: searchApplication,
			namespace: '<portlet:namespace />',
			nodeList: A.one('#<portlet:namespace />applicationList'),
			nodeSelector: '.drag-content-item',
			selected: !A.one('#<portlet:namespace />addApplicationForm').ancestor().hasClass('hide')
		}
	);

	addApplication.plug(
		Liferay.Dockbar.PortletDragDrop,
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

	Liferay.component('<portlet:namespace />addApplication', addApplication);
</aui:script>