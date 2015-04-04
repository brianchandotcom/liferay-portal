<bean name="/${serviceMapping}-hessian" class="org.springframework.remoting.caucho.HessianServiceExporter">
	<property name="service" ref="${serviceName}" />
	<property name="serviceInterface" value="${serviceName}" />
</bean>
<bean name="/${serviceMapping}-http" class="org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter">
	<property name="service" ref="${serviceName}" />
	<property name="serviceInterface" value="${serviceName}" />
</bean>