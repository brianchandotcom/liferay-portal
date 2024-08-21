/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.messaging;

import com.liferay.osb.faro.constants.FaroProjectConstants;
import com.liferay.osb.faro.engine.client.CerebroEngineClient;
import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.provisioning.client.ProvisioningClient;
import com.liferay.osb.faro.provisioning.client.constants.ProductConstants;
import com.liferay.osb.faro.provisioning.client.model.OSBAccountEntry;
import com.liferay.osb.faro.provisioning.client.model.OSBOfferingEntry;
import com.liferay.osb.faro.service.FaroProjectLocalService;
import com.liferay.osb.faro.web.internal.constants.FaroMessageDestinationNames;
import com.liferay.osb.faro.web.internal.messaging.destination.creator.DestinationCreator;
import com.liferay.osb.faro.web.internal.model.display.main.FaroSubscriptionDisplay;
import com.liferay.osb.faro.web.internal.util.JSONUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.Date;
import java.util.Objects;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Rachael Koestartyo
 */
@Component(
	property = "destination.name=" + FaroMessageDestinationNames.FARO_UPDATE_FARO_PROJECT_SUBSCRIPTIONS_MESSAGE_PROCESSOR,
	service = MessageListener.class
)
public class UpdateFaroProjectSubscriptionsMessageListener
	extends BaseMessageListener {

	@Activate
	protected void activate(BundleContext bundleContext) {
		try {
			_destinationCreator.createDestination(
				bundleContext, _destinationFactory,
				FaroMessageDestinationNames.
					FARO_UPDATE_FARO_PROJECT_SUBSCRIPTIONS_MESSAGE_PROCESSOR);

			Class<?> clazz = getClass();

			_trigger = _triggerFactory.createTrigger(
				clazz.getName(), clazz.getName(), new Date(), null,
				"0 0 0 * * ?");

			_schedulerEngineHelper.schedule(
				_trigger, StorageType.PERSISTED, null,
				FaroMessageDestinationNames.
					FARO_UPDATE_FARO_PROJECT_SUBSCRIPTIONS_MESSAGE_PROCESSOR,
				null);
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	@Deactivate
	protected void deactivate() {
		try {
			if (_destinationCreator != null) {
				_destinationCreator.removeDestination();

				_destinationCreator = null;
			}

			if (_trigger == null) {
				return;
			}

			_schedulerEngineHelper.delete(
				_trigger.getJobName(), _trigger.getGroupName(),
				StorageType.PERSISTED);
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		for (FaroProject faroProject :
				_faroProjectLocalService.getFaroProjects(
					QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {

			OSBAccountEntry osbAccountEntry = null;

			if (Validator.isNull(faroProject.getCorpProjectUuid())) {
				Date createDate = new Date(faroProject.getCreateTime());

				osbAccountEntry = new OSBAccountEntry() {
					{
						OSBOfferingEntry osbOfferingEntry =
							new OSBOfferingEntry();

						osbOfferingEntry.setProductEntryId(
							ProductConstants.BASIC_PRODUCT_ENTRY_ID);
						osbOfferingEntry.setQuantity(1);
						osbOfferingEntry.setStartDate(createDate);

						setOfferingEntries(
							Collections.singletonList(osbOfferingEntry));
					}
				};
			}
			else {
				osbAccountEntry = _provisioningClient.getOSBAccountEntry(
					faroProject.getCorpProjectUuid());
			}

			FaroSubscriptionDisplay faroSubscriptionDisplay =
				new FaroSubscriptionDisplay(osbAccountEntry);

			try {
				if (Objects.equals(
						faroProject.getState(),
						FaroProjectConstants.STATE_UNAVAILABLE)) {

					faroProject = _faroProjectLocalService.updateState(
						faroProject.getFaroProjectId(),
						FaroProjectConstants.STATE_READY);
				}

				faroSubscriptionDisplay.setCounts(
					faroProject, _cerebroEngineClient, _contactsEngineClient);

				faroProject.setSubscription(
					JSONUtil.writeValueAsString(faroSubscriptionDisplay));

				faroSubscriptionDisplay.setUsageCounts(
					_cerebroEngineClient, _contactsEngineClient, new Date(),
					faroProject);

				_faroProjectLocalService.updateSubscription(
					faroProject.getFaroProjectId(),
					JSONUtil.writeValueAsString(faroSubscriptionDisplay));
			}
			catch (Exception exception) {
				_log.error(exception);

				_faroProjectLocalService.updateState(
					faroProject.getFaroProjectId(),
					FaroProjectConstants.STATE_UNAVAILABLE);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpdateFaroProjectSubscriptionsMessageListener.class);

	@Reference
	private CerebroEngineClient _cerebroEngineClient;

	@Reference
	private ContactsEngineClient _contactsEngineClient;

	private DestinationCreator _destinationCreator = new DestinationCreator();

	@Reference
	private DestinationFactory _destinationFactory;

	@Reference
	private FaroProjectLocalService _faroProjectLocalService;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile ProvisioningClient _provisioningClient;

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	private Trigger _trigger;

	@Reference
	private TriggerFactory _triggerFactory;

}