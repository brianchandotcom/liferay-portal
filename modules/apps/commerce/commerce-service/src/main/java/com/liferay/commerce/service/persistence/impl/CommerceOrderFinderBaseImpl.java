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

package com.liferay.commerce.service.persistence.impl;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.persistence.CommerceOrderPersistence;
import com.liferay.commerce.service.persistence.impl.constants.CommercePersistenceConstants;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
public abstract class CommerceOrderFinderBaseImpl
	extends BasePersistenceImpl<CommerceOrder> {

	public CommerceOrderFinderBaseImpl() {
		setModelClass(CommerceOrder.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put(
			"deliveryCommerceTermEntryDescription",
			"deliveryCTermEntryDescription");
		dbColumnNames.put(
			"paymentCommerceTermEntryDescription",
			"paymentCTermEntryDescription");
		dbColumnNames.put(
			"shippingDiscountPercentageLevel1",
			"shippingDiscountPercentLevel1");
		dbColumnNames.put(
			"shippingDiscountPercentageLevel2",
			"shippingDiscountPercentLevel2");
		dbColumnNames.put(
			"shippingDiscountPercentageLevel3",
			"shippingDiscountPercentLevel3");
		dbColumnNames.put(
			"shippingDiscountPercentageLevel4",
			"shippingDiscountPercentLevel4");
		dbColumnNames.put(
			"shippingDiscountPercentageLevel1WithTaxAmount",
			"shippingDiscountPctLev1WithTax");
		dbColumnNames.put(
			"shippingDiscountPercentageLevel2WithTaxAmount",
			"shippingDiscountPctLev2WithTax");
		dbColumnNames.put(
			"shippingDiscountPercentageLevel3WithTaxAmount",
			"shippingDiscountPctLev3WithTax");
		dbColumnNames.put(
			"shippingDiscountPercentageLevel4WithTaxAmount",
			"shippingDiscountPctLev4WithTax");
		dbColumnNames.put(
			"subtotalDiscountPercentageLevel1",
			"subtotalDiscountPercentLevel1");
		dbColumnNames.put(
			"subtotalDiscountPercentageLevel2",
			"subtotalDiscountPercentLevel2");
		dbColumnNames.put(
			"subtotalDiscountPercentageLevel3",
			"subtotalDiscountPercentLevel3");
		dbColumnNames.put(
			"subtotalDiscountPercentageLevel4",
			"subtotalDiscountPercentLevel4");
		dbColumnNames.put(
			"subtotalDiscountPercentageLevel1WithTaxAmount",
			"subtotalDiscountPctLev1WithTax");
		dbColumnNames.put(
			"subtotalDiscountPercentageLevel2WithTaxAmount",
			"subtotalDiscountPctLev2WithTax");
		dbColumnNames.put(
			"subtotalDiscountPercentageLevel3WithTaxAmount",
			"subtotalDiscountPctLev3WithTax");
		dbColumnNames.put(
			"subtotalDiscountPercentageLevel4WithTaxAmount",
			"subtotalDiscountPctLev4WithTax");
		dbColumnNames.put(
			"totalDiscountPercentageLevel1WithTaxAmount",
			"totalDiscountPctLev1WithTax");
		dbColumnNames.put(
			"totalDiscountPercentageLevel2WithTaxAmount",
			"totalDiscountPctLev2WithTax");
		dbColumnNames.put(
			"totalDiscountPercentageLevel3WithTaxAmount",
			"totalDiscountPctLev3WithTax");
		dbColumnNames.put(
			"totalDiscountPercentageLevel4WithTaxAmount",
			"totalDiscountPctLev4WithTax");

		setDBColumnNames(dbColumnNames);
	}

	@Override
	public Set<String> getBadColumnNames() {
		return commerceOrderPersistence.getBadColumnNames();
	}

	@Override
	@Reference(
		target = CommercePersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = CommercePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = CommercePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected CommerceOrderPersistence commerceOrderPersistence;

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderFinderBaseImpl.class);

}