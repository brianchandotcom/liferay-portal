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

<%@ include file="/init.jsp" %>

<%
FragmentEntry fragmentEntry = fragmentEntryDisplayContext.getFragmentEntry();

String imagePreviewURL = fragmentEntry.getImagePreviewURL(themeDisplay);
%>

<c:choose>
	<c:when test="<%= Validator.isNotNull(imagePreviewURL) %>">
		<liferay-frontend:vertical-card
			cssClass="entry-display-style"
			imageCSSClass="aspect-ratio-bg-contain"
			imageUrl="<%= imagePreviewURL %>"
			title="<%= fragmentEntry.getName() %>"
		>
			<liferay-frontend:vertical-card-header>

				<%
				Date statusDate = fragmentEntry.getStatusDate();
				%>

				<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - statusDate.getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
			</liferay-frontend:vertical-card-header>

			<liferay-frontend:vertical-card-footer>
				<aui:workflow-status markupView="lexicon" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= fragmentEntry.getStatus() %>" />
			</liferay-frontend:vertical-card-footer>
		</liferay-frontend:vertical-card>
	</c:when>
	<c:otherwise>
		<liferay-frontend:icon-vertical-card
			cssClass="entry-display-style"
			icon="page"
			title="<%= fragmentEntry.getName() %>"
		>
			<liferay-frontend:vertical-card-header>

				<%
				Date statusDate = fragmentEntry.getStatusDate();
				%>

				<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - statusDate.getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
			</liferay-frontend:vertical-card-header>

			<liferay-frontend:vertical-card-footer>
				<aui:workflow-status markupView="lexicon" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= fragmentEntry.getStatus() %>" />
			</liferay-frontend:vertical-card-footer>
		</liferay-frontend:icon-vertical-card>
	</c:otherwise>
</c:choose>