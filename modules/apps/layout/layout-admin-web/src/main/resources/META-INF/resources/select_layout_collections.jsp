<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
SelectLayoutCollectionDisplayContext selectLayoutCollectionDisplayContext = (SelectLayoutCollectionDisplayContext)request.getAttribute(LayoutAdminWebKeys.SELECT_LAYOUT_COLLECTION_DISPLAY_CONTEXT);

SelectCollectionManagementToolbarDisplayContext selectCollectionManagementToolbarDisplayContext = new SelectCollectionManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, selectLayoutCollectionDisplayContext);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(layoutsAdminDisplayContext.getBackURL());

renderResponse.setTitle(LanguageUtil.get(request, "select-collection"));
%>

<clay:navigation-bar
	cssClass="border-bottom"
	inverted="<%= false %>"
	navigationItems="<%= selectLayoutCollectionDisplayContext.getNavigationItems() %>"
/>

<c:if test="<%= selectLayoutCollectionDisplayContext.isCollections() %>">
	<clay:management-toolbar
		managementToolbarDisplayContext="<%= selectCollectionManagementToolbarDisplayContext %>"
		propsTransformer="js/SelectLayoutCollectionManagementToolbarPropsTransformer"
	/>
</c:if>

<clay:container-fluid
	id='<%= liferayPortletResponse.getNamespace() + "collections" %>'
>
	<c:choose>
		<c:when test="<%= selectLayoutCollectionDisplayContext.isCollections() %>">
			<liferay-util:include page="/select_collections.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:otherwise>
			<liferay-util:include page="/select_collection_providers.jsp" servletContext="<%= application %>" />
		</c:otherwise>
	</c:choose>
</clay:container-fluid>

<aui:script require="frontend-js-web/index as frontendJsWeb">
	var {delegate} = frontendJsWeb;

	var collections = document.getElementById('<portlet:namespace />collections');

	var selectLayoutMasterLayoutActionOptionQueryClickHandler = delegate(
		collections,
		'click',
		'.select-collection-action-option',
		(event) => {
			Liferay.Util.navigate(
				event.delegateTarget.dataset.selectLayoutMasterLayoutUrl
			);
		}
	);

	function handleDestroyPortlet() {
		selectLayoutMasterLayoutActionOptionQueryClickHandler.dispose();

		Liferay.detach('destroyPortlet', handleDestroyPortlet);
	}

	Liferay.on('destroyPortlet', handleDestroyPortlet);
</aui:script>