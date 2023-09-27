/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.dto.v1_0.mapper;

import com.liferay.fragment.contributor.FragmentCollectionContributorRegistry;
import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.fragment.entry.processor.editable.element.constants.ActionEditableElementConstants;
import com.liferay.fragment.entry.processor.util.EditableFragmentEntryProcessorUtil;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.headless.delivery.dto.v1_0.ActionExecutionResult;
import com.liferay.headless.delivery.dto.v1_0.ClassPKReference;
import com.liferay.headless.delivery.dto.v1_0.Fragment;
import com.liferay.headless.delivery.dto.v1_0.FragmentField;
import com.liferay.headless.delivery.dto.v1_0.FragmentFieldAction;
import com.liferay.headless.delivery.dto.v1_0.FragmentFieldBackgroundImage;
import com.liferay.headless.delivery.dto.v1_0.FragmentFieldHTML;
import com.liferay.headless.delivery.dto.v1_0.FragmentFieldImage;
import com.liferay.headless.delivery.dto.v1_0.FragmentFieldText;
import com.liferay.headless.delivery.dto.v1_0.FragmentImage;
import com.liferay.headless.delivery.dto.v1_0.FragmentImageClassPKReference;
import com.liferay.headless.delivery.dto.v1_0.FragmentImageConfiguration;
import com.liferay.headless.delivery.dto.v1_0.FragmentInlineValue;
import com.liferay.headless.delivery.dto.v1_0.FragmentLink;
import com.liferay.headless.delivery.dto.v1_0.FragmentLinkValue;
import com.liferay.headless.delivery.dto.v1_0.FragmentMappedValue;
import com.liferay.headless.delivery.dto.v1_0.FragmentStyle;
import com.liferay.headless.delivery.dto.v1_0.FragmentViewport;
import com.liferay.headless.delivery.dto.v1_0.Mapping;
import com.liferay.headless.delivery.dto.v1_0.NoneActionExecutionResult;
import com.liferay.headless.delivery.dto.v1_0.NotificationActionExecutionResult;
import com.liferay.headless.delivery.dto.v1_0.PageFragmentInstanceDefinition;
import com.liferay.headless.delivery.dto.v1_0.SitePageActionExecutionResult;
import com.liferay.headless.delivery.dto.v1_0.URLActionExecutionResult;
import com.liferay.headless.delivery.dto.v1_0.WidgetInstance;
import com.liferay.headless.delivery.internal.dto.v1_0.mapper.util.FragmentMappedValueUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.mapper.util.LocalizedValueUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.mapper.util.StyledLayoutStructureItemUtil;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 * @author Javier de Arcos
 */
@Component(service = PageFragmentInstanceDefinitionMapper.class)
public class PageFragmentInstanceDefinitionMapper {

	public PageFragmentInstanceDefinition getPageFragmentInstanceDefinition(
		FragmentStyledLayoutStructureItem fragmentStyledLayoutStructureItem,
		FragmentStyle pageFragmentInstanceDefinitionFragmentStyle,
		FragmentViewport[] pageFragmentInstanceDefinitionFragmentViewports,
		boolean saveInlineContent, boolean saveMapping) {

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentStyledLayoutStructureItem.getFragmentEntryLinkId());

		if (fragmentEntryLink == null) {
			return null;
		}

		String rendererKey = fragmentEntryLink.getRendererKey();

		FragmentEntry fragmentEntry = _getFragmentEntry(
			_fragmentCollectionContributorRegistry,
			fragmentEntryLink.getFragmentEntryId(), rendererKey);

		return new PageFragmentInstanceDefinition() {
			{
				cssClasses = StyledLayoutStructureItemUtil.getCssClasses(
					fragmentStyledLayoutStructureItem);
				customCSS = StyledLayoutStructureItemUtil.getCustomCSS(
					fragmentStyledLayoutStructureItem);
				customCSSViewports =
					StyledLayoutStructureItemUtil.getCustomCSSViewports(
						fragmentStyledLayoutStructureItem);
				fragment = new Fragment() {
					{
						key = _getFragmentKey(fragmentEntry, rendererKey);

						setSiteKey(
							() -> {
								if ((fragmentEntry == null) ||
									(fragmentEntry.getGroupId() == 0)) {

									return null;
								}

								Group group = _groupLocalService.fetchGroup(
									fragmentEntry.getGroupId());

								if (group == null) {
									return null;
								}

								return group.getGroupKey();
							});
					}
				};
				fragmentConfig = _getFragmentConfig(fragmentEntryLink);
				fragmentFields = _getFragmentFields(
					fragmentEntryLink, saveInlineContent, saveMapping);
				fragmentStyle = pageFragmentInstanceDefinitionFragmentStyle;
				fragmentViewports =
					pageFragmentInstanceDefinitionFragmentViewports;
				indexed = fragmentStyledLayoutStructureItem.isIndexed();
				name = fragmentStyledLayoutStructureItem.getName();
				widgetInstances = _getWidgetInstances(fragmentEntryLink);
			}
		};
	}

	private List<FragmentField> _getBackgroundImageFragmentFields(
		JSONObject jsonObject, boolean saveMapping) {

		if (jsonObject == null) {
			return Collections.emptyList();
		}

		List<FragmentField> fragmentFields = new ArrayList<>();

		Set<String> backgroundImageIds = jsonObject.keySet();

		for (String backgroundImageId : backgroundImageIds) {
			JSONObject imageJSONObject = jsonObject.getJSONObject(
				backgroundImageId);

			Map<String, String> localizedValues =
				LocalizedValueUtil.toLocalizedValues(imageJSONObject);

			fragmentFields.add(
				new FragmentField() {
					{
						id = backgroundImageId;
						value = _toFragmentFieldBackgroundImage(
							imageJSONObject, localizedValues, saveMapping);
					}
				});
		}

		return fragmentFields;
	}

	private Map<String, Object> _getFragmentConfig(
		FragmentEntryLink fragmentEntryLink) {

		try {
			JSONObject editableValuesJSONObject = _jsonFactory.createJSONObject(
				fragmentEntryLink.getEditableValues());

			JSONObject configJSONObject =
				editableValuesJSONObject.getJSONObject(
					FragmentEntryProcessorConstants.
						KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR);

			if (configJSONObject == null) {
				configJSONObject =
					_fragmentEntryConfigurationParser.
						getConfigurationDefaultValuesJSONObject(
							fragmentEntryLink.getConfiguration());

				if (configJSONObject == null) {
					return Collections.emptyMap();
				}
			}

			JSONObject jsonObject = configJSONObject;

			return new HashMap<String, Object>() {
				{
					for (String key : jsonObject.keySet()) {
						Object value =
							_fragmentEntryConfigurationParser.getFieldValue(
								fragmentEntryLink.getConfiguration(),
								fragmentEntryLink.getEditableValues(),
								LocaleUtil.getMostRelevantLocale(), key);

						if (value == null) {
							value = jsonObject.get(key);
						}

						if (value instanceof JSONObject) {
							JSONObject valueJSONObject = (JSONObject)value;

							if (valueJSONObject.has("color")) {
								value = valueJSONObject.getString("color");
							}
						}

						if (value instanceof JSONArray ||
							value instanceof JSONObject) {

							JSONDeserializer<Map<String, Object>>
								jsonDeserializer =
									_jsonFactory.createJSONDeserializer();

							value = jsonDeserializer.deserialize(
								value.toString());
						}

						put(key, value);
					}
				}
			};
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}

			return null;
		}
	}

	private FragmentEntry _getFragmentEntry(
		FragmentCollectionContributorRegistry
			fragmentCollectionContributorRegistry,
		long fragmentEntryId, String rendererKey) {

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.fetchFragmentEntry(fragmentEntryId);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		Map<String, FragmentEntry> fragmentEntries =
			fragmentCollectionContributorRegistry.getFragmentEntries();

		return fragmentEntries.get(rendererKey);
	}

	private FragmentField[] _getFragmentFields(
		FragmentEntryLink fragmentEntryLink, boolean saveInlineContent,
		boolean saveMapping) {

		if (!saveInlineContent && !saveMapping) {
			return new FragmentField[0];
		}

		JSONObject editableValuesJSONObject = null;

		try {
			editableValuesJSONObject = _jsonFactory.createJSONObject(
				fragmentEntryLink.getEditableValues());
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}

			return null;
		}

		List<FragmentField> fragmentFields = new ArrayList<>(
			_getBackgroundImageFragmentFields(
				editableValuesJSONObject.getJSONObject(
					FragmentEntryProcessorConstants.
						KEY_BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR),
				saveMapping));

		JSONObject jsonObject = editableValuesJSONObject.getJSONObject(
			FragmentEntryProcessorConstants.
				KEY_EDITABLE_FRAGMENT_ENTRY_PROCESSOR);

		if (jsonObject != null) {
			fragmentFields.addAll(
				_getTextFragmentFields(
					EditableFragmentEntryProcessorUtil.getEditableTypes(
						fragmentEntryLink.getHtml()),
					jsonObject, saveInlineContent, saveMapping));
		}

		return fragmentFields.toArray(new FragmentField[0]);
	}

	private String _getFragmentKey(
		FragmentEntry fragmentEntry, String rendererKey) {

		if (fragmentEntry != null) {
			return fragmentEntry.getFragmentEntryKey();
		}

		return rendererKey;
	}

	private Function<Object, String> _getImageURLTransformerFunction() {
		return object -> {
			if (object instanceof JSONObject) {
				JSONObject jsonObject = (JSONObject)object;

				return jsonObject.getString("url");
			}

			return StringPool.BLANK;
		};
	}

	private List<FragmentField> _getTextFragmentFields(
		Map<String, String> editableTypes, JSONObject jsonObject,
		boolean saveInlineContent, boolean saveMapping) {

		List<FragmentField> fragmentFields = new ArrayList<>();

		Set<String> textIds = jsonObject.keySet();

		for (String textId : textIds) {
			fragmentFields.add(
				_toFragmentField(
					editableTypes, jsonObject, saveInlineContent, saveMapping,
					textId));
		}

		return fragmentFields;
	}

	private WidgetInstance[] _getWidgetInstances(
		FragmentEntryLink fragmentEntryLink) {

		List<String> fragmentEntryLinkPortletIds =
			_portletRegistry.getFragmentEntryLinkPortletIds(fragmentEntryLink);

		if (ListUtil.isNull(fragmentEntryLinkPortletIds)) {
			return null;
		}

		List<WidgetInstance> widgetInstances = new ArrayList<>();

		for (String fragmentEntryLinkPortletId : fragmentEntryLinkPortletIds) {
			widgetInstances.add(
				_widgetInstanceMapper.getWidgetInstance(
					fragmentEntryLink, fragmentEntryLinkPortletId));
		}

		return widgetInstances.toArray(new WidgetInstance[0]);
	}

	private ActionExecutionResult _toActionExecutionResult(
		JSONObject jsonObject, boolean saveInlineContent, boolean saveMapping) {

		if (jsonObject == null) {
			return null;
		}

		String interaction = jsonObject.getString("interaction", null);

		if (interaction.equals(
				ActionEditableElementConstants.INTERACTION_NONE)) {

			return new ActionExecutionResult() {
				{
					type = ActionExecutionResult.Type.NONE;
					value = new NoneActionExecutionResult() {
						{
							reload = jsonObject.getBoolean("reload");
						}
					};
				}
			};
		}
		else if (interaction.equals(
					ActionEditableElementConstants.INTERACTION_NOTIFICATION)) {

			return new ActionExecutionResult() {
				{
					type = ActionExecutionResult.Type.NOTIFICATION;

					setValue(
						() -> {
							if (!saveInlineContent || !jsonObject.has("text")) {
								return null;
							}

							return new NotificationActionExecutionResult() {
								{
									reload = jsonObject.getBoolean("reload");
									text = _toFragmentInlineValue(
										jsonObject.getJSONObject("text"));
								}
							};
						});
				}
			};
		}
		else if (interaction.equals(
					ActionEditableElementConstants.INTERACTION_PAGE)) {

			return new ActionExecutionResult() {
				{
					type = ActionExecutionResult.Type.PAGE;

					setValue(
						() -> {
							if (!saveMapping || !jsonObject.has("page")) {
								return null;
							}

							JSONObject pageJSONObject =
								jsonObject.getJSONObject("page");

							return new SitePageActionExecutionResult() {
								{
									itemReference =
										FragmentMappedValueUtil.
											toLayoutClassFieldsReference(
												pageJSONObject);
								}
							};
						});
				}
			};
		}
		else if (interaction.equals(
					ActionEditableElementConstants.INTERACTION_URL)) {

			return new ActionExecutionResult() {
				{
					type = ActionExecutionResult.Type.URL;

					setValue(
						() -> {
							if (!saveInlineContent || !jsonObject.has("url")) {
								return null;
							}

							return new URLActionExecutionResult() {
								{
									url = _toFragmentInlineValue(
										jsonObject.getJSONObject("url"));
								}
							};
						});
				}
			};
		}

		return null;
	}

	private Map<String, ClassPKReference> _toClassPKReferences(
		Map<String, JSONObject> localizedJSONObjects) {

		Map<String, ClassPKReference> classPKReferences = new HashMap<>();

		for (Map.Entry<String, JSONObject> entry :
				localizedJSONObjects.entrySet()) {

			JSONObject jsonObject = entry.getValue();

			classPKReferences.put(
				entry.getKey(),
				new ClassPKReference() {
					{
						className = FileEntry.class.getName();
						classPK = jsonObject.getLong("fileEntryId");
					}
				});
		}

		return classPKReferences;
	}

	private FragmentInlineValue _toDefaultMappingValue(
		JSONObject jsonObject, Function<Object, String> transformerFunction) {

		long classNameId = jsonObject.getLong("classNameId");

		if (classNameId == 0) {
			return null;
		}

		String className = null;

		try {
			className = _portal.getClassName(classNameId);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get class name for default mapping value",
					exception);
			}
		}

		if (Validator.isNull(className)) {
			return null;
		}

		InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemFieldValuesProvider.class, className);

		InfoItemObjectProvider<Object> infoItemObjectProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemObjectProvider.class, className,
				ClassPKInfoItemIdentifier.INFO_ITEM_SERVICE_FILTER);

		if ((infoItemFieldValuesProvider == null) ||
			(infoItemObjectProvider == null)) {

			return null;
		}

		long classPK = jsonObject.getLong("classPK");

		try {
			Object infoItem = infoItemObjectProvider.getInfoItem(
				new ClassPKInfoItemIdentifier(classPK));

			if (infoItem == null) {
				return null;
			}

			InfoFieldValue<Object> infoFieldValue =
				infoItemFieldValuesProvider.getInfoFieldValue(
					infoItem, jsonObject.getString("fieldId"));

			if (infoFieldValue == null) {
				return null;
			}

			Object infoFieldValueValue = infoFieldValue.getValue(
				LocaleUtil.getMostRelevantLocale());

			if (transformerFunction != null) {
				infoFieldValueValue = transformerFunction.apply(
					infoFieldValueValue);
			}

			String valueString = GetterUtil.getString(infoFieldValueValue);

			if (Validator.isNull(valueString)) {
				return null;
			}

			return new FragmentInlineValue() {
				{
					value = valueString;
				}
			};
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get default mapped value", exception);
			}
		}

		return null;
	}

	private FragmentInlineValue _toDescriptionFragmentInlineValue(
		JSONObject jsonObject) {

		JSONObject configJSONObject = jsonObject.getJSONObject("config");

		if (configJSONObject == null) {
			return null;
		}

		String alt = configJSONObject.getString("alt");

		if (Validator.isNull(alt)) {
			return null;
		}

		if (JSONUtil.isJSONObject(alt)) {
			JSONObject localizedJSONObject = configJSONObject.getJSONObject(
				"alt");

			Map<String, String> localizedValues = new HashMap<>();

			for (String key : localizedJSONObject.keySet()) {
				localizedValues.put(key, localizedJSONObject.getString(key));
			}

			return new FragmentInlineValue() {
				{
					value_i18n = localizedValues;
				}
			};
		}

		return new FragmentInlineValue() {
			{
				value = alt;
			}
		};
	}

	private FragmentField _toFragmentField(
		Map<String, String> editableTypes, JSONObject jsonObject,
		boolean saveInlineContent, boolean saveMapping, String textId) {

		JSONObject textJSONObject = jsonObject.getJSONObject(textId);

		return new FragmentField() {
			{
				id = textId;

				setValue(
					() -> {
						String type = editableTypes.getOrDefault(
							textId, "text");

						if (Objects.equals(type, "action")) {
							return _toFragmentFieldAction(
								textJSONObject, saveInlineContent, saveMapping);
						}

						if (Objects.equals(type, "html")) {
							return _toFragmentFieldHTML(
								textJSONObject, saveMapping);
						}

						if (Objects.equals(type, "image")) {
							return _toFragmentFieldImage(
								textJSONObject, saveMapping);
						}

						return _toFragmentFieldText(
							textJSONObject, saveMapping);
					});
			}
		};
	}

	private FragmentFieldAction _toFragmentFieldAction(
		JSONObject jsonObject, boolean saveInlineContent, boolean saveMapping) {

		JSONObject configJSONObject = jsonObject.getJSONObject("config");

		if (configJSONObject == null) {
			return null;
		}

		return new FragmentFieldAction() {
			{
				onError = _toActionExecutionResult(
					configJSONObject.getJSONObject("onError"),
					saveInlineContent, saveMapping);
				onSuccess = _toActionExecutionResult(
					configJSONObject.getJSONObject("onSuccess"),
					saveInlineContent, saveMapping);

				setAction(
					() -> {
						JSONObject mappedActionJSONObject =
							configJSONObject.getJSONObject("mappedAction");

						if (!FragmentMappedValueUtil.isSaveFragmentMappedValue(
								mappedActionJSONObject, saveMapping)) {

							return null;
						}

						return _toFragmentMappedValue(
							null, mappedActionJSONObject);
					});

				setText(
					() -> {
						if (FragmentMappedValueUtil.isSaveFragmentMappedValue(
								jsonObject, saveMapping)) {

							return _toFragmentMappedValue(
								_toDefaultMappingValue(jsonObject, null),
								jsonObject);
						}

						Map<String, String> localizedValues =
							LocalizedValueUtil.toLocalizedValues(jsonObject);

						if (MapUtil.isEmpty(localizedValues)) {
							return null;
						}

						return new FragmentInlineValue() {
							{
								value_i18n = localizedValues;
							}
						};
					});
			}
		};
	}

	private FragmentFieldBackgroundImage _toFragmentFieldBackgroundImage(
		JSONObject jsonObject, Map<String, String> localizedValues,
		boolean saveMapping) {

		return new FragmentFieldBackgroundImage() {
			{
				backgroundFragmentImage = new FragmentImage() {
					{
						title = _toTitleFragmentInlineValue(
							jsonObject, localizedValues);

						setUrl(
							() -> {
								if (FragmentMappedValueUtil.
										isSaveFragmentMappedValue(
											jsonObject, saveMapping)) {

									return _toFragmentMappedValue(
										_toDefaultMappingValue(
											jsonObject,
											_getImageURLTransformerFunction()),
										jsonObject);
								}

								return new FragmentInlineValue() {
									{
										value_i18n = localizedValues;
									}
								};
							});
					}
				};
			}
		};
	}

	private FragmentFieldHTML _toFragmentFieldHTML(
		JSONObject jsonObject, boolean saveMapping) {

		return new FragmentFieldHTML() {
			{
				setHtml(
					() -> {
						if (FragmentMappedValueUtil.isSaveFragmentMappedValue(
								jsonObject, saveMapping)) {

							return _toFragmentMappedValue(
								_toDefaultMappingValue(jsonObject, null),
								jsonObject);
						}

						return new FragmentInlineValue() {
							{
								value_i18n =
									LocalizedValueUtil.toLocalizedValues(
										jsonObject);
							}
						};
					});
			}
		};
	}

	private FragmentFieldImage _toFragmentFieldImage(
		JSONObject jsonObject, boolean saveMapping) {

		Map<String, JSONObject> localizedJSONObjects =
			_toLocalizedValueJSONObjects(jsonObject);
		Map<String, String> localizedValues =
			LocalizedValueUtil.toLocalizedValues(jsonObject);

		Map<String, String> localizedURLs = _toLocalizedURLs(
			localizedJSONObjects, localizedValues);

		return new FragmentFieldImage() {
			{
				fragmentImage = new FragmentImage() {
					{
						description = _toDescriptionFragmentInlineValue(
							jsonObject);
						title = _toTitleFragmentInlineValue(
							jsonObject, localizedValues);

						setFragmentImageClassPKReference(
							() -> {
								if (MapUtil.isEmpty(localizedJSONObjects) ||
									MapUtil.isNotEmpty(localizedURLs)) {

									return null;
								}

								return _toFragmentImageClassPKReference(
									jsonObject.getJSONObject("config"),
									localizedJSONObjects);
							});
						setUrl(
							() -> {
								if (FragmentMappedValueUtil.
										isSaveFragmentMappedValue(
											jsonObject, saveMapping)) {

									return _toFragmentMappedValue(
										_toDefaultMappingValue(
											jsonObject,
											_getImageURLTransformerFunction()),
										jsonObject);
								}

								return new FragmentInlineValue() {
									{
										value_i18n = localizedURLs;
									}
								};
							});
					}
				};
				fragmentLink = _toFragmentLink(jsonObject, saveMapping);
			}
		};
	}

	private FragmentFieldText _toFragmentFieldText(
		JSONObject jsonObject, boolean saveMapping) {

		return new FragmentFieldText() {
			{
				fragmentLink = _toFragmentLink(jsonObject, saveMapping);

				setText(
					() -> {
						if (FragmentMappedValueUtil.isSaveFragmentMappedValue(
								jsonObject, saveMapping)) {

							return _toFragmentMappedValue(
								_toDefaultMappingValue(jsonObject, null),
								jsonObject);
						}

						Map<String, String> localizedValues =
							LocalizedValueUtil.toLocalizedValues(jsonObject);

						if (MapUtil.isEmpty(localizedValues)) {
							return null;
						}

						return new FragmentInlineValue() {
							{
								value_i18n = localizedValues;
							}
						};
					});
			}
		};
	}

	private FragmentImageClassPKReference _toFragmentImageClassPKReference(
		JSONObject configJSONObject,
		Map<String, JSONObject> localizedJSONObjects) {

		JSONObject imageConfigurationJSONObject =
			configJSONObject.getJSONObject("imageConfiguration");

		return new FragmentImageClassPKReference() {
			{
				classPKReferences = _toClassPKReferences(localizedJSONObjects);
				fragmentImageConfiguration = new FragmentImageConfiguration() {
					{
						setLandscapeMobile(
							() -> {
								if (imageConfigurationJSONObject == null) {
									return null;
								}

								return imageConfigurationJSONObject.getString(
									"landscapeMobile", "auto");
							});
						setPortraitMobile(
							() -> {
								if (imageConfigurationJSONObject == null) {
									return null;
								}

								return imageConfigurationJSONObject.getString(
									"portraitMobile", "auto");
							});
						setTablet(
							() -> {
								if (imageConfigurationJSONObject == null) {
									return null;
								}

								return imageConfigurationJSONObject.getString(
									"tablet", "auto");
							});
					}
				};
			}
		};
	}

	private FragmentInlineValue _toFragmentInlineValue(JSONObject jsonObject) {
		return new FragmentInlineValue() {
			{
				value_i18n = LocalizedValueUtil.toLocalizedValues(jsonObject);
			}
		};
	}

	private FragmentLink _toFragmentLink(
		JSONObject jsonObject, boolean saveMapping) {

		JSONObject configJSONObject = jsonObject.getJSONObject("config");

		if (configJSONObject == null) {
			return null;
		}

		return new FragmentLink() {
			{
				value = _toFragmentLinkValue(configJSONObject, saveMapping);
				value_i18n = _toLocalizedFragmentLinkValues(
					configJSONObject, saveMapping);
			}
		};
	}

	private FragmentLinkValue _toFragmentLinkValue(
		JSONObject configJSONObject, boolean saveMapping) {

		boolean saveFragmentMappedValue =
			FragmentMappedValueUtil.isSaveFragmentMappedValue(
				configJSONObject, saveMapping);

		if ((configJSONObject == null) ||
			(configJSONObject.isNull("href") && !saveFragmentMappedValue)) {

			return null;
		}

		return new FragmentLinkValue() {
			{
				setHref(
					() -> {
						if (saveFragmentMappedValue) {
							return _toFragmentMappedValue(
								_toDefaultMappingValue(configJSONObject, null),
								configJSONObject);
						}

						return new FragmentInlineValue() {
							{
								setValue(
									() -> {
										JSONObject hrefJSONObject =
											configJSONObject.getJSONObject(
												"href");

										if (hrefJSONObject != null) {
											return null;
										}

										return configJSONObject.getString(
											"href");
									});
								setValue_i18n(
									() -> {
										JSONObject hrefJSONObject =
											configJSONObject.getJSONObject(
												"href");

										if (hrefJSONObject != null) {
											return JSONUtil.toStringMap(
												hrefJSONObject);
										}

										return null;
									});
							}
						};
					});

				setTarget(
					() -> {
						String target = configJSONObject.getString("target");

						if (Validator.isNull(target)) {
							return null;
						}

						if (StringUtil.equalsIgnoreCase(target, "_parent") ||
							StringUtil.equalsIgnoreCase(target, "_top")) {

							target = "_self";
						}

						return Target.create(
							StringUtil.upperCaseFirstLetter(
								target.substring(1)));
					});
			}
		};
	}

	private FragmentMappedValue _toFragmentMappedValue(
		FragmentInlineValue fragmentInlineValue, JSONObject jsonObject) {

		return new FragmentMappedValue() {
			{
				mapping = new Mapping() {
					{
						defaultFragmentInlineValue = fragmentInlineValue;
						fieldKey = FragmentMappedValueUtil.getFieldKey(
							jsonObject);
						itemReference = FragmentMappedValueUtil.toItemReference(
							jsonObject);
					}
				};
			}
		};
	}

	private Map<String, FragmentLinkValue> _toLocalizedFragmentLinkValues(
		JSONObject configJSONObject, boolean saveMapping) {

		Map<String, FragmentLinkValue> fragmentLinkValues = new HashMap<>();

		List<String> availableLanguageIds =
			LocalizedValueUtil.getAvailableLanguageIds();

		for (String languageId : availableLanguageIds) {
			JSONObject localizedJSONObject = configJSONObject.getJSONObject(
				languageId);

			FragmentLinkValue fragmentLinkValue = _toFragmentLinkValue(
				localizedJSONObject, saveMapping);

			if (fragmentLinkValue == null) {
				continue;
			}

			fragmentLinkValues.put(languageId, fragmentLinkValue);
		}

		if (fragmentLinkValues.isEmpty()) {
			return null;
		}

		return fragmentLinkValues;
	}

	private Map<String, String> _toLocalizedURLs(
		Map<String, JSONObject> localizedJSONObjects,
		Map<String, String> localizedValues) {

		HashMap<String, String> localizedURLs = new HashMap<String, String>() {
			{
				for (Map.Entry<String, JSONObject> entry :
						localizedJSONObjects.entrySet()) {

					JSONObject localizedJSONObject = entry.getValue();

					put(entry.getKey(), localizedJSONObject.getString("url"));
				}
			}
		};

		if (!localizedURLs.isEmpty()) {
			return localizedURLs;
		}

		return localizedValues;
	}

	private Map<String, JSONObject> _toLocalizedValueJSONObjects(
		JSONObject jsonObject) {

		return new HashMap<String, JSONObject>() {
			{
				List<String> availableLanguageIds =
					LocalizedValueUtil.getAvailableLanguageIds();

				Set<String> keys = jsonObject.keySet();

				for (String key : keys) {
					JSONObject valueJSONObject = jsonObject.getJSONObject(key);

					if (availableLanguageIds.contains(key) &&
						(valueJSONObject != null)) {

						put(key, valueJSONObject);
					}
				}
			}
		};
	}

	private FragmentInlineValue _toTitleFragmentInlineValue(
		JSONObject jsonObject, Map<String, String> localizedValues) {

		JSONObject configJSONObject = jsonObject.getJSONObject("config");

		if (configJSONObject == null) {
			return null;
		}

		String imageTitle = configJSONObject.getString("imageTitle");

		if (Validator.isNull(imageTitle) ||
			localizedValues.containsValue(imageTitle)) {

			return null;
		}

		return new FragmentInlineValue() {
			{
				value = imageTitle;
			}
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PageFragmentInstanceDefinitionMapper.class);

	@Reference
	private FragmentCollectionContributorRegistry
		_fragmentCollectionContributorRegistry;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

	@Reference
	private PortletRegistry _portletRegistry;

	@Reference
	private WidgetInstanceMapper _widgetInstanceMapper;

}