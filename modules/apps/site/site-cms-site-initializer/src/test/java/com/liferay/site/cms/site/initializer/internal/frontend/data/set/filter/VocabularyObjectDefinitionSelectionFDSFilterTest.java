/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.frontend.data.set.filter;

import com.liferay.frontend.data.set.constants.FDSEntityFieldTypes;
import com.liferay.frontend.data.set.filter.SelectionFDSFilterItem;
import com.liferay.object.constants.ObjectFolderConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
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
 * @author Yuri Monteiro
 */
public class VocabularyObjectDefinitionSelectionFDSFilterTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		_vocabularyObjectDefinitionSelectionFDSFilter =
			new VocabularyObjectDefinitionSelectionFDSFilter();

		ReflectionTestUtil.setFieldValue(
			_vocabularyObjectDefinitionSelectionFDSFilter,
			"_objectDefinitionService", _objectDefinitionService);

		ReflectionTestUtil.setFieldValue(
			_vocabularyObjectDefinitionSelectionFDSFilter, "_portal", _portal);
	}

	@Test
	public void testGetProperties() {
		Assert.assertEquals(
			FDSEntityFieldTypes.STRING,
			_vocabularyObjectDefinitionSelectionFDSFilter.getEntityFieldType());
		Assert.assertEquals(
			"assetTypes",
			_vocabularyObjectDefinitionSelectionFDSFilter.getId());
		Assert.assertEquals(
			"value",
			_vocabularyObjectDefinitionSelectionFDSFilter.getItemKey());
		Assert.assertEquals(
			"asset-types",
			_vocabularyObjectDefinitionSelectionFDSFilter.getLabel());
	}

	@Test
	public void testGetSelectionFDSFilterItemsUsesClassNameIds() {
		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(_COMPANY_ID)) {

			ObjectDefinition objectDefinition = Mockito.mock(
				ObjectDefinition.class);

			String objectDefinitionClassName = RandomTestUtil.randomString();

			Mockito.when(
				objectDefinition.getClassName()
			).thenReturn(
				objectDefinitionClassName
			);

			long objectDefinitionClassNameId = RandomTestUtil.randomLong();

			Mockito.when(
				_portal.getClassNameId(objectDefinitionClassName)
			).thenReturn(
				objectDefinitionClassNameId
			);

			String objectDefinitionLabel = RandomTestUtil.randomString();

			Mockito.when(
				objectDefinition.getLabel(_locale)
			).thenReturn(
				objectDefinitionLabel
			);

			Mockito.when(
				_objectDefinitionService.getCMSObjectDefinitions(
					Mockito.eq(_COMPANY_ID), Mockito.any(String[].class))
			).thenReturn(
				List.of(objectDefinition)
			);

			List<SelectionFDSFilterItem> selectionFDSFilterItems =
				_vocabularyObjectDefinitionSelectionFDSFilter.
					getSelectionFDSFilterItems(_locale);

			Assert.assertEquals(
				selectionFDSFilterItems.toString(), 1,
				selectionFDSFilterItems.size());

			SelectionFDSFilterItem selectionFDSFilterItem =
				selectionFDSFilterItems.get(0);

			Assert.assertEquals(
				objectDefinitionLabel, selectionFDSFilterItem.getLabel());
			Assert.assertEquals(
				String.valueOf(objectDefinitionClassNameId),
				selectionFDSFilterItem.getValue());

			Mockito.verify(
				_objectDefinitionService
			).getCMSObjectDefinitions(
				Mockito.eq(_COMPANY_ID),
				Mockito.argThat(
					values -> Arrays.equals(
						values,
						new String[] {
							ObjectFolderConstants.
								EXTERNAL_REFERENCE_CODE_CONTENT_STRUCTURES,
							ObjectFolderConstants.
								EXTERNAL_REFERENCE_CODE_FILE_TYPES
						}))
			);

			Mockito.verify(
				_portal
			).getClassNameId(
				objectDefinitionClassName
			);
		}
	}

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private final Locale _locale = LocaleUtil.US;

	@Mock
	private ObjectDefinitionService _objectDefinitionService;

	@Mock
	private Portal _portal;

	private VocabularyObjectDefinitionSelectionFDSFilter
		_vocabularyObjectDefinitionSelectionFDSFilter;

}