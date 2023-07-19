/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.internal.info.item.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.CategoriesInfoFieldType;
import com.liferay.info.field.type.HTMLInfoFieldType;
import com.liferay.info.field.type.ImageInfoFieldType;
import com.liferay.info.field.type.TagsInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alicia García
 */
@RunWith(Arquillian.class)
public class KBArticleInfoItemFormProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_user = TestPropsValues.getUser();
		_kbArticle = _addKBArticle();
	}

	@Test
	public void testGetInfoForm() throws Exception {
		InfoItemFormProvider<KBArticle> infoItemFormProvider =
			(InfoItemFormProvider<KBArticle>)
				_infoItemServiceRegistry.getFirstInfoItemService(
					InfoItemFormProvider.class, KBArticle.class.getName());

		InfoForm infoForm = infoItemFormProvider.getInfoForm(_kbArticle);

		List<InfoField<?>> infoFields = infoForm.getAllInfoFields();

		infoFields.sort(
			Comparator.comparing(
				InfoField::getName, String::compareToIgnoreCase));

		Assert.assertEquals(infoFields.toString(), 8, infoFields.size());

		Iterator<InfoField<?>> iterator = infoFields.iterator();

		InfoField infoField = iterator.next();

		Assert.assertEquals(
			TextInfoFieldType.INSTANCE, infoField.getInfoFieldType());
		Assert.assertEquals("authorName", infoField.getName());
		Assert.assertFalse(infoField.isLocalizable());

		infoField = iterator.next();

		Assert.assertEquals(
			ImageInfoFieldType.INSTANCE, infoField.getInfoFieldType());
		Assert.assertEquals("authorProfileImage", infoField.getName());
		Assert.assertFalse(infoField.isLocalizable());

		infoField = iterator.next();

		Assert.assertEquals(
			CategoriesInfoFieldType.INSTANCE, infoField.getInfoFieldType());
		Assert.assertEquals("categories", infoField.getName());
		Assert.assertFalse(infoField.isLocalizable());

		infoField = iterator.next();

		Assert.assertEquals(
			HTMLInfoFieldType.INSTANCE, infoField.getInfoFieldType());
		Assert.assertEquals("content", infoField.getName());
		Assert.assertFalse(infoField.isLocalizable());

		infoField = iterator.next();

		Assert.assertEquals(
			TextInfoFieldType.INSTANCE, infoField.getInfoFieldType());
		Assert.assertEquals("description", infoField.getName());
		Assert.assertFalse(infoField.isLocalizable());

		infoField = iterator.next();

		Assert.assertEquals(
			TagsInfoFieldType.INSTANCE, infoField.getInfoFieldType());
		Assert.assertEquals("tagNames", infoField.getName());
		Assert.assertFalse(infoField.isLocalizable());

		infoField = iterator.next();

		Assert.assertEquals(
			TextInfoFieldType.INSTANCE, infoField.getInfoFieldType());
		Assert.assertEquals("title", infoField.getName());
		Assert.assertFalse(infoField.isLocalizable());

		infoField = iterator.next();

		Assert.assertEquals(
			CategoriesInfoFieldType.INSTANCE, infoField.getInfoFieldType());
		Assert.assertEquals(
			"topic", StringUtil.toLowerCase(infoField.getName()));

		Assert.assertFalse(iterator.hasNext());
	}

	@Test
	public void testGetInfoItemFieldValues() throws Exception {
		InfoItemFieldValuesProvider<KBArticle> infoItemFieldValuesProvider =
			(InfoItemFieldValuesProvider<KBArticle>)
				_infoItemServiceRegistry.getFirstInfoItemService(
					InfoItemFieldValuesProvider.class,
					KBArticle.class.getName());

		InfoItemFieldValues infoItemFieldValues =
			infoItemFieldValuesProvider.getInfoItemFieldValues(_kbArticle);

		InfoItemReference infoItemReference =
			infoItemFieldValues.getInfoItemReference();

		InfoItemIdentifier infoItemIdentifier =
			infoItemReference.getInfoItemIdentifier();

		Assert.assertTrue(
			infoItemIdentifier instanceof ClassPKInfoItemIdentifier);

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			(ClassPKInfoItemIdentifier)
				infoItemReference.getInfoItemIdentifier();

		Assert.assertEquals(
			_kbArticle.getResourcePrimKey(),
			classPKInfoItemIdentifier.getClassPK());

		Assert.assertEquals(
			KBArticle.class.getName(), infoItemReference.getClassName());

		Collection<InfoFieldValue<Object>> infoFieldValues =
			infoItemFieldValues.getInfoFieldValues();

		Assert.assertEquals(
			infoFieldValues.toString(), 9, infoFieldValues.size());

		InfoFieldValue<Object> descriptionInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue("description");

		Assert.assertEquals(
			"Description", descriptionInfoFieldValue.getValue());

		InfoFieldValue<Object> titleInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue("title");

		Assert.assertEquals("title KB Article", titleInfoFieldValue.getValue());

		InfoFieldValue<Object> contentInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue("content");

		Assert.assertEquals(
			"<strong>Context text</strong>", contentInfoFieldValue.getValue());
	}

	private KBArticle _addKBArticle() throws Exception {
		return _kbArticleLocalService.addKBArticle(
			null, _user.getUserId(),
			ClassNameLocalServiceUtil.getClassNameId(
				KBFolderConstants.getClassName()),
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID, "title KB Article",
			StringUtil.randomString(), "<strong>Context text</strong>",
			"Description", null, StringPool.BLANK, new Date(), null, null, null,
			ServiceContextTestUtil.getServiceContext());
	}

	@Inject
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@DeleteAfterTestRun
	private KBArticle _kbArticle;

	@Inject
	private KBArticleLocalService _kbArticleLocalService;

	private User _user;

}