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

package com.liferay.portlet.social.service.impl;

import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.social.model.SocialEquityActionMapping;
import com.liferay.portlet.social.model.SocialEquitySetting;
import com.liferay.portlet.social.model.SocialEquitySettingConstants;
import com.liferay.portlet.social.model.impl.SocialEquitySettingImpl;
import com.liferay.portlet.social.service.base.SocialEquitySettingLocalServiceBaseImpl;

import java.util.Collections;
import java.util.List;

/**
 * @author Zsolt Berentey
 * @author Brian Wing Shun Chan
 */
public class SocialEquitySettingLocalServiceImpl
	extends SocialEquitySettingLocalServiceBaseImpl {

	public static final String CACHE_NAME =
		SocialEquitySettingLocalServiceImpl.class.getName();

	public List<SocialEquitySetting> getEquitySettings(
			long groupId, String className, String actionId)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getEquitySettings(groupId, classNameId, actionId);
	}

	public List<SocialEquitySetting> getEquitySettings(
			long groupId, long classNameId, String actionId)
		throws SystemException {

		String key = encodeKey(classNameId, actionId);

		List<SocialEquitySetting> socialEquitySettings =
			(List<SocialEquitySetting>)_portalCache.get(key);

		if (socialEquitySettings != null) {
			return socialEquitySettings;
		}

		String className = PortalUtil.getClassName(classNameId);

		SocialEquityActionMapping equityActionMapping =
			ResourceActionsUtil.getSocialEquityActionMapping(
				className, actionId);

		List<SocialEquitySetting> equitySettings =
			socialEquitySettingPersistence.findByG_C_A(
				groupId, classNameId, actionId);

		if ((equityActionMapping == null) && equitySettings.isEmpty()) {
			socialEquitySettings = Collections.emptyList();

			_portalCache.put(key, socialEquitySettings);

			return socialEquitySettings;
		}

		if (equityActionMapping == null) {
			equityActionMapping = new SocialEquityActionMapping();

			equityActionMapping.setActionId(actionId);
		}

		equitySettings = ListUtil.copy(equitySettings);

		if (equitySettings.isEmpty()) {
			if (equityActionMapping.getInformationValue() > 0) {
				SocialEquitySetting informationEquitySetting =
					getInformationEquitySetting(actionId, equityActionMapping);

				equitySettings.add(informationEquitySetting);
			}

			if (equityActionMapping.getParticipationValue() > 0) {
				SocialEquitySetting participationEquitySetting =
					getParticipationEquitySetting(
						actionId, equityActionMapping);

				equitySettings.add(participationEquitySetting);
			}
		}
		else if (equitySettings.size() == 1) {
			SocialEquitySetting equitySetting = equitySettings.get(0);

			if ((equityActionMapping.getInformationValue() > 0) &&
				(equitySetting.getType() ==
					SocialEquitySettingConstants.TYPE_PARTICIPATION)) {

				SocialEquitySetting informationEquitySetting =
					getInformationEquitySetting(actionId, equityActionMapping);

				equitySettings.add(informationEquitySetting);
			}
			else if ((equityActionMapping.getParticipationValue() > 0) &&
					 (equitySetting.getType() ==
						SocialEquitySettingConstants.TYPE_INFORMATION)) {

				SocialEquitySetting participationEquitySetting =
					getParticipationEquitySetting(
						actionId, equityActionMapping);

				equitySettings.add(participationEquitySetting);
			}
		}

		_portalCache.put(key, socialEquitySettings);

		return equitySettings;
	}

	public void updateEquitySettings(
			long groupId, String className,
			List<SocialEquityActionMapping> equityActionMappings)
		throws PortalException, SystemException {

		for (SocialEquityActionMapping equityActionMapping :
				equityActionMappings) {

			updateEquitySettings(groupId, className, equityActionMapping);
		}
	}

	protected String encodeKey(long classNameId, String actionId) {
		StringBundler sb = new StringBundler(5);

		sb.append(CACHE_NAME);
		sb.append(StringPool.POUND);
		sb.append(StringUtil.toHexString(classNameId));
		sb.append(StringPool.POUND);
		sb.append(actionId);

		return sb.toString();
	}

	protected SocialEquitySetting getInformationEquitySetting(
		String actionId, SocialEquityActionMapping equityActionMapping) {

		SocialEquitySetting equitySetting = new SocialEquitySettingImpl();

		equitySetting.setActionId(actionId);
		equitySetting.setDailyLimit(
			equityActionMapping.getInformationDailyLimit());
		equitySetting.setLifespan(equityActionMapping.getInformationLifespan());
		equitySetting.setType(SocialEquitySettingConstants.TYPE_INFORMATION);
		equitySetting.setUniqueEntry(equityActionMapping.isUnique());
		equitySetting.setValue(equityActionMapping.getInformationValue());

		return equitySetting;
	}

	protected SocialEquitySetting getParticipationEquitySetting(
		String actionId, SocialEquityActionMapping equityActionMapping) {

		SocialEquitySetting equitySetting = new SocialEquitySettingImpl();

		equitySetting.setActionId(actionId);
		equitySetting.setDailyLimit(
			equityActionMapping.getParticipationDailyLimit());
		equitySetting.setLifespan(
			equityActionMapping.getParticipationLifespan());
		equitySetting.setType(SocialEquitySettingConstants.TYPE_PARTICIPATION);
		equitySetting.setUniqueEntry(
			equityActionMapping.isUnique());
		equitySetting.setValue(equityActionMapping.getParticipationValue());

		return equitySetting;
	}

	protected void updateEquitySettings(
			long groupId, String className,
			SocialEquityActionMapping equityActionMapping)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		List<SocialEquitySetting> equitySettings = getEquitySettings(
			groupId, className, equityActionMapping.getActionId());

		if (equitySettings.isEmpty()) {
			SocialEquitySetting equitySetting = new SocialEquitySettingImpl();

			equitySetting.setActionId(equityActionMapping.getActionId());
			equitySetting.setType(
				SocialEquitySettingConstants.TYPE_INFORMATION);

			equitySettings.add(equitySetting);

			equitySetting = new SocialEquitySettingImpl();

			equitySetting.setActionId(equityActionMapping.getActionId());
			equitySetting.setType(
				SocialEquitySettingConstants.TYPE_PARTICIPATION);

			equitySettings.add(equitySetting);
		}
		else if (equitySettings.size() == 1) {
			if (equitySettings.get(0).getType() ==
					SocialEquitySettingConstants.TYPE_INFORMATION) {

				SocialEquitySetting equitySetting =
					new SocialEquitySettingImpl();

				equitySetting.setActionId(equityActionMapping.getActionId());
				equitySetting.setType(
					SocialEquitySettingConstants.TYPE_PARTICIPATION);

				equitySettings.add(equitySetting);
			}
			else {
				SocialEquitySetting equitySetting =
					new SocialEquitySettingImpl();

				equitySetting.setActionId(equityActionMapping.getActionId());
				equitySetting.setType(
					SocialEquitySettingConstants.TYPE_INFORMATION);

				equitySettings.add(equitySetting);
			}
		}

		for (SocialEquitySetting equitySetting : equitySettings) {
			if (!equityActionMapping.equals(equitySetting)) {
				equitySetting.update(equityActionMapping);

				if (equitySetting.getPrimaryKey() == 0) {
					Group group = groupLocalService.getGroup(groupId);

					equitySetting.setEquitySettingId(
						counterLocalService.increment());
					equitySetting.setGroupId(groupId);
					equitySetting.setCompanyId(group.getCompanyId());
					equitySetting.setClassNameId(classNameId);
				}

				socialEquitySettingPersistence.update(equitySetting, false);
			}
		}

		String key = encodeKey(classNameId, equityActionMapping.getActionId());

		_portalCache.remove(key);
	}

	private static PortalCache _portalCache = MultiVMPoolUtil.getCache(
		CACHE_NAME);

}