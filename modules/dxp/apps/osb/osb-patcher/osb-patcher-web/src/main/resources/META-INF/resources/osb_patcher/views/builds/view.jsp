<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/osb_patcher/views/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

long patcherBuildId = ParamUtil.getLong(request, "patcherBuildId");

PatcherBuild patcherBuild = PatcherBuildLocalServiceUtil.fetchPatcherBuild(patcherBuildId);

PatcherBuild latestPatcherBuild = null;

if (patcherConfiguration.patcherScanningEnabled() && !patcherBuild.getLatestSupportTicketBuild()) {
	latestPatcherBuild = PatcherBuildUtil.fetchPatcherBuildByLatestSupportTicketBuild(patcherBuild.getSupportTicket());
}
else if (!patcherBuild.getLatestKeyBuild()) {
	latestPatcherBuild = PatcherBuildUtil.fetchPatcherBuildByLatestKeyBuild(patcherBuild.getKey());
}
%>

<liferay-util:include page="/osb_patcher/views/header.jsp" servletContext="<%= application %>">
	<liferay-util:param name="title" value="view-build" />
	<liferay-util:param name="mvcRenderCommandName" value="/patcher/index_builds" />
</liferay-util:include>

<aui:model-context bean="<%= patcherBuild %>" model="<%= PatcherBuild.class %>" />

<portlet:renderURL var="viewPatcherBuildURL">
	<portlet:param name="mvcRenderCommandName" value="/patcher/view_builds" />
	<portlet:param name="patcherBuildId" value="<%= String.valueOf(patcherBuild.getPatcherBuildId()) %>" />

	<c:if test="<%= Validator.isNotNull(redirect) %>">
		<portlet:param name="redirect" value="<%= redirect %>" />
	</c:if>
</portlet:renderURL>

<c:if test="<%= (latestPatcherBuild != null) && !PatcherBuildUtil.isLatestPatcherBuild(patcherBuild) %>">
	<liferay-ui:message key="this-is-not-the-latest-build-version-view-the-latest-build-here" />

	<portlet:renderURL var="viewLatestPatcherBuildURL">
		<portlet:param name="mvcRenderCommandName" value="/patcher/view_builds" />
		<portlet:param name="patcherBuildId" value="<%= String.valueOf(latestPatcherBuild.getPatcherBuildId()) %>" />
		<portlet:param name="redirect" value="<%= viewPatcherBuildURL %>" />
	</portlet:renderURL>

	<a href="<%= viewLatestPatcherBuildURL %>">
		<%= latestPatcherBuild.getPatcherBuildId() %>
	</a>
</c:if>

<aui:field-wrapper label="create-date">
	<fmt:formatDate
		type="both"
		value="<%= patcherBuild.getCreateDate() %>"
	/>
</aui:field-wrapper>

<aui:field-wrapper label="modified-date">
	<fmt:formatDate
		type="both"
		value="<%= patcherBuild.getModifiedDate() %>"
	/>
</aui:field-wrapper>

<aui:field-wrapper label="status-date">
	<fmt:formatDate
		type="both"
		value="<%= patcherBuild.getStatusDate() %>"
	/>
</aui:field-wrapper>

<aui:field-wrapper label="created-by">
	<%= patcherBuild.getUserName() %>
</aui:field-wrapper>

<aui:field-wrapper label="status-updated-by">
	<%= patcherBuild.getStatusByUserName() %>
</aui:field-wrapper>

<aui:field-wrapper label="build-id">
	<%= patcherBuild.getPatcherBuildId() %>
</aui:field-wrapper>

<aui:field-wrapper label="version">
	<%= patcherBuild.getKeyVersion() %>
</aui:field-wrapper>

<aui:field-wrapper label="status">
	<liferay-ui:message key="<%= WorkflowConstants.getStatusLabel(patcherBuild.getStatus()) %>" />

	<c:if test="<%= patcherBuild.getStatus() == WorkflowConstants.STATUS_BUILD_FAILED %>">
		<clay:link
			href="<%= patcherConfiguration.troubleshootingURL() %>"
			target="_blank"
			title="troubleshooting-guide"
		/>
	</c:if>
</aui:field-wrapper>

<aui:field-wrapper label="qa-status">
	<liferay-ui:message key="<%= WorkflowConstants.getStatusLabel(patcherBuild.getQaStatus()) %>" />
</aui:field-wrapper>

<aui:field-wrapper label="qa-comments">
	<%= patcherBuild.getQaComments() %>
</aui:field-wrapper>

<aui:select disabled="<%= true %>" label="product-version" name="patcherProductVersionId" showEmptyOption="<%= true %>">

	<%
	for (PatcherProductVersion patcherProductVersion : PatcherProductVersionUtil.getPatcherProductVersions()) {
	%>

		<aui:option label="<%= patcherProductVersion.getName() %>" value="<%= patcherProductVersion.getPatcherProductVersionId() %>" />

	<%
	}
	%>

</aui:select>

<aui:select disabled="<%= true %>" label="project-version" name="patcherProjectVersionId" showEmptyOption="<%= false %>">

	<%
	for (PatcherProjectVersion patcherProjectVersion : PatcherProjectVersionLocalServiceUtil.getPatcherProjectVersions()) {
	%>

		<aui:option label="<%= patcherProjectVersion.getName() %>" value="<%= patcherProjectVersion.getPatcherProjectVersionId() %>" />

	<%
	}
	%>

</aui:select>

<%
PatcherFix patcherFix = PatcherFixLocalServiceUtil.fetchPatcherFix(patcherBuild.getPatcherFixId());
%>

<aui:field-wrapper label="git-hash">
	<c:if test="<%= patcherFix != null %>">
		<a href="<%= PatcherFixUtil.getPatcherFixGitHubURL(patcherFix.getPatcherFixId()) %>" target="_blank"><%= patcherFix.getGitHash() %></a>
	</c:if>
</aui:field-wrapper>

<aui:field-wrapper label="jenkins">

	<%
	for (Map<String, String> jenkinsResult : JenkinsUtil.getJenkinsResults(patcherBuild)) {
	%>

		<clay:link
			cssClass="nobr"
			href='<%= jenkinsResult.get("statusURL") %>'
			target="_blank"
			title='<%= jenkinsResult.get("jobName") %>'
		/>

	<%
	}
	%>

</aui:field-wrapper>

<%
String fileName = patcherBuild.getFileName();
%>

<aui:field-wrapper label="hotfix">
	<c:choose>
		<c:when test='<%= fileName.contains("/liferay-dxp-") %>'>
			<a href="https://releases-cdn.liferay.com/dxp/hotfix/<%= fileName %>" target="_blank">https://releases-cdn.liferay.com/dxp/hotfix/<%= fileName %></a><br />

			<pre>patching-tool install hotfix-<%= PatcherBuildUtil.getHotfixIdByFileName(fileName) %></pre>
			<pre>patching-tool.sh install hotfix-<%= PatcherBuildUtil.getHotfixIdByFileName(fileName) %></pre>
		</c:when>
		<c:otherwise>
			<a href="<%= patcherConfiguration.patcherBuildDownloadURL() %>/<%= fileName %>" target="_blank"><%= Validator.isNotNull(fileName) ? LanguageUtil.get(request, "download") : StringPool.BLANK %></a>
		</c:otherwise>
	</c:choose>
</aui:field-wrapper>

<c:if test="<%= Validator.isNotNull(patcherBuild.getSourceName()) %>">
	<aui:field-wrapper label="source-zip">
		<a href="<%= patcherConfiguration.patcherBuildDownloadURL() %>/<%= patcherBuild.getSourceName() %>" target="_blank"><liferay-ui:message key="download" /></a>
	</aui:field-wrapper>
</c:if>

<aui:input inputCssClass="osb-patcher-input-wide osb-patcher-read-only" label="tickets-list" name="patcherBuildName" readonly="<%= true %>" type="textarea" value="<%= patcherBuild.getName() %>" />

<c:if test="<%= (patcherBuild.getPatcherProductVersionId() != PatcherProductVersionUtil.getPatcherProductVersionId(PatcherProductVersionConstants.LABEL_PRODUCT_VERSION_PORTAL_6X)) && !patcherBuild.isChildBuild() && !StringUtil.equalsIgnoreCase(patcherBuild.getName(), patcherBuild.getInitialName()) %>">
	<aui:field-wrapper>
		<aui:input inputCssClass="osb-patcher-input-wide osb-patcher-read-only" label="original-tickets-list" name="patcherBuildInitialName" readonly="<%= true %>" type="textarea" value="<%= patcherBuild.getInitialName() %>" />

		<aui:field-wrapper>
			<liferay-ui:icon
				image="../api/exception"
				message=""
			/>

			<clay:link
				href="<%= patcherConfiguration.infoModifyTicketsListURL() %>"
				label="click-here-to-find-out-why-the-ticket-list-changed"
				target="_blank"
			/>
		</aui:field-wrapper>
	</aui:field-wrapper>
</c:if>

<%
PatcherAccount patcherAccount = PatcherAccountLocalServiceUtil.getPatcherAccount(patcherBuild.getPatcherAccountId());
%>

<portlet:renderURL var="viewPatcherAccountPatcherProductVersionURL">
	<portlet:param name="mvcRenderCommandName" value="/patcher/view_accounts" />
	<portlet:param name="patcherBuildAccountEntryCode" value="<%= patcherAccount.getAccountEntryCode() %>" />
	<portlet:param name="patcherProductVersionId" value="<%= String.valueOf(patcherBuild.getPatcherProductVersionId()) %>" />
</portlet:renderURL>

<aui:field-wrapper label="account-code">
	<clay:link
		href="<%= viewPatcherAccountPatcherProductVersionURL %>"
		title="<%= patcherAccount.getAccountEntryCode() %>"
	/>
</aui:field-wrapper>

<aui:input inputCssClass="osb-patcher-input-wide osb-patcher-read-only" name="supportTicket" readonly="<%= true %>" type="text" />

<aui:select disabled="<%= true %>" name="type">
	<aui:option label="<%= PatcherBuildConstants.LABEL_OFFICIAL %>" value="<%= PatcherBuildConstants.TYPE_OFFICIAL %>" />
	<aui:option label="<%= PatcherBuildConstants.LABEL_DEBUG %>" value="<%= PatcherBuildConstants.TYPE_DEBUG %>" />
	<aui:option label="<%= PatcherBuildConstants.LABEL_IGNORE %>" value="<%= PatcherBuildConstants.TYPE_IGNORE %>" />
</aui:select>

<aui:input disabled="<%= true %>" name="mergeOnly" type="checkbox" value="<%= PatcherBuildUtil.isMergeOnly(patcherBuild) %>" />

<aui:button-row>
	<c:if test="<%= PatcherBuildUtil.isLatestPatcherBuild(patcherBuild) && (patcherBuild.getType() != PatcherBuildConstants.TYPE_FIX_PACK) && !PatcherBuildRelUtil.hasParentPatcherBuilds(patcherBuild) %>">
		<portlet:renderURL var="editPatcherBuildURL">
			<portlet:param name="mvcRenderCommandName" value="/patcher/edit_builds" />
			<portlet:param name="patcherBuildId" value="<%= String.valueOf(patcherBuild.getPatcherBuildId()) %>" />
			<portlet:param name="redirect" value="<%= viewPatcherBuildURL %>" />
		</portlet:renderURL>

		<aui:button href="<%= editPatcherBuildURL %>" value="edit" />
	</c:if>

	<c:if test="<%= !windowState.equals(LiferayWindowState.POP_UP) && !PatcherBuildRelUtil.hasChildPatcherBuilds(patcherBuild) %>">
		<portlet:renderURL var="viewPatcherBuildPatcherFixesURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="mvcRenderCommandName" value="/patcher/view_fixes_builds" />
			<portlet:param name="patcherBuildId" value="<%= String.valueOf(patcherBuild.getPatcherBuildId()) %>" />
		</portlet:renderURL>

		<clay:button
			displayType="secondary"
			label='<%= LanguageUtil.get(request, "view-fixes") %>'
			onClick='<%= liferayPortletResponse.getNamespace() + "handleClick('" + UnicodeLanguageUtil.format(request, "view-fixes-for-build-id-x", patcherBuild.getPatcherBuildId()) + "', '" + viewPatcherBuildPatcherFixesURL + "');" %>'
		/>
	</c:if>

	<c:if test="<%= PatcherPermission.contains(permissionChecker, patcherBuild, PatcherActionKeys.CHILD_BUILDS, patcherBuild.getUserId()) && PatcherBuildRelUtil.hasChildPatcherBuilds(patcherBuild) %>">
		<portlet:renderURL var="viewChildPatcherBuildsURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="mvcRenderCommandName" value="/patcher/view_child_builds_builds" />
			<portlet:param name="patcherBuildId" value="<%= String.valueOf(patcherBuild.getPatcherBuildId()) %>" />
		</portlet:renderURL>

		<clay:button
			displayType="secondary"
			label='<%= LanguageUtil.get(request, "view-child-builds") %>'
			onClick='<%= liferayPortletResponse.getNamespace() + "handleClick('" + UnicodeLanguageUtil.format(request, "view-child-builds-for-build-id-x", patcherBuild.getPatcherBuildId()) + "', '" + viewChildPatcherBuildsURL + "');" %>'
		/>
	</c:if>

	<liferay-ui:icon-menu
		cssClass="osb-patcher-icon-menu"
	>
		<c:if test="<%= PatcherPermission.contains(permissionChecker, patcherBuild, PatcherActionKeys.EDIT, patcherBuild.getUserId()) && PatcherBuildUtil.isLatestPatcherBuild(patcherBuild) && (patcherBuild.getType() != PatcherBuildConstants.TYPE_FIX_PACK) %>">
			<portlet:renderURL var="createPatcherBuildTemplateURL">
				<portlet:param name="mvcRenderCommandName" value="/patcher/add_builds" />
				<portlet:param name="templatePatcherBuildId" value="<%= String.valueOf(patcherBuild.getPatcherBuildId()) %>" />
				<portlet:param name="redirect" value="<%= viewPatcherBuildURL %>" />
			</portlet:renderURL>

			<liferay-ui:icon
				image="edit"
				message="use-as-build-template"
				method="get"
				url="<%= createPatcherBuildTemplateURL %>"
			/>
		</c:if>

		<c:if test="<%= PatcherPermission.contains(permissionChecker, patcherBuild, PatcherActionKeys.EDIT_COMMENTS_FIELD, patcherBuild.getUserId()) && (patcherBuild.getType() != PatcherBuildConstants.TYPE_FIX_PACK) %>">
			<portlet:renderURL var="editPatcherBuildCommentsFieldURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
				<portlet:param name="mvcRenderCommandName" value="/patcher/edit_comments_field_builds" />
				<portlet:param name="patcherBuildId" value="<%= String.valueOf(patcherBuild.getPatcherBuildId()) %>" />
			</portlet:renderURL>

			<liferay-ui:icon
				image="edit"
				message="edit-engineer-comments"
				method="get"
				onClick='<%= liferayPortletResponse.getNamespace() + "handleClick('" + UnicodeLanguageUtil.format(request, "edit-engineer-comments-for-build-id-x", patcherBuild.getPatcherBuildId()) + "', '" + editPatcherBuildCommentsFieldURL + "');" %>'
				url="javascript:void(0);"
			/>
		</c:if>

		<c:if test="<%= PatcherPermission.contains(permissionChecker, patcherBuild, PatcherActionKeys.EDIT_QA_FIELDS, patcherBuild.getUserId()) && (patcherBuild.getType() != PatcherBuildConstants.TYPE_FIX_PACK) %>">
			<portlet:renderURL var="editPatcherBuildQAFieldsURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
				<portlet:param name="mvcRenderCommandName" value="/patcher/edit_qa_fields_builds" />
				<portlet:param name="patcherBuildId" value="<%= String.valueOf(patcherBuild.getPatcherBuildId()) %>" />
			</portlet:renderURL>

			<liferay-ui:icon
				image="edit"
				message="edit-qa-status"
				method="get"
				onClick='<%= liferayPortletResponse.getNamespace() + "handleClick('" + UnicodeLanguageUtil.format(request, "edit-qa-status-for-build-id-x", patcherBuild.getPatcherBuildId()) + "', '" + editPatcherBuildQAFieldsURL + "');" %>'
				url="javascript:void(0);"
			/>
		</c:if>

		<c:if test="<%= PatcherPermission.contains(permissionChecker, patcherBuild, PatcherActionKeys.SEND_REQUEST, patcherBuild.getUserId()) && JenkinsUtil.isValidJenkinsSetup() && JenkinsUtil.isValidSendDistJenkinsRequest(patcherBuild) && (patcherBuild.getType() != PatcherBuildConstants.TYPE_FIX_PACK) %>">
			<portlet:actionURL name="/patcher/build_builds" var="buildPatcherBuildURL">
				<portlet:param name="patcherBuildId" value="<%= String.valueOf(patcherBuild.getPatcherBuildId()) %>" />
				<portlet:param name="redirect" value="<%= viewPatcherBuildURL %>" />
			</portlet:actionURL>

			<liferay-ui:icon
				image="post"
				message="build"
				method="get"
				url="<%= buildPatcherBuildURL %>"
			/>
		</c:if>

		<c:if test="<%= patcherBuild.getStatus() == WorkflowConstants.STATUS_BUILD_COMPLETE %>">
			<portlet:actionURL name="/patcher/test_builds" var="testPatcherBuildURL">
				<portlet:param name="patcherBuildId" value="<%= String.valueOf(patcherBuild.getPatcherBuildId()) %>" />
				<portlet:param name="status" value="<%= String.valueOf(WorkflowConstants.STATUS_BUILD_QA_AUTOMATION_STARTED) %>" />
				<portlet:param name="redirect" value="<%= viewPatcherBuildURL %>" />
			</portlet:actionURL>

			<liferay-ui:icon
				image="post"
				message="test"
				method="get"
				url="<%= testPatcherBuildURL %>"
			/>

			<portlet:actionURL name="/patcher/test_builds" var="smokeTestPatcherBuildURL">
				<portlet:param name="patcherBuildId" value="<%= String.valueOf(patcherBuild.getPatcherBuildId()) %>" />
				<portlet:param name="status" value="<%= String.valueOf(WorkflowConstants.STATUS_BUILD_QA_AUTOMATION_STARTED_SMOKE_ONLY) %>" />
				<portlet:param name="redirect" value="<%= viewPatcherBuildURL %>" />
			</portlet:actionURL>

			<liferay-ui:icon
				image="post"
				message="smoke-test"
				method="get"
				url="<%= smokeTestPatcherBuildURL %>"
			/>
		</c:if>

		<c:if test="<%= patcherBuild.getStatus() == WorkflowConstants.STATUS_BUILD_COMPLETE %>">

			<%
			String releaseConfirmMessageKey = "this-patch-has-not-passed-qa-testing-are-you-sure-you-want-to-release-this-patch-to-the-customer";

			if (PatcherBuildUtil.isTestingPassed(patcherBuild)) {
				releaseConfirmMessageKey = "are-you-sure-you-want-to-release-this-patch-to-the-customer";
			}
			%>

			<portlet:actionURL name="/patcher/release_builds" var="releasePatcherBuildURL">
				<portlet:param name="patcherBuildId" value="<%= String.valueOf(patcherBuild.getPatcherBuildId()) %>" />
				<portlet:param name="status" value="<%= String.valueOf(WorkflowConstants.STATUS_BUILD_READY_TO_RELEASE) %>" />
				<portlet:param name="redirect" value="<%= viewPatcherBuildURL %>" />
			</portlet:actionURL>

			<liferay-ui:icon
				image="post"
				message="ready-for-release"
				method="get"
				onClick='<%= liferayPortletResponse.getNamespace() + "confirm('" + LanguageUtil.get(request, releaseConfirmMessageKey) + "', '" + releasePatcherBuildURL + "');" %>'
				url="javascript:void(0);"
			/>
		</c:if>

		<c:if test="<%= (patcherBuild.getStatus() == WorkflowConstants.STATUS_BUILD_COMPLETE) || (patcherBuild.getStatus() == WorkflowConstants.STATUS_BUILD_READY_TO_RELEASE) %>">

			<%
			String releaseConfirmMessageKey = "this-patch-has-not-passed-qa-testing-are-you-sure-you-want-to-release-this-patch-to-the-customer";

			if (PatcherBuildUtil.isTestingPassed(patcherBuild)) {
				releaseConfirmMessageKey = "are-you-sure-you-want-to-release-this-patch-to-the-customer";
			}
			%>

			<portlet:actionURL name="/patcher/release_builds" var="releasePatcherBuildURL">
				<portlet:param name="patcherBuildId" value="<%= String.valueOf(patcherBuild.getPatcherBuildId()) %>" />
				<portlet:param name="status" value="<%= String.valueOf(WorkflowConstants.STATUS_BUILD_RELEASED) %>" />
				<portlet:param name="redirect" value="<%= viewPatcherBuildURL %>" />
			</portlet:actionURL>

			<liferay-ui:icon
				image="post"
				message="release-manually"
				method="get"
				onClick='<%= liferayPortletResponse.getNamespace() + "confirm('" + LanguageUtil.get(request, releaseConfirmMessageKey) + "', '" + releasePatcherBuildURL + "');" %>'
				url="javascript:void(0);"
			/>

			<portlet:actionURL name="/patcher/release_builds" var="releasePatcherBuildURL">
				<portlet:param name="patcherBuildId" value="<%= String.valueOf(patcherBuild.getPatcherBuildId()) %>" />
				<portlet:param name="status" value="<%= String.valueOf(WorkflowConstants.STATUS_BUILD_RELEASED) %>" />
				<portlet:param name="releaseToHelpCenter" value="<%= Boolean.TRUE.toString() %>" />
				<portlet:param name="redirect" value="<%= viewPatcherBuildURL %>" />
			</portlet:actionURL>

			<liferay-ui:icon
				image="post"
				message="release-to-help-center"
				method="get"
				onClick='<%= liferayPortletResponse.getNamespace() + "confirm('" + LanguageUtil.get(request, releaseConfirmMessageKey) + "', '" + releasePatcherBuildURL + "');" %>'
				url="javascript:void(0);"
			/>
		</c:if>
	</liferay-ui:icon-menu>
</aui:button-row>

<%
PatcherViewBuildsDisplayContext patcherViewBuildsDisplayContext = new PatcherViewBuildsDisplayContext(request, renderRequest, renderResponse);

SearchContainer<PatcherBuild> patcherBuildSearchContainer = patcherViewBuildsDisplayContext.getSearchContainer();
%>

<c:if test="<%= patcherBuildSearchContainer.getTotal() > 1 %>">
	<aui:field-wrapper label="build-versions" />

	<liferay-ui:search-container
		searchContainer="<%= patcherBuildSearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.osb.patcher.model.PatcherBuild"
			escapedModel="<%= true %>"
			keyProperty="patcherBuildId"
			modelVar="patcherBuildKeyVersion"
		>
			<c:if test="<%= patcherBuild.getPatcherBuildId() == patcherBuildKeyVersion.getPatcherBuildId() %>">
				<liferay-ui:search-container-row-parameter
					name="className"
					value="selected"
				/>
			</c:if>

			<portlet:renderURL var="viewPatcherBuildKeyVersionURL">
				<portlet:param name="mvcRenderCommandName" value="/patcher/view_builds" />
				<portlet:param name="patcherBuildId" value="<%= String.valueOf(patcherBuildKeyVersion.getPatcherBuildId()) %>" />
			</portlet:renderURL>

			<liferay-ui:search-container-column-text
				href="<%= (patcherBuild.getPatcherBuildId() != patcherBuildKeyVersion.getPatcherBuildId()) ? viewPatcherBuildKeyVersionURL : null %>"
				name="build-id"
				property="patcherBuildId"
			/>

			<liferay-ui:search-container-column-text
				name="version"
				property="keyVersion"
			/>

			<liferay-ui:search-container-column-text
				cssClass="osb-patcher-user-display"
				name="created-by"
			>
				<liferay-ui:user-display
					displayStyle="<%= 1 %>"
					url="<%= PatcherUtil.getUserDisplayURL(themeDisplay, patcherBuildKeyVersion.getUserId()) %>"
					userId="<%= patcherBuildKeyVersion.getUserId() %>"
					userName="<%= patcherBuildKeyVersion.getUserName() %>"
				/>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="modified-date"
			>
				<fmt:formatDate
					type="both"
					value="<%= patcherBuildKeyVersion.getModifiedDate() %>"
				/>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="status"
				value="<%= LanguageUtil.get(request, WorkflowConstants.getStatusLabel(patcherBuildKeyVersion.getStatus())) %>"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
			paginate="<%= false %>"
		/>
	</liferay-ui:search-container>
</c:if>

<aui:script>
	function <portlet:namespace />handleClick(title, url) {
		Liferay.Util.openModal({
			title: title,
			url: url,
		});
	}

	Liferay.provide(
		window,
		'<portlet:namespace />confirm',
		function (message, url) {
			if (confirm(message)) {
				window.location.href = url;
			}
		}
	);

	YUI().ready('aui-popover', function (Y) {
		var align_points = [Y.WidgetPositionAlign.LC, Y.WidgetPositionAlign.RC];
		var tickets = document.getElementById(
			'_1_WAR_osbpatcherportlet_patcherBuildName'
		);
		var trigger = Y.one('#_1_WAR_osbpatcherportlet_patcherBuildName');

		Liferay.Patcher.getTicketLinksPopover(Y, align_points, tickets, trigger);
	});
</aui:script>