<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<div class="sheet">
	<div class="panel-group panel-group-flush">
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
	</div>
</div>