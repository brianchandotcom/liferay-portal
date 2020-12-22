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
String redirect = ParamUtil.getString(request, "redirect");

List<LayoutPageTemplateEntry> masterLayoutPageTemplateEntries = new ArrayList<>();

LayoutPageTemplateEntry layoutPageTemplateEntry = LayoutPageTemplateEntryLocalServiceUtil.createLayoutPageTemplateEntry(0);

layoutPageTemplateEntry.setName(LanguageUtil.get(request, "blank"));
layoutPageTemplateEntry.setStatus(WorkflowConstants.STATUS_APPROVED);

masterLayoutPageTemplateEntries.add(layoutPageTemplateEntry);

masterLayoutPageTemplateEntries.addAll(LayoutPageTemplateEntryServiceUtil.getLayoutPageTemplateEntries(scopeGroupId, LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT, WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null));

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(LanguageUtil.get(request, "select-master-page"));
%>

<clay:container-fluid
	cssClass="container-view"
>
	<div class="lfr-search-container-wrapper">
		<ul class="card-page card-page-equal-height">

			<%
			for (LayoutPageTemplateEntry masterLayoutPageTemplateEntry : masterLayoutPageTemplateEntries) {
			%>

				<li class="card-page-item card-page-item-asset">

					<%
					SelectLayoutPageTemplateEntryMasterLayoutVerticalCard selectLayoutPageTemplateEntryMasterLayoutVerticalCard = new SelectLayoutPageTemplateEntryMasterLayoutVerticalCard(masterLayoutPageTemplateEntry, renderRequest, renderResponse);
					%>

					<clay:vertical-card
						additionalProps='<%=
							HashMapBuilder.<String, Object>put(
								"addLayoutPageTemplateEntryUrl", selectLayoutPageTemplateEntryMasterLayoutVerticalCard.getAddLayoutPageTemplateEntryURL()
							).put(
								"dialogTitle", LanguageUtil.get(request, "add-page-template")
							).put(
								"mainFieldLabel", LanguageUtil.get(request, "name")
							).put(
								"mainFieldName", "name"
							).put(
								"mainFieldPlaceholder", LanguageUtil.get(request, "name")
							).build()
						%>'
						propsTransformer="js/propsTransformers/SelectLayoutPageTemplateEntryMasterLayoutVerticalCardPropsTransformer"
						verticalCard="<%= selectLayoutPageTemplateEntryMasterLayoutVerticalCard %>"
					/>
				</li>

			<%
			}
			%>

		</ul>
	</div>
</clay:container-fluid>