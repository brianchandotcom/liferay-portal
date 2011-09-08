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
 * The social equity setting local service. This service is responsible for
 * retrieving and updating the settings for social equity actions.
 *
 * <p>
 * This service stores settings for social equity actions in the database. Only
 * settings that are different from the default ones are stored. When reading
 * settings, this service reads user settings from the database and merges them
 * with the default values.
 * </p>
 *
 * <p>
 * Social equity actions are identified by an action ID and a
 * <code>className</code> that is the model name of the asset on which the
 * action can be executed.
 * </p>
 *
 * @author Zsolt Berentey
 * @author Brian Wing Shun Chan
 */
public class SocialEquitySettingLocalServiceImpl
	extends SocialEquitySettingLocalServiceBaseImpl {

	/**
	 * Returns all the settings for the social equity action.
	 *
	 * @param  groupId the primary key of the group
	 * @param  className the class name for the target asset
	 * @param  actionId the ID of the action
	 * @return the settings for the social equity action
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquitySetting> getEquitySettings(
			long groupId, String className, String actionId)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getEquitySettings(groupId, classNameId, actionId);
	}

	/**
	 * Returns all the settings for the social equity action.
	 *
	 * @param  groupId the primary key of the group
	 * @param  classNameId the ID of the target asset's class
	 * @param  actionId the ID of the action
	 * @return the settings for the social equity action
	 * @throws SystemException if a system exception occurred
	 */
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

	/**
	 * Updates settings for the model (asset type) in the group.
	 *
	 * <p>
	 * This method accepts a list of social equity action mappings. A
	 * <code>SocialEquityActionMapping</code> contains both participation and
	 * information settings for an action. The
	 * <code>SocialEquityActionMapping</code> class is used by the portal to
	 * store the default settings for social equity actions.
	 * </p>
	 *
	 * @param  groupId the primary key of the group
	 * @param  className the class name of the target asset
	 * @param  equityActionMappings the equity action mappings containing the
	 *         settings to be stored
	 * @throws PortalException if the group could not be found
	 * @throws SystemException if a system exception occurred
	 */
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

		sb.append(_CACHE_NAME);
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

	private static final String _CACHE_NAME =
		SocialEquitySettingLocalServiceImpl.class.getName();

	private static PortalCache _portalCache = MultiVMPoolUtil.getCache(
		_CACHE_NAME);

}