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

package com.liferay.headless.commerce.admin.order.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.service.CommerceOrderTypeLocalService;
import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.model.CommerceTermEntryRel;
import com.liferay.commerce.term.service.CommerceTermEntryLocalService;
import com.liferay.commerce.term.service.CommerceTermEntryRelLocalService;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.TermOrderType;
import com.liferay.headless.commerce.core.util.DateConfig;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alessio Antonio Rendina
 */
@RunWith(Arquillian.class)
public class TermOrderTypeResourceTest
	extends BaseTermOrderTypeResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			_user.getUserId());

		DateConfig displayDateConfig = DateConfig.toDisplayDateConfig(
			RandomTestUtil.nextDate(), _user.getTimeZone());
		DateConfig expirationDateConfig = DateConfig.toExpirationDateConfig(
			RandomTestUtil.nextDate(), _user.getTimeZone());

		_commerceTermEntry =
			_commerceTermEntryLocalService.addCommerceTermEntry(
				RandomTestUtil.randomString(), _user.getUserId(),
				RandomTestUtil.randomBoolean(),
				RandomTestUtil.randomLocaleStringMap(),
				displayDateConfig.getMonth(), displayDateConfig.getDay(),
				displayDateConfig.getYear(), displayDateConfig.getHour(),
				displayDateConfig.getMinute(), expirationDateConfig.getMonth(),
				expirationDateConfig.getDay(), expirationDateConfig.getYear(),
				expirationDateConfig.getHour(),
				expirationDateConfig.getMinute(), true,
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), RandomTestUtil.nextDouble(),
				RandomTestUtil.randomString(), StringPool.BLANK,
				_serviceContext);
	}

	@Override
	@Test
	public void testDeleteTermOrderType() throws Exception {
	}

	@Override
	@Test
	public void testGraphQLDeleteTermOrderType() throws Exception {
	}

	@Override
	protected Collection<EntityField> getEntityFields() throws Exception {
		return new ArrayList<>();
	}

	@Override
	protected TermOrderType randomTermOrderType() throws Exception {
		DateConfig displayDateConfig = DateConfig.toDisplayDateConfig(
			RandomTestUtil.nextDate(), _user.getTimeZone());
		DateConfig expirationDateConfig = DateConfig.toExpirationDateConfig(
			RandomTestUtil.nextDate(), _user.getTimeZone());

		CommerceOrderType commerceOrderType =
			_commerceOrderTypeLocalService.addCommerceOrderType(
				RandomTestUtil.randomString(), _user.getUserId(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomBoolean(), displayDateConfig.getMonth(),
				displayDateConfig.getDay(), displayDateConfig.getYear(),
				displayDateConfig.getHour(), displayDateConfig.getMinute(), 0,
				expirationDateConfig.getMonth(), expirationDateConfig.getDay(),
				expirationDateConfig.getYear(), expirationDateConfig.getHour(),
				expirationDateConfig.getMinute(), true, _serviceContext);

		return new TermOrderType() {
			{
				orderTypeExternalReferenceCode =
					commerceOrderType.getExternalReferenceCode();
				orderTypeId = commerceOrderType.getCommerceOrderTypeId();
				termExternalReferenceCode =
					_commerceTermEntry.getExternalReferenceCode();
				termId = _commerceTermEntry.getCommerceTermEntryId();
				termOrderTypeId = RandomTestUtil.randomLong();
			}
		};
	}

	@Override
	protected TermOrderType
			testGetTermByExternalReferenceCodeTermOrderTypesPage_addTermOrderType(
				String externalReferenceCode, TermOrderType termOrderType)
		throws Exception {

		return _addTermOrderType(termOrderType);
	}

	@Override
	protected String
			testGetTermByExternalReferenceCodeTermOrderTypesPage_getExternalReferenceCode()
		throws Exception {

		return _commerceTermEntry.getExternalReferenceCode();
	}

	@Override
	protected TermOrderType testGetTermIdTermOrderTypesPage_addTermOrderType(
			Long id, TermOrderType termOrderType)
		throws Exception {

		return _addTermOrderType(termOrderType);
	}

	@Override
	protected Long testGetTermIdTermOrderTypesPage_getId() throws Exception {
		return _commerceTermEntry.getCommerceTermEntryId();
	}

	@Override
	protected TermOrderType
			testPostTermByExternalReferenceCodeTermOrderType_addTermOrderType(
				TermOrderType termOrderType)
		throws Exception {

		return _addTermOrderType(termOrderType);
	}

	@Override
	protected TermOrderType testPostTermIdTermOrderType_addTermOrderType(
			TermOrderType termOrderType)
		throws Exception {

		return _addTermOrderType(termOrderType);
	}

	private TermOrderType _addTermOrderType(TermOrderType termOrderType)
		throws Exception {

		return _toTermOrderType(
			_commerceTermEntryRelLocalService.addCommerceTermEntryRel(
				_user.getUserId(), CommerceOrderType.class.getName(),
				termOrderType.getOrderTypeId(), termOrderType.getTermId()));
	}

	private TermOrderType _toTermOrderType(
			CommerceTermEntryRel commerceTermEntryRel)
		throws Exception {

		CommerceOrderType commerceOrderType =
			_commerceOrderTypeLocalService.getCommerceOrderType(
				commerceTermEntryRel.getClassPK());
		CommerceTermEntry commerceTermEntry =
			_commerceTermEntryLocalService.fetchCommerceTermEntry(
				commerceTermEntryRel.getCommerceTermEntryId());

		return new TermOrderType() {
			{
				orderTypeExternalReferenceCode =
					commerceOrderType.getExternalReferenceCode();
				orderTypeId = commerceOrderType.getCommerceOrderTypeId();
				termExternalReferenceCode =
					commerceTermEntry.getExternalReferenceCode();
				termId = commerceTermEntry.getCommerceTermEntryId();
				termOrderTypeId =
					commerceTermEntryRel.getCommerceTermEntryRelId();
			}
		};
	}

	@Inject
	private CommerceOrderTypeLocalService _commerceOrderTypeLocalService;

	private CommerceTermEntry _commerceTermEntry;

	@Inject
	private CommerceTermEntryLocalService _commerceTermEntryLocalService;

	@Inject
	private CommerceTermEntryRelLocalService _commerceTermEntryRelLocalService;

	private ServiceContext _serviceContext;
	private User _user;

}