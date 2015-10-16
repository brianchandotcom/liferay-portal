package ${packagePath}.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.model.NestedSetsTreeNodeModel;
import com.liferay.portal.model.PermissionedModel;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.model.TreeModel;

/**
 * The extended model interface for the ${entity.name} service. Represents a row in the &quot;${entity.table}&quot; database table, with each column mapped to a property of this class.
 *
 * @author ${author}
 * @see ${entity.name}Model
 * @see ${packagePath}.model.impl.${entity.name}Impl
 * @see ${packagePath}.model.impl.${entity.name}ModelImpl
<#if classDeprecated>
 * @deprecated ${classDeprecatedComment}
</#if>
 * @generated
 */

<#if classDeprecated>
	@Deprecated
</#if>

@ProviderType
public interface ${entity.name} extends
	${entity.name}Model

	<#assign overrideColumnNames = []>

	<#if entity.hasLocalService() && entity.hasColumns()>
		<#if entity.isHierarchicalTree()>
			, NestedSetsTreeNodeModel
		</#if>

		<#if entity.isPermissionedModel()>
			, PermissionedModel
		<#else>
			, PersistedModel
		</#if>

		<#if entity.isTreeModel()>
			, TreeModel

			<#assign overrideColumnNames = overrideColumnNames + ["buildTreePath", "updateTreePath"]>
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

			@Override
			public Class<String> getAttributeClass() {
				return String.class;
			}

			@Override
			public Class<${entity.name}> getTypeClass() {
				return ${entity.name}.class;
			}

		};
	</#if>

	<#list entity.columnList as column>
		<#if column.isAccessor() || column.isPrimary()>
			public static final Accessor<${entity.name}, ${serviceBuilder.getPrimitiveObj(column.type)}> ${textFormatter.format(textFormatter.format(column.name, 7), 0)}_ACCESSOR = new Accessor<${entity.name}, ${serviceBuilder.getPrimitiveObj(column.type)}>() {

				@Override
				public ${serviceBuilder.getPrimitiveObj(column.type)} get(${entity.name} ${entity.varName}) {
					return ${entity.varName}.get${column.methodName}(<#if column.isLocalized()>LocaleThreadLocal.getThemeDisplayLocale()</#if>);
				}

				@Override
				public Class<${serviceBuilder.getPrimitiveObj(column.type)}> getAttributeClass() {
					return ${serviceBuilder.getPrimitiveObj(column.type)}.class;
				}

				@Override
				public Class<${entity.name}> getTypeClass() {
					return ${entity.name}.class;
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

			<#if overrideColumnNames?seq_index_of(method.name) != -1>
				@Override
			</#if>

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