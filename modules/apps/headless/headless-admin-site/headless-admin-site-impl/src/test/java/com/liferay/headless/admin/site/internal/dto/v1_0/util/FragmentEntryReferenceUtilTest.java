/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.util;

import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.headless.admin.site.dto.v1_0.FragmentItemExternalReference;
import com.liferay.headless.admin.site.dto.v1_0.FragmentReference;
import com.liferay.headless.admin.site.internal.util.LogUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Alberto Javier Moreno Lage
 */
public class FragmentEntryReferenceUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@AfterClass
	public static void tearDownClass() {
		_fragmentEntryLocalServiceUtilMockedStatic.close();
		_itemScopeUtilMockedStatic.close();
		_logUtilMockedStatic.close();
	}

	@Before
	public void setUp() {
		_itemScopeUtilMockedStatic.when(
			() -> ItemScopeUtil.getItemGroupId(
				Mockito.anyLong(), Mockito.any(), Mockito.anyLong())
		).thenReturn(
			_SCOPE_GROUP_ID
		);
	}

	@Test
	public void testGetFragmentEntryReference() throws Exception {
		String externalReferenceCode = RandomTestUtil.randomString();
		String fragmentEntryKey = RandomTestUtil.randomString();

		FragmentEntry fragmentEntry = Mockito.mock(FragmentEntry.class);

		Mockito.when(
			fragmentEntry.getFragmentEntryKey()
		).thenReturn(
			fragmentEntryKey
		);

		_fragmentEntryLocalServiceUtilMockedStatic.when(
			() ->
				FragmentEntryLocalServiceUtil.
					fetchFragmentEntryByExternalReferenceCode(
						externalReferenceCode, _SCOPE_GROUP_ID)
		).thenReturn(
			fragmentEntry
		);

		FragmentEntryReference fragmentEntryReference =
			FragmentEntryReferenceUtil.getFragmentEntryReference(
				_COMPANY_ID,
				_getFragmentItemExternalReference(externalReferenceCode),
				_SCOPE_GROUP_ID);

		Assert.assertEquals(
			externalReferenceCode,
			fragmentEntryReference.getFragmentEntryERC());
		Assert.assertEquals(
			fragmentEntryKey, fragmentEntryReference.getFragmentEntryKey());

		_logUtilMockedStatic.verify(
			() -> LogUtil.logOptionalReference(
				Mockito.eq(FragmentEntry.class.getName()),
				Mockito.eq(externalReferenceCode), Mockito.any(),
				Mockito.eq(_SCOPE_GROUP_ID)),
			Mockito.never());

		_fragmentEntryLocalServiceUtilMockedStatic.when(
			() ->
				FragmentEntryLocalServiceUtil.
					fetchFragmentEntryByExternalReferenceCode(
						externalReferenceCode, _SCOPE_GROUP_ID)
		).thenReturn(
			null
		);

		fragmentEntryReference =
			FragmentEntryReferenceUtil.getFragmentEntryReference(
				_COMPANY_ID,
				_getFragmentItemExternalReference(externalReferenceCode),
				_SCOPE_GROUP_ID);

		Assert.assertEquals(
			externalReferenceCode,
			fragmentEntryReference.getFragmentEntryERC());
		Assert.assertNull(fragmentEntryReference.getFragmentEntryKey());

		_logUtilMockedStatic.verify(
			() -> LogUtil.logOptionalReference(
				Mockito.eq(FragmentEntry.class.getName()),
				Mockito.eq(externalReferenceCode), Mockito.any(),
				Mockito.eq(_SCOPE_GROUP_ID)));
	}

	private FragmentItemExternalReference _getFragmentItemExternalReference(
		String externalReferenceCode) {

		FragmentItemExternalReference fragmentItemExternalReference =
			new FragmentItemExternalReference();

		fragmentItemExternalReference.setExternalReferenceCode(
			externalReferenceCode);
		fragmentItemExternalReference.setFragmentReferenceType(
			FragmentReference.FragmentReferenceType.
				FRAGMENT_ITEM_EXTERNAL_REFERENCE);

		return fragmentItemExternalReference;
	}

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final long _SCOPE_GROUP_ID = RandomTestUtil.randomLong();

	private static final MockedStatic<FragmentEntryLocalServiceUtil>
		_fragmentEntryLocalServiceUtilMockedStatic = Mockito.mockStatic(
			FragmentEntryLocalServiceUtil.class);
	private static final MockedStatic<ItemScopeUtil>
		_itemScopeUtilMockedStatic = Mockito.mockStatic(ItemScopeUtil.class);
	private static final MockedStatic<LogUtil> _logUtilMockedStatic =
		Mockito.mockStatic(LogUtil.class);

}