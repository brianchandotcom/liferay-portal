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
taglib uri="http://liferay.com/tld/react" prefix="react" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.portal.search.tuning.synonyms.web.internal.constants.SynonymsPortletKeys" %><%@
page import="com.liferay.portal.search.tuning.synonyms.web.internal.display.context.EditSynonymSetsDisplayContext" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<%
EditSynonymSetsDisplayContext editSynonymSetsDisplayContext = (EditSynonymSetsDisplayContext)request.getAttribute(SynonymsPortletKeys.EDIT_SYNONYM_SET_DISPLAY_CONTEXT);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(editSynonymSetsDisplayContext.getBackURL());
%>

<portlet:actionURL name="updateSynonymSet" var="updateSynonymSetURL">
	<portlet:param name="mvcPath" value="/view_synonym_sets.jsp" />
</portlet:actionURL>

<liferay-frontend:edit-form
	action="<%= updateSynonymSetURL %>"
	name="<%= editSynonymSetsDisplayContext.getFormName() %>"
>
	<aui:input name="<%= editSynonymSetsDisplayContext.getInputName() %>" type="hidden" value="" />
	<aui:input name="<%= editSynonymSetsDisplayContext.getOriginalInputName() %>" type="hidden" value="" />
	<aui:input name="redirect" type="hidden" value="<%= editSynonymSetsDisplayContext.getRedirect() %>" />

	<liferay-frontend:edit-form-body>
		<span aria-hidden="true" class="loading-animation"></span>

		<react:component
			data="<%= editSynonymSetsDisplayContext.getData() %>"
			module="js/SynonymSetsApp.es"
		/>
	</liferay-frontend:edit-form-body>
</liferay-frontend:edit-form>