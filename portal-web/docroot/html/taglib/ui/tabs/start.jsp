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

<%@ include file="/html/taglib/ui/tabs/init.jsp" %>

<%

// URL

LiferayPortletURL portletURL = (LiferayPortletURL)request.getAttribute("liferay-ui:tabs:portletURL");

String url = GetterUtil.getString((String)request.getAttribute("liferay-ui:tabs:url"));
String anchor = StringPool.BLANK;
String separator = StringPool.AMPERSAND;

if (url != null) {

	// Strip existing tab parameter and value from the URL

	int x = url.indexOf(param + "=");

	if (x != -1) {
		int y = url.lastIndexOf("&", x);

		if (y == -1) {
			y = url.lastIndexOf("?", x);
		}

		int z = url.indexOf("&", y + 1);

		if (z == -1) {
			z = url.length();
		}

		url = url.substring(0, y) + url.substring(z);
	}

	// Strip trailing &

	if (url.endsWith("&")) {
		url = url.substring(0, url.length() - 1);
	}

	// Strip anchor

	String[] urlArray = PortalUtil.stripURLAnchor(url, "&#");

	anchor = urlArray[1];
	url = urlArray[0];

	if (!url.contains(StringPool.QUESTION)) {
		separator = StringPool.QUESTION;
	}
}

// Back url

String backLabel = (String)request.getAttribute("liferay-ui:tabs:backLabel");
String backURL = (String)request.getAttribute("liferay-ui:tabs:backURL");

if (Validator.isNotNull(backURL) && !backURL.equals("javascript:history.go(-1);")) {
	backURL = HtmlUtil.escapeHREF(PortalUtil.escapeRedirect(backURL));
}

// CSS class

String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:tabs:cssClass"));

// Refresh

boolean refresh = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:tabs:refresh"));

// onClick

String onClick = GetterUtil.getString((String)request.getAttribute("liferay-ui:tabs:onClick"));

// Type

String type = GetterUtil.getString((String)request.getAttribute("liferay-ui:tabs:type"), "tabs");
%>

<c:if test="<%= names.length > 0 %>">

	<%
	String oldPortletURLValue = null;

	if ((portletURL != null) && (param != null)) {
		oldPortletURLValue = portletURL.getParameter(param);
	}
	%>

	<input name="<%= namespace %><%= param %>TabsScroll" type="hidden" />

	<c:choose>
		<c:when test='<%= type.equals("dropdown") %>'>

			<%
			String name = value;

			int pos = Arrays.binarySearch(values, value);

			if (pos != -1) {
				name = names[pos];
			}
			%>

			<nav class="navbar navbar-default <%= cssClass %>">
				<div class="container-fluid">
					<ul class="nav navbar-nav">
						<c:if test="<%= names.length > 1 %>">
							<li class="active dropdown">
								<a class="dropdown-toggle" data-toggle="dropdown" href="javascript:;">
									<span id="<%= namespace + param + "dropdownTitle" %>"><%= LanguageUtil.get(resourceBundle, HtmlUtil.escape(name)) %></span>

									<span class="caret"></span>
								</a>

								<ul class="dropdown-menu">
						</c:if>
		</c:when>
		<c:otherwise>
			<ul class="lfr-nav nav nav-<%= type %> <%= cssClass %>" data-tabs-namespace="<%= namespace + param %>">
		</c:otherwise>
	</c:choose>

	<%
	for (int i = 0; i < values.length; i++) {
		String curURL = StringPool.BLANK;

		if ((urls != null) && (i < urls.length)) {
			curURL = urls[i];
		}

		if (Validator.isNull(curURL)) {
			if (values.length == 1) {
				/*if (Validator.isNotNull(backURL)) {
					curURL = backURL;
				}*/
			}
			else {
				if (refresh) {
					if (portletURL != null) {
						portletURL.setParameter(param, values[i]);

						curURL = portletURL.toString();
					}
					else {
						if (values[i].equals("&raquo;")) {
							curURL = url + separator + namespace + param + "=" + values[0] + anchor;
						}
						else {
							curURL = url + separator + namespace + param + "=" + values[i] + anchor;
						}
					}
				}
				else {
					curURL = "javascript:";

					if (Validator.isNotNull(formName)) {
						curURL += "document." + namespace + formName + "." + namespace + param + ".value = '" + names[i] + "';";
					}

					curURL += "Liferay.Portal.Tabs.show('" + namespace + param + "', " + namesJS + ", '" + UnicodeFormatter.toString(names[i]) + "');";
				}
			}
		}

		String curOnClick = StringPool.BLANK;

		if (Validator.isNotNull(onClick)) {
			if (refresh) {
				curOnClick = onClick + "('" + curURL + "', '" + values[i] + "'); return false;";
			}
			else {
				curOnClick = "Liferay.Portal.Tabs.show('" + namespace + param + "', " + namesJS + ", '" + UnicodeFormatter.toString(names[i]) + "', " + onClick + ");";
				curURL = "javascript:;";
			}
		}

		boolean selected = (values.length == 1) || value.equals(values[i]);

		String cssClassName = "tab";

		if (selected) {
			cssClassName += " active";
		}
	%>

		<li class="<%= cssClassName %>" data-tab-name="<%= names[i] %>" id="<%= namespace %><%= param %><%= StringUtil.toCharCode(values[i]) %>TabsId">
			<a href="<%= Validator.isNotNull(curURL) ? HtmlUtil.escapeAttribute(curURL) : "javascript:;" %>" onClick="<%= Validator.isNotNull(curOnClick) ? curOnClick : StringPool.BLANK %>">
				<%= LanguageUtil.get(resourceBundle, HtmlUtil.escape(names[i])) %>
			</a>
		</li>

	<%
	}
	%>

	<c:if test="<%= Validator.isNotNull(backURL) %>">
		<li>
			<a class="tab" href="<%= backURL %>" id="<%= namespace %><%= param %>TabsBack"><%= Validator.isNotNull(backLabel) ? HtmlUtil.escape(backLabel) : "&laquo;" + LanguageUtil.get(resourceBundle, "back") %></a>
		</li>
	</c:if>

	<c:choose>
		<c:when test='<%= type.equals("dropdown") %>'>
						<c:if test="<%= names.length == 1 %>">
								</ul>
							</li>
						</c:if>
					</ul>
				</div>
			</nav>
		</c:when>
		<c:otherwise>
			</ul>
		</c:otherwise>
	</c:choose>

	<%
	if ((portletURL != null) && (param != null) && (oldPortletURLValue != null)) {
		portletURL.setParameter(param, oldPortletURLValue);
	}
	%>

</c:if>