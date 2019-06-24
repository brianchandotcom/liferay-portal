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

package com.liferay.info.internal.display.field;

import com.liferay.info.display.contributor.InfoDisplayField;
import com.liferay.info.display.contributor.field.InfoDisplayContributorField;
import com.liferay.info.display.contributor.field.InfoDisplayContributorFieldTracker;
import com.liferay.info.display.contributor.field.InfoDisplayContributorFieldType;
import com.liferay.info.display.field.InfoDisplayFieldProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.ContentTypes;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = InfoDisplayFieldProvider.class)
public class InfoDisplayFieldProviderImpl implements InfoDisplayFieldProvider {

	@Override
	public Set<InfoDisplayField> getContributorInfoDisplayFields(
		Locale locale, String... classNames) {

		Set<InfoDisplayField> infoDisplayFields = new LinkedHashSet<>();

		List<InfoDisplayContributorField> infoDisplayContributorFields =
			_infoDisplayContributorFieldTracker.getInfoDisplayContributorFields(
				classNames);

		for (InfoDisplayContributorField infoDisplayContributorField :
				infoDisplayContributorFields) {

			InfoDisplayContributorFieldType infoDisplayContributorFieldType =
				infoDisplayContributorField.getType();

			infoDisplayFields.add(
				new InfoDisplayField(
					infoDisplayContributorField.getKey(),
					infoDisplayContributorField.getLabel(locale),
					infoDisplayContributorFieldType.getValue()));
		}

		return infoDisplayFields;
	}

	@Override
	public Map<String, Object> getContributorInfoDisplayFieldsValues(
			String className, Object displayObject, Locale locale)
		throws PortalException {

		Map<String, Object> infoDisplayFieldsValues = new HashMap<>();

		List<InfoDisplayContributorField> infoDisplayContributorFields =
			_infoDisplayContributorFieldTracker.getInfoDisplayContributorFields(
				className);

		for (InfoDisplayContributorField infoDisplayContributorField :
				infoDisplayContributorFields) {

			InfoDisplayContributorFieldType infoDisplayContributorFieldType =
				infoDisplayContributorField.getType();

			Object fieldValue = infoDisplayContributorField.getValue(
				displayObject, locale);

			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			if ((serviceContext != null) &&
				!Objects.equals(
					InfoDisplayContributorFieldType.URL,
					infoDisplayContributorFieldType) &&
				(fieldValue instanceof String)) {

				fieldValue = SanitizerUtil.sanitize(
					serviceContext.getCompanyId(),
					serviceContext.getScopeGroupId(),
					serviceContext.getUserId(), className, 0,
					ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL,
					(String)fieldValue, null);
			}

			infoDisplayFieldsValues.putIfAbsent(
				infoDisplayContributorField.getKey(), fieldValue);
		}

		return infoDisplayFieldsValues;
	}

	@Reference
	private InfoDisplayContributorFieldTracker
		_infoDisplayContributorFieldTracker;

}