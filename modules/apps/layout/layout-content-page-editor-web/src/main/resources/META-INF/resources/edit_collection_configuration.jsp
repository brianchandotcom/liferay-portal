<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
EditCollectionConfigurationDisplayContext editCollectionConfigurationDisplayContext = (EditCollectionConfigurationDisplayContext)request.getAttribute(EditCollectionConfigurationDisplayContext.class.getName());

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(editCollectionConfigurationDisplayContext.getRedirect());

renderResponse.setTitle(LanguageUtil.get(request, "filter-collection"));
%>

<liferay-product-navigation:control-menu />

<portlet:actionURL name="/layout_content_page_editor/update_collection_configuration" var="updateCollectionConfigurationURL" />

<liferay-util:html-top>
	<style type="text/css">
		.edit-collection-configuration-form .sheet {
			padding: 0;
		}

		.edit-collection-configuration-form .sheet-footer {
			padding: 0 1.5rem;
		}
	</style>
</liferay-util:html-top>

<liferay-frontend:edit-form
	action="<%= updateCollectionConfigurationURL %>"
	cssClass="edit-collection-configuration-form"
	method="post"
	name="fm"
>
	<aui:input name="classNameId" type="hidden" value="<%= editCollectionConfigurationDisplayContext.getClassNameId() %>" />
	<aui:input name="classPK" type="hidden" value="<%= editCollectionConfigurationDisplayContext.getClassPK() %>" />
	<aui:input name="collectionKey" type="hidden" value="<%= editCollectionConfigurationDisplayContext.getCollectionKey() %>" />
	<aui:input name="itemId" type="hidden" value="<%= editCollectionConfigurationDisplayContext.getItemId() %>" />
	<aui:input name="plid" type="hidden" value="<%= editCollectionConfigurationDisplayContext.getPlid() %>" />
	<aui:input name="redirect" type="hidden" value="<%= editCollectionConfigurationDisplayContext.getRedirect() %>" />
	<aui:input name="segmentsExperienceId" type="hidden" value="<%= editCollectionConfigurationDisplayContext.getSegmentsExperienceId() %>" />
	<aui:input name="type" type="hidden" value="<%= editCollectionConfigurationDisplayContext.getType() %>" />

	<div>
		<react:component
			module="page_editor/plugins/collection-configuration/CollectionConfiguration"
			props="<%= editCollectionConfigurationDisplayContext.getData() %>"
		/>
	</div>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons
			redirect="<%= editCollectionConfigurationDisplayContext.getRedirect() %>"
		/>
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>