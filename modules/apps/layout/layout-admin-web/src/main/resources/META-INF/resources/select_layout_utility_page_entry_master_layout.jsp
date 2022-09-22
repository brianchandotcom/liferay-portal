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
>
	<div class="lfr-search-container-wrapper">
		<ul class="card-page card-page-equal-height">

			<%
			for (LayoutPageTemplateEntry masterLayoutPageTemplateEntry : selectLayoutPageTemplateEntryDisplayContext.getMasterLayoutPageTemplateEntries()) {
			%>

				<li class="card-page-item card-page-item-asset">
					<clay:vertical-card
						additionalProps='<%=
							HashMapBuilder.<String, Object>put(
								"addLayoutUtilityPageUrl",
								PortletURLBuilder.createActionURL(
									renderResponse
								).setActionName(
									"/layout_admin/add_layout_utility_page_entry"
								).setRedirect(
									themeDisplay.getURLCurrent()
								).setParameter(
									"masterLayoutPlid", selectLayoutPageTemplateEntryDisplayContext.getMasterLayoutPlid()
								).setParameter(
									"type", selectLayoutPageTemplateEntryDisplayContext.getType()
								).buildString()
							).put(
								"dialogTitle", LanguageUtil.get(request, "add-utility-page")
							).put(
								"mainFieldLabel", LanguageUtil.get(request, "name")
							).put(
								"mainFieldName", "name"
							).put(
								"mainFieldPlaceholder", LanguageUtil.get(request, "name")
							).build()
						%>'
						propsTransformer="js/SelectLayoutUtilityPageEntryMasterLayoutVerticalCardPropsTransformer"
						verticalCard="<%= new SelectLayoutMasterLayoutVerticalCard(masterLayoutPageTemplateEntry, renderRequest, renderResponse) %>"
					/>
				</li>

			<%
			}
			%>

		</ul>
	</div>
</clay:container-fluid>