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
TrashEntry trashEntry = trashDisplayContext.getTrashEntry();
TrashHandler trashHandler = trashDisplayContext.getTrashHandler();
TrashRenderer trashRenderer = trashDisplayContext.getTrashRenderer();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:choose>
		<c:when test="<%= trashEntry != null %>">
			<c:choose>
				<c:when test="<%= trashHandler.isRestorable(trashEntry.getClassPK()) && !trashHandler.isInTrashContainer(trashEntry.getClassPK()) %>">
					<portlet:actionURL name="restoreEntries" var="restoreEntryURL">
						<portlet:param name="redirect" value="<%= trashDisplayContext.getViewContentRedirectURL() %>" />
						<portlet:param name="trashEntryId" value="<%= String.valueOf(trashEntry.getEntryId()) %>" />
					</portlet:actionURL>

					<liferay-ui:icon
						id="restoreEntryButton"
						message="restore"
						url="<%= restoreEntryURL.toString() %>"
					/>
				</c:when>
				<c:when test="<%= !trashHandler.isRestorable(trashEntry.getClassPK()) && trashHandler.isMovable() %>">

					<%
					String trashHandlerEntryContainerModelClassName = trashHandler.getContainerModelClassName(trashEntry.getClassPK());
					%>

					<portlet:renderURL var="moveURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
						<portlet:param name="mvcPath" value="/view_container_model.jsp" />
						<portlet:param name="classNameId" value="<%= String.valueOf(trashEntry.getClassNameId()) %>" />
						<portlet:param name="classPK" value="<%= String.valueOf(trashEntry.getClassPK()) %>" />
						<portlet:param name="containerModelClassNameId" value="<%= String.valueOf(PortalUtil.getClassNameId(trashHandlerEntryContainerModelClassName)) %>" />
					</portlet:renderURL>

					<%
					String taglibOnClick = renderResponse.getNamespace() + "restoreDialog('" + moveURL + "')";
					%>

					<liferay-ui:icon
						id="restoreEntryButton"
						message="restore"
						onClick="<%= taglibOnClick %>"
						url="javascript:;"
					/>
				</c:when>
			</c:choose>

			<c:if test="<%= trashHandler.isDeletable() %>">
				<portlet:actionURL name="deleteEntries" var="deleteEntryURL">
					<portlet:param name="redirect" value="<%= trashDisplayContext.getViewContentRedirectURL() %>" />
					<portlet:param name="trashEntryId" value="<%= String.valueOf(trashEntry.getEntryId()) %>" />
				</portlet:actionURL>

				<%
				String taglibOnClick = "if (confirm('" + UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") + "')) { submitForm(document.hrefFm, '" + deleteEntryURL.toString() + "'); }";
				%>

				<liferay-ui:icon
					id="removeEntryButton"
					message="delete"
					onClick="<%= taglibOnClick %>"
					url="javascript:;"
				/>
			</c:if>
		</c:when>
		<c:otherwise>
			<c:if test="<%= trashHandler.isMovable() %>">

				<%
				String containerModelClassName = trashHandler.getContainerModelClassName(trashDisplayContext.getClassPK());

				long trashRendererClassNameId = PortalUtil.getClassNameId(trashRenderer.getClassName());
				%>

				<portlet:renderURL var="moveURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
					<portlet:param name="mvcPath" value="/view_container_model.jsp" />
					<portlet:param name="classNameId" value="<%= String.valueOf(trashRendererClassNameId) %>" />
					<portlet:param name="classPK" value="<%= String.valueOf(trashRenderer.getClassPK()) %>" />
					<portlet:param name="containerModelClassNameId" value="<%= String.valueOf(PortalUtil.getClassNameId(containerModelClassName)) %>" />
				</portlet:renderURL>

				<%
				String taglibOnClick = renderResponse.getNamespace() + "restoreDialog('" + moveURL + "')";
				%>

				<liferay-ui:icon
					id="moveEntryButton"
					message="restore"
					onClick="<%= taglibOnClick %>"
					url="javascript:;"
				/>
			</c:if>

			<c:if test="<%= trashHandler.isDeletable() %>">
				<portlet:actionURL name="deleteEntries" var="deleteEntryURL">
					<portlet:param name="redirect" value="<%= trashDisplayContext.getViewContentRedirectURL() %>" />
					<portlet:param name="className" value="<%= trashRenderer.getClassName() %>" />
					<portlet:param name="classPK" value="<%= String.valueOf(trashRenderer.getClassPK()) %>" />
				</portlet:actionURL>

				<%
				String taglibOnClick = "if (confirm('" + UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") + "')) { submitForm(document.hrefFm, '" + deleteEntryURL.toString() + "'); }";
				%>

				<liferay-ui:icon
					id="removeEntryButton"
					message="delete"
					onClick="<%= taglibOnClick %>"
					url="javascript:;"
				/>
			</c:if>
		</c:otherwise>
	</c:choose>
</liferay-ui:icon-menu>