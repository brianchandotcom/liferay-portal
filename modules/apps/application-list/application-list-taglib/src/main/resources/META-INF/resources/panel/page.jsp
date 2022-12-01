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

<%@ include file="/panel/init.jsp" %>

<%
List<PanelCategory> childPanelCategories = (List<PanelCategory>)request.getAttribute("liferay-application-list:panel:childPanelCategories");
PanelCategory panelCategory = (PanelCategory)request.getAttribute("liferay-application-list:panel:panelCategory");

PanelAppRegistry panelAppRegistry = (PanelAppRegistry)request.getAttribute(ApplicationListWebKeys.PANEL_APP_REGISTRY);
PanelCategoryRegistry panelCategoryRegistry = (PanelCategoryRegistry)request.getAttribute(ApplicationListWebKeys.PANEL_CATEGORY_REGISTRY);

PanelCategoryHelper panelCategoryHelper = new PanelCategoryHelper(panelAppRegistry, panelCategoryRegistry);
%>

<ul aria-orientation="vertical" class="m-1 p-0" id="<portlet:namespace /><%= panelCategory.getKey() %>_panel" role="menubar">
	<li class="list-group" role="none">

		<%
		for (PanelCategory childPanelCategory : childPanelCategories) {
		%>

			<c:if test="<%= !childPanelCategory.include(request, PipingServletResponseFactory.createPipingServletResponse(pageContext)) %>">

				<%
				List<PanelApp> childPanelCategoryPanelApps = PanelCategoryUtil.getPanelApps(request, panelAppRegistry, childPanelCategory);
				%>

				<c:choose>
					<c:when test="<%= childPanelCategoryPanelApps.isEmpty() %>">
						<liferay-application-list:panel
							panelCategory="<%= childPanelCategory %>"
						/>
					</c:when>
					<c:otherwise>

						<%
						boolean active = PanelCategoryUtil.isActive(request, childPanelCategoryPanelApps, childPanelCategory, childPanelCategories, panelCategoryHelper);

						String id = PanelCategoryUtil.getId(childPanelCategory);
						int notificationsCount = PanelCategoryUtil.getNotificationsCount(request, childPanelCategory, panelCategoryHelper);
						%>

						<a aria-expanded="<%= active %>" class="<%= PanelCategoryUtil.isHeaderActive(request, childPanelCategory, panelCategoryHelper) ? "active" : "" %> collapse-icon collapse-icon-middle nav-link <%= active ? StringPool.BLANK : "collapsed" %> list-group-heading panel-header" data-qa-id="appGroup" data-toggle="liferay-collapse" href="#<%= id %>" id="<%= id %>-link" role="menuitem">
							<c:if test="<%= !childPanelCategory.includeHeader(request, PipingServletResponseFactory.createPipingServletResponse(pageContext)) %>">
								<%= childPanelCategory.getLabel(themeDisplay.getLocale()) %>

								<c:if test="<%= notificationsCount > 0 %>">
									<clay:badge
										cssClass="float-right panel-notifications-count"
										data-qa-id="notificationsCount"
										displayType="danger"
										label="<%= String.valueOf(notificationsCount) %>"
									/>
								</c:if>
							</c:if>

							<aui:icon cssClass="collapse-icon-closed" image="angle-right" markupView="lexicon" />

							<aui:icon cssClass="collapse-icon-open" image="angle-down" markupView="lexicon" />
						</a>

						<div class="collapse <%= active ? "show" : StringPool.BLANK %>" id="<%= id %>">
							<div class="list-group-item">
								<c:if test="<%= childPanelCategory.isAllowScopeLayouts() %>">

									<%
									Group curSite = themeDisplay.getSiteGroup();

									List<Layout> scopeLayouts = LayoutLocalServiceUtil.getScopeGroupLayouts(curSite.getGroupId());
									%>

									<c:if test="<%= !scopeLayouts.isEmpty() %>">
										<div class="scope-selector">

											<%
											Group curScopeGroup = themeDisplay.getScopeGroup();
											%>

											<clay:content-row
												verticalAlign="center"
											>
												<clay:content-col
													expand="<%= true %>"
												>
													<span class="scope-name">
														<c:choose>
															<c:when test="<%= curScopeGroup.isLayout() %>">
																<%= curScopeGroup.getDescriptiveName(locale) %> (<liferay-ui:message key="scope" />)
															</c:when>
															<c:otherwise>
																<liferay-ui:message key="default-scope" />
															</c:otherwise>
														</c:choose>
													</span>
												</clay:content-col>

												<%
												ContentPanelCategoryDisplayContext contentPanelCategoryDisplayContext = new ContentPanelCategoryDisplayContext(request);
												%>

												<clay:content-col>
													<clay:dropdown-menu
														borderless="<%= true %>"
														cssClass="text-light"
														displayType="secondary"
														dropdownItems="<%= contentPanelCategoryDisplayContext.getScopesDropdownItemList() %>"
														icon="cog"
														monospaced="<%= true %>"
													/>
												</clay:content-col>
											</clay:content-row>
										</div>
									</c:if>
								</c:if>

								<ul aria-labelledby="<%= id %>-link" class="nav nav-equal-height nav-stacked" role="menu">

									<%
									for (PanelApp panelApp : childPanelCategoryPanelApps) {
									%>

										<%@ include file="/panel/panel_app.jspf" %>

									<%
									}
									%>

								</ul>

								<liferay-application-list:panel
									panelCategory="<%= childPanelCategory %>"
								/>
							</div>
						</div>

						<c:if test="<%= childPanelCategory.isPersistState() %>">
							<aui:script position="auto">
								Liferay.on('liferay.collapse.hidden', (event) => {
									var panelId = event.panel.getAttribute('id');

									if (panelId === '<%= id %>') {
										Liferay.Util.Session.set(
											'<%= PanelCategory.class.getName() %><%= id %>',
											'closed'
										);
									}
								});

								Liferay.on('liferay.collapse.shown', (event) => {
									var panelId = event.panel.getAttribute('id');

									if (panelId === '<%= id %>') {
										Liferay.Util.Session.set(
											'<%= PanelCategory.class.getName() %><%= id %>',
											'open'
										);
									}
								});
							</aui:script>
						</c:if>
					</c:otherwise>
				</c:choose>
			</c:if>

		<%
		}
		%>

	</li>

	<%
	for (PanelApp panelApp : panelAppRegistry.getPanelApps(panelCategory.getKey())) {
	%>

		<c:if test="<%= panelApp.isShow(permissionChecker, themeDisplay.getScopeGroup()) %>">
			<li class="list-group" role="none">
				<div class="list-group-heading panel-app-root panel-header <%= Objects.equals(themeDisplay.getPpid(), panelApp.getPortletId()) ? "active" : StringPool.BLANK %>">
					<%@ include file="/panel/panel_app.jspf" %>
				</div>
			</li>
		</c:if>

	<%
	}
	%>

</ul>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"categoryKey", panelCategory.getKey()
		).build()
	%>'
	module="panel/PanelKeyboardHandler"
/>