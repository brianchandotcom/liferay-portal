package ${packagePath}.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.model.PermissionedModel;
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
@ProviderType
public interface ${entity.name} extends
	${entity.name}Model

	<#if entity.hasLocalService() && entity.hasColumns()>
		<#if entity.isPermissionedModel()>
			, PermissionedModel
		<#else>
			, PersistedModel
		</#if>
	</#if>

	{

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link ${packagePath}.model.impl.${entity.name}Impl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	<#if entity.hasUuidAccessor()>
		public static final Accessor<${entity.name}, String> UUID_ACCESSOR = new Accessor<${entity.name}, String>() {

			@Override
			public String get(${entity.name} ${entity.varName}) {
				return ${entity.varName}.getUuid();
			}

		};
	</#if>

	<#list entity.columnList as column>
		<#if column.isAccessor()>
			public static final Accessor<${entity.name}, ${serviceBuilder.getPrimitiveObj(column.type)}> ${textFormatter.format(textFormatter.format(column.name, 7), 0)}_ACCESSOR = new Accessor<${entity.name}, ${serviceBuilder.getPrimitiveObj(column.type)}>() {

				@Override
				public ${serviceBuilder.getPrimitiveObj(column.type)} get(${entity.name} ${entity.varName}) {
					return ${entity.varName}.get${column.methodName}();
				}

			};
		</#if>
	</#list>

	<#list methods as method>
		<#if !method.isConstructor() && !method.isStatic() && method.isPublic()>
			${serviceBuilder.getJavadocComment(method)}

			<#assign parameters = method.parameters>

			<#assign annotations = method.annotations>

			<#list annotations as annotation>
				<#if annotation.type.javaClass.name != "Override">
					${annotation.toString()}
				<#else>
					<#if (method.name == "equals") && (parameters?size == 1)>
						<#assign firstParameter = parameters?first>

						<#if serviceBuilder.getTypeGenericsName(firstParameter.type) == "java.lang.Object">
							@Override
						</#if>
					</#if>
				</#if>
			</#list>

			public ${serviceBuilder.getTypeGenericsName(method.returns)} ${method.name} (

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