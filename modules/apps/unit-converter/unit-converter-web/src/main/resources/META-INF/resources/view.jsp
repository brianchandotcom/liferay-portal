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
int type = ParamUtil.getInteger(request, "type");
int fromId = ParamUtil.getInteger(request, "fromUnit");
int toId = ParamUtil.getInteger(request, "toUnit");
double fromValue = ParamUtil.getDouble(request, "fromValue");

UnitConverter unitConverter = UnitConverterUtil.getUnitConverter(type, fromId, toId, fromValue);
%>

<portlet:renderURL var="unitURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>" />

<aui:form action="<%= unitURL %>" id="fm" method="post" name="fm">
	<aui:row>
		<aui:col xs="6">
			<aui:input label="from" name="fromValue" size="30" type="number" value="<%= unitConverter.getFromValue() %>" />

			<aui:select label="" name="fromUnit">
				<c:if test="<%= type == 0 %>">
					<aui:option label="meter" selected="<%= (fromId == 0) %>" value="0" />
					<aui:option label="millimeter" selected="<%= (fromId == 1) %>" value="1" />
					<aui:option label="centimeter" selected="<%= (fromId == 2) %>" value="2" />
					<aui:option label="kilometer" selected="<%= (fromId == 3) %>" value="3" />
					<aui:option label="foot" selected="<%= (fromId == 4) %>" value="4" />
					<aui:option label="inch" selected="<%= (fromId == 5) %>" value="5" />
					<aui:option label="yard" selected="<%= (fromId == 6) %>" value="6" />
					<aui:option label="mile" selected="<%= (fromId == 7) %>" value="7" />
					<aui:option label="cubit" selected="<%= (fromId == 8) %>" value="8" />
					<aui:option label="talent" selected="<%= (fromId == 9) %>" value="9" />
					<aui:option label="handbreath" selected="<%= (fromId == 10) %>" value="10" />
				</c:if>

				<c:if test="<%= type == 1 %>">
					<aui:option label="square-kilometer" selected="<%= (fromId == 0) %>" value="0" />
					<aui:option label="square-meter" selected="<%= (fromId == 1) %>" value="1" />
					<aui:option label="square-centimeter" selected="<%= (fromId == 2) %>" value="2" />
					<aui:option label="square-millimeter" selected="<%= (fromId == 3) %>" value="3" />
					<aui:option label="square-foot" selected="<%= (fromId == 4) %>" value="4" />
					<aui:option label="square-inch" selected="<%= (fromId == 5) %>" value="5" />
					<aui:option label="square-yard" selected="<%= (fromId == 6) %>" value="6" />
					<aui:option label="square-mile" selected="<%= (fromId == 7) %>" value="7" />
					<aui:option label="hectare" selected="<%= (fromId == 8) %>" value="8" />
					<aui:option label="acre" selected="<%= (fromId == 9) %>" value="9" />
				</c:if>

				<c:if test="<%= type == 2 %>">
					<aui:option label="liter" selected="<%= (fromId == 0) %>" value="0" />
					<aui:option label="cubic-centimeter" selected="<%= (fromId == 1) %>" value="1" />
					<aui:option label="cubic-inch" selected="<%= (fromId == 2) %>" value="2" />
					<aui:option label="pint" selected="<%= (fromId == 3) %>" value="3" />
					<aui:option label="cor" selected="<%= (fromId == 4) %>" value="4" />
					<aui:option label="lethek" selected="<%= (fromId == 5) %>" value="5" />
					<aui:option label="ephah" selected="<%= (fromId == 6) %>" value="6" />
					<aui:option label="seah" selected="<%= (fromId == 7) %>" value="7" />
					<aui:option label="omer" selected="<%= (fromId == 8) %>" value="8" />
					<aui:option label="cab" selected="<%= (fromId == 9) %>" value="9" />
					<aui:option label="bath" selected="<%= (fromId == 10) %>" value="10" />
					<aui:option label="hin" selected="<%= (fromId == 11) %>" value="11" />
					<aui:option label="log" selected="<%= (fromId == 12) %>" value="12" />
				</c:if>

				<c:if test="<%= type == 3 %>">
					<aui:option label="kilogram" selected="<%= (fromId == 0) %>" value="0" />
					<aui:option label="pound" selected="<%= (fromId == 1) %>" value="1" />
					<aui:option label="ton" selected="<%= (fromId == 2) %>" value="2" />
					<aui:option label="talent" selected="<%= (fromId == 3) %>" value="3" />
					<aui:option label="mina" selected="<%= (fromId == 4) %>" value="4" />
					<aui:option label="shekel" selected="<%= (fromId == 5) %>" value="5" />
					<aui:option label="pim" selected="<%= (fromId == 6) %>" value="6" />
					<aui:option label="beka" selected="<%= (fromId == 7) %>" value="7" />
					<aui:option label="gerah" selected="<%= (fromId == 8) %>" value="8" />
				</c:if>

				<c:if test="<%= type == 4 %>">
					<aui:option label="kelvin" selected="<%= (fromId == 0) %>" value="0" />
					<aui:option label="celsius" selected="<%= (fromId == 1) %>" value="1" />
					<aui:option label="fahrenheit" selected="<%= (fromId == 2) %>" value="2" />
					<aui:option label="rankine" selected="<%= (fromId == 3) %>" value="3" />
					<aui:option label="reaumure" selected="<%= (fromId == 4) %>" value="4" />
				</c:if>
			</aui:select>
		</aui:col>

		<aui:col xs="6">
			<aui:input disabled="<%= true %>" label="To" name="toValue" size="30" type="input" value="<%= unitConverter.getToValue() %>" />

			<aui:select label="" name="toUnit">
				<c:if test="<%= type == 0 %>">
					<aui:option label="meter" selected="<%= (toId == 0) %>" value="0" />
					<aui:option label="millimeter" selected="<%= (toId == 1) %>" value="1" />
					<aui:option label="centimeter" selected="<%= (toId == 2) %>" value="2" />
					<aui:option label="kilometer" selected="<%= (toId == 3) %>" value="3" />
					<aui:option label="foot" selected="<%= (toId == 4) %>" value="4" />
					<aui:option label="inch" selected="<%= (toId == 5) %>" value="5" />
					<aui:option label="yard" selected="<%= (toId == 6) %>" value="6" />
					<aui:option label="mile" selected="<%= (toId == 7) %>" value="7" />
					<aui:option label="cubit" selected="<%= (toId == 8) %>" value="8" />
					<aui:option label="talent" selected="<%= (toId == 9) %>" value="9" />
					<aui:option label="handbreath" selected="<%= (toId == 10) %>" value="10" />
				</c:if>

				<c:if test="<%= type == 1 %>">
					<aui:option label="square-kilometer" selected="<%= (toId == 0) %>" value="0" />
					<aui:option label="square-meter" selected="<%= (toId == 1) %>" value="1" />
					<aui:option label="square-centimeter" selected="<%= (toId == 2) %>" value="2" />
					<aui:option label="square-millimeter" selected="<%= (toId == 3) %>" value="3" />
					<aui:option label="square-foot" selected="<%= (toId == 4) %>" value="4" />
					<aui:option label="square-inch" selected="<%= (toId == 5) %>" value="5" />
					<aui:option label="square-yard" selected="<%= (toId == 6) %>" value="6" />
					<aui:option label="square-mile" selected="<%= (toId == 7) %>" value="7" />
					<aui:option label="hectare" selected="<%= (toId == 8) %>" value="8" />
					<aui:option label="acre" selected="<%= (toId == 9) %>" value="9" />
				</c:if>

				<c:if test="<%= type == 2 %>">
					<aui:option label="liter" selected="<%= (toId == 0) %>" value="0" />
					<aui:option label="cubic-centimeter" selected="<%= (toId == 1) %>" value="1" />
					<aui:option label="cubic-inch" selected="<%= (toId == 2) %>" value="2" />
					<aui:option label="pint" selected="<%= (toId == 3) %>" value="3" />
					<aui:option label="cor" selected="<%= (toId == 4) %>" value="4" />
					<aui:option label="lethek" selected="<%= (toId == 5) %>" value="5" />
					<aui:option label="ephah" selected="<%= (toId == 6) %>" value="6" />
					<aui:option label="seah" selected="<%= (toId == 7) %>" value="7" />
					<aui:option label="omer" selected="<%= (toId == 8) %>" value="8" />
					<aui:option label="cab" selected="<%= (toId == 9) %>" value="9" />
					<aui:option label="bath" selected="<%= (toId == 10) %>" value="10" />
					<aui:option label="hin" selected="<%= (toId == 11) %>" value="11" />
					<aui:option label="log" selected="<%= (toId == 12) %>" value="12" />
				</c:if>

				<c:if test="<%= type == 3 %>">
					<aui:option label="kilogram" selected="<%= (toId == 0) %>" value="0" />
					<aui:option label="pound" selected="<%= (toId == 1) %>" value="1" />
					<aui:option label="ton" selected="<%= (toId == 2) %>" value="2" />
					<aui:option label="talent" selected="<%= (toId == 3) %>" value="3" />
					<aui:option label="mina" selected="<%= (toId == 4) %>" value="4" />
					<aui:option label="shekel" selected="<%= (toId == 5) %>" value="5" />
					<aui:option label="pim" selected="<%= (toId == 6) %>" value="6" />
					<aui:option label="beka" selected="<%= (toId == 7) %>" value="7" />
					<aui:option label="gerah" selected="<%= (toId == 8) %>" value="8" />
				</c:if>

				<c:if test="<%= type == 4 %>">
					<aui:option label="kelvin" selected="<%= (toId == 0) %>" value="0" />
					<aui:option label="celsius" selected="<%= (toId == 1) %>" value="1" />
					<aui:option label="fahrenheit" selected="<%= (toId == 2) %>" value="2" />
					<aui:option label="rankine" selected="<%= (toId == 3) %>" value="3" />
					<aui:option label="reaumure" selected="<%= (toId == 4) %>" value="4" />
				</c:if>
			</aui:select>
		</aui:col>
	</aui:row>

	<aui:select id="type" label="Type" name="type">
		<aui:option label="length" selected="<%= (type == 0) %>" value="0" />
		<aui:option label="area" selected="<%= (type == 1) %>" value="1" />
		<aui:option label="volume" selected="<%= (type == 2) %>" value="2" />
		<aui:option label="mass" selected="<%= (type == 3) %>" value="3" />
		<aui:option label="temperature" selected="<%= (type == 4) %>" value="4" />
	</aui:select>

	<aui:button id="convertButton" type="submit" value="convert" />
</aui:form>

<aui:script sandbox="<%= true %>">
	var lengthArray = [
		'<liferay-ui:message key="meter" />',
		'<liferay-ui:message key="millimeter" />',
		'<liferay-ui:message key="centimeter" />',
		'<liferay-ui:message key="kilometer" />',
		'<liferay-ui:message key="foot" />',
		'<liferay-ui:message key="inch" />',
		'<liferay-ui:message key="yard" />',
		'<liferay-ui:message key="mile" />',
		'<liferay-ui:message key="cubit" />',
		'<liferay-ui:message key="talent" />',
		'<liferay-ui:message key="handbreath" />'
	];

	var areaArray = [
		'<liferay-ui:message key="square-kilometer" />',
		'<liferay-ui:message key="square-meter" />',
		'<liferay-ui:message key="square-centimeter" />',
		'<liferay-ui:message key="square-millimeter" />',
		'<liferay-ui:message key="square-foot" />',
		'<liferay-ui:message key="square-inch" />',
		'<liferay-ui:message key="square-yard" />',
		'<liferay-ui:message key="square-mile" />',
		'<liferay-ui:message key="hectare" />',
		'<liferay-ui:message key="acre" />'
	];

	var volumeArray = [
		'<liferay-ui:message key="liter" />',
		'<liferay-ui:message key="cubic-centimeter" />',
		'<liferay-ui:message key="cubic-inch" />',
		'<liferay-ui:message key="pint" />',
		'<liferay-ui:message key="cor" />',
		'<liferay-ui:message key="lethek" />',
		'<liferay-ui:message key="ephah" />',
		'<liferay-ui:message key="seah" />',
		'<liferay-ui:message key="omer" />',
		'<liferay-ui:message key="cab" />',
		'<liferay-ui:message key="bath" />',
		'<liferay-ui:message key="hin" />',
		'<liferay-ui:message key="log" />'
	];

	var massArray = [
		'<liferay-ui:message key="kilogram" />',
		'<liferay-ui:message key="pound" />',
		'<liferay-ui:message key="ton" />',
		'<liferay-ui:message key="talent" />',
		'<liferay-ui:message key="mina" />',
		'<liferay-ui:message key="shekel" />',
		'<liferay-ui:message key="pim" />',
		'<liferay-ui:message key="beka" />',
		'<liferay-ui:message key="gerah" />'
	];

	var temperatureArray = [
		'<liferay-ui:message key="kelvin" />',
		'<liferay-ui:message key="celsius" />',
		'<liferay-ui:message key="fahrenheit" />',
		'<liferay-ui:message key="rankine" />',
		'<liferay-ui:message key="reaumure" />'
	];

	var unitConverterTypes = [lengthArray, areaArray, volumeArray, massArray, temperatureArray];

	var setBox = function(oldBox, newBox) {
		oldBox.empty();

		var options = _.map(
			newBox,
			function(item, index) {
				return '<option value="' + index + '">' + item + '</option>';
			}
		);

		oldBox.append(options.join(''));
	};

	var form = $(document.<portlet:namespace />fm);

	var fromUnit = form.fm('fromUnit');
	var toUnit = form.fm('toUnit');

	var typeSelect = form.fm('type');

	typeSelect.on(
		'change',
		function(event) {
			var value = typeSelect.val();

			var unitConverterType = unitConverterTypes[value];

			if (unitConverterType) {
				setBox(fromUnit, unitConverterType);
				setBox(toUnit, unitConverterType);
			}
		}
	);

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		Liferay.Util.focusFormField(form.fm('fromValue'));
	</c:if>

	$('#<portlet:namespace />convertButton').on(
		'click',
		function(event) {
			event.preventDefault();

			form.ajaxSubmit(
				{
					success: function(responseData) {
						form.replaceWith(responseData);
					}
				}
			);
		}
	);
</aui:script>