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

PanelCategory panelCategory = siteAdministrationPanelCategoryDisplayContext.getPanelCategory();

ResourceBundle resourceBundle = ResourceBundleUtil.getBundle("content.Language", locale, getClass());
%>

<liferay-ui:icon
	cssClass="icon-sites"
	icon="sites"
	id="manageSitesLink"
	label="<%= false %>"
	markupView="lexicon"
	message='<%= LanguageUtil.get(resourceBundle, "go-to-other-site") %>'
	url="javascript:;"
/>

<div class="hide">
	<div id="<portlet:namespace/>siteSelectorContent">
		<liferay-util:include page="/sites/my_sites.jsp" servletContext="<%= application %>" />

		<c:if test="<%= Validator.isNotNull(siteAdministrationPanelCategoryDisplayContext.getManageSitesURL()) %>">
			<div class="manage-sites-link">
				<aui:icon image="sites" label='<%= LanguageUtil.get(resourceBundle, "manage-sites") %>' markupView="lexicon" url="<%= siteAdministrationPanelCategoryDisplayContext.getManageSitesURL() %>" />
			</div>
		</c:if>
	</div>
</div>

<div aria-controls="#<portlet:namespace /><%= AUIUtil.normalizeId(panelCategory.getKey()) %>Collapse" aria-expanded="<%= siteAdministrationPanelCategoryDisplayContext.isCollapsedPanel() %>" class="panel-toggler <%= siteAdministrationPanelCategoryDisplayContext.getGroup() != null ? "collapse-icon" : StringPool.BLANK %> <%= siteAdministrationPanelCategoryDisplayContext.isCollapsedPanel() ? StringPool.BLANK : "collapsed" %>" class="collapsed" data-parent="#<portlet:namespace />Accordion" data-toggle="collapse" href="#<portlet:namespace /><%= AUIUtil.normalizeId(panelCategory.getKey()) %>Collapse" id="<portlet:namespace /><%= AUIUtil.normalizeId(panelCategory.getKey()) %>Toggler" <%= siteAdministrationPanelCategoryDisplayContext.getGroup() != null ? "role=\"button\"" : StringPool.BLANK %> >
	<div>
		<c:if test="<%= siteAdministrationPanelCategoryDisplayContext.getGroup() != null %>">
			<c:choose>
				<c:when test="<%= Validator.isNotNull(siteAdministrationPanelCategoryDisplayContext.getLogoURL()) %>">
					<div class="aspect-ratio-bg-cover sticker" style="background-image: url(<%= siteAdministrationPanelCategoryDisplayContext.getLogoURL() %>);"></div>
				</c:when>
				<c:otherwise>
					<div class="sticker sticker-default">
						<aui:icon image="sites" markupView="lexicon" />
					</div>
				</c:otherwise>
			</c:choose>
		</c:if>

		<span class="site-name">
			<%= Validator.isNotNull(siteAdministrationPanelCategoryDisplayContext.getGroupName()) ? HtmlUtil.escape(siteAdministrationPanelCategoryDisplayContext.getGroupName()) : LanguageUtil.get(resourceBundle, "choose-a-site") %>

			<c:if test="<%= siteAdministrationPanelCategoryDisplayContext.isShowStagingInfo() %>">
				<span class="site-sub-name"> - <liferay-ui:message key="<%= siteAdministrationPanelCategoryDisplayContext.getStagingLabel() %>" /></span>
			</c:if>
		</span>

		<c:if test="<%= siteAdministrationPanelCategoryDisplayContext.getNotificationsCount() > 0 %>">
			<span class="panel-notifications-count sticker sticker-right sticker-rounded sticker-sm sticker-warning"><%= siteAdministrationPanelCategoryDisplayContext.getNotificationsCount() %></span>
		</c:if>
	</div>
</div>

<aui:script use="aui-popover,event-outside">
	var trigger = A.one('#<portlet:namespace/>manageSitesLink');

	var popOver = new A.Popover(
		{
			align: {
				node: '#<portlet:namespace /><%= AUIUtil.normalizeId(panelCategory.getKey()) %>Toggler',
				points:[A.WidgetPositionAlign.LT, A.WidgetPositionAlign.RT]
			},
			bodyContent: A.one('#<portlet:namespace/>siteSelectorContent'),
			cssClass: 'product-menu',
			constrain: true,
			hideOn: [
				{
					node: A.one('document'),
					eventName: 'key',
					keyCode: 'esc'
				},
				{
					node: A.one('document'),
					eventName: 'clickoutside'
				}
			],
			position: 'left',
			trigger: trigger,
			visible: false,
			width: 300,
			zIndex: Liferay.zIndex.TOOLTIP
		}
	).render();
</aui:script>