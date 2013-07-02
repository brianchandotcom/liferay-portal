<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
long groupId = ParamUtil.getLong(request, "groupId");
boolean validate = ParamUtil.getBoolean(request, "validate", true);

String[] tempFileEntryNames = LayoutServiceUtil.getTempFileEntryNames(groupId, ExportImportHelper.TEMP_FOLDER_NAME);
%>

<liferay-ui:tabs
	names="new-import-process,all-import-processes"
	param="tabs2"
	refresh="<%= false %>"
>
	<liferay-ui:section>
		<div id="<portlet:namespace />exportImportOptions">
			<c:choose>
				<c:when test="<%= (tempFileEntryNames.length > 0) && !validate %>">
					<liferay-util:include page="/html/portlet/layouts_admin/import_layouts_resources.jsp" />
				</c:when>
				<c:otherwise>
					<liferay-util:include page="/html/portlet/layouts_admin/import_layouts_validation.jsp" />
				</c:otherwise>
			</c:choose>
		</div>
	</liferay-ui:section>

	<liferay-ui:section>

		<%
		String orderByCol = ParamUtil.getString(request, "orderByCol");
		String orderByType = ParamUtil.getString(request, "orderByType");

		if (Validator.isNotNull(orderByCol) && Validator.isNotNull(orderByType)) {
			portalPreferences.setValue(PortletKeys.BACKGROUND_TASK, "entries-order-by-col", orderByCol);
			portalPreferences.setValue(PortletKeys.BACKGROUND_TASK, "entries-order-by-type", orderByType);
		}
		else {
			orderByCol = portalPreferences.getValue(PortletKeys.BACKGROUND_TASK, "entries-order-by-col", "create-date");
			orderByType = portalPreferences.getValue(PortletKeys.BACKGROUND_TASK, "entries-order-by-type", "desc");
		}

		OrderByComparator orderByComparator = BackgroundTaskUtil.getBackgroundTaskOrderByComparator(orderByCol, orderByType);
		%>

		<liferay-ui:search-container
			emptyResultsMessage="no-export-processes-were-found"
			iteratorURL="<%= portletURL %>"
			orderByCol="<%= orderByCol %>"
			orderByComparator="<%= orderByComparator %>"
			orderByType="<%= orderByType %>"
			total="<%= BackgroundTaskLocalServiceUtil.getBackgroundTasksCount(groupId, LayoutExportBackgroundTaskExecutor.class.getName()) %>"
		>
			<liferay-ui:search-container-results
				results="<%= BackgroundTaskLocalServiceUtil.getBackgroundTasks(groupId, LayoutExportBackgroundTaskExecutor.class.getName(), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portal.model.BackgroundTask"
				modelVar="backgroundTask"
			>
				<liferay-ui:search-container-column-text
					name="user-name"
					value="<%= backgroundTask.getUserName() %>"
				/>

				<liferay-ui:search-container-column-text
					name="status"
					value="<%= LanguageUtil.get(pageContext, BackgroundTaskConstants.toLabel(backgroundTask.getStatus())) %>"
				/>

				<liferay-ui:search-container-column-text
					name="create-date"
					orderable="<%= true %>"
					orderableProperty="createDate"
					value="<%= dateFormatDateTime.format(backgroundTask.getCreateDate()) %>"
				/>

				<liferay-ui:search-container-column-text
					name="completion-date"
					orderable="<%= true %>"
					orderableProperty="completionDate"
					value="<%= backgroundTask.getCompletionDate() != null ? dateFormatDateTime.format(backgroundTask.getCompletionDate()) : StringPool.BLANK %>"
				/>

				<liferay-ui:search-container-column-text
					name="download"
				>

					<%
					List<FileEntry> attachmentsFileEntries = backgroundTask.getAttachmentsFileEntries();
					%>

					<c:choose>
						<c:when test="<%= !attachmentsFileEntries.isEmpty() %>">

							<%
							for (FileEntry fileEntry : attachmentsFileEntries) {
							%>

								<portlet:actionURL var="attachmentURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
									<portlet:param name="struts_action" value="/group_pages/get_background_task_attachment" />
									<portlet:param name="backgroundTaskId" value="<%= String.valueOf(backgroundTask.getBackgroundTaskId()) %>" />
									<portlet:param name="attachment" value="<%= fileEntry.getTitle() %>" />
								</portlet:actionURL>

								<%
								StringBundler sb = new StringBundler(4);

								sb.append(fileEntry.getTitle());
								sb.append(StringPool.OPEN_PARENTHESIS);
								sb.append(TextFormatter.formatStorageSize(fileEntry.getSize(), locale));
								sb.append(StringPool.CLOSE_PARENTHESIS);
								%>

								<liferay-ui:icon
									image="download"
									label="<%= true %>"
									message="<%= sb.toString() %>"
									url="<%= attachmentURL %>"
								/>

							<%
							}
							%>

						</c:when>
						<c:otherwise>

							<%
							Map taskContextMap = backgroundTask.getTaskContextMap();
							%>

							<liferay-ui:icon
								image="download"
								label="<%= true %>"
								message='<%= MapUtil.getString(taskContextMap, "fileName") %>'
							/>
						</c:otherwise>
					</c:choose>

				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text>
					<portlet:actionURL var="deleteBackgroundTaskURL">
						<portlet:param name="struts_action" value="/group_pages/delete_background_task" />
						<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
						<portlet:param name="backgroundTaskId" value="<%= String.valueOf(backgroundTask.getBackgroundTaskId()) %>" />
						<portlet:param name="redirect" value="<%= portletURL.toString() %>" />
					</portlet:actionURL>

					<liferay-ui:icon-delete
						label="true"
						url="<%= deleteBackgroundTaskURL %>"
					/>
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator />
		</liferay-ui:search-container>

	</liferay-ui:section>
</liferay-ui:tabs>

<c:if test='<%= SessionMessages.contains(liferayPortletRequest, "requestProcessed") %>'>
	<aui:script>
		var opener = Liferay.Util.getOpener();

		if (opener.<portlet:namespace />saveLayoutset) {
			Liferay.fire(
				'closeWindow',
				{
					id: '<portlet:namespace />importDialog'
				}
			);

			opener.<portlet:namespace />saveLayoutset('view');
		}
	</aui:script>
</c:if>