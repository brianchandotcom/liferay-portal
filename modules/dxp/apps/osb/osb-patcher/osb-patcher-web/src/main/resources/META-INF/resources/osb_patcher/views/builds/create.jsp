<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/osb_patcher/views/init.jsp" %>

<%
PatcherCreateBuildsDisplayContext patcherCreateBuildsDisplayContext = new PatcherCreateBuildsDisplayContext(request);

PatcherBuild patcherBuild = patcherCreateBuildsDisplayContext.getPatcherBuild();
%>

<liferay-util:include page="/osb_patcher/views/header.jsp" servletContext="<%= application %>">
	<liferay-util:param name="title" value="create-build" />
</liferay-util:include>

<aui:model-context bean="<%= patcherBuild %>" model="<%= PatcherBuild.class %>" />

<portlet:actionURL name="/patcher/add_builds" var="addPatcherBuildURL" />

<aui:form action="<%= addPatcherBuildURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "checkForExistingHotfix();" %>'>
	<div class="layout osb-patcher-layout-flex">
		<div class="layout-content">
			<div class="osb-patcher-align-center">
				<clay:row>
					<clay:col>
						<aui:input name="redirect" type="hidden" value="<%= patcherCreateBuildsDisplayContext.getRedirect() %>" />

						<aui:input name="useExistingHotfix" type="hidden" value="<%= false %>" />

						<aui:field-wrapper label="version">
							<%= patcherBuild.getKeyVersion() %>
						</aui:field-wrapper>

						<aui:select label="product-version" name="patcherProductVersionId" onChange='<%= liferayPortletResponse.getNamespace() + "productVersionOnChange(this.value);" %>' required="<%= true %>" showEmptyOption="<%= true %>">

							<%
							for (PatcherProductVersion patcherProductVersion : PatcherProductVersionUtil.getPatcherProductVersions()) {
							%>

								<aui:option label="<%= patcherProductVersion.getName() %>" value="<%= patcherProductVersion.getPatcherProductVersionId() %>" />

							<%
							}
							%>

						</aui:select>

						<aui:select label="project-version" name="patcherProjectVersionId" onChange='<%= liferayPortletResponse.getNamespace() + "projectVersionOnChange(this.value);" %>' required="<%= true %>" />

						<aui:input inputCssClass="osb-patcher-input-wide" label="account-code" name="patcherBuildAccountEntryCode" required="<%= true %>" type="text" value="<%= patcherCreateBuildsDisplayContext.getPatcherBuildAccountEntryCode() %>" />

						<aui:input helpMessage="the-support-ticket-must-contain-only-the-help-center-ticket-id" inputCssClass="osb-patcher-input-wide" name="supportTicket" type="text">
							<aui:validator name="number" />
						</aui:input>

						<aui:select name="type">
							<aui:option label="<%= PatcherBuildConstants.LABEL_OFFICIAL %>" value="<%= PatcherBuildConstants.TYPE_OFFICIAL %>" />
							<aui:option label="<%= PatcherBuildConstants.LABEL_DEBUG %>" value="<%= PatcherBuildConstants.TYPE_DEBUG %>" />
							<aui:option label="<%= PatcherBuildConstants.LABEL_IGNORE %>" value="<%= PatcherBuildConstants.TYPE_IGNORE %>" />
						</aui:select>

						<aui:input name="mergeOnly" type="checkbox" value="<%= patcherCreateBuildsDisplayContext.isMergeOnly() %>" />

						<aui:input name="smokeTestOnly" type="checkbox" wrapperCssClass="osb-patcher-display-none" />

						<aui:button-row>
							<aui:button type="submit" value="add" />

							<aui:button href="<%= patcherCreateBuildsDisplayContext.getRedirect() %>" value="cancel" />
						</aui:button-row>
					</clay:col>

					<clay:col
						cssClass="osb-patcher-content-half"
					>
						<aui:input inputCssClass="osb-patcher-input-wide" label="tickets-list" name="patcherBuildName" type="textarea" value="<%= patcherBuild.getName() %>" wrapperCssClass="osb-patcher-max-height" />
					</clay:col>

					<clay:col
						cssClass="mt-1"
					>
						<aui:field-wrapper>
							<aui:input inputCssClass="osb-patcher-input-wide" label="troubleshooting-ticket-suggestions" name="troubleshootingTicketList" type="textarea" />

							<aui:button-row cssClass="osb-patcher-button-row">
								<aui:button cssClass="osb-patcher-button" icon="icon-plus-sign" onClick='<%= liferayPortletResponse.getNamespace() + "troubleshootAddOnClick(this.value);" %>' title="Apply" />
							</aui:button-row>
						</aui:field-wrapper>

						<div class="d-none">
							<aui:field-wrapper>
								<aui:input inputCssClass="osb-patcher-input-wide" label="security-ticket-suggestions" name="securityTicketList" type="textarea" />

								<aui:button-row cssClass="osb-patcher-button-row">
									<aui:button cssClass="osb-patcher-button" icon="icon-plus-sign" onClick='<%= liferayPortletResponse.getNamespace() + "securityAddOnClick(this.value);" %>' title="Apply" />
								</aui:button-row>
							</aui:field-wrapper>

							<aui:field-wrapper>
								<aui:input inputCssClass="osb-patcher-input-wide" label="regression-ticket-suggestions" name="regressionTicketList" type="textarea" />

								<aui:button-row cssClass="osb-patcher-button-row">
									<aui:button cssClass="osb-patcher-button" icon="icon-plus-sign" onClick='<%= liferayPortletResponse.getNamespace() + "regressionAddOnClick(this.value);" %>' title="Apply" />
								</aui:button-row>
							</aui:field-wrapper>
						</div>
					</clay:col>
				</clay:row>
			</div>
		</div>
	</div>
</aui:form>

<aui:script>
	var mergeOnly = document.getElementById('<portlet:namespace />mergeOnly');
	var patcherBuildName = document.getElementById(
		'<portlet:namespace />patcherBuildName'
	);
	var patcherProductVersionId = document.getElementById(
		'<portlet:namespace />patcherProductVersionId'
	);
	var patcherProjectVersionId = document.getElementById(
		'<portlet:namespace />patcherProjectVersionId'
	);
	var regressionTextArea = document.getElementById(
		'<portlet:namespace />regressionTicketList'
	);
	var securityTextArea = document.getElementById(
		'<portlet:namespace />securityTicketList'
	);
	var select = document.getElementById(
		'<portlet:namespace />patcherProjectVersionId'
	);
	var troubleshootingTextArea = document.getElementById(
		'<portlet:namespace />troubleshootingTicketList'
	);
	var useExistingHotfix = document.getElementById(
		'<portlet:namespace />useExistingHotfix'
	);

	Liferay.provide(
		window,
		'<portlet:namespace />checkForExistingHotfix',
		function () {
			if (
				patcherProductVersionId.value ==
				<%= PatcherProductVersionUtil.getPatcherProductVersionId(PatcherProductVersionConstants.LABEL_PRODUCT_VERSION_QUARTERLY_RELEASES) %>
			) {
				getUseExistingHotfixValue();
			}
			else {
				submitForm(document.<portlet:namespace />fm);
			}
		},
		['aui-base']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />productVersionOnChange',
		function (productVersionId) {
			Liferay.Patcher.populateProjectVersionField(
				productVersionId,
				select,
				<%= PatcherProjectVersionUtil.getPatcherProjectVersionsJSONObject() %>
			);

			getTicketSuggestionFields();
		},
		['aui-base', 'liferay-portlet-url']
	);

	function getTicketSuggestionFields() {
		var projectVersionId = patcherProjectVersionId.value
			? patcherProjectVersionId.value
			: 0;

		const formData = Liferay.Util.objectToFormData({
			tickets: patcherBuildName.value,
			productVersionId: patcherProductVersionId.value,
			projectVersionId: projectVersionId,
		});

		Liferay.Util.fetch(
			'<liferay-portlet:resourceURL id="/patcher/get_ticket_suggestion_fields" />',
			{
				body: formData,
				method: 'POST',
			}
		)
			.then((response) => {
				return response.json();
			})
			.then((data) => {
				regressionTextArea.value = data.regression;
				securityTextArea.value = data.security;
				troubleshootingTextArea.value = String(
					responseData.troubleshooting
				).replaceAll(' ', '');
			});
	}

	function getUseExistingHotfixValue() {
		const formData = Liferay.Util.objectToFormData({
			projectVersionId: patcherProjectVersionId.value,
			tickets: patcherBuildName.value,
		});

		Liferay.Util.fetch(
			'<liferay-portlet:resourceURL id="/patcher/exists_hotfix" />',
			{
				body: formData,
				method: 'POST',
			}
		)
			.then((response) => {
				return response.json();
			})
			.then((data) => {
				if (data.hotfixExists == true) {
					var alertMessage =
						'<liferay-ui:message key="a-hotfix-with-these-parameters-is-already-available-would-you-like-to-use-it-click-ok-to-use-the-existing-hotfix-or-cancel-to-start-the-normal-build-process" />';

					if (confirm(alertMessage)) {
						mergeOnly.value = true;

						useExistingHotfix.value = true;
					}
				}

				submitForm(document.<portlet:namespace />fm);
			});
	}

	Liferay.provide(
		window,
		'<portlet:namespace />projectVersionOnChange',
		function (projectVersionId) {
			getTicketSuggestionFields();
		},
		['aui-base', 'liferay-portlet-url']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />troubleshootAddOnClick',
		function () {
			if (patcherBuildName.value) {
				patcherBuildName.value =
					patcherBuildName.value + ',' + troubleshootingTextArea.value;
			}
			else {
				patcherBuildName.value = troubleshootingTextArea.value;
			}
		},
		['aui-base']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />securityAddOnClick',
		function () {
			patcherBuildName.value =
				patcherBuildName.value + ',' + securityTextArea.value;
		},
		['aui-base']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />regressionAddOnClick',
		function () {
			patcherBuildName.value =
				patcherBuildName.value + ',' + regressionTextArea.value;
		},
		['aui-base']
	);

	AUI().ready(function () {
		var A = AUI();

		var productVersionId = A.one(
			'#<portlet:namespace />patcherProductVersionId'
		).val();

		Liferay.Patcher.populateProjectVersionField(
			productVersionId,
			select,
			<%= PatcherProjectVersionUtil.getPatcherProjectVersionsJSONObject() %>
		);

		var projectVersionId =
			<%= patcherCreateBuildsDisplayContext.getPatcherProjectVersionId() %>;

		if (projectVersionId > 0) {
			A.one('#<portlet:namespace />patcherProjectVersionId').val(
				projectVersionId
			);
		}

		YUI().use('event-valuechange', function (Y) {
			Y.one('#<portlet:namespace />patcherBuildName').on(
				'valuechange',
				function (e) {
					if (patcherProductVersionId && patcherProjectVersionId) {
						getTicketSuggestionFields();
					}
				}
			);
		});
	});

	YUI().ready('aui-popover', function (Y) {
		var align_points = [Y.WidgetPositionAlign.BL, Y.WidgetPositionAlign.BR];
		var tickets = document.getElementById(
			'<portlet:namespace />patcherBuildName'
		);
		var trigger = Y.one('#<portlet:namespace />patcherBuildName');

		Liferay.Patcher.getTicketLinksPopover(Y, align_points, tickets, trigger);
	});

	YUI().ready('aui-popover', function (Y) {
		var align_points = [Y.WidgetPositionAlign.LC, Y.WidgetPositionAlign.RC];
		var tickets = document.getElementById(
			'<portlet:namespace />troubleshootingTicketList'
		);
		var trigger = Y.one('#<portlet:namespace />troubleshootingTicketList');

		Liferay.Patcher.getTicketLinksPopover(Y, align_points, tickets, trigger);
	});
</aui:script>