
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