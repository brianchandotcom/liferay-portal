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
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectFolder");
%>

<aui:form cssClass="container-fluid-1280" name="selectFolderFm">
	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<div class="portlet-journal-tree" id="<portlet:namespace />folderContainer">
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
					var treeItemId = event.newVal.replace(/(^,)|(,$)/g, '');

					var selectedFolderNode = dom.toElement('[data-treeitemid="' + treeItemId + '"]');

					var data = {
						folderId: treeItemId,
						folderName: selectedFolderNode.getAttribute('data-treeitemname')
					};

					Liferay.Util.getOpener().Liferay.fire(
						'<%= HtmlUtil.escapeJS(eventName) %>',
						{
							data: data
						}
					);
				}
			},
			nodes: [<%= journalDisplayContext.getFoldersJSON() %>],
			pathThemeImages: '<%= themeDisplay.getPathThemeImages() %>'
		},
		'#<portlet:namespace />folderContainer'
	);
</aui:script>