<#include "../init.ftl">

<#assign latitude = "">
<#assign longitude = "">

<#if (fieldRawValue != "")>
	<#assign geolocationJSONObject = jsonFactoryUtil.createJSONObject(fieldRawValue)>

	<#assign latitude = geolocationJSONObject.getDouble("latitude")>
	<#assign longitude = geolocationJSONObject.getDouble("longitude")>
</#if>

<@aui["field-wrapper"] data=data>
	<@aui.button onClick="window['${portletNamespace}${namespacedFieldName}geolocation']();" value="geolocate" />

	<@aui.input inlineField=true label='${languageUtil.get(locale, "latitude")}' name="${namespacedFieldName}Latitude" readonly="readonly" type="text" value="${latitude}">
		<#if required>
			<@aui.validator name="required" />
		</#if>
	</@aui.input>

	<@aui.input inlineField=true label='${languageUtil.get(locale, "longitude")}' name="${namespacedFieldName}Longitude" readonly="readonly" type="text" value="${longitude}">
		<#if required>
			<@aui.validator name="required" />
		</#if>
	</@aui.input>

	<@aui.input name=namespacedFieldName type="hidden" value=fieldRawValue />

	${fieldStructure.children}
</@>

<@aui.script>
	window['${portletNamespace}${namespacedFieldName}geolocation'] = function() {
		if (navigator.geolocation) {
			navigator.geolocation.getCurrentPosition(window['${portletNamespace}${namespacedFieldName}setGeolocation']);
		}
	};

	Liferay.provide(
		window,
		'${portletNamespace}${namespacedFieldName}setGeolocation',
		function(position) {
			var A = AUI();

			var latitude = position.coords.latitude;
			var longitude = position.coords.longitude;

			var inputNode = A.one('#${portletNamespace}${namespacedFieldName}');

			inputNode.val(
				A.JSON.stringify(
					{
						latitude: latitude,
						longitude: longitude
					}
				)
			);

			var latitudeNode = A.one('#${portletNamespace}${namespacedFieldName}Latitude');

			latitudeNode.val(latitude);

			var longitudeNode = A.one('#${portletNamespace}${namespacedFieldName}Longitude');

			longitudeNode.val(longitude);
		},
		['json']
	);
</@>