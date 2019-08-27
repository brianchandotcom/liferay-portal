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

<%@ include file="/html/portal/init.jsp" %>

<style type="text/css">
	.build-info {
		color: #555;
		font-size: 11px;
		margin: 0 0 15px 0;
	}

	.license-form {
		font-size: 15px;
		padding-bottom: 30px;
	}

	.license-form > .license-table {
		display: block;
		overflow-x: auto;
	}

	.license-table td, .license-table th {
		padding: 0 5px;
		vertical-align: top;
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
String buildInfo = StringUtil.replace(releaseInfoArray[1], ')', "");

List<ClusterNode> clusterNodes = ClusterExecutorUtil.getClusterNodes();

if (clusterNodes == null) {
	clusterNodes = Collections.emptyList();
}

DateFormat dateFormatDateTime = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

dateFormatDateTime.setTimeZone(timeZone);
%>

<div class="container-fluid container-view">
	<h2 class="version-info">
		<%= versionInfo %>
	</h2>

	<h3 class="build-info">
		<%= buildInfo %>
	</h3>

	<form class="license-form" method="post" name="license_fm" <%= (clusterNodes.size() > 1) ? "onsubmit=\"return validateForm();\"" : "" %>>
		<c:if test="<%= Validator.isNotNull(errorMessage) %>">
			<div class="alert alert-danger">
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
							<liferay-ui:message key="server-info" />
						</th>
						<th>
							<liferay-ui:message key="licenses-registered" />
						</th>
					</tr>
					<tr>
						<td style="border: 1px solid gray;">
							<table class="license-table">
								<tr>
									<th>
										<liferay-ui:message key="host-name" />
									</th>

									<c:if test='<%= GetterUtil.getBoolean(PropsUtil.get("license.server.info.display"), true) %>'>
										<th>
											<liferay-ui:message key="ip-addresses" />
										</th>
										<th>
											<liferay-ui:message key="mac-addresses" />
										</th>
										<th>
											<liferay-ui:message key="processor-cores" />
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
										<td>
											<%= serverInfo.get("processorCores") %>
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
										<liferay-ui:message key="max-processor-cores" />
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
											String maxProcessorCores = curLicenseProperties.get("maxProcessorCores");
											int maxUsers = GetterUtil.getInteger(curLicenseProperties.get("maxUsers"));
										%>

											<tr>
												<td>
													<%= curLicenseProperties.get("productEntryName") %>
												</td>
												<td>
													<c:choose>
														<c:when test="<%= licenseState == 1 %>">
															<span style="color: red;"><liferay-ui:message key="absent" /></span>
														</c:when>
														<c:when test="<%= licenseState == 2 %>">
															<span style="color: red;"><liferay-ui:message key="expired" /></span>
														</c:when>
														<c:when test="<%= licenseState == 3 %>">
															<liferay-ui:message key="active" />
														</c:when>
														<c:when test="<%= licenseState == 4 %>">
															<span style="color: red;"><liferay-ui:message key="inactive" /></span>
														</c:when>
														<c:when test="<%= (licenseState == 5) || (licenseState == 6) %>">
															<span style="color: red;"><liferay-ui:message key="invalid" /></span>
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
													<c:if test="<%= Validator.isNotNull(maxProcessorCores) %>">
														<%= maxProcessorCores %>
													</c:if>
												</td>
												<td>
													<%= dateFormatDateTime.format(new Date(startDateTime)) %>
												</td>
												<td>
													<%= dateFormatDateTime.format(new Date(expirationDateTime)) %>
												</td>
												<td>
													<c:if test="<%= maxConcurrentUsers > 0 %>">
														<liferay-ui:message key="max-concurrent-users" />: <%= maxConcurrentUsers %><br />
													</c:if>

													<c:if test="<%= maxUsers > 0 %>">
														<liferay-ui:message key="max-registered-users" />: <%= maxUsers %>
													</c:if>
												</td>
											</tr>

										<%
										}
										%>

										<c:if test="<%= licenseProperties.isEmpty() %>">
											<tr>
												<td colspan="8">
													<liferay-ui:message key="there-are-no-licenses-registered" />
												</td>
											</tr>
										</c:if>
									</c:when>
									<c:otherwise>
										<tr>
											<td colspan="8">
												<liferay-ui:message key="license-information-is-not-available" />
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
							<liferay-ui:message key="server-info" />
						</th>
						<th>
							<liferay-ui:message key="licenses-registered" />
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
									<div class="alert alert-danger">
										<%= curErrorMessage %>
									</div>
								</td>
							</tr>
						</c:if>

						<tr>
							<td style="border: 1px solid gray; vertical-align: middle;">
								<liferay-ui:input-checkbox
									disabled="<%= true %>"
									id='<%= "node_" + clusterNode.getClusterNodeId() + "_register" %>'
									param='<%= clusterNode.getClusterNodeId() + "_register" %>'
								/>
							</td>
							<td style="border: 1px solid gray;">
								<table class="license-table">
									<tr>
										<th>
											<liferay-ui:message key="host-name" />
										</th>

										<c:if test='<%= GetterUtil.getBoolean(PropsUtil.get("license.server.info.display"), true) %>'>
											<th>
												<liferay-ui:message key="ip-addresses" />
											</th>
											<th>
												<liferay-ui:message key="mac-addresses" />
											</th>
											<th>
												<liferay-ui:message key="processor-cores" />
											</th>
										</c:if>
									</tr>
									<tr>
										<td id="node_<%= clusterNode.getClusterNodeId() %>_hostName"></td>

										<c:if test='<%= GetterUtil.getBoolean(PropsUtil.get("license.server.info.display"), true) %>'>
											<td id="node_<%= clusterNode.getClusterNodeId() %>_ipAddresses"></td>
											<td id="node_<%= clusterNode.getClusterNodeId() %>_macAddresses"></td>
											<td id="node_<%= clusterNode.getClusterNodeId() %>_processorCores"></td>
										</c:if>
									</tr>
								</table>

								<div id="node_<%= clusterNode.getClusterNodeId() %>_serverInfo">
									<div style="text-align: center;">
										<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="loading" />" src="<%= themeDisplay.getPathThemeImages() %>/aui/loading_indicator.gif" />
									</div>
								</div>
							</td>
							<td style="border: 1px solid gray;">
								<table class="license-table" id="node_<%= clusterNode.getClusterNodeId() %>_licenseTable">
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
											<liferay-ui:message key="max-processor-cores" />
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

								<div id="node_<%= clusterNode.getClusterNodeId() %>_licenseProperties">
									<div style="text-align: center;">
										<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="loading" />" src="<%= themeDisplay.getPathThemeImages() %>/aui/loading_indicator.gif" />
									</div>
								</div>
							</td>
						</tr>

					<%
					}
					%>

				</table>

				<div id="portHelp" style="display: none;">
					<liferay-ui:message key="ports-are-not-initialized-until-the-server-has-processed-a-request" />
				</div>

				<aui:script>
					Liferay.provide(
						window,
						'sendClusterRequest',
						function(cmd, clusterNodeId, ip, port, success) {
							var url = '<%= themeDisplay.getPathMain() + "/portal/license" %>';

							var A = AUI();

							var body = new URLSearchParams({
								clusterNodeId: clusterNodeId,
								<%= Constants.CMD %>: cmd
							});

							Liferay.Util.fetch(url, {
								body: body,
								method: 'POST'
							}).then(
								function(response) {
									return response.json();
								}
							).then(
								function(data) {
									A.one('#node_' + clusterNodeId + '_' + cmd).html('');
									success(data);
								}
							).catch(
								function() {
									var errorMessage = A.Lang.sub('<liferay-ui:message key="error-contacting-x" />', [ip]);

									if (port != '-1') {
										errorMessage += ':' + port;
									}

									A.one('#node_' + clusterNodeId + '_' + cmd).html('<div class="alert alert-danger">' + errorMessage + '</div>');
								}
							);
						},
						['aui-base']
					);

					<%
					for (ClusterNode clusterNode : clusterNodes) {
					%>

						sendClusterRequest(
							'serverInfo',
							'<%= clusterNode.getClusterNodeId() %>',
							'<%= clusterNode.getBindInetAddress().getHostAddress() %>',
							'<%= clusterNode.getPortalPort() %>',
							function(message) {
								var A = AUI();

								<c:if test="<%= clusterNode.getPortalPort() == -1 %>">
									A.one('#portHelp').removeAttribute('style');
								</c:if>

								A.one('#node_<%= clusterNode.getClusterNodeId() %>_hostName').html(message.hostName + ':<%= clusterNode.getPortalPort() %><%= (clusterNode.getPortalPort() == -1) ? "*" : "" %>');
								A.one('#node_<%= clusterNode.getClusterNodeId() %>_ipAddresses').html(message.ipAddresses.split(',').join('<br />'));
								A.one('#node_<%= clusterNode.getClusterNodeId() %>_macAddresses').html(message.macAddresses.split(',').join('<br />'));
								A.one('#node_<%= clusterNode.getClusterNodeId() %>_processorCores').html(message.processorCores);
							}
						);

						sendClusterRequest(
							'licenseProperties',
							'<%= clusterNode.getClusterNodeId() %>',
							'<%= clusterNode.getBindInetAddress().getHostAddress() %>',
							'<%= clusterNode.getPortalPort() %>',
							function(message) {
								var A = AUI();

								var LString = A.Lang.String;

								A.one('#node_<%= clusterNode.getClusterNodeId() %>_register').attr('disabled', false);

								if (!message) {
									A.one('#node_<%= clusterNode.getClusterNodeId() %>_licenseProperties').html('<liferay-ui:message key="license-information-is-not-available" />');

									return;
								}

								var empty = true;

								var licenseTable = document.getElementById('node_<%= clusterNode.getClusterNodeId() %>_licenseTable');

								for (var i in message) {
									var productEntryName = message[i].productEntryName;

									if (!productEntryName) {
										break;
									}

									empty = false;

									var row = licenseTable.insertRow(-1);

									addColumn(row, productEntryName);
									addColumn(row, getLicenseState(message[i].licenseState));
									addColumn(row, LString.escapeHTML(message[i].owner));
									addColumn(row, LString.escapeHTML(message[i].description));
									addColumn(row, message[i].type);

									var maxProcessorCores = '';

									if (message[i].maxProcessorCores) {
										maxProcessorCores = message[i].maxProcessorCores;
									}

									addColumn(row, maxProcessorCores);

									addColumn(row, new Date(Number(message[i].startDate)).toLocaleDateString());
									addColumn(row, new Date(Number(message[i].expirationDate)).toLocaleDateString());

									var additionalInfo = '';

									if (Number(message[i].maxConcurrentUsers) > 0) {
										additionalInfo = '<liferay-ui:message key="max-concurrent-users" />' + ': ' + message[i].maxConcurrentUsers + '<br />';
									}

									if (Number(message[i].maxUsers) > 0) {
										additionalInfo += '<liferay-ui:message key="max-registered-users" />' + ': ' + message[i].maxUsers;
									}

									addColumn(row, additionalInfo);
								}

								if (empty) {
									A.one('#node_<%= clusterNode.getClusterNodeId() %>_licenseProperties').html('<liferay-ui:message key="there-are-no-licenses-registered" />');
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
							return '<span style="color: red;"><liferay-ui:message key="expired" /></span>';
						}
						else if (licenseState == 3) {
							return 'Active';
						}
						else if (licenseState == 4) {
							return '<span style="color: red;"><liferay-ui:message key="inactive" /></span>';
						}
						else if ((licenseState == 5) || (licenseState == 6)) {
							return '<span style="color: red;"><liferay-ui:message key="invalid" /></span>';
						}

						return '<span style="color: red;"><liferay-ui:message key="absent" /></span>';
					}

					function validateForm() {
						var A = AUI();

						if (document.license_fm.productEntryName.value != '') {
							var checkboxes = A.one(document.license_fm).all('input[type=checkbox]:checked');

							if (!checkboxes || (checkboxes.size() <= 0)) {
								alert('<liferay-ui:message key="there-are-no-selected-servers-to-register" />');

								return false;
							}
						}
					}
				</aui:script>
			</c:otherwise>
		</c:choose>

		<br />

		<h3><liferay-ui:message key="register-your-application" /></h3>

		<table class="lfr-table">
			<tr>
				<td>
					<liferay-ui:message key="order-id" />
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
						<liferay-ui:message key="product" />
					</td>
					<td>
						<select name="productEntryName" onChange="if (this.value == "basic-cluster") {document.getElementById("maxServers").style.display = "";} else {document.getElementById("maxServers").style.display = "none";}">
							<option value=""></option>

							<%
							for (Map.Entry<String, String> entry : orderProducts.entrySet()) {
								String key = entry.getKey();

								String licensesLeft = LanguageUtil.get(request, entry.getValue());
							%>

								<c:choose>
									<c:when test='<%= key.equals("basic") %>'>
										<option value="basic"><liferay-ui:message arguments='<%= new String[] {licensesLeft, licensesLeft.equals("1") ? "license" : "licenses"} %>' key="single-production-server-x-x-left" /></option>
										<option value="basic-cluster"><liferay-ui:message arguments='<%= new String[] {licensesLeft, licensesLeft.equals("1") ? "license" : "licenses"} %>' key="create-new-cluster-production-servers-x-x-left" /></option>
									</c:when>
									<c:when test='<%= key.startsWith("basic-") %>'>
										<option value="<%= key %>"><liferay-ui:message arguments='<%= new String[] {licensesLeft, licensesLeft.equals("1") ? "server" : "servers"} %>' key="join-existing-cluster-x-x-left" /></option>
									</c:when>
									<c:otherwise>
										<option value="<%= key %>"><liferay-ui:message arguments='<%= new String[] {key, licensesLeft, licensesLeft.equals("1") ? "license" : "licenses"} %>' key="x-x-x-left" /></option>
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
						<liferay-ui:message key="maximum-servers" />
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
				<input class="btn btn-default" type="submit" value="<liferay-ui:message key="register" />" />

				<input onClick="location.href='<%= HtmlUtil.escapeJS(themeDisplay.getURLCurrent()) %>';" type="button" value="<liferay-ui:message key="cancel" />" />
			</c:when>
			<c:otherwise>
				<input class="btn btn-default" type="submit" value="<liferay-ui:message key="query" />" />
			</c:otherwise>
		</c:choose>
	</form>
</div>