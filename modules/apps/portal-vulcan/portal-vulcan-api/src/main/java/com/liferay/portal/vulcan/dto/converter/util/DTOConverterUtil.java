/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.dto.converter.util;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;

/**
 * @author Joao Victor Alves
 */
public class DTOConverterUtil {

	public static <E extends BaseModel<?>, D> Long getModelPrimaryKey(
			DTOConverter<E, D> dtoConverter, String externalReferenceCode)
		throws Exception {

		E baseModel = dtoConverter.getObject(externalReferenceCode);

		return (Long)baseModel.getPrimaryKeyObj();
	}

}