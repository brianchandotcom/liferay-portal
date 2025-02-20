/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.internal.model.listener;

import com.liferay.asset.kernel.service.AssetVocabularyGroupRelLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(service = ModelListener.class)
public class GroupModelListener extends BaseModelListener<Group> {

	@Override
	public void onAfterRemove(Group group) throws ModelListenerException {
		_assetVocabularyGroupRelLocalService.
			deleteAssetVocabularyGroupRelsByGroupId(group.getGroupId());
	}

	@Reference
	private AssetVocabularyGroupRelLocalService
		_assetVocabularyGroupRelLocalService;

}