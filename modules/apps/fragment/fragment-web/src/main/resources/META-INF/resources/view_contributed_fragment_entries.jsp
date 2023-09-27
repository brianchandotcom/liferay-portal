<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ContributedFragmentManagementToolbarDisplayContext contributedFragmentManagementToolbarDisplayContext = new ContributedFragmentManagementToolbarDisplayContext(fragmentEntriesDisplayContext, request, liferayPortletRequest, liferayPortletResponse);
%>

<clay:management-toolbar
	additionalProps="<%= contributedFragmentManagementToolbarDisplayContext.getComponentContext() %>"
	managementToolbarDisplayContext="<%= contributedFragmentManagementToolbarDisplayContext %>"
	propsTransformer="js/ViewContributedFragmentEntriesManagementToolbarPropsTransformer"
/>

<aui:form name="fm">
	<liferay-ui:search-container
		searchContainer="<%= fragmentEntriesDisplayContext.getContributedEntriesSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="Object"
			modelVar="object"
		>
			<liferay-ui:search-container-column-text>
				<c:choose>
					<c:when test="<%= object instanceof FragmentComposition %>">
						<clay:vertical-card
							additionalProps="<%= fragmentEntriesDisplayContext.getAdditionalProps() %>"
							propsTransformer="js/ContributedFragmentEntryDropdownPropsTransformer"
							verticalCard="<%= new ContributedFragmentCompositionVerticalCard((FragmentComposition)object, renderRequest, renderResponse, searchContainer.getRowChecker()) %>"
						/>
					</c:when>
					<c:otherwise>
						<clay:vertical-card
							additionalProps="<%= fragmentEntriesDisplayContext.getAdditionalProps() %>"
							propsTransformer="js/ContributedFragmentEntryDropdownPropsTransformer"
							verticalCard="<%= new ContributedFragmentEntryVerticalCard((FragmentEntry)object, renderRequest, renderResponse, searchContainer.getRowChecker()) %>"
						/>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="icon"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:form name="fragmentEntryFm">
	<aui:input name="contributedEntryKeys" type="hidden" />
	<aui:input name="fragmentCollectionId" type="hidden" />
</aui:form>