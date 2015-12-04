<#if entity.isHierarchicalTree()>
	<#if entity.hasColumn("groupId")>
		<#assign scopeColumn = entity.getColumn("groupId")>
	<#else>
		<#assign scopeColumn = entity.getColumn("companyId")>
	</#if>

	<#assign pkColumn = entity.getPKList()?first>
</#if>

<#assign finderFieldSQLSuffix = "_SQL">

package ${packagePath}.service.persistence.impl;

<#assign noSuchEntity = serviceBuilder.getNoSuchEntityException(entity)>

<#if osgiModule>
	import ${packagePath}.exception.${noSuchEntity}Exception;
<#else>
	import ${packagePath}.${noSuchEntity}Exception;
</#if>

import ${packagePath}.model.${entity.name};
import ${packagePath}.model.impl.${entity.name}Impl;
import ${packagePath}.model.impl.${entity.name}ModelImpl;
import ${packagePath}.service.persistence.${entity.name}Persistence;

<#if entity.hasCompoundPK()>
	import ${packagePath}.service.persistence.${entity.PKClassName};
</#if>

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQuery;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQueryFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.RowMapper;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.MVCCModel;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.persistence.CompanyProvider;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.service.persistence.impl.NestedSetsTreeManager;
import com.liferay.portal.service.persistence.impl.PersistenceNestedSetsTreeManager;
import com.liferay.portal.service.persistence.impl.TableMapper;
import com.liferay.portal.service.persistence.impl.TableMapperFactory;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

<#list referenceList as tempEntity>
	<#if tempEntity.hasColumns() && (entity.name == "Counter" || tempEntity.name != "Counter")>
		import ${tempEntity.packagePath}.service.persistence.${tempEntity.name}Persistence;
	</#if>
</#list>

/**
 * The persistence implementation for the ${entity.humanName} service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author ${author}
 * @see ${entity.name}Persistence
 * @see ${packagePath}.service.persistence.${entity.name}Util
<#if classDeprecated>
 * @deprecated ${classDeprecatedComment}
</#if>
 * @generated
 */

<#if classDeprecated>
	@Deprecated
</#if>

@ProviderType
public class ${entity.name}PersistenceImpl extends BasePersistenceImpl<${entity.name}> implements ${entity.name}Persistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ${entity.name}Util} to access the ${entity.humanName} persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	public static final String FINDER_CLASS_NAME_ENTITY = ${entity.name}Impl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY + ".List2";

	<#assign columnBitmaskEnabled = (entity.finderColumnsList?size &gt; 0) && (entity.finderColumnsList?size &lt; 64)>

	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(
		${entity.name}ModelImpl.ENTITY_CACHE_ENABLED,
		${entity.name}ModelImpl.FINDER_CACHE_ENABLED,
		${entity.name}Impl.class,
		FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
		"findAll",
		new String[0]);

	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(
		${entity.name}ModelImpl.ENTITY_CACHE_ENABLED,
		${entity.name}ModelImpl.FINDER_CACHE_ENABLED,
		${entity.name}Impl.class,
		FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
		"findAll",
		new String[0]);

	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(
		${entity.name}ModelImpl.ENTITY_CACHE_ENABLED,
		${entity.name}ModelImpl.FINDER_CACHE_ENABLED,
		Long.class,
		FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
		"countAll",
		new String[0]);

	<#if entity.isHierarchicalTree()>
		public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_ANCESTORS = new FinderPath(
			${entity.name}ModelImpl.ENTITY_CACHE_ENABLED,
			${entity.name}ModelImpl.FINDER_CACHE_ENABLED,
			Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"countAncestors",
			new String[] {Long.class.getName(), Long.class.getName(), Long.class.getName()});

		public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_DESCENDANTS = new FinderPath(
			${entity.name}ModelImpl.ENTITY_CACHE_ENABLED,
			${entity.name}ModelImpl.FINDER_CACHE_ENABLED,
			Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"countDescendants",
			new String[] {Long.class.getName(), Long.class.getName(), Long.class.getName()});

		public static final FinderPath FINDER_PATH_WITH_PAGINATION_GET_ANCESTORS = new FinderPath(
			${entity.name}ModelImpl.ENTITY_CACHE_ENABLED,
			${entity.name}ModelImpl.FINDER_CACHE_ENABLED,
			${entity.name}Impl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"getAncestors",
			new String[] {Long.class.getName(), Long.class.getName(), Long.class.getName()});

		public static final FinderPath FINDER_PATH_WITH_PAGINATION_GET_DESCENDANTS = new FinderPath(
			${entity.name}ModelImpl.ENTITY_CACHE_ENABLED,
			${entity.name}ModelImpl.FINDER_CACHE_ENABLED,
			${entity.name}Impl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"getDescendants",
			new String[] {Long.class.getName(), Long.class.getName(), Long.class.getName()});
	</#if>

	<#list entity.getFinderList() as finder>
		<#include "persistence_impl_finder_finder_path.ftl">

		<#include "persistence_impl_finder_find.ftl">

		<#include "persistence_impl_finder_remove.ftl">

		<#include "persistence_impl_finder_count.ftl">

		<#include "persistence_impl_finder_fields.ftl">
	</#list>

	public ${entity.name}PersistenceImpl() {
		setModelClass(${entity.name}.class);
	}

	/**
	 * Caches the ${entity.humanName} in the entity cache if it is enabled.
	 *
	 * @param ${entity.varName} the ${entity.humanName}
	 */
	@Override
	public void cacheResult(${entity.name} ${entity.varName}) {
		entityCache.putResult(${entity.name}ModelImpl.ENTITY_CACHE_ENABLED, ${entity.name}Impl.class, ${entity.varName}.getPrimaryKey(), ${entity.varName});

		<#list entity.getUniqueFinderList() as finder>
			<#assign finderColsList = finder.getColumns()>

			finderCache.putResult(
				FINDER_PATH_FETCH_BY_${finder.name?upper_case},
				new Object[] {
					<#list finderColsList as finderCol>
						${entity.varName}.get${finderCol.methodName}()

						<#if finderCol_has_next>
							,
						</#if>
					</#list>
				},
				${entity.varName});
		</#list>

		${entity.varName}.resetOriginalValues();
	}

	/**
	 * Caches the ${entity.humanNames} in the entity cache if it is enabled.
	 *
	 * @param ${entity.varNames} the ${entity.humanNames}
	 */
	@Override
	public void cacheResult(List<${entity.name}> ${entity.varNames}) {
		for (${entity.name} ${entity.varName} : ${entity.varNames}) {
			if (entityCache.getResult(${entity.name}ModelImpl.ENTITY_CACHE_ENABLED, ${entity.name}Impl.class, ${entity.varName}.getPrimaryKey()) == null) {
				cacheResult(${entity.varName});
			}
			else {
				${entity.varName}.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all ${entity.humanNames}.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(${entity.name}Impl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the ${entity.humanName}.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(${entity.name} ${entity.varName}) {
		entityCache.removeResult(${entity.name}ModelImpl.ENTITY_CACHE_ENABLED, ${entity.name}Impl.class, ${entity.varName}.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		<#if entity.getUniqueFinderList()?size &gt; 0>
			clearUniqueFindersCache((${entity.name}ModelImpl)${entity.varName});
		</#if>
	}

	@Override
	public void clearCache(List<${entity.name}> ${entity.varNames}) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (${entity.name} ${entity.varName} : ${entity.varNames}) {
			entityCache.removeResult(${entity.name}ModelImpl.ENTITY_CACHE_ENABLED, ${entity.name}Impl.class, ${entity.varName}.getPrimaryKey());

			<#if entity.getUniqueFinderList()?size &gt; 0>
				clearUniqueFindersCache((${entity.name}ModelImpl)${entity.varName});
			</#if>
		}
	}

	<#if entity.getUniqueFinderList()?size &gt; 0>
		protected void cacheUniqueFindersCache(${entity.name}ModelImpl ${entity.varName}ModelImpl, boolean isNew) {
			if (isNew) {
				<#list entity.getUniqueFinderList() as finder>
					<#assign finderColsList = finder.getColumns()>

					<#if finder_index == 0>
						Object[]
					</#if>
					args = new Object[] {
						<#list finderColsList as finderCol>
							${entity.varName}ModelImpl.get${finderCol.methodName}()

							<#if finderCol_has_next>
								,
							</#if>
						</#list>
					};

					finderCache.putResult(FINDER_PATH_COUNT_BY_${finder.name?upper_case}, args, Long.valueOf(1));
					finderCache.putResult(FINDER_PATH_FETCH_BY_${finder.name?upper_case}, args, ${entity.varName}ModelImpl);
				</#list>
			}
			else {
				<#list entity.getUniqueFinderList() as finder>
					<#assign finderColsList = finder.getColumns()>

					if ((${entity.varName}ModelImpl.getColumnBitmask() & FINDER_PATH_FETCH_BY_${finder.name?upper_case}.getColumnBitmask()) != 0) {
						Object[] args = new Object[] {
							<#list finderColsList as finderCol>
								${entity.varName}ModelImpl.get${finderCol.methodName}()

								<#if finderCol_has_next>
									,
								</#if>
							</#list>
						};

						finderCache.putResult(FINDER_PATH_COUNT_BY_${finder.name?upper_case}, args, Long.valueOf(1));
						finderCache.putResult(FINDER_PATH_FETCH_BY_${finder.name?upper_case}, args, ${entity.varName}ModelImpl);
					}
				</#list>
			}
		}

		protected void clearUniqueFindersCache(${entity.name}ModelImpl ${entity.varName}ModelImpl) {
			<#list entity.getUniqueFinderList() as finder>
				<#assign finderColsList = finder.getColumns()>

				<#if finder_index == 0>
					Object[]
				</#if>
				args = new Object[] {
					<#list finderColsList as finderCol>
						${entity.varName}ModelImpl.get${finderCol.methodName}()

						<#if finderCol_has_next>
							,
						</#if>
					</#list>
				};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_${finder.name?upper_case}, args);
				finderCache.removeResult(FINDER_PATH_FETCH_BY_${finder.name?upper_case}, args);

				if ((${entity.varName}ModelImpl.getColumnBitmask() & FINDER_PATH_FETCH_BY_${finder.name?upper_case}.getColumnBitmask()) != 0) {
					args = new Object[] {
						<#list finderColsList as finderCol>
							${entity.varName}ModelImpl.getOriginal${finderCol.methodName}()

							<#if finderCol_has_next>
								,
							</#if>
						</#list>
					};

					finderCache.removeResult(FINDER_PATH_COUNT_BY_${finder.name?upper_case}, args);
					finderCache.removeResult(FINDER_PATH_FETCH_BY_${finder.name?upper_case}, args);
				}
			</#list>
		}
	</#if>

	/**
	 * Creates a new ${entity.humanName} with the primary key. Does not add the ${entity.humanName} to the database.
	 *
	 * @param ${entity.PKVarName} the primary key for the new ${entity.humanName}
	 * @return the new ${entity.humanName}
	 */
	@Override
	public ${entity.name} create(${entity.PKClassName} ${entity.PKVarName}) {
		${entity.name} ${entity.varName} = new ${entity.name}Impl();

		${entity.varName}.setNew(true);
		${entity.varName}.setPrimaryKey(${entity.PKVarName});

		<#if entity.hasUuid()>
			String uuid = PortalUUIDUtil.generate();

			${entity.varName}.setUuid(uuid);
		</#if>

		return ${entity.varName};
	}

	/**
	 * Removes the ${entity.humanName} with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ${entity.PKVarName} the primary key of the ${entity.humanName}
	 * @return the ${entity.humanName} that was removed
	 * @throws ${packagePath}.${noSuchEntity}Exception if a ${entity.humanName} with the primary key could not be found
	 */
	@Override
	public ${entity.name} remove(${entity.PKClassName} ${entity.PKVarName}) throws ${noSuchEntity}Exception {
		return remove((Serializable)${entity.PKVarName});
	}

	/**
	 * Removes the ${entity.humanName} with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the ${entity.humanName}
	 * @return the ${entity.humanName} that was removed
	 * @throws ${packagePath}.${noSuchEntity}Exception if a ${entity.humanName} with the primary key could not be found
	 */
	@Override
	public ${entity.name} remove(Serializable primaryKey) throws ${noSuchEntity}Exception {
		Session session = null;

		try {
			session = openSession();

			${entity.name} ${entity.varName} = (${entity.name})session.get(${entity.name}Impl.class, primaryKey);

			if (${entity.varName} == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new ${noSuchEntity}Exception(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(${entity.varName});
		}
		catch (${noSuchEntity}Exception nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected ${entity.name} removeImpl(${entity.name} ${entity.varName}) {
		${entity.varName} = toUnwrappedModel(${entity.varName});

		<#list entity.columnList as column>
			<#if column.isCollection() && column.isMappingManyToMany()>
				<#assign tempEntity = serviceBuilder.getEntity(column.getEJBName())>

				${entity.varName}To${tempEntity.name}TableMapper.deleteLeftPrimaryKeyTableMappings(0, ${entity.varName}.getPrimaryKey());
			</#if>
		</#list>

		Session session = null;

		try {
			session = openSession();

			<#if entity.isHierarchicalTree()>
				if (rebuildTreeEnabled) {
					if (session.isDirty()) {
						session.flush();
					}

					nestedSetsTreeManager.delete(${entity.varName});

					clearCache();

					session.clear();
				}
			</#if>

			if (!session.contains(${entity.varName})) {
				${entity.varName} = (${entity.name})session.get(${entity.name}Impl.class, ${entity.varName}.getPrimaryKeyObj());
			}

			if (${entity.varName} != null) {
				session.delete(${entity.varName});
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (${entity.varName} != null) {
			clearCache(${entity.varName});
		}

		return ${entity.varName};
	}

	@Override
	public ${entity.name} updateImpl(${packagePath}.model.${entity.name} ${entity.varName}) {
		${entity.varName} = toUnwrappedModel(${entity.varName});

		boolean isNew = ${entity.varName}.isNew();

		<#assign collectionFinderList = entity.getCollectionFinderList()>

		<#assign castEntityModelImpl = false>

		<#if entity.isHierarchicalTree()>
			<#assign castEntityModelImpl = true>
		</#if>

		<#if collectionFinderList?size != 0>
			<#list collectionFinderList as finder>
				<#if !finder.hasCustomComparator()>
					<#assign castEntityModelImpl = true>
				</#if>
			</#list>
		</#if>

		<#assign uniqueFinderList = entity.getUniqueFinderList()>

		<#if uniqueFinderList?size &gt; 0>
			<#assign castEntityModelImpl = true>
		</#if>

		<#if entity.hasColumn("createDate", "Date") && entity.hasColumn("modifiedDate", "Date")>
			<#assign castEntityModelImpl = true>
		</#if>

		<#if castEntityModelImpl>
			${entity.name}ModelImpl ${entity.varName}ModelImpl = (${entity.name}ModelImpl)${entity.varName};
		</#if>

		<#if entity.hasUuid()>
			if (Validator.isNull(${entity.varName}.getUuid())) {
				String uuid = PortalUUIDUtil.generate();

				${entity.varName}.setUuid(uuid);
			}
		</#if>

		<#if entity.hasColumn("createDate", "Date") && entity.hasColumn("modifiedDate", "Date")>
			ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

			Date now = new Date();

			if (isNew && (${entity.varName}.getCreateDate() == null)) {
				if (serviceContext == null) {
					${entity.varName}.setCreateDate(now);
				}
				else {
					${entity.varName}.setCreateDate(serviceContext.getCreateDate(now));
				}
			}

			if (!${entity.varName}ModelImpl.hasSetModifiedDate()) {
				if (serviceContext == null) {
					${entity.varName}.setModifiedDate(now);
				}
				else {
					${entity.varName}.setModifiedDate(serviceContext.getModifiedDate(now));
				}
			}
		</#if>

		<#assign sanitizeTuples = modelHintsUtil.getSanitizeTuples("${packagePath}.model.${entity.name}")>

		<#if sanitizeTuples?size != 0>
			long userId = GetterUtil.getLong(PrincipalThreadLocal.getName());

			if (userId > 0) {
				<#assign companyId = 0>

				<#if entity.hasColumn("companyId")>
					long companyId = ${entity.varName}.getCompanyId();
				<#else>
					long companyId = 0;
				</#if>

				<#if entity.hasColumn("groupId")>
					long groupId = ${entity.varName}.getGroupId();
				<#else>
					long groupId = 0;
				</#if>

				long ${entity.PKVarName} = 0;

				if (!isNew) {
					${entity.PKVarName} = ${entity.varName}.getPrimaryKey();
				}

				try {
					<#list sanitizeTuples as sanitizeTuple>
						<#assign colMethodName = textFormatter.format(sanitizeTuple.getObject(0), 6)>

						<#assign contentType = "\"" + sanitizeTuple.getObject(1) + "\"">

						<#if contentType == "\"text/html\"">
							<#assign contentType = "ContentTypes.TEXT_HTML">
						<#elseif contentType == "\"text/plain\"">
							<#assign contentType = "ContentTypes.TEXT_PLAIN">
						</#if>

						<#assign modes = "\"" + sanitizeTuple.getObject(2) + "\"">

						<#if modes == "\"ALL\"">
							<#assign modes = "Sanitizer.MODE_ALL">
						<#elseif modes == "\"BAD_WORDS\"">
							<#assign modes = "Sanitizer.MODE_BAD_WORDS">
						<#elseif modes == "\"XSS\"">
							<#assign modes = "Sanitizer.MODE_XSS">
						<#else>
							<#assign modes = "StringUtil.split(\"" + sanitizeTuple.getObject(2) + "\")">
						</#if>

						${entity.varName}.set${colMethodName}(SanitizerUtil.sanitize(companyId, groupId, userId, ${packagePath}.model.${entity.name}.class.getName(), ${entity.PKVarName}, ${contentType}, ${modes}, ${entity.varName}.get${colMethodName}(), null));
					</#list>
				}
				catch (SanitizerException se) {
					throw new SystemException(se);
				}
			}
		</#if>

		Session session = null;

		try {
			session = openSession();

			<#if entity.isHierarchicalTree()>
				if (rebuildTreeEnabled) {
					if (session.isDirty()) {
						session.flush();
					}

					if (isNew) {
						nestedSetsTreeManager.insert(${entity.varName}, fetchByPrimaryKey(${entity.varName}.getParent${pkColumn.methodName}()));
					}
					else if (${entity.varName}.getParent${pkColumn.methodName}() != ${entity.varName}ModelImpl.getOriginalParent${pkColumn.methodName}()){
						nestedSetsTreeManager.move(${entity.varName}, fetchByPrimaryKey(${entity.varName}ModelImpl.getOriginalParent${pkColumn.methodName}()), fetchByPrimaryKey(${entity.varName}.getParent${pkColumn.methodName}()));
					}

					clearCache();

					session.clear();
				}
			</#if>

			if (${entity.varName}.isNew()) {
				session.save(${entity.varName});

				${entity.varName}.setNew(false);
			}
			else {
				<#if entity.hasLazyBlobColumn()>

					<#-- Workaround for HHH-2680 -->

					session.evict(${entity.varName});
					session.saveOrUpdate(${entity.varName});
				<#else>
					${entity.varName} = (${entity.name})session.merge(${entity.varName});
				</#if>
			}

			<#if entity.hasLazyBlobColumn()>
				session.flush();
				session.clear();
			</#if>
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew
			<#if columnBitmaskEnabled>
				|| !${entity.name}ModelImpl.COLUMN_BITMASK_ENABLED
			</#if>
			) {

			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		<#if collectionFinderList?size != 0>
			<#assign hasEqualComparator = false>

			<#list collectionFinderList as finder>
				<#assign finderColsList = finder.getColumns()>

				<#if !finder.hasCustomComparator()>
					<#if !hasEqualComparator>
						<#assign hasEqualComparator = true>

						else {
					</#if>

					if (
						<#if columnBitmaskEnabled>
							(${entity.varName}ModelImpl.getColumnBitmask() & FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_${finder.name?upper_case}.getColumnBitmask()) != 0
						<#else>
							<#list finderColsList as finderCol>
								<#if finderCol.isPrimitiveType()>
									(${entity.varName}.get${finderCol.methodName}() != ${entity.varName}ModelImpl.getOriginal${finderCol.methodName}())
								<#else>
									!Validator.equals(${entity.varName}.get${finderCol.methodName}(), ${entity.varName}ModelImpl.getOriginal${finderCol.methodName}())
								</#if>

								<#if finderCol_has_next>
									||
								</#if>
							</#list>
						</#if>
						) {

						Object[] args = new Object[] {
							<#list finderColsList as finderCol>
								${entity.varName}ModelImpl.getOriginal${finderCol.methodName}()

								<#if finderCol_has_next>
									,
								</#if>
							</#list>
						};

						finderCache.removeResult(FINDER_PATH_COUNT_BY_${finder.name?upper_case}, args);
						finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_${finder.name?upper_case}, args);

						args = new Object[] {
							<#list finderColsList as finderCol>
								${entity.varName}ModelImpl.get${finderCol.methodName}()

								<#if finderCol_has_next>
									,
								</#if>
							</#list>
						};

						finderCache.removeResult(FINDER_PATH_COUNT_BY_${finder.name?upper_case}, args);
						finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_${finder.name?upper_case}, args);
					}
				</#if>
			</#list>

			<#if hasEqualComparator>
				}
			</#if>
		</#if>

		entityCache.putResult(${entity.name}ModelImpl.ENTITY_CACHE_ENABLED, ${entity.name}Impl.class, ${entity.varName}.getPrimaryKey(), ${entity.varName}, false);

		<#if uniqueFinderList?size &gt; 0>
			clearUniqueFindersCache(${entity.varName}ModelImpl);
			cacheUniqueFindersCache(${entity.varName}ModelImpl, isNew);
		</#if>

		${entity.varName}.resetOriginalValues();

		return ${entity.varName};
	}

	protected ${entity.name} toUnwrappedModel(${entity.name} ${entity.varName}) {
		if (${entity.varName} instanceof ${entity.name}Impl) {
			return ${entity.varName};
		}

		${entity.name}Impl ${entity.varName}Impl = new ${entity.name}Impl();

		${entity.varName}Impl.setNew(${entity.varName}.isNew());
		${entity.varName}Impl.setPrimaryKey(${entity.varName}.getPrimaryKey());

		<#list entity.regularColList as column>
			${entity.varName}Impl.set${column.methodName}(

			<#if column.type == "boolean">
				${entity.varName}.is${column.methodName}()
			<#else>
				${entity.varName}.get${column.methodName}()
			</#if>

			);
		</#list>

		return ${entity.varName}Impl;
	}

	/**
	 * Returns the ${entity.humanName} with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the ${entity.humanName}
	 * @return the ${entity.humanName}
	 * @throws ${packagePath}.${noSuchEntity}Exception if a ${entity.humanName} with the primary key could not be found
	 */
	@Override
	public ${entity.name} findByPrimaryKey(Serializable primaryKey) throws ${noSuchEntity}Exception {
		${entity.name} ${entity.varName} = fetchByPrimaryKey(primaryKey);

		if (${entity.varName} == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new ${noSuchEntity}Exception(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return ${entity.varName};
	}

	/**
	 * Returns the ${entity.humanName} with the primary key or throws a {@link ${packagePath}.${noSuchEntity}Exception} if it could not be found.
	 *
	 * @param ${entity.PKVarName} the primary key of the ${entity.humanName}
	 * @return the ${entity.humanName}
	 * @throws ${packagePath}.${noSuchEntity}Exception if a ${entity.humanName} with the primary key could not be found
	 */
	@Override
	public ${entity.name} findByPrimaryKey(${entity.PKClassName} ${entity.PKVarName}) throws ${noSuchEntity}Exception {
		return findByPrimaryKey((Serializable)${entity.PKVarName});
	}

	/**
	 * Returns the ${entity.humanName} with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ${entity.humanName}
	 * @return the ${entity.humanName}, or <code>null</code> if a ${entity.humanName} with the primary key could not be found
	 */
	@Override
	public ${entity.name} fetchByPrimaryKey(Serializable primaryKey) {
		${entity.name} ${entity.varName} = (${entity.name})entityCache.getResult(${entity.name}ModelImpl.ENTITY_CACHE_ENABLED, ${entity.name}Impl.class, primaryKey);

		if (${entity.varName} == _null${entity.name}) {
			return null;
		}

		if (${entity.varName} == null) {
			Session session = null;

			try {
				session = openSession();

				${entity.varName} = (${entity.name})session.get(${entity.name}Impl.class, primaryKey);

				if (${entity.varName} != null) {
					cacheResult(${entity.varName});
				}
				else {
					entityCache.putResult(${entity.name}ModelImpl.ENTITY_CACHE_ENABLED, ${entity.name}Impl.class, primaryKey, _null${entity.name});
				}
			}
			catch (Exception e) {
				entityCache.removeResult(${entity.name}ModelImpl.ENTITY_CACHE_ENABLED, ${entity.name}Impl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return ${entity.varName};
	}

	/**
	 * Returns the ${entity.humanName} with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ${entity.PKVarName} the primary key of the ${entity.humanName}
	 * @return the ${entity.humanName}, or <code>null</code> if a ${entity.humanName} with the primary key could not be found
	 */
	@Override
	public ${entity.name} fetchByPrimaryKey(${entity.PKClassName} ${entity.PKVarName}) {
		return fetchByPrimaryKey((Serializable)${entity.PKVarName});
	}

	@Override
	public Map<Serializable, ${entity.name}> fetchByPrimaryKeys(Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, ${entity.name}> map = new HashMap<Serializable, ${entity.name}>();

		<#if entity.hasCompoundPK()>
			for (Serializable primaryKey : primaryKeys) {
				${entity.name} ${entity.varName} = fetchByPrimaryKey(primaryKey);

				if (${entity.varName} != null) {
					map.put(primaryKey, ${entity.varName});
				}
			}

			return map;
		<#else>
			if (primaryKeys.size() == 1) {
				Iterator<Serializable> iterator = primaryKeys.iterator();

				Serializable primaryKey = iterator.next();

				${entity.name} ${entity.varName} = fetchByPrimaryKey(primaryKey);

				if (${entity.varName} != null) {
					map.put(primaryKey, ${entity.varName});
				}

				return map;
			}

			Set<Serializable> uncachedPrimaryKeys = null;

			for (Serializable primaryKey : primaryKeys) {
				${entity.name} ${entity.varName} = (${entity.name})entityCache.getResult(${entity.name}ModelImpl.ENTITY_CACHE_ENABLED, ${entity.name}Impl.class, primaryKey);

				if (${entity.varName} == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, ${entity.varName});
				}
			}

			if (uncachedPrimaryKeys == null) {
				return map;
			}

			<#if entity.PKClassName == "String">
				StringBundler query = new StringBundler(uncachedPrimaryKeys.size() * 4 + 1);
			<#else>
				StringBundler query = new StringBundler(uncachedPrimaryKeys.size() * 2 + 1);
			</#if>

			query.append(_SQL_SELECT_${entity.alias?upper_case}_WHERE_PKS_IN);

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				<#if entity.PKClassName == "String">
					query.append(StringPool.APOSTROPHE);
					query.append((String)primaryKey);
					query.append(StringPool.APOSTROPHE);
				<#else>
					query.append(String.valueOf(primaryKey));
				</#if>

				query.append(StringPool.COMMA);
			}

			query.setIndex(query.index() - 1);

			query.append(StringPool.CLOSE_PARENTHESIS);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				for (${entity.name} ${entity.varName} : (List<${entity.name}>)q.list()) {
					map.put(${entity.varName}.getPrimaryKeyObj(), ${entity.varName});

					cacheResult(${entity.varName});

					uncachedPrimaryKeys.remove(${entity.varName}.getPrimaryKeyObj());
				}

				for (Serializable primaryKey : uncachedPrimaryKeys) {
					entityCache.putResult(${entity.name}ModelImpl.ENTITY_CACHE_ENABLED, ${entity.name}Impl.class, primaryKey, _null${entity.name});
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}

			return map;
		</#if>
	}

	/**
	 * Returns all the ${entity.humanNames}.
	 *
	 * @return the ${entity.humanNames}
	 */
	@Override
	public List<${entity.name}> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ${entity.humanNames}.
	 *
	 * <p>
	 * <#include "range_comment.ftl">
	 * </p>
	 *
	 * @param start the lower bound of the range of ${entity.humanNames}
	 * @param end the upper bound of the range of ${entity.humanNames} (not inclusive)
	 * @return the range of ${entity.humanNames}
	 */
	@Override
	public List<${entity.name}> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the ${entity.humanNames}.
	 *
	 * <p>
	 * <#include "range_comment.ftl">
	 * </p>
	 *
	 * @param start the lower bound of the range of ${entity.humanNames}
	 * @param end the upper bound of the range of ${entity.humanNames} (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ${entity.humanNames}
	 */
	@Override
	public List<${entity.name}> findAll(int start, int end, OrderByComparator<${entity.name}> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ${entity.humanNames}.
	 *
	 * <p>
	 * <#include "range_comment.ftl">
	 * </p>
	 *
	 * @param start the lower bound of the range of ${entity.humanNames}
	 * @param end the upper bound of the range of ${entity.humanNames} (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of ${entity.humanNames}
	 */
	@Override
	public List<${entity.name}> findAll(int start, int end, OrderByComparator<${entity.name}> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) && (orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<${entity.name}> list = null;

		if (retrieveFromCache) {
			list = (List<${entity.name}>)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 + (orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_${entity.alias?upper_case});

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_${entity.alias?upper_case};

				if (pagination) {
					sql = sql.concat(${entity.name}ModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<${entity.name}>)QueryUtil.list(q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<${entity.name}>)QueryUtil.list(q, getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the ${entity.humanNames} from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (${entity.name} ${entity.varName} : findAll()) {
			remove(${entity.varName});
		}
	}

	/**
	 * Returns the number of ${entity.humanNames}.
	 *
	 * @return the number of ${entity.humanNames}
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_${entity.alias?upper_case});

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	<#list entity.columnList as column>
		<#if column.isCollection() && column.isMappingManyToMany()>
			<#assign tempEntity = serviceBuilder.getEntity(column.getEJBName())>

			/**
			 * Returns the primaryKeys of ${tempEntity.humanNames} associated with the ${entity.humanName}.
			 *
			 * @param pk the primary key of the ${entity.humanName}
			 * @return long[] of the primaryKeys of ${tempEntity.humanNames} associated with the ${entity.humanName}
			 */
			@Override
			public long[] get${tempEntity.name}PrimaryKeys(${entity.PKClassName} pk) {
				long[] pks = ${entity.varName}To${tempEntity.name}TableMapper.getRightPrimaryKeys(0, pk);

				return pks.clone();
			}

			/**
			 * Returns all the ${tempEntity.humanNames} associated with the ${entity.humanName}.
			 *
			 * @param pk the primary key of the ${entity.humanName}
			 * @return the ${tempEntity.humanNames} associated with the ${entity.humanName}
			 */
			@Override
			public List<${tempEntity.packagePath}.model.${tempEntity.name}> get${tempEntity.names}(${entity.PKClassName} pk) {
				return get${tempEntity.names}(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
			}

			/**
			 * Returns a range of all the ${tempEntity.humanNames} associated with the ${entity.humanName}.
			 *
			 * <p>
			 * <#include "range_comment.ftl">
			 * </p>
			 *
			 * @param pk the primary key of the ${entity.humanName}
			 * @param start the lower bound of the range of ${entity.humanNames}
			 * @param end the upper bound of the range of ${entity.humanNames} (not inclusive)
			 * @return the range of ${tempEntity.humanNames} associated with the ${entity.humanName}
			 */
			@Override
			public List<${tempEntity.packagePath}.model.${tempEntity.name}> get${tempEntity.names}(${entity.PKClassName} pk, int start, int end) {
				return get${tempEntity.names}(pk, start, end, null);
			}

			/**
			 * Returns an ordered range of all the ${tempEntity.humanNames} associated with the ${entity.humanName}.
			 *
			 * <p>
			 * <#include "range_comment.ftl">
			 * </p>
			 *
			 * @param pk the primary key of the ${entity.humanName}
			 * @param start the lower bound of the range of ${entity.humanNames}
			 * @param end the upper bound of the range of ${entity.humanNames} (not inclusive)
			 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
			 * @return the ordered range of ${tempEntity.humanNames} associated with the ${entity.humanName}
			 */
			@Override
			public List<${tempEntity.packagePath}.model.${tempEntity.name}> get${tempEntity.names}(${entity.PKClassName} pk, int start, int end, OrderByComparator<${tempEntity.packagePath}.model.${tempEntity.name}> orderByComparator) {
				return ${entity.varName}To${tempEntity.name}TableMapper.getRightBaseModels(0, pk, start, end, orderByComparator);
			}

			/**
			 * Returns the number of ${tempEntity.humanNames} associated with the ${entity.humanName}.
			 *
			 * @param pk the primary key of the ${entity.humanName}
			 * @return the number of ${tempEntity.humanNames} associated with the ${entity.humanName}
			 */
			@Override
			public int get${tempEntity.names}Size(${entity.PKClassName} pk) {
				long[] pks = ${entity.varName}To${tempEntity.name}TableMapper.getRightPrimaryKeys(0, pk);

				return pks.length;
			}

			/**
			 * Returns <code>true</code> if the ${tempEntity.humanName} is associated with the ${entity.humanName}.
			 *
			 * @param pk the primary key of the ${entity.humanName}
			 * @param ${tempEntity.varName}PK the primary key of the ${tempEntity.humanName}
			 * @return <code>true</code> if the ${tempEntity.humanName} is associated with the ${entity.humanName}; <code>false</code> otherwise
			 */
			@Override
			public boolean contains${tempEntity.name}(${entity.PKClassName} pk, ${tempEntity.PKClassName} ${tempEntity.varName}PK) {
				return ${entity.varName}To${tempEntity.name}TableMapper.containsTableMapping(0, pk, ${tempEntity.varName}PK);
			}

			/**
			 * Returns <code>true</code> if the ${entity.humanName} has any ${tempEntity.humanNames} associated with it.
			 *
			 * @param pk the primary key of the ${entity.humanName} to check for associations with ${tempEntity.humanNames}
			 * @return <code>true</code> if the ${entity.humanName} has any ${tempEntity.humanNames} associated with it; <code>false</code> otherwise
			 */
			@Override
			public boolean contains${tempEntity.names}(${entity.PKClassName} pk) {
				if (get${tempEntity.names}Size(pk)> 0) {
					return true;
				}
				else {
					return false;
				}
			}

			<#if column.isMappingManyToMany()>
				<#assign noSuchTempEntity = serviceBuilder.getNoSuchEntityException(tempEntity)>

				/**
				 * Adds an association between the ${entity.humanName} and the ${tempEntity.humanName}. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param ${tempEntity.varName}PK the primary key of the ${tempEntity.humanName}
				 */
				@Override
				public void add${tempEntity.name}(${entity.PKClassName} pk, ${tempEntity.PKClassName} ${tempEntity.varName}PK) {
					${entity.varName}To${tempEntity.name}TableMapper.addTableMapping(0, pk, ${tempEntity.varName}PK);
				}

				/**
				 * Adds an association between the ${entity.humanName} and the ${tempEntity.humanName}. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param ${tempEntity.varName} the ${tempEntity.humanName}
				 */
				@Override
				public void add${tempEntity.name}(${entity.PKClassName} pk, ${tempEntity.packagePath}.model.${tempEntity.name} ${tempEntity.varName}) {
					${entity.varName}To${tempEntity.name}TableMapper.addTableMapping(0, pk, ${tempEntity.varName}.getPrimaryKey());
				}

				/**
				 * Adds an association between the ${entity.humanName} and the ${tempEntity.humanNames}. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param ${tempEntity.varName}PKs the primary keys of the ${tempEntity.humanNames}
				 */
				@Override
				public void add${tempEntity.names}(${entity.PKClassName} pk, ${tempEntity.PKClassName}[] ${tempEntity.varName}PKs) {
					for (${tempEntity.PKClassName} ${tempEntity.varName}PK : ${tempEntity.varName}PKs) {
						${entity.varName}To${tempEntity.name}TableMapper.addTableMapping(0, pk, ${tempEntity.varName}PK);
					}
				}

				/**
				 * Adds an association between the ${entity.humanName} and the ${tempEntity.humanNames}. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param ${tempEntity.varNames} the ${tempEntity.humanNames}
				 */
				@Override
				public void add${tempEntity.names}(${entity.PKClassName} pk, List<${tempEntity.packagePath}.model.${tempEntity.name}> ${tempEntity.varNames}) {
					for (${tempEntity.packagePath}.model.${tempEntity.name} ${tempEntity.varName} : ${tempEntity.varNames}) {
						${entity.varName}To${tempEntity.name}TableMapper.addTableMapping(0, pk, ${tempEntity.varName}.getPrimaryKey());
					}
				}

				/**
				 * Clears all associations between the ${entity.humanName} and its ${tempEntity.humanNames}. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName} to clear the associated ${tempEntity.humanNames} from
				 */
				@Override
				public void clear${tempEntity.names}(${entity.PKClassName} pk) {
					${entity.varName}To${tempEntity.name}TableMapper.deleteLeftPrimaryKeyTableMappings(0, pk);
				}

				/**
				 * Removes the association between the ${entity.humanName} and the ${tempEntity.humanName}. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param ${tempEntity.varName}PK the primary key of the ${tempEntity.humanName}
				 */
				@Override
				public void remove${tempEntity.name}(${entity.PKClassName} pk, ${tempEntity.PKClassName} ${tempEntity.varName}PK) {
					${entity.varName}To${tempEntity.name}TableMapper.deleteTableMapping(0, pk, ${tempEntity.varName}PK);
				}

				/**
				 * Removes the association between the ${entity.humanName} and the ${tempEntity.humanName}. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param ${tempEntity.varName} the ${tempEntity.humanName}
				 */
				@Override
				public void remove${tempEntity.name}(${entity.PKClassName} pk, ${tempEntity.packagePath}.model.${tempEntity.name} ${tempEntity.varName}) {
					${entity.varName}To${tempEntity.name}TableMapper.deleteTableMapping(0, pk, ${tempEntity.varName}.getPrimaryKey());
				}

				/**
				 * Removes the association between the ${entity.humanName} and the ${tempEntity.humanNames}. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param ${tempEntity.varName}PKs the primary keys of the ${tempEntity.humanNames}
				 */
				@Override
				public void remove${tempEntity.names}(${entity.PKClassName} pk, ${tempEntity.PKClassName}[] ${tempEntity.varName}PKs) {
					for (${tempEntity.PKClassName} ${tempEntity.varName}PK : ${tempEntity.varName}PKs) {
						${entity.varName}To${tempEntity.name}TableMapper.deleteTableMapping(0, pk, ${tempEntity.varName}PK);
					}
				}

				/**
				 * Removes the association between the ${entity.humanName} and the ${tempEntity.humanNames}. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param ${tempEntity.varNames} the ${tempEntity.humanNames}
				 */
				@Override
				public void remove${tempEntity.names}(${entity.PKClassName} pk, List<${tempEntity.packagePath}.model.${tempEntity.name}> ${tempEntity.varNames}) {
					for (${tempEntity.packagePath}.model.${tempEntity.name} ${tempEntity.varName} : ${tempEntity.varNames}) {
						${entity.varName}To${tempEntity.name}TableMapper.deleteTableMapping(0, pk, ${tempEntity.varName}.getPrimaryKey());
					}
				}

				/**
				 * Sets the ${tempEntity.humanNames} associated with the ${entity.humanName}, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param ${tempEntity.varName}PKs the primary keys of the ${tempEntity.humanNames} to be associated with the ${entity.humanName}
				 */
				@Override
				public void set${tempEntity.names}(${entity.PKClassName} pk, ${tempEntity.PKClassName}[] ${tempEntity.varName}PKs) {
					Set<Long> new${tempEntity.name}PKsSet = SetUtil.fromArray(${tempEntity.varName}PKs);
					Set<Long> old${tempEntity.name}PKsSet = SetUtil.fromArray(${entity.varName}To${tempEntity.name}TableMapper.getRightPrimaryKeys(0, pk));

					Set<Long> remove${tempEntity.name}PKsSet = new HashSet<Long>(old${tempEntity.name}PKsSet);

					remove${tempEntity.name}PKsSet.removeAll(new${tempEntity.name}PKsSet);

					for (long remove${tempEntity.name}PK : remove${tempEntity.name}PKsSet) {
						${entity.varName}To${tempEntity.name}TableMapper.deleteTableMapping(0, pk, remove${tempEntity.name}PK);
					}

					new${tempEntity.name}PKsSet.removeAll(old${tempEntity.name}PKsSet);

					for (long new${tempEntity.name}PK :new${tempEntity.name}PKsSet) {
						${entity.varName}To${tempEntity.name}TableMapper.addTableMapping(0, pk, new${tempEntity.name}PK);
					}
				}

				/**
				 * Sets the ${tempEntity.humanNames} associated with the ${entity.humanName}, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param ${tempEntity.varNames} the ${tempEntity.humanNames} to be associated with the ${entity.humanName}
				 */
				@Override
				public void set${tempEntity.names}(${entity.PKClassName} pk, List<${tempEntity.packagePath}.model.${tempEntity.name}> ${tempEntity.varNames}) {
					try {
						${tempEntity.PKClassName}[] ${tempEntity.varName}PKs = new ${tempEntity.PKClassName}[${tempEntity.varNames}.size()];

						for (int i = 0; i < ${tempEntity.varNames}.size(); i++) {
							${tempEntity.packagePath}.model.${tempEntity.name} ${tempEntity.varName} = ${tempEntity.varNames}.get(i);

							${tempEntity.varName}PKs[i] = ${tempEntity.varName}.getPrimaryKey();
						}

						set${tempEntity.names}(pk, ${tempEntity.varName}PKs);
					}
					catch (Exception e) {
						throw processException(e);
					}
				}
			</#if>
		</#if>
	</#list>

	<#if entity.badNamedColumnsList?size != 0>
		@Override
	    public Set<String> getBadColumnNames() {
			return _badColumnNames;
		}
	</#if>

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ${entity.name}ModelImpl.TABLE_COLUMNS_MAP;
	}

	<#if entity.isHierarchicalTree()>
		@Override
		public long countAncestors(${entity.name} ${entity.varName}) {
			Object[] finderArgs = new Object[] {${entity.varName}.get${scopeColumn.methodName}(), ${entity.varName}.getLeft${pkColumn.methodName}(), ${entity.varName}.getRight${pkColumn.methodName}()};

			Long count = (Long)finderCache.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_ANCESTORS, finderArgs, this);

			if (count == null) {
				try {
					count = nestedSetsTreeManager.countAncestors(${entity.varName});

					finderCache.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_ANCESTORS, finderArgs, count);
				}
				catch (SystemException se) {
					finderCache.removeResult(FINDER_PATH_WITH_PAGINATION_COUNT_ANCESTORS, finderArgs);

					throw se;
				}
			}

			return count.intValue();
		}

		@Override
		public long countDescendants(${entity.name} ${entity.varName}) {
			Object[] finderArgs = new Object[] {${entity.varName}.get${scopeColumn.methodName}(), ${entity.varName}.getLeft${pkColumn.methodName}(), ${entity.varName}.getRight${pkColumn.methodName}()};

			Long count = (Long)finderCache.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_DESCENDANTS, finderArgs, this);

			if (count == null) {
				try {
					count = nestedSetsTreeManager.countDescendants(${entity.varName});

					finderCache.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_DESCENDANTS, finderArgs, count);
				}
				catch (SystemException se) {
					finderCache.removeResult(FINDER_PATH_WITH_PAGINATION_COUNT_DESCENDANTS, finderArgs);

					throw se;
				}
			}

			return count.intValue();
		}

		@Override
		public List<${entity.name}> getAncestors(${entity.name} ${entity.varName}) {
			Object[] finderArgs = new Object[] {${entity.varName}.get${scopeColumn.methodName}(), ${entity.varName}.getLeft${pkColumn.methodName}(), ${entity.varName}.getRight${pkColumn.methodName}()};

			List<${entity.name}> list = (List<${entity.name}>)finderCache.getResult(FINDER_PATH_WITH_PAGINATION_GET_ANCESTORS, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (${entity.name} temp${entity.name} : list) {
					if ((${entity.varName}.getLeft${pkColumn.methodName}() < temp${entity.name}.getLeft${pkColumn.methodName}()) || (${entity.varName}.getRight${pkColumn.methodName}() > temp${entity.name}.getRight${pkColumn.methodName}())) {
						list = null;

						break;
					}
				}
			}

			if (list == null) {
				try {
					list = nestedSetsTreeManager.getAncestors(${entity.varName});

					cacheResult(list);

					finderCache.putResult(FINDER_PATH_WITH_PAGINATION_GET_ANCESTORS, finderArgs, list);
				}
				catch (SystemException se) {
					finderCache.removeResult(FINDER_PATH_WITH_PAGINATION_GET_ANCESTORS, finderArgs);

					throw se;
				}
			}

			return list;
		}

		@Override
		public List<${entity.name}> getDescendants(${entity.name} ${entity.varName}) {
			Object[] finderArgs = new Object[] {${entity.varName}.get${scopeColumn.methodName}(), ${entity.varName}.getLeft${pkColumn.methodName}(), ${entity.varName}.getRight${pkColumn.methodName}()};

			List<${entity.name}> list = (List<${entity.name}>)finderCache.getResult(FINDER_PATH_WITH_PAGINATION_GET_DESCENDANTS, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (${entity.name} temp${entity.name} : list) {
					if ((${entity.varName}.getLeft${pkColumn.methodName}() > temp${entity.name}.getLeft${pkColumn.methodName}()) || (${entity.varName}.getRight${pkColumn.methodName}() < temp${entity.name}.getRight${pkColumn.methodName}())) {
						list = null;

						break;
					}
				}
			}

			if (list == null) {
				try {
					list = nestedSetsTreeManager.getDescendants(${entity.varName});

					cacheResult(list);

					finderCache.putResult(FINDER_PATH_WITH_PAGINATION_GET_DESCENDANTS, finderArgs, list);
				}
				catch (SystemException se) {
					finderCache.removeResult(FINDER_PATH_WITH_PAGINATION_GET_DESCENDANTS, finderArgs);

					throw se;
				}
			}

			return list;
		}

		/**
		 * Rebuilds the ${entity.humanNames} tree for the scope using the modified pre-order tree traversal algorithm.
		 *
		 * <p>
		 * Only call this method if the tree has become stale through operations other than normal CRUD. Under normal circumstances the tree is automatically rebuilt whenver necessary.
		 * </p>
		 *
		 * @param ${scopeColumn.name} the ID of the scope
		 * @param force whether to force the rebuild even if the tree is not stale
		 */
		@Override
		public void rebuildTree(long ${scopeColumn.name}, boolean force) {
			if (!rebuildTreeEnabled) {
				return;
			}

			if (force || (countOrphanTreeNodes(${scopeColumn.name}) > 0)) {
				Session session = null;

				try {
					session = openSession();

					if (session.isDirty()) {
						session.flush();
					}

					SQLQuery selectQuery = session.createSQLQuery("SELECT ${pkColumn.DBName} FROM ${entity.table} WHERE ${scopeColumn.DBName} = ? AND parent${pkColumn.methodName} = ? ORDER BY ${pkColumn.name} ASC");

					selectQuery.addScalar("${pkColumn.name}", com.liferay.portal.kernel.dao.orm.Type.LONG);

					SQLQuery updateQuery = session.createSQLQuery("UPDATE ${entity.table} SET left${pkColumn.methodName} = ?, right${pkColumn.methodName} = ? WHERE ${pkColumn.name} = ?");

					rebuildTree(session, selectQuery, updateQuery, ${scopeColumn.name}, 0, 0);
				}
				catch (Exception e) {
					throw processException(e);
				}
				finally {
					closeSession(session);
				}

				clearCache();
			}
		}

		@Override
		public void setRebuildTreeEnabled(boolean rebuildTreeEnabled) {
			this.rebuildTreeEnabled = rebuildTreeEnabled;
		}

		protected long countOrphanTreeNodes(long ${scopeColumn.name}) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSynchronizedSQLQuery("SELECT COUNT(*) AS COUNT_VALUE FROM ${entity.table} WHERE ${scopeColumn.DBName} = ? AND (left${pkColumn.methodName} = 0 OR left${pkColumn.methodName} IS NULL OR right${pkColumn.methodName} = 0 OR right${pkColumn.methodName} IS NULL)");

				q.addScalar(COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(${scopeColumn.name});

				return (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		protected long rebuildTree(Session session, SQLQuery selectQuery, SQLQuery updateQuery, long ${scopeColumn.name}, long parent${pkColumn.methodName}, long left${pkColumn.methodName}) {
			long right${pkColumn.methodName} = left${pkColumn.methodName} + 1;

			QueryPos qPos = QueryPos.getInstance(selectQuery);

			qPos.add(${scopeColumn.name});
			qPos.add(parent${pkColumn.methodName});

			List<Long> ${pkColumn.names} = selectQuery.list();

			for (long ${pkColumn.name} : ${pkColumn.names}) {
				right${pkColumn.methodName} = rebuildTree(session, selectQuery, updateQuery, ${scopeColumn.name}, ${pkColumn.name}, right${pkColumn.methodName});
			}

			if (parent${pkColumn.methodName} > 0) {
				qPos = QueryPos.getInstance(updateQuery);

				qPos.add(left${pkColumn.methodName});
				qPos.add(right${pkColumn.methodName});
				qPos.add(parent${pkColumn.methodName});

				updateQuery.executeUpdate();
			}

			return right${pkColumn.methodName} + 1;
		}
	</#if>

	/**
	 * Initializes the ${entity.humanName} persistence.
	 */
	public void afterPropertiesSet() {

		<#list entity.columnList as column>
			<#if column.isCollection() && column.isMappingManyToMany()>
				<#assign tempEntity = serviceBuilder.getEntity(column.getEJBName())>

				<#assign entityMapping = serviceBuilder.getEntityMapping(column.mappingTable)>

				<#assign companyEntity = serviceBuilder.getEntity(entityMapping.getEntity(0))>

				${entity.varName}To${tempEntity.name}TableMapper = TableMapperFactory.getTableMapper("${column.mappingTable}", "${companyEntity.PKDBName}", "${entity.PKDBName}", "${tempEntity.PKDBName}", this, ${tempEntity.varName}Persistence);
			</#if>
		</#list>

		<#if entity.isHierarchicalTree()>
			updateTree = new UpdateTree();
		</#if>
	}

	public void destroy() {
		entityCache.removeCache(${entity.name}Impl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		<#list entity.columnList as column>
			<#if column.isCollection() && column.isMappingManyToMany()>
				TableMapperFactory.removeTableMapper("${column.mappingTable}");
			</#if>
		</#list>
	}

	<#if entity.isShardedModel()>
		<#if osgiModule>
			@ServiceReference(type = CompanyProvider.class)
		<#else>
			@BeanReference(type = CompanyProvider.class)
		</#if>
		protected CompanyProvider companyProvider;
	<#else>
		<#list entity.columnList as column>
			<#if column.isCollection() && column.isMappingManyToMany()>
				<#if osgiModule>
					@ServiceReference(type = CompanyProvider.class)
				<#else>
					@BeanReference(type = CompanyProvider.class)
				</#if>
				protected CompanyProvider companyProvider;

				<#break>
			</#if>
		</#list>
	</#if>

	<#if osgiModule>
		@ServiceReference(type = EntityCache.class)
		protected EntityCache entityCache;

		@ServiceReference(type = FinderCache.class)
		protected FinderCache finderCache;
	<#else>
		protected EntityCache entityCache = EntityCacheUtil.getEntityCache();
		protected FinderCache finderCache = FinderCacheUtil.getFinderCache();
	</#if>

	<#list entity.columnList as column>
		<#if column.isCollection() && column.isMappingManyToMany()>
			<#assign tempEntity = serviceBuilder.getEntity(column.getEJBName())>

			@BeanReference(type = ${tempEntity.name}Persistence.class)
			protected ${tempEntity.name}Persistence ${tempEntity.varName}Persistence;
			protected TableMapper<${entity.name}, ${tempEntity.packagePath}.model.${tempEntity.name}> ${entity.varName}To${tempEntity.name}TableMapper;
		</#if>
	</#list>

	<#if entity.isHierarchicalTree()>
		protected NestedSetsTreeManager<${entity.name}> nestedSetsTreeManager = new PersistenceNestedSetsTreeManager<${entity.name}>(this, "${entity.table}", "${entity.name}", ${entity.name}Impl.class, "${pkColumn.DBName}", "${scopeColumn.DBName}", "left${pkColumn.methodName}", "right${pkColumn.methodName}");
		protected boolean rebuildTreeEnabled = true;
		protected UpdateTree updateTree;

		protected class UpdateTree {

			protected UpdateTree() {
				_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(), "UPDATE ${entity.table} SET left${pkColumn.methodName} = ?, right${pkColumn.methodName} = ? WHERE ${pkColumn.name} = ?", new int[] {java.sql.Types.${serviceBuilder.getSqlType("long")}, java.sql.Types.${serviceBuilder.getSqlType("long")}, java.sql.Types.${serviceBuilder.getSqlType("long")}});
			}

			protected void update(long ${pkColumn.name}, long left${pkColumn.methodName}, long right${pkColumn.methodName}) {
				_sqlUpdate.update(new Object[] {left${pkColumn.methodName}, right${pkColumn.methodName}, ${pkColumn.name}});
			}

			private SqlUpdate _sqlUpdate;

		}
	</#if>

	private static final String _SQL_SELECT_${entity.alias?upper_case} = "SELECT ${entity.alias} FROM ${entity.name} ${entity.alias}";

	<#if !entity.hasCompoundPK()>
		private static final String _SQL_SELECT_${entity.alias?upper_case}_WHERE_PKS_IN = "SELECT ${entity.alias} FROM ${entity.name} ${entity.alias} WHERE ${entity.PKDBName} IN (";
	</#if>

	<#if entity.getFinderList()?size != 0>
		private static final String _SQL_SELECT_${entity.alias?upper_case}_WHERE = "SELECT ${entity.alias} FROM ${entity.name} ${entity.alias} WHERE ";
	</#if>

	private static final String _SQL_COUNT_${entity.alias?upper_case} = "SELECT COUNT(${entity.alias}) FROM ${entity.name} ${entity.alias}";

	<#if entity.getFinderList()?size != 0>
		private static final String _SQL_COUNT_${entity.alias?upper_case}_WHERE = "SELECT COUNT(${entity.alias}) FROM ${entity.name} ${entity.alias} WHERE ";
	</#if>

	<#if entity.isPermissionCheckEnabled()>
		private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "${entity.alias}.${entity.filterPKColumn.DBName}";

		<#if entity.isPermissionedModel()>
			<#if entity.hasColumn("userId") >
				private static final String _FILTER_ENTITY_TABLE_FILTER_USERID_COLUMN = "${entity.alias}.userId";
			<#else>
				private static final String _FILTER_ENTITY_TABLE_FILTER_USERID_COLUMN = null;
			</#if>
		<#else>
			private static final String _FILTER_SQL_SELECT_${entity.alias?upper_case}_WHERE = "SELECT DISTINCT {${entity.alias}.*} FROM ${entity.table} ${entity.alias} WHERE ";

			private static final String _FILTER_SQL_SELECT_${entity.alias?upper_case}_NO_INLINE_DISTINCT_WHERE_1 = "SELECT {${entity.table}.*} FROM (SELECT DISTINCT ${entity.alias}.${entity.PKDBName} FROM ${entity.table} ${entity.alias} WHERE ";

			private static final String _FILTER_SQL_SELECT_${entity.alias?upper_case}_NO_INLINE_DISTINCT_WHERE_2 = ") TEMP_TABLE INNER JOIN ${entity.table} ON TEMP_TABLE.${entity.PKDBName} = ${entity.table}.${entity.PKDBName}";

			private static final String _FILTER_SQL_COUNT_${entity.alias?upper_case}_WHERE = "SELECT COUNT(DISTINCT ${entity.alias}.${entity.PKDBName}) AS COUNT_VALUE FROM ${entity.table} ${entity.alias} WHERE ";

			private static final String _FILTER_ENTITY_ALIAS = "${entity.alias}";

			private static final String _FILTER_ENTITY_TABLE = "${entity.table}";
		</#if>
	</#if>

	private static final String _ORDER_BY_ENTITY_ALIAS = "${entity.alias}.";

	<#if entity.isPermissionCheckEnabled() && !entity.isPermissionedModel()>
		private static final String _ORDER_BY_ENTITY_TABLE = "${entity.table}.";
	</#if>

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ${entity.name} exists with the primary key ";

	<#if entity.getFinderList()?size != 0>
		private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ${entity.name} exists with the key {";
	</#if>

	private static final Log _log = LogFactoryUtil.getLog(${entity.name}PersistenceImpl.class);

	<#if entity.badNamedColumnsList?size != 0>
		private static final Set<String> _badColumnNames = SetUtil.fromArray(
			new String[] {
				<#list entity.badNamedColumnsList as column>
					"${column.name}"

					<#if column_has_next>
						,
					</#if>
				</#list>
			});
	</#if>

	private static final ${entity.name} _null${entity.name} = new ${entity.name}Impl() {

		@Override
		public Object clone() {
			return this;
		}

		@Override
		public CacheModel<${entity.name}> toCacheModel() {
			return _null${entity.name}CacheModel;
		}

	};

	private static final CacheModel<${entity.name}> _null${entity.name}CacheModel =

	<#if entity.isMvccEnabled()>
		new NullCacheModel();

		private static class NullCacheModel implements CacheModel<${entity.name}>, MVCCModel {

			@Override
			public long getMvccVersion() {
				return -1;
			}

			@Override
			public void setMvccVersion(long mvccVersion) {
			}

			@Override
			public ${entity.name} toEntityModel() {
				return _null${entity.name};
			}

		}
	<#else>
		new CacheModel<${entity.name}>() {

			@Override
			public ${entity.name} toEntityModel() {
				return _null${entity.name};
			}

		};
	</#if>

}

<#function bindParameter finderColsList>
	<#list finderColsList as finderCol>
		<#if !finderCol.hasArrayableOperator() || finderCol.type == "String">
			<#return true>
		</#if>
	</#list>

	<#return false>
</#function>

<#macro finderQPos
	_arrayable = false
>
	<#list finderColsList as finderCol>
		<#if _arrayable && finderCol.hasArrayableOperator()>
			<#if finderCol.type == "String">
				for (String ${finderCol.name} : ${finderCol.names}) {
					if (${finderCol.name} != null && !${finderCol.name}.isEmpty()) {
						qPos.add(${finderCol.name});
					}
				}
			</#if>
		<#elseif finderCol.isPrimitiveType()>
			qPos.add(${finderCol.name}${serviceBuilder.getPrimitiveObjValue("${finderCol.type}")});

		<#else>
			if (bind${finderCol.methodName}) {
				qPos.add(
					<#if finderCol.type == "Date">
						new Timestamp(${finderCol.name}.getTime())
					<#elseif finderCol.type == "String" && !finderCol.isCaseSensitive()>
						StringUtil.toLowerCase(${finderCol.name})
					<#else>
						${finderCol.name}
					</#if>
				);
			}
		</#if>
	</#list>
</#macro>