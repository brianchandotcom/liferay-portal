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

<%@ include file="/html/taglib/ui/social_bookmark/init.jsp" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_ui_social_bookmark_facebook") + StringPool.UNDERLINE;

String facebookDisplayStyle = "button_count";

if (displayStyle.equals("simple")) {
	facebookDisplayStyle = "button";
}
else if (displayStyle.equals("vertical")) {
	facebookDisplayStyle = "box_count";
}
%>

<liferay-util:html-bottom outputKey="taglib_ui_social_bookmark_facebook">
	<script data-senna-track="temporary" type="text/javascript">
		(function(doc, scriptTagName, id) {
			if (doc.getElementById(id)) {
				return;
			}

			var facebookScriptNode = doc.createElement(scriptTagName);

			facebookScriptNode.id = id;

			facebookScriptNode.src = '//connect.facebook.net/<%= locale.getLanguage() %>_<%= locale.getCountry() %>/sdk.js#xfbml=1&version=v2.10';

			var firstScriptNode = doc.getElementsByTagName(scriptTagName)[0];

			firstScriptNode.parentNode.insertBefore(facebookScriptNode, firstScriptNode);
		}(document, 'script', 'facebook-jssdk'));

		(function() {
			if (FB && typeof(FB) !== 'undefined') {
				var fbLike = document.getElementById('<%= randomNamespace %>');

				FB.XFBML.parse(fbLike);
			}
		}());
	</script>
</liferay-util:html-bottom>

<div id="<%= randomNamespace %>fbRoot">
	<div class="fb-like"
		data-action="like"
		data-height="<%= (facebookDisplayStyle.equals("standard") || facebookDisplayStyle.equals("button_count")) ? 20 : StringPool.BLANK %>"
		data-href="<%= url %>"
		data-layout="<%= facebookDisplayStyle %>"
		data-size="small"
		data-show-faces="true"
	>
	</div>
</div>