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

package com.liferay.analytics.settings.rest.resource.v1_0.test;

import com.liferay.analytics.settings.rest.client.dto.v1_0.Field;
import com.liferay.analytics.settings.rest.client.pagination.Page;
import com.liferay.analytics.settings.rest.client.pagination.Pagination;
import com.liferay.analytics.settings.rest.constants.FieldAccountConstants;
import com.liferay.analytics.settings.rest.constants.FieldOrderConstants;
import com.liferay.analytics.settings.rest.constants.FieldPeopleConstants;
import com.liferay.analytics.settings.rest.constants.FieldProductConstants;
import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.test.rule.Inject;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Riccardo Ferrari
 */
@RunWith(Arquillian.class)
public class FieldResourceTest extends BaseFieldResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		try {
			_analyticsSettingsManager.deleteCompanyConfiguration(
				TestPropsValues.getCompanyId());
		}
		catch (Exception exception) {
		}
	}

	@Override
	@Test
	public void testGetFieldsAccountsPage() throws Exception {
		Page<Field> totalPage = fieldResource.getFieldsAccountsPage(
			null, null, null);

		int totalCount = GetterUtil.getInteger(totalPage.getTotalCount());

		Assert.assertEquals(
			FieldAccountConstants.FIELD_ACCOUNT_NAMES.length, totalCount);
	}

	@Ignore
	@Override
	@Test
	public void testGetFieldsAccountsPageWithPagination() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGetFieldsAccountsPageWithSortDateTime() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGetFieldsAccountsPageWithSortDouble() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGetFieldsAccountsPageWithSortInteger() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGetFieldsAccountsPageWithSortString() throws Exception {
	}

	@Override
	@Test
	public void testGetFieldsOrdersPage() throws Exception {
		Page<Field> totalPage = fieldResource.getFieldsOrdersPage(
			null, null, null);

		int totalCount = GetterUtil.getInteger(totalPage.getTotalCount());

		Assert.assertEquals(
			FieldOrderConstants.FIELD_ORDER_NAMES.length +
				FieldOrderConstants.FIELD_ORDER_ITEM_NAMES.length,
			totalCount);
	}

	@Ignore
	@Override
	@Test
	public void testGetFieldsOrdersPageWithPagination() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGetFieldsOrdersPageWithSortDateTime() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGetFieldsOrdersPageWithSortDouble() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGetFieldsOrdersPageWithSortInteger() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGetFieldsOrdersPageWithSortString() throws Exception {
	}

	@Override
	@Test
	public void testGetFieldsPeoplePage() throws Exception {
		Page<Field> totalPage = fieldResource.getFieldsPeoplePage(
			null, null, null);

		int totalCount = GetterUtil.getInteger(totalPage.getTotalCount());

		Assert.assertEquals(
			FieldPeopleConstants.FIELD_CONTACT_NAMES.length +
				FieldPeopleConstants.FIELD_USER_NAMES.length,
			totalCount);
	}

	@Ignore
	@Override
	@Test
	public void testGetFieldsPeoplePageWithPagination() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGetFieldsPeoplePageWithSortDateTime() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGetFieldsPeoplePageWithSortDouble() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGetFieldsPeoplePageWithSortInteger() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGetFieldsPeoplePageWithSortString() throws Exception {
	}

	@Override
	@Test
	public void testGetFieldsProductsPage() throws Exception {
		Page<Field> totalPage = fieldResource.getFieldsProductsPage(
			null, null, null);

		int totalCount = GetterUtil.getInteger(totalPage.getTotalCount());

		Assert.assertEquals(
			FieldProductConstants.FIELD_CATEGORY_NAMES.length +
				FieldProductConstants.FIELD_PRODUCT_NAMES.length +
					FieldProductConstants.FIELD_PRODUCT_CHANNEL_NAMES.length,
			totalCount);
	}

	@Ignore
	@Override
	@Test
	public void testGetFieldsProductsPageWithPagination() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGetFieldsProductsPageWithSortDateTime() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGetFieldsProductsPageWithSortDouble() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGetFieldsProductsPageWithSortInteger() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGetFieldsProductsPageWithSortString() throws Exception {
	}

	@Override
	@Test
	public void testPatchFieldAccount() throws Exception {
		fieldResource.patchFieldAccount(
			new Field[] {_getField("externalReferenceCode", true, "account")});

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS, 1, TimeUnit.SECONDS,
			() -> {
				try {
					Page<Field> fieldsAccountsPage =
						fieldResource.getFieldsAccountsPage(null, null, null);

					int selectedCount = 0;

					for (Field field : fieldsAccountsPage.getItems()) {
						if (field.getSelected()) {
							selectedCount += 1;
						}
					}

					Assert.assertEquals(
						FieldAccountConstants.FIELD_ACCOUNT_REQUIRED_NAMES.
							length + 1,
						selectedCount);
				}
				catch (Exception exception) {
					throw new RuntimeException(exception);
				}

				return null;
			});

		fieldResource.patchFieldAccount(
			Stream.of(
				FieldAccountConstants.FIELD_ACCOUNT_NAMES
			).map(
				name -> _getField(name, true, "account")
			).toArray(
				Field[]::new
			));

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS, 1, TimeUnit.SECONDS,
			() -> {
				try {
					Page<Field> fieldsAccountsPage =
						fieldResource.getFieldsAccountsPage(
							null, Pagination.of(1, 100), null);

					int selectedCount = 0;

					for (Field field : fieldsAccountsPage.getItems()) {
						if (field.getSelected()) {
							selectedCount += 1;
						}
					}

					Assert.assertEquals(
						FieldAccountConstants.FIELD_ACCOUNT_NAMES.length,
						selectedCount);
				}
				catch (Exception exception) {
					throw new RuntimeException(exception);
				}

				return null;
			});

		fieldResource.patchFieldAccount(
			Stream.of(
				FieldAccountConstants.FIELD_ACCOUNT_NAMES
			).map(
				name -> _getField(name, false, "account")
			).toArray(
				Field[]::new
			));

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS, 1, TimeUnit.SECONDS,
			() -> {
				try {
					Page<Field> fieldsAccountsPage =
						fieldResource.getFieldsAccountsPage(
							null, Pagination.of(1, 100), null);

					int selectedCount = 0;

					for (Field field : fieldsAccountsPage.getItems()) {
						if (field.getSelected()) {
							selectedCount += 1;
						}
					}

					Assert.assertEquals(
						FieldAccountConstants.FIELD_ACCOUNT_REQUIRED_NAMES.
							length,
						selectedCount);
				}
				catch (Exception exception) {
					throw new RuntimeException(exception);
				}

				return null;
			});
	}

	@Override
	@Test
	public void testPatchFieldOrder() throws Exception {
		fieldResource.patchFieldOrder(
			new Field[] {
				_getField("externalReferenceCode", true, "order"),
				_getField("externalReferenceCode", true, "order")
			});

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS, 1, TimeUnit.SECONDS,
			() -> {
				try {
					Page<Field> fieldsOrdersPage =
						fieldResource.getFieldsOrdersPage(
							null, Pagination.of(1, 100), null);

					int selectedCount = 0;

					for (Field field : fieldsOrdersPage.getItems()) {
						if (field.getSelected()) {
							selectedCount += 1;
						}
					}

					Assert.assertEquals(
						FieldOrderConstants.FIELD_ORDER_REQUIRED_NAMES.length +
							FieldOrderConstants.FIELD_ORDER_ITEM_REQUIRED_NAMES.
								length,
						selectedCount);
				}
				catch (Exception exception) {
					throw new RuntimeException(exception);
				}

				return null;
			});
	}

	@Override
	@Test
	public void testPatchFieldPeople() throws Exception {
		fieldResource.patchFieldPeople(
			new Field[] {
				_getField("jobClass", true, "contact"),
				_getField("externalReferenceCode", true, "user")
			});

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS, 1, TimeUnit.SECONDS,
			() -> {
				try {
					Page<Field> fieldsPeoplePage =
						fieldResource.getFieldsPeoplePage(
							null, Pagination.of(1, 100), null);

					int selectedCount = 0;

					for (Field field : fieldsPeoplePage.getItems()) {
						if (field.getSelected()) {
							selectedCount += 1;
						}
					}

					Assert.assertEquals(
						FieldPeopleConstants.FIELD_CONTACT_REQUIRED_NAMES.
							length +
								FieldPeopleConstants.FIELD_USER_REQUIRED_NAMES.
									length + 2,
						selectedCount);
				}
				catch (Exception exception) {
					throw new RuntimeException(exception);
				}

				return null;
			});

		fieldResource.patchFieldPeople(
			ArrayUtil.append(
				Stream.of(
					FieldPeopleConstants.FIELD_CONTACT_NAMES
				).map(
					name -> _getField(name, true, "contact")
				).toArray(
					Field[]::new
				),
				Stream.of(
					FieldPeopleConstants.FIELD_USER_NAMES
				).map(
					name -> _getField(name, true, "user")
				).toArray(
					Field[]::new
				)));

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS, 1, TimeUnit.SECONDS,
			() -> {
				try {
					Page<Field> fieldsPeoplePage =
						fieldResource.getFieldsPeoplePage(
							null, Pagination.of(1, 100), null);

					int selectedCount = 0;

					for (Field field : fieldsPeoplePage.getItems()) {
						if (field.getSelected()) {
							selectedCount += 1;
						}
					}

					Assert.assertEquals(
						FieldPeopleConstants.FIELD_CONTACT_NAMES.length +
							FieldPeopleConstants.FIELD_USER_NAMES.length,
						selectedCount);
				}
				catch (Exception exception) {
					throw new RuntimeException(exception);
				}

				return null;
			});

		fieldResource.patchFieldPeople(
			ArrayUtil.append(
				Stream.of(
					FieldPeopleConstants.FIELD_CONTACT_NAMES
				).map(
					name -> _getField(name, false, "contact")
				).toArray(
					Field[]::new
				),
				Stream.of(
					FieldPeopleConstants.FIELD_USER_NAMES
				).map(
					name -> _getField(name, false, "user")
				).toArray(
					Field[]::new
				)));

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS, 1, TimeUnit.SECONDS,
			() -> {
				try {
					Page<Field> fieldsPeoplePage =
						fieldResource.getFieldsPeoplePage(
							null, Pagination.of(1, 100), null);

					int selectedCount = 0;

					for (Field field : fieldsPeoplePage.getItems()) {
						if (field.getSelected()) {
							selectedCount += 1;
						}
					}

					Assert.assertEquals(
						FieldPeopleConstants.FIELD_CONTACT_REQUIRED_NAMES.
							length +
								FieldPeopleConstants.FIELD_USER_REQUIRED_NAMES.
									length,
						selectedCount);
				}
				catch (Exception exception) {
					throw new RuntimeException(exception);
				}

				return null;
			});
	}

	@Override
	@Test
	public void testPatchFieldProduct() throws Exception {
		fieldResource.patchFieldProduct(
			new Field[] {_getField("externalReferenceCode", true, "product")});

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS, 1, TimeUnit.SECONDS,
			() -> {
				try {
					Page<Field> fieldsProductsPage =
						fieldResource.getFieldsProductsPage(
							null, Pagination.of(1, 100), null);

					int selectedCount = 0;

					for (Field field : fieldsProductsPage.getItems()) {
						if (field.getSelected()) {
							selectedCount += 1;
						}
					}

					Assert.assertEquals(
						FieldProductConstants.FIELD_CATEGORY_REQUIRED_NAMES.
							length +
								FieldProductConstants.
									FIELD_PRODUCT_REQUIRED_NAMES.length +
										FieldProductConstants.
											FIELD_PRODUCT_CHANNEL_REQUIRED_NAMES.length,
						selectedCount);
				}
				catch (Exception exception) {
					throw new RuntimeException(exception);
				}

				return null;
			});

		fieldResource.patchFieldProduct(
			ArrayUtil.append(
				Stream.of(
					FieldProductConstants.FIELD_CATEGORY_NAMES
				).map(
					name -> _getField(name, true, "category")
				).toArray(
					Field[]::new
				),
				Stream.of(
					FieldProductConstants.FIELD_PRODUCT_NAMES
				).map(
					name -> _getField(name, true, "product")
				).toArray(
					Field[]::new
				),
				Stream.of(
					FieldProductConstants.FIELD_PRODUCT_CHANNEL_NAMES
				).map(
					name -> _getField(name, true, "product-channel")
				).toArray(
					Field[]::new
				)));

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS, 1, TimeUnit.SECONDS,
			() -> {
				try {
					Page<Field> fieldsProductsPage =
						fieldResource.getFieldsProductsPage(
							null, Pagination.of(1, 100), null);

					int selectedCount = 0;

					for (Field field : fieldsProductsPage.getItems()) {
						if (field.getSelected()) {
							selectedCount += 1;
						}
					}

					Assert.assertEquals(
						FieldProductConstants.FIELD_CATEGORY_NAMES.length +
							FieldProductConstants.FIELD_PRODUCT_NAMES.length +
								FieldProductConstants.
									FIELD_PRODUCT_CHANNEL_NAMES.length,
						selectedCount);
				}
				catch (Exception exception) {
					throw new RuntimeException(exception);
				}

				return null;
			});

		fieldResource.patchFieldProduct(
			ArrayUtil.append(
				Stream.of(
					FieldProductConstants.FIELD_CATEGORY_NAMES
				).map(
					name -> _getField(name, false, "category")
				).toArray(
					Field[]::new
				),
				Stream.of(
					FieldProductConstants.FIELD_PRODUCT_NAMES
				).map(
					name -> _getField(name, false, "product")
				).toArray(
					Field[]::new
				),
				Stream.of(
					FieldProductConstants.FIELD_PRODUCT_CHANNEL_NAMES
				).map(
					name -> _getField(name, false, "product-channel")
				).toArray(
					Field[]::new
				)));

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS, 1, TimeUnit.SECONDS,
			() -> {
				try {
					Page<Field> fieldsProductsPage =
						fieldResource.getFieldsProductsPage(
							null, Pagination.of(1, 100), null);

					int selectedCount = 0;

					for (Field field : fieldsProductsPage.getItems()) {
						if (field.getSelected()) {
							selectedCount += 1;
						}
					}

					Assert.assertEquals(
						FieldProductConstants.FIELD_CATEGORY_REQUIRED_NAMES.
							length +
								FieldProductConstants.
									FIELD_PRODUCT_REQUIRED_NAMES.length +
										FieldProductConstants.
											FIELD_PRODUCT_CHANNEL_REQUIRED_NAMES.length,
						selectedCount);
				}
				catch (Exception exception) {
					throw new RuntimeException(exception);
				}

				return null;
			});
	}

	private Field _getField(String name, boolean selected, String source) {
		Field field = new Field();

		field.setName(name);
		field.setSelected(selected);
		field.setSource(source);

		return field;
	}

	@Inject
	private AnalyticsSettingsManager _analyticsSettingsManager;

}