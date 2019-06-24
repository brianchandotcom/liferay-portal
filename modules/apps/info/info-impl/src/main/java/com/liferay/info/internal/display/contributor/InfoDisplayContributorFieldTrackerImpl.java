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

package com.liferay.info.internal.display.contributor;

import com.liferay.info.display.contributor.field.InfoDisplayContributorField;
import com.liferay.info.display.contributor.field.InfoDisplayContributorFieldTracker;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = InfoDisplayContributorFieldTracker.class)
public class InfoDisplayContributorFieldTrackerImpl
	implements InfoDisplayContributorFieldTracker {

	@Override
	public List<InfoDisplayContributorField> getInfoDisplayContributorFields(
		String className) {

		if (Validator.isNull(className)) {
			return Collections.emptyList();
		}

		return _itemClassInfoDisplayContributorFields.get(className);
	}

	@Override
	public List<InfoDisplayContributorField> getInfoDisplayContributorFields(
		String... classNames) {

		List<InfoDisplayContributorField> infoDisplayContributorFields =
			new ArrayList<>();

		for (String className : classNames) {
			infoDisplayContributorFields.addAll(
				_itemClassInfoDisplayContributorFields.get(className));
		}

		return infoDisplayContributorFields;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setInfoDisplayField(
		InfoDisplayContributorField infoDisplayContributorField,
		Map<String, Object> properties) {

		String className = (String)properties.get("model.class.name");

		if (Validator.isNull(className)) {
			return;
		}

		List<InfoDisplayContributorField> infoDisplayContributorFields =
			_itemClassInfoDisplayContributorFields.computeIfAbsent(
				className, itemClass -> new ArrayList<>());

		infoDisplayContributorFields.add(infoDisplayContributorField);
	}

	protected void unsetInfoDisplayField(
		InfoDisplayContributorField infoDisplayContributorField,
		Map<String, Object> properties) {

		String className = (String)properties.get("model.class.name");

		if (Validator.isNull(className)) {
			return;
		}

		List<InfoDisplayContributorField> infoDisplayContributorFields =
			_itemClassInfoDisplayContributorFields.get(className);

		if (infoDisplayContributorFields != null) {
			infoDisplayContributorFields.remove(infoDisplayContributorField);
		}
	}

	private final Map<String, List<InfoDisplayContributorField>>
		_itemClassInfoDisplayContributorFields = new ConcurrentHashMap<>();

}