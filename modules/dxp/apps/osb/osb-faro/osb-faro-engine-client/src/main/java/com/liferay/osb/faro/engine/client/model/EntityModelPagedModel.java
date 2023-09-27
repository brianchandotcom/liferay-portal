/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model;

import org.springframework.hateoas.EntityModel;

/**
 * @author Matthew Kong
 */
public class EntityModelPagedModel<T> extends PagedModel<EntityModel<T>, T> {

	@Override
	public T processContent(EntityModel<T> entityModel) {
		return entityModel.getContent();
	}

}