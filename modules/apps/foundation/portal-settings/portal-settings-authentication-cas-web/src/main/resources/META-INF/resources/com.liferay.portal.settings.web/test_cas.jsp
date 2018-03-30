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
String casLoginURL = ParamUtil.getString(request, "casLoginURL");
String casLogoutURL = ParamUtil.getString(request, "casLogoutURL");
String casServerURL = ParamUtil.getString(request, "casServerURL");
String casServiceURL = ParamUtil.getString(request, "casServiceURL");
%>

<table class="lfr-table lfr-table-grid">
	<tr>
		<td class="lfr-label">
			<liferay-ui:message key="login-url" />:
		</td>
		<td>

			<%
			String casLoginURLResult = _testURL(casLoginURL);

			String casLoginURLCssClass = _getCssClass(casLoginURLResult);
			%>

			<span class="<%= casLoginURLCssClass %>"><liferay-ui:message key="<%= casLoginURLResult %>" /></span> <span class="field-hint"><%= HtmlUtil.escape(casLoginURL) %></span>
		</td>
	</tr>
	<tr>
		<td class="lfr-label">
			<liferay-ui:message key="logout-url" />:
		</td>
		<td>

			<%
			String casLogoutURLResult = _testURL(casLogoutURL);

			String casLogoutURLCssClass = _getCssClass(casLogoutURLResult);
			%>

			<span class="<%= casLogoutURLCssClass %>"><liferay-ui:message key="<%= casLogoutURLResult %>" /></span> <span class="field-hint"><%= HtmlUtil.escape(casLogoutURL) %></span>
		</td>
	</tr>

	<c:if test="<%= Validator.isNotNull(casServerURL) %>">
		<tr>
			<td class="lfr-label">
				<liferay-ui:message key="server-url" />:
			</td>
			<td>

				<%
				String casServerURLResult = _testURL(casServerURL);

				String casServerURLCssClass = _getCssClass(casServerURLResult);
				%>

				<span class="<%= casServerURLCssClass %>"><liferay-ui:message key="<%= casServerURLResult %>" /></span> <span class="field-hint"><%= HtmlUtil.escape(casServerURL) %></span>
			</td>
		</tr>
	</c:if>

	<c:if test="<%= Validator.isNotNull(casServiceURL) %>">
		<tr>
			<td class="lfr-label">
				<liferay-ui:message key="service-url" />:
			</td>
			<td>

				<%
				String casServiceURLResult = _testURL(casServiceURL);

				String casServiceURLCssClass = _getCssClass(casServiceURLResult);
				%>

				<span class="<%= casServiceURLCssClass %>"><liferay-ui:message key="<%= casServiceURLResult %>" /></span> <span class="field-hint"><%= HtmlUtil.escape(casServiceURL) %></span>
			</td>
		</tr>
	</c:if>
</table>

<%!
private String _getCssClass(String result) {
	String cssClass = "lfr-status-";

	if (result.equals("pass")) {
		cssClass += "success";
	}
	else {
		cssClass += "error";
	}

	cssClass += "-label";

	return cssClass;
}

private String _testURL(String url) {
	try {
		URL urlObj = new URL(url);

		HttpURLConnection httpURLConnection = (HttpURLConnection)urlObj.openConnection();

		httpURLConnection.setConnectTimeout(3000);

		httpURLConnection.getResponseCode();
	}
	catch (MalformedURLException murle) {
		return "fail";
	}
	catch (Exception e) {
		String message = GetterUtil.getString(e.getMessage());

		if (message.contains("PKIX")) {
			return "ssl-error";
		}
		else {
			return "unreachable";
		}
	}

	return "pass";
}
%>