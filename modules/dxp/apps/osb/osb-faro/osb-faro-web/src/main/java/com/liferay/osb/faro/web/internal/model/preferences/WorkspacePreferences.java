/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.web.internal.model.preferences;

import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class WorkspacePreferences {

	public void addDistributionCardTabPreferences(
		String id, String individualSegmentId,
		DistributionCardTabPreferences distributionCardTabPreferences) {

		DistributionCardTabsPreferences distributionCardTabsPreferences =
			getDistributionCardTabsPreferences(individualSegmentId);

		distributionCardTabsPreferences.addDistributionTab(
			id, distributionCardTabPreferences);
	}

	public String getDefaultChannelId() {
		return _defaultChannelId;
	}

	public DistributionCardTabsPreferences getDistributionCardTabsPreferences(
		String individualSegmentId) {

		if (Validator.isNull(individualSegmentId)) {
			return _individualDashboardPreferences.
				getDistributionCardTabsPreferences();
		}

		IndividualSegmentPreferences individualSegmentPreferences =
			_individualSegmentPreferences.get(individualSegmentId);

		if (individualSegmentPreferences == null) {
			individualSegmentPreferences = new IndividualSegmentPreferences();

			_individualSegmentPreferences.put(
				individualSegmentId, individualSegmentPreferences);
		}

		return individualSegmentPreferences.
			getDistributionCardTabsPreferences();
	}

	public IndividualDashboardPreferences getIndividualDashboardPreferences() {
		return _individualDashboardPreferences;
	}

	public Map<String, IndividualSegmentPreferences>
		getIndividualSegmentPreferences() {

		return _individualSegmentPreferences;
	}

	public boolean isUpgradeModalSeen() {
		return _upgradeModalSeen;
	}

	public void removeDistributionCardTabPreferences(
		String id, String individualSegmentId) {

		DistributionCardTabsPreferences distributionCardTabsPreferences =
			getDistributionCardTabsPreferences(individualSegmentId);

		distributionCardTabsPreferences.removeDistributionTab(id);
	}

	public void removeIndividualSegmentPreference(String individualSegmentId) {
		_individualSegmentPreferences.remove(individualSegmentId);
	}

	public void setDefaultChannelId(String defaultChannelId) {
		_defaultChannelId = defaultChannelId;
	}

	public void setIndividualDashboardPreferences(
		IndividualDashboardPreferences individualDashboardPreferences) {

		_individualDashboardPreferences = individualDashboardPreferences;
	}

	public void setIndividualSegmentPreferences(
		Map<String, IndividualSegmentPreferences>
			individualSegmentPreferences) {

		_individualSegmentPreferences = individualSegmentPreferences;
	}

	public void setUpgradeModalSeen(boolean upgradeModalSeen) {
		_upgradeModalSeen = upgradeModalSeen;
	}

	private String _defaultChannelId;
	private IndividualDashboardPreferences _individualDashboardPreferences =
		new IndividualDashboardPreferences();
	private Map<String, IndividualSegmentPreferences>
		_individualSegmentPreferences = new HashMap<>();
	private boolean _upgradeModalSeen;

}