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

import com.liferay.dynamic.data.lists.exporter.DDLExporter;
import com.liferay.dynamic.data.lists.exporter.DDLExporterFactory;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerTracker;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.dynamic.data.mapping.util.DDMDisplayRegistry;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.workflow.kaleo.forms.constants.KaleoFormsPortletKeys;
import com.liferay.portal.workflow.kaleo.forms.constants.KaleoFormsWebKeys;
import com.liferay.portal.workflow.kaleo.forms.exception.NoSuchKaleoProcessException;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess;
import com.liferay.portal.workflow.kaleo.forms.service.KaleoProcessService;
import com.liferay.portal.workflow.kaleo.forms.web.internal.configuration.KaleoFormsWebConfiguration;
import com.liferay.portal.workflow.kaleo.forms.web.internal.display.context.KaleoFormsAdminDisplayContext;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;

import java.io.IOException;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
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
	configurationPid = "com.liferay.portal.workflow.kaleo.forms.web.internal.configuration.KaleoFormsWebConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
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
		"javax.portlet.security-role-ref=administrator,power-user"
	},
	service = Portlet.class
)
public class KaleoFormsAdminPortlet extends MVCPortlet {

	public KaleoFormsAdminPortlet() {
		_parameterNames = ListUtil.fromArray(
			"backURL", "ddmStructureId", "ddmStructureName", "ddmTemplateId",
			"historyKey", "kaleoProcessId", "kaleoTaskFormPairsData", "mvcPath",
			"redirect", "tabs1", "translatedLanguagesDescription",
			"translatedLanguagesName", "workflowDefinition");

		for (Locale availableLocale : LanguageUtil.getAvailableLocales()) {
			_parameterNames.add(
				"description" + LocaleUtil.toLanguageId(availableLocale));
			_parameterNames.add(
				"name" + LocaleUtil.toLanguageId(availableLocale));
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

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			_setDisplayContext(renderRequest, renderResponse);

			_renderKaleoProcess(renderRequest);
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchKaleoProcessException ||
				exception instanceof PrincipalException ||
				exception instanceof WorkflowException) {

				SessionErrors.add(renderRequest, exception.getClass());
			}
			else {
				throw new PortletException(exception);
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

			if (Objects.equals(resourceID, "kaleoDraftDefinitions")) {
				_serveKaleoDraftDefinitions(resourceRequest, resourceResponse);
			}
			else if (Objects.equals(resourceID, "kaleoProcess")) {
				_serveKaleoProcess(resourceRequest, resourceResponse);
			}
			else if (Objects.equals(resourceID, "saveInPortletSession")) {
				_saveInPortletSession(resourceRequest);
			}
		}
		catch (IOException ioException) {
			throw ioException;
		}
		catch (PortletException portletException) {
			throw portletException;
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_kaleoFormsWebConfiguration = ConfigurableUtil.createConfigurable(
			KaleoFormsWebConfiguration.class, properties);
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

	@Reference
	protected StorageEngine storageEngine;

	/**
	 * Stores the Kaleo process, workflow instance, and workflow task as
	 * attributes in the request if the Kaleo process ID, workflow instance ID,
	 * and workflow task ID are present in the render request, respectively.
	 *
	 * @param  renderRequest the render request
	 * @param  renderResponse the render response
	 * @throws Exception if an exception occurred
	 */
	private void _renderKaleoProcess(RenderRequest renderRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long kaleoProcessId = ParamUtil.getLong(
			renderRequest, "kaleoProcessId");

		if (kaleoProcessId > 0) {
			renderRequest.setAttribute(
				KaleoFormsWebKeys.KALEO_PROCESS,
				_kaleoProcessService.getKaleoProcess(kaleoProcessId));
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
	 */
	private void _saveInPortletSession(
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
	 */
	private void _saveInPortletSession(ResourceRequest resourceRequest) {
		Map<String, String[]> parameterMap = resourceRequest.getParameterMap();

		PortletSession portletSession = resourceRequest.getPortletSession();

		for (String parameterName : _parameterNames) {
			if (!parameterMap.containsKey(parameterName)) {
				continue;
			}

			portletSession.setAttribute(
				parameterName,
				ParamUtil.getString(resourceRequest, parameterName));
		}

		if (parameterMap.containsKey("kaleoProcessLinkDDMTemplateId")) {
			portletSession.setAttribute(
				StringBundler.concat(
					ParamUtil.getString(
						resourceRequest, "kaleoProcessLinkDDMStructureId"),
					ParamUtil.getString(
						resourceRequest, "kaleoProcessLinkWorkflowDefinition"),
					ParamUtil.getString(
						resourceRequest, "kaleoProcessLinkWorkflowTaskName")),
				ParamUtil.getString(
					resourceRequest, "kaleoProcessLinkDDMTemplateId"));
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
	private void _serveKaleoDraftDefinitions(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		String name = ParamUtil.getString(resourceRequest, "name");
		String version = ParamUtil.getString(resourceRequest, "version");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (Validator.isNotNull(name) && Validator.isNotNull(version)) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)resourceRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				resourceRequest);

			KaleoDefinitionVersion kaleoDefinitionVersion =
				_kaleoDefinitionVersionLocalService.getKaleoDefinitionVersion(
					serviceContext.getCompanyId(), name, version);

			jsonObject.put(
				"content", kaleoDefinitionVersion.getContent()
			).put(
				"name", kaleoDefinitionVersion.getName()
			).put(
				"title",
				kaleoDefinitionVersion.getTitle(themeDisplay.getLocale())
			).put(
				"version", kaleoDefinitionVersion.getVersion()
			);
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
	private void _serveKaleoProcess(
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

		PortletResponseUtil.sendFile(
			resourceRequest, resourceResponse, fileName, bytes,
			MimeTypesUtil.getContentType(fileName));
	}

	/**
	 * Stores the {@link KaleoFormsAdminDisplayContext} as an attribute in the
	 * request.
	 *
	 * @param renderRequest the render request
	 * @param renderResponse the render response
	 */
	private void _setDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		KaleoFormsAdminDisplayContext kaleoFormsAdminDisplayContext =
			new KaleoFormsAdminDisplayContext(
				renderRequest, renderResponse, _ddlRecordLocalService,
				_ddmDisplayRegistry, _kaleoDefinitionVersionLocalService,
				_kaleoFormsWebConfiguration, storageEngine);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, kaleoFormsAdminDisplayContext);
	}

	@Reference
	private DDLExporterFactory _ddlExporterFactory;

	@Reference
	private DDLRecordLocalService _ddlRecordLocalService;

	@Reference
	private DDMDisplayRegistry _ddmDisplayRegistry;

	@Reference
	private DDMFormDeserializerTracker _ddmFormDeserializerTracker;

	@Reference
	private KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;

	private volatile KaleoFormsWebConfiguration _kaleoFormsWebConfiguration;

	@Reference
	private KaleoProcessService _kaleoProcessService;

	private final List<String> _parameterNames;

	@Reference
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

}