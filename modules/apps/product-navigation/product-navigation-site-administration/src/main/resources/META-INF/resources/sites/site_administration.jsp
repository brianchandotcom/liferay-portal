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
SiteAdministrationPanelCategoryDisplayContext sapcDisplayContext = new SiteAdministrationPanelCategoryDisplayContext(liferayPortletRequest, liferayPortletResponse, null);

PanelCategory panelCategory = sapcDisplayContext.getPanelCategory();
%>

<aui:a cssClass="icon-monospaced icon-sites" href="javascript:;" id="manageSitesLink" title="go-to-other-site">
	<aui:icon image="sites" markupView="lexicon" />
</aui:a>

<div class="hide">
	<div id="<portlet:namespace/>siteSelectorContent">
		<liferay-util:include page="/sites/my_sites.jsp" servletContext="<%= application %>" />

		<c:if test="<%= Validator.isNotNull(sapcDisplayContext.getManageSitesURL()) %>">

			<%
			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle("content.Language", locale, getClass());
			%>

			<div class="manage-sites-link">
				<aui:icon image="sites" label='<%= LanguageUtil.get(resourceBundle, "manage-sites") %>' markupView="lexicon" url="<%= sapcDisplayContext.getManageSitesURL() %>" />
			</div>
		</c:if>
	</div>
</div>

<div aria-controls="#<portlet:namespace /><%= AUIUtil.normalizeId(panelCategory.getKey()) %>Collapse" aria-expanded="<%= sapcDisplayContext.isCollapsedPanel() %>" class="panel-toggler collapse-icon <%= sapcDisplayContext.isCollapsedPanel() ? StringPool.BLANK : "collapsed" %>" class="collapsed" data-parent="#<portlet:namespace />Accordion" data-toggle="collapse" href="#<portlet:namespace /><%= AUIUtil.normalizeId(panelCategory.getKey()) %>Collapse" role="button">
	<div>
		<div class="toolbar-group-field">
			<c:choose>
				<c:when test="<%= Validator.isNotNull(sapcDisplayContext.getLogoURL()) %>">
					<div class="aspect-ratio-bg-cover sticker" style="background-image: url(<%= sapcDisplayContext.getLogoURL() %>);"></div>
				</c:when>
				<c:otherwise>
					<div class="sticker sticker-default">
						<aui:icon image="sites" markupView="lexicon" />
					</div>
				</c:otherwise>
			</c:choose>
		</div>

		<div class="toolbar-group-content">
			<c:if test="<%= sapcDisplayContext.getNotificationsCount() > 0 %>">
				<span class="sticker sticker-right sticker-rounded sticker-sm sticker-warning"><%= sapcDisplayContext.getNotificationsCount() %></span>
			</c:if>

			<span class="site-name">
				<%= HtmlUtil.escape(sapcDisplayContext.getGroupName()) %>

				<c:if test="<%= sapcDisplayContext.isShowStagingInfo() %>">
					<span class="site-sub-name">(<%= sapcDisplayContext.getStagingLabel() %>)</span>
				</c:if>
			</span>

			<c:if test="<%= sapcDisplayContext.isShowStagingInfo() %>">
				<div class="site-subheader">
					<div class="<%= Validator.isNull(sapcDisplayContext.getStagingGroupURL()) ? "active" : StringPool.BLANK %>">
						<aui:a cssClass="icon-fb-radio icon-monospaced" href="<%= sapcDisplayContext.getStagingGroupURL() %>" title="staging" />
					</div>

					<div class="<%= Validator.isNull(sapcDisplayContext.getLiveGroupURL()) ? "active" : StringPool.BLANK %>">
						<aui:a cssClass="icon-circle-blank icon-monospaced" href="<%= sapcDisplayContext.getLiveGroupURL() %>" title="live" />
					</div>
				</div>
			</c:if>
		</div>
	</div>
</div>

<aui:script use="aui-popover,event-outside">
	var trigger = A.one('#<portlet:namespace/>manageSitesLink');

	var popOver = new A.Popover(
		{
			align: {
				node: trigger,
				points:[A.WidgetPositionAlign.LC, A.WidgetPositionAlign.RC]
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
			visible: false,
			width: 300,
			zIndex: Liferay.zIndex.TOOLTIP
		}
	).render();

	trigger.on(
		'click',
		function() {
			popOver.set('visible', !popOver.get('visible'));
		}
	);
</aui:script>