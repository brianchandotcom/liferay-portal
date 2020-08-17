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

<%
String carPartsFinderRootElementId = liferayPortletResponse.getNamespace() + "-car-parts-finder";

NPMResolver npmResolver = NPMResolverProvider.getNPMResolver();
%>

<div class="car-parts-finder-module" id="<%= carPartsFinderRootElementId %>">
	<div class="inline-item my-5 p-5 w-100">
		<span aria-hidden="true" class="loading-animation"></span>
	</div>
</div>

<aui:script require='<%= npmResolver.resolveModuleName("commerce-bom-web/js/index.es") + " as CarPartsFinder" %>'>
	CarPartsFinder.default('partFinder', '<%= carPartsFinderRootElementId %>', {
		basename: window.location.pathname,
		basePathUrl:
			'<%= PortalUtil.getGroupFriendlyURL(layout.getLayoutSet(), themeDisplay, locale) %>',
		areasEndpoint:
			'<%= PortalUtil.getPortalURL(request) + "/o/commerce-bom/1.0/areas" %>',
		foldersEndpoint:
			'<%= PortalUtil.getPortalURL(request) + "/o/commerce-bom/1.0/folders" %>',
		spritemap:
			'<%= themeDisplay.getPathThemeImages() + "/lexicon/icons.svg" %>',
	});
</aui:script>