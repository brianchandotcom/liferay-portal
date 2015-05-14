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

<c:choose>
	<c:when test="<%= iFrameDisplayContext.isAuth() && Validator.isNull(iFrameDisplayContext.getUserName()) && !themeDisplay.isSignedIn() %>">
		<div class="alert alert-info">
			<a href="<%= themeDisplay.getURLSignIn() %>" target="_top"><liferay-ui:message key="please-sign-in-to-access-this-application" /></a>
		</div>
	</c:when>
	<c:otherwise>
		<div class="iframe-container">
			<iframe alt="<%= HtmlUtil.escapeAttribute(iFrameDisplayContext.getAlt()) %>" border="<%= HtmlUtil.escapeAttribute(iFrameDisplayContext.getBorder()) %>" bordercolor="<%= HtmlUtil.escapeAttribute(iFrameDisplayContext.getBordercolor()) %>" frameborder="<%= HtmlUtil.escapeAttribute(iFrameDisplayContext.getFrameborder()) %>" height="<%= HtmlUtil.escapeAttribute(iFrameDisplayContext.getHeight()) %>" hspace="<%= HtmlUtil.escapeAttribute(iFrameDisplayContext.getHspace()) %>" id="<portlet:namespace />iframe" longdesc="<%= HtmlUtil.escapeAttribute(iFrameDisplayContext.getLongdesc()) %>" name="<portlet:namespace />iframe" onload="<portlet:namespace />monitorIframe();" scrolling="<%= HtmlUtil.escapeAttribute(iFrameDisplayContext.getScrolling()) %>" src="<%= HtmlUtil.escapeHREF(iFrameDisplayContext.getIframeSrc()) %>" title="<%= HtmlUtil.escapeAttribute(iFrameDisplayContext.getTitle()) %>" vspace="<%= HtmlUtil.escapeAttribute(iFrameDisplayContext.getVspace()) %>" width="<%= HtmlUtil.escapeAttribute(iFrameDisplayContext.getWidth()) %>">
				<liferay-ui:message
					arguments="<%= HtmlUtil.escape(iFrameDisplayContext.getIframeSrc()) %>"
					key="your-browser-does-not-support-inline-frames-or-is-currently-configured-not-to-display-inline-frames.-content-can-be-viewed-at-actual-source-page-x"
					translateArguments="<%= false %>"
				/>
			</iframe>
		</div>
	</c:otherwise>
</c:choose>

<aui:script>
	function <portlet:namespace />monitorIframe() {
		var A = AUI();

		var url = null;

		try {
			var iframe = document.getElementById('<portlet:namespace />iframe');

			url = iframe.contentWindow.document.location.href;
		}
		catch (e) {
			return true;
		}

		var baseSrc = '<%= HtmlUtil.escapeJS(iFrameDisplayContext.getIframeBaseSrc()) %>';
		var iframeSrc = '<%= HtmlUtil.escapeJS(iFrameDisplayContext.getIframeSrc()) %>';

		if ((url == iframeSrc) || (url == (iframeSrc + '/'))) {
		}
		else if (A.Lang.String.startsWith(url, baseSrc)) {
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

			// LPS-33951

			if (!A.UA.gecko) {
				hash = A.QueryString.unescape(hash);
			}

			var hashObj = A.QueryString.parse(hash);

			hash = hashObj['<portlet:namespace />'];

			if (hash) {
				var src = '';

				if (!(/^https?\:\/\//.test(hash))) {
					src = '<%= HtmlUtil.escapeJS(iFrameDisplayContext.getIframeBaseSrc()) %>';
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

			// LPS-33951

			location.hash = A.QueryString.escape(hash);
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
				monitorHeight: <%= iFrameDisplayContext.isResizeAutomatically() %>
			}
		);

		iframe.on(
			'load',
			function() {
				var height = A.Plugin.AutosizeIframe.getContentHeight(iframe);

				if (height == null) {
					height = '<%= HtmlUtil.escapeJS(iFrameDisplayContext.getHeightNormal()) %>';

					if (themeDisplay.isStateMaximized()) {
						height = '<%= HtmlUtil.escapeJS(iFrameDisplayContext.getHeightMaximized()) %>';
					}

					iframe.setStyle('height', height);

					iframe.autosizeiframe.set('monitorHeight', false);
				}
			}
		);
	}
</aui:script>