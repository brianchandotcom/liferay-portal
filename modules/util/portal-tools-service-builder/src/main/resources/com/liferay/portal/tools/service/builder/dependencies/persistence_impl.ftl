<#if entity.isHierarchicalTree()>
	<#if entity.hasEntityColumn("groupId")>
		<#assign scopeEntityColumn = entity.getEntityColumn("groupId") />
	<#else>
		<#assign scopeEntityColumn = entity.getEntityColumn("companyId") />
	</#if>

	<#assign pkEntityColumn = entity.PKEntityColumns?first />
</#if>

<#assign finderFieldSQLSuffix = "_SQL" />

package ${packagePath}.service.persistence.impl;

<#assign noSuchEntity = serviceBuilder.getNoSuchEntityException(entity) />

import ${apiPackagePath}.exception.${noSuchEntity}Exception;
import ${apiPackagePath}.model.${entity.name};
import ${packagePath}.model.impl.${entity.name}Impl;
import ${packagePath}.model.impl.${entity.name}ModelImpl;
import ${apiPackagePath}.service.persistence.${entity.name}Persistence;

<#if entity.hasCompoundPK()>
	import ${apiPackagePath}.service.persistence.${entity.PKClassName};
</#if>

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.NestedSetsTreeManager;
import com.liferay.portal.kernel.service.persistence.impl.PersistenceNestedSetsTreeManager;
import com.liferay.portal.kernel.service.persistence.impl.TableMapper;
import com.liferay.portal.kernel.service.persistence.impl.TableMapperFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;

import java.math.BigDecimal;

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
import java.util.Objects;
import java.util.Set;

<#list referenceEntities as referenceEntity>
	<#if referenceEntity.hasEntityColumns() && (stringUtil.equals(entity.name, "Counter") || !stringUtil.equals(referenceEntity.name, "Counter"))>
		import ${referenceEntity.apiPackagePath}.service.persistence.${referenceEntity.name}Persistence;
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
 * @see ${apiPackagePath}.service.persistence.${entity.name}Util
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

	<#assign columnBitmaskEnabled = (entity.finderEntityColumns?size &gt; 0) && (entity.finderEntityColumns?size &lt; 64) />

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

	<#list entity.entityFinders as entityFinder>
		<#include "persistence_impl_finder_finder_path.ftl">

		<#include "persistence_impl_finder_find.ftl">

		<#include "persistence_impl_finder_remove.ftl">

		<#include "persistence_impl_finder_count.ftl">

		<#include "persistence_impl_finder_fields.ftl">
	</#list>

	public ${entity.name}PersistenceImpl() {
		setModelClass(${entity.name}.class);

		<#if entity.badEntityColumns?size != 0>
			try {
				Field field = BasePersistenceImpl.class.getDeclaredField("_dbColumnNames");

				field.setAccessible(true);

				Map<String, String> dbColumnNames = new HashMap<String, String>();

				<#list entity.badEntityColumns as badEntityColumn>
					dbColumnNames.put("${badEntityColumn.name}", "${badEntityColumn.DBName}");
				</#list>

				field.set(this, dbColumnNames);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}
			}
		</#if>
	}

	/**
	 * Caches the ${entity.humanName} in the entity cache if it is enabled.
	 *
	 * @param ${entity.varName} the ${entity.humanName}
	 */
	@Override
	public void cacheResult(${entity.name} ${entity.varName}) {
		entityCache.putResult(${entity.name}ModelImpl.ENTITY_CACHE_ENABLED, ${entity.name}Impl.class, ${entity.varName}.getPrimaryKey(), ${entity.varName});

		<#list entity.uniqueEntityFinders as uniqueEntityFinder>
			<#assign entityColumns = uniqueEntityFinder.entityColumns />

			finderCache.putResult(
				FINDER_PATH_FETCH_BY_${uniqueEntityFinder.name?upper_case},
				new Object[] {
					<#list entityColumns as entityColumn>
						${entity.varName}.get${entityColumn.methodName}()

						<#if entityColumn_has_next>
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

		<#if entity.uniqueEntityFinders?size &gt; 0>
			clearUniqueFindersCache((${entity.name}ModelImpl)${entity.varName}, true);
		</#if>
	}

	@Override
	public void clearCache(List<${entity.name}> ${entity.varNames}) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (${entity.name} ${entity.varName} : ${entity.varNames}) {
			entityCache.removeResult(${entity.name}ModelImpl.ENTITY_CACHE_ENABLED, ${entity.name}Impl.class, ${entity.varName}.getPrimaryKey());

			<#if entity.uniqueEntityFinders?size &gt; 0>
				clearUniqueFindersCache((${entity.name}ModelImpl)${entity.varName}, true);
			</#if>
		}
	}

	<#if entity.uniqueEntityFinders?size &gt; 0>
		protected void cacheUniqueFindersCache(${entity.name}ModelImpl ${entity.varName}ModelImpl) {
			<#list entity.uniqueEntityFinders as uniqueEntityFinder>
				<#assign entityColumns = uniqueEntityFinder.entityColumns />

				<#if uniqueEntityFinder_index == 0>
					Object[]
				</#if>
				args = new Object[] {
					<#list entityColumns as entityColumn>
						${entity.varName}ModelImpl.get${entityColumn.methodName}()

						<#if entityColumn_has_next>
							,
						</#if>
					</#list>
				};

				finderCache.putResult(FINDER_PATH_COUNT_BY_${uniqueEntityFinder.name?upper_case}, args, Long.valueOf(1), false);
				finderCache.putResult(FINDER_PATH_FETCH_BY_${uniqueEntityFinder.name?upper_case}, args, ${entity.varName}ModelImpl, false);
			</#list>
		}

		protected void clearUniqueFindersCache(${entity.name}ModelImpl ${entity.varName}ModelImpl, boolean clearCurrent) {
			<#list entity.uniqueEntityFinders as uniqueEntityFinder>
				<#assign entityColumns = uniqueEntityFinder.entityColumns />

				if (clearCurrent) {
					Object[] args = new Object[] {
						<#list entityColumns as entityColumn>
							${entity.varName}ModelImpl.get${entityColumn.methodName}()

							<#if entityColumn_has_next>
								,
							</#if>
						</#list>
					};

					finderCache.removeResult(FINDER_PATH_COUNT_BY_${uniqueEntityFinder.name?upper_case}, args);
					finderCache.removeResult(FINDER_PATH_FETCH_BY_${uniqueEntityFinder.name?upper_case}, args);
				}

				if ((${entity.varName}ModelImpl.getColumnBitmask() & FINDER_PATH_FETCH_BY_${uniqueEntityFinder.name?upper_case}.getColumnBitmask()) != 0) {
					Object[] args = new Object[] {
						<#list entityColumns as entityColumn>
							${entity.varName}ModelImpl.getOriginal${entityColumn.methodName}()

							<#if entityColumn_has_next>
								,
							</#if>
						</#list>
					};

					finderCache.removeResult(FINDER_PATH_COUNT_BY_${uniqueEntityFinder.name?upper_case}, args);
					finderCache.removeResult(FINDER_PATH_FETCH_BY_${uniqueEntityFinder.name?upper_case}, args);
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

		<#if entity.isShardedModel()>
			${entity.varName}.setCompanyId(companyProvider.getCompanyId());
		</#if>

		return ${entity.varName};
	}

	/**
	 * Removes the ${entity.humanName} with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ${entity.PKVarName} the primary key of the ${entity.humanName}
	 * @return the ${entity.humanName} that was removed
	 * @throws ${noSuchEntity}Exception if a ${entity.humanName} with the primary key could not be found
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
	 * @throws ${noSuchEntity}Exception if a ${entity.humanName} with the primary key could not be found
	 */
	@Override
	public ${entity.name} remove(Serializable primaryKey) throws ${noSuchEntity}Exception {
		Session session = null;

		try {
			session = openSession();

			${entity.name} ${entity.varName} = (${entity.name})session.get(${entity.name}Impl.class, primaryKey);

			if (${entity.varName} == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
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

		<#list entity.entityColumns as entityColumn>
			<#if entityColumn.isCollection() && entityColumn.isMappingManyToMany()>
				<#assign referenceEntity = serviceBuilder.getEntity(entityColumn.entityName) />

				${entity.varName}To${referenceEntity.name}TableMapper.deleteLeftPrimaryKeyTableMappings(${entity.varName}.getPrimaryKey());
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
	public ${entity.name} updateImpl(${apiPackagePath}.model.${entity.name} ${entity.varName}) {
		${entity.varName} = toUnwrappedModel(${entity.varName});

		boolean isNew = ${entity.varName}.isNew();

		<#if entity.isHierarchicalTree() || (entity.collectionEntityFinders?size != 0) || (entity.uniqueEntityFinders?size &gt; 0) || (entity.hasEntityColumn("createDate", "Date") && entity.hasEntityColumn("modifiedDate", "Date"))>
			${entity.name}ModelImpl ${entity.varName}ModelImpl = (${entity.name}ModelImpl)${entity.varName};
		</#if>

		<#if entity.hasUuid()>
			if (Validator.isNull(${entity.varName}.getUuid())) {
				String uuid = PortalUUIDUtil.generate();

				${entity.varName}.setUuid(uuid);
			}
		</#if>

		<#if entity.hasEntityColumn("createDate", "Date") && entity.hasEntityColumn("modifiedDate", "Date")>
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

		<#assign sanitizeTuples = modelHintsUtil.getSanitizeTuples("${apiPackagePath}.model.${entity.name}") />

		<#if sanitizeTuples?size != 0>
			long userId = GetterUtil.getLong(PrincipalThreadLocal.getName());

			if (userId > 0) {
				<#assign companyId = 0 />

				<#if entity.hasEntityColumn("companyId")>
					long companyId = ${entity.varName}.getCompanyId();
				<#else>
					long companyId = 0;
				</#if>

				<#if entity.hasEntityColumn("groupId")>
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
						<#assign
							colMethodName = textFormatter.format(sanitizeTuple.getObject(0), 6)

							contentType = "\"" + sanitizeTuple.getObject(1) + "\""
						/>

						<#if contentType == "\"text/html\"">
							<#assign contentType = "ContentTypes.TEXT_HTML" />
						<#elseif contentType == "\"text/plain\"">
							<#assign contentType = "ContentTypes.TEXT_PLAIN" />
						</#if>

						<#assign modes = "\"" + sanitizeTuple.getObject(2) + "\"" />

						<#if modes == "\"ALL\"">
							<#assign modes = "Sanitizer.MODE_ALL" />
						<#elseif modes == "\"BAD_WORDS\"">
							<#assign modes = "Sanitizer.MODE_BAD_WORDS" />
						<#elseif modes == "\"XSS\"">
							<#assign modes = "Sanitizer.MODE_XSS" />
						<#else>
							<#assign modes = "StringUtil.split(\"" + sanitizeTuple.getObject(2) + "\")" />
						</#if>

						${entity.varName}.set${colMethodName}(SanitizerUtil.sanitize(companyId, groupId, userId, ${apiPackagePath}.model.${entity.name}.class.getName(), ${entity.PKVarName}, ${contentType}, ${modes}, ${entity.varName}.get${colMethodName}(), null));
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
						nestedSetsTreeManager.insert(${entity.varName}, fetchByPrimaryKey(${entity.varName}.getParent${pkEntityColumn.methodName}()));
					}
					else if (${entity.varName}.getParent${pkEntityColumn.methodName}() != ${entity.varName}ModelImpl.getOriginalParent${pkEntityColumn.methodName}()){
						nestedSetsTreeManager.move(${entity.varName}, fetchByPrimaryKey(${entity.varName}ModelImpl.getOriginalParent${pkEntityColumn.methodName}()), fetchByPrimaryKey(${entity.varName}.getParent${pkEntityColumn.methodName}()));
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
				<#if entity.hasLazyBlobEntityColumn()>

					<#-- Workaround for HHH-2680 -->

					session.evict(${entity.varName});
					session.saveOrUpdate(${entity.varName});
				<#else>
					${entity.varName} = (${entity.name})session.merge(${entity.varName});
				</#if>
			}

			<#if entity.hasLazyBlobEntityColumn()>
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

		<#if columnBitmaskEnabled>
			if (!${entity.name}ModelImpl.COLUMN_BITMASK_ENABLED) {
				finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
			}
			else
		</#if>

		if (isNew) {
			<#if entity.finderEntityColumns?size &gt; 64>
				finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
			<#else>
				<#if columnBitmaskEnabled && (entity.collectionEntityFinders?size != 0)>
					Object[]
					<#list entity.collectionEntityFinders as entityFinder>
						<#assign entityColumns = entityFinder.entityColumns />

						args = new Object[] {
							<#list entityColumns as entityColumn>
								${entity.varName}ModelImpl.get${entityColumn.methodName}()

								<#if entityColumn_has_next>
									,
								</#if>
							</#list>
						};

						finderCache.removeResult(FINDER_PATH_COUNT_BY_${entityFinder.name?upper_case}, args);
						finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_${entityFinder.name?upper_case}, args);
					</#list>
				</#if>

				finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL, FINDER_ARGS_EMPTY);
			</#if>
		}

		<#if entity.collectionEntityFinders?size != 0>
			else {
				<#list entity.collectionEntityFinders as entityFinder>
					<#assign entityColumns = entityFinder.entityColumns />
					if (
						<#if columnBitmaskEnabled>
							(${entity.varName}ModelImpl.getColumnBitmask() & FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_${entityFinder.name?upper_case}.getColumnBitmask()) != 0
						<#else>
							<#list entityColumns as entityColumn>
								<#if entityColumn.isPrimitiveType()>
									(${entity.varName}.get${entityColumn.methodName}() != ${entity.varName}ModelImpl.getOriginal${entityColumn.methodName}())
								<#else>
									!Objects.equals(${entity.varName}.get${entityColumn.methodName}(), ${entity.varName}ModelImpl.getOriginal${entityColumn.methodName}())
								</#if>

								<#if entityColumn_has_next>
									||
								</#if>
							</#list>
						</#if>
						) {

						Object[] args = new Object[] {
							<#list entityColumns as entityColumn>
								${entity.varName}ModelImpl.getOriginal${entityColumn.methodName}()

								<#if entityColumn_has_next>
									,
								</#if>
							</#list>
						};

						finderCache.removeResult(FINDER_PATH_COUNT_BY_${entityFinder.name?upper_case}, args);
						finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_${entityFinder.name?upper_case}, args);

						args = new Object[] {
							<#list entityColumns as entityColumn>
								${entity.varName}ModelImpl.get${entityColumn.methodName}()

								<#if entityColumn_has_next>
									,
								</#if>
							</#list>
						};

						finderCache.removeResult(FINDER_PATH_COUNT_BY_${entityFinder.name?upper_case}, args);
						finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_${entityFinder.name?upper_case}, args);
					}
				</#list>
			}
		</#if>

		entityCache.putResult(${entity.name}ModelImpl.ENTITY_CACHE_ENABLED, ${entity.name}Impl.class, ${entity.varName}.getPrimaryKey(), ${entity.varName}, false);

		<#if entity.uniqueEntityFinders?size &gt; 0>
			clearUniqueFindersCache(${entity.varName}ModelImpl, false);
			cacheUniqueFindersCache(${entity.varName}ModelImpl);
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

		<#list entity.regularEntityColumns as entityColumn>
			${entity.varName}Impl.set${entityColumn.methodName}(

			<#if stringUtil.equals(entityColumn.type, "boolean")>
				${entity.varName}.is${entityColumn.methodName}()
			<#else>
				${entity.varName}.get${entityColumn.methodName}()
			</#if>

			);
		</#list>

		return ${entity.varName}Impl;
	}

	/**
	 * Returns the ${entity.humanName} with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the ${entity.humanName}
	 * @return the ${entity.humanName}
	 * @throws ${noSuchEntity}Exception if a ${entity.humanName} with the primary key could not be found
	 */
	@Override
	public ${entity.name} findByPrimaryKey(Serializable primaryKey) throws ${noSuchEntity}Exception {
		${entity.name} ${entity.varName} = fetchByPrimaryKey(primaryKey);

		if (${entity.varName} == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new ${noSuchEntity}Exception(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return ${entity.varName};
	}

	/**
	 * Returns the ${entity.humanName} with the primary key or throws a {@link ${noSuchEntity}Exception} if it could not be found.
	 *
	 * @param ${entity.PKVarName} the primary key of the ${entity.humanName}
	 * @return the ${entity.humanName}
	 * @throws ${noSuchEntity}Exception if a ${entity.humanName} with the primary key could not be found
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
		Serializable serializable = entityCache.getResult(${entity.name}ModelImpl.ENTITY_CACHE_ENABLED, ${entity.name}Impl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		${entity.name} ${entity.varName} = (${entity.name})serializable;

		if (${entity.varName} == null) {
			Session session = null;

			try {
				session = openSession();

				${entity.varName} = (${entity.name})session.get(${entity.name}Impl.class, primaryKey);

				if (${entity.varName} != null) {
					cacheResult(${entity.varName});
				}
				else {
					entityCache.putResult(${entity.name}ModelImpl.ENTITY_CACHE_ENABLED, ${entity.name}Impl.class, primaryKey, nullModel);
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
				Serializable serializable = entityCache.getResult(${entity.name}ModelImpl.ENTITY_CACHE_ENABLED, ${entity.name}Impl.class, primaryKey);

				if (serializable != nullModel) {
					if (serializable == null) {
						if (uncachedPrimaryKeys == null) {
							uncachedPrimaryKeys = new HashSet<Serializable>();
						}

						uncachedPrimaryKeys.add(primaryKey);
					}
					else {
						map.put(primaryKey, (${entity.name})serializable);
					}
				}
			}

			if (uncachedPrimaryKeys == null) {
				return map;
			}

			StringBundler query = new StringBundler(uncachedPrimaryKeys.size() * 2 + 1);

			query.append(_SQL_SELECT_${entity.alias?upper_case}_WHERE_PKS_IN);

			<#if stringUtil.equals(entity.PKClassName, "String")>
				for (int i = 0; i < uncachedPrimaryKeys.size(); i++) {
					query.append("?");

					query.append(",");
				}
			<#else>
				for (Serializable primaryKey : uncachedPrimaryKeys) {
					query.append((${entity.PKClassName})primaryKey);

					query.append(",");
				}
			</#if>

			query.setIndex(query.index() - 1);

			query.append(")");

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				<#if stringUtil.equals(entity.PKClassName, "String")>
					QueryPos qPos = QueryPos.getInstance(q);

					for (Serializable primaryKey : uncachedPrimaryKeys) {
						qPos.add((String)primaryKey);
					}
				</#if>

				for (${entity.name} ${entity.varName} : (List<${entity.name}>)q.list()) {
					map.put(${entity.varName}.getPrimaryKeyObj(), ${entity.varName});

					cacheResult(${entity.varName});

					uncachedPrimaryKeys.remove(${entity.varName}.getPrimaryKeyObj());
				}

				for (Serializable primaryKey : uncachedPrimaryKeys) {
					entityCache.putResult(${entity.name}ModelImpl.ENTITY_CACHE_ENABLED, ${entity.name}Impl.class, primaryKey, nullModel);
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
				query = new StringBundler(2 + (orderByComparator.getOrderByFields().length * 2));

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

	<#list entity.entityColumns as entityColumn>
		<#if entityColumn.isCollection() && entityColumn.isMappingManyToMany()>
			<#assign referenceEntity = serviceBuilder.getEntity(entityColumn.entityName) />

			/**
			 * Returns the primaryKeys of ${referenceEntity.humanNames} associated with the ${entity.humanName}.
			 *
			 * @param pk the primary key of the ${entity.humanName}
			 * @return long[] of the primaryKeys of ${referenceEntity.humanNames} associated with the ${entity.humanName}
			 */
			@Override
			public long[] get${referenceEntity.name}PrimaryKeys(${entity.PKClassName} pk) {
				long[] pks = ${entity.varName}To${referenceEntity.name}TableMapper.getRightPrimaryKeys(pk);

				return pks.clone();
			}

			/**
			 * Returns all the ${referenceEntity.humanNames} associated with the ${entity.humanName}.
			 *
			 * @param pk the primary key of the ${entity.humanName}
			 * @return the ${referenceEntity.humanNames} associated with the ${entity.humanName}
			 */
			@Override
			public List<${referenceEntity.apiPackagePath}.model.${referenceEntity.name}> get${referenceEntity.names}(${entity.PKClassName} pk) {
				return get${referenceEntity.names}(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
			}

			/**
			 * Returns a range of all the ${referenceEntity.humanNames} associated with the ${entity.humanName}.
			 *
			 * <p>
			 * <#include "range_comment.ftl">
			 * </p>
			 *
			 * @param pk the primary key of the ${entity.humanName}
			 * @param start the lower bound of the range of ${entity.humanNames}
			 * @param end the upper bound of the range of ${entity.humanNames} (not inclusive)
			 * @return the range of ${referenceEntity.humanNames} associated with the ${entity.humanName}
			 */
			@Override
			public List<${referenceEntity.apiPackagePath}.model.${referenceEntity.name}> get${referenceEntity.names}(${entity.PKClassName} pk, int start, int end) {
				return get${referenceEntity.names}(pk, start, end, null);
			}

			/**
			 * Returns an ordered range of all the ${referenceEntity.humanNames} associated with the ${entity.humanName}.
			 *
			 * <p>
			 * <#include "range_comment.ftl">
			 * </p>
			 *
			 * @param pk the primary key of the ${entity.humanName}
			 * @param start the lower bound of the range of ${entity.humanNames}
			 * @param end the upper bound of the range of ${entity.humanNames} (not inclusive)
			 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
			 * @return the ordered range of ${referenceEntity.humanNames} associated with the ${entity.humanName}
			 */
			@Override
			public List<${referenceEntity.apiPackagePath}.model.${referenceEntity.name}> get${referenceEntity.names}(${entity.PKClassName} pk, int start, int end, OrderByComparator<${referenceEntity.apiPackagePath}.model.${referenceEntity.name}> orderByComparator) {
				return ${entity.varName}To${referenceEntity.name}TableMapper.getRightBaseModels(pk, start, end, orderByComparator);
			}

			/**
			 * Returns the number of ${referenceEntity.humanNames} associated with the ${entity.humanName}.
			 *
			 * @param pk the primary key of the ${entity.humanName}
			 * @return the number of ${referenceEntity.humanNames} associated with the ${entity.humanName}
			 */
			@Override
			public int get${referenceEntity.names}Size(${entity.PKClassName} pk) {
				long[] pks = ${entity.varName}To${referenceEntity.name}TableMapper.getRightPrimaryKeys(pk);

				return pks.length;
			}

			/**
			 * Returns <code>true</code> if the ${referenceEntity.humanName} is associated with the ${entity.humanName}.
			 *
			 * @param pk the primary key of the ${entity.humanName}
			 * @param ${referenceEntity.varName}PK the primary key of the ${referenceEntity.humanName}
			 * @return <code>true</code> if the ${referenceEntity.humanName} is associated with the ${entity.humanName}; <code>false</code> otherwise
			 */
			@Override
			public boolean contains${referenceEntity.name}(${entity.PKClassName} pk, ${referenceEntity.PKClassName} ${referenceEntity.varName}PK) {
				return ${entity.varName}To${referenceEntity.name}TableMapper.containsTableMapping(pk, ${referenceEntity.varName}PK);
			}

			/**
			 * Returns <code>true</code> if the ${entity.humanName} has any ${referenceEntity.humanNames} associated with it.
			 *
			 * @param pk the primary key of the ${entity.humanName} to check for associations with ${referenceEntity.humanNames}
			 * @return <code>true</code> if the ${entity.humanName} has any ${referenceEntity.humanNames} associated with it; <code>false</code> otherwise
			 */
			@Override
			public boolean contains${referenceEntity.names}(${entity.PKClassName} pk) {
				if (get${referenceEntity.names}Size(pk)> 0) {
					return true;
				}
				else {
					return false;
				}
			}

			<#if entityColumn.isMappingManyToMany()>
				<#assign noSuchTempEntity = serviceBuilder.getNoSuchEntityException(referenceEntity) />

				/**
				 * Adds an association between the ${entity.humanName} and the ${referenceEntity.humanName}. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param ${referenceEntity.varName}PK the primary key of the ${referenceEntity.humanName}
				 */
				@Override
				public void add${referenceEntity.name}(${entity.PKClassName} pk, ${referenceEntity.PKClassName} ${referenceEntity.varName}PK) {
					${entity.name} ${entity.varName} = fetchByPrimaryKey(pk);

					if (${entity.varName} == null) {
						${entity.varName}To${referenceEntity.name}TableMapper.addTableMapping(companyProvider.getCompanyId(), pk, ${referenceEntity.varName}PK);
					}
					else {
						${entity.varName}To${referenceEntity.name}TableMapper.addTableMapping(${entity.varName}.getCompanyId(), pk, ${referenceEntity.varName}PK);
					}
				}

				/**
				 * Adds an association between the ${entity.humanName} and the ${referenceEntity.humanName}. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param ${referenceEntity.varName} the ${referenceEntity.humanName}
				 */
				@Override
				public void add${referenceEntity.name}(${entity.PKClassName} pk, ${referenceEntity.apiPackagePath}.model.${referenceEntity.name} ${referenceEntity.varName}) {
					${entity.name} ${entity.varName} = fetchByPrimaryKey(pk);

					if (${entity.varName} == null) {
						${entity.varName}To${referenceEntity.name}TableMapper.addTableMapping(companyProvider.getCompanyId(), pk, ${referenceEntity.varName}.getPrimaryKey());
					}
					else {
						${entity.varName}To${referenceEntity.name}TableMapper.addTableMapping(${entity.varName}.getCompanyId(), pk, ${referenceEntity.varName}.getPrimaryKey());
					}
				}

				/**
				 * Adds an association between the ${entity.humanName} and the ${referenceEntity.humanNames}. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param ${referenceEntity.varName}PKs the primary keys of the ${referenceEntity.humanNames}
				 */
				@Override
				public void add${referenceEntity.names}(${entity.PKClassName} pk, ${referenceEntity.PKClassName}[] ${referenceEntity.varName}PKs) {
					long companyId = 0;

					${entity.name} ${entity.varName} = fetchByPrimaryKey(pk);

					if (${entity.varName} == null) {
						companyId = companyProvider.getCompanyId();
					}
					else {
						companyId = ${entity.varName}.getCompanyId();
					}

					${entity.varName}To${referenceEntity.name}TableMapper.addTableMappings(companyId, pk, ${referenceEntity.varName}PKs);
				}

				/**
				 * Adds an association between the ${entity.humanName} and the ${referenceEntity.humanNames}. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param ${referenceEntity.varNames} the ${referenceEntity.humanNames}
				 */
				@Override
				public void add${referenceEntity.names}(${entity.PKClassName} pk, List<${referenceEntity.apiPackagePath}.model.${referenceEntity.name}> ${referenceEntity.varNames}) {
					add${referenceEntity.names}(pk, ListUtil.toLongArray(${referenceEntity.varNames}, ${referenceEntity.apiPackagePath}.model.${referenceEntity.name}.${textFormatter.format(textFormatter.format(referenceEntity.getPKVarName(), 7), 0)}_ACCESSOR));
				}

				/**
				 * Clears all associations between the ${entity.humanName} and its ${referenceEntity.humanNames}. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName} to clear the associated ${referenceEntity.humanNames} from
				 */
				@Override
				public void clear${referenceEntity.names}(${entity.PKClassName} pk) {
					${entity.varName}To${referenceEntity.name}TableMapper.deleteLeftPrimaryKeyTableMappings(pk);
				}

				/**
				 * Removes the association between the ${entity.humanName} and the ${referenceEntity.humanName}. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param ${referenceEntity.varName}PK the primary key of the ${referenceEntity.humanName}
				 */
				@Override
				public void remove${referenceEntity.name}(${entity.PKClassName} pk, ${referenceEntity.PKClassName} ${referenceEntity.varName}PK) {
					${entity.varName}To${referenceEntity.name}TableMapper.deleteTableMapping(pk, ${referenceEntity.varName}PK);
				}

				/**
				 * Removes the association between the ${entity.humanName} and the ${referenceEntity.humanName}. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param ${referenceEntity.varName} the ${referenceEntity.humanName}
				 */
				@Override
				public void remove${referenceEntity.name}(${entity.PKClassName} pk, ${referenceEntity.apiPackagePath}.model.${referenceEntity.name} ${referenceEntity.varName}) {
					${entity.varName}To${referenceEntity.name}TableMapper.deleteTableMapping(pk, ${referenceEntity.varName}.getPrimaryKey());
				}

				/**
				 * Removes the association between the ${entity.humanName} and the ${referenceEntity.humanNames}. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param ${referenceEntity.varName}PKs the primary keys of the ${referenceEntity.humanNames}
				 */
				@Override
				public void remove${referenceEntity.names}(${entity.PKClassName} pk, ${referenceEntity.PKClassName}[] ${referenceEntity.varName}PKs) {
					${entity.varName}To${referenceEntity.name}TableMapper.deleteTableMappings(pk, ${referenceEntity.varName}PKs);
				}

				/**
				 * Removes the association between the ${entity.humanName} and the ${referenceEntity.humanNames}. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param ${referenceEntity.varNames} the ${referenceEntity.humanNames}
				 */
				@Override
				public void remove${referenceEntity.names}(${entity.PKClassName} pk, List<${referenceEntity.apiPackagePath}.model.${referenceEntity.name}> ${referenceEntity.varNames}) {
					remove${referenceEntity.names}(pk, ListUtil.toLongArray(${referenceEntity.varNames}, ${referenceEntity.apiPackagePath}.model.${referenceEntity.name}.${textFormatter.format(textFormatter.format(referenceEntity.getPKVarName(), 7), 0)}_ACCESSOR));
				}

				/**
				 * Sets the ${referenceEntity.humanNames} associated with the ${entity.humanName}, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param ${referenceEntity.varName}PKs the primary keys of the ${referenceEntity.humanNames} to be associated with the ${entity.humanName}
				 */
				@Override
				public void set${referenceEntity.names}(${entity.PKClassName} pk, ${referenceEntity.PKClassName}[] ${referenceEntity.varName}PKs) {
					Set<Long> new${referenceEntity.name}PKsSet = SetUtil.fromArray(${referenceEntity.varName}PKs);
					Set<Long> old${referenceEntity.name}PKsSet = SetUtil.fromArray(${entity.varName}To${referenceEntity.name}TableMapper.getRightPrimaryKeys(pk));

					Set<Long> remove${referenceEntity.name}PKsSet = new HashSet<Long>(old${referenceEntity.name}PKsSet);

					remove${referenceEntity.name}PKsSet.removeAll(new${referenceEntity.name}PKsSet);

					${entity.varName}To${referenceEntity.name}TableMapper.deleteTableMappings(pk, ArrayUtil.toLongArray(remove${referenceEntity.name}PKsSet));

					new${referenceEntity.name}PKsSet.removeAll(old${referenceEntity.name}PKsSet);

					long companyId = 0;

					${entity.name} ${entity.varName} = fetchByPrimaryKey(pk);

					if (${entity.varName} == null) {
						companyId = companyProvider.getCompanyId();
					}
					else {
						companyId = ${entity.varName}.getCompanyId();
					}

					${entity.varName}To${referenceEntity.name}TableMapper.addTableMappings(companyId, pk, ArrayUtil.toLongArray(new${referenceEntity.name}PKsSet));
				}

				/**
				 * Sets the ${referenceEntity.humanNames} associated with the ${entity.humanName}, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param ${referenceEntity.varNames} the ${referenceEntity.humanNames} to be associated with the ${entity.humanName}
				 */
				@Override
				public void set${referenceEntity.names}(${entity.PKClassName} pk, List<${referenceEntity.apiPackagePath}.model.${referenceEntity.name}> ${referenceEntity.varNames}) {
					try {
						${referenceEntity.PKClassName}[] ${referenceEntity.varName}PKs = new ${referenceEntity.PKClassName}[${referenceEntity.varNames}.size()];

						for (int i = 0; i < ${referenceEntity.varNames}.size(); i++) {
							${referenceEntity.apiPackagePath}.model.${referenceEntity.name} ${referenceEntity.varName} = ${referenceEntity.varNames}.get(i);

							${referenceEntity.varName}PKs[i] = ${referenceEntity.varName}.getPrimaryKey();
						}

						set${referenceEntity.names}(pk, ${referenceEntity.varName}PKs);
					}
					catch (Exception e) {
						throw processException(e);
					}
				}
			</#if>
		</#if>
	</#list>

	<#if entity.badEntityColumns?size != 0>
		@Override
		public Set<String> getBadColumnNames() {
			return _badColumnNames;
		}
	</#if>

	<#if entity.hasCompoundPK()>
		@Override
		public Set<String> getCompoundPKColumnNames() {
			return _compoundPKColumnNames;
		}
	</#if>

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ${entity.name}ModelImpl.TABLE_COLUMNS_MAP;
	}

	<#if entity.isHierarchicalTree()>
		@Override
		public long countAncestors(${entity.name} ${entity.varName}) {
			Object[] finderArgs = new Object[] {${entity.varName}.get${scopeEntityColumn.methodName}(), ${entity.varName}.getLeft${pkEntityColumn.methodName}(), ${entity.varName}.getRight${pkEntityColumn.methodName}()};

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
			Object[] finderArgs = new Object[] {${entity.varName}.get${scopeEntityColumn.methodName}(), ${entity.varName}.getLeft${pkEntityColumn.methodName}(), ${entity.varName}.getRight${pkEntityColumn.methodName}()};

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
			Object[] finderArgs = new Object[] {${entity.varName}.get${scopeEntityColumn.methodName}(), ${entity.varName}.getLeft${pkEntityColumn.methodName}(), ${entity.varName}.getRight${pkEntityColumn.methodName}()};

			List<${entity.name}> list = (List<${entity.name}>)finderCache.getResult(FINDER_PATH_WITH_PAGINATION_GET_ANCESTORS, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (${entity.name} temp${entity.name} : list) {
					if ((${entity.varName}.getLeft${pkEntityColumn.methodName}() < temp${entity.name}.getLeft${pkEntityColumn.methodName}()) || (${entity.varName}.getRight${pkEntityColumn.methodName}() > temp${entity.name}.getRight${pkEntityColumn.methodName}())) {
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
			Object[] finderArgs = new Object[] {${entity.varName}.get${scopeEntityColumn.methodName}(), ${entity.varName}.getLeft${pkEntityColumn.methodName}(), ${entity.varName}.getRight${pkEntityColumn.methodName}()};

			List<${entity.name}> list = (List<${entity.name}>)finderCache.getResult(FINDER_PATH_WITH_PAGINATION_GET_DESCENDANTS, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (${entity.name} temp${entity.name} : list) {
					if ((${entity.varName}.getLeft${pkEntityColumn.methodName}() > temp${entity.name}.getLeft${pkEntityColumn.methodName}()) || (${entity.varName}.getRight${pkEntityColumn.methodName}() < temp${entity.name}.getRight${pkEntityColumn.methodName}())) {
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
		 * @param ${scopeEntityColumn.name} the ID of the scope
		 * @param force whether to force the rebuild even if the tree is not stale
		 */
		@Override
		public void rebuildTree(long ${scopeEntityColumn.name}, boolean force) {
			if (!rebuildTreeEnabled) {
				return;
			}

			if (force || (countOrphanTreeNodes(${scopeEntityColumn.name}) > 0)) {
				Session session = null;

				try {
					session = openSession();

					if (session.isDirty()) {
						session.flush();
					}

					SQLQuery selectQuery = session.createSQLQuery("SELECT ${pkEntityColumn.DBName} FROM ${entity.table} WHERE ${scopeEntityColumn.DBName} = ? AND parent${pkEntityColumn.methodName} = ? ORDER BY ${pkEntityColumn.name} ASC");

					selectQuery.addScalar("${pkEntityColumn.name}", com.liferay.portal.kernel.dao.orm.Type.LONG);

					SQLQuery updateQuery = session.createSQLQuery("UPDATE ${entity.table} SET left${pkEntityColumn.methodName} = ?, right${pkEntityColumn.methodName} = ? WHERE ${pkEntityColumn.name} = ?");

					rebuildTree(session, selectQuery, updateQuery, ${scopeEntityColumn.name}, 0, 0);
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

		protected long countOrphanTreeNodes(long ${scopeEntityColumn.name}) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSynchronizedSQLQuery("SELECT COUNT(*) AS COUNT_VALUE FROM ${entity.table} WHERE ${scopeEntityColumn.DBName} = ? AND (left${pkEntityColumn.methodName} = 0 OR left${pkEntityColumn.methodName} IS NULL OR right${pkEntityColumn.methodName} = 0 OR right${pkEntityColumn.methodName} IS NULL)");

				q.addScalar(COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(${scopeEntityColumn.name});

				return (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		protected long rebuildTree(Session session, SQLQuery selectQuery, SQLQuery updateQuery, long ${scopeEntityColumn.name}, long parent${pkEntityColumn.methodName}, long left${pkEntityColumn.methodName}) {
			long right${pkEntityColumn.methodName} = left${pkEntityColumn.methodName} + 1;

			QueryPos qPos = QueryPos.getInstance(selectQuery);

			qPos.add(${scopeEntityColumn.name});
			qPos.add(parent${pkEntityColumn.methodName});

			List<Long> ${pkEntityColumn.names} = selectQuery.list();

			for (long ${pkEntityColumn.name} : ${pkEntityColumn.names}) {
				right${pkEntityColumn.methodName} = rebuildTree(session, selectQuery, updateQuery, ${scopeEntityColumn.name}, ${pkEntityColumn.name}, right${pkEntityColumn.methodName});
			}

			if (parent${pkEntityColumn.methodName} > 0) {
				qPos = QueryPos.getInstance(updateQuery);

				qPos.add(left${pkEntityColumn.methodName});
				qPos.add(right${pkEntityColumn.methodName});
				qPos.add(parent${pkEntityColumn.methodName});

				updateQuery.executeUpdate();
			}

			return right${pkEntityColumn.methodName} + 1;
		}
	</#if>

	/**
	 * Initializes the ${entity.humanName} persistence.
	 */
	public void afterPropertiesSet() {

		<#list entity.entityColumns as entityColumn>
			<#if entityColumn.isCollection() && entityColumn.isMappingManyToMany()>
				<#assign
					referenceEntity = serviceBuilder.getEntity(entityColumn.entityName)

					entityMapping = serviceBuilder.getEntityMapping(entityColumn.mappingTableName)

					companyEntity = serviceBuilder.getEntity(entityMapping.getEntityName(0))
				/>

				${entity.varName}To${referenceEntity.name}TableMapper = TableMapperFactory.getTableMapper("${entityColumn.mappingTableName}", "${companyEntity.PKDBName}", "${entity.PKDBName}", "${referenceEntity.PKDBName}", this, ${referenceEntity.varName}Persistence);
			</#if>
		</#list>
	}

	public void destroy() {
		entityCache.removeCache(${entity.name}Impl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		<#list entity.entityColumns as entityColumn>
			<#if entityColumn.isCollection() && entityColumn.isMappingManyToMany()>
				TableMapperFactory.removeTableMapper("${entityColumn.mappingTableName}");
			</#if>
		</#list>
	}

	<#if entity.isShardedModel()>
		<#if osgiModule>
			@ServiceReference(type = CompanyProviderWrapper.class)
		<#else>
			@BeanReference(type = CompanyProviderWrapper.class)
		</#if>
		protected CompanyProvider companyProvider;
	<#else>
		<#list entity.entityColumns as entityColumn>
			<#if entityColumn.isCollection() && entityColumn.isMappingManyToMany()>
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

	<#list entity.entityColumns as entityColumn>
		<#if entityColumn.isCollection() && entityColumn.isMappingManyToMany()>
			<#assign referenceEntity = serviceBuilder.getEntity(entityColumn.entityName) />

			@BeanReference(type = ${referenceEntity.name}Persistence.class)
			protected ${referenceEntity.name}Persistence ${referenceEntity.varName}Persistence;
			protected TableMapper<${entity.name}, ${referenceEntity.apiPackagePath}.model.${referenceEntity.name}> ${entity.varName}To${referenceEntity.name}TableMapper;
		</#if>
	</#list>

	<#if entity.isHierarchicalTree()>
		protected NestedSetsTreeManager<${entity.name}> nestedSetsTreeManager = new PersistenceNestedSetsTreeManager<${entity.name}>(this, "${entity.table}", "${entity.name}", ${entity.name}Impl.class, "${pkEntityColumn.DBName}", "${scopeEntityColumn.DBName}", "left${pkEntityColumn.methodName}", "right${pkEntityColumn.methodName}");
		protected boolean rebuildTreeEnabled = true;
	</#if>

	private static final String _SQL_SELECT_${entity.alias?upper_case} = "SELECT ${entity.alias} FROM ${entity.name} ${entity.alias}";

	<#if !entity.hasCompoundPK()>
		private static final String _SQL_SELECT_${entity.alias?upper_case}_WHERE_PKS_IN = "SELECT ${entity.alias} FROM ${entity.name} ${entity.alias} WHERE ${entity.PKDBName} IN (";
	</#if>

	<#if entity.entityFinders?size != 0>
		private static final String _SQL_SELECT_${entity.alias?upper_case}_WHERE = "SELECT ${entity.alias} FROM ${entity.name} ${entity.alias} WHERE ";
	</#if>

	private static final String _SQL_COUNT_${entity.alias?upper_case} = "SELECT COUNT(${entity.alias}) FROM ${entity.name} ${entity.alias}";

	<#if entity.entityFinders?size != 0>
		private static final String _SQL_COUNT_${entity.alias?upper_case}_WHERE = "SELECT COUNT(${entity.alias}) FROM ${entity.name} ${entity.alias} WHERE ";
	</#if>

	<#if entity.isPermissionCheckEnabled()>
		private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "${entity.alias}.${entity.filterPKEntityColumn.DBName}";

		<#if entity.isPermissionedModel()>
			<#if entity.hasEntityColumn("userId")>
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

	<#if entity.entityFinders?size != 0>
		private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ${entity.name} exists with the key {";
	</#if>

	private static final Log _log = LogFactoryUtil.getLog(${entity.name}PersistenceImpl.class);

	<#if entity.badEntityColumns?size != 0>
		private static final Set<String> _badColumnNames = SetUtil.fromArray(
			new String[] {
				<#list entity.badEntityColumns as badEntityColumn>
					"${badEntityColumn.name}"

					<#if badEntityColumn_has_next>
						,
					</#if>
				</#list>
			});
	</#if>

	<#if entity.hasCompoundPK()>
		private static final Set<String> _compoundPKColumnNames = SetUtil.fromArray(
			new String[] {
				<#list entity.PKEntityColumns as entityColumn>
					"${entityColumn.name}"

					<#if entityColumn_has_next>
						,
					</#if>
				</#list>
			});
	</#if>

}

<#function bindParameter entityColumns>
	<#list entityColumns as entityColumn>
		<#if !entityColumn.hasArrayableOperator() || stringUtil.equals(entityColumn.type, "String")>
			<#return true>
		</#if>
	</#list>

	<#return false>
</#function>

<#macro finderQPos
	_arrayable = false
>
	<#list entityColumns as entityColumn>
		<#if _arrayable && entityColumn.hasArrayableOperator()>
			<#if stringUtil.equals(entityColumn.type, "String")>
				for (String ${entityColumn.name} : ${entityColumn.names}) {
					if (${entityColumn.name} != null && !${entityColumn.name}.isEmpty()) {
						qPos.add(${entityColumn.name});
					}
				}
			</#if>
		<#elseif entityColumn.isPrimitiveType()>
			qPos.add(${entityColumn.name}${serviceBuilder.getPrimitiveObjValue("${entityColumn.type}")});

		<#else>
			if (bind${entityColumn.methodName}) {
				qPos.add(
					<#if stringUtil.equals(entityColumn.type, "Date")>
						new Timestamp(${entityColumn.name}.getTime())
					<#elseif stringUtil.equals(entityColumn.type, "String") && !entityColumn.isCaseSensitive()>
						StringUtil.toLowerCase(${entityColumn.name})
					<#else>
						${entityColumn.name}
					</#if>
				);
			}
		</#if>
	</#list>
</#macro>