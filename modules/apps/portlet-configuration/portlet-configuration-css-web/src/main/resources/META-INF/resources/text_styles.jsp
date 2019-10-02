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
DecimalFormat decimalFormat = portletConfigurationCSSPortletDisplayContext.getDecimalFormat();
%>

<aui:row>
	<aui:col width="<%= 33 %>">
		<aui:select label="font" name="fontFamily" showEmptyOption="<%= true %>">
			<aui:option label="Arial" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getTextDataProperty("fontFamily"), "Arial") %>' />
			<aui:option label="Georgia" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getTextDataProperty("fontFamily"), "Georgia") %>' />
			<aui:option label="Times New Roman" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getTextDataProperty("fontFamily"), "Times New Roman") %>' />
			<aui:option label="Tahoma" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getTextDataProperty("fontFamily"), "Tahoma") %>' />
			<aui:option label="Trebuchet MS" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getTextDataProperty("fontFamily"), "Trebuchet MS") %>' />
			<aui:option label="Verdana" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getTextDataProperty("fontFamily"), "Verdana") %>' />
		</aui:select>

		<aui:input label="bold" name="fontBold" type="toggle-switch" value='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getTextDataProperty("fontWeight"), "bold") %>' />

		<aui:input label="italic" name="fontItalic" type="toggle-switch" value='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getTextDataProperty("fontStyle"), "italic") %>' />

		<aui:select label="size" name="fontSize" showEmptyOption="<%= true %>">

			<%
			for (double i = 0.1; i <= 12; i += 0.1) {
				String value = decimalFormat.format(i);
			%>

				<aui:option label="<%= value %>" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getTextDataProperty("fontSize"), value) %>' />

			<%
			}
			%>

		</aui:select>

		<div>

			<%
			String color = portletConfigurationCSSPortletDisplayContext.getTextDataProperty("color");
			String label = LanguageUtil.get(request, "color");
			String name = renderResponse.getNamespace() + "fontColor";

			Map<String, Object> data = new HashMap<>();

			data.put("color", color);
			data.put("label", label);
			data.put("name", name);
			%>

			<div class="form-group">
				<input name="<%= name %>" type="hidden" value="#<%= color %>" />

				<div class="clay-color-picker">
					<label><%= label %></label>

					<div class="clay-color input-group">
						<div class="input-group-item input-group-item-shrink input-group-prepend">
							<div class="input-group-text">
								<button class="btn clay-color-btn dropdown-toggle" style="border-width: 0px; height: 28px; width: 28px;" title="<%= color %>" type="button" />
							</div>
						</div>

						<div class="input-group-append input-group-item">
							<input class="form-control input-group-inset input-group-inset-before" type="hidden" />
							<label class="input-group-inset-item input-group-inset-item-before"><%= color %></label>
						</div>
					</div>
				</div>
			</div>

			<react:component
				data="<%= data %>"
				module="js/ColorPickerInput.es"
				servletContext="<%= application %>"
			/>
		</div>

		<aui:select label="alignment" name="textAlign" showEmptyOption="<%= true %>">
			<aui:option label="justify" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getTextDataProperty("textAlign"), "justify") %>' />
			<aui:option label="left" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getTextDataProperty("textAlign"), "left") %>' />
			<aui:option label="right" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getTextDataProperty("textAlign"), "right") %>' />
			<aui:option label="center" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getTextDataProperty("textAlign"), "center") %>' />
		</aui:select>

		<aui:select label="text-decoration" name="textDecoration" showEmptyOption="<%= true %>">
			<aui:option label="none" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getTextDataProperty("textDecoration"), "none") %>' />
			<aui:option label="underline" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getTextDataProperty("textDecoration"), "underline") %>' />
			<aui:option label="overline" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getTextDataProperty("textDecoration"), "overline") %>' />
			<aui:option label="strikethrough" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getTextDataProperty("textDecoration"), "line-through") %>' value="line-through" />
		</aui:select>
	</aui:col>

	<aui:col last="<%= true %>" width="<%= 60 %>">
		<aui:select label="word-spacing" name="wordSpacing" showEmptyOption="<%= true %>">

			<%
			for (double i = -1; i <= 1; i += 0.05) {
				String value = decimalFormat.format(i);

				if (value.equals("0em")) {
					value = "normal";
				}
			%>

				<aui:option label="<%= value %>" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getTextDataProperty("wordSpacing"), value) %>' />

			<%
			}
			%>

		</aui:select>

		<aui:select label="line-height" name="lineHeight" showEmptyOption="<%= true %>">

			<%
			for (double i = 0.1; i <= 12; i += 0.1) {
				String value = decimalFormat.format(i);
			%>

				<aui:option label="<%= value %>" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getTextDataProperty("lineHeight"), value) %>' />

			<%
			}
			%>

		</aui:select>

		<aui:select label="letter-spacing" name="letterSpacing" showEmptyOption="<%= true %>">

			<%
			for (int i = -10; i <= 50; i++) {
				String value = i + "px";

				if (i == 0) {
					value = "0";
				}
			%>

				<aui:option label="<%= value %>" selected='<%= Objects.equals(portletConfigurationCSSPortletDisplayContext.getTextDataProperty("letterSpacing"), value) %>' />

			<%
			}
			%>

		</aui:select>
	</aui:col>
</aui:row>