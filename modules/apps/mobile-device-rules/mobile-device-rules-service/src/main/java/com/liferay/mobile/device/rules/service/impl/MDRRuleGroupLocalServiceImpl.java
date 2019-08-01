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

package com.liferay.mobile.device.rules.service.impl;

import com.liferay.mobile.device.rules.model.MDRRule;
import com.liferay.mobile.device.rules.model.MDRRuleGroup;
import com.liferay.mobile.device.rules.service.MDRRuleGroupInstanceLocalService;
import com.liferay.mobile.device.rules.service.MDRRuleLocalService;
import com.liferay.mobile.device.rules.service.base.MDRRuleGroupLocalServiceBaseImpl;
import com.liferay.mobile.device.rules.util.comparator.RuleGroupCreateDateComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.util.PropsValues;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Edward C. Han
 * @author Manuel de la Peña
 */
@Component(
	property = "model.class.name=com.liferay.mobile.device.rules.model.MDRRuleGroup",
	service = AopService.class
)
public class MDRRuleGroupLocalServiceImpl
	extends MDRRuleGroupLocalServiceBaseImpl {

	@Override
	public MDRRuleGroup addRuleGroup(
			long groupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, ServiceContext serviceContext)
		throws PortalException {

		// Rule group

		User user = userLocalService.getUser(serviceContext.getUserId());

		long ruleGroupId = counterLocalService.increment();

		MDRRuleGroup ruleGroup = createMDRRuleGroup(ruleGroupId);

		ruleGroup.setUuid(serviceContext.getUuid());
		ruleGroup.setGroupId(groupId);
		ruleGroup.setCompanyId(serviceContext.getCompanyId());
		ruleGroup.setUserId(user.getUserId());
		ruleGroup.setUserName(user.getFullName());
		ruleGroup.setNameMap(nameMap);
		ruleGroup.setDescriptionMap(descriptionMap);

		// Resources

		resourceLocalService.addModelResources(ruleGroup, serviceContext);

		return updateMDRRuleGroup(ruleGroup);
	}

	@Override
	public MDRRuleGroup copyRuleGroup(
			long ruleGroupId, long groupId, ServiceContext serviceContext)
		throws PortalException {

		MDRRuleGroup ruleGroup = mdrRuleGroupPersistence.findByPrimaryKey(
			ruleGroupId);

		return copyRuleGroup(ruleGroup, groupId, serviceContext);
	}

	@Override
	public MDRRuleGroup copyRuleGroup(
			MDRRuleGroup ruleGroup, long groupId, ServiceContext serviceContext)
		throws PortalException {

		Group group = groupLocalService.getGroup(groupId);

		Map<Locale, String> nameMap = ruleGroup.getNameMap();

		for (Map.Entry<Locale, String> entry : nameMap.entrySet()) {
			String name = entry.getValue();

			if (Validator.isNull(name)) {
				continue;
			}

			Locale locale = entry.getKey();

			String postfix = LanguageUtil.get(
				locale,
				PropsValues.MOBILE_DEVICE_RULES_RULE_GROUP_COPY_POSTFIX);

			nameMap.put(
				locale,
				name.concat(
					StringPool.SPACE
				).concat(
					postfix
				));
		}

		MDRRuleGroup newRuleGroup = addRuleGroup(
			group.getGroupId(), nameMap, ruleGroup.getDescriptionMap(),
			serviceContext);

		List<MDRRule> rules = mdrRulePersistence.findByRuleGroupId(
			ruleGroup.getRuleGroupId());

		for (MDRRule rule : rules) {
			serviceContext.setUuid(PortalUUIDUtil.generate());

			_mdrRuleLocalService.copyRule(
				rule, newRuleGroup.getRuleGroupId(), serviceContext);
		}

		return newRuleGroup;
	}

	@Override
	public void deleteRuleGroup(long ruleGroupId) {
		MDRRuleGroup ruleGroup = mdrRuleGroupPersistence.fetchByPrimaryKey(
			ruleGroupId);

		if (ruleGroup != null) {
			mdrRuleGroupLocalService.deleteRuleGroup(ruleGroup);
		}
	}

	@Override
	@SystemEvent(
		action = SystemEventConstants.ACTION_SKIP,
		type = SystemEventConstants.TYPE_DELETE
	)
	public void deleteRuleGroup(MDRRuleGroup ruleGroup) {

		// Rule group

		mdrRuleGroupPersistence.remove(ruleGroup);

		// Rules

		_mdrRuleLocalService.deleteRules(ruleGroup.getRuleGroupId());

		// Rule group instances

		_mdrRuleGroupInstanceLocalService.deleteRuleGroupInstances(
			ruleGroup.getRuleGroupId());
	}

	@Override
	public void deleteRuleGroups(long groupId) {
		List<MDRRuleGroup> ruleGroups = mdrRuleGroupPersistence.findByGroupId(
			groupId);

		for (MDRRuleGroup ruleGroup : ruleGroups) {
			mdrRuleGroupLocalService.deleteRuleGroup(ruleGroup);
		}
	}

	@Override
	public MDRRuleGroup fetchRuleGroup(long ruleGroupId) {
		return mdrRuleGroupPersistence.fetchByPrimaryKey(ruleGroupId);
	}

	@Override
	public MDRRuleGroup getRuleGroup(long ruleGroupId) throws PortalException {
		return mdrRuleGroupPersistence.findByPrimaryKey(ruleGroupId);
	}

	@Override
	public List<MDRRuleGroup> getRuleGroups(long groupId) {
		return mdrRuleGroupPersistence.findByGroupId(groupId);
	}

	@Override
	public List<MDRRuleGroup> getRuleGroups(long groupId, int start, int end) {
		return mdrRuleGroupPersistence.findByGroupId(groupId, start, end);
	}

	@Override
	public List<MDRRuleGroup> getRuleGroups(
		long[] groupIds, int start, int end) {

		return mdrRuleGroupPersistence.findByGroupId(groupIds, start, end);
	}

	@Override
	public int getRuleGroupsCount(long groupId) {
		return mdrRuleGroupPersistence.countByGroupId(groupId);
	}

	@Override
	public int getRuleGroupsCount(long[] groupIds) {
		return mdrRuleGroupPersistence.countByGroupId(groupIds);
	}

	@Override
	public List<MDRRuleGroup> search(
		long groupId, String name, LinkedHashMap<String, Object> params,
		boolean andOperator, int start, int end) {

		return mdrRuleGroupFinder.findByG_N(
			groupId, name, params, andOperator, start, end);
	}

	@Override
	public List<MDRRuleGroup> searchByKeywords(
		long groupId, String keywords, LinkedHashMap<String, Object> params,
		boolean andOperator, int start, int end) {

		return searchByKeywords(
			groupId, keywords, params, andOperator, start, end,
			new RuleGroupCreateDateComparator());
	}

	@Override
	public List<MDRRuleGroup> searchByKeywords(
		long groupId, String keywords, LinkedHashMap<String, Object> params,
		boolean andOperator, int start, int end,
		OrderByComparator<MDRRuleGroup> obc) {

		return mdrRuleGroupFinder.findByKeywords(
			groupId, keywords, params, start, end, obc);
	}

	@Override
	public int searchByKeywordsCount(
		long groupId, String keywords, LinkedHashMap<String, Object> params,
		boolean andOperator) {

		return mdrRuleGroupFinder.countByKeywords(groupId, keywords, params);
	}

	@Override
	public int searchCount(
		long groupId, String name, LinkedHashMap<String, Object> params,
		boolean andOperator) {

		return mdrRuleGroupFinder.countByG_N(
			groupId, name, params, andOperator);
	}

	@Override
	public MDRRuleGroup updateRuleGroup(
			long ruleGroupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, ServiceContext serviceContext)
		throws PortalException {

		MDRRuleGroup ruleGroup = mdrRuleGroupPersistence.findByPrimaryKey(
			ruleGroupId);

		ruleGroup.setNameMap(nameMap);
		ruleGroup.setDescriptionMap(descriptionMap);

		mdrRuleGroupPersistence.update(ruleGroup);

		return ruleGroup;
	}

	@Reference
	private MDRRuleGroupInstanceLocalService _mdrRuleGroupInstanceLocalService;

	@Reference
	private MDRRuleLocalService _mdrRuleLocalService;

}