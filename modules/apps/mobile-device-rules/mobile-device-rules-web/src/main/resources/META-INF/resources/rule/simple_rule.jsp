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
MDRRule rule = (MDRRule)request.getAttribute(MDRWebKeys.MOBILE_DEVICE_RULES_RULE);

Set<String> operatingSystems = Collections.emptySet();
int tablet = 0;

String screenPhysicalHeightMax = StringPool.BLANK;
String screenPhysicalHeightMin = StringPool.BLANK;
String screenPhysicalWidthMax = StringPool.BLANK;
String screenPhysicalWidthMin = StringPool.BLANK;

String screenResolutionHeightMax = StringPool.BLANK;
String screenResolutionHeightMin = StringPool.BLANK;
String screenResolutionWidthMax = StringPool.BLANK;
String screenResolutionWidthMin = StringPool.BLANK;

if (rule != null) {
	UnicodeProperties typeSettingsProperties = rule.getTypeSettingsProperties();

	operatingSystems = SetUtil.fromArray(StringUtil.split(typeSettingsProperties.get(SimpleRuleHandler.PROPERTY_OS)));

	String tabletString = GetterUtil.getString(typeSettingsProperties.get(SimpleRuleHandler.PROPERTY_TABLET));

	if (tabletString.equals(StringPool.TRUE)) {
		tablet = 1;
	}
	else if (tabletString.equals(StringPool.FALSE)) {
		tablet = 2;
	}

	screenPhysicalHeightMax = GetterUtil.getString(typeSettingsProperties.get(SimpleRuleHandler.PROPERTY_SCREEN_PHYSICAL_HEIGHT_MAX));
	screenPhysicalHeightMin = GetterUtil.getString(typeSettingsProperties.get(SimpleRuleHandler.PROPERTY_SCREEN_PHYSICAL_HEIGHT_MIN));
	screenPhysicalWidthMax = GetterUtil.getString(typeSettingsProperties.get(SimpleRuleHandler.PROPERTY_SCREEN_PHYSICAL_WIDTH_MAX));
	screenPhysicalWidthMin = GetterUtil.getString(typeSettingsProperties.get(SimpleRuleHandler.PROPERTY_SCREEN_PHYSICAL_WIDTH_MIN));

	screenResolutionHeightMax = GetterUtil.getString(typeSettingsProperties.get(SimpleRuleHandler.PROPERTY_SCREEN_RESOLUTION_HEIGHT_MAX));
	screenResolutionHeightMin = GetterUtil.getString(typeSettingsProperties.get(SimpleRuleHandler.PROPERTY_SCREEN_RESOLUTION_HEIGHT_MIN));
	screenResolutionWidthMax = GetterUtil.getString(typeSettingsProperties.get(SimpleRuleHandler.PROPERTY_SCREEN_RESOLUTION_WIDTH_MAX));
	screenResolutionWidthMin = GetterUtil.getString(typeSettingsProperties.get(SimpleRuleHandler.PROPERTY_SCREEN_RESOLUTION_WIDTH_MIN));
}
%>

<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="operating-system-and-type" markupView="lexicon">
	<aui:select multiple="<%= true %>" name="os">
		<aui:option label="any-os" selected="<%= operatingSystems.isEmpty() %>" value="" />

		<%
		Set<VersionableName> knownOperationSystems = DeviceDetectionUtil.getKnownOperatingSystems();

		for (VersionableName knownOperationSystem : knownOperationSystems) {
		%>

			<aui:option label="<%= knownOperationSystem.getName() %>" selected="<%= operatingSystems.contains(knownOperationSystem.getName()) %>" />

		<%
		}
		%>

	</aui:select>

	<aui:select label="device-type" name="tablet">
		<aui:option label="any" selected="<%= tablet == 0 %>" value="" />
		<aui:option label="tablets" selected="<%= tablet == 1 %>" value="<%= true %>" />
		<aui:option label="other-devices" selected="<%= tablet == 2 %>" value="<%= false %>" />
	</aui:select>
</aui:fieldset>

<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="physical-screen-size" markupView="lexicon">
	<aui:row>
		<aui:col width="<%= 50 %>">
			<h5><liferay-ui:message key="minimum" /></h5>

			<aui:input
				cssClass="physical-screen-size-field aui-field-digits"
				id="<%= SimpleRuleHandler.PROPERTY_SCREEN_PHYSICAL_WIDTH_MIN %>"
				inlineField="<%= true %>"
				label="width"
				name="<%= SimpleRuleHandler.PROPERTY_SCREEN_PHYSICAL_WIDTH_MIN %>"
				placeholder="mm"
				value="<%= screenPhysicalWidthMin %>"
			/>

			<aui:input
				cssClass="physical-screen-size-field-field aui-field-digits"
				id="<%= SimpleRuleHandler.PROPERTY_SCREEN_PHYSICAL_HEIGHT_MIN %>"
				inlineField="<%= true %>"
				label="height"
				name="<%= SimpleRuleHandler.PROPERTY_SCREEN_PHYSICAL_HEIGHT_MIN %>"
				placeholder="mm"
				value="<%= screenPhysicalHeightMin %>"
			/>
		</aui:col>

		<aui:col width="<%= 50 %>">
			<h5><liferay-ui:message key="maximum" /></h5>

			<aui:input
				cssClass="physical-physical-screen-size-field-field aui-field-digits"
				id="<%= SimpleRuleHandler.PROPERTY_SCREEN_PHYSICAL_WIDTH_MAX %>"
				inlineField="<%= true %>"
				label="width"
				name="<%= SimpleRuleHandler.PROPERTY_SCREEN_PHYSICAL_WIDTH_MAX %>"
				placeholder="mm"
				value="<%= screenPhysicalWidthMax %>"
			/>

			<aui:input
				cssClass="screen-physical-size-field-field aui-field-digits"
				id="<%= SimpleRuleHandler.PROPERTY_SCREEN_PHYSICAL_HEIGHT_MAX %>"
				inlineField="<%= true %>"
				label="height"
				name="<%= SimpleRuleHandler.PROPERTY_SCREEN_PHYSICAL_HEIGHT_MAX %>"
				placeholder="mm"
				value="<%= screenPhysicalHeightMax %>"
			/>
		</aui:col>
	</aui:row>
</aui:fieldset>

<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="screen-resolution" markupView="lexicon">
	<aui:row>
		<aui:col width="<%= 50 %>">
			<h5><liferay-ui:message key="minimum" /></h5>

			<aui:input
				cssClass="screen-resolution-field aui-field-digits"
				id="<%= SimpleRuleHandler.PROPERTY_SCREEN_RESOLUTION_WIDTH_MIN %>"
				inlineField="<%= true %>"
				label="width"
				name="<%= SimpleRuleHandler.PROPERTY_SCREEN_RESOLUTION_WIDTH_MIN %>"
				placeholder="px"
				value="<%= screenResolutionWidthMin %>"
			/>

			<aui:input
				cssClass="screen-resolution-field aui-field-digits"
				id="<%= SimpleRuleHandler.PROPERTY_SCREEN_RESOLUTION_HEIGHT_MIN %>"
				inlineField="<%= true %>"
				label="height"
				name="<%= SimpleRuleHandler.PROPERTY_SCREEN_RESOLUTION_HEIGHT_MIN %>"
				placeholder="px"
				value="<%= screenResolutionHeightMin %>"
			/>
		</aui:col>
		<aui:col width="<%= 50 %>">
			<h5><liferay-ui:message key="maximum" /></h5>

			<aui:input
				cssClass="screen-resolution-field aui-field-digits"
				id="<%= SimpleRuleHandler.PROPERTY_SCREEN_RESOLUTION_WIDTH_MAX %>"
				inlineField="<%= true %>"
				label="width"
				name="<%= SimpleRuleHandler.PROPERTY_SCREEN_RESOLUTION_WIDTH_MAX %>"
				placeholder="px"
				value="<%= screenResolutionWidthMax %>"
			/>

			<aui:input
				cssClass="screen-resolution-field aui-field-digits"
				id="<%= SimpleRuleHandler.PROPERTY_SCREEN_RESOLUTION_HEIGHT_MAX %>"
				inlineField="<%= true %>"
				label="height"
				name="<%= SimpleRuleHandler.PROPERTY_SCREEN_RESOLUTION_HEIGHT_MAX %>"
				placeholder="px"
				value="<%= screenResolutionHeightMax %>"
			/>
		</aui:col>
	</aui:row>
</aui:fieldset>