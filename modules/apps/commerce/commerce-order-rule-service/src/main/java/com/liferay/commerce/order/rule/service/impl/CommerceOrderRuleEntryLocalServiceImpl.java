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

package com.liferay.commerce.order.rule.service.impl;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountGroup;
import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.order.rule.exception.CommerceOrderRuleEntryDisplayDateException;
import com.liferay.commerce.order.rule.exception.CommerceOrderRuleEntryExpirationDateException;
import com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry;
import com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRelTable;
import com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryTable;
import com.liferay.commerce.order.rule.service.CommerceOrderRuleEntryRelLocalService;
import com.liferay.commerce.order.rule.service.base.CommerceOrderRuleEntryLocalServiceBaseImpl;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.GroupByStep;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.LongStream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "model.class.name=com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry",
	service = AopService.class
)
public class CommerceOrderRuleEntryLocalServiceImpl
	extends CommerceOrderRuleEntryLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrderRuleEntry addCommerceOrderRuleEntry(
			String externalReferenceCode, long userId, boolean active,
			String description, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire, String name,
			int priority, String type, String typeSettings,
			ServiceContext serviceContext)
		throws PortalException {

		CommerceOrderRuleEntry commerceOrderRuleEntry =
			commerceOrderRuleEntryPersistence.create(
				counterLocalService.increment());

		commerceOrderRuleEntry.setExternalReferenceCode(externalReferenceCode);

		User user = userLocalService.getUser(userId);

		commerceOrderRuleEntry.setCompanyId(user.getCompanyId());
		commerceOrderRuleEntry.setUserId(user.getUserId());
		commerceOrderRuleEntry.setUserName(user.getFullName());

		commerceOrderRuleEntry.setActive(active);
		commerceOrderRuleEntry.setDescription(description);

		Date date = new Date();

		commerceOrderRuleEntry.setDisplayDate(
			_portal.getDate(
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, user.getTimeZone(),
				CommerceOrderRuleEntryDisplayDateException.class));

		Date expirationDate = null;

		if (!neverExpire) {
			expirationDate = _portal.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, user.getTimeZone(),
				CommerceOrderRuleEntryExpirationDateException.class);
		}

		commerceOrderRuleEntry.setExpirationDate(expirationDate);

		commerceOrderRuleEntry.setName(name);
		commerceOrderRuleEntry.setPriority(priority);
		commerceOrderRuleEntry.setType(type);

		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.fastLoad(
				typeSettings
			).build();

		commerceOrderRuleEntry.setTypeSettingsProperties(
			typeSettingsUnicodeProperties);

		if ((expirationDate == null) || expirationDate.after(date)) {
			commerceOrderRuleEntry.setStatus(WorkflowConstants.STATUS_DRAFT);
		}
		else {
			commerceOrderRuleEntry.setStatus(WorkflowConstants.STATUS_EXPIRED);
		}

		commerceOrderRuleEntry.setStatusByUserId(user.getUserId());
		commerceOrderRuleEntry.setStatusDate(
			serviceContext.getModifiedDate(date));

		commerceOrderRuleEntry = commerceOrderRuleEntryPersistence.update(
			commerceOrderRuleEntry);

		resourceLocalService.addModelResources(
			commerceOrderRuleEntry, serviceContext);

		return _startWorkflowInstance(
			user.getUserId(), commerceOrderRuleEntry, serviceContext);
	}

	@Override
	public void checkCommerceOrderRuleEntries() throws PortalException {
		_checkCommerceOrderRuleEntriesByDisplayDate();
		_checkCommerceOrderRuleEntriesByExpirationDate();
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceOrderRuleEntry deleteCommerceOrderRuleEntry(
			CommerceOrderRuleEntry commerceOrderRuleEntry)
		throws PortalException {

		commerceOrderRuleEntryPersistence.remove(commerceOrderRuleEntry);

		resourceLocalService.deleteResource(
			commerceOrderRuleEntry.getCompanyId(),
			CommerceOrderRuleEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			commerceOrderRuleEntry.getCommerceOrderRuleEntryId());

		_commerceOrderRuleEntryRelLocalService.deleteCommerceOrderRuleEntryRels(
			commerceOrderRuleEntry.getCommerceOrderRuleEntryId());

		_workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
			commerceOrderRuleEntry.getCompanyId(), 0L,
			CommerceOrderRuleEntry.class.getName(),
			commerceOrderRuleEntry.getCommerceOrderRuleEntryId());

		return commerceOrderRuleEntry;
	}

	@Override
	public CommerceOrderRuleEntry deleteCommerceOrderRuleEntry(
			long commerceOrderRuleEntryId)
		throws PortalException {

		CommerceOrderRuleEntry commerceOrderRuleEntry =
			commerceOrderRuleEntryPersistence.findByPrimaryKey(
				commerceOrderRuleEntryId);

		return commerceOrderRuleEntryLocalService.deleteCommerceOrderRuleEntry(
			commerceOrderRuleEntry);
	}

	@Override
	public List<CommerceOrderRuleEntry>
		getAccountAndChannelAndOrderTypeCommerceRuleEntries(
			long companyId, long accountEntryId, long commerceChannelId,
			long commerceOrderTypeId) {

		return dslQuery(
			_getGroupByStep(
				accountEntryId, null, companyId, commerceChannelId,
				commerceOrderTypeId,
				DSLQueryFactoryUtil.selectDistinct(
					CommerceOrderRuleEntryTable.INSTANCE)
			).orderBy(
				CommerceOrderRuleEntryTable.INSTANCE.type.descending(),
				CommerceOrderRuleEntryTable.INSTANCE.priority.descending()
			));
	}

	@Override
	public List<CommerceOrderRuleEntry> getAccountAndChannelCommerceRuleEntries(
		long companyId, long accountEntryId, long commerceChannelId) {

		return dslQuery(
			_getGroupByStep(
				accountEntryId, null, companyId, commerceChannelId, null,
				DSLQueryFactoryUtil.selectDistinct(
					CommerceOrderRuleEntryTable.INSTANCE)
			).orderBy(
				CommerceOrderRuleEntryTable.INSTANCE.type.descending(),
				CommerceOrderRuleEntryTable.INSTANCE.priority.descending()
			));
	}

	@Override
	public List<CommerceOrderRuleEntry>
		getAccountAndOrderTypeCommerceRuleEntries(
			long companyId, long accountEntryId, long commerceOrderTypeId) {

		return dslQuery(
			_getGroupByStep(
				accountEntryId, null, companyId, null, commerceOrderTypeId,
				DSLQueryFactoryUtil.selectDistinct(
					CommerceOrderRuleEntryTable.INSTANCE)
			).orderBy(
				CommerceOrderRuleEntryTable.INSTANCE.type.descending(),
				CommerceOrderRuleEntryTable.INSTANCE.priority.descending()
			));
	}

	@Override
	public List<CommerceOrderRuleEntry> getAccountCommerceRuleEntries(
		long companyId, long accountEntryId) {

		return dslQuery(
			_getGroupByStep(
				accountEntryId, null, companyId, null, null,
				DSLQueryFactoryUtil.selectDistinct(
					CommerceOrderRuleEntryTable.INSTANCE)
			).orderBy(
				CommerceOrderRuleEntryTable.INSTANCE.type.descending(),
				CommerceOrderRuleEntryTable.INSTANCE.priority.descending()
			));
	}

	@Override
	public List<CommerceOrderRuleEntry>
		getAccountGroupsAndChannelAndOrderTypeCommerceRuleEntries(
			long companyId, long[] accountGroupIds, long commerceChannelId,
			long commerceOrderTypeId) {

		return dslQuery(
			_getGroupByStep(
				null, accountGroupIds, companyId, commerceChannelId,
				commerceOrderTypeId,
				DSLQueryFactoryUtil.selectDistinct(
					CommerceOrderRuleEntryTable.INSTANCE)
			).orderBy(
				CommerceOrderRuleEntryTable.INSTANCE.type.descending(),
				CommerceOrderRuleEntryTable.INSTANCE.priority.descending()
			));
	}

	@Override
	public List<CommerceOrderRuleEntry>
		getAccountGroupsAndChannelCommerceRuleEntries(
			long companyId, long[] accountGroupIds, long commerceChannelId) {

		return dslQuery(
			_getGroupByStep(
				null, accountGroupIds, companyId, commerceChannelId, null,
				DSLQueryFactoryUtil.selectDistinct(
					CommerceOrderRuleEntryTable.INSTANCE)
			).orderBy(
				CommerceOrderRuleEntryTable.INSTANCE.type.descending(),
				CommerceOrderRuleEntryTable.INSTANCE.priority.descending()
			));
	}

	@Override
	public List<CommerceOrderRuleEntry>
		getAccountGroupsAndOrderTypeCommerceRuleEntries(
			long companyId, long[] accountGroupIds, long commerceOrderTypeId) {

		return dslQuery(
			_getGroupByStep(
				null, accountGroupIds, companyId, null, commerceOrderTypeId,
				DSLQueryFactoryUtil.selectDistinct(
					CommerceOrderRuleEntryTable.INSTANCE)
			).orderBy(
				CommerceOrderRuleEntryTable.INSTANCE.type.descending(),
				CommerceOrderRuleEntryTable.INSTANCE.priority.descending()
			));
	}

	@Override
	public List<CommerceOrderRuleEntry> getAccountGroupsCommerceRuleEntries(
		long companyId, long[] accountGroupIds) {

		return dslQuery(
			_getGroupByStep(
				null, accountGroupIds, companyId, null, null,
				DSLQueryFactoryUtil.selectDistinct(
					CommerceOrderRuleEntryTable.INSTANCE)
			).orderBy(
				CommerceOrderRuleEntryTable.INSTANCE.type.descending(),
				CommerceOrderRuleEntryTable.INSTANCE.priority.descending()
			));
	}

	@Override
	public List<CommerceOrderRuleEntry>
		getChannelAndOrderTypeCommerceRuleEntries(
			long companyId, long commerceChannelId, long commerceOrderTypeId) {

		return dslQuery(
			_getGroupByStep(
				null, null, companyId, commerceChannelId, commerceOrderTypeId,
				DSLQueryFactoryUtil.selectDistinct(
					CommerceOrderRuleEntryTable.INSTANCE)
			).orderBy(
				CommerceOrderRuleEntryTable.INSTANCE.type.descending(),
				CommerceOrderRuleEntryTable.INSTANCE.priority.descending()
			));
	}

	@Override
	public List<CommerceOrderRuleEntry> getChannelCommerceRuleEntries(
		long companyId, long commerceChannelId) {

		return dslQuery(
			_getGroupByStep(
				null, null, companyId, commerceChannelId, null,
				DSLQueryFactoryUtil.selectDistinct(
					CommerceOrderRuleEntryTable.INSTANCE)
			).orderBy(
				CommerceOrderRuleEntryTable.INSTANCE.type.descending(),
				CommerceOrderRuleEntryTable.INSTANCE.priority.descending()
			));
	}

	@Override
	public List<CommerceOrderRuleEntry> getCommerceOrderRuleEntries(
		long companyId, boolean active, int start, int end) {

		return commerceOrderRuleEntryPersistence.findByC_A(
			companyId, active, start, end);
	}

	@Override
	public List<CommerceOrderRuleEntry> getCommerceOrderRuleEntries(
		long companyId, boolean active, String type, int start, int end) {

		return commerceOrderRuleEntryPersistence.findByC_A_LikeType(
			companyId, active, type, start, end);
	}

	@Override
	public List<CommerceOrderRuleEntry> getCommerceOrderRuleEntries(
		long companyId, String type, int start, int end) {

		return commerceOrderRuleEntryPersistence.findByC_LikeType(
			companyId, type, start, end);
	}

	@Override
	public List<CommerceOrderRuleEntry> getOrderTypeCommerceRuleEntries(
		long companyId, long commerceOrderTypeId) {

		return dslQuery(
			_getGroupByStep(
				null, null, companyId, null, commerceOrderTypeId,
				DSLQueryFactoryUtil.selectDistinct(
					CommerceOrderRuleEntryTable.INSTANCE)
			).orderBy(
				CommerceOrderRuleEntryTable.INSTANCE.type.descending(),
				CommerceOrderRuleEntryTable.INSTANCE.priority.descending()
			));
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrderRuleEntry updateCommerceOrderRuleEntry(
			long userId, long commerceOrderRuleEntryId, boolean active,
			String description, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire, String name,
			int priority, String typeSettings, ServiceContext serviceContext)
		throws PortalException {

		CommerceOrderRuleEntry commerceOrderRuleEntry =
			commerceOrderRuleEntryLocalService.getCommerceOrderRuleEntry(
				commerceOrderRuleEntryId);

		commerceOrderRuleEntry.setActive(active);
		commerceOrderRuleEntry.setDescription(description);

		Date date = new Date();

		User user = userLocalService.getUser(userId);

		commerceOrderRuleEntry.setDisplayDate(
			_portal.getDate(
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, user.getTimeZone(),
				CommerceOrderRuleEntryDisplayDateException.class));

		Date expirationDate = null;

		if (!neverExpire) {
			expirationDate = _portal.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, user.getTimeZone(),
				CommerceOrderRuleEntryExpirationDateException.class);
		}

		commerceOrderRuleEntry.setExpirationDate(expirationDate);

		commerceOrderRuleEntry.setName(name);
		commerceOrderRuleEntry.setPriority(priority);

		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.fastLoad(
				typeSettings
			).build();

		commerceOrderRuleEntry.setTypeSettingsProperties(
			typeSettingsUnicodeProperties);

		if ((expirationDate == null) || expirationDate.after(date)) {
			commerceOrderRuleEntry.setStatus(WorkflowConstants.STATUS_DRAFT);
		}
		else {
			commerceOrderRuleEntry.setStatus(WorkflowConstants.STATUS_EXPIRED);
		}

		commerceOrderRuleEntry.setStatusByUserId(user.getUserId());
		commerceOrderRuleEntry.setStatusDate(
			serviceContext.getModifiedDate(date));
		commerceOrderRuleEntry.setExpandoBridgeAttributes(serviceContext);

		commerceOrderRuleEntry = commerceOrderRuleEntryPersistence.update(
			commerceOrderRuleEntry);

		return _startWorkflowInstance(
			user.getUserId(), commerceOrderRuleEntry, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrderRuleEntry updateStatus(
			long userId, long commerceOrderRuleEntryId, int status,
			ServiceContext serviceContext)
		throws PortalException {

		CommerceOrderRuleEntry commerceOrderRuleEntry =
			commerceOrderRuleEntryPersistence.findByPrimaryKey(
				commerceOrderRuleEntryId);

		Date date = new Date();

		if ((status == WorkflowConstants.STATUS_APPROVED) &&
			(commerceOrderRuleEntry.getDisplayDate() != null) &&
			date.before(commerceOrderRuleEntry.getDisplayDate())) {

			commerceOrderRuleEntry.setActive(false);

			status = WorkflowConstants.STATUS_SCHEDULED;
		}

		if (status == WorkflowConstants.STATUS_APPROVED) {
			Date expirationDate = commerceOrderRuleEntry.getExpirationDate();

			if ((expirationDate != null) && expirationDate.before(date)) {
				commerceOrderRuleEntry.setExpirationDate(null);
			}

			if (commerceOrderRuleEntry.getStatus() ==
					WorkflowConstants.STATUS_SCHEDULED) {

				commerceOrderRuleEntry.setActive(true);
			}
		}

		if (status == WorkflowConstants.STATUS_EXPIRED) {
			commerceOrderRuleEntry.setActive(false);
			commerceOrderRuleEntry.setExpirationDate(date);
		}

		commerceOrderRuleEntry.setStatus(status);

		User user = userLocalService.getUser(userId);

		commerceOrderRuleEntry.setStatusByUserId(user.getUserId());
		commerceOrderRuleEntry.setStatusByUserName(user.getFullName());

		commerceOrderRuleEntry.setStatusDate(
			serviceContext.getModifiedDate(date));

		return commerceOrderRuleEntryPersistence.update(commerceOrderRuleEntry);
	}

	private void _checkCommerceOrderRuleEntriesByDisplayDate()
		throws PortalException {

		List<CommerceOrderRuleEntry> commerceOrderRuleEntries =
			commerceOrderRuleEntryPersistence.findByLtD_S(
				new Date(), WorkflowConstants.STATUS_SCHEDULED);

		for (CommerceOrderRuleEntry commerceOrderRuleEntry :
				commerceOrderRuleEntries) {

			long userId = _portal.getValidUserId(
				commerceOrderRuleEntry.getCompanyId(),
				commerceOrderRuleEntry.getUserId());

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCommand(Constants.UPDATE);

			commerceOrderRuleEntryLocalService.updateStatus(
				userId, commerceOrderRuleEntry.getCommerceOrderRuleEntryId(),
				WorkflowConstants.STATUS_APPROVED, serviceContext);
		}
	}

	private void _checkCommerceOrderRuleEntriesByExpirationDate()
		throws PortalException {

		List<CommerceOrderRuleEntry> commerceOrderRuleEntries =
			commerceOrderRuleEntryPersistence.findByLtE_S(
				new Date(), WorkflowConstants.STATUS_APPROVED);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Expiring " + commerceOrderRuleEntries.size() +
					" commerce order rule entries");
		}

		for (CommerceOrderRuleEntry commerceOrderRuleEntry :
				commerceOrderRuleEntries) {

			long userId = _portal.getValidUserId(
				commerceOrderRuleEntry.getCompanyId(),
				commerceOrderRuleEntry.getUserId());

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCommand(Constants.UPDATE);

			commerceOrderRuleEntryLocalService.updateStatus(
				userId, commerceOrderRuleEntry.getCommerceOrderRuleEntryId(),
				WorkflowConstants.STATUS_EXPIRED, serviceContext);
		}
	}

	private GroupByStep _getGroupByStep(
		Long accountEntryId, long[] accountGroupIds, Long companyId,
		Long commerceChannelId, Long commerceOrderTypeId, FromStep fromStep) {

		CommerceOrderRuleEntryRelTable
			aliasCommerceOrderRuleEntryAccountEntryRel =
				CommerceOrderRuleEntryRelTable.INSTANCE.as(
					"aliasCommerceOrderRuleEntryAccountEntryRel");
		CommerceOrderRuleEntryRelTable
			aliasCommerceOrderRuleEntryAccountGroupRel =
				CommerceOrderRuleEntryRelTable.INSTANCE.as(
					"aliasCommerceOrderRuleEntryAccountGroupRel");
		CommerceOrderRuleEntryRelTable
			aliasCommerceOrderRuleEntryCommerceChannelRel =
				CommerceOrderRuleEntryRelTable.INSTANCE.as(
					"aliasCommerceOrderRuleEntryCommerceChannelRel");
		CommerceOrderRuleEntryRelTable
			aliasCommerceOrderRuleEntryCommerceOrderTypeRel =
				CommerceOrderRuleEntryRelTable.INSTANCE.as(
					"aliasCommerceOrderRuleEntryCommerceOrderTypeRel");

		JoinStep joinStep = fromStep.from(
			CommerceOrderRuleEntryTable.INSTANCE
		).leftJoinOn(
			aliasCommerceOrderRuleEntryAccountEntryRel,
			_getPredicate(
				AccountEntry.class.getName(),
				aliasCommerceOrderRuleEntryAccountEntryRel.classNameId,
				aliasCommerceOrderRuleEntryAccountEntryRel.
					commerceOrderRuleEntryId)
		).leftJoinOn(
			aliasCommerceOrderRuleEntryAccountGroupRel,
			_getPredicate(
				AccountGroup.class.getName(),
				aliasCommerceOrderRuleEntryAccountGroupRel.classNameId,
				aliasCommerceOrderRuleEntryAccountGroupRel.
					commerceOrderRuleEntryId)
		).leftJoinOn(
			aliasCommerceOrderRuleEntryCommerceChannelRel,
			_getPredicate(
				CommerceChannel.class.getName(),
				aliasCommerceOrderRuleEntryCommerceChannelRel.classNameId,
				aliasCommerceOrderRuleEntryCommerceChannelRel.
					commerceOrderRuleEntryId)
		).leftJoinOn(
			aliasCommerceOrderRuleEntryCommerceOrderTypeRel,
			_getPredicate(
				CommerceOrderType.class.getName(),
				aliasCommerceOrderRuleEntryCommerceOrderTypeRel.classNameId,
				aliasCommerceOrderRuleEntryCommerceOrderTypeRel.
					commerceOrderRuleEntryId)
		);

		Predicate predicate = CommerceOrderRuleEntryTable.INSTANCE.status.eq(
			WorkflowConstants.STATUS_APPROVED
		).and(
			CommerceOrderRuleEntryTable.INSTANCE.companyId.eq(companyId)
		).and(
			CommerceOrderRuleEntryTable.INSTANCE.active.eq(true)
		);

		if (accountEntryId != null) {
			predicate = predicate.and(
				aliasCommerceOrderRuleEntryAccountEntryRel.classPK.eq(
					accountEntryId));
		}
		else {
			predicate = predicate.and(
				aliasCommerceOrderRuleEntryAccountEntryRel.
					commerceOrderRuleEntryRelId.isNull());
		}

		if (accountGroupIds != null) {
			if (accountGroupIds.length == 0) {
				accountGroupIds = new long[] {0};
			}

			LongStream longStream = Arrays.stream(accountGroupIds);

			predicate = predicate.and(
				aliasCommerceOrderRuleEntryAccountGroupRel.classPK.in(
					longStream.boxed(
					).toArray(
						Long[]::new
					)));
		}
		else {
			predicate = predicate.and(
				aliasCommerceOrderRuleEntryAccountGroupRel.
					commerceOrderRuleEntryRelId.isNull());
		}

		if (commerceChannelId != null) {
			predicate = predicate.and(
				aliasCommerceOrderRuleEntryCommerceChannelRel.classPK.eq(
					commerceChannelId));
		}
		else {
			predicate = predicate.and(
				aliasCommerceOrderRuleEntryCommerceChannelRel.
					commerceOrderRuleEntryRelId.isNull());
		}

		if (commerceOrderTypeId != null) {
			predicate = predicate.and(
				aliasCommerceOrderRuleEntryCommerceOrderTypeRel.classPK.eq(
					commerceOrderTypeId));
		}
		else {
			predicate = predicate.and(
				aliasCommerceOrderRuleEntryCommerceOrderTypeRel.
					commerceOrderRuleEntryRelId.isNull());
		}

		return joinStep.where(predicate);
	}

	private Predicate _getPredicate(
		String className,
		Column<CommerceOrderRuleEntryRelTable, Long> classNameId,
		Column<CommerceOrderRuleEntryRelTable, Long> commerceOrderRuleEntryId) {

		return classNameId.eq(
			classNameLocalService.getClassNameId(className)
		).and(
			commerceOrderRuleEntryId.eq(
				CommerceOrderRuleEntryTable.INSTANCE.commerceOrderRuleEntryId)
		);
	}

	private CommerceOrderRuleEntry _startWorkflowInstance(
			long userId, CommerceOrderRuleEntry commerceOrderRuleEntry,
			ServiceContext serviceContext)
		throws PortalException {

		Map<String, Serializable> workflowContext = new HashMap<>();

		return WorkflowHandlerRegistryUtil.startWorkflowInstance(
			commerceOrderRuleEntry.getCompanyId(), 0L, userId,
			CommerceOrderRuleEntry.class.getName(),
			commerceOrderRuleEntry.getCommerceOrderRuleEntryId(),
			commerceOrderRuleEntry, serviceContext, workflowContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderRuleEntryLocalServiceImpl.class);

	@Reference
	private CommerceOrderRuleEntryRelLocalService
		_commerceOrderRuleEntryRelLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

}