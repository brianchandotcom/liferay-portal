package ${packagePath}.model;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.ModelWrapper;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

/**
 * <p>
 * This class is a wrapper for {@link ${entity.name}}.
 * </p>
 *
 * @author    ${author}
 * @see       ${entity.name}
 * @generated
 */
public class ${entity.name}Wrapper implements ${entity.name}, ModelWrapper<${entity.name}> {

	public ${entity.name}Wrapper(${entity.name} ${entity.varName}) {
		_${entity.varName} = ${entity.varName};
	}

	public Class<?> getModelClass() {
		return ${entity.name}.class;
	}

	public String getModelClassName() {
		return ${entity.name}.class.getName();
	}

	<#list methods as method>
		<#if !method.isConstructor() && !method.isStatic() && method.isPublic() && !serviceBuilder.isDuplicateMethod(method, tempMap)>
			<#assign parameters = method.parameters>

			${serviceBuilder.getJavadocComment(method)}
			<#if (method.name == "clone" || method.name == "hashCode" || method.name == "toString") && (parameters?size == 0)>
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

			{
				<#if method.name == "clone" && (parameters?size == 0)>
					return new ${entity.name}Wrapper((${entity.name})_${entity.varName}.clone());
				<#elseif method.name == "toEscapedModel" && (parameters?size == 0)>
					return new ${entity.name}Wrapper(_${entity.varName}.toEscapedModel());
				<#else>
					<#if method.returns.value != "void">
						return
					</#if>

					_${entity.varName}.${method.name}(

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

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public ${entity.name} getWrapped${entity.name}() {
		return _${entity.varName};
	}

	public ${entity.name} getWrappedModel() {
		return _${entity.varName};
	}

	public void resetOriginalValues() {
		_${entity.varName}.resetOriginalValues();
	}

	private ${entity.name} _${entity.varName};

}