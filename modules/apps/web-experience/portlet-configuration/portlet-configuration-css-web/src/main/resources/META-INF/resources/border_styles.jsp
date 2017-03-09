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
JSONObject borderDataJSONObject = portletSetupJSONObject.getJSONObject("borderData");

JSONObject borderColorSONObject = borderDataJSONObject.getJSONObject("borderColor");
JSONObject borderStyleJSONObject = borderDataJSONObject.getJSONObject("borderStyle");
JSONObject borderWidthJSONObject = borderDataJSONObject.getJSONObject("borderWidth");
%>

<aui:row>
	<aui:col cssClass="lfr-border-width use-for-all-column" width="<%= 33 %>">
		<aui:fieldset label="border-width">
			<aui:input checked="checked" cssClass="lfr-use-for-all" label="same-for-all" name="useForAllWidth" type="toggle-switch" value='<%= borderWidthJSONObject.getBoolean("sameForAll") %>' />

			<%
			JSONObject borderWidthTopJSONObject = borderWidthJSONObject.getJSONObject("top");
			%>

			<span class="field-row">
				<aui:input inlineField="<%= true %>" label="top" name="borderWidthTop" value='<%= borderWidthTopJSONObject.getString("value") %>' />

				<aui:select inlineField="<%= true %>" label="" name="borderWidthTopUnit" title="top-border-unit">
					<aui:option label="%" selected='<%= Objects.equals(borderWidthTopJSONObject.getString("unit"), "%") %>' />
					<aui:option label="px" selected='<%= Objects.equals(borderWidthTopJSONObject.getString("unit"), "px") %>' />
					<aui:option label="em" selected='<%= Objects.equals(borderWidthTopJSONObject.getString("unit"), "em") %>' />
				</aui:select>
			</span>

			<%
			JSONObject borderWidthRightJSONObject = borderWidthJSONObject.getJSONObject("right");
			%>

			<span class="field-row">
				<aui:input inlineField="<%= true %>" label="right" name="borderWidthRight" value='<%= borderWidthRightJSONObject.getString("value") %>' />

				<aui:select inlineField="<%= true %>" label="" name="borderWidthRightUnit" title="right-border-unit">
					<aui:option label="%" selected='<%= Objects.equals(borderWidthRightJSONObject.getString("unit"), "%") %>' />
					<aui:option label="px" selected='<%= Objects.equals(borderWidthRightJSONObject.getString("unit"), "px") %>' />
					<aui:option label="em" selected='<%= Objects.equals(borderWidthRightJSONObject.getString("unit"), "em") %>' />
				</aui:select>
			</span>

			<%
			JSONObject borderWidthBottomJSONObject = borderWidthJSONObject.getJSONObject("bottom");
			%>

			<span class="field-row">
				<aui:input inlineField="<%= true %>" label="bottom" name="borderWidthBottom" value='<%= borderWidthBottomJSONObject.getString("value") %>' />

				<aui:select inlineField="<%= true %>" label="" name="borderWidthBottomUnit" title="bottom-border-unit">
					<aui:option label="%" selected='<%= Objects.equals(borderWidthBottomJSONObject.getString("unit"), "%") %>' />
					<aui:option label="px" selected='<%= Objects.equals(borderWidthBottomJSONObject.getString("unit"), "px") %>' />
					<aui:option label="em" selected='<%= Objects.equals(borderWidthBottomJSONObject.getString("unit"), "em") %>' />
				</aui:select>
			</span>

			<%
			JSONObject borderWidthLeftJSONObject = borderWidthJSONObject.getJSONObject("left");
			%>

			<span class="field-row">
				<aui:input inlineField="<%= true %>" label="left" name="borderWidthLeft" value='<%= borderWidthLeftJSONObject.getString("value") %>' />

				<aui:select inlineField="<%= true %>" label="" name="borderWidthLeftUnit" title="left-border-unit">
					<aui:option label="%" selected='<%= Objects.equals(borderWidthLeftJSONObject.getString("unit"), "%") %>' />
					<aui:option label="px" selected='<%= Objects.equals(borderWidthLeftJSONObject.getString("unit"), "px") %>' />
					<aui:option label="em" selected='<%= Objects.equals(borderWidthLeftJSONObject.getString("unit"), "em") %>' />
				</aui:select>
			</span>
		</aui:fieldset>
	</aui:col>

	<aui:col cssClass="lfr-border-style" width="<%= 33 %>">
		<aui:fieldset label="border-style">
			<aui:input checked="checked" cssClass="lfr-use-for-all use-for-all-column" label="same-for-all" name="useForAllStyle" type="toggle-switch" value='<%= borderStyleJSONObject.getBoolean("sameForAll") %>' />

			<aui:select label="top" name="borderStyleTop" showEmptyOption="<%= true %>" wrapperCssClass="field-row">
				<aui:option label="dashed" selected='<%= Objects.equals(borderStyleJSONObject.get("top"), "dashed") %>' />
				<aui:option label="double" selected='<%= Objects.equals(borderStyleJSONObject.get("top"), "double") %>' />
				<aui:option label="dotted" selected='<%= Objects.equals(borderStyleJSONObject.get("top"), "dotted") %>' />
				<aui:option label="groove" selected='<%= Objects.equals(borderStyleJSONObject.get("top"), "groove") %>' />
				<aui:option label="hidden[css]" selected='<%= Objects.equals(borderStyleJSONObject.get("top"), "hidden") %>' value="hidden" />
				<aui:option label="inset" selected='<%= Objects.equals(borderStyleJSONObject.get("top"), "inset") %>' />
				<aui:option label="outset" selected='<%= Objects.equals(borderStyleJSONObject.get("top"), "outset") %>' />
				<aui:option label="ridge" selected='<%= Objects.equals(borderStyleJSONObject.get("top"), "ridge") %>' />
				<aui:option label="solid" selected='<%= Objects.equals(borderStyleJSONObject.get("top"), "solid") %>' />
			</aui:select>

			<aui:select label="right" name="borderStyleRight" showEmptyOption="<%= true %>" wrapperCssClass="field-row">
				<aui:option label="dashed" selected='<%= Objects.equals(borderStyleJSONObject.get("right"), "dashed") %>' />
				<aui:option label="double" selected='<%= Objects.equals(borderStyleJSONObject.get("right"), "double") %>' />
				<aui:option label="dotted" selected='<%= Objects.equals(borderStyleJSONObject.get("right"), "dotted") %>' />
				<aui:option label="groove" selected='<%= Objects.equals(borderStyleJSONObject.get("right"), "groove") %>' />
				<aui:option label="hidden[css]" selected='<%= Objects.equals(borderStyleJSONObject.get("right"), "hidden") %>' value="hidden" />
				<aui:option label="inset" selected='<%= Objects.equals(borderStyleJSONObject.get("right"), "inset") %>' />
				<aui:option label="outset" selected='<%= Objects.equals(borderStyleJSONObject.get("right"), "outset") %>' />
				<aui:option label="ridge" selected='<%= Objects.equals(borderStyleJSONObject.get("right"), "ridge") %>' />
				<aui:option label="solid" selected='<%= Objects.equals(borderStyleJSONObject.get("right"), "solid") %>' />
			</aui:select>

			<aui:select label="bottom" name="borderStyleBottom" showEmptyOption="<%= true %>" wrapperCssClass="field-row">
				<aui:option label="dashed" selected='<%= Objects.equals(borderStyleJSONObject.get("bottom"), "dashed") %>' />
				<aui:option label="double" selected='<%= Objects.equals(borderStyleJSONObject.get("bottom"), "double") %>' />
				<aui:option label="dotted" selected='<%= Objects.equals(borderStyleJSONObject.get("bottom"), "dotted") %>' />
				<aui:option label="groove" selected='<%= Objects.equals(borderStyleJSONObject.get("bottom"), "groove") %>' />
				<aui:option label="hidden[css]" selected='<%= Objects.equals(borderStyleJSONObject.get("bottom"), "hidden") %>' value="hidden" />
				<aui:option label="inset" selected='<%= Objects.equals(borderStyleJSONObject.get("bottom"), "inset") %>' />
				<aui:option label="outset" selected='<%= Objects.equals(borderStyleJSONObject.get("bottom"), "outset") %>' />
				<aui:option label="ridge" selected='<%= Objects.equals(borderStyleJSONObject.get("bottom"), "ridge") %>' />
				<aui:option label="solid" selected='<%= Objects.equals(borderStyleJSONObject.get("bottom"), "solid") %>' />
			</aui:select>

			<aui:select label="left" name="borderStyleLeft" showEmptyOption="<%= true %>" wrapperCssClass="field-row">
				<aui:option label="dashed" selected='<%= Objects.equals(borderStyleJSONObject.get("left"), "dashed") %>' />
				<aui:option label="double" selected='<%= Objects.equals(borderStyleJSONObject.get("left"), "double") %>' />
				<aui:option label="dotted" selected='<%= Objects.equals(borderStyleJSONObject.get("left"), "dotted") %>' />
				<aui:option label="groove" selected='<%= Objects.equals(borderStyleJSONObject.get("left"), "groove") %>' />
				<aui:option label="hidden[css]" selected='<%= Objects.equals(borderStyleJSONObject.get("left"), "hidden") %>' value="hidden" />
				<aui:option label="inset" selected='<%= Objects.equals(borderStyleJSONObject.get("left"), "inset") %>' />
				<aui:option label="outset" selected='<%= Objects.equals(borderStyleJSONObject.get("left"), "outset") %>' />
				<aui:option label="ridge" selected='<%= Objects.equals(borderStyleJSONObject.get("left"), "ridge") %>' />
				<aui:option label="solid" selected='<%= Objects.equals(borderStyleJSONObject.get("left"), "solid") %>' />
			</aui:select>
		</aui:fieldset>
	</aui:col>

	<aui:col cssClass="lfr-border-color" last="<%= true %>" width="<%= 33 %>">
		<aui:fieldset label="border-color">
			<aui:input checked="checked" cssClass="lfr-use-for-all use-for-all-column" label="same-for-all" name="useForAllColor" type="toggle-switch" value='<%= borderColorSONObject.getBoolean("sameForAll") %>' />

			<aui:input label="top" name="borderColorTop" value='<%= borderColorSONObject.get("top") %>' wrapperCssClass="field-row" />

			<aui:input label="right" name="borderColorRight" value='<%= borderColorSONObject.get("right") %>' wrapperCssClass="field-row" />

			<aui:input label="bottom" name="borderColorBottom" value='<%= borderColorSONObject.get("bottom") %>' wrapperCssClass="field-row" />

			<aui:input label="left" name="borderColorLeft" value='<%= borderColorSONObject.get("left") %>' wrapperCssClass="field-row" />
		</aui:fieldset>
	</aui:col>
</aui:row>