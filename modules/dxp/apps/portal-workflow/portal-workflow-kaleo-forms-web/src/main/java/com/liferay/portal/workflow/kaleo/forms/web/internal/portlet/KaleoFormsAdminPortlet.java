/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.kaleo.forms.web.internal.portlet;

import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.dynamic.data.lists.exception.RecordSetDDMStructureIdException;
import com.liferay.dynamic.data.lists.exception.RecordSetNameException;
import com.liferay.dynamic.data.lists.exporter.DDLExporter;
import com.liferay.dynamic.data.lists.exporter.DDLExporterFactory;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordConstants;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.lists.service.DDLRecordService;
import com.liferay.dynamic.data.mapping.exception.RequiredStructureException;
import com.liferay.dynamic.data.mapping.exception.StructureDefinitionException;
import com.liferay.dynamic.data.mapping.io.DDMFormJSONDeserializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.dynamic.data.mapping.util.DDMDisplayRegistry;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesMerger;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.WorkflowInstanceLink;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.RequiredWorkflowDefinitionException;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.workflow.kaleo.exception.DuplicateKaleoDefinitionNameException;
import com.liferay.portal.workflow.kaleo.exception.KaleoDefinitionContentException;
import com.liferay.portal.workflow.kaleo.exception.KaleoDefinitionNameException;
import com.liferay.portal.workflow.kaleo.exception.NoSuchDefinitionException;
import com.liferay.portal.workflow.kaleo.exception.NoSuchDefinitionVersionException;
import com.liferay.portal.workflow.kaleo.forms.constants.KaleoFormsActionKeys;
import com.liferay.portal.workflow.kaleo.forms.constants.KaleoFormsPortletKeys;
import com.liferay.portal.workflow.kaleo.forms.constants.KaleoFormsWebKeys;
import com.liferay.portal.workflow.kaleo.forms.exception.KaleoProcessDDMTemplateIdException;
import com.liferay.portal.workflow.kaleo.forms.exception.NoSuchKaleoProcessException;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoTaskFormPairs;
import com.liferay.portal.workflow.kaleo.forms.service.KaleoProcessService;
import com.liferay.portal.workflow.kaleo.forms.service.permission.KaleoProcessPermission;
import com.liferay.portal.workflow.kaleo.forms.web.configuration.KaleoFormsWebConfiguration;
import com.liferay.portal.workflow.kaleo.forms.web.internal.display.context.KaleoFormsAdminDisplayContext;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;

import java.io.IOException;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * Handles the render, action, and resource serving phases of the Kaleo Forms
 * Admin portlet.
 *
 * @author Marcellus Tavares
 * @author Eduardo Lundgren
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=kaleo-forms-admin-portlet",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.footer-portal-javascript=/o/dynamic-data-mapping-web/js/custom_fields.js",
		"com.liferay.portlet.footer-portal-javascript=/o/dynamic-data-mapping-web/js/main.js",
		"com.liferay.portlet.footer-portlet-javascript=/admin/js/components.js",
		"com.liferay.portlet.footer-portlet-javascript=/admin/js/main.js",
		"com.liferay.portlet.header-portal-css=/o/dynamic-data-mapping-web/css/main.css",
		"com.liferay.portlet.header-portlet-css=/admin/css/main.css",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.render-weight=12",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Kaleo Forms Admin Web",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.copy-request-parameters=true",
		"javax.portlet.init-param.template-path=/admin/",
		"javax.portlet.init-param.view-template=/admin/view.jsp",
		"javax.portlet.name=" + KaleoFormsPortletKeys.KALEO_FORMS_ADMIN,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,power-user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class KaleoFormsAdminPortlet extends MVCPortlet {

	/**
	 * Deactivates the <code>WorkflowDefinition</code> (in
	 * <code>com.liferay.portal.kernel</code>) by using its name and version
	 * from the action request. If deactivation fails, an error key is submitted
	 * to <code>SessionErrors</code> (in
	 * <code>com.liferay.portal.kernel</code>).
	 *
	 * @param  actionRequest the request from which to get the request
	 *         parameters
	 * @param  actionResponse the response to receive the render parameters
	 * @throws Exception if an exception occurred
	 */
	public void deactivateWorkflowDefinition(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String name = ParamUtil.getString(actionRequest, "name");
		int version = ParamUtil.getInteger(actionRequest, "version");

		try {
			WorkflowDefinitionManagerUtil.updateActive(
				themeDisplay.getCompanyId(), themeDisplay.getUserId(), name,
				version, false);
		}
		catch (Exception e) {
			if (isSessionErrorException(e)) {
				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}

				SessionErrors.add(actionRequest, e.getClass(), e);

				sendRedirect(actionRequest, actionResponse);
			}
			else {
				throw e;
			}
		}
	}

	/**
	 * Deletes the <code>DDLRecord</code> (in the
	 * <code>com.liferay.dynamic.data.lists.api</code> module) associated with
	 * the record IDs from the action request. This method also deletes the
	 * <code>WorkflowInstanceLink</code>s (in
	 * <code>com.liferay.portal.kernel</code>) associated with each record ID
	 * and matching name of the <code>KaleoProcess</code> implementation (in the
	 * <code>com.liferay.portal.workflow.kaleo.forms.api</code> module). This
	 * method uses <code>TransactionInvokerUtil</code> (in
	 * <code>com.liferay.portal.kernel</code>) to ensure all tasks are performed
	 * in a single transaction.
	 *
	 * @param  actionRequest the request from which to get the request
	 *         parameters
	 * @param  actionResponse the response to receive the render parameters
	 * @throws Exception if an exception occurred
	 */
	public void deleteDDLRecord(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		final ThemeDisplay themeDisplay =
			(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

		long[] ddlRecordIds = getDDLRecordIds(actionRequest);

		for (final long ddlRecordId : ddlRecordIds) {
			try {
				Callable<Void> callable = new Callable<Void>() {

					@Override
					public Void call() throws Exception {
						_ddlRecordService.deleteRecord(ddlRecordId);

						_workflowInstanceLinkLocalService.
							deleteWorkflowInstanceLinks(
								themeDisplay.getCompanyId(),
								themeDisplay.getScopeGroupId(),
								KaleoProcess.class.getName(), ddlRecordId);

						return null;
					}

				};

				TransactionInvokerUtil.invoke(_transactionConfig, callable);
			}
			catch (Throwable t) {
				if (t instanceof PortalException) {
					throw (PortalException)t;
				}

				throw new SystemException(t);
			}
		}
	}

	/**
	 * Deletes the <code>KaleoDraftDefinition</code> (in the
	 * <code>com.liferay.portal.workflow.kaleo.designer.api</code> module) by
	 * using its name and version from the action request.
	 *
	 * @param  actionRequest the request from which to get the request
	 *         parameters
	 * @param  actionResponse the response to receive the render parameters
	 * @throws Exception if an exception occurred
	 */
	public void deleteKaleoDraftDefinition(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String name = ParamUtil.getString(actionRequest, "name");
		String version = ParamUtil.getString(actionRequest, "version");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		_kaleoDefinitionVersionLocalService.deleteKaleoDefinitionVersion(
			serviceContext.getCompanyId(), name, version);
	}

	/**
	 * Deletes all versions of a <code>KaleoDraftDefinition</code> (in the
	 * <code>com.liferay.portal.workflow.kaleo.designer.api</code> module) by
	 * using its name from the action request.
	 *
	 * @param  actionRequest the request from which to get the request
	 *         parameters
	 * @param  actionResponse the response to receive the render parameters
	 * @throws Exception if an exception occurred
	 */
	public void deleteKaleoDraftDefinitions(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String name = ParamUtil.getString(actionRequest, "name");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		_kaleoDefinitionVersionLocalService.deleteKaleoDefinitionVersions(
			serviceContext.getCompanyId(), name);
	}

	/**
	 * Deletes the <code>KaleoProcess</code> (in the
	 * <code>com.liferay.portal.workflow.kaleo.forms.api</code> module)
	 * associated with the Kaleo process IDs from the action request.
	 *
	 * @param  actionRequest the request from which to get the request
	 *         parameters
	 * @param  actionResponse the response to receive the render parameters
	 * @throws Exception if an exception occurred
	 */
	public void deleteKaleoProcess(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long[] kaleoProcessIds = getKaleoProcessIds(actionRequest);

		for (final long kaleoProcessId : kaleoProcessIds) {
			_kaleoProcessService.deleteKaleoProcess(kaleoProcessId);
		}
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			setDisplayContext(renderRequest, renderResponse);

			renderKaleoProcess(renderRequest, renderResponse);
		}
		catch (Exception e) {
			if (e instanceof NoSuchKaleoProcessException ||
				e instanceof PrincipalException ||
				e instanceof WorkflowException) {

				SessionErrors.add(renderRequest, e.getClass());
			}
			else {
				throw new PortletException(e);
			}
		}

		super.render(renderRequest, renderResponse);
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		try {
			String resourceID = resourceRequest.getResourceID();

			if (resourceID.equals("kaleoDraftDefinitions")) {
				serveKaleoDraftDefinitions(resourceRequest, resourceResponse);
			}
			else if (resourceID.equals("kaleoProcess")) {
				serveKaleoProcess(resourceRequest, resourceResponse);
			}
			else if (resourceID.equals("saveInPortletSession")) {
				saveInPortletSession(resourceRequest, resourceResponse);
			}
		}
		catch (IOException ioe) {
			throw ioe;
		}
		catch (PortletException pe) {
			throw pe;
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	/**
	 * Starts a <code>WorkflowInstance</code> (in
	 * <code>com.liferay.portal.kernel</code>) if the user has the
	 * <code>SUBMIT</code> permission. This method also updates the
	 * <code>DDLRecord</code> (in the
	 * <code>com.liferay.dynamic.data.lists.api</code> module).
	 *
	 * @param  actionRequest the request from which to get the request
	 *         parameters
	 * @param  actionResponse the response to receive the render parameters
	 * @throws Exception if an exception occurred
	 */
	public void startWorkflowInstance(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			_portal.getUploadPortletRequest(actionRequest);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDLRecord.class.getName(), uploadPortletRequest);

		checkKaleoProcessPermission(serviceContext, ActionKeys.SUBMIT);

		DDLRecord ddlRecord = updateDDLRecord(serviceContext);

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			serviceContext.getCompanyId(), serviceContext.getScopeGroupId(),
			serviceContext.getUserId(), KaleoProcess.class.getName(),
			ddlRecord.getRecordId(), ddlRecord, serviceContext);
	}

	/**
	 * Updates the <code>DDLRecord</code> (in the
	 * <code>com.liferay.dynamic.data.lists.api</code> module), checking the
	 * permission for the action ID
	 * <code>KaleoFormsActionKeys.COMPLETE_FORM</code> (in the
	 * <code>com.liferay.portal.workflow.kaleo.forms.api</code> module).
	 *
	 * @param  actionRequest the request from which to get the request
	 *         parameters
	 * @param  actionResponse the response to receive the render parameters
	 * @throws Exception if an exception occurred
	 */
	public void updateDDLRecord(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		UploadPortletRequest uploadPortletRequest =
			_portal.getUploadPortletRequest(actionRequest);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDLRecord.class.getName(), uploadPortletRequest);

		checkKaleoProcessPermission(
			serviceContext, KaleoFormsActionKeys.COMPLETE_FORM);

		updateDDLRecord(serviceContext);
	}

	/**
	 * Updates the <code>KaleoProcess</code> (in the
	 * <code>com.liferay.portal.workflow.kaleo.forms.api</code> module), or adds
	 * a new process if the Kaleo process ID from the action request is not
	 * greater than <code>0</code>. This method also updates the process's
	 * <code>WorkflowDefinitionLink</code> (in
	 * <code>com.liferay.portal.kernel</code>).
	 *
	 * @param  actionRequest the request from which to get the request
	 *         parameters
	 * @param  actionResponse the response to receive the render parameters
	 * @throws Exception if an exception occurred
	 */
	public void updateKaleoProcess(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long kaleoProcessId = ParamUtil.getLong(
			actionRequest, "kaleoProcessId");

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		long ddmStructureId = ParamUtil.getLong(
			actionRequest, "ddmStructureId");
		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");
		long ddmTemplateId = ParamUtil.getLong(actionRequest, "ddmTemplateId");
		String workflowDefinitionName = ParamUtil.getString(
			actionRequest, "workflowDefinitionName");
		int workflowDefinitionVersion = ParamUtil.getInteger(
			actionRequest, "workflowDefinitionVersion");
		String kaleoTaskFormPairsData = ParamUtil.getString(
			actionRequest, "kaleoTaskFormPairsData");

		KaleoTaskFormPairs kaleoKaleoTaskFormPairs = KaleoTaskFormPairs.parse(
			kaleoTaskFormPairsData);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			KaleoProcess.class.getName(), actionRequest);

		KaleoProcess kaleoProcess = null;

		if (kaleoProcessId <= 0) {
			kaleoProcess = _kaleoProcessService.addKaleoProcess(
				groupId, ddmStructureId, nameMap, descriptionMap, ddmTemplateId,
				workflowDefinitionName, workflowDefinitionVersion,
				kaleoKaleoTaskFormPairs, serviceContext);
		}
		else {
			kaleoProcess = _kaleoProcessService.updateKaleoProcess(
				kaleoProcessId, ddmStructureId, nameMap, descriptionMap,
				ddmTemplateId, workflowDefinitionName,
				workflowDefinitionVersion, kaleoKaleoTaskFormPairs,
				serviceContext);
		}

		String workflowDefinition = ParamUtil.getString(
			actionRequest, "workflowDefinition");

		_workflowDefinitionLinkLocalService.updateWorkflowDefinitionLink(
			serviceContext.getUserId(), serviceContext.getCompanyId(), groupId,
			KaleoProcess.class.getName(), kaleoProcess.getKaleoProcessId(), 0,
			workflowDefinition);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_kaleoFormsWebConfiguration = ConfigurableUtil.createConfigurable(
			KaleoFormsWebConfiguration.class, properties);
	}

	/**
	 * Checks the permission for the action ID.
	 *
	 * @param  serviceContext the service context to be applied
	 * @param  actionId the action ID
	 * @throws Exception if an exception occurred
	 */
	protected void checkKaleoProcessPermission(
			ServiceContext serviceContext, String actionId)
		throws Exception {

		HttpServletRequest request = serviceContext.getRequest();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		long kaleoProcessId = ParamUtil.getLong(request, "kaleoProcessId");

		KaleoProcessPermission.check(
			permissionChecker, kaleoProcessId, actionId);
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (SessionErrors.contains(
				renderRequest, NoSuchKaleoProcessException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, PrincipalException.getNestedClasses()) ||
			SessionErrors.contains(
				renderRequest, WorkflowException.class.getName())) {

			include(templatePath + "error.jsp", renderRequest, renderResponse);
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
	}

	/**
	 * Returns an array of the DDL record IDs obtained from the action request.
	 *
	 * @param  actionRequest the request from which to get the request
	 *         parameters
	 * @return an array of the DDL record IDs
	 */
	protected long[] getDDLRecordIds(ActionRequest actionRequest) {
		long ddlRecordId = ParamUtil.getLong(actionRequest, "ddlRecordId");

		if (ddlRecordId > 0) {
			return new long[] {ddlRecordId};
		}

		return StringUtil.split(
			ParamUtil.getString(actionRequest, "ddlRecordIds"), 0L);
	}

	/**
	 * Returns the workflow instance link ID associated with the company ID,
	 * group ID, and DDL record ID.
	 *
	 * @param  companyId the company ID
	 * @param  groupId the group ID
	 * @param  ddlRecordId the DDL record ID
	 * @return the primary key of the workflow instance link
	 * @throws Exception if an exception occurred
	 */
	protected long getDDLRecordWorkfowInstanceLinkId(
			long companyId, long groupId, long ddlRecordId)
		throws Exception {

		WorkflowInstanceLink workflowInstanceLink =
			_workflowInstanceLinkLocalService.getWorkflowInstanceLink(
				companyId, groupId, KaleoProcess.class.getName(), ddlRecordId);

		return workflowInstanceLink.getWorkflowInstanceLinkId();
	}

	/**
	 * Returns the DDM form using the definition in JSON format obtained from
	 * the action request.
	 *
	 * @param  actionRequest the request from which to get the request
	 *         parameters
	 * @return the DDM form
	 * @throws Exception if an exception occurred
	 */
	protected DDMForm getDDMForm(ActionRequest actionRequest) throws Exception {
		String definition = ParamUtil.getString(actionRequest, "definition");

		return _ddmFormJSONDeserializer.deserialize(definition);
	}

	/**
	 * Returns an array of the Kaleo process IDs in the action request.
	 *
	 * @param  actionRequest the request from which to get the request
	 *         parameters
	 * @return an array of the Kaleo process IDs
	 */
	protected long[] getKaleoProcessIds(ActionRequest actionRequest) {
		long kaleoProcessId = ParamUtil.getLong(
			actionRequest, "kaleoProcessId");

		if (kaleoProcessId > 0) {
			return new long[] {kaleoProcessId};
		}

		return StringUtil.split(
			ParamUtil.getString(actionRequest, "kaleoProcessIds"), 0L);
	}

	/**
	 * Returns the value of the first element identified by <code>name</code> in
	 * the XML content. If no name is read, returns the default name.
	 *
	 * @param  content the content in XML format
	 * @param  defaultName the default name
	 * @return the name value in the XML content, or the default name if the XML
	 *         content does not contain a name
	 */
	protected String getName(String content, String defaultName) {
		if (Validator.isNull(content)) {
			return defaultName;
		}

		try {
			Document document = SAXReaderUtil.read(content);

			Element rootElement = document.getRootElement();

			return rootElement.elementTextTrim("name");
		}
		catch (DocumentException de) {
			return defaultName;
		}
	}

	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		if (cause instanceof DuplicateKaleoDefinitionNameException ||
			cause instanceof KaleoDefinitionContentException ||
			cause instanceof KaleoDefinitionNameException ||
			cause instanceof KaleoProcessDDMTemplateIdException ||
			cause instanceof NoSuchDefinitionException ||
			cause instanceof NoSuchDefinitionVersionException ||
			cause instanceof RecordSetDDMStructureIdException ||
			cause instanceof RecordSetNameException ||
			cause instanceof RequiredStructureException ||
			cause instanceof RequiredWorkflowDefinitionException ||
			cause instanceof StructureDefinitionException ||
			cause instanceof WorkflowException) {

			return true;
		}

		return false;
	}

	/**
	 * Stores the Kaleo process, workflow instance, and workflow task as
	 * attributes in the request if the Kaleo process ID, workflow instance ID,
	 * and workflow task ID are present in the render request, respectively.
	 *
	 * @param  renderRequest the render request
	 * @param  renderResponse the render response
	 * @throws Exception if an exception occurred
	 */
	protected void renderKaleoProcess(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long kaleoProcessId = ParamUtil.getLong(
			renderRequest, "kaleoProcessId");

		if (kaleoProcessId > 0) {
			KaleoProcess kaleoProcess = _kaleoProcessService.getKaleoProcess(
				kaleoProcessId);

			renderRequest.setAttribute(
				KaleoFormsWebKeys.KALEO_PROCESS, kaleoProcess);
		}

		long workflowInstanceId = ParamUtil.getLong(
			renderRequest, "workflowInstanceId");

		if (workflowInstanceId > 0) {
			WorkflowInstance workflowInstance =
				WorkflowInstanceManagerUtil.getWorkflowInstance(
					themeDisplay.getCompanyId(), workflowInstanceId);

			renderRequest.setAttribute(
				KaleoFormsWebKeys.WORKFLOW_INSTANCE, workflowInstance);
		}

		long workflowTaskId = ParamUtil.getLong(
			renderRequest, "workflowTaskId");

		if (workflowTaskId > 0) {
			WorkflowTask workflowTask = WorkflowTaskManagerUtil.getWorkflowTask(
				themeDisplay.getCompanyId(), workflowTaskId);

			renderRequest.setAttribute(
				KaleoFormsWebKeys.WORKFLOW_TASK, workflowTask);
		}
	}

	/**
	 * Binds the workflow definition to the portlet session, following the
	 * format <code>KaleoDraftDefinition.getName() + "@" +
	 * KaleoDraftDefinition.getVersion()</code>.
	 *
	 * @param actionRequest the request from which to get the request parameters
	 * @param kaleoDraftDefinition the object instance to bound to the portlet
	 *        session
	 */
	protected void saveInPortletSession(
		ActionRequest actionRequest,
		KaleoDefinitionVersion kaleoDefinitionVersion) {

		PortletSession portletSession = actionRequest.getPortletSession();

		portletSession.setAttribute(
			"workflowDefinition",
			kaleoDefinitionVersion.getName() + StringPool.AT +
				kaleoDefinitionVersion.getVersion());
	}

	/**
	 * Binds all parameters in the request except <code>doAsUserId</code> to the
	 * portlet session.
	 *
	 * @param  resourceRequest the resource request
	 * @param  resourceResponse the resource response
	 * @throws Exception if an exception occurred
	 */
	protected void saveInPortletSession(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		Enumeration<String> enumeration = resourceRequest.getParameterNames();

		while (enumeration.hasMoreElements()) {
			String name = enumeration.nextElement();

			if (name.equals("doAsUserId")) {
				continue;
			}

			PortletSession portletSession = resourceRequest.getPortletSession();

			String value = ParamUtil.getString(resourceRequest, name);

			portletSession.setAttribute(name, value);
		}
	}

	/**
	 * Sends the Kaleo draft definition in JSON format if the name and draft
	 * version exist; otherwise, sends an empty JSON object.
	 *
	 * @param  resourceRequest the resource request
	 * @param  resourceResponse the resource response
	 * @throws Exception if an exception occurred
	 */
	protected void serveKaleoDraftDefinitions(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String name = ParamUtil.getString(resourceRequest, "name");
		String version = ParamUtil.getString(resourceRequest, "version");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			resourceRequest);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (Validator.isNotNull(name) && Validator.isNotNull(version)) {
			KaleoDefinitionVersion kaleoDefinitionVersion =
				_kaleoDefinitionVersionLocalService.getKaleoDefinitionVersion(
					serviceContext.getCompanyId(), name, version);

			jsonObject.put("content", kaleoDefinitionVersion.getContent());
			jsonObject.put("name", kaleoDefinitionVersion.getName());
			jsonObject.put(
				"title",
				kaleoDefinitionVersion.getTitle(themeDisplay.getLocale()));
			jsonObject.put("version", kaleoDefinitionVersion.getVersion());
		}

		writeJSON(resourceRequest, resourceResponse, jsonObject);
	}

	/**
	 * Sends a file with the exported records. If
	 * <code>exportOnlyApproved</code> is <code>true</code>, only records with
	 * the status of approved are included. The file format is determined by the
	 * file extension.
	 *
	 * @param  resourceRequest the resource request
	 * @param  resourceResponse the resource response
	 * @throws Exception if an exception occurred
	 */
	protected void serveKaleoProcess(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long kaleoProcessId = ParamUtil.getLong(
			resourceRequest, "kaleoProcessId");

		KaleoProcess kaleoProcess = _kaleoProcessService.getKaleoProcess(
			kaleoProcessId);

		String fileExtension = ParamUtil.getString(
			resourceRequest, "fileExtension");

		String fileName =
			kaleoProcess.getName(themeDisplay.getLocale()) + CharPool.PERIOD +
				fileExtension;

		int status = WorkflowConstants.STATUS_ANY;

		boolean exportOnlyApproved = ParamUtil.getBoolean(
			resourceRequest, "exportOnlyApproved");

		if (exportOnlyApproved) {
			status = WorkflowConstants.STATUS_APPROVED;
		}

		DDLExporter ddlExporter = _ddlExporterFactory.getDDLExporter(
			fileExtension);

		ddlExporter.setLocale(themeDisplay.getLocale());

		byte[] bytes = ddlExporter.export(
			kaleoProcess.getDDLRecordSetId(), status);

		String contentType = MimeTypesUtil.getContentType(fileName);

		PortletResponseUtil.sendFile(
			resourceRequest, resourceResponse, fileName, bytes, contentType);
	}

	@Reference(unbind = "-")
	protected void setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDLExporterFactory(
		DDLExporterFactory ddlExporterFactory) {

		_ddlExporterFactory = ddlExporterFactory;
	}

	@Reference(unbind = "-")
	protected void setDDLRecordLocalService(
		DDLRecordLocalService ddlRecordLocalService) {

		_ddlRecordLocalService = ddlRecordLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDLRecordService(DDLRecordService ddlRecordService) {
		_ddlRecordService = ddlRecordService;
	}

	@Reference(unbind = "-")
	protected void setDDM(DDM ddm) {
		_ddm = ddm;
	}

	@Reference(unbind = "-")
	protected void setDDMDisplayRegistry(
		DDMDisplayRegistry ddmDisplayRegistry) {

		_ddmDisplayRegistry = ddmDisplayRegistry;
	}

	@Reference(unbind = "-")
	protected void setDDMFormJSONDeserializer(
		DDMFormJSONDeserializer ddmFormJSONDeserializer) {

		_ddmFormJSONDeserializer = ddmFormJSONDeserializer;
	}

	@Reference(unbind = "-")
	protected void setDDMFormValuesMerger(
		DDMFormValuesMerger ddmFormValuesMerger) {

		_ddmFormValuesMerger = ddmFormValuesMerger;
	}

	/**
	 * Stores the {@link KaleoFormsAdminDisplayContext} as an attribute in the
	 * request.
	 *
	 * @param renderRequest the render request
	 * @param renderResponse the render response
	 */
	protected void setDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		KaleoFormsAdminDisplayContext kaleoFormsAdminDisplayContext =
			new KaleoFormsAdminDisplayContext(
				renderRequest, renderResponse, _ddmDisplayRegistry,
				_kaleoDefinitionVersionLocalService,
				_kaleoFormsWebConfiguration, storageEngine);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, kaleoFormsAdminDisplayContext);
	}

	@Reference(unbind = "-")
	protected void setKaleoDefinitionVersionLocalService(
		KaleoDefinitionVersionLocalService kaleoDefinitionVersionLocalService) {

		_kaleoDefinitionVersionLocalService =
			kaleoDefinitionVersionLocalService;
	}

	@Reference(unbind = "-")
	protected void setKaleoProcessService(
		KaleoProcessService kaleoProcessService) {

		_kaleoProcessService = kaleoProcessService;
	}

	@Reference(unbind = "-")
	protected void setStorageEngine(StorageEngine storageEngine) {
		this.storageEngine = storageEngine;
	}

	@Reference(unbind = "-")
	protected void setWorkflowDefinitionLinkLocalService(
		WorkflowDefinitionLinkLocalService workflowDefinitionLinkLocalService) {

		_workflowDefinitionLinkLocalService =
			workflowDefinitionLinkLocalService;
	}

	@Reference(unbind = "-")
	protected void setWorkflowInstanceLinkLocalService(
		WorkflowInstanceLinkLocalService workflowInstanceLinkLocalService) {

		_workflowInstanceLinkLocalService = workflowInstanceLinkLocalService;
	}

	/**
	 * Updates the Kaleo process's asset entry with new asset categories, tag
	 * names, and link entries, removing and adding them as necessary.
	 *
	 * @param  userId the primary key of the user updating the record's asset
	 *         entry
	 * @param  ddlRecord the DDL record
	 * @param  kaleoProcess the Kaleo process
	 * @param  assetCategoryIds the primary keys of the new asset categories
	 * @param  assetTagNames the new asset tag names
	 * @param  locale the locale to apply to the asset
	 * @param  priority the new priority
	 * @throws PortalException if a portal exception occurred
	 */
	protected void updateAssetEntry(
			long userId, DDLRecord ddlRecord, KaleoProcess kaleoProcess,
			long[] assetCategoryIds, String[] assetTagNames, Locale locale,
			Double priority)
		throws PortalException {

		DDLRecordSet ddlRecordSet = ddlRecord.getRecordSet();

		DDMStructure ddmStructure = ddlRecordSet.getDDMStructure();

		String ddmStructureName = ddmStructure.getName(locale);

		String ddlRecordSetName = ddlRecordSet.getName(locale);

		String title = LanguageUtil.format(
			locale, "new-x-for-list-x",
			new Object[] {ddmStructureName, ddlRecordSetName}, false);

		_assetEntryLocalService.updateEntry(
			userId, kaleoProcess.getGroupId(), kaleoProcess.getCreateDate(),
			kaleoProcess.getModifiedDate(), KaleoProcess.class.getName(),
			ddlRecord.getRecordId(), kaleoProcess.getUuid(), 0,
			assetCategoryIds, assetTagNames, true, true, null, null, null,
			ContentTypes.TEXT_HTML, title, null, StringPool.BLANK, null, null,
			0, 0, priority);
	}

	/**
	 * Updates the DDL record by replacing its values, or creates a new DDL
	 * record if one does not exist in the request. This method also updates the
	 * Kaleo process's asset entry.
	 *
	 * @param  serviceContext the service context to be applied
	 * @return the DDL record
	 * @throws Exception if an exception occurred
	 */
	protected DDLRecord updateDDLRecord(ServiceContext serviceContext)
		throws Exception {

		HttpServletRequest request = serviceContext.getRequest();

		long ddlRecordId = ParamUtil.getLong(request, "ddlRecordId");

		long ddlRecordSetId = ParamUtil.getLong(request, "ddlRecordSetId");

		long kaleoProcessId = ParamUtil.getLong(request, "kaleoProcessId");

		KaleoProcess kaleoProcess = _kaleoProcessService.getKaleoProcess(
			kaleoProcessId);

		DDLRecord ddlRecord = _ddlRecordLocalService.fetchDDLRecord(
			ddlRecordId);

		DDLRecordSet ddlRecordSet = kaleoProcess.getDDLRecordSet();

		DDMFormValues ddmFormValues = _ddm.getDDMFormValues(
			ddlRecordSet.getDDMStructureId(), StringPool.BLANK, serviceContext);

		if (ddlRecord == null) {
			ddlRecord = _ddlRecordLocalService.addRecord(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				ddlRecordSetId, DDLRecordConstants.DISPLAY_INDEX_DEFAULT,
				ddmFormValues, serviceContext);
		}
		else {
			boolean majorVersion = ParamUtil.getBoolean(
				serviceContext, "majorVersion");

			ddmFormValues = _ddmFormValuesMerger.merge(
				ddmFormValues, ddlRecord.getDDMFormValues());

			ddlRecord = _ddlRecordLocalService.updateRecord(
				serviceContext.getUserId(), ddlRecordId, majorVersion,
				DDLRecordConstants.DISPLAY_INDEX_DEFAULT, ddmFormValues,
				serviceContext);
		}

		updateAssetEntry(
			serviceContext.getUserId(), ddlRecord, kaleoProcess,
			serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(), serviceContext.getLocale(),
			serviceContext.getAssetPriority());

		return ddlRecord;
	}

	protected StorageEngine storageEngine;

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoFormsAdminPortlet.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRES_NEW, new Class<?>[] {Exception.class});

	private AssetEntryLocalService _assetEntryLocalService;
	private DDLExporterFactory _ddlExporterFactory;
	private DDLRecordLocalService _ddlRecordLocalService;
	private DDLRecordService _ddlRecordService;
	private DDM _ddm;
	private DDMDisplayRegistry _ddmDisplayRegistry;
	private DDMFormJSONDeserializer _ddmFormJSONDeserializer;
	private DDMFormValuesMerger _ddmFormValuesMerger;
	private KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;
	private volatile KaleoFormsWebConfiguration _kaleoFormsWebConfiguration;
	private KaleoProcessService _kaleoProcessService;

	@Reference
	private Portal _portal;

	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

}