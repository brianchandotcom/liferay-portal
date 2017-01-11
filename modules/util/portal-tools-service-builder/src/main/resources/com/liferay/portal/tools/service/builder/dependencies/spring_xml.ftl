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
		<#if !stringUtil.equals(entity.dataSource, "liferayDataSource") || !stringUtil.equals(entity.sessionFactory, "liferaySessionFactory")>
			<bean class="${entity.getPersistenceClass()}" id="${apiPackagePath}.service.persistence.${entity.name}Persistence" parent="basePersistence">
				<#if !stringUtil.equals(entity.dataSource, "liferayDataSource")>
					<property name="dataSource" ref="${entity.getDataSource()}" />
				</#if>

				<#if !stringUtil.equals(entity.sessionFactory, "liferaySessionFactory")>
					<property name="sessionFactory" ref="${entity.getSessionFactory()}" />
				</#if>
			</bean>
		<#else>
			<bean class="${entity.getPersistenceClass()}" id="${apiPackagePath}.service.persistence.${entity.name}Persistence" parent="basePersistence" />
		</#if>
	</#if>

	<#if entity.hasFinderClass()>
		<#if !stringUtil.equals(entity.dataSource, "liferayDataSource") || !stringUtil.equals(entity.sessionFactory, "liferaySessionFactory")>
			<bean class="${entity.finderClass}" id="${apiPackagePath}.service.persistence.${entity.name}Finder" parent="basePersistence">
				<#if !stringUtil.equals(entity.dataSource, "liferayDataSource")>
					<property name="dataSource" ref="${entity.getDataSource()}" />
				</#if>

				<#if !stringUtil.equals(entity.sessionFactory, "liferaySessionFactory")>
					<property name="sessionFactory" ref="${entity.getSessionFactory()}" />
				</#if>
			</bean>
		<#else>
			<bean class="${entity.finderClass}" id="${apiPackagePath}.service.persistence.${entity.name}Finder" parent="basePersistence" />
		</#if>
	</#if>
</#list>