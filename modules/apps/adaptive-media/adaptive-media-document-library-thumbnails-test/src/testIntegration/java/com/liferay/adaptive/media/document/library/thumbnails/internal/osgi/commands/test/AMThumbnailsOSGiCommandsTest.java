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

package com.liferay.adaptive.media.document.library.thumbnails.internal.osgi.commands.test;

import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
import com.liferay.adaptive.media.image.finder.AMImageFinder;
import com.liferay.adaptive.media.image.processor.AMImageProcessor;
import com.liferay.adaptive.media.image.service.AMImageEntryLocalServiceUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.store.DLStoreUtil;
import com.liferay.document.library.kernel.util.DLPreviewableProcessor;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.PropsValuesTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Map;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.runtime.ServiceComponentRuntime;
import org.osgi.service.component.runtime.dto.ComponentDescriptionDTO;
import org.osgi.util.promise.Promise;

/**
 * @author Adolfo Pérez
 */
@RunWith(Arquillian.class)
public class AMThumbnailsOSGiCommandsTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		_amImageConfigurationHelperServiceReference =
			registry.getServiceReference(AMImageConfigurationHelper.class);
		_amImageFinderServiceReference = registry.getServiceReference(
			AMImageFinder.class);

		_amImageConfigurationHelper = registry.getService(
			_amImageConfigurationHelperServiceReference);
		_amImageFinder = registry.getService(_amImageFinderServiceReference);

		_disableAMThumbnails();
		_disableDocumentLibraryAM();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		registry.ungetService(_amImageConfigurationHelperServiceReference);
		registry.ungetService(_amImageFinderServiceReference);

		_enableAMThumbnails();
		_enableDocumentLibraryAM();
	}

	@Before
	public void setUp() throws Exception {
		UserTestUtil.setUser(TestPropsValues.getUser());

		_company = CompanyTestUtil.addCompany();

		_user = UserTestUtil.addCompanyAdminUser(_company);

		_group = GroupTestUtil.addGroup(
			_company.getCompanyId(), _user.getUserId(),
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		_addConfiguration(100, 100);
		_addConfiguration(300, 300);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group, _user.getUserId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);
	}

	@After
	public void tearDown() throws Exception {
		_amImageConfigurationHelper.forceDeleteAMImageConfigurationEntry(
			_company.getCompanyId(), _THUMBNAIL_CONFIGURATION + 100);

		_amImageConfigurationHelper.forceDeleteAMImageConfigurationEntry(
			_company.getCompanyId(), _THUMBNAIL_CONFIGURATION + 300);

		FileVersion latestFileVersion = _pngFileEntry.getFileVersion();

		AMImageEntryLocalServiceUtil.deleteAMImageEntryFileVersion(
			latestFileVersion);

		GroupLocalServiceUtil.deleteGroup(_group);

		CompanyLocalServiceUtil.deleteCompany(_company);

		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testCleanUpDeletesImageThumbnails() throws Exception {
		_cleanUp();

		int count = _getThumbnailCount();

		_addPNGFileEntry();

		Assert.assertEquals(count + 1, _getThumbnailCount());

		_cleanUp();

		Assert.assertEquals(count, _getThumbnailCount());
	}

	@Test
	public void testCleanUpDeletesOnlyImageThumbnails() throws Exception {
		_cleanUp();

		int count = _getThumbnailCount();

		_addPDFFileEntry();
		_addPNGFileEntry();

		Assert.assertEquals(count + 2, _getThumbnailCount());

		_cleanUp();

		Assert.assertEquals(count + 1, _getThumbnailCount());
	}

	@Test
	public void testMigrateDoesNotRemoveThumbnails() throws Exception {
		int count = _getThumbnailCount();

		_addPDFFileEntry();
		_addPNGFileEntry();

		Assert.assertEquals(count + 2, _getThumbnailCount());

		_migrate();

		Assert.assertEquals(count + 2, _getThumbnailCount());
	}

	@Ignore
	@Test
	public void testMigrateOnlyProcessesImages() throws Exception {
		try (SafeCloseable safeCloseable1 =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"DL_FILE_ENTRY_THUMBNAIL_CUSTOM_1_MAX_HEIGHT", 100);
			SafeCloseable safeCloseable2 =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"DL_FILE_ENTRY_THUMBNAIL_CUSTOM_1_MAX_WIDTH", 100)) {

			FileEntry pdfFileEntry = _addPDFFileEntry();
			FileEntry pngFileEntry = _addPNGFileEntry();

			_migrate();

			Assert.assertEquals(0, _getAdaptiveMediaCount(pdfFileEntry));
			Assert.assertEquals(2, _getAdaptiveMediaCount(pngFileEntry));
		}
	}

	@Test(expected = InvocationTargetException.class)
	public void testMigrateThrowsExceptionWhenNoValidConfiguration()
		throws Exception {

		try (SafeCloseable safeCloseable1 =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"DL_FILE_ENTRY_THUMBNAIL_MAX_HEIGHT", 999);
			SafeCloseable safeCloseable2 =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"DL_FILE_ENTRY_THUMBNAIL_MAX_HEIGHT", 999)) {

			_addPNGFileEntry();

			_migrate();
		}
	}

	private static void _disableAMThumbnails() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		ServiceComponentRuntime serviceComponentRuntime = registry.getService(
			registry.getServiceReference(ServiceComponentRuntime.class));

		Object service = registry.getService(
			registry.getServiceReference(_CLASS_NAME_PROCESSOR));

		ComponentDescriptionDTO componentDescriptionDTO =
			serviceComponentRuntime.getComponentDescriptionDTO(
				FrameworkUtil.getBundle(service.getClass()),
				_CLASS_NAME_PROCESSOR);

		if (componentDescriptionDTO == null) {
			return;
		}

		Promise<Void> promise = serviceComponentRuntime.disableComponent(
			componentDescriptionDTO);

		promise.getValue();
	}

	private static void _disableDocumentLibraryAM() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			AMThumbnailsOSGiCommandsTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		for (Bundle curBundle : bundleContext.getBundles()) {
			if (_BUNDLE_SYMBOLIC_NAME.equals(curBundle.getSymbolicName())) {
				if (curBundle.getState() == Bundle.ACTIVE) {
					curBundle.stop();
				}

				break;
			}
		}
	}

	private static void _enableAMThumbnails() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		ServiceComponentRuntime serviceComponentRuntime = registry.getService(
			registry.getServiceReference(ServiceComponentRuntime.class));

		Object service = registry.getService(
			registry.getServiceReference(_CLASS_NAME_OSGI_COMMAND));

		ComponentDescriptionDTO componentDescriptionDTO =
			serviceComponentRuntime.getComponentDescriptionDTO(
				FrameworkUtil.getBundle(service.getClass()),
				_CLASS_NAME_PROCESSOR);

		if (componentDescriptionDTO == null) {
			return;
		}

		Promise<Void> promise = serviceComponentRuntime.enableComponent(
			componentDescriptionDTO);

		promise.getValue();
	}

	private static void _enableDocumentLibraryAM() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			AMThumbnailsOSGiCommandsTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		for (Bundle curBundle : bundleContext.getBundles()) {
			if (_BUNDLE_SYMBOLIC_NAME.equals(curBundle.getSymbolicName())) {
				if (curBundle.getState() != Bundle.ACTIVE) {
					curBundle.start();
				}

				break;
			}
		}
	}

	private void _addConfiguration(int width, int height) throws Exception {
		Map<String, String> properties = HashMapBuilder.put(
			"max-height", String.valueOf(height)
		).put(
			"max-width", String.valueOf(width)
		).build();

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			_company.getCompanyId(), _THUMBNAIL_CONFIGURATION + width,
			StringPool.BLANK, _THUMBNAIL_CONFIGURATION + width, properties);
	}

	private FileEntry _addPDFFileEntry() throws Exception {
		return DLAppLocalServiceUtil.addFileEntry(
			null, _user.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".pdf",
			ContentTypes.APPLICATION_PDF, _read("sample.pdf"), null, null,
			_serviceContext);
	}

	private FileEntry _addPNGFileEntry() throws Exception {
		_pngFileEntry = DLAppLocalServiceUtil.addFileEntry(
			null, _user.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".png", ContentTypes.IMAGE_PNG,
			_read("sample.png"), null, null, _serviceContext);

		return _pngFileEntry;
	}

	private void _cleanUp() throws Exception {
		_run("cleanUp");
	}

	private long _getAdaptiveMediaCount(FileEntry fileEntry) throws Exception {
		Stream<AdaptiveMedia<AMImageProcessor>> adaptiveMediaStream =
			_amImageFinder.getAdaptiveMediaStream(
				amImageQueryBuilder -> amImageQueryBuilder.forFileEntry(
					fileEntry
				).done());

		return adaptiveMediaStream.count();
	}

	private int _getThumbnailCount() throws Exception {
		String[] fileNames = DLStoreUtil.getFileNames(
			_company.getCompanyId(), DLPreviewableProcessor.REPOSITORY_ID,
			DLPreviewableProcessor.THUMBNAIL_PATH);

		return fileNames.length;
	}

	private void _migrate() throws Exception {
		_run("migrate");
	}

	private byte[] _read(String fileName) throws Exception {
		return FileUtil.getBytes(AMThumbnailsOSGiCommandsTest.class, fileName);
	}

	private void _run(String functionName) throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		Object service = registry.getService(
			registry.getServiceReference(_CLASS_NAME_OSGI_COMMAND));

		Class<?> clazz = service.getClass();

		Method method = clazz.getMethod(functionName, String[].class);

		method.invoke(
			service,
			(Object)new String[] {String.valueOf(_company.getCompanyId())});
	}

	private static final String _BUNDLE_SYMBOLIC_NAME =
		"com.liferay.adaptive.media.document.library";

	private static final String _CLASS_NAME_OSGI_COMMAND =
		"com.liferay.adaptive.media.document.library.thumbnails.internal." +
			"osgi.commands.AMThumbnailsOSGiCommands";

	private static final String _CLASS_NAME_PROCESSOR =
		"com.liferay.adaptive.media.document.library.thumbnails.internal." +
			"processor.AMImageEntryProcessor";

	private static final String _THUMBNAIL_CONFIGURATION = "thumbnail";

	private static AMImageConfigurationHelper _amImageConfigurationHelper;
	private static ServiceReference<AMImageConfigurationHelper>
		_amImageConfigurationHelperServiceReference;
	private static AMImageFinder _amImageFinder;
	private static ServiceReference<AMImageFinder>
		_amImageFinderServiceReference;

	private Company _company;
	private Group _group;
	private FileEntry _pngFileEntry;
	private ServiceContext _serviceContext;
	private User _user;

}