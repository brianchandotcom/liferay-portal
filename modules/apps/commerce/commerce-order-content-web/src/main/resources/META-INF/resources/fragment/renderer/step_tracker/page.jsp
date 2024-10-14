<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/fragment/renderer/step_tracker/init.jsp" %>

<react:component
	module="{StepTracker} from commerce-order-content-web"
	props='<%=
		HashMapBuilder.<String, Object>put(
			"isOpen", open
		).put(
			"orderId", commerceOrderId
		).put(
			"stepModels", stepModels
		).build()
	%>'
/>