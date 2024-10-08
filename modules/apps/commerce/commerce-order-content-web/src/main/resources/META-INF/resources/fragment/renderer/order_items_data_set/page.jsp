<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/fragment/renderer/order_items_data_set/init.jsp" %>

<frontend-data-set:headless-display
	additionalProps="<%= additionalProps %>"
	apiURL="<%= apiURL %>"
	bulkActionDropdownItems="<%= bulkActionDropdownItems %>"
	fdsActionDropdownItems="<%= fdsActionDropdownItems %>"
	id="<%= name %>"
	propsTransformer="<%= propsTransformer %>"
	selectedItemsKey="id"
	selectionType="multiple"
	style="<%= displayStyle %>"
/>