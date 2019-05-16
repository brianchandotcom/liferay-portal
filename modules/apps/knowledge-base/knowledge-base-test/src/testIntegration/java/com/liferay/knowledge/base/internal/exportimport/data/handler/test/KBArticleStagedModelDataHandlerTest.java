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
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;
import java.util.Map;

import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 */
@RunWith(Arquillian.class)
public class KBArticleStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		ServiceContext serviceContext = _createServiceContext(group);

		return KBArticleLocalServiceUtil.addKBArticle(
			serviceContext.getUserId(),
			ClassNameLocalServiceUtil.getClassNameId(
				KBFolderConstants.getClassName()),
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(), null, null,
			null, serviceContext);
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

	private ServiceContext _createServiceContext(Group group)
		throws PortalException {

		return ServiceContextTestUtil.getServiceContext(group.getGroupId());
	}

}