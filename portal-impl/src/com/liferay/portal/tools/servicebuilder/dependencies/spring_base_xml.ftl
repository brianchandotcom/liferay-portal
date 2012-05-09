<?xml version="1.0"?>

<beans
	default-destroy-method="destroy"
	default-init-method="afterPropertiesSet"
	xmlns="http://www.springframework.org/schema/beans"
	xmlns:aop="http://www.springframework.org/schema/aop"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-3.0.xsd http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd"
>
	<bean class="com.liferay.portal.kernel.util.PrototypeBeanUtil" factory-method="lookup">
		<constructor-arg value="com.liferay.portal.spring.aop.ServiceBeanAutoProxyCreator"/>
	</bean>
	<bean class="com.liferay.portal.kernel.util.PrototypeBeanUtil" factory-method="lookup">
		<constructor-arg value="com.liferay.portal.spring.context.PortletBeanFactoryCleaner"/>
	</bean>
	<bean class="com.liferay.portal.kernel.util.PrototypeBeanUtil" factory-method="lookup">
		<constructor-arg value="com.liferay.portal.spring.context.PortletBeanFactoryPostProcessor"/>
	</bean>
	<bean class="com.liferay.portal.kernel.util.PrototypeBeanUtil" factory-method="lookup">
		<constructor-arg value="com.liferay.portal.spring.bean.BeanReferenceAnnotationBeanPostProcessor"/>
	</bean>
	<bean id="portletClassLoader" class="com.liferay.portal.kernel.portlet.PortletClassLoaderUtil" factory-method="getClassLoader" />
	<bean id="servletContextName" class="com.liferay.portal.kernel.portlet.PortletClassLoaderUtil" factory-method="getServletContextName" />
	<bean id="basePersistence" abstract="true">
		<property name="dataSource" ref="liferayDataSource" />
		<property name="sessionFactory" ref="liferaySessionFactory" />
	</bean>
</beans>