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

package com.liferay.shopping.service.persistence;

import aQute.bnd.annotation.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface ShoppingCategoryFinder {
	public int countC_I_ByG_C(long groupId, long categoryId);

	public int filterCountC_I_ByG_C(long groupId, long categoryId);

	public java.util.List<java.lang.Object> filterFindC_I_ByG_C(long groupId,
		long categoryId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition);

	public java.util.List<java.lang.Object> findC_I_ByG_C(long groupId,
		long categoryId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition);
}