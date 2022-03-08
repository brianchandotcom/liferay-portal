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

package com.liferay.content.dashboard.web.internal.item.selector;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.content.dashboard.web.internal.display.context.ContentDashboardItemSubtypeItemSelectorViewDisplayContext;
import com.liferay.content.dashboard.web.internal.item.ContentDashboardItemFactory;
import com.liferay.content.dashboard.web.internal.item.ContentDashboardItemFactoryTracker;
import com.liferay.content.dashboard.web.internal.item.selector.criteria.content.dashboard.type.criterion.ContentDashboardItemSubtypeItemSelectorCriterion;
import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemSubtype;
import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemSubtypeFactory;
import com.liferay.content.dashboard.web.internal.search.request.ContentDashboardItemSearchClassMapperTracker;
import com.liferay.content.dashboard.web.internal.util.ContentDashboardGroupUtil;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Stream;

import javax.portlet.PortletURL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = ItemSelectorView.class)
public class ContentDashboardItemSubtypeItemSelectorView
	implements ItemSelectorView
		<ContentDashboardItemSubtypeItemSelectorCriterion> {

	@Override
	public Class<ContentDashboardItemSubtypeItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return ContentDashboardItemSubtypeItemSelectorCriterion.class;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, getClass());

		return ResourceBundleUtil.getString(resourceBundle, "subtype");
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			ContentDashboardItemSubtypeItemSelectorCriterion
				contentDashboardItemSubtypeItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(
				"/view_content_dashboard_item_types.jsp");

		servletRequest.setAttribute(
			ContentDashboardItemSubtypeItemSelectorViewDisplayContext.class.
				getName(),
			new ContentDashboardItemSubtypeItemSelectorViewDisplayContext(
				_getContentDashboardItemTypesJSONArray(
					servletRequest,
					(ThemeDisplay)servletRequest.getAttribute(
						WebKeys.THEME_DISPLAY)),
				itemSelectedEventName));

		requestDispatcher.include(servletRequest, servletResponse);
	}

	private JSONArray _getContentDashboardItemTypesJSONArray(
		ServletRequest servletRequest, ThemeDisplay themeDisplay) {

		JSONArray contentDashboardItemTypesJSONArray =
			JSONFactoryUtil.createJSONArray();

		Set<String> checkedContentDashboardItemSubtypes = SetUtil.fromArray(
			servletRequest.getParameterValues(
				"checkedContentDashboardItemSubtypes"));

		for (String className :
				_contentDashboardItemFactoryTracker.getClassNames()) {

			Optional<ContentDashboardItemFactory<?>>
				contentDashboardItemFactoryOptional =
					_contentDashboardItemFactoryTracker.
						getContentDashboardItemFactoryOptional(className);

			contentDashboardItemFactoryOptional.flatMap(
				ContentDashboardItemFactory::
					getContentDashboardItemSubtypeFactoryOptional
			).ifPresent(
				contentDashboardItemSubtypeFactory ->
					_populateContentDashboardItemTypesJSONArray(
						className, contentDashboardItemSubtypeFactory,
						checkedContentDashboardItemSubtypes,
						contentDashboardItemTypesJSONArray, themeDisplay)
			);
		}

		return contentDashboardItemTypesJSONArray;
	}

	private long[] _getGroupIds(long companyId) {
		List<Long> groupIds = _groupLocalService.getGroupIds(companyId, true);

		Stream<Long> stream = groupIds.stream();

		return stream.mapToLong(
			groupId -> groupId
		).toArray();
	}

	private String _getIcon(String className) {
		return Optional.ofNullable(
			_contentDashboardItemSearchClassMapperTracker.getSearchClassName(
				className)
		).map(
			AssetRendererFactoryRegistryUtil::getAssetRendererFactoryByClassName
		).map(
			AssetRendererFactory::getIconCssClass
		).orElseGet(
			null
		);
	}

	private String _getInfoItemFormVariationLabel(
		InfoItemFormVariation infoItemFormVariation, Locale locale) {

		Optional<Long> groupIdOptional =
			infoItemFormVariation.getGroupIdOptional();

		InfoLocalizedValue<String> labelInfoLocalizedValue =
			infoItemFormVariation.getLabelInfoLocalizedValue();

		return groupIdOptional.map(
			groupId -> {
				Group group = _groupLocalService.fetchGroup(groupId);

				if (group == null) {
					return labelInfoLocalizedValue.getValue(locale);
				}

				return LanguageUtil.format(
					locale, "x-group-x",
					new String[] {
						labelInfoLocalizedValue.getValue(locale),
						ContentDashboardGroupUtil.getGroupName(group, locale)
					});
			}
		).orElseGet(
			() -> labelInfoLocalizedValue.getValue(locale)
		);
	}

	private void _populateContentDashboardItemTypesJSONArray(
		String className,
		ContentDashboardItemSubtypeFactory contentDashboardItemSubtypeFactory,
		Set<String> checkedContentDashboardItemSubtypes,
		JSONArray contentDashboardItemTypesJSONArray,
		ThemeDisplay themeDisplay) {

		InfoItemClassDetails infoItemClassDetails = new InfoItemClassDetails(
			className);

		InfoItemFormVariationsProvider<?> infoItemFormVariationsProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFormVariationsProvider.class,
				infoItemClassDetails.getClassName());

		if (infoItemFormVariationsProvider == null) {
			contentDashboardItemTypesJSONArray.put(
				JSONUtil.put(
					"entryClassName", className
				).put(
					"icon", _getIcon(className)
				).put(
					"itemSubtypes", JSONFactoryUtil.createJSONArray()
				).put(
					"label",
					() -> {
						InfoLocalizedValue<String>
							infoItemClassDetailsLabelInfoLocalizedValue =
								infoItemClassDetails.
									getLabelInfoLocalizedValue();

						return infoItemClassDetailsLabelInfoLocalizedValue.
							getValue(themeDisplay.getLocale());
					}
				));

			return;
		}

		Collection<InfoItemFormVariation> infoItemFormVariations =
			infoItemFormVariationsProvider.getInfoItemFormVariations(
				_getGroupIds(themeDisplay.getCompanyId()));

		JSONArray itemSubtypesJSONArray = JSONFactoryUtil.createJSONArray();

		for (InfoItemFormVariation infoItemFormVariation :
				infoItemFormVariations) {

			try {
				ContentDashboardItemSubtype contentDashboardItemSubtype =
					contentDashboardItemSubtypeFactory.create(
						Long.valueOf(infoItemFormVariation.getKey()));

				Company company = _companyLocalService.getCompany(
					themeDisplay.getCompanyId());

				DLFileEntryType googleDocsDLFileEntryType =
					_dlFileEntryTypeLocalService.fetchFileEntryType(
						company.getGroupId(), "GOOGLE_DOCS");

				if (googleDocsDLFileEntryType != null) {
					String fileEntryTypeIdString = String.valueOf(
						googleDocsDLFileEntryType.getFileEntryTypeId());

					if (StringUtil.equalsIgnoreCase(
							fileEntryTypeIdString,
							infoItemFormVariation.getKey())) {

						continue;
					}
				}

				InfoItemReference infoItemReference =
					contentDashboardItemSubtype.getInfoItemReference();

				itemSubtypesJSONArray.put(
					JSONUtil.put(
						"className", infoItemReference.getClassName()
					).put(
						"classPK",
						String.valueOf(infoItemFormVariation.getKey())
					).put(
						"entryClassName", className
					).put(
						"label",
						_getInfoItemFormVariationLabel(
							infoItemFormVariation, themeDisplay.getLocale())
					).put(
						"selected",
						checkedContentDashboardItemSubtypes.contains(
							infoItemFormVariation.getKey())
					));
			}
			catch (PortalException portalException) {
				_log.error(portalException);
			}
		}

		contentDashboardItemTypesJSONArray.put(
			JSONUtil.put(
				"icon", _getIcon(className)
			).put(
				"itemSubtypes", itemSubtypesJSONArray
			).put(
				"label",
				() -> {
					InfoLocalizedValue<String>
						infoItemClassDetailsLabelInfoLocalizedValue =
							infoItemClassDetails.getLabelInfoLocalizedValue();

					return infoItemClassDetailsLabelInfoLocalizedValue.getValue(
						themeDisplay.getLocale());
				}
			));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContentDashboardItemSubtypeItemSelectorView.class);

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new UUIDItemSelectorReturnType());

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ContentDashboardItemFactoryTracker
		_contentDashboardItemFactoryTracker;

	@Reference
	private ContentDashboardItemSearchClassMapperTracker
		_contentDashboardItemSearchClassMapperTracker;

	@Reference
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.content.dashboard.web)"
	)
	private ServletContext _servletContext;

}