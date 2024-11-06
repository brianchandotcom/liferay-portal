<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String portletResource = ParamUtil.getString(request, "portletResource");
%>

<clay:alert>
	<liferay-ui:message key="this-widget-has-been-updated-to-a-new-version" />

	<liferay-learn:message
		key="read-more-about-asset-publisher-changes"
		resource="asset-publisher-web"
	/>

	<liferay-portlet:actionURL name="/asset_publisher/add_asset_list" portletName="<%= portletResource %>" var="addAssetListURL">
		<portlet:param name="portletResource" value="<%= portletResource %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
	</liferay-portlet:actionURL>

	<clay:button
		additionalProps='<%=
			HashMapBuilder.<String, Object>put(
				"portletNamespace", PortalUtil.getPortletNamespace(HtmlUtil.escape(portletResource))
			).put(
				"url", addAssetListURL
			).build()
		%>'
		cssClass="mt-2"
		displayType="primary"
		label="create-collection"
		propsTransformer="{CreateAssetListActionButtonPropsTransformer} from asset-publisher-web"
		small="<%= true %>"
	/>
</clay:alert>