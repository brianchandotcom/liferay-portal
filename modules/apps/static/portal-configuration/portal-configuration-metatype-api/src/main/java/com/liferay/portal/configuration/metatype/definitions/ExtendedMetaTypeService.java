/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.configuration.metatype.definitions;

import org.osgi.framework.Bundle;
import org.osgi.service.metatype.MetaTypeService;

/**
 * @author Iván Zaera
 */
public interface ExtendedMetaTypeService extends MetaTypeService {

	@Override
	public ExtendedMetaTypeInformation getMetaTypeInformation(Bundle bundle);

}