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

<%
List<FaroProjectAdminDisplay> faroProjectAdminDisplays = (List<FaroProjectAdminDisplay>)request.getAttribute(FaroAdminWebKeys.FARO_PROJECT_ENTRIES);

if (faroProjectAdminDisplays == null) {
	faroProjectAdminDisplays = Collections.emptyList();
}

String tab = GetterUtil.getString(request.getAttribute("tab"), "details");

List<NavigationItem> navigationItems =
	new JSPNavigationItemList(pageContext) {
		{
			add(
				navigationItem -> {
					navigationItem.setActive(tab.equals("details"));
					navigationItem.setHref(currentURL);
					navigationItem.setLabel(LanguageUtil.get(request, "details"));
				});
		}
	};

request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
%>

<c:choose>
	<c:when test="<%= faroProjectAdminDisplays.size() == 1 %>">

		<%
		FaroProjectAdminDisplay faroProjectAdminDisplay = faroProjectAdminDisplays.get(0);
		%>

		<div class="sidebar-header">
			<h4><%= HtmlUtil.escape(faroProjectAdminDisplay.getName()) %></h4>
		</div>

		<clay:navigation-bar
			navigationItems="<%= navigationItems %>"
		/>

		<c:choose>
			<c:when test='<%= tab.equals("details") %>'>
				<div class="sidebar-body">
					<h5><liferay-ui:message key="group-id" /></h5>

					<p>
						<%= faroProjectAdminDisplay.getGroupId() %>
					</p>

					<h5><liferay-ui:message key="corp-project-uuid" /></h5>

					<p>
						<%= faroProjectAdminDisplay.getCorpProjectUuid() %>
					</p>

					<h5><liferay-ui:message key="corp-project-name" /></h5>

					<p>
						<%= faroProjectAdminDisplay.getCorpProjectName() %>
					</p>

					<h5><liferay-ui:message key="wedeploy-key" /></h5>

					<p>
						<%= faroProjectAdminDisplay.getWeDeployKey() %>
					</p>

					<h5><liferay-ui:message key="last-access-date" /></h5>

					<p>
						<%= faroProjectAdminDisplay.getLastAccessDate() %>
					</p>

					<h5><liferay-ui:message key="individuals-usage" /></h5>

					<p>
						<%= StringBundler.concat(faroProjectAdminDisplay.getIndividualsCount(), " / ", faroProjectAdminDisplay.getIndividualsLimit(), " (", faroProjectAdminDisplay.getIndividualsUsage(), "%)") %>
					</p>

					<h5><liferay-ui:message key="page-views-usage" /></h5>

					<p>
						<%= StringBundler.concat(faroProjectAdminDisplay.getPageViewsCount(), " / ", faroProjectAdminDisplay.getPageViewsLimit(), " (", faroProjectAdminDisplay.getPageViewsUsage(), "%)") %>
					</p>

					<h5><liferay-ui:message key="subscription" /></h5>

					<p>
						<%= faroProjectAdminDisplay.getSubscription() %>
					</p>

					<h5><liferay-ui:message key="serverLocation" /></h5>

					<p>
						<%= faroProjectAdminDisplay.getServerLocation() %>
					</p>
				</div>
			</c:when>
			<c:otherwise>
				<div class="sidebar-header">
					<h4><liferay-ui:message arguments="<%= faroProjectAdminDisplays.size() %>" key="x-items-are-selected" /></h4>
				</div>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<div class="sidebar-header">
			<h4><liferay-ui:message arguments="<%= (int)request.getAttribute(FaroAdminWebKeys.FARO_PROJECT_ENTRIES_COUNT) %>" key="x-items-are-selected" /></h4>
		</div>
	</c:otherwise>
</c:choose>