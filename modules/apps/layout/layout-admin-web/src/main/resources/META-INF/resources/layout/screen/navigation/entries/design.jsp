<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL", redirect);

if (Validator.isNull(backURL)) {
	backURL = PortalUtil.getLayoutFullURL(layoutsAdminDisplayContext.getSelLayout(), themeDisplay);
}

String portletResource = ParamUtil.getString(request, "portletResource");

Group selGroup = (Group)request.getAttribute(WebKeys.GROUP);

Group group = layoutsAdminDisplayContext.getGroup();

Layout selLayout = layoutsAdminDisplayContext.getSelLayout();

LayoutRevision layoutRevision = LayoutStagingUtil.getLayoutRevision(selLayout);
%>

<portlet:actionURL name="/layout_admin/edit_layout_design" var="editLayoutURL">
	<portlet:param name="mvcRenderCommandName" value="/layout_admin/edit_layout_design" />
</portlet:actionURL>

<liferay-frontend:edit-form
	action="<%= editLayoutURL %>"
	cssClass="c-pt-0"
	enctype="multipart/form-data"
	method="post"
	name="editLayoutFm"
	wrappedFormContent="<%= false %>"
>
	<aui:input name="redirect" type="hidden" value="<%= themeDisplay.getURLCurrent() %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="portletResource" type="hidden" value="<%= portletResource %>" />
	<aui:input name="groupId" type="hidden" value="<%= layoutsAdminDisplayContext.getGroupId() %>" />
	<aui:input name="liveGroupId" type="hidden" value="<%= layoutsAdminDisplayContext.getLiveGroupId() %>" />
	<aui:input name="stagingGroupId" type="hidden" value="<%= layoutsAdminDisplayContext.getStagingGroupId() %>" />
	<aui:input name="selPlid" type="hidden" value="<%= layoutsAdminDisplayContext.getSelPlid() %>" />
	<aui:input name="type" type="hidden" value="<%= selLayout.getType() %>" />

	<h2 class="c-mb-4 text-7"><liferay-ui:message key="design" /></h2>

	<liferay-frontend:edit-form-body>
		<c:if test="<%= layoutsAdminDisplayContext.isShowPublishedConfigurationMessage() %>">
			<clay:alert
				displayType="info"
			>
				<liferay-ui:message key="these-design-configurations-are-now-saved-in-a-draft-.to-fully-apply-them,-the-page-needs-to-be-published" />

				<clay:link
					href="<%= layoutsAdminDisplayContext.getPreviewCurrentDesignURL() %>"
					label="see-current-published-configuration-here"
				/>
			</clay:alert>
		</c:if>

		<c:if test="<%= layoutRevision != null %>">
			<aui:input name="layoutSetBranchId" type="hidden" value="<%= layoutRevision.getLayoutSetBranchId() %>" />
		</c:if>

		<c:if test="<%= !group.isLayoutPrototype() %>">
			<%@ include file="/error_layout_prototype_exception.jspf" %>
		</c:if>

		<liferay-frontend:form-navigator
			formModelBean="<%= selLayout %>"
			id="<%= FormNavigatorConstants.FORM_NAVIGATOR_ID_LAYOUT_DESIGN %>"
			showButtons="<%= false %>"
			type="<%= FormNavigatorConstants.FormNavigatorType.SHEET_SECTIONS %>"
		/>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<c:if test="<%= layoutsAdminDisplayContext.isShowButtons() %>">
			<liferay-frontend:edit-form-buttons
				redirect="<%= backURL %>"
			/>
		</c:if>
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>