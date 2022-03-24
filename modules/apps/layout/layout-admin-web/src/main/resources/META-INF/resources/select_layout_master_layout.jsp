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

<%
SelectLayoutPageTemplateEntryDisplayContext selectLayoutPageTemplateEntryDisplayContext = new SelectLayoutPageTemplateEntryDisplayContext(request, liferayPortletResponse);

String backURL = selectLayoutPageTemplateEntryDisplayContext.getBackURL();

if (Validator.isNull(backURL)) {
	PortletURL portletURL = layoutsAdminDisplayContext.getPortletURL();

	backURL = portletURL.toString();
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle(LanguageUtil.get(request, "select-master-page"));
%>

<clay:container-fluid
	cssClass="container-view"
	id='<%= liferayPortletResponse.getNamespace() + "layoutPageTemplateEntries" %>'
>
	<clay:sheet
		size="full"
	>
		<div class="lfr-search-container-wrapper" id="<portlet:namespace />layoutTypes">
			<ul class="card-page card-page-equal-height">

				<%
				for (LayoutPageTemplateEntry masterLayoutPageTemplateEntry : selectLayoutPageTemplateEntryDisplayContext.getMasterLayoutPageTemplateEntries()) {
				%>

					<li class="card-page-item card-page-item-asset">
						<clay:vertical-card
							verticalCard="<%= new SelectLayoutMasterLayoutVerticalCard(masterLayoutPageTemplateEntry, renderRequest, renderResponse) %>"
						/>
					</li>

				<%
				}
				%>

			</ul>
		</div>
	</clay:sheet>
</clay:container-fluid>

<aui:script require="frontend-js-web/index as frontendJsWeb">
	var {delegate} = frontendJsWeb;

	var layoutPageTemplateEntries = document.getElementById(
		'<portlet:namespace />layoutPageTemplateEntries'
	);

	var addLayoutActionOptionQueryClickHandler = delegate(
		layoutPageTemplateEntries,
		'click',
		'.add-layout-action-option',
		(event) => {
			Liferay.Util.openModal({
				disableAutoClose: true,
				height: '60vh',
				id: '<portlet:namespace />addLayoutDialog',
				size: 'md',
				title: '<liferay-ui:message key="add-collection-page" />',
				url: event.delegateTarget.dataset.addLayoutUrl,
			});
		}
	);

	function handleDestroyPortlet() {
		addLayoutActionOptionQueryClickHandler.dispose();

		Liferay.detach('destroyPortlet', handleDestroyPortlet);
	}

	Liferay.on('destroyPortlet', handleDestroyPortlet);
</aui:script>