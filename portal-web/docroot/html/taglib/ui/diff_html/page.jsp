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

<%@ include file="/html/taglib/init.jsp" %>

<%
String diffHtmlResults = (String)request.getAttribute("liferay-ui:diff-html:diffHtmlResults");
String infoMessage = (String)request.getAttribute("liferay-ui:diff-html:infoMessage");
%>

<div id="<portlet:namespace />diffContainerHtmlResults">
	<c:choose>
		<c:when test="<%= Validator.isNotNull(diffHtmlResults) %>">
			<div class="taglib-diff-html">
				<%= diffHtmlResults %>
			</div>

			<aui:script>
				function updateOverlays() {
					var images = document.getElementsByTagName('img');

					for (var i = 0; i < images.length; i++) {
						var image = images[i];

						var imageChangeType = image.getAttribute('changeType');

						if ((imageChangeType == 'diff-removed-image') ||
							(imageChangeType == 'diff-added-image')) {

							var filter = null;
							var existingDivs = image.parentNode.getElementsByTagName('div');

							if ((existingDivs.length > 0) &&
								(existingDivs[0].className == imageChangeType)) {

								filter = existingDivs[0];
							}
							else {
								filter = document.createElement('div');

								filter.className= image.getAttribute('changeType');
							}

							filter.style.height = image.offsetHeight - 4 + 'px';
							filter.style.width = image.offsetWidth - 4 + 'px';

							if (image.y && image.x) {

								// Workaround for IE

								filter.style.top = image.y + 'px';
								filter.style.left = image.x - 1 + 'px';
							}

							if (existingDivs.length == 0) {
								image.parentNode.insertBefore(filter, image);
							}
						}
					}
				}
			</aui:script>
		</c:when>
		<c:otherwise>
			<div class="alert alert-info">
				<c:choose>
					<c:when test="<%= Validator.isNotNull(infoMessage) %>">
						<liferay-ui:message key="<%= infoMessage %>" />
					</c:when>
					<c:otherwise>
						<liferay-ui:message key="these-versions-are-not-comparable" />
					</c:otherwise>
				</c:choose>
			</div>
		</c:otherwise>
	</c:choose>
</div>