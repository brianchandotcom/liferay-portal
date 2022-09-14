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

package com.liferay.document.library.web.internal.change.tracking.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.repository.registry.RepositoryClassDefinition;
import com.liferay.portal.repository.registry.RepositoryClassDefinitionCatalogUtil;

import java.util.Locale;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(immediate = true, service = CTDisplayRenderer.class)
public class RepositoryCTDisplayRenderer
	extends BaseCTDisplayRenderer<Repository> {

	@Override
	public String getEditURL(
			HttpServletRequest httpServletRequest, Repository repository)
		throws Exception {

		Group group = _groupLocalService.getGroup(repository.getGroupId());

		if (group.isCompany()) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			group = themeDisplay.getScopeGroup();
		}

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest, group, DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
				0, 0, PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/document_library/edit_repository"
		).setRedirect(
			_portal.getCurrentURL(httpServletRequest)
		).setParameter(
			"folderId", repository.getDlFolderId()
		).setParameter(
			"repositoryId", repository.getRepositoryId()
		).buildString();
	}

	@Override
	public Class<Repository> getModelClass() {
		return Repository.class;
	}

	@Override
	public String getTitle(Locale locale, Repository repository) {
		return repository.getName();
	}

	@Override
	protected void buildDisplay(DisplayBuilder<Repository> displayBuilder) {
		Repository repository = displayBuilder.getModel();

		displayBuilder.display(
			"name", repository.getName()
		).display(
			"description", repository.getDescription()
		).display(
			"repository-type",
			() -> {
				RepositoryClassDefinition repositoryClassDefinition =
					RepositoryClassDefinitionCatalogUtil.
						getRepositoryClassDefinition(repository.getClassName());

				return repositoryClassDefinition.getRepositoryTypeLabel(
					displayBuilder.getLocale());
			}
		);
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

}