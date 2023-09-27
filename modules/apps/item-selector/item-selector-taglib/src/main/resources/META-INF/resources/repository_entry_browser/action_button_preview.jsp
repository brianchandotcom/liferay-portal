<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/repository_entry_browser/init.jsp" %>

<clay:button
	aria-label='<%= LanguageUtil.get(request, "preview") %>'
	borderless="<%= true %>"
	cssClass="component-action icon-view"
	displayType="secondary"
	icon="view"
	monospaced="<%= true %>"
/>