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

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveConfiguration();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<aui:fieldset label="languages">
		<aui:input name="preferences--languageIds--" type="hidden" />

		<liferay-ui:input-move-boxes
			leftBoxName="currentLanguageIds"
			leftList="<%= languageDisplayContext.getCurrentLanguageList() %>"
			leftReorder="true"
			leftTitle="current"
			rightBoxName="availableLanguageIds"
			rightList="<%= languageDisplayContext.getAvailableLanguageList() %>"
			rightTitle="available"
		/>
	</aui:fieldset>

	<aui:fieldset>
		<div class="display-template">
			<liferay-ui:ddm-template-selector
				className="<%= LanguageEntry.class.getName() %>"
				displayStyle="<%= languageDisplayContext.getDisplayStyle() %>"
				displayStyleGroupId="<%= languageDisplayContext.getDisplayStyleGroupId() %>"
				refreshURL="<%= configurationRenderURL %>"
			/>
		</div>
	</aui:fieldset>

	<aui:input name="preferences--displayCurrentLocale--" type="checkbox" value="<%= languageDisplayContext.isDisplayCurrentLocale() %>" />

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveConfiguration() {
		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('languageIds').val(Liferay.Util.listSelect(form.fm('currentLanguageIds')));

		submitForm(form);
	}
</aui:script>