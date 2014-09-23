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
		<@aui.button cssClass="geolocate-button" value="geolocate" />
	</@>

	<p class="${coordinatesContainerCssClass}" id="${portletNamespace}${namespacedFieldName}CoordinatesContainer">
		<strong><@liferay_ui.message key="location" />:</strong>

		<span id="${portletNamespace}${namespacedFieldName}Coordinates">
		    <@fmt.formatNumber value=latitude type="NUMBER" />, <@fmt.formatNumber value=longitude type="NUMBER" />
		</span>
	</p>

	${fieldStructure.children}
</@>