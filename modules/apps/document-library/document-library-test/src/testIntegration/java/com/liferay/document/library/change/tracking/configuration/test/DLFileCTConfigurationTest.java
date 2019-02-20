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

package com.liferay.document.library.change.tracking.configuration.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.configuration.CTConfiguration;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.Function;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luiz Marins
 */
@RunWith(Arquillian.class)
public class DLFileCTConfigurationTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		_group = GroupTestUtil.addGroup();

		_fileEntry = _addFileEntry();

		_fileVersion = _fileEntry.getFileVersion();

		_ctConfiguration = _getCTConfiguration();
	}

	@Test
	public void testGetResourceEntityByResourceEntityIdFunction() {
		DLFileEntry resourceEntity =
			(DLFileEntry)_ctConfiguration.
				getResourceEntityByResourceEntityIdFunction(
				).apply(
					_fileEntry.getFileEntryId()
				);

		Assert.assertEquals(
			"Resource entity by resource entity ID is invalid", _fileEntry,
			resourceEntity);
	}

	@Test
	public void testGetResourceEntityClass() {
		Class<?> resourceClass = _ctConfiguration.getResourceEntityClass();

		Assert.assertEquals(
			"Resource entity class is invalid", _fileEntry.getModelClass(),
			resourceClass);
	}

	@Test
	public void testGetResourceEntityIdFromResourceEntityFunction() {
		Function<DLFileEntry, Serializable>
			resourceEntityIdFromResourceEntityFunction =
				(Function<DLFileEntry, Serializable>)_ctConfiguration.
					getResourceEntityIdFromResourceEntityFunction();

		long resourceEntityId =
			(long)resourceEntityIdFromResourceEntityFunction.apply(_fileEntry);

		Assert.assertEquals(
			"Resource entity ID by resource entity is invalid",
			_fileEntry.getFileEntryId(), resourceEntityId);
	}

	@Test
	public void testGetResourceEntityIdFromVersionEntityFunction() {
		Function<DLFileVersion, Serializable>
			resourceEntityIdFromVersionEntityFunction =
				(Function<DLFileVersion, Serializable>)_ctConfiguration.
					getResourceEntityIdFromVersionEntityFunction();

		long resourceEntityId =
			(long)resourceEntityIdFromVersionEntityFunction.apply(_fileVersion);

		Assert.assertEquals(
			"Resource entity ID by version entity is invalid",
			_fileEntry.getFileEntryId(), resourceEntityId);
	}

	@Test
	public void testGetVersionEntityByVersionEntityIdFunction() {
		DLFileVersion versionEntity =
			(DLFileVersion)_ctConfiguration.
				getVersionEntityByVersionEntityIdFunction(
				).apply(
					_fileVersion.getFileVersionId()
				);

		Assert.assertEquals(
			"Version entity by version entity ID is invalid", _fileVersion,
			versionEntity);
	}

	@Test
	public void testGetVersionEntityClass() {
		Class<DLFileEntry> versionClass =
			(Class<DLFileEntry>)_ctConfiguration.getVersionEntityClass();

		Assert.assertEquals(
			"Version entity class is invalid", _fileVersion.getModelClass(),
			versionClass);
	}

	@Test
	public void testGetVersionEntityIdFromResourceEntityFunction() {
		Function<DLFileEntry, Serializable>
			versionEntityIdFromResourceEntityFunction =
				(Function<DLFileEntry, Serializable>)_ctConfiguration.
					getVersionEntityIdFromResourceEntityFunction();

		long versionEntityId =
			(long)versionEntityIdFromResourceEntityFunction.apply(_fileEntry);

		Assert.assertEquals(
			"Version entity ID by resource entity is invalid",
			_fileVersion.getFileVersionId(), versionEntityId);
	}

	@Test
	public void testGetVersionEntityIdFromVersionEntityFunction() {
		Function<DLFileVersion, Serializable>
			versionEntityIdFromVersionEntityFunction =
				(Function<DLFileVersion, Serializable>)_ctConfiguration.
					getVersionEntityIdFromVersionEntityFunction();

		long versionEntityId =
			(long)versionEntityIdFromVersionEntityFunction.apply(_fileVersion);

		Assert.assertEquals(
			"Version entity ID by version entity is invalid",
			_fileVersion.getFileVersionId(), versionEntityId);
	}

	@Test
	public void testGetVersionEntityStatusFunction() {
		Function<DLFileVersion, Integer> versionEntityStatusFunction =
			(Function<DLFileVersion, Integer>)_ctConfiguration.
				getVersionEntityStatusFunction();

		int versionEntityStatus = versionEntityStatusFunction.apply(
			_fileVersion);

		Assert.assertEquals(
			"Version entity status is invalid", _fileVersion.getStatus(),
			versionEntityStatus);
	}

	private DLFileEntry _addFileEntry() throws Exception {
		String content = StringUtil.randomString();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		return _dlFileEntryLocalService.addFileEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"file.txt", ContentTypes.TEXT_PLAIN, "file.txt", StringPool.BLANK,
			StringPool.BLANK, -1, new HashMap<>(), null,
			new ByteArrayInputStream(content.getBytes()), 0, serviceContext);
	}

	private CTConfiguration<?, ?> _getCTConfiguration() throws Exception {
		Optional<CTConfiguration<?, ?>> ctConfigurationOptional =
			_getCTConfigurationOptional();

		Assert.assertTrue(
			"Change tracking configuration is not registered",
			ctConfigurationOptional.isPresent());

		return ctConfigurationOptional.get();
	}

	private Optional<CTConfiguration<?, ?>> _getCTConfigurationOptional()
		throws Exception {

		Registry registry = RegistryUtil.getRegistry();

		Object[] objects = registry.getServices(
			"com.liferay.change.tracking.configuration.CTConfigurationRegistry",
			null);

		Object ctConfigurationRegistry = objects[0];

		return ReflectionTestUtil.invoke(
			ctConfigurationRegistry,
			"getCTConfigurationOptionalByVersionClassName",
			new Class<?>[] {String.class},
			new String[] {DLFileVersion.class.getName()});
	}

	private CTConfiguration<?, ?> _ctConfiguration;

	@Inject
	private DLFileEntryLocalService _dlFileEntryLocalService;

	private DLFileEntry _fileEntry;
	private DLFileVersion _fileVersion;

	@DeleteAfterTestRun
	private Group _group;

}