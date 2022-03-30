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

package com.liferay.object.web.internal.info.list.renderer;

import com.liferay.info.item.renderer.InfoItemRenderer;
import com.liferay.info.item.renderer.InfoItemRendererTracker;
import com.liferay.info.list.renderer.DefaultInfoListRendererContext;
import com.liferay.info.list.renderer.InfoListRenderer;
import com.liferay.info.list.renderer.InfoListRendererContext;
import com.liferay.info.taglib.servlet.taglib.InfoListBasicTableTag;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.web.internal.info.item.renderer.ObjectEntryRowInfoItemRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Guilherme Camacho
 */
public class ObjectEntryTableInfoListRenderer
	implements InfoListRenderer<ObjectEntry> {

	public ObjectEntryTableInfoListRenderer(
		InfoItemRendererTracker infoItemRendererTracker,
		ObjectFieldLocalService objectFieldLocalService) {

		_infoItemRendererTracker = infoItemRendererTracker;
		_objectFieldLocalService = objectFieldLocalService;
	}

	@Override
	public List<InfoItemRenderer<?>> getAvailableInfoItemRenderers() {
		return _infoItemRendererTracker.getInfoItemRenderers(
			ObjectEntry.class.getName());
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "table");
	}

	@Override
	public void render(
		List<ObjectEntry> objectEntries, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		render(
			objectEntries,
			new DefaultInfoListRendererContext(
				httpServletRequest, httpServletResponse));
	}

	@Override
	public void render(
		List<ObjectEntry> objectEntries,
		InfoListRendererContext infoListRendererContext) {

		InfoListBasicTableTag infoListBasicTableTag =
			new InfoListBasicTableTag();

		if ((objectEntries != null) && !objectEntries.isEmpty()) {
			ObjectEntry objectEntry = objectEntries.get(0);

			List<ObjectField> objectFields =
				_objectFieldLocalService.getObjectFields(
					objectEntry.getObjectDefinitionId());

			try {
				objectFields = _objectFieldLocalService.getActiveObjectFields(
					objectFields);
			}
			catch (PortalException portalException) {
				_log.error(portalException);
			}

			infoListBasicTableTag.setInfoListObjectColumnNames(
				ListUtil.toList(objectFields, ObjectField::getLabel));
		}

		infoListBasicTableTag.setInfoListObjects(objectEntries);

		Optional<String> infoListItemRendererKeyOptional =
			infoListRendererContext.getListItemRendererKeyOptional();

		if (infoListItemRendererKeyOptional.isPresent() &&
			Validator.isNotNull(infoListItemRendererKeyOptional.get())) {

			infoListBasicTableTag.setItemRendererKey(
				infoListItemRendererKeyOptional.get());
		}
		else {
			infoListBasicTableTag.setItemRendererKey(
				ObjectEntryRowInfoItemRenderer.class.getName());
		}

		try {
			infoListBasicTableTag.doTag(
				infoListRendererContext.getHttpServletRequest(),
				infoListRendererContext.getHttpServletResponse());
		}
		catch (Exception exception) {
			_log.error("Unable to render object entries list", exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryTableInfoListRenderer.class);

	private final InfoItemRendererTracker _infoItemRendererTracker;
	private final ObjectFieldLocalService _objectFieldLocalService;

}