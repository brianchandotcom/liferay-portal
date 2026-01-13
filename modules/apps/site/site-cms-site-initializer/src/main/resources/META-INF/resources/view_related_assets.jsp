<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ViewRelatedAssetsSectionDisplayContext viewRelatedAssetsSectionDisplayContext = (ViewRelatedAssetsSectionDisplayContext)request.getAttribute(ViewRelatedAssetsSectionDisplayContext.class.getName());
%>

<div class="cms-related-assets cms-section custom-empty-state">
	<h5 class="cms-related-assets-header"><liferay-ui:message key="assets" /></h5>

	<frontend-data-set:headless-display
		additionalProps="<%= viewRelatedAssetsSectionDisplayContext.getAdditionalProps() %>"
		apiURL="<%= viewRelatedAssetsSectionDisplayContext.getAPIURL() %>"
		creationMenu="<%= viewRelatedAssetsSectionDisplayContext.getCreationMenu() %>"
		emptyState="<%= viewRelatedAssetsSectionDisplayContext.getEmptyState() %>"
		fdsActionDropdownItems="<%= viewRelatedAssetsSectionDisplayContext.getFDSActionDropdownItems() %>"
		formName="fm"
		id="<%= CMSSiteInitializerFDSNames.RELATED_ASSETS_SECTION %>"
		propsTransformer="{RelatedAssetsFDSPropsTransformer} from site-cms-site-initializer"
		selectedItemsKey="embedded.id"
		showPagination="<%= false %>"
		showSearch="<%= false %>"
	/>
</div>