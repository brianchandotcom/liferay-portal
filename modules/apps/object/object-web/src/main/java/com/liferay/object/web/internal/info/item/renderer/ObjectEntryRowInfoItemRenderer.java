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

package com.liferay.object.web.internal.info.item.renderer;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.info.item.renderer.InfoItemRenderer;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.web.internal.constants.ObjectWebKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.io.Serializable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(
	property = "service.ranking:Integer=100", service = InfoItemRenderer.class
)
public class ObjectEntryRowInfoItemRenderer
	implements InfoItemRenderer<ObjectEntry> {

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, getClass());

		return LanguageUtil.get(resourceBundle, "row");
	}

	@Override
	public void render(
		ObjectEntry objectEntry, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		try {
			httpServletRequest.setAttribute(
				AssetDisplayPageFriendlyURLProvider.class.getName(),
				_assetDisplayPageFriendlyURLProvider);
			httpServletRequest.setAttribute(
				ObjectWebKeys.OBJECT_DEFINITION,
				_objectDefinitionLocalService.getObjectDefinition(
					objectEntry.getObjectDefinitionId()));
			httpServletRequest.setAttribute(
				ObjectWebKeys.OBJECT_ENTRY, objectEntry);
			httpServletRequest.setAttribute(
				ObjectWebKeys.OBJECT_ENTRY_VALUES, _getValues(objectEntry));

			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher(
					"/info/item/renderer/object_entry.jsp");

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.object.web)", unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private Map<String, Serializable> _getValues(ObjectEntry objectEntry)
		throws PortalException {

		Map<String, Serializable> values = _objectEntryLocalService.getValues(
			objectEntry);

		Set<Map.Entry<String, Serializable>> entries = values.entrySet();

		Stream<Map.Entry<String, Serializable>> entriesStream =
			entries.stream();

		List<String> objectFieldNames = ListUtil.toList(
			_objectFieldLocalService.getObjectFields(
				objectEntry.getObjectDefinitionId()),
			ObjectField::getName);

		return entriesStream.filter(
			entry -> objectFieldNames.contains(entry.getKey())
		).sorted(
			Map.Entry.comparingByKey()
		).collect(
			Collectors.toMap(
				Map.Entry::getKey,
				entry -> Optional.ofNullable(
					entry.getValue()
				).orElse(
					StringPool.BLANK
				),
				(oldValue, newValue) -> oldValue, LinkedHashMap::new)
		);
	}

	@Reference
	private AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	private ServletContext _servletContext;

}