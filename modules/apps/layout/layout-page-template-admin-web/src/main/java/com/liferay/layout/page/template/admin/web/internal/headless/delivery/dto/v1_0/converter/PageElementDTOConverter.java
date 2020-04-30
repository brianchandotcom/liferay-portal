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

package com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.converter;

import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.headless.delivery.dto.v1_0.Fragment;
import com.liferay.headless.delivery.dto.v1_0.FragmentImage;
import com.liferay.headless.delivery.dto.v1_0.FragmentInlineValue;
import com.liferay.headless.delivery.dto.v1_0.FragmentMappedValue;
import com.liferay.headless.delivery.dto.v1_0.Mapping;
import com.liferay.headless.delivery.dto.v1_0.PageCollectionDefinition;
import com.liferay.headless.delivery.dto.v1_0.PageCollectionItemDefinition;
import com.liferay.headless.delivery.dto.v1_0.PageColumnDefinition;
import com.liferay.headless.delivery.dto.v1_0.PageDropZoneDefinition;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.headless.delivery.dto.v1_0.PageRowDefinition;
import com.liferay.headless.delivery.dto.v1_0.PageSectionDefinition;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.page.template.util.PaddingConverter;
import com.liferay.layout.util.structure.CollectionItemLayoutStructureItem;
import com.liferay.layout.util.structure.CollectionLayoutStructureItem;
import com.liferay.layout.util.structure.ColumnLayoutStructureItem;
import com.liferay.layout.util.structure.ContainerLayoutStructureItem;
import com.liferay.layout.util.structure.DropZoneLayoutStructureItem;
import com.liferay.layout.util.structure.FragmentLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.RootLayoutStructureItem;
import com.liferay.layout.util.structure.RowLayoutStructureItem;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = PageElementDTOConverter.class)
public class PageElementDTOConverter {

	public PageElement toDTO(
		Layout layout, boolean saveInlineContent,
		boolean saveMappingConfiguration, long segmentsExperienceId) {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(), _portal.getClassNameId(Layout.class),
					layout.getPlid());

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(segmentsExperienceId));

		LayoutStructureItem mainLayoutStructureItem =
			layoutStructure.getMainLayoutStructureItem();

		List<PageElement> mainPageElements = new ArrayList<>();

		for (String childItemId :
				mainLayoutStructureItem.getChildrenItemIds()) {

			mainPageElements.add(
				toDTO(
					layout.getGroupId(), layoutStructure,
					layoutStructure.getLayoutStructureItem(childItemId),
					saveInlineContent, saveMappingConfiguration,
					segmentsExperienceId));
		}

		PageElement pageElement = toDTO(
			layout.getGroupId(), mainLayoutStructureItem, saveInlineContent,
			saveMappingConfiguration);

		if (!mainPageElements.isEmpty()) {
			pageElement.setPageElements(
				mainPageElements.toArray(new PageElement[0]));
		}

		return pageElement;
	}

	public PageElement toDTO(
		long groupId, LayoutStructure layoutStructure,
		LayoutStructureItem layoutStructureItem, boolean saveInlineContent,
		boolean saveMappingConfiguration, long segmentsExperienceId) {

		List<PageElement> pageElements = new ArrayList<>();

		List<String> childrenItemIds = layoutStructureItem.getChildrenItemIds();

		for (String childItemId : childrenItemIds) {
			LayoutStructureItem childLayoutStructureItem =
				layoutStructure.getLayoutStructureItem(childItemId);

			List<String> grandChildrenItemIds =
				childLayoutStructureItem.getChildrenItemIds();

			if (grandChildrenItemIds.isEmpty()) {
				pageElements.add(
					toDTO(
						groupId, childLayoutStructureItem, saveInlineContent,
						saveMappingConfiguration));
			}
			else {
				pageElements.add(
					toDTO(
						groupId, layoutStructure, childLayoutStructureItem,
						saveInlineContent, saveMappingConfiguration,
						segmentsExperienceId));
			}
		}

		PageElement pageElement = toDTO(
			groupId, layoutStructureItem, saveInlineContent,
			saveMappingConfiguration);

		if (!pageElements.isEmpty()) {
			pageElement.setPageElements(
				pageElements.toArray(new PageElement[0]));
		}

		return pageElement;
	}

	public PageElement toDTO(
		long groupId, LayoutStructureItem layoutStructureItem,
		boolean saveInlineContent, boolean saveMappingConfiguration) {

		if (layoutStructureItem instanceof CollectionLayoutStructureItem) {
			CollectionLayoutStructureItem collectionLayoutStructureItem =
				(CollectionLayoutStructureItem)layoutStructureItem;

			return new PageElement() {
				{
					definition = new PageCollectionDefinition() {
						{
							collectionConfig = _getConfigAsMap(
								collectionLayoutStructureItem.
									getCollectionJSONObject());
							numberOfColumns =
								collectionLayoutStructureItem.
									getNumberOfColumns();
							numberOfItems =
								collectionLayoutStructureItem.
									getNumberOfItems();
						}
					};
					type = PageElement.Type.COLLECTION;
				}
			};
		}

		if (layoutStructureItem instanceof CollectionItemLayoutStructureItem) {
			CollectionItemLayoutStructureItem
				collectionItemLayoutStructureItem =
					(CollectionItemLayoutStructureItem)layoutStructureItem;

			return new PageElement() {
				{
					definition = new PageCollectionItemDefinition() {
						{
							collectionItemConfig = _getConfigAsMap(
								collectionItemLayoutStructureItem.
									getItemConfigJSONObject());
						}
					};
					type = PageElement.Type.COLLECTION_ITEM;
				}
			};
		}

		if (layoutStructureItem instanceof ColumnLayoutStructureItem) {
			ColumnLayoutStructureItem columnLayoutStructureItem =
				(ColumnLayoutStructureItem)layoutStructureItem;

			return new PageElement() {
				{
					definition = new PageColumnDefinition() {
						{
							size = columnLayoutStructureItem.getSize();
						}
					};
					type = PageElement.Type.COLUMN;
				}
			};
		}

		if (layoutStructureItem instanceof ContainerLayoutStructureItem) {
			ContainerLayoutStructureItem containerLayoutStructureItem =
				(ContainerLayoutStructureItem)layoutStructureItem;

			return new PageElement() {
				{
					definition = new PageSectionDefinition() {
						{
							backgroundColor = GetterUtil.getString(
								containerLayoutStructureItem.
									getBackgroundColorCssClass(),
								null);
							backgroundImage = _toBackgroundFragmentImage(
								containerLayoutStructureItem.
									getBackgroundImageJSONObject(),
								saveMappingConfiguration);
							layout =
								new com.liferay.headless.delivery.dto.v1_0.
									Layout() {

									{
										paddingBottom =
											PaddingConverter.
												convertToExternalValue(
													containerLayoutStructureItem.
														getPaddingBottom());
										paddingHorizontal =
											PaddingConverter.
												convertToExternalValue(
													containerLayoutStructureItem.
														getPaddingHorizontal());
										paddingTop =
											PaddingConverter.
												convertToExternalValue(
													containerLayoutStructureItem.
														getPaddingTop());

										setContainerType(
											() -> {
												String containerType =
													containerLayoutStructureItem.
														getContainerType();

												if (Validator.isNull(
														containerType)) {

													return null;
												}

												return ContainerType.create(
													StringUtil.
														upperCaseFirstLetter(
															containerType));
											});
									}
								};
						}
					};
					type = PageElement.Type.SECTION;
				}
			};
		}

		if (layoutStructureItem instanceof DropZoneLayoutStructureItem) {
			DropZoneLayoutStructureItem dropZoneLayoutStructureItem =
				(DropZoneLayoutStructureItem)layoutStructureItem;

			return new PageElement() {
				{
					definition = new PageDropZoneDefinition() {
						{
							fragmentSettings = _toFragmentSettingsMap(
								dropZoneLayoutStructureItem, groupId);
						}
					};
					type = PageElement.Type.DROP_ZONE;
				}
			};
		}

		if (layoutStructureItem instanceof FragmentLayoutStructureItem) {
			FragmentLayoutStructureItem fragmentLayoutStructureItem =
				(FragmentLayoutStructureItem)layoutStructureItem;

			FragmentEntryLink fragmentEntryLink =
				_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
					fragmentLayoutStructureItem.getFragmentEntryLinkId());

			if (fragmentEntryLink == null) {
				return null;
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

			if (Validator.isNull(portletId)) {
				return new PageElement() {
					{
						definition =
							_pageFragmentInstanceDefinitionDTOConverter.toDTO(
								fragmentLayoutStructureItem, saveInlineContent,
								saveMappingConfiguration);
						type = PageElement.Type.FRAGMENT;
					}
				};
			}

			String instanceId = editableValuesJSONObject.getString(
				"instanceId");

			return new PageElement() {
				{
					definition =
						_pageWidgetInstanceDefinitionDTOConverter.toDTO(
							fragmentEntryLink.getClassPK(),
							PortletIdCodec.encode(portletId, instanceId));
					type = PageElement.Type.WIDGET;
				}
			};
		}

		if (layoutStructureItem instanceof RootLayoutStructureItem) {
			return new PageElement() {
				{
					type = PageElement.Type.ROOT;
				}
			};
		}

		if (layoutStructureItem instanceof RowLayoutStructureItem) {
			RowLayoutStructureItem rowLayoutStructureItem =
				(RowLayoutStructureItem)layoutStructureItem;

			return new PageElement() {
				{
					definition = new PageRowDefinition() {
						{
							gutters = rowLayoutStructureItem.isGutters();
							numberOfColumns =
								rowLayoutStructureItem.getNumberOfColumns();
						}
					};
					type = PageElement.Type.ROW;
				}
			};
		}

		return null;
	}

	private Map<String, Object> _getConfigAsMap(JSONObject jsonObject) {
		if (jsonObject == null) {
			return null;
		}

		return new HashMap<String, Object>() {
			{
				Set<String> keys = jsonObject.keySet();

				Iterator<String> iterator = keys.iterator();

				while (iterator.hasNext()) {
					String key = iterator.next();

					put(key, jsonObject.get(key));
				}
			}
		};
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

	private boolean _isFragmentEntryKey(String fragmentEntryKey, long groupId) {
		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.fetchFragmentEntry(
				groupId, fragmentEntryKey);

		if (fragmentEntry != null) {
			return true;
		}

		fragmentEntry = _fragmentCollectionContributorTracker.getFragmentEntry(
			fragmentEntryKey);

		if (fragmentEntry != null) {
			return true;
		}

		FragmentRenderer fragmentRenderer =
			_fragmentRendererTracker.getFragmentRenderer(fragmentEntryKey);

		if (fragmentRenderer != null) {
			return true;
		}

		return false;
	}

	private boolean _isSaveFragmentMappedValue(
		JSONObject jsonObject, boolean saveMapping) {

		if (saveMapping && jsonObject.has("classNameId") &&
			jsonObject.has("classPK") && jsonObject.has("fieldId")) {

			return true;
		}

		if (saveMapping && jsonObject.has("mappedField")) {
			return true;
		}

		return false;
	}

	private FragmentImage _toBackgroundFragmentImage(
		JSONObject jsonObject, boolean saveMappingConfiguration) {

		if (jsonObject == null) {
			return null;
		}

		String urlValue = jsonObject.getString("url");

		return new FragmentImage() {
			{
				title = _toTitleFragmentInlineValue(jsonObject, urlValue);

				setUrl(
					() -> {
						if (_isSaveFragmentMappedValue(
								jsonObject, saveMappingConfiguration)) {

							return _toFragmentMappedValue(
								_toDefaultMappingValue(
									jsonObject,
									_getImageURLTransformerFunction()),
								jsonObject);
						}

						if (Validator.isNull(urlValue)) {
							return null;
						}

						return new FragmentInlineValue() {
							{
								value = urlValue;
							}
						};
					});
			}
		};
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

		InfoDisplayContributor infoDisplayContributor =
			_infoDisplayContributorTracker.getInfoDisplayContributor(className);

		if (infoDisplayContributor == null) {
			return null;
		}

		long classPK = jsonObject.getLong("classPK");

		try {
			InfoDisplayObjectProvider infoDisplayObjectProvider =
				infoDisplayContributor.getInfoDisplayObjectProvider(classPK);

			Map<String, Object> fieldValues =
				infoDisplayContributor.getInfoDisplayFieldsValues(
					infoDisplayObjectProvider.getDisplayObject(),
					LocaleUtil.getMostRelevantLocale());

			Object fieldValue = fieldValues.get(
				jsonObject.getString("fieldId"));

			if (transformerFunction != null) {
				fieldValue = transformerFunction.apply(fieldValue);
			}

			String valueString = GetterUtil.getString(fieldValue);

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
			_log.error("Unable to get default mapped value", exception);
		}

		return null;
	}

	private FragmentMappedValue _toFragmentMappedValue(
		FragmentInlineValue fragmentInlineValue, JSONObject jsonObject) {

		return new FragmentMappedValue() {
			{
				mapping = new Mapping() {
					{
						defaultValue = fragmentInlineValue;

						setFieldKey(
							() -> {
								String fieldId = jsonObject.getString(
									"fieldId");

								if (Validator.isNotNull(fieldId)) {
									return fieldId;
								}

								return jsonObject.getString("mappedField");
							});
						setItemKey(
							() -> {
								String classNameId = jsonObject.getString(
									"classNameId");

								if (Validator.isNull(classNameId)) {
									return null;
								}

								String classPK = jsonObject.getString(
									"classPK");

								if (Validator.isNull(classPK)) {
									return null;
								}

								return StringBundler.concat(
									classNameId, StringPool.POUND, classPK);
							});
					}
				};
			}
		};
	}

	private Fragment[] _toFragments(
		List<String> fragmentEntryKeys, long groupId) {

		List<Fragment> fragments = new ArrayList<>();

		for (String fragmentEntryKey : fragmentEntryKeys) {
			if (_isFragmentEntryKey(fragmentEntryKey, groupId)) {
				fragments.add(
					new Fragment() {
						{
							key = fragmentEntryKey;
						}
					});
			}
		}

		return fragments.toArray(new Fragment[0]);
	}

	private Map<String, Fragment[]> _toFragmentSettingsMap(
		DropZoneLayoutStructureItem dropZoneLayoutStructureItem, long groupId) {

		if (dropZoneLayoutStructureItem.isAllowNewFragmentEntries()) {
			return HashMapBuilder.put(
				"unallowedFragments",
				_toFragments(
					dropZoneLayoutStructureItem.getFragmentEntryKeys(), groupId)
			).build();
		}

		return HashMapBuilder.put(
			"allowedFragments",
			_toFragments(
				dropZoneLayoutStructureItem.getFragmentEntryKeys(), groupId)
		).build();
	}

	private FragmentInlineValue _toTitleFragmentInlineValue(
		JSONObject jsonObject, String urlValue) {

		String title = jsonObject.getString("title");

		if (Validator.isNull(title) || title.equals(urlValue)) {
			return null;
		}

		return new FragmentInlineValue() {
			{
				value = title;
			}
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PageElementDTOConverter.class);

	@Reference
	private FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Reference
	private FragmentRendererTracker _fragmentRendererTracker;

	@Reference
	private InfoDisplayContributorTracker _infoDisplayContributorTracker;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private PageFragmentInstanceDefinitionDTOConverter
		_pageFragmentInstanceDefinitionDTOConverter;

	@Reference
	private PageWidgetInstanceDefinitionDTOConverter
		_pageWidgetInstanceDefinitionDTOConverter;

	@Reference
	private Portal _portal;

}