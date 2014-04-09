<#include "../init.ftl">

<#assign latitude = "">
<#assign longitude = "">

<#if (fieldRawValue != "")>
	<#assign geolocationJSONObject = jsonFactoryUtil.createJSONObject(fieldRawValue)>

	<#assign latitude = geolocationJSONObject.getDouble("latitude")>
	<#assign longitude = geolocationJSONObject.getDouble("longitude")>
</#if>

<@aui["field-wrapper"] cssClass="geolocation-field" data=data required=required>
	<@aui.input inlineField=true label='${languageUtil.get(locale, "latitude")}' name="${namespacedFieldName}Latitude" readonly="readonly" type="text" value="${latitude}" />

	<@aui.input inlineField=true label='${languageUtil.get(locale, "longitude")}' name="${namespacedFieldName}Longitude" readonly="readonly" type="text" value="${longitude}" />

	<@aui.input name=namespacedFieldName type="hidden" value=fieldRawValue />

	<@aui["button-row"]>
		<@aui.button onClick="window['${portletNamespace}${namespacedFieldName}SetGeolocation']();" value="geolocate" />
	</@>

	${fieldStructure.children}
</@>

<@aui.script>
	Liferay.provide(
		window,
		'${portletNamespace}${namespacedFieldName}SetGeolocation',
		function(position) {
			var A = AUI();

			var latitudeNode = A.one('#${portletNamespace}${namespacedFieldName}Latitude');

			latitudeNode.val('<@liferay_ui.message key="loading" />');

			var longitudeNode = A.one('#${portletNamespace}${namespacedFieldName}Longitude');

			longitudeNode.val('<@liferay_ui.message key="loading" />');

			Liferay.Util.getGeolocation(
				function(latitude, longitude) {
					var inputNode = A.one('#${portletNamespace}${namespacedFieldName}');

					inputNode.val(
						A.JSON.stringify(
							{
								latitude: latitude,
								longitude: longitude
							}
						)
					);

					latitudeNode.val(latitude);
					longitudeNode.val(longitude);
				}
			);
		},
		['aui-base', 'json']
	);
</@>