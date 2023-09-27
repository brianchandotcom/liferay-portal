<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/process_info/init.jsp" %>

<clay:container-fluid
	cssClass="text-secondary"
>
	<clay:row>
		<clay:col
			sm=""
		>
			<liferay-staging:process-title
				backgroundTask="<%= backgroundTask %>"
				listView="<%= false %>"
			/>
		</clay:col>
	</clay:row>

	<clay:row>
		<clay:col
			sm=""
		>
			<%= HtmlUtil.escape(userName) %>
		</clay:col>

		<clay:col
			sm=""
		>
			<liferay-staging:process-date
				date="<%= backgroundTask.getCreateDate() %>"
				labelKey="start-date"
				listView="<%= false %>"
			/>
		</clay:col>

		<clay:col
			sm=""
		>
			<liferay-staging:process-date
				date="<%= backgroundTask.getCompletionDate() %>"
				labelKey="completion-date"
				listView="<%= false %>"
			/>
		</clay:col>
	</clay:row>

	<clay:row>
		<clay:col>
			<liferay-staging:process-in-progress
				backgroundTask="<%= backgroundTask %>"
				listView="<%= false %>"
			/>
		</clay:col>
	</clay:row>

	<clay:row>
		<clay:col>
			<liferay-staging:process-status
				backgroundTaskStatus="<%= backgroundTask.getStatus() %>"
				backgroundTaskStatusLabel="<%= backgroundTask.getStatusLabel() %>"
			/>
		</clay:col>
	</clay:row>
</clay:container-fluid>