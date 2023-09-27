<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/fragment/renderer/add_to_cart/init.jsp" %>

<commerce-ui:add-to-cart
	alignment="<%= alignment %>"
	CPCatalogEntry="<%= cpCatalogEntry %>"
	inline="<%= inline %>"
	namespace="<%= namespace %>"
	showUnitOfMeasureSelector="<%= true %>"
	size="<%= size %>"
	skuOptions="[]"
/>