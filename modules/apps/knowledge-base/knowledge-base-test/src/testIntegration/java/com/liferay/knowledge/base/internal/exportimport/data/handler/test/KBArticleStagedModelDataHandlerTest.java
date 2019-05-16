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

package com.liferay.knowledge.base.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.test.util.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.knowledge.base.constants.KBArticleConstants;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 */
@RunWith(Arquillian.class)
public class KBArticleStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@Test
	public void testMovingKBArticleUpdatesParentResourcePrimKey()
		throws Exception {

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			addDependentStagedModelsMap(stagingGroup);

		StagedModel stagedModel = addStagedModel(
			stagingGroup, dependentStagedModelsMap);

		exportImportStagedModel(stagedModel);

		KBArticle kbArticle = (KBArticle)stagedModel;

		KBArticleLocalServiceUtil.moveKBArticle(
			TestPropsValues.getUserId(), kbArticle.getResourcePrimKey(),
			ClassNameLocalServiceUtil.getClassNameId(
				KBFolderConstants.getClassName()),
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			KBArticleConstants.DEFAULT_PRIORITY);

		exportImportStagedModel(
			getStagedModel(kbArticle.getUuid(), stagingGroup));

		KBArticle importedKBArticle = (KBArticle)getStagedModel(
			kbArticle.getUuid(), liveGroup);

		Assert.assertEquals(
			ClassNameLocalServiceUtil.getClassNameId(
				KBFolderConstants.getClassName()),
			importedKBArticle.getParentResourceClassNameId());
		Assert.assertEquals(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			importedKBArticle.getParentResourcePrimKey());
	}

	@Override
	protected Map<String, List<StagedModel>> addDependentStagedModelsMap(
			Group group)
		throws Exception {

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new HashMap<>();

		addDependentStagedModel(
			dependentStagedModelsMap, KBArticle.class,
			_addKBArticle(
				KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				ClassNameLocalServiceUtil.getClassNameId(
					KBFolderConstants.getClassName()),
				_createServiceContext(group)));

		return dependentStagedModelsMap;
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		List<StagedModel> stagedModels = dependentStagedModelsMap.get(
			KBArticle.class.getSimpleName());

		KBArticle kbArticle = (KBArticle)stagedModels.get(0);

		return _addKBArticle(
			kbArticle.getResourcePrimKey(),
			ClassNameLocalServiceUtil.getClassNameId(
				KBArticleConstants.getClassName()),
			_createServiceContext(group));
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group)
		throws PortalException {

		return KBArticleLocalServiceUtil.getKBArticleByUuidAndGroupId(
			uuid, group.getGroupId());
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return KBArticle.class;
	}

	private KBArticle _addKBArticle(
			long parentResourcePrimKey, long parentResourceClassNameId,
			ServiceContext serviceContext)
		throws PortalException {

		return KBArticleLocalServiceUtil.addKBArticle(
			serviceContext.getUserId(), parentResourceClassNameId,
			parentResourcePrimKey, StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), null, null, null, serviceContext);
	}

	private ServiceContext _createServiceContext(Group group)
		throws PortalException {

		return ServiceContextTestUtil.getServiceContext(group.getGroupId());
	}

}