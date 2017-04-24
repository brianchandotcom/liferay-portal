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

<aui:row>
	<aui:col cssClass="lfr-border-width use-for-all-column" width="<%= 33 %>">
		<aui:fieldset label="border-width">

			<%
			Map<String, Object> contextUseForAllWidth = new HashMap<>();

			contextUseForAllWidth.put("checked", portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderWidth"));
			contextUseForAllWidth.put("inputSelector", ".same-border-width");
			contextUseForAllWidth.put("label", LanguageUtil.get(request, "same-for-all"));
			contextUseForAllWidth.put("name", renderResponse.getNamespace() + "useForAllWidth");
			%>

			<soy:template-renderer
				context="<%= contextUseForAllWidth %>"
				module="portlet-configuration-css-web/js/ToggleDisableInputs.es"
				templateNamespace="ToggleDisableInputs.render"
			/>

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
	</aui:col>

	<aui:col cssClass="lfr-border-style" width="<%= 33 %>">
		<aui:fieldset label="border-style">

			<%
			Map<String, Object> contextUseForAllStyle = new HashMap<>();

			contextUseForAllStyle.put("checked", portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderStyle"));
			contextUseForAllStyle.put("inputSelector", ".same-border-style");
			contextUseForAllStyle.put("label", LanguageUtil.get(request, "same-for-all"));
			contextUseForAllStyle.put("name", renderResponse.getNamespace() + "useForAllStyle");
			%>

			<soy:template-renderer
				context="<%= contextUseForAllStyle %>"
				module="portlet-configuration-css-web/js/ToggleDisableInputs.es"
				templateNamespace="ToggleDisableInputs.render"
			/>

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
	</aui:col>

	<aui:col cssClass="lfr-border-color" last="<%= true %>" width="<%= 33 %>">
		<aui:fieldset label="border-color">

			<%
			Map<String, Object> contextUseForAllColor = new HashMap<>();

			contextUseForAllColor.put("checked", portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderColor"));
			contextUseForAllColor.put("inputSelector", ".same-border-color");
			contextUseForAllColor.put("label", LanguageUtil.get(request, "same-for-all"));
			contextUseForAllColor.put("name", renderResponse.getNamespace() + "useForAllColor");
			%>

			<soy:template-renderer
				context="<%= contextUseForAllColor %>"
				module="portlet-configuration-css-web/js/ToggleDisableInputs.es"
				templateNamespace="ToggleDisableInputs.render"
			/>

			<%
			Map<String, Object> contextBorderTop = new HashMap<>();

			contextBorderTop.put("color", portletConfigurationCSSPortletDisplayContext.getBorderProperty("top", "borderColor"));
			contextBorderTop.put("elementClasses", "field-row");
			contextBorderTop.put("id", renderResponse.getNamespace() + "borderColorTop");
			contextBorderTop.put("label", LanguageUtil.get(request, "top"));
			contextBorderTop.put("name", renderResponse.getNamespace() + "borderColorTop");
			%>

			<soy:template-renderer
				context="<%= contextBorderTop %>"
				module="portlet-configuration-css-web/js/ColorPickerInput.es"
				templateNamespace="ColorPickerInput.render"
			/>

			<%
			Map<String, Object> contextBorderRight = new HashMap<>();

			contextBorderRight.put("color", portletConfigurationCSSPortletDisplayContext.getBorderProperty("right", "borderColor"));
			contextBorderRight.put("disabled", portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderColor"));
			contextBorderRight.put("elementClasses", "field-row");
			contextBorderRight.put("id", renderResponse.getNamespace() + "borderColorRight");
			contextBorderRight.put("inputClasses", "same-border-color");
			contextBorderRight.put("label", LanguageUtil.get(request, "right"));
			contextBorderRight.put("name", renderResponse.getNamespace() + "borderColorRight");
			%>

			<soy:template-renderer
				context="<%= contextBorderRight %>"
				module="portlet-configuration-css-web/js/ColorPickerInput.es"
				templateNamespace="ColorPickerInput.render"
			/>

			<%
			Map<String, Object> contextBorderBottom = new HashMap<>();

			contextBorderBottom.put("color", portletConfigurationCSSPortletDisplayContext.getBorderProperty("bottom", "borderColor"));
			contextBorderBottom.put("disabled", portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderColor"));
			contextBorderBottom.put("elementClasses", "field-row");
			contextBorderBottom.put("id", renderResponse.getNamespace() + "borderColorBottom");
			contextBorderBottom.put("inputClasses", "same-border-color");
			contextBorderBottom.put("label", LanguageUtil.get(request, "bottom"));
			contextBorderBottom.put("name", renderResponse.getNamespace() + "borderColorBottom");
			%>

			<soy:template-renderer
				context="<%= contextBorderBottom %>"
				module="portlet-configuration-css-web/js/ColorPickerInput.es"
				templateNamespace="ColorPickerInput.render"
			/>

			<%
			Map<String, Object> contextBorderLeft = new HashMap<>();

			contextBorderLeft.put("color", portletConfigurationCSSPortletDisplayContext.getBorderProperty("left", "borderColor"));
			contextBorderLeft.put("disabled", portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderColor"));
			contextBorderLeft.put("elementClasses", "field-row");
			contextBorderLeft.put("id", renderResponse.getNamespace() + "borderColorLeft");
			contextBorderLeft.put("inputClasses", "same-border-color");
			contextBorderLeft.put("label", LanguageUtil.get(request, "left"));
			contextBorderLeft.put("name", renderResponse.getNamespace() + "borderColorLeft");
			%>

			<soy:template-renderer
				context="<%= contextBorderLeft %>"
				module="portlet-configuration-css-web/js/ColorPickerInput.es"
				templateNamespace="ColorPickerInput.render"
			/>
		</aui:fieldset>
	</aui:col>
</aui:row>