<#include "../init.ftl">

<#assign latitude = "">
<#assign longitude = "">

<#assign coordinatesContainerCssClass = "hide">

<#assign fieldRawValue = paramUtil.getString(request, "${namespacedFieldName}", fieldRawValue)>

<#if (fieldRawValue != "")>
	<#assign geolocationJSONObject = jsonFactoryUtil.createJSONObject(fieldRawValue)>

	<#assign latitude = geolocationJSONObject.getDouble("latitude")>
	<#assign longitude = geolocationJSONObject.getDouble("longitude")>

	<#assign coordinatesContainerCssClass = "">
</#if>

<@aui["field-wrapper"] cssClass="geolocation-field" data=data label=label required=required>
	<@aui.input name=namespacedFieldName type="hidden" value=fieldRawValue />

	<@aui["button-row"]>
		<@aui.button onClick="window['${portletNamespace}${namespacedFieldName}SetGeolocation']();" value="geolocate" />
	</@>

	<p class="${coordinatesContainerCssClass}" id="${portletNamespace}${namespacedFieldName}CoordinatesContainer">
		<strong><@liferay_ui.message key="location" />:</strong>

		<span id="${portletNamespace}${namespacedFieldName}Coordinates">
		    <@fmt.formatNumber value=latitude type="NUMBER" />, <@fmt.formatNumber value=longitude type="NUMBER" />
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
			var coordinatesContainerNode = A.one('#${portletNamespace}${namespacedFieldName}CoordinatesContainer');

			coordinatesContainerNode.show();

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