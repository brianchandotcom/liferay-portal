<#include "init.ftl">

<#assign encodedName = name />

<#if !repeatable>
	<#assign encodedName = stringUtil.replace(name, ".", "_") />
</#if>

	${r"<#assign"} latitude = 0>
	${r"<#assign"} longitude = 0>

	${r"<#if"} (${variableName} != "")>
		${r"<#assign"} geolocationJSONObject = jsonFactoryUtil.createJSONObject(${variableName})>

		${r"<#assign"} latitude = geolocationJSONObject.getDouble("lat")>
		${r"<#assign"} longitude = geolocationJSONObject.getDouble("lng")>

		${r"<@liferay_map"}["map-display"]
			geolocation=true
			latitude=latitude
			longitude=longitude
			name="${encodedName}${r"${random.nextInt()}"}"
		/>
	${r"</#if>"}