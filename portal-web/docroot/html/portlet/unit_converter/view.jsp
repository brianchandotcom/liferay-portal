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

<portlet:renderURL var="unitURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
	<portlet:param name="struts_action" value="/unit_converter/view" />
</portlet:renderURL>

<aui:form action="<%= unitURL %>" id="fm" method="post" name="fm">
	<aui:row>
		<aui:col xs="6">
			<aui:input label="from" name="fromValue" size="30" type="number" value="<%= conversion.getFromValue() %>" />

			<aui:select label="" name="fromId">
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
					<aui:option label="Liter" selected="<%= (fromId == 0) %>" value="0" />
					<aui:option label="Cubic Centimeter" selected="<%= (fromId == 1) %>" value="1" />
					<aui:option label="Cubic Inch (Liquid Measure)" selected="<%= (fromId == 2) %>" value="2" />
					<aui:option label="Pint (Dry Measure)" selected="<%= (fromId == 3) %>" value="3" />
					<aui:option label="Cor (Homer)" selected="<%= (fromId == 4) %>" value="4" />
					<aui:option label="Lethek" selected="<%= (fromId == 5) %>" value="5" />
					<aui:option label="Ephah" selected="<%= (fromId == 6) %>" value="6" />
					<aui:option label="Seah" selected="<%= (fromId == 7) %>" value="7" />
					<aui:option label="Omer" selected="<%= (fromId == 8) %>" value="8" />
					<aui:option label="Cab" selected="<%= (fromId == 9) %>" value="9" />
					<aui:option label="Bath" selected="<%= (fromId == 10) %>" value="10" />
					<aui:option label="Hin" selected="<%= (fromId == 11) %>" value="11" />
					<aui:option label="Log" selected="<%= (fromId == 12) %>" value="12" />
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
					<aui:option label="Kelvin" selected="<%= (fromId == 0) %>" value="0" />
					<aui:option label="Celcius" selected="<%= (fromId == 1) %>" value="1" />
					<aui:option label="Fahrenheit" selected="<%= (fromId == 2) %>" value="2" />
					<aui:option label="Rankine" selected="<%= (fromId == 3) %>" value="3" />
					<aui:option label="Réaumure" selected="<%= (fromId == 4) %>" value="4" />
				</c:if>
			</aui:select>
		</aui:col>

		<aui:col xs="6">
			<aui:input label="To" name="to_value" size="30" type="number" value="<%= conversion.getToValue() %>" />

			<aui:select label="" name="toId">
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
					<aui:option label="Liter" selected="<%= (toId == 0) %>" value="0" />
					<aui:option label="Cubic Centimeter" selected="<%= (toId == 1) %>" value="1" />
					<aui:option label="Cubic Inch (Liquid Measure)" selected="<%= (toId == 2) %>" value="2" />
					<aui:option label="Pint (Dry Measure)" selected="<%= (toId == 3) %>" value="3" />
					<aui:option label="Cor (Homer)" selected="<%= (toId == 4) %>" value="4" />
					<aui:option label="Lethek" selected="<%= (toId == 5) %>" value="5" />
					<aui:option label="Ephah" selected="<%= (toId == 6) %>" value="6" />
					<aui:option label="Seah" selected="<%= (toId == 7) %>" value="7" />
					<aui:option label="Omer" selected="<%= (toId == 8) %>" value="8" />
					<aui:option label="Cab" selected="<%= (toId == 9) %>" value="9" />
					<aui:option label="Bath" selected="<%= (toId == 10) %>" value="10" />
					<aui:option label="Hin" selected="<%= (toId == 11) %>" value="11" />
					<aui:option label="Log" selected="<%= (toId == 12) %>" value="12" />
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
					<aui:option label="Kelvin" selected="<%= (toId == 0) %>" value="0" />
					<aui:option label="Celcius" selected="<%= (toId == 1) %>" value="1" />
					<aui:option label="Fahrenheit" selected="<%= (toId == 2) %>" value="2" />
					<aui:option label="Rankine" selected="<%= (toId == 3) %>" value="3" />
					<aui:option label="Réaumure" selected="<%= (toId == 4) %>" value="4" />
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

	<aui:button type="submit" value="convert" />
</aui:form>

<aui:script use="aui-node">
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

	var selectType = A.one('#<portlet:namespace />type');

	selectType.on(
		'change',
		function(event) {
			var selectTypeTarget = event.currentTarget;

			if (selectTypeTarget.get('value') == 0) {
				Liferay.Util.setBox(document.<portlet:namespace />fm.<portlet:namespace />fromId, lengthArray);
				Liferay.Util.setBox(document.<portlet:namespace />fm.<portlet:namespace />toId, lengthArray);
			}
			else if (selectTypeTarget.get('value') == 1) {
				Liferay.Util.setBox(document.<portlet:namespace />fm.<portlet:namespace />fromId, areaArray);
				Liferay.Util.setBox(document.<portlet:namespace />fm.<portlet:namespace />toId, areaArray);
			}
			else if (selectTypeTarget.get('value') == 2) {
				Liferay.Util.setBox(document.<portlet:namespace />fm.<portlet:namespace />fromId, volumeArray);
				Liferay.Util.setBox(document.<portlet:namespace />fm.<portlet:namespace />toId, volumeArray);
			}
			else if (selectTypeTarget.get('value') == 3) {
				Liferay.Util.setBox(document.<portlet:namespace />fm.<portlet:namespace />fromId, massArray);
				Liferay.Util.setBox(document.<portlet:namespace />fm.<portlet:namespace />toId, massArray);
			}
			else if (selectTypeTarget.get('value') == 4) {
				Liferay.Util.setBox(document.<portlet:namespace />fm.<portlet:namespace />fromId, temperatureArray);
				Liferay.Util.setBox(document.<portlet:namespace />fm.<portlet:namespace />toId, temperatureArray);
			}
		}
	);

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