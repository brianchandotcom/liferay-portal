package ${apiPackagePath}.service.persistence;

import ${apiPackagePath}.model.${entity.name};

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the ${entity.humanName} service. This utility wraps <code>${packagePath}.service.persistence.impl.${entity.name}PersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author ${author}
 * @see ${entity.name}Persistence
<#if classDeprecated>
 * @deprecated ${classDeprecatedComment}
</#if>
 * @generated
 */

<#if classDeprecated>
	@Deprecated
</#if>
public class ${entity.name}Util {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(${entity.name} ${entity.variableName}) {
		getPersistence().clearCache(${entity.variableName});
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, ${entity.name}> fetchByPrimaryKeys(Set<Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<${entity.name}> findWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<${entity.name}> findWithDynamicQuery(DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<${entity.name}> findWithDynamicQuery(DynamicQuery dynamicQuery, int start, int end, OrderByComparator<${entity.name}> orderByComparator) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ${entity.name} update(${entity.name} ${entity.variableName}) {
		return getPersistence().update(${entity.variableName});
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ${entity.name} update(${entity.name} ${entity.variableName}, ServiceContext serviceContext) {
		return getPersistence().update(${entity.variableName}, serviceContext);
	}

	<#list methods as method>
		<#if method.isPublic() && serviceBuilder.isCustomMethod(method) && !serviceBuilder.isBasePersistenceMethod(method)>
			${serviceBuilder.getJavadocComment(method)}

			<#if serviceBuilder.hasAnnotation(method, "Deprecated")>
				@Deprecated
			</#if>

			public static ${serviceBuilder.getTypeGenericsName(method.returns)} ${method.name} (

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

				getPersistence().${method.name}(

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

	public static ${entity.name}Persistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(${entity.name}Persistence persistence) {
		_persistence = persistence;
	}

	private static volatile ${entity.name}Persistence _persistence;

}