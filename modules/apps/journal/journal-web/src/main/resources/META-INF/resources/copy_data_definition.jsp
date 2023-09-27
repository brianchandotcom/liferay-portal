<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

long ddmStructureId = ParamUtil.getLong(request, "ddmStructureId");

DDMStructure ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(ddmStructureId);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);
portletDisplay.setURLBackTitle(portletDisplay.getPortletDisplayName());

renderResponse.setTitle(LanguageUtil.format(request, "copy-x", ddmStructure.getName(locale), false));
%>

<portlet:actionURL name="/journal/copy_data_definition" var="copyDDMStructureURL">
	<portlet:param name="mvcPath" value="/copy_ddm_structure.jsp" />
</portlet:actionURL>

<liferay-frontend:edit-form
	action="<%= copyDDMStructureURL %>"
	method="post"
	name="fm"
>
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="ddmStructureId" type="hidden" value="<%= String.valueOf(ddmStructure.getStructureId()) %>" />

	<liferay-frontend:edit-form-body>
		<liferay-ui:error exception="<%= StructureNameException.class %>" message="please-enter-a-valid-name" />

		<aui:model-context bean="<%= ddmStructure %>" model="<%= DDMStructure.class %>" />

		<liferay-frontend:fieldset>
			<aui:input name="name" />

			<aui:input name="description" />

			<aui:input label="copy-templates" name="copyTemplates" type="checkbox" />
		</liferay-frontend:fieldset>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons
			redirect="<%= redirect %>"
			submitLabel="copy"
		/>
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>