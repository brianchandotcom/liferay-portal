<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
SocialBookmark socialBookmark = (SocialBookmark)request.getAttribute("liferay-social-bookmarks:bookmark:socialBookmark");
String title = GetterUtil.getString((String)request.getAttribute("liferay-social-bookmarks:bookmark:title"));
String url = GetterUtil.getString((String)request.getAttribute("liferay-social-bookmarks:bookmark:url"));
%>

<clay:link
	additionalProps='<%= (HashMap)request.getAttribute("liferay-social-bookmarks:bookmark:additionalProps") %>'
	aria-label="<%= socialBookmark.getName(request.getLocale()) %>"
	borderless="<%= true %>"
	cssClass="lfr-portal-tooltip"
	displayType="secondary"
	href="<%= socialBookmark.getPostURL(title, url) %>"
	icon="social-facebook"
	monospaced="<%= true %>"
	outline="<%= true %>"
	propsTransformer="js/OpenSocialBookmarkPropsTransformer"
	small="<%= true %>"
	title="<%= socialBookmark.getName(request.getLocale()) %>"
	type="button"
/>