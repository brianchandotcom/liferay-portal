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

package com.liferay.translation.web.internal.asset.model;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalServiceUtil;
import com.liferay.translation.info.field.TranslationInfoFieldChecker;
import com.liferay.translation.model.TranslationEntry;
import com.liferay.translation.snapshot.TranslationSnapshot;
import com.liferay.translation.snapshot.TranslationSnapshotProvider;
import com.liferay.translation.web.internal.display.context.ViewTranslationDisplayContext;

import java.io.ByteArrayInputStream;

import java.nio.charset.StandardCharsets;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Adolfo Pérez
 */
public class TranslationEntryAssetRenderer
	extends BaseJSPAssetRenderer<TranslationEntry> {

	public TranslationEntryAssetRenderer(
		InfoItemServiceTracker infoItemServiceTracker,
		ServletContext servletContext, TranslationEntry translationEntry,
		TranslationInfoFieldChecker translationInfoFieldChecker,
		TranslationSnapshotProvider translationSnapshotProvider) {

		_infoItemServiceTracker = infoItemServiceTracker;
		_translationEntry = translationEntry;
		_translationInfoFieldChecker = translationInfoFieldChecker;
		_translationSnapshotProvider = translationSnapshotProvider;

		setServletContext(servletContext);
	}

	@Override
	public TranslationEntry getAssetObject() {
		return _translationEntry;
	}

	@Override
	public String getClassName() {
		return TranslationEntry.class.getName();
	}

	@Override
	public long getClassPK() {
		return _translationEntry.getTranslationEntryId();
	}

	@Override
	public long getGroupId() {
		return _translationEntry.getGroupId();
	}

	@Override
	public String getJspPath(
		HttpServletRequest httpServletRequest, String template) {

		return "/asset/full_content.jsp";
	}

	@Override
	public String getSummary(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return getTitle(PortalUtil.getLocale(portletRequest));
	}

	@Override
	public String getTitle(Locale locale) {
		return LanguageUtil.format(
			locale, "translation-of-x-to-x",
			new Object[] {
				_getInfoItemTitleOptional(
					locale
				).orElseGet(
					() -> _getAssetRendererTitle(locale)
				),
				StringUtil.replace(
					_translationEntry.getLanguageId(), CharPool.UNDERLINE,
					CharPool.DASH)
			});
	}

	@Override
	public long getUserId() {
		return _translationEntry.getUserId();
	}

	@Override
	public String getUserName() {
		return _translationEntry.getUserName();
	}

	@Override
	public String getUuid() {
		return _translationEntry.getUuid();
	}

	@Override
	public boolean include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String template)
		throws Exception {

		InfoItemFormProvider<Object> infoItemFormProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFormProvider.class, _translationEntry.getClassName());
		InfoItemObjectProvider<Object> infoItemObjectProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemObjectProvider.class, _translationEntry.getClassName());

		String content = _translationEntry.getContent();

		TranslationSnapshot translationSnapshot =
			_translationSnapshotProvider.getTranslationSnapshot(
				_translationEntry.getGroupId(),
				new InfoItemReference(
					_translationEntry.getClassName(),
					_translationEntry.getClassPK()),
				new ByteArrayInputStream(
					content.getBytes(StandardCharsets.UTF_8)));

		httpServletRequest.setAttribute(
			ViewTranslationDisplayContext.class.getName(),
			new ViewTranslationDisplayContext(
				httpServletRequest,
				infoItemFormProvider.getInfoForm(
					infoItemObjectProvider.getInfoItem(
						_translationEntry.getClassPK())),
				_translationInfoFieldChecker, translationSnapshot));

		return super.include(httpServletRequest, httpServletResponse, template);
	}

	private AssetRenderer<?> _getAssetRenderer(
			AssetRendererFactory<?> assetRendererFactory)
		throws PortalException {

		try {
			return assetRendererFactory.getAssetRenderer(
				_translationEntry.getClassPK());
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}

			return null;
		}
	}

	private String _getAssetRendererTitle(Locale locale) {
		try {
			AssetRendererFactory<?> assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassNameId(
						_translationEntry.getClassNameId());

			if (assetRendererFactory == null) {
				return LanguageUtil.get(locale, "translation");
			}

			AssetRenderer<?> assetRenderer = _getAssetRenderer(
				assetRendererFactory);

			if (assetRenderer == null) {
				return LanguageUtil.get(locale, "translation");
			}

			return assetRenderer.getTitle(locale);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return LanguageUtil.get(locale, "translation");
		}
	}

	private Optional<String> _getInfoItemTitleOptional(Locale locale) {
		try {
			EntryReference entryReference = _resolveIndirectEntryReference(
				locale);

			InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
				_infoItemServiceTracker.getFirstInfoItemService(
					InfoItemFieldValuesProvider.class,
					entryReference.getClassName());

			InfoItemObjectProvider<Object> infoItemObjectProvider =
				_infoItemServiceTracker.getFirstInfoItemService(
					InfoItemObjectProvider.class,
					entryReference.getClassName());

			Object object = infoItemObjectProvider.getInfoItem(
				entryReference.getClassPK());

			return _getTitleOptional(
				object, infoItemFieldValuesProvider, locale
			).map(
				title -> title + entryReference.getSuffix()
			);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return Optional.empty();
		}
	}

	private Optional<String> _getTitleOptional(
		Object object,
		InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider,
		Locale locale) {

		InfoFieldValue<Object> titleInfoFieldValue =
			infoItemFieldValuesProvider.getInfoFieldValue(object, "title");

		if (titleInfoFieldValue != null) {
			return Optional.ofNullable(
				(String)titleInfoFieldValue.getValue(locale));
		}

		InfoFieldValue<Object> nameInfoFieldValue =
			infoItemFieldValuesProvider.getInfoFieldValue(object, "name");

		return Optional.ofNullable((String)nameInfoFieldValue.getValue(locale));
	}

	private EntryReference _resolveIndirectEntryReference(Locale locale) {
		if (!Objects.equals(
				_translationEntry.getClassName(),
				SegmentsExperience.class.getName())) {

			return new EntryReference(
				_translationEntry.getClassName(),
				_translationEntry.getClassPK(), StringPool.BLANK);
		}

		SegmentsExperience segmentsExperience =
			SegmentsExperienceLocalServiceUtil.fetchSegmentsExperience(
				_translationEntry.getClassPK());

		if (segmentsExperience == null) {
			return new EntryReference(
				_translationEntry.getClassName(),
				_translationEntry.getClassPK(), StringPool.BLANK);
		}

		return new EntryReference(
			segmentsExperience.getClassName(), segmentsExperience.getClassPK(),
			StringBundler.concat(
				StringPool.SPACE, StringPool.OPEN_PARENTHESIS,
				segmentsExperience.getName(locale),
				StringPool.CLOSE_PARENTHESIS));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TranslationEntryAssetRenderer.class);

	private final InfoItemServiceTracker _infoItemServiceTracker;
	private final TranslationEntry _translationEntry;
	private final TranslationInfoFieldChecker _translationInfoFieldChecker;
	private final TranslationSnapshotProvider _translationSnapshotProvider;

	private static class EntryReference {

		public EntryReference(String className, long classPK, String suffix) {
			_className = className;
			_classPK = classPK;
			_suffix = suffix;
		}

		public String getClassName() {
			return _className;
		}

		public long getClassPK() {
			return _classPK;
		}

		public String getSuffix() {
			return _suffix;
		}

		private final String _className;
		private final long _classPK;
		private final String _suffix;

	}

}