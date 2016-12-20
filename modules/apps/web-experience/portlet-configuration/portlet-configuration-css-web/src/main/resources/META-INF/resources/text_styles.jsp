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
DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();

decimalFormatSymbols.setDecimalSeparator('.');
%>

<aui:row>
	<aui:col width="<%= 33 %>">
		<aui:select label="font" name="lfr-font-family" showEmptyOption="<%= true %>">
			<aui:option label="Arial" />
			<aui:option label="Georgia" />
			<aui:option label="Times New Roman" />
			<aui:option label="Tahoma" />
			<aui:option label="Trebuchet MS" />
			<aui:option label="Verdana" />
		</aui:select>

		<aui:input label="bold" name="lfr-font-bold" type="toggle-switch" />

		<aui:input label="italic" name="lfr-font-italic" type="toggle-switch" />

		<aui:select label="size" name="lfr-font-size" showEmptyOption="<%= true %>">

			<%
			DecimalFormat decimalFormat = new DecimalFormat("#.##em", decimalFormatSymbols);

			for (double i = 0.1; i <= 12; i += 0.1) {
				String value = decimalFormat.format(i);
			%>

				<aui:option label="<%= value %>" />

			<%
			}
			%>

		</aui:select>

		<aui:input label="color" name="lfr-font-color" />

		<aui:select label="alignment" name="lfr-font-align" showEmptyOption="<%= true %>">
			<aui:option label="justify" />
			<aui:option label="left" />
			<aui:option label="right" />
			<aui:option label="center" />
		</aui:select>

		<aui:select label="text-decoration" name="lfr-font-decoration" showEmptyOption="<%= true %>">
			<aui:option label="none" />
			<aui:option label="underline" />
			<aui:option label="overline" />
			<aui:option label="strikethrough" value="line-through" />
		</aui:select>
	</aui:col>

	<aui:col last="<%= true %>" width="<%= 60 %>">
		<aui:select label="word-spacing" name="lfr-font-space" showEmptyOption="<%= true %>">

			<%
			DecimalFormat decimalFormat = new DecimalFormat("#.##em", decimalFormatSymbols);

			for (double i = -1; i <= 1; i += 0.05) {
				String value = decimalFormat.format(i);

				if (value.equals("0em")) {
					value = "normal";
				}
			%>

				<aui:option label="<%= value %>" />

			<%
			}
			%>

		</aui:select>

		<aui:select label="line-height" name="lfr-font-leading" showEmptyOption="<%= true %>">

			<%
			DecimalFormat decimalFormat = new DecimalFormat("#.##em", decimalFormatSymbols);

			for (double i = 0.1; i <= 12; i += 0.1) {
				String value = decimalFormat.format(i);
			%>

				<aui:option label="<%= value %>" />

			<%
			}
			%>

		</aui:select>

		<aui:select label="letter-spacing" name="lfr-font-tracking" showEmptyOption="<%= true %>">

			<%
			for (int i = -10; i <= 50; i++) {
				String value = i + "px";

				if (i == 0) {
					value = "0";
				}
			%>

				<aui:option label="<%= value %>" />

			<%
			}
			%>

		</aui:select>
	</aui:col>
</aui:row>