<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ViewHomeRecentAssetsSectionDisplayContext viewHomeRecentAssetsSectionDisplayContext = (ViewHomeRecentAssetsSectionDisplayContext)request.getAttribute(ViewHomeRecentAssetsSectionDisplayContext.class.getName());
%>

<div class="cms-section p-2 p-sm-3">
	<div class="container-fluid-max">
		<div id="<%= CMSSiteInitializerFDSNames.HOME_RECENT_ASSETS_SECTION %>">
			<react:component
				module="{RecentAssetsHeader} from site-cms-site-initializer"
				props='<%=
					HashMapBuilder.<String, Object>put(
						"label", LanguageUtil.get(request, "view-all")
					).put(
						"title", LanguageUtil.get(request, "recent-assets")
					).put(
						"url", viewHomeRecentAssetsSectionDisplayContext.getAssetsAllURL()
					).build()
				%>'
			/>
		</div>

		<div class="cms-fds-fluid cms-section custom-empty-state recent-assets-fds">
			<frontend-data-set:headless-display
				additionalProps="<%= viewHomeRecentAssetsSectionDisplayContext.getAdditionalProps() %>"
				apiURL="<%= viewHomeRecentAssetsSectionDisplayContext.getAPIURL() %>"
				emptyState="<%= viewHomeRecentAssetsSectionDisplayContext.getEmptyState() %>"
				fdsActionDropdownItems="<%= viewHomeRecentAssetsSectionDisplayContext.getFDSActionDropdownItems() %>"
				formName="fm"
				id="<%= CMSSiteInitializerFDSNames.HOME_RECENT_ASSETS_SECTION %>"
				itemsPerPage="<%= 20 %>"
				propsTransformer="{HomeRecentAssetsFDSPropsTransformer} from site-cms-site-initializer"
				showManagementBar="<%= false %>"
				showSearch="<%= false %>"
				style="fluid"
			/>
		</div>
	</div>
</div>