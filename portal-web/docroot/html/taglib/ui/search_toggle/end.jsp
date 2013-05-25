<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/taglib/ui/search_toggle/init.jsp" %>

			</div>
		</div>
	</div>
</div>

<aui:script position="inline" use="aui-popover,event-key">
	var popover;

	var simpleNode = A.one('#<%= id %>simple');
	var advancedNode = A.one('#<%= id %>advanced');
	var toggleAdvancedNode = A.one('#<%= id %>toggleAdvanced');
	var keywordsNode = A.one('#<%= id + displayTerms.KEYWORDS %>');

	function enableOrDisableElements(event) {
		simpleNode.all('input').set('disabled', event.newVal);
		advancedNode.all('input').set('disabled', !event.newVal);
	}

	function getPopover() {
		if (!popover) {
			popover = new A.Popover(
				{
					after: {
						visibleChange: enableOrDisableElements
					},
					align: {
						node: toggleAdvancedNode,
						points:[A.WidgetPositionAlign.TR, A.WidgetPositionAlign.BR]
					},
					bodyContent: A.one('#<%= id %>advancedBodyNode'),
					boundingBox: advancedNode,
					position: 'bottom',
					srcNode: '#<%= id %>advancedContent',
					visible: false,
					width: <%= width %>,
					zIndex: Liferay.zIndex.ALERT
				}
			);
		}

		return popover;
	}

	function togglePopover(event) {
		popover = getPopover().render();

		var visible = popover.get('visible');

		popover.set('visible', !visible);

		if (visible) {
			keywordsNode.focus();
		}
		else {
			var inputTextNode = advancedNode.one('input[type=text]');

			if (inputTextNode) {
				inputTextNode.focus();
			}
		}

		var advancedSearchNode = advancedNode.one('#<%= id + displayTerms.ADVANCED_SEARCH %>');

		advancedSearchNode.val(!visible);

		event.preventDefault();
	}

	toggleAdvancedNode.on('click', togglePopover);
	keywordsNode.on('key', togglePopover, 'down:38,40');
</aui:script>