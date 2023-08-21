<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<div>
	<react:component
		componentId="scheduleModalId"
		module="admin/js/components/ScheduleModal"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"scheduledDate", ""
			).build()
		%>'
	/>
</div>