<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/publications/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

CTCollection ctCollection = (CTCollection)request.getAttribute(CTWebKeys.CT_COLLECTION);
CTRemote ctRemote = (CTRemote)request.getAttribute(CTWebKeys.CT_REMOTE);

String actionName = "/change_tracking/edit_ct_collection";
long ctCollectionId = CTConstants.CT_COLLECTION_ID_PRODUCTION;
String description = StringPool.BLANK;
String name = StringPool.BLANK;
String saveButtonLabel = "create";
boolean showTemplates = true;

boolean revert = ParamUtil.getBoolean(request, "revert");

if (revert) {
	actionName = "/change_tracking/undo_ct_collection";
	ctCollectionId = ctCollection.getCtCollectionId();
	name = StringBundler.concat(LanguageUtil.get(request, "revert"), " \"", ctCollection.getName(), "\"");
	saveButtonLabel = "revert-and-create-publication";
	showTemplates = false;

	renderResponse.setTitle(LanguageUtil.get(resourceBundle, "revert"));
}
else if (ctCollection != null) {
	ctCollectionId = ctCollection.getCtCollectionId();
	description = ctCollection.getDescription();
	name = ctCollection.getName();
	saveButtonLabel = "save";

	renderResponse.setTitle(StringBundler.concat(LanguageUtil.format(resourceBundle, "edit-x", new Object[] {ctCollection.getName()})));
}
else if (ctRemote != null) {
	renderResponse.setTitle(LanguageUtil.format(request, "create-new-publication-on-x", ctRemote.getName()));
}
else {
	renderResponse.setTitle(LanguageUtil.get(request, "create-new-publication"));
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);
%>

<clay:container-fluid
	cssClass="container-form-lg edit-publication-container"
>
	<liferay-portlet:actionURL name="<%= actionName %>" var="actionURL">
		<liferay-portlet:param name="mvcRenderCommandName" value="/change_tracking/view_publications" />
		<liferay-portlet:param name="redirect" value="<%= redirect %>" />
	</liferay-portlet:actionURL>

	<liferay-portlet:resourceURL id="/change_tracking/invite_users" var="inviteUsersURL" />

	<react:component
		module="publications/js/views/ChangeTrackingCollectionEditView"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"actionUrl", actionURL
			).put(
				"ctCollectionId", ctCollectionId
			).put(
				"ctCollectionTemplates", request.getAttribute(CTWebKeys.CT_COLLECTION_TEMPLATES)
			).put(
				"ctCollectionTemplatesData", request.getAttribute(CTWebKeys.CT_COLLECTION_TEMPLATES_DATA)
			).put(
				"ctRemoteId", (ctRemote != null) ? ctRemote.getCtRemoteId() : null
			).put(
				"defaultCTCollectionTemplateId", request.getAttribute(CTWebKeys.DEFAULT_CT_COLLECTION_TEMPLATE_ID)
			).put(
				"descriptionFieldMaxLength", ModelHintsUtil.getMaxLength(CTCollection.class.getName(), "description")
			).put(
				"inviteUsersURL", inviteUsersURL
			).put(
				"nameFieldMaxLength", ModelHintsUtil.getMaxLength(CTCollection.class.getName(), "name")
			).put(
				"namespace", liferayPortletResponse.getNamespace()
			).put(
				"publicationDescription", description
			).put(
				"publicationName", name
			).put(
				"redirect", redirect
			).put(
				"revertingPublication", revert
			).put(
				"saveButtonLabel", LanguageUtil.get(request, saveButtonLabel)
			).put(
				"showTemplates", showTemplates
			).build()
		%>'
	/>
</clay:container-fluid>