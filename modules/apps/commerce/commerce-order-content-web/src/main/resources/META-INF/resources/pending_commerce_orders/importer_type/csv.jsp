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

<portlet:actionURL name="/commerce_open_order_content/import_csv_file_entry" var="importCSVFileEntryActionURL" />

<aui:form action="<%= importCSVFileEntryActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.IMPORT %>" />
	<aui:input name="fileEntryId" type="hidden" />

	<p class="text-default">
		<span class="hide" id="<portlet:namespace />fileEntryRemoveIcon" role="button">
			<aui:icon cssClass="icon-monospaced" image="times" markupView="lexicon" />
		</span>
		<span id="<portlet:namespace />fileEntryNameInput">
			<span class="text-muted"><liferay-ui:message key="none" /></span>
		</span>
	</p>

	<aui:button name="selectFileButton" value="select" />

	<aui:button-row>
		<aui:button cssClass="btn-lg" name="importButton" primary="<%= true %>" type="submit" value='<%= LanguageUtil.get(request, "import") %>' />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"currentURL", currentURL
		).put(
			"itemSelectorURL", commerceOrderContentDisplayContext.getCSVFileEntryItemSelectorURL()
		).build()
	%>'
	module="js/csv"
/>