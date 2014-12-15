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

<%@ include file="/html/portal/init.jsp" %>

<%
String ppid = ParamUtil.getString(request, "p_p_id");

String controlPanelCategory = themeDisplay.getControlPanelCategory();

boolean showControlPanelMenu = true;

if (controlPanelCategory.startsWith(PortletCategoryKeys.CURRENT_SITE)) {
	showControlPanelMenu = false;
}

if (ppid.equals(PortletKeys.PORTLET_CONFIGURATION)) {
	String portletResource = ParamUtil.getString(request, PortalUtil.getPortletNamespace(ppid) + "portletResource");

	if (Validator.isNull(portletResource)) {
		portletResource = ParamUtil.getString(request, "portletResource");
	}

	if (Validator.isNotNull(portletResource)) {
		String strutsAction = ParamUtil.getString(request, PortalUtil.getPortletNamespace(ppid) + "struts_action");

		if (!strutsAction.startsWith("/portlet_configuration/")) {
			ppid = portletResource;
		}
	}
}

String category = PortalUtil.getControlPanelCategory(ppid, themeDisplay);

Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), ppid);

request.setAttribute("control_panel.jsp-ppid", ppid);
%>

<c:choose>
	<c:when test="<%= !themeDisplay.isStatePopUp() %>">

		<%
		String panelBodyCssClass = "panel-page-body";
		String panelCategory = "lfr-ctrl-panel";
		String categoryTitle = Validator.isNotNull(category) ? LanguageUtil.get(pageContext, "category." + category) : StringPool.BLANK;

		if (!layoutTypePortlet.hasStateMax()) {
			panelBodyCssClass += " panel-page-frontpage";
		}
		else {
			panelBodyCssClass += " panel-page-application";
		}

		if (category.equals(PortletCategoryKeys.APPS)) {
			panelCategory += " panel-manage-apps";
		}
		else if (category.equals(PortletCategoryKeys.CONFIGURATION)) {
			panelCategory += " panel-manage-configuration";
		}
		else if (category.equals(PortletCategoryKeys.MY)) {
			panelCategory += " panel-manage-my";
			categoryTitle = user.getFullName();
		}
		else if (category.equals(PortletCategoryKeys.SITES)) {
			panelCategory += " panel-manage-sites";
		}
		else if (category.equals(PortletCategoryKeys.USERS)) {
			panelCategory += " panel-manage-users";
		}
		else {
			panelCategory += " panel-manage-frontpage";
		}

		Group group = themeDisplay.getScopeGroup();

		if (group.isLayout()) {
			Layout scopeLayout = LayoutLocalServiceUtil.getLayout(group.getClassPK());

			group = scopeLayout.getGroup();
		}
		%>

		<div id="content-wrapper">
			<div class="<%= panelCategory %>">
				<c:if test="<%= showControlPanelMenu %>">
					<%@ include file="/html/portal/layout/view/control_panel_nav_main.jspf" %>
				</c:if>

				<div class="<%= panelBodyCssClass %>">
					<c:choose>
						<c:when test="<%= Validator.isNull(controlPanelCategory) %>">
							<liferay-portlet:runtime portletName="<%= PropsValues.CONTROL_PANEL_HOME_PORTLET_ID %>" />
						</c:when>
						<c:when test="<%= ((portlet != null) && !portlet.getControlPanelEntryCategory().startsWith(PortletCategoryKeys.SITE_ADMINISTRATION)) %>">
							<%@ include file="/html/portal/layout/view/panel_content.jspf" %>
						</c:when>
						<c:otherwise>
							<aui:container cssClass="<%= panelCategory %>">
								<c:if test="<%= showControlPanelMenu %>">
									<aui:row>
										<div id="controlPanelSiteHeading">
											<c:if test="<%= showControlPanelMenu %>">

												<%
												String backURL = HttpUtil.setParameter(themeDisplay.getURLControlPanel(), "p_p_id", PortletKeys.SITES_ADMIN);
												%>

												<a class="previous-level" href="<%= backURL %>" title="<liferay-ui:message key="back" />">
													<i class="control-panel-back-icon icon-circle-arrow-left"></i>

													<span class="helper-hidden-accessible">
														<liferay-ui:message key="back" />
													</span>
												</a>
											</c:if>

											<h1 class="site-title">
												<c:choose>
													<c:when test="<%= showControlPanelMenu && Validator.isNotNull(controlPanelCategory) %>">
														<%@ include file="/html/portal/layout/view/control_panel_site_selector.jspf" %>
													</c:when>
													<c:otherwise>
														<%= group.getDescriptiveName(themeDisplay.getLocale()) %>
													</c:otherwise>
												</c:choose>
											</h1>

											<c:if test="<%= group.hasPrivateLayouts() || group.hasPublicLayouts() %>">
												<ul class="visit-links">
													<li><liferay-ui:message key="visit" />:</li>

													<%
													PortletURL portletURL = new PortletURLImpl(request, PortletKeys.SITE_REDIRECTOR, plid, PortletRequest.ACTION_PHASE);

													portletURL.setParameter("struts_action", "/my_sites/view");
													portletURL.setParameter("groupId", String.valueOf(group.getGroupId()));
													portletURL.setPortletMode(PortletMode.VIEW);
													portletURL.setWindowState(WindowState.NORMAL);
													%>

													<c:choose>
														<c:when test="<%= group.hasPrivateLayouts() && group.hasPublicLayouts() %>">

															<%
															portletURL.setParameter("privateLayout", Boolean.FALSE.toString());
															%>

															<li><a href="<%= portletURL.toString() %>"><liferay-ui:message key="public-pages" /></a></li>
															<li class="divider"></li>

															<%
															portletURL.setParameter("privateLayout", Boolean.TRUE.toString());
															%>

															<li><a href="<%= portletURL.toString() %>"><liferay-ui:message key="private-pages" /></a></li>
														</c:when>
														<c:otherwise>

															<%
															portletURL.setParameter("privateLayout", group.hasPrivateLayouts() ? Boolean.TRUE.toString() : Boolean.FALSE.toString());
															%>

															<li><a href="<%= portletURL.toString() %>"><liferay-ui:message key="site-pages" /></a></li>
														</c:otherwise>
													</c:choose>
												</ul>
											</c:if>
										</div>
									</aui:row>
								</c:if>

								<aui:row>

									<%
									Map<String, List<Portlet>> categoriesMap = PortalUtil.getSiteAdministrationCategoriesMap(request);

									boolean singlePortlet = false;

									if (categoriesMap.size() == 1) {
										for (List<Portlet> categoryPortlets : categoriesMap.values()) {
											if (categoryPortlets.size() == 1) {
												singlePortlet = true;
											}
										}
									}
									%>

									<c:if test="<%= !singlePortlet %>">
										<aui:col cssClass="panel-page-menu" width="<%= 25 %>">
											<liferay-portlet:runtime portletName="160" />
										</aui:col>
									</c:if>

									<aui:col cssClass="<%= panelBodyCssClass %>"  width="<%= singlePortlet ? 100 : 75 %>">
										<%@ include file="/html/portal/layout/view/panel_content.jspf" %>
									</aui:col>
								</aui:row>
							</aui:container>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</c:when>
	<c:otherwise>
		<%@ include file="/html/portal/layout/view/panel_content.jspf" %>
	</c:otherwise>
</c:choose>

<%@ include file="/html/portal/layout/view/common.jspf" %>