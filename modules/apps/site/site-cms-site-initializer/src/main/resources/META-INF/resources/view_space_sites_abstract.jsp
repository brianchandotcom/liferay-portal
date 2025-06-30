<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ViewSpaceSitesAbstractSectionDisplayContext viewSpaceSitesAbstractSectionDisplayContext = (ViewSpaceSitesAbstractSectionDisplayContext)request.getAttribute(ViewSpaceSitesAbstractSectionDisplayContext.class.getName());
%>

<div class="cms-section">
	<div id="<%= CMSSiteInitializerFDSNames.SPACE_SITES_ABSTRACT_SECTION %>">
		<react:component
			module="{SpaceAbstractHeader} from site-cms-site-initializer"
			props="<%= viewSpaceSitesAbstractSectionDisplayContext.getHeaderProps() %>"
		/>
	</div>

	<div class="cms-fds-fluid cms-section custom-empty-state">
		<frontend-data-set:headless-display
			apiURL="<%= viewSpaceSitesAbstractSectionDisplayContext.getAPIURL() %>"
			creationMenu="<%= viewSpaceSitesAbstractSectionDisplayContext.getCreationMenu() %>"
			emptyState="<%= viewSpaceSitesAbstractSectionDisplayContext.getEmptyState() %>"
			fdsActionDropdownItems="<%= viewSpaceSitesAbstractSectionDisplayContext.getFDSActionDropdownItems() %>"
			formName="fm"
			id="<%= CMSSiteInitializerFDSNames.SPACE_SITES_ABSTRACT_SECTION %>"
			propsTransformer="{SitesFDSPropsTransformer} from site-cms-site-initializer"
			showManagementBar="<%= false %>"
			showPagination="<%= false %>"
			showSearch="<%= false %>"
			showSelectAll="<%= false %>"
			style="fluid"
		/>
	</div>
</div>