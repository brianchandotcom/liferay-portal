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

<%@ include file="/process_in_progress/init.jsp" %>

<c:if test="<%= backgroundTaskStatus != null %>">
	<div class="active progress progress-striped progress-xs">
		<div class="progress-bar" style="width: <%= percentage %>%;">
			<c:if test="<%= (allProgressBarCountersTotal > 0) && (!Objects.equals(cmd, Constants.PUBLISH_TO_REMOTE) || (percentage < 100)) %>">
				<%= percentage + StringPool.PERCENT %>
			</c:if>
		</div>
	</div>

	<c:choose>
		<c:when test="<%= Objects.equals(cmd, Constants.PUBLISH_TO_REMOTE) && (percentage == 100) %>">
			<div class="progress-current-item">
				<strong><liferay-ui:message key="please-wait-as-the-publication-processes-on-the-remote-site" /></strong>
			</div>
		</c:when>
		<c:when test="<%= Validator.isNotNull(stagedModelName) && Validator.isNotNull(stagedModelType) %>">
			<div class="progress-current-item">
				<strong><liferay-ui:message key="publishing" /><%= StringPool.TRIPLE_PERIOD %></strong> <%= ResourceActionsUtil.getModelResource(locale, stagedModelType) %> <em><%= HtmlUtil.escape(stagedModelName) %></em>
			</div>
		</c:when>
	</c:choose>
</c:if>