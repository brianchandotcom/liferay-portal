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
String cmd = (String)request.getAttribute("liferay-trash:undo:cmd");
String portletURL = (String)request.getAttribute("liferay-trash:undo:portletURL");
String redirectURL = GetterUtil.getString(request.getAttribute("liferay-trash:undo:redirect"), currentURL);
List<Long> restoreTrashEntryIds = (List<Long>)request.getAttribute("liferay-trash:undo:restoreTrashEntryIds");
List<String> titles = (List<String>)request.getAttribute("liferay-trash:undo:titles");
int trashedEntriesCount = GetterUtil.getInteger(request.getAttribute("liferay-trash:undo:trashedEntriesCount"));
%>

<liferay-util:buffer
	var="alertMessage"
>
	<aui:form action="<%= portletURL %>" cssClass="alert-trash-form" name="undoForm">
		<liferay-util:buffer
			var="trashLink"
		>

			<%
			PortletURL trashURL = PortletProviderUtil.getPortletURL(request, TrashEntry.class.getName(), PortletProvider.Action.VIEW);
			%>

			<c:choose>
				<c:when test="<%= themeDisplay.isShowSiteAdministrationIcon() && (trashURL != null) %>">
					<aui:a cssClass="alert-link" href="<%= trashURL.toString() %>" label="the-recycle-bin" />
				</c:when>
				<c:otherwise>
					<liferay-ui:message key="the-recycle-bin" />
				</c:otherwise>
			</c:choose>
		</liferay-util:buffer>

		<c:choose>
			<c:when test="<%= trashedEntriesCount > 1 %>">
				<c:choose>
					<c:when test="<%= Objects.equals(cmd, Constants.REMOVE) %>">
						<liferay-ui:message arguments="<%= new Object[] {trashedEntriesCount} %>" key="x-items-were-removed" translateArguments="<%= false %>" />
					</c:when>
					<c:otherwise>
						<liferay-ui:message arguments="<%= new Object[] {trashedEntriesCount, trashLink.trim()} %>" key="x-items-were-moved-to-x" translateArguments="<%= false %>" />
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>

				<%
				String title = StringPool.BLANK;

				if (ListUtil.isNotEmpty(titles)) {
					title = titles.get(0);
				}
				%>

				<liferay-util:buffer
					var="trashEntityLink"
				>
					<c:if test="<%= Validator.isNotNull(title) %>">
						<strong><em class="delete-entry-title"><%= HtmlUtil.escape(title) %></em></strong>
					</c:if>
				</liferay-util:buffer>

				<c:choose>
					<c:when test="<%= Objects.equals(cmd, Constants.REMOVE) %>">
						<liferay-ui:message arguments="<%= trashEntityLink %>" key="the-element-x-was-removed" translateArguments="<%= false %>" />
					</c:when>
					<c:otherwise>
						<liferay-ui:message arguments="<%= new Object[] {trashEntityLink, trashLink.trim()} %>" key="the-element-x-was-moved-to-x" translateArguments="<%= false %>" />
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>

		<aui:input name="redirect" type="hidden" value="<%= redirectURL %>" />
		<aui:input name="restoreTrashEntryIds" type="hidden" value="<%= StringUtil.merge(restoreTrashEntryIds) %>" />

		<aui:button cssClass="alert-link btn-link btn-sm btn-unstyled trash-undo-button" type="submit" value="undo" />
	</aui:form>
</liferay-util:buffer>

<liferay-ui:success key="<%= portletDisplay.getId() + SessionMessages.KEY_SUFFIX_DELETE_SUCCESS_DATA %>" message="<%= alertMessage %>" />