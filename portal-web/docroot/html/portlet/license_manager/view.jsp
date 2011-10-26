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

<%@ include file="/html/portlet/init.jsp" %>

<iframe allowTransparency="true" frameborder="0" id="<portlet:namespace />iframe" onLoad="<portlet:namespace />resizeHeight();" scrolling="no" src="/c/portal/license?p_p_state=pop_up" style="border: none; width: 100%;"></iframe>

<script type="text/javascript">
	function <portlet:namespace />resizeHeight() {
		var iframe = document.getElementById("<portlet:namespace />iframe");

		var iframeBody = iframe.contentWindow.document.body;
		var iframeHtml = iframe.contentWindow.document.documentElement;

		iframe.height = Math.max(iframeBody.offsetHeight, iframeBody.scrollHeight, iframeBody.clientHeight, iframeHtml.offsetHeight, iframeHtml.scrollHeight, iframeHtml.clientHeight);
	}
</script>