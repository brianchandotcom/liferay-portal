/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.internal.upgrade.v1_3_5;

import com.liferay.portal.kernel.upgrade.BaseLastPublishDateUpgradeProcess;

/**
 * @author Máté Thurzó
 */
public class UpgradeLastPublishDate extends BaseLastPublishDateUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		alterTableAddColumn("KBArticle", "lastPublishDate", "DATE null");

		updateLastPublishDates("1_WAR_knowledgebaseportlet", "KBArticle");

		alterTableAddColumn("KBComment", "lastPublishDate", "DATE null");

		updateLastPublishDates("1_WAR_knowledgebaseportlet", "KBComment");

		alterTableAddColumn("KBFolder", "lastPublishDate", "DATE null");

		updateLastPublishDates("1_WAR_knowledgebaseportlet", "KBFolder");

		alterTableAddColumn("KBTemplate", "lastPublishDate", "DATE null");

		updateLastPublishDates("1_WAR_knowledgebaseportlet", "KBTemplate");
	}

}