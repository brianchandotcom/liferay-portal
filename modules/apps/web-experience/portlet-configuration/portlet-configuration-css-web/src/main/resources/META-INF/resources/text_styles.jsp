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
JSONObject textDataJSONObject = portletSetupJSONObject.getJSONObject("textData");

DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();

decimalFormatSymbols.setDecimalSeparator('.');
%>

<aui:row>
	<aui:col width="<%= 33 %>">
		<aui:select label="font" name="fontFamily" showEmptyOption="<%= true %>">
			<aui:option label="Arial" selected='<%= Objects.equals(textDataJSONObject.get("fontFamily"), "Arial") %>' />
			<aui:option label="Georgia" selected='<%= Objects.equals(textDataJSONObject.get("fontFamily"), "Georgia") %>' />
			<aui:option label="Times New Roman" selected='<%= Objects.equals(textDataJSONObject.get("fontFamily"), "Times New Roman") %>' />
			<aui:option label="Tahoma" selected='<%= Objects.equals(textDataJSONObject.get("fontFamily"), "Tahoma") %>' />
			<aui:option label="Trebuchet MS" selected='<%= Objects.equals(textDataJSONObject.get("fontFamily"), "Trebuchet MS") %>' />
			<aui:option label="Verdana" selected='<%= Objects.equals(textDataJSONObject.get("fontFamily"), "Verdana") %>' />
		</aui:select>

		<aui:input label="bold" name="fontBold" type="toggle-switch" value='<%= Objects.equals(textDataJSONObject.get("fontWeight"), "bold") %>' />

		<aui:input label="italic" name="fontItalic" type="toggle-switch" value='<%= Objects.equals(textDataJSONObject.get("fontStyle"), "italic") %>' />

		<aui:select label="size" name="fontSize" showEmptyOption="<%= true %>">

			<%
			DecimalFormat decimalFormat = new DecimalFormat("#.##em", decimalFormatSymbols);

			for (double i = 0.1; i <= 12; i += 0.1) {
				String value = decimalFormat.format(i);
			%>

				<aui:option label="<%= value %>" selected='<%= Objects.equals(textDataJSONObject.get("fontSize"), value) %>' />

			<%
			}
			%>

		</aui:select>

		<aui:input label="color" name="fontColor" value='<%= textDataJSONObject.get("color") %>' />

		<aui:select label="alignment" name="textAlign" showEmptyOption="<%= true %>">
			<aui:option label="justify" selected='<%= Objects.equals(textDataJSONObject.get("textAlign"), "justify") %>' />
			<aui:option label="left" selected='<%= Objects.equals(textDataJSONObject.get("textAlign"), "left") %>' />
			<aui:option label="right" selected='<%= Objects.equals(textDataJSONObject.get("textAlign"), "right") %>' />
			<aui:option label="center" selected='<%= Objects.equals(textDataJSONObject.get("textAlign"), "center") %>' />
		</aui:select>

		<aui:select label="text-decoration" name="textDecoration" showEmptyOption="<%= true %>">
			<aui:option label="none" selected='<%= Objects.equals(textDataJSONObject.get("textDecoration"), "none") %>' />
			<aui:option label="underline" selected='<%= Objects.equals(textDataJSONObject.get("textDecoration"), "underline") %>' />
			<aui:option label="overline" selected='<%= Objects.equals(textDataJSONObject.get("textDecoration"), "overline") %>' />
			<aui:option label="strikethrough" selected='<%= Objects.equals(textDataJSONObject.get("textDecoration"), "line-through") %>' value="line-through" />
		</aui:select>
	</aui:col>

	<aui:col last="<%= true %>" width="<%= 60 %>">
		<aui:select label="word-spacing" name="wordSpacing" showEmptyOption="<%= true %>">

			<%
			DecimalFormat decimalFormat = new DecimalFormat("#.##em", decimalFormatSymbols);

			for (double i = -1; i <= 1; i += 0.05) {
				String value = decimalFormat.format(i);

				if (value.equals("0em")) {
					value = "normal";
				}
			%>

				<aui:option label="<%= value %>" selected='<%= Objects.equals(textDataJSONObject.get("wordSpacing"), value) %>' />

			<%
			}
			%>

		</aui:select>

		<aui:select label="line-height" name="lineHeight" showEmptyOption="<%= true %>">

			<%
			DecimalFormat decimalFormat = new DecimalFormat("#.##em", decimalFormatSymbols);

			for (double i = 0.1; i <= 12; i += 0.1) {
				String value = decimalFormat.format(i);
			%>

				<aui:option label="<%= value %>" selected='<%= Objects.equals(textDataJSONObject.get("lineHeight"), value) %>' />

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

				<aui:option label="<%= value %>" selected='<%= Objects.equals(textDataJSONObject.get("letterSpacing"), value) %>' />

			<%
			}
			%>

		</aui:select>
	</aui:col>
</aui:row>