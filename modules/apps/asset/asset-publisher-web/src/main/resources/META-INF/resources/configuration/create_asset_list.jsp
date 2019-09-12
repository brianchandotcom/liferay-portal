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
	<aui:a cssClass="create-content-set-link" href="javascript:;">
		<liferay-ui:message key="create-a-content-set-from-this-configuration" />
	</aui:a>
</div>

<aui:script require="metal-dom/src/all/dom as dom,frontend-js-web/liferay/modal/commands/OpenSimpleInputModal.es as modalCommands">
	function handleCreateAssetListLinkClick(event) {
		event.preventDefault();

		modalCommands.openSimpleInputModal({
			dialogTitle: '<liferay-ui:message key="content-set-title" />',
			formSubmitURL:
				'<liferay-portlet:actionURL name="/asset_publisher/add_asset_list" portletName="<%= portletResource %>"><portlet:param name="portletResource" value="<%= portletResource %>" /><portlet:param name="redirect" value="<%= currentURL %>" /></liferay-portlet:actionURL>',
			mainFieldLabel: '<liferay-ui:message key="title" />',
			mainFieldName: 'title',
			mainFieldPlaceholder: '<liferay-ui:message key="title" />',
			namespace:
				'<%= PortalUtil.getPortletNamespace(HtmlUtil.escape(portletResource)) %>',
			spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg'
		});
	}

	var createAssetListLinkClickHandler = dom.delegate(
		document.body,
		'click',
		'a.create-content-set-link',
		handleCreateAssetListLinkClick
	);

	function handleDestroyPortlet() {
		createAssetListLinkClickHandler.removeListener();

		Liferay.detach('destroyPortlet', handleDestroyPortlet);
	}

	Liferay.on('destroyPortlet', handleDestroyPortlet);
</aui:script>