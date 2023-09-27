<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

DDMTemplateVersion templateVersion = (DDMTemplateVersion)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<portlet:renderURL var="viewTemplateVersionURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
		<portlet:param name="mvcPath" value="/view_template_version.jsp" />
		<portlet:param name="redirect" value="<%= redirect %>" />
		<portlet:param name="templateVersionId" value="<%= String.valueOf(templateVersion.getTemplateVersionId()) %>" />
		<portlet:param name="formBuilderReadOnly" value="<%= Boolean.TRUE.toString() %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="view[action]"
		url="<%= viewTemplateVersionURL %>"
	/>

	<c:if test="<%= templateVersion.isApproved() %>">
		<liferay-portlet:actionURL name="/dynamic_data_mapping/revert_template" portletName="<%= DDMPortletKeys.DYNAMIC_DATA_MAPPING %>" var="revertURL">
			<portlet:param name="mvcPath" value="/edit_template.jsp" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="refererPortletName" value="<%= refererPortletName %>" />
			<portlet:param name="templateId" value="<%= String.valueOf(templateVersion.getTemplateId()) %>" />
			<portlet:param name="version" value="<%= templateVersion.getVersion() %>" />
		</liferay-portlet:actionURL>

		<liferay-ui:icon
			message="revert"
			url="<%= revertURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>