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

package com.liferay.portal.kernel.upgrade;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.upgrade.util.TranslationUpdater;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;

import java.util.Collection;
import java.util.List;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseUpgradeTranslations extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		List<Group> groups = GroupLocalServiceUtil.getGroups(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (Group group : groups) {
			for (TranslationUpdater translationUpdater :
					getTranslationUpdaters()) {

				translationUpdater.update(
					group.getCompanyId(), group.getGroupId());
			}
		}
	}

	protected abstract Collection<TranslationUpdater> getTranslationUpdaters();

}