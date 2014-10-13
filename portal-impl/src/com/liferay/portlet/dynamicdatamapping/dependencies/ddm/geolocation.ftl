<#include "../init.ftl">

<#assign latitude = 0>
<#assign longitude = 0>

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

	<div id="${portletNamespace}${namespacedFieldName}CoordinatesContainer" style="padding: 15px;">
		<div class="glyphicon glyphicon-map-marker" id="${portletNamespace}${namespacedFieldName}Location"></div>

		<@liferay_ui["map"] geolocation=true latitude=latitude longitude=longitude name=namespacedFieldName />
	</div>

	${fieldStructure.children}
</@>