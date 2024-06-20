<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
long cpSpecificationOptionId = ParamUtil.getLong(request, "cpSpecificationOptionId");
%>

<commerce-ui:modal-content
	submitButtonLabel='<%= LanguageUtil.get(request, "save") %>'
	title='<%= LanguageUtil.get(request, "create-a-new-picklist") %>'
>
	<aui:form method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "storeToParentForm(this.form);" %>' useNamespace="<%= false %>">
		<aui:input label='<%= LanguageUtil.get(request, "name") %>' localized="<%= true %>" name="name" required="<%= true %>" value='<%= ParamUtil.getString(request, "cpSpecificationOptionTitle") %>' />
	</aui:form>

	<liferay-frontend:component
		context='<%=
			HashMapBuilder.<String, Object>put(
				"cmd", ParamUtil.getString(request, Constants.CMD)
			).put(
				"cpSpecificationOptionId", cpSpecificationOptionId
			).put(
				"namespace", liferayPortletResponse.getNamespace()
			).build()
		%>'
		module="{CreateOrAssignCPSpecificationOptionListTypeDefinition} from commerce-product-options-web"
	/>
</commerce-ui:modal-content>