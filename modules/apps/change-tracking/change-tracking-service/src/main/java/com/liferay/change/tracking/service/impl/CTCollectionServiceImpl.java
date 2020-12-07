/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.change.tracking.service.impl;

import com.liferay.change.tracking.closure.CTClosure;
import com.liferay.change.tracking.closure.CTClosureFactory;
import com.liferay.change.tracking.constants.CTActionKeys;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.exception.CTEnclosureException;
import com.liferay.change.tracking.internal.CTEnclosureUtil;
import com.liferay.change.tracking.internal.CTServiceRegistry;
import com.liferay.change.tracking.model.CTAutoResolutionInfo;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTCollectionTable;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTProcessLocalService;
import com.liferay.change.tracking.service.base.CTCollectionServiceBaseImpl;
import com.liferay.change.tracking.service.persistence.CTAutoResolutionInfoPersistence;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.dao.jdbc.CurrentConnectionUtil;
import com.liferay.portal.kernel.dao.orm.WildcardMode;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.InlineSQLHelper;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.model.uid.UIDFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(
	property = {
		"json.web.service.context.name=ct",
		"json.web.service.context.path=CTCollection"
	},
	service = AopService.class
)
public class CTCollectionServiceImpl extends CTCollectionServiceBaseImpl {

	@Override
	public CTCollection addCTCollection(
			long companyId, long userId, String name, String description)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null, CTActionKeys.ADD_PUBLICATION);

		return ctCollectionLocalService.addCTCollection(
			companyId, userId, name, description);
	}

	@Override
	public void deleteCTAutoResolutionInfo(long ctAutoResolutionInfoId)
		throws PortalException {

		CTAutoResolutionInfo ctAutoResolutionInfo =
			_ctAutoResolutionInfoPersistence.fetchByPrimaryKey(
				ctAutoResolutionInfoId);

		if (ctAutoResolutionInfo == null) {
			return;
		}

		_ctCollectionModelResourcePermission.check(
			getPermissionChecker(), ctAutoResolutionInfo.getCtCollectionId(),
			ActionKeys.UPDATE);

		ctCollectionLocalService.deleteCTAutoResolutionInfo(
			ctAutoResolutionInfoId);
	}

	@Override
	public CTCollection deleteCTCollection(CTCollection ctCollection)
		throws PortalException {

		_ctCollectionModelResourcePermission.check(
			getPermissionChecker(), ctCollection, ActionKeys.DELETE);

		return ctCollectionLocalService.deleteCTCollection(ctCollection);
	}

	@Override
	public void discardCTEntries(
			long ctCollectionId, long modelClassNameId, long modelClassPK)
		throws PortalException {

		CTCollection ctCollection = ctCollectionPersistence.findByPrimaryKey(
			ctCollectionId);

		_ctCollectionModelResourcePermission.check(
			getPermissionChecker(), ctCollection, ActionKeys.UPDATE);

		if ((ctCollection.getStatus() != WorkflowConstants.STATUS_DRAFT) &&
			(ctCollection.getStatus() != WorkflowConstants.STATUS_PENDING)) {

			throw new PortalException(
				"Change tracking collection " + ctCollection + " is read only");
		}

		List<CTEntry> discardCTEntries =
			ctCollectionLocalService.getDiscardCTEntries(
				ctCollectionId, modelClassNameId, modelClassPK);

		Map<Long, List<CTEntry>> ctEntryMap = new HashMap<>();

		for (CTEntry ctEntry : discardCTEntries) {
			List<CTEntry> ctEntries = ctEntryMap.computeIfAbsent(
				ctEntry.getModelClassNameId(), key -> new ArrayList<>());

			ctEntries.add(ctEntry);
		}

		for (Map.Entry<Long, List<CTEntry>> entry : ctEntryMap.entrySet()) {
			_discardCTEntries(ctCollection, entry.getKey(), entry.getValue());
		}
	}

	@Override
	public void discardCTEntry(
			long ctCollectionId, long modelClassNameId, long modelClassPK)
		throws PortalException {

		CTCollection ctCollection = ctCollectionPersistence.findByPrimaryKey(
			ctCollectionId);

		_ctCollectionModelResourcePermission.check(
			getPermissionChecker(), ctCollection, ActionKeys.UPDATE);

		if ((ctCollection.getStatus() != WorkflowConstants.STATUS_DRAFT) &&
			(ctCollection.getStatus() != WorkflowConstants.STATUS_PENDING)) {

			throw new PortalException(
				"Change tracking collection " + ctCollection + " is read only");
		}

		CTClosure ctClosure = _ctClosureFactory.create(
			ctCollection.getCtCollectionId());

		Map<Long, Set<Long>> enclosureMap = CTEnclosureUtil.getEnclosureMap(
			ctClosure, modelClassNameId, modelClassPK);

		for (Map.Entry<Long, Long> parentEntry :
				CTEnclosureUtil.getEnclosureParentEntries(
					ctClosure, enclosureMap)) {

			long classNameId = parentEntry.getKey();
			long classPK = parentEntry.getValue();

			int count = ctEntryPersistence.countByC_MCNI_MCPK(
				ctCollectionId, classNameId, classPK);

			if (count > 0) {
				throw new CTEnclosureException(
					StringBundler.concat(
						"{classNameId=", classNameId, ", classPK=", classPK,
						", ctCollectionId=", ctCollectionId, "}"));
			}
		}

		for (Map.Entry<Long, Set<Long>> enclosureEntry :
				enclosureMap.entrySet()) {

			long classNameId = enclosureEntry.getKey();

			Set<Long> classPKs = enclosureEntry.getValue();

			List<CTEntry> ctEntries = new ArrayList<>(classPKs.size());

			for (long classPK : classPKs) {
				CTEntry ctEntry = ctEntryPersistence.fetchByC_MCNI_MCPK(
					ctCollectionId, classNameId, classPK);

				if (ctEntry != null) {
					ctEntries.add(ctEntry);
				}
			}

			if (ctEntries.isEmpty()) {
				continue;
			}

			_discardCTEntries(ctCollection, classNameId, ctEntries);
		}
	}

	@Override
	public List<CTCollection> getCTCollections(
		long companyId, int[] statuses, int start, int end,
		OrderByComparator<CTCollection> orderByComparator) {

		if (statuses == null) {
			return ctCollectionPersistence.filterFindByCompanyId(
				companyId, start, end, orderByComparator);
		}

		return ctCollectionPersistence.filterFindByC_S(
			companyId, statuses, start, end, orderByComparator);
	}

	@Override
	public List<CTCollection> getCTCollections(
		long companyId, int[] statuses, String keywords, int start, int end,
		OrderByComparator<CTCollection> orderByComparator) {

		DSLQuery dslQuery = DSLQueryFactoryUtil.select(
			CTCollectionTable.INSTANCE
		).from(
			CTCollectionTable.INSTANCE
		).where(
			_getPredicate(companyId, statuses, keywords)
		).orderBy(
			CTCollectionTable.INSTANCE, orderByComparator
		).limit(
			start, end
		);

		return ctCollectionPersistence.dslQuery(dslQuery);
	}

	@Override
	public int getCTCollectionsCount(
		long companyId, int[] statuses, String keywords) {

		DSLQuery dslQuery = DSLQueryFactoryUtil.count(
		).from(
			CTCollectionTable.INSTANCE
		).where(
			_getPredicate(companyId, statuses, keywords)
		);

		Long count = ctCollectionPersistence.dslQuery(dslQuery);

		return count.intValue();
	}

	@Override
	public void publishCTCollection(long userId, long ctCollectionId)
		throws PortalException {

		_ctCollectionModelResourcePermission.check(
			getPermissionChecker(), ctCollectionId, CTActionKeys.PUBLISH);

		_ctProcessLocalService.addCTProcess(userId, ctCollectionId);
	}

	@Override
	public CTCollection undoCTCollection(
			long ctCollectionId, long userId, String name, String description)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		_ctCollectionModelResourcePermission.check(
			permissionChecker, ctCollectionId, ActionKeys.VIEW);

		_portletResourcePermission.check(
			permissionChecker, null, CTActionKeys.ADD_PUBLICATION);

		return ctCollectionLocalService.undoCTCollection(
			ctCollectionId, userId, name, description);
	}

	@Override
	public CTCollection updateCTCollection(
			long userId, long ctCollectionId, String name, String description)
		throws PortalException {

		_ctCollectionModelResourcePermission.check(
			getPermissionChecker(), ctCollectionId, ActionKeys.UPDATE);

		return ctCollectionLocalService.updateCTCollection(
			userId, ctCollectionId, name, description);
	}

	private void _discardCTEntries(
		CTCollection ctCollection, long classNameId, List<CTEntry> ctEntries) {

		CTService<?> ctService = _ctServiceRegistry.getCTService(classNameId);

		ctService.updateWithUnsafeFunction(
			ctPersistence -> {
				Set<String> primaryKeyNames = ctPersistence.getCTColumnNames(
					CTColumnResolutionType.PK);

				if (primaryKeyNames.size() != 1) {
					throw new IllegalArgumentException(
						StringBundler.concat(
							"{primaryKeyNames=", primaryKeyNames,
							", tableName=", ctPersistence.getTableName(), "}"));
				}

				Iterator<String> iterator = primaryKeyNames.iterator();

				String primaryKeyName = iterator.next();

				StringBundler sb = new StringBundler(
					(2 * ctEntries.size()) + 7);

				sb.append("delete from ");
				sb.append(ctPersistence.getTableName());
				sb.append(" where ctCollectionId = ");
				sb.append(ctCollection.getCtCollectionId());
				sb.append(" and ");
				sb.append(primaryKeyName);
				sb.append(" in (");

				for (CTEntry ctEntry : ctEntries) {
					sb.append(ctEntry.getModelClassPK());
					sb.append(", ");
				}

				sb.setStringAt(")", sb.index() - 1);

				Connection connection = CurrentConnectionUtil.getConnection(
					ctPersistence.getDataSource());

				try (PreparedStatement preparedStatement =
						connection.prepareStatement(sb.toString())) {

					preparedStatement.executeUpdate();
				}
				catch (Exception exception) {
					throw new SystemException(exception);
				}

				for (String mappingTableName :
						ctPersistence.getMappingTableNames()) {

					sb.setStringAt(mappingTableName, 1);

					try (PreparedStatement preparedStatement =
							connection.prepareStatement(sb.toString())) {

						preparedStatement.executeUpdate();
					}
					catch (Exception exception) {
						throw new SystemException(exception);
					}
				}

				return null;
			});

		List<Long> modelClassPKs = new ArrayList<>(ctEntries.size());

		for (CTEntry ctEntry : ctEntries) {
			modelClassPKs.add(ctEntry.getModelClassPK());

			ctEntryPersistence.remove(ctEntry);
		}

		for (CTAutoResolutionInfo ctAutoResolutionInfo :
				_ctAutoResolutionInfoPersistence.findByC_MCNI_SMCPK(
					ctCollection.getCtCollectionId(), classNameId,
					ArrayUtil.toLongArray(modelClassPKs))) {

			_ctAutoResolutionInfoPersistence.remove(ctAutoResolutionInfo);
		}

		Indexer<?> indexer = _indexerRegistry.getIndexer(
			ctService.getModelClass());

		if (indexer != null) {
			TransactionCommitCallbackUtil.registerCallback(
				() -> {
					List<String> uids = new ArrayList<>(ctEntries.size());

					for (CTEntry ctEntry : ctEntries) {
						if (ctEntry.getChangeType() !=
								CTConstants.CT_CHANGE_TYPE_DELETION) {

							uids.add(
								_uidFactory.getUID(
									indexer.getClassName(),
									ctEntry.getModelClassPK(),
									ctEntry.getCtCollectionId()));
						}
					}

					_indexWriterHelper.deleteDocuments(
						indexer.getSearchEngineId(),
						ctCollection.getCompanyId(), uids,
						indexer.isCommitImmediately());

					return null;
				});
		}
	}

	private Predicate _getPredicate(
		long companyId, int[] statuses, String keywords) {

		Predicate predicate = CTCollectionTable.INSTANCE.companyId.eq(
			companyId);

		if (!ArrayUtil.isEmpty(statuses)) {
			predicate = predicate.and(
				CTCollectionTable.INSTANCE.status.in(
					ArrayUtil.toArray(statuses)));
		}

		Predicate keywordsPredicate = null;

		for (String keyword :
				_customSQL.keywords(keywords, true, WildcardMode.SURROUND)) {

			if (keyword == null) {
				continue;
			}

			Predicate keywordPredicate = DSLFunctionFactoryUtil.lower(
				CTCollectionTable.INSTANCE.name
			).like(
				keyword
			).or(
				DSLFunctionFactoryUtil.lower(
					CTCollectionTable.INSTANCE.description
				).like(
					keyword
				)
			);

			if (keywordsPredicate == null) {
				keywordsPredicate = keywordPredicate;
			}
			else {
				keywordsPredicate = keywordsPredicate.or(keywordPredicate);
			}
		}

		if (keywordsPredicate != null) {
			predicate = predicate.and(keywordsPredicate.withParentheses());
		}

		return predicate.and(
			_inlineSQLHelper.getPermissionWherePredicate(
				CTCollection.class, CTCollectionTable.INSTANCE.ctCollectionId));
	}

	@Reference
	private CTAutoResolutionInfoPersistence _ctAutoResolutionInfoPersistence;

	@Reference
	private CTClosureFactory _ctClosureFactory;

	@Reference(
		target = "(model.class.name=com.liferay.change.tracking.model.CTCollection)"
	)
	private ModelResourcePermission<CTCollection>
		_ctCollectionModelResourcePermission;

	@Reference
	private CTProcessLocalService _ctProcessLocalService;

	@Reference
	private CTServiceRegistry _ctServiceRegistry;

	@Reference
	private CustomSQL _customSQL;

	@Reference
	private IndexerRegistry _indexerRegistry;

	@Reference
	private IndexWriterHelper _indexWriterHelper;

	@Reference
	private InlineSQLHelper _inlineSQLHelper;

	@Reference(target = "(resource.name=" + CTConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private UIDFactory _uidFactory;

}