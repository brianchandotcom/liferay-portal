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

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
String synonymSetsRootElementId = renderResponse.getNamespace() + "-synonym-sets-root";
String synonymSets = (String)request.getAttribute("synonymSets");

String redirect = ParamUtil.getString(request, "redirect");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);
%>

<portlet:actionURL name="updateSynonymSet" var="updateSynonymSetURL">
	<portlet:param name="mvcPath" value="/view_synonym_sets.jsp" />
</portlet:actionURL>

<liferay-frontend:edit-form
	action="<%= updateSynonymSetURL %>"
	name="synonymSetsForm"
>
	<aui:input name="newSynonymSet" type="hidden" value="" />
	<aui:input name="originalSynonymSet" type="hidden" value="" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<liferay-frontend:edit-form-body>
		<div id="<%= synonymSetsRootElementId %>"></div>
	</liferay-frontend:edit-form-body>
</liferay-frontend:edit-form>

<aui:script require='<%= npmResolvedPackageName + "/js/index-synonym-sets.es as SynonymSets" %>'>
	SynonymSets.default(
		'<%= synonymSetsRootElementId %>',
		{
			formName: '<%= renderResponse.getNamespace() + "synonymSetsForm" %>',
			inputName: '<%= renderResponse.getNamespace() + "newSynonymSet" %>',
			originalInputName: '<%= renderResponse.getNamespace() + "originalSynonymSet" %>',
			synonymSets: '<%= synonymSets %>'
		}
	);
</aui:script>