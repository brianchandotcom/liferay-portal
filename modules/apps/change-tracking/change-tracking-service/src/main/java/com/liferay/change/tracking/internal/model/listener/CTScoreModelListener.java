/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.model.listener;

import com.liferay.change.tracking.constants.PublicationRoleConstants;
import com.liferay.change.tracking.internal.helper.CTUserNotificationHelper;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTScore;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gislayne Vitorino
 */
@Component(service = ModelListener.class)
public class CTScoreModelListener extends BaseModelListener<CTScore> {

	@Override
	public void onAfterUpdate(CTScore originalCTScore, CTScore ctScore)
		throws ModelListenerException {

		if (originalCTScore.getScore() != ctScore.getScore()) {
			long ctCollectionId = ctScore.getCtCollectionId();

			try {
				CTCollection ctCollection =
					_ctCollectionLocalService.getCTCollection(ctCollectionId);

				Set<Long> userIds = SetUtil.fromArray(
					_ctUserNotificationHelper.getPublicationRoleUserIds(
						ctCollection, true, PublicationRoleConstants.NAME_ADMIN,
						PublicationRoleConstants.NAME_PUBLISHER));

				_ctUserNotificationHelper.sendUserNotificationEvents(
					ctCollection,
					JSONUtil.put(
						"ctCollectionId", ctCollectionId
					).put(
						"notificationType",
						UserNotificationDefinition.
							NOTIFICATION_TYPE_UPDATE_ENTRY
					).put(
						"originalScore", originalCTScore.getScore()
					).put(
						"score", ctScore.getScore()
					),
					ArrayUtil.toLongArray(userIds));
			}
			catch (PortalException portalException) {
				_log.error(
					"Unable to send user notification events", portalException);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTScoreModelListener.class);

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTUserNotificationHelper _ctUserNotificationHelper;

}