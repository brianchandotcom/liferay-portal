<#list entities as entity>
	<#if entity.hasLocalService()>
		<#assign sessionType = "Local" />

		<#include "spring_xml_session.ftl">
	</#if>

	<#if entity.hasRemoteService()>
		<#assign sessionType = "" />

		<#include "spring_xml_session.ftl">
	</#if>

	<#if entity.hasColumns()>
		<#if (entity.dataSource != "liferayDataSource") || (entity.sessionFactory != "liferaySessionFactory")>
			<bean class="${entity.getPersistenceClass()}" id="${apiPackagePath}.service.persistence.${entity.name}Persistence" parent="basePersistence">
				<#if entity.dataSource != "liferayDataSource">
					<property name="dataSource" ref="${entity.getDataSource()}" />
				</#if>

				<#if entity.sessionFactory != "liferaySessionFactory">
					<property name="sessionFactory" ref="${entity.getSessionFactory()}" />
				</#if>
			</bean>
		<#else>
			<bean class="${entity.getPersistenceClass()}" id="${apiPackagePath}.service.persistence.${entity.name}Persistence" parent="basePersistence" />
		</#if>
	</#if>

	<#if entity.hasFinderClass()>
		<#if (entity.dataSource != "liferayDataSource") || (entity.sessionFactory != "liferaySessionFactory")>
			<bean class="${entity.finderClass}" id="${apiPackagePath}.service.persistence.${entity.name}Finder" parent="basePersistence">
				<#if entity.dataSource != "liferayDataSource">
					<property name="dataSource" ref="${entity.getDataSource()}" />
				</#if>

				<#if entity.sessionFactory != "liferaySessionFactory">
					<property name="sessionFactory" ref="${entity.getSessionFactory()}" />
				</#if>
			</bean>
		<#else>
			<bean class="${entity.finderClass}" id="${apiPackagePath}.service.persistence.${entity.name}Finder" parent="basePersistence" />
		</#if>
	</#if>
</#list>