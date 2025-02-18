<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/fragment/renderer/currency_selector/init.jsp" %>

<react:component
	module="{CurrencySelector} from commerce-currency-web"
	props='<%=
		HashMapBuilder.<String, Object>put(
			"commerceChannelId", commerceChannelId
		).build()
	%>'
/>