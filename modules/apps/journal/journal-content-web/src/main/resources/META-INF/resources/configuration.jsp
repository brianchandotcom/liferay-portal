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
String redirect = ParamUtil.getString(request, "redirect");
%>

<liferay-ui:error exception="<%= NoSuchArticleException.class %>" message="the-web-content-could-not-be-found" />

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" varImpl="configurationRenderURL" />

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />
	<aui:input name="preferences--assetEntryId--" type="hidden" value="<%= journalContentDisplayContext.getAssetEntryId() %>" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset-group>
			<liferay-frontend:fieldset>
				<div id="<portlet:namespace />articlePreview">
					<liferay-util:include page="/journal_resources.jsp" servletContext="<%= application %>">
						<liferay-util:param name="refererPortletName" value="<%= renderResponse.getNamespace() %>" />
					</liferay-util:include>
				</div>
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button name="saveButton" type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<aui:script require="metal-dom/src/all/dom as dom, frontend-js-web/liferay/ItemSelectorDialog.es as ItemSelectorDialog">
	const articlePreview = document.getElementById(
		'<portlet:namespace />articlePreview'
	);
	const assetEntryIdInput = document.getElementById(
		'<portlet:namespace />assetEntryId'
	);

	var itemSelectorDialog = new ItemSelectorDialog.default({
		buttonAddLabel: '<liferay-ui:message key="done" />',
		eventName: '<portlet:namespace />selectedItem',
		title: '<liferay-ui:message key="select-web-content" />',
		url: '<%= journalContentDisplayContext.getItemSelectorURL() %>'
	});

	itemSelectorDialog.on('selectedItemChange', function(event) {
		var selectedItem = event.selectedItem;

		if (!selectedItem) {
			return;
		}

		var itemValue = JSON.parse(selectedItem.value);

		retrieveWebContent(itemValue.classPK);
	});

	dom.delegate(articlePreview, 'click', '.web-content-selector', function(event) {
		event.preventDefault();
		itemSelectorDialog.open();
	});

	dom.delegate(articlePreview, 'click', '.selector-button', function(event) {
		event.preventDefault();
		retrieveWebContent(-1);
	});

	function retrieveWebContent(assetClassPK) {
		var uri = '<%= configurationRenderURL %>';

		uri = Liferay.Util.addParams(
			'<portlet:namespace />articleResourcePrimKey=' + assetClassPK,
			uri
		);

		location.href = uri;
	}
</aui:script>