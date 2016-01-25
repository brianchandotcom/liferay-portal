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
long trashEntryId = ParamUtil.getLong(request, "trashEntryId");

long classNameId = ParamUtil.getLong(request, "classNameId");

String className = StringPool.BLANK;

if (classNameId != 0) {
	className = PortalUtil.getClassName(classNameId);
}

long classPK = ParamUtil.getLong(request, "classPK");

TrashEntry entry = null;

if (trashEntryId > 0) {
	entry = TrashEntryLocalServiceUtil.getEntry(trashEntryId);
}
else if (Validator.isNotNull(className) && (classPK > 0)) {
	entry = TrashEntryLocalServiceUtil.fetchEntry(className, classPK);
}

if (entry != null) {
	className = entry.getClassName();
	classPK = entry.getClassPK();
}

TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(className);

TrashRenderer trashRenderer = trashHandler.getTrashRenderer(classPK);
%>

<c:choose>
	<c:when test="<%= Validator.isNotNull(trashRenderer.renderActions(renderRequest, renderResponse)) %>">
		<liferay-util:include page="<%= trashRenderer.renderActions(renderRequest, renderResponse) %>" />
	</c:when>
	<c:otherwise>
		<div class="edit-toolbar" id="<portlet:namespace />entryToolbar">
			<div class="btn-group">
				<c:choose>
					<c:when test="<%= entry != null %>">
						<c:choose>
							<c:when test="<%= trashHandler.isRestorable(entry.getClassPK()) && !trashHandler.isInTrashContainer(entry.getClassPK()) %>">
								<aui:button icon="icon-undo" name="restoreEntryButton" value="restore" />

								<aui:script>
									<portlet:actionURL name="restoreEntries" var="restoreEntryURL">
										<portlet:param name="redirect" value="<%= currentURL %>" />
										<portlet:param name="trashEntryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
									</portlet:actionURL>

									AUI.$('#<portlet:namespace />restoreEntryButton').on(
										'click',
										function(event) {
											Liferay.fire(
												'<portlet:namespace />checkEntry',
												{
													trashEntryId: <%= entry.getEntryId() %>,
													uri: '<%= restoreEntryURL.toString() %>'
												}
											);
										}
									);
								</aui:script>
							</c:when>
							<c:when test="<%= !trashHandler.isRestorable(entry.getClassPK()) && trashHandler.isMovable() %>">

								<%
								String trashHandlerEntryContainerModelClassName = trashHandler.getContainerModelClassName(entry.getClassPK());
								%>

								<portlet:renderURL var="moveURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
									<portlet:param name="mvcPath" value="/view_container_model.jsp" />
									<portlet:param name="classNameId" value="<%= String.valueOf(entry.getClassNameId()) %>" />
									<portlet:param name="classPK" value="<%= String.valueOf(entry.getClassPK()) %>" />
									<portlet:param name="containerModelClassNameId" value="<%= String.valueOf(PortalUtil.getClassNameId(trashHandlerEntryContainerModelClassName)) %>" />
								</portlet:renderURL>

								<%
								String taglibOnClick = renderResponse.getNamespace() + "restoreDialog('" + moveURL + "')";
								%>

								<aui:button icon="icon-undo" name="restoreEntryButton" onClick="<%= taglibOnClick %>" value="restore" />
							</c:when>
						</c:choose>

						<c:if test="<%= trashHandler.isDeletable() %>">
							<aui:button icon="icon-remove" name="removeEntryButton" value="delete" />

							<aui:script>
								<portlet:actionURL name="deleteEntries" var="deleteEntryURL">
									<portlet:param name="redirect" value="<%= currentURL %>" />
									<portlet:param name="trashEntryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
								</portlet:actionURL>

								AUI.$('#<portlet:namespace />removeEntryButton').on(
									'click',
									function(event) {
										if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>')) {
											submitForm(document.hrefFm, '<%= deleteEntryURL.toString() %>');
										}
									}
								);
							</aui:script>
						</c:if>
					</c:when>
					<c:otherwise>
						<c:if test="<%= trashHandler.isMovable() %>">

							<%
							String containerModelClassName = trashHandler.getContainerModelClassName(classPK);

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

							<aui:button icon="icon-undo" name="moveEntryButton" onClick="<%= taglibOnClick %>" value="restore" />
						</c:if>

						<c:if test="<%= trashHandler.isDeletable() %>">
							<aui:button icon="icon-remove" name="removeEntryButton" value="delete" />

							<aui:script>
								<portlet:actionURL name="deleteEntries" var="deleteEntryURL">
									<portlet:param name="redirect" value="<%= currentURL %>" />
									<portlet:param name="className" value="<%= trashRenderer.getClassName() %>" />
									<portlet:param name="classPK" value="<%= String.valueOf(trashRenderer.getClassPK()) %>" />
								</portlet:actionURL>

								AUI.$('#<portlet:namespace />removeEntryButton').on(
									'click',
									function(event) {
										if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>')) {
											submitForm(document.hrefFm, '<%= deleteEntryURL.toString() %>');
										}
									}
								);
							</aui:script>
						</c:if>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</c:otherwise>
</c:choose>