<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/process_info/init.jsp" %>

<div class="container text-secondary">
	<div class="row">
		<div class="col-sm">
			<liferay-staging:process-title
				backgroundTask="<%= backgroundTask %>"
				listView="<%= false %>"
			/>
		</div>
	</div>

	<div class="row">
		<div class="col-sm"><%= HtmlUtil.escape(userName) %></div>
		<div class="col-sm">
			<liferay-staging:process-date
				date="<%= backgroundTask.getCreateDate() %>"
				labelKey="start-date"
				listView="<%= false %>"
			/>
		</div>

		<div class="col-sm">
			<liferay-staging:process-date
				date="<%= backgroundTask.getCompletionDate() %>"
				labelKey="completion-date"
				listView="<%= false %>"
			/>
		</div>
	</div>

	<div class="row">
		<div class="col">
			<liferay-staging:process-in-progress
				backgroundTask="<%= backgroundTask %>"
				listView="<%= false %>"
			/>
		</div>
	</div>

	<div class="row">
		<div class="col">
			<liferay-staging:process-status
				backgroundTaskStatus="<%= backgroundTask.getStatus() %>"
				backgroundTaskStatusLabel="<%= backgroundTask.getStatusLabel() %>"
			/>
		</div>
	</div>
</div>