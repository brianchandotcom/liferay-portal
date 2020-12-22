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

SelectDisplayPageMasterLayoutDisplayContext selectDisplayPageMasterLayoutDisplayContext = new SelectDisplayPageMasterLayoutDisplayContext(request);

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
			for (LayoutPageTemplateEntry masterLayoutPageTemplateEntry : selectDisplayPageMasterLayoutDisplayContext.getMasterLayoutPageTemplateEntries()) {
			%>

				<li class="card-page-item card-page-item-asset">

					<%
					SelectDisplayPageMasterLayoutVerticalCard selectDisplayPageMasterLayoutVerticalCard = new SelectDisplayPageMasterLayoutVerticalCard(masterLayoutPageTemplateEntry, renderRequest, renderResponse);
					%>

					<clay:vertical-card
						additionalProps='<%=
							HashMapBuilder.<String, Object>put(
								"addDisplayPageUrl", selectDisplayPageMasterLayoutVerticalCard.getAddDisplayPageURL()
							).put(
								"mappingTypes", selectDisplayPageMasterLayoutDisplayContext.getMappingTypesJSONArray()
							).put(
								"title", LanguageUtil.get(request, "add-display-page-template")
							).build()
						%>'
						propsTransformer="js/propsTransformers/SelectDisplayPageMasterLayoutVerticalCardPropsTransformer"
						verticalCard="<%= selectDisplayPageMasterLayoutVerticalCard %>"
					/>
				</li>

			<%
			}
			%>

		</ul>
	</div>
</clay:container-fluid>