package ${packagePath}.service.http;

<#if hasHttpMethods>
	import ${packagePath}.service.${entity.name}ServiceUtil;
</#if>

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

/**
 * Provides the HTTP utility for the
 * {@link ${packagePath}.service.${entity.name}ServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link com.liferay.portal.security.auth.HttpPrincipal} parameter.
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author ${author}
 * @see ${entity.name}ServiceSoap
 * @see com.liferay.portal.security.auth.HttpPrincipal
 * @see ${packagePath}.service.${entity.name}ServiceUtil
<#if isDeprecated>
 * @deprecated ${deprecatedComment}
</#if>
 * @generated
 */
public class ${entity.name}ServiceHttp {

	<#assign hasMethods = false>

	<#list methods as method>
		<#if !method.isConstructor() && method.isPublic() && serviceBuilder.isCustomMethod(method)>
			<#assign hasMethods = true>

			<#assign returnTypeName = serviceBuilder.getTypeGenericsName(method.returns)>
			<#assign parameters = method.parameters>

			public static ${returnTypeName} ${method.name} (HttpPrincipal httpPrincipal

			<#list parameters as parameter>
				, ${serviceBuilder.getTypeGenericsName(parameter.type)} ${parameter.name}
			</#list>

			)
			throws

			<#if !method.exceptions?seq_contains("com.liferay.portal.kernel.exception.SystemException")>
				com.liferay.portal.kernel.exception.SystemException

				<#if method.exceptions?size gt 0>
					,
				</#if>
			</#if>

			<#list method.exceptions as exception>
				${exception.value}

				<#if exception_has_next>
					,
				</#if>
			</#list>

			{
				try {
					MethodKey methodKey = new MethodKey(${entity.name}ServiceUtil.class, "${method.name}", _${method.name}ParameterTypes${method_index});

					MethodHandler methodHandler = new MethodHandler(methodKey

					<#list parameters as parameter>
						, ${parameter.name}
					</#list>

					);

					<#if returnTypeName != "void">
						Object returnObj = null;
					</#if>

					try {
						<#if returnTypeName != "void">
							returnObj =
						</#if>

						TunnelUtil.invoke(httpPrincipal, methodHandler);
					}
					catch (Exception e) {
						<#list method.exceptions as exception>
							if (e instanceof ${exception.value}) {
								throw (${exception.value})e;
							}
						</#list>

						throw new com.liferay.portal.kernel.exception.SystemException(e);
					}

					<#if returnTypeName != "void">
						<#if returnTypeName == "boolean">
							return ((Boolean)returnObj).booleanValue();
						<#elseif returnTypeName == "double">
							return ((Double)returnObj).doubleValue();
						<#elseif returnTypeName == "float">
							return ((Float)returnObj).floatValue();
						<#elseif returnTypeName == "int">
							return ((Integer)returnObj).intValue();
						<#elseif returnTypeName == "long">
							return ((Long)returnObj).longValue();
						<#elseif returnTypeName == "short">
							return ((Short)returnObj).shortValue();
						<#elseif returnTypeName == "java.lang.Object">
							return returnObj;
						<#else>
							return (${returnTypeName})returnObj;
						</#if>
					</#if>
				}
				catch (com.liferay.portal.kernel.exception.SystemException se) {
					_log.error(se, se);

					throw se;
				}
			}
		</#if>
	</#list>

	<#if hasMethods>
		private static Log _log = LogFactoryUtil.getLog(${entity.name}ServiceHttp.class);
	</#if>

	<#list methods as method>
		<#if !method.isConstructor() && method.isPublic() && serviceBuilder.isCustomMethod(method)>
			<#assign parameters = method.parameters>

			private static final Class<?>[] _${method.name}ParameterTypes${method_index} = new Class[] {

			<#list parameters as parameter>
				${serviceBuilder.getLiteralClass(parameter.type)}

				<#if parameter_has_next>
					,
				</#if>
			</#list>

			};
		</#if>
	</#list>

}