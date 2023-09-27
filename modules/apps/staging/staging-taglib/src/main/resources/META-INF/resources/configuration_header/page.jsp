<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/configuration_header/init.jsp" %>

<aui:fieldset cssClass="options-group" label="<%= label %>" markupView="lexicon">
	<aui:model-context bean="<%= exportImportConfiguration %>" model="<%= ExportImportConfiguration.class %>" />

	<aui:input name="nameRequired" type="hidden" value="1" />

	<aui:input label="title" name="name" showRequiredLabel="<%= true %>">
		<aui:validator name="required">
			function() {
				var nameRequiredInput = document.getElementById('<portlet:namespace />nameRequired');

				if (nameRequiredInput) {
					return nameRequiredInput.value === "1";
				}
			}
		</aui:validator>
	</aui:input>

	<aui:input label="description" name="description" />
</aui:fieldset>