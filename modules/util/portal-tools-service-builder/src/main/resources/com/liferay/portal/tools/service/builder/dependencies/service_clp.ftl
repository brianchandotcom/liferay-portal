package ${apiPackagePath}.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.Invokable${sessionTypeName}Service;

/**
 * @author ${author}
<#if classDeprecated>
 * @deprecated ${classDeprecatedComment}
</#if>
 * @generated
 */

<#if classDeprecated>
	@Deprecated
</#if>

@ProviderType
public class ${entity.name}${sessionTypeName}ServiceClp implements ${entity.name}${sessionTypeName}Service {

	public ${entity.name}${sessionTypeName}ServiceClp(Invokable${sessionTypeName}Service invokable${sessionTypeName}Service) {
		_invokable${sessionTypeName}Service = invokable${sessionTypeName}Service;

		<#list methods as method>
			<#if !method.isConstructor() && method.isPublic() && serviceBuilder.isCustomMethod(method) && !stringUtil.equals(method.name, "invokeMethod")>
				<#assign parameters = method.parameters />

				_methodName${method_index} = "${method.name}";

				_methodParameterTypes${method_index} = new String[] {

				<#list parameters as parameter>
					"${parameter.type}${serviceBuilder.getDimensions(parameter.type.dimensions)}"

					<#if parameter_has_next>
						,
					</#if>
				</#list>

				};
			</#if>
		</#list>
	}

	<#list methods as method>
		<#if !method.isConstructor() && method.isPublic() && serviceBuilder.isCustomMethod(method)>
			<#assign
				returnTypeName = serviceBuilder.getTypeGenericsName(method.returns)
				parameters = method.parameters
			/>

			@Override
			public

			<#if method.name = "dynamicQuery" && (returnTypeName == "java.util.List<T>")>
				<T>
			</#if>

			${returnTypeName} ${method.name}(

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
				<#if method.name = "invokeMethod">
					throw new UnsupportedOperationException();
				<#else>
					<#if !stringUtil.equals(returnTypeName, "void")>
						Object returnObj = null;
					</#if>

					try {
						<#if !stringUtil.equals(returnTypeName, "void")>
							returnObj =
						</#if>

						_invokable${sessionTypeName}Service.invokeMethod(
							_methodName${method_index},
							_methodParameterTypes${method_index},

							new Object[] {

							<#list parameters as parameter>
								<#assign parameterTypeName = serviceBuilder.getTypeGenericsName(parameter.type) />

								<#if stringUtil.equals(parameterTypeName, "boolean") || stringUtil.equals(parameterTypeName, "double") || stringUtil.equals(parameterTypeName, "float") || stringUtil.equals(parameterTypeName, "int") || stringUtil.equals(parameterTypeName, "long") || stringUtil.equals(parameterTypeName, "short")>
									${parameter.name}
								<#else>
									ClpSerializer.translateInput(${parameter.name})
								</#if>

								<#if parameter_has_next>
									,
								</#if>
							</#list>

							}
						);
					}
					catch (Throwable t) {
						t = ClpSerializer.translateThrowable(t);

						<#list method.exceptions as exception>
							if (t instanceof ${exception.value}) {
								throw (${exception.value})t;
							}
						</#list>

						if (t instanceof RuntimeException) {
							throw (RuntimeException)t;
						}
						else {
							throw new RuntimeException(t.getClass().getName() + " is not a valid exception");
						}
					}

					<#if !stringUtil.equals(returnTypeName, "void")>
						<#if stringUtil.equals(returnTypeName, "boolean")>
							return ((Boolean)returnObj).booleanValue();
						<#elseif stringUtil.equals(returnTypeName, "double")>
							return ((Double)returnObj).doubleValue();
						<#elseif stringUtil.equals(returnTypeName, "float")>
							return ((Float)returnObj).floatValue();
						<#elseif stringUtil.equals(returnTypeName, "int")>
							return ((Integer)returnObj).intValue();
						<#elseif stringUtil.equals(returnTypeName, "long")>
							return ((Long)returnObj).longValue();
						<#elseif stringUtil.equals(returnTypeName, "short")>
							return ((Short)returnObj).shortValue();
						<#else>
							return (${returnTypeName})ClpSerializer.translateOutput(returnObj);
						</#if>
					</#if>
				</#if>
			}
		</#if>
	</#list>

	private Invokable${sessionTypeName}Service _invokable${sessionTypeName}Service;

	<#list methods as method>
		<#if !method.isConstructor() && method.isPublic() && serviceBuilder.isCustomMethod(method) && !stringUtil.equals(method.name, "invokeMethod")>
			<#assign parameters = method.parameters />

			private String _methodName${method_index};
			private String[] _methodParameterTypes${method_index};
		</#if>
	</#list>

}