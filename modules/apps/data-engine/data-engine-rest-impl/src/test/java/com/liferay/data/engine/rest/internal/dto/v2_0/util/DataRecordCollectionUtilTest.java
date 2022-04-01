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

package com.liferay.data.engine.rest.internal.dto.v2_0.util;

import com.liferay.data.engine.rest.dto.v2_0.DataRecordCollection;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Mateus Santana
 */
public class DataRecordCollectionUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		LanguageUtil languageUtil = new LanguageUtil();

		Language language = Mockito.mock(Language.class);

		Mockito.when(
			language.isAvailableLocale(LocaleUtil.BRAZIL)
		).thenReturn(
			true
		);

		Mockito.when(
			language.isAvailableLocale(LocaleUtil.US)
		).thenReturn(
			true
		);

		Mockito.when(
			language.getLanguageId(LocaleUtil.BRAZIL)
		).thenReturn(
			"pt_BR"
		);

		Mockito.when(
			language.getLanguageId(LocaleUtil.US)
		).thenReturn(
			"en_US"
		);

		languageUtil.setLanguage(language);
	}

	@Test
	public void testToDataRecordCollectionEquals() {
		Mockito.when(
			_ddlRecordSet.getDDMStructureId()
		).thenReturn(
			123L
		);

		Mockito.when(
			_ddlRecordSet.getDescriptionMap()
		).thenReturn(
			HashMapBuilder.put(
				LocaleUtil.BRAZIL, "Descrição"
			).put(
				LocaleUtil.US, "Description"
			).build()
		);

		Mockito.when(
			_ddlRecordSet.getGroupId()
		).thenReturn(
			789L
		);

		Mockito.when(
			_ddlRecordSet.getNameMap()
		).thenReturn(
			HashMapBuilder.put(
				LocaleUtil.BRAZIL, "Nome"
			).put(
				LocaleUtil.US, "Name"
			).build()
		);

		Mockito.when(
			_ddlRecordSet.getRecordSetId()
		).thenReturn(
			456L
		);

		Mockito.when(
			_ddlRecordSet.getRecordSetKey()
		).thenReturn(
			"RecordSetId"
		);

		Assert.assertEquals(
			new DataRecordCollection() {
				{
					setDataDefinitionId(123L);
					setDataRecordCollectionKey("RecordSetId");
					setDescription(
						HashMapBuilder.<String, Object>put(
							"en_US", "Description"
						).put(
							"pt_BR", "Descrição"
						).build());
					setId(456L);
					setName(
						HashMapBuilder.<String, Object>put(
							"en_US", "Name"
						).put(
							"pt_BR", "Nome"
						).build());
					setSiteId(789L);
				}
			},
			DataRecordCollectionUtil.toDataRecordCollection(_ddlRecordSet));
	}

	@Test
	public void testToDataRecordCollectionNotEquals() {
		Mockito.when(
			_ddlRecordSet.getDDMStructureId()
		).thenReturn(
			124L
		);

		Mockito.when(
			_ddlRecordSet.getDescriptionMap()
		).thenReturn(
			HashMapBuilder.put(
				LocaleUtil.BRAZIL, "Descrição1"
			).put(
				LocaleUtil.US, "Description1"
			).build()
		);

		Mockito.when(
			_ddlRecordSet.getGroupId()
		).thenReturn(
			788L
		);

		Mockito.when(
			_ddlRecordSet.getNameMap()
		).thenReturn(
			HashMapBuilder.put(
				LocaleUtil.BRAZIL, "Nome1"
			).put(
				LocaleUtil.US, "Name1"
			).build()
		);

		Mockito.when(
			_ddlRecordSet.getRecordSetId()
		).thenReturn(
			457L
		);

		Mockito.when(
			_ddlRecordSet.getRecordSetKey()
		).thenReturn(
			"RecordSetId1"
		);

		Assert.assertNotEquals(
			new DataRecordCollection() {
				{
					setDataDefinitionId(123L);
					setDataRecordCollectionKey("RecordSetId");
					setDescription(
						HashMapBuilder.<String, Object>put(
							"en_US", "Description"
						).put(
							"pt_BR", "Descrição"
						).build());
					setId(456L);
					setName(
						HashMapBuilder.<String, Object>put(
							"en_US", "Name"
						).put(
							"pt_BR", "Nome"
						).build());
					setSiteId(789L);
				}
			},
			DataRecordCollectionUtil.toDataRecordCollection(_ddlRecordSet));
	}

	@Test
	public void testToDataRecordCollectionNullDDLRecordSet() {
		Assert.assertEquals(
			new DataRecordCollection(),
			DataRecordCollectionUtil.toDataRecordCollection(null));
	}

	private final DDLRecordSet _ddlRecordSet = Mockito.mock(DDLRecordSet.class);

}