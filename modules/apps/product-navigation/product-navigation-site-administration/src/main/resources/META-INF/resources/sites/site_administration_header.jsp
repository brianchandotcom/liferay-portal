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
SiteAdministrationPanelCategoryDisplayContext siteAdministrationPanelCategoryDisplayContext = new SiteAdministrationPanelCategoryDisplayContext(liferayPortletRequest, liferayPortletResponse, null);

Group group = siteAdministrationPanelCategoryDisplayContext.getGroup();
PanelCategory panelCategory = siteAdministrationPanelCategoryDisplayContext.getPanelCategory();

PortletURL portletURL = PortletURLFactoryUtil.create(request, ProductNavigationProductMenuPortletKeys.PRODUCT_NAVIGATION_PRODUCT_MENU, RenderRequest.RENDER_PHASE);

portletURL.setParameter("mvcPath", "/portlet/pages_tree.jsp");
portletURL.setParameter("selPpid", portletDisplay.getId());
portletURL.setWindowState(LiferayWindowState.EXCLUSIVE);
%>

<c:if test="<%= group.getType() != GroupConstants.TYPE_DEPOT %>">
	<div class="icon-pages-tree">
		<liferay-ui:icon
			icon="pages-tree"
			id="pagesTreeSidenavToggleId"
			label="<%= false %>"
			linkCssClass="icon-monospaced"
			markupView="lexicon"
			message="find-a-page"
			url="javascript:;"
		/>
	</div>

	<aui:script sandbox="<%= true %>">
		var pagesTreeToggle = document.getElementById(
			'<portlet:namespace />pagesTreeSidenavToggleId'
		);

		pagesTreeToggle.addEventListener('click', function(event) {
			Liferay.Util.Session.set(
				'com.liferay.product.navigation.product.menu.web_pagesTreeState',
				'open'
			).then(function() {
				Liferay.Util.fetch('<%= portletURL.toString() %>')
					.then(function(response) {
						if (!response.ok) {
							throw new Error(
								'<liferay-ui:message key="an-unexpected-error-occurred" />'
							);
						}

						return response.text();
					})
					.then(function(response) {
						var sidebar = document.querySelector('.sidebar-body');
						sidebar.innerHTML = '';

						var range = document.createRange();
						range.selectNode(sidebar);

						var fragment = range.createContextualFragment(response);

						var pagesTree = document.createElement('div');
						pagesTree.setAttribute('class', 'pages-tree');
						pagesTree.appendChild(fragment);

						sidebar.appendChild(pagesTree);
					});
			});
		});
	</aui:script>
</c:if>

<c:if test="<%= siteAdministrationPanelCategoryDisplayContext.isShowSiteSelector() %>">
	<div class="icon-sites">
		<liferay-ui:icon
			icon="change"
			id="manageSitesLink"
			label="<%= false %>"
			linkCssClass="icon-monospaced"
			markupView="lexicon"
			message="go-to-other-space"
			url="javascript:;"
		/>
	</div>

	<%
	String eventName = liferayPortletResponse.getNamespace() + "selectSite";

	ItemSelector itemSelector = (ItemSelector)request.getAttribute(SiteAdministrationWebKeys.ITEM_SELECTOR);

	ItemSelectorCriterion itemSelectorCriterion = new GroupItemSelectorCriterion();

	itemSelectorCriterion.setDesiredItemSelectorReturnTypes(new URLItemSelectorReturnType());

	PortletURL itemSelectorURL = itemSelector.getItemSelectorURL(RequestBackedPortletURLFactoryUtil.create(liferayPortletRequest), eventName, itemSelectorCriterion);
	%>

	<aui:script sandbox="<%= true %>">
		$('#<portlet:namespace />manageSitesLink').on('click', function(event) {
			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						destroyOnHide: true,
						modal: true
					},
					eventName: '<%= eventName %>',
					id: '<portlet:namespace />selectSite',
					title: '<liferay-ui:message key="select-space" />',
					uri: '<%= itemSelectorURL.toString() %>'
				},
				function(event) {
					location.href = event.url;
				}
			);
		});
	</aui:script>
</c:if>

<c:choose>
	<c:when test="<%= group != null %>">
		<a aria-controls="<portlet:namespace /><%= AUIUtil.normalizeId(panelCategory.getKey()) %>Collapse" aria-expanded="<%= siteAdministrationPanelCategoryDisplayContext.isCollapsedPanel() %>" class="panel-toggler <%= (group != null) ? "collapse-icon collapse-icon-middle " : StringPool.BLANK %> <%= siteAdministrationPanelCategoryDisplayContext.isCollapsedPanel() ? StringPool.BLANK : "collapsed" %> site-administration-toggler" data-parent="#<portlet:namespace />Accordion" data-qa-id="productMenuSiteAdministrationPanelCategory" data-toggle="collapse" href="#<portlet:namespace /><%= AUIUtil.normalizeId(panelCategory.getKey()) %>Collapse" id="<portlet:namespace /><%= AUIUtil.normalizeId(panelCategory.getKey()) %>Toggler" <%= (group != null) ? "role=\"button\"" : StringPool.BLANK %>>
			<div class="autofit-row autofit-row-center">
				<div class="autofit-col">
					<c:choose>
						<c:when test="<%= Validator.isNotNull(siteAdministrationPanelCategoryDisplayContext.getLogoURL()) %>">
							<div class="aspect-ratio-bg-cover sticker" style="background-image: url(<%= siteAdministrationPanelCategoryDisplayContext.getLogoURL() %>);"></div>
						</c:when>
						<c:otherwise>
							<div class="sticker sticker-secondary">
								<aui:icon image="<%= group.getIconCssClass() %>" markupView="lexicon" />
							</div>
						</c:otherwise>
					</c:choose>
				</div>

				<div class="autofit-col autofit-col-expand">
					<div class="depot-type">
						<liferay-ui:message key='<%= (group.getType() == GroupConstants.TYPE_DEPOT) ? "repository" : "site" %>' />
					</div>

					<div class="site-name text-truncate">
						<%= HtmlUtil.escape(siteAdministrationPanelCategoryDisplayContext.getGroupName()) %>

						<c:if test="<%= siteAdministrationPanelCategoryDisplayContext.isShowStagingInfo() && !group.isStagedRemotely() %>">
							<span class="site-sub-name"> - <liferay-ui:message key="<%= siteAdministrationPanelCategoryDisplayContext.getStagingLabel() %>" /></span>
						</c:if>
					</div>
				</div>
			</div>

			<c:if test="<%= siteAdministrationPanelCategoryDisplayContext.getNotificationsCount() > 0 %>">
				<span class="panel-notifications-count sticker sticker-rounded sticker-sm sticker-top-right sticker-warning"><%= siteAdministrationPanelCategoryDisplayContext.getNotificationsCount() %></span>
			</c:if>

			<aui:icon cssClass="collapse-icon-closed" image="angle-right" markupView="lexicon" />

			<aui:icon cssClass="collapse-icon-open" image="angle-down" markupView="lexicon" />
		</a>
	</c:when>
	<c:when test="<%= siteAdministrationPanelCategoryDisplayContext.isShowSiteSelector() %>">
		<div class="collapsed panel-toggler">
			<span class="site-name">
				<liferay-ui:message key="choose-a-space" />
			</span>
		</div>
	</c:when>
</c:choose>