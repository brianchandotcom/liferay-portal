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

package com.liferay.dynamic.data.mapping.test.util.search;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.DDMFormValuesReader;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.test.util.AssetEntryQueryTestUtil;
import com.liferay.asset.util.AssetHelper;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.render.ValueAccessor;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.FieldConstants;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMBeanTranslatorUtil;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.osgi.util.service.OSGiServiceUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

import org.junit.Assert;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Preston Crary
 * @author André de Oliveira
 */
public abstract class TestOrderHelper {

	public void testOrderByDDMBooleanField() throws Exception {
		testOrderByDDMField(
			new String[] {"false", "true", "false", "true"},
			new String[] {"false", "false", "true", "true"},
			FieldConstants.BOOLEAN, DDMFormFieldType.CHECKBOX);
	}

	public void testOrderByDDMBooleanFieldRepeatable() throws Exception {
		testOrderByDDMFieldRepeatable(
			new String[] {
				"true|true", "false|false", "true|true", "false|false"
			},
			new String[] {
				"false|false", "false|false", "true|true", "true|true"
			},
			FieldConstants.BOOLEAN, DDMFormFieldType.CHECKBOX);
	}

	public void testOrderByDDMDateField() throws Exception {
		testOrderByDDMField(
			new String[] {"20160417192501", "20160417192510", "20160417192503"},
			new String[] {"20160417192501", "20160417192503", "20160417192510"},
			FieldConstants.DATE, DDMFormFieldType.DATE);
	}

	public void testOrderByDDMIntegerField() throws Exception {
		testOrderByDDMField(
			new String[] {"1", "10", "3", "2"},
			new String[] {"1", "2", "3", "10"}, FieldConstants.INTEGER,
			DDMFormFieldType.INTEGER);
	}

	public void testOrderByDDMIntegerFieldRepeatable() throws Exception {
		testOrderByDDMFieldRepeatable(
			new String[] {"50", "707|25", "1|99|42"},
			new String[] {"1|99|42", "707|25", "50"}, FieldConstants.INTEGER,
			DDMFormFieldType.INTEGER);
	}

	public void testOrderByDDMNumberField() throws Exception {
		testOrderByDDMField(
			new String[] {"3", "3.14", "12.34", "2.72", "1.41", "23.45", "20"},
			new String[] {"1.41", "2.72", "3", "3.14", "12.34", "20", "23.45"},
			FieldConstants.NUMBER, DDMFormFieldType.NUMBER);
	}

	public void testOrderByDDMNumberFieldRepeatable() throws Exception {
		testOrderByDDMFieldRepeatable(
			new String[] {"20|12.34", "16.0", "3.14"},
			new String[] {"3.14", "20|12.34", "16.0"}, FieldConstants.NUMBER,
			DDMFormFieldType.NUMBER);
	}

	public void testOrderByDDMRadioField() throws Exception {
		testOrderByDDMRadioField("text");
	}

	public void testOrderByDDMRadioFieldKeyword() throws Exception {
		testOrderByDDMRadioField("keyword");
	}

	public void testOrderByDDMTextField() throws Exception {
		testOrderByDDMTextField("text");
	}

	public void testOrderByDDMTextFieldKeyword() throws Exception {
		testOrderByDDMTextField("keyword");
	}

	public void testOrderByDDMTextFieldRepeatable() throws Exception {
		testOrderByDDMFieldRepeatable(
			new String[] {"B", "X|Y", "D|A|C|Z"},
			new String[] {"D|A|C|Z", "B", "X|Y"}, "string",
			DDMFormFieldType.TEXT);
	}

	protected static String[] toJsonArrays(String... strings) {
		String[] jsonArrays = new String[strings.length];

		for (int i = 0; i < strings.length; i++) {
			jsonArrays[i] = "[\"" + strings[i] + "\"]";
		}

		return jsonArrays;
	}

	protected TestOrderHelper(DDMIndexer ddmIndexer, Group group)
		throws Exception {

		_ddmIndexer = ddmIndexer;
		_group = group;

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			group.getGroupId());
	}

	protected DDMStructure addDDMStructure() throws Exception {
		DDMForm ddmForm = DDMStructureTestUtil.getSampleDDMForm(
			"name", _dataType, _indexType, _repeatable, _type,
			new Locale[] {LocaleUtil.US}, LocaleUtil.US);

		setDDMFormFieldOptions(ddmForm);

		return DDMStructureTestUtil.addStructure(
			_serviceContext.getScopeGroupId(),
			getSearchableAssetEntryStructureClassName(), ddmForm);
	}

	protected DDMTemplate addDDMTemplate(DDMStructure ddmStructure)
		throws Exception {

		return DDMTemplateTestUtil.addTemplate(
			_serviceContext.getScopeGroupId(), ddmStructure.getStructureId(),
			getClassNameId());
	}

	protected void addSearchableAssetEntries(
			DDMStructure ddmStructure, DDMTemplate ddmTemplate)
		throws Exception {

		BaseModel<?> parentBaseModel = getSearchableAssetEntryParentBaseModel(
			_group, _serviceContext);

		for (String value : _unsortedValues) {
			if (!_repeatable) {
				addSearchableAssetEntry(
					value, parentBaseModel, ddmStructure, ddmTemplate,
					_serviceContext);
			}
			else {
				addSearchableAssetEntryRepeatable(
					StringUtil.split(value, CharPool.PIPE), parentBaseModel,
					ddmStructure, ddmTemplate, _serviceContext);
			}
		}
	}

	protected abstract BaseModel<?> addSearchableAssetEntry(
			String fieldValue, BaseModel<?> parentBaseModel,
			DDMStructure ddmStructure, DDMTemplate ddmTemplate,
			ServiceContext serviceContext)
		throws Exception;

	protected abstract BaseModel<?> addSearchableAssetEntryRepeatable(
			String[] fieldValues, BaseModel<?> parentBaseModel,
			DDMStructure ddmStructure, DDMTemplate ddmTemplate,
			ServiceContext serviceContext)
		throws Exception;

	protected void assertSearch(AssetEntryQuery assetEntryQuery)
		throws Exception {

		Hits hits = search(assetEntryQuery);

		Bundle bundle = FrameworkUtil.getBundle(TestOrderHelper.class);

		List<AssetEntry> assetEntries = OSGiServiceUtil.callService(
			bundle.getBundleContext(), AssetHelper.class,
			assetHelper -> assetHelper.getAssetEntries(hits));

		Assert.assertEquals(
			ArrayUtils.toString(_sortedValues),
			ArrayUtils.toString(getValues(assetEntries)));
	}

	protected AssetEntryQuery createAssetEntryQuery(DDMStructure ddmStructure)
		throws Exception {

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group.getGroupId(),
				new String[] {getSearchableAssetEntryClassName()});

		String orderByCol1 = _ddmIndexer.encodeName(
			ddmStructure.getStructureId(), "name");

		assetEntryQuery.setOrderByCol1(orderByCol1);

		assetEntryQuery.setOrderByType1("asc");

		return assetEntryQuery;
	}

	protected AssetRendererFactory<?> getAssetRendererFactory() {
		return AssetRendererFactoryRegistryUtil.
			getAssetRendererFactoryByClassName(
				getSearchableAssetEntryClassName());
	}

	protected abstract long getClassNameId();

	protected abstract String getSearchableAssetEntryClassName();

	protected abstract BaseModel<?> getSearchableAssetEntryParentBaseModel(
			Group group, ServiceContext serviceContext)
		throws Exception;

	protected abstract String getSearchableAssetEntryStructureClassName();

	protected String getValue(AssetRenderer<?> assetRenderer) throws Exception {
		DDMFormValuesReader ddmFormValuesReader =
			assetRenderer.getDDMFormValuesReader();

		DDMFormValues ddmFormValues = DDMBeanTranslatorUtil.translate(
			ddmFormValuesReader.getDDMFormValues());

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			ddmFormValues.getDDMFormFieldValuesMap();

		return ListUtil.toString(
			ddmFormFieldValuesMap.get("name"),
			new ValueAccessor(LocaleUtil.getDefault()) {

				@Override
				public String get(DDMFormFieldValue ddmFormFieldValue) {
					Value value = ddmFormFieldValue.getValue();

					return value.getString(locale);
				}

			},
			StringPool.PIPE);
	}

	protected String[] getValues(List<AssetEntry> assetEntries)
		throws Exception {

		AssetRendererFactory<?> assetRendererFactory =
			getAssetRendererFactory();

		String[] values = new String[assetEntries.size()];

		for (int i = 0; i < assetEntries.size(); i++) {
			AssetEntry assetEntry = assetEntries.get(i);

			AssetRenderer<?> assetRenderer =
				assetRendererFactory.getAssetRenderer(assetEntry.getClassPK());

			values[i] = getValue(assetRenderer);
		}

		return values;
	}

	protected Hits search(AssetEntryQuery assetEntryQuery) throws Exception {
		SearchContext searchContext = SearchContextTestUtil.getSearchContext();

		searchContext.setGroupIds(assetEntryQuery.getGroupIds());

		Bundle bundle = FrameworkUtil.getBundle(TestOrderHelper.class);

		return OSGiServiceUtil.callService(
			bundle.getBundleContext(), AssetHelper.class,
			assetHelper -> assetHelper.search(
				searchContext, assetEntryQuery, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS));
	}

	protected void setDDMFormFieldOptions(DDMForm ddmForm) {
		if (!_type.equals(DDMFormFieldType.RADIO)) {
			return;
		}

		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		DDMFormField ddmFormField = ddmFormFields.get(0);

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		for (String option : _optionValues) {
			ddmFormFieldOptions.addOptionLabel(option, LocaleUtil.US, option);
		}

		ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);
	}

	protected void testOrderByDDMField() throws Exception {
		DDMStructure ddmStructure = addDDMStructure();

		DDMTemplate ddmTemplate = addDDMTemplate(ddmStructure);

		addSearchableAssetEntries(ddmStructure, ddmTemplate);

		final AssetEntryQuery assetEntryQuery = createAssetEntryQuery(
			ddmStructure);

		assertSearch(assetEntryQuery);
	}

	protected void testOrderByDDMField(
			String[] unsortedValues, String[] sortedValues, String dataType,
			String type)
		throws Exception {

		testOrderByDDMField(
			unsortedValues, sortedValues, dataType, "text", type);
	}

	protected void testOrderByDDMField(
			String[] unsortedValues, String[] sortedValues, String dataType,
			String indexType, String type)
		throws Exception {

		_unsortedValues = unsortedValues;
		_sortedValues = sortedValues;
		_dataType = dataType;
		_indexType = indexType;
		_type = type;
		_repeatable = false;

		testOrderByDDMField();
	}

	protected void testOrderByDDMFieldRepeatable(
			String[] unsortedValues, String[] sortedValues, String dataType,
			String type)
		throws Exception {

		_unsortedValues = unsortedValues;
		_sortedValues = sortedValues;
		_dataType = dataType;
		_indexType = "text";
		_type = type;
		_repeatable = true;

		testOrderByDDMField();
	}

	protected void testOrderByDDMRadioField(String indexType) throws Exception {
		testOrderByDDMRadioField(
			new String[] {"a", "D", "c", "B"},
			new String[] {"a", "B", "c", "D"}, FieldConstants.STRING, indexType,
			DDMFormFieldType.RADIO);
	}

	protected void testOrderByDDMRadioField(
			String[] unsortedValues, String[] sortedValues, String dataType,
			String indexType, String type)
		throws Exception {

		_unsortedValues = unsortedValues;
		_sortedValues = sortedValues;
		_dataType = dataType;
		_indexType = indexType;
		_type = type;

		_optionValues = unsortedValues;
		_repeatable = false;

		testOrderByDDMField();
	}

	protected void testOrderByDDMTextField(String indexType) throws Exception {
		testOrderByDDMField(
			new String[] {"a", "D", "c", "B"},
			new String[] {"a", "B", "c", "D"}, FieldConstants.STRING, indexType,
			DDMFormFieldType.TEXT);
	}

	private String _dataType;
	private final DDMIndexer _ddmIndexer;
	private final Group _group;
	private String _indexType;
	private String[] _optionValues;
	private boolean _repeatable;
	private final ServiceContext _serviceContext;
	private String[] _sortedValues;
	private String _type;
	private String[] _unsortedValues;

}