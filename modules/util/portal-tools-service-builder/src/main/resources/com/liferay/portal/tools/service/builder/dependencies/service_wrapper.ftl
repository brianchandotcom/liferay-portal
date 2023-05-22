package ${apiPackagePath}.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

<#if entity.isChangeTrackingEnabled()>
	import ${apiPackagePath}.model.${entity.name};
	import com.liferay.petra.function.UnsafeFunction;
	import com.liferay.portal.kernel.service.change.tracking.CTService;
	import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
</#if>

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
public class ${entity.name}${sessionTypeName}ServiceWrapper implements ${entity.name}${sessionTypeName}Service, ServiceWrapper<${entity.name}${sessionTypeName}Service> {

	<#if serviceBuilder.isVersionGTE_7_4_0()>
		public ${entity.name}${sessionTypeName}ServiceWrapper() {
			this(null);
		}
	</#if>

	public ${entity.name}${sessionTypeName}ServiceWrapper(${entity.name}${sessionTypeName}Service ${entity.variableName}${sessionTypeName}Service) {
		_${entity.variableName}${sessionTypeName}Service = ${entity.variableName}${sessionTypeName}Service;
	}

	<#list methods as method>
		<#if method.isPublic() && serviceBuilder.isCustomMethod(method)>
			${serviceBuilder.getJavadocComment(method)}

			<#if serviceBuilder.hasAnnotation(method, "Deprecated")>
				@Deprecated
			</#if>

			@Override
			public

			${serviceBuilder.getTypeParametersDefinition(method.typeParameters)} ${serviceBuilder.getTypeGenericsName(method.returns)} ${method.name}(

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

				${exception.fullyQualifiedName}

				<#if exception_has_next>
					,
				</#if>
			</#list>

			{
				<#if !stringUtil.equals(method.returns.value, "void")>
					return
				</#if>

				_${entity.variableName}${sessionTypeName}Service.${method.name}(

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

	<#if entity.hasPersistence() && stringUtil.equals(sessionTypeName, "Local") && entity.hasEntityColumns()>
		<#if entity.isChangeTrackingEnabled()>
			@Override
			public CTPersistence<${entity.name}> getCTPersistence() {
				return _${entity.variableName}LocalService.getCTPersistence();
			}

			@Override
			public Class<${entity.name}> getModelClass() {
				return _${entity.variableName}LocalService.getModelClass();
			}

			@Override
			public <R, E extends Throwable> R updateWithUnsafeFunction(UnsafeFunction<CTPersistence<${entity.name}>, R, E> updateUnsafeFunction) throws E {
				return _${entity.variableName}LocalService.updateWithUnsafeFunction(updateUnsafeFunction);
			}
		</#if>

		@Override
		public BasePersistence<?> getBasePersistence() {
			return _${entity.variableName}LocalService.getBasePersistence();
		}
	</#if>

	@Override
	public ${entity.name}${sessionTypeName}Service getWrappedService() {
		return _${entity.variableName}${sessionTypeName}Service;
	}

	@Override
	public void setWrappedService(${entity.name}${sessionTypeName}Service ${entity.variableName}${sessionTypeName}Service) {
		_${entity.variableName}${sessionTypeName}Service = ${entity.variableName}${sessionTypeName}Service;
	}

	private ${entity.name}${sessionTypeName}Service _${entity.variableName}${sessionTypeName}Service;

}