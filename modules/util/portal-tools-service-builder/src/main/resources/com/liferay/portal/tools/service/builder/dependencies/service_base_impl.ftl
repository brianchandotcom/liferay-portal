package ${packagePath}.service.base;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import ${beanLocatorUtil};
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Conjunction;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.service.Base${sessionTypeName}ServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistryUtil;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;
import ${apiPackagePath}.service.${entity.name}${sessionTypeName}Service;

<#if entity.hasEntityColumns()>
	<#if entity.hasCompoundPK()>
		import ${apiPackagePath}.service.persistence.${entity.name}PK;
	</#if>

	import ${apiPackagePath}.model.${entity.name};

	<#list entity.blobEntityColumns as entityColumn>
		<#if entityColumn.lazy>
			import ${apiPackagePath}.model.${entity.name}${entityColumn.methodName}BlobModel;
		</#if>
	</#list>

	import ${packagePath}.model.impl.${entity.name}Impl;
</#if>

<#if entity.localizedEntity??>
	<#assign localizedEntity = entity.localizedEntity />

	import ${apiPackagePath}.model.${localizedEntity.name};
</#if>

<#if entity.versionEntity??>
	<#assign versionEntity = entity.versionEntity />

	import ${apiPackagePath}.model.${versionEntity.name};
	import com.liferay.portal.kernel.service.version.VersionService;
	import com.liferay.portal.kernel.service.version.VersionServiceListener;
	<#if entity.localizedEntity??>
		<#assign
			localizedEntity = entity.localizedEntity
			localizedVersionEntity = localizedEntity.versionEntity
		/>

		import ${apiPackagePath}.model.${localizedVersionEntity.name};
	</#if>
</#if>

<#list referenceEntities as referenceEntity>
	<#if referenceEntity.hasEntityColumns() && referenceEntity.hasPersistence()>
		import ${referenceEntity.apiPackagePath}.service.persistence.${referenceEntity.name}Persistence;
		import ${referenceEntity.apiPackagePath}.service.persistence.${referenceEntity.name}Util;
	</#if>

	<#if referenceEntity.hasFinderClassName() && (stringUtil.equals(entity.name, "Counter") || !stringUtil.equals(referenceEntity.name, "Counter"))>
		import ${referenceEntity.apiPackagePath}.service.persistence.${referenceEntity.name}Finder;
		import ${referenceEntity.apiPackagePath}.service.persistence.${referenceEntity.name}FinderUtil;
	</#if>
</#list>

<#if stringUtil.equals(sessionTypeName, "Local")>
/**
 * Provides the base implementation for the ${entity.humanName} local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link ${packagePath}.service.impl.${entity.name}LocalServiceImpl}.
 * </p>
 *
 * @author ${author}
 * @see ${packagePath}.service.impl.${entity.name}LocalServiceImpl
 * @see ${apiPackagePath}.service.${entity.name}LocalServiceUtil
<#if classDeprecated>
 * @deprecated ${classDeprecatedComment}
</#if>
 * @generated
 */

<#if classDeprecated>
	@Deprecated
</#if>

	@ProviderType
	public abstract class ${entity.name}LocalServiceBaseImpl extends BaseLocalServiceImpl implements ${entity.name}LocalService, IdentifiableOSGiService

	<#if entity.versionEntity??>
		<#assign versionEntity = entity.versionEntity />
		, VersionService<${entity.name}, ${versionEntity.name}>
	</#if>

	{

		/*
		 * NOTE FOR DEVELOPERS:
		 *
		 * Never modify or reference this class directly. Always use {@link ${apiPackagePath}.service.${entity.name}LocalServiceUtil} to access the ${entity.humanName} local service.
		 */
<#else>
/**
 * Provides the base implementation for the ${entity.humanName} remote service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link ${packagePath}.service.impl.${entity.name}ServiceImpl}.
 * </p>
 *
 * @author ${author}
 * @see ${packagePath}.service.impl.${entity.name}ServiceImpl
 * @see ${apiPackagePath}.service.${entity.name}ServiceUtil
<#if classDeprecated>
 * @deprecated ${classDeprecatedComment}
</#if>
 * @generated
 */

<#if classDeprecated>
	@Deprecated
</#if>

	public abstract class ${entity.name}ServiceBaseImpl extends BaseServiceImpl implements ${entity.name}Service, IdentifiableOSGiService {

		/*
		 * NOTE FOR DEVELOPERS:
		 *
		 * Never modify or reference this class directly. Always use {@link ${apiPackagePath}.service.${entity.name}ServiceUtil} to access the ${entity.humanName} remote service.
		 */
</#if>

	<#if stringUtil.equals(sessionTypeName, "Local") && entity.hasEntityColumns() && entity.hasPersistence()>
		<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "add" + entity.name, [apiPackagePath + ".model." + entity.name], []) />

		/**
		 * Adds the ${entity.humanName} to the database. Also notifies the appropriate model listeners.
		 *
		 * @param ${entity.varName} the ${entity.humanName}
		 * @return the ${entity.humanName} that was added
		<#list serviceBaseExceptions as exception>
		 * @throws ${exception}
		</#list>
		 */
		@Indexable(type = IndexableType.REINDEX)
		@Override
		public ${entity.name} add${entity.name}(${entity.name} ${entity.varName}) <#if (serviceBaseExceptions?size gt 0)>throws ${stringUtil.merge(serviceBaseExceptions)} </#if>{
			${entity.varName}.setNew(true);

			return ${entity.varName}Persistence.update(${entity.varName});
		}

		<#if entity.versionEntity??>
			/**
			 * Creates a new ${entity.humanName}. Does not add the ${entity.humanName} to the database.
			 *
			 * @return the new ${entity.humanName}
			 */
			@Override
			@Transactional(enabled = false)
			public ${entity.name} create() {
				long primaryKey = counterLocalService.increment(${entity.name}.class.getName());

				${entity.name} draft${entity.name} = ${entity.varName}Persistence.create(primaryKey);

				draft${entity.name}.setHeadId(primaryKey);

				return draft${entity.name};
			}
		<#else>
			/**
			 * Creates a new ${entity.humanName} with the primary key. Does not add the ${entity.humanName} to the database.
			 *
			 * @param ${entity.PKVarName} the primary key for the new ${entity.humanName}
			 * @return the new ${entity.humanName}
			 */
			@Override
			@Transactional(enabled = false)
			public ${entity.name} create${entity.name}(${entity.PKClassName} ${entity.PKVarName}) {
				return ${entity.varName}Persistence.create(${entity.PKVarName});
			}
		</#if>

		<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "delete" + entity.name, [entity.PKClassName], ["PortalException"]) />

		/**
		 * Deletes the ${entity.humanName} with the primary key from the database. Also notifies the appropriate model listeners.
		 *
		 * @param ${entity.PKVarName} the primary key of the ${entity.humanName}
		 * @return the ${entity.humanName} that was removed
		<#list serviceBaseExceptions as exception>
		<#if stringUtil.equals(exception, "PortalException")>
		 * @throws PortalException if a ${entity.humanName} with the primary key could not be found
		<#else>
		 * @throws ${exception}
		</#if>
		</#list>
		 */
		@Indexable(type = IndexableType.DELETE)
		@Override
		public ${entity.name} delete${entity.name}(${entity.PKClassName} ${entity.PKVarName}) <#if (serviceBaseExceptions?size gt 0)>throws ${stringUtil.merge(serviceBaseExceptions)} </#if>{
			<#if entity.versionEntity??>
				<#if !serviceBaseExceptions?seq_contains("PortalException")>
					try {
				</#if>

				${entity.name} ${entity.varName} = ${entity.varName}Persistence.fetchByPrimaryKey(${entity.PKVarName});

				if (${entity.varName} != null) {
					delete(${entity.varName});
				}

				return ${entity.varName};

				<#if !serviceBaseExceptions?seq_contains("PortalException")>
					}
					catch (PortalException pe) {
						throw new SystemException(pe);
					}
				</#if>
			<#else>
				return ${entity.varName}Persistence.remove(${entity.PKVarName});
			</#if>
		}

		<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "delete" + entity.name, [apiPackagePath + ".model." + entity.name], []) />

		/**
		 * Deletes the ${entity.humanName} from the database. Also notifies the appropriate model listeners.
		 *
		 * @param ${entity.varName} the ${entity.humanName}
		 * @return the ${entity.humanName} that was removed
		<#list serviceBaseExceptions as exception>
		 * @throws ${exception}
		</#list>
		 */
		@Indexable(type = IndexableType.DELETE)
		@Override
		public ${entity.name} delete${entity.name}(${entity.name} ${entity.varName}) <#if (serviceBaseExceptions?size gt 0)>throws ${stringUtil.merge(serviceBaseExceptions)} </#if>{
			<#if entity.versionEntity??>
				<#if !serviceBaseExceptions?seq_contains("PortalException")>
					try {
				</#if>

				delete(${entity.varName});

				return ${entity.varName};

				<#if !serviceBaseExceptions?seq_contains("PortalException")>
					}
					catch (PortalException pe) {
						throw new SystemException(pe);
					}
				</#if>
			<#else>
				return ${entity.varName}Persistence.remove(${entity.varName});
			</#if>
		}

		@Override
		public DynamicQuery dynamicQuery() {
			Class<?> clazz = getClass();

			return DynamicQueryFactoryUtil.forClass(${entity.name}.class, clazz.getClassLoader());
		}

		/**
		 * Performs a dynamic query on the database and returns the matching rows.
		 *
		 * @param dynamicQuery the dynamic query
		 * @return the matching rows
		 */
		@Override
		public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
			return ${entity.varName}Persistence.findWithDynamicQuery(dynamicQuery);
		}

		/**
		 * Performs a dynamic query on the database and returns a range of the matching rows.
		 *
		 * <p>
		 * <#include "range_comment.ftl">
		 * </p>
		 *
		 * @param dynamicQuery the dynamic query
		 * @param start the lower bound of the range of model instances
		 * @param end the upper bound of the range of model instances (not inclusive)
		 * @return the range of matching rows
		 */
		@Override
		public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start, int end) {
			return ${entity.varName}Persistence.findWithDynamicQuery(dynamicQuery, start, end);
		}

		/**
		 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
		 *
		 * <p>
		 * <#include "range_comment.ftl">
		 * </p>
		 *
		 * @param dynamicQuery the dynamic query
		 * @param start the lower bound of the range of model instances
		 * @param end the upper bound of the range of model instances (not inclusive)
		 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
		 * @return the ordered range of matching rows
		 */
		@Override
		public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start, int end, OrderByComparator<T> orderByComparator) {
			return ${entity.varName}Persistence.findWithDynamicQuery(dynamicQuery, start, end, orderByComparator);
		}

		/**
		 * Returns the number of rows matching the dynamic query.
		 *
		 * @param dynamicQuery the dynamic query
		 * @return the number of rows matching the dynamic query
		 */
		@Override
		public long dynamicQueryCount(DynamicQuery dynamicQuery) {
			return ${entity.varName}Persistence.countWithDynamicQuery(dynamicQuery);
		}

		/**
		 * Returns the number of rows matching the dynamic query.
		 *
		 * @param dynamicQuery the dynamic query
		 * @param projection the projection to apply to the query
		 * @return the number of rows matching the dynamic query
		 */
		@Override
		public long dynamicQueryCount(DynamicQuery dynamicQuery, Projection projection) {
			return ${entity.varName}Persistence.countWithDynamicQuery(dynamicQuery, projection);
		}

		<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "fetch" + entity.name, [entity.PKClassName], []) />

		@Override
		public ${entity.name} fetch${entity.name}(${entity.PKClassName} ${entity.PKVarName}) <#if (serviceBaseExceptions?size gt 0)>throws ${stringUtil.merge(serviceBaseExceptions)} </#if>{
			return ${entity.varName}Persistence.fetchByPrimaryKey(${entity.PKVarName});
		}

		<#if entity.hasUuid() && entity.hasEntityColumn("companyId") && (!entity.hasEntityColumn("groupId") || stringUtil.equals(entity.name, "Group")) && !entity.versionEntity??>
			/**
			 * Returns the ${entity.humanName} with the matching UUID and company.
			 *
			 * @param uuid the ${entity.humanName}'s UUID
			 * @param companyId the primary key of the company
			 * @return the matching ${entity.humanName}, or <code>null</code> if a matching ${entity.humanName} could not be found
			<#list serviceBaseExceptions as exception>
			 * @throws ${exception}
			</#list>
			 */
			@Override
			public ${entity.name} fetch${entity.name}ByUuidAndCompanyId(String uuid, long companyId) <#if (serviceBaseExceptions?size gt 0)>throws ${stringUtil.merge(serviceBaseExceptions)} </#if>{
				return ${entity.varName}Persistence.fetchByUuid_C_First(uuid, companyId, null);
			}
		</#if>

		<#if entity.hasUuid() && entity.hasEntityColumn("groupId") && !stringUtil.equals(entity.name, "Group") && !entity.versionEntity??>
			<#if stringUtil.equals(entity.name, "Layout")>
				/**
				 * Returns the ${entity.humanName} matching the UUID, group, and privacy.
				 *
				 * @param uuid the ${entity.humanName}'s UUID
				 * @param groupId the primary key of the group
				 * @param privateLayout whether the ${entity.humanName} is private to the group
				 * @return the matching ${entity.humanName}, or <code>null</code> if a matching ${entity.humanName} could not be found
				<#list serviceBaseExceptions as exception>
				 * @throws ${exception}
				</#list>
				 */
				@Override
				public ${entity.name} fetch${entity.name}ByUuidAndGroupId(String uuid, long groupId, boolean privateLayout) <#if (serviceBaseExceptions?size gt 0)>throws ${stringUtil.merge(serviceBaseExceptions)} </#if>{
					return ${entity.varName}Persistence.fetchByUUID_G_P(uuid, groupId, privateLayout);
				}
			<#else>
				/**
				 * Returns the ${entity.humanName} matching the UUID and group.
				 *
				 * @param uuid the ${entity.humanName}'s UUID
				 * @param groupId the primary key of the group
				 * @return the matching ${entity.humanName}, or <code>null</code> if a matching ${entity.humanName} could not be found
				<#list serviceBaseExceptions as exception>
				 * @throws ${exception}
				</#list>
				 */
				@Override
				public ${entity.name} fetch${entity.name}ByUuidAndGroupId(String uuid, long groupId) <#if (serviceBaseExceptions?size gt 0)>throws ${stringUtil.merge(serviceBaseExceptions)} </#if>{
					return ${entity.varName}Persistence.fetchByUUID_G(uuid, groupId);
				}
			</#if>
		</#if>

		<#if entity.hasExternalReferenceCode() && entity.hasEntityColumn("companyId") && !entity.versionEntity??>
			/**
			 * Returns the ${entity.humanName} with the matching external reference code and company.
			 *
			 * @param companyId the primary key of the company
			 * @param externalReferenceCode the ${entity.humanName}'s external reference code
			 * @return the matching ${entity.humanName}, or <code>null</code> if a matching ${entity.humanName} could not be found
			<#list serviceBaseExceptions as exception>
			 * @throws ${exception}
			</#list>
			 */
			@Override
			public ${entity.name} fetch${entity.name}ByReferenceCode(long companyId, String externalReferenceCode) <#if (serviceBaseExceptions?size gt 0)>throws ${stringUtil.merge(serviceBaseExceptions)} </#if>{
				return ${entity.varName}Persistence.fetchByC_ERC(companyId, externalReferenceCode);
			}
		</#if>

		<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "get" + entity.name, [entity.PKClassName], ["PortalException"]) />

		/**
		 * Returns the ${entity.humanName} with the primary key.
		 *
		 * @param ${entity.PKVarName} the primary key of the ${entity.humanName}
		 * @return the ${entity.humanName}
		<#list serviceBaseExceptions as exception>
		<#if stringUtil.equals(exception, "PortalException")>
		 * @throws PortalException if a ${entity.humanName} with the primary key could not be found
		<#else>
		 * @throws ${exception}
		</#if>
		</#list>
		 */
		@Override
		public ${entity.name} get${entity.name}(${entity.PKClassName} ${entity.PKVarName}) <#if (serviceBaseExceptions?size gt 0)>throws ${stringUtil.merge(serviceBaseExceptions)} </#if>{
			return ${entity.varName}Persistence.findByPrimaryKey(${entity.PKVarName});
		}

		<#if entity.hasActionableDynamicQuery()>
			@Override
			public ActionableDynamicQuery getActionableDynamicQuery() {
				ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

				actionableDynamicQuery.setBaseLocalService(${entity.varName}LocalService);
				actionableDynamicQuery.setClassLoader(getClassLoader());
				actionableDynamicQuery.setModelClass(${entity.name}.class);

				<#if entity.hasPrimitivePK()>
					actionableDynamicQuery.setPrimaryKeyPropertyName("${entity.PKVarName}");
				<#else>
					<#assign
						pkEntityColumn = entity.PKEntityColumns?first
					/>

					actionableDynamicQuery.setPrimaryKeyPropertyName("primaryKey.${pkEntityColumn.name}");

					<#list entity.PKEntityColumns as pkEntityColumn>
						<#if stringUtil.equals(pkEntityColumn.name, "groupId")>
							actionableDynamicQuery.setGroupIdPropertyName("primaryKey.groupId");
						</#if>
					</#list>
				</#if>

				return actionableDynamicQuery;
			}

			@Override
			public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
				IndexableActionableDynamicQuery indexableActionableDynamicQuery = new IndexableActionableDynamicQuery();

				indexableActionableDynamicQuery.setBaseLocalService(${entity.varName}LocalService);
				indexableActionableDynamicQuery.setClassLoader(getClassLoader());
				indexableActionableDynamicQuery.setModelClass(${entity.name}.class);

				<#if entity.hasPrimitivePK()>
					indexableActionableDynamicQuery.setPrimaryKeyPropertyName("${entity.PKVarName}");
				<#else>
					<#assign
						pkEntityColumn = entity.PKEntityColumns?first
					/>

					indexableActionableDynamicQuery.setPrimaryKeyPropertyName("primaryKey.${pkEntityColumn.name}");

					<#list entity.PKEntityColumns as pkEntityColumn>
						<#if stringUtil.equals(pkEntityColumn.name, "groupId")>
							indexableActionableDynamicQuery.setGroupIdPropertyName("primaryKey.groupId");
						</#if>
					</#list>
				</#if>

				return indexableActionableDynamicQuery;
			}

			protected void initActionableDynamicQuery(ActionableDynamicQuery actionableDynamicQuery) {
				actionableDynamicQuery.setBaseLocalService(${entity.varName}LocalService);
				actionableDynamicQuery.setClassLoader(getClassLoader());
				actionableDynamicQuery.setModelClass(${entity.name}.class);

				<#if entity.hasPrimitivePK()>
					actionableDynamicQuery.setPrimaryKeyPropertyName("${entity.PKVarName}");
				<#else>
					<#assign
						pkEntityColumn = entity.PKEntityColumns?first
					/>

					actionableDynamicQuery.setPrimaryKeyPropertyName("primaryKey.${pkEntityColumn.name}");

					<#list entity.PKEntityColumns as pkEntityColumn>
						<#if stringUtil.equals(pkEntityColumn.name, "groupId")>
							actionableDynamicQuery.setGroupIdPropertyName("primaryKey.groupId");
						</#if>
					</#list>
				</#if>
			}

			<#if entity.isStagedModel()>
				@Override
				public ExportActionableDynamicQuery getExportActionableDynamicQuery(final PortletDataContext portletDataContext) {
					final ExportActionableDynamicQuery exportActionableDynamicQuery = new ExportActionableDynamicQuery() {

						@Override
						public long performCount() throws PortalException {
							ManifestSummary manifestSummary = portletDataContext.getManifestSummary();

							StagedModelType stagedModelType = getStagedModelType();

							long modelAdditionCount = super.performCount();

							manifestSummary.addModelAdditionCount(stagedModelType, modelAdditionCount);

							long modelDeletionCount = ExportImportHelperUtil.getModelDeletionCount(portletDataContext, stagedModelType);

							manifestSummary.addModelDeletionCount(stagedModelType, modelDeletionCount);

							return modelAdditionCount;
						}

						<#if entity.isResourcedModel()>
							@Override
							protected Projection getCountProjection() {
								return ProjectionFactoryUtil.countDistinct("resourcePrimKey");
							}
						</#if>
					};

					initActionableDynamicQuery(exportActionableDynamicQuery);

					exportActionableDynamicQuery.setAddCriteriaMethod(
						new ActionableDynamicQuery.AddCriteriaMethod() {

							@Override
							public void addCriteria(DynamicQuery dynamicQuery) {
								<#if entity.isWorkflowEnabled()>
									Criterion modifiedDateCriterion = portletDataContext.getDateRangeCriteria("modifiedDate");

									<#if entity.isStagedGroupedModel()>
										if (modifiedDateCriterion != null) {
											Conjunction conjunction = RestrictionsFactoryUtil.conjunction();

											conjunction.add(modifiedDateCriterion);

											Disjunction disjunction = RestrictionsFactoryUtil.disjunction();

											disjunction.add(RestrictionsFactoryUtil.gtProperty("modifiedDate", "lastPublishDate"));

											Property lastPublishDateProperty = PropertyFactoryUtil.forName("lastPublishDate");

											disjunction.add(lastPublishDateProperty.isNull());

											conjunction.add(disjunction);

											modifiedDateCriterion = conjunction;
										}
									</#if>

									Criterion statusDateCriterion = portletDataContext.getDateRangeCriteria("statusDate");

									if ((modifiedDateCriterion != null) && (statusDateCriterion != null)) {
										Disjunction disjunction = RestrictionsFactoryUtil.disjunction();

										disjunction.add(modifiedDateCriterion);
										disjunction.add(statusDateCriterion);

										dynamicQuery.add(disjunction);
									}
								<#else>
									portletDataContext.addDateRangeCriteria(dynamicQuery, "modifiedDate");
								</#if>

								<#if entity.isTypedModel()>
									StagedModelType stagedModelType = exportActionableDynamicQuery.getStagedModelType();

									long referrerClassNameId = stagedModelType.getReferrerClassNameId();

									Property classNameIdProperty = PropertyFactoryUtil.forName("classNameId");

									if ((referrerClassNameId != StagedModelType.REFERRER_CLASS_NAME_ID_ALL) && (referrerClassNameId != StagedModelType.REFERRER_CLASS_NAME_ID_ANY)) {
										dynamicQuery.add(classNameIdProperty.eq(stagedModelType.getReferrerClassNameId()));
									}
									else if (referrerClassNameId == StagedModelType.REFERRER_CLASS_NAME_ID_ANY) {
										dynamicQuery.add(classNameIdProperty.isNotNull());
									}
								</#if>

								<#if entity.isWorkflowEnabled()>
									Property workflowStatusProperty = PropertyFactoryUtil.forName("status");

									if (portletDataContext.isInitialPublication()) {
										dynamicQuery.add(workflowStatusProperty.ne(WorkflowConstants.STATUS_IN_TRASH));
									}
									else {
										StagedModelDataHandler<?> stagedModelDataHandler = StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(${entity.name}.class.getName());

										dynamicQuery.add(workflowStatusProperty.in(stagedModelDataHandler.getExportableStatuses()));
									}
								</#if>
							}

						});

					exportActionableDynamicQuery.setCompanyId(portletDataContext.getCompanyId());

					<#if entity.isStagedGroupedModel()>
						exportActionableDynamicQuery.setGroupId(portletDataContext.getScopeGroupId());
					</#if>

					exportActionableDynamicQuery.setPerformActionMethod(
						new ActionableDynamicQuery.PerformActionMethod<${entity.name}>() {

							@Override
							public void performAction(${entity.name} ${entity.varName}) throws PortalException {
								StagedModelDataHandlerUtil.exportStagedModel(portletDataContext, ${entity.varName});
							}

						});
					<#if entity.isTypedModel()>
						exportActionableDynamicQuery.setStagedModelType(new StagedModelType(PortalUtil.getClassNameId(${entity.name}.class.getName()), StagedModelType.REFERRER_CLASS_NAME_ID_ALL));
					<#else>
						exportActionableDynamicQuery.setStagedModelType(new StagedModelType(PortalUtil.getClassNameId(${entity.name}.class.getName())));
					</#if>

					return exportActionableDynamicQuery;
				}
			</#if>
		</#if>

		/**
		 * @throws PortalException
		 */
		@Override
		public PersistedModel deletePersistedModel(PersistedModel persistedModel) throws PortalException {
			return ${entity.varName}LocalService.delete${entity.name}((${entity.name})persistedModel);
		}

		@Override
		public PersistedModel getPersistedModel(Serializable primaryKeyObj) throws PortalException {
			return ${entity.varName}Persistence.findByPrimaryKey(primaryKeyObj);
		}

		<#if entity.isResourcedModel()>
			@Override
			public List<? extends PersistedModel> getPersistedModel(long resourcePrimKey) throws PortalException {
				return ${entity.varName}Persistence.findByResourcePrimKey(resourcePrimKey);
			}
		</#if>

		<#if entity.hasUuid() && entity.hasEntityColumn("companyId") && !entity.versionEntity??>
			<#if entity.hasEntityColumn("groupId") && !stringUtil.equals(entity.name, "Group")>
				/**
				 * Returns all the ${entity.humanNames} matching the UUID and company.
				 *
				 * @param uuid the UUID of the ${entity.humanNames}
				 * @param companyId the primary key of the company
				 * @return the matching ${entity.humanNames}, or an empty list if no matches were found
				 */
				@Override
				public List<${entity.name}> get${entity.names}ByUuidAndCompanyId(String uuid, long companyId) {
					return ${entity.varName}Persistence.findByUuid_C(uuid, companyId);
				}

				/**
				 * Returns a range of ${entity.humanNames} matching the UUID and company.
				 *
				 * @param uuid the UUID of the ${entity.humanNames}
				 * @param companyId the primary key of the company
				 * @param start the lower bound of the range of ${entity.humanNames}
				 * @param end the upper bound of the range of ${entity.humanNames} (not inclusive)
				 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
				 * @return the range of matching ${entity.humanNames}, or an empty list if no matches were found
				 */
				@Override
				public List<${entity.name}> get${entity.names}ByUuidAndCompanyId(String uuid, long companyId, int start, int end, OrderByComparator<${entity.name}> orderByComparator) {
					return ${entity.varName}Persistence.findByUuid_C(uuid, companyId, start, end, orderByComparator);
				}
			<#else>
				/**
				 * Returns the ${entity.humanName} with the matching UUID and company.
				 *
				 * @param uuid the ${entity.humanName}'s UUID
				 * @param companyId the primary key of the company
				 * @return the matching ${entity.humanName}
				<#list serviceBaseExceptions as exception>
				<#if stringUtil.equals(exception, "PortalException")>
				 * @throws PortalException if a matching ${entity.humanName} could not be found
				<#else>
				 * @throws ${exception}
				</#if>
				</#list>
				 */
				@Override
				public ${entity.name} get${entity.name}ByUuidAndCompanyId(String uuid, long companyId) <#if (serviceBaseExceptions?size gt 0)>throws ${stringUtil.merge(serviceBaseExceptions)} </#if>{
					return ${entity.varName}Persistence.findByUuid_C_First(uuid, companyId, null);
				}
			</#if>
		</#if>

		<#if entity.hasUuid() && entity.hasEntityColumn("groupId") && !stringUtil.equals(entity.name, "Group") && !entity.versionEntity??>
			<#if stringUtil.equals(entity.name, "Layout")>
				/**
				 * Returns the ${entity.humanName} matching the UUID, group, and privacy.
				 *
				 * @param uuid the ${entity.humanName}'s UUID
				 * @param groupId the primary key of the group
				 * @param privateLayout whether the ${entity.humanName} is private to the group
				 * @return the matching ${entity.humanName}
				<#list serviceBaseExceptions as exception>
				<#if stringUtil.equals(exception, "PortalException")>
				 * @throws PortalException if a matching ${entity.humanName} could not be found
				<#else>
				 * @throws ${exception}
				</#if>
				</#list>
				 */
				@Override
				public ${entity.name} get${entity.name}ByUuidAndGroupId(String uuid, long groupId, boolean privateLayout) <#if (serviceBaseExceptions?size gt 0)>throws ${stringUtil.merge(serviceBaseExceptions)} </#if>{
					return ${entity.varName}Persistence.findByUUID_G_P(uuid, groupId, privateLayout);
				}
			<#else>
				/**
				 * Returns the ${entity.humanName} matching the UUID and group.
				 *
				 * @param uuid the ${entity.humanName}'s UUID
				 * @param groupId the primary key of the group
				 * @return the matching ${entity.humanName}
				<#list serviceBaseExceptions as exception>
				<#if stringUtil.equals(exception, "PortalException")>
				 * @throws PortalException if a matching ${entity.humanName} could not be found
				<#else>
				 * @throws ${exception}
				</#if>
				</#list>
				 */
				@Override
				public ${entity.name} get${entity.name}ByUuidAndGroupId(String uuid, long groupId) <#if (serviceBaseExceptions?size gt 0)>throws ${stringUtil.merge(serviceBaseExceptions)} </#if>{
					return ${entity.varName}Persistence.findByUUID_G(uuid, groupId);
				}
			</#if>
		</#if>

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
		public List<${entity.name}> get${entity.names}(int start, int end) {
			return ${entity.varName}Persistence.findAll(start, end);
		}

		/**
		 * Returns the number of ${entity.humanNames}.
		 *
		 * @return the number of ${entity.humanNames}
		 */
		@Override
		public int get${entity.names}Count() {
			return ${entity.varName}Persistence.countAll();
		}

		<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "update" + entity.name, [apiPackagePath + ".model." + entity.name], []) />

		/**
		 * Updates the ${entity.humanName} in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
		 *
		 * @param ${entity.varName} the ${entity.humanName}
		 * @return the ${entity.humanName} that was updated
		<#list serviceBaseExceptions as exception>
		 * @throws ${exception}
		</#list>
		 */
		@Indexable(type = IndexableType.REINDEX)
		@Override
		<#if entity.versionEntity??>
			public ${entity.name} update${entity.name}(${entity.name} draft${entity.name}) throws PortalException {
				return updateDraft(draft${entity.name});
			}
		<#else>
			public ${entity.name} update${entity.name}(${entity.name} ${entity.varName}) <#if (serviceBaseExceptions?size gt 0)>throws ${stringUtil.merge(serviceBaseExceptions)} </#if>{
				return ${entity.varName}Persistence.update(${entity.varName});
			}
		</#if>

		<#list entity.blobEntityColumns as entityColumn>
			<#if entityColumn.lazy>
				@Override
				public ${entity.name}${entityColumn.methodName}BlobModel get${entityColumn.methodName}BlobModel(Serializable primaryKey) {
					Session session = null;

					try {
						session = ${entity.varName}Persistence.openSession();

						return (${apiPackagePath}.model.${entity.name}${entityColumn.methodName}BlobModel)session.get(${entity.name}${entityColumn.methodName}BlobModel.class, primaryKey);
					}
					catch (Exception e) {
						throw ${entity.varName}Persistence.processException(e);
					}
					finally {
						${entity.varName}Persistence.closeSession(session);
					}
				}
			</#if>
		</#list>

		<#list entity.entityColumns as entityColumn>
			<#if entityColumn.isCollection() && entityColumn.isMappingManyToMany()>
				<#assign
					referenceEntity = serviceBuilder.getEntity(entityColumn.entityName)

					serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "add" + referenceEntity.name + entity.name, [referenceEntity.PKClassName, entity.PKClassName], [])
				/>

				/**
				<#list serviceBaseExceptions as exception>
				 * @throws ${exception}
				</#list>
				 */
				@Override
				public void add${referenceEntity.name}${entity.name}(${referenceEntity.PKClassName} ${referenceEntity.PKVarName}, ${entity.PKClassName} ${entity.PKVarName}) <#if (serviceBaseExceptions?size gt 0)>throws ${stringUtil.merge(serviceBaseExceptions)} </#if>{
					${referenceEntity.varName}Persistence.add${entity.name}(${referenceEntity.PKVarName}, ${entity.PKVarName});
				}

				<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "add" + referenceEntity.name + entity.name, [referenceEntity.PKClassName, apiPackagePath + ".model." + entity.name], []) />

				/**
				<#list serviceBaseExceptions as exception>
				 * @throws ${exception}
				</#list>
				 */
				@Override
				public void add${referenceEntity.name}${entity.name}(${referenceEntity.PKClassName} ${referenceEntity.PKVarName}, ${entity.name} ${entity.varName}) <#if (serviceBaseExceptions?size gt 0)>throws ${stringUtil.merge(serviceBaseExceptions)} </#if>{
					${referenceEntity.varName}Persistence.add${entity.name}(${referenceEntity.PKVarName}, ${entity.varName});
				}

				<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "add" + referenceEntity.name + entity.names, [referenceEntity.PKClassName, entity.PKClassName + "[]"], []) />

				/**
				<#list serviceBaseExceptions as exception>
				 * @throws ${exception}
				</#list>
				 */
				@Override
				public void add${referenceEntity.name}${entity.names}(${referenceEntity.PKClassName} ${referenceEntity.PKVarName}, ${entity.PKClassName}[] ${entity.PKVarNames}) <#if (serviceBaseExceptions?size gt 0)>throws ${stringUtil.merge(serviceBaseExceptions)} </#if>{
					${referenceEntity.varName}Persistence.add${entity.names}(${referenceEntity.PKVarName}, ${entity.PKVarNames});
				}

				<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "add" + referenceEntity.name + entity.names, [referenceEntity.PKClassName, "java.util.List<" + entity.name + ">"], []) />

				/**
				<#list serviceBaseExceptions as exception>
				 * @throws ${exception}
				</#list>
				 */
				@Override
				public void add${referenceEntity.name}${entity.names}(${referenceEntity.PKClassName} ${referenceEntity.PKVarName}, List<${entity.name}> ${entity.varNames}) <#if (serviceBaseExceptions?size gt 0)>throws ${stringUtil.merge(serviceBaseExceptions)} </#if>{
					${referenceEntity.varName}Persistence.add${entity.names}(${referenceEntity.PKVarName}, ${entity.varNames});
				}

				<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "clear" + referenceEntity.name + entity.names, [referenceEntity.PKClassName], []) />

				/**
				<#list serviceBaseExceptions as exception>
				 * @throws ${exception}
				</#list>
				 */
				@Override
				public void clear${referenceEntity.name}${entity.names}(${referenceEntity.PKClassName} ${referenceEntity.PKVarName}) <#if (serviceBaseExceptions?size gt 0)>throws ${stringUtil.merge(serviceBaseExceptions)} </#if>{
					${referenceEntity.varName}Persistence.clear${entity.names}(${referenceEntity.PKVarName});
				}

				<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "delete" + referenceEntity.name + entity.name, [referenceEntity.PKClassName, entity.PKClassName], []) />

				/**
				<#list serviceBaseExceptions as exception>
				 * @throws ${exception}
				</#list>
				 */
				@Override
				public void delete${referenceEntity.name}${entity.name}(${referenceEntity.PKClassName} ${referenceEntity.PKVarName}, ${entity.PKClassName} ${entity.PKVarName}) <#if (serviceBaseExceptions?size gt 0)>throws ${stringUtil.merge(serviceBaseExceptions)} </#if>{
					${referenceEntity.varName}Persistence.remove${entity.name}(${referenceEntity.PKVarName}, ${entity.PKVarName});
				}

				<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "delete" + referenceEntity.name + entity.name, [referenceEntity.PKClassName, apiPackagePath + ".model." + entity.name], []) />

				/**
				<#list serviceBaseExceptions as exception>
				 * @throws ${exception}
				</#list>
				 */
				@Override
				public void delete${referenceEntity.name}${entity.name}(${referenceEntity.PKClassName} ${referenceEntity.PKVarName}, ${entity.name} ${entity.varName}) <#if (serviceBaseExceptions?size gt 0)>throws ${stringUtil.merge(serviceBaseExceptions)} </#if>{
					${referenceEntity.varName}Persistence.remove${entity.name}(${referenceEntity.PKVarName}, ${entity.varName});
				}

				<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "delete" + referenceEntity.name + entity.names, [referenceEntity.PKClassName, entity.PKClassName + "[]"], []) />

				/**
				<#list serviceBaseExceptions as exception>
				 * @throws ${exception}
				</#list>
				 */
				@Override
				public void delete${referenceEntity.name}${entity.names}(${referenceEntity.PKClassName} ${referenceEntity.PKVarName}, ${entity.PKClassName}[] ${entity.PKVarNames}) <#if (serviceBaseExceptions?size gt 0)>throws ${stringUtil.merge(serviceBaseExceptions)} </#if>{
					${referenceEntity.varName}Persistence.remove${entity.names}(${referenceEntity.PKVarName}, ${entity.PKVarNames});
				}

				<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "delete" + referenceEntity.name + entity.names, [referenceEntity.PKClassName, "java.util.List<" + entity.name + ">"], []) />

				/**
				<#list serviceBaseExceptions as exception>
				 * @throws ${exception}
				</#list>
				 */
				@Override
				public void delete${referenceEntity.name}${entity.names}(${referenceEntity.PKClassName} ${referenceEntity.PKVarName}, List<${entity.name}> ${entity.varNames}) <#if (serviceBaseExceptions?size gt 0)>throws ${stringUtil.merge(serviceBaseExceptions)} </#if>{
					${referenceEntity.varName}Persistence.remove${entity.names}(${referenceEntity.PKVarName}, ${entity.varNames});
				}

				/**
				 * Returns the ${referenceEntity.PKVarName}s of the ${referenceEntity.humanNames} associated with the ${entity.humanName}.
				 *
				 * @param ${entity.PKVarName} the ${entity.PKVarName} of the ${entity.humanName}
				 * @return long[] the ${referenceEntity.PKVarName}s of ${referenceEntity.humanNames} associated with the ${entity.humanName}
				 */
				@Override
				public long[] get${referenceEntity.name}PrimaryKeys(${entity.PKClassName} ${entity.PKVarName}) {
					return ${entity.varName}Persistence.get${referenceEntity.name}PrimaryKeys(${entity.PKVarName});
				}

				<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "get" + referenceEntity.name + entity.names, [referenceEntity.PKClassName], []) />

				/**
				<#list serviceBaseExceptions as exception>
				 * @throws ${exception}
				</#list>
				 */
				@Override
				public List<${entity.name}> get${referenceEntity.name}${entity.names}(${referenceEntity.PKClassName} ${referenceEntity.PKVarName}) <#if (serviceBaseExceptions?size gt 0)>throws ${stringUtil.merge(serviceBaseExceptions)} </#if>{
					return ${referenceEntity.varName}Persistence.get${entity.names}(${referenceEntity.PKVarName});
				}

				<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "get" + referenceEntity.name + entity.names, [referenceEntity.PKClassName, "int", "int"], []) />

				/**
				<#list serviceBaseExceptions as exception>
				 * @throws ${exception}
				</#list>
				 */
				@Override
				public List<${entity.name}> get${referenceEntity.name}${entity.names}(${referenceEntity.PKClassName} ${referenceEntity.PKVarName}, int start, int end) <#if (serviceBaseExceptions?size gt 0)>throws ${stringUtil.merge(serviceBaseExceptions)} </#if>{
					return ${referenceEntity.varName}Persistence.get${entity.names}(${referenceEntity.PKVarName}, start, end);
				}

				<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "get" + referenceEntity.name + entity.names, [referenceEntity.PKClassName, "int", "int", "com.liferay.portal.kernel.util.OrderByComparator"], []) />

				/**
				<#list serviceBaseExceptions as exception>
				 * @throws ${exception}
				</#list>
				 */
				@Override
				public List<${entity.name}> get${referenceEntity.name}${entity.names}(${referenceEntity.PKClassName} ${referenceEntity.PKVarName}, int start, int end, OrderByComparator<${entity.name}> orderByComparator) <#if (serviceBaseExceptions?size gt 0)>throws ${stringUtil.merge(serviceBaseExceptions)} </#if>{
					return ${referenceEntity.varName}Persistence.get${entity.names}(${referenceEntity.PKVarName}, start, end, orderByComparator);
				}

				<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "get" + referenceEntity.name + entity.names + "Count", [referenceEntity.PKClassName], []) />

				/**
				<#list serviceBaseExceptions as exception>
				 * @throws ${exception}
				</#list>
				 */
				@Override
				public int get${referenceEntity.name}${entity.names}Count(${referenceEntity.PKClassName} ${referenceEntity.PKVarName}) <#if (serviceBaseExceptions?size gt 0)>throws ${stringUtil.merge(serviceBaseExceptions)} </#if>{
					return ${referenceEntity.varName}Persistence.get${entity.names}Size(${referenceEntity.PKVarName});
				}

				<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "has" + referenceEntity.name + entity.name, [referenceEntity.PKClassName, entity.PKClassName], []) />

				/**
				<#list serviceBaseExceptions as exception>
				 * @throws ${exception}
				</#list>
				 */
				@Override
				public boolean has${referenceEntity.name}${entity.name}(${referenceEntity.PKClassName} ${referenceEntity.PKVarName}, ${entity.PKClassName} ${entity.PKVarName}) <#if (serviceBaseExceptions?size gt 0)>throws ${stringUtil.merge(serviceBaseExceptions)} </#if>{
					return ${referenceEntity.varName}Persistence.contains${entity.name}(${referenceEntity.PKVarName}, ${entity.PKVarName});
				}

				<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "has" + referenceEntity.name + entity.names, [referenceEntity.PKClassName], []) />

				/**
				<#list serviceBaseExceptions as exception>
				 * @throws ${exception}
				</#list>
				 */
				@Override
				public boolean has${referenceEntity.name}${entity.names}(${referenceEntity.PKClassName} ${referenceEntity.PKVarName}) <#if (serviceBaseExceptions?size gt 0)>throws ${stringUtil.merge(serviceBaseExceptions)} </#if>{
					return ${referenceEntity.varName}Persistence.contains${entity.names}(${referenceEntity.PKVarName});
				}

				<#assign serviceBaseExceptions = serviceBuilder.getServiceBaseExceptions(methods, "set" + referenceEntity.name + entity.names, [referenceEntity.PKClassName, entity.PKClassName + "[]"], []) />

				/**
				<#list serviceBaseExceptions as exception>
				 * @throws ${exception}
				</#list>
				 */
				@Override
				public void set${referenceEntity.name}${entity.names}(${referenceEntity.PKClassName} ${referenceEntity.PKVarName}, ${entity.PKClassName}[] ${entity.PKVarNames}) <#if (serviceBaseExceptions?size gt 0)>throws ${stringUtil.merge(serviceBaseExceptions)} </#if>{
					${referenceEntity.varName}Persistence.set${entity.names}(${referenceEntity.PKVarName}, ${entity.PKVarNames});
				}
			</#if>
		</#list>
	</#if>

	<#if stringUtil.equals(sessionTypeName, "Local") && (entity.localizedEntity??) && entity.hasPersistence()>
		<#assign
			localizedEntity = entity.localizedEntity
			localizedEntityColumns = entity.localizedEntityColumns
			pkEntityColumn = entity.PKEntityColumns?first
		/>

		@Override
		public ${localizedEntity.name} fetch${localizedEntity.name}(${entity.PKClassName} ${entity.PKVarName}, String languageId) {
			return ${localizedEntity.varName}Persistence.fetchBy${pkEntityColumn.methodName}_LanguageId(${entity.PKVarName}, languageId);
		}

		@Override
		public ${localizedEntity.name} get${localizedEntity.name}(${entity.PKClassName} ${entity.PKVarName}, String languageId) throws PortalException {
			return ${localizedEntity.varName}Persistence.findBy${pkEntityColumn.methodName}_LanguageId(${entity.PKVarName}, languageId);
		}

		@Override
		public List<${localizedEntity.name}> get${localizedEntity.names}(${entity.PKClassName} ${entity.PKVarName}) {
			return ${localizedEntity.varName}Persistence.findBy${pkEntityColumn.methodName}(${entity.PKVarName});
		}

		<#assign entityVarName = entity.varName />

		<#if entity.versionEntity??>
			<#assign entityVarName = "draft" + entity.name />
		</#if>

		@Override
		public ${localizedEntity.name} update${localizedEntity.name}(
			${entity.name} ${entityVarName}, String languageId,
			<#list localizedEntityColumns as entityColumn>
				String ${entityColumn.name}

				<#if entityColumn?has_next>
					,
				</#if>
			</#list>
			) throws PortalException {

			${entityVarName} = ${entity.varName}Persistence.findByPrimaryKey(${entityVarName}.getPrimaryKey());

			<#if entity.versionEntity??>
				if (${entityVarName}.isHead()) {
					throw new IllegalArgumentException("Can only update draft entries " + ${entityVarName}.getPrimaryKey());
				}
			</#if>

			${localizedEntity.name} ${localizedEntity.varName} = ${localizedEntity.varName}Persistence.fetchBy${pkEntityColumn.methodName}_LanguageId(${entityVarName}.get${pkEntityColumn.methodName}(), languageId);

			return _update${localizedEntity.name}(${entityVarName}, ${localizedEntity.varName}, languageId,
				<#list localizedEntityColumns as entityColumn>
					${entityColumn.name}

					<#if entityColumn?has_next>
						,
					</#if>
				</#list>
			);
		}

		@Override
		public List<${localizedEntity.name}> update${localizedEntity.names}(
			${entity.name} ${entityVarName},
			<#list localizedEntityColumns as entityColumn>
				Map<String, String> ${entityColumn.name}Map

				<#if entityColumn?has_next>
					,
				</#if>
			</#list>
			) throws PortalException {

			${entityVarName} = ${entity.varName}Persistence.findByPrimaryKey(${entityVarName}.getPrimaryKey());

			<#if entity.versionEntity??>
				if (${entityVarName}.isHead()) {
					throw new IllegalArgumentException("Can only update draft entries " + ${entityVarName}.getPrimaryKey());
				}
			</#if>

			Map<String, String[]> localizedValuesMap = new HashMap<String, String[]>();

			<#list localizedEntityColumns as entityColumn>
				for (Map.Entry<String, String> entry : ${entityColumn.name}Map.entrySet()) {
					String languageId = entry.getKey();

					String[] localizedValues = localizedValuesMap.get(languageId);

					if (localizedValues == null) {
						localizedValues = new String[${localizedEntityColumns?size}];

						localizedValuesMap.put(languageId, localizedValues);
					}

					localizedValues[${entityColumn?index}] = entry.getValue();
				}
			</#list>

			List<${localizedEntity.name}> ${localizedEntity.varNames} = new ArrayList<${localizedEntity.name}>(localizedValuesMap.size());

			for (${localizedEntity.name} ${localizedEntity.varName} : ${localizedEntity.varName}Persistence.findBy${pkEntityColumn.methodName}(${entityVarName}.get${pkEntityColumn.methodName}())) {
				String[] localizedValues = localizedValuesMap.remove(${localizedEntity.varName}.getLanguageId());

				if (localizedValues == null) {
					${localizedEntity.varName}Persistence.remove(${localizedEntity.varName});
				}
				else {
					<#if entity.versionEntity??>
						<#list entity.entityColumns as entityColumn>
							<#if !stringUtil.equals(entityColumn.name, "headId") && localizedEntity.hasEntityColumn(entityColumn.name) && !stringUtil.equals(entityColumn.name, "mvccVersion") && !stringUtil.equals(entityColumn.name, pkEntityColumn.name)>
								${localizedEntity.varName}.set${entityColumn.methodName}(${entityVarName}.get${entityColumn.methodName}());
							</#if>
						</#list>
					<#else>
						<#list entity.entityColumns as entityColumn>
							<#if localizedEntity.hasEntityColumn(entityColumn.name) && !stringUtil.equals(entityColumn.name, "mvccVersion") && !stringUtil.equals(entityColumn.name, pkEntityColumn.name)>
								${localizedEntity.varName}.set${entityColumn.methodName}(${entityVarName}.get${entityColumn.methodName}());
							</#if>
						</#list>
					</#if>

					<#list localizedEntityColumns as entityColumn>
						${localizedEntity.varName}.set${entityColumn.methodName}(localizedValues[${entityColumn?index}]);
					</#list>

					${localizedEntity.varNames}.add(${localizedEntity.varName}Persistence.update(${localizedEntity.varName}));
				}
			}

			long batchCounter = counterLocalService.increment(${localizedEntity.name}.class.getName(), localizedValuesMap.size()) - localizedValuesMap.size();

			for (Map.Entry<String, String[]> entry : localizedValuesMap.entrySet()) {
				String languageId = entry.getKey();
				String[] localizedValues = entry.getValue();

				${localizedEntity.name} ${localizedEntity.varName} = ${localizedEntity.varName}Persistence.create(++batchCounter);

				<#if entity.versionEntity??>
					${localizedEntity.varName}.setHeadId(${localizedEntity.varName}.getPrimaryKey());

					<#list entity.entityColumns as entityColumn>
						<#if !stringUtil.equals(entityColumn.name, "headId") && localizedEntity.hasEntityColumn(entityColumn.name) && !stringUtil.equals(entityColumn.name, "mvccVersion")>
							${localizedEntity.varName}.set${entityColumn.methodName}(${entityVarName}.get${entityColumn.methodName}());
						</#if>
					</#list>
				<#else>
					<#list entity.entityColumns as entityColumn>
						<#if localizedEntity.hasEntityColumn(entityColumn.name) && !stringUtil.equals(entityColumn.name, "mvccVersion")>
							${localizedEntity.varName}.set${entityColumn.methodName}(${entityVarName}.get${entityColumn.methodName}());
						</#if>
					</#list>
				</#if>

				${localizedEntity.varName}.setLanguageId(languageId);

				<#list localizedEntityColumns as entityColumn>
					${localizedEntity.varName}.set${entityColumn.methodName}(localizedValues[${entityColumn?index}]);
				</#list>

				${localizedEntity.varNames}.add(${localizedEntity.varName}Persistence.update(${localizedEntity.varName}));
			}

			return ${localizedEntity.varNames};
		}

		private ${localizedEntity.name} _update${localizedEntity.name}(
			${entity.name} ${entityVarName}, ${localizedEntity.name} ${localizedEntity.varName}, String languageId,
			<#list localizedEntityColumns as entityColumn>
				String ${entityColumn.name}

				<#if entityColumn?has_next>
					,
				</#if>
			</#list>
			) throws PortalException {

			if (${localizedEntity.varName} == null) {
				long ${localizedEntity.varName}Id = counterLocalService.increment(${localizedEntity.name}.class.getName());

				${localizedEntity.varName} = ${localizedEntity.varName}Persistence.create(${localizedEntity.varName}Id);

				${localizedEntity.varName}.set${pkEntityColumn.methodName}(${entityVarName}.get${pkEntityColumn.methodName}());
				${localizedEntity.varName}.setLanguageId(languageId);
			}

			<#if entity.versionEntity??>
				${localizedEntity.varName}.setHeadId(${localizedEntity.varName}.getPrimaryKey());

				<#list entity.entityColumns as entityColumn>
					<#if localizedEntity.hasEntityColumn(entityColumn.name) && !stringUtil.equals(entityColumn.name, "mvccVersion") && !stringUtil.equals(entityColumn.name, "headId") && !stringUtil.equals(entityColumn.name, pkEntityColumn.name)>
						${localizedEntity.varName}.set${entityColumn.methodName}(${entityVarName}.get${entityColumn.methodName}());
					</#if>
				</#list>
			<#else>

				<#list entity.entityColumns as entityColumn>
					<#if localizedEntity.hasEntityColumn(entityColumn.name) && !stringUtil.equals(entityColumn.name, "mvccVersion") && !stringUtil.equals(entityColumn.name, pkEntityColumn.name)>
						${localizedEntity.varName}.set${entityColumn.methodName}(${entityVarName}.get${entityColumn.methodName}());
					</#if>
				</#list>
			</#if>

			<#list localizedEntityColumns as entityColumn>
				${localizedEntity.varName}.set${entityColumn.methodName}(${entityColumn.name});
			</#list>

			return ${localizedEntity.varName}Persistence.update(${localizedEntity.varName});
		}
	</#if>

	<#list referenceEntities as referenceEntity>
		<#if referenceEntity.hasLocalService()>
			/**
			 * Returns the ${referenceEntity.humanName} local service.
			 *
			 * @return the ${referenceEntity.humanName} local service
			 */

			<#if !classDeprecated && referenceEntity.isDeprecated()>
				@SuppressWarnings("deprecation")
			</#if>

			public ${referenceEntity.apiPackagePath}.service.${referenceEntity.name}LocalService get${referenceEntity.name}LocalService() {
				return ${referenceEntity.varName}LocalService;
			}

			/**
			 * Sets the ${referenceEntity.humanName} local service.
			 *
			 * @param ${referenceEntity.varName}LocalService the ${referenceEntity.humanName} local service
			 */

			<#if !classDeprecated && referenceEntity.isDeprecated()>
				@SuppressWarnings("deprecation")
			</#if>

			public void set${referenceEntity.name}LocalService(${referenceEntity.apiPackagePath}.service.${referenceEntity.name}LocalService ${referenceEntity.varName}LocalService) {
				this.${referenceEntity.varName}LocalService = ${referenceEntity.varName}LocalService;
			}
		</#if>

		<#if !stringUtil.equals(sessionTypeName, "Local") && referenceEntity.hasRemoteService()>
			/**
			 * Returns the ${referenceEntity.humanName} remote service.
			 *
			 * @return the ${referenceEntity.humanName} remote service
			 */

			<#if !classDeprecated && referenceEntity.isDeprecated()>
				@SuppressWarnings("deprecation")
			</#if>

			public ${referenceEntity.apiPackagePath}.service.${referenceEntity.name}Service get${referenceEntity.name}Service() {
				return ${referenceEntity.varName}Service;
			}

			/**
			 * Sets the ${referenceEntity.humanName} remote service.
			 *
			 * @param ${referenceEntity.varName}Service the ${referenceEntity.humanName} remote service
			 */

			<#if !classDeprecated && referenceEntity.isDeprecated()>
				@SuppressWarnings("deprecation")
			</#if>

			public void set${referenceEntity.name}Service(${referenceEntity.apiPackagePath}.service.${referenceEntity.name}Service ${referenceEntity.varName}Service) {
				this.${referenceEntity.varName}Service = ${referenceEntity.varName}Service;
			}
		</#if>

		<#if referenceEntity.hasEntityColumns() && referenceEntity.hasPersistence()>
			/**
			 * Returns the ${referenceEntity.humanName} persistence.
			 *
			 * @return the ${referenceEntity.humanName} persistence
			 */
			public ${referenceEntity.name}Persistence get${referenceEntity.name}Persistence() {
				return ${referenceEntity.varName}Persistence;
			}

			/**
			 * Sets the ${referenceEntity.humanName} persistence.
			 *
			 * @param ${referenceEntity.varName}Persistence the ${referenceEntity.humanName} persistence
			 */
			public void set${referenceEntity.name}Persistence(${referenceEntity.name}Persistence ${referenceEntity.varName}Persistence) {
				this.${referenceEntity.varName}Persistence = ${referenceEntity.varName}Persistence;
			}
		</#if>

		<#if referenceEntity.hasFinderClassName() && (stringUtil.equals(entity.name, "Counter") || !stringUtil.equals(referenceEntity.name, "Counter"))>
			/**
			 * Returns the ${referenceEntity.humanName} finder.
			 *
			 * @return the ${referenceEntity.humanName} finder
			 */
			public ${referenceEntity.name}Finder get${referenceEntity.name}Finder() {
				return ${referenceEntity.varName}Finder;
			}

			/**
			 * Sets the ${referenceEntity.humanName} finder.
			 *
			 * @param ${referenceEntity.varName}Finder the ${referenceEntity.humanName} finder
			 */
			public void set${referenceEntity.name}Finder(${referenceEntity.name}Finder ${referenceEntity.varName}Finder) {
				this.${referenceEntity.varName}Finder = ${referenceEntity.varName}Finder;
			}
		</#if>
	</#list>

	public void afterPropertiesSet() {
		<#if stringUtil.equals(sessionTypeName, "Local") && entity.hasEntityColumns() && entity.hasPersistence()>
			<#if validator.isNotNull(pluginName)>
				PersistedModelLocalServiceRegistryUtil.register("${apiPackagePath}.model.${entity.name}", ${entity.varName}LocalService);
			<#else>
				persistedModelLocalServiceRegistry.register("${apiPackagePath}.model.${entity.name}", ${entity.varName}LocalService);
			</#if>
		</#if>

		<#if stringUtil.equals(sessionTypeName, "Local") && entity.localizedEntity?? && entity.versionEntity?? && entity.hasPersistence()>
			<#assign localizedEntity = entity.localizedEntity />

			registerListener(new ${localizedEntity.name}VersionServiceListener());
		</#if>
	}

	public void destroy() {
		<#if stringUtil.equals(sessionTypeName, "Local") && entity.hasEntityColumns() && entity.hasPersistence()>
			<#if validator.isNotNull(pluginName)>
				PersistedModelLocalServiceRegistryUtil.unregister("${apiPackagePath}.model.${entity.name}");
			<#else>
				persistedModelLocalServiceRegistry.unregister("${apiPackagePath}.model.${entity.name}");
			</#if>
		</#if>
	}

	<#if stringUtil.equals(sessionTypeName, "Local") && entity.versionEntity?? && entity.hasPersistence()>
		<#assign
			versionEntity = entity.versionEntity
			pkEntityMethod = entity.PKEntityColumns?first.methodName
		/>

		@Indexable(type = IndexableType.REINDEX)
		@Override
		public ${entity.name} checkout(${entity.name} published${entity.name}, int version) throws PortalException {
			if (!published${entity.name}.isHead()) {
				throw new IllegalArgumentException("Unable to checkout with unpublished changes " + published${entity.name}.getHeadId());
			}

			${entity.name} draft${entity.name} = ${entity.varName}Persistence.fetchByHeadId(published${entity.name}.getPrimaryKey());

			if (draft${entity.name} != null) {
				throw new IllegalArgumentException("Unable to checkout with unpublished changes " + published${entity.name}.getPrimaryKey());
			}

			${versionEntity.name} ${versionEntity.varName} = getVersion(published${entity.name}, version);

			draft${entity.name} = _createDraft(published${entity.name});

			${versionEntity.varName}.populateVersionedModel(draft${entity.name});

			draft${entity.name} = ${entity.varName}Persistence.update(draft${entity.name});

			for (VersionServiceListener<${entity.name}, ${versionEntity.name}> versionServiceListener : _versionServiceListeners) {
				versionServiceListener.afterCheckout(draft${entity.name}, version);
			}

			return draft${entity.name};
		}

		@Indexable(type = IndexableType.DELETE)
		@Override
		public ${entity.name} delete(${entity.name} published${entity.name}) throws PortalException {
			if (!published${entity.name}.isHead()) {
				throw new IllegalArgumentException("${entity.name} is a draft " + published${entity.name}.getPrimaryKey());
			}

			${entity.name} draft${entity.name} = ${entity.varName}Persistence.fetchByHeadId(published${entity.name}.getPrimaryKey());

			if (draft${entity.name} != null) {
				deleteDraft(draft${entity.name});
			}

			for (${versionEntity.name} ${versionEntity.varName} : getVersions(published${entity.name})) {
				${versionEntity.varName}Persistence.remove(${versionEntity.varName});
			}

			${entity.varName}Persistence.remove(published${entity.name});

			for (VersionServiceListener<${entity.name}, ${versionEntity.name}> versionServiceListener : _versionServiceListeners) {
				versionServiceListener.afterDelete(published${entity.name});
			}

			return published${entity.name};
		}

		@Indexable(type = IndexableType.DELETE)
		@Override
		public ${entity.name} deleteDraft(${entity.name} draft${entity.name})
			throws PortalException {

			if (draft${entity.name}.isHead()) {
				throw new IllegalArgumentException("${entity.name} is not a draft " + draft${entity.name}.getPrimaryKey());
			}

			${entity.varName}Persistence.remove(draft${entity.name});

			for (VersionServiceListener<${entity.name}, ${versionEntity.name}> versionServiceListener : _versionServiceListeners) {
				versionServiceListener.afterDeleteDraft(draft${entity.name});
			}

			return draft${entity.name};
		}

		@Override
		public ${versionEntity.name} deleteVersion(${versionEntity.name} ${versionEntity.varName}) throws PortalException {
			${versionEntity.name} latest${versionEntity.name} = ${versionEntity.varName}Persistence.findBy${pkEntityMethod}_First(${versionEntity.varName}.getVersionedModelId(), null);

			if (latest${versionEntity.name}.getVersion() == ${versionEntity.varName}.getVersion()) {
				throw new IllegalArgumentException("Unable to delete latest version " + ${versionEntity.varName}.getVersion());
			}

			${versionEntity.varName} = ${versionEntity.varName}Persistence.remove(${versionEntity.varName});

			for (VersionServiceListener<${entity.name}, ${versionEntity.name}> versionServiceListener : _versionServiceListeners) {
				versionServiceListener.afterDeleteVersion(${versionEntity.varName});
			}

			return ${versionEntity.varName};
		}

		@Override
		public ${entity.name} fetchDraft(${entity.name} ${entity.varName}) {
			if (${entity.varName}.isHead()) {
				return ${entity.varName}Persistence.fetchByHeadId(${entity.varName}.getPrimaryKey());
			}

			return ${entity.varName};
		}

		@Override
		public ${entity.name} fetchDraft(long primaryKey) {
			return ${entity.varName}Persistence.fetchByHeadId(primaryKey);
		}

		@Override
		public ${versionEntity.name} fetchLatestVersion(${entity.name} ${entity.varName}) {
			long primaryKey = ${entity.varName}.getHeadId();

			if (${entity.varName}.isHead()) {
				primaryKey = ${entity.varName}.getPrimaryKey();
			}

			return ${versionEntity.varName}Persistence.fetchBy${pkEntityMethod}_First(primaryKey, null);
		}

		@Override
		public ${entity.name} fetchPublished(${entity.name} ${entity.varName}) {
			if (${entity.varName}.isHead()) {
				return ${entity.varName};
			}

			if (${entity.varName}.getHeadId() == ${entity.varName}.getPrimaryKey()) {
				return null;
			}

			return ${entity.varName}Persistence.fetchByPrimaryKey(${entity.varName}.getHeadId());
		}

		@Override
		public ${entity.name} fetchPublished(long primaryKey) {
			${entity.name} ${entity.varName} = ${entity.varName}Persistence.fetchByPrimaryKey(primaryKey);

			if ((${entity.varName} == null) || (${entity.varName}.getHeadId() == ${entity.varName}.getPrimaryKey())) {
				return null;
			}

			return ${entity.varName};
		}

		@Override
		public ${entity.name} getDraft(${entity.name} ${entity.varName}) throws PortalException {
			if (!${entity.varName}.isHead()) {
				return ${entity.varName};
			}

			${entity.name} draft${entity.name} = ${entity.varName}Persistence.fetchByHeadId(${entity.varName}.getPrimaryKey());

			if (draft${entity.name} == null) {
				draft${entity.name} = ${entity.varName}LocalService.updateDraft(_createDraft(${entity.varName}));
			}

			return draft${entity.name};
		}

		@Override
		public ${entity.name} getDraft(long primaryKey) throws PortalException {
			${entity.name} draft${entity.name} = ${entity.varName}Persistence.fetchByHeadId(primaryKey);

			if (draft${entity.name} == null) {
				${entity.name} ${entity.varName} = ${entity.varName}Persistence.findByPrimaryKey(primaryKey);

				draft${entity.name} = ${entity.varName}LocalService.updateDraft(_createDraft(${entity.varName}));
			}

			return draft${entity.name};
		}

		@Override
		public ${versionEntity.name} getVersion(${entity.name} ${entity.varName}, int version) throws PortalException {
			long primaryKey = ${entity.varName}.getHeadId();

			if (${entity.varName}.isHead()) {
				primaryKey = ${entity.varName}.getPrimaryKey();
			}

			return ${versionEntity.varName}Persistence.findBy${pkEntityMethod}_Version(primaryKey, version);
		}

		@Override
		public List<${versionEntity.name}> getVersions(${entity.name} ${entity.varName}) {
			long primaryKey = ${entity.varName}.getPrimaryKey();

			if (!${entity.varName}.isHead()) {
				if (${entity.varName}.getHeadId() == ${entity.varName}.getPrimaryKey()) {
					return Collections.emptyList();
				}

				primaryKey = ${entity.varName}.getHeadId();
			}

			return ${versionEntity.varName}Persistence.findBy${pkEntityMethod}(primaryKey);
		}

		@Indexable(type = IndexableType.REINDEX)
		@Override
		public ${entity.name} publishDraft(${entity.name} draft${entity.name}) throws PortalException {
			if (draft${entity.name}.isHead()) {
				throw new IllegalArgumentException("Can only publish drafts " + draft${entity.name}.getPrimaryKey());
			}

			${entity.name} head${entity.name} = null;

			int version = 1;

			if (draft${entity.name}.getHeadId() == draft${entity.name}.getPrimaryKey()) {
				head${entity.name} = create();

				draft${entity.name}.setHeadId(head${entity.name}.getPrimaryKey());
			}
			else {
				head${entity.name} = ${entity.varName}Persistence.findByPrimaryKey(draft${entity.name}.getHeadId());

				${versionEntity.name} latest${versionEntity.name} = ${versionEntity.varName}Persistence.findBy${pkEntityMethod}_First(draft${entity.name}.getHeadId(), null);

				version = latest${versionEntity.name}.getVersion() + 1;
			}

			${versionEntity.name} ${versionEntity.varName} = ${versionEntity.varName}Persistence.create(counterLocalService.increment(${versionEntity.name}.class.getName()));

			${versionEntity.varName}.setVersion(version);
			${versionEntity.varName}.setVersionedModelId(head${entity.name}.getPrimaryKey());

			draft${entity.name}.populateVersionModel(${versionEntity.varName});

			${versionEntity.varName}Persistence.update(${versionEntity.varName});

			${versionEntity.varName}.populateVersionedModel(head${entity.name});

			head${entity.name}.setHeadId(-head${entity.name}.getPrimaryKey());

			head${entity.name} = ${entity.varName}Persistence.update(head${entity.name});

			for (VersionServiceListener<${entity.name}, ${versionEntity.name}> versionServiceListener : _versionServiceListeners) {
				versionServiceListener.afterPublishDraft(draft${entity.name}, version);
			}

			deleteDraft(draft${entity.name});

			return head${entity.name};
		}

		@Override
		public void registerListener(VersionServiceListener<${entity.name}, ${versionEntity.name}> versionServiceListener) {
			_versionServiceListeners.add(versionServiceListener);
		}

		@Override
		public void unregisterListener(VersionServiceListener<${entity.name}, ${versionEntity.name}> versionServiceListener) {
			_versionServiceListeners.remove(versionServiceListener);
		}

		@Indexable(type = IndexableType.REINDEX)
		@Override
		public ${entity.name} updateDraft(${entity.name} draft${entity.name}) throws PortalException {
			if (draft${entity.name}.isHead()) {
				throw new IllegalArgumentException("Can only update draft entries " + draft${entity.name}.getPrimaryKey());
			}

			${entity.name} previous${entity.name} = ${entity.varName}Persistence.fetchByPrimaryKey(draft${entity.name}.getPrimaryKey());

			draft${entity.name} = ${entity.varName}Persistence.update(draft${entity.name});

			if (previous${entity.name} == null) {
				for (VersionServiceListener<${entity.name}, ${versionEntity.name}> versionServiceListener : _versionServiceListeners) {
					versionServiceListener.afterCreateDraft(draft${entity.name});
				}
			}
			else {
				for (VersionServiceListener<${entity.name}, ${versionEntity.name}> versionServiceListener : _versionServiceListeners) {
					versionServiceListener.afterUpdateDraft(draft${entity.name});
				}
			}

			return draft${entity.name};
		}

		private ${entity.name} _createDraft(${entity.name} published${entity.name}) throws PortalException {
			${entity.name} draft${entity.name} = create();

			<#list entity.entityColumns as entityColumn>
				<#if stringUtil.equals(entityColumn.methodName, "HeadId")>
					draft${entity.name}.setHeadId(published${entity.name}.getPrimaryKey());
				<#elseif !entityColumn.isPrimary() && !stringUtil.equals(entityColumn.methodName, "MvccVersion")>
					draft${entity.name}.set${entityColumn.methodName}(published${entity.name}.get${entityColumn.methodName}());
				</#if>
			</#list>

			draft${entity.name}.resetOriginalValues();

			return draft${entity.name};
		}

		private final Set<VersionServiceListener<${entity.name}, ${versionEntity.name}>> _versionServiceListeners = Collections.newSetFromMap(new ConcurrentHashMap<VersionServiceListener<${entity.name}, ${versionEntity.name}>, Boolean>());

	</#if>

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		<#if stringUtil.equals(sessionTypeName, "Local")>
			return ${entity.name}LocalService.class.getName();
		<#else>
			return ${entity.name}Service.class.getName();
		</#if>
	}

	<#if entity.hasEntityColumns()>
		protected Class<?> getModelClass() {
			return ${entity.name}.class;
		}

		protected String getModelClassName() {
			return ${entity.name}.class.getName();
		}
	</#if>

	<#if entity.hasPersistence()>
		/**
		 * Performs a SQL query.
		 *
		 * @param sql the sql query
		 */
		protected void runSQL(String sql) {
			try {
				<#if entity.hasEntityColumns()>
					DataSource dataSource = ${entity.varName}Persistence.getDataSource();
				<#else>
					DataSource dataSource = InfrastructureUtil.getDataSource();
				</#if>

				DB db = DBManagerUtil.getDB();

				sql = db.buildSQL(sql);
				sql = PortalUtil.transformSQL(sql);

				SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource, sql);

				sqlUpdate.update();
			}
			catch (Exception e) {
				throw new SystemException(e);
			}
		}
	</#if>

	<#list referenceEntities as referenceEntity>
		<#if referenceEntity.hasLocalService()>
			<#if osgiModule && (referenceEntity.apiPackagePath != apiPackagePath)>
				@ServiceReference(type = ${referenceEntity.apiPackagePath}.service.${referenceEntity.name}LocalService.class)
			<#else>
				@BeanReference(type = ${referenceEntity.apiPackagePath}.service.${referenceEntity.name}LocalService.class)
			</#if>

			<#if !classDeprecated && referenceEntity.isDeprecated()>
				@SuppressWarnings("deprecation")
			</#if>

			protected ${referenceEntity.apiPackagePath}.service.${referenceEntity.name}LocalService ${referenceEntity.varName}LocalService;
		</#if>

		<#if !stringUtil.equals(sessionTypeName, "Local") && referenceEntity.hasRemoteService()>
			<#if osgiModule && (referenceEntity.apiPackagePath != apiPackagePath)>
				@ServiceReference(type = ${referenceEntity.apiPackagePath}.service.${referenceEntity.name}Service.class)
			<#else>
				@BeanReference(type = ${referenceEntity.apiPackagePath}.service.${referenceEntity.name}Service.class)
			</#if>

			<#if !classDeprecated && referenceEntity.isDeprecated()>
				@SuppressWarnings("deprecation")
			</#if>

			protected ${referenceEntity.apiPackagePath}.service.${referenceEntity.name}Service ${referenceEntity.varName}Service;
		</#if>

		<#if referenceEntity.hasEntityColumns() && referenceEntity.hasPersistence()>
			<#if osgiModule && (referenceEntity.apiPackagePath != apiPackagePath)>
				@ServiceReference(type = ${referenceEntity.name}Persistence.class)
			<#else>
				@BeanReference(type = ${referenceEntity.name}Persistence.class)
			</#if>

			protected ${referenceEntity.name}Persistence ${referenceEntity.varName}Persistence;
		</#if>

		<#if referenceEntity.hasFinderClassName() && (stringUtil.equals(entity.name, "Counter") || !stringUtil.equals(referenceEntity.name, "Counter"))>
			<#if osgiModule && (referenceEntity.apiPackagePath != apiPackagePath)>
				@ServiceReference(type = ${referenceEntity.name}Finder.class)
			<#else>
				@BeanReference(type = ${referenceEntity.name}Finder.class)
			</#if>

			protected ${referenceEntity.name}Finder ${referenceEntity.varName}Finder;
		</#if>
	</#list>

	<#if stringUtil.equals(sessionTypeName, "Local") && entity.hasEntityColumns() && entity.hasPersistence()>
		<#if validator.isNull(pluginName)>
			<#if osgiModule>
				@ServiceReference(type = PersistedModelLocalServiceRegistry.class)
			<#else>
				@BeanReference(type = PersistedModelLocalServiceRegistry.class)
			</#if>

			protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
		</#if>
	</#if>

	<#if stringUtil.equals(sessionTypeName, "Local") && entity.localizedEntity?? && entity.versionEntity?? && entity.hasPersistence()>
		<#assign
			localizedEntity = entity.localizedEntity
			versionEntity = entity.versionEntity
			localizedVersionEntity = localizedEntity.versionEntity
			pkEntityMethod = entity.PKEntityColumns?first.methodName
		/>

		private class ${localizedEntity.name}VersionServiceListener implements VersionServiceListener<${entity.name}, ${versionEntity.name}> {

			@Override
			public void afterCheckout(${entity.name} draft${entity.name}, int version) throws PortalException {
				Map<String, ${localizedEntity.name}> published${localizedEntity.name}Map = new HashMap<String, ${localizedEntity.name}>();

				for (${localizedEntity.name} published${localizedEntity.name} : ${localizedEntity.varName}Persistence.findBy${pkEntityMethod}(draft${entity.name}.getHeadId())) {
					published${localizedEntity.name}Map.put(published${localizedEntity.name}.getLanguageId(), published${localizedEntity.name});
				}

				List<${localizedVersionEntity.name}> ${localizedVersionEntity.varNames} = ${localizedVersionEntity.varName}Persistence.findBy${pkEntityMethod}_Version(draft${entity.name}.getHeadId(), version);

				long ${localizedVersionEntity.varName}BatchCounter = counterLocalService.increment(${localizedVersionEntity.name}.class.getName(), ${localizedVersionEntity.varNames}.size()) - ${localizedVersionEntity.varNames}.size();

				for (${localizedVersionEntity.name} ${localizedVersionEntity.varName} : ${localizedVersionEntity.varNames}) {
					${localizedEntity.name} draft${localizedEntity.name} = ${localizedEntity.varName}Persistence.create(++${localizedVersionEntity.varName}BatchCounter);

					long headId = draft${localizedEntity.name}.getPrimaryKey();

					${localizedEntity.name} published${localizedEntity.name} = published${localizedEntity.name}Map.get(${localizedVersionEntity.varName}.getLanguageId());

					if (published${localizedEntity.name} != null) {
						headId = published${localizedEntity.name}.getPrimaryKey();
					}

					draft${localizedEntity.name}.setHeadId(headId);

					draft${localizedEntity.name}.set${pkEntityMethod}(draft${entity.name}.getPrimaryKey());
					draft${localizedEntity.name}.setLanguageId(${localizedVersionEntity.varName}.getLanguageId());

					<#list localizedEntityColumns as entityColumn>
						draft${localizedEntity.name}.set${entityColumn.methodName}(${localizedVersionEntity.varName}.get${entityColumn.methodName}());
					</#list>

					${localizedEntity.varName}Persistence.update(draft${localizedEntity.name});
				}
			}

			@Override
			public void afterCreateDraft(${entity.name} draft${entity.name}) throws PortalException {
				if (draft${entity.name}.getHeadId() == draft${entity.name}.getPrimaryKey()) {
					return;
				}

				for (${localizedEntity.name} published${localizedEntity.name} : ${localizedEntity.varName}Persistence.findBy${pkEntityMethod}(draft${entity.name}.getHeadId())) {
					_update${localizedEntity.name}(
						draft${entity.name}, null, published${localizedEntity.name}.getLanguageId(),
							<#list localizedEntityColumns as entityColumn>
								published${localizedEntity.name}.get${entityColumn.methodName}()

								<#if entityColumn?has_next>
									,
								</#if>
							</#list>
						);
				}
			}

			@Override
			public void afterDelete(${entity.name} published${entity.name}) throws PortalException {
				${localizedEntity.varName}Persistence.removeBy${pkEntityMethod}(published${entity.name}.getPrimaryKey());
				${localizedVersionEntity.varName}Persistence.removeBy${pkEntityMethod}(published${entity.name}.getPrimaryKey());
			}

			@Override
			public void afterDeleteDraft(${entity.name} draft${entity.name}) throws PortalException {
				${localizedEntity.varName}Persistence.removeBy${pkEntityMethod}(draft${entity.name}.getPrimaryKey());
			}

			@Override
			public void afterDeleteVersion(${versionEntity.name} ${versionEntity.varName}) throws PortalException {
				${localizedVersionEntity.varName}Persistence.removeBy${pkEntityMethod}_Version(${versionEntity.varName}.getVersionedModelId(), ${versionEntity.varName}.getVersion());
			}

			@Override
			public void afterPublishDraft(${entity.name} draft${entity.name}, int version) throws PortalException {
				Map<String, ${localizedEntity.name}> draft${localizedEntity.name}Map = new HashMap<String, ${localizedEntity.name}>();

				for (${localizedEntity.name} draft${localizedEntity.name} : ${localizedEntity.varName}Persistence.findBy${pkEntityMethod}(draft${entity.name}.getPrimaryKey())) {
					draft${localizedEntity.name}Map.put(draft${localizedEntity.name}.getLanguageId(), draft${localizedEntity.name});
				}

				long ${localizedVersionEntity.varName}BatchCounter = counterLocalService.increment(${localizedVersionEntity.name}.class.getName(), draft${localizedEntity.name}Map.size()) - draft${localizedEntity.name}Map.size();

				for (${localizedEntity.name} published${localizedEntity.name} : ${localizedEntity.varName}Persistence.findBy${pkEntityMethod}(draft${entity.name}.getHeadId())) {
					${localizedEntity.name} draft${localizedEntity.name} = draft${localizedEntity.name}Map.remove(published${localizedEntity.name}.getLanguageId());

					if (draft${localizedEntity.name} == null) {
						${localizedEntity.varName}Persistence.remove(published${localizedEntity.name});
					}
					else {
						published${localizedEntity.name}.setHeadId(-published${localizedEntity.name}.getPrimaryKey());

						<#list localizedEntityColumns as entityColumn>
							published${localizedEntity.name}.set${entityColumn.methodName}(draft${localizedEntity.name}.get${entityColumn.methodName}());
						</#list>

						${localizedEntity.varName}Persistence.update(published${localizedEntity.name});

						_publish${localizedVersionEntity.name}(published${localizedEntity.name}, ++${localizedVersionEntity.varName}BatchCounter, version);
					}
				}

				long ${localizedEntity.varName}BatchCounter = counterLocalService.increment(${localizedEntity.name}.class.getName(), draft${localizedEntity.name}Map.size()) - draft${localizedEntity.name}Map.size();

				for (${localizedEntity.name} draft${localizedEntity.name} : draft${localizedEntity.name}Map.values()) {
					${localizedEntity.name} ${localizedEntity.varName} = ${localizedEntity.varName}Persistence.create(++${localizedEntity.varName}BatchCounter);

					${localizedEntity.varName}.setHeadId(${localizedEntity.varName}.getPrimaryKey());
					${localizedEntity.varName}.set${pkEntityMethod}(draft${entity.name}.getHeadId());
					${localizedEntity.varName}.setLanguageId(draft${localizedEntity.name}.getLanguageId());

					<#list localizedEntityColumns as entityColumn>
						${localizedEntity.varName}.set${entityColumn.methodName}(draft${localizedEntity.name}.get${entityColumn.methodName}());
					</#list>

					${localizedEntity.varName}Persistence.update(${localizedEntity.varName});

					_publish${localizedVersionEntity.name}(${localizedEntity.varName}, ++${localizedVersionEntity.varName}BatchCounter, version);
				}
			}

			@Override
			public void afterUpdateDraft(${entity.name} draft${entity.name}) {
			}

			private void _publish${localizedVersionEntity.name}(${localizedEntity.name} ${localizedEntity.varName}, long primaryKey, int version) {
				${localizedVersionEntity.name} ${localizedVersionEntity.varName} = ${localizedVersionEntity.varName}Persistence.create(primaryKey);

				${localizedVersionEntity.varName}.setVersion(version);
				${localizedVersionEntity.varName}.setVersionedModelId(${localizedEntity.varName}.getPrimaryKey());

				${localizedEntity.varName}.populateVersionModel(${localizedVersionEntity.varName});

				${localizedVersionEntity.varName}Persistence.update(${localizedVersionEntity.varName});
			}

		}
	</#if>
}