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
	<c:when test="<%= iFramePortletInstanceConfiguration.auth() && Validator.isNull(iFrameDisplayContext.getUserName()) && !themeDisplay.isSignedIn() %>">
		<div class="alert alert-info">
			<a href="<%= themeDisplay.getURLSignIn() %>" target="_top"><liferay-ui:message key="please-sign-in-to-access-this-application" /></a>
		</div>
	</c:when>
	<c:otherwise>
		<div class="iframe-container">
			<iframe alt="<%= HtmlUtil.escapeAttribute(iFramePortletInstanceConfiguration.alt()) %>" border="<%= HtmlUtil.escapeAttribute(iFramePortletInstanceConfiguration.border()) %>" bordercolor="<%= HtmlUtil.escapeAttribute(iFramePortletInstanceConfiguration.bordercolor()) %>" frameborder="<%= HtmlUtil.escapeAttribute(iFramePortletInstanceConfiguration.frameborder()) %>" height="<%= iFramePortletInstanceConfiguration.resizeAutomatically() ? StringPool.BLANK : HtmlUtil.escapeAttribute(iFrameDisplayContext.getHeight()) %>" hspace="<%= HtmlUtil.escapeAttribute(iFramePortletInstanceConfiguration.hspace()) %>" id="<portlet:namespace />iframe" longdesc="<%= HtmlUtil.escapeAttribute(iFramePortletInstanceConfiguration.longdesc()) %>" name="<portlet:namespace />iframe" scrolling="<%= HtmlUtil.escapeAttribute(iFramePortletInstanceConfiguration.scrolling()) %>" title="<%= HtmlUtil.escapeAttribute(iFramePortletInstanceConfiguration.title()) %>" vspace="<%= HtmlUtil.escapeAttribute(iFramePortletInstanceConfiguration.vspace()) %>" width="<%= HtmlUtil.escapeAttribute(iFramePortletInstanceConfiguration.width()) %>">
				<liferay-ui:message arguments="<%= HtmlUtil.escape(iFrameDisplayContext.getIframeSrc()) %>" key="your-browser-does-not-support-inline-frames-or-is-currently-configured-not-to-display-inline-frames.-content-can-be-viewed-at-actual-source-page-x" translateArguments="<%= false %>" />
			</iframe>
		</div>
	</c:otherwise>
</c:choose>

<c:if test="<%= iFramePortletInstanceConfiguration.dynamicUrlEnabled() %>">
	<aui:script>
		Liferay.provide(
			window,
			'<portlet:namespace />init',
			function() {
				var A = AUI();

				var hash = document.location.hash.replace('#', '');

				var hashObj = A.QueryString.parse(hash);

				hash = hashObj['<portlet:namespace />'];

				var iframe = A.one('#<portlet:namespace />iframe');

				if (iframe) {
					if (hash) {
						var src = '';

						var baseSrc =
							'<%= HtmlUtil.escapeJS(iFrameDisplayContext.getIframeBaseSrc()) %>';

						if (
							!/^https?\:\/\//.test(hash) ||
							!A.Lang.String.startsWith(hash, baseSrc)
						) {
							src = A.QueryString.unescape(hash);
						}

						iframe.attr('src', baseSrc + src);
					}

					iframe.on('load', <portlet:namespace />monitorIframe);
				}
			},
			['aui-base', 'querystring']
		);

		Liferay.provide(
			window,
			'<portlet:namespace />monitorIframe',
			function() {
				var A = AUI();

				var url = null;

				try {
					var iframe = document.getElementById('<portlet:namespace />iframe');

					url = iframe.contentWindow.document.location.href;

					iframe.contentWindow.Liferay.on(
						'endNavigate',
						<portlet:namespace />monitorIframe
					);
				} catch (e) {
					return true;
				}

				var baseSrc =
					'<%= HtmlUtil.escapeJS(iFrameDisplayContext.getIframeBaseSrc()) %>';
				var iframeSrc =
					'<%= HtmlUtil.escapeJS(iFrameDisplayContext.getIframeSrc()) %>';
				var hasBaseSrc = A.Lang.String.startsWith(url, baseSrc);

				if (hasBaseSrc) {
					url = url.substring(baseSrc.length);

					<portlet:namespace />updateHash(url);
				} else if (
					!(url == iframeSrc || url == iframeSrc + '/') &&
					!hasBaseSrc
				) {
					<portlet:namespace />updateHash(url);
				}
			},
			['aui-base']
		);

		Liferay.provide(
			window,
			'<portlet:namespace />updateHash',
			function(url) {
				var A = AUI();

				var hash = document.location.hash.replace('#', '');

				var hashObj = A.QueryString.parse(hash);

				hashObj['<portlet:namespace />'] = url;

				hash = A.QueryString.stringify(hashObj);

				var maximize = A.one(
					'#p_p_id<portlet:namespace /> .portlet-maximize-icon a'
				);

				if (maximize) {
					var maximizeUrl = maximize.attr('href');

					maximizeUrl = maximizeUrl.split('#')[0];

					maximize.attr('href', maximizeUrl + '#' + hash);
				}

				var restore = A.one('#p_p_id<portlet:namespace /> a.portlet-icon-back');

				if (restore) {
					var restoreHREF = restore.attr('href');

					restoreHREF = restoreHREF.split('#')[0];

					restore.attr('href', restoreHREF + '#' + hash);
				}

				location.hash = hash;
			},
			['aui-base', 'querystring']
		);

		<portlet:namespace />init();
	</aui:script>
</c:if>

<aui:script use="aui-autosize-iframe">
	var iframe = A.one('#<portlet:namespace />iframe');

	if (iframe) {
		iframe.set(
			'src',
			'<%= HtmlUtil.escapeHREF(iFrameDisplayContext.getIframeSrc()) %>'
		);

		iframe.plug(A.Plugin.AutosizeIframe, {
			monitorHeight: <%= iFramePortletInstanceConfiguration.resizeAutomatically() %>
		});

		iframe.on('load', function() {
			var height = A.Plugin.AutosizeIframe.getContentHeight(iframe);

			if (height == null) {
				height =
					'<%= HtmlUtil.escapeJS(iFramePortletInstanceConfiguration.heightNormal()) %>';

				if (themeDisplay.isStateMaximized()) {
					height =
						'<%= HtmlUtil.escapeJS(iFramePortletInstanceConfiguration.heightMaximized()) %>';
				}

				iframe.setStyle('height', height);

				iframe.autosizeiframe.set('monitorHeight', false);
			}
		});
	}
</aui:script>

<c:if test='<%= iFramePortletInstanceConfiguration.auth() && StringUtil.equals(iFramePortletInstanceConfiguration.authType(), "basic") %>'>
	<aui:script>
		var headers = new Headers();

		headers.append(
			'Authorization',
			'Basic ' +
				btoa(
					'<%= iFramePortletInstanceConfiguration.basicUserName() %>:<%= iFramePortletInstanceConfiguration.basicPassword() %>'
				)
		);

		Liferay.Util.fetch(
			'<%= HtmlUtil.escapeHREF(iFrameDisplayContext.getIframeSrc()) %>',
			{
				headers: headers,
				mode: 'no-cors'
			}
		);
	</aui:script>
</c:if>