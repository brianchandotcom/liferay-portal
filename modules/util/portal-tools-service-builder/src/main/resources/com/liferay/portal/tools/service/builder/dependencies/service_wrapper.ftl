package ${apiPackagePath}.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

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

	public ${entity.name}${sessionTypeName}ServiceWrapper(${entity.name}${sessionTypeName}Service ${entity.varName}${sessionTypeName}Service) {
		_${entity.varName}${sessionTypeName}Service = ${entity.varName}${sessionTypeName}Service;
	}

	<#list methods as method>
		<#if method.isPublic() && serviceBuilder.isCustomMethod(method)>
			${serviceBuilder.getJavadocComment(method)}

			<#if serviceBuilder.hasAnnotation(method, "Deprecated")>
				@Deprecated
			</#if>

			@Override
			public

			<#if (method.name = "dslQuery" && (serviceBuilder.getTypeGenericsName(method.returns) == "T")) || (method.name = "dynamicQuery" && (serviceBuilder.getTypeGenericsName(method.returns) == "java.util.List<T>"))>
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

				${exception.fullyQualifiedName}

				<#if exception_has_next>
					,
				</#if>
			</#list>

			{
				<#if !stringUtil.equals(method.returns.value, "void")>
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

	<#if entity.isChangeTrackingEnabled() && stringUtil.equals(sessionTypeName, "Local")>
		@Override
		public CTPersistence<${entity.name}> getCTPersistence() {
			return _${entity.varName}LocalService.getCTPersistence();
		}

		@Override
		public Class<${entity.name}> getModelClass() {
			return _${entity.varName}LocalService.getModelClass();
		}

		@Override
		public <R, E extends Throwable> R updateWithUnsafeFunction(UnsafeFunction<CTPersistence<${entity.name}>, R, E> updateUnsafeFunction) throws E {
			return _${entity.varName}LocalService.updateWithUnsafeFunction(updateUnsafeFunction);
		}
	</#if>

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