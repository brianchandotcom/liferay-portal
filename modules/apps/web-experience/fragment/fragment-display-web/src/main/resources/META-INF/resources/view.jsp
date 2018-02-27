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
FragmentEntryLink fragmentEntryLink = fragmentEntryDisplayContext.getFragmentEntryLink();
%>

<c:choose>
	<c:when test="<%= fragmentEntryLink == null %>">
		<div class="alert alert-info text-center">
			<div>
				<liferay-ui:message key="this-application-is-not-visible-to-users-yet" />
			</div>

			<c:if test="<%= fragmentEntryDisplayContext.isShowConfigurationLink() %>">
				<div>
					<aui:a href="javascript:;" onClick="<%= portletDisplay.getURLConfigurationJS() %>"><liferay-ui:message key="select-fragment-entry-to-make-it-visible" /></aui:a>
				</div>
			</c:if>
		</div>
	</c:when>
</c:choose>