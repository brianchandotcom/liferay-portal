package ${packagePath}.service.persistence;

import ${packagePath}.model.${entity.name};

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import java.util.Date;
import java.util.List;

/**
 * The persistence utility for the ${entity.humanName} service. This utility wraps {@link ${entity.name}PersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author ${author}
 * @see ${entity.name}Persistence
 * @see ${entity.name}PersistenceImpl
 * @generated
 */
public class ${entity.name}Util {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(${entity.name} ${entity.varName}) {
		getPersistence().clearCache(${entity.varName});
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<${entity.name}> findWithDynamicQuery(DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<${entity.name}> findWithDynamicQuery(DynamicQuery dynamicQuery, int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<${entity.name}> findWithDynamicQuery(DynamicQuery dynamicQuery, int start, int end, OrderByComparator orderByComparator) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static ${entity.name} update(${entity.name} ${entity.varName}) throws SystemException {
		return getPersistence().update(${entity.varName});
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static ${entity.name} update(${entity.name} ${entity.varName}, ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(${entity.varName}, serviceContext);
	}

	<#list methods as method>
		<#if !method.isConstructor() && method.isPublic() && serviceBuilder.isCustomMethod(method) && !serviceBuilder.isBasePersistenceMethod(method)>
			${serviceBuilder.getJavadocComment(method)}
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

				${exception.value}

				<#if exception_has_next>
					,
				</#if>
			</#list>

			{
				<#if method.returns.value != "void">
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
		if (_persistence == null) {
			<#if pluginName != "">
				_persistence = (${entity.name}Persistence)PortletBeanLocatorUtil.locate(${packagePath}.service.ClpSerializer.getServletContextName(), ${entity.name}Persistence.class.getName());
			<#else>
				_persistence = (${entity.name}Persistence)PortalBeanLocatorUtil.locate(${entity.name}Persistence.class.getName());
			</#if>

			ReferenceRegistry.registerReference(${entity.name}Util.class, "_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(${entity.name}Persistence persistence) {
	}

	private static ${entity.name}Persistence _persistence;

}