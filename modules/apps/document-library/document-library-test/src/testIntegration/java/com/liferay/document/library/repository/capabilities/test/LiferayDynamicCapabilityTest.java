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

package com.liferay.document.library.repository.capabilities.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.capabilities.Capability;
import com.liferay.portal.kernel.repository.event.RepositoryEventAware;
import com.liferay.portal.kernel.repository.event.RepositoryEventType;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.repository.util.LocalRepositoryWrapper;
import com.liferay.portal.repository.util.RepositoryWrapperAware;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.io.File;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class LiferayDynamicCapabilityTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_addRandomFileEntry(
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@Test
	public void testCallCapabilityLocalRepositoryEventListenersOnlyOnce()
		throws Exception {

		AtomicInteger atomicInteger = new AtomicInteger(0);

		Registry registry = RegistryUtil.getRegistry();

		TestRepositoryEventAwareCapability testRepositoryEventAwareCapability =
			repositoryEventRegistry ->
				repositoryEventRegistry.registerRepositoryEventListener(
					RepositoryEventType.Add.class, FileEntry.class,
					fileEntry -> atomicInteger.incrementAndGet());

		ServiceRegistration<Capability> capabilityServiceRegistration =
			registry.registerService(
				Capability.class, testRepositoryEventAwareCapability,
				_getCapabilityProperties("ALL"));

		capabilityServiceRegistration.unregister();

		capabilityServiceRegistration = registry.registerService(
			Capability.class, testRepositoryEventAwareCapability,
			_getCapabilityProperties("ALL"));

		try {
			_addRandomFileEntry(
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

			Assert.assertEquals(1, atomicInteger.get());
		}
		finally {
			capabilityServiceRegistration.unregister();
		}
	}

	@Test
	public void testCallsCapabilityLocalRepositoryEventListeners()
		throws Exception {

		AtomicInteger atomicInteger = new AtomicInteger(0);

		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<Capability> capabilityServiceRegistration =
			registry.registerService(
				Capability.class,
				(TestRepositoryEventAwareCapability)repositoryEventRegistry ->
					repositoryEventRegistry.registerRepositoryEventListener(
						RepositoryEventType.Add.class, FileEntry.class,
						fileEntry -> atomicInteger.incrementAndGet()),
				_getCapabilityProperties("ALL"));

		try {
			_addRandomFileEntry(
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

			Assert.assertEquals(1, atomicInteger.get());
		}
		finally {
			capabilityServiceRegistration.unregister();
		}
	}

	@Test
	public void testCallsCapabilityLocalRepositoryWrapper() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		FileEntry fileEntry1 = _addRandomFileEntry(serviceContext);

		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<Capability> capabilityServiceRegistration =
			registry.registerService(
				Capability.class,
				new TestRepositoryWrapperAwareCapability() {

					@Override
					public LocalRepository wrapLocalRepository(
						LocalRepository localRepository) {

						return new LocalRepositoryWrapper(localRepository) {

							@Override
							public FileEntry addFileEntry(
								String externalReferenceCode, long userId,
								long folderId, String sourceFileName,
								String mimeType, String title,
								String description, String changeLog, File file,
								Date expirationDate, Date reviewDate,
								ServiceContext serviceContext) {

								return fileEntry1;
							}

						};
					}

					@Override
					public Repository wrapRepository(Repository repository) {
						return repository;
					}

				},
				_getCapabilityProperties("ALL"));

		try {
			FileEntry fileEntry2 = _addRandomFileEntry(serviceContext);

			Assert.assertSame(fileEntry1, fileEntry2);
		}
		finally {
			capabilityServiceRegistration.unregister();

			FileEntry fileEntry2 = _addRandomFileEntry(serviceContext);

			Assert.assertNotSame(fileEntry1, fileEntry2);
		}
	}

	@Test
	public void testDoesNotCallCapabilityLocalRepositoryEventListenersForACapabilityWithADifferentRepositoryClassName()
		throws Exception {

		AtomicInteger atomicInteger = new AtomicInteger(0);

		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<Capability> capabilityServiceRegistration =
			registry.registerService(
				Capability.class,
				(TestRepositoryEventAwareCapability)repositoryEventRegistry ->
					repositoryEventRegistry.registerRepositoryEventListener(
						RepositoryEventType.Add.class, FileEntry.class,
						fileEntry -> atomicInteger.incrementAndGet()),
				_getCapabilityProperties("unknown"));

		try {
			_addRandomFileEntry(
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

			Assert.assertEquals(0, atomicInteger.get());
		}
		finally {
			capabilityServiceRegistration.unregister();
		}
	}

	@Test
	public void testDoesNotCallCapabilityLocalRepositoryEventListenersForACapabilityWithNoRepositoryClassName()
		throws Exception {

		AtomicInteger atomicInteger = new AtomicInteger(0);

		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<Capability> capabilityServiceRegistration =
			registry.registerService(
				Capability.class,
				(TestRepositoryEventAwareCapability)repositoryEventRegistry ->
					repositoryEventRegistry.registerRepositoryEventListener(
						RepositoryEventType.Add.class, FileEntry.class,
						fileEntry -> atomicInteger.incrementAndGet()),
				_getCapabilityProperties(null));

		try {
			_addRandomFileEntry(
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

			Assert.assertEquals(0, atomicInteger.get());
		}
		finally {
			capabilityServiceRegistration.unregister();
		}
	}

	@Test
	public void testDoesNotCallCapabilityLocalRepositoryEventListenersWhenTheCapabilityHasNoRepositoryClassName()
		throws Exception {

		AtomicInteger atomicInteger = new AtomicInteger(0);

		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<Capability> capabilityServiceRegistration =
			registry.registerService(
				Capability.class,
				(TestRepositoryEventAwareCapability)repositoryEventRegistry ->
					repositoryEventRegistry.registerRepositoryEventListener(
						RepositoryEventType.Add.class, FileEntry.class,
						fileEntry -> atomicInteger.incrementAndGet()),
				_getCapabilityProperties(null));

		capabilityServiceRegistration.unregister();

		_addRandomFileEntry(
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Assert.assertEquals(0, atomicInteger.get());
	}

	@Test
	public void testDoesNotCallCapabilityLocalRepositoryEventListenersWhenTheCapabilityIsGone()
		throws Exception {

		AtomicInteger atomicInteger = new AtomicInteger(0);

		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<Capability> capabilityServiceRegistration =
			registry.registerService(
				Capability.class,
				(TestRepositoryEventAwareCapability)repositoryEventRegistry ->
					repositoryEventRegistry.registerRepositoryEventListener(
						RepositoryEventType.Add.class, FileEntry.class,
						fileEntry -> atomicInteger.incrementAndGet()),
				_getCapabilityProperties("ALL"));

		capabilityServiceRegistration.unregister();

		_addRandomFileEntry(
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Assert.assertEquals(0, atomicInteger.get());
	}

	@Test
	public void testDoesNotCallCapabilityLocalRepositoryWrapperForACapabilityWithADifferentRepositoryClassName()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		FileEntry fileEntry1 = _addRandomFileEntry(serviceContext);

		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<Capability> capabilityServiceRegistration =
			registry.registerService(
				Capability.class,
				new TestRepositoryWrapperAwareCapability() {

					@Override
					public LocalRepository wrapLocalRepository(
						LocalRepository localRepository) {

						return new LocalRepositoryWrapper(localRepository) {

							@Override
							public FileEntry addFileEntry(
								String externalReferenceCode, long userId,
								long folderId, String sourceFileName,
								String mimeType, String title,
								String description, String changeLog, File file,
								Date expirationDate, Date reviewDate,
								ServiceContext serviceContext) {

								return fileEntry1;
							}

						};
					}

					@Override
					public Repository wrapRepository(Repository repository) {
						return repository;
					}

				},
				_getCapabilityProperties("unknown"));

		try {
			FileEntry fileEntry2 = _addRandomFileEntry(serviceContext);

			Assert.assertNotSame(fileEntry1, fileEntry2);
		}
		finally {
			capabilityServiceRegistration.unregister();
		}
	}

	@Test
	public void testDoesNotCallCapabilityLocalRepositoryWrapperForACapabilityWithNoRepositoryClassName()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		FileEntry fileEntry1 = _addRandomFileEntry(serviceContext);

		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<Capability> capabilityServiceRegistration =
			registry.registerService(
				Capability.class,
				new TestRepositoryWrapperAwareCapability() {

					@Override
					public LocalRepository wrapLocalRepository(
						LocalRepository localRepository) {

						return new LocalRepositoryWrapper(localRepository) {

							@Override
							public FileEntry addFileEntry(
								String externalReferenceCode, long userId,
								long folderId, String sourceFileName,
								String mimeType, String title,
								String description, String changeLog, File file,
								Date expirationDate, Date reviewDate,
								ServiceContext serviceContext) {

								return fileEntry1;
							}

						};
					}

					@Override
					public Repository wrapRepository(Repository repository) {
						return repository;
					}

				},
				_getCapabilityProperties(null));

		try {
			FileEntry fileEntry2 = _addRandomFileEntry(serviceContext);

			Assert.assertNotSame(fileEntry1, fileEntry2);
		}
		finally {
			capabilityServiceRegistration.unregister();
		}
	}

	private FileEntry _addRandomFileEntry(ServiceContext serviceContext)
		throws PortalException {

		return DLAppLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			TestDataConstants.TEST_BYTE_ARRAY, null, null, serviceContext);
	}

	private Map<String, Object> _getCapabilityProperties(
		String repositoryClassName) {

		return HashMapBuilder.<String, Object>put(
			"repository.class.name", repositoryClassName
		).build();
	}

	@DeleteAfterTestRun
	private Group _group;

	private interface TestRepositoryEventAwareCapability
		extends Capability, RepositoryEventAware {
	}

	private interface TestRepositoryWrapperAwareCapability
		extends Capability, RepositoryWrapperAware {
	}

}