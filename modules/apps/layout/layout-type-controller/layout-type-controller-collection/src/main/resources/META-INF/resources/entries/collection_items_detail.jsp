<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/entries/init.jsp" %>

<%
CollectionItemsDetailDisplayContext collectionItemsDetailDisplayContext = (CollectionItemsDetailDisplayContext)request.getAttribute(CollectionPageLayoutTypeControllerWebKeys.COLLECTION_ITEMS_DETAIL_DISPLAY_CONTEXT);
%>

<li class="control-menu-nav-item">
	<clay:button
		cssClass="text-muted"
		displayType="unstyled"
		id='<%= collectionItemsDetailDisplayContext.getNamespace() + "viewCollectionItems" %>'
		label='<%= "(" + LanguageUtil.format(resourceBundle, "x-items", collectionItemsDetailDisplayContext.getCollectionItemsCount(), false) + ")" %>'
	/>
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