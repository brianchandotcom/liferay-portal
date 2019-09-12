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

<aui:script require="metal-dom/src/all/dom as dom">
	function handleIframeMessage(event) {
		if (event.data) {
			var virtualDocument = document.createElement('html');

			virtualDocument.innerHTML = JSON.parse(event.data).data;

			var virtualBody = virtualDocument.querySelector('.portlet-body');

			if (virtualBody) {
				document.querySelector('.portlet-body').innerHTML =
					virtualBody.innerHTML;
			}

			dom.globalEval.runScriptsInElement(virtualBody);
		}
	}

	window.addEventListener('message', handleIframeMessage);
</aui:script>