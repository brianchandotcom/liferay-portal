<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/taglib/init.jsp" %>

<%@ page import="com.liferay.util.RSSUtil" %>

<%
ResourceURL baseResourceURL = (ResourceURL)request.getAttribute("liferay-ui:rss:baseResourceURL");
String baseURL = (String)request.getAttribute("liferay-ui:rss:baseURL");
int delta = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:rss:delta"), SearchContainer.DEFAULT_DELTA);
String displayStyle = GetterUtil.get((String)request.getAttribute("liferay-ui:rss:displayStyle"), RSSUtil.DISPLAY_STYLE_FULL_CONTENT);
String[] format = (String[])request.getAttribute("liferay-ui:rss:format");

if (Validator.isNull(format)) {
	format = new String[] {RSSUtil.FEED_FORMAT_DEFAULT};
}

String label = (String)request.getAttribute("liferay-ui:rss:label");

if (baseResourceURL != null) {
	baseResourceURL.setCacheability(ResourceURL.FULL);

	if ((delta != SearchContainer.DEFAULT_DELTA) || !displayStyle.equals(RSSUtil.DISPLAY_STYLE_FULL_CONTENT)) {
		if (delta != SearchContainer.DEFAULT_DELTA) {
			baseResourceURL.setParameter("max", String.valueOf(delta));
		}

		if (!displayStyle.equals(RSSUtil.DISPLAY_STYLE_FULL_CONTENT)) {
			baseResourceURL.setParameter("displayStyle", displayStyle);
		}
	}
}
else if (Validator.isNotNull(baseURL)) {
	if ((delta != SearchContainer.DEFAULT_DELTA) || !displayStyle.equals(RSSUtil.DISPLAY_STYLE_FULL_CONTENT)) {
		if (delta != SearchContainer.DEFAULT_DELTA) {
			baseURL = HttpUtil.addParameter(baseURL, "max", delta);
		}

		if (!displayStyle.equals(RSSUtil.DISPLAY_STYLE_FULL_CONTENT)) {
			baseURL = HttpUtil.addParameter(baseURL, "displayStyle", displayStyle);
		}
	}
}
%>

<c:if test="<%= Validator.isNotNull(label) %>">
	<liferay-ui:message key="<%= label %>" />
</c:if>

<%
for (String feedFormat : format) {
%>

<liferay-ui:icon
	image="rss"
	label="<%= true %>"
	message="<%= feedFormat %>"
	target="_blank"
	url="<%= _getRssURL(baseResourceURL, baseURL, feedFormat) %>"
/>

<%
}
%>

<%!
private static String _getRssURL(ResourceURL baseResourceURL, String baseURL, String feedFormat) {
	String rssFormatType = RSSUtil.getFormatType(feedFormat);
	double rssFormatVersion = RSSUtil.getFormatVersion(feedFormat);

	if (baseResourceURL != null) {
		ResourceURL resourceURL = baseResourceURL;

		if (!rssFormatType.equals(RSSUtil.TYPE_DEFAULT) || (rssFormatVersion != RSSUtil.VERSION_DEFAULT)) {
			if (!rssFormatType.equals(RSSUtil.TYPE_DEFAULT)) {
				resourceURL.setParameter("type", rssFormatType);
			}

			if (rssFormatVersion != RSSUtil.VERSION_DEFAULT) {
				resourceURL.setParameter("version", String.valueOf(rssFormatVersion));
			}
		}

		return resourceURL.toString();
	}
	else if (baseURL != null) {
		String url = baseURL;

		if (!rssFormatType.equals(RSSUtil.TYPE_DEFAULT) || (rssFormatVersion != RSSUtil.VERSION_DEFAULT)) {
			if (!rssFormatType.equals(RSSUtil.TYPE_DEFAULT)) {
				url = HttpUtil.addParameter(url, "type", rssFormatType);
			}

			if (rssFormatVersion != RSSUtil.VERSION_DEFAULT) {
				url = HttpUtil.addParameter(url, "version", rssFormatVersion);
			}
		}

		return url.toString();
	}

	return StringPool.BLANK;
}
%>