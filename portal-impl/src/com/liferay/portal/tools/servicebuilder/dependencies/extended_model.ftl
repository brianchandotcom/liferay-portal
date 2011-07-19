package ${packagePath}.model;

import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the ${entity.name} service. Represents a row in the &quot;${entity.table}&quot; database table, with each column mapped to a property of this class.
 *
 * @author ${author}
 * @see ${entity.name}Model
 * @see ${packagePath}.model.impl.${entity.name}Impl
 * @see ${packagePath}.model.impl.${entity.name}ModelImpl
 * @generated
 */
public interface ${entity.name} extends
	${entity.name}Model

	<#if entity.hasLocalService() && entity.hasColumns()>
		, PersistedModel
	</#if>

	{

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link ${packagePath}.model.impl.${entity.name}Impl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	<#list methods as method>
		<#if !method.isConstructor() && !method.isStatic() && method.isPublic()>
			${serviceBuilder.getJavadocComment(method)}

			<#assign annotations = method.annotations>

			<#list annotations as annotation>
				<#if annotation.type.javaClass.name != "Override">
					${annotation.toString()}
				</#if>
			</#list>

			public ${serviceBuilder.getTypeGenericsName(method.returns)} ${method.name} (

			<#assign parameters = method.parameters>

			<#list parameters as parameter>
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

			;
		</#if>
	</#list>

}