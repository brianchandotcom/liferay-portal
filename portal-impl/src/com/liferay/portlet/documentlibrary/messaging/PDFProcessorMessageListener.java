/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.documentlibrary.messaging;

import com.liferay.document.library.kernel.util.PDFProcessorUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;

/**
 * @author Alexander Chow
 */
public class PDFProcessorMessageListener extends BaseProcessorMessageListener {

	@Override
	protected void generate(
			FileVersion sourceFileVersion, FileVersion destinationFileVersion)
		throws Exception {

		if (CTCollectionThreadLocal.isProductionMode() ||
			!(destinationFileVersion instanceof LiferayFileVersion)) {

			PDFProcessorUtil.generateImages(
				sourceFileVersion, destinationFileVersion);

			return;
		}

		LiferayFileVersion liferayFileVersion =
			(LiferayFileVersion)destinationFileVersion;

		long ctCollectionId = liferayFileVersion.getCTCollectionId();

		if (ctCollectionId == CTCollectionThreadLocal.getCTCollectionId()) {
			PDFProcessorUtil.generateImages(
				sourceFileVersion, destinationFileVersion);
		}
		else {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						ctCollectionId)) {

				PDFProcessorUtil.generateImages(
					sourceFileVersion, destinationFileVersion);
			}
		}
	}

}