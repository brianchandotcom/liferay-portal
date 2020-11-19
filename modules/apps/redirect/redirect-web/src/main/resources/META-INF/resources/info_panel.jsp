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
List<RedirectEntry> redirectEntries = (List<RedirectEntry>)GetterUtil.getObject(request.getAttribute(RedirectWebKeys.REDIRECT_ENTRIES), Collections.emptyList());
%>

<div class="sidebar-header">
	<h1 class="component-title">
		<liferay-ui:message key="info" />
	</h1>
</div>

<clay:navigation-bar
	navigationItems='<%=
		new JSPNavigationItemList(pageContext) {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(true);
						navigationItem.setLabel(LanguageUtil.get(httpServletRequest, "details"));
					});
			}
		}
	%>'
/>

<div class="sidebar-body">
	<c:choose>
		<c:when test="<%= redirectEntries.size() == 0 %>">
			<p class="h5">
				<liferay-ui:message key="num-of-items" />
			</p>

			<p>
				<%= RedirectEntryLocalServiceUtil.getRedirectEntriesCount(themeDisplay.getScopeGroupId()) %>
			</p>
		</c:when>
		<c:when test="<%= redirectEntries.size() == 1 %>">

			<%
			RedirectEntry redirectEntry = redirectEntries.get(0);
			%>

			<dl class="sidebar-dl sidebar-section">
				<dt class="sidebar-dt">
					<liferay-ui:message key="created-by" />
				</dt>
				<dd class="sidebar-dd">
					<clay:content-row
						cssClass="sidebar-panel widget-metadata"
					>

						<%
						User owner = UserLocalServiceUtil.fetchUser(redirectEntry.getUserId());
						%>

						<clay:content-col
							cssClass="inline-item-before"
						>
							<liferay-ui:user-portrait
								size="sm"
								user="<%= owner %>"
							/>
						</clay:content-col>

						<div class="username">
							<%= HtmlUtil.escape(owner.getFullName()) %>
						</div>
					</clay:content-row>
				</dd>
				<dt class="sidebar-dt">
					<liferay-ui:message key="type" />
				</dt>
				<dd class="sidebar-dd">
					<liferay-ui:message key='<%= redirectEntry.isPermanent() ? "permanent" : "temporary" %>' />
				</dd>
				<dt class="sidebar-dt">
					<liferay-ui:message key="create-date" />
				</dt>

				<%
				DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, locale);
				%>

				<dd class="sidebar-dd">
					<%= dateFormat.format(redirectEntry.getCreateDate()) %>
				</dd>
				<dt class="sidebar-dt">
					<liferay-ui:message key="latest-occurrence" />
				</dt>
				<dd class="sidebar-dd">
					<%= (redirectEntry.getLastOccurrenceDate() != null) ? dateFormat.format(redirectEntry.getLastOccurrenceDate()) : LanguageUtil.get(request, "never") %>
				</dd>
				<dt class="sidebar-dt">
					<liferay-ui:message key="expiration-date" />
				</dt>
				<dd class="sidebar-dd">
					<%= (redirectEntry.getExpirationDate() != null) ? dateFormat.format(redirectEntry.getExpirationDate()) : LanguageUtil.get(request, "never") %>
				</dd>
			</dl>
		</c:when>
		<c:otherwise>
			<p class="h5">
				<liferay-ui:message arguments="<%= redirectEntries.size() %>" key="x-items-are-selected" />
			</p>
		</c:otherwise>
	</c:choose>
</div>