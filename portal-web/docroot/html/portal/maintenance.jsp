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

<%
boolean invokingSession = false;

if (session.getId().equals(MaintenanceUtil.getSessionId())) {
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

													<%
													if (invokingSession) {
													%>

														<tr>
															<td>
																<%= MaintenanceUtil.getStatus() %>
															</td>
														</tr>

													<%
													}
													%>

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