<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL", redirect);

if (Validator.isNotNull(backURL)) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(backURL);
}

MySavedContentDisplayContext mySavedContentDisplayContext = new MySavedContentDisplayContext(liferayPortletRequest, liferayPortletResponse);
%>

<clay:container-fluid>
	<liferay-ui:search-container
		emptyResultsMessage="no-saved-content-were-found"
		searchContainer="<%= mySavedContentDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.saved.content.model.SavedContentEntry"
			escapedModel="<%= true %>"
			keyProperty="savedContentEntryId"
			modelVar="savedContentEntry"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
			>
				<p class="list-group-title"><%= mySavedContentDisplayContext.getAssetTitle(savedContentEntry.getClassName(), savedContentEntry.getClassPK()) %></p>
				<p class="list-group-subtitle"><%= ResourceActionsUtil.getModelResource(locale, savedContentEntry.getClassName()) %></p>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand-smallest"
			>
				<div class="btn-group">
					<div class="btn-group-item">
						<clay:link
							borderless="<%= true %>"
							cssClass="lfr-portal-tooltip"
							displayType="secondary"
							href="<%= mySavedContentDisplayContext.getURL(savedContentEntry.getClassName(), savedContentEntry.getClassPK()) %>"
							icon="shortcut"
							monospaced="<%= true %>"
							small="<%= true %>"
							target="_blank"
							title='<%= LanguageUtil.get(request, "open-in-a-new-tab") %>'
							type="button"
						/>
					</div>

					<div class="btn-group-item">
						<clay:link
							borderless="<%= true %>"
							cssClass="lfr-portal-tooltip"
							displayType="secondary"
							href="<%= mySavedContentDisplayContext.getRemoveSavedContentURL(savedContentEntry.getClassName(), savedContentEntry.getClassPK()) %>"
							icon="trash"
							monospaced="<%= true %>"
							small="<%= true %>"
							title='<%= LanguageUtil.get(request, "remove-content") %>'
							type="button"
						/>
					</div>
				</div>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>