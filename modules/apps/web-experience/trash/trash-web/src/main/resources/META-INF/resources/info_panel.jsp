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
List<TrashEntry> trashEntries = (List<TrashEntry>)request.getAttribute(TrashWebKeys.TRASH_ENTRIES);
%>

<c:choose>
	<c:when test="<%= ListUtil.isNotEmpty(trashEntries) %>">
		<c:choose>
			<c:when test="<%= trashEntries.size() == 1 %>">

				<%
				TrashEntry trashEntry = trashEntries.get(0);

				TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(trashEntry.getClassName());

				TrashRenderer trashRenderer = trashHandler.getTrashRenderer(trashEntry.getClassPK());
				%>

				<div class="sidebar-header">
					<ul class="sidebar-actions">
						<li>
							<c:choose>
								<c:when test="<%= Validator.isNotNull(trashRenderer.renderActions(renderRequest, renderResponse)) %>">
									<liferay-util:include page="<%= trashRenderer.renderActions(renderRequest, renderResponse) %>" servletContext="<%= application %>" />
								</c:when>
								<c:when test="<%= trashEntry.getRootEntry() == null %>">

									<%
									request.setAttribute(TrashWebKeys.TRASH_ENTRY, trashEntry);
									%>

									<liferay-util:include page="/entry_action.jsp" servletContext="<%= application %>" />
								</c:when>
								<c:otherwise>

									<%
									request.setAttribute(TrashWebKeys.TRASH_RENDERER, trashRenderer);
									%>

									<liferay-util:include page="/view_content_action.jsp" servletContext="<%= application %>" />
								</c:otherwise>
							</c:choose>
						</li>
					</ul>

					<h4 class="sidebar-title"><%= HtmlUtil.escape(trashRenderer.getTitle(locale)) %></h4>
				</div>

				<aui:nav-bar cssClass="navbar-no-collapse" markupView="lexicon">
					<aui:nav collapsible="<%= false %>" cssClass="navbar-nav">
						<aui:nav-item label="details" selected="<%= true %>" />
					</aui:nav>
				</aui:nav-bar>

				<div class="sidebar-body">
					<dl class="sidebar-block">
						<dt class="h5">
							<liferay-ui:message key="type" />
						</dt>
						<dd class="h6 sidebar-caption">
							<%= ResourceActionsUtil.getModelResource(locale, trashEntry.getClassName()) %>
						</dd>
						<dt class="h5">
							<liferay-ui:message key="removed-date" />
						</dt>
						<dd class="h6 sidebar-caption">
							<%= dateFormatDateTime.format(trashEntry.getCreateDate()) %>
						</dd>
						<dt class="h5">
							<liferay-ui:message key="removed-by" />
						</dt>
						<dd class="h6 sidebar-caption">
							<%= HtmlUtil.escape(trashEntry.getUserName()) %>
						</dd>
					</dl>
				</div>
			</c:when>
			<c:otherwise>
				<div class="sidebar-header">
					<h4 class="sidebar-title"><liferay-ui:message arguments="<%= trashEntries.size() %>" key="x-items-are-selected" /></h4>
				</div>

				<aui:nav-bar cssClass="navbar-no-collapse" markupView="lexicon">
					<aui:nav collapsible="<%= false %>" cssClass="navbar-nav">
						<aui:nav-item label="details" selected="<%= true %>" />
					</aui:nav>
				</aui:nav-bar>

				<div class="sidebar-body">
					<dl class="sidebar-block">
						<dt class="h5">
							<liferay-ui:message key="num-of-items" />
						</dt>
						<dd class="h6 sidebar-caption">
							<%= trashEntries.size() %>
						</dd>
					</dl>
				</div>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<div class="sidebar-header">
			<h4 class="sidebar-title"><liferay-ui:message key="home" /></h4>
		</div>

		<aui:nav-bar cssClass="navbar-no-collapse" markupView="lexicon">
			<aui:nav collapsible="<%= false %>" cssClass="navbar-nav">
				<aui:nav-item label="details" selected="<%= true %>" />
			</aui:nav>
		</aui:nav-bar>

		<div class="sidebar-body">
			<dl class="sidebar-block">
				<dt class="h5">
					<liferay-ui:message key="num-of-items" />
				</dt>
				<dd class="h6 sidebar-caption">
					<%= TrashEntryLocalServiceUtil.getEntriesCount(themeDisplay.getScopeGroupId()) %>
				</dd>
			</dl>
		</div>
	</c:otherwise>
</c:choose>