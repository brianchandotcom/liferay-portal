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