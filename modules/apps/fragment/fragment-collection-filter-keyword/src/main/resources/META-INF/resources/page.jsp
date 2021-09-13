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

<label class="control-label <%= fragmentCollectionFilterKeywordDisplayContext.isShowLabel() ? "" : "sr-only" %>" for="<%= fragmentCollectionFilterKeywordDisplayContext.getFragmentEntryLinkNamespace() + "keywordsInput" %>">
	<%= fragmentCollectionFilterKeywordDisplayContext.getLabel() %>
</label>

<div class="input-group">
	<div class="input-group-item">
		<input class="form-control form-control-sm input-group-inset input-group-inset-after" id="<%= fragmentCollectionFilterKeywordDisplayContext.getFragmentEntryLinkNamespace() + "keywordsInput" %>" placeholder="<%= LanguageUtil.get(request, "search") %>" style="border-color: var(--gray-400);" type="text" value="" />

		<div class="input-group-inset-item input-group-inset-item-after" style="border-color: var(--gray-400);">
			<clay:button
				aria-label='<%= LanguageUtil.get(request, "submit") %>'
				disabled="<%= fragmentCollectionFilterKeywordDisplayContext.isDisabled() %>"
				displayType="unstyled"
				icon="search"
				id='<%= fragmentCollectionFilterKeywordDisplayContext.getFragmentEntryLinkNamespace() + "keywordsButton" %>'
			/>
		</div>
	</div>
</div>

<liferay-frontend:component
	context="<%= fragmentCollectionFilterKeywordDisplayContext.getProps() %>"
	module="js/FragmentCollectionFilterKeyword"
/>