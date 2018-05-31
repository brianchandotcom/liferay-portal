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

<%@ include file="/admin/init.jsp" %>

<%
String displayStyle = kaleoFormsAdminDisplayContext.getDisplayStyle();

PortletURL portletURL = kaleoFormsAdminDisplayContext.getPortletURL();

portletURL.setParameter("displayStyle", displayStyle);

KaleoProcessSearch kaleoProcessSearch = kaleoFormsAdminDisplayContext.getKaleoProcessSearch(portletURL);

DisplayTerms displayTerms = kaleoProcessSearch.getDisplayTerms();
%>

<liferay-util:include page="/admin/navigation_bar.jsp" servletContext="<%= application %>" />

<liferay-util:include page="/admin/management_bar.jsp" servletContext="<%= application %>" />

<div class="container-fluid-1280" id="<portlet:namespace />formContainer">
	<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
		<aui:input name="kaleoProcessIds" type="hidden" />

		<liferay-ui:search-container
			id="kaleoProcess"
			rowChecker="<%= new EmptyOnClickRowChecker(renderResponse) %>"
			searchContainer="<%= kaleoProcessSearch %>"
			total="<%= KaleoProcessServiceUtil.searchCount(scopeGroupId, displayTerms.getKeywords()) %>"
		>
			<liferay-ui:search-container-results
				results="<%= KaleoProcessServiceUtil.search(scopeGroupId, displayTerms.getKeywords(), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess"
				keyProperty="kaleoProcessId"
				modelVar="process"
			>
				<portlet:renderURL var="rowURL">
					<portlet:param name="mvcPath" value='<%= "/admin/view_kaleo_process.jsp" %>' />
					<portlet:param name="redirect" value="<%= searchContainer.getIteratorURL().toString() %>" />
					<portlet:param name="kaleoProcessId" value="<%= String.valueOf(process.getKaleoProcessId()) %>" />
					<portlet:param name="displayStyle" value="<%= displayStyle %>" />
				</portlet:renderURL>

				<liferay-ui:search-container-column-text
					href="<%= rowURL %>"
					name="id"
					orderable="<%= false %>"
					property="kaleoProcessId"
				/>

				<liferay-ui:search-container-column-text
					href="<%= rowURL %>"
					name="name"
					orderable="<%= false %>"
					truncate="<%= true %>"
					value="<%= HtmlUtil.escape(process.getName(locale)) %>"
				/>

				<liferay-ui:search-container-column-text
					name="description"
					orderable="<%= false %>"
					truncate="<%= true %>"
					value="<%= HtmlUtil.escape(StringUtil.shorten(process.getDescription(locale), 100)) %>"
				/>

				<liferay-ui:search-container-column-date
					name="modified-date"
					orderable="<%= false %>"
					value="<%= process.getModifiedDate() %>"
				/>

				<liferay-ui:search-container-column-jsp
					align="right"
					cssClass="entry-action"
					path="/admin/kaleo_process_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				displayStyle="<%= displayStyle %>"
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>

<c:if test="<%= KaleoFormsPermission.contains(permissionChecker, scopeGroupId, KaleoFormsActionKeys.ADD_PROCESS) %>">
	<portlet:renderURL var="addProcessURL">
		<portlet:param name="mvcPath" value="/admin/edit_kaleo_process.jsp" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
	</portlet:renderURL>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item
			title='<%= LanguageUtil.get(request, "add") %>'
			url="<%= addProcessURL.toString() %>"
		/>
	</liferay-frontend:add-menu>
</c:if>

<%@ include file="/admin/export_kaleo_process.jspf" %>

<%
KaleoFormsUtil.cleanUpPortletSession(portletSession);
%>