/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.object.constants.ObjectFolderConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.site.cms.site.initializer.internal.display.context.WorkflowTaskDisplayContext;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.portal.workflow.security.permission.WorkflowTaskPermission;
import com.liferay.portal.workflow.comparator.WorkflowComparatorFactory;
import com.liferay.portal.workflow.manager.WorkflowLogManager;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import jakarta.portlet.RenderRequest;
import jakarta.portlet.RenderResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.portal.kernel.util.MapUtil;
import jakarta.servlet.ServletContext;
import org.osgi.service.component.annotations.Reference;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;

/**
 * @author Christian Dorado
 */
public class ViewHomeWorkflowTasksDisplayContext {

	public ViewHomeWorkflowTasksDisplayContext(HttpServletRequest httpServletRequest, ThemeDisplay themeDisplay) {
        RenderRequest renderRequest = (RenderRequest)httpServletRequest.getAttribute(JavaConstants.JAKARTA_PORTLET_REQUEST);
        RenderResponse renderResponse = (RenderResponse)httpServletRequest.getAttribute(JavaConstants.JAKARTA_PORTLET_RESPONSE);

        _renderRequest = renderRequest;
        _renderResponse = renderResponse;
		_themeDisplay = themeDisplay;

        try {
            _setWorkflowTaskDisplayContextRenderRequestAttribute(_renderRequest, _renderResponse);
            _setWorkflowTaskRenderRequestAttribute(_renderRequest, _themeDisplay);
        } catch (PortalException e) {
            e.printStackTrace();
        }
	}

    private void _setWorkflowTaskDisplayContextRenderRequestAttribute(
        RenderRequest renderRequest, RenderResponse renderResponse
    ) throws PortalException {
            _workflowTaskDisplayContext = new WorkflowTaskDisplayContext(
            _portal.getLiferayPortletRequest(renderRequest),
            _portal.getLiferayPortletResponse(renderResponse),
            _workflowComparatorFactory, _workflowLogManager);
    }

    private void _setWorkflowTaskRenderRequestAttribute(RenderRequest renderRequest, ThemeDisplay themeDisplay) throws PortalException {
        long workflowTaskId = ParamUtil.getLong(renderRequest, "workflowTaskId");

        if (workflowTaskId > 0) {
			WorkflowTask workflowTask = WorkflowTaskManagerUtil.getWorkflowTask(
				themeDisplay.getCompanyId(), workflowTaskId);

            _workflowTask = workflowTask;
            
            long ctCollectionId = MapUtil.getLong(
            workflowTask.getOptionalAttributes(), "ctCollectionId",
            CTConstants.CT_COLLECTION_ID_PRODUCTION);

            CTCollection ctCollection = _ctCollectionLocalService.fetchCTCollection(
                ctCollectionId);

            if ((ctCollection != null) &&
                (ctCollection.getStatus() == WorkflowConstants.STATUS_APPROVED)) {

                ctCollectionId = CTConstants.CT_COLLECTION_ID_PRODUCTION;
            }

            if (ctCollectionId != CTCollectionThreadLocal.getCTCollectionId()) {
                _workflowTaskReadOnly = false;
            }

            long groupId = MapUtil.getLong(
                workflowTask.getOptionalAttributes(), "groupId",
                themeDisplay.getSiteGroupId());

            _workflowTaskReadOnly = _workflowTaskPermission.contains(
                themeDisplay.getPermissionChecker(), workflowTask, groupId
            );
        }
    }

	public Map<String, Object> getConstants() {
		return HashMapBuilder.<String, Object>put(
			"ercContentStructures",
			ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENT_STRUCTURES
		).put(
			"ercFileTypes",
			ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_FILE_TYPES
		).build();
	}

	public Map<String, Object> getReactData() throws PortalException {
		return HashMapBuilder.<String, Object>put(
			"constants", getConstants()
		).build();
	}
    
    @Reference
	private CTCollectionLocalService _ctCollectionLocalService;

    @Reference
	private WorkflowTaskPermission _workflowTaskPermission;

	@Reference
	private WorkflowComparatorFactory _workflowComparatorFactory;

	@Reference
	private WorkflowLogManager _workflowLogManager;

	@Reference
	private Portal _portal;

    protected ServletContext servletContext;
	private final ThemeDisplay _themeDisplay;
    private RenderRequest _renderRequest;
    private RenderResponse _renderResponse;
    private WorkflowTaskDisplayContext _workflowTaskDisplayContext;
    private WorkflowTask _workflowTask;
    private boolean _workflowTaskReadOnly;

}