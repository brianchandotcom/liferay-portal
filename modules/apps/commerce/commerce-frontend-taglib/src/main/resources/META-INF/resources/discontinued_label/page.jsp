<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/discontinued_label/init.jsp" %>

<span class="<%= discontinued ? StringPool.BLANK : "invisible" + StringPool.SPACE %>label label-danger m-0 <%= namespace %>discontinued-label">
	<span class="label-item label-item-expand"><liferay-ui:message key="discontinued" /></span>
</span>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"namespace", namespace
		).build()
	%>'
	module="discontinued_label/js/DiscontinuedLabelCPInstanceChangeHandler"
/>