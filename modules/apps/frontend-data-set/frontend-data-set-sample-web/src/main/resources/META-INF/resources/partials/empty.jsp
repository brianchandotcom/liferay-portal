<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
EmptyFDSDisplayContext emptyFDSDisplayContext = new EmptyFDSDisplayContext(request);
%>

<frontend-data-set:headless-display
	apiURL="<%= emptyFDSDisplayContext.getAPIURL() %>"
	emptyState='<%=
		HashMapBuilder.<String, Object>put(
			"description", LanguageUtil.get(request, "start-creating-one-to-show-your-data")
		).put(
			"image", "/states/empty_state.gif"
		).put(
			"title", LanguageUtil.get(request, "no-data-sets-created")
		).build()
	%>'
	id="<%= FDSSampleFDSNames.EMPTY %>"
/>