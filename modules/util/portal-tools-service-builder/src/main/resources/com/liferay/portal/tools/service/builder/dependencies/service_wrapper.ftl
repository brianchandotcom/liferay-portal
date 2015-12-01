package ${packagePath}.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ${entity.name}${sessionTypeName}Service}.
 *
 * @author ${author}
 * @see ${entity.name}${sessionTypeName}Service
<#if classDeprecated>
 * @deprecated ${classDeprecatedComment}
</#if>
 * @generated
 */

<#if classDeprecated>
	@Deprecated
</#if>

@ProviderType
public class ${entity.name}${sessionTypeName}ServiceWrapper implements ${entity.name}${sessionTypeName}Service, ServiceWrapper<${entity.name}${sessionTypeName}Service> {

	public ${entity.name}${sessionTypeName}ServiceWrapper(${entity.name}${sessionTypeName}Service ${entity.varName}${sessionTypeName}Service) {
		_${entity.varName}${sessionTypeName}Service = ${entity.varName}${sessionTypeName}Service;
	}

	<#list methods as method>
		<#if !method.isConstructor() && method.isPublic() && serviceBuilder.isCustomMethod(method)>
			${serviceBuilder.getJavadocComment(method)}

			<#if serviceBuilder.hasAnnotation(method, "Deprecated")>
				@Deprecated
			</#if>

			@Override
			public

			<#if method.name = "dynamicQuery" && (serviceBuilder.getTypeGenericsName(method.returns) == "java.util.List<T>")>
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

				${exception.value}

				<#if exception_has_next>
					,
				</#if>
			</#list>

			{
				<#if method.returns.value != "void">
					return
				</#if>

				_${entity.varName}${sessionTypeName}Service.${method.name}(

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

	@Override
	public ${entity.name}${sessionTypeName}Service getWrappedService() {
		return _${entity.varName}${sessionTypeName}Service;
	}

	@Override
	public void setWrappedService(${entity.name}${sessionTypeName}Service ${entity.varName}${sessionTypeName}Service) {
		_${entity.varName}${sessionTypeName}Service = ${entity.varName}${sessionTypeName}Service;
	}

	private ${entity.name}${sessionTypeName}Service _${entity.varName}${sessionTypeName}Service;

}