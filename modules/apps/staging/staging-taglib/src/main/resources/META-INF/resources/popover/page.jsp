<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/popover/init.jsp" %>

<c:if test="<%= Validator.isNotNull(text) %>">
	<span aria-label="<%= text %>" class="lfr-portal-tooltip" tabindex="0" title="<%= text %>">
		<clay:icon
			aria-label="<%= text %>"
			symbol="question-circle-full"
		/>
	</span>

	<aui:script use="aui-base">
		A.ready('aui-base', (A) => {
			var popoverNode = A.one('#<%= domId %>');

			var popover = popoverNode.one('.popover');

			var arrow = popover.one('.arrow');

			var dx = arrow.width();
			var dy = arrow.height() / 2;

			var iconHolderNode = popoverNode.one('.staging-taglib-popover-icon-holder');

			iconHolderNode.on('hover', function alignPopoverToIcon(event) {
				if ('visible' !== popover.get('visibility')) {
					popover.setXY([
						iconHolderNode.getX() + iconHolderNode.width() + dx,
						iconHolderNode.getY() - dy,
					]);
				}
			});
		});
	</aui:script>
</c:if>