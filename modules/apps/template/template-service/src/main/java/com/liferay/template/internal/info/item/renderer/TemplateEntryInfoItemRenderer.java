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

package com.liferay.template.internal.info.item.renderer;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.renderer.InfoItemRenderer;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portlet.display.template.constants.PortletDisplayTemplateConstants;
import com.liferay.template.internal.transformer.TemplateDisplayTemplateTransformer;
import com.liferay.template.internal.transformer.TemplateNodeFactory;
import com.liferay.template.model.TemplateEntry;

import java.io.Writer;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Eudaldo Alonso
 */
public class TemplateEntryInfoItemRenderer<T> implements InfoItemRenderer<T> {

	public TemplateEntryInfoItemRenderer(
		InfoItemServiceTracker infoItemServiceTracker,
		TemplateEntry templateEntry, TemplateNodeFactory templateNodeFactory) {

		_infoItemServiceTracker = infoItemServiceTracker;
		_templateEntry = templateEntry;
		_templateNodeFactory = templateNodeFactory;
	}

	@Override
	public String getKey() {
		return PortletDisplayTemplateConstants.DISPLAY_STYLE_PREFIX +
			_templateEntry.getTemplateEntryId();
	}

	@Override
	public String getLabel(Locale locale) {
		DDMTemplate ddmTemplate = DDMTemplateLocalServiceUtil.fetchDDMTemplate(
			_templateEntry.getDDMTemplateId());

		return ddmTemplate.getName(locale);
	}

	@Override
	public boolean isAvailable() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if ((serviceContext == null) ||
			(serviceContext.getScopeGroupId() != _templateEntry.getGroupId())) {

			return false;
		}

		return true;
	}

	@Override
	public void render(
		T t, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		try {
			Writer writer = httpServletResponse.getWriter();

			InfoItemFieldValues infoItemFieldValues =
				InfoItemFieldValues.builder(
				).build();

			InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
				_infoItemServiceTracker.getFirstInfoItemService(
					InfoItemFieldValuesProvider.class,
					_templateEntry.getInfoItemClassName());

			if (infoItemFieldValuesProvider != null) {
				infoItemFieldValues =
					infoItemFieldValuesProvider.getInfoItemFieldValues(t);
			}

			TemplateDisplayTemplateTransformer
				templateDisplayTemplateTransformer =
					new TemplateDisplayTemplateTransformer(
						_templateEntry, infoItemFieldValues,
						_templateNodeFactory);

			String content = templateDisplayTemplateTransformer.transform(
				LocaleThreadLocal.getThemeDisplayLocale());

			writer.write(content);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private final InfoItemServiceTracker _infoItemServiceTracker;
	private final TemplateEntry _templateEntry;
	private final TemplateNodeFactory _templateNodeFactory;

}