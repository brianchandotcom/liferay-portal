<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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