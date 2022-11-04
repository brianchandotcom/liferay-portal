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
RedirectEntryInfoPanelDisplayContext redirectEntryInfoPanelDisplayContext = (RedirectEntryInfoPanelDisplayContext)request.getAttribute(RedirectEntryInfoPanelDisplayContext.class.getName());
%>

<div class="sidebar-header">
	<h1 class="component-title">
		<liferay-ui:message key="info" />
	</h1>
</div>

<clay:navigation-bar
	navigationItems="<%= redirectEntryInfoPanelDisplayContext.getNavigationItems() %>"
/>

<div class="sidebar-body">
	<c:choose>
		<c:when test="<%= redirectEntryInfoPanelDisplayContext.isEmptyRedirectEntries() %>">
			<p class="h5">
				<liferay-ui:message key="num-of-items" />
			</p>

			<p>
				<%= redirectEntryInfoPanelDisplayContext.getRedirectEntriesCount() %>
			</p>
		</c:when>
		<c:when test="<%= redirectEntryInfoPanelDisplayContext.isSingletonRedirectEntry() %>">
			<dl class="sidebar-dl sidebar-section">
				<dt class="sidebar-dt">
					<liferay-ui:message key="created-by" />
				</dt>
				<dd class="sidebar-dd">
					<clay:content-row
						cssClass="sidebar-panel widget-metadata"
					>
						<clay:content-col
							cssClass="inline-item-before"
						>
							<liferay-ui:user-portrait
								size="sm"
								user="<%= redirectEntryInfoPanelDisplayContext.getRedirectEntryUser() %>"
							/>
						</clay:content-col>

						<div class="username">
							<%= HtmlUtil.escape(redirectEntryInfoPanelDisplayContext.getRedirectEntryUserFullName()) %>
						</div>
					</clay:content-row>
				</dd>
				<dt class="sidebar-dt">
					<liferay-ui:message key="type" />
				</dt>
				<dd class="sidebar-dd">
					<liferay-ui:message key="<%= redirectEntryInfoPanelDisplayContext.getRedirectEntryTypeLabel() %>" />
				</dd>
				<dt class="sidebar-dt">
					<liferay-ui:message key="create-date" />
				</dt>
				<dd class="sidebar-dd">
					<%= redirectEntryInfoPanelDisplayContext.getFormattedRedirectEntryCreateDate() %>
				</dd>
				<dt class="sidebar-dt">
					<liferay-ui:message key="latest-occurrence" />
				</dt>
				<dd class="sidebar-dd">
					<%= redirectEntryInfoPanelDisplayContext.getFormattedRedirectEntryLastOccurrenceDate() %>
				</dd>
				<dt class="sidebar-dt">
					<liferay-ui:message key="expiration-date" />
				</dt>
				<dd class="sidebar-dd">
					<%= redirectEntryInfoPanelDisplayContext.getFormattedRedirectEntryExpirationDate() %>
				</dd>
			</dl>
		</c:when>
		<c:otherwise>
			<p class="h5">
				<liferay-ui:message arguments="<%= redirectEntryInfoPanelDisplayContext.getRedirectEntriesCount() %>" key="x-items-are-selected" />
			</p>
		</c:otherwise>
	</c:choose>
</div>