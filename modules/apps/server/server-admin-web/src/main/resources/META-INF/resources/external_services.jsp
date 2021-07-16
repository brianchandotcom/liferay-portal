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

<div class="server-admin-tabs">
	<aui:fieldset>
		<liferay-ui:panel-container
			extended="<%= true %>"
			id="adminExternalServicesPanelContainer"
			persistState="<%= true %>"
		>
			<liferay-ui:panel
				collapsible="<%= true %>"
				extended="<%= true %>"
				id="adminImageMagickConversionPanel"
				markupView="lexicon"
				persistState="<%= true %>"
				title="enabling-imagemagick-provides-document-preview-functionality"
			>
				<aui:input label="enabled" name="imageMagickEnabled" type="checkbox" value="<%= ImageMagickUtil.isEnabled() %>" />

				<aui:input cssClass="lfr-input-text-container" label="path" name="imageMagickPath" type="text" value="<%= ImageMagickUtil.getGlobalSearchPath() %>" />

				<aui:fieldset label="resource-limits">

					<%
					Properties resourceLimitsProperties = ImageMagickUtil.getResourceLimitsProperties();

					for (String label : _IMAGEMAGICK_RESOURCE_LIMIT_LABELS) {
					%>

						<aui:input cssClass="lfr-input-text-container" label="<%= label %>" name='<%= "imageMagickLimit" + StringUtil.upperCaseFirstLetter(label) %>' type="text" value="<%= resourceLimitsProperties.getProperty(label) %>" />

					<%
					}
					%>

				</aui:fieldset>
			</liferay-ui:panel>
		</liferay-ui:panel-container>
	</aui:fieldset>
</div>

<aui:button-row>
	<aui:button cssClass="save-server-button" data-cmd="updateExternalServices" value="save" />
</aui:button-row>

<%!
private static final String[] _IMAGEMAGICK_RESOURCE_LIMIT_LABELS = {"area", "disk", "file", "map", "memory", "thread", "time"};
%>