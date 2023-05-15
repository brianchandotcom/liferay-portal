package ${apiPackagePath}.service;

<#if entity.hasEntityColumns()>
	import ${apiPackagePath}.model.${entity.name};
</#if>

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.InputStream;
import java.io.Serializable;

import java.sql.Blob;

import java.util.List;
import java.util.Map;
import java.util.Set;

<#if stringUtil.equals(sessionTypeName, "Local")>
/**
 * Provides the local service utility for ${entity.name}. This utility wraps
 * <code>${packagePath}.service.impl.${entity.name}LocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author ${author}
 * @see ${entity.name}LocalService
<#if classDeprecated>
 * @deprecated ${classDeprecatedComment}
</#if>
 * @generated
 */
<#else>
/**
 * Provides the remote service utility for ${entity.name}. This utility wraps
 * <code>${packagePath}.service.impl.${entity.name}ServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author ${author}
 * @see ${entity.name}Service
<#if classDeprecated>
 * @deprecated ${classDeprecatedComment}
</#if>
 * @generated
 */
</#if>

<#if classDeprecated>
	@Deprecated
</#if>
public class ${entity.name}${sessionTypeName}ServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>${packagePath}.service.impl.${entity.name}${sessionTypeName}ServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	<#list methods as method>
		<#if !method.isStatic() && method.isPublic() && serviceBuilder.isCustomMethod(method)>
			${serviceBuilder.getJavadocComment(method)}

			<#if serviceBuilder.hasAnnotation(method, "Deprecated")>
				@Deprecated
			</#if>
			public static

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

				getService().${method.name}(

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

	<#if validator.isNotNull(pluginName)>
		public static void clearService() {
			_service = null;
		}
	</#if>

	public static ${entity.name}${sessionTypeName}Service getService() {
		return _service;
	}

	public static void setService(${entity.name}${sessionTypeName}Service service) {
		_service = service;
	}

	private static volatile ${entity.name}${sessionTypeName}Service _service;

}