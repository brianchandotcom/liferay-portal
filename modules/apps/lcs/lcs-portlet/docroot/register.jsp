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
List<LCSProject> lcsProjects = LCSProjectServiceUtil.getUserManageableLCSProjects(liferayPortletRequest);
%>

<h3><liferay-ui:message arguments="<%= 3 %>" key="registration-step-x-3" /></h3>

<portlet:actionURL name="resetCredentials" var="resetCredentialsURL" />

<c:choose>
	<c:when test='<%= SessionErrors.contains(liferayPortletRequest, "generalPluginAccess") %>'>
		<div class="alert alert-danger lcs-alert">
			<liferay-ui:message key="an-error-occurred-while-accessing-liferay-connected-services" />
		</div>
	</c:when>
	<c:when test="<%= (lcsProjects == null) || lcsProjects.isEmpty() %>">
		<div class="alert alert-warning lcs-alert">
			<liferay-ui:message arguments="<%= LCSUtil.getLCSPortalURL() %>" key="please-download-and-install-the-latest-version-of-liferay-connected-services-client" />
		</div>

		<aui:button-row>
			<aui:button href="<%= resetCredentialsURL %>" value="back" />
		</aui:button-row>
	</c:when>
	<c:when test="<%= ClusterExecutorUtil.isEnabled() && !ClusterNodeUtil.isFirstClusterNode() %>">
		<div class="alert alert-info lcs-alert">
			<liferay-ui:message key="this-cluster-node-is-not-registered-in-liferay-connected-services-but-is-part-of-registered-cluster" />
		</div>

		<portlet:actionURL name="addSiblingLCSClusterNode" var="addSiblingLCSClusterNodeURL" />

		<aui:form action="<%= addSiblingLCSClusterNodeURL %>" name="registrationFm">
			<aui:button-row>
				<aui:button href="<%= resetCredentialsURL %>" value="back" />

				<aui:button cssClass="btn-success" name="register" type="submit" value="register" />
			</aui:button-row>
		</aui:form>
	</c:when>
	<c:otherwise>
		<div class="alert alert-info lcs-alert">
			<liferay-ui:message key="please-add-information-about-your-server" />
		</div>

		<portlet:actionURL name="addLCSClusterNode" var="addLCSClusterNodeURL" />

		<aui:form action="<%= addLCSClusterNodeURL %>" name="registrationFm">
			<c:choose>
				<c:when test='<%= SessionErrors.contains(liferayPortletRequest, "duplicateLCSClusterEntryName") %>'>
					<div class="alert alert-danger lcs-alert">
						<liferay-ui:message key="please-enter-an-unique-environment-name" />
					</div>
				</c:when>
				<c:when test='<%= SessionErrors.contains(liferayPortletRequest, "duplicateLCSClusterNodeName") %>'>
					<div class="alert alert-danger lcs-alert">
						<liferay-ui:message key="please-enter-an-unique-server-name" />
					</div>
				</c:when>
				<c:when test='<%= SessionErrors.contains(liferayPortletRequest, "noSuchLCSSubscriptionEntry") %>'>
					<div class="alert alert-warning lcs-alert">
						<liferay-ui:message key="exceeded-subscription-number" />
					</div>
				</c:when>
				<c:when test='<%= SessionErrors.contains(liferayPortletRequest, "requiredLCSClusterEntryName") %>'>
					<div class="alert alert-danger lcs-alert">
						<liferay-ui:message key="environment-name-is-required" />
					</div>
				</c:when>
				<c:when test='<%= SessionErrors.contains(liferayPortletRequest, "requiredLCSClusterNodeName") %>'>
					<div class="alert alert-danger lcs-alert">
						<liferay-ui:message key="server-name-is-required" />
					</div>
				</c:when>
			</c:choose>

			<aui:input name="addLCSClusterEntry" type="hidden" value="<%= false %>" />
			<aui:input name="newLCSClusterEntryDescription" type="hidden" />
			<aui:input name="newLCSClusterEntryLocation" type="hidden" />
			<aui:input name="newLCSClusterEntryName" type="hidden" />

			<aui:select label="project" name="lcsProjectId">

				<%
				for (LCSProject lcsProject : lcsProjects) {
				%>

					<aui:option label="<%= HtmlUtil.escape(lcsProject.getName()) %>" value="<%= lcsProject.getLcsProjectId() %>" />

				<%
				}
				%>

			</aui:select>

			<%
			LCSProject lcsProject = lcsProjects.get(0);

			long lcsProjectId = lcsProject.getLcsProjectId();

			List<LCSClusterEntry> lcsClusterEntries = LCSClusterEntryServiceUtil.getLCSProjectManageableLCSClusterEntries(lcsProjectId);

			boolean lcsAdministratorLCSRole = LCSRoleServiceUtil.hasUserLCSAdministratorLCSRole(lcsProjectId);
			%>

			<aui:field-wrapper cssClass="lcs-environment-container" helpMessage="environment-help" label="environment-required">
				<div class="lcs-environment-input-wrapper" id="<portlet:namespace />lcsClusterEntryInputWrapper">
					<c:choose>
						<c:when test="<%= ClusterExecutorUtil.isEnabled() %>">
							<liferay-ui:message key="please-create-a-new-environment" />
						</c:when>
						<c:when test="<%= !ClusterExecutorUtil.isEnabled() && lcsClusterEntries.isEmpty() %>">
							<liferay-ui:message key="there-are-no-environments-created-yet" />
						</c:when>
						<c:otherwise>
							<aui:select id="lcsClusterEntryId" label="" name="lcsClusterEntryId">

								<%
								for (LCSClusterEntry lcsClusterEntry : lcsClusterEntries) {
								%>

									<aui:option value="<%= lcsClusterEntry.getLcsClusterEntryId() %>">
										<%= HtmlUtil.escape(lcsClusterEntry.getName()) %>
									</aui:option>

								<%
								}
								%>

							</aui:select>
						</c:otherwise>
					</c:choose>
				</div>

				<aui:button-row>
					<aui:button cssClass='<%= lcsAdministratorLCSRole ? StringPool.BLANK : "hide" %>' name="addEnvironmentButton" value="add-new-environment" />
				</aui:button-row>
			</aui:field-wrapper>

			<aui:input label="server-name" name="lcsClusterNodeName" required="<%= true %>" />

			<aui:input label="server-location" name="lcsClusterNodeLocation" />

			<aui:input label="server-description" name="lcsClusterNodeDescription" />

			<aui:field-wrapper label="lcs-server-key">
				<%= KeyGeneratorUtil.getKey() %>
			</aui:field-wrapper>

			<div class="<%= ClusterExecutorUtil.isEnabled() ? StringPool.BLANK : "hide" %>">
				<aui:input checked="<%= true %>" id="registerAllClusterNodes" label="register-all-nodes-of-this-cluster" name="registerAllClusterNodes" type="checkbox" />
			</div>

			<aui:button-row>
				<aui:button href="<%= resetCredentialsURL %>" value="back" />

				<aui:button cssClass="btn-success" disabled="<%= true %>" name="register" type="submit" value="register" />
			</aui:button-row>
		</aui:form>

		<aui:script use="lcs">
			var lcsPortlet = new Liferay.Portlet.LCS(
				{
					namespace: '<portlet:namespace />'
				}
			);

			<portlet:renderURL var="addLCSClusterEntryURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
				<portlet:param name="mvcPath" value="/add_lcs_cluster_entry.jsp" />
			</portlet:renderURL>

			lcsPortlet.initializeRegisterPage(
				{
					addLCSClusterEntryURL: '<%= addLCSClusterEntryURL %>',
					cluster: <%= ClusterExecutorUtil.isEnabled() %>,
					labelAddNewEnvironment: '<%= UnicodeLanguageUtil.get(request, "add-new-environment") %>',
					labelEditNewEnvironment: '<%= UnicodeLanguageUtil.get(request, "edit-new-environment") %>',
					labelNewEnvironment: '<%= UnicodeLanguageUtil.get(request, "new-environment") %>',
					msgNoEnvironmentsCreated: '<%= ClusterExecutorUtil.isEnabled() ? UnicodeLanguageUtil.get(request, "please-create-a-new-environment") : UnicodeLanguageUtil.get(request, "there-are-no-environments-created-yet") %>',
					serveLCSProjectURL: '<portlet:resourceURL id="serveLCSProject" />'
				}
			);
		</aui:script>
	</c:otherwise>
</c:choose>