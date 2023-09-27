/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.internal.template.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.layout.seo.template.LayoutSEOTemplateProcessor;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alicia García
 */
@RunWith(Arquillian.class)
public class LayoutSEOTemplateProcessorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testProcessTemplateDefaultMappedTitleAndDescriptionReferences() {
		InfoItemFieldValues infoItemFieldValues = _getInfoItemFieldValues();

		Assert.assertEquals(
			"<p>defaultMappedDescription</p>",
			_layoutSEOTemplateProcessor.processTemplate(
				"${description}", infoItemFieldValues, LocaleUtil.US));
		Assert.assertEquals(
			"defaultMappedTitle",
			_layoutSEOTemplateProcessor.processTemplate(
				"${title}", infoItemFieldValues, LocaleUtil.US));
	}

	@Test
	public void testProcessTemplateEmptyLabelReference() {
		Assert.assertEquals(
			"This is the text.",
			_layoutSEOTemplateProcessor.processTemplate(
				"${Text45966564:}", _getInfoItemFieldValues(), LocaleUtil.US));
	}

	@Test
	public void testProcessTemplateIncompleteReference() {
		Assert.assertEquals(
			"${Text45966564: incomplete tag",
			_layoutSEOTemplateProcessor.processTemplate(
				"${Text45966564: incomplete tag", _getInfoItemFieldValues(),
				LocaleUtil.US));
	}

	@Test
	public void testProcessTemplateIncorrectLabeledReference() {
		Assert.assertEquals(
			"This is the text.",
			_layoutSEOTemplateProcessor.processTemplate(
				"${Text45966564:ThisIsNotTheLabel}", _getInfoItemFieldValues(),
				LocaleUtil.US));
	}

	@Test
	public void testProcessTemplateIncorrectReference() {
		Assert.assertEquals(
			"This is the text. }",
			_layoutSEOTemplateProcessor.processTemplate(
				"${Text45966564:This is a bad label ${title} }",
				_getInfoItemFieldValues(), LocaleUtil.US));
	}

	@Test
	public void testProcessTemplateIncorrectReferenceLegacyBehavior() {
		Assert.assertEquals(
			"Text85",
			_layoutSEOTemplateProcessor.processTemplate(
				"${Text85}", _getInfoItemFieldValues(), LocaleUtil.US));
	}

	@Test
	public void testProcessTemplateLabeledReference() {
		Assert.assertEquals(
			"This is the text.",
			_layoutSEOTemplateProcessor.processTemplate(
				"${Text45966564:Text Label}", _getInfoItemFieldValues(),
				LocaleUtil.US));
	}

	@Test
	public void testProcessTemplateReferencesWithMessage() {
		Assert.assertEquals(
			"This is the title: defaultMappedTitle",
			_layoutSEOTemplateProcessor.processTemplate(
				"This is the title: ${title}", _getInfoItemFieldValues(),
				LocaleUtil.US));
	}

	@Test
	public void testProcessTemplateReferencesWithMessageBreakLines() {
		Assert.assertEquals(
			"This is the title: defaultMappedTitle\n" +
				"<p>defaultMappedDescription</p>\nThis is the text.",
			_layoutSEOTemplateProcessor.processTemplate(
				"This is the title: ${title}\n${description}\n${Text45966564}",
				_getInfoItemFieldValues(), LocaleUtil.US));
	}

	@Test
	public void testProcessTemplateSeveralReferences() {
		Assert.assertEquals(
			"defaultMappedTitle <p>defaultMappedDescription</p> This is the " +
				"text.",
			_layoutSEOTemplateProcessor.processTemplate(
				"${title} ${description} ${Text45966564}",
				_getInfoItemFieldValues(), LocaleUtil.US));
	}

	private InfoItemFieldValues _getInfoItemFieldValues() {
		return InfoItemFieldValues.builder(
		).infoFieldValue(
			new InfoFieldValue<>(
				InfoField.builder(
				).infoFieldType(
					TextInfoFieldType.INSTANCE
				).namespace(
					StringPool.BLANK
				).name(
					"description"
				).labelInfoLocalizedValue(
					null
				).build(),
				"<p>defaultMappedDescription</p>")
		).infoFieldValue(
			new InfoFieldValue<>(
				InfoField.builder(
				).infoFieldType(
					TextInfoFieldType.INSTANCE
				).namespace(
					StringPool.BLANK
				).name(
					"Text45966564"
				).labelInfoLocalizedValue(
					InfoLocalizedValue.localize(getClass(), "Text Label")
				).build(),
				"This is the text.")
		).infoFieldValue(
			new InfoFieldValue<>(
				InfoField.builder(
				).infoFieldType(
					TextInfoFieldType.INSTANCE
				).namespace(
					StringPool.BLANK
				).name(
					"title"
				).labelInfoLocalizedValue(
					null
				).build(),
				"defaultMappedTitle")
		).build();
	}

	@Inject
	private LayoutSEOTemplateProcessor _layoutSEOTemplateProcessor;

}