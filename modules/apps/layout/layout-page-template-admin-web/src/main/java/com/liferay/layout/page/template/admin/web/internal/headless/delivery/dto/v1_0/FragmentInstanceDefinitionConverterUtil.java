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

package com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0;

import com.liferay.fragment.contributor.FragmentCollectionContributor;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.renderer.constants.FragmentRendererConstants;
import com.liferay.fragment.service.FragmentCollectionLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.headless.delivery.dto.v1_0.FragmentContentField;
import com.liferay.headless.delivery.dto.v1_0.FragmentContentFieldImage;
import com.liferay.headless.delivery.dto.v1_0.FragmentContentFieldText;
import com.liferay.headless.delivery.dto.v1_0.FragmentDefinition;
import com.liferay.headless.delivery.dto.v1_0.FragmentImage;
import com.liferay.headless.delivery.dto.v1_0.FragmentLink;
import com.liferay.headless.delivery.dto.v1_0.InlineLink;
import com.liferay.headless.delivery.dto.v1_0.InlineValue;
import com.liferay.layout.util.structure.FragmentLayoutStructureItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * @author Rubén Pulido
 */
public class FragmentInstanceDefinitionConverterUtil {

	public static FragmentDefinition toFragmentDefinition(
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		FragmentLayoutStructureItem fragmentLayoutStructureItem,
		FragmentRendererTracker fragmentRendererTracker) {

		FragmentEntryLink fragmentEntryLink =
			FragmentEntryLinkLocalServiceUtil.fetchFragmentEntryLink(
				fragmentLayoutStructureItem.getFragmentEntryLinkId());

		String rendererKey = fragmentEntryLink.getRendererKey();

		FragmentEntry fragmentEntry = _getFragmentEntry(
			fragmentCollectionContributorTracker,
			fragmentEntryLink.getFragmentEntryId(), rendererKey);

		return new FragmentDefinition() {
			{
				fragmentCollectionName = _getFragmentCollectionName(
					fragmentCollectionContributorTracker, fragmentEntry,
					fragmentRendererTracker, rendererKey);
				fragmentConfig = _getFragmentConfig(
					fragmentEntryConfigurationParser, fragmentEntryLink);
				fragmentContentFields = _getFragmentContentFields(
					fragmentEntryLink);
				fragmentName = _getFragmentName(
					fragmentEntry, fragmentEntryLink, fragmentRendererTracker,
					rendererKey);
			}
		};
	}

	private static List<FragmentContentField>
		_getBackgroundImageFragmentContentFields(JSONObject jsonObject) {

		List<FragmentContentField> fragmentContentFields = new ArrayList<>();

		Set<String> backgroundImageIds = jsonObject.keySet();

		for (String backgroundImageId : backgroundImageIds) {
			JSONObject imageJSONObject = jsonObject.getJSONObject(
				backgroundImageId);

			fragmentContentFields.add(
				new FragmentContentField() {
					{
						id = backgroundImageId;
						value = new FragmentContentFieldImage() {
							{
								fragmentImage = new FragmentImage() {
									{
										title = new InlineValue() {
											{
												value_i18n = _toMap(
													imageJSONObject, "title");
											}
										};
										url = new InlineValue() {
											{
												value_i18n = _toMap(
													imageJSONObject, "url");
											}
										};
									}
								};
							}
						};
					}
				});
		}

		return fragmentContentFields;
	}

	private static Map<String, String> _getEditableTypes(String html) {
		Map<String, String> editableTypes = new HashMap<>();

		Document document = Jsoup.parse(html);

		Elements elements = document.getElementsByTag("lfr-editable");

		elements.forEach(
			element -> editableTypes.put(
				element.attr("id"), element.attr("type")));

		return editableTypes;
	}

	private static String _getFragmentCollectionName(
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		FragmentEntry fragmentEntry,
		FragmentRendererTracker fragmentRendererTracker, String rendererKey) {

		if (fragmentEntry == null) {
			if (Validator.isNull(rendererKey)) {
				rendererKey =
					FragmentRendererConstants.
						FRAGMENT_ENTRY_FRAGMENT_RENDERER_KEY;
			}

			FragmentRenderer fragmentRenderer =
				fragmentRendererTracker.getFragmentRenderer(rendererKey);

			return LanguageUtil.get(
				ResourceBundleUtil.getBundle(
					LocaleUtil.getSiteDefault(), fragmentRenderer.getClass()),
				"fragment.collection.label." +
					fragmentRenderer.getCollectionKey());
		}

		FragmentCollection fragmentCollection =
			FragmentCollectionLocalServiceUtil.fetchFragmentCollection(
				fragmentEntry.getFragmentCollectionId());

		if (fragmentCollection != null) {
			return fragmentCollection.getName();
		}

		String[] parts = StringUtil.split(rendererKey, StringPool.DASH);

		if (ArrayUtil.isEmpty(parts)) {
			return null;
		}

		List<FragmentCollectionContributor> fragmentCollectionContributors =
			fragmentCollectionContributorTracker.
				getFragmentCollectionContributors();

		for (FragmentCollectionContributor fragmentCollectionContributor :
				fragmentCollectionContributors) {

			if (Objects.equals(
					fragmentCollectionContributor.getFragmentCollectionKey(),
					parts[0])) {

				return fragmentCollectionContributor.getName();
			}
		}

		return null;
	}

	private static Map<String, Object> _getFragmentConfig(
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		FragmentEntryLink fragmentEntryLink) {

		try {
			return new HashMap<String, Object>() {
				{
					JSONObject jsonObject =
						fragmentEntryConfigurationParser.
							getConfigurationJSONObject(
								fragmentEntryLink.getConfiguration(),
								fragmentEntryLink.getEditableValues(),
								new long[] {0L});

					Set<String> keys = jsonObject.keySet();

					Iterator<String> iterator = keys.iterator();

					while (iterator.hasNext()) {
						String key = iterator.next();

						Object value = jsonObject.get(key);

						if (value instanceof JSONObject) {
							JSONObject valueJSONObject = (JSONObject)value;

							value = _toMap(valueJSONObject);
						}

						put(key, value);
					}
				}
			};
		}
		catch (JSONException jsonException) {
			return null;
		}
	}

	private static FragmentContentField[] _getFragmentContentFields(
		FragmentEntryLink fragmentEntryLink) {

		JSONObject editableValuesJSONObject = null;

		try {
			editableValuesJSONObject = JSONFactoryUtil.createJSONObject(
				fragmentEntryLink.getEditableValues());
		}
		catch (JSONException jsonException) {
			return null;
		}

		List<FragmentContentField> fragmentContentFields = new ArrayList<>();

		fragmentContentFields.addAll(
			_getBackgroundImageFragmentContentFields(
				editableValuesJSONObject.getJSONObject(
					"com.liferay.fragment.entry.processor.background.image." +
						"BackgroundImageFragmentEntryProcessor")));

		Map<String, String> editableTypes = _getEditableTypes(
			fragmentEntryLink.getHtml());

		fragmentContentFields.addAll(
			_getTextFragmentContentFields(
				editableTypes,
				editableValuesJSONObject.getJSONObject(
					"com.liferay.fragment.entry.processor.editable." +
						"EditableFragmentEntryProcessor")));

		return fragmentContentFields.toArray(new FragmentContentField[0]);
	}

	private static FragmentEntry _getFragmentEntry(
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		long fragmentEntryId, String rendererKey) {

		FragmentEntry fragmentEntry =
			FragmentEntryLocalServiceUtil.fetchFragmentEntry(fragmentEntryId);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		Map<String, FragmentEntry> fragmentEntries =
			fragmentCollectionContributorTracker.getFragmentEntries();

		return fragmentEntries.get(rendererKey);
	}

	private static String _getFragmentName(
		FragmentEntry fragmentEntry, FragmentEntryLink fragmentEntryLink,
		FragmentRendererTracker fragmentRendererTracker, String rendererKey) {

		if (fragmentEntry != null) {
			return fragmentEntry.getName();
		}

		JSONObject editableValuesJSONObject = null;

		try {
			editableValuesJSONObject = JSONFactoryUtil.createJSONObject(
				fragmentEntryLink.getEditableValues());
		}
		catch (JSONException jsonException) {
			return null;
		}

		String portletId = editableValuesJSONObject.getString("portletId");

		if (Validator.isNotNull(portletId)) {
			return PortalUtil.getPortletTitle(
				portletId, LocaleUtil.getSiteDefault());
		}

		if (Validator.isNull(rendererKey)) {
			rendererKey =
				FragmentRendererConstants.FRAGMENT_ENTRY_FRAGMENT_RENDERER_KEY;
		}

		FragmentRenderer fragmentRenderer =
			fragmentRendererTracker.getFragmentRenderer(rendererKey);

		return fragmentRenderer.getLabel(LocaleUtil.getSiteDefault());
	}

	private static List<FragmentContentField> _getTextFragmentContentFields(
		Map<String, String> editableTypes, JSONObject jsonObject) {

		List<FragmentContentField> fragmentContentFields = new ArrayList<>();

		Set<String> textIds = jsonObject.keySet();

		for (String textId : textIds) {
			fragmentContentFields.add(
				_toFragmentContentField(editableTypes, jsonObject, textId));
		}

		return fragmentContentFields;
	}

	private static FragmentContentField _toFragmentContentField(
		Map<String, String> editableTypes, JSONObject jsonObject,
		String textId) {

		JSONObject textJSONObject = jsonObject.getJSONObject(textId);

		return new FragmentContentField() {
			{
				id = textId;

				setFragmentLink(
					() -> {
						JSONObject configJSONObject =
							textJSONObject.getJSONObject("config");

						if (configJSONObject.isNull("href")) {
							return null;
						}

						return new FragmentLink() {
							{
								value = new InlineLink() {
									{
										href = configJSONObject.getString(
											"href");
									}
								};

								setTarget(
									() -> {
										String target =
											configJSONObject.getString(
												"target");

										if (Validator.isNull(target)) {
											return null;
										}

										return Target.create(
											StringUtil.upperCaseFirstLetter(
												target.substring(1)));
									});
							}
						};
					});
				setValue(
					() -> {
						String type = editableTypes.getOrDefault(
							textId, "text");

						if (Objects.equals(type, "image")) {
							return _toFragmentContentFieldImage(textJSONObject);
						}

						return _toFragmentContentFieldText(textJSONObject);
					});
			}
		};
	}

	private static FragmentContentFieldImage _toFragmentContentFieldImage(
		JSONObject jsonObject) {

		return new FragmentContentFieldImage() {
			{
				fragmentImage = new FragmentImage() {
					{
						title = HashMapBuilder.<String, Object>put(
							"value_i18n", _toLocaleMap(jsonObject, "title")
						).build();
						url = HashMapBuilder.<String, Object>put(
							"value_i18n", _toLocaleMap(jsonObject, "url")
						).build();
					}
				};
			}
		};
	}

	private static FragmentContentFieldText _toFragmentContentFieldText(
		JSONObject jsonObject) {

		return new FragmentContentFieldText() {
			{
				text = new InlineValue() {
					{
						value_i18n = _toLocaleMap(jsonObject);
					}
				};
			}
		};
	}

	private static Map<String, String> _toLocaleMap(JSONObject jsonObject) {
		return new HashMap<String, String>() {
			{
				Set<String> keys = jsonObject.keySet();

				Iterator<String> iterator = keys.iterator();

				while (iterator.hasNext()) {
					String key = iterator.next();

					if (!key.equals("classNameId") && !key.equals("classPK") &&
						!key.equals("config") && !key.equals("defaultValue") &&
						!key.equals("fieldId")) {

						put(key, jsonObject.getString(key));
					}
				}
			}
		};
	}

	private static Map<String, String> _toLocaleMap(
		JSONObject jsonObject, String key) {

		return new HashMap<String, String>() {
			{
				Set<String> locales = jsonObject.keySet();

				Iterator<String> iterator = locales.iterator();

				while (iterator.hasNext()) {
					String locale = iterator.next();

					if (!locale.equals("config") &&
						!locale.equals("defaultValue")) {

						JSONObject localizedJSONObject =
							jsonObject.getJSONObject(locale);

						put(locale, localizedJSONObject.getString(key));
					}
				}
			}
		};
	}

	private static Map<String, String> _toMap(JSONObject jsonObject) {
		HashMap<String, String> map = new HashMap<String, String>() {
			{
				Set<String> keys = jsonObject.keySet();

				Iterator<String> iterator = keys.iterator();

				while (iterator.hasNext()) {
					String key = iterator.next();

					String value = jsonObject.getString(key);

					if (Validator.isNotNull(value)) {
						put(key, value);
					}
				}
			}
		};

		if (map.isEmpty()) {
			return null;
		}

		return map;
	}

	private static Map<String, String> _toMap(
		JSONObject jsonObject, String key) {

		return new HashMap<String, String>() {
			{
				Set<String> locales = jsonObject.keySet();

				Iterator<String> iterator = locales.iterator();

				while (iterator.hasNext()) {
					String locale = iterator.next();

					if (!locale.equals("config") &&
						!locale.equals("defaultValue")) {

						JSONObject localizedJSONObject =
							jsonObject.getJSONObject(locale);

						put(key, localizedJSONObject.getString(key));
					}
				}
			}
		};
	}

}