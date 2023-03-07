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

package com.liferay.headless.commerce.delivery.catalog.v2.resource.v2_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.constants.CPAttachmentFileEntryConstants;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPAttachmentFileEntryLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.headless.commerce.delivery.catalog.v2.client.dto.v2_0.Image;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Crescenzo Rega
 */
@RunWith(Arquillian.class)
public class ImageResourceTest extends BaseImageResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			_user.getUserId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			testGroup.getGroupId(), RandomTestUtil.randomString());
		_cpDefinition = CPTestUtil.addCPDefinition(
			testGroup.getGroupId(), "simple", true, false);
	}

	@Override
	protected Image randomImage() throws Exception {
		return new Image() {
			{
				displayDate = RandomTestUtil.nextDate();
				expirationDate = RandomTestUtil.nextDate();
				id = RandomTestUtil.randomLong();
				neverExpire = true;
				priority = RandomTestUtil.randomDouble();
				title = StringUtil.toLowerCase(RandomTestUtil.randomString());
				type = CPAttachmentFileEntryConstants.TYPE_IMAGE;
			}
		};
	}

	@Override
	protected Image testGetChannelIdProductImagesPage_addImage(
			Long channelId, Long productId, Image image)
		throws Exception {

		return _addCPAttachmentFileEntry(image);
	}

	@Override
	protected Long testGetChannelIdProductImagesPage_getId() throws Exception {
		return _commerceChannel.getCommerceChannelId();
	}

	@Override
	protected Long testGetChannelIdProductImagesPage_getProductId()
		throws Exception {

		return _cpDefinition.getCProductId();
	}

	@Override
	protected Image testGraphQLImage_addImage() throws Exception {
		return _addCPAttachmentFileEntry(randomImage());
	}

	private Image _addCPAttachmentFileEntry(Image image) throws Exception {
		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			RandomTestUtil.randomString(), _user.getUserId(),
			testGroup.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			null, RandomTestUtil.nextDate(), _serviceContext);

		Calendar displayDate = Calendar.getInstance();
		Calendar expirationDate = Calendar.getInstance();

		displayDate.setTime(image.getDisplayDate());
		expirationDate.setTime(image.getExpirationDate());

		CPAttachmentFileEntry imageFileEntry =
			_cpAttachmentFileEntryLocalService.addCPAttachmentFileEntry(
				RandomTestUtil.randomString(), _user.getUserId(),
				testGroup.getGroupId(),
				_classNameLocalService.getClassNameId(CPDefinition.class),
				_cpDefinition.getCPDefinitionId(), fileEntry.getFileEntryId(),
				false, null, displayDate.get(Calendar.MONTH),
				displayDate.get(Calendar.DAY_OF_MONTH),
				displayDate.get(Calendar.YEAR), displayDate.get(Calendar.HOUR),
				displayDate.get(Calendar.MINUTE),
				expirationDate.get(Calendar.MONTH),
				expirationDate.get(Calendar.DAY_OF_MONTH),
				expirationDate.get(Calendar.YEAR),
				expirationDate.get(Calendar.HOUR),
				expirationDate.get(Calendar.MINUTE), true,
				RandomTestUtil.randomLocaleStringMap(), null,
				RandomTestUtil.nextDouble(), image.getType(), _serviceContext);

		_imageFileEntries.add(imageFileEntry);

		return new Image() {
			{
				displayDate = imageFileEntry.getDisplayDate();
				expirationDate = imageFileEntry.getExpirationDate();
				id = imageFileEntry.getCPAttachmentFileEntryId();
				priority = imageFileEntry.getPriority();
				title = imageFileEntry.getTitle();
				type = imageFileEntry.getType();
			}
		};
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@Inject
	private CPAttachmentFileEntryLocalService
		_cpAttachmentFileEntryLocalService;

	@DeleteAfterTestRun
	private CPDefinition _cpDefinition;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@DeleteAfterTestRun
	private final List<CPAttachmentFileEntry> _imageFileEntries =
		new ArrayList<>();

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}