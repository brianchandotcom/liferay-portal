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
LayoutItemSelectorViewDisplayContext layoutItemSelectorViewDisplayContext = (LayoutItemSelectorViewDisplayContext)request.getAttribute(BaseLayoutsItemSelectorView.LAYOUT_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT);

Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletDisplay.getId());
%>

<liferay-util:html-top>
	<link href="<%= PortalUtil.getStaticResourceURL(request, application.getContextPath() + "/css/main.css", portlet.getTimestamp()) %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<div class="container-fluid-1280 layouts-selector">
	<aui:form cssClass="container-fluid-1280" name="selectDisplayPageFm">
		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<div class="portlet-journal-tree" id="<portlet:namespace />displayPageContainer">
				</div>
			</aui:fieldset>
		</aui:fieldset-group>
	</aui:form>

	<aui:script require="journal-web/js/CardsTreeView.es,metal-dom/src/dom">
		var CardsTreeView = journalWebJsCardsTreeViewEs.default;
		var dom = metalDomSrcDom.default;

		new CardsTreeView(
			{
				events: {
					selectedNodesChanged: function(event) {
						var node = event.newVal[0];

						var data = {
							id: node.id,
							name: node.value
						};

						Liferay.Util.getOpener().Liferay.fire(
							'<%= HtmlUtil.escapeJS(layoutItemSelectorViewDisplayContext.getItemSelectedEventName()) %>',
							{
								data: data
							}
						);
					}
				},
				nodes: [<%= layoutItemSelectorViewDisplayContext.getLayoutsJSONObject() %>]
			},
			'#<portlet:namespace />displayPageContainer'
		);
	</aui:script>
</div>