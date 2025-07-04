<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/osb_patcher/views/init.jsp" %>

<%
PatcherEditFixPackFieldsDisplayContext patcherEditFixPackFieldsDisplayContext = new PatcherEditFixPackFieldsDisplayContext(request);

PatcherFix patcherFix = patcherEditFixPackFieldsDisplayContext.getPatcherFix();
%>

<aui:model-context bean="<%= patcherFix %>" model="<%= PatcherFix.class %>" />

<portlet:actionURL name="/patcher/set_fix_pack_fields_fixes" var="setFixPackFieldsURL" />

<liferay-frontend:edit-form
	action="<%= setFixPackFieldsURL %>"
	fluid="<%= true %>"
	method="post"
	name="fm"
	onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "setFixPackFields();" %>'
>
	<aui:input name="patcherFixId" type="hidden" value="<%= patcherFix.getPatcherFixId() %>" />

	<liferay-frontend:edit-form-body>
		<aui:input inputCssClass="osb-patcher-input-wide osb-patcher-read-only" label="content" name="patcherFixName" readonly="<%= true %>" type="textarea" value="<%= patcherFix.getName() %>" />

		<aui:field-wrapper label="fix-pack-schedule" />

		<aui:select disabled="<%= patcherEditFixPackFieldsDisplayContext.isDisabled() %>" name="fixPackStatus" showEmptyOption="<%= false %>">
			<aui:option label="<%= WorkflowConstants.LABEL_FIX_FIX_PACK_READY %>" value="<%= WorkflowConstants.STATUS_FIX_FIX_PACK_READY %>" />
			<aui:option label="<%= WorkflowConstants.LABEL_FIX_FIX_PACK_GENERATED %>" value="<%= WorkflowConstants.STATUS_FIX_FIX_PACK_GENERATED %>" />
			<aui:option label="<%= WorkflowConstants.LABEL_FIX_FIX_PACK_MULTI_COMPONENT %>" value="<%= WorkflowConstants.STATUS_FIX_FIX_PACK_MULTI_COMPONENT %>" />
			<aui:option label="<%= WorkflowConstants.LABEL_FIX_FIX_PACK_NOT_COMPATIBLE %>" value="<%= WorkflowConstants.STATUS_FIX_FIX_PACK_NOT_COMPATIBLE %>" />
			<aui:option label="<%= WorkflowConstants.LABEL_FIX_FIX_PACK_RESOLVED_BY_OTHER_FIXES %>" value="<%= WorkflowConstants.STATUS_FIX_FIX_PACK_RESOLVED_BY_OTHER_FIXES %>" />
			<aui:option label="<%= WorkflowConstants.LABEL_FIX_FIX_PACK_SINGLE_COMPONENT %>" value="<%= WorkflowConstants.STATUS_FIX_FIX_PACK_SINGLE_COMPONENT %>" />
			<aui:option label="<%= WorkflowConstants.LABEL_FIX_FIX_PACK_UNKNOWN_COMPONENT %>" value="<%= WorkflowConstants.STATUS_FIX_FIX_PACK_UNKNOWN_COMPONENT %>" />
		</aui:select>

		<aui:input disabled="<%= patcherEditFixPackFieldsDisplayContext.isDisabled() %>" name="dependencies" />

		<c:if test="<%= !patcherEditFixPackFieldsDisplayContext.isDisabled() %>">
			<aui:button-row>
				<portlet:actionURL name="/patcher/edit_fix_pack_fields_fixes" var="editPatcherFixFixPackFieldsURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
					<portlet:param name="patcherFixId" value="<%= String.valueOf(patcherFix.getPatcherFixId()) %>" />
				</portlet:actionURL>

				<aui:button onClick='<%= "form.action = '" + editPatcherFixFixPackFieldsURL + "';" %>' type="submit" value="reload-available-fix-packs" />
			</aui:button-row>

			<aui:input name="patcherFixPackIds" type="hidden" />

			<liferay-ui:input-move-boxes
				leftBoxName="currentPatcherFixPackFields"
				leftList="<%= patcherEditFixPackFieldsDisplayContext.getCurrentPatcherFixPacks() %>"
				leftReorder="false"
				leftTitle="current-fix-packs"
				rightBoxName="availablePatcherFixPackFields"
				rightList="<%= patcherEditFixPackFieldsDisplayContext.getAvailablePatcherFixPacks() %>"
				rightTitle="available-fix-packs"
			/>
		</c:if>

		<aui:input disabled="<%= patcherEditFixPackFieldsDisplayContext.isDisabled() %>" name="requirements" />
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons
			submitDisabled="<%= patcherEditFixPackFieldsDisplayContext.isDisabled() %>"
		/>
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<%
List<PatcherFixPack> patcherFixPacks = patcherEditFixPackFieldsDisplayContext.getPatcherFixPacks();
%>

<c:if test="<%= !patcherFixPacks.isEmpty() %>">
	<aui:field-wrapper label="fix-packs" />

	<liferay-ui:search-container
		total="<%= patcherFixPacks.size() %>"
	>
		<liferay-ui:search-container-results
			results="<%= patcherFixPacks %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.osb.patcher.model.PatcherFixPack"
			escapedModel="<%= true %>"
			keyProperty="patcherFixPackId"
			modelVar="patcherFixPack"
		>
			<portlet:renderURL var="viewPatcherFixPackURL">
				<portlet:param name="mvcRenderCommandName" value="/patcher/view_fix_packs" />
				<portlet:param name="patcherFixPackId" value="<%= String.valueOf(patcherFixPack.getPatcherFixPackId()) %>" />
			</portlet:renderURL>

			<liferay-ui:search-container-column-text
				href="<%= viewPatcherFixPackURL %>"
				name="name"
				value="<%= patcherFixPack.getName() %>"
			/>

			<%
			PatcherFixComponent patcherFixComponent = PatcherFixComponentLocalServiceUtil.getPatcherFixComponent(patcherFixPack.getPatcherFixComponentId());
			%>

			<liferay-ui:search-container-column-text
				name="component"
				value="<%= patcherFixComponent.getName() %>"
			/>

			<liferay-ui:search-container-column-text
				name="version"
				value="<%= String.valueOf(patcherFixPack.getVersion()) %>"
			/>

			<%
			PatcherProjectVersion patcherProjectVersion = PatcherProjectVersionLocalServiceUtil.getPatcherProjectVersion(patcherFixPack.getPatcherProjectVersionId());
			%>

			<liferay-ui:search-container-column-text
				name="project-version"
				value="<%= patcherProjectVersion.getName() %>"
			/>

			<liferay-ui:search-container-column-text
				name="status"
				value="<%= LanguageUtil.get(request, WorkflowConstants.getStatusLabel(patcherFixPack.getStatus())) %>"
			/>

			<liferay-ui:search-container-column-date
				name="released-date"
				value="<%= patcherFixPack.getReleasedDate() %>"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
			paginate="<%= false %>"
		/>
	</liferay-ui:search-container>
</c:if>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />setFixPackFields',
		function () {
			document.<portlet:namespace />fm.<portlet:namespace />patcherFixPackIds.value =
				Liferay.Util.listSelect(
					document.<portlet:namespace />fm
						.<portlet:namespace />currentPatcherFixPackFields
				);

			submitForm(document.<portlet:namespace />fm);
		},
		['liferay-util-list-fields']
	);
</aui:script>