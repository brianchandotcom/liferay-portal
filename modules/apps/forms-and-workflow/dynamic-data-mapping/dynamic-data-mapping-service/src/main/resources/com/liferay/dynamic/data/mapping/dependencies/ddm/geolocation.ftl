<#include "../init.ftl">

<#assign
	latitude = 0
	longitude = 0

	fieldRawValue = paramUtil.getString(request, "${namespacedFieldName}", fieldRawValue)
/>

<#if validator.isNotNull(fieldRawValue)>
	<#assign
		geolocationJSONObject = jsonFactoryUtil.createJSONObject(fieldRawValue)

		latitude = geolocationJSONObject.getDouble("latitude")
		longitude = geolocationJSONObject.getDouble("longitude")
	/>
</#if>

<@liferay_aui["field-wrapper"]
	cssClass="form-builder-field geolocation-field"
	data=data
	helpMessage=escape(fieldStructure.tip)
	label=label
	required=required
>
	<div class="form-group">
		<@liferay_aui.input
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