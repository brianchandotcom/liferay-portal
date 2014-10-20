package ${packagePath}.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @generated
 */
@ProviderType
public class ${entity.name}FinderUtil {

	<#list methods as method>
		<#if !method.isConstructor() && method.isPublic()>
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

				getFinder().${method.name}(

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

	public static ${entity.name}Finder getFinder() {
		if (_finder == null) {
			<#if pluginName != "">
				_finder = (${entity.name}Finder)PortletBeanLocatorUtil.locate(${packagePath}.service.ClpSerializer.getServletContextName(), ${entity.name}Finder.class.getName());
			<#else>
				_finder = (${entity.name}Finder)PortalBeanLocatorUtil.locate(${entity.name}Finder.class.getName());
			</#if>

			ReferenceRegistry.registerReference(${entity.name}FinderUtil.class, "_finder");
		}

		return _finder;
	}

	public void setFinder(${entity.name}Finder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(${entity.name}FinderUtil.class, "_finder");
	}

	private static ${entity.name}Finder _finder;

}