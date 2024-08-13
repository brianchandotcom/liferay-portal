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
		cssClass="c-pl-0 create-collection-link"
		displayType="link"
		id='<%= liferayPortletResponse.getNamespace() + "collectionButton" %>'
		label="create-a-collection-from-this-configuration"
		propsTransformer="{CreateAssetListActionButtonPropsTransformer} from asset-publisher-web"
	/>
</div>