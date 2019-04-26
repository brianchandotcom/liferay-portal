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
	immediate = true,
	property = "indexer.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
	service = ExpandoBridgeRetriever.class
)
public class DLFileEntryExpandoBridgeRetriever
	implements ExpandoBridgeRetriever {

	@Override
	public ExpandoBridge getExpandoBridge(BaseModel baseModel) {
		try {
			DLFileEntry dlFileEntry = (DLFileEntry)baseModel;

			DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

			return expandoBridgeFactory.getExpandoBridge(
				dlFileEntry.getCompanyId(), DLFileEntry.class.getName(),
				dlFileVersion.getFileVersionId());
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	@Reference
	protected ExpandoBridgeFactory expandoBridgeFactory;

}