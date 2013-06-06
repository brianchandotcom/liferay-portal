package ${packagePath}.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.Invokable${sessionTypeName}Service;

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
<#if isDeprecated>
 * @deprecated ${deprecatedComment}
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
<#if isDeprecated>
 * @deprecated ${deprecatedComment}
</#if>
 * @generated
 */
</#if>
public class ${entity.name}${sessionTypeName}ServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link ${packagePath}.service.impl.${entity.name}${sessionTypeName}ServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	<#list methods as method>
		<#if !method.isConstructor() && !method.isStatic() && method.isPublic() && serviceBuilder.isCustomMethod(method)>
			${serviceBuilder.getJavadocComment(method)}

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

	<#if pluginName != "">
		public static void clearService() {
			_service = null;
		}
	</#if>

	public static ${entity.name}${sessionTypeName}Service getService() {
		if (_service == null) {
			<#if pluginName != "">
				Invokable${sessionTypeName}Service invokable${sessionTypeName}Service = (Invokable${sessionTypeName}Service)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(), ${entity.name}${sessionTypeName}Service.class.getName());

				if (invokable${sessionTypeName}Service instanceof ${entity.name}${sessionTypeName}Service) {
					_service = (${entity.name}${sessionTypeName}Service)invokable${sessionTypeName}Service;
				}
				else {
					_service = new ${entity.name}${sessionTypeName}ServiceClp(invokable${sessionTypeName}Service);
				}
			<#else>
				_service = (${entity.name}${sessionTypeName}Service)PortalBeanLocatorUtil.locate(${entity.name}${sessionTypeName}Service.class.getName());
			</#if>

			ReferenceRegistry.registerReference(${entity.name}${sessionTypeName}ServiceUtil.class, "_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(${entity.name}${sessionTypeName}Service service) {
	}

	private static ${entity.name}${sessionTypeName}Service _service;

}