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

package com.liferay.dynamic.data.mapping.upgrade.v5_0_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceReportLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.petra.string.StringPool;
import com.liferay.polls.model.PollsChoice;
import com.liferay.polls.model.PollsQuestion;
import com.liferay.polls.model.PollsVote;
import com.liferay.polls.service.PollsChoiceLocalServiceUtil;
import com.liferay.polls.service.PollsQuestionLocalServiceUtil;
import com.liferay.polls.service.PollsVoteLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carolina Barbosa
 */
@RunWith(Arquillian.class)
public class PollsToDDMUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_groupId = _group.getGroupId();

		_userId = TestPropsValues.getUserId();

		_setUpPollsToDDMUpgrade();
	}

	@Test
	public void testPollsToDDMUpgradeProcess() throws Exception {
		Map<Locale, String> descriptionsMap = _getLocalizations(
			HashMapBuilder.put(
				LocaleUtil.BRAZIL, "Description PT"
			).put(
				LocaleUtil.US, "Description US"
			).build());
		Map<Locale, String> titlesMap = _getLocalizations(
			HashMapBuilder.put(
				LocaleUtil.BRAZIL, "Title PT"
			).put(
				LocaleUtil.US, "Title US"
			).build());

		PollsQuestion pollsQuestion = _addPollsQuestion(
			titlesMap, descriptionsMap);

		long questionId = pollsQuestion.getQuestionId();

		PollsChoice pollsChoice = _addPollsChoice(
			questionId, "a",
			_getPollsChoiceDescription(
				HashMapBuilder.put(
					LocaleUtil.BRAZIL, "Option 1 PT"
				).put(
					LocaleUtil.US, "Option 1 US"
				).build()));

		_addPollsVote(questionId, pollsChoice.getChoiceId());

		_addPollsChoice(
			questionId, "b",
			_getPollsChoiceDescription(
				HashMapBuilder.put(
					LocaleUtil.BRAZIL, "Option 2 PT"
				).put(
					LocaleUtil.US, "Option 2 US"
				).build()));

		_pollsToDDMUpgradeProcess.upgrade();

		EntityCacheUtil.clearCache();

		DDMFormInstance ddmFormInstance =
			DDMFormInstanceLocalServiceUtil.getDDMFormInstance(questionId);

		Assert.assertEquals(
			descriptionsMap, ddmFormInstance.getDescriptionMap());
		Assert.assertEquals(titlesMap, ddmFormInstance.getNameMap());

		DDMForm ddmForm = ddmFormInstance.getDDMForm();

		_assertDDMForm(ddmForm);

		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		Assert.assertEquals(ddmFormFields.toString(), 1, ddmFormFields.size());

		DDMFormField ddmFormField = ddmFormFields.get(0);

		_assertDDMFormField(ddmFormField);

		_assertDDMFormInstanceRecords(
			ddmFormField,
			DDMFormInstanceRecordLocalServiceUtil.getFormInstanceRecords(
				questionId));

		_assertDDMFormInstanceReport(
			ddmFormField,
			DDMFormInstanceReportLocalServiceUtil.
				getFormInstanceReportByFormInstanceId(questionId));
	}

	private PollsChoice _addPollsChoice(
			long questionId, String name, String description)
		throws Exception {

		return PollsChoiceLocalServiceUtil.addChoice(
			_userId, questionId, name, description,
			ServiceContextTestUtil.getServiceContext(_groupId));
	}

	private PollsQuestion _addPollsQuestion(
			Map<Locale, String> titlesMap, Map<Locale, String> descriptionsMap)
		throws Exception {

		return PollsQuestionLocalServiceUtil.addQuestion(
			_userId, titlesMap, descriptionsMap, 0, 0, 0, 0, 0, true, null,
			ServiceContextTestUtil.getServiceContext(_groupId));
	}

	private PollsVote _addPollsVote(long questionId, long choiceId)
		throws Exception {

		return PollsVoteLocalServiceUtil.addVote(
			_userId, questionId, choiceId,
			ServiceContextTestUtil.getServiceContext(_groupId));
	}

	private void _assertDDMForm(DDMForm ddmForm) {
		Assert.assertEquals(_availableLocales, ddmForm.getAvailableLocales());
		Assert.assertEquals(_defaultLocale, ddmForm.getDefaultLocale());
	}

	private void _assertDDMFormField(DDMFormField ddmFormField) {
		_assertDDMFormFieldOptions(ddmFormField.getDDMFormFieldOptions());

		Assert.assertEquals("string", ddmFormField.getDataType());
		Assert.assertEquals("radio", ddmFormField.getType());
	}

	private void _assertDDMFormFieldOptions(
		DDMFormFieldOptions ddmFormFieldOptions) {

		Map<String, LocalizedValue> options = ddmFormFieldOptions.getOptions();

		Assert.assertEquals(options.toString(), 2, options.size());

		List<String> orderedOptionValues = _getOrderedOptionValues(
			ddmFormFieldOptions);

		LocalizedValue localizedValue = options.get(orderedOptionValues.get(0));

		Assert.assertEquals(
			"a. Option 1 PT", localizedValue.getString(LocaleUtil.BRAZIL));
		Assert.assertEquals(
			"a. Option 1 US", localizedValue.getString(LocaleUtil.US));

		localizedValue = options.get(orderedOptionValues.get(1));

		Assert.assertEquals(
			"b. Option 2 PT", localizedValue.getString(LocaleUtil.BRAZIL));
		Assert.assertEquals(
			"b. Option 2 US", localizedValue.getString(LocaleUtil.US));
	}

	private void _assertDDMFormFieldValue(
		DDMFormFieldOptions ddmFormFieldOptions,
		List<DDMFormFieldValue> ddmFormFieldValues) {

		Assert.assertEquals(
			ddmFormFieldValues.toString(), 1, ddmFormFieldValues.size());

		DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(0);

		Value value = ddmFormFieldValue.getValue();

		List<String> optionValues = _getOrderedOptionValues(
			ddmFormFieldOptions);

		Assert.assertEquals(
			optionValues.get(0), value.getString(_defaultLocale));
	}

	private void _assertDDMFormInstanceRecords(
			DDMFormField ddmFormField,
			List<DDMFormInstanceRecord> ddmFormInstanceRecords)
		throws Exception {

		Assert.assertEquals(
			ddmFormInstanceRecords.toString(), 1,
			ddmFormInstanceRecords.size());

		DDMFormInstanceRecord ddmFormInstanceRecord =
			ddmFormInstanceRecords.get(0);

		_assertDDMFormValues(
			ddmFormField, ddmFormInstanceRecord.getDDMFormValues());
	}

	private void _assertDDMFormInstanceReport(
		DDMFormField ddmFormField,
		DDMFormInstanceReport ddmFormInstanceReport) {

		List<String> orderedOptionValues = _getOrderedOptionValues(
			ddmFormField.getDDMFormFieldOptions());

		Assert.assertEquals(
			JSONUtil.put(
				ddmFormField.getName(),
				JSONUtil.put(
					"type", "radio"
				).put(
					"values",
					JSONUtil.put(
						orderedOptionValues.get(0), 1
					).put(
						orderedOptionValues.get(1), 0
					)
				)
			).put(
				"totalItems", 1
			).toString(),
			ddmFormInstanceReport.getData());
	}

	private void _assertDDMFormValues(
		DDMFormField ddmFormField, DDMFormValues ddmFormValues) {

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			ddmFormValues.getDDMFormFieldValuesMap(false);

		Assert.assertEquals(
			ddmFormFieldValuesMap.toString(), 1, ddmFormFieldValuesMap.size());

		_assertDDMFormFieldValue(
			ddmFormField.getDDMFormFieldOptions(),
			ddmFormFieldValuesMap.get(ddmFormField.getName()));
	}

	private Map<Locale, String> _getLocalizations(
		HashMap<Locale, String> hashMap) {

		Map<Locale, String> localizations = new HashMap<>();

		for (Locale availableLocale : _availableLocales) {
			localizations.put(availableLocale, hashMap.get(availableLocale));
		}

		return localizations;
	}

	private List<String> _getOrderedOptionValues(
		DDMFormFieldOptions ddmFormFieldOptions) {

		List<String> optionValues = new LinkedList<>();

		optionValues.addAll(ddmFormFieldOptions.getOptionsValues());

		return optionValues;
	}

	private String _getPollsChoiceDescription(HashMap<Locale, String> hashMap) {
		return LocalizationUtil.updateLocalization(
			_getLocalizations(hashMap), StringPool.BLANK, "Description",
			LocaleUtil.toLanguageId(_defaultLocale));
	}

	private void _setUpPollsToDDMUpgrade() {
		_upgradeStepRegistrator.register(
			new UpgradeStepRegistrator.Registry() {

				@Override
				public void register(
					String fromSchemaVersionString,
					String toSchemaVersionString, UpgradeStep... upgradeSteps) {

					for (UpgradeStep upgradeStep : upgradeSteps) {
						Class<?> clazz = upgradeStep.getClass();

						String className = clazz.getName();

						if (className.contains(_CLASS_NAME)) {
							_pollsToDDMUpgradeProcess =
								(UpgradeProcess)upgradeStep;
						}
					}
				}

			});
	}

	private static final String _CLASS_NAME =
		"com.liferay.dynamic.data.mapping.internal.upgrade.v5_0_0." +
			"PollsToDDMUpgradeProcess";

	@Inject(
		filter = "(&(objectClass=com.liferay.dynamic.data.mapping.internal.upgrade.DDMServiceUpgrade))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	private final Set<Locale> _availableLocales = SetUtil.fromArray(
		new Locale[] {LocaleUtil.BRAZIL, LocaleUtil.US});
	private final Locale _defaultLocale = LocaleUtil.US;

	@DeleteAfterTestRun
	private Group _group;

	private long _groupId;
	private UpgradeProcess _pollsToDDMUpgradeProcess;
	private long _userId;

}