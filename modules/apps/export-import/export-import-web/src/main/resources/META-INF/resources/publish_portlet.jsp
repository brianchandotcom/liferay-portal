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

<liferay-staging:defineObjects />

<%
String tabs3 = ParamUtil.getString(request, "tabs3", "new-publication-process");

String errorMessageKey = StringPool.BLANK;

Layout targetLayout = null;

if (!layout.isTypeControlPanel()) {
	long remoteLayoutPlid = 0;

	if ((liveGroup == null) || (stagingGroup == null) || (group.isLayout() && (stagingGroup.getLiveGroupId() == 0))) {
		errorMessageKey = "this-widget-is-placed-in-a-page-that-does-not-exist-in-the-live-site-publish-the-page-first";
	}
	else {
		try {
			if (stagingGroup.isLayout()) {
				targetLayout = LayoutLocalServiceUtil.getLayout(liveGroup.getClassPK());
			}
			else if (stagingGroup.isStagedRemotely()) {
				remoteLayoutPlid = StagingUtil.getRemoteLayoutPlid(user.getUserId(), stagingGroup.getGroupId(), layout.getPlid());
			}
			else {
				targetLayout = LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(layout.getUuid(), liveGroup.getGroupId(), layout.isPrivateLayout());
			}
		}
		catch (NoSuchLayoutException nsle) {
			errorMessageKey = "this-widget-is-placed-in-a-page-that-does-not-exist-in-the-live-site-publish-the-page-first";
		}

		if (targetLayout != null) {
			LayoutType layoutType = targetLayout.getLayoutType();

			if (!(layoutType instanceof LayoutTypePortlet) || !((LayoutTypePortlet)layoutType).hasPortletId(selPortlet.getPortletId())) {
				errorMessageKey = "this-widget-has-not-been-added-to-the-live-page-publish-the-page-first";
			}
		}
		else if (stagingGroup.isStagedRemotely() && (remoteLayoutPlid > 0)) {
			boolean remoteLayoutHasPortletId = StagingUtil.isRemoteLayoutHasPortletId(user.getUserId(), stagingGroup.getGroupId(), remoteLayoutPlid, selPortlet.getPortletId());

			if (!remoteLayoutHasPortletId) {
				errorMessageKey = "this-widget-has-not-been-added-to-the-live-page-publish-the-page-first";
			}
		}
	}
}
else if (group.isLayout()) {
	if ((liveGroup == null) || (stagingGroup == null) || (stagingGroup.getLiveGroupId() == 0)) {
		errorMessageKey = "a-widget-is-placed-in-this-page-of-scope-that-does-not-exist-in-the-live-site-publish-the-page-first";
	}
	else {
		try {
			targetLayout = LayoutLocalServiceUtil.getLayout(liveGroup.getClassPK());
		}
		catch (NoSuchLayoutException nsle) {
			errorMessageKey = "a-portlet-is-placed-in-this-page-of-scope-that-does-not-exist-in-the-live-site-publish-the-page-first";
		}
	}
}

if (!GroupPermissionUtil.contains(permissionChecker, themeDisplay.getScopeGroup(), ActionKeys.PUBLISH_PORTLET_INFO)) {
	errorMessageKey = "you-do-not-have-permission-to-access-the-requested-resource";
}
%>

<liferay-util:include page="/publish_portlet_navigation.jsp" servletContext="<%= application %>" />

<div class="portlet-export-import-container" id="<portlet:namespace />stagingPortletContainer">
	<liferay-util:include page="/export_import_error.jsp" servletContext="<%= application %>" />

	<c:choose>
		<c:when test="<%= Validator.isNotNull(errorMessageKey) %>">
			<liferay-ui:message key="<%= errorMessageKey %>" />
		</c:when>
		<c:when test="<%= (themeDisplay.getURLPublishToLive() != null) || layout.isTypeControlPanel() %>">
			<c:choose>
				<c:when test='<%= tabs3.equals("copy-from-live") || tabs3.equals("new-publication-process") %>'>
					<liferay-util:include page="/publish_portlet_publish_or_copy.jsp" servletContext="<%= application %>" />
				</c:when>
				<c:when test='<%= tabs3.equals("current-and-previous") %>'>
					<div class="portlet-export-import-publish-processes process-list" id="<portlet:namespace />publishProcesses">
						<liferay-util:include page="/publish_portlet_processes.jsp" servletContext="<%= application %>" />
					</div>
				</c:when>
			</c:choose>

			<aui:script use="liferay-export-import-export-import">
				<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="publishPortlet" var="publishProcessesURL">
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.PUBLISH %>" />
					<portlet:param name="<%= SearchContainer.DEFAULT_CUR_PARAM %>" value="<%= ParamUtil.getString(request, SearchContainer.DEFAULT_CUR_PARAM) %>" />
					<portlet:param name="<%= SearchContainer.DEFAULT_DELTA_PARAM %>" value="<%= ParamUtil.getString(request, SearchContainer.DEFAULT_DELTA_PARAM) %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(themeDisplay.getScopeGroupId()) %>" />
					<portlet:param name="portletResource" value="<%= portletResource %>" />
				</liferay-portlet:resourceURL>

				var exportImport = new Liferay.ExportImport({
					commentsNode: '#<%= PortletDataHandlerKeys.COMMENTS %>',
					deletionsNode: '#<%= PortletDataHandlerKeys.DELETIONS %>',
					form: document.<portlet:namespace />fm1,
					incompleteProcessMessageNode:
						'#<portlet:namespace />incompleteProcessMessage',
					locale: '<%= locale.toLanguageTag() %>',
					namespace: '<portlet:namespace />',
					processesNode: '#publishProcesses',
					processesResourceURL:
						'<%= HtmlUtil.escapeJS(publishProcessesURL.toString()) %>',
					rangeAllNode: '#rangeAll',
					rangeDateRangeNode: '#rangeDateRange',
					rangeLastNode: '#rangeLast',
					rangeLastPublishNode: '#rangeLastPublish',
					ratingsNode: '#<%= PortletDataHandlerKeys.RATINGS %>',
					timeZoneOffset: <%= timeZoneOffset %>
				});

				Liferay.component('<portlet:namespace />ExportImportComponent', exportImport);
			</aui:script>

			<aui:script>
				function <portlet:namespace />copyFromLive() {
					var exportImport = Liferay.component(
						'<portlet:namespace />ExportImportComponent'
					);

					var dateChecker = exportImport.getDateRangeChecker();

					if (
						dateChecker.validRange &&
						confirm(
							'<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-copy-from-live-and-update-the-existing-staging-widget-information") %>'
						)
					) {
						document.<portlet:namespace />fm1.<portlet:namespace /><%= Constants.CMD %>.value =
							'copy_from_live';

						submitForm(document.<portlet:namespace />fm1);
					}
					else if (!dateChecker.validRange) {
						exportImport.showNotification(dateChecker);
					}
				}

				function <portlet:namespace />publishToLive() {
					var exportImport = Liferay.component(
						'<portlet:namespace />ExportImportComponent'
					);

					var dateChecker = exportImport.getDateRangeChecker();

					if (
						dateChecker.validRange &&
						confirm(
							'<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-publish-to-live-and-update-the-existing-application-data") %>'
						)
					) {
						submitForm(document.<portlet:namespace />fm1);
					}
					else if (!dateChecker.validRange) {
						exportImport.showNotification(dateChecker);
					}
				}

				Liferay.Util.toggleRadio(
					'<portlet:namespace />portletMetaDataFilter',
					'<portlet:namespace />portletMetaDataList'
				);
				Liferay.Util.toggleRadio('<portlet:namespace />portletMetaDataAll', '', [
					'<portlet:namespace />portletMetaDataList'
				]);

				Liferay.Util.toggleRadio('<portlet:namespace />rangeAll', '', [
					'<portlet:namespace />startEndDate',
					'<portlet:namespace />rangeLastInputs'
				]);
				Liferay.Util.toggleRadio(
					'<portlet:namespace />rangeDateRange',
					'<portlet:namespace />startEndDate',
					'<portlet:namespace />rangeLastInputs'
				);
				Liferay.Util.toggleRadio('<portlet:namespace />rangeLastPublish', '', [
					'<portlet:namespace />startEndDate',
					'<portlet:namespace />rangeLastInputs'
				]);
				Liferay.Util.toggleRadio(
					'<portlet:namespace />rangeLast',
					'<portlet:namespace />rangeLastInputs',
					['<portlet:namespace />startEndDate']
				);
			</aui:script>
		</c:when>
	</c:choose>
</div>