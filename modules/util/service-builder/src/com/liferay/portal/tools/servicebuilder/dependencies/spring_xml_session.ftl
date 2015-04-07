<bean id="${packagePath}.service.${entity.name}${sessionType}Service" class="${packagePath}.service.impl.${entity.name}${sessionType}ServiceImpl" />

<#if osgiModule>
	<osgi:service interface="${packagePath}.service.${entity.name}${sessionType}Service" ref="${packagePath}.service.${entity.name}${sessionType}Service">
		<osgi:service-properties>
			<entry key="json.web.service.context.name" value="${portletShortName?lower_case}" />
			<entry key="json.web.service.context.path" value="${entity.name}" />
		</osgi:service-properties>
	</osgi:service>
</#if>