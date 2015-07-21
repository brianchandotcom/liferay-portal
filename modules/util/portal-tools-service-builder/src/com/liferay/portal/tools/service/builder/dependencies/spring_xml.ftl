<#list entities as entity>
	<#if entity.hasLocalService()>
		<#assign sessionType = "Local">

		<#include "spring_xml_session.ftl">
	</#if>

	<#if entity.hasRemoteService()>
		<#assign sessionType = "">

		<#include "spring_xml_session.ftl">
	</#if>

	<#if entity.hasColumns()>
		<#if (entity.dataSource != "liferayDataSource") || (entity.sessionFactory != "liferaySessionFactory")>
			<bean id="${packagePath}.service.persistence.${entity.name}Persistence" class="${entity.getPersistenceClass()}" parent="basePersistence">
				<#if entity.dataSource != "liferayDataSource">
					<property name="dataSource" ref="${entity.getDataSource()}" />
				</#if>

				<#if entity.sessionFactory != "liferaySessionFactory" >
					<property name="sessionFactory" ref="${entity.getSessionFactory()}" />
				</#if>
			</bean>
		<#else>
			<bean id="${packagePath}.service.persistence.${entity.name}Persistence" class="${entity.getPersistenceClass()}" parent="basePersistence" />
		</#if>
	</#if>

	<#if entity.hasFinderClass()>
		<#if (entity.dataSource != "liferayDataSource") || (entity.sessionFactory != "liferaySessionFactory")>
			<bean id="${packagePath}.service.persistence.${entity.name}Finder" class="${entity.finderClass}" parent="basePersistence">
				<#if entity.dataSource != "liferayDataSource">
					<property name="dataSource" ref="${entity.getDataSource()}" />
				</#if>

				<#if entity.sessionFactory != "liferaySessionFactory" >
					<property name="sessionFactory" ref="${entity.getSessionFactory()}" />
				</#if>
			</bean>
		<#else>
			<bean id="${packagePath}.service.persistence.${entity.name}Finder" class="${entity.finderClass}" parent="basePersistence" />
		</#if>
	</#if>
</#list>