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

<%@ include file="/html/portlet/control_panel_menu/init.jsp" %>

<div class="portal-add-content">
	<div class="control-panel-tools">
		<div class="search-panels">
			<i class="icon-search"></i>

			<div class="search-panels-bar">
				<aui:input cssClass="form-control search-panels-input search-query" label="" name="searchPanel" />
			</div>
		</div>
	</div>

	<liferay-ui:panel-container accordion="<%= true %>" extended="<%= true %>" id="controlPanelMenuAddContentPanelContainer" persistState="<%= true %>">

		<%
		String ppid = GetterUtil.getString((String)request.getAttribute("control_panel.jsp-ppid"), layoutTypePortlet.getStateMaxPortletId());

		String portletCategory = null;

		if (Validator.isNotNull(ppid)) {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(themeDisplay.getCompanyId(), ppid);

			portletCategory = portlet.getControlPanelEntryCategory();
		}

		Map<String, List<Portlet>> siteAdministrationCategoriesMap = PortalUtil.getSiteAdministrationCategoriesMap(request);

		for (String siteAdministrationCategory : siteAdministrationCategoriesMap.keySet()) {
			String iconCssClass = "icon-file";

			String panelPageCategoryId = "panel-manage-" + siteAdministrationCategory;

			if (siteAdministrationCategory.equals(PortletCategoryKeys.SITE_ADMINISTRATION_CONFIGURATION)) {
				iconCssClass = "icon-hdd";
			}
			else if (siteAdministrationCategory.equals(PortletCategoryKeys.SITE_ADMINISTRATION_CONTENT)) {
				iconCssClass = "icon-file-text";
			}
			else if (siteAdministrationCategory.equals(PortletCategoryKeys.SITE_ADMINISTRATION_PAGES)) {
				iconCssClass = "icon-sitemap";
			}
			else if (siteAdministrationCategory.equals(PortletCategoryKeys.SITE_ADMINISTRATION_USERS)) {
				iconCssClass = "icon-group";
			}
		%>

			<liferay-ui:panel collapsible="<%= true %>" cssClass="list-unstyled panel-page-category" extended="<%= true %>" iconCssClass="<%= iconCssClass %>" id="<%= panelPageCategoryId %>" persistState="<%= true %>" state='<%= siteAdministrationCategory.equals(portletCategory) ? "open" : "closed" %>' title='<%= LanguageUtil.get(request, "category." + siteAdministrationCategory) %>'>
				<c:if test="<%= siteAdministrationCategory.equals(PortletCategoryKeys.SITE_ADMINISTRATION_CONTENT) %>">

					<%
					List<Layout> scopeLayouts = new ArrayList<Layout>();

					Group curSite = themeDisplay.getSiteGroup();

					scopeLayouts.addAll(LayoutLocalServiceUtil.getScopeGroupLayouts(curSite.getGroupId()));

					String scopeLabel = null;

					Group curScopeGroup = themeDisplay.getScopeGroup();

					if (curScopeGroup.isLayout()) {
						Layout scopeLayout = LayoutLocalServiceUtil.getLayout(curScopeGroup.getClassPK());

						scopeLabel = StringUtil.shorten(scopeLayout.getName(locale), 20);
					}
					else {
						scopeLabel = LanguageUtil.get(request, "default");
					}
					%>

					<c:if test="<%= !scopeLayouts.isEmpty() && siteAdministrationCategory.equals(PortletCategoryKeys.SITE_ADMINISTRATION_CONTENT) %>">
						<div class="lfr-title-scope-selector nobr">
							<liferay-ui:message key="scope" />:
							<liferay-ui:icon-menu direction="down" icon="" message="<%= scopeLabel %>">

								<%
								Map<String, Object> data = new HashMap<String, Object>();

								data.put("navigation", Boolean.TRUE.toString());
								%>

								<liferay-ui:icon
									data="<%= data %>"
									iconCssClass="<%= curSite.getIconCssClass() %>"
									message="default"
									url='<%= HttpUtil.setParameter(PortalUtil.getCurrentURL(request), "doAsGroupId", curSite.getGroupId()) %>'
								/>

								<%
								for (Layout curScopeLayout : scopeLayouts) {
									Group scopeGroup = curScopeLayout.getScopeGroup();
								%>

									<liferay-ui:icon
										data="<%= data %>"
										iconCssClass="<%= scopeGroup.getIconCssClass() %>"
										message="<%= HtmlUtil.escape(curScopeLayout.getName(locale)) %>"
										url='<%= HttpUtil.setParameter(PortalUtil.getCurrentURL(request), "doAsGroupId", scopeGroup.getGroupId()) %>'
									/>

								<%
								}
								%>

							</liferay-ui:icon-menu>
						</div>
					</c:if>
				</c:if>

				<ul aria-labelledby="<%= panelPageCategoryId %>" class="category-portlets list-unstyled" role="menu">

					<%
					List<Portlet> portlets = siteAdministrationCategoriesMap.get(siteAdministrationCategory);

					for (Portlet portlet : portlets) {
						String portletId = portlet.getPortletId();

						String portletTitle = PortalUtil.getPortletTitle(portletId, themeDisplay.getLocale());
					%>

						<li class="<%= ppid.equals(portletId) ? "selected-portlet" : "" %>" data-search="<%= HtmlUtil.escape(LanguageUtil.get(request, "category." + siteAdministrationCategory) + "-" + portletTitle) %>" role="presentation">
							<liferay-portlet:renderURL
								doAsGroupId="<%= themeDisplay.getScopeGroupId() %>"
								portletName="<%= portlet.getRootPortletId() %>"
								var="portletURL"
								windowState="<%= WindowState.MAXIMIZED.toString() %>"
							/>

							<liferay-portlet:icon-portlet
								ariaRole="menuitem"
								id='<%= "portlet_" + portletId %>'
								label="<%= true %>"
								portlet="<%= portlet %>"
								url="<%= portletURL %>"
							/>
						</li>

						<c:if test="<%= !ppid.equals(portletId) %>">

							<%
							String portletClassName = portlet.getPortletClass();
							%>

							<%
							if (portletClassName.equals("com.liferay.alloy.mvc.AlloyPortlet")) {
								PortletConfig alloyPortletConfig = PortletConfigFactoryUtil.create(portlet, application);

								PortletContext alloyPortletContext = alloyPortletConfig.getPortletContext();

								if (alloyPortletContext.getAttribute("com.liferay.alloy.mvc.BaseAlloyControllerImpl#TOUCH#" + portlet.getRootPortletId()) != Boolean.FALSE) {
							%>

								<iframe height="0" src="<%= portletURL %>" style="display: none; visibility: hidden;" width="0"></iframe>

							<%
								}
							}
							%>

						</c:if>

					<%
					}
					%>

				</ul>
			</liferay-ui:panel>

		<%
		}
		%>

	</liferay-ui:panel-container>
</div>