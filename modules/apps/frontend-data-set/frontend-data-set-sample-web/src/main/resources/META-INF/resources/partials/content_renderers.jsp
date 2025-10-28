<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<p class="mt-3">
	This tests how content renderers handle specific scenarios and edge cases.
</p>

<div>
	<react:component
		module="{ContentRenderersFrontendDataSet} from frontend-data-set-sample-web"
	/>
</div>