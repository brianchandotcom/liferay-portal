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

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset-group>
			<liferay-frontend:fieldset
				collapsed="<%= false %>"
				collapsible="<%= true %>"
				label="display-settings"
			>
				<aui:input label="video-id" name="preferences--url--" value="<%= youTubeDisplayContext.getURL() %>" />

				<aui:select inlineField="<%= true %>" label="preset-frame-size" name="preferences--presetSize--" onChange='<%= renderResponse.getNamespace() + "updateFrameSize(this.value);" %>' value="<%= youTubeDisplayContext.getPresetSize() %>">
					<aui:option label="custom" selected='<%= Objects.equals(youTubeDisplayContext.getPresetSize(), "custom") %>' value="custom" />
					<aui:option label="standard-360-4-3" selected='<%= Objects.equals(youTubeDisplayContext.getPresetSize(), "480x360") %>' value="480x360" />
					<aui:option label="standard-360-16-9" selected='<%= Objects.equals(youTubeDisplayContext.getPresetSize(), "640x360") %>' value="640x360" />
					<aui:option label="enhanced-480-4-3" selected='<%= Objects.equals(youTubeDisplayContext.getPresetSize(), "640x480") %>' value="640x480" />
					<aui:option label="enhanced-480-16-9" selected='<%= Objects.equals(youTubeDisplayContext.getPresetSize(), "854x480") %>' value="854x480" />
					<aui:option label="hd-720-4-3" selected='<%= Objects.equals(youTubeDisplayContext.getPresetSize(), "960x720") %>' value="960x720" />
					<aui:option label="hd-720-16-9" selected='<%= Objects.equals(youTubeDisplayContext.getPresetSize(), "1280x720") %>' value="1280x720" />
					<aui:option label="full-hd-1080-4-3" selected='<%= Objects.equals(youTubeDisplayContext.getPresetSize(), "1440x1080") %>' value="1440x1080" />
					<aui:option label="full-hd-1080-16-9" selected='<%= Objects.equals(youTubeDisplayContext.getPresetSize(), "1920x1080") %>' value="1920x1080" />
				</aui:select>

				<aui:input disabled="<%= !youTubeDisplayContext.isCustomSize() %>" inlineField="<%= true %>" label="frame-width" name="preferences--width--" value="<%= youTubeDisplayContext.getWidth() %>">
					<aui:validator name="digits" />
				</aui:input>

				<aui:input disabled="<%= !youTubeDisplayContext.isCustomSize() %>" inlineField="<%= true %>" label="frame-height" name="preferences--height--" value="<%= youTubeDisplayContext.getHeight() %>">
					<aui:validator name="digits" />
				</aui:input>
			</liferay-frontend:fieldset>

			<liferay-frontend:fieldset
				collapsed="<%= true %>"
				collapsible="<%= true %>"
				label="advanced-options"
			>
				<aui:input label="watch-this-video-at-youtube" name="preferences--showThumbnail--" type="toggle-switch" value="<%= youTubeDisplayContext.isShowThumbnail() %>" />

				<div class="<%= youTubeDisplayContext.isShowThumbnail() ? "hide" : StringPool.BLANK %>" id="<portlet:namespace />videoPreferences">
					<aui:input inlineField="<%= true %>" label="auto-play" name="preferences--autoplay--" type="toggle-switch" value="<%= youTubeDisplayContext.isAutoPlay() %>" />

					<aui:input inlineField="<%= true %>" name="preferences--loop--" type="toggle-switch" value="<%= youTubeDisplayContext.isLoop() %>" />

					<aui:input inlineField="<%= true %>" name="preferences--enableKeyboardControls--" type="toggle-switch" value="<%= youTubeDisplayContext.isEnableKeyboardControls() %>" />

					<aui:input inlineField="<%= true %>" name="preferences--annotations--" type="toggle-switch" value="<%= youTubeDisplayContext.isAnnotations() %>" />

					<aui:input inlineField="<%= true %>" name="preferences--closedCaptioning--" type="toggle-switch" value="<%= youTubeDisplayContext.isClosedCaptioning() %>" />

					<aui:input name="preferences--startTime--" value="<%= youTubeDisplayContext.getStartTime() %>">
						<aui:validator name="digits" />
					</aui:input>
				</div>
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<aui:script>
	function <portlet:namespace />updateFrameSize(value) {
		var Util = Liferay.Util;

		var heightNode = document.querySelector('#<portlet:namespace />height');
		var widthNode = document.querySelector('#<portlet:namespace />width');

		var useDefaults = value != 'custom';

		Util.toggleDisabled(heightNode, useDefaults);
		Util.toggleDisabled(widthNode, useDefaults);

		if (useDefaults) {
			var dimensions = value.split('x');

			heightNode.value = dimensions[1];
			widthNode.value = dimensions[0];
		}
	}

	Liferay.Util.toggleBoxes(
		'<portlet:namespace />showThumbnail',
		'<portlet:namespace />videoPreferences',
		'<%= youTubeDisplayContext.isShowThumbnail() %>'
	);
</aui:script>