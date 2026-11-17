/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.rest.repository.internal.document.library.repository.external;

import com.liferay.document.library.repository.external.ExtRepositoryFileEntry;
import com.liferay.document.library.repository.external.ExtRepositoryFolder;
import com.liferay.document.library.repository.external.ExtRepositoryObject;
import com.liferay.document.library.repository.external.ExtRepositoryObjectType;
import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.sharepoint.rest.repository.internal.document.library.repository.external.model.SharepointFolder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Akhash Ramprakash
 */
public class SharepointCachingExtRepositoryTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@After
	public void tearDown() {
		CentralizedThreadLocal.clearShortLivedCentralizedThreadLocals();
	}

	@Test
	public void testGetExtRepositoryObjects() throws Exception {
		SharepointExtRepository sharepointExtRepository = Mockito.mock(
			SharepointExtRepository.class);

		String extRepositoryFolderKey = "/" + RandomTestUtil.randomString();

		List<ExtRepositoryFileEntry> extRepositoryFileEntries =
			Collections.singletonList(
				Mockito.mock(ExtRepositoryFileEntry.class));

		Mockito.when(
			sharepointExtRepository.getExtRepositoryObjects(
				ExtRepositoryObjectType.FILE, extRepositoryFolderKey)
		).thenReturn(
			extRepositoryFileEntries
		);

		List<ExtRepositoryFolder> extRepositoryFolders =
			Collections.singletonList(Mockito.mock(ExtRepositoryFolder.class));

		Mockito.when(
			sharepointExtRepository.getExtRepositoryObjects(
				ExtRepositoryObjectType.FOLDER, extRepositoryFolderKey)
		).thenReturn(
			extRepositoryFolders
		);

		SharepointCachingExtRepository sharepointCachingExtRepository =
			new SharepointCachingExtRepository(sharepointExtRepository);

		for (int i = 0; i < 2; i++) {
			Assert.assertSame(
				extRepositoryFileEntries,
				sharepointCachingExtRepository.getExtRepositoryObjects(
					ExtRepositoryObjectType.FILE, extRepositoryFolderKey));
			Assert.assertSame(
				extRepositoryFolders,
				sharepointCachingExtRepository.getExtRepositoryObjects(
					ExtRepositoryObjectType.FOLDER, extRepositoryFolderKey));
			Assert.assertEquals(
				Arrays.asList(
					extRepositoryFileEntries.get(0),
					extRepositoryFolders.get(0)),
				sharepointCachingExtRepository.getExtRepositoryObjects(
					ExtRepositoryObjectType.OBJECT, extRepositoryFolderKey));
		}

		Mockito.verify(
			sharepointExtRepository
		).getExtRepositoryObjects(
			ExtRepositoryObjectType.FILE, extRepositoryFolderKey
		);

		Mockito.verify(
			sharepointExtRepository
		).getExtRepositoryObjects(
			ExtRepositoryObjectType.FOLDER, extRepositoryFolderKey
		);

		Mockito.verify(
			sharepointExtRepository, Mockito.never()
		).getExtRepositoryObjects(
			ExtRepositoryObjectType.OBJECT, extRepositoryFolderKey
		);
	}

	@Test
	public void testGetExtRepositoryObjectsCount() throws Exception {

		// Counted folders

		SharepointExtRepository sharepointExtRepository = Mockito.mock(
			SharepointExtRepository.class);

		String extRepositoryFolderKey = "/" + RandomTestUtil.randomString();

		String countedExtRepositoryFolderKey =
			extRepositoryFolderKey + "/" + RandomTestUtil.randomString();
		String uncountedExtRepositoryFolderKey =
			extRepositoryFolderKey + "/" + RandomTestUtil.randomString();

		Mockito.when(
			sharepointExtRepository.getExtRepositoryObjects(
				ExtRepositoryObjectType.FOLDER, extRepositoryFolderKey)
		).thenReturn(
			Arrays.asList(
				_getSharepointFolder(countedExtRepositoryFolderKey, null, null),
				_getSharepointFolder(
					uncountedExtRepositoryFolderKey, null, null))
		);

		Mockito.when(
			sharepointExtRepository.getExtRepositoryFoldersWithChildrenCount(
				extRepositoryFolderKey)
		).thenReturn(
			Collections.singletonList(
				_getSharepointFolder(countedExtRepositoryFolderKey, 3, 2))
		);

		int extRepositoryObjectsCount = RandomTestUtil.randomInt();

		Mockito.when(
			sharepointExtRepository.getExtRepositoryObjectsCount(
				Mockito.eq(ExtRepositoryObjectType.FILE), Mockito.anyString())
		).thenReturn(
			extRepositoryObjectsCount
		);

		SharepointCachingExtRepository sharepointCachingExtRepository =
			new SharepointCachingExtRepository(sharepointExtRepository);

		sharepointCachingExtRepository.getExtRepositoryObjects(
			ExtRepositoryObjectType.FOLDER, extRepositoryFolderKey);

		Assert.assertEquals(
			3,
			sharepointCachingExtRepository.getExtRepositoryObjectsCount(
				ExtRepositoryObjectType.FILE, countedExtRepositoryFolderKey));
		Assert.assertEquals(
			2,
			sharepointCachingExtRepository.getExtRepositoryObjectsCount(
				ExtRepositoryObjectType.FOLDER, countedExtRepositoryFolderKey));
		Assert.assertEquals(
			extRepositoryObjectsCount,
			sharepointCachingExtRepository.getExtRepositoryObjectsCount(
				ExtRepositoryObjectType.FILE, uncountedExtRepositoryFolderKey));

		Mockito.verify(
			sharepointExtRepository
		).getExtRepositoryFoldersWithChildrenCount(
			extRepositoryFolderKey
		);

		// Moved file

		sharepointCachingExtRepository.moveExtRepositoryObject(
			ExtRepositoryObjectType.FILE,
			countedExtRepositoryFolderKey + "/" + RandomTestUtil.randomString(),
			uncountedExtRepositoryFolderKey, RandomTestUtil.randomString());

		Assert.assertEquals(
			extRepositoryObjectsCount,
			sharepointCachingExtRepository.getExtRepositoryObjectsCount(
				ExtRepositoryObjectType.FILE, countedExtRepositoryFolderKey));
	}

	@Test
	public void testGetExtRepositoryParentFolder() throws Exception {

		// Cached folder

		SharepointExtRepository sharepointExtRepository = Mockito.mock(
			SharepointExtRepository.class);

		ExtRepositoryFolder extRepositoryFolder = Mockito.mock(
			ExtRepositoryFolder.class);

		Mockito.when(
			sharepointExtRepository.getExtRepositoryParentFolder(
				Mockito.any(ExtRepositoryObject.class))
		).thenReturn(
			extRepositoryFolder
		);

		String rootFolderKey = "/" + RandomTestUtil.randomString();

		Mockito.when(
			sharepointExtRepository.getRootFolderKey()
		).thenReturn(
			rootFolderKey
		);

		String extRepositoryFolderKey = "/" + RandomTestUtil.randomString();

		ExtRepositoryFolder cachedExtRepositoryFolder = Mockito.mock(
			ExtRepositoryFolder.class);

		Mockito.when(
			sharepointExtRepository.getExtRepositoryObject(
				ExtRepositoryObjectType.FOLDER, extRepositoryFolderKey)
		).thenReturn(
			cachedExtRepositoryFolder
		);

		SharepointCachingExtRepository sharepointCachingExtRepository =
			new SharepointCachingExtRepository(sharepointExtRepository);

		sharepointCachingExtRepository.getExtRepositoryObject(
			ExtRepositoryObjectType.FOLDER, extRepositoryFolderKey);

		Assert.assertSame(
			cachedExtRepositoryFolder,
			sharepointCachingExtRepository.getExtRepositoryParentFolder(
				_getExtRepositoryObject(
					extRepositoryFolderKey + "/" +
						RandomTestUtil.randomString())));

		Mockito.verify(
			sharepointExtRepository, Mockito.never()
		).getExtRepositoryParentFolder(
			Mockito.any(ExtRepositoryObject.class)
		);

		// Root folder

		Mockito.when(
			sharepointExtRepository.getExtRepositoryObject(
				ExtRepositoryObjectType.FOLDER, rootFolderKey)
		).thenReturn(
			Mockito.mock(ExtRepositoryFolder.class)
		);

		sharepointCachingExtRepository.getExtRepositoryObject(
			ExtRepositoryObjectType.FOLDER, rootFolderKey);

		Assert.assertSame(
			extRepositoryFolder,
			sharepointCachingExtRepository.getExtRepositoryParentFolder(
				_getExtRepositoryObject(
					rootFolderKey + "/" + RandomTestUtil.randomString())));

		Mockito.verify(
			sharepointExtRepository
		).getExtRepositoryParentFolder(
			Mockito.any(ExtRepositoryObject.class)
		);

		// Same folder

		extRepositoryFolderKey = "/" + RandomTestUtil.randomString();

		for (int i = 0; i < 2; i++) {
			Assert.assertSame(
				extRepositoryFolder,
				sharepointCachingExtRepository.getExtRepositoryParentFolder(
					_getExtRepositoryObject(
						extRepositoryFolderKey + "/" +
							RandomTestUtil.randomString())));
		}

		Mockito.verify(
			sharepointExtRepository, Mockito.times(2)
		).getExtRepositoryParentFolder(
			Mockito.any(ExtRepositoryObject.class)
		);
	}

	private ExtRepositoryObject _getExtRepositoryObject(
		String extRepositoryModelKey) {

		ExtRepositoryObject extRepositoryObject = Mockito.mock(
			ExtRepositoryObject.class);

		Mockito.when(
			extRepositoryObject.getExtRepositoryModelKey()
		).thenReturn(
			extRepositoryModelKey
		);

		return extRepositoryObject;
	}

	private SharepointFolder _getSharepointFolder(
		String extRepositoryModelKey, Integer filesCount,
		Integer foldersCount) {

		return new SharepointFolder(
			extRepositoryModelKey, RandomTestUtil.randomString(),
			RandomTestUtil.nextDate(), RandomTestUtil.nextDate(),
			RandomTestUtil.randomLong(), filesCount, foldersCount);
	}

}