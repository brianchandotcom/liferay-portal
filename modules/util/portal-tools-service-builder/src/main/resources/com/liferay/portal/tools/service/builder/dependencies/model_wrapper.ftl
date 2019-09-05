package ${apiPackagePath}.model;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.math.BigDecimal;

import java.sql.Blob;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link ${entity.name}}.
 * </p>
 *
 * @author ${author}
 * @see ${entity.name}
<#if classDeprecated>
 * @deprecated ${classDeprecatedComment}
</#if>
 * @generated
 */

<#if classDeprecated>
	@Deprecated
</#if>
public class ${entity.name}Wrapper
	<#assign entityFieldName = "model" />

	<#if serviceBuilder.isVersionLTE_7_1_0()>
		<#assign entityFieldName = "_${entity.varName}" />
	<#else>
		extends BaseModelWrapper<${entity.name}>
	</#if>

	implements ${entity.name}, ModelWrapper<${entity.name}> {

	public ${entity.name}Wrapper(${entity.name} ${entity.varName}) {
		<#if serviceBuilder.isVersionLTE_7_1_0()>
			${entityFieldName} = ${entity.varName};
		<#else>
			super(${entity.varName});
		</#if>
	}

	<#if serviceBuilder.isVersionLTE_7_1_0()>
		@Override
		public Class<?> getModelClass() {
			return ${entity.name}.class;
		}

		@Override
		public String getModelClassName() {
			return ${entity.name}.class.getName();
		}
	</#if>

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		<#list entity.regularEntityColumns as entityColumn>
			<#if stringUtil.equals(entityColumn.type, "boolean")>
				attributes.put("${entityColumn.name}", is${entityColumn.methodName}());
			<#else>
				attributes.put("${entityColumn.name}", get${entityColumn.methodName}());
			</#if>
		</#list>

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		<#list entity.regularEntityColumns as entityColumn>
			<#if entityColumn.isPrimitiveType()>
				${serviceBuilder.getPrimitiveObj(entityColumn.type)}
			<#else>
				${entityColumn.genericizedType}
			</#if>

			${entityColumn.name} =

			<#if entityColumn.isPrimitiveType()>
				(${serviceBuilder.getPrimitiveObj(entityColumn.type)})
			<#else>
				(${entityColumn.genericizedType})
			</#if>

			attributes.get("${entityColumn.name}");

			if (${entityColumn.name} != null) {
				set${entityColumn.methodName}(${entityColumn.name});
			}
		</#list>
	}

	<#list methods as method>
		<#assign parameters = method.parameters />

		<#if !method.isStatic() && method.isPublic() && (!serviceBuilder.isVersionLTE_7_1_0() || !(stringUtil.equals(method.name, "equals") && (parameters?size == 1)))>
			<#if stringUtil.equals(method.name, "getStagedModelType")>
				<#assign hasGetStagedModelTypeMethod = true />
			</#if>

			${serviceBuilder.getJavadocComment(method)}

			<#if serviceBuilder.hasAnnotation(method, "Deprecated")>
				@Deprecated
			</#if>

			@Override
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

			{
				<#if stringUtil.equals(method.name, "clone") && (parameters?size == 0)>
					return new ${entity.name}Wrapper((${entity.name})${entityFieldName}.clone());
				<#elseif (stringUtil.equals(method.name, "toEscapedModel") || stringUtil.equals(method.name, "toUnescapedModel")) && (parameters?size == 0)>
					return new ${entity.name}Wrapper(${entityFieldName}.${method.name}());
				<#else>
					<#if !stringUtil.equals(method.returns.value, "void")>
						return
					</#if>

					${entityFieldName}.${method.name}(

					<#list method.parameters as parameter>
						${parameter.name}

						<#if parameter_has_next>
							,
						</#if>
					</#list>

					);
				</#if>
			}
		</#if>
	</#list>

	<#if serviceBuilder.isVersionLTE_7_1_0()>
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}

			if (!(obj instanceof ${entity.name}Wrapper)) {
				return false;
			}

			${entity.name}Wrapper ${entity.varName}Wrapper = (${entity.name}Wrapper)obj;

			if (Objects.equals(${entityFieldName}, ${entity.varName}Wrapper.${entityFieldName})) {
				return true;
			}

			return false;
		}
	</#if>

	<#if entity.isHierarchicalTree()>
		@Override
		public long getNestedSetsTreeNodeLeft() {
			return ${entityFieldName}.getNestedSetsTreeNodeLeft();
		}

		@Override
		public long getNestedSetsTreeNodeRight() {
			return ${entityFieldName}.getNestedSetsTreeNodeRight();
		}

		@Override
		public long getNestedSetsTreeNodeScopeId() {
			return ${entityFieldName}.getNestedSetsTreeNodeScopeId();
		}

		@Override
		public void setNestedSetsTreeNodeLeft(long nestedSetsTreeNodeLeft) {
			${entityFieldName}.setNestedSetsTreeNodeLeft(nestedSetsTreeNodeLeft);
		}

		@Override
		public void setNestedSetsTreeNodeRight(long nestedSetsTreeNodeRight) {
			${entityFieldName}.setNestedSetsTreeNodeRight(nestedSetsTreeNodeRight);
		}
	</#if>

	<#if entity.isStagedModel() && !hasGetStagedModelTypeMethod!false>
		@Override
		public StagedModelType getStagedModelType() {
			return ${entityFieldName}.getStagedModelType();
		}
	</#if>

	<#if entity.versionEntity??>
		<#assign versionEntity = entity.versionEntity />

		@Override
		public boolean isHead() {
			return ${entityFieldName}.isHead();
		}

		@Override
		public void populateVersionModel(${versionEntity.name} ${versionEntity.varName}) {
			${entityFieldName}.populateVersionModel(${versionEntity.varName});
		}
	<#elseif entity.versionedEntity??>
		<#assign versionedEntity = entity.versionedEntity />

		@Override
		public long getVersionedModelId() {
			return ${entityFieldName}.getVersionedModelId();
		}

		@Override
		public void setVersionedModelId(long id) {
			${entityFieldName}.setVersionedModelId(id);
		}

		@Override
		public void populateVersionedModel(${versionedEntity.name} ${versionedEntity.varName}) {
			${entityFieldName}.populateVersionedModel(${versionedEntity.varName});
		}

		@Override
		public ${versionedEntity.name} toVersionedModel() {
			return ${entityFieldName}.toVersionedModel();
		}
	</#if>

	<#if serviceBuilder.isVersionLTE_7_1_0()>
		@Override
		public ${entity.name} getWrappedModel() {
			return ${entityFieldName};
		}

		@Override
		public boolean isEntityCacheEnabled() {
			return ${entityFieldName}.isEntityCacheEnabled();
		}

		@Override
		public boolean isFinderCacheEnabled() {
			return ${entityFieldName}.isFinderCacheEnabled();
		}

		@Override
		public void resetOriginalValues() {
			${entityFieldName}.resetOriginalValues();
		}

		private final ${entity.name} ${entityFieldName};
	<#else>
		@Override
		protected ${entity.name}Wrapper wrap(${entity.name} ${entity.varName}) {
			return new ${entity.name}Wrapper(${entity.varName});
		}
	</#if>

}