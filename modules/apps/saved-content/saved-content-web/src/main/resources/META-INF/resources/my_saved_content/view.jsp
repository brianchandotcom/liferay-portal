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
				cssClass="table-cell-expand table-cell-minw-200"
				name="title"
				value="<%= mySavedContentDisplayContext.getAssetTitle(savedContentEntry.getClassName(), savedContentEntry.getClassPK()) %>"
			/>

			<div class="autofit-row">
				<div class="autofit-col autofit-col-expand pl-1">
					<div class="table-title">
					</div>
				</div>
			</div>

			<liferay-ui:search-container-column-text
				name="description"
				value="<%= ResourceActionsUtil.getModelResource(locale, savedContentEntry.getClassName()) %>"
			/>

			<liferay-ui:search-container-column-icon
				href="<%= mySavedContentDisplayContext.getURL(savedContentEntry.getClassName(), savedContentEntry.getClassPK()) %>"
				icon="shortcut"
				name="icon"
			/>

			<liferay-ui:search-container-column-text
				href="<%= mySavedContentDisplayContext.getURL(savedContentEntry.getClassName(), savedContentEntry.getClassPK()) %>"
				name="url"
				value="url"
			/>

			<liferay-ui:search-container-column-icon
				icon="trash"
			/>

			<liferay-ui:search-container-column-text
				href="<%= mySavedContentDisplayContext.getRemoveSavedContentURL(savedContentEntry.getClassName(), savedContentEntry.getClassPK()) %>"
				name="trash"
				value="trash"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>