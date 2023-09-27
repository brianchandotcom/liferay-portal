<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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