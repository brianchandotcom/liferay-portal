package ${apiPackagePath}.service;

import ${serviceBuilder.getCompatJavaClassName("ProviderType")};

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.Base${sessionTypeName}Service;

<#if entity.isPermissionedModel()>
	import com.liferay.portal.kernel.service.PermissionedModelLocalService;
</#if>

import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.PersistedResourcedModelLocalService;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.portal.kernel.service.version.VersionService;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

<#list imports as import>
import ${import};
</#list>

<#if entity.versionEntity??>
	<#assign versionEntity = entity.versionEntity />

	import ${apiPackagePath}.model.${versionEntity.name};
</#if>

<#if stringUtil.equals(sessionTypeName, "Local")>
/**
 * Provides the local service interface for ${entity.name}. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author ${author}
 * @see ${entity.name}LocalServiceUtil
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
<#if classDeprecated>
 * @deprecated ${classDeprecatedComment}
</#if>
 * @generated
 */
</#if>

<#if classDeprecated>
	@Deprecated
</#if>

<#if entity.hasRemoteService() && !stringUtil.equals(sessionTypeName, "Local")>
	@AccessControlled

	<#if entity.isJsonEnabled()>
		@JSONWebService
	</#if>
</#if>

<#if !dependencyInjectorDS>
	<#if entity.hasRemoteService() && !stringUtil.equals(sessionTypeName, "Local") && osgiModule>
		@OSGiBeanProperties(
			property = {
				"json.web.service.context.name=${portletShortName?lower_case}",
				"json.web.service.context.path=${entity.name}"
			},
			service = ${entity.name}${sessionTypeName}Service.class
		)
	<#elseif stringUtil.equals(sessionTypeName, "Local") && entity.versionEntity??>
		<#assign versionEntity = entity.versionEntity />

		@OSGiBeanProperties(
			property = {
				"model.class.name=${apiPackagePath}.model.${entity.name}",
				"version.model.class.name=${apiPackagePath}.model.${versionEntity.name}"
			}
		)
	</#if>
</#if>

@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor = {PortalException.class, SystemException.class})
public interface ${entity.name}${sessionTypeName}Service
	extends Base${sessionTypeName}Service

	<#assign overrideMethodNames = [] />

	<#if stringUtil.equals(sessionTypeName, "Local") && entity.hasEntityColumns() && entity.hasPersistence()>
		<#if entity.isChangeTrackingEnabled()>
			, CTService<${entity.name}>
		</#if>

		<#if entity.isPermissionedModel()>
			, PermissionedModelLocalService
		<#elseif entity.isResourcedModel()>
			, PersistedModelLocalService
			, PersistedResourcedModelLocalService
		<#elseif entity.versionEntity??>
			<#assign versionEntity = entity.versionEntity />

			, PersistedModelLocalService
			, VersionService<${entity.name}, ${versionEntity.name}>

			<#assign overrideMethodNames = overrideMethodNames + ["checkout", "create", "delete", "deleteDraft", "deleteVersion", "fetchDraft", "fetchLatestVersion", "fetchPublished", "getDraft", "getVersion", "getVersions", "publishDraft", "registerListener", "unregisterListener", "updateDraft"] />
		<#else>
			, PersistedModelLocalService
		</#if>

		<#assign overrideMethodNames = overrideMethodNames + ["deletePersistedModel", "getPersistedModel"] />
	</#if>

	{

	/*
	 * NOTE FOR DEVELOPERS:
	 *
<#if stringUtil.equals(sessionTypeName, "Local")>
	 * Never modify or reference this interface directly. Always use {@link ${entity.name}LocalServiceUtil} to access the ${entity.humanName} local service. Add custom service methods to <code>${packagePath}.service.impl.${entity.name}LocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
<#else>
	 * Never modify or reference this interface directly. Always use {@link ${entity.name}ServiceUtil} to access the ${entity.humanName} remote service. Add custom service methods to <code>${packagePath}.service.impl.${entity.name}ServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
</#if>
	 */

	<#list methods as method>
		<#if !method.isStatic() && method.isPublic() && serviceBuilder.isCustomMethod(method)>
			${serviceBuilder.getJavadocComment(method)}

			<#list method.annotations as annotation>
				<#if !stringUtil.equals(annotation.type.name, "Override") && !stringUtil.equals(annotation.type.name, "SuppressWarnings")>
					${serviceBuilder.javaAnnotationToString(annotation)}
				</#if>
			</#list>

			<#if overrideMethodNames?seq_index_of(method.name) != -1>
				@Override
			</#if>

			<#if serviceBuilder.isServiceReadOnlyMethod(method, entity.txRequiredMethodNames) && !stringUtil.equals(method.name, "getOSGiServiceIdentifier")>
				@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
			</#if>
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

			<#if stringUtil.equals(sessionTypeName, "Local")>
				<#list method.exceptions as exception>
					<#if exception_index == 0>
						throws
					</#if>

					${exception.fullyQualifiedName}

					<#if exception_has_next>
						,
					</#if>
				</#list>
			<#else>
				<#list method.exceptions as exception>
					<#if exception_index == 0>
						throws
					</#if>

					${exception.fullyQualifiedName}

					<#if exception_has_next>
						,
					</#if>
				</#list>
			</#if>

			;
		</#if>
	</#list>

	<#if stringUtil.equals(sessionTypeName, "Local") && entity.hasEntityColumns() && entity.hasPersistence() && entity.isChangeTrackingEnabled()>
		@Transactional(enabled = false)
		@Override
		public CTPersistence<${entity.name}> getCTPersistence();

		@Transactional(enabled = false)
		@Override
		public Class<${entity.name}> getModelClass();

		@Transactional(rollbackFor = Throwable.class)
		@Override
		public <R, E extends Throwable> R updateWithUnsafeFunction(UnsafeFunction<CTPersistence<${entity.name}>, R, E> updateUnsafeFunction) throws E;
	</#if>
}