<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portal/init.jsp" %>

<%@ page import="com.liferay.portal.kernel.cluster.ClusterExecutorUtil" %>
<%@ page import="com.liferay.portal.kernel.cluster.ClusterNode" %>
<%@ page import="com.liferay.portal.license.util.LicenseManagerUtil" %>
<%@ page import="com.liferay.portal.license.util.LicenseUtil" %>

<style type="text/css">
	.build-info {
		color: #555;
		font-size: 11px;
		margin: 0 0 15px 0;
	}

	.license-table td, .license-table th {
		padding: 0 5px;
		vertical-align: top;
	}

	.license-form {
		padding-bottom: 30px;
	}

	.alert-error, .alert-success {
		margin: 15px auto 5px;
	}

	.version-info {
		font-size: 16px;
		font-weight: bold;
		margin: 0 0 2px 0;
	}
</style>

<%
Map<String, String> orderProducts = (Map<String, String>)request.getAttribute("ORDER_PRODUCTS");

String errorMessage = (String)request.getAttribute("ERROR_MESSAGE");

boolean error = false;

if (Validator.isNotNull(errorMessage)) {
	error = true;
}

String orderUuid = ParamUtil.getString(request, "orderUuid");

String[] releaseInfoArray = StringUtil.split(ReleaseInfo.getReleaseInfo(), "(");

String versionInfo = releaseInfoArray[0];
String buildInfo = StringUtil.replace(releaseInfoArray[1], ")", "");

List<ClusterNode> clusterNodes = ClusterExecutorUtil.getClusterNodes();

Collections.sort(clusterNodes);

DateFormat dateFormatDateTime = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

dateFormatDateTime.setTimeZone(timeZone);
%>

<h2 class="version-info">
	<%= versionInfo %>
</h2>

<h3 class="build-info">
	<%= buildInfo %>
</h3>

<form class="license-form" method="post" name="license_fm" <%= (clusterNodes.size() > 1) ? "onsubmit=\"return validateForm();\"" : "" %>>

<c:if test="<%= Validator.isNotNull(errorMessage) %>">
	<div class="alert alert-error">
		<%= errorMessage %>
	</div>
</c:if>

<c:choose>
	<c:when test="<%= clusterNodes.size() <= 1 %>">

		<%
		String successMessage = (String)request.getAttribute("SUCCESS_MESSAGE");

		Map<String, String> serverInfo = LicenseUtil.getServerInfo();

		List<Map<String, String>> licenseProperties = LicenseManagerUtil.getLicenseProperties();
		%>

		<c:if test="<%= Validator.isNotNull(successMessage) %>">
			<div class="alert alert-success">
				<%= successMessage %>
			</div>
		</c:if>

		<table class="license-table">
		<tr>
			<th>
				Server Info
			</th>
			<th>
				Licenses Registered
			</th>
		</tr>
		<tr>
			<td style="border: 1px solid gray;">
				<table class="license-table">
				<tr>
					<th>
						Host Name
					</th>

					<c:if test='<%= GetterUtil.getBoolean(PropsUtil.get("license.server.info.display"), true) %>'>
						<th>
							IP Addresses
						</th>
						<th>
							MAC Addresses
						</th>
					</c:if>
				</tr>
				<tr>
					<td>
						<%= serverInfo.get("hostName") %>
					</td>

					<c:if test='<%= GetterUtil.getBoolean(PropsUtil.get("license.server.info.display"), true) && (serverInfo != null) %>'>
						<td>
							<c:if test='<%= serverInfo.containsKey("ipAddresses") %>'>

								<%
								for (String ipAddress : StringUtil.split(serverInfo.get("ipAddresses"))) {
								%>

									<%= ipAddress %><br />

								<%
								}
								%>

							</c:if>
						</td>
						<td>
							<c:if test='<%= serverInfo.containsKey("macAddresses") %>'>

								<%
								for (String macAddress : StringUtil.split(serverInfo.get("macAddresses"))) {
								%>

									<%= macAddress %><br />

								<%
								}
								%>

							</c:if>
						</td>
					</c:if>
				</tr>
				</table>
			</td>
			<td style="border: 1px solid gray;">
				<table class="license-table">
				<tr>
					<th>
						<liferay-ui:message key="product" />
					</th>
					<th>
						<liferay-ui:message key="status" />
					</th>
					<th>
						<liferay-ui:message key="owner" />
					</th>
					<th>
						<liferay-ui:message key="description" />
					</th>
					<th>
						<liferay-ui:message key="type" />
					</th>
					<th>
						<liferay-ui:message key="start-date" />
					</th>
					<th>
						<liferay-ui:message key="expiration-date" />
					</th>
					<th>
						<liferay-ui:message key="additional-information" />
					</th>
				</tr>

				<c:choose>
					<c:when test="<%= licenseProperties != null %>">

						<%
						for (int i = 0; i < licenseProperties.size(); i++) {
							Map<String, String> curLicenseProperties = licenseProperties.get(i);

							int licenseState = GetterUtil.getInteger(curLicenseProperties.get("licenseState"));
							long startDateTime = GetterUtil.getLong(curLicenseProperties.get("startDate"));
							long expirationDateTime = GetterUtil.getLong(curLicenseProperties.get("expirationDate"));
							int maxConcurrentUsers = GetterUtil.getInteger(curLicenseProperties.get("maxConcurrentUsers"));
							int maxUsers = GetterUtil.getInteger(curLicenseProperties.get("maxUsers"));
						%>

							<tr>
								<td>
									<%= curLicenseProperties.get("productEntryName") %>
								</td>
								<td>
									<c:choose>
										<c:when test="<%= licenseState == 1 %>">
											<span style="color: red;">Absent</span>
										</c:when>
										<c:when test="<%= licenseState == 2 %>">
											<span style="color: red;">Expired</span>
										</c:when>
										<c:when test="<%= licenseState == 3 %>">
											Active
										</c:when>
										<c:when test="<%= licenseState == 4 %>">
											<span style="color: red;">Inactive</span>
										</c:when>
										<c:when test="<%= (licenseState == 5) || (licenseState == 6) %>">
											<span style="color: red;">Invalid</span>
										</c:when>
									</c:choose>
								</td>
								<td>
									<%= HtmlUtil.escape(curLicenseProperties.get("owner")) %>
								</td>
								<td>
									<%= HtmlUtil.escape(curLicenseProperties.get("description")) %>
								</td>
								<td>
									<liferay-ui:message key='<%= curLicenseProperties.get("type") %>' />
								</td>
								<td>
									<%= dateFormatDateTime.format(new Date(startDateTime)) %>
								</td>
								<td>
									<%= dateFormatDateTime.format(new Date(expirationDateTime)) %>
								</td>
								<td>
									<c:if test="<%= maxConcurrentUsers > 0 %>">
										Max Concurrent Users: <%= maxConcurrentUsers %><br />
									</c:if>

									<c:if test="<%= maxUsers > 0 %>">
										Max Registered Users: <%= maxUsers %>
									</c:if>
								</td>
							</tr>

						<%
						}
						%>

						<c:if test="<%= licenseProperties.isEmpty() %>">
							<tr>
								<td colspan="8">
									There are no licenses registered.
								</td>
							</tr>
						</c:if>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="8">
								License information is not available.
							</td>
						</tr>
					</c:otherwise>
				</c:choose>

				</table>
			</td>
		</tr>
		</table>
	</c:when>
	<c:otherwise>
		<table class="license-table">
		<tr>
			<th></th>
			<th>
				Server Info
			</th>
			<th>
				Licenses Registered
			</th>
		</tr>

		<%
		for (ClusterNode clusterNode : clusterNodes) {
			String successMessage = (String)request.getAttribute(clusterNode.getClusterNodeId() + "_SUCCESS_MESSAGE");

			String curErrorMessage = (String)request.getAttribute(clusterNode.getClusterNodeId() + "_ERROR_MESSAGE");

			if (Validator.isNotNull(curErrorMessage)) {
				error = true;
			}
		%>

			<c:if test="<%= Validator.isNotNull(successMessage) %>">
				<tr>
					<td colspan="3">
						<div class="alert alert-success">
							<%= successMessage %>
						</div>
					</td>
				</tr>
			</c:if>

			<c:if test="<%= Validator.isNotNull(curErrorMessage) %>">
				<tr>
					<td colspan="3">
						<div class="alert alert-error">
							<%= curErrorMessage %>
						</div>
					</td>
				</tr>
			</c:if>

			<tr>
				<td style="border: 1px solid gray; vertical-align: middle;">
					<liferay-ui:input-checkbox disabled="<%= true %>" param='<%= clusterNode.getClusterNodeId() + "_register" %>' />
				</td>
				<td style="border: 1px solid gray;">
					<table class="license-table">
					<tr>
						<th>
							Host Name
						</th>

						<c:if test='<%= GetterUtil.getBoolean(PropsUtil.get("license.server.info.display"), true) %>'>
							<th>
								IP Addresses
							</th>
							<th>
								MAC Addresses
							</th>
						</c:if>
					</tr>
					<tr>
						<td id="<%= clusterNode.getClusterNodeId() %>_hostName"></td>

						<c:if test='<%= GetterUtil.getBoolean(PropsUtil.get("license.server.info.display"), true) %>'>
							<td id="<%= clusterNode.getClusterNodeId() %>_ipAddresses"></td>
							<td id="<%= clusterNode.getClusterNodeId() %>_macAddresses"></td>
						</c:if>
					</tr>
					</table>

					<div id="<%= clusterNode.getClusterNodeId() %>_serverInfo">
						<div style="text-align: center;">
							<img src="<%= themeDisplay.getPathThemeImages() %>/aui/loading_indicator.gif" />
						</div>
					</div>
				</td>
				<td style="border: 1px solid gray;">
					<table class="license-table" id="<%= clusterNode.getClusterNodeId() %>_licenseTable">
					<tr>
						<th>
							<liferay-ui:message key="product" />
						</th>
						<th>
							<liferay-ui:message key="status" />
						</th>
						<th>
							<liferay-ui:message key="owner" />
						</th>
						<th>
							<liferay-ui:message key="description" />
						</th>
						<th>
							<liferay-ui:message key="type" />
						</th>
						<th>
							<liferay-ui:message key="start-date" />
						</th>
						<th>
							<liferay-ui:message key="expiration-date" />
						</th>
						<th>
							<liferay-ui:message key="additional-information" />
						</th>
					</tr>
					</table>

					<div id="<%= clusterNode.getClusterNodeId() %>_licenseProperties">
						<div style="text-align: center;">
							<img src="<%= themeDisplay.getPathThemeImages() %>/aui/loading_indicator.gif" />
						</div>
					</div>
				</td>
			</tr>

		<%
		}
		%>

		</table>

		<div id="portHelp" style="display: none;">
			*ports are not initialized until the server has processed a request.
		</div>

		<script type="text/javascript">
			Liferay.provide(
				window,
				'sendClusterRequest',
				function(cmd, clusterNodeId, ip, port, success) {
					var url = '<%= themeDisplay.getPathMain() + "/portal/license" %>';

					var A = AUI();

					A.io.request(
						url,
						{
							data: {
								<%= Constants.CMD %>: cmd,
								clusterNodeId: clusterNodeId
							},
							dataType: 'json',
							on: {
								failure: function() {
									var errorMessage = 'Error contacting ' + ip;

									if (port != '-1') {
										errorMessage += ':' + port;
									}

									A.one('#' + clusterNodeId + '_' + cmd).html('<div class="alert alert-error">' + errorMessage + '</div>');
								},
								success: function(event, id, obj) {
									var instance = this;

									var message = instance.get('responseData');

									A.one('#' + clusterNodeId + '_' + cmd).html('');

									success(message);
								}
							}
						}
					);
				},
				['aui-io-request']
			);

			<%
			for (ClusterNode clusterNode : clusterNodes) {
			%>

				sendClusterRequest(
					'serverInfo',
					'<%= clusterNode.getClusterNodeId() %>',
					'<%= clusterNode.getInetAddress().getHostAddress() %>',
					'<%= clusterNode.getPort() %>',
					function(message) {
						var A = AUI();

						<c:if test="<%= clusterNode.getPort() == -1 %>">
							A.one('#portHelp').removeAttribute('style');
						</c:if>

						A.one('#<%= clusterNode.getClusterNodeId() %>_hostName').html(message.hostName + ':<%= clusterNode.getPort() %><%= (clusterNode.getPort() == -1) ? "*" : "" %>');
						A.one('#<%= clusterNode.getClusterNodeId() %>_ipAddresses').html(message.ipAddresses.split(',').join('<br />'));
						A.one('#<%= clusterNode.getClusterNodeId() %>_macAddresses').html(message.macAddresses.split(',').join('<br />'));
					}
				);

				sendClusterRequest(
					'licenseProperties',
					'<%= clusterNode.getClusterNodeId() %>',
					'<%= clusterNode.getInetAddress().getHostAddress() %>',
					'<%= clusterNode.getPort() %>',
					function(message) {
						var A = AUI();

						A.one('#<%= clusterNode.getClusterNodeId() %>_registerCheckbox').attr('disabled', false);

						if (!message) {
							A.one('#<%= clusterNode.getClusterNodeId() %>_licenseProperties').html('License information is not available.');

							return;
						}

						var empty = true;

						var licenseTable = document.getElementById('<%= clusterNode.getClusterNodeId() %>_licenseTable');

						for (var i in message) {
							var productEntryName = message[i].productEntryName;

							if (!productEntryName) {
								break;
							}

							empty = false;

							var row = licenseTable.insertRow(-1);

							addColumn(row, productEntryName);
							addColumn(row, getLicenseState(message[i].licenseState));
							addColumn(row, Liferay.Util.escapeHTML(message[i].owner));
							addColumn(row, Liferay.Util.escapeHTML(message[i].description));
							addColumn(row, message[i].type);
							addColumn(row, new Date(Number(message[i].startDate)).toLocaleDateString());
							addColumn(row, new Date(Number(message[i].expirationDate)).toLocaleDateString());

							var additionalInfo = '';

							if (Number(message[i].maxConcurrentUsers) > 0) {
								additionalInfo = 'Max Concurrent Users: ' + message[i].maxConcurrentUsers + '<br />';
							}

							if (Number(message[i].maxUsers) > 0) {
								additionalInfo += 'Max Registered Users: ' + message[i].maxUsers;
							}

							addColumn(row, additionalInfo);
						}

						if (empty) {
							A.one('#<%= clusterNode.getClusterNodeId() %>_licenseProperties').html('There are no licenses registered.');
						}
					}
				);

			<%
			}
			%>

			function addColumn(row, html) {
				var cell = row.insertCell(-1);

				cell.innerHTML = html;
			}

			function getLicenseState(licenseState) {
				if (licenseState == 2) {
					return '<span style="color: red;">Expired</span>';
				}
				else if (licenseState == 3) {
					return 'Active';
				}
				else if (licenseState == 4) {
					return '<span style="color: red;">Inactive</span>';
				}
				else if ((licenseState == 5) || (licenseState == 6)) {
					return '<span style="color: red;">Invalid</span>';
				}

				return '<span style="color: red;">Absent</span>';
			}

			function validateForm() {
				var A = AUI();

				if (document.license_fm.productEntryName.value != '') {
					var checkboxes = A.one(document.license_fm).all('input[type=checkbox]:checked');

					if (!checkboxes || (checkboxes.size() <= 0)) {
						alert("There are no selected servers to register.");

						return false;
					}
				}
			}
		</script>
	</c:otherwise>
</c:choose>

<br />

<h3>Register Your Application</h3>

<table class="lfr-table">
<tr>
	<td>
		Order Id
	</td>
	<td>
		<c:choose>
			<c:when test="<%= !error && (orderProducts != null) && Validator.isNotNull(orderUuid) %>">
				<%= HtmlUtil.escape(orderUuid) %>

				<input name="orderUuid" type="hidden" value="<%= HtmlUtil.escapeAttribute(orderUuid) %>" />
			</c:when>
			<c:otherwise>
				<input name="orderUuid" size="50" type="text" value="<%= HtmlUtil.escapeAttribute(orderUuid) %>" />
			</c:otherwise>
		</c:choose>
	</td>
</tr>

<c:if test="<%= orderProducts != null %>">
	<tr>
		<td>
			Product
		</td>
		<td>
			<select name="productEntryName" onChange='if (this.value == "basic-cluster") {document.getElementById("maxServers").style.display = "";} else {document.getElementById("maxServers").style.display = "none";}'>
				<option value=""></option>

				<%
				for (Map.Entry<String, String> entry : orderProducts.entrySet()) {
					String key = entry.getKey();

					String licensesLeft = LanguageUtil.get(pageContext, entry.getValue());
				%>

					<c:choose>
						<c:when test='<%= key.equals("basic") %>'>
							<option value="basic">Single Production Server (<%= licensesLeft %><aui:spacer /><%= licensesLeft.equals("1") ? "License" : "Licenses" %> Left)</option>
							<option value="basic-cluster">Create New Cluster Production Servers (<%= licensesLeft %><aui:spacer /><%= licensesLeft.equals("1") ? "License" : "Licenses" %> Left)</option>
						</c:when>
						<c:when test='<%= key.startsWith("basic-") %>'>
							<option value="<%= key %>">Join Existing Cluster (<%= licensesLeft %><aui:spacer /><%= licensesLeft.equals("1") ? "Server" : "Servers" %> Left)</option>
						</c:when>
						<c:otherwise>
							<option value="<%= key %>"><%= LanguageUtil.get(pageContext, key) %> (<%= licensesLeft %><aui:spacer /><%= licensesLeft.equals("1") ? "License" : "Licenses" %> Left)</option>
						</c:otherwise>
					</c:choose>

				<%
				}
				%>

			</select>
		</td>
	</tr>
	<tr id="maxServers" style="display: none;">
		<td>
			Maximum Servers
		</td>
		<td>
			<select name="maxServers">
				<option value="0"></option>
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
				<option value="6">6</option>
				<option value="7">7</option>
				<option value="8">8</option>
				<option value="9">9</option>
				<option value="10">10</option>
				<option value="11">11</option>
				<option value="12">12</option>
				<option value="13">13</option>
				<option value="14">14</option>
				<option value="15">15</option>
			</select>
		</td>
	</tr>
</c:if>

</table>

<br />

<c:choose>
	<c:when test="<%= orderProducts != null %>">
		<input class="btn" type="submit" value="<liferay-ui:message key="register" />" />

		<input onClick="location.href='<%= themeDisplay.getURLCurrent() %>';" type="button" value="<liferay-ui:message key="cancel" />" />
	</c:when>
	<c:otherwise>
		<input class="btn" type="submit" value="Query" />
	</c:otherwise>
</c:choose>

</form>