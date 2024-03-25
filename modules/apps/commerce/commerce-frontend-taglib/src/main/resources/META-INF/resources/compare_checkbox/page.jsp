<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/compare_checkbox/init.jsp" %>

<div class="compare-checkbox-root" id="<%= rootId %>"></div>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"commerceChannelGroupId", commerceChannelGroupId
		).put(
			"disabled", disabled
		).put(
			"inCompare", inCompare
		).put(
			"itemId", cpCatalogEntry.getCPDefinitionId()
		).put(
			"label", label
		).put(
			"pictureUrl", pictureUrl
		).put(
			"refreshOnRemove", refreshOnRemove
		).put(
			"rootId", rootId
		).build()
	%>'
	module="{compareCheckboxTag} from commerce-frontend-taglib"
/>