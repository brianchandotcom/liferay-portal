package ${packagePath}.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

<#if sessionTypeName == "Local">
/**
 * Provides the local service utility for ${entity.name}. This utility wraps
 * {@link ${packagePath}.service.impl.${entity.name}LocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author ${author}
 * @see ${entity.name}LocalService
 * @see ${packagePath}.service.base.${entity.name}LocalServiceBaseImpl
 * @see ${packagePath}.service.impl.${entity.name}LocalServiceImpl
<#if classDeprecated>
 * @deprecated ${classDeprecatedComment}
</#if>
 * @generated
 */
<#else>
/**
 * Provides the remote service utility for ${entity.name}. This utility wraps
 * {@link ${packagePath}.service.impl.${entity.name}ServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author ${author}
 * @see ${entity.name}Service
 * @see ${packagePath}.service.base.${entity.name}ServiceBaseImpl
 * @see ${packagePath}.service.impl.${entity.name}ServiceImpl
<#if classDeprecated>
 * @deprecated ${classDeprecatedComment}
</#if>
 * @generated
 */
</#if>

<#if classDeprecated>
	@Deprecated
</#if>

@ProviderType

public class ${entity.name}${sessionTypeName}ServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link ${packagePath}.service.impl.${entity.name}${sessionTypeName}ServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	<#list methods as method>
		<#if !method.isConstructor() && !method.isStatic() && method.isPublic() && serviceBuilder.isCustomMethod(method)>
			${serviceBuilder.getJavadocComment(method)}

			<#if serviceBuilder.hasAnnotation(method, "Deprecated")>
				@Deprecated
			</#if>

			<#if method.name = "dynamicQuery" && (method.parameters?size != 0)>
				@SuppressWarnings("rawtypes")
			</#if>

			public static ${serviceBuilder.getTypeGenericsName(method.returns)} ${method.name}(

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

				${exception.value}

				<#if exception_has_next>
					,
				</#if>
			</#list>

			{
				<#if method.returns.value != "void">
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


	public static ${entity.name}${sessionTypeName}Service getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<${entity.name}${sessionTypeName}Service, ${entity.name}${sessionTypeName}Service> _serviceTracker;
	
	static {
		Bundle bundle = FrameworkUtil.getBundle(${entity.name}${sessionTypeName}ServiceUtil.class);

		_serviceTracker = new ServiceTracker<${entity.name}${sessionTypeName}Service, ${entity.name}${sessionTypeName}Service>(bundle.getBundleContext(), ${entity.name}${sessionTypeName}Service.class, null);

		_serviceTracker.open();
	}

}
