<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<div class="alert alert-info">
	<p>
		<liferay-ui:message key="your-current-portlet-information-is-as-follows" />
	</p>

	<p>
		<liferay-ui:message key="portlet-id" />: <strong>#portlet_<%= HtmlUtil.escapeJS(portletConfigurationCSSPortletDisplayContext.getPortletResource()) %></strong>
	</p>

	<p>
		<liferay-ui:message key="portlet-classes" />: <strong><span id="<portlet:namespace />portletClasses"></span></strong>
	</p>
</div>

<aui:input label="enter-your-custom-css-class-names" name="customCSSClassName" type="text" value="<%= portletConfigurationCSSPortletDisplayContext.getCustomCSSClassName() %>" />

<aui:input label="enter-your-custom-css" name="customCSS" type="textarea" value="<%= portletConfigurationCSSPortletDisplayContext.getCustomCSS() %>" />

<div id="lfr-add-rule-container">
	<aui:button cssClass="btn btn-sm" id="addId" value="add-a-css-rule-for-this-portlet" />

	<aui:button cssClass="btn btn-sm" id="addClass" value="add-a-css-rule-for-all-portlets-like-this-one" />
</div>

<aui:script>
	function <portlet:namespace />insertCustomCSSValue(value) {
		var customCSSTextarea = document.getElementById(
			'<portlet:namespace />customCSS'
		);

		if (customCSSTextarea) {
			var customCSSTextareaValue = customCSSTextarea.value.trim();

			if (customCSSTextareaValue.length) {
				customCSSTextareaValue += '\n\n';
			}

			var newValue = customCSSTextareaValue + value + ' {\n\t\n}\n';

			customCSSTextarea.value = newValue;

			customCSSTextarea.focus();

			var newCursorPosition = customCSSTextarea.value.length - 3;

			customCSSTextarea.setSelectionRange(
				newCursorPosition,
				newCursorPosition
			);
		}
	}

	var <portlet:namespace />addId = document.getElementById(
		'<portlet:namespace />addId'
	);

	if (<portlet:namespace />addId) {
		<portlet:namespace />addId.addEventListener('click', () => {
			<portlet:namespace />insertCustomCSSValue(
				'#portlet_<%= HtmlUtil.escapeJS(portletConfigurationCSSPortletDisplayContext.getPortletResource()) %>'
			);
		});
	}

	var <portlet:namespace />addClass = document.getElementById(
		'<portlet:namespace />addClass'
	);

	if (<portlet:namespace />addClass) {
		<portlet:namespace />addClass.addEventListener('click', () => {
			<portlet:namespace />insertCustomCSSValue(portletClasses);
		});
	}

	var portlet = Liferay.Util.getOpener()[
		'portlet_<%= HtmlUtil.escapeJS(portletConfigurationCSSPortletDisplayContext.getPortletResource()) %>'
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
				.replace(
					/(?:([\w\d-]+)-)?portlet(?:-?([\w\d-]+-?))?/g,
					(match, subMatch1, subMatch2) => {
						var regexIgnoredClasses = /boundary|draggable/;

						if (!regexIgnoredClasses.test(subMatch2)) {
							boundaryClasses.push(match);
						}
					}
				);

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