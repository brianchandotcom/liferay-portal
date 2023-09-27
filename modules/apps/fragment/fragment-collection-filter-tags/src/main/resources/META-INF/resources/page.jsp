<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<div class="form-group form-group-sm mb-0">
	<div role="presentation">
		<label class="control-label <%= fragmentCollectionFilterTagsDisplayContext.isShowLabel() ? "" : "sr-only" %>">
			<%= fragmentCollectionFilterTagsDisplayContext.getLabel() %>
		</label>

		<input class="form-control form-control-select form-control-sm w-100" style="min-height: 2.125rem;" type="text" />

		<c:choose>
			<c:when test="<%= fragmentCollectionFilterTagsDisplayContext.isShowHelpText() %>">
				<p class="m-0 mt-1 small text-secondary">
					<%= fragmentCollectionFilterTagsDisplayContext.getHelpText() %>
				</p>
			</c:when>
		</c:choose>
	</div>

	<react:component
		module="js/SelectTags"
		props="<%= fragmentCollectionFilterTagsDisplayContext.getProps() %>"
	/>
</div>