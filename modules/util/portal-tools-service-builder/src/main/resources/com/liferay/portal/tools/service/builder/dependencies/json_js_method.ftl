Liferay.Service.registerClass(
	Liferay.Service.${portletShortName}, "${entity.name}",
	{
		<#list methods as method>
		${method}: true<#if method_has_next>,</#if>
		</#list>
	}
);