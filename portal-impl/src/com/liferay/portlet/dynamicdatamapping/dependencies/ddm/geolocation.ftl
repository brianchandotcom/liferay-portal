<#include "../init.ftl">

<#assign latitude = "">
<#assign longitude = "">

<#if (fieldRawValue != "")>
	<#assign geolocationJSONObject = jsonFactoryUtil.createJSONObject(fieldRawValue)>

	<#assign latitude = geolocationJSONObject.getDouble("latitude")>
	<#assign longitude = geolocationJSONObject.getDouble("longitude")>
</#if>

<@aui["field-wrapper"] cssClass="geolocation-field" data=data label=label required=required>
	<@aui.input name=namespacedFieldName type="hidden" value=fieldRawValue />

	<@aui["button-row"]>
		<@aui.button onClick="window['${portletNamespace}${namespacedFieldName}SetGeolocation']();" value="geolocate" />
	</@>

	<p>
		<strong><@liferay_ui.message key="location" />:</strong>

		<span id="${portletNamespace}${namespacedFieldName}Coordinates">
			<#if (fieldRawValue != "")>
				${latitude}, ${longitude}
			<#else>
				-
			</#if>
		</span>
	</p>

	${fieldStructure.children}
</@>

<@aui.script>
	Liferay.provide(
		window,
		'${portletNamespace}${namespacedFieldName}SetGeolocation',
		function(position) {
			var A = AUI();

			var coordinatesNode = A.one('#${portletNamespace}${namespacedFieldName}Coordinates');

			coordinatesNode.html('<@liferay_ui.message key="loading" />');

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

					coordinatesNode.html([latitude, longitude].join(', '));
				}
			);
		},
		['aui-base', 'json']
	);
</@>