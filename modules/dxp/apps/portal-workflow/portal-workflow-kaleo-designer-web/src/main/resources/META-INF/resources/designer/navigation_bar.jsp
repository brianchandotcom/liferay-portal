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

<%@ include file="/designer/init.jsp" %>

<portlet:renderURL var="searchURL">
	<portlet:param name="mvcPath" value="/designer/view.jsp" />
</portlet:renderURL>

<aui:nav-bar cssClass="navbar-collapse-absolute navbar-expand-md navbar-underline navigation-bar navigation-bar-light" markupView="lexicon">
	<aui:nav cssClass="navbar-nav navbar-nav-expand">
		<aui:nav-item cssClass="nav-item-expand" label="workflow-definitions" selected="<%= true %>" />

		<li class="nav-item">
			<aui:form action="<%= searchURL.toString() %>" name="searchFm">
				<liferay-util:include page="/designer/kaleo_definition_search.jsp" servletContext="<%= application %>" />
			</aui:form>
		</li>
	</aui:nav>
</aui:nav-bar>