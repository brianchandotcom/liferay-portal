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

package com.liferay.polls.exportimport.portlet.preferences.processor;

import com.liferay.exportimport.portlet.preferences.processor.Capability;
import com.liferay.exportimport.portlet.preferences.processor.ExportImportPortletPreferencesProcessor;
import com.liferay.exportimport.portlet.preferences.processor.capability.ReferencedStagedModelImporterCapability;
import com.liferay.polls.constants.PollsPortletKeys;
import com.liferay.polls.exception.NoSuchQuestionException;
import com.liferay.polls.lar.PollsPortletDataHandler;
import com.liferay.polls.model.PollsChoice;
import com.liferay.polls.model.PollsQuestion;
import com.liferay.polls.model.PollsVote;
import com.liferay.polls.service.permission.PollsResourcePermissionChecker;
import com.liferay.polls.service.persistence.PollsQuestionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataException;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + PollsPortletKeys.POLLS_DISPLAY},
	service = ExportImportPortletPreferencesProcessor.class
)
public class PollsDisplayExportImportPortletPreferencesProcessor
	implements ExportImportPortletPreferencesProcessor {

	@Override
	public List<Capability> getExportCapabilities() {
		return null;
	}

	@Override
	public List<Capability> getImportCapabilities() {
		return ListUtil.toList(
			new Capability[] {_referencedStagedModelImporterCapability});
	}

	@Override
	public PortletPreferences processExportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		String portletId = portletDataContext.getPortletId();

		long questionId = GetterUtil.getLong(
			portletPreferences.getValue("questionId", StringPool.BLANK));

		if (questionId <= 0) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No question ID found in preferences of portlet " +
						portletId);
			}

			return portletPreferences;
		}

		PollsQuestion question = null;

		try {
			question = PollsQuestionUtil.findByPrimaryKey(questionId);
		}
		catch (NoSuchQuestionException nsqe) {
			if (_log.isWarnEnabled()) {
				_log.warn(nsqe, nsqe);
			}

			return portletPreferences;
		}

		try {
			portletDataContext.addPortletPermissions(
				PollsResourcePermissionChecker.RESOURCE_NAME);
		}
		catch (PortalException pe) {
			throw new PortletDataException(
				"Unable to export portlet permissions", pe);
		}

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, portletId, question);

		for (PollsChoice choice : question.getChoices()) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, portletId, choice);
		}

		if (portletDataContext.getBooleanParameter(
				PollsPortletDataHandler.NAMESPACE, "votes")) {

			for (PollsVote vote : question.getVotes()) {
				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, portletId, vote);
			}
		}

		return portletPreferences;
	}

	@Override
	public PortletPreferences processImportPortletPreferences(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		try {
			portletDataContext.importPortletPermissions(
				PollsResourcePermissionChecker.RESOURCE_NAME);
		}
		catch (PortalException pe) {
			throw new PortletDataException(
				"Unable to import portlet permissions", pe);
		}

		long questionId = GetterUtil.getLong(
			portletPreferences.getValue("questionId", StringPool.BLANK));

		if (questionId > 0) {
			Map<Long, Long> questionIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					PollsQuestion.class);

			questionId = MapUtil.getLong(questionIds, questionId, questionId);

			try {
				portletPreferences.setValue(
					"questionId", String.valueOf(questionId));
			}
			catch (ReadOnlyException roe) {
				throw new PortletDataException(
					"Unable to update portlet preferences during import", roe);
			}
		}

		return portletPreferences;
	}

	@Reference(unbind = "-")
	protected void setReferencedStagedModelImporterCapability(
		ReferencedStagedModelImporterCapability
			referencedStagedModelImporterCapability) {

		_referencedStagedModelImporterCapability =
			referencedStagedModelImporterCapability;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PollsDisplayExportImportPortletPreferencesProcessor.class);

	private volatile ReferencedStagedModelImporterCapability
		_referencedStagedModelImporterCapability;

}