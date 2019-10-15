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

<%@ include file="/designer/init.jsp" %>

<clay:management-toolbar
	clearResultsURL="<%= kaleoDesignerDisplayContext.getClearResultsURL() %>"
	creationMenu="<%= kaleoDesignerDisplayContext.getCreationMenu(pageContext) %>"
	filterDropdownItems="<%= kaleoDesignerDisplayContext.getFilterItemsDropdownItems() %>"
	itemsTotal="<%= kaleoDesignerDisplayContext.getTotalItems() %>"
	namespace="<%= renderResponse.getNamespace() %>"
	searchActionURL="<%= kaleoDesignerDisplayContext.getSearchActionURL() %>"
	searchContainerId="<%= kaleoDesignerDisplayContext.getSearchContainerId() %>"
	searchFormName="fm1"
	selectable="false"
	sortingOrder="<%= kaleoDesignerDisplayContext.getOrderByType() %>"
	sortingURL="<%= kaleoDesignerDisplayContext.getSortingURL() %>"
/>