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

import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.headless.delivery.dto.v1_0.Fragment;
import com.liferay.headless.delivery.dto.v1_0.FragmentImage;
import com.liferay.headless.delivery.dto.v1_0.FragmentInlineValue;
import com.liferay.headless.delivery.dto.v1_0.Layout;
import com.liferay.headless.delivery.dto.v1_0.MasterPage;
import com.liferay.headless.delivery.dto.v1_0.PageCollectionDefinition;
import com.liferay.headless.delivery.dto.v1_0.PageCollectionItemDefinition;
import com.liferay.headless.delivery.dto.v1_0.PageColumnDefinition;
import com.liferay.headless.delivery.dto.v1_0.PageDefinition;
import com.liferay.headless.delivery.dto.v1_0.PageDropZoneDefinition;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.headless.delivery.dto.v1_0.PageRowDefinition;
import com.liferay.headless.delivery.dto.v1_0.PageSectionDefinition;
import com.liferay.headless.delivery.dto.v1_0.Settings;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ColorScheme;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Rubén Pulido
 */
public class PageDefinitionConverterUtil {

	public static PageDefinition toPageDefinition(
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		FragmentRendererTracker fragmentRendererTracker,
		InfoDisplayContributorTracker infoDisplayContributorTracker,
		com.liferay.portal.kernel.model.Layout layout) {

		return toPageDefinition(
			fragmentCollectionContributorTracker,
			fragmentEntryConfigurationParser, fragmentRendererTracker,
			infoDisplayContributorTracker, layout, true, true);
	}

	public static PageDefinition toPageDefinition(
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		FragmentRendererTracker fragmentRendererTracker,
		InfoDisplayContributorTracker infoDisplayContributorTracker,
		com.liferay.portal.kernel.model.Layout layout,
		boolean saveInlineContent, boolean saveMappingConfiguration) {

		return toPageDefinition(
			fragmentCollectionContributorTracker,
			fragmentEntryConfigurationParser, fragmentRendererTracker,
			infoDisplayContributorTracker, layout, saveInlineContent,
			saveMappingConfiguration, 0);
	}

	public static PageDefinition toPageDefinition(
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		FragmentRendererTracker fragmentRendererTracker,
		InfoDisplayContributorTracker infoDisplayContributorTracker,
		com.liferay.portal.kernel.model.Layout layout,
		boolean saveInlineContent, boolean saveMappingConfiguration,
		long segmentsExperienceId) {

		return new PageDefinition() {
			{
				pageElement = _toPageElement(
					fragmentCollectionContributorTracker,
					fragmentEntryConfigurationParser, fragmentRendererTracker,
					infoDisplayContributorTracker, layout, saveInlineContent,
					saveMappingConfiguration, segmentsExperienceId);
				settings = _toSettings(layout);
			}
		};
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #toPageElement(FragmentCollectionContributorTracker,
	 *             FragmentEntryConfigurationParser, FragmentRendererTracker,
	 *             long, InfoDisplayContributorTracker, LayoutStructure,
	 *             LayoutStructureItem, boolean, boolean, long)}
	 */
	@Deprecated
	public static PageElement toPageElement(
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		FragmentRendererTracker fragmentRendererTracker,
		LayoutStructure layoutStructure,
		LayoutStructureItem layoutStructureItem, boolean saveInlineContent,
		boolean saveMappingConfiguration) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #toPageElement(FragmentCollectionContributorTracker,
	 *             FragmentEntryConfigurationParser, FragmentRendererTracker,
	 *             long, InfoDisplayContributorTracker, LayoutStructure,
	 *             LayoutStructureItem, boolean, boolean, long)}
	 */
	@Deprecated
	public static PageElement toPageElement(
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		FragmentRendererTracker fragmentRendererTracker,
		LayoutStructure layoutStructure,
		LayoutStructureItem layoutStructureItem, boolean saveInlineContent,
		boolean saveMappingConfiguration, long segmentsExperienceId) {

		throw new UnsupportedOperationException();
	}

	public static PageElement toPageElement(
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		FragmentRendererTracker fragmentRendererTracker, long groupId,
		InfoDisplayContributorTracker infoDisplayContributorTracker,
		LayoutStructure layoutStructure,
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
					_toPageElement(
						fragmentCollectionContributorTracker,
						fragmentEntryConfigurationParser,
						fragmentRendererTracker, groupId,
						infoDisplayContributorTracker, childLayoutStructureItem,
						saveInlineContent, saveMappingConfiguration,
						segmentsExperienceId));
			}
			else {
				pageElements.add(
					toPageElement(
						fragmentCollectionContributorTracker,
						fragmentEntryConfigurationParser,
						fragmentRendererTracker, groupId,
						infoDisplayContributorTracker, layoutStructure,
						childLayoutStructureItem, saveInlineContent,
						saveMappingConfiguration, segmentsExperienceId));
			}
		}

		PageElement pageElement = _toPageElement(
			fragmentCollectionContributorTracker,
			fragmentEntryConfigurationParser, fragmentRendererTracker, groupId,
			infoDisplayContributorTracker, layoutStructureItem,
			saveInlineContent, saveMappingConfiguration, segmentsExperienceId);

		if (!pageElements.isEmpty()) {
			pageElement.setPageElements(
				pageElements.toArray(new PageElement[0]));
		}

		return pageElement;
	}

	private static Map<String, Object> _getConfigAsMap(JSONObject jsonObject) {
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

	private static boolean _isFragmentEntryKey(
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		String fragmentEntryKey,
		FragmentRendererTracker fragmentRendererTracker, long groupId) {

		FragmentEntry fragmentEntry =
			FragmentEntryLocalServiceUtil.fetchFragmentEntry(
				groupId, fragmentEntryKey);

		if (fragmentEntry != null) {
			return true;
		}

		Map<String, FragmentEntry> fragmentEntries =
			fragmentCollectionContributorTracker.getFragmentEntries();

		fragmentEntry = fragmentEntries.get(fragmentEntryKey);

		if (fragmentEntry != null) {
			return true;
		}

		FragmentRenderer fragmentRenderer =
			fragmentRendererTracker.getFragmentRenderer(fragmentEntryKey);

		if (fragmentRenderer != null) {
			return true;
		}

		return false;
	}

	private static Fragment[] _toFragments(
		List<String> fragmentEntryKeys,
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		FragmentRendererTracker fragmentRendererTracker, long groupId) {

		List<Fragment> fragments = new ArrayList<>();

		for (String fragmentEntryKey : fragmentEntryKeys) {
			if (_isFragmentEntryKey(
					fragmentCollectionContributorTracker, fragmentEntryKey,
					fragmentRendererTracker, groupId)) {

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

	private static Map<String, Fragment[]> _toFragmentSettingsMap(
		DropZoneLayoutStructureItem dropZoneLayoutStructureItem,
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		FragmentRendererTracker fragmentRendererTracker, long groupId) {

		if (dropZoneLayoutStructureItem.isAllowNewFragmentEntries()) {
			return HashMapBuilder.put(
				"unallowedFragments",
				_toFragments(
					dropZoneLayoutStructureItem.getFragmentEntryKeys(),
					fragmentCollectionContributorTracker,
					fragmentRendererTracker, groupId)
			).build();
		}

		return HashMapBuilder.put(
			"allowedFragments",
			_toFragments(
				dropZoneLayoutStructureItem.getFragmentEntryKeys(),
				fragmentCollectionContributorTracker, fragmentRendererTracker,
				groupId)
		).build();
	}

	private static PageElement _toPageElement(
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		FragmentRendererTracker fragmentRendererTracker,
		InfoDisplayContributorTracker infoDisplayContributorTracker,
		com.liferay.portal.kernel.model.Layout layout,
		boolean saveInlineContent, boolean saveMappingConfiguration,
		long segmentsExperienceId) {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(),
					PortalUtil.getClassNameId(
						com.liferay.portal.kernel.model.Layout.class),
					layout.getPlid());

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(0L));

		LayoutStructureItem mainLayoutStructureItem =
			layoutStructure.getMainLayoutStructureItem();

		List<PageElement> mainPageElements = new ArrayList<>();

		for (String childItemId :
				mainLayoutStructureItem.getChildrenItemIds()) {

			mainPageElements.add(
				toPageElement(
					fragmentCollectionContributorTracker,
					fragmentEntryConfigurationParser, fragmentRendererTracker,
					layout.getGroupId(), infoDisplayContributorTracker,
					layoutStructure,
					layoutStructure.getLayoutStructureItem(childItemId),
					saveInlineContent, saveMappingConfiguration, 0));
		}

		PageElement pageElement = _toPageElement(
			fragmentCollectionContributorTracker,
			fragmentEntryConfigurationParser, fragmentRendererTracker,
			layout.getGroupId(), infoDisplayContributorTracker,
			mainLayoutStructureItem, saveInlineContent,
			saveMappingConfiguration, segmentsExperienceId);

		if (!mainPageElements.isEmpty()) {
			pageElement.setPageElements(
				mainPageElements.toArray(new PageElement[0]));
		}

		return pageElement;
	}

	private static PageElement _toPageElement(
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		FragmentRendererTracker fragmentRendererTracker, long groupId,
		InfoDisplayContributorTracker infoDisplayContributorTracker,
		LayoutStructureItem layoutStructureItem, boolean saveInlineContent,
		boolean saveMappingConfiguration, long segmentsExperienceId) {

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
							layout = new Layout() {
								{
									paddingBottom =
										PaddingConverter.convertToExternalValue(
											containerLayoutStructureItem.
												getPaddingBottom());
									paddingHorizontal =
										PaddingConverter.convertToExternalValue(
											containerLayoutStructureItem.
												getPaddingHorizontal());
									paddingTop =
										PaddingConverter.convertToExternalValue(
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
												StringUtil.upperCaseFirstLetter(
													containerType));
										});
								}
							};

							setBackgroundImage(
								() -> {
									JSONObject backgroundImageJSONObject =
										containerLayoutStructureItem.
											getBackgroundImageJSONObject();

									if ((backgroundImageJSONObject == null) ||
										!backgroundImageJSONObject.has("url")) {

										return null;
									}

									String urlValue =
										backgroundImageJSONObject.getString(
											"url");

									return new FragmentImage() {
										{
											title = _toTitleFragmentInlineValue(
												backgroundImageJSONObject,
												urlValue);
											url = new FragmentInlineValue() {
												{
													value = urlValue;
												}
											};
										}
									};
								});
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
								dropZoneLayoutStructureItem,
								fragmentCollectionContributorTracker,
								fragmentRendererTracker, groupId);
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
				FragmentEntryLinkLocalServiceUtil.fetchFragmentEntryLink(
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
							PageFragmentInstanceDefinitionConverterUtil.
								toPageFragmentInstanceDefinition(
									fragmentCollectionContributorTracker,
									fragmentEntryConfigurationParser,
									fragmentLayoutStructureItem,
									fragmentRendererTracker,
									infoDisplayContributorTracker,
									saveInlineContent, saveMappingConfiguration,
									segmentsExperienceId);
						type = PageElement.Type.FRAGMENT;
					}
				};
			}

			return new PageElement() {
				{
					definition =
						PageWidgetInstanceDefinitionConverterUtil.
							toWidgetInstanceDefinition(portletId);
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

	private static Settings _toSettings(
		com.liferay.portal.kernel.model.Layout layout) {

		UnicodeProperties unicodeProperties =
			layout.getTypeSettingsProperties();

		return new Settings() {
			{
				setColorSchemeName(
					() -> {
						ColorScheme colorScheme = null;

						try {
							colorScheme = layout.getColorScheme();
						}
						catch (PortalException portalException) {
							if (_log.isWarnEnabled()) {
								_log.warn(portalException, portalException);
							}
						}

						if (colorScheme == null) {
							return null;
						}

						return colorScheme.getName();
					});
				setCss(
					() -> {
						if (Validator.isNull(layout.getCss())) {
							return null;
						}

						return layout.getCss();
					});
				setJavascript(
					() -> {
						for (Map.Entry<String, String> entry :
								unicodeProperties.entrySet()) {

							String key = entry.getKey();

							if (key.equals("javascript")) {
								return entry.getValue();
							}
						}

						return null;
					});
				setMasterPage(
					() -> {
						LayoutPageTemplateEntry layoutPageTemplateEntry =
							LayoutPageTemplateEntryLocalServiceUtil.
								fetchLayoutPageTemplateEntryByPlid(
									layout.getMasterLayoutPlid());

						if (layoutPageTemplateEntry == null) {
							return null;
						}

						return new MasterPage() {
							{
								key =
									layoutPageTemplateEntry.
										getLayoutPageTemplateEntryKey();
							}
						};
					});
				setThemeName(
					() -> {
						Theme theme = layout.getTheme();

						if (theme == null) {
							return null;
						}

						return theme.getName();
					});
				setThemeSettings(
					() -> {
						UnicodeProperties themeSettingsUnicodeProperties =
							new UnicodeProperties();

						for (Map.Entry<String, String> entry :
								unicodeProperties.entrySet()) {

							String key = entry.getKey();

							if (key.startsWith("lfr-theme:")) {
								themeSettingsUnicodeProperties.setProperty(
									key, entry.getValue());
							}
						}

						if (themeSettingsUnicodeProperties.isEmpty()) {
							return null;
						}

						return themeSettingsUnicodeProperties;
					});
			}
		};
	}

	private static FragmentInlineValue _toTitleFragmentInlineValue(
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
		PageDefinitionConverterUtil.class);

}