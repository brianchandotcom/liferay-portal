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

package com.liferay.headless.commerce.admin.catalog.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.constants.CPAttachmentFileEntryConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.Attachment;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.AttachmentBase64;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.AttachmentUrl;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Zoltán Takács
 */
@RunWith(Arquillian.class)
public class AttachmentResourceTest extends BaseAttachmentResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_cpInstance = CPTestUtil.addCPInstanceWithRandomSku(
			testGroup.getGroupId(), BigDecimal.TEN);

		_cpDefinition = _cpInstance.getCPDefinition();

		_cProduct = _cpDefinition.getCProduct();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"externalReferenceCode", "priority", "title"};
	}

	@Override
	protected Attachment randomAttachment() throws Exception {
		return new Attachment() {
			{
				attachment = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				cdnEnabled = false;
				cdnURL = StringUtil.toLowerCase(RandomTestUtil.randomString());
				contentType = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				displayDate = RandomTestUtil.nextDate();
				expirationDate = RandomTestUtil.nextDate();
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				neverExpire = true;
				priority = RandomTestUtil.randomDouble();
				src = StringUtil.toLowerCase(RandomTestUtil.randomString());
				title = LanguageUtils.getLanguageIdMap(
					RandomTestUtil.randomLocaleStringMap());
				type = CPAttachmentFileEntryConstants.TYPE_OTHER;
			}
		};
	}

	@Override
	protected Attachment
			testGetProductByExternalReferenceCodeAttachmentsPage_addAttachment(
				String externalReferenceCode, Attachment attachment)
		throws Exception {

		return attachmentResource.postProductByExternalReferenceCodeAttachment(
			externalReferenceCode, attachment);
	}

	@Override
	protected String
			testGetProductByExternalReferenceCodeAttachmentsPage_getExternalReferenceCode()
		throws Exception {

		return _cProduct.getExternalReferenceCode();
	}

	@Override
	protected Attachment
			testGetProductByExternalReferenceCodeImagesPage_addAttachment(
				String externalReferenceCode, Attachment attachment)
		throws Exception {

		return attachmentResource.postProductByExternalReferenceCodeImage(
			externalReferenceCode, attachment);
	}

	@Override
	protected String
			testGetProductByExternalReferenceCodeImagesPage_getExternalReferenceCode()
		throws Exception {

		return _cProduct.getExternalReferenceCode();
	}

	@Override
	protected Attachment testGetProductIdAttachmentsPage_addAttachment(
			Long id, Attachment attachment)
		throws Exception {

		return attachmentResource.postProductIdAttachment(id, attachment);
	}

	@Override
	protected Long testGetProductIdAttachmentsPage_getId() throws Exception {
		return _cProduct.getCProductId();
	}

	@Override
	protected Attachment testGetProductIdImagesPage_addAttachment(
			Long id, Attachment attachment)
		throws Exception {

		return attachmentResource.postProductIdImage(id, attachment);
	}

	@Override
	protected Long testGetProductIdImagesPage_getId() throws Exception {
		return _cProduct.getCProductId();
	}

	@Override
	protected Attachment
			testPostProductByExternalReferenceCodeAttachment_addAttachment(
				Attachment attachment)
		throws Exception {

		return attachmentResource.postProductByExternalReferenceCodeAttachment(
			_cProduct.getExternalReferenceCode(), attachment);
	}

	@Override
	protected Attachment
			testPostProductByExternalReferenceCodeAttachmentByBase64_addAttachment(
				Attachment attachment)
		throws Exception {

		return attachmentResource.
			postProductByExternalReferenceCodeAttachmentByBase64(
				_cProduct.getExternalReferenceCode(),
				_toAttachmentBase64(attachment));
	}

	@Override
	protected Attachment
			testPostProductByExternalReferenceCodeAttachmentByUrl_addAttachment(
				Attachment attachment)
		throws Exception {

		return attachmentResource.
			postProductByExternalReferenceCodeAttachmentByUrl(
				_cProduct.getExternalReferenceCode(),
				_toAttachmentUrl(attachment));
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
	protected Attachment
			testPostProductByExternalReferenceCodeImageByBase64_addAttachment(
				Attachment attachment)
		throws Exception {

		return attachmentResource.
			postProductByExternalReferenceCodeImageByBase64(
				_cProduct.getExternalReferenceCode(),
				_toAttachmentBase64(attachment));
	}

	@Override
	protected Attachment
			testPostProductByExternalReferenceCodeImageByUrl_addAttachment(
				Attachment attachment)
		throws Exception {

		return attachmentResource.
			postProductByExternalReferenceCodeAttachmentByUrl(
				_cProduct.getExternalReferenceCode(),
				_toAttachmentUrl(attachment));
	}

	@Override
	protected Attachment testPostProductIdAttachment_addAttachment(
			Attachment attachment)
		throws Exception {

		return attachmentResource.postProductIdAttachment(
			_cProduct.getCProductId(), attachment);
	}

	@Override
	protected Attachment testPostProductIdAttachmentByBase64_addAttachment(
			Attachment attachment)
		throws Exception {

		return attachmentResource.postProductIdAttachmentByBase64(
			_cProduct.getCProductId(), _toAttachmentBase64(attachment));
	}

	@Override
	protected Attachment testPostProductIdAttachmentByUrl_addAttachment(
			Attachment attachment)
		throws Exception {

		return attachmentResource.postProductIdAttachmentByUrl(
			_cProduct.getCProductId(), _toAttachmentUrl(attachment));
	}

	@Override
	protected Attachment testPostProductIdImage_addAttachment(
			Attachment attachment)
		throws Exception {

		return attachmentResource.postProductIdImage(
			_cProduct.getCProductId(), attachment);
	}

	@Override
	protected Attachment testPostProductIdImageByBase64_addAttachment(
			Attachment attachment)
		throws Exception {

		return attachmentResource.postProductIdImageByBase64(
			_cProduct.getCProductId(), _toAttachmentBase64(attachment));
	}

	@Override
	protected Attachment testPostProductIdImageByUrl_addAttachment(
			Attachment attachment)
		throws Exception {

		return attachmentResource.postProductIdImageByUrl(
			_cProduct.getCProductId(), _toAttachmentUrl(attachment));
	}

	private AttachmentBase64 _toAttachmentBase64(Attachment catalogAttachment) {
		return new AttachmentBase64() {
			{
				attachment =
					"R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7";
				contentType = catalogAttachment.getContentType();
				displayDate = catalogAttachment.getDisplayDate();
				expirationDate = catalogAttachment.getExpirationDate();
				externalReferenceCode =
					catalogAttachment.getExternalReferenceCode();
				id = catalogAttachment.getId();
				neverExpire = catalogAttachment.getNeverExpire();
				priority = catalogAttachment.getPriority();
				title = catalogAttachment.getTitle();
				type = catalogAttachment.getType();
			}
		};
	}

	private AttachmentUrl _toAttachmentUrl(Attachment catalogAttachment) {
		return new AttachmentUrl() {
			{
				contentType = catalogAttachment.getContentType();
				displayDate = catalogAttachment.getDisplayDate();
				expirationDate = catalogAttachment.getExpirationDate();
				externalReferenceCode =
					catalogAttachment.getExternalReferenceCode();
				id = catalogAttachment.getId();
				neverExpire = catalogAttachment.getNeverExpire();
				priority = catalogAttachment.getPriority();
				src = "https://picsum.photos/50";
				title = catalogAttachment.getTitle();
				type = catalogAttachment.getType();
			}
		};
	}

	@DeleteAfterTestRun
	private CPDefinition _cpDefinition;

	@DeleteAfterTestRun
	private CPInstance _cpInstance;

	@DeleteAfterTestRun
	private CProduct _cProduct;

}