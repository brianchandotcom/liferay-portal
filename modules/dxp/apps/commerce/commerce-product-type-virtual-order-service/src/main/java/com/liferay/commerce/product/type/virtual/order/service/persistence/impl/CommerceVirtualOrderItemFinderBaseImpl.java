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

package com.liferay.commerce.product.type.virtual.order.service.persistence.impl;

import com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem;
import com.liferay.commerce.product.type.virtual.order.service.persistence.CommerceVirtualOrderItemPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceVirtualOrderItemFinderBaseImpl extends BasePersistenceImpl<CommerceVirtualOrderItem> {
	public CommerceVirtualOrderItemFinderBaseImpl() {
		setModelClass(CommerceVirtualOrderItem.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("uuid", "uuid_");
			dbColumnNames.put("active", "active_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	@Override
	public Set<String> getBadColumnNames() {
		return getCommerceVirtualOrderItemPersistence().getBadColumnNames();
	}

	/**
	 * Returns the commerce virtual order item persistence.
	 *
	 * @return the commerce virtual order item persistence
	 */
	public CommerceVirtualOrderItemPersistence getCommerceVirtualOrderItemPersistence() {
		return commerceVirtualOrderItemPersistence;
	}

	/**
	 * Sets the commerce virtual order item persistence.
	 *
	 * @param commerceVirtualOrderItemPersistence the commerce virtual order item persistence
	 */
	public void setCommerceVirtualOrderItemPersistence(
		CommerceVirtualOrderItemPersistence commerceVirtualOrderItemPersistence) {
		this.commerceVirtualOrderItemPersistence = commerceVirtualOrderItemPersistence;
	}

	@BeanReference(type = CommerceVirtualOrderItemPersistence.class)
	protected CommerceVirtualOrderItemPersistence commerceVirtualOrderItemPersistence;
	private static final Log _log = LogFactoryUtil.getLog(CommerceVirtualOrderItemFinderBaseImpl.class);
}