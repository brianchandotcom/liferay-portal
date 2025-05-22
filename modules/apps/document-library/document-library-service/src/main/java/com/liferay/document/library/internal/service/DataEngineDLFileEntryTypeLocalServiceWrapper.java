/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.service;

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalServiceWrapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceWrapper;

import java.util.Collections;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(service = ServiceWrapper.class)
public class DataEngineDLFileEntryTypeLocalServiceWrapper
	extends DLFileEntryTypeLocalServiceWrapper {

	@Override
	public DLFileEntryType deleteFileEntryType(DLFileEntryType dlFileEntryType)
		throws PortalException {

		dlFileEntryType = super.deleteFileEntryType(dlFileEntryType);

		updateDDMStructureLinks(
			dlFileEntryType.getFileEntryTypeId(), Collections.emptySet());

		return dlFileEntryType;
	}

}