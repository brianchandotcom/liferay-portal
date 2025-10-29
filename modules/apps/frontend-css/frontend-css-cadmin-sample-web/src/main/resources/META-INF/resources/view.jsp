<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<style>
.cadmin-test-unstyled {
	background-color: rgba(255, 0, 0, 0.8);
}

.cadmin-test-styled {
	background-color: rgba(0, 0, 255, 0.8);
}

.cadmin-test-styled-override {
	background-color: rgba(0, 0, 255, 0.8) !important;
}
</style>

<div>
	<react:component
		module="{App} from frontend-css-cadmin-sample-web"
	/>
</div>
