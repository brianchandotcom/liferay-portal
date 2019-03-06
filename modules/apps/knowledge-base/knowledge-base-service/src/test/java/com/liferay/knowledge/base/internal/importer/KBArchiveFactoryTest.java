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

package com.liferay.knowledge.base.internal.importer;

import com.liferay.knowledge.base.configuration.KBGroupServiceConfiguration;
import com.liferay.knowledge.base.constants.KBConstants;
import com.liferay.knowledge.base.exception.KBArticleImportException;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.zip.ZipReader;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.mockito.expectation.ConstructorExpectationSetup;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Adolfo Pérez
 */
@PrepareForTest({GroupServiceSettingsLocator.class, KBArchiveFactory.class})
@RunWith(PowerMockRunner.class)
public class KBArchiveFactoryTest {

	@Before
	public void setUp() throws Exception {
		_kbArchiveFactory.setConfigurationProvider(_configurationProvider);

		ConstructorExpectationSetup<GroupServiceSettingsLocator>
			groupServiceSettingsLocatorConstructorExpectationSetup =
				PowerMockito.whenNew(GroupServiceSettingsLocator.class);

		OngoingStubbing<GroupServiceSettingsLocator>
			groupServiceSettingsLocatorOngoingStubbing =
				groupServiceSettingsLocatorConstructorExpectationSetup.
					withArguments(
						Mockito.anyLong(),
						Mockito.eq(KBConstants.SERVICE_NAME));

		groupServiceSettingsLocatorOngoingStubbing.thenReturn(
			_groupServiceSettingsLocator);

		Mockito.when(
			_configurationProvider.getConfiguration(
				KBGroupServiceConfiguration.class, _groupServiceSettingsLocator)
		).thenReturn(
			_kbGroupServiceConfiguration
		);

		Mockito.when(
			_kbGroupServiceConfiguration.markdownImporterArticleExtensions()
		).thenReturn(
			new String[] {".md"}
		);

		Mockito.when(
			_kbGroupServiceConfiguration.markdownImporterArticleIntro()
		).thenReturn(
			"intro.md"
		);
	}

	@Test
	public void testEmptyZipReaderReturnsNoFolders() throws Exception {
		Mockito.when(
			_zipReader.getEntries()
		).thenReturn(
			Collections.<String>emptyList()
		);

		KBArchive kbArchive = _kbArchiveFactory.createKBArchive(
			1234L, _zipReader);

		Collection<KBArchive.Folder> folders = kbArchive.getFolders();

		Assert.assertTrue(folders.toString(), folders.isEmpty());
	}

	@Test
	public void testZipReaderWithALoneIntroFileReturnsFolder()
		throws Exception {

		Mockito.when(
			_zipReader.getEntries()
		).thenReturn(
			Arrays.asList("/intro.md")
		);

		KBArchive kbArchive = _kbArchiveFactory.createKBArchive(
			1234L, _zipReader);

		Collection<KBArchive.Folder> folders = kbArchive.getFolders();

		Iterator<KBArchive.Folder> iterator = folders.iterator();

		KBArchive.Folder folder = iterator.next();

		Assert.assertEquals(StringPool.SLASH, folder.getName());

		KBArchive.File introFile = folder.getIntroFile();

		Assert.assertEquals("/intro.md", introFile.getName());
	}

	@Test
	public void testZipReaderWithIntroFileAndArticleFiles() throws Exception {
		Mockito.when(
			_zipReader.getEntries()
		).thenReturn(
			Arrays.asList("/intro.md", "/x/a.md", "/y/z/b.md", "/y/z/c.md")
		);

		KBArchive kbArchive = _kbArchiveFactory.createKBArchive(
			1234L, _zipReader);

		Collection<KBArchive.Folder> folders = kbArchive.getFolders();

		Assert.assertEquals(folders.toString(), 3, folders.size());

		Iterator<KBArchive.Folder> folderIterator = folders.iterator();

		KBArchive.Folder folder0 = folderIterator.next();

		Assert.assertEquals(StringPool.SLASH, folder0.getName());

		KBArchive.File introFile0 = folder0.getIntroFile();

		Assert.assertEquals("/intro.md", introFile0.getName());

		Assert.assertNull(folder0.getParentFolderIntroFile());

		Collection<KBArchive.File> files0 = folder0.getFiles();

		Assert.assertTrue(files0.isEmpty());

		KBArchive.Folder folder1 = folderIterator.next();

		Assert.assertEquals("/x", folder1.getName());
		Assert.assertNull(folder1.getIntroFile());
		Assert.assertNotNull(folder1.getParentFolderIntroFile());

		Collection<KBArchive.File> files1 = folder1.getFiles();

		Assert.assertEquals(files1.toString(), 1, files1.size());

		KBArchive.Folder folder2 = folderIterator.next();

		Assert.assertEquals("/y/z", folder2.getName());
		Assert.assertNull(folder2.getIntroFile());
		Assert.assertNull(folder2.getParentFolderIntroFile());

		Collection<KBArchive.File> files2 = folder2.getFiles();

		Assert.assertEquals(files2.toString(), 2, files2.size());
	}

	@Test(expected = KBArticleImportException.class)
	public void testZipReaderWithNoEntriesFails() throws Exception {
		Mockito.when(
			_zipReader.getEntries()
		).thenReturn(
			null
		);

		_kbArchiveFactory.createKBArchive(1234L, _zipReader);
	}

	@Test
	public void testZipReaderWithNoIntroFileReturnsFolder() throws Exception {
		Mockito.when(
			_zipReader.getEntries()
		).thenReturn(
			Arrays.asList("/a.md", "/b.md")
		);

		KBArchive kbArchive = _kbArchiveFactory.createKBArchive(
			1234L, _zipReader);

		Collection<KBArchive.Folder> folders = kbArchive.getFolders();

		Assert.assertEquals(folders.toString(), 1, folders.size());

		Iterator<KBArchive.Folder> foldersIterator = folders.iterator();

		KBArchive.Folder folder = foldersIterator.next();

		Assert.assertNull(folder.getIntroFile());

		Collection<KBArchive.File> files = folder.getFiles();

		Assert.assertEquals(files.toString(), 2, files.size());

		Iterator<KBArchive.File> filesIterator = files.iterator();

		KBArchive.File file0 = filesIterator.next();

		Assert.assertEquals("/a.md", file0.getName());

		KBArchive.File file1 = filesIterator.next();

		Assert.assertEquals("/b.md", file1.getName());
	}

	@Test
	public void testZipReaderWithNoValidFilesReturnsNoFolders()
		throws Exception {

		Mockito.when(
			_zipReader.getEntries()
		).thenReturn(
			Arrays.asList("/a.txt", "/b.txt")
		);

		KBArchive kbArchive = _kbArchiveFactory.createKBArchive(
			1234L, _zipReader);

		Collection<KBArchive.Folder> folders = kbArchive.getFolders();

		Assert.assertTrue(folders.toString(), folders.isEmpty());
	}

	private final ConfigurationProvider _configurationProvider = Mockito.mock(
		ConfigurationProvider.class);
	private final GroupServiceSettingsLocator _groupServiceSettingsLocator =
		Mockito.mock(GroupServiceSettingsLocator.class);
	private final KBArchiveFactory _kbArchiveFactory = new KBArchiveFactory();
	private final KBGroupServiceConfiguration _kbGroupServiceConfiguration =
		Mockito.mock(KBGroupServiceConfiguration.class);
	private final ZipReader _zipReader = Mockito.mock(ZipReader.class);

}