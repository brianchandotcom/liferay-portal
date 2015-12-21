<#include "../init.ftl">

<#assign latitude = 0>
<#assign longitude = 0>

<#assign fieldRawValue = paramUtil.getString(request, "${namespacedFieldName}", fieldRawValue)>

<#if (fieldRawValue != "")>
	<#assign geolocationJSONObject = jsonFactoryUtil.createJSONObject(fieldRawValue)>

	<#assign latitude = geolocationJSONObject.getDouble("latitude")>
	<#assign longitude = geolocationJSONObject.getDouble("longitude")>
</#if>

<@aui["field-wrapper"] cssClass="geolocation-field" data=data label=label required=required>
	<div class="form-group">
		<@aui.input
			name=namespacedFieldName
			type="hidden"
			value=fieldRawValue
		/>

		<div id="${portletNamespace}${namespacedFieldName}CoordinatesContainer">
			<div class="glyphicon glyphicon-map-marker" id="${portletNamespace}${namespacedFieldName}Location"></div>

			<@liferay_map["map-display"]
				geolocation=true
				latitude=latitude
				longitude=longitude
				name=namespacedFieldName
			/>
		</div>
	</div>

	${fieldStructure.children}
</@>