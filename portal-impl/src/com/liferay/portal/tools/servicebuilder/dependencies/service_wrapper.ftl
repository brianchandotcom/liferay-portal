package ${packagePath}.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ${entity.name}${sessionTypeName}Service}.
 *
 * @author ${author}
 * @see ${entity.name}${sessionTypeName}Service
<#if isDeprecated>
 * @deprecated ${deprecatedComment}
</#if>
 * @generated
 */
public class ${entity.name}${sessionTypeName}ServiceWrapper implements ${entity.name}${sessionTypeName}Service, ServiceWrapper<${entity.name}${sessionTypeName}Service> {

	public ${entity.name}${sessionTypeName}ServiceWrapper(${entity.name}${sessionTypeName}Service ${entity.varName}${sessionTypeName}Service) {
		_${entity.varName}${sessionTypeName}Service = ${entity.varName}${sessionTypeName}Service;
	}

	<#list methods as method>
		<#if !method.isConstructor() && method.isPublic() && serviceBuilder.isCustomMethod(method)>
			${serviceBuilder.getJavadocComment(method)}

			@Override

			<#if method.name = "dynamicQuery" && (method.parameters?size != 0)>
				@SuppressWarnings("rawtypes")
			</#if>

			public ${serviceBuilder.getTypeGenericsName(method.returns)} ${method.name}(

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

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public ${entity.name}${sessionTypeName}Service getWrapped${entity.name}${sessionTypeName}Service() {
		return _${entity.varName}${sessionTypeName}Service;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrapped${entity.name}${sessionTypeName}Service(${entity.name}${sessionTypeName}Service ${entity.varName}${sessionTypeName}Service) {
		_${entity.varName}${sessionTypeName}Service = ${entity.varName}${sessionTypeName}Service;
	}

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