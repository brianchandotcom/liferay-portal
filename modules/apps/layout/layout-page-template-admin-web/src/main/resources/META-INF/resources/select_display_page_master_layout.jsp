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

<clay:container
	className="mt-4"
>
	<div class="lfr-search-container-wrapper">
		<ul class="card-page card-page-equal-height">

			<%
			for (LayoutPageTemplateEntry masterLayoutPageTemplateEntry : selectDisplayPageMasterLayoutDisplayContext.getMasterLayoutPageTemplateEntries()) {
			%>

				<li class="card-page-item col-md-4 col-sm-6">
					<clay:vertical-card
						verticalCard="<%= new SelectDisplayPageMasterLayoutVerticalCard(masterLayoutPageTemplateEntry, renderRequest, renderResponse) %>"
					/>
				</li>

			<%
			}
			%>

		</ul>
	</div>
</clay:container>

<%
StringBundler sb = new StringBundler(3);

sb.append("metal-dom/src/all/dom as dom, ");
sb.append(npmResolvedPackageName);
sb.append("/js/modal/openDisplayPageModal.es as openDisplayPageModal");
%>

<aui:script require="<%= sb.toString() %>" sandbox="<%= true %>">
	var addDisplayPageClickHandler = dom.delegate(
		document.body,
		'click',
		'.add-master-page-action-option',
		function (event) {
			var data = event.delegateTarget.dataset;

			event.preventDefault();

			openDisplayPageModal.default({
				formSubmitURL: data.addDisplayPageUrl,
				mappingTypes: JSON.parse(
					'<%= selectDisplayPageMasterLayoutDisplayContext.getMappingTypesJSONArray() %>'
				),
				namespace: '<portlet:namespace />',
				spritemap:
					'<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg',
				title: '<liferay-ui:message key="add-display-page-template" />',
			});
		}
	);

	function handleDestroyPortlet() {
		addDisplayPageClickHandler.removeListener();

		Liferay.detach('destroyPortlet', handleDestroyPortlet);
	}

	Liferay.on('destroyPortlet', handleDestroyPortlet);
</aui:script>