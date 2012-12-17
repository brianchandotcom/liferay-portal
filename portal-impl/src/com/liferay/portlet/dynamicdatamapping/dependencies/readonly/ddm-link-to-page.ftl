<#include "../init.ftl">

<#if (fieldRawValue?? && fieldRawValue != "")>
	<#assign fieldLayoutJSONObject = jsonFactoryUtil.createJSONObject(fieldRawValue)>

	<#assign layoutLocalService = serviceLocator.findService("com.liferay.portal.service.LayoutLocalService")>

	<#assign fieldLayout = layoutLocalService.fetchLayout(fieldLayoutJSONObject.getLong("groupId"), fieldLayoutJSONObject.getBoolean("privateLayout"), fieldLayoutJSONObject.getLong("layoutId"))!"">

	<#if (fieldLayout?? && fieldLayout != "")>
		<@aui["field-wrapper"] label=escape(label)>
			<@aui.input name=namespacedFieldName type="hidden" value=fieldValue />

			<a href="${fieldLayout.getRegularURL(request)}">${fieldLayout.getName(requestedLocale)}</a>

			${fieldStructure.children}
		</@>
	</#if>
</#if>