<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-ui:icon
	id="importIcon"
	message="import"
	onClick='<%= liferayPortletResponse.getNamespace() + "openImportModal();" %>'
	url="javascript:void(0);"
/>

<div>
	<react:component
		module="sxp_blueprint_admin/js/view_sxp_blueprints/ImportSXPBlueprintModal"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"componentId", liferayPortletResponse.getNamespace() + "importModal"
			).put(
				"redirectURL", currentURL
			).build()
		%>'
	/>
</div>

<aui:script>
	function <portlet:namespace />openImportModal() {
		Liferay.componentReady('<portlet:namespace />importModal').then(
			(importModal) => {
				importModal.open();
			}
		);
	}
</aui:script>