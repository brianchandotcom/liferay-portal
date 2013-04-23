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

<c:choose>
	<c:when test="<%= themeDisplay.isSignedIn() %>">

		<%
		Group group = null;

		if (layout != null) {
			group = layout.getGroup();
		}

		boolean hasLayoutCustomizePermission = LayoutPermissionUtil.contains(permissionChecker, layout, ActionKeys.CUSTOMIZE);
		boolean hasLayoutUpdatePermission = LayoutPermissionUtil.contains(permissionChecker, layout, ActionKeys.UPDATE);
		%>

		<c:if test="<%= !themeDisplay.isStateMaximized() && (layout != null) && (layout.isTypePortlet() || layout.isTypePanel()) && !layout.isLayoutPrototypeLinkActive() && !group.isControlPanel() && (!group.hasStagingGroup() || group.isStagingGroup()) && (GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.ADD_LAYOUT) || hasLayoutUpdatePermission || (layoutTypePortlet.isCustomizable() && layoutTypePortlet.isCustomizedView() && hasLayoutCustomizePermission)) %>">
			<div class="add-content-menu" id="<portlet:namespace />addPanelContainer">
				<span class="close-add-panel" id="<portlet:namespace />closePanel">
					<liferay-ui:message key="close" />
				</span>

				<%
				String selectedTab = GetterUtil.getString(SessionClicks.get(request, "liferay_addpanel_tab", "content"));
				%>

				<liferay-ui:tabs
					names="content,applications"
					refresh="<%= false %>"
					value="<%= selectedTab %>"
				>
					<liferay-ui:section>

						<%
						int deltaDefault = GetterUtil.getInteger(SessionClicks.get(request, "liferay_addpanel_numitems", "10"));

						int delta = ParamUtil.getInteger(request, "delta", deltaDefault);
						%>

						<portlet:resourceURL var="updateContentListURL">
							<portlet:param name="struts_action" value="/dockbar/view" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
						</portlet:resourceURL>

						<aui:form action="<%= updateContentListURL %>" cssClass="add-content-tab" name="addContentForm" onSubmit="event.preventDefault();">
							<div class="search-panel">
								<aui:input cssClass="add-content-search lfr-auto-focus" label="" name="searchContentInput" type="text" />

								<%
								String displayStyleDefault = GetterUtil.getString(SessionClicks.get(request, "liferay_addpanel_displaystyle", "descriptive"));

								String displayStyle = ParamUtil.getString(request, "displayStyle", displayStyleDefault);
								%>

								<span class="buttons" id="<portlet:namespace />styleButtons">
									<span class='icon button <%= displayStyle.equals("icon") ? "selected" : "" %>' data-style="icon" title='<%= LanguageUtil.get(pageContext, "icon-view") %>'>
										<liferay-ui:message key="icon" />
									</span>

									<span class='descriptive button <%= displayStyle.equals("descriptive") ? "selected" : "" %>' data-style="descriptive" title='<%= LanguageUtil.get(pageContext, "descriptive-view") %>'>
										<liferay-ui:message key="descriptive" />
									</span>

									<span class='list button last <%= displayStyle.equals("list") ? "selected" : "" %>' data-style="list" title='<%= LanguageUtil.get(pageContext, "list-view") %>'>
										<liferay-ui:message key="list" />
									</span>
								</span>

								<aui:select cssClass="num-items" label="" name="numItems">

									<%
									for (int curDelta : PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES) {
										if (curDelta > SearchContainer.MAX_DELTA) {
											continue;
										}
									%>

										<aui:option label="<%= curDelta %>" selected="<%= delta == curDelta %>" />

									<%
									}
									%>

								</aui:select>
							</div>

							<div id="<portlet:namespace />entriesContainer">
								<liferay-util:include page="/html/portlet/dockbar/view_resources.jsp" />
							</div>
						</aui:form>
					</liferay-ui:section>

					<liferay-ui:section>

						<%
						PortletURL refererURL = renderResponse.createActionURL();

						refererURL.setParameter("updateLayout", "true");
						%>

						<div class="portal-add-content">
							<aui:form action='<%= themeDisplay.getPathMain() + "/portal/update_layout?p_auth=" + AuthTokenUtil.getToken(request) + "&p_l_id=" + plid + "&p_v_l_s_g_id=" + themeDisplay.getSiteGroupId() %>' cssClass="add-application-tab" method="post" name="addApplicationForm">
								<aui:input name="doAsUserId" type="hidden" value="<%= themeDisplay.getDoAsUserId() %>" />
								<aui:input name="<%= Constants.CMD %>" type="hidden" value="template" />
								<aui:input name="<%= WebKeys.REFERER %>" type="hidden" value="<%= refererURL.toString() %>" />
								<aui:input name="refresh" type="hidden" value="<%= true %>" />

								<c:if test="<%= layout.isTypePortlet() %>">
									<div class="search-panel">
										<aui:input cssClass="add-content-search lfr-auto-focus" label="" name="searchApplication" type="text" />
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
									String id = renderResponse.getNamespace() + "portletCategory" + portletCategoryIndex;
									%>

									<div class="lfr-add-content">
										<liferay-ui:panel collapsible="<%= layout.isTypePortlet() %>" cssClass="lfr-content-category lfr-component panel-page-category" extended="<%= true %>" id="<%= id %>" persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, "highlighted") %>'>

											<%
											for (Portlet portlet : portlets) {
												if (!PortletPermissionUtil.contains(permissionChecker, layout, portlet.getPortletId(), ActionKeys.ADD_TO_PAGE)) {
													continue;
												}

												boolean portletInstanceable = portlet.isInstanceable();

												boolean portletUsed = layoutTypePortlet.hasPortletId(portlet.getPortletId());

												boolean portletLocked = (!portletInstanceable && portletUsed);

												Map<String, Object> data = new HashMap<String, Object>();

												data.put("id", renderResponse.getNamespace() + "portletItem" + portlet.getPortletId());
												data.put("instanceable", portletInstanceable);
												data.put("plid", plid);
												data.put("portlet-id", portlet.getPortletId());
												data.put("title", PortalUtil.getPortletTitle(portlet, application, locale));

												String cssClass = "lfr-portlet-item";

												if (portletLocked) {
													cssClass += " lfr-portlet-used";
												}

												if (portletInstanceable) {
													cssClass += " lfr-instanceable";
												}
											%>

												<div class="content-item">
													<span <%= AUIUtil.buildData(data) %> class='add-content-item <%= portletLocked ? "lfr-portlet-used" : StringPool.BLANK %>'>
														<liferay-ui:message key="add" />
													</span>

													<%
													data.put("draggable", Boolean.TRUE.toString());
													%>

													<liferay-ui:app-view-entry
														cssClass="<%= cssClass %>"
														data="<%= data %>"
														displayStyle="list"
														showCheckbox="<%= false %>"
														showLinkTitle="<%= false %>"
														thumbnailSrc="<%= StringPool.BLANK %>"
														title="<%= PortalUtil.getPortletTitle(portlet, application, locale) %>"
													/>
												</div>

											<%
											}
											%>

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
					</liferay-ui:section>
				</liferay-ui:tabs>
			</div>

			<aui:script use="liferay-dockbar-add-content">
				new Liferay.Dockbar.AddContent(
					{
						namespace: '<portlet:namespace />'
					}
				);
			</aui:script>
		</c:if>
	</c:when>
	<c:otherwise>
		<liferay-ui:message key="please-sign-in-to-continue" />
	</c:otherwise>
</c:choose>

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