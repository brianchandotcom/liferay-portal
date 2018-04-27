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

package com.liferay.commerce.product.service.persistence;

import aQute.bnd.annotation.ProviderType;

/**
 * @author Marco Leo
 * @generated
 */
@ProviderType
public interface CPDefinitionFinder {
	public int countByG_P_S(long groupId, String productTypeName,
		String languageId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.commerce.product.model.CPDefinition> queryDefinition);

	public int filterCountByG_P_S(long groupId, String productTypeName,
		String languageId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.commerce.product.model.CPDefinition> queryDefinition);

	public java.util.List<com.liferay.commerce.product.model.CPDefinition> filterFindByG_P_S(
		long groupId, String productTypeName, String languageId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.commerce.product.model.CPDefinition> queryDefinition);

	public java.util.List<com.liferay.commerce.product.model.CPDefinition> findByExpirationDate(
		java.util.Date expirationDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.commerce.product.model.CPDefinition> queryDefinition);

	public java.util.List<com.liferay.commerce.product.model.CPDefinition> findByG_P_S(
		long groupId, String productTypeName, String languageId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.commerce.product.model.CPDefinition> queryDefinition);
}