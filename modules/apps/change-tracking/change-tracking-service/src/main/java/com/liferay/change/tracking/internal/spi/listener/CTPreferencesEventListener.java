/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.spi.listener;

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.change.tracking.spi.listener.CTEventListener;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(service = CTEventListener.class)
public class CTPreferencesEventListener implements CTEventListener {

	@Override
	public void onAfterPublish(long ctCollectionId) {
		if (FeatureFlagManagerUtil.isEnabled("LPD-39203")) {
			_ctPreferencesLocalService.resetCTPreferences(ctCollectionId);

			if (_log.isInfoEnabled()) {
				CTCollection ctCollection =
					_ctCollectionLocalService.fetchCTCollection(ctCollectionId);

				if (ctCollection != null) {
					_log.info(
						StringBundler.concat(
							"Publication ", ctCollection.getName(),
							" has been published. Production is live."));
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTPreferencesEventListener.class);

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTPreferencesLocalService _ctPreferencesLocalService;

}