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

package com.liferay.layout.seo.web.internal.servlet.taglib;

import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.kernel.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.dynamic.data.mapping.kernel.StorageEngineManagerUtil;
import com.liferay.dynamic.data.mapping.kernel.Value;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.layout.seo.kernel.LayoutSEOLink;
import com.liferay.layout.seo.kernel.LayoutSEOLinkManager;
import com.liferay.layout.seo.model.LayoutSEOEntry;
import com.liferay.layout.seo.model.LayoutSEOSite;
import com.liferay.layout.seo.open.graph.OpenGraphConfiguration;
import com.liferay.layout.seo.service.LayoutSEOEntryLocalService;
import com.liferay.layout.seo.service.LayoutSEOSiteLocalService;
import com.liferay.layout.seo.web.internal.util.FileEntryMetadataOpenGraphTagsProvider;
import com.liferay.layout.seo.web.internal.util.TitleProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(service = DynamicInclude.class)
public class OpenGraphTopHeadDynamicInclude extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			Layout layout = themeDisplay.getLayout();

			if (themeDisplay.isSignedIn() || layout.isPrivateLayout()) {
				return;
			}

			String completeURL = _portal.getCurrentCompleteURL(
				httpServletRequest);

			String canonicalURL = _portal.getCanonicalURL(
				completeURL, themeDisplay, layout, false, false);

			Map<Locale, String> alternateURLs = Collections.emptyMap();

			Set<Locale> availableLocales = _language.getAvailableLocales(
				themeDisplay.getSiteGroupId());

			if (availableLocales.size() > 1) {
				alternateURLs = _portal.getAlternateURLs(
					canonicalURL, themeDisplay, layout);
			}

			PrintWriter printWriter = httpServletResponse.getWriter();

			for (LayoutSEOLink layoutSEOLink :
					_layoutSEOLinkManager.getLocalizedLayoutSEOLinks(
						layout, _portal.getLocale(httpServletRequest),
						canonicalURL, alternateURLs)) {

				printWriter.println(_addLinkTag(layoutSEOLink));
			}

			LayoutSEOEntry layoutSEOEntry =
				_layoutSEOEntryLocalService.fetchLayoutSEOEntry(
					layout.getGroupId(), layout.isPrivateLayout(),
					layout.getLayoutId());

			if ((layoutSEOEntry != null) &&
				(layoutSEOEntry.getDDMStorageId() != 0)) {

				DDMFormValues ddmFormValues =
					StorageEngineManagerUtil.getDDMFormValues(
						layoutSEOEntry.getDDMStorageId());

				Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
					ddmFormValues.getDDMFormFieldValuesMap();

				for (List<DDMFormFieldValue> ddmFormFieldValues :
						ddmFormFieldValuesMap.values()) {

					for (DDMFormFieldValue nameDDMFormFieldValue :
							ddmFormFieldValues) {

						Value nameValue = nameDDMFormFieldValue.getValue();

						List<DDMFormFieldValue> nestedDDMFormFieldValues =
							nameDDMFormFieldValue.getNestedDDMFormFieldValues();

						DDMFormFieldValue valueDDMFormFieldValue =
							nestedDDMFormFieldValues.get(0);

						Value valueValue = valueDDMFormFieldValue.getValue();

						printWriter.println(
							_getOpenGraphTag(
								nameValue.getString(themeDisplay.getLocale()),
								valueValue.getString(
									themeDisplay.getLocale())));
					}
				}
			}

			if (!_openGraphConfiguration.isOpenGraphEnabled(
					layout.getGroup())) {

				return;
			}

			printWriter.println(
				_getOpenGraphTag(
					"og:description",
					_getDescriptionTagValue(layoutSEOEntry, themeDisplay)));

			printWriter.println(
				_getOpenGraphTag("og:locale", themeDisplay.getLanguageId()));

			availableLocales.forEach(
				locale -> printWriter.println(
					_getOpenGraphTag(
						"og:locale:alternate",
						LocaleUtil.toLanguageId(locale))));

			Group group = layout.getGroup();

			printWriter.println(
				_getOpenGraphTag("og:site_name", group.getDescriptiveName()));

			printWriter.println(
				_getOpenGraphTag(
					"og:title",
					_getTitleTagValue(httpServletRequest, layoutSEOEntry)));
			printWriter.println(_getOpenGraphTag("og:type", "website"));

			LayoutSEOLink layoutSEOLink =
				_layoutSEOLinkManager.getCanonicalLayoutSEOLink(
					layout, themeDisplay.getLocale(), canonicalURL,
					alternateURLs);

			printWriter.println(
				_getOpenGraphTag("og:url", layoutSEOLink.getHref()));

			long openGraphImageFileEntryId = _getOpenGraphImageFileEntryId(
				layoutSEOEntry, group);

			if (openGraphImageFileEntryId == 0) {
				return;
			}

			FileEntry fileEntry = _dlAppLocalService.getFileEntry(
				openGraphImageFileEntryId);

			printWriter.println(
				_getOpenGraphTag(
					"og:image",
					_dlurlHelper.getImagePreviewURL(fileEntry, themeDisplay)));

			printWriter.println(
				_getOpenGraphTag(
					"og:image:alt",
					_getImageAltTagValue(
						layoutSEOEntry, group, themeDisplay.getLocale())));

			if (themeDisplay.isSecure()) {
				printWriter.println(
					_getOpenGraphTag(
						"og:image:secure_url",
						_dlurlHelper.getImagePreviewURL(
							fileEntry, themeDisplay)));
			}

			printWriter.println(
				_getOpenGraphTag("og:image:type", fileEntry.getMimeType()));
			printWriter.println(
				_getOpenGraphTag(
					"og:image:url",
					_dlurlHelper.getImagePreviewURL(fileEntry, themeDisplay)));

			for (KeyValuePair keyValuePair :
					_fileEntryMetadataOpenGraphTagsProvider.
						getFileEntryMetadataOpenGraphTagKeyValuePairs(
							fileEntry)) {

				printWriter.println(
					_getOpenGraphTag(
						keyValuePair.getKey(), keyValuePair.getValue()));
			}
		}
		catch (RuntimeException runtimeException) {
			throw runtimeException;
		}
		catch (Exception exception) {
			throw new IOException(exception);
		}
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register("/html/common/themes/top_head.jsp#pre");
	}

	@Activate
	protected void activate() {
		_fileEntryMetadataOpenGraphTagsProvider =
			new FileEntryMetadataOpenGraphTagsProvider(
				_ddmStructureLocalService, _dlFileEntryMetadataLocalService,
				_portal, _storageEngine);

		_titleProvider = new TitleProvider(_layoutSEOLinkManager);
	}

	private String _addLinkTag(LayoutSEOLink layoutSEOLink) {
		StringBuilder sb = new StringBuilder(10);

		sb.append("<link data-senna-track=\"temporary\" ");
		sb.append("href=\"");
		sb.append(layoutSEOLink.getHref());
		sb.append("\" ");

		if (Validator.isNotNull(layoutSEOLink.getHrefLang())) {
			sb.append("hreflang=\"");
			sb.append(layoutSEOLink.getHrefLang());
			sb.append("\" ");
		}

		sb.append("rel=\"");
		sb.append(layoutSEOLink.getRelationship());
		sb.append("\" />");

		return sb.toString();
	}

	private String _getDescriptionTagValue(
		LayoutSEOEntry layoutSEOEntry, ThemeDisplay themeDisplay) {

		if ((layoutSEOEntry != null) &&
			layoutSEOEntry.isOpenGraphDescriptionEnabled()) {

			return layoutSEOEntry.getOpenGraphDescription(
				themeDisplay.getLocale());
		}

		Layout layout = themeDisplay.getLayout();

		return layout.getDescription(themeDisplay.getLanguageId());
	}

	private String _getImageAltTagValue(
		LayoutSEOEntry layoutSEOEntry, Group group, Locale locale) {

		if ((layoutSEOEntry != null) &&
			(layoutSEOEntry.getOpenGraphImageFileEntryId() > 0)) {

			return layoutSEOEntry.getOpenGraphImageAlt(locale);
		}

		LayoutSEOSite layoutSEOSite =
			_layoutSEOSiteLocalService.fetchLayoutSEOSiteByGroupId(
				group.getGroupId());

		if ((layoutSEOSite != null) &&
			(layoutSEOSite.getOpenGraphImageFileEntryId() > 0)) {

			return layoutSEOSite.getOpenGraphImageAlt(locale);
		}

		return null;
	}

	private long _getOpenGraphImageFileEntryId(
			LayoutSEOEntry layoutSEOEntry, Group group)
		throws PortalException {

		if (!_openGraphConfiguration.isOpenGraphEnabled(group)) {
			return 0;
		}

		if ((layoutSEOEntry != null) &&
			(layoutSEOEntry.getOpenGraphImageFileEntryId() > 0)) {

			return layoutSEOEntry.getOpenGraphImageFileEntryId();
		}

		LayoutSEOSite layoutSEOSite =
			_layoutSEOSiteLocalService.fetchLayoutSEOSiteByGroupId(
				group.getGroupId());

		if ((layoutSEOSite == null) ||
			(layoutSEOSite.getOpenGraphImageFileEntryId() == 0)) {

			return 0;
		}

		return layoutSEOSite.getOpenGraphImageFileEntryId();
	}

	private String _getOpenGraphTag(String property, String content) {
		if (Validator.isNull(content)) {
			return StringPool.BLANK;
		}

		return StringBundler.concat(
			"<meta property=\"", property, "\" content=\"", content, "\">");
	}

	private String _getTitleTagValue(
			HttpServletRequest httpServletRequest,
			LayoutSEOEntry layoutSEOEntry)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if ((layoutSEOEntry != null) &&
			layoutSEOEntry.isOpenGraphTitleEnabled()) {

			return layoutSEOEntry.getOpenGraphTitle(themeDisplay.getLocale());
		}

		return _titleProvider.getTitle(httpServletRequest);
	}

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLFileEntryMetadataLocalService _dlFileEntryMetadataLocalService;

	@Reference
	private DLURLHelper _dlurlHelper;

	private FileEntryMetadataOpenGraphTagsProvider
		_fileEntryMetadataOpenGraphTagsProvider;

	@Reference
	private Language _language;

	@Reference
	private LayoutSEOEntryLocalService _layoutSEOEntryLocalService;

	@Reference
	private LayoutSEOLinkManager _layoutSEOLinkManager;

	@Reference
	private LayoutSEOSiteLocalService _layoutSEOSiteLocalService;

	@Reference
	private OpenGraphConfiguration _openGraphConfiguration;

	@Reference
	private Portal _portal;

	@Reference
	private StorageEngine _storageEngine;

	private TitleProvider _titleProvider;

}