<#include "init.ftl">

<#if language == "ftl">
	${r"<#assign"} liferay_map = PortalJspTagLibs["/META-INF/resources/liferay-map.tld"] />

	${r"<#assign"} latitude = 0>
	${r"<#assign"} longitude = 0>

	${r"<#if"} (${variableName} != "")>
		${r"<#assign"} geolocationJSONObject = jsonFactoryUtil.createJSONObject(${variableName})>

		${r"<#assign"} latitude = geolocationJSONObject.getDouble("latitude")>
		${r"<#assign"} longitude = geolocationJSONObject.getDouble("longitude")>

		${r"<@liferay_map"}["map_display"]
			geolocation=true
			latitude=latitude
			longitude=longitude
			name="${name}"
		/>
	${r"</#if>"}
<#else>
	${getVariableReferenceCode(variableName)}
</#if>