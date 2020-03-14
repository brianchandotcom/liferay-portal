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

package com.liferay.layout.page.template.headless.delivery.dto.v1_0;

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
import com.liferay.headless.delivery.dto.v1_0.Fragment;
import com.liferay.headless.delivery.dto.v1_0.FragmentField;
import com.liferay.headless.delivery.dto.v1_0.FragmentFieldBackgroundImage;
import com.liferay.headless.delivery.dto.v1_0.FragmentFieldHTML;
import com.liferay.headless.delivery.dto.v1_0.FragmentFieldImage;
import com.liferay.headless.delivery.dto.v1_0.FragmentFieldText;
import com.liferay.headless.delivery.dto.v1_0.FragmentImage;
import com.liferay.headless.delivery.dto.v1_0.FragmentInstanceDefinition;
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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * @author Rubén Pulido
 */
public class FragmentInstanceDefinitionConverterUtil {

	public static FragmentInstanceDefinition toFragmentInstanceDefinition(
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		FragmentLayoutStructureItem fragmentLayoutStructureItem,
		FragmentRendererTracker fragmentRendererTracker) {

		return toFragmentInstanceDefinition(
			fragmentCollectionContributorTracker,
			fragmentEntryConfigurationParser, fragmentLayoutStructureItem,
			fragmentRendererTracker, true, true);
	}

	public static FragmentInstanceDefinition toFragmentInstanceDefinition(
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		FragmentLayoutStructureItem fragmentLayoutStructureItem,
		FragmentRendererTracker fragmentRendererTracker,
		boolean saveInlineContent, boolean saveMappingConfiguration) {

		return toFragmentInstanceDefinition(
			fragmentCollectionContributorTracker,
			fragmentEntryConfigurationParser, fragmentLayoutStructureItem,
			fragmentRendererTracker, saveInlineContent,
			saveMappingConfiguration, 0);
	}

	public static FragmentInstanceDefinition toFragmentInstanceDefinition(
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		FragmentLayoutStructureItem fragmentLayoutStructureItem,
		FragmentRendererTracker fragmentRendererTracker,
		boolean saveInlineContent, boolean saveMappingConfiguration,
		long segmentsExperienceId) {

		FragmentEntryLink fragmentEntryLink =
			FragmentEntryLinkLocalServiceUtil.fetchFragmentEntryLink(
				fragmentLayoutStructureItem.getFragmentEntryLinkId());

		if (fragmentEntryLink == null) {
			return null;
		}

		String rendererKey = fragmentEntryLink.getRendererKey();

		FragmentEntry fragmentEntry = _getFragmentEntry(
			fragmentCollectionContributorTracker,
			fragmentEntryLink.getFragmentEntryId(), rendererKey);

		return new FragmentInstanceDefinition() {
			{
				fragment = new Fragment() {
					{
						collectionName = _getFragmentCollectionName(
							fragmentCollectionContributorTracker, fragmentEntry,
							fragmentRendererTracker, rendererKey);
						key = _getFragmentKey(fragmentEntry, rendererKey);
						name = _getFragmentName(
							fragmentEntry, fragmentEntryLink,
							fragmentRendererTracker, rendererKey);
					}
				};
				fragmentConfig = _getFragmentConfig(
					fragmentEntryConfigurationParser, fragmentEntryLink);
				fragmentFields = _getFragmentFields(
					fragmentEntryLink, saveInlineContent, segmentsExperienceId);
			}
		};
	}

	private static List<String> _getAvailableLanguageIds() {
		Set<Locale> availableLocales = LanguageUtil.getAvailableLocales();

		Stream<Locale> stream = availableLocales.stream();

		return stream.map(
			availableLocale -> LanguageUtil.getLanguageId(availableLocale)
		).collect(
			Collectors.toList()
		);
	}

	private static List<FragmentField> _getBackgroundImageFragmentFields(
		JSONObject jsonObject, long segmentsExperienceId) {

		List<FragmentField> fragmentFields = new ArrayList<>();

		Set<String> backgroundImageIds = jsonObject.keySet();

		for (String backgroundImageId : backgroundImageIds) {
			JSONObject imageJSONObject = jsonObject.getJSONObject(
				backgroundImageId);

			Map<String, String> localeMap = _toLocaleMap(
				imageJSONObject, segmentsExperienceId);

			fragmentFields.add(
				new FragmentField() {
					{
						id = backgroundImageId;
						value = new FragmentFieldBackgroundImage() {
							{
								backgroundImage = new FragmentImage() {
									{
										title = _toTitleInlineValue(
											imageJSONObject, localeMap);
										url = new InlineValue() {
											{
												value_i18n = localeMap;
											}
										};
									}
								};
							}
						};
					}
				});
		}

		return fragmentFields;
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

							value = valueJSONObject.getString("color");
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

	private static FragmentField[] _getFragmentFields(
		FragmentEntryLink fragmentEntryLink, boolean saveInlineContent,
		long segmentsExperienceId) {

		if (!saveInlineContent) {
			return new FragmentField[0];
		}

		JSONObject editableValuesJSONObject = null;

		try {
			editableValuesJSONObject = JSONFactoryUtil.createJSONObject(
				fragmentEntryLink.getEditableValues());
		}
		catch (JSONException jsonException) {
			return null;
		}

		List<FragmentField> fragmentFields = new ArrayList<>();

		fragmentFields.addAll(
			_getBackgroundImageFragmentFields(
				editableValuesJSONObject.getJSONObject(
					"com.liferay.fragment.entry.processor.background.image." +
						"BackgroundImageFragmentEntryProcessor"),
				segmentsExperienceId));

		Map<String, String> editableTypes = _getEditableTypes(
			fragmentEntryLink.getHtml());

		fragmentFields.addAll(
			_getTextFragmentFields(
				editableTypes,
				editableValuesJSONObject.getJSONObject(
					"com.liferay.fragment.entry.processor.editable." +
						"EditableFragmentEntryProcessor"),
				segmentsExperienceId));

		return fragmentFields.toArray(new FragmentField[0]);
	}

	private static String _getFragmentKey(
		FragmentEntry fragmentEntry, String rendererKey) {

		if (fragmentEntry != null) {
			return fragmentEntry.getFragmentEntryKey();
		}

		return rendererKey;
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

	private static List<FragmentField> _getTextFragmentFields(
		Map<String, String> editableTypes, JSONObject jsonObject,
		long segmentsExperienceId) {

		List<FragmentField> fragmentFields = new ArrayList<>();

		Set<String> textIds = jsonObject.keySet();

		for (String textId : textIds) {
			fragmentFields.add(
				_toFragmentField(
					editableTypes, jsonObject, segmentsExperienceId, textId));
		}

		return fragmentFields;
	}

	private static InlineValue _toDescriptionInlineValue(
		JSONObject jsonObject) {

		JSONObject configJSONObject = jsonObject.getJSONObject("config");

		if (configJSONObject == null) {
			return null;
		}

		String alt = configJSONObject.getString("alt");

		if (Validator.isNull(alt)) {
			return null;
		}

		return new InlineValue() {
			{
				value = alt;
			}
		};
	}

	private static FragmentField _toFragmentField(
		Map<String, String> editableTypes, JSONObject jsonObject,
		long segmentsExperienceId, String textId) {

		JSONObject textJSONObject = jsonObject.getJSONObject(textId);

		return new FragmentField() {
			{
				id = textId;

				setValue(
					() -> {
						String type = editableTypes.getOrDefault(
							textId, "text");

						if (Objects.equals(type, "html")) {
							return _toFragmentFieldHTML(
								textJSONObject, segmentsExperienceId);
						}

						if (Objects.equals(type, "image")) {
							return _toFragmentFieldImage(
								textJSONObject, segmentsExperienceId);
						}

						return _toFragmentFieldText(
							textJSONObject, segmentsExperienceId);
					});
			}
		};
	}

	private static FragmentFieldHTML _toFragmentFieldHTML(
		JSONObject jsonObject, long segmentsExperienceId) {

		return new FragmentFieldHTML() {
			{
				html = new InlineValue() {
					{
						value_i18n = _toLocaleMap(
							jsonObject, segmentsExperienceId);
					}
				};
			}
		};
	}

	private static FragmentFieldImage _toFragmentFieldImage(
		JSONObject jsonObject, long segmentsExperienceId) {

		Map<String, String> localeMap = _toLocaleMap(
			jsonObject, segmentsExperienceId);

		return new FragmentFieldImage() {
			{
				fragmentImage = new FragmentImage() {
					{
						description = _toDescriptionInlineValue(jsonObject);
						title = _toTitleInlineValue(jsonObject, localeMap);
						url = new InlineValue() {
							{
								value_i18n = localeMap;
							}
						};
					}
				};
				fragmentLink = _toFragmentLink(jsonObject);
			}
		};
	}

	private static FragmentFieldText _toFragmentFieldText(
		JSONObject jsonObject, long segmentsExperienceId) {

		return new FragmentFieldText() {
			{
				fragmentLink = _toFragmentLink(jsonObject);
				text = new InlineValue() {
					{
						value_i18n = _toLocaleMap(
							jsonObject, segmentsExperienceId);
					}
				};
			}
		};
	}

	private static FragmentLink _toFragmentLink(JSONObject jsonObject) {
		JSONObject configJSONObject = jsonObject.getJSONObject("config");

		if (configJSONObject.isNull("href")) {
			return null;
		}

		return new FragmentLink() {
			{
				value = new InlineLink() {
					{
						href = configJSONObject.getString("href");
					}
				};

				setTarget(
					() -> {
						String target = configJSONObject.getString("target");

						if (Validator.isNull(target)) {
							return null;
						}

						return Target.create(
							StringUtil.upperCaseFirstLetter(
								target.substring(1)));
					});
			}
		};
	}

	private static Map<String, String> _toLocaleMap(
		JSONObject jsonObject, long segmentsExperienceId) {

		if (jsonObject.has("segments-experience-id-" + segmentsExperienceId)) {
			return _toLocaleMap(
				jsonObject.getJSONObject(
					"segments-experience-id-" + segmentsExperienceId),
				segmentsExperienceId);
		}

		return new HashMap<String, String>() {
			{
				List<String> availableLanguageIds = _getAvailableLanguageIds();

				Set<String> keys = jsonObject.keySet();

				Iterator<String> iterator = keys.iterator();

				while (iterator.hasNext()) {
					String key = iterator.next();

					if (availableLanguageIds.contains(key)) {
						put(key, jsonObject.getString(key));
					}
				}
			}
		};
	}

	private static InlineValue _toTitleInlineValue(
		JSONObject jsonObject, Map<String, String> map) {

		JSONObject configJSONObject = jsonObject.getJSONObject("config");

		if (configJSONObject == null) {
			return null;
		}

		String imageTitle = configJSONObject.getString("imageTitle");

		if (Validator.isNull(imageTitle) || map.containsValue(imageTitle)) {
			return null;
		}

		return new InlineValue() {
			{
				value = imageTitle;
			}
		};
	}

}