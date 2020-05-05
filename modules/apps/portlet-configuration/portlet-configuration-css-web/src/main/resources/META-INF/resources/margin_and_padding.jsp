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

<clay:row>
	<clay:col
		className="lfr-padding use-for-all-column"
		md="6"
	>
		<aui:fieldset label="padding">
			<aui:input checked='<%= portletConfigurationCSSPortletDisplayContext.isSpacingSameForAll("padding") %>' data-inputselector=".same-padding" label="same-for-all" name="useForAllPadding" type="toggle-switch" />

			<span class="field-row">
				<aui:input inlineField="<%= true %>" label="top" name="paddingTop" value='<%= portletConfigurationCSSPortletDisplayContext.getPaddingProperty("top", "value") %>' />

				<aui:select inlineField="<%= true %>" label="" name="paddingTopUnit" title="top-padding-unit">
					<aui:option label="%" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getPaddingProperty("top", "unit"), "%") %>' />
					<aui:option label="px" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getPaddingProperty("top", "unit"), "px") %>' />
					<aui:option label="em" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getPaddingProperty("top", "unit"), "em") %>' />
				</aui:select>
			</span>
			<span class="field-row">
				<aui:input cssClass="same-padding" disabled='<%= portletConfigurationCSSPortletDisplayContext.isSpacingSameForAll("padding") %>' inlineField="<%= true %>" label="right" name="paddingRight" value='<%= portletConfigurationCSSPortletDisplayContext.getPaddingProperty("right", "value") %>' />

				<aui:select cssClass="same-padding" disabled='<%= portletConfigurationCSSPortletDisplayContext.isSpacingSameForAll("padding") %>' inlineField="<%= true %>" label="" name="paddingRightUnit" title="right-padding-unit">
					<aui:option label="%" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getPaddingProperty("right", "unit"), "%") %>' />
					<aui:option label="px" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getPaddingProperty("right", "unit"), "px") %>' />
					<aui:option label="em" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getPaddingProperty("right", "unit"), "em") %>' />
				</aui:select>
			</span>
			<span class="field-row">
				<aui:input cssClass="same-padding" disabled='<%= portletConfigurationCSSPortletDisplayContext.isSpacingSameForAll("padding") %>' inlineField="<%= true %>" label="bottom" name="paddingBottom" value='<%= portletConfigurationCSSPortletDisplayContext.getPaddingProperty("bottom", "value") %>' />

				<aui:select cssClass="same-padding" disabled='<%= portletConfigurationCSSPortletDisplayContext.isSpacingSameForAll("padding") %>' inlineField="<%= true %>" label="" name="paddingBottomUnit" title="bottom-padding-unit">
					<aui:option label="%" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getPaddingProperty("bottom", "unit"), "%") %>' />
					<aui:option label="px" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getPaddingProperty("bottom", "unit"), "px") %>' />
					<aui:option label="em" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getPaddingProperty("bottom", "unit"), "em") %>' />
				</aui:select>
			</span>
			<span class="field-row">
				<aui:input cssClass="same-padding" disabled='<%= portletConfigurationCSSPortletDisplayContext.isSpacingSameForAll("padding") %>' inlineField="<%= true %>" label="left" name="paddingLeft" value='<%= portletConfigurationCSSPortletDisplayContext.getPaddingProperty("left", "value") %>' />

				<aui:select cssClass="same-padding" disabled='<%= portletConfigurationCSSPortletDisplayContext.isSpacingSameForAll("padding") %>' inlineField="<%= true %>" label="" name="paddingLeftUnit" title="left-padding-unit">
					<aui:option label="%" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getPaddingProperty("left", "unit"), "%") %>' />
					<aui:option label="px" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getPaddingProperty("left", "unit"), "px") %>' />
					<aui:option label="em" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getPaddingProperty("left", "unit"), "em") %>' />
				</aui:select>
			</span>
		</aui:fieldset>
	</clay:col>

	<clay:col
		className="lfr-margin use-for-all-column"
		md="6"
	>
		<aui:fieldset label="margin">
			<aui:input checked='<%= portletConfigurationCSSPortletDisplayContext.isSpacingSameForAll("margin") %>' data-inputselector=".same-margin" label="same-for-all" name="useForAllMargin" type="toggle-switch" />

			<span class="field-row">
				<aui:input inlineField="<%= true %>" label="top" name="marginTop" value='<%= portletConfigurationCSSPortletDisplayContext.getMarginProperty("top", "value") %>' />

				<aui:select inlineField="<%= true %>" label="" name="marginTopUnit" title="top-margin-unit">
					<aui:option label="%" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getMarginProperty("top", "unit"), "%") %>' />
					<aui:option label="px" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getMarginProperty("top", "unit"), "px") %>' />
					<aui:option label="em" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getMarginProperty("top", "unit"), "em") %>' />
				</aui:select>
			</span>
			<span class="field-row">
				<aui:input cssClass="same-margin" disabled='<%= portletConfigurationCSSPortletDisplayContext.isSpacingSameForAll("margin") %>' inlineField="<%= true %>" label="right" name="marginRight" value='<%= portletConfigurationCSSPortletDisplayContext.getMarginProperty("right", "value") %>' />

				<aui:select cssClass="same-margin" disabled='<%= portletConfigurationCSSPortletDisplayContext.isSpacingSameForAll("margin") %>' inlineField="<%= true %>" label="" name="marginRightUnit" title="top-margin-unit">
					<aui:option label="%" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getMarginProperty("right", "unit"), "%") %>' />
					<aui:option label="px" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getMarginProperty("right", "unit"), "px") %>' />
					<aui:option label="em" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getMarginProperty("right", "unit"), "em") %>' />
				</aui:select>
			</span>
			<span class="field-row">
				<aui:input cssClass="same-margin" disabled='<%= portletConfigurationCSSPortletDisplayContext.isSpacingSameForAll("margin") %>' inlineField="<%= true %>" label="bottom" name="marginBottom" value='<%= portletConfigurationCSSPortletDisplayContext.getMarginProperty("bottom", "value") %>' />

				<aui:select cssClass="same-margin" disabled='<%= portletConfigurationCSSPortletDisplayContext.isSpacingSameForAll("margin") %>' inlineField="<%= true %>" label="" name="marginBottomUnit" title="top-margin-unit">
					<aui:option label="%" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getMarginProperty("bottom", "unit"), "%") %>' />
					<aui:option label="px" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getMarginProperty("bottom", "unit"), "px") %>' />
					<aui:option label="em" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getMarginProperty("bottom", "unit"), "em") %>' />
				</aui:select>
			</span>
			<span class="field-row">
				<aui:input cssClass="same-margin" disabled='<%= portletConfigurationCSSPortletDisplayContext.isSpacingSameForAll("margin") %>' inlineField="<%= true %>" label="left" name="marginLeft" value='<%= portletConfigurationCSSPortletDisplayContext.getMarginProperty("left", "value") %>' />

				<aui:select cssClass="same-margin" disabled='<%= portletConfigurationCSSPortletDisplayContext.isSpacingSameForAll("margin") %>' inlineField="<%= true %>" label="" name="marginLeftUnit" title="top-margin-unit">
					<aui:option label="%" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getMarginProperty("left", "unit"), "%") %>' />
					<aui:option label="px" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getMarginProperty("left", "unit"), "px") %>' />
					<aui:option label="em" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getMarginProperty("left", "unit"), "em") %>' />
				</aui:select>
			</span>
		</aui:fieldset>
	</clay:col>
</clay:row>