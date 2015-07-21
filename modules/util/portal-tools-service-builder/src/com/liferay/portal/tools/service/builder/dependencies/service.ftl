package ${packagePath}.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.service.Base${sessionTypeName}Service;
import com.liferay.portal.service.Invokable${sessionTypeName}Service;
import com.liferay.portal.service.PermissionedModelLocalService;
import com.liferay.portal.service.PersistedModelLocalService;

<#list imports as import>
import ${import};
</#list>

<#if sessionTypeName == "Local">
/**
 * Provides the local service interface for ${entity.name}. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author ${author}
 * @see ${entity.name}LocalServiceUtil
 * @see ${packagePath}.service.base.${entity.name}LocalServiceBaseImpl
 * @see ${packagePath}.service.impl.${entity.name}LocalServiceImpl
<#if classDeprecated>
 * @deprecated ${classDeprecatedComment}
</#if>
 * @generated
 */
<#else>
/**
 * Provides the remote service interface for ${entity.name}. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author ${author}
 * @see ${entity.name}ServiceUtil
 * @see ${packagePath}.service.base.${entity.name}ServiceBaseImpl
 * @see ${packagePath}.service.impl.${entity.name}ServiceImpl
<#if classDeprecated>
 * @deprecated ${classDeprecatedComment}
</#if>
 * @generated
 */
</#if>

<#if classDeprecated>
	@Deprecated
</#if>

<#if entity.hasRemoteService() && sessionTypeName != "Local">
	@AccessControlled
	@JSONWebService
</#if>

<#if entity.hasRemoteService() && sessionTypeName != "Local" && osgiModule>
	@OSGiBeanProperties(
		property = {
			"json.web.service.context.name=${portletShortName?lower_case}",
			"json.web.service.context.path=${entity.name}"
		},
		service = ${entity.name}${sessionTypeName}Service.class
	)
</#if>

@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor = {PortalException.class, SystemException.class})
public interface ${entity.name}${sessionTypeName}Service
	extends Base${sessionTypeName}Service

	<#assign overrideMethodNames = []>

	<#if pluginName != "">
		, Invokable${sessionTypeName}Service

		<#assign overrideMethodNames = overrideMethodNames + ["invokeMethod"]>
	</#if>

	<#if (sessionTypeName == "Local") && entity.hasColumns()>
		<#if entity.isPermissionedModel()>
			, PermissionedModelLocalService
		<#else>
			, PersistedModelLocalService
		</#if>

		<#assign overrideMethodNames = overrideMethodNames + ["deletePersistedModel", "getPersistedModel"]>
	</#if>

	{

	/*
	 * NOTE FOR DEVELOPERS:
	 *
<#if sessionTypeName == "Local">
	 * Never modify or reference this interface directly. Always use {@link ${entity.name}LocalServiceUtil} to access the ${entity.humanName} local service. Add custom service methods to {@link ${packagePath}.service.impl.${entity.name}LocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
<#else>
	 * Never modify or reference this interface directly. Always use {@link ${entity.name}ServiceUtil} to access the ${entity.humanName} remote service. Add custom service methods to {@link ${packagePath}.service.impl.${entity.name}ServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
</#if>
	 */

	<#list methods as method>
		<#if !method.isConstructor() && !method.isStatic() && method.isPublic() && serviceBuilder.isCustomMethod(method)>
			${serviceBuilder.getJavadocComment(method)}

			<#list method.annotations as annotation>
				<#if (annotation.type != "java.lang.Override") && (annotation.type != "java.lang.SuppressWarnings")>
					${serviceBuilder.annotationToString(annotation)}
				</#if>
			</#list>

			<#if overrideMethodNames?seq_index_of(method.name) != -1>
				@Override
			</#if>

			<#if serviceBuilder.isServiceReadOnlyMethod(method, entity.txRequiredList) && (method.name != "getBeanIdentifier")>
				@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
			</#if>
			public

			<#if method.name = "dynamicQuery" && (serviceBuilder.getTypeGenericsName(method.returns) == "java.util.List<T>")>
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

			<#if sessionTypeName == "Local">
				<#list method.exceptions as exception>
					<#if exception_index == 0>
						throws
					</#if>

					${exception.value}

					<#if exception_has_next>
						,
					</#if>
				</#list>
			<#else>
				<#list method.exceptions as exception>
					<#if exception_index == 0>
						throws
					</#if>

					${exception.value}

					<#if exception_has_next>
						,
					</#if>
				</#list>
			</#if>

			;
		</#if>
	</#list>

}