/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.model.attributes.provider;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;

import java.util.Map;

/**
 * @author Riccardo Alberti
 */
public interface CommerceModelAttributesProvider {

	public Map<String, Object> getModelAttributes(
		BaseModel<?> baseModel, DTOConverter<?, ?> dtoConverter, long userId);

}