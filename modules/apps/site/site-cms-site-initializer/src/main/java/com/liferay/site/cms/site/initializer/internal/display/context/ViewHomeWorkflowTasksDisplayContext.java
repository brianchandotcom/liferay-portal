/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.object.constants.ObjectFolderConstants;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.*;
import com.liferay.portal.kernel.workflow.*;
import com.liferay.site.cms.site.initializer.internal.display.context.helper.WorkflowTaskRequestHelper;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.portal.workflow.security.permission.WorkflowTaskPermission;
import com.liferay.portal.workflow.comparator.WorkflowComparatorFactory;
import com.liferay.portal.workflow.manager.WorkflowLogManager;
import jakarta.portlet.PortletException;
import jakarta.portlet.RenderRequest;
import jakarta.portlet.RenderResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.change.tracking.constants.CTConstants;
import jakarta.servlet.ServletContext;
import org.osgi.service.component.annotations.Reference;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.json.JSONUtil;

/**
 * @author Christian Dorado
 */
public class ViewHomeWorkflowTasksDisplayContext {

    public ViewHomeWorkflowTasksDisplayContext(HttpServletRequest httpServletRequest, ThemeDisplay themeDisplay, WorkflowLogManager workflowLogManager, WorkflowComparatorFactory workflowComparatorFactory) {

        _themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
                WebKeys.THEME_DISPLAY);
        _httpServletRequest = httpServletRequest;
        _workflowTaskRequestHelper = new WorkflowTaskRequestHelper(
                httpServletRequest);
        _workflowLogManager = workflowLogManager;
        _workflowComparatorFactory = workflowComparatorFactory;

        try {
            long userId = themeDisplay.getUserId();
            long companyId = themeDisplay.getCompanyId();
            _workflowTasks = WorkflowTaskManagerUtil.getWorkflowTasksByUser(
                    companyId, userId, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null
            );

            _workflowTasksByUserRoles = WorkflowTaskManagerUtil.getWorkflowTasksByUserRoles(companyId, userId, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

        } catch (PortalException e) {
            e.printStackTrace();
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

    public List<WorkflowTask> getWorkflowTasks() {
        return _workflowTasks;
    }

    public List<WorkflowTask> getWorkflowTasksByCurrentUserRoles() {
        return _workflowTasksByUserRoles;
    }

    private WorkflowLog _getWorkflowLog(WorkflowTask workflowTask)
            throws PortalException {

        List<WorkflowLog> workflowLogs =
                _workflowLogManager.getWorkflowLogsByWorkflowTask(
                        _workflowTaskRequestHelper.getCompanyId(),
                        workflowTask.getWorkflowTaskId(), null, 0, 1,
                        _workflowComparatorFactory.getLogCreateDateComparator(false));

        if (!workflowLogs.isEmpty()) {
            return workflowLogs.get(0);
        }

        return null;
    }

    public Locale getTaskContentLocale() {
        String languageId = LanguageUtil.getLanguageId(_httpServletRequest);

        if (Validator.isNotNull(languageId)) {
            return LocaleUtil.fromLanguageId(languageId);
        }

        return _workflowTaskRequestHelper.getLocale();
    }

    public String getAssetTitle(WorkflowTask workflowTask)
            throws PortalException {

        WorkflowHandler<?> workflowHandler = getWorkflowHandler(workflowTask);

        String title = workflowHandler.getTitle(
                getWorkflowContextEntryClassPK(workflowHandler, workflowTask),
                getTaskContentLocale());

        if (title != null) {
            return title;
        }

        return getAssetType(workflowTask);
    }

    public WorkflowHandler<?> getWorkflowHandler(WorkflowTask workflowTask)
            throws PortalException {

        return WorkflowHandlerRegistryUtil.getWorkflowHandler(
                _getWorkflowContextEntryClassName(workflowTask));
    }

    public String getAssetType(WorkflowTask workflowTask)
            throws PortalException {

        WorkflowHandler<?> workflowHandler = getWorkflowHandler(workflowTask);

        return workflowHandler.getType(getTaskContentLocale());
    }

    public long getWorkflowContextEntryClassPK(
            WorkflowHandler<?> workflowHandler, WorkflowTask workflowTask)
            throws PortalException {

        return workflowHandler.getEntryClassPK(
                _workflowTaskRequestHelper.getCompanyId(), _httpServletRequest,
                workflowTask);
    }

    private String _getWorkflowContextEntryClassName(WorkflowTask workflowTask)
            throws PortalException {

        Map<String, Serializable> workflowContext = _getWorkflowContext(
                workflowTask);

        return (String)workflowContext.get(
                WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME);
    }

    private Map<String, Serializable> _getWorkflowContext(
            WorkflowTask workflowTask)
            throws PortalException {

        WorkflowInstance workflowInstance = _getWorkflowInstance(workflowTask);

        return workflowInstance.getWorkflowContext();
    }

    public Map<String, Object> getReactData() throws Exception {
        String portletId = "com_liferay_portal_workflow_task_web_portlet_MyWorkflowTaskPortlet";

        String url = PortalUtil.getControlPanelFullURL(
                _themeDisplay.getScopeGroupId(), portletId, null);

        String fullURL = HttpComponentsUtil.addParameter(
                url,
                "mvcPath", "/edit_workflow_task.jsp");

        fullURL = HttpComponentsUtil.addParameter(
                fullURL, "backURL", _themeDisplay.getURLCurrent());

        return HashMapBuilder.<String, Object>put(
                "constants", getConstants()
        ).put(
                "currentWorkflowTasks", JSONUtil.toJSONArray(getWorkflowTasks(), workflowTask -> {
                        Map<String, Serializable> workflowContext = _getWorkflowContext(workflowTask);


                        JSONObject workflowContextJSON = JSONFactoryUtil.createJSONObject();

                        for (Map.Entry<String, Serializable> entry : workflowContext.entrySet()) {
                            Serializable value = entry.getValue();


                            workflowContextJSON.put(entry.getKey(), String.valueOf(value));
                        }
                        return JSONUtil
                                .put("auditUser", UserLocalServiceUtil.getUser(_getWorkflowLog(workflowTask).getAuditUserId()).getFullName())
                                .put("workflowId", workflowTask.getWorkflowTaskId())
                                .put("assetType", getAssetType(workflowTask))
                                .put("assetTitle", getAssetTitle(workflowTask))
                                .put("isComplete", workflowTask.isCompleted())
                                .put("createDate", workflowTask.getCreateDate())
                                .put("dueDate", workflowTask.getDueDate())
                                .put("workflowContext", workflowContextJSON)
                                .put("name", workflowTask.getName());

                })).put(
                "workflowTasksByCurrentUserRoles", JSONUtil.toJSONArray(getWorkflowTasksByCurrentUserRoles(), workflowTask -> {
                    Map<String, Serializable> workflowContext = _getWorkflowContext(workflowTask);


                    JSONObject workflowContextJSON = JSONFactoryUtil.createJSONObject();

                    for (Map.Entry<String, Serializable> entry : workflowContext.entrySet()) {
                        Serializable value = entry.getValue();


                        workflowContextJSON.put(entry.getKey(), String.valueOf(value));
                    }

                    return JSONUtil
                            .put("auditUser", UserLocalServiceUtil.getUser(_getWorkflowLog(workflowTask).getAuditUserId()).getFullName())
                            .put("workflowId", workflowTask.getWorkflowTaskId())
                            .put("assetType", getAssetType(workflowTask))
                            .put("assetTitle", getAssetTitle(workflowTask))
                            .put("isComplete", workflowTask.isCompleted())
                            .put("createDate", workflowTask.getCreateDate())
                            .put("dueDate", workflowTask.getDueDate())
                            .put("workflowContext", workflowContextJSON)
                            .put("name", workflowTask.getName());
                })).put("myWorkflowTasksURL", fullURL).build();
    }


    private WorkflowInstance _getWorkflowInstance(WorkflowTask workflowTask)
            throws PortalException {

        return WorkflowInstanceManagerUtil.getWorkflowInstance(
                _workflowTaskRequestHelper.getCompanyId(),
                _getWorkflowInstanceId(workflowTask));
    }

    private long _getWorkflowInstanceId(WorkflowTask workflowTask) {
        return workflowTask.getWorkflowInstanceId();
    }

    protected ServletContext servletContext;
    private List<WorkflowTask> _workflowTasks;
    private List<WorkflowTask> _workflowTasksByUserRoles;
    private final WorkflowTaskRequestHelper _workflowTaskRequestHelper;
    private final HttpServletRequest _httpServletRequest;
    private ThemeDisplay _themeDisplay;
    private WorkflowLogManager _workflowLogManager;
    private WorkflowComparatorFactory _workflowComparatorFactory;
}