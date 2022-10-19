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

package com.liferay.change.tracking.web.internal.configuration.persistence.listener;

import com.liferay.change.tracking.configuration.CTSettingsConfiguration;
import com.liferay.change.tracking.exception.CTStagingEnabledException;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.model.CTPreferencesTable;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.change.tracking.web.internal.scheduler.PublishScheduler;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PropsValues;

import java.util.Dictionary;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author David Truong
 */
@Component(
	property = "model.class.name=com.liferay.change.tracking.configuration.CTSettingsConfiguration",
	service = ConfigurationModelListener.class
)
public class CTSettingsConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onAfterSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		boolean enabled = GetterUtil.getBoolean(properties.get("enabled"));

		if (!enabled) {
			try {
				long companyId = GetterUtil.getLong(
					properties.get("companyId"));

				_cleanUpCTPreferences(companyId);

				_cleanUpScheduledCTCollections(companyId);
			}
			catch (PortalException portalException) {
				throw new ConfigurationModelListenerException(
					portalException, CTSettingsConfiguration.class, getClass(),
					properties);
			}
		}
	}

	@Override
	public void onBeforeSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		boolean enabled = GetterUtil.getBoolean(properties.get("enabled"));
		boolean sandboxEnabled = GetterUtil.getBoolean(
			properties.get("sandboxEnabled"));

		if (!enabled && sandboxEnabled) {
			properties.put("sandboxEnabled", false);
		}

		if (enabled) {
			try {
				long companyId = GetterUtil.getLong(
					properties.get("companyId"));

				_checkStagingEnabled(companyId);
			}
			catch (PortalException portalException) {
				throw new ConfigurationModelListenerException(
					portalException, CTSettingsConfiguration.class, getClass(),
					properties);
			}
		}
	}

	private void _checkStagingEnabled(long companyId) throws PortalException {
		for (Group group :
				_groupLocalService.<List<Group>>dslQuery(
					DSLQueryFactoryUtil.select(
						GroupTable.INSTANCE
					).from(
						GroupTable.INSTANCE
					).where(
						GroupTable.INSTANCE.companyId.eq(
							companyId
						).and(
							GroupTable.INSTANCE.liveGroupId.neq(
								GroupConstants.DEFAULT_LIVE_GROUP_ID
							).or(
								GroupTable.INSTANCE.typeSettings.like(
									"%staged=true%")
							).or(
								GroupTable.INSTANCE.remoteStagingGroupCount.gt(
									0)
							).withParentheses()
						)
					))) {

			if (group.hasRemoteStagingGroup() || group.isStaged() ||
				group.isStagingGroup()) {

				throw new CTStagingEnabledException();
			}
		}
	}

	private void _cleanUpCTPreferences(long companyId) {
		for (CTPreferences ctPreferences :
				_ctPreferencesLocalService.<List<CTPreferences>>dslQuery(
					DSLQueryFactoryUtil.select(
						CTPreferencesTable.INSTANCE
					).from(
						CTPreferencesTable.INSTANCE
					).where(
						CTPreferencesTable.INSTANCE.companyId.eq(companyId)
					))) {

			_ctPreferencesLocalService.deleteCTPreferences(ctPreferences);
		}
	}

	private void _cleanUpScheduledCTCollections(long companyId)
		throws PortalException {

		if (PropsValues.SCHEDULER_ENABLED) {
			List<CTCollection> ctCollections =
				_ctCollectionLocalService.getCTCollections(
					companyId, WorkflowConstants.STATUS_SCHEDULED,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

			for (CTCollection ctCollection : ctCollections) {
				_publishScheduler.unschedulePublish(
					ctCollection.getCtCollectionId());
			}
		}
	}

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTPreferencesLocalService _ctPreferencesLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile PublishScheduler _publishScheduler;

}