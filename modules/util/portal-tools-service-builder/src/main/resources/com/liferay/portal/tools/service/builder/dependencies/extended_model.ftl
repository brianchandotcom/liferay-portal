package ${apiPackagePath}.model;

import ${serviceBuilder.getCompatJavaClassName("ProviderType")};

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.NestedSetsTreeNodeModel;

<#if entity.isPermissionedModel()>
	import com.liferay.portal.kernel.model.PermissionedModel;
</#if>

import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.TreeModel;
import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.kernel.util.LocaleThreadLocal;

/**
 * The extended model interface for the ${entity.name} service. Represents a row in the &quot;${entity.table}&quot; database table, with each column mapped to a property of this class.
 *
 * @author ${author}
 * @see ${entity.name}Model
<#if classDeprecated>
 * @deprecated ${classDeprecatedComment}
</#if>
 * @generated
 */

<#if classDeprecated>
	@Deprecated
</#if>

@ImplementationClassName("${packagePath}.model.impl.${entity.name}Impl")
@ProviderType
public interface ${entity.name} extends
	${entity.name}Model

	<#assign overrideColumnNames = [] />

	<#if entity.hasLocalService() && entity.hasEntityColumns() && entity.hasPersistence()>
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

			<#assign overrideColumnNames = overrideColumnNames + ["buildTreePath", "updateTreePath"] />
		</#if>
	</#if>

	{

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>${packagePath}.model.impl.${entity.name}Impl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
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

	<#list entity.entityColumns as entityColumn>
		<#if entityColumn.isAccessor() || entityColumn.isPrimary()>
			public static final Accessor<${entity.name}, ${serviceBuilder.getPrimitiveObj(entityColumn.type)}> ${entityColumn.getAccessorName(apiPackagePath + ".model." + entity.name)} = new Accessor<${entity.name}, ${serviceBuilder.getPrimitiveObj(entityColumn.type)}>() {

				@Override
				public ${serviceBuilder.getPrimitiveObj(entityColumn.type)} get(${entity.name} ${entity.varName}) {
					return ${entity.varName}.get${entityColumn.methodName}(<#if entityColumn.isLocalized()>LocaleThreadLocal.getThemeDisplayLocale()</#if>);
				}

				@Override
				public Class<${serviceBuilder.getPrimitiveObj(entityColumn.type)}> getAttributeClass() {
					return ${serviceBuilder.getPrimitiveObj(entityColumn.type)}.class;
				}

				@Override
				public Class<${entity.name}> getTypeClass() {
					return ${entity.name}.class;
				}

			};
		</#if>
	</#list>

	<#list methods as method>
		<#if !method.isStatic() && method.isPublic()>
			${serviceBuilder.getJavadocComment(method)}

			<#assign
				parameters = method.parameters

				annotations = method.annotations
			/>

			<#list annotations as annotation>
				<#if !stringUtil.equals(annotation.type.name, "Override")>
					${annotation.toString()}
				<#else>
					<#if stringUtil.equals(method.name, "equals") && (parameters?size == 1)>
						<#assign firstParameter = parameters?first />

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

				${exception.fullyQualifiedName}

				<#if exception_has_next>
					,
				</#if>
			</#list>

			;
		</#if>
	</#list>

}