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

<%@ include file="/publications/init.jsp" %>

<%
long ctCollectionId = ParamUtil.getLong(request, "ctCollectionId");
%>

<div class="modal-iframe-wrapper">
	<header class="modal-header modal-iframe-header">
		<h2 class="modal-title"><liferay-ui:message key="invite-users" /></h2>

		<button aria-label="close" class="btn btn-unstyled close modal-closer" type="button">
			<clay:icon
				symbol="times"
			/>
		</button>
	</header>

	<div class="modal-iframe-content">
		<react:component
			module="publications/js/components/manage-collaborators-modal/ManageCollaborators"
			props='<%=
				HashMapBuilder.<String, Object>put(
					"onlyForm", true
				).putAll(
					publicationsDisplayContext.getCollaboratorsReactData(ctCollectionId, false)
				).build()
			%>'
		/>
	</div>
</div>

<aui:script>
	window.addEventListener('keyup', (event) => {
		event.preventDefault();

		if (event.key === 'Escape') {
			window.top.Liferay.fire('close-modal');
		}
	});

	window.top.Liferay.fire('is-loading-modal', {isLoading: false});

	document.querySelectorAll('.modal-closer').forEach((trigger) => {
		trigger.addEventListener('click', (e) => {
			e.preventDefault();
			window.top.Liferay.fire('close-modal');
		});
	});
</aui:script>