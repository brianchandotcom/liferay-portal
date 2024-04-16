/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_4_x;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Luis Ortiz
 */
public class UpgradeRelease extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeReleaseVerifiedStatus();
		_upgradeRemoveReleaseEntry();
	}

	private void _upgradeReleaseVerifiedStatus() throws Exception {
		StringBundler sb = new StringBundler(
			"update Release_ set verified = [$FALSE$] where " +
				"servletContextName not in (");

		for (String verifiedServletContextName : _verifiedServletContextNames) {
			sb.append(StringPool.APOSTROPHE);
			sb.append(verifiedServletContextName);
			sb.append(StringPool.APOSTROPHE + StringPool.COMMA);
		}

		sb.setIndex(sb.index() - 1);

		sb.append(StringPool.APOSTROPHE + StringPool.CLOSE_PARENTHESIS);

		runSQL(sb.toString());
	}

	private void _upgradeRemoveReleaseEntry() throws Exception {
		runSQL(
			"delete from Release_ where servletContextName = " +
				"'com.liferay.notifications.service'");
	}

	private static final List<String> _verifiedServletContextNames =
		new ArrayList<>(
			Arrays.asList(
				"com.liferay.adaptive.media.document.library.thumbnails",
				"com.liferay.blogs.service", "com.liferay.bookmarks.service",
				"com.liferay.commerce.price.list.service",
				"com.liferay.commerce.product.service",
				"com.liferay.commerce.service", "com.liferay.depot.service",
				"com.liferay.document.library.google.docs",
				"com.liferay.document.library.service",
				"com.liferay.document.library.video",
				"com.liferay.dynamic.data.mapping.service",
				"com.liferay.frontend.js.a11y.web",
				"com.liferay.message.boards.service",
				"com.liferay.organizations.service",
				"com.liferay.portal.lock.service",
				"com.liferay.portal.search.tuning.rankings.web",
				"com.liferay.portal.search.tuning.synonyms.web",
				"com.liferay.portal.security.service.access.policy.service",
				"com.liferay.portal.security.sso.facebook.connect",
				"com.liferay.portal.security.sso.opensso",
				"com.liferay.portal.workflow.kaleo.designer.web",
				"com.liferay.search.experiences.service",
				"com.liferay.wiki.service", "portal"));

}