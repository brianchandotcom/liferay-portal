<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String portletResource = ParamUtil.getString(request, "portletResource");
%>

<div class="mb-2">
	<aui:a cssClass="create-collection-link" href="javascript:void(0);">
		<liferay-ui:message key="create-a-collection-from-this-configuration" />
	</aui:a>
</div>

<aui:script require="frontend-js-web/index as frontendJsWeb">
	var {delegate, openSimpleInputModal} = frontendJsWeb;

	function handleCreateAssetListLinkClick(event) {
		event.preventDefault();

		openSimpleInputModal({
			dialogTitle: '<liferay-ui:message key="collection-title" />',
			formSubmitURL:
				'<liferay-portlet:actionURL name="/asset_publisher/add_asset_list" portletName="<%= portletResource %>"><portlet:param name="portletResource" value="<%= portletResource %>" /><portlet:param name="redirect" value="<%= currentURL %>" /></liferay-portlet:actionURL>',
			mainFieldLabel: '<liferay-ui:message key="title" />',
			mainFieldName: 'title',
			mainFieldPlaceholder: '<liferay-ui:message key="title" />',
			namespace:
				'<%= PortalUtil.getPortletNamespace(HtmlUtil.escape(portletResource)) %>',
			spritemap: '<%= themeDisplay.getPathThemeSpritemap() %>',
		});
	}

	var createAssetListLinkClickHandler = delegate(
		document.body,
		'click',
		'a.create-collection-link',
		handleCreateAssetListLinkClick
	);

	function handleDestroyPortlet() {
		createAssetListLinkClickHandler.dispose();

		Liferay.detach('destroyPortlet', handleDestroyPortlet);
	}

	Liferay.on('destroyPortlet', handleDestroyPortlet);
</aui:script>