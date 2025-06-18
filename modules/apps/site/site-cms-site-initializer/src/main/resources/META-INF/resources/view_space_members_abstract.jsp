<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ViewSpaceMembersAbstractSectionDisplayContext viewSpaceMembersAbstractSectionDisplayContext = (ViewSpaceMembersAbstractSectionDisplayContext)request.getAttribute(ViewSpaceMembersAbstractSectionDisplayContext.class.getName());
%>

<div class="cms-section">
	<div id="<%= CMSSiteInitializerFDSNames.SPACE_MEMBERS_ABSTRACT_SECTION %>">
		<react:component
			module="{SpaceAbstractHeader} from site-cms-site-initializer"
			props="<%= viewSpaceMembersAbstractSectionDisplayContext.getHeaderProps() %>"
		/>
	</div>

	<clay:tabs
		tabsItems="<%= viewSpaceMembersAbstractSectionDisplayContext.getTabsItems() %>"
	>
		<clay:tabs-panel>
			<div class="cms-fds-fluid cms-section custom-empty-state">
				<frontend-data-set:headless-display
					apiURL='<%= viewSpaceMembersAbstractSectionDisplayContext.getAPIURL("user-accounts") %>'
					creationMenu="<%= viewSpaceMembersAbstractSectionDisplayContext.getCreationMenu() %>"
					emptyState="<%= viewSpaceMembersAbstractSectionDisplayContext.getEmptyState() %>"
					formName="fm"
					id="<%= CMSSiteInitializerFDSNames.SPACE_MEMBERS_USERS_ABSTRACT_SECTION %>"
					propsTransformer="{MembersFDSPropsTransformer} from site-cms-site-initializer"
					showManagementBar="<%= false %>"
					showPagination="<%= false %>"
					showSearch="<%= false %>"
					showSelectAll="<%= false %>"
					style="fluid"
				/>
			</div>
		</clay:tabs-panel>

		<clay:tabs-panel>
			<div class="cms-fds-fluid cms-section custom-empty-state">
				<frontend-data-set:headless-display
					apiURL='<%= viewSpaceMembersAbstractSectionDisplayContext.getAPIURL("user-groups") %>'
					creationMenu="<%= viewSpaceMembersAbstractSectionDisplayContext.getCreationMenu() %>"
					emptyState="<%= viewSpaceMembersAbstractSectionDisplayContext.getEmptyState() %>"
					formName="fm"
					id="<%= CMSSiteInitializerFDSNames.SPACE_MEMBERS_USER_GROUPS_ABSTRACT_SECTION %>"
					propsTransformer="{MembersFDSPropsTransformer} from site-cms-site-initializer"
					showManagementBar="<%= false %>"
					showPagination="<%= false %>"
					showSearch="<%= false %>"
					showSelectAll="<%= false %>"
					style="fluid"
				/>
			</div>
		</clay:tabs-panel>
	</clay:tabs>
</div>