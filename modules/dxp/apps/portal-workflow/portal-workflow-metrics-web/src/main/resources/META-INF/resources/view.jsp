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

<div id="<portlet:namespace />root">
	<span aria-hidden="true" class="loading-animation"></span>
</div>

<aui:script require='<%= npmResolvedPackageName + " as bootstrapRequire" %>'>
	bootstrapRequire.default(
		<%= PropsValues.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA %>,
		<%= Arrays.toString(PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES) %>,
		<%= PropsValues.SEARCH_CONTAINER_PAGE_ITERATOR_MAX_PAGES %>,
		'<portlet:namespace />'
	);
</aui:script>