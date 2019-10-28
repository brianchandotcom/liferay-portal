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

package com.liferay.portal.workflow.kaleo.forms.web.internal.util;

import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManagerUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLink;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoTaskFormPair;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoTaskFormPairs;
import com.liferay.portal.workflow.kaleo.forms.service.KaleoProcessLinkLocalServiceUtil;
import com.liferay.portal.workflow.kaleo.forms.service.KaleoProcessServiceUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletSession;

/**
 * @author Marcellus Tavares
 */
public class KaleoFormsUtil {

	/**
	 * Removes all attributes from the portlet session.
	 *
	 * @param portletSession the session obtained from the request
	 */
	public static void cleanUpPortletSession(PortletSession portletSession) {
		Enumeration<String> enumeration = portletSession.getAttributeNames();

		while (enumeration.hasMoreElements()) {
			String name = enumeration.nextElement();

			portletSession.removeAttribute(name);
		}
	}

	/**
	 * Returns a new instance of the Kaleo task form pair.
	 *
	 * @param  kaleoProcessId the primary key of the Kaleo process
	 * @param  ddmStructureId the primary key of the record set's DDM structure
	 * @param  workflowDefinition the workflow definition
	 * @param  initialStateName the initial workflow task name
	 * @param  portletSession the portlet session obtained from the request
	 * @return a new instance of the Kaleo task form pair
	 * @throws Exception if an exception occurred
	 */
	public static KaleoTaskFormPair getInitialStateKaleoTaskFormPair(
			long kaleoProcessId, long ddmStructureId, String workflowDefinition,
			String initialStateName, PortletSession portletSession)
		throws Exception {

		String taskSessionKey = _getTaskSessionKey(
			ddmStructureId, workflowDefinition, initialStateName);

		long ddmTemplateId = 0;

		if (portletSession.getAttribute(taskSessionKey) != null) {
			ddmTemplateId = GetterUtil.getLong(
				portletSession.getAttribute(taskSessionKey));
		}
		else if (kaleoProcessId > 0) {
			KaleoProcess kaleoProcess = KaleoProcessServiceUtil.getKaleoProcess(
				kaleoProcessId);

			DDLRecordSet ddlRecordSet = kaleoProcess.getDDLRecordSet();

			String kaleoProcessWorkflowDefinition =
				kaleoProcess.getWorkflowDefinition();

			if ((ddlRecordSet.getDDMStructureId() == ddmStructureId) &&
				kaleoProcessWorkflowDefinition.equals(workflowDefinition)) {

				ddmTemplateId = kaleoProcess.getDDMTemplateId();
			}
		}

		return new KaleoTaskFormPair(initialStateName, ddmTemplateId);
	}

	/**
	 * Returns the initial workflow task name.
	 *
	 * @param  companyId the company ID
	 * @param  workflowDefinition the workflow definition
	 * @return the workflow task name
	 * @throws Exception if an exception occurred
	 */
	public static String getInitialStateName(
			long companyId, String workflowDefinition)
		throws Exception {

		if (Validator.isNull(workflowDefinition)) {
			return StringPool.BLANK;
		}

		String[] workflowDefinitionParts = StringUtil.split(
			workflowDefinition, CharPool.AT);

		String workflowDefinitionName = workflowDefinitionParts[0];
		int workflowDefinitionVersion = GetterUtil.getInteger(
			workflowDefinitionParts[1]);

		Document document = _getWorkflowDefinitionDocument(
			companyId, workflowDefinitionName, workflowDefinitionVersion);

		return _getInitalStateName(document.getRootElement());
	}

	/**
	 * Returns the Kaleo process's DDM structure ID.
	 *
	 * @param  kaleoProcess the Kaleo process
	 * @param  portletSession the portlet session obtained from the request
	 * @return the Kaleo process's DDM structure ID
	 * @throws Exception if an exception occurred
	 */
	public static long getKaleoProcessDDMStructureId(
			KaleoProcess kaleoProcess, PortletSession portletSession)
		throws Exception {

		long ddmStructureId = GetterUtil.getLong(
			portletSession.getAttribute("ddmStructureId"));

		if (ddmStructureId > 0) {
			return ddmStructureId;
		}

		if (kaleoProcess != null) {
			DDLRecordSet ddlRecordSet = kaleoProcess.getDDLRecordSet();

			return ddlRecordSet.getDDMStructureId();
		}

		return 0;
	}

	/**
	 * Returns the Kaleo process's DDM structure ID associated with the Kaleo
	 * process ID.
	 *
	 * @param  kaleoProcessId the primary key of the Kaleo process
	 * @param  portletSession the portlet session obtained from the request
	 * @return the Kaleo process's DDM structure ID
	 * @throws Exception if an exception occurred
	 */
	public static long getKaleoProcessDDMStructureId(
			long kaleoProcessId, PortletSession portletSession)
		throws Exception {

		KaleoProcess kaleoProcess = null;

		if (kaleoProcessId > 0) {
			kaleoProcess = KaleoProcessServiceUtil.getKaleoProcess(
				kaleoProcessId);
		}

		return getKaleoProcessDDMStructureId(kaleoProcess, portletSession);
	}

	/**
	 * Returns the Kaleo process's description.
	 *
	 * @param  kaleoProcess the Kaleo process
	 * @param  portletSession the portlet session obtained from the request
	 * @return the Kaleo process's description
	 * @throws Exception if an exception occurred
	 */
	public static String getKaleoProcessDescription(
			KaleoProcess kaleoProcess, PortletSession portletSession)
		throws Exception {

		Map<Locale, String> descriptionMap = new HashMap<>();

		String translatedLanguagesDescription = GetterUtil.getString(
			portletSession.getAttribute("translatedLanguagesDescription"));

		for (String translatedLanguageId :
				StringUtil.split(translatedLanguagesDescription)) {

			String description = GetterUtil.getString(
				portletSession.getAttribute(
					"description" + translatedLanguageId));

			Locale locale = LocaleUtil.fromLanguageId(translatedLanguageId);

			descriptionMap.put(locale, description);
		}

		if (!descriptionMap.isEmpty()) {
			return LocalizationUtil.updateLocalization(
				descriptionMap, StringPool.BLANK, "Description",
				_getDefaultLanguageId());
		}

		if (kaleoProcess != null) {
			return kaleoProcess.getDescription();
		}

		return StringPool.BLANK;
	}

	/**
	 * Returns the Kaleo process's name.
	 *
	 * @param  kaleoProcess the Kaleo process
	 * @param  portletSession the portlet session obtained from the request
	 * @return the Kaleo process's name
	 * @throws Exception if an exception occurred
	 */
	public static String getKaleoProcessName(
			KaleoProcess kaleoProcess, PortletSession portletSession)
		throws Exception {

		Map<Locale, String> nameMap = new HashMap<>();

		String translatedLanguagesName = GetterUtil.getString(
			portletSession.getAttribute("translatedLanguagesName"));

		for (String translatedLanguageId :
				StringUtil.split(translatedLanguagesName)) {

			String name = GetterUtil.getString(
				portletSession.getAttribute("name" + translatedLanguageId));

			Locale locale = LocaleUtil.fromLanguageId(translatedLanguageId);

			nameMap.put(locale, name);
		}

		if (!nameMap.isEmpty()) {
			return LocalizationUtil.updateLocalization(
				nameMap, StringPool.BLANK, "Name", _getDefaultLanguageId());
		}

		if (kaleoProcess != null) {
			return kaleoProcess.getName();
		}

		return StringPool.BLANK;
	}

	/**
	 * Returns the Kaleo process's name associated with the locale.
	 *
	 * @param  kaleoProcess the kaleo process
	 * @param  portletSession the portlet session obtained from the request
	 * @param  locale the locale of the returned name
	 * @return the Kaleo process's name
	 * @throws Exception if an exception occurred
	 */
	public static String getKaleoProcessName(
			KaleoProcess kaleoProcess, PortletSession portletSession,
			Locale locale)
		throws Exception {

		String defaultName = GetterUtil.getString(
			portletSession.getAttribute("name" + _getDefaultLanguageId()));

		String languageId = LocaleUtil.toLanguageId(locale);

		String name = GetterUtil.getString(
			portletSession.getAttribute("name" + languageId), defaultName);

		if (Validator.isNotNull(name)) {
			return name;
		}

		if (kaleoProcess != null) {
			return kaleoProcess.getName(locale);
		}

		return StringPool.BLANK;
	}

	/**
	 * Returns the Kaleo task form pairs.
	 *
	 * @param  companyId the company ID
	 * @param  kaleoProcessId the primary key of the Kaleo process
	 * @param  ddmStructureId the primary key of the record set's DDM structure
	 * @param  workflowDefinition the workflow definition
	 * @param  portletSession the portlet session obtained from the request
	 * @return the Kaleo task form pairs
	 * @throws Exception if an exception occurred
	 */
	public static KaleoTaskFormPairs getKaleoTaskFormPairs(
			long companyId, long kaleoProcessId, long ddmStructureId,
			String workflowDefinition, PortletSession portletSession)
		throws Exception {

		KaleoTaskFormPairs kaleoKaleoTaskFormPairs = new KaleoTaskFormPairs();

		for (String taskName : _getTaskNames(companyId, workflowDefinition)) {
			long ddmTemplateId = _getDDMTemplateId(
				kaleoProcessId, ddmStructureId, workflowDefinition, taskName,
				portletSession);

			KaleoTaskFormPair kaleoKaleoTaskFormPair = new KaleoTaskFormPair(
				taskName, ddmTemplateId);

			kaleoKaleoTaskFormPairs.add(kaleoKaleoTaskFormPair);
		}

		return kaleoKaleoTaskFormPairs;
	}

	/**
	 * Returns the Kaleo process's workflow definition.
	 *
	 * @param  kaleoProcess the Kaleo process
	 * @param  portletSession the portlet session obtained from the request
	 * @return the Kaleo process's workflow definition
	 */
	public static String getWorkflowDefinition(
		KaleoProcess kaleoProcess, PortletSession portletSession) {

		String workflowDefinition = GetterUtil.getString(
			portletSession.getAttribute("workflowDefinition"));

		if (Validator.isNotNull(workflowDefinition)) {
			return workflowDefinition;
		}

		if (kaleoProcess != null) {
			workflowDefinition = kaleoProcess.getWorkflowDefinition();
		}

		return workflowDefinition;
	}

	/**
	 * Returns the workflow definition.
	 *
	 * @param  companyId the company ID
	 * @param  name the workflow definition's name
	 * @param  version the workflow definition's version
	 * @return the workflow definition
	 */
	public static WorkflowDefinition getWorkflowDefinition(
		long companyId, String name, int version) {

		try {
			return WorkflowDefinitionManagerUtil.getWorkflowDefinition(
				companyId, name, version);
		}
		catch (Exception exception) {
			return null;
		}
	}

	/**
	 * Returns <code>true</code> if the workflow definition is active.
	 *
	 * @param  companyId the company ID
	 * @param  name the workflow definition's name
	 * @param  version the workflow definition's version
	 * @return <code>true</code> if the workflow definition is active;
	 *         <code>false</code> otherwise
	 */
	public static boolean isWorkflowDefinitionActive(
		long companyId, String name, int version) {

		WorkflowDefinition workflowDefinition = getWorkflowDefinition(
			companyId, name, version);

		if (workflowDefinition != null) {
			return workflowDefinition.isActive();
		}

		return false;
	}

	private static void _addTaskNames(Element element, List<String> taskNames) {
		for (Element taskElement : element.elements("task")) {
			taskNames.add(taskElement.elementTextTrim("name"));

			_addTaskNames(taskElement, taskNames);
		}
	}

	private static long _getDDMTemplateId(
			long kaleoProcessId, long ddmStructureId, String workflowDefinition,
			String taskName, PortletSession portletSession)
		throws Exception {

		String taskSessionKey = _getTaskSessionKey(
			ddmStructureId, workflowDefinition, taskName);

		long ddmTemplateId = 0;

		if (portletSession.getAttribute(taskSessionKey) != null) {
			ddmTemplateId = GetterUtil.getLong(
				portletSession.getAttribute(taskSessionKey));
		}
		else if (kaleoProcessId > 0) {
			KaleoProcess kaleoProcess = KaleoProcessServiceUtil.getKaleoProcess(
				kaleoProcessId);

			DDLRecordSet ddlRecordSet = kaleoProcess.getDDLRecordSet();

			String kaleoProcessWorkflowDefinition =
				kaleoProcess.getWorkflowDefinition();

			if ((ddlRecordSet.getDDMStructureId() == ddmStructureId) &&
				kaleoProcessWorkflowDefinition.equals(workflowDefinition)) {

				KaleoProcessLink kaleoProcessLink =
					KaleoProcessLinkLocalServiceUtil.fetchKaleoProcessLink(
						kaleoProcessId, taskName);

				if (kaleoProcessLink != null) {
					ddmTemplateId = kaleoProcessLink.getDDMTemplateId();
				}
			}
		}

		return ddmTemplateId;
	}

	private static String _getDefaultLanguageId() {
		Locale defaultLocale = LocaleUtil.getSiteDefault();

		return LocaleUtil.toLanguageId(defaultLocale);
	}

	private static String _getInitalStateName(Element rootElement) {
		for (Element element : rootElement.elements("state")) {
			boolean initial = GetterUtil.getBoolean(
				element.elementTextTrim("initial"));

			if (initial) {
				return element.elementTextTrim("name");
			}
		}

		return null;
	}

	private static List<String> _getTaskNames(Element rootElement) {
		List<String> taskNames = new ArrayList<>();

		_addTaskNames(rootElement, taskNames);

		return taskNames;
	}

	private static List<String> _getTaskNames(
			long companyId, String workflowDefinition)
		throws Exception {

		if (Validator.isNull(workflowDefinition)) {
			return Collections.emptyList();
		}

		String[] workflowDefinitionParts = StringUtil.split(
			workflowDefinition, CharPool.AT);

		String workflowDefinitionName = workflowDefinitionParts[0];
		int workflowDefinitionVersion = GetterUtil.getInteger(
			workflowDefinitionParts[1]);

		Document document = _getWorkflowDefinitionDocument(
			companyId, workflowDefinitionName, workflowDefinitionVersion);

		return _getTaskNames(document.getRootElement());
	}

	private static String _getTaskSessionKey(
		long ddmStructureId, String workflowDefinition, String taskName) {

		StringBundler sb = new StringBundler(3);

		sb.append(ddmStructureId);
		sb.append(workflowDefinition);
		sb.append(taskName);

		return sb.toString();
	}

	private static Document _getWorkflowDefinitionDocument(
			long companyId, String workflowDefinitionName,
			int workflowDefinitionVersion)
		throws Exception {

		WorkflowDefinition workflowDefinition =
			WorkflowDefinitionManagerUtil.getWorkflowDefinition(
				companyId, workflowDefinitionName, workflowDefinitionVersion);

		return SAXReaderUtil.read(workflowDefinition.getContent());
	}

}