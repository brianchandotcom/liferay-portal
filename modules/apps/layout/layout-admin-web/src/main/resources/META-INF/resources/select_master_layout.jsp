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
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectMasterLayout");

SelectLayoutPageTemplateEntryDisplayContext selectLayoutPageTemplateEntryDisplayContext = new SelectLayoutPageTemplateEntryDisplayContext(request);

List<LayoutPageTemplateEntry> masterLayoutPageTemplateEntries = selectLayoutPageTemplateEntryDisplayContext.getMasterLayoutPageTemplateEntries();
%>

<aui:form cssClass="container-fluid-1280 mt-3" name="fm">
	<ul class="card-page card-page-equal-height">

		<%
		for (LayoutPageTemplateEntry masterLayoutPageTemplateEntry : masterLayoutPageTemplateEntries) {
		%>

			<li class="card-page-item col-md-3 col-sm-6 form-check-card">
				<clay:vertical-card
					verticalCard="<%= new SelectMasterLayoutVerticalCard(masterLayoutPageTemplateEntry, renderRequest) %>"
				/>
			</li>

		<%
		}
		%>

	</ul>
</aui:form>

<aui:script require="metal-dom/src/dom as dom">
	var delegateHandler = dom.delegate(
		document.body,
		'click',
		'.select-master-layout-option',
		function(event) {
			dom.removeClasses(
				document.querySelectorAll('.form-check-card.active'),
				'active'
			);
			dom.addClasses(
				dom.closest(event.delegateTarget, '.form-check-card'),
				'active'
			);

			Liferay.Util.getOpener().Liferay.fire(
				'<%= HtmlUtil.escape(eventName) %>',
				{
					data: event.delegateTarget.dataset,
				}
			);
		}
	);

	var onDestroyPortlet = function() {
		delegateHandler.removeListener();

		Liferay.detach('destroyPortlet', onDestroyPortlet);
	};

	Liferay.on('destroyPortlet', onDestroyPortlet);
</aui:script>