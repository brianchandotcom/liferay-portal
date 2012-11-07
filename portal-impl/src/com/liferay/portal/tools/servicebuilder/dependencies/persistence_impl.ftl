<#if entity.isHierarchicalTree()>
	<#if entity.hasColumn("groupId")>
		<#assign scopeColumn = entity.getColumn("groupId")>
	<#else>
		<#assign scopeColumn = entity.getColumn("companyId")>
	</#if>

	<#assign pkColumn = entity.getPKList()?first>
</#if>

package ${packagePath}.service.persistence;

<#assign noSuchEntity = serviceBuilder.getNoSuchEntityException(entity)>

import ${packagePath}.${noSuchEntity}Exception;
import ${packagePath}.model.${entity.name};
import ${packagePath}.model.impl.${entity.name}Impl;
import ${packagePath}.model.impl.${entity.name}ModelImpl;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQuery;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQueryFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.RowMapper;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
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
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.CalendarUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
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
 * @see ${entity.name}Util
 * @generated
 */
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

	<#list entity.getFinderList() as finder>
		<#assign finderColsList = finder.getColumns()>

		<#if finder.isCollection()>
			public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_${finder.name?upper_case} = new FinderPath(
				${entity.name}ModelImpl.ENTITY_CACHE_ENABLED,
				${entity.name}ModelImpl.FINDER_CACHE_ENABLED,
				${entity.name}Impl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findBy${finder.name}",
				new String[] {
					<#list finderColsList as finderCol>
						${serviceBuilder.getPrimitiveObj("${finderCol.type}")}.class.getName(),
					</#list>

					"java.lang.Integer", "java.lang.Integer", "com.liferay.portal.kernel.util.OrderByComparator"
				});

			<#if !finder.hasCustomComparator()>
				public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_${finder.name?upper_case} = new FinderPath(
					${entity.name}ModelImpl.ENTITY_CACHE_ENABLED,
					${entity.name}ModelImpl.FINDER_CACHE_ENABLED,
					${entity.name}Impl.class,
					FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
					"findBy${finder.name}",
					new String[] {
						<#list finderColsList as finderCol>
							${serviceBuilder.getPrimitiveObj("${finderCol.type}")}.class.getName()

							<#if finderCol_has_next>
								,
							</#if>
						</#list>
					}

					<#if columnBitmaskEnabled>
						,

						<#list finderColsList as finderCol>
							${entity.name}ModelImpl.${finderCol.name?upper_case}_COLUMN_BITMASK

							<#if finderCol_has_next>
								|
							</#if>
						</#list>
					</#if>

					);
			</#if>
		<#else>
			public static final FinderPath FINDER_PATH_FETCH_BY_${finder.name?upper_case} = new FinderPath(
				${entity.name}ModelImpl.ENTITY_CACHE_ENABLED,
				${entity.name}ModelImpl.FINDER_CACHE_ENABLED,
				${entity.name}Impl.class,
				FINDER_CLASS_NAME_ENTITY,
				"fetchBy${finder.name}",
				new String[] {
					<#list finderColsList as finderCol>
						${serviceBuilder.getPrimitiveObj("${finderCol.type}")}.class.getName()

						<#if finderCol_has_next>
							,
						</#if>
					</#list>
				}

				<#if columnBitmaskEnabled>
					,

					<#list finderColsList as finderCol>
						${entity.name}ModelImpl.${finderCol.name?upper_case}_COLUMN_BITMASK

						<#if finderCol_has_next>
							|
						</#if>
					</#list>
				</#if>

				);
		</#if>

		<#if !finder.hasCustomComparator()>
			public static final FinderPath FINDER_PATH_COUNT_BY_${finder.name?upper_case} = new FinderPath(
				${entity.name}ModelImpl.ENTITY_CACHE_ENABLED,
				${entity.name}ModelImpl.FINDER_CACHE_ENABLED,
				Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"countBy${finder.name}",
				new String[] {
					<#list finderColsList as finderCol>
						${serviceBuilder.getPrimitiveObj("${finderCol.type}")}.class.getName()

						<#if finderCol_has_next>
							,
						</#if>
					</#list>
				});
		</#if>

		<#if finder.hasArrayableOperator() || finder.hasCustomComparator()>
			public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_${finder.name?upper_case} = new FinderPath(
				${entity.name}ModelImpl.ENTITY_CACHE_ENABLED,
				${entity.name}ModelImpl.FINDER_CACHE_ENABLED,
				Long.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"countBy${finder.name}",
				new String[] {
					<#list finderColsList as finderCol>
						${serviceBuilder.getPrimitiveObj("${finderCol.type}")}.class.getName()

						<#if finderCol_has_next>
							,
						</#if>
					</#list>
				});
		</#if>
	</#list>

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

	/**
	 * Caches the ${entity.humanName} in the entity cache if it is enabled.
	 *
	 * @param ${entity.varName} the ${entity.humanName}
	 */
	public void cacheResult(${entity.name} ${entity.varName}) {
		EntityCacheUtil.putResult(${entity.name}ModelImpl.ENTITY_CACHE_ENABLED, ${entity.name}Impl.class, ${entity.varName}.getPrimaryKey(), ${entity.varName});

		<#list entity.getUniqueFinderList() as finder>
			<#assign finderColsList = finder.getColumns()>

			FinderCacheUtil.putResult(
				FINDER_PATH_FETCH_BY_${finder.name?upper_case},
				new Object[] {
					<#list finderColsList as finderCol>
						<#if finderCol.isPrimitiveType()>
							${serviceBuilder.getPrimitiveObj("${finderCol.type}")}.valueOf(
						</#if>

						${entity.varName}.get${finderCol.methodName}()

						<#if finderCol.isPrimitiveType()>
							)
						</#if>

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
	public void cacheResult(List<${entity.name}> ${entity.varNames}) {
		for (${entity.name} ${entity.varName} : ${entity.varNames}) {
			if (EntityCacheUtil.getResult(${entity.name}ModelImpl.ENTITY_CACHE_ENABLED, ${entity.name}Impl.class, ${entity.varName}.getPrimaryKey()) == null) {
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
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(${entity.name}Impl.class.getName());
		}

		EntityCacheUtil.clearCache(${entity.name}Impl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
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
		EntityCacheUtil.removeResult(${entity.name}ModelImpl.ENTITY_CACHE_ENABLED, ${entity.name}Impl.class, ${entity.varName}.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		<#if entity.getUniqueFinderList()?size &gt; 0>
			clearUniqueFindersCache(${entity.varName});
		</#if>
	}

	@Override
	public void clearCache(List<${entity.name}> ${entity.varNames}) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (${entity.name} ${entity.varName} : ${entity.varNames}) {
			EntityCacheUtil.removeResult(${entity.name}ModelImpl.ENTITY_CACHE_ENABLED, ${entity.name}Impl.class, ${entity.varName}.getPrimaryKey());

			<#if entity.getUniqueFinderList()?size &gt; 0>
				clearUniqueFindersCache(${entity.varName});
			</#if>
		}
	}

	<#if entity.getUniqueFinderList()?size &gt; 0>
		protected void clearUniqueFindersCache(${entity.name} ${entity.varName}) {
			<#list entity.getUniqueFinderList() as finder>
				<#assign finderColsList = finder.getColumns()>

				FinderCacheUtil.removeResult(
					FINDER_PATH_FETCH_BY_${finder.name?upper_case},
					new Object[] {
						<#list finderColsList as finderCol>
							<#if finderCol.isPrimitiveType()>
								${serviceBuilder.getPrimitiveObj("${finderCol.type}")}.valueOf(
							</#if>

							${entity.varName}.get${finderCol.methodName}()

							<#if finderCol.isPrimitiveType()>
								)
							</#if>

							<#if finderCol_has_next>
								,
							</#if>
						</#list>
					});
			</#list>
		}
	</#if>

	/**
	 * Creates a new ${entity.humanName} with the primary key. Does not add the ${entity.humanName} to the database.
	 *
	 * @param ${entity.PKVarName} the primary key for the new ${entity.humanName}
	 * @return the new ${entity.humanName}
	 */
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
	 * @throws SystemException if a system exception occurred
	 */
	public ${entity.name} remove(${entity.PKClassName} ${entity.PKVarName}) throws ${noSuchEntity}Exception, SystemException {
		<#if entity.hasPrimitivePK(false)>
			return remove(${serviceBuilder.getPrimitiveObj("${entity.PKClassName}")}.valueOf(${entity.PKVarName}));
		<#else>
			return remove((Serializable)${entity.PKVarName});
		</#if>
	}

	/**
	 * Removes the ${entity.humanName} with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the ${entity.humanName}
	 * @return the ${entity.humanName} that was removed
	 * @throws ${packagePath}.${noSuchEntity}Exception if a ${entity.humanName} with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ${entity.name} remove(Serializable primaryKey) throws ${noSuchEntity}Exception, SystemException {
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
	protected ${entity.name} removeImpl(${entity.name} ${entity.varName}) throws SystemException {
		${entity.varName} = toUnwrappedModel(${entity.varName});

		<#list entity.columnList as column>
			<#if column.isCollection() && column.isMappingManyToMany()>
				<#assign tempEntity = serviceBuilder.getEntity(column.getEJBName())>

				try {
					clear${tempEntity.names}.clear(${entity.varName}.getPrimaryKey());
				}
				catch (Exception e) {
					throw processException(e);
				}
				finally {
					FinderCacheUtil.clearCache(${entity.name}ModelImpl.MAPPING_TABLE_${stringUtil.upperCase(column.mappingTable)}_NAME);
				}
			</#if>
		</#list>

		<#if entity.isHierarchicalTree()>
			shrinkTree(${entity.varName});
		</#if>

		Session session = null;

		try {
			session = openSession();

			if (${entity.varName}.isCachedModel()) {
				${entity.varName} = (${entity.name})session.get(
					${entity.name}Impl.class, ${entity.varName}.getPrimaryKeyObj());
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
	public ${entity.name} updateImpl(${packagePath}.model.${entity.name} ${entity.varName}) throws SystemException {
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

		<#if uniqueFinderList?size != 0>
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

		<#if entity.isHierarchicalTree()>
			if (isNew) {
				expandTree(${entity.varName}, null);
			}
			else {
				if (${entity.varName}.getParent${pkColumn.methodName}() != ${entity.varName}ModelImpl.getOriginalParent${pkColumn.methodName}()) {
					List<Long> children${pkColumn.methodNames} = getChildrenTree${pkColumn.methodNames}(${entity.varName});

					shrinkTree(${entity.varName});
					expandTree(${entity.varName}, children${pkColumn.methodNames});
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

			if (${entity.varName}.isNew()) {
				session.save(${entity.varName});

				${entity.varName}.setNew(false);
			}
			else {
				session.merge(${entity.varName});
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

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew
			<#if columnBitmaskEnabled>
				|| !${entity.name}ModelImpl.COLUMN_BITMASK_ENABLED
			</#if>
			) {

			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
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
								<#if finderCol.isPrimitiveType()>
									${serviceBuilder.getPrimitiveObj("${finderCol.type}")}.valueOf(
								</#if>

								${entity.varName}ModelImpl.getOriginal${finderCol.methodName}()

								<#if finderCol.isPrimitiveType()>
									)
								</#if>

								<#if finderCol_has_next>
									,
								</#if>
							</#list>
						};

						FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_${finder.name?upper_case}, args);
						FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_${finder.name?upper_case}, args);

						args = new Object[] {
							<#list finderColsList as finderCol>
								<#if finderCol.isPrimitiveType()>
									${serviceBuilder.getPrimitiveObj("${finderCol.type}")}.valueOf(
								</#if>

								${entity.varName}ModelImpl.get${finderCol.methodName}()

								<#if finderCol.isPrimitiveType()>
									)
								</#if>

								<#if finderCol_has_next>
									,
								</#if>
							</#list>
						};

						FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_${finder.name?upper_case}, args);
						FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_${finder.name?upper_case}, args);
					}
				</#if>
			</#list>

			<#if hasEqualComparator>
				}
			</#if>
		</#if>

		EntityCacheUtil.putResult(${entity.name}ModelImpl.ENTITY_CACHE_ENABLED, ${entity.name}Impl.class, ${entity.varName}.getPrimaryKey(), ${entity.varName});

		<#if uniqueFinderList?size &gt; 0>
			if (isNew) {
				<#list uniqueFinderList as finder>
					<#assign finderColsList = finder.getColumns()>

					FinderCacheUtil.putResult(
						FINDER_PATH_FETCH_BY_${finder.name?upper_case},
						new Object[] {
							<#list finderColsList as finderCol>
								<#if finderCol.isPrimitiveType()>
									${serviceBuilder.getPrimitiveObj("${finderCol.type}")}.valueOf(
								</#if>

								${entity.varName}.get${finderCol.methodName}()

								<#if finderCol.isPrimitiveType()>
									)
								</#if>

								<#if finderCol_has_next>
									,
								</#if>
							</#list>
						},
						${entity.varName});
				</#list>
			}
			else {
				<#list uniqueFinderList as finder>
					<#assign finderColsList = finder.getColumns()>

					if ((${entity.varName}ModelImpl.getColumnBitmask() & FINDER_PATH_FETCH_BY_${finder.name?upper_case}.getColumnBitmask()) != 0) {
						Object[] args = new Object[] {
							<#list finderColsList as finderCol>
								<#if finderCol.isPrimitiveType()>
									${serviceBuilder.getPrimitiveObj("${finderCol.type}")}.valueOf(
								</#if>

								${entity.varName}ModelImpl.getOriginal${finderCol.methodName}()

								<#if finderCol.isPrimitiveType()>
									)
								</#if>

								<#if finderCol_has_next>
									,
								</#if>
							</#list>
						};

						<#if !finder.hasCustomComparator()>
							FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_${finder.name?upper_case}, args);
						</#if>

						FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_${finder.name?upper_case}, args);

						FinderCacheUtil.putResult(
							FINDER_PATH_FETCH_BY_${finder.name?upper_case},
							new Object[] {
								<#list finderColsList as finderCol>
									<#if finderCol.isPrimitiveType()>
										${serviceBuilder.getPrimitiveObj("${finderCol.type}")}.valueOf(
									</#if>

									${entity.varName}.get${finderCol.methodName}()

									<#if finderCol.isPrimitiveType()>
										)
									</#if>

									<#if finderCol_has_next>
										,
									</#if>
								</#list>
							},
							${entity.varName});
					}
				</#list>
			}
		</#if>

		<#if entity.hasLazyBlobColumn()>
			${entity.varName}.resetOriginalValues();
		</#if>

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
	 * @throws com.liferay.portal.NoSuchModelException if a ${entity.humanName} with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ${entity.name} findByPrimaryKey(Serializable primaryKey) throws NoSuchModelException, SystemException {
		<#if entity.hasPrimitivePK(false)>
			return findByPrimaryKey(((${serviceBuilder.getPrimitiveObj("${entity.PKClassName}")})primaryKey).${entity.PKClassName}Value());
		<#else>
			return findByPrimaryKey((${entity.PKClassName})primaryKey);
		</#if>
	}

	/**
	 * Returns the ${entity.humanName} with the primary key or throws a {@link ${packagePath}.${noSuchEntity}Exception} if it could not be found.
	 *
	 * @param ${entity.PKVarName} the primary key of the ${entity.humanName}
	 * @return the ${entity.humanName}
	 * @throws ${packagePath}.${noSuchEntity}Exception if a ${entity.humanName} with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ${entity.name} findByPrimaryKey(${entity.PKClassName} ${entity.PKVarName}) throws ${noSuchEntity}Exception, SystemException {
		${entity.name} ${entity.varName} = fetchByPrimaryKey(${entity.PKVarName});

		if (${entity.varName} == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + ${entity.PKVarName});
			}

			throw new ${noSuchEntity}Exception(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + ${entity.PKVarName});
		}

		return ${entity.varName};
	}

	/**
	 * Returns the ${entity.humanName} with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ${entity.humanName}
	 * @return the ${entity.humanName}, or <code>null</code> if a ${entity.humanName} with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ${entity.name} fetchByPrimaryKey(Serializable primaryKey) throws SystemException {
		<#if entity.hasPrimitivePK(false)>
			return fetchByPrimaryKey(((${serviceBuilder.getPrimitiveObj("${entity.PKClassName}")})primaryKey).${entity.PKClassName}Value());
		<#else>
			return fetchByPrimaryKey((${entity.PKClassName})primaryKey);
		</#if>
	}

	/**
	 * Returns the ${entity.humanName} with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ${entity.PKVarName} the primary key of the ${entity.humanName}
	 * @return the ${entity.humanName}, or <code>null</code> if a ${entity.humanName} with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ${entity.name} fetchByPrimaryKey(${entity.PKClassName} ${entity.PKVarName}) throws SystemException {
		${entity.name} ${entity.varName} = (${entity.name})EntityCacheUtil.getResult(${entity.name}ModelImpl.ENTITY_CACHE_ENABLED, ${entity.name}Impl.class, ${entity.PKVarName});

		if (${entity.varName} == _null${entity.name}) {
			return null;
		}

		if (${entity.varName} == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				${entity.varName} = (${entity.name})session.get(${entity.name}Impl.class,

				<#if entity.hasPrimitivePK()>
					${serviceBuilder.getPrimitiveObj("${entity.PKClassName}")}.valueOf(
				</#if>

				${entity.PKVarName}

				<#if entity.hasPrimitivePK()>
					)
				</#if>

				);
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (${entity.varName} != null) {
					cacheResult(${entity.varName});
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(${entity.name}ModelImpl.ENTITY_CACHE_ENABLED, ${entity.name}Impl.class, ${entity.PKVarName}, _null${entity.name});
				}

				closeSession(session);
			}
		}

		return ${entity.varName};
	}

	<#list entity.getFinderList() as finder>
		<#assign finderColsList = finder.getColumns()>

		<#if finder.isCollection()>
			/**
			 * Returns all the ${entity.humanNames} where ${finder.getHumanConditions(false)}.
			 *
			<#list finderColsList as finderCol>
			 * @param ${finderCol.name} the ${finderCol.humanName}
			</#list>
			 * @return the matching ${entity.humanNames}
			 * @throws SystemException if a system exception occurred
			 */
			public List<${entity.name}> findBy${finder.name}(

			<#list finderColsList as finderCol>
				${finderCol.type} ${finderCol.name}

				<#if finderCol_has_next>
					,
				</#if>
			</#list>

			) throws SystemException {
				return findBy${finder.name}(

				<#list finderColsList as finderCol>
					${finderCol.name},
				</#list>

				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
			}

			/**
			 * Returns a range of all the ${entity.humanNames} where ${finder.getHumanConditions(false)}.
			 *
			 * <p>
			 * <#include "range_comment.ftl">
			 * </p>
			 *
			<#list finderColsList as finderCol>
			 * @param ${finderCol.name} the ${finderCol.humanName}
			</#list>
			 * @param start the lower bound of the range of ${entity.humanNames}
			 * @param end the upper bound of the range of ${entity.humanNames} (not inclusive)
			 * @return the range of matching ${entity.humanNames}
			 * @throws SystemException if a system exception occurred
			 */
			public List<${entity.name}> findBy${finder.name}(

			<#list finderColsList as finderCol>
				${finderCol.type} ${finderCol.name},
			</#list>

			int start, int end) throws SystemException {
				return findBy${finder.name}(

				<#list finderColsList as finderCol>
					${finderCol.name},
				</#list>

				start, end, null);
			}

			/**
			 * Returns an ordered range of all the ${entity.humanNames} where ${finder.getHumanConditions(false)}.
			 *
			 * <p>
			 * <#include "range_comment.ftl">
			 * </p>
			 *
			<#list finderColsList as finderCol>
			 * @param ${finderCol.name} the ${finderCol.humanName}
			</#list>
			 * @param start the lower bound of the range of ${entity.humanNames}
			 * @param end the upper bound of the range of ${entity.humanNames} (not inclusive)
			 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
			 * @return the ordered range of matching ${entity.humanNames}
			 * @throws SystemException if a system exception occurred
			 */
			public List<${entity.name}> findBy${finder.name}(

			<#list finderColsList as finderCol>
				${finderCol.type} ${finderCol.name},
			</#list>

			int start, int end, OrderByComparator orderByComparator) throws SystemException {
				FinderPath finderPath = null;
				Object[] finderArgs = null;

				<#if !finder.hasCustomComparator()>
					if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) && (orderByComparator == null)) {
						finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_${finder.name?upper_case};
						finderArgs = new Object[] {
							<#list finderColsList as finderCol>
								${finderCol.name}

								<#if finderCol_has_next>
									,
								</#if>
							</#list>
						};
					}
					else {
				</#if>

				finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_${finder.name?upper_case};
				finderArgs = new Object[] {
					<#list finderColsList as finderCol>
						${finderCol.name},
					</#list>

					start, end, orderByComparator
				};

				<#if !finder.hasCustomComparator()>
					}
				</#if>

				List<${entity.name}> list = (List<${entity.name}>)FinderCacheUtil.getResult(finderPath, finderArgs, this);

				if ((list != null) && !list.isEmpty()) {
					for (${entity.name} ${entity.varName} : list) {
						if (
							<#list finderColsList as finderCol>
								<#if finderCol.isPrimitiveType(false)>
									(${finderCol.name} != ${entity.varName}.get${finderCol.methodName}())
								<#else>
									!Validator.equals(${finderCol.name}, ${entity.varName}.get${finderCol.methodName}())
								</#if>

								<#if finderCol_has_next>
									||
								</#if>
							</#list>
						) {
							list = null;

							break;
						}
					}
				}

				if (list == null) {
					<#include "persistence_impl_find_by_query.ftl">

					String sql = query.toString();

					Session session = null;

					try {
						session = openSession();

						Query q = session.createQuery(sql);

						QueryPos qPos = QueryPos.getInstance(q);

						<#include "persistence_impl_finder_qpos.ftl">

						list = (List<${entity.name}>)QueryUtil.list(q, getDialect(), start, end);
					}
					catch (Exception e) {
						throw processException(e);
					}
					finally {
						if (list == null) {
							FinderCacheUtil.removeResult(finderPath, finderArgs);
						}
						else {
							cacheResult(list);

							FinderCacheUtil.putResult(finderPath, finderArgs, list);
						}

						closeSession(session);
					}
				}

				return list;
			}

			/**
			 * Returns the first ${entity.humanName} in the ordered set where ${finder.getHumanConditions(false)}.
			 *
			<#list finderColsList as finderCol>
			 * @param ${finderCol.name} the ${finderCol.humanName}
			</#list>
			 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
			 * @return the first matching ${entity.humanName}
			 * @throws ${packagePath}.${noSuchEntity}Exception if a matching ${entity.humanName} could not be found
			 * @throws SystemException if a system exception occurred
			 */
			public ${entity.name} findBy${finder.name}_First(

			<#list finderColsList as finderCol>
				${finderCol.type} ${finderCol.name},
			</#list>

			OrderByComparator orderByComparator) throws ${noSuchEntity}Exception, SystemException {
				${entity.name} ${entity.varName} = fetchBy${finder.name}_First(

				<#list finderColsList as finderCol>
					${finderCol.name},
				</#list>

				orderByComparator);

				if (${entity.varName} != null) {
					return ${entity.varName};
				}

				StringBundler msg = new StringBundler(${(finderColsList?size * 2) + 2});

				msg.append(_NO_SUCH_ENTITY_WITH_KEY);

				<#list finderColsList as finderCol>
					msg.append("<#if finderCol_index != 0>, </#if>${finderCol.name}=");
					msg.append(${finderCol.name});

					<#if !finderCol_has_next>
						msg.append(StringPool.CLOSE_CURLY_BRACE);
					</#if>
				</#list>

				throw new ${noSuchEntity}Exception(msg.toString());
			}

			/**
			 * Returns the first ${entity.humanName} in the ordered set where ${finder.getHumanConditions(false)}.
			 *
			<#list finderColsList as finderCol>
			 * @param ${finderCol.name} the ${finderCol.humanName}
			</#list>
			 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
			 * @return the first matching ${entity.humanName}, or <code>null</code> if a matching ${entity.humanName} could not be found
			 * @throws SystemException if a system exception occurred
			 */
			public ${entity.name} fetchBy${finder.name}_First(

			<#list finderColsList as finderCol>
				${finderCol.type} ${finderCol.name},
			</#list>

			OrderByComparator orderByComparator) throws SystemException {
				List<${entity.name}> list = findBy${finder.name}(

				<#list finderColsList as finderCol>
					${finderCol.name},
				</#list>

				0, 1, orderByComparator);

				if (!list.isEmpty()) {
					return list.get(0);
				}

				return null;
			}

			/**
			 * Returns the last ${entity.humanName} in the ordered set where ${finder.getHumanConditions(false)}.
			 *
			<#list finderColsList as finderCol>
			 * @param ${finderCol.name} the ${finderCol.humanName}
			</#list>
			 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
			 * @return the last matching ${entity.humanName}
			 * @throws ${packagePath}.${noSuchEntity}Exception if a matching ${entity.humanName} could not be found
			 * @throws SystemException if a system exception occurred
			 */
			public ${entity.name} findBy${finder.name}_Last(

			<#list finderColsList as finderCol>
				${finderCol.type} ${finderCol.name},
			</#list>

			OrderByComparator orderByComparator) throws ${noSuchEntity}Exception, SystemException {
				${entity.name} ${entity.varName} = fetchBy${finder.name}_Last(

				<#list finderColsList as finderCol>
					${finderCol.name},
				</#list>

				orderByComparator);

				if (${entity.varName} != null) {
					return ${entity.varName};
				}

				StringBundler msg = new StringBundler(${(finderColsList?size * 2) + 2});

				msg.append(_NO_SUCH_ENTITY_WITH_KEY);

				<#list finderColsList as finderCol>
					msg.append("<#if finderCol_index != 0>, </#if>${finderCol.name}=");
					msg.append(${finderCol.name});

					<#if !finderCol_has_next>
						msg.append(StringPool.CLOSE_CURLY_BRACE);
					</#if>
				</#list>

				throw new ${noSuchEntity}Exception(msg.toString());
			}

			/**
			 * Returns the last ${entity.humanName} in the ordered set where ${finder.getHumanConditions(false)}.
			 *
			<#list finderColsList as finderCol>
			 * @param ${finderCol.name} the ${finderCol.humanName}
			</#list>
			 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
			 * @return the last matching ${entity.humanName}, or <code>null</code> if a matching ${entity.humanName} could not be found
			 * @throws SystemException if a system exception occurred
			 */
			public ${entity.name} fetchBy${finder.name}_Last(

			<#list finderColsList as finderCol>
				${finderCol.type} ${finderCol.name},
			</#list>

			OrderByComparator orderByComparator) throws SystemException {
				int count = countBy${finder.name}(

				<#list finderColsList as finderCol>
					${finderCol.name}

					<#if finderCol_has_next>
						,
					</#if>
				</#list>

				);

				List<${entity.name}> list = findBy${finder.name}(

				<#list finderColsList as finderCol>
					${finderCol.name},
				</#list>

				count - 1, count, orderByComparator);

				if (!list.isEmpty()) {
					return list.get(0);
				}

				return null;
			}

			/**
			 * Returns the ${entity.humanNames} before and after the current ${entity.humanName} in the ordered set where ${finder.getHumanConditions(false)}.
			 *
			 * @param ${entity.PKVarName} the primary key of the current ${entity.humanName}
			<#list finderColsList as finderCol>
			 * @param ${finderCol.name} the ${finderCol.humanName}
			</#list>
			 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
			 * @return the previous, current, and next ${entity.humanName}
			 * @throws ${packagePath}.${noSuchEntity}Exception if a ${entity.humanName} with the primary key could not be found
			 * @throws SystemException if a system exception occurred
			 */
			public ${entity.name}[] findBy${finder.name}_PrevAndNext(${entity.PKClassName} ${entity.PKVarName},

			<#list finderColsList as finderCol>
				${finderCol.type} ${finderCol.name},
			</#list>

			OrderByComparator orderByComparator) throws ${noSuchEntity}Exception, SystemException {
				${entity.name} ${entity.varName} = findByPrimaryKey(${entity.PKVarName});

				Session session = null;

				try {
					session = openSession();

					${entity.name}[] array = new ${entity.name}Impl[3];

					array[0] =
						getBy${finder.name}_PrevAndNext(
							session, ${entity.varName},

							<#list finderColsList as finderCol>
								${finderCol.name},
							</#list>

							orderByComparator, true);

					array[1] = ${entity.varName};

					array[2] =
						getBy${finder.name}_PrevAndNext(
							session, ${entity.varName},

							<#list finderColsList as finderCol>
								${finderCol.name},
							</#list>

							orderByComparator, false);

					return array;
				}
				catch (Exception e) {
					throw processException(e);
				}
				finally {
					closeSession(session);
				}
			}

			protected ${entity.name} getBy${finder.name}_PrevAndNext(
				Session session, ${entity.name} ${entity.varName},

				<#list finderColsList as finderCol>
					${finderCol.type} ${finderCol.name},
				</#list>

				OrderByComparator orderByComparator, boolean previous) {

				<#include "persistence_impl_get_by_prev_and_next_query.ftl">

				String sql = query.toString();

				Query q = session.createQuery(sql);

				q.setFirstResult(0);
				q.setMaxResults(2);

				QueryPos qPos = QueryPos.getInstance(q);

				<#include "persistence_impl_finder_qpos.ftl">

				if (orderByComparator != null) {
					Object[] values = orderByComparator.getOrderByConditionValues(${entity.varName});

					for (Object value : values) {
						qPos.add(value);
					}
				}

				List<${entity.name}> list = q.list();

				if (list.size() == 2) {
					return list.get(1);
				}
				else {
					return null;
				}
			}

			<#if finder.hasArrayableOperator()>
				/**
				 * Returns all the ${entity.humanNames} where ${finder.getHumanConditions(true)}.
				 *
				 * <p>
				 * <#include "range_comment.ftl">
				 * </p>
				 *
				<#list finderColsList as finderCol>
					<#if finderCol.hasArrayableOperator()>
				 * @param ${finderCol.names} the ${finderCol.humanNames}
					<#else>
				 * @param ${finderCol.name} the ${finderCol.humanName}
					</#if>
				</#list>
				 * @return the matching ${entity.humanNames}
				 * @throws SystemException if a system exception occurred
				 */
				public List<${entity.name}> findBy${finder.name}(

				<#list finderColsList as finderCol>
					<#if finderCol.hasArrayableOperator()>
						${finderCol.type}[] ${finderCol.names}
					<#else>
						${finderCol.type} ${finderCol.name}
					</#if>

					<#if finderCol_has_next>
						,
					</#if>
				</#list>

				) throws SystemException {
					return findBy${finder.name}(

					<#list finderColsList as finderCol>
						<#if finderCol.hasArrayableOperator()>
							${finderCol.names},
						<#else>
							${finderCol.name},
						</#if>
					</#list>

					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
				}

				/**
				 * Returns a range of all the ${entity.humanNames} where ${finder.getHumanConditions(true)}.
				 *
				 * <p>
				 * <#include "range_comment.ftl">
				 * </p>
				 *
				<#list finderColsList as finderCol>
					<#if finderCol.hasArrayableOperator()>
				 * @param ${finderCol.names} the ${finderCol.humanNames}
					<#else>
				 * @param ${finderCol.name} the ${finderCol.humanName}
					</#if>
				</#list>
				 * @param start the lower bound of the range of ${entity.humanNames}
				 * @param end the upper bound of the range of ${entity.humanNames} (not inclusive)
				 * @return the range of matching ${entity.humanNames}
				 * @throws SystemException if a system exception occurred
				 */
				public List<${entity.name}> findBy${finder.name}(

				<#list finderColsList as finderCol>
					<#if finderCol.hasArrayableOperator()>
						${finderCol.type}[] ${finderCol.names},
					<#else>
						${finderCol.type} ${finderCol.name},
					</#if>
				</#list>

				int start, int end) throws SystemException {
					return findBy${finder.name}(

					<#list finderColsList as finderCol>
						<#if finderCol.hasArrayableOperator()>
							${finderCol.names},
						<#else>
							${finderCol.name},
						</#if>
					</#list>

					start, end, null);
				}

				/**
				 * Returns an ordered range of all the ${entity.humanNames} where ${finder.getHumanConditions(true)}.
				 *
				 * <p>
				 * <#include "range_comment.ftl">
				 * </p>
				 *
				<#list finderColsList as finderCol>
					<#if finderCol.hasArrayableOperator()>
				 * @param ${finderCol.names} the ${finderCol.humanNames}
					<#else>
				 * @param ${finderCol.name} the ${finderCol.humanName}
					</#if>
				</#list>
				 * @param start the lower bound of the range of ${entity.humanNames}
				 * @param end the upper bound of the range of ${entity.humanNames} (not inclusive)
				 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
				 * @return the ordered range of matching ${entity.humanNames}
				 * @throws SystemException if a system exception occurred
				 */
				public List<${entity.name}> findBy${finder.name}(

				<#list finderColsList as finderCol>
					<#if finderCol.hasArrayableOperator()>
						${finderCol.type}[] ${finderCol.names},
					<#else>
						${finderCol.type} ${finderCol.name},
					</#if>
				</#list>

				int start, int end, OrderByComparator orderByComparator) throws SystemException {
					FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_${finder.name?upper_case};
					Object[] finderArgs = null;

					if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) && (orderByComparator == null)) {
						finderArgs = new Object[] {
							<#list finderColsList as finderCol>
								<#if finderCol.hasArrayableOperator()>
									StringUtil.merge(${finderCol.names})
								<#else>
									${finderCol.name}
								</#if>

								<#if finderCol_has_next>
									,
								</#if>
							</#list>
						};
					}
					else {
						finderArgs = new Object[] {
							<#list finderColsList as finderCol>
								<#if finderCol.hasArrayableOperator()>
									StringUtil.merge(${finderCol.names}),
								<#else>
									${finderCol.name},
								</#if>
							</#list>

							start, end, orderByComparator
						};
					}

					List<${entity.name}> list = (List<${entity.name}>)FinderCacheUtil.getResult(finderPath, finderArgs, this);

					if ((list != null) && !list.isEmpty()) {
						for (${entity.name} ${entity.varName} : list) {
							if (
								<#list finderColsList as finderCol>
									<#if finderCol.hasArrayableOperator()>
										!ArrayUtil.contains(${finderCol.names}, ${entity.varName}.get${finderCol.methodName}())
									<#else>
										<#if finderCol.isPrimitiveType(false)>
											(${finderCol.name} != ${entity.varName}.get${finderCol.methodName}())
										<#else>
											!Validator.equals(${finderCol.name}, ${entity.varName}.get${finderCol.methodName}())
										</#if>
									</#if>

									<#if finderCol_has_next>
										||
									</#if>
								</#list>
							) {
								list = null;

								break;
							}
						}
					}

					if (list == null) {
						<#include "persistence_impl_find_by_arrayable_query.ftl">

						String sql = query.toString();

						Session session = null;

						try {
							session = openSession();

							Query q = session.createQuery(sql);

							QueryPos qPos = QueryPos.getInstance(q);

							<#include "persistence_impl_finder_arrayable_qpos.ftl">

							list = (List<${entity.name}>)QueryUtil.list(q, getDialect(), start, end);
						}
						catch (Exception e) {
							throw processException(e);
						}
						finally {
							if (list == null) {
								FinderCacheUtil.removeResult(finderPath, finderArgs);
							}
							else {
								cacheResult(list);

								FinderCacheUtil.putResult(finderPath, finderArgs, list);
							}

							closeSession(session);
						}
					}

					return list;
				}
			</#if>

			<#if entity.isPermissionCheckEnabled(finder)>
				/**
				 * Returns all the ${entity.humanNames} that the user has permission to view where ${finder.getHumanConditions(false)}.
				 *
				<#list finderColsList as finderCol>
				 * @param ${finderCol.name} the ${finderCol.humanName}
				</#list>
				 * @return the matching ${entity.humanNames} that the user has permission to view
				 * @throws SystemException if a system exception occurred
				 */
				public List<${entity.name}> filterFindBy${finder.name}(

				<#list finderColsList as finderCol>
					${finderCol.type} ${finderCol.name}

					<#if finderCol_has_next>
						,
					</#if>
				</#list>

				) throws SystemException {
					return filterFindBy${finder.name}(

					<#list finderColsList as finderCol>
						${finderCol.name},
					</#list>

					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
				}

				/**
				 * Returns a range of all the ${entity.humanNames} that the user has permission to view where ${finder.getHumanConditions(false)}.
				 *
				 * <p>
				 * <#include "range_comment.ftl">
				 * </p>
				 *
				<#list finderColsList as finderCol>
				 * @param ${finderCol.name} the ${finderCol.humanName}
				</#list>
				 * @param start the lower bound of the range of ${entity.humanNames}
				 * @param end the upper bound of the range of ${entity.humanNames} (not inclusive)
				 * @return the range of matching ${entity.humanNames} that the user has permission to view
				 * @throws SystemException if a system exception occurred
				 */
				public List<${entity.name}> filterFindBy${finder.name}(

				<#list finderColsList as finderCol>
					${finderCol.type} ${finderCol.name},
				</#list>

				int start, int end) throws SystemException {
					return filterFindBy${finder.name}(

					<#list finderColsList as finderCol>
						${finderCol.name},
					</#list>

					start, end, null);
				}

				/**
				 * Returns an ordered range of all the ${entity.humanNames} that the user has permissions to view where ${finder.getHumanConditions(false)}.
				 *
				 * <p>
				 * <#include "range_comment.ftl">
				 * </p>
				 *
				<#list finderColsList as finderCol>
				 * @param ${finderCol.name} the ${finderCol.humanName}
				</#list>
				 * @param start the lower bound of the range of ${entity.humanNames}
				 * @param end the upper bound of the range of ${entity.humanNames} (not inclusive)
				 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
				 * @return the ordered range of matching ${entity.humanNames} that the user has permission to view
				 * @throws SystemException if a system exception occurred
				 */
				public List<${entity.name}> filterFindBy${finder.name}(

				<#list finderColsList as finderCol>
					${finderCol.type} ${finderCol.name},
				</#list>

				int start, int end, OrderByComparator orderByComparator) throws SystemException {
					if (!InlineSQLHelperUtil.isEnabled(<#if finder.hasColumn("groupId")>groupId</#if>)) {
						return findBy${finder.name}(

						<#list finderColsList as finderCol>
							${finderCol.name},
						</#list>

						start, end, orderByComparator);
					}

					<#if entity.isPermissionedModel()>
						<#include "persistence_impl_find_by_query.ftl">

						String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(), ${entity.name}.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, _FILTER_ENTITY_TABLE_FILTER_USERID_COLUMN<#if finder.hasColumn("groupId")>, groupId</#if>);

						Session session = null;

						try {
							session = openSession();

							Query q = session.createQuery(sql);

							QueryPos qPos = QueryPos.getInstance(q);

							<#include "persistence_impl_finder_qpos.ftl">

							return (List<${entity.name}>)QueryUtil.list(q, getDialect(), start, end);
						}
						catch (Exception e) {
							throw processException(e);
						}
						finally {
							closeSession(session);
						}
					<#else>
						StringBundler query = null;

						if (orderByComparator != null) {
							query = new StringBundler(${finderColsList?size + 2} + (orderByComparator.getOrderByFields().length * 3));
						}
						else {
							query = new StringBundler(<#if entity.getOrder()??>${finderColsList?size + 2}<#else>${finderColsList?size + 1}</#if>);
						}

						if (getDB().isSupportsInlineDistinct()) {
							query.append(_FILTER_SQL_SELECT_${entity.alias?upper_case}_WHERE);
						}
						else {
							query.append(_FILTER_SQL_SELECT_${entity.alias?upper_case}_NO_INLINE_DISTINCT_WHERE_1);
						}

						<#include "persistence_impl_finder_cols.ftl">

						if (!getDB().isSupportsInlineDistinct()) {
							query.append(_FILTER_SQL_SELECT_${entity.alias?upper_case}_NO_INLINE_DISTINCT_WHERE_2);
						}

						if (orderByComparator != null) {
							if (getDB().isSupportsInlineDistinct()) {
								appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
							}
							else {
								appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE, orderByComparator);
							}
						}

						<#if entity.getOrder()??>
							else {
								if (getDB().isSupportsInlineDistinct()) {
									query.append(${entity.name}ModelImpl.ORDER_BY_JPQL);
								}
								else {
									query.append(${entity.name}ModelImpl.ORDER_BY_SQL);
								}
							}
						</#if>

						String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(), ${entity.name}.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN<#if finder.hasColumn("groupId")>, groupId</#if>);

						Session session = null;

						try {
							session = openSession();

							SQLQuery q = session.createSQLQuery(sql);

							if (getDB().isSupportsInlineDistinct()) {
								q.addEntity(_FILTER_ENTITY_ALIAS, ${entity.name}Impl.class);
							}
							else {
								q.addEntity(_FILTER_ENTITY_TABLE, ${entity.name}Impl.class);
							}

							QueryPos qPos = QueryPos.getInstance(q);

							<#include "persistence_impl_finder_qpos.ftl">

							return (List<${entity.name}>)QueryUtil.list(q, getDialect(), start, end);
						}
						catch (Exception e) {
							throw processException(e);
						}
						finally {
							closeSession(session);
						}
					</#if>
				}

				/**
				 * Returns the ${entity.humanNames} before and after the current ${entity.humanName} in the ordered set of ${entity.humanNames} that the user has permission to view where ${finder.getHumanConditions(false)}.
				 *
				 * @param ${entity.PKVarName} the primary key of the current ${entity.humanName}
				<#list finderColsList as finderCol>
				 * @param ${finderCol.name} the ${finderCol.humanName}
				</#list>
				 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
				 * @return the previous, current, and next ${entity.humanName}
				 * @throws ${packagePath}.${noSuchEntity}Exception if a ${entity.humanName} with the primary key could not be found
				 * @throws SystemException if a system exception occurred
				 */
				public ${entity.name}[] filterFindBy${finder.name}_PrevAndNext(${entity.PKClassName} ${entity.PKVarName},

				<#list finderColsList as finderCol>
					${finderCol.type} ${finderCol.name},
				</#list>

				OrderByComparator orderByComparator) throws ${noSuchEntity}Exception, SystemException {
					if (!InlineSQLHelperUtil.isEnabled(<#if finder.hasColumn("groupId")>groupId</#if>)) {
						return findBy${finder.name}_PrevAndNext(${entity.PKVarName},

						<#list finderColsList as finderCol>
							${finderCol.name},
						</#list>

						orderByComparator);
					}

					${entity.name} ${entity.varName} = findByPrimaryKey(${entity.PKVarName});

					Session session = null;

					try {
						session = openSession();

						${entity.name}[] array = new ${entity.name}Impl[3];

						array[0] =
							filterGetBy${finder.name}_PrevAndNext(
								session, ${entity.varName},

								<#list finderColsList as finderCol>
									${finderCol.name},
								</#list>

								orderByComparator, true);

						array[1] = ${entity.varName};

						array[2] =
							filterGetBy${finder.name}_PrevAndNext(
								session, ${entity.varName},

								<#list finderColsList as finderCol>
									${finderCol.name},
								</#list>

								orderByComparator, false);

						return array;
					}
					catch (Exception e) {
						throw processException(e);
					}
					finally {
						closeSession(session);
					}
				}

				protected ${entity.name} filterGetBy${finder.name}_PrevAndNext(
					Session session, ${entity.name} ${entity.varName},

					<#list finderColsList as finderCol>
						${finderCol.type} ${finderCol.name},
					</#list>

					OrderByComparator orderByComparator, boolean previous) {

					<#if entity.isPermissionedModel()>
						<#include "persistence_impl_get_by_prev_and_next_query.ftl">

						String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(), ${entity.name}.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, _FILTER_ENTITY_TABLE_FILTER_USERID_COLUMN<#if finder.hasColumn("groupId")>, groupId</#if>);

						Query q = session.createQuery(sql);

						q.setFirstResult(0);
						q.setMaxResults(2);

						QueryPos qPos = QueryPos.getInstance(q);

						<#include "persistence_impl_finder_qpos.ftl">

						if (orderByComparator != null) {
							Object[] values = orderByComparator.getOrderByConditionValues(${entity.varName});

							for (Object value : values) {
								qPos.add(value);
							}
						}

						List<${entity.name}> list = q.list();

						if (list.size() == 2) {
							return list.get(1);
						}
						else {
							return null;
						}
					<#else>
						StringBundler query = null;

						if (orderByComparator != null) {
							query = new StringBundler(6 + (orderByComparator.getOrderByFields().length * 6));
						}
						else {
							query = new StringBundler(3);
						}

						if (getDB().isSupportsInlineDistinct()) {
							query.append(_FILTER_SQL_SELECT_${entity.alias?upper_case}_WHERE);
						}
						else {
							query.append(_FILTER_SQL_SELECT_${entity.alias?upper_case}_NO_INLINE_DISTINCT_WHERE_1);
						}

						<#include "persistence_impl_finder_cols.ftl">

						if (!getDB().isSupportsInlineDistinct()) {
							query.append(_FILTER_SQL_SELECT_${entity.alias?upper_case}_NO_INLINE_DISTINCT_WHERE_2);
						}

						if (orderByComparator != null) {
							String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

							if (orderByConditionFields.length > 0) {
								query.append(WHERE_AND);
							}

							for (int i = 0; i < orderByConditionFields.length; i++) {
								if (getDB().isSupportsInlineDistinct()) {
									query.append(_ORDER_BY_ENTITY_ALIAS);
								}
								else {
									query.append(_ORDER_BY_ENTITY_TABLE);
								}

								query.append(orderByConditionFields[i]);

								if ((i + 1) < orderByConditionFields.length) {
									if (orderByComparator.isAscending() ^ previous) {
										query.append(WHERE_GREATER_THAN_HAS_NEXT);
									}
									else {
										query.append(WHERE_LESSER_THAN_HAS_NEXT);
									}
								}
								else {
									if (orderByComparator.isAscending() ^ previous) {
										query.append(WHERE_GREATER_THAN);
									}
									else {
										query.append(WHERE_LESSER_THAN);
									}
								}
							}

							query.append(ORDER_BY_CLAUSE);

							String[] orderByFields = orderByComparator.getOrderByFields();

							for (int i = 0; i < orderByFields.length; i++) {
								if (getDB().isSupportsInlineDistinct()) {
									query.append(_ORDER_BY_ENTITY_ALIAS);
								}
								else {
									query.append(_ORDER_BY_ENTITY_TABLE);
								}

								query.append(orderByFields[i]);

								if ((i + 1) < orderByFields.length) {
									if (orderByComparator.isAscending() ^ previous) {
										query.append(ORDER_BY_ASC_HAS_NEXT);
									}
									else {
										query.append(ORDER_BY_DESC_HAS_NEXT);
									}
								}
								else {
									if (orderByComparator.isAscending() ^ previous) {
										query.append(ORDER_BY_ASC);
									}
									else {
										query.append(ORDER_BY_DESC);
									}
								}
							}
						}

						<#if entity.getOrder()??>
							else {
								if (getDB().isSupportsInlineDistinct()) {
									query.append(${entity.name}ModelImpl.ORDER_BY_JPQL);
								}
								else {
									query.append(${entity.name}ModelImpl.ORDER_BY_SQL);
								}
							}
						</#if>

						String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(), ${entity.name}.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN<#if finder.hasColumn("groupId")>, groupId</#if>);

						SQLQuery q = session.createSQLQuery(sql);

						q.setFirstResult(0);
						q.setMaxResults(2);

						if (getDB().isSupportsInlineDistinct()) {
							q.addEntity(_FILTER_ENTITY_ALIAS, ${entity.name}Impl.class);
						}
						else {
							q.addEntity(_FILTER_ENTITY_TABLE, ${entity.name}Impl.class);
						}

						QueryPos qPos = QueryPos.getInstance(q);

						<#include "persistence_impl_finder_qpos.ftl">

						if (orderByComparator != null) {
							Object[] values = orderByComparator.getOrderByConditionValues(${entity.varName});

							for (Object value : values) {
								qPos.add(value);
							}
						}

						List<${entity.name}> list = q.list();

						if (list.size() == 2) {
							return list.get(1);
						}
						else {
							return null;
						}
					</#if>
				}

				<#if finder.hasArrayableOperator()>
					/**
					 * Returns all the ${entity.humanNames} that the user has permission to view where ${finder.getHumanConditions(true)}.
					 *
					<#list finderColsList as finderCol>
						<#if finderCol.hasArrayableOperator()>
					 * @param ${finderCol.names} the ${finderCol.humanNames}
						<#else>
					 * @param ${finderCol.name} the ${finderCol.humanName}
						</#if>
					</#list>
					 * @return the matching ${entity.humanNames} that the user has permission to view
					 * @throws SystemException if a system exception occurred
					 */
					public List<${entity.name}> filterFindBy${finder.name}(

					<#list finderColsList as finderCol>
						<#if finderCol.hasArrayableOperator()>
							${finderCol.type}[] ${finderCol.names}
						<#else>
							${finderCol.type} ${finderCol.name}
						</#if>

						<#if finderCol_has_next>
							,
						</#if>
					</#list>

					) throws SystemException {
						return filterFindBy${finder.name}(

						<#list finderColsList as finderCol>
							<#if finderCol.hasArrayableOperator()>
								${finderCol.names},
							<#else>
								${finderCol.name},
							</#if>
						</#list>

						QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
					}

					/**
					 * Returns a range of all the ${entity.humanNames} that the user has permission to view where ${finder.getHumanConditions(true)}.
					 *
					 * <p>
					 * <#include "range_comment.ftl">
					 * </p>
					 *
					<#list finderColsList as finderCol>
						<#if finderCol.hasArrayableOperator()>
					 * @param ${finderCol.names} the ${finderCol.humanNames}
						<#else>
					 * @param ${finderCol.name} the ${finderCol.humanName}
						</#if>
					</#list>
					 * @param start the lower bound of the range of ${entity.humanNames}
					 * @param end the upper bound of the range of ${entity.humanNames} (not inclusive)
					 * @return the range of matching ${entity.humanNames} that the user has permission to view
					 * @throws SystemException if a system exception occurred
					 */
					public List<${entity.name}> filterFindBy${finder.name}(

					<#list finderColsList as finderCol>
						<#if finderCol.hasArrayableOperator()>
							${finderCol.type}[] ${finderCol.names},
						<#else>
							${finderCol.type} ${finderCol.name},
						</#if>
					</#list>

					int start, int end) throws SystemException {
						return filterFindBy${finder.name}(

						<#list finderColsList as finderCol>
							<#if finderCol.hasArrayableOperator()>
								${finderCol.names},
							<#else>
								${finderCol.name},
							</#if>
						</#list>

						start, end, null);
					}

					/**
					 * Returns an ordered range of all the ${entity.humanNames} that the user has permission to view where ${finder.getHumanConditions(true)}.
					 *
					 * <p>
					 * <#include "range_comment.ftl">
					 * </p>
					 *
					<#list finderColsList as finderCol>
						<#if finderCol.hasArrayableOperator()>
					 * @param ${finderCol.names} the ${finderCol.humanNames}
						<#else>
					 * @param ${finderCol.name} the ${finderCol.humanName}
						</#if>
					</#list>
					 * @param start the lower bound of the range of ${entity.humanNames}
					 * @param end the upper bound of the range of ${entity.humanNames} (not inclusive)
					 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
					 * @return the ordered range of matching ${entity.humanNames} that the user has permission to view
					 * @throws SystemException if a system exception occurred
					 */
					public List<${entity.name}> filterFindBy${finder.name}(

					<#list finderColsList as finderCol>
						<#if finderCol.hasArrayableOperator()>
							${finderCol.type}[] ${finderCol.names},
						<#else>
							${finderCol.type} ${finderCol.name},
						</#if>
					</#list>

					int start, int end, OrderByComparator orderByComparator) throws SystemException {
						if (!InlineSQLHelperUtil.isEnabled(
							<#if finder.hasColumn("groupId")>
								<#if finder.getColumn("groupId").hasArrayableOperator()>
									groupIds
								<#else>
									groupId
								</#if>
							</#if>)) {

							return findBy${finder.name}(

							<#list finderColsList as finderCol>
								<#if finderCol.hasArrayableOperator()>
									${finderCol.names},
								<#else>
									${finderCol.name},
								</#if>
							</#list>

							start, end, orderByComparator);
						}

						<#if entity.isPermissionedModel()>
							<#include "persistence_impl_find_by_arrayable_query.ftl">

							String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(), ${entity.name}.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, _FILTER_ENTITY_TABLE_FILTER_USERID_COLUMN

							<#if finder.hasColumn("groupId")>,
								<#if finder.getColumn("groupId").hasArrayableOperator()>
									groupIds
								<#else>
									groupId
								</#if>
							</#if>);

							Session session = null;

							try {
								session = openSession();

								Query q = session.createQuery(sql);

								QueryPos qPos = QueryPos.getInstance(q);

								<#include "persistence_impl_finder_arrayable_qpos.ftl">

								return (List<${entity.name}>)QueryUtil.list(q, getDialect(), start, end);
							}
							catch (Exception e) {
								throw processException(e);
							}
							finally {
								closeSession(session);
							}
						<#else>
							StringBundler query = new StringBundler();

							if (getDB().isSupportsInlineDistinct()) {
								query.append(_FILTER_SQL_SELECT_${entity.alias?upper_case}_WHERE);
							}
							else {
								query.append(_FILTER_SQL_SELECT_${entity.alias?upper_case}_NO_INLINE_DISTINCT_WHERE_1);
							}

							<#include "persistence_impl_finder_arrayable_cols.ftl">

							if (!getDB().isSupportsInlineDistinct()) {
								query.append(_FILTER_SQL_SELECT_${entity.alias?upper_case}_NO_INLINE_DISTINCT_WHERE_2);
							}

							if (orderByComparator != null) {
								if (getDB().isSupportsInlineDistinct()) {
									appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
								}
								else {
									appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE, orderByComparator);
								}
							}

							<#if entity.getOrder()??>
								else {
									if (getDB().isSupportsInlineDistinct()) {
										query.append(${entity.name}ModelImpl.ORDER_BY_JPQL);
									}
									else {
										query.append(${entity.name}ModelImpl.ORDER_BY_SQL);
									}
								}
							</#if>

							String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(), ${entity.name}.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN

							<#if finder.hasColumn("groupId")>,
								<#if finder.getColumn("groupId").hasArrayableOperator()>
									groupIds
								<#else>
									groupId
								</#if>
							</#if>);

							Session session = null;

							try {
								session = openSession();

								SQLQuery q = session.createSQLQuery(sql);

								if (getDB().isSupportsInlineDistinct()) {
									q.addEntity(_FILTER_ENTITY_ALIAS, ${entity.name}Impl.class);
								}
								else {
									q.addEntity(_FILTER_ENTITY_TABLE, ${entity.name}Impl.class);
								}

								QueryPos qPos = QueryPos.getInstance(q);

								<#include "persistence_impl_finder_arrayable_qpos.ftl">

								return (List<${entity.name}>)QueryUtil.list(q, getDialect(), start, end);
							}
							catch (Exception e) {
								throw processException(e);
							}
							finally {
								closeSession(session);
							}
						</#if>
					}
				</#if>
			</#if>
		<#else>
			/**
			 * Returns the ${entity.humanName} where ${finder.getHumanConditions(false)} or throws a {@link ${packagePath}.${noSuchEntity}Exception} if it could not be found.
			 *
			<#list finderColsList as finderCol>
			 * @param ${finderCol.name} the ${finderCol.humanName}
			</#list>
			 * @return the matching ${entity.humanName}
			 * @throws ${packagePath}.${noSuchEntity}Exception if a matching ${entity.humanName} could not be found
			 * @throws SystemException if a system exception occurred
			 */
			public ${entity.name} findBy${finder.name}(

			<#list finderColsList as finderCol>
				${finderCol.type} ${finderCol.name}

				<#if finderCol_has_next>
					,
				</#if>
			</#list>

			) throws ${noSuchEntity}Exception, SystemException {
				${entity.name} ${entity.varName} = fetchBy${finder.name}(

				<#list finderColsList as finderCol>
					${finderCol.name}

					<#if finderCol_has_next>
						,
					</#if>
				</#list>

				);

				if ( ${entity.varName} == null) {
					StringBundler msg = new StringBundler(${(finderColsList?size * 2) + 2});

					msg.append(_NO_SUCH_ENTITY_WITH_KEY);

					<#list finderColsList as finderCol>
						msg.append("<#if finderCol_index != 0>, </#if>${finderCol.name}=");
						msg.append(${finderCol.name});

						<#if !finderCol_has_next>
							msg.append(StringPool.CLOSE_CURLY_BRACE);
						</#if>
					</#list>

					if (_log.isWarnEnabled()) {
						_log.warn(msg.toString());
					}

					throw new ${noSuchEntity}Exception(msg.toString());
				}

				return ${entity.varName};
			}

			/**
			 * Returns the ${entity.humanName} where ${finder.getHumanConditions(false)} or returns <code>null</code> if it could not be found. Uses the finder cache.
			 *
			<#list finderColsList as finderCol>
			 * @param ${finderCol.name} the ${finderCol.humanName}
			</#list>
			 * @return the matching ${entity.humanName}, or <code>null</code> if a matching ${entity.humanName} could not be found
			 * @throws SystemException if a system exception occurred
			 */
			public ${entity.name} fetchBy${finder.name}(

			<#list finderColsList as finderCol>
				${finderCol.type} ${finderCol.name}

				<#if finderCol_has_next>
					,
				</#if>
			</#list>

			) throws SystemException {
				return fetchBy${finder.name}(

				<#list finderColsList as finderCol>
					${finderCol.name},
				</#list>

				true);
			}

			/**
			 * Returns the ${entity.humanName} where ${finder.getHumanConditions(false)} or returns <code>null</code> if it could not be found, optionally using the finder cache.
			 *
			<#list finderColsList as finderCol>
			 * @param ${finderCol.name} the ${finderCol.humanName}
			</#list>
			 * @param retrieveFromCache whether to use the finder cache
			 * @return the matching ${entity.humanName}, or <code>null</code> if a matching ${entity.humanName} could not be found
			 * @throws SystemException if a system exception occurred
			 */
			public ${entity.name} fetchBy${finder.name}(

			<#list finderColsList as finderCol>
				${finderCol.type} ${finderCol.name}

				,
			</#list>

			boolean retrieveFromCache) throws SystemException {
				Object[] finderArgs = new Object[] {
					<#list finderColsList as finderCol>
						${finderCol.name}

						<#if finderCol_has_next>
							,
						</#if>
					</#list>
				};

				Object result = null;

				if (retrieveFromCache) {
					result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_${finder.name?upper_case}, finderArgs, this);
				}

				if (result instanceof ${entity.name}) {
					${entity.name} ${entity.varName} = (${entity.name})result;

					if (
						<#list finderColsList as finderCol>
							<#if finderCol.isPrimitiveType(false)>
								(${finderCol.name} != ${entity.varName}.get${finderCol.methodName}())
							<#else>
								!Validator.equals(${finderCol.name}, ${entity.varName}.get${finderCol.methodName}())
							</#if>

							<#if finderCol_has_next>
								||
							</#if>
						</#list>
					) {
						result = null;
					}
				}

				if (result == null) {
					StringBundler query = new StringBundler(<#if entity.getOrder()??>${finderColsList?size + 2}<#else>${finderColsList?size + 1}</#if>);

					query.append(_SQL_SELECT_${entity.alias?upper_case}_WHERE);

					<#include "persistence_impl_finder_cols.ftl">

					<#if entity.getOrder()??>
						query.append(${entity.name}ModelImpl.ORDER_BY_JPQL);
					</#if>

					String sql = query.toString();

					Session session = null;

					try {
						session = openSession();

						Query q = session.createQuery(sql);

						QueryPos qPos = QueryPos.getInstance(q);

						<#include "persistence_impl_finder_qpos.ftl">

						List<${entity.name}> list = q.list();

						result = list;

						${entity.name} ${entity.varName} = null;

						if (list.isEmpty()) {
							FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_${finder.name?upper_case}, finderArgs, list);
						}
						else {
							${entity.varName} = list.get(0);

							cacheResult(${entity.varName});

							if (
								<#list finderColsList as finderCol>
									<#if finderCol.isPrimitiveType()>
										(${entity.varName}.get${finderCol.methodName}() != ${finderCol.name})
									<#else>
										(${entity.varName}.get${finderCol.methodName}() == null) || !${entity.varName}.get${finderCol.methodName}().equals(${finderCol.name})
									</#if>

									<#if finderCol_has_next>
										||
									</#if>
								</#list>
							) {
								FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_${finder.name?upper_case}, finderArgs, ${entity.varName});
							}
						}

						return ${entity.varName};
					}
					catch (Exception e) {
						throw processException(e);
					}
					finally {
						if (result == null) {
							FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_${finder.name?upper_case}, finderArgs);
						}

						closeSession(session);
					}
				}
				else {
					if (result instanceof List<?>) {
						return null;
					}
					else {
						return (${entity.name})result;
					}
				}
			}
		</#if>
	</#list>

	/**
	 * Returns all the ${entity.humanNames}.
	 *
	 * @return the ${entity.humanNames}
	 * @throws SystemException if a system exception occurred
	 */
	public List<${entity.name}> findAll() throws SystemException {
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
	 * @throws SystemException if a system exception occurred
	 */
	public List<${entity.name}> findAll(int start, int end) throws SystemException {
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
	 * @throws SystemException if a system exception occurred
	 */
	public List<${entity.name}> findAll(int start, int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = new Object[] {start, end, orderByComparator};

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) && (orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<${entity.name}> list = (List<${entity.name}>)FinderCacheUtil.getResult(finderPath, finderArgs, this);

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
				<#if entity.getOrder()??>
					sql = _SQL_SELECT_${entity.alias?upper_case}.concat(${entity.name}ModelImpl.ORDER_BY_JPQL);
				<#else>
					sql = _SQL_SELECT_${entity.alias?upper_case};
				</#if>
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<${entity.name}>)QueryUtil.list(q, getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<${entity.name}>)QueryUtil.list(q, getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	<#list entity.getFinderList() as finder>
		<#assign finderColsList = finder.getColumns()>

		<#if finder.isCollection()>
			/**
			 * Removes all the ${entity.humanNames} where ${finder.getHumanConditions(false)} from the database.
			 *
			<#list finderColsList as finderCol>
			 * @param ${finderCol.name} the ${finderCol.humanName}
			</#list>
			 * @throws SystemException if a system exception occurred
			 */
			public void removeBy${finder.name}(

			<#list finderColsList as finderCol>
				${finderCol.type} ${finderCol.name}<#if finderCol_has_next>,</#if>
			</#list>

			) throws SystemException {
				for (${entity.name} ${entity.varName} : findBy${finder.name}(

				<#list finderColsList as finderCol>
					${finderCol.name}

					<#if finderCol_has_next>
						,
					</#if>
				</#list>

				)) {
					remove(${entity.varName});
				}
			}
		<#else>
			/**
			 * Removes the ${entity.humanName} where ${finder.getHumanConditions(false)} from the database.
			 *
			<#list finderColsList as finderCol>
			 * @param ${finderCol.name} the ${finderCol.humanName}
			</#list>
			 * @return the ${entity.humanName} that was removed
			 * @throws SystemException if a system exception occurred
			 */
			public ${entity.name} removeBy${finder.name}(

			<#list finderColsList as finderCol>
				${finderCol.type} ${finderCol.name}

				<#if finderCol_has_next>
					,
				</#if>
			</#list>

			) throws ${noSuchEntity}Exception, SystemException {
				${entity.name} ${entity.varName} = findBy${finder.name}(

				<#list finderColsList as finderCol>
					${finderCol.name}

					<#if finderCol_has_next>
						,
					</#if>
				</#list>

				);

				return remove(${entity.varName});
			}
		</#if>
	</#list>

	/**
	 * Removes all the ${entity.humanNames} from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (${entity.name} ${entity.varName} : findAll()) {
			remove(${entity.varName});
		}
	}

	<#list entity.getFinderList() as finder>
		<#assign finderColsList = finder.getColumns()>

		/**
		 * Returns the number of ${entity.humanNames} where ${finder.getHumanConditions(false)}.
		 *
		<#list finderColsList as finderCol>
		 * @param ${finderCol.name} the ${finderCol.humanName}
		</#list>
		 * @return the number of matching ${entity.humanNames}
		 * @throws SystemException if a system exception occurred
		 */
		public int countBy${finder.name}(

		<#list finderColsList as finderCol>
			${finderCol.type} ${finderCol.name}

			<#if finderCol_has_next>
				,
			</#if>
		</#list>

		) throws SystemException {
			Object[] finderArgs = new Object[] {
				<#list finderColsList as finderCol>
					${finderCol.name}

					<#if finderCol_has_next>
						,
					</#if>
				</#list>
			};

			Long count = (Long)FinderCacheUtil.getResult(
				<#if !finder.hasCustomComparator()>
					FINDER_PATH_COUNT_BY_${finder.name?upper_case},
				<#else>
					FINDER_PATH_WITH_PAGINATION_COUNT_BY_${finder.name?upper_case},
				</#if>
				finderArgs, this);

			if (count == null) {
				<#include "persistence_impl_count_by_query.ftl">

				String sql = query.toString();

				Session session = null;

				try {
					session = openSession();

					Query q = session.createQuery(sql);

					QueryPos qPos = QueryPos.getInstance(q);

					<#include "persistence_impl_finder_qpos.ftl">

					count = (Long)q.uniqueResult();
				}
				catch (Exception e) {
					throw processException(e);
				}
				finally {
					if (count == null) {
						count = Long.valueOf(0);
					}

					FinderCacheUtil.putResult(
						<#if !finder.hasCustomComparator()>
							FINDER_PATH_COUNT_BY_${finder.name?upper_case},
						<#else>
							FINDER_PATH_WITH_PAGINATION_COUNT_BY_${finder.name?upper_case},
						</#if>
						finderArgs, count);

					closeSession(session);
				}
			}

			return count.intValue();
		}

		<#if finder.hasArrayableOperator()>
			/**
			 * Returns the number of ${entity.humanNames} where ${finder.getHumanConditions(true)}.
			 *
			<#list finderColsList as finderCol>
				<#if finderCol.hasArrayableOperator()>
			 * @param ${finderCol.names} the ${finderCol.humanNames}
				<#else>
			 * @param ${finderCol.name} the ${finderCol.humanName}
				</#if>
			</#list>
			 * @return the number of matching ${entity.humanNames}
			 * @throws SystemException if a system exception occurred
			 */
			public int countBy${finder.name}(

			<#list finderColsList as finderCol>
				<#if finderCol.hasArrayableOperator()>
					${finderCol.type}[] ${finderCol.names}
				<#else>
					${finderCol.type} ${finderCol.name}
				</#if>

				<#if finderCol_has_next>
					,
				</#if>
			</#list>

			) throws SystemException {
				Object[] finderArgs = new Object[] {
					<#list finderColsList as finderCol>
						<#if finderCol.hasArrayableOperator()>
							StringUtil.merge(${finderCol.names})
						<#else>
							${finderCol.name}
						</#if>

						<#if finderCol_has_next>
							,
						</#if>
					</#list>
				};

				Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_${finder.name?upper_case}, finderArgs, this);

				if (count == null) {
					<#include "persistence_impl_count_by_arrayable_query.ftl">

					String sql = query.toString();

					Session session = null;

					try {
						session = openSession();

						Query q = session.createQuery(sql);

						QueryPos qPos = QueryPos.getInstance(q);

						<#include "persistence_impl_finder_arrayable_qpos.ftl">

						count = (Long)q.uniqueResult();
					}
					catch (Exception e) {
						throw processException(e);
					}
					finally {
						if (count == null) {
							count = Long.valueOf(0);
						}

						FinderCacheUtil.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_${finder.name?upper_case}, finderArgs, count);

						closeSession(session);
					}
				}

				return count.intValue();
			}
		</#if>

		<#if entity.isPermissionCheckEnabled(finder)>
			/**
			 * Returns the number of ${entity.humanNames} that the user has permission to view where ${finder.getHumanConditions(false)}.
			 *
			<#list finderColsList as finderCol>
			 * @param ${finderCol.name} the ${finderCol.humanName}
			</#list>
			 * @return the number of matching ${entity.humanNames} that the user has permission to view
			 * @throws SystemException if a system exception occurred
			 */
			public int filterCountBy${finder.name}(

			<#list finderColsList as finderCol>
				${finderCol.type} ${finderCol.name}

				<#if finderCol_has_next>
					,
				</#if>
			</#list>

			) throws SystemException {
				if (!InlineSQLHelperUtil.isEnabled(<#if finder.hasColumn("groupId")>groupId</#if>)) {
					return countBy${finder.name}(

					<#list finderColsList as finderCol>
						${finderCol.name}

						<#if finderCol_has_next>
							,
						</#if>
					</#list>

					);
				}

				<#if entity.isPermissionedModel()>
					<#include "persistence_impl_count_by_query.ftl">

					String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(), ${entity.name}.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, _FILTER_ENTITY_TABLE_FILTER_USERID_COLUMN<#if finder.hasColumn("groupId")>, groupId</#if>);

					Session session = null;

					try {
						session = openSession();

						Query q = session.createQuery(sql);

						QueryPos qPos = QueryPos.getInstance(q);

						<#include "persistence_impl_finder_qpos.ftl">

						Long count = (Long)q.uniqueResult();

						return count.intValue();
					}
					catch (Exception e) {
						throw processException(e);
					}
					finally {
						closeSession(session);
					}
				<#else>
					StringBundler query = new StringBundler(${finderColsList?size + 1});

					query.append(_FILTER_SQL_COUNT_${entity.alias?upper_case}_WHERE);

					<#include "persistence_impl_finder_cols.ftl">

					String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(), ${entity.name}.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN<#if finder.hasColumn("groupId")>, groupId</#if>);

					Session session = null;

					try {
						session = openSession();

						SQLQuery q = session.createSQLQuery(sql);

						q.addScalar(COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

						QueryPos qPos = QueryPos.getInstance(q);

						<#include "persistence_impl_finder_qpos.ftl">

						Long count = (Long)q.uniqueResult();

						return count.intValue();
					}
					catch (Exception e) {
						throw processException(e);
					}
					finally {
						closeSession(session);
					}
				</#if>
			}

			<#if finder.hasArrayableOperator()>
				/**
				 * Returns the number of ${entity.humanNames} that the user has permission to view where ${finder.getHumanConditions(true)}.
				 *
				<#list finderColsList as finderCol>
					<#if finderCol.hasArrayableOperator()>
				 * @param ${finderCol.names} the ${finderCol.humanNames}
					<#else>
				 * @param ${finderCol.name} the ${finderCol.humanName}
					</#if>
				</#list>
				 * @return the number of matching ${entity.humanNames} that the user has permission to view
				 * @throws SystemException if a system exception occurred
				 */
				public int filterCountBy${finder.name}(

				<#list finderColsList as finderCol>
					<#if finderCol.hasArrayableOperator()>
						${finderCol.type}[] ${finderCol.names}
					<#else>
						${finderCol.type} ${finderCol.name}
					</#if>

					<#if finderCol_has_next>
						,
					</#if>
				</#list>

				) throws SystemException {
					if (!InlineSQLHelperUtil.isEnabled(
						<#if finder.hasColumn("groupId")>
							<#if finder.getColumn("groupId").hasArrayableOperator()>
								groupIds
							<#else>
								groupId
							</#if>
						</#if>)) {

						return countBy${finder.name}(

						<#list finderColsList as finderCol>
							<#if finderCol.hasArrayableOperator()>
								${finderCol.names}
							<#else>
								${finderCol.name}
							</#if>

							<#if finderCol_has_next>
								,
							</#if>
						</#list>

						);
					}

					<#if entity.isPermissionedModel()>
						<#include "persistence_impl_count_by_arrayable_query.ftl">

						String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(), ${entity.name}.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, _FILTER_ENTITY_TABLE_FILTER_USERID_COLUMN

						<#if finder.hasColumn("groupId")>,
							<#if finder.getColumn("groupId").hasArrayableOperator()>
								groupIds
							<#else>
								groupId
							</#if>
						</#if>);

						Session session = null;

						try {
							session = openSession();

							Query q = session.createQuery(sql);

							QueryPos qPos = QueryPos.getInstance(q);

							<#include "persistence_impl_finder_arrayable_qpos.ftl">

							Long count = (Long)q.uniqueResult();

							return count.intValue();
						}
						catch (Exception e) {
							throw processException(e);
						}
						finally {
							closeSession(session);
						}
					<#else>
						StringBundler query = new StringBundler();

						query.append(_FILTER_SQL_COUNT_${entity.alias?upper_case}_WHERE);

						<#include "persistence_impl_finder_arrayable_cols.ftl">

						String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(), ${entity.name}.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN

						<#if finder.hasColumn("groupId")>,
							<#if finder.getColumn("groupId").hasArrayableOperator()>
								groupIds
							<#else>
								groupId
							</#if>
						</#if>);

						Session session = null;

						try {
							session = openSession();

							SQLQuery q = session.createSQLQuery(sql);

							q.addScalar(COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

							QueryPos qPos = QueryPos.getInstance(q);

							<#include "persistence_impl_finder_arrayable_qpos.ftl">

							Long count = (Long)q.uniqueResult();

							return count.intValue();
						}
						catch (Exception e) {
							throw processException(e);
						}
						finally {
							closeSession(session);
						}
					</#if>
				}
			</#if>
		</#if>
	</#list>

	/**
	 * Returns the number of ${entity.humanNames}.
	 *
	 * @return the number of ${entity.humanNames}
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_${entity.alias?upper_case});

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	<#list entity.columnList as column>
		<#if column.isCollection() && (column.isMappingManyToMany() || column.isMappingOneToMany())>
			<#assign tempEntity = serviceBuilder.getEntity(column.getEJBName())>

			/**
			 * Returns all the ${tempEntity.humanNames} associated with the ${entity.humanName}.
			 *
			 * @param pk the primary key of the ${entity.humanName}
			 * @return the ${tempEntity.humanNames} associated with the ${entity.humanName}
			 * @throws SystemException if a system exception occurred
			 */
			public List<${tempEntity.packagePath}.model.${tempEntity.name}> get${tempEntity.names}(${entity.PKClassName} pk) throws SystemException {
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
			 * @throws SystemException if a system exception occurred
			 */
			public List<${tempEntity.packagePath}.model.${tempEntity.name}> get${tempEntity.names}(${entity.PKClassName} pk, int start, int end) throws SystemException {
				return get${tempEntity.names}(pk, start, end, null);
			}

			public static final FinderPath FINDER_PATH_GET_${tempEntity.names?upper_case} = new FinderPath(
				${tempEntity.packagePath}.model.impl.${tempEntity.name}ModelImpl.ENTITY_CACHE_ENABLED,

				<#if column.mappingTable??>
					${entity.name}ModelImpl.FINDER_CACHE_ENABLED_${stringUtil.upperCase(column.mappingTable)},
					${tempEntity.packagePath}.model.impl.${tempEntity.name}Impl.class,
					${entity.name}ModelImpl.MAPPING_TABLE_${stringUtil.upperCase(column.mappingTable)}_NAME,
				<#else>
					${tempEntity.packagePath}.model.impl.${tempEntity.name}ModelImpl.FINDER_CACHE_ENABLED,
					${tempEntity.packagePath}.model.impl.${tempEntity.name}Impl.class,
					${tempEntity.packagePath}.service.persistence.${tempEntity.name}PersistenceImpl.FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				</#if>

				"get${tempEntity.names}",
				new String[] {
					<#if entity.hasPrimitivePK()>
						${serviceBuilder.getPrimitiveObj(entity.getPKClassName())}
					<#else>
						${entity.PKClassName}
					</#if>

					.class.getName(), "java.lang.Integer", "java.lang.Integer", "com.liferay.portal.kernel.util.OrderByComparator"
				});

			static {
				FINDER_PATH_GET_${tempEntity.names?upper_case}.setCacheKeyGeneratorCacheName(null);
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
			 * @throws SystemException if a system exception occurred
			 */
			public List<${tempEntity.packagePath}.model.${tempEntity.name}> get${tempEntity.names}(${entity.PKClassName} pk, int start, int end, OrderByComparator orderByComparator) throws SystemException {
				Object[] finderArgs = new Object[] {
					pk, start, end, orderByComparator
				};

				List<${tempEntity.packagePath}.model.${tempEntity.name}> list = (List<${tempEntity.packagePath}.model.${tempEntity.name}>)FinderCacheUtil.getResult(FINDER_PATH_GET_${tempEntity.names?upper_case}, finderArgs, this);

				if (list == null) {
					Session session = null;

					try {
						session = openSession();

						String sql = null;

						if (orderByComparator != null) {
							sql = _SQL_GET${tempEntity.names?upper_case}.concat(ORDER_BY_CLAUSE).concat(orderByComparator.getOrderBy());
						}
						else {
							<#if tempEntity.getOrder()??>
								sql = _SQL_GET${tempEntity.names?upper_case}.concat(${tempEntity.packagePath}.model.impl.${tempEntity.name}ModelImpl.ORDER_BY_SQL);
							<#else>
								sql = _SQL_GET${tempEntity.names?upper_case};
							</#if>
						}

						SQLQuery q = session.createSQLQuery(sql);

						q.addEntity("${tempEntity.table}", ${tempEntity.packagePath}.model.impl.${tempEntity.name}Impl.class);

						QueryPos qPos = QueryPos.getInstance(q);

						qPos.add(pk);

						list = (List<${tempEntity.packagePath}.model.${tempEntity.name}>)QueryUtil.list(q, getDialect(), start, end);
					}
					catch (Exception e) {
						throw processException(e);
					}
					finally {
						if (list == null) {
							FinderCacheUtil.removeResult(FINDER_PATH_GET_${tempEntity.names?upper_case}, finderArgs);
						}
						else {
							${tempEntity.varName}Persistence.cacheResult(list);

							FinderCacheUtil.putResult(FINDER_PATH_GET_${tempEntity.names?upper_case}, finderArgs, list);
						}

						closeSession(session);
					}
				}

				return list;
			}

			public static final FinderPath FINDER_PATH_GET_${tempEntity.names?upper_case}_SIZE = new FinderPath(
				${tempEntity.packagePath}.model.impl.${tempEntity.name}ModelImpl.ENTITY_CACHE_ENABLED,

				<#if column.mappingTable??>
					${entity.name}ModelImpl.FINDER_CACHE_ENABLED_${stringUtil.upperCase(column.mappingTable)},
					Long.class,
					${entity.name}ModelImpl.MAPPING_TABLE_${stringUtil.upperCase(column.mappingTable)}_NAME,
				<#else>
					${tempEntity.packagePath}.model.impl.${tempEntity.name}ModelImpl.FINDER_CACHE_ENABLED,
					${tempEntity.packagePath}.model.impl.${tempEntity.name}Impl.class,
					${tempEntity.packagePath}.service.persistence.${tempEntity.name}PersistenceImpl.FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				</#if>

				"get${tempEntity.names}Size",
				new String[] {
					<#if entity.hasPrimitivePK()>
						${serviceBuilder.getPrimitiveObj(entity.getPKClassName())}
					<#else>
						${entity.PKClassName}
					</#if>

					.class.getName()
				});

			static {
				FINDER_PATH_GET_${tempEntity.names?upper_case}_SIZE.setCacheKeyGeneratorCacheName(null);
			}

			/**
			 * Returns the number of ${tempEntity.humanNames} associated with the ${entity.humanName}.
			 *
			 * @param pk the primary key of the ${entity.humanName}
			 * @return the number of ${tempEntity.humanNames} associated with the ${entity.humanName}
			 * @throws SystemException if a system exception occurred
			 */
			public int get${tempEntity.names}Size(${entity.PKClassName} pk) throws SystemException {
				Object[] finderArgs = new Object[] {pk};

				Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_${tempEntity.names?upper_case}_SIZE, finderArgs, this);

				if (count == null) {
					Session session = null;

					try {
						session = openSession();

						SQLQuery q = session.createSQLQuery(_SQL_GET${tempEntity.names?upper_case}SIZE);

						q.addScalar(COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

						QueryPos qPos = QueryPos.getInstance(q);

						qPos.add(pk);

						count = (Long)q.uniqueResult();
					}
					catch (Exception e) {
						throw processException(e);
					}
					finally {
						if (count == null) {
							count = Long.valueOf(0);
						}

						FinderCacheUtil.putResult(FINDER_PATH_GET_${tempEntity.names?upper_case}_SIZE, finderArgs, count);

						closeSession(session);
					}
				}

				return count.intValue();
			}

			public static final FinderPath FINDER_PATH_CONTAINS_${tempEntity.name?upper_case} = new FinderPath(
				${tempEntity.packagePath}.model.impl.${tempEntity.name}ModelImpl.ENTITY_CACHE_ENABLED,

				<#if column.mappingTable??>
					${entity.name}ModelImpl.FINDER_CACHE_ENABLED_${stringUtil.upperCase(column.mappingTable)},
					Boolean.class,
					${entity.name}ModelImpl.MAPPING_TABLE_${stringUtil.upperCase(column.mappingTable)}_NAME,
				<#else>
					${tempEntity.packagePath}.model.impl.${tempEntity.name}ModelImpl.FINDER_CACHE_ENABLED,
					${tempEntity.packagePath}.model.impl.${tempEntity.name}Impl.class,
					${tempEntity.packagePath}.service.persistence.${tempEntity.name}PersistenceImpl.FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				</#if>

				"contains${tempEntity.name}",
				new String[] {
					<#if entity.hasPrimitivePK()>
						${serviceBuilder.getPrimitiveObj(entity.getPKClassName())}
					<#else>
						${entity.PKClassName}
					</#if>

					.class.getName(),

					<#if tempEntity.hasPrimitivePK()>
						${serviceBuilder.getPrimitiveObj(tempEntity.getPKClassName())}
					<#else>
						${tempEntity.PKClassName}
					</#if>

					.class.getName()
				});

			/**
			 * Returns <code>true</code> if the ${tempEntity.humanName} is associated with the ${entity.humanName}.
			 *
			 * @param pk the primary key of the ${entity.humanName}
			 * @param ${tempEntity.varName}PK the primary key of the ${tempEntity.humanName}
			 * @return <code>true</code> if the ${tempEntity.humanName} is associated with the ${entity.humanName}; <code>false</code> otherwise
			 * @throws SystemException if a system exception occurred
			 */
			public boolean contains${tempEntity.name}(${entity.PKClassName} pk, ${tempEntity.PKClassName} ${tempEntity.varName}PK) throws SystemException {
				Object[] finderArgs = new Object[] {pk, ${tempEntity.varName}PK};

				Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_${tempEntity.name?upper_case}, finderArgs, this);

				if (value == null) {
					try {
						value = Boolean.valueOf(contains${tempEntity.name}.contains(pk, ${tempEntity.varName}PK));
					}
					catch (Exception e) {
						throw processException(e);
					}
					finally {
						if (value == null) {
							value = Boolean.FALSE;
						}

						FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_${tempEntity.name?upper_case}, finderArgs, value);
					}
				}

				return value.booleanValue();
			}

			/**
			 * Returns <code>true</code> if the ${entity.humanName} has any ${tempEntity.humanNames} associated with it.
			 *
			 * @param pk the primary key of the ${entity.humanName} to check for associations with ${tempEntity.humanNames}
			 * @return <code>true</code> if the ${entity.humanName} has any ${tempEntity.humanNames} associated with it; <code>false</code> otherwise
			 * @throws SystemException if a system exception occurred
			 */
			public boolean contains${tempEntity.names}(${entity.PKClassName} pk) throws SystemException {
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
				 * @throws SystemException if a system exception occurred
				 */
				public void add${tempEntity.name}(${entity.PKClassName} pk, ${tempEntity.PKClassName} ${tempEntity.varName}PK) throws SystemException {
					try {
						add${tempEntity.name}.add(pk, ${tempEntity.varName}PK);
					}
					catch (Exception e) {
						throw processException(e);
					}
					finally {
						FinderCacheUtil.clearCache(${entity.name}ModelImpl.MAPPING_TABLE_${stringUtil.upperCase(column.mappingTable)}_NAME);
					}
				}

				/**
				 * Adds an association between the ${entity.humanName} and the ${tempEntity.humanName}. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param ${tempEntity.varName} the ${tempEntity.humanName}
				 * @throws SystemException if a system exception occurred
				 */
				public void add${tempEntity.name}(${entity.PKClassName} pk, ${tempEntity.packagePath}.model.${tempEntity.name} ${tempEntity.varName}) throws SystemException {
					try {
						add${tempEntity.name}.add(pk, ${tempEntity.varName}.getPrimaryKey());
					}
					catch (Exception e) {
						throw processException(e);
					}
					finally {
						FinderCacheUtil.clearCache(${entity.name}ModelImpl.MAPPING_TABLE_${stringUtil.upperCase(column.mappingTable)}_NAME);
					}
				}

				/**
				 * Adds an association between the ${entity.humanName} and the ${tempEntity.humanNames}. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param ${tempEntity.varName}PKs the primary keys of the ${tempEntity.humanNames}
				 * @throws SystemException if a system exception occurred
				 */
				public void add${tempEntity.names}(${entity.PKClassName} pk, ${tempEntity.PKClassName}[] ${tempEntity.varName}PKs) throws SystemException {
					try {
						for (${tempEntity.PKClassName} ${tempEntity.varName}PK : ${tempEntity.varName}PKs) {
							add${tempEntity.name}.add(pk, ${tempEntity.varName}PK);
						}
					}
					catch (Exception e) {
						throw processException(e);
					}
					finally {
						FinderCacheUtil.clearCache(${entity.name}ModelImpl.MAPPING_TABLE_${stringUtil.upperCase(column.mappingTable)}_NAME);
					}
				}

				/**
				 * Adds an association between the ${entity.humanName} and the ${tempEntity.humanNames}. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param ${tempEntity.varNames} the ${tempEntity.humanNames}
				 * @throws SystemException if a system exception occurred
				 */
				public void add${tempEntity.names}(${entity.PKClassName} pk, List<${tempEntity.packagePath}.model.${tempEntity.name}> ${tempEntity.varNames}) throws SystemException {
					try {
						for (${tempEntity.packagePath}.model.${tempEntity.name} ${tempEntity.varName} : ${tempEntity.varNames}) {
							add${tempEntity.name}.add(pk, ${tempEntity.varName}.getPrimaryKey());
						}
					}
					catch (Exception e) {
						throw processException(e);
					}
					finally {
						FinderCacheUtil.clearCache(${entity.name}ModelImpl.MAPPING_TABLE_${stringUtil.upperCase(column.mappingTable)}_NAME);
					}
				}

				/**
				 * Clears all associations between the ${entity.humanName} and its ${tempEntity.humanNames}. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName} to clear the associated ${tempEntity.humanNames} from
				 * @throws SystemException if a system exception occurred
				 */
				public void clear${tempEntity.names}(${entity.PKClassName} pk) throws SystemException {
					try {
						clear${tempEntity.names}.clear(pk);
					}
					catch (Exception e) {
						throw processException(e);
					}
					finally {
						FinderCacheUtil.clearCache(${entity.name}ModelImpl.MAPPING_TABLE_${stringUtil.upperCase(column.mappingTable)}_NAME);
					}
				}

				/**
				 * Removes the association between the ${entity.humanName} and the ${tempEntity.humanName}. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param ${tempEntity.varName}PK the primary key of the ${tempEntity.humanName}
				 * @throws SystemException if a system exception occurred
				 */
				public void remove${tempEntity.name}(${entity.PKClassName} pk, ${tempEntity.PKClassName} ${tempEntity.varName}PK) throws SystemException {
					try {
						remove${tempEntity.name}.remove(pk, ${tempEntity.varName}PK);
					}
					catch (Exception e) {
						throw processException(e);
					}
					finally {
						FinderCacheUtil.clearCache(${entity.name}ModelImpl.MAPPING_TABLE_${stringUtil.upperCase(column.mappingTable)}_NAME);
					}
				}

				/**
				 * Removes the association between the ${entity.humanName} and the ${tempEntity.humanName}. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param ${tempEntity.varName} the ${tempEntity.humanName}
				 * @throws SystemException if a system exception occurred
				 */
				public void remove${tempEntity.name}(${entity.PKClassName} pk, ${tempEntity.packagePath}.model.${tempEntity.name} ${tempEntity.varName}) throws SystemException {
					try {
						remove${tempEntity.name}.remove(pk, ${tempEntity.varName}.getPrimaryKey());
					}
					catch (Exception e) {
						throw processException(e);
					}
					finally {
						FinderCacheUtil.clearCache(${entity.name}ModelImpl.MAPPING_TABLE_${stringUtil.upperCase(column.mappingTable)}_NAME);
					}
				}

				/**
				 * Removes the association between the ${entity.humanName} and the ${tempEntity.humanNames}. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param ${tempEntity.varName}PKs the primary keys of the ${tempEntity.humanNames}
				 * @throws SystemException if a system exception occurred
				 */
				public void remove${tempEntity.names}(${entity.PKClassName} pk, ${tempEntity.PKClassName}[] ${tempEntity.varName}PKs) throws SystemException {
					try {
						for (${tempEntity.PKClassName} ${tempEntity.varName}PK : ${tempEntity.varName}PKs) {
							remove${tempEntity.name}.remove(pk, ${tempEntity.varName}PK);
						}
					}
					catch (Exception e) {
						throw processException(e);
					}
					finally {
						FinderCacheUtil.clearCache(${entity.name}ModelImpl.MAPPING_TABLE_${stringUtil.upperCase(column.mappingTable)}_NAME);
					}
				}

				/**
				 * Removes the association between the ${entity.humanName} and the ${tempEntity.humanNames}. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param ${tempEntity.varNames} the ${tempEntity.humanNames}
				 * @throws SystemException if a system exception occurred
				 */
				public void remove${tempEntity.names}(${entity.PKClassName} pk, List<${tempEntity.packagePath}.model.${tempEntity.name}> ${tempEntity.varNames}) throws SystemException {
					try {
						for (${tempEntity.packagePath}.model.${tempEntity.name} ${tempEntity.varName} : ${tempEntity.varNames}) {
							remove${tempEntity.name}.remove(pk, ${tempEntity.varName}.getPrimaryKey());
						}
					}
					catch (Exception e) {
						throw processException(e);
					}
					finally {
						FinderCacheUtil.clearCache(${entity.name}ModelImpl.MAPPING_TABLE_${stringUtil.upperCase(column.mappingTable)}_NAME);
					}
				}

				/**
				 * Sets the ${tempEntity.humanNames} associated with the ${entity.humanName}, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param ${tempEntity.varName}PKs the primary keys of the ${tempEntity.humanNames} to be associated with the ${entity.humanName}
				 * @throws SystemException if a system exception occurred
				 */
				public void set${tempEntity.names}(${entity.PKClassName} pk, ${tempEntity.PKClassName}[] ${tempEntity.varName}PKs) throws SystemException {
					try {
						Set<${serviceBuilder.getPrimitiveObj("${tempEntity.PKClassName}")}> ${tempEntity.varName}PKSet = SetUtil.fromArray(${tempEntity.varName}PKs);

						List<${tempEntity.packagePath}.model.${tempEntity.name}> ${tempEntity.varNames} = get${tempEntity.names}(pk);

						for (${tempEntity.packagePath}.model.${tempEntity.name} ${tempEntity.varName} : ${tempEntity.varNames}) {
							if (!${tempEntity.varName}PKSet.remove(${tempEntity.varName}.getPrimaryKey())) {
								remove${tempEntity.name}.remove(pk, ${tempEntity.varName}.getPrimaryKey());
							}
						}

						for (${serviceBuilder.getPrimitiveObj("${tempEntity.PKClassName}")} ${tempEntity.varName}PK : ${tempEntity.varName}PKSet) {
							add${tempEntity.name}.add(pk, ${tempEntity.varName}PK);
						}
					}
					catch (Exception e) {
						throw processException(e);
					}
					finally {
						FinderCacheUtil.clearCache(${entity.name}ModelImpl.MAPPING_TABLE_${stringUtil.upperCase(column.mappingTable)}_NAME);
					}
				}

				/**
				 * Sets the ${tempEntity.humanNames} associated with the ${entity.humanName}, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
				 *
				 * @param pk the primary key of the ${entity.humanName}
				 * @param ${tempEntity.varNames} the ${tempEntity.humanNames} to be associated with the ${entity.humanName}
				 * @throws SystemException if a system exception occurred
				 */
				public void set${tempEntity.names}(${entity.PKClassName} pk, List<${tempEntity.packagePath}.model.${tempEntity.name}> ${tempEntity.varNames}) throws SystemException {
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
					finally {
						FinderCacheUtil.clearCache(${entity.name}ModelImpl.MAPPING_TABLE_${stringUtil.upperCase(column.mappingTable)}_NAME);
					}
				}
			</#if>
		</#if>
	</#list>

	<#if entity.isHierarchicalTree()>
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
		public void rebuildTree(long ${scopeColumn.name}, boolean force) throws SystemException {
			if (!rebuildTreeEnabled) {
				return;
			}

			if (force || (countOrphanTreeNodes(${scopeColumn.name}) > 0)) {
				rebuildTree(${scopeColumn.name}, 0, 1);

				CacheRegistryUtil.clear(${entity.name}Impl.class.getName());
				EntityCacheUtil.clearCache(${entity.name}Impl.class.getName());
				FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
				FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
			}
		}

		public void setRebuildTreeEnabled(boolean rebuildTreeEnabled) {
			this.rebuildTreeEnabled = rebuildTreeEnabled;
		}

		protected long countOrphanTreeNodes(long ${scopeColumn.name}) throws SystemException {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery("SELECT COUNT(*) AS COUNT_VALUE FROM ${entity.table} WHERE ${scopeColumn.name} = ? AND (left${pkColumn.methodName} = 0 OR left${pkColumn.methodName} IS NULL OR right${pkColumn.methodName} = 0 OR right${pkColumn.methodName} IS NULL)");

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

		protected void expandNoChildrenLeft${pkColumn.methodName}(long ${scopeColumn.name}, long left${pkColumn.methodName}, List<Long> children${pkColumn.methodNames}, long delta) {
			String sql = "UPDATE ${entity.table} SET left${entity.PKDBName} = (left${entity.PKDBName} + ?) WHERE (${scopeColumn.DBName} = ?) AND (left${entity.PKDBName} > ?) AND (${entity.PKDBName} NOT IN (" + StringUtil.merge(children${pkColumn.methodNames}) + "))";

			SqlUpdate _sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(), sql, new int[] {java.sql.Types.BIGINT, java.sql.Types.BIGINT, java.sql.Types.BIGINT});

			_sqlUpdate.update(new Object[] {delta, ${scopeColumn.name}, left${pkColumn.methodName} });
		}

		protected void expandNoChildrenRight${pkColumn.methodName}(long ${scopeColumn.name}, long right${pkColumn.methodName}, List<Long> children${pkColumn.methodNames}, long delta) {
			String sql = "UPDATE ${entity.table} SET right${entity.PKDBName} = (right${entity.PKDBName} + ?) WHERE (${scopeColumn.DBName} = ?) AND (right${entity.PKDBName} > ?) AND (${entity.PKDBName} NOT IN (" + StringUtil.merge(children${pkColumn.methodNames}) + "))";

			SqlUpdate _sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(), sql, new int[] {java.sql.Types.BIGINT, java.sql.Types.BIGINT, java.sql.Types.BIGINT});

			_sqlUpdate.update(new Object[] {delta, ${scopeColumn.name}, right${pkColumn.methodName} });
		}

		protected void expandTree(${entity.name} ${entity.varName}, List<Long> children${pkColumn.methodNames}) throws SystemException {
			if (!rebuildTreeEnabled) {
				return;
			}

			long ${scopeColumn.name} = ${entity.varName}.get${scopeColumn.methodName}();

			long lastRight${pkColumn.methodName} = getLastRight${pkColumn.methodName}(${scopeColumn.name}, ${entity.varName}.getParent${pkColumn.methodName}());

			long left${pkColumn.methodName} = 2;
			long right${pkColumn.methodName} = 3;

			if (lastRight${pkColumn.methodName} > 0) {
				left${pkColumn.methodName} = lastRight${pkColumn.methodName} + 1;

				long childrenDistance = ${entity.varName}.getRight${pkColumn.methodName}() - ${entity.varName}.getLeft${pkColumn.methodName}();

				if (childrenDistance > 1) {
					right${pkColumn.methodName} = left${pkColumn.methodName} + childrenDistance;

					updateChildrenTree(${scopeColumn.name}, children${pkColumn.methodNames}, left${pkColumn.methodName} - ${entity.varName}.getLeft${pkColumn.methodName}());

					expandNoChildrenLeft${pkColumn.methodName}(${scopeColumn.name}, lastRight${pkColumn.methodName}, children${pkColumn.methodNames}, childrenDistance + 1);
					expandNoChildrenRight${pkColumn.methodName}(${scopeColumn.name}, lastRight${pkColumn.methodName}, children${pkColumn.methodNames}, childrenDistance + 1);
				}
				else {
					right${pkColumn.methodName} = lastRight${pkColumn.methodName} + 2;

					expandTreeLeft${pkColumn.methodName}.expand(${scopeColumn.name}, lastRight${pkColumn.methodName});
					expandTreeRight${pkColumn.methodName}.expand(${scopeColumn.name}, lastRight${pkColumn.methodName});
				}

				CacheRegistryUtil.clear(${entity.name}Impl.class.getName());
				EntityCacheUtil.clearCache(${entity.name}Impl.class.getName());
				FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
				FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
			}

			${entity.varName}.setLeft${pkColumn.methodName}(left${pkColumn.methodName});
			${entity.varName}.setRight${pkColumn.methodName}(right${pkColumn.methodName});
		}

		protected List<Long> getChildrenTree${pkColumn.methodNames}(${entity.name} parent${entity.name}) throws SystemException {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery("SELECT ${entity.PKDBName} FROM ${entity.table} WHERE (${scopeColumn.DBName} = ?) AND (left${entity.PKDBName} BETWEEN ? AND ?)");

				q.addScalar("${pkColumn.methodName}", com.liferay.portal.kernel.dao.orm.Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parent${entity.name}.get${scopeColumn.methodName}());
				qPos.add(parent${entity.name}.getLeft${pkColumn.methodName}() + 1);
				qPos.add(parent${entity.name}.getRight${pkColumn.methodName}());

				return q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		protected long getLastRight${pkColumn.methodName}(long ${scopeColumn.name}, long parent${pkColumn.methodName}) throws SystemException {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery("SELECT right${pkColumn.methodName} FROM ${entity.table} WHERE (${scopeColumn.DBName} = ?) AND (parent${pkColumn.methodName} = ?) ORDER BY right${pkColumn.methodName} DESC");

				q.addScalar("right${pkColumn.methodName}", com.liferay.portal.kernel.dao.orm.Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(${scopeColumn.name});
				qPos.add(parent${pkColumn.methodName});

				List<Long> list = (List<Long>)QueryUtil.list(q, getDialect(), 0, 1);

				if (list.isEmpty()) {
					if (parent${pkColumn.methodName} > 0) {
						session.clear();

						${entity.name} parent${entity.name} = findByPrimaryKey(parent${pkColumn.methodName});

						return parent${entity.name}.getLeft${pkColumn.methodName}();
					}

					return 0;
				}
				else {
					return list.get(0);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		protected long rebuildTree(long ${scopeColumn.name}, long parent${pkColumn.methodName}, long left${pkColumn.methodName}) throws SystemException {
			if (!rebuildTreeEnabled) {
				return 0;
			}

			List<Long> ${pkColumn.names} = null;

			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery("SELECT ${pkColumn.name} FROM ${entity.table} WHERE ${scopeColumn.name} = ? AND parent${pkColumn.methodName} = ? ORDER BY ${pkColumn.name} ASC");

				q.addScalar("${pkColumn.name}", com.liferay.portal.kernel.dao.orm.Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(${scopeColumn.name});
				qPos.add(parent${pkColumn.methodName});

				${pkColumn.names} = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}

			long right${pkColumn.methodName} = left${pkColumn.methodName} + 1;

			for (long ${pkColumn.name} : ${pkColumn.names}) {
				right${pkColumn.methodName} = rebuildTree(${scopeColumn.name}, ${pkColumn.name}, right${pkColumn.methodName});
			}

			if (parent${pkColumn.methodName} > 0) {
				updateTree.update(parent${pkColumn.methodName}, left${pkColumn.methodName}, right${pkColumn.methodName});
			}

			return right${pkColumn.methodName} + 1;
		}

		protected void shrinkTree(${entity.name} ${entity.varName}) {
			if (!rebuildTreeEnabled) {
				return;
			}

			long ${scopeColumn.name} = ${entity.varName}.get${scopeColumn.methodName}();

			long left${pkColumn.methodName} = ${entity.varName}.getLeft${pkColumn.methodName}();
			long right${pkColumn.methodName} = ${entity.varName}.getRight${pkColumn.methodName}();

			long delta = (right${pkColumn.methodName} - left${pkColumn.methodName}) + 1;

			shrinkTreeLeft${pkColumn.methodName}.shrink(${scopeColumn.name}, right${pkColumn.methodName}, delta);
			shrinkTreeRight${pkColumn.methodName}.shrink(${scopeColumn.name}, right${pkColumn.methodName}, delta);

			CacheRegistryUtil.clear(${entity.name}Impl.class.getName());
			EntityCacheUtil.clearCache(${entity.name}Impl.class.getName());
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		protected void updateChildrenTree(long ${scopeColumn.name}, List<Long> children${pkColumn.methodNames}, long delta) {
			String sql = "UPDATE ${entity.table} SET left${entity.PKDBName} = (left${entity.PKDBName} + ?), right${entity.PKDBName} = (right${entity.PKDBName} + ?) WHERE (${scopeColumn.DBName} = ?) AND (${entity.PKDBName} IN (" + StringUtil.merge(children${pkColumn.methodNames}) + "))";

			SqlUpdate _sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(), sql, new int[] {java.sql.Types.BIGINT, java.sql.Types.BIGINT, java.sql.Types.BIGINT});

			_sqlUpdate.update(new Object[] {delta, delta, ${scopeColumn.name} });
		}
	</#if>

	/**
	 * Initializes the ${entity.humanName} persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(${propsUtil}.get("value.object.listener.${packagePath}.model.${entity.name}")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<${entity.name}>> listenersList = new ArrayList<ModelListener<${entity.name}>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<${entity.name}>)InstanceFactory.newInstance(listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		<#list entity.columnList as column>
			<#if column.isCollection() && (column.isMappingManyToMany() || column.isMappingOneToMany())>
				<#assign tempEntity = serviceBuilder.getEntity(column.getEJBName())>

				contains${tempEntity.name} = new Contains${tempEntity.name}();

				<#if column.isMappingManyToMany()>
					add${tempEntity.name} = new Add${tempEntity.name}();
					clear${tempEntity.names} = new Clear${tempEntity.names}();
					remove${tempEntity.name} = new Remove${tempEntity.name}();
				</#if>
			</#if>
		</#list>

		<#if entity.isHierarchicalTree()>
			expandTreeLeft${pkColumn.methodName} = new ExpandTreeLeft${pkColumn.methodName}();
			expandTreeRight${pkColumn.methodName} = new ExpandTreeRight${pkColumn.methodName}();
			shrinkTreeLeft${pkColumn.methodName} = new ShrinkTreeLeft${pkColumn.methodName}();
			shrinkTreeRight${pkColumn.methodName} = new ShrinkTreeRight${pkColumn.methodName}();
			updateTree = new UpdateTree();
		</#if>
	}

	public void destroy() {
		EntityCacheUtil.removeCache(${entity.name}Impl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	<#list referenceList as tempEntity>
		<#if tempEntity.hasColumns() && (entity.name == "Counter" || tempEntity.name != "Counter")>
			@BeanReference(type = ${tempEntity.name}Persistence.class)
			protected ${tempEntity.name}Persistence ${tempEntity.varName}Persistence;
		</#if>
	</#list>

	<#list entity.columnList as column>
		<#if column.isCollection() && (column.isMappingManyToMany() || column.isMappingOneToMany())>
			<#assign tempEntity = serviceBuilder.getEntity(column.getEJBName())>

			protected Contains${tempEntity.name} contains${tempEntity.name};

			<#if column.isMappingManyToMany()>
				protected Add${tempEntity.name} add${tempEntity.name};
				protected Clear${tempEntity.names} clear${tempEntity.names};
				protected Remove${tempEntity.name} remove${tempEntity.name};
			</#if>
		</#if>
	</#list>

	<#list entity.columnList as column>
		<#if column.isCollection() && (column.isMappingManyToMany() || column.isMappingOneToMany())>
			<#assign tempEntity = serviceBuilder.getEntity(column.getEJBName())>
			<#assign entitySqlType = serviceBuilder.getSqlType(packagePath + ".model." + entity.getName(), entity.getPKVarName(), entity.getPKClassName())>
			<#assign tempEntitySqlType = serviceBuilder.getSqlType(tempEntity.getPackagePath() + ".model." + entity.getName(), tempEntity.getPKVarName(), tempEntity.getPKClassName())>

			<#if entity.hasPrimitivePK()>
				<#assign pkVarNameWrapper = "new " + serviceBuilder.getPrimitiveObj(entity.getPKClassName()) + "("+ entity.getPKVarName() + ")">
			<#else>
				<#assign pkVarNameWrapper = entity.getPKVarName()>
			</#if>

			<#if tempEntity.hasPrimitivePK()>
				<#assign tempEntityPkVarNameWrapper = "new " + serviceBuilder.getPrimitiveObj(tempEntity.getPKClassName()) + "("+ tempEntity.getPKVarName() + ")">
			<#else>
				<#assign tempEntityPkVarNameWrapper = tempEntity.getPKVarName()>
			</#if>

			protected class Contains${tempEntity.name} {

				protected Contains${tempEntity.name}() {
					_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(), _SQL_CONTAINS${tempEntity.name?upper_case}, new int[] {java.sql.Types.${entitySqlType}, java.sql.Types.${tempEntitySqlType}}, RowMapper.COUNT);
				}

				protected boolean contains(${entity.PKClassName} ${entity.PKVarName}, ${tempEntity.PKClassName} ${tempEntity.PKVarName}) {
					List<Integer> results = _mappingSqlQuery.execute(new Object[] {${pkVarNameWrapper}, ${tempEntityPkVarNameWrapper}});

					if (results.size()> 0) {
						Integer count = results.get(0);

						if (count.intValue()> 0) {
							return true;
						}
					}

					return false;
				}

				private MappingSqlQuery<Integer> _mappingSqlQuery;

			}

			<#if column.isMappingManyToMany()>
				protected class Add${tempEntity.name} {

					protected Add${tempEntity.name}() {
						_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(), "INSERT INTO ${column.mappingTable} (${entity.PKVarName}, ${tempEntity.PKVarName}) VALUES (?, ?)", new int[] {java.sql.Types.${entitySqlType}, java.sql.Types.${tempEntitySqlType}});
					}

					protected void add(${entity.PKClassName} ${entity.PKVarName}, ${tempEntity.PKClassName} ${tempEntity.PKVarName}) throws SystemException {
						if (!contains${tempEntity.name}.contains(${entity.PKVarName}, ${tempEntity.PKVarName})) {
							ModelListener<${tempEntity.packagePath}.model.${tempEntity.name}>[] ${tempEntity.varName}Listeners = ${tempEntity.varName}Persistence.getListeners();

							for (ModelListener<${entity.name}> listener : listeners) {
								listener.onBeforeAddAssociation(${entity.PKVarName}, ${tempEntity.packagePath}.model.${tempEntity.name}.class.getName(), ${tempEntity.PKVarName});
							}

							for (ModelListener<${tempEntity.packagePath}.model.${tempEntity.name}> listener : ${tempEntity.varName}Listeners) {
								listener.onBeforeAddAssociation(${tempEntity.PKVarName}, ${entity.name}.class.getName(), ${entity.PKVarName});
							}

							_sqlUpdate.update(new Object[] {${pkVarNameWrapper}, ${tempEntityPkVarNameWrapper}});

							for (ModelListener<${entity.name}> listener : listeners) {
								listener.onAfterAddAssociation(${entity.PKVarName}, ${tempEntity.packagePath}.model.${tempEntity.name}.class.getName(), ${tempEntity.PKVarName});
							}

							for (ModelListener<${tempEntity.packagePath}.model.${tempEntity.name}> listener : ${tempEntity.varName}Listeners) {
								listener.onAfterAddAssociation(${tempEntity.PKVarName}, ${entity.name}.class.getName(), ${entity.PKVarName});
							}
						}
					}

					private SqlUpdate _sqlUpdate;

				}

				protected class Clear${tempEntity.names} {

					protected Clear${tempEntity.names}() {
						_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(), "DELETE FROM ${column.mappingTable} WHERE ${entity.PKVarName} = ?", new int[] {java.sql.Types.${entitySqlType}});
					}

					protected void clear(${entity.PKClassName} ${entity.PKVarName}) throws SystemException {
						ModelListener<${tempEntity.packagePath}.model.${tempEntity.name}>[] ${tempEntity.varName}Listeners = ${tempEntity.varName}Persistence.getListeners();

						List<${tempEntity.packagePath}.model.${tempEntity.name}> ${tempEntity.varNames} = null;

						if ((listeners.length > 0) || (${tempEntity.varName}Listeners.length > 0)) {
							${tempEntity.varNames} = get${tempEntity.names}(${entity.PKVarName});

							for (${tempEntity.packagePath}.model.${tempEntity.name} ${tempEntity.varName} : ${tempEntity.varNames}) {
								for (ModelListener<${entity.name}> listener : listeners) {
									listener.onBeforeRemoveAssociation(${entity.PKVarName}, ${tempEntity.packagePath}.model.${tempEntity.name}.class.getName(), ${tempEntity.varName}.getPrimaryKey());
								}

								for (ModelListener<${tempEntity.packagePath}.model.${tempEntity.name}> listener : ${tempEntity.varName}Listeners) {
									listener.onBeforeRemoveAssociation(${tempEntity.varName}.getPrimaryKey(), ${entity.name}.class.getName(), ${entity.PKVarName});
								}
							}
						}

						_sqlUpdate.update(new Object[] {${pkVarNameWrapper}});

						if ((listeners.length > 0) || (${tempEntity.varName}Listeners.length > 0)) {
							for (${tempEntity.packagePath}.model.${tempEntity.name} ${tempEntity.varName} : ${tempEntity.varNames}) {
								for (ModelListener<${entity.name}> listener : listeners) {
									listener.onAfterRemoveAssociation(${entity.PKVarName}, ${tempEntity.packagePath}.model.${tempEntity.name}.class.getName(), ${tempEntity.varName}.getPrimaryKey());
								}

								for (ModelListener<${tempEntity.packagePath}.model.${tempEntity.name}> listener : ${tempEntity.varName}Listeners) {
									listener.onAfterRemoveAssociation(${tempEntity.varName}.getPrimaryKey(), ${entity.name}.class.getName(), ${entity.PKVarName});
								}
							}
						}
					}

					private SqlUpdate _sqlUpdate;

				}

				protected class Remove${tempEntity.name} {

					protected Remove${tempEntity.name}() {
						_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(), "DELETE FROM ${column.mappingTable} WHERE ${entity.PKVarName} = ? AND ${tempEntity.PKVarName} = ?", new int[] {java.sql.Types.${entitySqlType}, java.sql.Types.${tempEntitySqlType}});
					}

					protected void remove(${entity.PKClassName} ${entity.PKVarName}, ${tempEntity.PKClassName} ${tempEntity.PKVarName}) throws SystemException {
						if (contains${tempEntity.name}.contains(${entity.PKVarName}, ${tempEntity.PKVarName})) {
							ModelListener<${tempEntity.packagePath}.model.${tempEntity.name}>[] ${tempEntity.varName}Listeners = ${tempEntity.varName}Persistence.getListeners();

							for (ModelListener<${entity.name}> listener : listeners) {
								listener.onBeforeRemoveAssociation(${entity.PKVarName}, ${tempEntity.packagePath}.model.${tempEntity.name}.class.getName(), ${tempEntity.PKVarName});
							}

							for (ModelListener<${tempEntity.packagePath}.model.${tempEntity.name}> listener : ${tempEntity.varName}Listeners) {
								listener.onBeforeRemoveAssociation(${tempEntity.PKVarName}, ${entity.name}.class.getName(), ${entity.PKVarName});
							}

							_sqlUpdate.update(new Object[] {${pkVarNameWrapper}, ${tempEntityPkVarNameWrapper}});

							for (ModelListener<${entity.name}> listener : listeners) {
								listener.onAfterRemoveAssociation(${entity.PKVarName}, ${tempEntity.packagePath}.model.${tempEntity.name}.class.getName(), ${tempEntity.PKVarName});
							}

							for (ModelListener<${tempEntity.packagePath}.model.${tempEntity.name}> listener : ${tempEntity.varName}Listeners) {
								listener.onAfterRemoveAssociation(${tempEntity.PKVarName}, ${entity.name}.class.getName(), ${entity.PKVarName});
							}
						}
					}

					private SqlUpdate _sqlUpdate;

				}
			</#if>
		</#if>
	</#list>

	<#if entity.isHierarchicalTree()>
		protected boolean rebuildTreeEnabled = true;
		protected ExpandTreeLeft${pkColumn.methodName} expandTreeLeft${pkColumn.methodName};
		protected ExpandTreeRight${pkColumn.methodName} expandTreeRight${pkColumn.methodName};
		protected ShrinkTreeLeft${pkColumn.methodName} shrinkTreeLeft${pkColumn.methodName};
		protected ShrinkTreeRight${pkColumn.methodName} shrinkTreeRight${pkColumn.methodName};
		protected UpdateTree updateTree;

		protected class ExpandTreeLeft${pkColumn.methodName} {

			protected ExpandTreeLeft${pkColumn.methodName}() {
				_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(), "UPDATE ${entity.table} SET left${pkColumn.methodName} = (left${pkColumn.methodName} + 2) WHERE (${scopeColumn.DBName} = ?) AND (left${pkColumn.methodName} > ?)", new int[] {java.sql.Types.${serviceBuilder.getSqlType("long")}, java.sql.Types.${serviceBuilder.getSqlType("long")}});
			}

			protected void expand(long ${scopeColumn.name}, long left${pkColumn.methodName}) {
				_sqlUpdate.update(new Object[] {${scopeColumn.name}, left${pkColumn.methodName}});
			}

			private SqlUpdate _sqlUpdate;

		}

		protected class ExpandTreeRight${pkColumn.methodName} {

			protected ExpandTreeRight${pkColumn.methodName}() {
				_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(), "UPDATE ${entity.table} SET right${pkColumn.methodName} = (right${pkColumn.methodName} + 2) WHERE (${scopeColumn.DBName} = ?) AND (right${pkColumn.methodName} > ?)", new int[] {java.sql.Types.${serviceBuilder.getSqlType("long")}, java.sql.Types.${serviceBuilder.getSqlType("long")}});
			}

			protected void expand(long ${scopeColumn.name}, long right${pkColumn.methodName}) {
				_sqlUpdate.update(new Object[] {${scopeColumn.name}, right${pkColumn.methodName}});
			}

			private SqlUpdate _sqlUpdate;

		}

		protected class ShrinkTreeLeft${pkColumn.methodName} {

			protected ShrinkTreeLeft${pkColumn.methodName}() {
				_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(), "UPDATE ${entity.table} SET left${pkColumn.methodName} = (left${pkColumn.methodName} - ?) WHERE (${scopeColumn.DBName} = ?) AND (left${pkColumn.methodName} > ?)", new int[] {java.sql.Types.${serviceBuilder.getSqlType("long")}, java.sql.Types.${serviceBuilder.getSqlType("long")}, java.sql.Types.${serviceBuilder.getSqlType("long")}});
			}

			protected void shrink(long ${scopeColumn.name}, long right${pkColumn.methodName}, long delta) {
				_sqlUpdate.update(new Object[] {delta, ${scopeColumn.name}, right${pkColumn.methodName}});
			}

			private SqlUpdate _sqlUpdate;

		}

		protected class ShrinkTreeRight${pkColumn.methodName} {

			protected ShrinkTreeRight${pkColumn.methodName}() {
				_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(), "UPDATE ${entity.table} SET right${pkColumn.methodName} = (right${pkColumn.methodName} - ?) WHERE (${scopeColumn.DBName} = ?) AND (right${pkColumn.methodName} > ?)", new int[] {java.sql.Types.${serviceBuilder.getSqlType("long")}, java.sql.Types.${serviceBuilder.getSqlType("long")}, java.sql.Types.${serviceBuilder.getSqlType("long")}});
			}

			protected void shrink(long ${scopeColumn.name}, long right${pkColumn.methodName}, long delta) {
				_sqlUpdate.update(new Object[] {delta, ${scopeColumn.name}, right${pkColumn.methodName}});
			}

			private SqlUpdate _sqlUpdate;

		}

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

	<#if entity.getFinderList()?size != 0>
		private static final String _SQL_SELECT_${entity.alias?upper_case}_WHERE = "SELECT ${entity.alias} FROM ${entity.name} ${entity.alias} WHERE ";
	</#if>

	private static final String _SQL_COUNT_${entity.alias?upper_case} = "SELECT COUNT(${entity.alias}) FROM ${entity.name} ${entity.alias}";

	<#if entity.getFinderList()?size != 0>
		private static final String _SQL_COUNT_${entity.alias?upper_case}_WHERE = "SELECT COUNT(${entity.alias}) FROM ${entity.name} ${entity.alias} WHERE ";
	</#if>

	<#list entity.columnList as column>
		<#if column.isCollection()>
			<#assign tempEntity = serviceBuilder.getEntity(column.getEJBName())>

			<#if column.isMappingManyToMany()>
				private static final String _SQL_GET${tempEntity.names?upper_case} = "SELECT {${tempEntity.table}.*} FROM ${tempEntity.table} INNER JOIN ${column.mappingTable} ON (${column.mappingTable}.${tempEntity.PKDBName} = ${tempEntity.table}.${tempEntity.PKDBName}) WHERE (${column.mappingTable}.${entity.PKDBName} = ?)";

				private static final String _SQL_GET${tempEntity.names?upper_case}SIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM ${column.mappingTable} WHERE ${entity.PKDBName} = ?";

				private static final String _SQL_CONTAINS${tempEntity.name?upper_case} = "SELECT COUNT(*) AS COUNT_VALUE FROM ${column.mappingTable} WHERE ${entity.PKDBName} = ? AND ${tempEntity.PKDBName} = ?";
			<#elseif column.isMappingOneToMany()>
				private static final String _SQL_GET${tempEntity.names?upper_case} = "SELECT {${tempEntity.table}.*} FROM ${tempEntity.table} INNER JOIN ${entity.table} ON (${entity.table}.${entity.PKDBName} = ${tempEntity.table}.${entity.PKDBName}) WHERE (${entity.table}.${entity.PKDBName} = ?)";

				private static final String _SQL_GET${tempEntity.names?upper_case}SIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM ${tempEntity.table} WHERE ${entity.PKDBName} = ?";

				private static final String _SQL_CONTAINS${tempEntity.name?upper_case} = "SELECT COUNT(*) AS COUNT_VALUE FROM ${tempEntity.table} WHERE ${entity.PKDBName} = ? AND ${tempEntity.PKDBName} = ?";
			</#if>
		</#if>
	</#list>

	<#list entity.getFinderList() as finder>
		<#assign finderColsList = finder.getColumns()>

		<#list finderColsList as finderCol>
			<#assign finderColConjunction = "">

			<#if finderCol_has_next>
				<#assign finderColConjunction = " AND ">
			<#elseif finder.where?? && validator.isNotNull(finder.getWhere())>
				<#assign finderColConjunction = " AND " + finder.where>
			</#if>

			<#if !finderCol.isPrimitiveType()>
				private static final String _FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_1 =

				<#if finderCol.comparator == "=">
					"${entity.alias}<#if entity.hasCompoundPK() && finderCol.isPrimary()>.id</#if>.${finderCol.name} IS NULL${finderColConjunction}"
				<#elseif finderCol.comparator == "<>" || finderCol.comparator = "!=">
					"${entity.alias}<#if entity.hasCompoundPK() && finderCol.isPrimary()>.id</#if>.${finderCol.name} IS NOT NULL${finderColConjunction}"
				<#else>
					"${entity.alias}<#if entity.hasCompoundPK() && finderCol.isPrimary()>.id</#if>.${finderCol.name} ${finderCol.comparator} NULL${finderColConjunction}"
				</#if>

				;
			</#if>

			<#if finderCol.type == "String" && !finderCol.isCaseSensitive()>
				<#if entity.hasCompoundPK() && finderCol.isPrimary()>
					<#assign finderColExpression = "lower(" + entity.alias + ".id." + finderCol.name + ") " + finderCol.comparator + " lower(CAST_TEXT(?))">
				<#else>
					<#assign finderColExpression = "lower(" + entity.alias + "." + finderCol.name + ") " + finderCol.comparator + " lower(CAST_TEXT(?))">
				</#if>
			<#else>
				<#if entity.hasCompoundPK() && finderCol.isPrimary()>
					<#assign finderColExpression = entity.alias + ".id." + finderCol.name + " " + finderCol.comparator + " ?">
				<#else>
					<#assign finderColExpression = entity.alias + "." + finderCol.name + " " + finderCol.comparator + " ?">
				</#if>
			</#if>

			private static final String _FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_2 = "${finderColExpression}${finderColConjunction}";

			<#if finderCol.type == "String">
				private static final String _FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_3 = "(${entity.alias}<#if entity.hasCompoundPK() && finderCol.isPrimary()>.id</#if>.${finderCol.name} IS NULL OR ${finderColExpression})${finderColConjunction}";
			</#if>

			<#if finder.hasArrayableOperator()>
				<#if !finderCol.isPrimitiveType()>
					private static final String _FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_4 = "(" + _removeConjunction(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_1) + ")";
				</#if>

				private static final String _FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_5 = "(" + _removeConjunction(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_2) + ")";

				<#if finderCol.type == "String">
					private static final String _FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_6 = "(" + _removeConjunction(_FINDER_COLUMN_${finder.name?upper_case}_${finderCol.name?upper_case}_3) + ")";
				</#if>
			</#if>
		</#list>
	</#list>

	<#if entity.hasArrayableOperator()>
		private static String _removeConjunction(String sql) {
			int pos = sql.indexOf(" AND ");

			if (pos != -1) {
				sql = sql.substring(0, pos);
			}

			return sql;
		}
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

	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = <#if pluginName != "">GetterUtil.getBoolean(PropsUtil.get(PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE))<#else>com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE</#if>;

	private static Log _log = LogFactoryUtil.getLog(${entity.name}PersistenceImpl.class);

	private static ${entity.name} _null${entity.name} = new ${entity.name}Impl() {

		@Override
		public Object clone() {
			return this;
		}

		@Override
		public CacheModel<${entity.name}> toCacheModel() {
			return _null${entity.name}CacheModel;
		}

	};

	private static CacheModel<${entity.name}> _null${entity.name}CacheModel = new CacheModel<${entity.name}>() {
		public ${entity.name} toEntityModel() {
			return _null${entity.name};
		}
	};

}