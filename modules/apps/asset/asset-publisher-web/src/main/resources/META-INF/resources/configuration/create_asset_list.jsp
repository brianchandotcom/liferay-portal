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
			spritemap: '<%= FrontendIconsUtil.getSpritemap(themeDisplay) %>',
		});
	}

	var delegate = delegateModule.default;

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