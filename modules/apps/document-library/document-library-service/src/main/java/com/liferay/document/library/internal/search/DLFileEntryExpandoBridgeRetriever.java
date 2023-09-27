/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.search;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.search.spi.model.index.contributor.ExpandoBridgeRetriever;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	property = "indexer.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
	service = ExpandoBridgeRetriever.class
)
public class DLFileEntryExpandoBridgeRetriever
	implements ExpandoBridgeRetriever {

	@Override
	public ExpandoBridge getExpandoBridge(BaseModel<?> baseModel) {
		try {
			DLFileEntry dlFileEntry = (DLFileEntry)baseModel;

			DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

			return expandoBridgeFactory.getExpandoBridge(
				dlFileEntry.getCompanyId(), DLFileEntry.class.getName(),
				dlFileVersion.getFileVersionId());
		}
		catch (PortalException portalException) {
			throw new SystemException(portalException);
		}
	}

	@Reference
	protected ExpandoBridgeFactory expandoBridgeFactory;

}