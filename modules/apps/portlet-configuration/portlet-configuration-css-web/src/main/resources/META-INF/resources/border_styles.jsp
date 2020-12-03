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
		cssClass="lfr-border-width use-for-all-column"
		md="4"
	>
		<aui:fieldset label="border-width">
			<aui:input checked='<%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderWidth") %>' data-inputselector=".same-border-width" inlineLabel="right" label="same-for-all" labelCssClass="simple-toggle-switch" name="useForAllWidth" type="toggle-switch" />

			<span class="field-row">
				<aui:input inlineField="<%= true %>" label="top" name="borderWidthTop" value='<%= portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("top", "value") %>' />

				<aui:select inlineField="<%= true %>" label="" name="borderWidthTopUnit" title="top-border-unit">
					<aui:option label="%" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("top", "unit"), "%") %>' />
					<aui:option label="px" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("top", "unit"), "px") %>' />
					<aui:option label="em" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("top", "unit"), "em") %>' />
				</aui:select>
			</span>
			<span class="field-row">
				<aui:input cssClass="same-border-width" disabled='<%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderWidth") %>' inlineField="<%= true %>" label="right" name="borderWidthRight" value='<%= portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("right", "value") %>' />

				<aui:select cssClass="same-border-width" disabled='<%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderWidth") %>' inlineField="<%= true %>" label="" name="borderWidthRightUnit" title="right-border-unit">
					<aui:option label="%" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("right", "unit"), "%") %>' />
					<aui:option label="px" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("right", "unit"), "px") %>' />
					<aui:option label="em" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("right", "unit"), "em") %>' />
				</aui:select>
			</span>
			<span class="field-row">
				<aui:input cssClass="same-border-width" disabled='<%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderWidth") %>' inlineField="<%= true %>" label="bottom" name="borderWidthBottom" value='<%= portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("bottom", "value") %>' />

				<aui:select cssClass="same-border-width" disabled='<%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderWidth") %>' inlineField="<%= true %>" label="" name="borderWidthBottomUnit" title="bottom-border-unit">
					<aui:option label="%" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("bottom", "unit"), "%") %>' />
					<aui:option label="px" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("bottom", "unit"), "px") %>' />
					<aui:option label="em" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("bottom", "unit"), "em") %>' />
				</aui:select>
			</span>
			<span class="field-row">
				<aui:input cssClass="same-border-width" disabled='<%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderWidth") %>' inlineField="<%= true %>" label="left" name="borderWidthLeft" value='<%= portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("left", "value") %>' />

				<aui:select cssClass="same-border-width" disabled='<%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderWidth") %>' inlineField="<%= true %>" label="" name="borderWidthLeftUnit" title="left-border-unit">
					<aui:option label="%" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("left", "unit"), "%") %>' />
					<aui:option label="px" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("left", "unit"), "px") %>' />
					<aui:option label="em" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("left", "unit"), "em") %>' />
				</aui:select>
			</span>
		</aui:fieldset>
	</clay:col>

	<clay:col
		cssClass="lfr-border-style"
		md="4"
	>
		<aui:fieldset label="border-style">
			<aui:input checked='<%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderStyle") %>' data-inputselector=".same-border-style" inlineLabel="right" label="same-for-all" labelCssClass="simple-toggle-switch" name="useForAllStyle" type="toggle-switch" />

			<aui:select label="top" name="borderStyleTop" showEmptyOption="<%= true %>" wrapperCssClass="field-row">
				<aui:option label="dashed" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("top", "borderStyle"), "dashed") %>' />
				<aui:option label="double" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("top", "borderStyle"), "double") %>' />
				<aui:option label="dotted" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("top", "borderStyle"), "dotted") %>' />
				<aui:option label="groove" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("top", "borderStyle"), "groove") %>' />
				<aui:option label="hidden[css]" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("top", "borderStyle"), "hidden") %>' value="hidden" />
				<aui:option label="inset" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("top", "borderStyle"), "inset") %>' />
				<aui:option label="outset" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("top", "borderStyle"), "outset") %>' />
				<aui:option label="ridge" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("top", "borderStyle"), "ridge") %>' />
				<aui:option label="solid" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("top", "borderStyle"), "solid") %>' />
			</aui:select>

			<aui:select cssClass="same-border-style" disabled='<%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderStyle") %>' label="right" name="borderStyleRight" showEmptyOption="<%= true %>" wrapperCssClass="field-row">
				<aui:option label="dashed" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("right", "borderStyle"), "dashed") %>' />
				<aui:option label="double" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("right", "borderStyle"), "double") %>' />
				<aui:option label="dotted" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("right", "borderStyle"), "dotted") %>' />
				<aui:option label="groove" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("right", "borderStyle"), "groove") %>' />
				<aui:option label="hidden[css]" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("right", "borderStyle"), "hidden") %>' value="hidden" />
				<aui:option label="inset" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("right", "borderStyle"), "inset") %>' />
				<aui:option label="outset" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("right", "borderStyle"), "outset") %>' />
				<aui:option label="ridge" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("right", "borderStyle"), "ridge") %>' />
				<aui:option label="solid" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("right", "borderStyle"), "solid") %>' />
			</aui:select>

			<aui:select cssClass="same-border-style" disabled='<%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderStyle") %>' label="bottom" name="borderStyleBottom" showEmptyOption="<%= true %>" wrapperCssClass="field-row">
				<aui:option label="dashed" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("bottom", "borderStyle"), "dashed") %>' />
				<aui:option label="double" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("bottom", "borderStyle"), "double") %>' />
				<aui:option label="dotted" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("bottom", "borderStyle"), "dotted") %>' />
				<aui:option label="groove" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("bottom", "borderStyle"), "groove") %>' />
				<aui:option label="hidden[css]" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("bottom", "borderStyle"), "hidden") %>' value="hidden" />
				<aui:option label="inset" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("bottom", "borderStyle"), "inset") %>' />
				<aui:option label="outset" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("bottom", "borderStyle"), "outset") %>' />
				<aui:option label="ridge" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("bottom", "borderStyle"), "ridge") %>' />
				<aui:option label="solid" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("bottom", "borderStyle"), "solid") %>' />
			</aui:select>

			<aui:select cssClass="same-border-style" disabled='<%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderStyle") %>' label="left" name="borderStyleLeft" showEmptyOption="<%= true %>" wrapperCssClass="field-row">
				<aui:option label="dashed" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("left", "borderStyle"), "dashed") %>' />
				<aui:option label="double" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("left", "borderStyle"), "double") %>' />
				<aui:option label="dotted" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("left", "borderStyle"), "dotted") %>' />
				<aui:option label="groove" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("left", "borderStyle"), "groove") %>' />
				<aui:option label="hidden[css]" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("left", "borderStyle"), "hidden") %>' value="hidden" />
				<aui:option label="inset" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("left", "borderStyle"), "inset") %>' />
				<aui:option label="outset" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("left", "borderStyle"), "outset") %>' />
				<aui:option label="ridge" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("left", "borderStyle"), "ridge") %>' />
				<aui:option label="solid" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderProperty("left", "borderStyle"), "solid") %>' />
			</aui:select>
		</aui:fieldset>
	</clay:col>

	<clay:col
		cssClass="lfr-border-color"
		md="4"
	>
		<aui:fieldset label="border-color">
			<aui:input checked='<%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderColor") %>' data-inputselector=".same-border-color" inlineLabel="right" label="same-for-all" labelCssClass="simple-toggle-switch" name="useForAllColor" type="toggle-switch" />

			<liferay-util:include page="/color_picker_input.jsp" servletContext="<%= application %>">
				<liferay-util:param name="color" value='<%= portletConfigurationCSSPortletDisplayContext.getBorderProperty("top", "borderColor") %>' />
				<liferay-util:param name="label" value='<%= LanguageUtil.get(request, "top") %>' />
				<liferay-util:param name="name" value='<%= liferayPortletResponse.getNamespace() + "borderColorTop" %>' />
			</liferay-util:include>

			<fieldset class="same-border-color" <%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderColor") ? "disabled" : StringPool.BLANK %>>
				<liferay-util:include page="/color_picker_input.jsp" servletContext="<%= application %>">
					<liferay-util:param name="color" value='<%= portletConfigurationCSSPortletDisplayContext.getBorderProperty("right", "borderColor") %>' />
					<liferay-util:param name="label" value='<%= LanguageUtil.get(request, "right") %>' />
					<liferay-util:param name="name" value='<%= liferayPortletResponse.getNamespace() + "borderColorRight" %>' />
				</liferay-util:include>

				<liferay-util:include page="/color_picker_input.jsp" servletContext="<%= application %>">
					<liferay-util:param name="color" value='<%= portletConfigurationCSSPortletDisplayContext.getBorderProperty("bottom", "borderColor") %>' />
					<liferay-util:param name="label" value='<%= LanguageUtil.get(request, "bottom") %>' />
					<liferay-util:param name="name" value='<%= liferayPortletResponse.getNamespace() + "borderColorBottom" %>' />
				</liferay-util:include>

				<liferay-util:include page="/color_picker_input.jsp" servletContext="<%= application %>">
					<liferay-util:param name="color" value='<%= portletConfigurationCSSPortletDisplayContext.getBorderProperty("left", "borderColor") %>' />
					<liferay-util:param name="label" value='<%= LanguageUtil.get(request, "left") %>' />
					<liferay-util:param name="name" value='<%= liferayPortletResponse.getNamespace() + "borderColorLeft" %>' />
				</liferay-util:include>
			</fieldset>
		</aui:fieldset>
	</clay:col>
</clay:row>