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

package com.liferay.knowledge.base.internal.upgrade.v1_1_0;

import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.upgrade.CamelCaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collections;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Peter Shin
 */
public class UpgradePortletPreferences
	extends CamelCaseUpgradePortletPreferences {

	protected Map<String, String> getDefaultPreferencesMap(
		String rootPortletId) {

		if (rootPortletId.equals("1_WAR_knowledgebaseportlet")) {
			return _adminDefaultPreferencesMap;
		}
		else if (rootPortletId.equals("2_WAR_knowledgebaseportlet")) {
			return _displayDefaultPreferencesMap;
		}
		else if (rootPortletId.equals("3_WAR_knowledgebaseportlet")) {
			return _articleDefaultPreferencesMap;
		}

		return Collections.emptyMap();
	}

	protected String getName(String rootPortletId, String oldName) {
		if (rootPortletId.equals("1_WAR_knowledgebaseportlet")) {
			return _oldAdminPreferenceNamesMap.get(oldName);
		}
		else if (rootPortletId.equals("2_WAR_knowledgebaseportlet")) {
			return _oldDisplayPreferenceNamesMap.get(oldName);
		}
		else if (rootPortletId.equals("3_WAR_knowledgebaseportlet")) {
			return _oldArticlePreferenceNamesMap.get(oldName);
		}

		return null;
	}

	@Override
	protected String[] getPortletIds() {
		return _PORTLET_IDS;
	}

	protected String updatePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		PortletPreferences preferences = PortletPreferencesFactoryUtil.fromXML(
			companyId, ownerId, ownerType, plid, portletId, xml);

		Map<String, String[]> preferencesMap = preferences.getMap();

		String rootPortletId = PortletIdCodec.decodePortletName(portletId);

		for (Map.Entry<String, String[]> entry : preferencesMap.entrySet()) {
			String oldName = entry.getKey();

			String newName = getName(rootPortletId, oldName);

			preferences.reset(oldName);

			if (newName != null) {
				preferences.setValues(newName, entry.getValue());
			}
		}

		Map<String, String> defaultPreferencesMap = getDefaultPreferencesMap(
			rootPortletId);

		for (Map.Entry<String, String> entry :
				defaultPreferencesMap.entrySet()) {

			String name = entry.getKey();

			if (preferences.getValues(name, null) == null) {
				preferences.setValues(name, StringUtil.split(entry.getValue()));
			}
		}

		return PortletPreferencesFactoryUtil.toXML(preferences);
	}

	@Override
	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		String preferences = super.upgradePreferences(
			companyId, ownerId, ownerType, plid, portletId, xml);

		return updatePreferences(
			companyId, ownerId, ownerType, plid, portletId, preferences);
	}

	private static final String[] _PORTLET_IDS = {
		"1_WAR_knowledgebaseportlet", "2_WAR_knowledgebaseportlet",
		"3_WAR_knowledgebaseportlet_INSTANCE_%"
	};

	private static final Map<String, String> _adminDefaultPreferencesMap =
		HashMapBuilder.put(
			"enableKBArticleAssetCategories", "false"
		).put(
			"enableKBArticleAssetTags", "true"
		).put(
			"enableKBArticleDescription", "false"
		).put(
			"enableKBArticleKBComments", "true"
		).put(
			"enableKBArticleRatings", "false"
		).put(
			"enableKBArticleViewCountIncrement", "true"
		).put(
			"enableKBStructureKBComments", "true"
		).put(
			"enableKBTemplateKBComments", "true"
		).put(
			"kbArticlesOrderByCol", "priority"
		).put(
			"kbArticlesOrderByType", "desc"
		).put(
			"showKBArticleAssetEntries", "true"
		).put(
			"showKBArticleKBComments", "true"
		).put(
			"showKBStructureKBComments", "true"
		).put(
			"showKBTemplateKBComments", "true"
		).build();
	private static final Map<String, String> _articleDefaultPreferencesMap =
		HashMapBuilder.put(
			"enableKBArticleAssetCategories", "false"
		).put(
			"enableKBArticleAssetTags", "true"
		).put(
			"enableKBArticleDescription", "false"
		).put(
			"enableKBArticleKBComments", "true"
		).put(
			"enableKBArticleRatings", "false"
		).put(
			"enableKBArticleViewCountIncrement", "true"
		).put(
			"resourcePrimKey", "0"
		).put(
			"rssDelta", "20"
		).put(
			"rssDisplayStyle", "full-content"
		).put(
			"rssFormat", "atom10"
		).put(
			"showKBArticleAssetEntries", "true"
		).put(
			"showKBArticleKBComments", "true"
		).build();
	private static final Map<String, String> _displayDefaultPreferencesMap =
		HashMapBuilder.put(
			"enableKBArticleAssetCategories", "false"
		).put(
			"enableKBArticleAssetTags", "true"
		).put(
			"enableKBArticleDescription", "false"
		).put(
			"enableKBArticleKBComments", "true"
		).put(
			"enableKBArticleRatings", "false"
		).put(
			"enableKBArticleViewCountIncrement", "true"
		).put(
			"enableKBTemplateKBComments", "true"
		).put(
			"kbArticlesOrderByCol", "priority"
		).put(
			"kbArticlesOrderByType", "desc"
		).put(
			"rssDelta", "20"
		).put(
			"rssDisplayStyle", "full-content"
		).put(
			"rssFormat", "atom10"
		).put(
			"showKBArticleAssetEntries", "true"
		).put(
			"showKBArticleAuthorColumn", "true"
		).put(
			"showKBArticleCreateDateColumn", "true"
		).put(
			"showKBArticleKBComments", "true"
		).put(
			"showKBArticleModifiedDateColumn", "true"
		).put(
			"showKBArticlePriorityColumn", "true"
		).put(
			"showKBArticleStatusColumn", "true"
		).put(
			"showKBArticleViewsColumn", "true"
		).put(
			"showKBTemplateKBComments", "true"
		).build();
	private static final Map<String, String> _oldAdminPreferenceNamesMap =
		HashMapBuilder.put(
			"articlesOrderByCol", "kbArticlesOrderByCol"
		).put(
			"articlesOrderByType", "kbArticlesOrderByType"
		).put(
			"enableArticleAssetCategories", "enableKBArticleAssetCategories"
		).put(
			"enableArticleAssetTags", "enableKBArticleAssetTags"
		).put(
			"enableArticleComments", "enableKBArticleKBComments"
		).put(
			"enableArticleDescription", "enableKBArticleDescription"
		).put(
			"enableArticleRatings", "enableKBArticleRatings"
		).put(
			"enableArticleViewCountIncrement",
			"enableKBArticleViewCountIncrement"
		).put(
			"enableTemplateComments", "enableKBTemplateKBComments"
		).put(
			"showArticleComments", "showKBArticleKBComments"
		).put(
			"showTemplateComments", "showKBTemplateKBComments"
		).build();
	private static final Map<String, String> _oldArticlePreferenceNamesMap =
		HashMapBuilder.put(
			"enableArticleAssetCategories", "enableKBArticleAssetCategories"
		).put(
			"enableArticleAssetTags", "enableKBArticleAssetTags"
		).put(
			"enableArticleComments", "enableKBArticleKBComments"
		).put(
			"enableArticleDescription", "enableKBArticleDescription"
		).put(
			"enableArticleRatings", "enableKBArticleRatings"
		).put(
			"enableArticleViewCountIncrement",
			"enableKBArticleViewCountIncrement"
		).put(
			"showArticleComments", "showKBArticleKBComments"
		).build();
	private static final Map<String, String> _oldDisplayPreferenceNamesMap =
		HashMapBuilder.put(
			"articlesOrderByCol", "kbArticlesOrderByCol"
		).put(
			"articlesOrderByType", "kbArticlesOrderByType"
		).put(
			"enableArticleAssetCategories", "enableKBArticleAssetCategories"
		).put(
			"enableArticleAssetTags", "enableKBArticleAssetTags"
		).put(
			"enableArticleComments", "enableKBArticleKBComments"
		).put(
			"enableArticleDescription", "enableKBArticleDescription"
		).put(
			"enableArticleRatings", "enableKBArticleRatings"
		).put(
			"enableArticleViewCountIncrement",
			"enableKBArticleViewCountIncrement"
		).put(
			"enableTemplateComments", "enableKBTemplateKBComments"
		).put(
			"showArticleComments", "showKBArticleKBComments"
		).put(
			"showTemplateComments", "showKBTemplateKBComments"
		).build();

}