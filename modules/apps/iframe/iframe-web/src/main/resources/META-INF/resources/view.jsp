<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<c:choose>
	<c:when test="<%= iFramePortletInstanceConfiguration.auth() && Validator.isNull(iFrameDisplayContext.getUserName()) && !themeDisplay.isSignedIn() %>">
		<clay:alert
			displayType="info"
		>
			<clay:link
				href="<%= themeDisplay.getURLSignIn() %>"
				label="please-sign-in-to-access-this-application"
				target="_top"
			/>
		</clay:alert>
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
	<aui:script use="aui-base,querystring">
		var A = AUI();

		function init() {
			var hash = document.location.hash.replace('#', '');

			var hashSearch = new URLSearchParams(hash);

			hash = hashSearch.get('<portlet:namespace />');

			if (hash) {
				hash = String(hash);
			}

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

				iframe.on('load', monitorIframe);
			}
		}

		function monitorIframe() {
			var url = null;

			try {
				var iframe = document.getElementById('<portlet:namespace />iframe');

				url = iframe.contentWindow.document.location.href;

				iframe.contentWindow.Liferay.on('endNavigate', monitorIframe);
			}
			catch (e) {
				return true;
			}

			var baseSrc =
				'<%= HtmlUtil.escapeJS(iFrameDisplayContext.getIframeBaseSrc()) %>';
			var iframeSrc =
				'<%= HtmlUtil.escapeJS(iFrameDisplayContext.getIframeSrc()) %>';
			var hasBaseSrc = A.Lang.String.startsWith(url, baseSrc);

			if (hasBaseSrc) {
				url = url.substring(baseSrc.length);

				updateHash(url);
			}
			else if (!(url == iframeSrc || url == iframeSrc + '/') && !hasBaseSrc) {
				updateHash(url);
			}
		}

		function updateHash(url) {
			var A = AUI();

			var hash = document.location.hash.replace('#', '');

			var hashSearch = new URLSearchParams(hash);

			hashSearch.set('<portlet:namespace />', url);

			hash = hashSearch.toString();

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
		}

		init();
	</aui:script>
</c:if>

<aui:script use="aui-autosize-iframe">
	var iframe = A.one('#<portlet:namespace />iframe');

	function isNested(initial = window, parentUrls = []) {
		var isTop = initial === initial.Liferay.Util.getTop();

		if (isTop) {
			return false;
		}

		var href = initial.location.href;

		if (parentUrls.length > 2 && parentUrls.includes(href)) {
			return true;
		}

		return isNested(initial.Liferay.Util.getTop(), parentUrls.concat([href]));
	}

	if (iframe) {
		if (!isNested()) {
			iframe.set(
				'src',
				'<%= HtmlUtil.escapeJS(iFrameDisplayContext.getIframeSrc()) %>'
			);
		}

		iframe.plug(A.Plugin.AutosizeIframe, {
			monitorHeight: <%= iFramePortletInstanceConfiguration.resizeAutomatically() %>,
		});

		iframe.on('load', () => {
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
			'<%= HtmlUtil.escapeJS(iFrameDisplayContext.getIframeSrc()) %>',
			{
				headers: headers,
				mode: 'no-cors',
			}
		);
	</aui:script>
</c:if>