<#include "../init.ftl">

<#assign layoutLocalService = serviceLocator.findService("com.liferay.portal.service.LayoutLocalService")>

<#macro getLayoutsOptions groupId privateLayout parentLayoutId selectedPlid level=0>
	<#assign layouts = layoutLocalService.getLayouts(groupId, privateLayout, parentLayoutId)>

	<#if (layouts?size > 0)>
		<#if (level == 0)>
			<optgroup label="<#if (privateLayout)>${languageUtil.get(requestedLocale, "private-pages")}<#else>${languageUtil.get(requestedLocale, "public-pages")}</#if>">
		</#if>

		<#list layouts as curLayout>
			<#assign curLayoutJSON = htmlUtil.escapeAttribute("{ \"layoutId\": ${curLayout.getLayoutId()}, \"groupId\": ${groupId}, \"privateLayout\": ${privateLayout?string} }")>

			<#assign selected = false>

			<#if selectedLayout??>
				<#assign selected = (selectedPlid == curLayout.getPlid())>
			</#if>

			<@aui.option selected=selected value=curLayoutJSON>
				<#list 0..level as i>
					&ndash;&nbsp;
				</#list>

				${curLayout.getName(requestedLocale)}
			</@>

			<@getLayoutsOptions groupId=scopeGroupId privateLayout=false parentLayoutId=curLayout.getLayoutId() selectedPlid=selectedPlid level=level+1></@>
		</#list>

		<#if (level == 0)>
			</optgroup>
		</#if>

	</#if>

</#macro>

<@aui["field-wrapper"] data=data helpMessage=escape(fieldStructure.tip) label=escape(label) required=required>
	<#assign selectedPlid = 0>

	<#if (fieldRawValue?? && fieldRawValue != "")>
		<#assign fieldLayoutJSONObject = jsonFactoryUtil.createJSONObject(fieldRawValue)>

		<#assign selectedLayout = layoutLocalService.fetchLayout(fieldLayoutJSONObject.getLong("groupId"), fieldLayoutJSONObject.getBoolean("privateLayout"), fieldLayoutJSONObject.getLong("layoutId"))!"">

		<#if (selectedLayout?? && selectedLayout != "")>
			<#assign selectedPlid = selectedLayout.getPlid()>
		</#if>
	</#if>

	<select name="${namespacedFieldName}">
		<@getLayoutsOptions groupId=scopeGroupId privateLayout=false parentLayoutId=0 selectedPlid=selectedPlid></@>

		<@getLayoutsOptions groupId=scopeGroupId privateLayout=true parentLayoutId=0 selectedPlid=selectedPlid></@>
	</select>

	${fieldStructure.children}
</@>