/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.StringBundler;
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

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Edward C. Han
 */
public class MDRRuleGroupLocalServiceImpl
	extends MDRRuleGroupLocalServiceBaseImpl {

	public MDRRuleGroup addMDRRuleGroup(
			long groupId, Map<Locale, String> nameMap,
            Map<Locale, String> descriptionMap, ServiceContext serviceContext)
		throws PortalException, SystemException{

        User user = userLocalService.getUser(serviceContext.getUserId());
        Date now = new Date();

		long ruleGroupId = counterLocalService.increment();

		MDRRuleGroup mdrRuleGroup = createMDRRuleGroup(ruleGroupId);

        mdrRuleGroup.setUuid(serviceContext.getUuid());
        mdrRuleGroup.setCompanyId(serviceContext.getCompanyId());
        mdrRuleGroup.setCreateDate(serviceContext.getCreateDate(now));
        mdrRuleGroup.setModifiedDate(serviceContext.getModifiedDate(now));
        mdrRuleGroup.setUserId(user.getUserId());
        mdrRuleGroup.setUserName(user.getFullName());
        mdrRuleGroup.setGroupId(groupId);
        mdrRuleGroup.setNameMap(nameMap);
        mdrRuleGroup.setDescriptionMap(descriptionMap);

		return updateMDRRuleGroup(mdrRuleGroup, false);
	}

	public MDRRuleGroup cloneMDRRuleGroup(
            long ruleGroupId, long targetGroupId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		MDRRuleGroup mdrRuleGroup = getMDRRuleGroup(ruleGroupId);

		return cloneMDRRuleGroup(mdrRuleGroup, targetGroupId, serviceContext);
	}

	public MDRRuleGroup cloneMDRRuleGroup(
			MDRRuleGroup ruleGroup, long targetGroupId,
            ServiceContext serviceContext)
		throws PortalException, SystemException {

		Group targetGroup = groupLocalService.getGroup(targetGroupId);

		Map<Locale, String> nameMap = ruleGroup.getNameMap();

		String prefixProp = PropsValues.DEVICE_RULE_GROUP_CLONE_POSTFIX;

		for (Map.Entry<Locale, String> nameMapEntry : nameMap.entrySet()) {
			if (Validator.isNotNull(nameMapEntry.getValue())) {
				String prefix = LanguageUtil.get(
                    nameMapEntry.getKey(), prefixProp);

				StringBundler newName = new StringBundler(3);

				newName.append(nameMapEntry.getValue());
				newName.append(StringPool.SPACE);
				newName.append(prefix);

				nameMap.put(nameMapEntry.getKey(), newName.toString());
			}
		}

		MDRRuleGroup newMdrRuleGroup = addMDRRuleGroup(
            targetGroup.getGroupId(), nameMap, ruleGroup.getDescriptionMap(),
            serviceContext);

		for (MDRRule mdrRule : ruleGroup.getRules()) {
            serviceContext.setUuid(PortalUUIDUtil.generate());

			mdrRuleLocalService.cloneMDRRule(
                mdrRule, newMdrRuleGroup.getRuleGroupId(), serviceContext);
		}

		return newMdrRuleGroup;
	}

	public int countByGroupId(long groupId) throws SystemException {
		return mdrRuleGroupPersistence.countByGroupId(groupId);
	}

	@Override
	public void deleteMDRRuleGroup(MDRRuleGroup ruleGroup)
		throws PortalException, SystemException {

		mdrRuleLocalService.deleteMDRRules(ruleGroup.getRuleGroupId());

		super.deleteMDRRuleGroup(ruleGroup);
	}

	@Override
	public void deleteMDRRuleGroup(long ruleGroupId)
		throws PortalException, SystemException {

		MDRRuleGroup deviceRuleGroup =
			mdrRuleGroupPersistence.fetchByPrimaryKey(ruleGroupId);

		if (deviceRuleGroup != null) {
			deleteMDRRuleGroup(deviceRuleGroup);
		}
	}

	public MDRRuleGroup fetchMDRRuleGroup(long ruleGroupId)
        throws SystemException {

		return mdrRuleGroupPersistence.fetchByPrimaryKey(ruleGroupId);
	}

	public List<MDRRuleGroup> findByGroupId(long groupId)
		throws SystemException {

		return mdrRuleGroupPersistence.findByGroupId(groupId);
	}

	public List<MDRRuleGroup> findByGroupId(long groupId, int start, int end)
		throws SystemException {

		return mdrRuleGroupPersistence.findByGroupId(groupId, start, end);
	}

	public List<MDRRuleGroup> search(long groupId, String name)
		throws SystemException {

		return mdrRuleGroupFinder.findByG_N(groupId, name);
	}

	public List<MDRRuleGroup> search(
            long groupId, String name, int start, int end)
		throws SystemException {

		return mdrRuleGroupFinder.findByG_N(groupId, name, start, end);
	}

	public int searchCount(long groupId, String name) throws SystemException {
		return mdrRuleGroupFinder.countByG_N(groupId, name);
	}

    public MDRRuleGroup updateRuleGroup(
            long ruleGroupId, Map<Locale, String> nameMap,
            Map<Locale, String> descriptionMap, ServiceContext serviceContext)
        throws PortalException, SystemException {

        MDRRuleGroup mdrRuleGroup = mdrRuleGroupPersistence.findByPrimaryKey(
            ruleGroupId);

        mdrRuleGroup.setNameMap(nameMap);
        mdrRuleGroup.setDescriptionMap(descriptionMap);

        mdrRuleGroupPersistence.update(mdrRuleGroup, false);

        return mdrRuleGroup;
    }

}