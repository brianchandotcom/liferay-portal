<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

		url = url.substring(0, y) + url.substring(z, url.length());
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
	backURL = HtmlUtil.escape(HtmlUtil.escapeHREF(PortalUtil.escapeRedirect(backURL)));
}

// Refresh

boolean refresh = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:tabs:refresh"));

// onClick

String onClick = GetterUtil.getString((String)request.getAttribute("liferay-ui:tabs:onClick"));
%>

<c:if test="<%= names.length > 0 %>">

	<%
	String oldPortletURLValue = null;

	if (portletURL != null) {
		oldPortletURLValue = portletURL.getParameter(param);
	}
	%>

	<c:choose>
		<c:when test="<%= themeDisplay.isFacebook() %>">
			<fb:tabs>
		</c:when>
		<c:otherwise>
			<input name="<%= namespace %><%= param %>TabsScroll" type="hidden" />

			<ul class="aui-tabview-list">
		</c:otherwise>
	</c:choose>

	<%
	for (int i = 0; i < values.length; i++) {
		String curURL = (String)request.getAttribute("liferay-ui:tabs:url" + i);

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
							curURL = url + separator + param + "=" + values[0] + anchor;
						}
						else {
							curURL = url + separator + param + "=" + values[i] + anchor;
						}
					}
				}
				else {
					curURL = "javascript:";

					if (Validator.isNotNull(formName)) {
						curURL += "document." + namespace + formName + "." + namespace + param + ".value = '" + names[i] + "';";
					}

					curURL += "Liferay.Portal.Tabs.show('" + namespace + param + "', " + namesJS + ", '" + names[i] + "');";
				}
			}
		}

		String curOnClick = StringPool.BLANK;

		if (Validator.isNotNull(onClick)) {
			if (refresh) {
				curOnClick = onClick + "('" + curURL + "', '" + values[i] + "'); return false;";
			}
			else {
				curOnClick = "Liferay.Portal.Tabs.show('" + namespace + param + "', " + namesJS + ", '" + names[i] + "', " + onClick + ");";

				curURL = "javascript:;";
			}
		}

		boolean selected = (values.length == 1) || value.equals(values[i]);

		String cssClassName = "aui-tab aui-state-default";

		if (selected) {
			cssClassName += " current aui-tab-active aui-state-active";
		}

		if (i == 0) {
			cssClassName += " first";
		}

		if (i == values.length - 1) {
			cssClassName += " last";
		}
	%>

		<c:choose>
			<c:when test="<%= themeDisplay.isFacebook() %>">
				<fb:tab_item
					align="left"
					href="<%= curURL %>"
					selected="<%= selected %>"
					title="<%= LanguageUtil.get(pageContext, names[i]) %>"
				/>
			</c:when>
			<c:otherwise>
				<li class="<%= cssClassName %>" id="<%= namespace %><%= param %><%= StringUtil.toCharCode(values[i]) %>TabsId">
					<span class="aui-tab-content">
						<c:choose>
							<c:when test="<%= Validator.isNotNull(curURL) %>">
								<a class="aui-tab-label" href="<%= curURL %>"
									<c:if test="<%= Validator.isNotNull(curOnClick) %>">
										onClick="<%= curOnClick %>"
									</c:if>
								>
							</c:when>
							<c:otherwise>
								<span class="aui-tab-label">
							</c:otherwise>
						</c:choose>

						<c:if test="<%= selected %>">
							<strong>
						</c:if>

						<%= LanguageUtil.get(pageContext, names[i]) %>

						<c:if test="<%= selected %>">
							</strong>
						</c:if>

						<c:choose>
							<c:when test="<%= Validator.isNotNull(curURL) %>">
								</a>
							</c:when>
							<c:otherwise>
								</span>
							</c:otherwise>
						</c:choose>
					</span>
				</li>
			</c:otherwise>
		</c:choose>

	<%
	}
	%>

	<c:if test="<%= Validator.isNotNull(backURL) %>">
		<c:choose>
			<c:when test="<%= themeDisplay.isFacebook() %>">
				<fb:tab_item
					align="left"
					href="<%= backURL %>"
					selected="<%= false %>"
					title='<%= Validator.isNotNull(backLabel) ? backLabel : "&laquo;" + LanguageUtil.get(pageContext, "back") %>'
				/>
			</c:when>
			<c:otherwise>
				<li class="aui-tab aui-tab-back toggle last">
					<span class="aui-tab-content aui-tab-back-content">
						<span class="aui-tab-label">
							<a href="<%= backURL %>" id="<%= namespace %><%= param %>TabsBack"><%= Validator.isNotNull(backLabel) ? backLabel : "&laquo;" + LanguageUtil.get(pageContext, "back") %></a>
						</span>
					</span>
				</li>
			</c:otherwise>
		</c:choose>
	</c:if>

	<c:choose>
		<c:when test="<%= themeDisplay.isFacebook() %>">
			</fb:tabs>
		</c:when>
		<c:otherwise>
			</ul>
		</c:otherwise>
	</c:choose>

	<%
	if (portletURL != null) {
		portletURL.setParameter(param, oldPortletURLValue);
	}
	%>

</c:if>