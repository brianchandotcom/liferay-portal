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

<aui:fieldset>
	<liferay-ui:panel-container extended="<%= true %>" id="adminExternalServicesPanelContainer" persistState="<%= true %>">
		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="adminImageMagickConversionPanel" persistState="<%= true %>" title="enabling-imagemagick-provides-document-preview-functionality">
			<aui:input label="enabled" name="imageMagickEnabled" type="checkbox" value="<%= ImageMagickUtil.isEnabled() %>" />

			<aui:input cssClass="lfr-input-text-container" label="path" name="imageMagickPath" type="text" value="<%= ImageMagickUtil.getGlobalSearchPath() %>" />

			<aui:fieldset label="resource-limits">

				<%
				Properties resourceLimitsProperties = ImageMagickUtil.getResourceLimitsProperties();

				for (String label : _IMAGEMAGICK_RESOURCE_LIMIT_LABELS) {
					String name = "imageMagickLimit" + StringUtil.upperCaseFirstLetter(label);
				%>

					<aui:input cssClass="lfr-input-text-container" label="<%= label %>" name="<%= name %>" type="text" value="<%= resourceLimitsProperties.getProperty(label) %>" />

				<%
				}
				%>

			</aui:fieldset>
		</liferay-ui:panel>

		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="adminOpenOfficeConversionPanel" persistState="<%= true %>" title="enabling-openoffice-integration-provides-document-conversion-functionality">
			<aui:input label="enabled" name="openOfficeEnabled" type="checkbox" value="<%= PrefsPropsUtil.getBoolean(PropsKeys.OPENOFFICE_SERVER_ENABLED) %>" />

			<aui:input cssClass="lfr-input-text-container" label="port" name="openOfficePort" type="text" value="<%= PrefsPropsUtil.getString(PropsKeys.OPENOFFICE_SERVER_PORT) %>" />
		</liferay-ui:panel>

		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="adminXugglerPanel" persistState="<%= true %>" title="enabling-xuggler-provides-video-conversion-functionality">
			<liferay-ui:error exception="<%= XugglerInstallException.class %>">

				<%
					XugglerInstallException xie = (XugglerInstallException)errorException;

					StringBundler sb = new StringBundler();

					sb.append(LanguageUtil.get(locale, "an-unexpected-error-occurred-while-installing-xuggler"));
					sb.append(StringPool.SPACE);
					sb.append(StringPool.COLON);
					sb.append(StringPool.SPACE);
					sb.append(xie.getMessage());
				%>

				<liferay-ui:message key="<%= sb.toString() %>" localizeKey="<%= false %>" />

			</liferay-ui:error>

			<c:choose>
				<c:when test="<%= XugglerUtil.isNativeLibraryInstalled() %>">
					<div class="alert alert-info">
						<liferay-ui:message key="xuggler-installed" />
					</div>

					<aui:input label="enabled" name="xugglerEnabled" type="checkbox" value="<%= XugglerUtil.isEnabled() %>" />
				</c:when>
				<c:when test="<%= XugglerUtil.isNativeLibraryCopied() %>">
					<div class="alert alert-info">
						<liferay-ui:message key="xuggler-has-been-installed-you-need-to-reboot-your-server-to-apply-changes" />
					</div>
				</c:when>
				<c:otherwise>

					<%
					String xugglerHelp = LanguageUtil.format(request, "xuggler-help", "http://www.xuggle.com/xuggler/downloads", false);

					String[] xugglerOptions = PropsUtil.getArray(PropsKeys.XUGGLER_JAR_OPTIONS);

					String bitmode = OSDetector.getBitmode();

					String guess = StringPool.BLANK;

					if (Validator.isNotNull(bitmode) && (bitmode.equals("32") || bitmode.equals("64"))) {
						if (OSDetector.isApple()) {
							guess = bitmode + "-mac";
						}
						else if (OSDetector.isLinux()) {
							guess = bitmode + "-linux";
						}
						else if (OSDetector.isWindows()) {
							guess = bitmode + "-win";
						}

						if (Validator.isNotNull(guess)) {
							boolean found = false;

							for (String xugglerOption : xugglerOptions) {
								if (xugglerOption.equals(guess)) {
									found = true;

									break;
								}
							}

							if (!found) {
								guess = StringPool.BLANK;
							}
						}
					}
					%>

					<div class="alert alert-info">
						<liferay-ui:message key="<%= xugglerHelp %>" />
					</div>

					<aui:select label="jar-file" name="jarName">

						<%
						if (Validator.isNull(guess)) {
						%>

							<aui:option label="unknown" value="" />

						<%
						}

						for (String xugglerOption : xugglerOptions) {
							String jarFile = PropsUtil.get(PropsKeys.XUGGLER_JAR_FILE, new Filter(xugglerOption));
							String jarName = PropsUtil.get(PropsKeys.XUGGLER_JAR_NAME, new Filter(xugglerOption));
						%>

							<aui:option label='<%= jarName + " (" + jarFile + ")" %>' selected="<%= xugglerOption.equals(guess) %>" value="<%= jarFile %>" />

						<%
						}
						%>

					</aui:select>

					<aui:button-row>
						<aui:button cssClass="save-server-button" data-cmd="installXuggler" name="installXugglerButton" value="install" />
					</aui:button-row>
				</c:otherwise>
			</c:choose>
		</liferay-ui:panel>
	</liferay-ui:panel-container>
</aui:fieldset>

<aui:button-row>
	<aui:button cssClass="save-server-button" data-cmd="updateExternalServices" value="save" />
</aui:button-row>

<%!
private static final String[] _IMAGEMAGICK_RESOURCE_LIMIT_LABELS = {"area", "disk", "file", "map", "memory", "thread", "time"};
%>