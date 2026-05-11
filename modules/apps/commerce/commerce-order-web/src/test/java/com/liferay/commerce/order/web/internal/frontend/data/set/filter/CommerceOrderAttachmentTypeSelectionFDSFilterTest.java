/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.web.internal.frontend.data.set.filter;

import com.liferay.frontend.data.set.constants.FDSEntityFieldTypes;
import com.liferay.frontend.data.set.filter.SelectionFDSFilterItem;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Stefano Motta
 */
public class CommerceOrderAttachmentTypeSelectionFDSFilterTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);

		ReflectionTestUtil.setFieldValue(
			_commerceOrderAttachmentTypeSelectionFDSFilter,
			"_listTypeDefinitionLocalService", _listTypeDefinitionLocalService);
		ReflectionTestUtil.setFieldValue(
			_commerceOrderAttachmentTypeSelectionFDSFilter,
			"_listTypeEntryLocalService", _listTypeEntryLocalService);
	}

	@Test
	public void testGetProperties() {
		Assert.assertEquals(
			FDSEntityFieldTypes.STRING,
			_commerceOrderAttachmentTypeSelectionFDSFilter.
				getEntityFieldType());
		Assert.assertEquals(
			"type", _commerceOrderAttachmentTypeSelectionFDSFilter.getId());
		Assert.assertEquals(
			"type", _commerceOrderAttachmentTypeSelectionFDSFilter.getLabel());
		Assert.assertFalse(
			_commerceOrderAttachmentTypeSelectionFDSFilter.isMultiple());
	}

	@Test
	public void testGetSelectionFDSFilterItems() {
		Mockito.when(
			_listTypeDefinitionLocalService.
				fetchListTypeDefinitionByExternalReferenceCode(
					Mockito.eq("L_COMMERCE_ORDER_ATTACHMENT_TYPES"),
					Mockito.anyLong())
		).thenReturn(
			null
		);

		List<SelectionFDSFilterItem> selectionFDSFilterItems =
			_commerceOrderAttachmentTypeSelectionFDSFilter.
				getSelectionFDSFilterItems(_locale);

		Assert.assertTrue(
			selectionFDSFilterItems.toString(),
			selectionFDSFilterItems.isEmpty());

		ListTypeDefinition listTypeDefinition = Mockito.mock(
			ListTypeDefinition.class);

		Mockito.when(
			listTypeDefinition.getListTypeDefinitionId()
		).thenReturn(
			1L
		);

		Mockito.when(
			_listTypeDefinitionLocalService.
				fetchListTypeDefinitionByExternalReferenceCode(
					Mockito.eq("L_COMMERCE_ORDER_ATTACHMENT_TYPES"),
					Mockito.anyLong())
		).thenReturn(
			listTypeDefinition
		);

		ListTypeEntry listTypeEntry1 = _mockListTypeEntry("invoice", "Invoice");
		ListTypeEntry listTypeEntry2 = _mockListTypeEntry(
			"purchaseOrderDocument", "Purchase Order Document");

		Mockito.when(
			_listTypeEntryLocalService.getListTypeEntries(
				Mockito.eq(1L), Mockito.anyInt(), Mockito.anyInt())
		).thenReturn(
			List.of(listTypeEntry1, listTypeEntry2)
		);

		selectionFDSFilterItems =
			_commerceOrderAttachmentTypeSelectionFDSFilter.
				getSelectionFDSFilterItems(_locale);

		Assert.assertEquals(
			selectionFDSFilterItems.toString(), 2,
			selectionFDSFilterItems.size());

		SelectionFDSFilterItem selectionFDSFilterItem =
			selectionFDSFilterItems.get(0);

		Assert.assertEquals("Invoice", selectionFDSFilterItem.getLabel());
		Assert.assertEquals("invoice", selectionFDSFilterItem.getValue());

		selectionFDSFilterItem = selectionFDSFilterItems.get(1);

		Assert.assertEquals(
			"Purchase Order Document", selectionFDSFilterItem.getLabel());
		Assert.assertEquals(
			"purchaseorderdocument", selectionFDSFilterItem.getValue());
	}

	private ListTypeEntry _mockListTypeEntry(String key, String name) {
		ListTypeEntry listTypeEntry = Mockito.mock(ListTypeEntry.class);

		Mockito.when(
			listTypeEntry.getKey()
		).thenReturn(
			key
		);

		Mockito.when(
			listTypeEntry.getName(_locale)
		).thenReturn(
			name
		);

		return listTypeEntry;
	}

	private final CommerceOrderAttachmentTypeSelectionFDSFilter
		_commerceOrderAttachmentTypeSelectionFDSFilter =
			new CommerceOrderAttachmentTypeSelectionFDSFilter();

	@Mock
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Mock
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	private final Locale _locale = LocaleUtil.US;

}