/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.spi.listener;

import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.spi.listener.CTEventListener;
import com.liferay.portal.kernel.model.VirtualHost;
import com.liferay.portal.kernel.service.persistence.LayoutSetPersistence;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(service = CTEventListener.class)
public class VirtualHostCTEventListener implements CTEventListener {

	@Override
	public void onAfterPublish(long ctCollectionId) {
		if (_ctEntryLocalService.hasCTEntries(
				ctCollectionId,
				_portal.getClassNameId(VirtualHost.class.getName()))) {

			_layoutSetPersistence.clearCache();
		}
	}

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private LayoutSetPersistence _layoutSetPersistence;

	@Reference
	private Portal _portal;

}