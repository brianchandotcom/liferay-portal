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
String redirect = ParamUtil.getString(request, "redirect");

String redirectWindowState = ParamUtil.getString(request, "redirectWindowState");

String cmd = ParamUtil.getString(request, Constants.CMD, Constants.EXPORT);

long groupId = ParamUtil.getLong(request, "groupId");

Group group = null;

if (groupId > 0) {
	group = GroupLocalServiceUtil.getGroup(groupId);
}
else {
	group = (Group)request.getAttribute(WebKeys.GROUP);
}

Group liveGroup = group;

if (group.isStagingGroup()) {
	liveGroup = group.getLiveGroup();
}

long liveGroupId = ParamUtil.getLong(request, "liveGroupId", liveGroup.getGroupId());

boolean privateLayout = ParamUtil.getBoolean(request, "privateLayout");

String rootNodeName = ParamUtil.getString(request, "rootNodeName");

List<Portlet> portletsList = new ArrayList<Portlet>();
Set<String> portletIdsSet = new HashSet<String>();

for (Layout curLayout : LayoutLocalServiceUtil.getLayouts(liveGroupId, privateLayout)) {
	if (curLayout.isTypePortlet()) {
		LayoutTypePortlet curLayoutTypePortlet = (LayoutTypePortlet)curLayout.getLayoutType();

		for (String portletId : curLayoutTypePortlet.getPortletIds()) {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletId);

			if (portlet == null) {
				continue;
			}

			PortletDataHandler portletDataHandler = portlet.getPortletDataHandlerInstance();

			if ((portletDataHandler != null) && !portletIdsSet.contains(portlet.getRootPortletId())) {
				portletIdsSet.add(portlet.getRootPortletId());

				portletsList.add(portlet);
			}
		}
	}
}

List<Portlet> alwaysExportablePortlets = LayoutExporter.getAlwaysExportablePortlets(company.getCompanyId());

for (Portlet alwaysExportablePortlet : alwaysExportablePortlets) {
	if (!portletIdsSet.contains(alwaysExportablePortlet.getRootPortletId())) {
		portletIdsSet.add(alwaysExportablePortlet.getRootPortletId());

		portletsList.add(alwaysExportablePortlet);
	}
}

portletsList = ListUtil.sort(portletsList, new PortletTitleComparator(application, locale));

String[] uploadedFiles = LayoutServiceUtil.getTempFileEntryNames(groupId, ImportLayoutsAction.class.getName());
%>

<div id="<portlet:namespace />exportImportOptions">
	<aui:form cssClass="lfr-export-dialog" encoding='<%= (cmd.equals(Constants.VALIDATE)) ? "multipart/form-data" : StringPool.BLANK %>' method="post" name="fm1">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= cmd %>" />

		<c:choose>
			<c:when test="<%= cmd.equals(Constants.EXPORT) %>">
				<%@ include file="/html/portlet/layouts_admin/export_options.jspf" %>

				<aui:button-row>
					<aui:button type="submit" value="export" />
				</aui:button-row>
			</c:when>
			<c:when test="<%= cmd.equals(Constants.IMPORT) && (uploadedFiles.length > 0) %>">
				<liferay-util:include page="/html/portlet/layouts_admin/export_import_resources.jsp" />
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="<%= (layout.getGroupId() != groupId) || (layout.isPrivateLayout() != privateLayout) %>">
						<liferay-ui:message key="import-a-lar-file-to-overwrite-the-selected-data" />

						<div class="lfr-dynamic-uploader">
							<div class="lfr-upload-container" id="<portlet:namespace />fileUpload"></div>
						</div>

						<aui:button-row>
							<aui:button type="submit" value="continue" />
						</aui:button-row>

						<%
						Date expirationDate = new Date(System.currentTimeMillis() + PropsValues.SESSION_TIMEOUT * Time.MINUTE);

						Ticket ticket = TicketLocalServiceUtil.addTicket(user.getCompanyId(), User.class.getName(), user.getUserId(), TicketConstants.TYPE_IMPERSONATE, null, expirationDate, new ServiceContext());
						%>

						<aui:script use="liferay-upload">
							var liferayUpload = new Liferay.Upload(
								{
									allowedFileTypes: '.lar',
									boundingBox: '#<portlet:namespace />fileUpload',
									deleteFile: '<liferay-portlet:actionURL doAsUserId="<%= user.getUserId() %>"><portlet:param name="struts_action" value="/layouts_admin/import_layouts" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE_TEMP %>" /></liferay-portlet:actionURL>&ticketKey=<%= ticket.getKey() %><liferay-ui:input-permissions-params modelName="<%= Group.class.getName() %>" />',
									fileDescription: '<%= StringUtil.merge(PrefsPropsUtil.getStringArray(PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA)) %>',
									maxFileSize: '<%= PrefsPropsUtil.getLong(PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE) %> B',
									metadataContainer: '#<portlet:namespace />commonFileMetadataContainer',
									metadataExplanationContainer: '#<portlet:namespace />metadataExplanationContainer',
									multipleFiles: false,
									namespace: '<portlet:namespace />',
									tempFileURL: {
										method: Liferay.Service.bind('/layout/get-temp-file-entry-names'),
										params: {
											groupId: <%= scopeGroupId %>,
											tempFolderName: '<%= ExportImportUtil.TEMP_FOLDER_NAME %>'
										}
									},
									uploadFile: '<liferay-portlet:actionURL doAsUserId="<%= user.getUserId() %>"><portlet:param name="struts_action" value="/layouts_admin/import_layouts" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD_TEMP %>" /></liferay-portlet:actionURL>&ticketKey=<%= ticket.getKey() %><liferay-ui:input-permissions-params modelName="<%= Group.class.getName() %>" />'
								}
							);

							liferayUpload.set('strings.uploadsCompleteText', '<liferay-ui:message key="the-file-is-ready-to-be-imported" />');
							liferayUpload.set('strings.pendingFileText', '<liferay-ui:message key="this-file-has-been-previously-uploaded-but-not-actually-imported" />');
						</aui:script>
					</c:when>
					<c:otherwise>
						<liferay-ui:message key="import-from-within-the-target-site-can-cause-conflicts" />
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
	</aui:form>
</div>

<aui:script use="aui-base,aui-loading-mask-deprecated,json-stringify,aui-io-plugin">
	var form = A.one('#<portlet:namespace />fm1');

	form.on(
		'submit',
		function(event) {
			event.halt();

			<c:choose>
				<c:when test="<%= cmd.equals(Constants.EXPORT) %>">
					<c:if test="<%= !group.isLayoutPrototype() %>">
						var layoutsExportTreeOutput = A.one('#<portlet:namespace />layoutsExportTreeOutput');

						if (layoutsExportTreeOutput) {
							var treeView = layoutsExportTreeOutput.getData('treeInstance');

							var layoutIds = [];

							var regexLayoutId = /layoutId_(\d+)/;

							treeView.eachChildren(
								function(item, index, collection) {
									if (item.isChecked()) {
										var match = regexLayoutId.exec(item.get('id'));

										if (match) {
											layoutIds.push(
												{
													includeChildren: !item.hasChildNodes(),
													layoutId: match[1]
												}
											);
										}
									}
								},
								true
							);

							var layoutIdsInput = A.one('#<portlet:namespace />layoutIds');

							if (layoutIdsInput) {
								layoutIdsInput.val(A.JSON.stringify(layoutIds));
							}
						}
					</c:if>

					<portlet:actionURL var="exportPagesURL">
						<portlet:param name="struts_action" value="/layouts_admin/export_layouts" />
						<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
						<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
						<portlet:param name="exportLAR" value="<%= Boolean.TRUE.toString() %>" />
					</portlet:actionURL>

					submitForm(form, '<%= exportPagesURL + "&etag=0&strip=0" %>', false);
				</c:when>
				<c:otherwise>
					<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" var="importPagesURL">
						<portlet:param name="struts_action" value="/layouts_admin/import_layouts" />
						<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
						<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
					</liferay-portlet:resourceURL>

					var exportImportOptions = A.one('#<portlet:namespace />exportImportOptions');

					exportImportOptions.plug(
						A.Plugin.IO,
						{
							form: {
								id: '<portlet:namespace />fm1'
							},
							uri: '<%= importPagesURL %>'
						}
					);
				</c:otherwise>
			</c:choose>
		}
	);

	var toggleHandlerControl = function(item, index, collection) {
		var container = item.ancestor('.handler-control').one('ul');

		if (container) {
			var checked = item.get('checked');

			container.toggle(checked);

			container.all(':checkbox').attr('checked', checked);
		}
	};

	var checkboxes = A.all('.handler-control :checkbox');

	checkboxes.filter(':not(:checked)').each(toggleHandlerControl);

	checkboxes.detach('click');

	checkboxes.on(
		'click',
		function(event) {
			toggleHandlerControl(event.currentTarget);
		}
	);
</aui:script>