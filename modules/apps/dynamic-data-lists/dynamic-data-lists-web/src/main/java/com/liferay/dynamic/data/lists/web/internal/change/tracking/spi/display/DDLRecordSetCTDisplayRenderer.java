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

package com.liferay.dynamic.data.lists.web.internal.change.tracking.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Cheryl Tang
 */
@Component(immediate = true, service = CTDisplayRenderer.class)
public class DDLRecordSetCTDisplayRenderer
	extends BaseCTDisplayRenderer<DDLRecordSet> {

	@Override
	public Class<DDLRecordSet> getModelClass() {
		return DDLRecordSet.class;
	}

	@Override
	public String getTitle(Locale locale, DDLRecordSet ddlRecordSet)
		throws PortalException {

		return ddlRecordSet.getName(locale);
	}

	@Override
	protected void buildDisplay(DisplayBuilder<DDLRecordSet> displayBuilder) {
		DDLRecordSet ddlRecordSet = displayBuilder.getModel();

		Locale locale = displayBuilder.getLocale();

		displayBuilder.display(
			"name", ddlRecordSet.getName(locale)
		).display(
			"created-by",
			() -> {
				String userName = ddlRecordSet.getUserName();

				if (Validator.isNotNull(userName)) {
					return userName;
				}

				return null;
			}
		).display(
			"create-date", ddlRecordSet.getCreateDate()
		).display(
			"last-modified", ddlRecordSet.getModifiedDate()
		).display(
			"version", ddlRecordSet.getVersion()
		).display(
			"description", ddlRecordSet.getDescription(locale)
		).display(
			"cached", ddlRecordSet.isCachedModel()
		);
	}

}