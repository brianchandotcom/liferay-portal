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

<%@ include file="/new_publication/publish_layouts_setup.jspf" %>

<portlet:renderURL var="basePortletURL">
	<portlet:param name="mvcRenderCommandName" value="processesList" />
</portlet:renderURL>

<aui:form action='<%= portletURL.toString() + "&etag=0&strip=0" %>' cssClass="lfr-export-dialog" method="post" name="exportPagesFm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "publishPages();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= cmd %>" />
	<aui:input name="exportImportConfigurationId" type="hidden" value="<%= exportImportConfigurationId %>" />
	<aui:input name="originalCmd" type="hidden" value="<%= cmd %>" />
	<aui:input name="currentURL" type="hidden" value="<%= currentURL %>" />
	<aui:input name="redirect" type="hidden" value="<%= basePortletURL %>" />
	<aui:input name="groupId" type="hidden" value="<%= stagingGroupId %>" />
	<aui:input name="privateLayout" type="hidden" value="<%= privateLayout %>" />
	<aui:input name="layoutSetBranchName" type="hidden" value="<%= layoutSetBranchName %>" />
	<aui:input name="lastImportUserName" type="hidden" value="<%= user.getFullName() %>" />
	<aui:input name="lastImportUserUuid" type="hidden" value="<%= String.valueOf(user.getUserUuid()) %>" />
	<aui:input name="treeId" type="hidden" value="<%= treeId %>" />
	<aui:input name="<%= PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS_ALL %>" type="hidden" value="<%= true %>" />
	<aui:input name="<%= PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL %>" type="hidden" value="<%= true %>" />
	<aui:input name="<%= PortletDataHandlerKeys.PORTLET_SETUP_ALL %>" type="hidden" value="<%= true %>" />
	<aui:input name="<%= PortletDataHandlerKeys.PORTLET_USER_PREFERENCES_ALL %>" type="hidden" value="<%= true %>" />

	<liferay-staging:process-error
		authException="<%= true %>"
		duplicateLockException="<%= true %>"
		illegalArgumentException="<%= true %>"
		layoutPrototypeException="<%= true %>"
		remoteExportException="<%= true %>"
		remoteOptionsException="<%= true %>"
		systemException="<%= true %>"
	/>

	<div id="<portlet:namespace />publishOptions">
		<div class="export-dialog-tree">
			<liferay-staging:incomplete-process-message
				localPublishing="<%= localPublishing %>"
			/>

			<aui:fieldset-group markupView="lexicon">
				<aui:fieldset>
					<c:choose>
						<c:when test="<%= exportImportConfiguration == null %>">
							<aui:input label="title" maxlength='<%= ModelHintsUtil.getMaxLength(ExportImportConfiguration.class.getName(), "name") %>' name="name" placeholder="process-name-placeholder" />
						</c:when>
						<c:otherwise>
							<aui:input label="title" maxlength='<%= ModelHintsUtil.getMaxLength(ExportImportConfiguration.class.getName(), "name") %>' name="name" value="<%= exportImportConfiguration.getName() %>" />
						</c:otherwise>
					</c:choose>
				</aui:fieldset>

				<aui:fieldset cssClass="options-group">
					<div class="sheet-section">
						<h3 class="sheet-subtitle"><liferay-ui:message key="date" /></h3>

						<%@ include file="/new_publication/publish_layouts_scheduler.jspf" %>
					</div>
				</aui:fieldset>

				<liferay-staging:deletions
					cmd="<%= Constants.PUBLISH %>"
					disableInputs="<%= configuredPublish %>"
					exportImportConfigurationId="<%= exportImportConfigurationId %>"
				/>

				<c:if test="<%= !group.isCompany() %>">
					<liferay-staging:select-pages
						action="<%= Constants.PUBLISH %>"
						disableInputs="<%= configuredPublish %>"
						exportImportConfigurationId="<%= exportImportConfigurationId %>"
						groupId="<%= groupId %>"
						privateLayout="<%= privateLayout %>"
						treeId="<%= treeId %>"
					/>
				</c:if>

				<liferay-staging:content
					cmd="<%= cmd %>"
					disableInputs="<%= configuredPublish %>"
					exportImportConfigurationId="<%= exportImportConfigurationId %>"
					type="<%= localPublishing ? Constants.PUBLISH_TO_LIVE : Constants.PUBLISH_TO_REMOTE %>"
				/>

				<liferay-staging:permissions
					action="<%= Constants.PUBLISH %>"
					descriptionCSSClass="permissions-description"
					disableInputs="<%= configuredPublish %>"
					exportImportConfigurationId="<%= exportImportConfigurationId %>"
					global="<%= group.isCompany() %>"
					labelCSSClass="permissions-label"
				/>

				<c:if test="<%= !localPublishing %>">
					<aui:fieldset collapsible="<%= true %>" cssClass="options-group" label="remote-live-connection-settings">
						<liferay-staging:remote-options
							disableInputs="<%= configuredPublish %>"
							exportImportConfigurationId="<%= exportImportConfigurationId %>"
							privateLayout="<%= privateLayout %>"
						/>
					</aui:fieldset>
				</c:if>
			</aui:fieldset-group>
		</div>

		<aui:button-row>
			<aui:button id="addButton" onClick='<%= renderResponse.getNamespace() + "schedulePublishEvent();" %>' value="add-event" />

			<aui:button id="publishButton" type="submit" value="<%= LanguageUtil.get(request, publishMessageKey) %>" />

			<aui:button href="<%= basePortletURL %>" type="cancel" />
		</aui:button-row>
	</div>
</aui:form>

<aui:script>
	function <portlet:namespace />publishPages() {
		var exportImport = Liferay.component(
			'<portlet:namespace />ExportImportComponent'
		);

		var deletePortletDataBeforeImportingCheckbox = document.getElementById(
			'<portlet:namespace />deletePortletDataBeforeImportingCheckbox'
		);

		var dateChecker = exportImport.getDateRangeChecker();

		if (dateChecker.validRange) {
			var form = document.<portlet:namespace />exportPagesFm;

			if (
				deletePortletDataBeforeImportingCheckbox &&
				deletePortletDataBeforeImportingCheckbox.checked
			) {
				confirm(
					'<%= UnicodeLanguageUtil.get(request, "delete-application-data-before-importing-confirmation") %>'
				) && submitForm(form);
			} else {
				submitForm(form);
			}
		} else {
			exportImport.showNotification(dateChecker);
		}
	}

	Liferay.Util.toggleRadio(
		'<portlet:namespace />allApplications',
		'<portlet:namespace />showChangeGlobalConfiguration',
		['<portlet:namespace />selectApplications']
	);
	Liferay.Util.toggleRadio(
		'<portlet:namespace />allContent',
		'<portlet:namespace />showChangeGlobalContent',
		['<portlet:namespace />selectContents']
	);
	Liferay.Util.toggleRadio(
		'<portlet:namespace />publishingEventNow',
		'<portlet:namespace />publishButton',
		['<portlet:namespace />selectSchedule', '<portlet:namespace />addButton']
	);
	Liferay.Util.toggleRadio(
		'<portlet:namespace />publishingEventSchedule',
		['<portlet:namespace />selectSchedule', '<portlet:namespace />addButton'],
		'<portlet:namespace />publishButton'
	);
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

<aui:script use="liferay-staging-processes-export-import">
	var exportImport = new Liferay.ExportImport({
		commentsNode: '#<%= PortletDataHandlerKeys.COMMENTS %>',
		deletionsNode: '#<%= PortletDataHandlerKeys.DELETIONS %>',
		form: document.<portlet:namespace />exportPagesFm,
		incompleteProcessMessageNode:
			'#<portlet:namespace />incompleteProcessMessage',
		locale: '<%= locale.toLanguageTag() %>',
		namespace: '<portlet:namespace />',
		pageTreeId: '<%= treeId %>',
		processesNode: '#publishProcesses',
		rangeAllNode: '#rangeAll',
		rangeDateRangeNode: '#rangeDateRange',
		rangeLastNode: '#rangeLast',
		rangeLastPublishNode: '#rangeLastPublish',
		ratingsNode: '#<%= PortletDataHandlerKeys.RATINGS %>',
		setupNode: '#<%= PortletDataHandlerKeys.PORTLET_SETUP_ALL %>',
		timeZoneOffset: <%= timeZoneOffset %>,
		userPreferencesNode:
			'#<%= PortletDataHandlerKeys.PORTLET_USER_PREFERENCES_ALL %>'
	});

	Liferay.component('<portlet:namespace />ExportImportComponent', exportImport);

	var clickHandler = function(event) {
		var dataValue = event.target.ancestor('li').attr('data-value');

		processDataValue(dataValue);
	};

	var processDataValue = function(dataValue) {
		var customConfiguration = A.one(
			'#<portlet:namespace />customConfiguration'
		);
		var savedConfigurations = A.one(
			'#<portlet:namespace />savedConfigurations'
		);

		if (dataValue === 'custom') {
			savedConfigurations.hide();

			customConfiguration.show();
		} else if (dataValue === 'saved') {
			customConfiguration.hide();

			savedConfigurations.show();
		}
	};

	var publishConfigurationButtons = A.one(
		'#<portlet:namespace />publishConfigurationButtons'
	);

	if (publishConfigurationButtons) {
		publishConfigurationButtons.delegate('click', clickHandler, 'li a');
	}
</aui:script>