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

<%
FragmentManagementToolbarDisplayContext fragmentManagementToolbarDisplayContext = new FragmentManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, fragmentDisplayContext);
%>

<liferay-ui:error exception="<%= RequiredFragmentEntryException.class %>" message="the-fragment-entry-cannot-be-deleted-because-it-is-required-by-one-or-more-page-templates" />

<clay:management-toolbar
	displayContext="<%= fragmentManagementToolbarDisplayContext %>"
/>

<aui:form name="fm">
	<liferay-ui:search-container
		searchContainer="<%= fragmentDisplayContext.getFragmentEntriesSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.fragment.model.FragmentEntry"
			keyProperty="fragmentEntryId"
			modelVar="fragmentEntry"
		>
			<portlet:renderURL var="editFragmentEntryURL">
				<portlet:param name="mvcRenderCommandName" value="/fragment/edit_fragment_entry" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="fragmentCollectionId" value="<%= String.valueOf(fragmentEntry.getFragmentCollectionId()) %>" />
				<portlet:param name="fragmentEntryId" value="<%= String.valueOf(fragmentEntry.getFragmentEntryId()) %>" />
			</portlet:renderURL>

			<%
			row.setCssClass("entry-card lfr-asset-item " + row.getCssClass());

			String imagePreviewURL = fragmentEntry.getImagePreviewURL(themeDisplay);
			%>

			<liferay-ui:search-container-column-text>
				<c:choose>
					<c:when test="<%= Validator.isNotNull(imagePreviewURL) %>">
						<liferay-frontend:vertical-card
							actionJsp="/fragment_entry_action.jsp"
							actionJspServletContext="<%= application %>"
							cssClass="entry-display-style"
							imageCSSClass="aspect-ratio-bg-contain"
							imageUrl="<%= imagePreviewURL %>"
							resultRow="<%= row %>"
							rowChecker="<%= searchContainer.getRowChecker() %>"
							title="<%= fragmentEntry.getName() %>"
							url="<%= editFragmentEntryURL %>"
						>
							<liferay-frontend:vertical-card-header>

								<%
								Date statusDate = fragmentEntry.getStatusDate();
								%>

								<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - statusDate.getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
							</liferay-frontend:vertical-card-header>

							<liferay-frontend:vertical-card-footer>
								<span class="label <%= (fragmentEntry.getStatus() == WorkflowConstants.STATUS_APPROVED) ? "label-success" : "label-secondary" %>">
									<liferay-ui:message key="<%= WorkflowConstants.getStatusLabel(fragmentEntry.getStatus()) %>" />
								</span>
							</liferay-frontend:vertical-card-footer>

							<liferay-frontend:vertical-card-sticker-bottom>
								<div class="sticker sticker-bottom-left sticker-primary <%= (fragmentEntry.getType() == FragmentEntryTypeConstants.TYPE_ELEMENT) ? "file-icon-color-4" : "file-icon-color-2" %>">
									<liferay-ui:icon
										cssClass="inline-item"
										icon="cards"
										markupView="lexicon"
									/>
								</div>
							</liferay-frontend:vertical-card-sticker-bottom>
						</liferay-frontend:vertical-card>
					</c:when>
					<c:otherwise>
						<liferay-frontend:icon-vertical-card
							actionJsp="/fragment_entry_action.jsp"
							actionJspServletContext="<%= application %>"
							cssClass="entry-display-style"
							icon="code"
							resultRow="<%= row %>"
							rowChecker="<%= searchContainer.getRowChecker() %>"
							title="<%= fragmentEntry.getName() %>"
							url="<%= editFragmentEntryURL %>"
						>
							<liferay-frontend:vertical-card-header>

								<%
								Date statusDate = fragmentEntry.getStatusDate();
								%>

								<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - statusDate.getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
							</liferay-frontend:vertical-card-header>

							<liferay-frontend:vertical-card-footer>
								<span class="label <%= (fragmentEntry.getStatus() == WorkflowConstants.STATUS_APPROVED) ? "label-success" : "label-secondary" %>">
									<liferay-ui:message key="<%= WorkflowConstants.getStatusLabel(fragmentEntry.getStatus()) %>" />
								</span>
							</liferay-frontend:vertical-card-footer>

							<liferay-frontend:vertical-card-sticker-bottom>
								<div class="sticker sticker-bottom-left sticker-primary <%= (fragmentEntry.getType() == FragmentEntryTypeConstants.TYPE_ELEMENT) ? "file-icon-color-4" : "file-icon-color-2" %>">
									<liferay-ui:icon
										cssClass="inline-item"
										icon="cards"
										markupView="lexicon"
									/>
								</div>
							</liferay-frontend:vertical-card-sticker-bottom>
						</liferay-frontend:icon-vertical-card>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="icon"
			markupView="lexicon"
			resultRowSplitter="<%= fragmentDisplayContext.isSearch() ? null : new FragmentEntryResultRowSplitter() %>"
		/>
	</liferay-ui:search-container>
</aui:form>

<portlet:actionURL name="/fragment/update_fragment_entry_preview" var="updateFragmentEntryPreviewURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= updateFragmentEntryPreviewURL %>" name="fragmentEntryPreviewFm">
	<aui:input name="fragmentEntryId" type="hidden" />
	<aui:input name="fileEntryId" type="hidden" />
</aui:form>

<portlet:actionURL name="/fragment/move_fragment_entry" var="moveFragmentEntryURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= moveFragmentEntryURL %>" name="moveFragmentEntryFm">
	<aui:input name="fragmentEntryIds" type="hidden" />
	<aui:input name="fragmentCollectionId" type="hidden" />
</aui:form>

<c:if test="<%= FragmentPermission.contains(permissionChecker, scopeGroupId, FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES) %>">
	<aui:script require='<%= npmResolvedPackageName + "/js/ElementsDefaultEventHandler.es as ElementsDefaultEventHandler" %>'>
		Liferay.component(
			'<%= FragmentWebKeys.FRAGMENT_ENTRY_ELEMENTS_DEFAULT_EVENT_HANDLER %>',
			new ElementsDefaultEventHandler.default(
				{
					namespace: '<portlet:namespace />',
					spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg'
				}
			),
			{
				destroyOnNavigate: true,
				portletId: '<%= HtmlUtil.escapeJS(portletDisplay.getId()) %>'
			}
		);
	</aui:script>
</c:if>

<aui:script require='<%= npmResolvedPackageName + "/js/ManagementToolbarDefaultEventHandler.es as ManagementToolbarDefaultEventHandler" %>'>
	Liferay.component(
		'<%= fragmentManagementToolbarDisplayContext.getDefaultEventHandler() %>',
		new ManagementToolbarDefaultEventHandler.default(
			{
				deleteFragmentEntriesURL: '<portlet:actionURL name="/fragment/delete_fragment_entries"><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>',
				exportFragmentEntriesURL: '<portlet:resourceURL id="/fragment/export_fragment_entries" />',
				namespace: '<portlet:namespace />',
				selectFragmentCollectionURL: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcRenderCommandName" value="/fragment/select_fragment_collection" /></portlet:renderURL>',
				spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg'
			}
		),
		{
			destroyOnNavigate: true,
			portletId: '<%= HtmlUtil.escapeJS(portletDisplay.getId()) %>'
		}
	);
</aui:script>