<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" var="baseResourceURL" />

<div>
	<react:component
		module="{MarketplaceSettings} from marketplace-settings-web"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"baseResourceURL", String.valueOf(baseResourceURL)
			).put(
				"clientId", request.getAttribute(MarketplaceSettingsWebKeys.MARKETPLACE_CLIENT_ID)
			).put(
				"redirect", request.getAttribute(MarketplaceSettingsWebKeys.MARKETPLACE_REDIRECT)
			).put(
				"url", request.getAttribute(MarketplaceSettingsWebKeys.MARKETPLACE_URL)
			).build()
		%>'
	/>
</div>

<aui:script>
	function <portlet:namespace />resetPage() {
		const portlet = document.querySelector(
			'#portlet<portlet:namespace />'.slice(0, -1)
		);
		const container = portlet.querySelectorAll(
			'.portlet-body > .container-fluid'
		)[1];
		const firstColumn = container.querySelector('.row > .col-md-3');
		const secondColumn = container.querySelector('.row > .col-md-9');

		firstColumn.remove();
		secondColumn.className = 'col-md-12';
	}

	<portlet:namespace />resetPage();
</aui:script>