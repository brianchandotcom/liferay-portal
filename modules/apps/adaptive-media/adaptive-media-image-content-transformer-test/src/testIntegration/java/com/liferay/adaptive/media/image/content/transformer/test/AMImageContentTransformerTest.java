/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.content.transformer.test;

import com.liferay.adaptive.media.content.transformer.ContentTransformerHandler;
import com.liferay.adaptive.media.content.transformer.constants.ContentTransformerContentTypes;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.After;
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
public class AMImageContentTransformerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_amImageConfigurationEntry =
			_amImageConfigurationHelper.addAMImageConfigurationEntry(
				_group.getCompanyId(), StringUtil.randomString(),
				StringUtil.randomString(), StringUtil.randomString(),
				HashMapBuilder.put(
					"max-height", "600"
				).put(
					"max-width", "800"
				).build());
	}

	@After
	public void tearDown() throws Exception {
		_amImageConfigurationHelper.forceDeleteAMImageConfigurationEntry(
			_group.getCompanyId(), _amImageConfigurationEntry.getUUID());
	}

	@Test
	public void testTransformASingleImage() throws Exception {
		FileEntry fileEntry = _addImageFileEntry(
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId()));

		String rawHTML = String.format(
			"<img data-fileentryid=\"%s\" src=\"%s\" />",
			fileEntry.getFileEntryId(),
			_dlURLHelper.getPreviewURL(
				fileEntry, fileEntry.getFileVersion(), null, StringPool.BLANK,
				false, false));

		String regex = StringBundler.concat(
			"<picture data-fileentryid=\".+\">",
			"<source media=\"\\(max-width:.+px\\)\" srcset=\".+\" \\/>",
			"<source media=\"\\(max-width:.+px\\) and \\(min-width:.+px\\)\" ",
			"srcset=\".+\" \\/><img data-fileentryid=\".+\" src=\".+\" \\/>",
			"<\\/picture>");

		String transformedHTML = _contentTransformerHandler.transform(
			ContentTransformerContentTypes.HTML, rawHTML);

		Assert.assertTrue(transformedHTML.matches(regex));
	}

	private FileEntry _addImageFileEntry(ServiceContext serviceContext)
		throws Exception {

		return _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), ContentTypes.IMAGE_JPEG,
			FileUtil.getBytes(
				AMImageContentTransformerTest.class, "dependencies/image.jpg"),
			null, null, serviceContext);
	}

	private AMImageConfigurationEntry _amImageConfigurationEntry;

	@Inject
	private AMImageConfigurationHelper _amImageConfigurationHelper;

	@Inject
	private ContentTransformerHandler _contentTransformerHandler;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private DLURLHelper _dlURLHelper;

	@DeleteAfterTestRun
	private Group _group;

}