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

<div class="alert alert-info">
	<p>
		<liferay-ui:message key="your-current-portlet-information-is-as-follows" />
	</p>

	<p>
		<liferay-ui:message key="portlet-id" />: <strong>#portlet_<%= portletConfigurationCSSPortletDisplayContext.getPortletResource() %></strong>
	</p>

	<p>
		<liferay-ui:message key="portlet-classes" />: <strong><span id="<portlet:namespace />portletClasses"></span></strong>
	</p>
</div>

<aui:input label="enter-your-custom-css-class-names" name="customCSSClassName" type="text" value="<%= portletConfigurationCSSPortletDisplayContext.getCustomCSSClassName() %>" />

<aui:input label="enter-your-custom-css" name="customCSS" type="textarea" value="<%= portletConfigurationCSSPortletDisplayContext.getCustomCSS() %>" />

<div id="lfr-add-rule-container">
	<aui:button cssClass="btn btn-link" id="addId" value="add-a-css-rule-for-this-portlet" />

	<aui:button cssClass="btn btn-link" id="addClass" value="add-a-css-rule-for-all-portlets-like-this-one" />
</div>

<aui:script>
	function <portlet:namespace />insertCustomCSSValue(value) {
		var customCSS = document.getElementById('<portlet:namespace />customCSS');

		if (customCSS) {
			var customCSSVal = customCSS.value.trim();

			if (customCSSVal.length) {
				customCSSVal += '\n\n';
			}

			var newVal = customCSSVal + value + ' {\n\t\n}\n';

			customCSS.value = newVal;

			Liferay.Util.setCursorPosition(customCSS, newVal.length - 3);
		}
	}

	var <portlet:namespace />addId = document.getElementById(
		'<portlet:namespace />addId'
	);

	if (<portlet:namespace />addId) {
		<portlet:namespace />addId.addEventListener('click', function() {
			<portlet:namespace />insertCustomCSSValue(
				'#portlet_<%= portletConfigurationCSSPortletDisplayContext.getPortletResource() %>'
			);
		});
	}

	var <portlet:namespace />addClass = document.getElementById(
		'<portlet:namespace />addClass'
	);

	if (<portlet:namespace />addClass) {
		<portlet:namespace />addClass.addEventListener('click', function() {
			<portlet:namespace />insertCustomCSSValue(portletClasses);
		});
	}

	var portlet = Liferay.Util.getOpener()[
		'portlet_<%= portletConfigurationCSSPortletDisplayContext.getPortletResource() %>'
	];

	if (portlet) {
		var portletContent = portlet.querySelector('.portlet-content');

		if (portletContent) {
			var portletClasses;

			if (portlet != portletContent) {
				portletClasses = portlet
					.getAttribute('class')
					.replace(/(?:^|\s)portlet(?=\s|$)/g, '');

				portletClasses = portletClasses.replace(/\s+/g, '.').trim();

				if (portletClasses) {
					portletClasses = ' .' + portletClasses;
				}
			}

			var boundaryClasses = [];

			portletContent
				.getAttribute('class')
				.replace(/(?:([\w\d-]+)-)?portlet(?:-?([\w\d-]+-?))?/g, function(
					match,
					subMatch1,
					subMatch2
				) {
					var regexIgnoredClasses = /boundary|draggable/;

					if (!regexIgnoredClasses.test(subMatch2)) {
						boundaryClasses.push(match);
					}
				});

			portletClasses = '.' + boundaryClasses.join('.') + portletClasses;

			var portletClassesNode = document.getElementById(
				'<portlet:namespace />portletClasses'
			);

			if (portletClassesNode) {
				portletClassesNode.innerHTML = portletClasses;
			}
		}
	}
</aui:script>