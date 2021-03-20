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
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.workflow.constants.WorkflowPortletKeys;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;

import java.util.Locale;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(immediate = true, service = CTDisplayRenderer.class)
public class KaleoDefinitionCTDisplayRenderer
	extends BaseCTDisplayRenderer<KaleoDefinition> {

	@Override
	public String getEditURL(
		HttpServletRequest httpServletRequest,
		KaleoDefinition kaleoDefinition) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest, themeDisplay.getScopeGroup(),
				WorkflowPortletKeys.CONTROL_PANEL_WORKFLOW, 0, 0,
				PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/definition/edit_workflow_definition.jsp"
		).setRedirect(
			_portal.getCurrentURL(httpServletRequest)
		).setParameter(
			"name", kaleoDefinition.getName()
		).setParameter(
			"version", kaleoDefinition.getVersion()
		).build(
		).toString();
	}

	@Override
	public Class<KaleoDefinition> getModelClass() {
		return KaleoDefinition.class;
	}

	@Override
	public String getTitle(Locale locale, KaleoDefinition kaleoDefinition) {
		return kaleoDefinition.getTitle(locale);
	}

	@Override
	public String renderPreview(
		DisplayContext<KaleoDefinition> displayContext) {

		KaleoDefinition kaleoDefinition = displayContext.getModel();

		return StringBundler.concat(
			"<pre>", HtmlUtil.escapeAttribute(kaleoDefinition.getContent()),
			"</pre>");
	}

	@Override
	public boolean showPreviewDiff() {
		return true;
	}

	@Override
	protected void buildDisplay(
		DisplayBuilder<KaleoDefinition> displayBuilder) {

		KaleoDefinition kaleoDefinition = displayBuilder.getModel();

		displayBuilder.display(
			"name", kaleoDefinition.getName()
		).display(
			"title", kaleoDefinition.getTitle(displayBuilder.getLocale())
		).display(
			"description", kaleoDefinition.getDescription()
		).display(
			"created-by",
			() -> {
				String userName = kaleoDefinition.getUserName();

				if (Validator.isNotNull(userName)) {
					return userName;
				}

				return null;
			}
		).display(
			"create-date", kaleoDefinition.getCreateDate()
		).display(
			"last-modified", kaleoDefinition.getModifiedDate()
		).display(
			"version", kaleoDefinition.getVersion()
		).display(
			"active", kaleoDefinition.isActive()
		);
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

}