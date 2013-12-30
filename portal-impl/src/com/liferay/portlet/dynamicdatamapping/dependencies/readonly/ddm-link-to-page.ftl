<#include "../init.ftl">

<#if (fieldRawValue?? && fieldRawValue != "")>
	<#assign fieldLayoutJSONObject = jsonFactoryUtil.createJSONObject(fieldRawValue)>

	<#assign layoutLocalService = serviceLocator.findService("com.liferay.portal.service.LayoutLocalService")>

	<#if (fieldLayoutJSONObject.getLong("groupId") > 0)>
		<#assign fieldLayoutGroupId = fieldLayoutJSONObject.getLong("groupId")>
	<#else>
		<#assign fieldLayoutGroupId = scopeGroupId>
	</#if>

	<#assign fieldLayout = layoutLocalService.fetchLayout(fieldLayoutGroupId, fieldLayoutJSONObject.getBoolean("privateLayout"), fieldLayoutJSONObject.getLong("layoutId"))!"">

	<#if (fieldLayout?? && fieldLayout != "")>
		<@aui["field-wrapper"] label=escape(label)>
			<@aui.input name=namespacedFieldName type="hidden" value=fieldValue />

			<a href="${fieldLayout.getRegularURL(request)}">${escape(fieldLayout.getName(requestedLocale))}</a>

			${fieldStructure.children}
		</@>
	</#if>
</#if>