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
String lcsClusterEntryLayoutURL = StringPool.BLANK;
String lcsClusterNodeLayoutURL = StringPool.BLANK;
String lcsProjectLayoutURL = StringPool.BLANK;

LCSClusterEntry lcsClusterEntry = null;
LCSClusterNode lcsClusterNode = null;
LCSProject lcsProject = new LCSProjectImpl();

try {
	lcsClusterNode = LCSClusterNodeServiceUtil.fetchLCSClusterNode(KeyGeneratorUtil.getKey());

	if (lcsClusterNode != null) {
		lcsClusterEntry = LCSClusterEntryServiceUtil.getLCSClusterEntry(lcsClusterNode.getLcsClusterEntryId());

		List<LCSProject> lcsProjects = LCSProjectServiceUtil.getUserManageableLCSProjects(liferayPortletRequest);

		for (LCSProject currentLCSProject : lcsProjects) {
			if (currentLCSProject.getLcsProjectId() == lcsClusterEntry.getLcsProjectId()) {
				lcsProject = currentLCSProject;

				break;
			}
		}

		lcsProjectLayoutURL = LCSUtil.getLCSProjectLayoutURL(lcsProject);

		lcsClusterEntryLayoutURL = LCSUtil.getLCSClusterEntryLayoutURL(lcsProject, lcsClusterNode);

		lcsClusterNodeLayoutURL = LCSUtil.getLCSClusterNodeLayoutURL(lcsProject, lcsClusterNode);
	}
}
catch (Exception e) {
	LCSExceptionHandler.error(e);

	SessionErrors.add(liferayPortletRequest, "generalPluginAccess");
}
%>

<c:if test="<%= !LCSConnectionManagerUtil.isLCSGatewayAvailable() %>">
	<div class="alert alert-danger lcs-alert" id="<portlet:namespace />lcsGatewayUnavailable">
		<liferay-ui:message key="unable-to-access-liferay-connected-services-gateway" />
	</div>
</c:if>

<div class="alert alert-danger hide lcs-alert" id="<portlet:namespace />heartbeatExpired">
	<liferay-ui:message key="the-connection-to-liferay-connected-services-has-expired" />
</div>

<c:choose>
	<c:when test="<%= ((lcsClusterNode == null) || (lcsClusterEntry == null)) %>">
		<c:choose>
			<c:when test='<%= SessionErrors.contains(liferayPortletRequest, "generalPluginAccess") %>'>
				<div class="alert alert-warning lcs-alert">
					<liferay-ui:message key="an-error-occurred-while-accessing-liferay-connected-services" />
				</div>
			</c:when>
			<c:otherwise>
				<div class="alert alert-warning lcs-alert">
					<liferay-ui:message arguments="<%= LCSUtil.getLCSPortalURL() %>" key="please-download-and-install-the-latest-version-of-liferay-connected-services-client" />
				</div>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>

		<%
		Map<String, String> lcsConnectionMetadata = LCSConnectionManagerUtil.getLCSConnectionMetadata();
		%>

		<c:if test='<%= GetterUtil.getBoolean(lcsConnectionMetadata.get("newLCSPortletBuildNumber")) %>'>
			<div class="alert alert-warning lcs-alert">
				<liferay-ui:message arguments="<%= LCSUtil.getDownloadsLayoutURL() %>" key="please-download-and-install-the-latest-version-of-liferay-connected-services-client" />
			</div>
		</c:if>

		<%
		boolean pending = LCSConnectionManagerUtil.isPending();
		boolean ready = LCSConnectionManagerUtil.isReady();

		String connectionStatusLabel = "connected";

		if (!ready && !pending) {
			connectionStatusLabel = "disconnected";
		}
		else if (!ready && pending) {
			connectionStatusLabel = "synchronizing";
		}
		%>

		<div class="connection-status <%= connectionStatusLabel %>" id="<portlet:namespace />connectionStatus">
			<span class="connection-icon"></span>

			<span class="connection-label">
				<%= LanguageUtil.get(request, connectionStatusLabel) %>
			</span>

			<span class="connection-help">
				<liferay-ui:icon-help message='<%= LanguageUtil.get(request, connectionStatusLabel + "-help") %>' />
			</span>
		</div>

		<div class="lcs-info">
			<div class="lcs-connection-info">
				<h3><liferay-ui:message key="connection" /></h3>

				<dl>
					<dt>
						<liferay-ui:message key="heartbeat-interval" />

						<liferay-ui:icon-help message="heartbeat-interval-help" />
					</dt>
					<dd>

						<%
						Date heartbeatIntervalDate = new Date(GetterUtil.getLong(lcsConnectionMetadata.get("heartbeatInterval")));
						%>

						<%= intervalDateFormatDate.format(heartbeatIntervalDate) %>
					</dd>
					<dt>
						<liferay-ui:message key="message-task-interval" />

						<liferay-ui:icon-help message="message-task-interval-help" />
					</dt>
					<dd>

						<%
						Date messageTaskIntervalDate = new Date(GetterUtil.getLong(lcsConnectionMetadata.get("messageTaskInterval")));
						%>

						<%= intervalDateFormatDate.format(messageTaskIntervalDate) %>
					</dd>
					<dt>
						<liferay-ui:message key="metrics-task-interval" />

						<liferay-ui:icon-help message="metrics-task-interval-help" />
					</dt>
					<dd>

						<%
						Date jvmMetricsTaskIntervalDate = new Date(GetterUtil.getLong(lcsConnectionMetadata.get("jvmMetricsTaskInterval")));
						%>

						<%= intervalDateFormatDate.format(jvmMetricsTaskIntervalDate) %>
					</dd>

					<c:if test='<%= lcsConnectionMetadata.get("messageTaskTime") != null %>'>
						<dt>
							<liferay-ui:message key="last-message-received" />

							<liferay-ui:icon-help message="last-message-received-help" />
						</dt>
						<dd>

							<%
							Date messageTaskTimeDate = new Date(GetterUtil.getLong(lcsConnectionMetadata.get("messageTaskTime")));
							%>

							<%= dateFormatDate.format(messageTaskTimeDate) %>
						</dd>
					</c:if>

					<dt>
						<liferay-ui:message key="connection-uptime" />

						<liferay-ui:icon-help message="connection-uptime-help" />
					</dt>
					<dd>
						<div id="<portlet:namespace />duration">

							<%
							String handshakeTime = lcsConnectionMetadata.get("handshakeTime");
							%>

							<c:choose>
								<c:when test="<%= ready && !pending && (handshakeTime != null) %>">

									<%
									Date handshakeTimeDate = new Date(System.currentTimeMillis() - GetterUtil.getLong(handshakeTime));
									%>

									<%= intervalDateFormatDate.format(handshakeTimeDate) %>
								</c:when>
								<c:otherwise>
									<%= intervalDateFormatDate.format(new Date(0)) %>
								</c:otherwise>
							</c:choose>
						</div>
					</dd>
				</dl>
			</div>

			<div class="lcs-portal-info">
				<h3><liferay-ui:message key="liferay-connected-services-sites" /></h3>

				<dl>
					<dt>
						<liferay-ui:message key="project-home" />
					</dt>
					<dd>
						<aui:a href="<%= lcsProjectLayoutURL %>" target="_blank">
							<%= HtmlUtil.escape(lcsProject.getName()) %>
						</aui:a>
					</dd>
				</dl>
				<dl>
					<dt>
						<liferay-ui:message key="environment" />
					</dt>
					<dd>
						<aui:a href="<%= lcsClusterEntryLayoutURL %>" target="_blank">
							<%= HtmlUtil.escape(lcsClusterEntry.getName()) %>
						</aui:a>
					</dd>
				</dl>
				<dl>
					<dt>
						<liferay-ui:message key="server-dashboard" />
					</dt>
					<dd>
						<aui:a href="<%= lcsClusterNodeLayoutURL %>" target="_blank">
							<%= HtmlUtil.escape(lcsClusterNode.getName()) %>
						</aui:a>
					</dd>
				</dl>
			</div>

			<c:if test="<%= ClusterExecutorUtil.isEnabled() %>">
				<div class="lcs-cluster-info">
					<h3><liferay-ui:message key="cluster" /></h3>

					<dl>
						<dt>
							<liferay-ui:message key="local-cluster-node-key" />
						</dt>
						<dd>

							<%
							Map<String, Object> localClusterNodeInfo = ClusterNodeUtil.getClusterNodeInfo();
							%>

							<%= localClusterNodeInfo.get("key") %>
						</dd>
					</dl>

					<%
					List<Map<String, Object>> clusterNodeInfos = ClusterNodeUtil.getClusterNodeInfos();
					%>

					<c:if test="<%= !clusterNodeInfos.isEmpty() %>">
						<dl>
							<dt>
								<liferay-ui:message key="sibling-cluster-nodes-keys" />
							</dt>
							<dd>

								<%
								for (Map<String, Object> clusterNodeInfo : ClusterNodeUtil.getClusterNodeInfos()) {
								%>

									<c:choose>
										<c:when test='<%= clusterNodeInfo.containsKey("lcsPortletMissing") %>'>
											<div class="cluster-node-error">
												<%= clusterNodeInfo.get("key") %>

												<i class="icon-warning-sign"></i>
											</div>
										</c:when>
										<c:otherwise>
											<%= clusterNodeInfo.get("key") %>
										</c:otherwise>
									</c:choose>

								<%
								}
								%>

							</dd>
						</dl>
					</c:if>
				</div>
			</c:if>
		</div>

		<div class="<%= ClusterExecutorUtil.isEnabled() ? StringPool.BLANK : "hide" %>">
			<aui:input checked="<%= true %>" id="applyToSiblingClusterNodes" label="apply-to-all-nodes-of-this-cluster" name="applyToSiblingClusterNodes" type="checkbox" />
		</div>

		<liferay-portlet:renderURL var="configureLCSServicesURL">
			<liferay-portlet:param name="configureLCSServices" value="true" />
		</liferay-portlet:renderURL>

		<aui:a href="<%= configureLCSServicesURL %>" label="configure-services" />

		<aui:button-row>
			<aui:button cssClass='<%= "btn-success " + (ready ? "hide" : StringPool.BLANK) %>' disabled="<%= pending %>" name="connect" value="connect" />

			<aui:button cssClass='<%= ready ? StringPool.BLANK : "hide" %>' disabled="<%= pending %>" name="disconnect" value="disconnect" />
		</aui:button-row>

		<div class="alert alert-info lcs-synchronizing-alert <%= pending ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />connectionAlertContainer">
			<liferay-ui:message key="synchronizing-help" />

			<i class="icon-spin icon-spinner"></i>
		</div>

		<aui:script use="lcs">
			var lcsPortlet = new Liferay.Portlet.LCS(
				{
					namespace: '<portlet:namespace />'
				}
			);

			lcsPortlet.initializeLCSClusterNodePage(
				{
					connectionStatusURL: '<portlet:resourceURL id="serveConnectionStatus" />',
					connectURL: '<portlet:resourceURL id="connect" />',
					disconnectURL: '<portlet:resourceURL id="disconnect" />',
					handshakeTime: <%= handshakeTime %>,
					labelConnected: '<%= UnicodeLanguageUtil.get(request, "connected") %>',
					labelConnectedHelp: '<%= UnicodeLanguageUtil.get(request, "connected-help") %>',
					labelDisconnected: '<%= UnicodeLanguageUtil.get(request, "disconnected") %>',
					labelDisconnectedHelp: '<%= UnicodeLanguageUtil.get(request, "disconnected-help") %>',
					labelPending: '<%= UnicodeLanguageUtil.get(request, "synchronizing") %>',
					labelPendingHelp: '<%= UnicodeLanguageUtil.get(request, "synchronizing-help") %>',
					pending: <%= pending %>,
					ready: <%= ready %>,
					tooltipClusterNodeError: '<%= UnicodeLanguageUtil.get(request, "this-cluster-node-does-not-have-liferay-connected-services-client-installed") %>',
					tooltipConnect: '<%= UnicodeLanguageUtil.get(request, "connect-help") %>',
					tooltipDisconnect: '<%= UnicodeLanguageUtil.get(request, "disconnect-help") %>'
				}
			);
		</aui:script>
	</c:otherwise>
</c:choose>