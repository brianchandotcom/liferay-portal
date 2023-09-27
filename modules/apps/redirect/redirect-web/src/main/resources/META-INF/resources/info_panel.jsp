<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
							<liferay-user:user-portrait
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