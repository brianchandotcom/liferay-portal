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

<%@ include file="/html/portlet/iframe/init.jsp" %>

<%
String iframeSrc = StringPool.BLANK;

if (relative) {
	iframeSrc = themeDisplay.getPathContext();
}

iframeSrc += (String)request.getAttribute(WebKeys.IFRAME_SRC);

if (Validator.isNotNull(iframeVariables)) {
	if (iframeSrc.contains(StringPool.QUESTION)) {
		iframeSrc = iframeSrc.concat(StringPool.AMPERSAND).concat(StringUtil.merge(iframeVariables, StringPool.AMPERSAND));
	}
	else {
		iframeSrc = iframeSrc.concat(StringPool.QUESTION).concat(StringUtil.merge(iframeVariables, StringPool.AMPERSAND));
	}
}

String baseSrc = iframeSrc;

int lastSlashPos = iframeSrc.substring(7).lastIndexOf(StringPool.SLASH);

if (lastSlashPos != -1) {
	baseSrc = iframeSrc.substring(0, lastSlashPos + 8);
}

String iframeHeight = heightNormal;

if (windowState.equals(WindowState.MAXIMIZED)) {
	iframeHeight = heightMaximized;
}
%>

<c:choose>
	<c:when test="<%= auth && Validator.isNull(userName) && !themeDisplay.isSignedIn() %>">
		<div class="alert alert-info">
			<a href="<%= themeDisplay.getURLSignIn() %>" target="_top"><liferay-ui:message key="please-sign-in-to-access-this-application" /></a>
		</div>
	</c:when>
	<c:otherwise>
		<div>
			<iframe alt="<%= alt %>" border="<%= border %>" bordercolor="<%= bordercolor %>" frameborder="<%= frameborder %>" height="<%= iframeHeight %>" hspace="<%= hspace %>" id="<portlet:namespace />iframe" longdesc="<%= longdesc%>" name="<portlet:namespace />iframe" onload="<portlet:namespace />monitorIframe();" scrolling="<%= scrolling %>" src="<%= iframeSrc %>" title="<%= title %>" vspace="<%= vspace %>" width="<%= width %>">
				<%= LanguageUtil.format(pageContext, "your-browser-does-not-support-inline-frames-or-is-currently-configured-not-to-display-inline-frames.-content-can-be-viewed-at-actual-source-page-x", iframeSrc) %>
			</iframe>
		</div>
	</c:otherwise>
</c:choose>

<aui:script>
	function <portlet:namespace />monitorIframe() {
		var url = null;

		try {
			var iframe = document.getElementById('<portlet:namespace />iframe');

			url = iframe.contentWindow.document.location.href;
		}
		catch (e) {
			return true;
		}

		var baseSrc = '<%= baseSrc %>';
		var iframeSrc = '<%= iframeSrc %>';

		if ((url == iframeSrc) || (url == (iframeSrc + '/'))) {
		}
		else if (Liferay.Util.startsWith(url, baseSrc)) {
			url = url.substring(baseSrc.length);

			<portlet:namespace />updateHash(url);
		}
		else {
			<portlet:namespace />updateHash(url);
		}

		return true;
	}

	Liferay.provide(
		window,
		'<portlet:namespace />init',
		function() {
			var A = AUI();

			var hash = document.location.hash.replace('#', '');

			var hashObj = A.QueryString.parse(hash);

			hash = hashObj['<portlet:namespace />'];

			if (hash) {
				var src = '';

				if (!(/^https?\:\/\//.test(hash))) {
					src = '<%= baseSrc %>';
				}

				src += hash;

				var iframe = A.one('#<portlet:namespace />iframe');

				if (iframe) {
					iframe.attr('src', src);
				}
			}
		},
		['aui-base', 'querystring']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />updateHash',
		function(url) {
			var A = AUI();

			var hash = document.location.hash.replace('#', '');

			var hashObj = A.QueryString.parse(hash);

			hashObj['<portlet:namespace />'] = url;

			var maximize = A.one('#p_p_id<portlet:namespace /> .portlet-maximize-icon a');

			hash = A.QueryString.stringify(hashObj);

			if (maximize) {
				var href = maximize.attr('href');

				href = href.split('#')[0];

				maximize.attr('href', href + '#' + hash);
			}

			var restore = A.one('#p_p_id<portlet:namespace /> a.portlet-icon-back');

			if (restore) {
				var href = restore.attr('href');

				href = href.split('#')[0];

				restore.attr('href', href + '#' + hash);
			}

			location.hash = hash;
		},
		['aui-base', 'querystring']
	);

	<portlet:namespace />init();
</aui:script>

<aui:script use="aui-autosize-iframe">
	var iframe = A.one('#<portlet:namespace />iframe');

	if (iframe) {
		iframe.plug(
			A.Plugin.AutosizeIframe,
			{
				monitorHeight: <%= resizeAutomatically %>
			}
		);

		iframe.on(
			'load',
			function() {
				var height = A.Plugin.AutosizeIframe.getContentHeight(iframe);

				if (height == null) {
					height = '<%= heightNormal %>';

					if (themeDisplay.isStateMaximized()) {
						height = '<%= heightMaximized %>';
					}

					iframe.setStyle('height', height);

					iframe.autosizeiframe.set('monitorHeight', false);
				}
			}
		);
	}
</aui:script>