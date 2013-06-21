/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.lar;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThreadFlag;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadFlagLocalServiceUtil;
import com.liferay.portlet.messageboards.util.MBTestUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.runner.RunWith;

/**
 * @author Daniel Kocsis
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class MBThreadFlagStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@Override
	protected Map<String, List<StagedModel>> addDependentStagedModelsMap(
			Group group)
		throws Exception {

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new HashMap<String, List<StagedModel>>();

		MBMessage message = MBTestUtil.addMessage(group.getGroupId());

		addDependentStagedModel(
			dependentStagedModelsMap, MBMessage.class, message);

		return dependentStagedModelsMap;
	}

	@Override
	protected StagedModel addStagedModel(
			Group group, Map<String,
			List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			MBMessage.class.getSimpleName());

		MBMessage message = (MBMessage)dependentStagedModels.get(0);

		return MBTestUtil.addThreadFlag(
			group.getGroupId(), message.getThread());
	}

	@Override
	protected void deleteStagedModel(
			StagedModel stagedModel,
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		MBThreadFlagLocalServiceUtil.deleteThreadFlag(
			(MBThreadFlag)stagedModel);

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			MBMessage.class.getSimpleName());

		MBMessage message = (MBMessage)dependentStagedModels.get(0);

		MBMessageLocalServiceUtil.deleteMessage(message);
	}

	@Override
	protected StagedModelType[] getDeletionSystemEventStagedModelTypes() {
		MBPortletDataHandler mbPortletDataHandler = new MBPortletDataHandler();

		return mbPortletDataHandler.getDeletionSystemEventStagedModelTypes();
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group)
		throws SystemException {

		return MBThreadFlagLocalServiceUtil.fetchMBThreadFlagByUuidAndGroupId(
			uuid, group.getGroupId());
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return MBThreadFlag.class;
	}

	@Override
	protected void validateDeletion(
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			MBMessage.class.getSimpleName());

		Assert.assertEquals(1, dependentStagedModels.size());

		MBMessage message = (MBMessage)dependentStagedModels.get(0);

		message = MBMessageLocalServiceUtil.fetchMBMessageByUuidAndGroupId(
			message.getUuid(), group.getGroupId());

		Assert.assertNull("Not Deleted: " + MBMessage.class, message);
	}

	@Override
	protected void validateImport(
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			MBMessage.class.getSimpleName());

		Assert.assertEquals(1, dependentStagedModels.size());

		MBMessage message = (MBMessage)dependentStagedModels.get(0);

		MBMessageLocalServiceUtil.getMBMessageByUuidAndGroupId(
			message.getUuid(), group.getGroupId());
	}

	@Override
	protected void validateImport(
			StagedModel stagedModel,
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		super.validateImport(stagedModel, dependentStagedModelsMap, group);

		MBThreadFlag importedThreadFlag = (MBThreadFlag)getStagedModel(
			stagedModel.getUuid(), group);

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			MBMessage.class.getSimpleName());

		MBMessage message = (MBMessage)dependentStagedModels.get(0);

		MBMessage importedMessage =
			MBMessageLocalServiceUtil.fetchMBMessageByUuidAndGroupId(
				message.getUuid(), group.getGroupId());

		Assert.assertTrue(
			MBThreadFlagLocalServiceUtil.hasThreadFlag(
				importedThreadFlag.getUserId(), importedMessage.getThread()));
	}

}