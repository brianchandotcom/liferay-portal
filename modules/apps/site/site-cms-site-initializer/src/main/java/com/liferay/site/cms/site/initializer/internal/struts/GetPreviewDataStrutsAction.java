/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.struts;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.model.DepotEntryGroupRel;
import com.liferay.depot.service.DepotEntryGroupRelService;
import com.liferay.depot.service.DepotEntryService;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionService;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán Grande
 */
@Component(
	property = "path=/cms/get_preview_data", service = StrutsAction.class
)
public class GetPreviewDataStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		long objectEntryId = ParamUtil.getLong(
			httpServletRequest, "objectEntryId");

		ObjectEntry objectEntry = _objectEntryService.getObjectEntry(
			objectEntryId);

		ObjectDefinition objectDefinition =
			_objectDefinitionService.getObjectDefinition(
				objectEntry.getObjectDefinitionId());

		long classNameId = _portal.getClassNameId(
			objectDefinition.getClassName());

		DepotEntry depotEntry = _depotEntryService.getGroupDepotEntry(
			objectEntry.getGroupId());

		List<DepotEntryGroupRel> depotEntryGroupRels =
			_depotEntryGroupRelService.getDepotEntryGroupRels(
				depotEntry, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (DepotEntryGroupRel depotEntryGroupRel : depotEntryGroupRels) {
			long toGroupId = depotEntryGroupRel.getToGroupId();

			List<LayoutPageTemplateEntry> layoutPageTemplateEntries =
				_layoutPageTemplateEntryService.getLayoutPageTemplateEntries(
					toGroupId, classNameId, 0L,
					LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE,
					WorkflowConstants.STATUS_APPROVED);

			JSONArray displayPagesJSONArray = _jsonFactory.createJSONArray();

			for (LayoutPageTemplateEntry layoutPageTemplateEntry :
					layoutPageTemplateEntries) {

				displayPagesJSONArray.put(
					JSONUtil.put(
						"name", layoutPageTemplateEntry.getName()
					).put(
						"plid", layoutPageTemplateEntry.getPlid()
					).put(
						"url",
						() -> {
							ThemeDisplay themeDisplay =
								(ThemeDisplay)httpServletRequest.getAttribute(
									WebKeys.THEME_DISPLAY);

							return HttpComponentsUtil.addParameters(
								themeDisplay.getPortalURL() +
									themeDisplay.getPathMain() +
										"/portal/get_page_preview",
								"className", objectDefinition.getClassName(),
								"classPK", objectEntry.getObjectEntryId(),
								"p_l_mode", Constants.PREVIEW, "selPlid",
								layoutPageTemplateEntry.getPlid(), "p_p_state",
								LiferayWindowState.POP_UP.toString());
						}
					));
			}

			Group group = _groupService.getGroup(toGroupId);

			jsonArray.put(
				JSONUtil.put(
					"displayPageTemplates", displayPagesJSONArray
				).put(
					"groupId", toGroupId
				).put(
					"name", group.getDescriptiveName(LocaleUtil.getDefault())
				));
		}

		ServletResponseUtil.write(
			httpServletResponse, JSONUtil.toString(jsonArray));

		return null;
	}

	@Reference
	private DepotEntryGroupRelService _depotEntryGroupRelService;

	@Reference
	private DepotEntryService _depotEntryService;

	@Reference
	private GroupService _groupService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Reference
	private ObjectDefinitionService _objectDefinitionService;

	@Reference
	private ObjectEntryService _objectEntryService;

	@Reference
	private Portal _portal;

}