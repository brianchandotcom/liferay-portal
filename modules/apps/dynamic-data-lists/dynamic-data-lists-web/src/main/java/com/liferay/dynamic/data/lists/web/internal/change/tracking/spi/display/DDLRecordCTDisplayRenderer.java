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
import com.liferay.change.tracking.spi.display.context.DisplayContext;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.taglib.servlet.taglib.HTMLTag;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.servlet.PipingServletResponse;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(immediate = true, service = CTDisplayRenderer.class)
public class DDLRecordCTDisplayRenderer
	extends BaseCTDisplayRenderer<DDLRecord> {

	@Override
	public Class<DDLRecord> getModelClass() {
		return DDLRecord.class;
	}

	@Override
	public String getTitle(Locale locale, DDLRecord ddlRecord) {
		return String.valueOf(ddlRecord.getPrimaryKey());
	}

	@Override
	protected void buildDisplay(DisplayBuilder<DDLRecord> displayBuilder)
		throws PortalException {

		DDLRecord ddlRecord = displayBuilder.getModel();

		DisplayContext<?> displayContext = displayBuilder.getDisplayContext();

		displayBuilder.display(
			"created-by",
			() -> {
				String userName = ddlRecord.getUserName();

				if (Validator.isNotNull(userName)) {
					return userName;
				}

				return null;
			}
		).display(
			"version", ddlRecord.getVersion()
		).display(
			"content",
			_getContent(
				displayContext.getHttpServletRequest(),
				displayContext.getHttpServletResponse(), ddlRecord,
				displayBuilder.getLocale()),
			false
		);
	}

	private String _getContent(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, DDLRecord ddlRecord,
			Locale locale)
		throws PortalException {

		DDLRecordSet ddlRecordSet = ddlRecord.getRecordSet();

		HTMLTag htmlTag = new HTMLTag();

		htmlTag.setClassNameId(
			_classNameLocalService.getClassNameId(DDMStructure.class));
		htmlTag.setClassPK(ddlRecordSet.getDDMStructureId());
		htmlTag.setDdmFormValues(ddlRecord.getDDMFormValues());
		htmlTag.setGroupId(ddlRecord.getGroupId());
		htmlTag.setReadOnly(true);
		htmlTag.setRequestedLocale(locale);

		try (UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter()) {
			htmlTag.doTag(
				httpServletRequest,
				new PipingServletResponse(
					httpServletResponse, unsyncStringWriter));

			return unsyncStringWriter.toString();
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDLRecordCTDisplayRenderer.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

}