<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

OAuthClientPRLocalMetadata oAuthClientPRLocalMetadata = (OAuthClientPRLocalMetadata)row.getObject();

OAuthClientPRLocalMetadataActionDropdownItemsProvider oAuthClientPRLocalMetadataActionDropdownItemsProvider = new OAuthClientPRLocalMetadataActionDropdownItemsProvider(oAuthClientPRLocalMetadata, renderRequest, renderResponse);
%>

<clay:dropdown-actions
	aria-label='<%= LanguageUtil.format(request, "actions-for-x", oAuthClientPRLocalMetadata.getProtectedResourceURI()) %>'
	dropdownItems="<%= oAuthClientPRLocalMetadataActionDropdownItemsProvider.getActionDropdownItems() %>"
	propsTransformer="{OAuthClientPRLocalMetadataActionDropdownPropsTransformer} from oauth-client-admin-web"
/>