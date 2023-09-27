<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ViewSXPElementsDisplayContext viewSXPElementsDisplayContext = (ViewSXPElementsDisplayContext)request.getAttribute(SXPWebKeys.VIEW_SXP_ELEMENTS_DISPLAY_CONTEXT);
%>

<aui:form action="<%= viewSXPElementsDisplayContext.getPortletURL() %>" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= String.valueOf(viewSXPElementsDisplayContext.getPortletURL()) %>" />

	<frontend-data-set:headless-display
		apiURL="<%= viewSXPElementsDisplayContext.getAPIURL() %>"
		bulkActionDropdownItems="<%= viewSXPElementsDisplayContext.getBulkActionDropdownItems() %>"
		creationMenu="<%= viewSXPElementsDisplayContext.getCreationMenu() %>"
		fdsActionDropdownItems="<%= viewSXPElementsDisplayContext.getFDSActionDropdownItems() %>"
		formName="fm"
		id="<%= SXPBlueprintAdminFDSNames.SXP_ELEMENTS %>"
		propsTransformer="sxp_blueprint_admin/js/view_sxp_elements/ViewSXPElementsPropsTransformer"
		selectedItemsKey="id"
		selectionType="multiple"
		style="fluid"
	/>
</aui:form>

<div id="<portlet:namespace />addSXPElement">
	<react:component
		module="sxp_blueprint_admin/js/view_sxp_elements/AddSXPElementModal"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"defaultLocale", LocaleUtil.toLanguageId(LocaleUtil.getDefault())
			).put(
				"editSXPElementURL",
				PortletURLBuilder.createRenderURL(
					renderResponse
				).setMVCRenderCommandName(
					"/sxp_blueprint_admin/edit_sxp_element"
				).buildString()
			).put(
				"portletNamespace", liferayPortletResponse.getNamespace()
			).build()
		%>'
	/>
</div>

<liferay-frontend:component
	module="sxp_blueprint_admin/js/utils/openInitialSuccessToastHandler"
/>

<c:if test="<%= SessionErrors.contains(renderRequest, SXPElementReadOnlyException.class) %>">
	<aui:script>
		Liferay.Util.openToast({
			message:
				'<liferay-ui:message key="system-read-only-elements-cannot-be-deleted" />',
			type: 'danger',
		});
	</aui:script>
</c:if>