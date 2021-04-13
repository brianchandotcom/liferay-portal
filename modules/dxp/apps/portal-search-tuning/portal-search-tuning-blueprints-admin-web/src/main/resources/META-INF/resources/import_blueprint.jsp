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

<%@ include file="/init.jsp" %>

<portlet:actionURL name="<%= BlueprintsAdminMVCCommandNames.IMPORT %>" var="importURL">
	<portlet:param name="redirect" value='<%= ParamUtil.getString(request, "redirect") %>' />
</portlet:actionURL>

<react:component
	module="js/view_blueprints/ImportBlueprintModal"
	props='<%=
		HashMapBuilder.<String, Object>put(
			"formSubmitURL", importURL
		).put(
			"namespace", liferayPortletResponse.getNamespace()
		).build()
	%>'
/>