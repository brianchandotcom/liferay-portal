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

<%
String redirect = ParamUtil.getString(request, "redirect");

long ddmStructureId = ParamUtil.getLong(request, "ddmStructureId");

DDMStructure ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(ddmStructureId);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(LanguageUtil.format(request, "copy-x", ddmStructure.getName(locale), false));
%>

<portlet:actionURL name="/journal/copy_ddm_structure" var="copyDDMStructureURL">
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

		<liferay-frontend:fieldset-group>
			<liferay-frontend:fieldset>
				<aui:input name="name" />

				<aui:input name="description" />

				<aui:input label="copy-templates" name="copyTemplates" type="checkbox" />
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" value="copy" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>