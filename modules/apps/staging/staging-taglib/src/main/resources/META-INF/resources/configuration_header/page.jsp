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

<%@ include file="/configuration_header/init.jsp" %>

<aui:fieldset cssClass="options-group" label="<%= label %>" markupView="lexicon">
	<aui:model-context bean="<%= exportImportConfiguration %>" model="<%= ExportImportConfiguration.class %>" />

	<aui:input name="nameRequired" type="hidden" value="1" />

	<aui:input label="title" name="name" showRequiredLabel="<%= true %>">
		<aui:validator name="required">
			function() {
				return (AUI.$('#<portlet:namespace />nameRequired').prop('value') === "1");
			}
		</aui:validator>
	</aui:input>

	<aui:input label="description" name="description" />
</aui:fieldset>