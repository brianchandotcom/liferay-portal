package ${packagePath}.service.persistence;

import aQute.bnd.annotation.ProviderType;

/**
 * @generated
 */
@ProviderType
public interface ${entity.name}Finder {

	<#list methods as method>
		<#if !method.isConstructor() && method.isPublic()>
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
			</#list>;
		</#if>
	</#list>

}