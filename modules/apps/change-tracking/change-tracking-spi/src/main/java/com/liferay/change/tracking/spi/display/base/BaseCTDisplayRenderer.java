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

package com.liferay.change.tracking.spi.display.base;

import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.change.tracking.spi.display.context.DisplayContext;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.InputStream;
import java.io.Writer;

import java.sql.Blob;
import java.sql.SQLException;

import java.text.Format;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Preston Crary
 */
public abstract class BaseCTDisplayRenderer<T extends CTModel<T>>
	implements CTDisplayRenderer<T> {

	@Override
	public InputStream getDownloadInputStream(T model, String key)
		throws PortalException {

		CTService<T> ctService = getCTService();

		return ctService.updateWithUnsafeFunction(
			ctPersistence -> {
				Map<String, Function<T, Object>> attributeGetterFunctions =
					model.getAttributeGetterFunctions();

				Function<T, Object> function = attributeGetterFunctions.get(
					key);

				Blob blob = (Blob)function.apply(model);

				try {
					return blob.getBinaryStream();
				}
				catch (SQLException sqlException) {
					throw new ORMException(sqlException);
				}
			});
	}

	@Override
	public String getEditURL(HttpServletRequest httpServletRequest, T model) {
		return null;
	}

	@Override
	public abstract Class<T> getModelClass();

	@Override
	public abstract String getTitle(Locale locale, T model)
		throws PortalException;

	@Override
	public String getTypeName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, getClass());

		Class<T> modelClass = getModelClass();

		return LanguageUtil.get(
			resourceBundle, "model.resource." + modelClass.getName(),
			modelClass.getName());
	}

	@Override
	public boolean isHideable(T model) {
		return false;
	}

	@Override
	public void render(DisplayContext<T> displayContext) throws Exception {
		HttpServletResponse httpServletResponse =
			displayContext.getHttpServletResponse();

		Writer writer = httpServletResponse.getWriter();

		writer.write("<div class=\"table-responsive\"><table class=\"table\">");

		HttpServletRequest httpServletRequest =
			displayContext.getHttpServletRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Locale locale = themeDisplay.getLocale();

		Format format = FastDateFormatFactoryUtil.getDateTime(
			locale, themeDisplay.getTimeZone());

		Map<String, Object> displayAttributes = getDisplayAttributes(
			locale, displayContext.getModel());

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			themeDisplay.getLocale(), getClass());

		for (Map.Entry<String, Object> entry : displayAttributes.entrySet()) {
			writer.write("<tr><td>");
			writer.write(LanguageUtil.get(resourceBundle, entry.getKey()));
			writer.write("</td><td>");

			Object value = entry.getValue();

			if (value instanceof Blob) {
				String downloadURL = displayContext.getDownloadURL(
					entry.getKey(), 0, null);

				if (downloadURL == null) {
					writer.write(
						LanguageUtil.get(resourceBundle, "no-download"));
				}
				else {
					writer.write("<a href=\"");
					writer.write(downloadURL);
					writer.write("\" >");
					writer.write(LanguageUtil.get(resourceBundle, "download"));
					writer.write("</a>");
				}
			}
			else if (value instanceof Date) {
				writer.write(format.format(value));
			}
			else if (value instanceof String) {
				writer.write(HtmlUtil.escape(value.toString()));
			}
			else {
				writer.write(String.valueOf(value));
			}

			writer.write("</td></tr>");
		}

		writer.write("</table></div>");
	}

	protected abstract CTService<T> getCTService();

	protected String[] getDisplayAttributeNames() {
		return null;
	}

	protected Map<String, Object> getDisplayAttributes(Locale locale, T model) {
		Map<String, Object> attributes = new LinkedHashMap<>();

		Map<String, Function<T, Object>> attributeGetterFunctions =
			model.getAttributeGetterFunctions();

		for (Map.Entry<String, Function<T, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();

			if ((getDisplayAttributeNames() == null) ||
				ArrayUtil.contains(getDisplayAttributeNames(), attributeName)) {

				Function<T, Object> attributeGetterFunction = entry.getValue();

				Object value = attributeGetterFunction.apply(model);

				if (Validator.isNotNull(value)) {
					attributes.put(attributeName, value);
				}
			}
		}

		return attributes;
	}

}