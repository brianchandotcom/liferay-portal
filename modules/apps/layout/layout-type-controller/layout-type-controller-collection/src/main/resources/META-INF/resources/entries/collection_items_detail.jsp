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

<%@ include file="/entries/init.jsp" %>

<%
CollectionItemsDetailDisplayContext collectionItemsDetailDisplayContext = (CollectionItemsDetailDisplayContext)request.getAttribute(CollectionPageLayoutTypeControllerWebKeys.COLLECTION_ITEMS_DETAIL_DISPLAY_CONTEXT);
%>

<li class="control-menu-nav-item control-menu-nav-item-content">
	<button class="btn btn-unstyled text-muted" id="<%= collectionItemsDetailDisplayContext.getNamespace() %>viewCollectionItems" />
		(<%= LanguageUtil.format(resourceBundle, "x-items", collectionItemsDetailDisplayContext.getCollectionItemsCount(), false) %>)
	</button>
</li>

<aui:script>
	var viewCollectionItems = document.getElementById(
		'<%= collectionItemsDetailDisplayContext.getNamespace() %>viewCollectionItems'
	);

	viewCollectionItems.addEventListener('click', (event) => {
		Liferay.Util.openModal({
			id:
				'<%= collectionItemsDetailDisplayContext.getNamespace() %>viewCollectionItemsDialog',
			title: '<liferay-ui:message key="collection-items" />',
			url:
				'<%= collectionItemsDetailDisplayContext.getViewCollectionItemsURL() %>',
		});
	});

	var onDestroyPortlet = function () {
		document.removeEventListener('click', viewCollectionItems);
	};

	Liferay.on('destroyPortlet', onDestroyPortlet);
</aui:script>