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

<%@ include file="/adaptive_media/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

boolean configurationEntryEditable = GetterUtil.getBoolean(request.getAttribute(AMWebKeys.CONFIGURATION_ENTRY_EDITABLE));
AMImageConfigurationEntry amImageConfigurationEntry = (AMImageConfigurationEntry)request.getAttribute(AMWebKeys.CONFIGURATION_ENTRY);

String configurationEntryUuid = ParamUtil.getString(request, "uuid", (amImageConfigurationEntry != null) ? amImageConfigurationEntry.getUUID() : StringPool.BLANK);

renderResponse.setTitle((amImageConfigurationEntry != null) ? amImageConfigurationEntry.getName() : LanguageUtil.get(request, "new-image-resolution"));

Map<String, String> properties = null;

if (amImageConfigurationEntry != null) {
	properties = amImageConfigurationEntry.getProperties();
}
%>

<portlet:actionURL name="/adaptive_media/edit_image_configuration_entry" var="editImageConfigurationEntryURL">
	<portlet:param name="mvcRenderCommandName" value="/adaptive_media/edit_image_configuration_entry" />
</portlet:actionURL>

<liferay-frontend:edit-form
	action="<%= editImageConfigurationEntryURL %>"
	method="post"
	name="fm"
>
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="uuid" type="hidden" value="<%= configurationEntryUuid %>" />

	<liferay-frontend:edit-form-body>
		<liferay-ui:error exception="<%= AMImageConfigurationException.DuplicateAMImageConfigurationNameException.class %>" message="a-configuration-with-this-name-already-exists" />
		<liferay-ui:error exception="<%= AMImageConfigurationException.DuplicateAMImageConfigurationUuidException.class %>" message="a-configuration-with-this-id-already-exists" />
		<liferay-ui:error exception="<%= AMImageConfigurationException.InvalidHeightException.class %>" message="please-enter-a-max-height-value-larger-than-0" />
		<liferay-ui:error exception="<%= AMImageConfigurationException.InvalidNameException.class %>" message="please-enter-a-valid-name" />
		<liferay-ui:error exception="<%= AMImageConfigurationException.InvalidUuidException.class %>" message="please-enter-a-valid-identifier" />
		<liferay-ui:error exception="<%= AMImageConfigurationException.InvalidWidthException.class %>" message="please-enter-a-max-width-value-larger-than-0" />
		<liferay-ui:error exception="<%= AMImageConfigurationException.RequiredWidthOrHeightException.class %>" message="please-enter-a-max-width-or-max-height-value-larger-than-0" />

		<div class="error-wrapper"></div>

		<c:if test="<%= !configurationEntryEditable %>">
			<div class="alert alert-info">
				<liferay-ui:message key="the-images-for-this-resolution-are-already-adapted" />
			</div>
		</c:if>

		<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" name="name" required="<%= true %>" value="<%= (amImageConfigurationEntry != null) ? amImageConfigurationEntry.getName() : StringPool.BLANK %>" />

		<aui:input name="description" type="textarea" value="<%= (amImageConfigurationEntry != null) ? amImageConfigurationEntry.getDescription() : StringPool.BLANK %>" />

		<div class="sheet-subtitle">
			<liferay-ui:message key="size" />

			<aui:icon cssClass="reference-mark text-warning" image="asterisk" markupView="lexicon" />

			<span class="hide-accessible"><liferay-ui:message key="required" /></span>
		</div>

		<label class="control-label form-group">
			<liferay-ui:message key="please-enter-at-least-one-of-the-following-fields" />

			<liferay-ui:icon-help message="leave-a-size-field-empty-to-get-images-scaled-proportionally" />
		</label>

		<div class="row">
			<div class="col-md-3">

				<%
				String maxWidth = StringPool.BLANK;

				if (properties != null) {
					String curMaxWidth = properties.get("max-width");

					if (!curMaxWidth.equals("0")) {
						maxWidth = curMaxWidth;
					}
				}
				%>

				<aui:input disabled="<%= !configurationEntryEditable %>" label="max-width-px" min="0" name="maxWidth" type="number" value="<%= maxWidth %>">
					<aui:validator name="number" />
				</aui:input>
			</div>

			<div class="col-md-3">

				<%
				String maxHeight = StringPool.BLANK;

				if (properties != null) {
					String curMaxHeight = properties.get("max-height");

					if (!curMaxHeight.equals("0")) {
						maxHeight = curMaxHeight;
					}
				}
				%>

				<aui:input disabled="<%= !configurationEntryEditable %>" label="max-height-px" min="0" name="maxHeight" type="number" value="<%= maxHeight %>">
					<aui:validator name="number" />
				</aui:input>
			</div>
		</div>

		<c:if test="<%= amImageConfigurationEntry == null %>">
			<aui:input label="add-a-resolution-for-high-density-displays" name="addHighResolution" type="checkbox" />
		</c:if>

		<%
		boolean automaticUuid;

		if (amImageConfigurationEntry == null) {
			automaticUuid = Validator.isNull(configurationEntryUuid);
		}
		else {
			automaticUuid = configurationEntryUuid.equals(FriendlyURLNormalizerUtil.normalize(amImageConfigurationEntry.getName()));
		}

		automaticUuid = ParamUtil.getBoolean(request, "automaticUuid", automaticUuid);
		%>

		<div class="sheet-subtitle">
			<liferay-ui:message key="identifier" />
		</div>

		<div class="form-group" id="<portlet:namespace />idOptions">
			<aui:input checked="<%= automaticUuid %>" disabled="<%= !configurationEntryEditable %>" helpMessage="the-id-is-based-on-the-name-field" label="automatic" name="automaticUuid" type="radio" value="<%= true %>" />

			<aui:input checked="<%= !automaticUuid %>" disabled="<%= !configurationEntryEditable %>" label="custom" name="automaticUuid" type="radio" value="<%= false %>" />
		</div>

		<aui:input cssClass="input-medium" disabled="<%= automaticUuid || !configurationEntryEditable %>" label="id" name="newUuid" type="text" value="<%= configurationEntryUuid %>">
			<aui:validator errorMessage="please-enter-only-alphanumeric-characters-dashes-or-underscores" name="custom">
				function(value) {
					var radioButtons = document.getElementsByName('<portlet:namespace/>automaticUuid');

					for (var i = 0; i < radioButtons.length; i++) {
						var radioButton = radioButtons[i];

						if (radioButton.checked && (radioButton.value === <%= automaticUuid %>)) {
							return true;
						}
					}

					var pattern = /^[a-zA-Z0-9_-]+$/;

					return pattern.test(value);
				}
			</aui:validator>
		</aui:input>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<c:if test="<%= configurationEntryEditable %>">
	<liferay-frontend:component
		module="adaptive_media/js/EditAdaptiveMediaConfig.es"
	/>
</c:if>