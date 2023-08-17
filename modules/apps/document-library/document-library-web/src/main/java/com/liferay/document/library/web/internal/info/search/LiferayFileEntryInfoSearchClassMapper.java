/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.info.search;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.info.search.InfoSearchClassMapper;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;

import org.osgi.service.component.annotations.Component;

/**
 * @author Roberto Díaz
 */
@Component(service = InfoSearchClassMapper.class)
public class LiferayFileEntryInfoSearchClassMapper
	implements InfoSearchClassMapper<LiferayFileEntry> {

	@Override
	public String getSearchClassName() {
		return DLFileEntry.class.getName();
	}

}