/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.mobiledevicerules.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.mobiledevicerules.model.MDRRule;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup;
import com.liferay.portlet.mobiledevicerules.service.base.MDRRuleGroupLocalServiceBaseImpl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Edward C. Han
 * @author Manuel de la Peña
 */
public class MDRRuleGroupLocalServiceImpl
	extends MDRRuleGroupLocalServiceBaseImpl {

	public MDRRuleGroup addRuleGroup(
			long groupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(
			serviceContext.getUserId());
		Date now = new Date();

		long ruleGroupId = counterLocalService.increment();

		MDRRuleGroup ruleGroup = createMDRRuleGroup(ruleGroupId);

		ruleGroup.setUuid(serviceContext.getUuid());
		ruleGroup.setGroupId(groupId);
		ruleGroup.setCompanyId(serviceContext.getCompanyId());
		ruleGroup.setCreateDate(serviceContext.getCreateDate(now));
		ruleGroup.setModifiedDate(serviceContext.getModifiedDate(now));
		ruleGroup.setUserId(user.getUserId());
		ruleGroup.setUserName(user.getFullName());
		ruleGroup.setNameMap(nameMap);
		ruleGroup.setDescriptionMap(descriptionMap);

		return updateMDRRuleGroup(ruleGroup);
	}

	public MDRRuleGroup copyRuleGroup(
			long ruleGroupId, long groupId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		MDRRuleGroup ruleGroup = mdrRuleGroupPersistence.findByPrimaryKey(
			ruleGroupId);

		return copyRuleGroup(ruleGroup, groupId, serviceContext);
	}

	public MDRRuleGroup copyRuleGroup(
			MDRRuleGroup ruleGroup, long groupId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		Map<Locale, String> nameMap = ruleGroup.getNameMap();

		for (Map.Entry<Locale, String> entry : nameMap.entrySet()) {
			Locale locale = entry.getKey();
			String name = entry.getValue();

			if (Validator.isNull(name)) {
				continue;
			}

			String postfix = LanguageUtil.get(
				locale,
				PropsValues.MOBILE_DEVICE_RULES_RULE_GROUP_COPY_POSTFIX);

			nameMap.put(locale, name.concat(StringPool.SPACE).concat(postfix));
		}

		MDRRuleGroup newRuleGroup = addRuleGroup(
			group.getGroupId(), nameMap, ruleGroup.getDescriptionMap(),
			serviceContext);

		List<MDRRule> rules = mdrRulePersistence.findByRuleGroupId(
			ruleGroup.getRuleGroupId());

		for (MDRRule rule : rules) {
			serviceContext.setUuid(PortalUUIDUtil.generate());

			mdrRuleLocalService.copyRule(
				rule, newRuleGroup.getRuleGroupId(), serviceContext);
		}

		return newRuleGroup;
	}

	public void deleteRuleGroup(long ruleGroupId) throws SystemException {
		MDRRuleGroup ruleGroup = mdrRuleGroupPersistence.fetchByPrimaryKey(
			ruleGroupId);

		if (ruleGroup != null) {
			deleteRuleGroup(ruleGroup);
		}
	}

	public void deleteRuleGroup(MDRRuleGroup ruleGroup) throws SystemException {

		// Rule group

		mdrRuleGroupPersistence.remove(ruleGroup);

		// Rules

		mdrRuleLocalService.deleteRules(ruleGroup.getRuleGroupId());

		//	Rule group instances

		mdrRuleGroupInstanceLocalService.deleteRuleGroupInstances(
			ruleGroup.getRuleGroupId());
	}

	public void deleteRuleGroups(long groupId) throws SystemException {
		List<MDRRuleGroup> ruleGroups = mdrRuleGroupPersistence.findByGroupId(
			groupId);

		for (MDRRuleGroup ruleGroup : ruleGroups) {
			deleteRuleGroup(ruleGroup);
		}
	}

	public MDRRuleGroup fetchRuleGroup(long ruleGroupId)
		throws SystemException {

		return mdrRuleGroupPersistence.fetchByPrimaryKey(ruleGroupId);
	}

	public MDRRuleGroup getRuleGroup(long ruleGroupId)
		throws PortalException, SystemException {

		return mdrRuleGroupPersistence.findByPrimaryKey(ruleGroupId);
	}

	public List<MDRRuleGroup> getRuleGroups(long groupId)
		throws SystemException {

		return mdrRuleGroupPersistence.findByGroupId(groupId);
	}

	public List<MDRRuleGroup> getRuleGroups(long groupId, int start, int end)
		throws SystemException {

		return mdrRuleGroupPersistence.findByGroupId(groupId, start, end);
	}

	public int getRuleGroupsCount(long groupId) throws SystemException {
		return mdrRuleGroupPersistence.countByGroupId(groupId);
	}

	public List<MDRRuleGroup> search(
			long groupId, String name, LinkedHashMap<String, Object> params,
			boolean andOperator, int start, int end)
		throws SystemException {

		return mdrRuleGroupFinder.findByG_N(
			groupId, name, params, andOperator, start, end);
	}

	public List<MDRRuleGroup> searchByKeywords(
			long groupId, String keywords, LinkedHashMap<String, Object> params,
			boolean andOperator, int start, int end)
		throws SystemException {

		return mdrRuleGroupFinder.findByKeywords(
			groupId, keywords, params, start, end);
	}

	public int searchByKeywordsCount(
			long groupId, String keywords, LinkedHashMap<String, Object> params,
			boolean andOperator)
		throws SystemException {

		return mdrRuleGroupFinder.countByKeywords(groupId, params, keywords);
	}

	public int searchCount(
			long groupId, String name, LinkedHashMap<String, Object> params,
			boolean andOperator)
		throws SystemException {

		return mdrRuleGroupFinder.countByG_N(
			groupId, name, params, andOperator);
	}

	public MDRRuleGroup updateRuleGroup(
			long ruleGroupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, ServiceContext serviceContext)
		throws PortalException, SystemException {

		MDRRuleGroup ruleGroup = mdrRuleGroupPersistence.findByPrimaryKey(
			ruleGroupId);

		ruleGroup.setModifiedDate(serviceContext.getModifiedDate(null));
		ruleGroup.setNameMap(nameMap);
		ruleGroup.setDescriptionMap(descriptionMap);

		mdrRuleGroupPersistence.update(ruleGroup);

		return ruleGroup;
	}

}