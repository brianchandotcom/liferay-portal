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

<%@ include file="/info_bar/init.jsp" %>

			</div>

			<c:if test="<%= Validator.isNotNull(buttons) %>">
				<div class="management-bar-header-right">
					<%= buttons %>
				</div>
			</c:if>
		</div>
	</div>
</div>

<c:if test="<%= fixed %>">
	<aui:script sandbox="<%= true %>">
		const element = document.querySelector('.info-bar-container');

		let lastPosition;
		let topOffset = 15;

		function checkPosition() {
			let newPosition = 'affix';

			if (document.defaultView.pageYOffset <= topOffset) {
				newPosition = 'affix-top';
			}

			if (element && newPosition !== lastPosition) {
				element.classList.add(newPosition);
				element.classList.remove(lastPosition);

				lastPosition = newPosition;
			}
		}

		checkPosition();

		document.addEventListener('scroll', checkPosition);
	</aui:script>
</c:if>