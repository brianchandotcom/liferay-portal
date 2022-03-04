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
import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.service.CommerceTermEntryLocalService;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Term;
import com.liferay.headless.commerce.core.util.DateConfig;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alessio Antonio Rendina
 */
@RunWith(Arquillian.class)
public class TermResourceTest extends BaseTermResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			_user.getUserId());
	}

	@Override
	@Test
	public void testGetTermsPageWithSortString() throws Exception {
	}

	@Override
	@Test
	public void testGraphQLGetTermNotFound() throws Exception {
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"active", "name"};
	}

	@Override
	protected Term randomPatchTerm() throws Exception {
		return randomTerm();
	}

	@Override
	protected Term randomTerm() {
		return new Term() {
			{
				active = RandomTestUtil.randomBoolean();
				description = LanguageUtils.getLanguageIdMap(
					RandomTestUtil.randomLocaleStringMap());
				displayDate = RandomTestUtil.nextDate();
				expirationDate = RandomTestUtil.nextDate();
				externalReferenceCode = RandomTestUtil.randomString();
				id = RandomTestUtil.nextLong();
				label = LanguageUtils.getLanguageIdMap(
					RandomTestUtil.randomLocaleStringMap());
				name = FriendlyURLNormalizerUtil.normalize(
					RandomTestUtil.randomString());
				priority = RandomTestUtil.randomDouble();
				type = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	@Override
	protected Term testDeleteTerm_addTerm() throws Exception {
		return _addTerm(randomTerm());
	}

	@Override
	protected Term testDeleteTermByExternalReferenceCode_addTerm()
		throws Exception {

		return _addTerm(randomTerm());
	}

	@Override
	protected Term testGetTerm_addTerm() throws Exception {
		return _addTerm(randomTerm());
	}

	@Override
	protected Term testGetTermByExternalReferenceCode_addTerm()
		throws Exception {

		return _addTerm(randomTerm());
	}

	@Override
	protected Term testGetTermsPage_addTerm(Term term) throws Exception {
		return _addTerm(term);
	}

	@Override
	protected Term testGraphQLTerm_addTerm() throws Exception {
		return _addTerm(randomTerm());
	}

	@Override
	protected Term testPatchTerm_addTerm() throws Exception {
		return _addTerm(randomTerm());
	}

	@Override
	protected Term testPatchTermByExternalReferenceCode_addTerm()
		throws Exception {

		return _addTerm(randomTerm());
	}

	@Override
	protected Term testPostTerm_addTerm(Term term) throws Exception {
		return _addTerm(term);
	}

	private Term _addTerm(Term term) throws Exception {
		DateConfig displayDateConfig = DateConfig.toDisplayDateConfig(
			term.getDisplayDate(), _user.getTimeZone());
		DateConfig expirationDateConfig = DateConfig.toExpirationDateConfig(
			term.getExpirationDate(), _user.getTimeZone());

		CommerceTermEntry commerceTermEntry =
			_commerceTermEntryLocalService.addCommerceTermEntry(
				term.getExternalReferenceCode(), _user.getUserId(),
				GetterUtil.getBoolean(term.getActive()),
				LanguageUtils.getLocalizedMap(term.getDescription()),
				displayDateConfig.getMonth(), displayDateConfig.getDay(),
				displayDateConfig.getYear(), displayDateConfig.getHour(),
				displayDateConfig.getMinute(), expirationDateConfig.getMonth(),
				expirationDateConfig.getDay(), expirationDateConfig.getYear(),
				expirationDateConfig.getHour(),
				expirationDateConfig.getMinute(),
				GetterUtil.getBoolean(term.getNeverExpire(), true),
				LanguageUtils.getLocalizedMap(term.getLabel()), term.getName(),
				GetterUtil.getDouble(term.getPriority()), term.getType(),
				term.getTypeSettings(), _serviceContext);

		_commerceTermEntries.add(commerceTermEntry);

		return _toTerm(commerceTermEntry);
	}

	private Term _toTerm(CommerceTermEntry commerceTermEntry) {
		return new Term() {
			{
				active = commerceTermEntry.isActive();
				description = commerceTermEntry.getLanguageIdToDescriptionMap();
				displayDate = commerceTermEntry.getDisplayDate();
				expirationDate = commerceTermEntry.getExpirationDate();
				externalReferenceCode =
					commerceTermEntry.getExternalReferenceCode();
				id = commerceTermEntry.getCommerceTermEntryId();
				label = commerceTermEntry.getLanguageIdToLabelMap();
				name = commerceTermEntry.getName();
				priority = commerceTermEntry.getPriority();
				type = commerceTermEntry.getType();
			}
		};
	}

	@DeleteAfterTestRun
	private final List<CommerceTermEntry> _commerceTermEntries =
		new ArrayList<>();

	@Inject
	private CommerceTermEntryLocalService _commerceTermEntryLocalService;

	private ServiceContext _serviceContext;
	private User _user;

}