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
PanelCategory panelCategory = (PanelCategory)request.getAttribute(ApplicationListWebKeys.PANEL_CATEGORY);

SiteAdministrationPanelCategoryDisplayContext siteAdministrationPanelCategoryDisplayContext = new SiteAdministrationPanelCategoryDisplayContext(liferayPortletRequest, liferayPortletResponse, null);
%>

<c:if test="<%= siteAdministrationPanelCategoryDisplayContext.getGroup() != null %>">
	<div class="row">
		<div class="col-md-12">
			<c:if test="<%= siteAdministrationPanelCategoryDisplayContext.isShowStagingInfo() %>">

				<%
				Map<String, Object> data = new HashMap<String, Object>();

				data.put("qa-id", "staging");
				%>

				<div class="pull-right staging-links">
					<span class="<%= Validator.isNull(siteAdministrationPanelCategoryDisplayContext.getStagingGroupURL()) ? "active" : StringPool.BLANK %>">
						<aui:a data="<%= data %>" href="<%= siteAdministrationPanelCategoryDisplayContext.getStagingGroupURL() %>" label="staging" />
					</span>
					<span class="links-separator"> |</span>

					<%
					data.put("qa-id", "live");

					try {
						String liveGroupURL = siteAdministrationPanelCategoryDisplayContext.getLiveGroupURL();
					%>

						<span class="<%= Validator.isNull(liveGroupURL) ? "active" : StringPool.BLANK %>">
							<aui:a data="<%= data %>" href="<%= liveGroupURL %>" label="<%= siteAdministrationPanelCategoryDisplayContext.getLiveGroupLabel() %>" />
						</span>

					<%
					}
					catch (RemoteExportException | SystemException e) {
						if (e instanceof SystemException) {
							_log.error(e, e);
						}
					%>

						<aui:a data="<%= data %>" href="" id="remoteLiveLink" label="<%= siteAdministrationPanelCategoryDisplayContext.getLiveGroupLabel() %>" />

						<aui:script use="aui-tooltip">
							new A.Tooltip({
								bodyContent: Liferay.Language.get(
									'the-connection-to-the-remote-live-site-cannot-be-established-due-to-a-network-problem'
								),
								position: 'right',
								trigger: A.one('#<portlet:namespace />remoteLiveLink'),
								visible: false,
								zIndex: Liferay.zIndex.TOOLTIP
							}).render();
						</aui:script>

					<%
					}
					%>

				</div>
			</c:if>

			<c:if test="<%= siteAdministrationPanelCategoryDisplayContext.isDisplaySiteLink() %>">
				<aui:a cssClass="goto-link list-group-heading panel-header-link" href="<%= siteAdministrationPanelCategoryDisplayContext.getGroupURL() %>" label="go-to-site" />
			</c:if>
		</div>
	</div>

	<c:if test="<%= siteAdministrationPanelCategoryDisplayContext.isShowSiteAdministration() %>">
		<liferay-application-list:panel-category-body
			panelCategory="<%= panelCategory %>"
		/>
	</c:if>
</c:if>

<%!
private static Log _log = LogFactoryUtil.getLog("com_liferay_product_navigation_site_administration.sites.site_administration_body_jsp");
%>