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

package com.liferay.user.associated.data.web.internal.portlet.action;

import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.constants.UserAssociatedDataPortletKeys;
import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.user.associated.data.web.internal.display.UADHierarchyDisplay;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Noah Sherrill
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + UserAssociatedDataPortletKeys.USER_ASSOCIATED_DATA,
		"mvc.command.name=/delete_uad_entities"
	},
	service = MVCActionCommand.class
)
public class DeleteUADEntitiesMVCActionCommand extends BaseUADMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String applicationKey = ParamUtil.getString(
			actionRequest, "applicationKey");

		UADHierarchyDisplay uadHierarchyDisplay =
			uadRegistry.getUADHierarchyDisplay(applicationKey);

		for (String entityType : getEntityTypes(actionRequest)) {
			String[] primaryKeys = getPrimaryKeys(actionRequest, entityType);

			UADAnonymizer entityUADAnonymizer = getUADAnonymizer(
				actionRequest, entityType);
			UADDisplay<?> entityUADDisplay = getUADDisplay(
				actionRequest, entityType);

			for (String primaryKey : primaryKeys) {
				_delete(
					entityUADAnonymizer, entityUADDisplay, primaryKey,
					getSelectedUserId(actionRequest), uadHierarchyDisplay);
			}
		}

		doReviewableRedirect(actionRequest, actionResponse);
	}

	private void _delete(
			UADAnonymizer entityUADAnonymizer, UADDisplay<?> entityUADDisplay,
			String primaryKey, long selectedUserId,
			UADHierarchyDisplay uadHierarchyDisplay)
		throws Exception {

		Object entity = entityUADDisplay.get(primaryKey);

		if (uadHierarchyDisplay != null) {
			if (uadHierarchyDisplay.isUserOwned(entity, selectedUserId)) {
				entityUADAnonymizer.delete(entity);
			}
			else {
				Map<Class<?>, List<Serializable>> containerItemPKsMap =
					uadHierarchyDisplay.getContainerItemPKsMap(
						entityUADDisplay.getTypeClass(),
						uadHierarchyDisplay.getPrimaryKey(entity),
						selectedUserId);

				for (Map.Entry<Class<?>, List<Serializable>> entry :
						containerItemPKsMap.entrySet()) {

					Class<?> containerItemClass = entry.getKey();

					UADAnonymizer containerItemUADAnonymizer =
						uadRegistry.getUADAnonymizer(
							containerItemClass.getName());
					UADDisplay containerItemUADDisplay =
						uadRegistry.getUADDisplay(containerItemClass.getName());

					doMultipleAction(
						entry.getValue(),
						containerItemPK -> {
							try {
								Object containerItem =
									containerItemUADDisplay.get(
										containerItemPK);

								containerItemUADAnonymizer.delete(
									containerItem);
							}
							catch (NoSuchModelException nsme) {
								if (_log.isDebugEnabled()) {
									_log.debug(nsme, nsme);
								}
							}
						});
				}
			}
		}
		else {
			entityUADAnonymizer.delete(entity);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DeleteUADEntitiesMVCActionCommand.class);

}