/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.rss.web.internal.upgrade.v3_0_2.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portlet.display.template.test.util.BaseUpgradePortletPreferencesTestCase;
import com.liferay.rss.constants.RSSPortletKeys;

import org.junit.runner.RunWith;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class UpgradePortletPreferencesTest
	extends BaseUpgradePortletPreferencesTestCase {

	@Override
	protected String getPortletId() {
		return RSSPortletKeys.RSS;
	}

	@Override
	protected UpgradeStepRegistrator getUpgradeStepRegistrator() {
		return _upgradeStepRegistrator;
	}

	@Override
	protected Version getVersion() {
		return _VERSION;
	}

	private static final Version _VERSION = new Version(3, 0, 2);

	@Inject(
		filter = "(&(component.name=com.liferay.rss.web.internal.upgrade.registry.RSSWebUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

}