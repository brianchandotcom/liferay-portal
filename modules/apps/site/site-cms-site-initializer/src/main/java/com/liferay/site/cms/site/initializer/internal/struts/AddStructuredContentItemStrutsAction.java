/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.struts;

import com.liferay.fragment.listener.FragmentEntryLinkListenerRegistry;
import com.liferay.fragment.renderer.FragmentRendererRegistry;
import com.liferay.fragment.service.FragmentEntryLinkService;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.search.InfoSearchClassMapperRegistry;
import com.liferay.layout.manager.FormManager;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.dto.v1_0.Status;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.service.ObjectDefinitionService;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.site.cms.site.initializer.internal.util.ActionUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	property = "path=/cms/add_structured_content_item",
	service = StrutsAction.class
)
public class AddStructuredContentItemStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		long objectDefinitionId = ParamUtil.getLong(
			httpServletRequest, "objectDefinitionId");

		ObjectDefinition objectDefinition =
			_objectDefinitionService.getObjectDefinition(objectDefinitionId);

		if (!Objects.equals(
				objectDefinition.getScope(),
				ObjectDefinitionConstants.SCOPE_DEPOT)) {

			return null;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		ObjectEntryManager objectEntryManager =
			_objectEntryManagerRegistry.getObjectEntryManager(
				objectDefinition.getCompanyId(),
				objectDefinition.getStorageType());

		ObjectEntry objectEntry = new ObjectEntry();

		DefaultDTOConverterContext defaultDTOConverterContext =
			new DefaultDTOConverterContext(
				false, null, null, null, null,
				themeDisplay.getSiteDefaultLocale(), null,
				themeDisplay.getUser());

		String objectEntryFolderExternalReferenceCode = ParamUtil.getString(
			httpServletRequest, "objectEntryFolderExternalReferenceCode");

		ObjectEntryFolder objectEntryFolder =
			_objectEntryFolderLocalService.
				fetchObjectEntryFolderByExternalReferenceCode(
					objectEntryFolderExternalReferenceCode,
					ParamUtil.getLong(httpServletRequest, "groupId"),
					objectDefinition.getCompanyId());

		String filterString = StringBundler.concat(
			"status eq ", WorkflowConstants.STATUS_DRAFT,
			" and title eq null and userId eq ", themeDisplay.getUserId());

		if (objectEntryFolder != null) {
			filterString = StringBundler.concat(
				"folderId eq ", objectEntryFolder.getObjectEntryFolderId(),
				" and ", filterString);
		}

		Page<ObjectEntry> page = objectEntryManager.getObjectEntries(
			objectDefinition.getCompanyId(), objectDefinition,
			String.valueOf(ParamUtil.getLong(httpServletRequest, "groupId")),
			null, defaultDTOConverterContext, filterString, Pagination.of(1, 1),
			null, null);

		if (page.getTotalCount() > 0) {
			objectEntry = page.fetchFirstItem();
		}
		else {
			objectEntry.setObjectEntryFolderExternalReferenceCode(
				() -> ParamUtil.getString(
					httpServletRequest,
					"objectEntryFolderExternalReferenceCode"));
			objectEntry.setStatus(
				() -> new Status() {
					{
						setCode(() -> WorkflowConstants.STATUS_DRAFT);
					}
				});

			objectEntry = objectEntryManager.addObjectEntry(
				defaultDTOConverterContext, objectDefinition, objectEntry,
				String.valueOf(
					ParamUtil.getLong(httpServletRequest, "groupId")));
		}

		httpServletResponse.sendRedirect(
			ActionUtil.getEditURL(
				_formManager, _fragmentEntryLinkListenerRegistry,
				_fragmentEntryLinkService, _fragmentRendererRegistry,
				httpServletRequest, String.valueOf(objectEntry.getId()),
				_infoItemServiceRegistry, _infoSearchClassMapperRegistry,
				objectDefinition));

		return null;
	}

	@Reference
	private FormManager _formManager;

	@Reference
	private FragmentEntryLinkListenerRegistry
		_fragmentEntryLinkListenerRegistry;

	@Reference
	private FragmentEntryLinkService _fragmentEntryLinkService;

	@Reference
	private FragmentRendererRegistry _fragmentRendererRegistry;

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Reference
	private InfoSearchClassMapperRegistry _infoSearchClassMapperRegistry;

	@Reference
	private ObjectDefinitionService _objectDefinitionService;

	@Reference
	private ObjectEntryFolderLocalService _objectEntryFolderLocalService;

	@Reference
	private ObjectEntryManagerRegistry _objectEntryManagerRegistry;

}