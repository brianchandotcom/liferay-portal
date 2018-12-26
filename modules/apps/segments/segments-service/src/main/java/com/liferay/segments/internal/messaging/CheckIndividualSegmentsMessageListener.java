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

package com.liferay.segments.internal.messaging;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.segments.configuration.SegmentsServiceConfiguration;
import com.liferay.segments.internal.asah.client.AsahFaroBackendClient;
import com.liferay.segments.internal.asah.client.model.Individual;
import com.liferay.segments.internal.asah.client.model.IndividualSegment;
import com.liferay.segments.internal.asah.client.model.Results;
import com.liferay.segments.internal.asah.client.util.OrderByField;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(
	configurationPid = "com.liferay.journal.configuration.SegmentsServiceConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	service = CheckIndividualSegmentsMessageListener.class
)
public class CheckIndividualSegmentsMessageListener
	extends BaseMessageListener {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		SegmentsServiceConfiguration segmentsServiceConfiguration =
			ConfigurableUtil.createConfigurable(
				SegmentsServiceConfiguration.class, properties);

		Class<?> clazz = getClass();

		String className = clazz.getName();

		Trigger trigger = _triggerFactory.createTrigger(
			className, className, null, null,
			segmentsServiceConfiguration.checkInterval(), TimeUnit.MINUTE);

		SchedulerEntry schedulerEntry = new SchedulerEntryImpl(
			className, trigger);

		_schedulerEngineHelper.register(
			this, schedulerEntry, DestinationNames.SCHEDULER_DISPATCH);
	}

	@Deactivate
	protected void deactivate() {
		_schedulerEngineHelper.unregister(this);
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug(message.toString());
		}

		_checkIndividualSegments();
	}

	@Reference(unbind = "-")
	protected void setAsahFaroBackendClient(
		AsahFaroBackendClient asahFaroBackendClient) {

		_asahFaroBackendClient = asahFaroBackendClient;
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(unbind = "-")
	protected void setSchedulerEngineHelper(
		SchedulerEngineHelper schedulerEngineHelper) {

		_schedulerEngineHelper = schedulerEngineHelper;
	}

	private void _checkIndividual(Individual individual) {
		if (_log.isInfoEnabled()) {
			_log.info("Checking individual : " + individual.getId());
		}

		if (_log.isDebugEnabled()) {
			_log.debug(individual.toString());
		}
	}

	private void _checkIndividualSegmentMembers(
		IndividualSegment individualSegment) {

		if (_log.isInfoEnabled()) {
			_log.info(
				"Checking segment members for: " + individualSegment.getName());
		}

		if (_log.isDebugEnabled()) {
			_log.debug(individualSegment.toString());
		}

		Results<Individual> individualResults =
			_asahFaroBackendClient.getIndividualResults(
				individualSegment.getId(), 1, 10000,
				Collections.singletonList(OrderByField.desc("dateModified")));

		if (_log.isInfoEnabled()) {
			_log.info(
				individualResults.getTotal() + " members found for Segment " +
					individualSegment.getName());
		}

		List<Individual> individuals = individualResults.getItems();

		individuals.forEach(this::_checkIndividual);
	}

	private void _checkIndividualSegments() {
		Results<IndividualSegment> individualSegmentResults =
			_asahFaroBackendClient.getIndividualSegmentResults(
				1, 10000,
				Collections.singletonList(OrderByField.desc("dateModified")));

		if (_log.isInfoEnabled()) {
			_log.info(
				individualSegmentResults.getTotal() + " Active Segments found");
		}

		List<IndividualSegment> individualSegments =
			individualSegmentResults.getItems();

		individualSegments.forEach(this::_checkIndividualSegmentMembers);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CheckIndividualSegmentsMessageListener.class);

	private AsahFaroBackendClient _asahFaroBackendClient;
	private SchedulerEngineHelper _schedulerEngineHelper;

	@Reference
	private TriggerFactory _triggerFactory;

}