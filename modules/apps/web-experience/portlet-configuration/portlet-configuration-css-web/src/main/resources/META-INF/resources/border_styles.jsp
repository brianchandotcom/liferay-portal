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
			<aui:input checked="checked" cssClass="lfr-use-for-all" label="same-for-all" name="useForAllWidth" type="toggle-switch" value='<%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderWidth") %>' />

			<span class="field-row">
				<aui:input inlineField="<%= true %>" label="top" name="borderWidthTop" value='<%= portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("top", "value") %>' />

				<aui:select inlineField="<%= true %>" label="" name="borderWidthTopUnit" title="top-border-unit">
					<aui:option label="%" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("top", "unit"), "%") %>' />
					<aui:option label="px" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("top", "unit"), "px") %>' />
					<aui:option label="em" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("top", "unit"), "em") %>' />
				</aui:select>
			</span>
			<span class="field-row">
				<aui:input inlineField="<%= true %>" label="right" name="borderWidthRight" value='<%= portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("right", "value") %>' />

				<aui:select inlineField="<%= true %>" label="" name="borderWidthRightUnit" title="right-border-unit">
					<aui:option label="%" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("right", "unit"), "%") %>' />
					<aui:option label="px" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("right", "unit"), "px") %>' />
					<aui:option label="em" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("right", "unit"), "em") %>' />
				</aui:select>
			</span>
			<span class="field-row">
				<aui:input inlineField="<%= true %>" label="bottom" name="borderWidthBottom" value='<%= portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("bottom", "value") %>' />

				<aui:select inlineField="<%= true %>" label="" name="borderWidthBottomUnit" title="bottom-border-unit">
					<aui:option label="%" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("bottom", "unit"), "%") %>' />
					<aui:option label="px" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("bottom", "unit"), "px") %>' />
					<aui:option label="em" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("bottom", "unit"), "em") %>' />
				</aui:select>
			</span>
			<span class="field-row">
				<aui:input inlineField="<%= true %>" label="left" name="borderWidthLeft" value='<%= portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("left", "value") %>' />

				<aui:select inlineField="<%= true %>" label="" name="borderWidthLeftUnit" title="left-border-unit">
					<aui:option label="%" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("left", "unit"), "%") %>' />
					<aui:option label="px" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("left", "unit"), "px") %>' />
					<aui:option label="em" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getBorderWidthProperty("left", "unit"), "em") %>' />
				</aui:select>
			</span>
		</aui:fieldset>
	</aui:col>

	<aui:col cssClass="lfr-border-style" width="<%= 33 %>">
		<aui:fieldset label="border-style">
			<aui:input checked="checked" cssClass="lfr-use-for-all use-for-all-column" label="same-for-all" name="useForAllStyle" type="toggle-switch" value='<%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderStyle") %>' />

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

			<aui:select label="right" name="borderStyleRight" showEmptyOption="<%= true %>" wrapperCssClass="field-row">
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

			<aui:select label="bottom" name="borderStyleBottom" showEmptyOption="<%= true %>" wrapperCssClass="field-row">
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

			<aui:select label="left" name="borderStyleLeft" showEmptyOption="<%= true %>" wrapperCssClass="field-row">
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
			<aui:input checked="checked" cssClass="lfr-use-for-all use-for-all-column" label="same-for-all" name="useForAllColor" type="toggle-switch" value='<%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderColor") %>' />

			<%
			Map<String, Object> contextBorderTop = new HashMap<>();

			contextBorderTop.put("color", portletConfigurationCSSPortletDisplayContext.getBorderProperty("top", "borderColor"));
			contextBorderTop.put("id", renderResponse.getNamespace() + "borderColorTop");
			contextBorderTop.put("label", LanguageUtil.get(request, "top"));
			contextBorderTop.put("name", renderResponse.getNamespace() + "borderColorTop");
			contextBorderTop.put("wrapperCssClass", "field-row");
			%>

			<soy:template-renderer
				context="<%= contextBorderTop %>"
				module="portlet-configuration-css-web/js/ColorPickerInput.es"
				templateNamespace="ColorPickerInput.render"
			/>

			<%
			Map<String, Object> contextBorderRight = new HashMap<>();

			contextBorderRight.put("color", portletConfigurationCSSPortletDisplayContext.getBorderProperty("right", "borderColor"));
			contextBorderRight.put("id", renderResponse.getNamespace() + "borderColorRight");
			contextBorderRight.put("label", LanguageUtil.get(request, "right"));
			contextBorderRight.put("name", renderResponse.getNamespace() + "borderColorRight");
			contextBorderRight.put("wrapperCssClass", "field-row");
			%>

			<soy:template-renderer
				context="<%= contextBorderRight %>"
				module="portlet-configuration-css-web/js/ColorPickerInput.es"
				templateNamespace="ColorPickerInput.render"
			/>

			<%
			Map<String, Object> contextBorderBottom = new HashMap<>();

			contextBorderBottom.put("color", portletConfigurationCSSPortletDisplayContext.getBorderProperty("bottom", "borderColor"));
			contextBorderBottom.put("id", renderResponse.getNamespace() + "borderColorBottom");
			contextBorderBottom.put("label", LanguageUtil.get(request, "bottom"));
			contextBorderBottom.put("name", renderResponse.getNamespace() + "borderColorBottom");
			contextBorderBottom.put("wrapperCssClass", "field-row");
			%>

			<soy:template-renderer
				context="<%= contextBorderBottom %>"
				module="portlet-configuration-css-web/js/ColorPickerInput.es"
				templateNamespace="ColorPickerInput.render"
			/>

			<%
			Map<String, Object> contextBorderLeft = new HashMap<>();

			contextBorderLeft.put("color", portletConfigurationCSSPortletDisplayContext.getBorderProperty("left", "borderColor"));
			contextBorderLeft.put("id", renderResponse.getNamespace() + "borderColorLeft");
			contextBorderLeft.put("label", LanguageUtil.get(request, "left"));
			contextBorderLeft.put("name", renderResponse.getNamespace() + "borderColorLeft");
			contextBorderLeft.put("wrapperCssClass", "field-row");
			%>

			<soy:template-renderer
				context="<%= contextBorderLeft %>"
				module="portlet-configuration-css-web/js/ColorPickerInput.es"
				templateNamespace="ColorPickerInput.render"
			/>
		</aui:fieldset>
	</aui:col>
</aui:row>

<aui:script>
	Liferay.Util.disableToggleBoxes('<portlet:namespace />useForAllWidth', '<portlet:namespace />borderWidthRight', <%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderWidth") %>);
	Liferay.Util.disableToggleBoxes('<portlet:namespace />useForAllWidth', '<portlet:namespace />borderWidthRightUnit', <%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderWidth") %>);
	Liferay.Util.disableToggleBoxes('<portlet:namespace />useForAllWidth', '<portlet:namespace />borderWidthBottom', <%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderWidth") %>);
	Liferay.Util.disableToggleBoxes('<portlet:namespace />useForAllWidth', '<portlet:namespace />borderWidthBottomUnit', <%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderWidth") %>);
	Liferay.Util.disableToggleBoxes('<portlet:namespace />useForAllWidth', '<portlet:namespace />borderWidthLeft', <%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderWidth") %>);
	Liferay.Util.disableToggleBoxes('<portlet:namespace />useForAllWidth', '<portlet:namespace />borderWidthLeftUnit', <%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderWidth") %>);
	Liferay.Util.disableToggleBoxes('<portlet:namespace />useForAllStyle', '<portlet:namespace />borderStyleRight', <%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderStyle") %>);
	Liferay.Util.disableToggleBoxes('<portlet:namespace />useForAllStyle', '<portlet:namespace />borderStyleBottom', <%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderStyle") %>);
	Liferay.Util.disableToggleBoxes('<portlet:namespace />useForAllStyle', '<portlet:namespace />borderStyleLeft', <%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderStyle") %>);
	Liferay.Util.disableToggleBoxes('<portlet:namespace />useForAllColor', '<portlet:namespace />borderColorRight', <%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderColor") %>);
	Liferay.Util.disableToggleBoxes('<portlet:namespace />useForAllColor', '<portlet:namespace />borderColorBottom', <%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderColor") %>);
	Liferay.Util.disableToggleBoxes('<portlet:namespace />useForAllColor', '<portlet:namespace />borderColorLeft', <%= portletConfigurationCSSPortletDisplayContext.isBorderSameForAll("borderColor") %>);
</aui:script>