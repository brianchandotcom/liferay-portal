/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.snapshot;

import com.liferay.info.item.InfoItemReference;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Adolfo Pérez
 */
public interface TranslationSnapshotProvider {

	public TranslationSnapshot getTranslationSnapshot(
			long groupId, InfoItemReference infoItemReference,
			InputStream inputStream, boolean includeSource)
		throws IOException, PortalException;

}