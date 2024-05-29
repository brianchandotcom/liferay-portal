<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
Boolean saved = GetterUtil.getBoolean(request.getAttribute("liferay-saved-content:saved-content:saved"));
%>

<div>
	<clay:button
		aria-label='<%= GetterUtil.getString((String)request.getAttribute("liferay-saved-content:saved-content:label")) %>'
		disabled="<%= true %>"
		displayType="secondary"
		monospaced="<%= true %>"
		small="<%= true %>"
	>
		<clay:icon
			symbol='<%= saved ? "bookmarks-full" : "bookmarks" %>'
		/>
	</clay:button>

	<react:component
		module="{SavedContentEntry} from saved-content-taglib"
		props='<%= (Map<String, Object>)request.getAttribute("liferay-saved-content:saved-content:data") %>'
	/>
</div>