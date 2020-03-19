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

package com.liferay.exportimport.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryRegistryUtil;
import com.liferay.exportimport.test.util.constants.DummyFolderPortletKeys;
import com.liferay.exportimport.test.util.lar.BasePortletExportImportTestCase;
import com.liferay.exportimport.test.util.model.DummyFolder;
import com.liferay.exportimport.test.util.model.util.DummyFolderTestUtil;
import com.liferay.portal.dao.orm.hibernate.DynamicQueryFactoryImpl;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Date;
import java.util.Map;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Akos Thurzo
 */
@RunWith(Arquillian.class)
public class DummyFolderExportImportTest
	extends BasePortletExportImportTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	public String getPortletId() throws Exception {
		return DummyFolderPortletKeys.DUMMY_FOLDER;
	}

	@Before
	@Override
	public void setUp() throws Exception {
		UserTestUtil.setUser(TestPropsValues.getUser());

		super.setUp();

		stagedModelRepository =
			(StagedModelRepository<DummyFolder>)
				StagedModelRepositoryRegistryUtil.getStagedModelRepository(
					DummyFolder.class.getName());

		DynamicQueryFactoryUtil dynamicQueryFactoryUtil =
			new DynamicQueryFactoryUtil();

		dynamicQueryFactoryUtil.setDynamicQueryFactory(
			new DynamicQueryFactoryImpl() {

				@Override
				protected Class<?> getImplClass(
					Class<?> clazz, ClassLoader classLoader) {

					if (clazz.equals(DummyFolder.class)) {
						return DummyFolder.class;
					}

					return super.getImplClass(clazz, classLoader);
				}

			});
	}

	@Override
	public void testExportImportAssetLinks() throws Exception {
	}

	@Override
	protected StagedModel addStagedModel(long groupId) throws Exception {
		return stagedModelRepository.addStagedModel(
			null, DummyFolderTestUtil.createDummyFolder(groupId));
	}

	@Override
	protected StagedModel addStagedModel(long groupId, Date createdDate)
		throws Exception {

		return stagedModelRepository.addStagedModel(
			null, DummyFolderTestUtil.createDummyFolder(groupId, createdDate));
	}

	@Override
	protected void deleteStagedModel(StagedModel stagedModel) throws Exception {
		stagedModelRepository.deleteStagedModel((DummyFolder)stagedModel);
	}

	@Override
	protected Map<String, String[]> getExportParameterMap() throws Exception {
		return HashMapBuilder.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_SETUP_ALL,
			new String[] {Boolean.TRUE.toString()}
		).build();
	}

	@Override
	protected Map<String, String[]> getImportParameterMap() throws Exception {
		return getExportParameterMap();
	}

	@Override
	protected StagedModel getStagedModel(String uuid, long groupId)
		throws PortalException {

		return stagedModelRepository.fetchStagedModelByUuidAndGroupId(
			uuid, groupId);
	}

	protected StagedModelRepository<DummyFolder> stagedModelRepository;

}