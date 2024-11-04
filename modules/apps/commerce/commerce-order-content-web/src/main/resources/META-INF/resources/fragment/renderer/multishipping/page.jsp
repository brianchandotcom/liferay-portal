<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/fragment/renderer/multishipping/init.jsp" %>

<react:component
	module="{Multishipping} from commerce-frontend-js"
	props='<%=
		HashMapBuilder.<String, Object>put(
			"accountId", commerceAccountId
		).put(
			"orderId", commerceOrderId
		).put(
			"readonly", readOnly
		).build()
	%>'
/>