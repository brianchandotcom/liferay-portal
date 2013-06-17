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

<%@ include file="/html/portlet/control_panel_menu/init.jsp" %>

<%
Map<String, List<Portlet>> categoriesMap = PortalUtil.getSiteAdministrationCategoriesMap(request);
%>

<div class="portal-add-content">
	<div class="control-panel-tools">
		<div class="search-panels">
			<div class="search-panels-bar">
				<aui:input cssClass="search-query span12 search-panels-input" label="" name="searchPanel" />
			</div>
		</div>
	</div>

	<liferay-ui:panel-container extended="<%= true %>" id="controlPanelMenuAddContentPanelContainer" persistState="<%= true %>">

		<%
		String ppid = GetterUtil.getString((String)request.getAttribute("control_panel.jsp-ppid"), layoutTypePortlet.getStateMaxPortletId());

		for (String curCategory : categoriesMap.keySet()) {
			String title = LanguageUtil.get(pageContext, "category." + curCategory);

			List<Portlet> portlets = categoriesMap.get(curCategory);
		%>

			<liferay-ui:panel collapsible="<%= true %>" cssClass="panel-page-category unstyled" extended="<%= true %>" id='<%= "panel-manage-" + curCategory %>' persistState="<%= true %>" title="<%= title %>">
				<c:if test="<%= curCategory.equals(PortletCategoryKeys.SITE_ADMINISTRATION_CONTENT) %>">

					<%
					List<Layout> scopeLayouts = new ArrayList<Layout>();

					Group curSite = themeDisplay.getSiteGroup();

					scopeLayouts.addAll(LayoutLocalServiceUtil.getScopeGroupLayouts(curSite.getGroupId(), false));
					scopeLayouts.addAll(LayoutLocalServiceUtil.getScopeGroupLayouts(curSite.getGroupId(), true));

					String scopeLabel = null;

					Group curScopeGroup = themeDisplay.getScopeGroup();

					if (curScopeGroup.isLayout()) {
						Layout scopeLayout = LayoutLocalServiceUtil.getLayout(curScopeGroup.getClassPK());

						scopeLabel = StringUtil.shorten(scopeLayout.getName(locale), 20);
					}
					else {
						scopeLabel = LanguageUtil.get(pageContext, "default");
					}
					%>

					<c:if test="<%= !scopeLayouts.isEmpty() && curCategory.equals(PortletCategoryKeys.SITE_ADMINISTRATION_CONTENT) %>">
						<div class="nobr lfr-title-scope-selector">
							<liferay-ui:message key="scope" />:
							<liferay-ui:icon-menu direction="down" icon="" message="<%= scopeLabel %>">
								<liferay-ui:icon
									message="default"
									src="<%= curSite.getIconURL(themeDisplay) %>"
									url='<%= HttpUtil.setParameter(PortalUtil.getCurrentURL(request), "doAsGroupId", curSite.getGroupId()) %>'
								/>

								<%
								for (Layout curScopeLayout : scopeLayouts) {
									Group scopeGroup = curScopeLayout.getScopeGroup();
								%>

									<liferay-ui:icon
										message="<%= HtmlUtil.escape(curScopeLayout.getName(locale)) %>"
										src="<%= scopeGroup.getIconURL(themeDisplay) %>"
										url='<%= HttpUtil.setParameter(PortalUtil.getCurrentURL(request), "doAsGroupId", scopeGroup.getGroupId()) %>'
										/>

								<%
								}
								%>

							</liferay-ui:icon-menu>
						</div>
					</c:if>
				</c:if>

				<ul class="category-portlets">

					<%
					for (Portlet portlet : portlets) {
						String portletId = portlet.getPortletId();
					%>

						<li class="<%= ppid.equals(portletId) ? "selected-portlet" : "" %>">
							<liferay-portlet:renderURL
								doAsGroupId="<%= themeDisplay.getScopeGroupId() %>"
								portletName="<%= portlet.getRootPortletId() %>"
								var="portletURL"
								windowState="<%= WindowState.MAXIMIZED.toString() %>"
								/>

							<a href="<%= portletURL %>" id="<portlet:namespace />portlet_<%= portletId %>">
								<c:choose>
									<c:when test="<%= Validator.isNull(portlet.getIcon()) %>">
										<liferay-ui:icon src='<%= themeDisplay.getPathContext() + "/html/icons/default.png" %>' />
									</c:when>
									<c:otherwise>
										<liferay-portlet:icon-portlet portlet="<%= portlet %>" />
									</c:otherwise>
								</c:choose>

								<%= PortalUtil.getPortletTitle(portlet, application, locale) %>
							</a>
						</li>

						<c:if test="<%= !ppid.equals(portletId) %>">

							<%
							String portletClassName = portlet.getPortletClass();
							%>

							<%
							if (portletClassName.equals(AlloyPortlet.class.getName())) {
								PortletConfig alloyPortletConfig = PortletConfigFactoryUtil.create(portlet, application);

								PortletContext alloyPortletContext = alloyPortletConfig.getPortletContext();

								if (alloyPortletContext.getAttribute(BaseAlloyControllerImpl.TOUCH + portlet.getRootPortletId()) != Boolean.FALSE) {
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