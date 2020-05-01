package ${apiPackagePath}.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

<#if stringUtil.equals(sessionTypeName, "Local")>
/**
 * Provides the local service utility for ${entity.name}. This utility wraps
 * <code>${packagePath}.service.impl.${entity.name}LocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author ${author}
 * @see ${entity.name}LocalService
<#if classDeprecated>
 * @deprecated ${classDeprecatedComment}
</#if>
 * @generated
 */
<#else>
/**
 * Provides the remote service utility for ${entity.name}. This utility wraps
 * <code>${packagePath}.service.impl.${entity.name}ServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author ${author}
 * @see ${entity.name}Service
<#if classDeprecated>
 * @deprecated ${classDeprecatedComment}
</#if>
 * @generated
 */
</#if>

<#if classDeprecated>
	@Deprecated
</#if>
public class ${entity.name}${sessionTypeName}ServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>${packagePath}.service.impl.${entity.name}${sessionTypeName}ServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	<#list methods as method>
		<#if !method.isStatic() && method.isPublic() && serviceBuilder.isCustomMethod(method)>
			${serviceBuilder.getJavadocComment(method)}

			<#if serviceBuilder.hasAnnotation(method, "Deprecated")>
				@Deprecated
			</#if>
			public static

			<#if (method.name = "dslQuery" && (serviceBuilder.getTypeGenericsName(method.returns) == "T")) || (method.name = "dynamicQuery" && (serviceBuilder.getTypeGenericsName(method.returns) == "java.util.List<T>"))>
				<T>
			</#if>

			${serviceBuilder.getTypeGenericsName(method.returns)} ${method.name}(

			<#list method.parameters as parameter>
				${serviceBuilder.getTypeGenericsName(parameter.type)} ${parameter.name}

				<#if parameter_has_next>
					,
				</#if>
			</#list>

			)

			<#list method.exceptions as exception>
				<#if exception_index == 0>
					throws
				</#if>

				${exception.fullyQualifiedName}

				<#if exception_has_next>
					,
				</#if>
			</#list>

			{
				<#if !stringUtil.equals(method.returns.value, "void")>
					return
				</#if>

				getService().${method.name}(

				<#list method.parameters as parameter>
					${parameter.name}

					<#if parameter_has_next>
						,
					</#if>
				</#list>

				);
			}
		</#if>
	</#list>

	<#if validator.isNotNull(pluginName)>
		public static void clearService() {
			_service = null;
		}
	</#if>

	public static ${entity.name}${sessionTypeName}Service getService() {
		<#if osgiModule>
			return _serviceTracker.getService();
		<#else>
			if (_service == null) {
				<#if validator.isNotNull(pluginName)>
					_service = (${entity.name}${sessionTypeName}Service)PortletBeanLocatorUtil.locate(ServletContextUtil.getServletContextName(), ${entity.name}${sessionTypeName}Service.class.getName());
				<#else>
					_service = (${entity.name}${sessionTypeName}Service)PortalBeanLocatorUtil.locate(${entity.name}${sessionTypeName}Service.class.getName());
				</#if>
			}

			return _service;
		</#if>
	}

	<#if osgiModule>
		private static ServiceTracker<${entity.name}${sessionTypeName}Service, ${entity.name}${sessionTypeName}Service> _serviceTracker;

		static {
			Bundle bundle = FrameworkUtil.getBundle(${entity.name}${sessionTypeName}Service.class);

			ServiceTracker<${entity.name}${sessionTypeName}Service, ${entity.name}${sessionTypeName}Service> serviceTracker = new ServiceTracker<${entity.name}${sessionTypeName}Service, ${entity.name}${sessionTypeName}Service>(bundle.getBundleContext(), ${entity.name}${sessionTypeName}Service.class, null);

			serviceTracker.open();

			_serviceTracker = serviceTracker;
		}
	<#else>
		private static ${entity.name}${sessionTypeName}Service _service;
	</#if>

}