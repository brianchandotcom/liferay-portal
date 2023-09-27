<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/info/item/renderer/availability_label/init.jsp" %>

<span class="<%= Validator.isNull(label) ? "invisible" + StringPool.SPACE : StringPool.BLANK %>label label-<%= labelType %> m-0 <%= namespace %>availability-label">
	<span class="label-item label-item-expand"><%= label %></span>
</span>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"displayAvailability", displayAvailability
		).put(
			"namespace", namespace
		).build()
	%>'
	module="info/item/renderer/availability_label/js/AvailabilityCPInstanceChangeHandler"
/>