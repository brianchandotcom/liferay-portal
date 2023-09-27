/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.taglib.util;

import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Adolfo Pérez
 */
public class InfoItemObjectProviderUtil {

	public static Object getInfoItem(String className, long classPK) {
		try {
			InfoItemServiceRegistry infoItemServiceRegistry =
				_infoItemServiceRegistrySnapshot.get();

			if (infoItemServiceRegistry == null) {
				return null;
			}

			InfoItemObjectProvider<Object> infoItemObjectProvider =
				infoItemServiceRegistry.getFirstInfoItemService(
					InfoItemObjectProvider.class, className,
					ClassPKInfoItemIdentifier.INFO_ITEM_SERVICE_FILTER);

			return infoItemObjectProvider.getInfoItem(
				new ClassPKInfoItemIdentifier(classPK));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		InfoItemObjectProviderUtil.class);

	private static final Snapshot<InfoItemServiceRegistry>
		_infoItemServiceRegistrySnapshot = new Snapshot<>(
			InfoItemObjectProviderUtil.class, InfoItemServiceRegistry.class,
			null, true);

}