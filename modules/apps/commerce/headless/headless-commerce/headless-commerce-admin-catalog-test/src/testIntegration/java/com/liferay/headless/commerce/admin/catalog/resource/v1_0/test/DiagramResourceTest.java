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
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CPAttachmentFileEntryLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.Diagram;
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

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zoltán Takács
 */
@RunWith(Arquillian.class)
public class DiagramResourceTest extends BaseDiagramResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_cpInstance = CPTestUtil.addCPInstanceWithRandomSku(
			testGroup.getGroupId(), BigDecimal.TEN);

		_cpDefinition = _cpInstance.getCPDefinition();

		_cProduct = _cpDefinition.getCProduct();

		_user = UserTestUtil.addUser(testCompany);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			_user.getUserId());
	}

	@Override
	@Test
	public void testPatchDiagram() throws Exception {
		Diagram postDiagram = diagramResource.postProductIdDiagram(
			_cProduct.getCProductId(), randomDiagram());

		Diagram randomPatchDiagram = randomPatchDiagram();

		diagramResource.patchDiagram(postDiagram.getId(), randomPatchDiagram);

		Diagram expectedDiagram = postDiagram.clone();

		BeanTestUtil.copyProperties(randomPatchDiagram, expectedDiagram);

		Diagram getDiagram = diagramResource.getProductIdDiagram(
			_cProduct.getCProductId());

		assertEquals(expectedDiagram, getDiagram);
		assertValid(getDiagram);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"color", "productExternalReferenceCode", "productId", "radius",
			"type"
		};
	}

	@Override
	protected Diagram randomDiagram() throws Exception {
		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			RandomTestUtil.randomString(), _user.getUserId(),
			testGroup.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			null, RandomTestUtil.nextDate(), _serviceContext);

		CPAttachmentFileEntry cpAttachmentFileEntry =
			_cpAttachmentFileEntryLocalService.addCPAttachmentFileEntry(
				RandomTestUtil.randomString(), _user.getUserId(),
				testGroup.getGroupId(),
				_classNameLocalService.getClassNameId(
					_cProduct.getModelClass()),
				_cProduct.getPrimaryKey(), fileEntry.getFileEntryId(), false,
				null, 1, 1, 2022, 12, 0, 0, 0, 0, 0, 0, true,
				RandomTestUtil.randomLocaleStringMap(), null,
				RandomTestUtil.randomDouble(),
				CPAttachmentFileEntryConstants.TYPE_IMAGE, _serviceContext);

		_cpAttachmentFileEntries.add(cpAttachmentFileEntry);

		return new Diagram() {
			{
				color = StringUtil.toLowerCase(RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				imageId = cpAttachmentFileEntry.getCPAttachmentFileEntryId();
				imageURL = "https://picsum.photos/50";
				productExternalReferenceCode =
					_cProduct.getExternalReferenceCode();
				productId = _cProduct.getCProductId();
				radius = RandomTestUtil.randomDouble();
				type = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	@Override
	protected Diagram testGetProductByExternalReferenceCodeDiagram_addDiagram()
		throws Exception {

		return diagramResource.postProductByExternalReferenceCodeDiagram(
			_cProduct.getExternalReferenceCode(), randomDiagram());
	}

	@Override
	protected String
			testGetProductByExternalReferenceCodeDiagram_getExternalReferenceCode()
		throws Exception {

		return _cProduct.getExternalReferenceCode();
	}

	@Override
	protected Diagram testGetProductIdDiagram_addDiagram() throws Exception {
		return diagramResource.postProductIdDiagram(
			_cProduct.getCProductId(), randomDiagram());
	}

	@Override
	protected Diagram testGraphQLDiagram_addDiagram() throws Exception {
		return diagramResource.postProductIdDiagram(
			_cProduct.getCProductId(), randomDiagram());
	}

	@Override
	protected String
			testGraphQLGetProductByExternalReferenceCodeDiagram_getExternalReferenceCode()
		throws Exception {

		return _cProduct.getExternalReferenceCode();
	}

	@Override
	protected Diagram testPostProductByExternalReferenceCodeDiagram_addDiagram(
			Diagram diagram)
		throws Exception {

		return diagramResource.postProductByExternalReferenceCodeDiagram(
			_cProduct.getExternalReferenceCode(), diagram);
	}

	@Override
	protected Diagram testPostProductIdDiagram_addDiagram(Diagram diagram)
		throws Exception {

		return diagramResource.postProductIdDiagram(
			_cProduct.getCProductId(), diagram);
	}

	@Inject
	private static ClassNameLocalService _classNameLocalService;

	@Inject
	private static CPAttachmentFileEntryLocalService
		_cpAttachmentFileEntryLocalService;

	@Inject
	private static DLAppLocalService _dlAppLocalService;

	@DeleteAfterTestRun
	private final List<CPAttachmentFileEntry> _cpAttachmentFileEntries =
		new ArrayList<>();

	@DeleteAfterTestRun
	private CPDefinition _cpDefinition;

	@DeleteAfterTestRun
	private CPInstance _cpInstance;

	@DeleteAfterTestRun
	private CProduct _cProduct;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}