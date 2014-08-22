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

<%@ include file="/html/portlet/unit_converter/init.jsp" %>

<%
int type = ParamUtil.getInteger(request, "type");
int fromId = ParamUtil.getInteger(request, "fromId");
int toId = ParamUtil.getInteger(request, "toId");
double fromValue = ParamUtil.getDouble(request, "fromValue");

Conversion conversion = ConverterUtil.getConversion(type, fromId, toId, fromValue);
%>

<form action="<liferay-portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="struts_action" value="/unit_converter/view" /></liferay-portlet:renderURL>" id="<portlet:namespace />fm" method="post" name="<portlet:namespace />fm">

<table class="lfr-table">
<tr>
	<td>
		<liferay-ui:message key="from" />
	</td>
	<td>
		<input name="<portlet:namespace />fromValue" size="30" type="text" value="<%= conversion.getFromValue() %>" />

		<select name="<portlet:namespace />fromId">
			<c:if test="<%= type == 0 %>">
				<option <%= (fromId == 0) ? "selected" : "" %> value="0"><liferay-ui:message key="meter" /></option>
				<option <%= (fromId == 1) ? "selected" : "" %> value="1"><liferay-ui:message key="millimeter" /></option>
				<option <%= (fromId == 2) ? "selected" : "" %> value="2"><liferay-ui:message key="centimeter" /></option>
				<option <%= (fromId == 3) ? "selected" : "" %> value="3"><liferay-ui:message key="kilometer" /></option>
				<option <%= (fromId == 4) ? "selected" : "" %> value="4"><liferay-ui:message key="foot" /></option>
				<option <%= (fromId == 5) ? "selected" : "" %> value="5"><liferay-ui:message key="inch" /></option>
				<option <%= (fromId == 6) ? "selected" : "" %> value="6"><liferay-ui:message key="yard" /></option>
				<option <%= (fromId == 7) ? "selected" : "" %> value="7"><liferay-ui:message key="mile" /></option>
				<option <%= (fromId == 8) ? "selected" : "" %> value="8"><liferay-ui:message key="cubit" /></option>
				<option <%= (fromId == 9) ? "selected" : "" %> value="9"><liferay-ui:message key="talent" /></option>
				<option <%= (fromId == 10) ? "selected" : "" %> value="10"><liferay-ui:message key="handbreath" /></option>
			</c:if>
			<c:if test="<%= type == 1 %>">
				<option <%= (fromId == 0) ? "selected" : "" %> value="0"><liferay-ui:message key="square-kilometer" /></option>
				<option <%= (fromId == 1) ? "selected" : "" %> value="1"><liferay-ui:message key="square-meter" /></option>
				<option <%= (fromId == 2) ? "selected" : "" %> value="2"><liferay-ui:message key="square-centimeter" /></option>
				<option <%= (fromId == 3) ? "selected" : "" %> value="3"><liferay-ui:message key="square-millimeter" /></option>
				<option <%= (fromId == 4) ? "selected" : "" %> value="4"><liferay-ui:message key="square-foot" /></option>
				<option <%= (fromId == 5) ? "selected" : "" %> value="5"><liferay-ui:message key="square-inch" /></option>
				<option <%= (fromId == 6) ? "selected" : "" %> value="6"><liferay-ui:message key="square-yard" /></option>
				<option <%= (fromId == 7) ? "selected" : "" %> value="7"><liferay-ui:message key="square-mile" /></option>
				<option <%= (fromId == 8) ? "selected" : "" %> value="8"><liferay-ui:message key="hectare" /></option>
				<option <%= (fromId == 9) ? "selected" : "" %> value="9"><liferay-ui:message key="acre" /></option>
			</c:if>
			<c:if test="<%= type == 2 %>">
				<option <%= (fromId == 0) ? "selected" : "" %> value="0">Liter</option>
				<option <%= (fromId == 1) ? "selected" : "" %> value="1">Cubic Centimeter</option>
				<option <%= (fromId == 2) ? "selected" : "" %> value="2">Cubic Inch (Liquid Measure)</option>
				<option <%= (fromId == 3) ? "selected" : "" %> value="3">Pint (Dry Measure)</option>
				<option <%= (fromId == 4) ? "selected" : "" %> value="4">Cor (Homer)</option>
				<option <%= (fromId == 5) ? "selected" : "" %> value="5">Lethek</option>
				<option <%= (fromId == 6) ? "selected" : "" %> value="6">Ephah</option>
				<option <%= (fromId == 7) ? "selected" : "" %> value="7">Seah</option>
				<option <%= (fromId == 8) ? "selected" : "" %> value="8">Omer</option>
				<option <%= (fromId == 9) ? "selected" : "" %> value="9">Cab</option>
				<option <%= (fromId == 10) ? "selected" : "" %> value="10">Bath</option>
				<option <%= (fromId == 11) ? "selected" : "" %> value="11">Hin</option>
				<option <%= (fromId == 12) ? "selected" : "" %> value="12">Log</option>
			</c:if>
			<c:if test="<%= type == 3 %>">
				<option <%= (fromId == 0) ? "selected" : "" %> value="0"><liferay-ui:message key="kilogram" /></option>
				<option <%= (fromId == 1) ? "selected" : "" %> value="1"><liferay-ui:message key="pound" /></option>
				<option <%= (fromId == 2) ? "selected" : "" %> value="2"><liferay-ui:message key="ton" /></option>
				<option <%= (fromId == 3) ? "selected" : "" %> value="3"><liferay-ui:message key="talent" /></option>
				<option <%= (fromId == 4) ? "selected" : "" %> value="4"><liferay-ui:message key="mina" /></option>
				<option <%= (fromId == 5) ? "selected" : "" %> value="5"><liferay-ui:message key="shekel" /></option>
				<option <%= (fromId == 6) ? "selected" : "" %> value="6"><liferay-ui:message key="pim" /></option>
				<option <%= (fromId == 7) ? "selected" : "" %> value="7"><liferay-ui:message key="beka" /></option>
				<option <%= (fromId == 8) ? "selected" : "" %> value="8"><liferay-ui:message key="gerah" /></option>
			</c:if>
			<c:if test="<%= type == 4 %>">
				<option <%= (fromId == 0) ? "selected" : "" %> value="0">Kelvin</option>
				<option <%= (fromId == 1) ? "selected" : "" %> value="1">Celcius</option>
				<option <%= (fromId == 2) ? "selected" : "" %> value="2">Fahrenheit</option>
				<option <%= (fromId == 3) ? "selected" : "" %> value="3">Rankine</option>
				<option <%= (fromId == 4) ? "selected" : "" %> value="4">Réaumure</option>
			</c:if>
		</select>
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="to" />
	</td>
	<td>
		<input name="<portlet:namespace />to_value" size="30" type="text" value="<%= conversion.getToValue() %>" />

		<select name="<portlet:namespace />toId">
			<c:if test="<%= type == 0 %>">
				<option <%= (toId == 0) ? "selected" : "" %> value="0"><liferay-ui:message key="meter" /></option>
				<option <%= (toId == 1) ? "selected" : "" %> value="1"><liferay-ui:message key="millimeter" /></option>
				<option <%= (toId == 2) ? "selected" : "" %> value="2"><liferay-ui:message key="centimeter" /></option>
				<option <%= (toId == 3) ? "selected" : "" %> value="3"><liferay-ui:message key="kilometer" /></option>
				<option <%= (toId == 4) ? "selected" : "" %> value="4"><liferay-ui:message key="foot" /></option>
				<option <%= (toId == 5) ? "selected" : "" %> value="5"><liferay-ui:message key="inch" /></option>
				<option <%= (toId == 6) ? "selected" : "" %> value="6"><liferay-ui:message key="yard" /></option>
				<option <%= (toId == 7) ? "selected" : "" %> value="7"><liferay-ui:message key="mile" /></option>
				<option <%= (toId == 8) ? "selected" : "" %> value="8"><liferay-ui:message key="cubit" /></option>
				<option <%= (toId == 9) ? "selected" : "" %> value="9"><liferay-ui:message key="talent" /></option>
				<option <%= (toId == 10) ? "selected" : "" %> value="10"><liferay-ui:message key="handbreath" /></option>
			</c:if>
			<c:if test="<%= type == 1 %>">
				<option <%= (toId == 0) ? "selected" : "" %> value="0"><liferay-ui:message key="square-kilometer" /></option>
				<option <%= (toId == 1) ? "selected" : "" %> value="1"><liferay-ui:message key="square-meter" /></option>
				<option <%= (toId == 2) ? "selected" : "" %> value="2"><liferay-ui:message key="square-centimeter" /></option>
				<option <%= (toId == 3) ? "selected" : "" %> value="3"><liferay-ui:message key="square-millimeter" /></option>
				<option <%= (toId == 4) ? "selected" : "" %> value="4"><liferay-ui:message key="square-foot" /></option>
				<option <%= (toId == 5) ? "selected" : "" %> value="5"><liferay-ui:message key="square-inch" /></option>
				<option <%= (toId == 6) ? "selected" : "" %> value="6"><liferay-ui:message key="square-yard" /></option>
				<option <%= (toId == 7) ? "selected" : "" %> value="7"><liferay-ui:message key="square-mile" /></option>
				<option <%= (toId == 8) ? "selected" : "" %> value="8"><liferay-ui:message key="hectare" /></option>
				<option <%= (toId == 9) ? "selected" : "" %> value="9"><liferay-ui:message key="acre" /></option>
			</c:if>
			<c:if test="<%= type == 2 %>">
				<option <%= (toId == 0) ? "selected" : "" %> value="0">Liter</option>
				<option <%= (toId == 1) ? "selected" : "" %> value="1">Cubic Centimeter</option>
				<option <%= (toId == 2) ? "selected" : "" %> value="2">Cubic Inch (Liquid Measure)</option>
				<option <%= (toId == 3) ? "selected" : "" %> value="3">Pint (Dry Measure)</option>
				<option <%= (toId == 4) ? "selected" : "" %> value="4">Cor (Homer)</option>
				<option <%= (toId == 5) ? "selected" : "" %> value="5">Lethek</option>
				<option <%= (toId == 6) ? "selected" : "" %> value="6">Ephah</option>
				<option <%= (toId == 7) ? "selected" : "" %> value="7">Seah</option>
				<option <%= (toId == 8) ? "selected" : "" %> value="8">Omer</option>
				<option <%= (toId == 9) ? "selected" : "" %> value="9">Cab</option>
				<option <%= (toId == 10) ? "selected" : "" %> value="10">Bath</option>
				<option <%= (toId == 11) ? "selected" : "" %> value="11">Hin</option>
				<option <%= (toId == 12) ? "selected" : "" %> value="12">Log</option>
			</c:if>
			<c:if test="<%= type == 3 %>">
				<option <%= (toId == 0) ? "selected" : "" %> value="0"><liferay-ui:message key="kilogram" /></option>
				<option <%= (toId == 1) ? "selected" : "" %> value="1"><liferay-ui:message key="pound" /></option>
				<option <%= (toId == 2) ? "selected" : "" %> value="2"><liferay-ui:message key="ton" /></option>
				<option <%= (toId == 3) ? "selected" : "" %> value="3"><liferay-ui:message key="talent" /></option>
				<option <%= (toId == 4) ? "selected" : "" %> value="4"><liferay-ui:message key="mina" /></option>
				<option <%= (toId == 5) ? "selected" : "" %> value="5"><liferay-ui:message key="shekel" /></option>
				<option <%= (toId == 6) ? "selected" : "" %> value="6"><liferay-ui:message key="pim" /></option>
				<option <%= (toId == 7) ? "selected" : "" %> value="7"><liferay-ui:message key="beka" /></option>
				<option <%= (toId == 8) ? "selected" : "" %> value="8"><liferay-ui:message key="gerah" /></option>
			</c:if>
			<c:if test="<%= type == 4 %>">
				<option <%= (toId == 0) ? "selected" : "" %> value="0">Kelvin</option>
				<option <%= (toId == 1) ? "selected" : "" %> value="1">Celcius</option>
				<option <%= (toId == 2) ? "selected" : "" %> value="2">Fahrenheit</option>
				<option <%= (toId == 3) ? "selected" : "" %> value="3">Rankine</option>
				<option <%= (toId == 4) ? "selected" : "" %> value="4">Réaumure</option>
			</c:if>
		</select>
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="type" />
	</td>
	<td>
		<select name="<portlet:namespace />type"
			onChange="
				if (this[this.selectedIndex].value == 0) {
					Liferay.Util.setBox(document.<portlet:namespace />fm.<portlet:namespace />fromId, lengthArray);
					Liferay.Util.setBox(document.<portlet:namespace />fm.<portlet:namespace />toId, lengthArray);
				}
				else if (this[this.selectedIndex].value == 1) {
					Liferay.Util.setBox(document.<portlet:namespace />fm.<portlet:namespace />fromId, areaArray);
					Liferay.Util.setBox(document.<portlet:namespace />fm.<portlet:namespace />toId, areaArray);
				}
				else if (this[this.selectedIndex].value == 2) {
					Liferay.Util.setBox(document.<portlet:namespace />fm.<portlet:namespace />fromId, volumeArray);
					Liferay.Util.setBox(document.<portlet:namespace />fm.<portlet:namespace />toId, volumeArray);
				}
				else if (this[this.selectedIndex].value == 3) {
					Liferay.Util.setBox(document.<portlet:namespace />fm.<portlet:namespace />fromId, massArray);
					Liferay.Util.setBox(document.<portlet:namespace />fm.<portlet:namespace />toId, massArray);
				}
				else if (this[this.selectedIndex].value == 4) {
					Liferay.Util.setBox(document.<portlet:namespace />fm.<portlet:namespace />fromId, temperatureArray);
					Liferay.Util.setBox(document.<portlet:namespace />fm.<portlet:namespace />toId, temperatureArray);
				}"
		>
			<option <%= (type == 0) ? "selected" : "" %> value="0"><liferay-ui:message key="length" /></option>
			<option <%= (type == 1) ? "selected" : "" %> value="1"><liferay-ui:message key="area" /></option>
			<option <%= (type == 2) ? "selected" : "" %> value="2"><liferay-ui:message key="volume" /></option>
			<option <%= (type == 3) ? "selected" : "" %> value="3"><liferay-ui:message key="mass" /></option>
			<option <%= (type == 4) ? "selected" : "" %> value="4"><liferay-ui:message key="temperature" /></option>
		</select>
	</td>
</tr>
</table>

<br />

<input type="submit" value="<liferay-ui:message key="convert" />" />

</form>

<aui:script>
	var lengthArray = [
		new Option(0, '<%= UnicodeLanguageUtil.get(request, "meter") %>'),
		new Option(1, '<%= UnicodeLanguageUtil.get(request, "millimeter") %>'),
		new Option(2, '<%= UnicodeLanguageUtil.get(request, "centimeter") %>'),
		new Option(3, '<%= UnicodeLanguageUtil.get(request, "kilometer") %>'),
		new Option(4, '<%= UnicodeLanguageUtil.get(request, "foot") %>'),
		new Option(5, '<%= UnicodeLanguageUtil.get(request, "inch") %>'),
		new Option(6, '<%= UnicodeLanguageUtil.get(request, "yard") %>'),
		new Option(7, '<%= UnicodeLanguageUtil.get(request, "mile") %>'),
		new Option(8, '<%= UnicodeLanguageUtil.get(request, "cubit") %>'),
		new Option(9, '<%= UnicodeLanguageUtil.get(request, "talent") %>'),
		new Option(10, '<%= UnicodeLanguageUtil.get(request, "handbreath") %>')
	];

	var areaArray = [
		new Option(0, '<%= UnicodeLanguageUtil.get(request, "square-kilometer") %>'),
		new Option(1, '<%= UnicodeLanguageUtil.get(request, "square-meter") %>'),
		new Option(2, '<%= UnicodeLanguageUtil.get(request, "square-centimeter") %>'),
		new Option(3, '<%= UnicodeLanguageUtil.get(request, "square-millimeter") %>'),
		new Option(4, '<%= UnicodeLanguageUtil.get(request, "square-foot") %>'),
		new Option(5, '<%= UnicodeLanguageUtil.get(request, "square-inch") %>'),
		new Option(6, '<%= UnicodeLanguageUtil.get(request, "square-yard") %>'),
		new Option(7, '<%= UnicodeLanguageUtil.get(request, "square-mile") %>'),
		new Option(8, '<%= UnicodeLanguageUtil.get(request, "hectare") %>'),
		new Option(9, '<%= UnicodeLanguageUtil.get(request, "acre") %>')
	];

	var volumeArray = [
		new Option(0, 'Liter'),
		new Option(1, 'Cubic Centimeter'),
		new Option(2, 'Cubic Inch (Liquid Measure)'),
		new Option(3, 'Pint (Dry Measure)'),
		new Option(4, 'Cor (Homer)'),
		new Option(5, 'Lethek'),
		new Option(6, 'Ephah'),
		new Option(7, 'Seah'),
		new Option(8, 'Omer'),
		new Option(9, 'Cab'),
		new Option(10, 'Bath'),
		new Option(11, 'Hin'),
		new Option(12, 'Log')
	];

	var massArray = [
		new Option(0, '<%= UnicodeLanguageUtil.get(request, "kilogram") %>'),
		new Option(1, '<%= UnicodeLanguageUtil.get(request, "pound") %>'),
		new Option(2, '<%= UnicodeLanguageUtil.get(request, "ton") %>'),
		new Option(3, '<%= UnicodeLanguageUtil.get(request, "talent") %>'),
		new Option(4, '<%= UnicodeLanguageUtil.get(request, "mina") %>'),
		new Option(5, '<%= UnicodeLanguageUtil.get(request, "shekel") %>'),
		new Option(6, '<%= UnicodeLanguageUtil.get(request, "pim") %>'),
		new Option(7, '<%= UnicodeLanguageUtil.get(request, "beka") %>'),
		new Option(8, '<%= UnicodeLanguageUtil.get(request, "gerah") %>')
	];

	var temperatureArray = [
		new Option(0, 'Kelvin'),
		new Option(1, 'Celcius'),
		new Option(2, 'Fahrenheit'),
		new Option(3, 'Rankine'),
		new Option(4, 'Réaumure')
	];

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />fromValue);
	</c:if>
</aui:script>

<aui:script use="aui-io-request,aui-parse-content">
	var form = A.one('#<portlet:namespace />fm');

	form.on(
		'submit',
		function(event) {
			var uri = form.getAttribute('action');
			var parentNode = form.get('parentNode');

			parentNode.plug(A.Plugin.ParseContent);

			A.io.request(
				uri,
				{
					form: {
						id: form
					},
					on: {
						success: function(event, id, obj) {
							var responseData = this.get('responseData');

							parentNode.setContent(responseData);
						}
					}
				}
			);

			event.halt();
		}
	);
</aui:script>