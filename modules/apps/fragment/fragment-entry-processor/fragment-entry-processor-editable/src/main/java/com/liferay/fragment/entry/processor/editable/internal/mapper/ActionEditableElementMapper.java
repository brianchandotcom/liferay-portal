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

package com.liferay.fragment.entry.processor.editable.internal.mapper;

import com.liferay.fragment.entry.processor.editable.mapper.EditableElementMapper;
import com.liferay.fragment.processor.FragmentEntryProcessorContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Validator;

import org.jsoup.nodes.Element;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rubén Pulido
 */
@Component(property = "type=action", service = EditableElementMapper.class)
public class ActionEditableElementMapper implements EditableElementMapper {

	@Override
	public void map(
			Element element, JSONObject configJSONObject,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException {

		JSONObject mappedActionJSONObject = configJSONObject.getJSONObject(
			"mappedAction");

		if (mappedActionJSONObject == null) {
			return;
		}

		String classNameId = mappedActionJSONObject.getString("classNameId");
		String classPK = mappedActionJSONObject.getString("classPK");
		String fieldId = mappedActionJSONObject.getString("fieldId");

		if (Validator.isNull(classNameId) || Validator.isNull(classPK) ||
			Validator.isNull(fieldId)) {

			return;
		}

		element.attr("data-lfr-class-name-id", classNameId);
		element.attr("data-lfr-class-pk", classPK);
		element.attr("data-lfr-field-id", fieldId);
	}

}