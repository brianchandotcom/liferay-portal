<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ViewSubtasksSectionDisplayContext viewSubtasksSectionDisplayContext = (ViewSubtasksSectionDisplayContext)request.getAttribute(ViewSubtasksSectionDisplayContext.class.getName());
%>

<c:if test="<%= viewSubtasksSectionDisplayContext.isVisible() %>">
	<div class="cms-section custom-empty-state pt-4">
		<div>
			<react:component
				module="{SubtasksTitle} from content-marketing-platform-web"
				props="<%= viewSubtasksSectionDisplayContext.getSubtasksTitleProps() %>"
			/>
		</div>

		<frontend-data-set:headless-display
			additionalProps="<%= viewSubtasksSectionDisplayContext.getAdditionalProps() %>"
			apiURL="<%= viewSubtasksSectionDisplayContext.getAPIURL() %>"
			bulkActionDropdownItems="<%= viewSubtasksSectionDisplayContext.getBulkActionDropdownItems() %>"
			creationMenu="<%= viewSubtasksSectionDisplayContext.getCreationMenu() %>"
			emptyState="<%= viewSubtasksSectionDisplayContext.getEmptyState() %>"
			fdsActionDropdownItems="<%= viewSubtasksSectionDisplayContext.getFDSActionDropdownItems() %>"
			formName="fm"
			id="<%= CMPSiteInitializerFDSNames.CMP_SUBTASKS %>"
			propsTransformer="{SubtasksFDSPropsTransformer} from content-marketing-platform-web"
			selectedItemsKey="embedded.id"
			selectionType="<%= viewSubtasksSectionDisplayContext.getSelectionType() %>"
		/>
	</div>
</c:if>