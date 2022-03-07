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

int childPanelCategoriesSize = GetterUtil.getInteger(request.getAttribute("product_menu.jsp-childPanelCategoriesSize"));
%>

<c:choose>
	<c:when test="<%= group != null %>">
		<c:choose>
			<c:when test="<%= childPanelCategoriesSize > 1 %>">
				<%@ include file="/sites/site_administration_header_icon_sites.jspf" %>

				<a aria-controls="<portlet:namespace /><%= AUIUtil.normalizeId(panelCategory.getKey()) %>Collapse" aria-expanded="<%= siteAdministrationPanelCategoryDisplayContext.isCollapsedPanel() %>" class="panel-toggler <%= (group != null) ? "collapse-icon collapse-icon-middle " : StringPool.BLANK %> <%= siteAdministrationPanelCategoryDisplayContext.isCollapsedPanel() ? StringPool.BLANK : "collapsed" %> site-administration-toggler" data-parent="#<portlet:namespace />Accordion" data-qa-id="productMenuSiteAdministrationPanelCategory" data-toggle="liferay-collapse" href="#<portlet:namespace /><%= AUIUtil.normalizeId(panelCategory.getKey()) %>Collapse" id="<portlet:namespace /><%= AUIUtil.normalizeId(panelCategory.getKey()) %>Toggler" <%= (group != null) ? "role=\"button\"" : StringPool.BLANK %>>
					<clay:content-row
						verticalAlign="center"
					>
						<clay:content-col>
							<c:choose>
								<c:when test="<%= Validator.isNotNull(siteAdministrationPanelCategoryDisplayContext.getLogoURL()) %>">
									<div class="aspect-ratio-bg-cover sticker" style="background-image: url(<%= siteAdministrationPanelCategoryDisplayContext.getLogoURL() %>);"></div>
								</c:when>
								<c:otherwise>
									<clay:sticker
										displayType="secondary"
										icon="<%= group.getIconCssClass() %>"
									/>
								</c:otherwise>
							</c:choose>
						</clay:content-col>

						<clay:content-col
							cssClass="mr-4"
							expand="<%= true %>"
						>
							<div class="lfr-portal-tooltip site-name text-truncate" title="<%= HtmlUtil.escapeAttribute(siteAdministrationPanelCategoryDisplayContext.getGroupName()) %>">
								<%= HtmlUtil.escape(siteAdministrationPanelCategoryDisplayContext.getGroupName()) %>

								<c:if test="<%= siteAdministrationPanelCategoryDisplayContext.isShowStagingInfo() && !group.isStagedRemotely() %>">
									<span class="site-sub-name"> - <liferay-ui:message key="<%= siteAdministrationPanelCategoryDisplayContext.getStagingLabel() %>" /></span>
								</c:if>
							</div>
						</clay:content-col>
					</clay:content-row>

					<c:if test="<%= siteAdministrationPanelCategoryDisplayContext.getNotificationsCount() > 0 %>">
						<clay:sticker
							cssClass="panel-notifications-count"
							displayType="warning"
							position="top-right"
							size="sm"
						>
							<%= siteAdministrationPanelCategoryDisplayContext.getNotificationsCount() %>
						</clay:sticker>
					</c:if>

					<aui:icon cssClass="collapse-icon-closed" image="angle-right" markupView="lexicon" />

					<aui:icon cssClass="collapse-icon-open" image="angle-down" markupView="lexicon" />
				</a>
			</c:when>
			<c:otherwise>
				<%@ include file="/sites/site_administration_header_no_collapsible.jspf" %>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:when test="<%= siteAdministrationPanelCategoryDisplayContext.isShowSiteSelector() %>">
		<%@ include file="/sites/site_administration_header_icon_sites.jspf" %>

		<div class="collapsed panel-toggler">
			<span class="site-name">
				<liferay-ui:message key="choose-a-site" />
			</span>
		</div>
	</c:when>
</c:choose>