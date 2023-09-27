<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/portal/init.jsp" %>

<%
boolean invokingSession = false;

if (Objects.equals(session.getId(), MaintenanceUtil.getSessionId())) {
	invokingSession = true;
}
%>

<html>
	<head>
		<meta content="30; url=<%= PortalUtil.getPortalURL(request) %>" http-equiv="refresh" />
	</head>

	<body>
		<center>
			<table border="0" cellpadding="0" cellspacing="0" height="100%" width="700">
				<tr>
					<td align="center" valign="middle">
						<table border="0" cellpadding="1" cellspacing="0" width="100%">
							<tr>
								<td bgcolor="#FF0000">
									<table border="0" cellpadding="0" cellspacing="0" width="100%">
										<tr>
											<td bgcolor="#FFFFFF">
												<br />

												<table border="0" cellpadding="10" cellspacing="0" width="100%">
													<tr>
														<td align="center">
															The system is currently undergoing maintenance. Please try again later.
														</td>
													</tr>

													<c:if test="<%= invokingSession %>">
														<tr>
															<td>
																<%= MaintenanceUtil.getStatus() %>
															</td>
														</tr>
													</c:if>
												</table>

												<br />
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</center>
	</body>
</html>