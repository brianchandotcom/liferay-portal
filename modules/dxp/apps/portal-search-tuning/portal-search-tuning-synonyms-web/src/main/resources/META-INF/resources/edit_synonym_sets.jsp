<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

<portlet:actionURL name="/synonyms/edit_synonym_sets" var="editSynonymSetURL">
	<portlet:param name="mvcPath" value="/view.jsp" />
</portlet:actionURL>

<liferay-frontend:edit-form
	action="<%= editSynonymSetURL %>"
	name="<%= editSynonymSetsDisplayContext.getFormName() %>"
>
	<aui:input name="<%= editSynonymSetsDisplayContext.getInputName() %>" type="hidden" value="" />
	<aui:input name="redirect" type="hidden" value="<%= editSynonymSetsDisplayContext.getRedirect() %>" />
	<aui:input name="synonymSetId" type="hidden" value="<%= editSynonymSetsDisplayContext.getSynonymSetId() %>" />

	<liferay-frontend:edit-form-body>
		<span aria-hidden="true" class="loading-animation"></span>

		<react:component
			module="js/SynonymSetsApp.es"
			props="<%= editSynonymSetsDisplayContext.getData() %>"
		/>
	</liferay-frontend:edit-form-body>
</liferay-frontend:edit-form>