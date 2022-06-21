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

package com.liferay.portal.workflow.kaleo.definition.internal.change.tracking.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.change.tracking.spi.display.context.DisplayContext;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(immediate = true, service = CTDisplayRenderer.class)
public class KaleoDefinitionVersionCTDisplayRenderer
	extends BaseCTDisplayRenderer<KaleoDefinitionVersion> {

	@Override
	public KaleoDefinitionVersion fetchLatestVersionedModel(
		KaleoDefinitionVersion kaleoDefinitionVersion) {

		try {
			return _kaleoDefinitionVersionLocalService.
				fetchLatestKaleoDefinitionVersion(
					kaleoDefinitionVersion.getCompanyId(),
					kaleoDefinitionVersion.getName());
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}

			return null;
		}
	}

	@Override
	public Class<KaleoDefinitionVersion> getModelClass() {
		return KaleoDefinitionVersion.class;
	}

	@Override
	public String getTitle(
		Locale locale, KaleoDefinitionVersion kaleoDefinitionVersion) {

		return kaleoDefinitionVersion.getTitle(locale);
	}

	@Override
	public String getVersionName(
		KaleoDefinitionVersion kaleoDefinitionVersion) {

		return kaleoDefinitionVersion.getVersion();
	}

	@Override
	public String renderPreview(
		DisplayContext<KaleoDefinitionVersion> displayContext) {

		KaleoDefinitionVersion kaleoDefinitionVersion =
			displayContext.getModel();

		return StringBundler.concat(
			"<pre>",
			HtmlUtil.escapeAttribute(kaleoDefinitionVersion.getContent()),
			"</pre>");
	}

	@Override
	public boolean showPreviewDiff() {
		return true;
	}

	@Override
	protected void buildDisplay(
		DisplayBuilder<KaleoDefinitionVersion> displayBuilder) {

		KaleoDefinitionVersion kaleoDefinitionVersion =
			displayBuilder.getModel();

		displayBuilder.display(
			"name", kaleoDefinitionVersion.getName()
		).display(
			"title", kaleoDefinitionVersion.getTitle(displayBuilder.getLocale())
		).display(
			"description", kaleoDefinitionVersion.getDescription()
		).display(
			"created-by",
			() -> {
				String userName = kaleoDefinitionVersion.getUserName();

				if (Validator.isNotNull(userName)) {
					return userName;
				}

				return null;
			}
		).display(
			"create-date", kaleoDefinitionVersion.getCreateDate()
		).display(
			"last-modified", kaleoDefinitionVersion.getModifiedDate()
		).display(
			"version", kaleoDefinitionVersion.getVersion()
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoDefinitionVersionCTDisplayRenderer.class);

	@Reference
	private KaleoDefinitionLocalService _kaleoDefinitionLocalService;

	@Reference
	private KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;

}