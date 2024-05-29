<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<clay:icon
	aria-label='<%= LanguageUtil.get(request, "not-visible-to-guest-users") %>'
	cssClass="c-ml-2 c-mt-0 lfr-portal-tooltip text-white"
	data-title='<%= LanguageUtil.get(request, "not-visible-to-guest-users") %>'
	symbol="password-policies"
/>