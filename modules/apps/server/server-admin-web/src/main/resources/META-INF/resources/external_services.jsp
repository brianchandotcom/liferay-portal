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

<aui:fieldset-group markupView="lexicon">
	<aui:fieldset collapsed="<%= false %>" collapsible="<%= true %>" label="enabling-imagemagick-provides-document-preview-functionality">
		<aui:input label="enabled" name="imageMagickEnabled" type="checkbox" value="<%= ImageMagickUtil.isEnabled() %>" />

		<aui:input cssClass="lfr-input-text-container" label="path" name="imageMagickPath" type="text" value="<%= ImageMagickUtil.getGlobalSearchPath() %>" />
	</aui:fieldset>

	<aui:fieldset collapsed="<%= false %>" collapsible="<%= true %>" label="resource-limits">

		<%
		Properties resourceLimitsProperties = ImageMagickUtil.getResourceLimitsProperties();

		for (String label : ImageMagickResourceLimitConstants.PROPERTY_NAMES) {
		%>

			<aui:input cssClass="lfr-input-text-container" label="<%= label %>" name="<%= PropsKeys.IMAGEMAGICK_RESOURCE_LIMIT + label %>" type="text" value="<%= resourceLimitsProperties.getProperty(label) %>" />

		<%
		}
		%>

	</aui:fieldset>

	<aui:button-row>
		<aui:button cssClass="save-server-button" data-cmd="updateExternalServices" primary="<%= true %>" value="save" />
	</aui:button-row>
</aui:fieldset-group>