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
List<Long> restoreTrashEntryIds = (List<Long>)request.getAttribute("liferay-trash:undo:restoreTrashEntryIds");
List<String> titles = (List<String>)request.getAttribute("liferay-trash:undo:titles");
int trashedEntriesCount = GetterUtil.getInteger(request.getAttribute("liferay-trash:undo:trashedEntriesCount"));
%>

<liferay-util:buffer
	var="alertMessage"
>
	<aui:form action='<%= (String)request.getAttribute("liferay-trash:undo:portletURL") %>' cssClass="d-inline" name="undoForm">
		<liferay-util:buffer
			var="trashLink"
		>

			<%
			PortletURL trashURL = PortletProviderUtil.getPortletURL(request, TrashEntry.class.getName(), PortletProvider.Action.VIEW);
			%>

			<c:choose>
				<c:when test="<%= themeDisplay.isShowSiteAdministrationIcon() && (trashURL != null) %>">
					<aui:a cssClass="alert-link" href="<%= trashURL.toString() %>" label="recycle-bin" />
				</c:when>
				<c:otherwise>
					<liferay-ui:message key="recycle-bin" />
				</c:otherwise>
			</c:choose>
		</liferay-util:buffer>

		<c:choose>
			<c:when test="<%= trashedEntriesCount > 1 %>">
				<c:choose>
					<c:when test="<%= Objects.equals(cmd, Constants.REMOVE) %>">
						<liferay-ui:message arguments="<%= trashedEntriesCount %>" key="x-items-were-removed" translateArguments="<%= false %>" />
					</c:when>
					<c:otherwise>
						<liferay-ui:message arguments="<%= new Object[] {trashedEntriesCount, trashLink.trim()} %>" key="x-items-were-moved-to-the-x" translateArguments="<%= false %>" />
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
						<liferay-ui:message arguments="<%= new Object[] {trashEntityLink, trashLink.trim()} %>" key="the-element-x-was-moved-to-the-x" translateArguments="<%= false %>" />
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>

		<aui:input name="redirect" type="hidden" value='<%= GetterUtil.getString(request.getAttribute("liferay-trash:undo:redirect"), currentURL) %>' />
		<aui:input name="restoreTrashEntryIds" type="hidden" value="<%= StringUtil.merge(restoreTrashEntryIds) %>" />

		<div class="alert-footer">
			<div class="btn-group" role="group">
				<clay:button
					aria-label='<%= LanguageUtil.get(request, "undo-deletion") %>'
					cssClass="alert-btn trash-undo-button"
					displayType="primary"
					label="undo"
					small="<%= true %>"
					type="submit"
				/>
			</div>
		</div>
	</aui:form>
</liferay-util:buffer>

<aui:script>
	const componentId =
		'<%= liferayPortletResponse.getNamespace() + "recycleBinAlert" %>';

	Liferay.Util.openToast({
		toastProps: {
			tabIndex: '-1',
			id: componentId,
		},
		renderData: {
			componentId,
		},
		autoClose: 15000,
		message: '<%= HtmlUtil.escapeJS(alertMessage) %>',
		type: 'success',
	});

	Liferay.componentReady(componentId).then(() =>
		document.getElementById(componentId).focus()
	);
</aui:script>