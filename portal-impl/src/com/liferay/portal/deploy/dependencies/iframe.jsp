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

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HttpUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %>

<%@ page import="java.util.HashMap" %><%@
page import="java.util.Map" %>

<%@ page import="javax.portlet.PortletURL" %>

<portlet:defineObjects />

<%
String defaultHeight = GetterUtil.getString(portletConfig.getInitParameter("wai.connector.iframe.height.default"), "500");

Map<String, String[]> parameterMap = new HashMap<String, String[]>(renderRequest.getParameterMap());

String appURL = ParamUtil.getString(request, "appURL", PortalUtil.getPathContext(renderRequest));

parameterMap.remove("appURL");

appURL = appURL.concat(HttpUtil.parameterMapToString(parameterMap));

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("appURL", StringPool.BLANK);
%>

<div id="<portlet:namespace />iframeDiv">
	<iframe frameborder="0" height="<%= defaultHeight %>" id="<portlet:namespace />iframe" src="<%= appURL %>" width="100%"></iframe>
</div>

<div id="<portlet:namespace />bookmarkDiv">
	<a href="#">Permanent Link</a>
</div>

<script type="text/javascript">
	AUI().use(
		'aui-base',
		function(A) {
			var iframe = A.one('#<portlet:namespace />iframe');

			var getURL = function() {
				var location = iframe.get('contentWindow.document.location');

				if (location) {
					return location.pathname + location.search;
				}

				return null;
			};

			var bookmarkLink = A.one('#<portlet:namespace />bookmarkDiv a');

			var getHeight = function() {
				var body = iframe.get('contentWindow.document.body');

				if (body) {
					var max = 0;

					// The scrollHeight of the body does not always account for every
					// element. One solution is to manually check the position of the
					// bottom edge of every div.

					body.all('div').each(
						function(div) {
							var height = div.getY() + div.get('scrollHeight');

							if (height > max) {
								max = height;
							}
						}
					);

					var scrollHeight = body.get('scrollHeight');

					if (scrollHeight > max) {
						return scrollHeight;
					}
					else {
						return max;
					}
				}
				else {
					return <%= defaultHeight %>;
				}
			}

			var resizeIframe = function() {
				iframe.attr('height', getHeight());
			};

			var updateIframe = function() {
				bookmarkLink.attr('href', '<%= portletURL.toString() %>' + escape(getURL()));

				resizeIframe();
			};

			iframe.on('load', updateIframe);

			updateIframe();
		}
	);
</script>