<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/designer/init.jsp" %>

<%
String randomNamespace = StringUtil.randomId() + StringPool.UNDERLINE;

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

KaleoDefinitionVersion kaleoDefinitionVersion = (KaleoDefinitionVersion)row.getObject();

KaleoDefinition kaleoDefinition = kaleoDesignerDisplayContext.getKaleoDefinition(kaleoDefinitionVersion);

String duplicateTitle = kaleoDesignerDisplayContext.getDuplicateTitle(kaleoDefinition);

String kaleoNamespace = PortalUtil.getPortletNamespace(KaleoDesignerPortletKeys.KALEO_DESIGNER);
%>

<liferay-portlet:actionURL name="duplicateWorkflowDefinition" portletName="<%= KaleoDesignerPortletKeys.KALEO_DESIGNER %>" var="duplicateWorkflowDefinition">
	<portlet:param name="mvcPath" value="/designer/edit_kaleo_definition_version.jsp" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
</liferay-portlet:actionURL>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<liferay-portlet:renderURL portletName="<%= KaleoDesignerPortletKeys.KALEO_DESIGNER %>" var="editURL">
		<portlet:param name="mvcPath" value='<%= "/designer/edit_kaleo_definition_version.jsp" %>' />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="name" value="<%= kaleoDefinitionVersion.getName() %>" />
		<portlet:param name="draftVersion" value="<%= kaleoDefinitionVersion.getVersion() %>" />
	</liferay-portlet:renderURL>

	<c:choose>
		<c:when test="<%= KaleoDefinitionVersionPermission.contains(permissionChecker, kaleoDefinitionVersion, ActionKeys.UPDATE) %>">
			<liferay-ui:icon
				message="edit"
				method="get"
				url="<%= editURL %>"
			/>
		</c:when>
		<c:otherwise>
			<liferay-ui:icon
				message="view"
				url="<%= editURL %>"
			/>
		</c:otherwise>
	</c:choose>

	<liferay-ui:icon
		id='<%= "duplicate" + kaleoDefinition.getName() %>'
		message="duplicate"
		url="javascript:;"
	/>

	<c:if test="<%= KaleoDefinitionVersionPermission.contains(permissionChecker, kaleoDefinitionVersion, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= KaleoDefinitionVersion.class.getName() %>"
			modelResourceDescription="<%= kaleoDefinitionVersion.getName() %>"
			resourcePrimKey="<%= String.valueOf(kaleoDefinitionVersion.getKaleoDefinitionVersionId()) %>"
			var="permissionsURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			message="permissions"
			method="get"
			url="<%= permissionsURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:choose>
		<c:when test="<%= (kaleoDefinition != null) && kaleoDefinition.isActive() %>">
			<c:if test="<%= KaleoDefinitionVersionPermission.contains(permissionChecker, kaleoDefinitionVersion, ActionKeys.UPDATE) %>">
				<liferay-portlet:actionURL name="unpublishKaleoDefinitionVersion" portletName="<%= KaleoDesignerPortletKeys.CONTROL_PANEL_WORKFLOW %>" var="unpublishURL">
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="name" value="<%= kaleoDefinitionVersion.getName() %>" />
					<portlet:param name="version" value="<%= String.valueOf(kaleoDefinition.getVersion()) %>" />
				</liferay-portlet:actionURL>

				<liferay-ui:icon
					message="unpublish"
					url="<%= unpublishURL %>"
				/>
			</c:if>
		</c:when>
		<c:otherwise>
			<c:if test="<%= KaleoDefinitionVersionPermission.contains(permissionChecker, kaleoDefinitionVersion, ActionKeys.DELETE) %>">
				<liferay-portlet:actionURL name="deleteKaleoDefinitionVersion" portletName="<%= KaleoDesignerPortletKeys.KALEO_DESIGNER %>" var="deleteURL">
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="name" value="<%= kaleoDefinitionVersion.getName() %>" />
					<portlet:param name="draftVersion" value="<%= kaleoDefinitionVersion.getVersion() %>" />
				</liferay-portlet:actionURL>

				<liferay-ui:icon
					message="delete"
					onClick='<%= renderResponse.getNamespace() + "confirmDeleteDefinition('" + deleteURL + "'); return false;" %>'
					url="<%= deleteURL %>"
				/>
			</c:if>
		</c:otherwise>
	</c:choose>
</liferay-ui:icon-menu>

<div class="hide" id="<%= randomNamespace %>titleInputLocalized">
	<aui:form name='<%= randomNamespace + "form" %>' portletNamespace="<%= kaleoNamespace %>">
		<aui:input name="randomNamespace" type="hidden" value="<%= randomNamespace %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="name" type="hidden" value="<%= PortalUUIDUtil.generate() %>" />
		<aui:input name="content" type="hidden" value="<%= kaleoDefinition.getContent() %>" />
		<aui:input name="duplicatedDefinitionTitle" type="hidden" value="<%= kaleoDefinition.getTitle(LanguageUtil.getLanguageId(request)) %>" />
		<aui:input name="defaultDuplicationTitle" type="hidden" value="<%= duplicateTitle %>" />
		<aui:input name="duplicatedDefinitionName" type="hidden" value="<%= kaleoDefinition.getName() %>" />

		<aui:fieldset>
			<aui:col>
				<aui:input label="title" name='<%= randomNamespace + "title" %>' placeholder="<%= duplicateTitle %>" type="text" />
			</aui:col>

			<aui:col>
				<liferay-ui:message key="copy-does-not-include-revisions" />
			</aui:col>
		</aui:fieldset>
	</aui:form>
</div>

<aui:script use="liferay-kaleo-designer-dialogs">
	var title = '<liferay-ui:message key="duplicate-workflow" />';

	var confirmBeforeDuplicateDialog = A.rbind('confirmBeforeDuplicateDialog', Liferay.KaleoDesignerDialogs, '<%= duplicateWorkflowDefinition %>', title, '<%= randomNamespace %>', "<%= kaleoNamespace %>");

	Liferay.delegateClick('<portlet:namespace />duplicate<%= kaleoDefinition.getName() %>', confirmBeforeDuplicateDialog);
</aui:script>