/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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

	public java.util.List<com.liferay.commerce.product.model.CPDefinition> findByExpirationDate(
		java.util.Date expirationDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.commerce.product.model.CPDefinition> queryDefinition);

	public java.util.List<com.liferay.commerce.product.model.CPDefinition> findByG_P_S(
		long groupId, String productTypeName, String languageId,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.commerce.product.model.CPDefinition> queryDefinition);
}