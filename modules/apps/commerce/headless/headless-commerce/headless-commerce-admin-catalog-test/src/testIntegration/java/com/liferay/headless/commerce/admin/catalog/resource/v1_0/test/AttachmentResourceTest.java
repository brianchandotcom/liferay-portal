/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.constants.CPAttachmentFileEntryConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.Attachment;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.test.rule.Inject;

import java.io.ByteArrayInputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Michele Vigilante
 */
@RunWith(Arquillian.class)
public class AttachmentResourceTest extends BaseAttachmentResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_cpDefinition = CPTestUtil.addCPDefinition(
			testGroup.getGroupId(), "simple", true, false);

		_cProduct = _cpDefinition.getCProduct();

		_user = UserTestUtil.addUser(testCompany);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			_user.getUserId());
	}

	@Ignore
	@Override
	@Test
	public void testGetProductByExternalReferenceCodeAttachmentsPage()
		throws Exception {

		super.testGetProductByExternalReferenceCodeAttachmentsPage();
	}

	@Ignore
	@Override
	@Test
	public void testGetProductByExternalReferenceCodeAttachmentsPageWithPagination()
		throws Exception {

		super.
			testGetProductByExternalReferenceCodeAttachmentsPageWithPagination();
	}

	@Ignore
	@Override
	@Test
	public void testGetProductByExternalReferenceCodeImagesPage()
		throws Exception {

		super.testGetProductByExternalReferenceCodeImagesPage();
	}

	@Ignore
	@Override
	@Test
	public void testGetProductByExternalReferenceCodeImagesPageWithPagination()
		throws Exception {

		super.testGetProductByExternalReferenceCodeImagesPageWithPagination();
	}

	@Ignore
	@Override
	@Test
	public void testGetProductIdAttachmentsPage() throws Exception {
		super.testGetProductIdAttachmentsPage();
	}

	@Ignore
	@Override
	@Test
	public void testGetProductIdAttachmentsPageWithPagination()
		throws Exception {

		super.testGetProductIdAttachmentsPageWithPagination();
	}

	@Ignore
	@Override
	@Test
	public void testGetProductIdImagesPage() throws Exception {
		super.testGetProductIdImagesPage();
	}

	@Ignore
	@Override
	@Test
	public void testGetProductIdImagesPageWithPagination() throws Exception {
		super.testGetProductIdImagesPageWithPagination();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLDeleteAttachment() throws Exception {
		super.testGraphQLDeleteAttachment();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetAttachmentByExternalReferenceCode()
		throws Exception {

		super.testGraphQLGetAttachmentByExternalReferenceCode();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetAttachmentByExternalReferenceCodeNotFound()
		throws Exception {

		super.testGraphQLGetAttachmentByExternalReferenceCodeNotFound();
	}

	@Override
	@Test
	public void testPatchAttachmentByExternalReferenceCode() throws Exception {
		super.testPatchAttachmentByExternalReferenceCode();

		_testPatchAttachmentByExternalReferenceCodeWithFileEntryExternalReferenceCode();
	}

	@Override
	@Test
	public void testPostProductByExternalReferenceCodeAttachment()
		throws Exception {

		super.testPostProductByExternalReferenceCodeAttachment();

		_testPostProductByExternalReferenceCodeAttachmentWithFileEntryExternalReferenceCode();
	}

	@Ignore
	@Override
	@Test
	public void testPostProductByExternalReferenceCodeAttachmentByBase64()
		throws Exception {

		super.testPostProductByExternalReferenceCodeAttachmentByBase64();
	}

	@Ignore
	@Override
	@Test
	public void testPostProductByExternalReferenceCodeAttachmentByUrl()
		throws Exception {

		super.testPostProductByExternalReferenceCodeAttachmentByUrl();
	}

	@Override
	@Test
	public void testPostProductByExternalReferenceCodeImage() throws Exception {
		Attachment randomAttachment = _randomImageAttachment();

		Attachment postAttachment =
			testPostProductByExternalReferenceCodeImage_addAttachment(
				randomAttachment);

		assertEquals(randomAttachment, postAttachment);
		assertValid(postAttachment);

		_testPostProductByExternalReferenceCodeImageWithFileEntryExternalReferenceCode();
	}

	@Ignore
	@Override
	@Test
	public void testPostProductByExternalReferenceCodeImageByBase64()
		throws Exception {

		super.testPostProductByExternalReferenceCodeImageByBase64();
	}

	@Ignore
	@Override
	@Test
	public void testPostProductByExternalReferenceCodeImageByUrl()
		throws Exception {

		super.testPostProductByExternalReferenceCodeImageByUrl();
	}

	@Override
	@Test
	public void testPostProductIdAttachment() throws Exception {
		super.testPostProductIdAttachment();

		_testPostProductIdAttachmentWithFileEntryExternalReferenceCode();
	}

	@Ignore
	@Override
	@Test
	public void testPostProductIdAttachmentByBase64() throws Exception {
		super.testPostProductIdAttachmentByBase64();
	}

	@Ignore
	@Override
	@Test
	public void testPostProductIdAttachmentByUrl() throws Exception {
		super.testPostProductIdAttachmentByUrl();
	}

	@Override
	@Test
	public void testPostProductIdImage() throws Exception {
		Attachment randomAttachment = _randomImageAttachment();

		Attachment postAttachment = testPostProductIdImage_addAttachment(
			randomAttachment);

		assertEquals(randomAttachment, postAttachment);
		assertValid(postAttachment);

		_testPostProductIdImageWithFileEntryExternalReferenceCode();
	}

	@Ignore
	@Override
	@Test
	public void testPostProductIdImageByBase64() throws Exception {
		super.testPostProductIdImageByBase64();
	}

	@Ignore
	@Override
	@Test
	public void testPostProductIdImageByUrl() throws Exception {
		super.testPostProductIdImageByUrl();
	}

	@Override
	@Test
	public void testPutAttachmentByExternalReferenceCode() throws Exception {
		testPatchAttachmentByExternalReferenceCode();
	}

	@Override
	protected Attachment randomAttachment() throws PortalException {
		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), testGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			TempFileEntryUtil.getTempFileName(
				RandomTestUtil.randomString() + ".txt"),
			ContentTypes.TEXT_PLAIN, RandomTestUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			new ByteArrayInputStream(RandomTestUtil.randomBytes()), 0, null,
			null, null, _serviceContext);

		return new Attachment() {
			{
				cdnEnabled = false;
				cdnURL = RandomTestUtil.randomString();
				externalReferenceCode = RandomTestUtil.randomString();
				fileEntryGroupExternalReferenceCode =
					testGroup.getExternalReferenceCode();
				fileEntryId = fileEntry.getFileEntryId();
				galleryEnabled = true;
				neverExpire = true;
				priority = RandomTestUtil.randomDouble();
				title = HashMapBuilder.put(
					"en_US", RandomTestUtil.randomString(5)
				).build();
				type = RandomTestUtil.randomInt();
			}
		};
	}

	@Override
	protected Attachment testDeleteAttachment_addAttachment() throws Exception {
		return attachmentResource.postProductIdAttachment(
			_cProduct.getCProductId(), randomAttachment());
	}

	@Override
	protected Attachment
			testDeleteAttachmentByExternalReferenceCode_addAttachment()
		throws Exception {

		return attachmentResource.postProductByExternalReferenceCodeAttachment(
			_cProduct.getExternalReferenceCode(), randomAttachment());
	}

	@Override
	protected Attachment
			testGetAttachmentByExternalReferenceCode_addAttachment()
		throws Exception {

		return attachmentResource.postProductByExternalReferenceCodeAttachment(
			_cProduct.getExternalReferenceCode(), randomAttachment());
	}

	@Override
	protected Attachment
			testPatchAttachmentByExternalReferenceCode_addAttachment()
		throws Exception {

		return attachmentResource.postProductByExternalReferenceCodeAttachment(
			_cProduct.getExternalReferenceCode(), randomAttachment());
	}

	@Override
	protected Attachment
			testPostProductByExternalReferenceCodeAttachment_addAttachment(
				Attachment attachment)
		throws Exception {

		return attachmentResource.postProductByExternalReferenceCodeAttachment(
			_cProduct.getExternalReferenceCode(), randomAttachment());
	}

	@Override
	protected Attachment
			testPostProductByExternalReferenceCodeImage_addAttachment(
				Attachment attachment)
		throws Exception {

		return attachmentResource.postProductByExternalReferenceCodeImage(
			_cProduct.getExternalReferenceCode(), attachment);
	}

	@Override
	protected Attachment testPostProductIdAttachment_addAttachment(
			Attachment attachment)
		throws Exception {

		return attachmentResource.postProductIdAttachment(
			_cProduct.getCProductId(), randomAttachment());
	}

	@Override
	protected Attachment testPostProductIdImage_addAttachment(
			Attachment attachment)
		throws Exception {

		return attachmentResource.postProductIdImage(
			_cpDefinition.getCProductId(), attachment);
	}

	@Override
	protected Attachment
			testPutAttachmentByExternalReferenceCode_addAttachment()
		throws Exception {

		return attachmentResource.postProductByExternalReferenceCodeAttachment(
			_cProduct.getExternalReferenceCode(), randomAttachment());
	}

	private Attachment _randomImageAttachment() throws Exception {
		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), testGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			TempFileEntryUtil.getTempFileName(
				RandomTestUtil.randomString() + ".jpg"),
			ContentTypes.IMAGE_JPEG, RandomTestUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			new ByteArrayInputStream(RandomTestUtil.randomBytes()), 0, null,
			null, null, _serviceContext);

		return new Attachment() {
			{
				cdnEnabled = false;
				cdnURL = RandomTestUtil.randomString();
				externalReferenceCode = RandomTestUtil.randomString();
				fileEntryGroupExternalReferenceCode =
					testGroup.getExternalReferenceCode();
				fileEntryId = fileEntry.getFileEntryId();
				galleryEnabled = true;
				neverExpire = true;
				priority = RandomTestUtil.randomDouble();
				title = HashMapBuilder.put(
					"en_US", RandomTestUtil.randomString(5)
				).build();
				type = CPAttachmentFileEntryConstants.TYPE_IMAGE;
			}
		};
	}

	private void _testPatchAttachmentByExternalReferenceCodeWithFileEntryExternalReferenceCode()
		throws Exception {

		Attachment postAttachment =
			testPatchAttachmentByExternalReferenceCode_addAttachment();

		Attachment randomPatchAttachment = randomPatchAttachment();

		long randomPatchAttachmentFileEntryId = GetterUtil.getLong(
			randomPatchAttachment.getFileEntryId());

		FileEntry fileEntry = _dlAppLocalService.getFileEntry(
			randomPatchAttachmentFileEntryId);

		randomPatchAttachment.setFileEntryExternalReferenceCode(
			fileEntry.getExternalReferenceCode());

		randomPatchAttachment.setFileEntryId(0L);

		Attachment patchAttachment =
			attachmentResource.patchAttachmentByExternalReferenceCode(
				postAttachment.getExternalReferenceCode(),
				randomPatchAttachment);

		randomPatchAttachment.setFileEntryId(randomPatchAttachmentFileEntryId);

		Attachment expectedPatchAttachment = postAttachment.clone();

		BeanTestUtil.copyProperties(
			randomPatchAttachment, expectedPatchAttachment);

		Attachment getAttachment =
			attachmentResource.getAttachmentByExternalReferenceCode(
				patchAttachment.getExternalReferenceCode());

		assertEquals(expectedPatchAttachment, getAttachment);
		assertValid(getAttachment);
		Assert.assertEquals(
			randomPatchAttachment.getFileEntryExternalReferenceCode(),
			getAttachment.getFileEntryExternalReferenceCode());
		Assert.assertEquals(
			randomPatchAttachmentFileEntryId,
			GetterUtil.getLong(getAttachment.getFileEntryId()));
	}

	private void _testPostProductByExternalReferenceCodeAttachmentWithFileEntryExternalReferenceCode()
		throws Exception {

		Attachment randomAttachment = randomAttachment();

		FileEntry fileEntry = _dlAppLocalService.getFileEntry(
			randomAttachment.getFileEntryId());

		randomAttachment.setFileEntryExternalReferenceCode(
			fileEntry.getExternalReferenceCode());

		randomAttachment.setFileEntryId(0L);

		Attachment postAttachment =
			attachmentResource.postProductByExternalReferenceCodeAttachment(
				_cProduct.getExternalReferenceCode(), randomAttachment);

		assertEquals(randomAttachment, postAttachment);
		assertValid(postAttachment);
		Assert.assertEquals(
			fileEntry.getExternalReferenceCode(),
			postAttachment.getFileEntryExternalReferenceCode());
		Assert.assertEquals(
			fileEntry.getFileEntryId(),
			GetterUtil.getLong(postAttachment.getFileEntryId()));
	}

	private void _testPostProductByExternalReferenceCodeImageWithFileEntryExternalReferenceCode()
		throws Exception {

		Attachment randomAttachment = _randomImageAttachment();

		FileEntry fileEntry = _dlAppLocalService.getFileEntry(
			randomAttachment.getFileEntryId());

		randomAttachment.setFileEntryExternalReferenceCode(
			fileEntry.getExternalReferenceCode());

		randomAttachment.setFileEntryId(0L);

		Attachment postAttachment =
			attachmentResource.postProductByExternalReferenceCodeImage(
				_cProduct.getExternalReferenceCode(), randomAttachment);

		assertEquals(randomAttachment, postAttachment);
		assertValid(postAttachment);
		Assert.assertEquals(
			fileEntry.getExternalReferenceCode(),
			postAttachment.getFileEntryExternalReferenceCode());
		Assert.assertEquals(
			fileEntry.getFileEntryId(),
			GetterUtil.getLong(postAttachment.getFileEntryId()));
	}

	private void _testPostProductIdAttachmentWithFileEntryExternalReferenceCode()
		throws Exception {

		Attachment randomAttachment = randomAttachment();

		FileEntry fileEntry = _dlAppLocalService.getFileEntry(
			randomAttachment.getFileEntryId());

		randomAttachment.setFileEntryExternalReferenceCode(
			fileEntry.getExternalReferenceCode());

		randomAttachment.setFileEntryId(0L);

		Attachment postAttachment = attachmentResource.postProductIdAttachment(
			_cProduct.getCProductId(), randomAttachment);

		assertEquals(randomAttachment, postAttachment);
		assertValid(postAttachment);
		Assert.assertEquals(
			fileEntry.getExternalReferenceCode(),
			postAttachment.getFileEntryExternalReferenceCode());
		Assert.assertEquals(
			fileEntry.getFileEntryId(),
			GetterUtil.getLong(postAttachment.getFileEntryId()));
	}

	private void _testPostProductIdImageWithFileEntryExternalReferenceCode()
		throws Exception {

		Attachment randomAttachment = _randomImageAttachment();

		FileEntry fileEntry = _dlAppLocalService.getFileEntry(
			randomAttachment.getFileEntryId());

		randomAttachment.setFileEntryExternalReferenceCode(
			fileEntry.getExternalReferenceCode());

		randomAttachment.setFileEntryId(0L);

		Attachment postAttachment = attachmentResource.postProductIdImage(
			_cProduct.getCProductId(), randomAttachment);

		assertEquals(randomAttachment, postAttachment);
		assertValid(postAttachment);
		Assert.assertEquals(
			fileEntry.getExternalReferenceCode(),
			postAttachment.getFileEntryExternalReferenceCode());
		Assert.assertEquals(
			fileEntry.getFileEntryId(),
			GetterUtil.getLong(postAttachment.getFileEntryId()));
	}

	@DeleteAfterTestRun
	private CPDefinition _cpDefinition;

	@DeleteAfterTestRun
	private CProduct _cProduct;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}