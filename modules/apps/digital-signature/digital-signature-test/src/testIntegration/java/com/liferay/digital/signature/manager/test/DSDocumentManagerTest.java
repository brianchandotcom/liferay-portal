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

package com.liferay.digital.signature.manager.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.digital.signature.manager.DSDocumentManager;
import com.liferay.digital.signature.manager.DSEnvelopeManager;
import com.liferay.digital.signature.model.DSDocument;
import com.liferay.digital.signature.model.DSEnvelope;
import com.liferay.digital.signature.model.DSRecipient;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Collections;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class DSDocumentManagerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testGetDSDocumentsAsBytes() throws Exception {
		DSEnvelope dsEnvelope = _dsEnvelopeManager.addDSEnvelope(
			TestPropsValues.getCompanyId(), TestPropsValues.getGroupId(),
			new DSEnvelope() {
				{
					dsDocuments = Collections.singletonList(
						new DSDocument() {
							{
								data = Base64.encode(
									FileUtil.getBytes(
										getClass(),
										"dependencies/Document.pdf"));
								dsDocumentId = "1";
								name = RandomTestUtil.randomString();
							}
						});
					dsRecipients = Collections.singletonList(
						new DSRecipient() {
							{
								dsRecipientId = "1";
								emailAddress = "test@liferay.com";
								name = RandomTestUtil.randomString();
							}
						});
					emailBlurb = RandomTestUtil.randomString();
					emailSubject = RandomTestUtil.randomString();
					status = "sent";
				}
			});

		byte[] dsDocumentsAsBytes = _dsDocumentManager.getDSDocumentsAsBytes(
			TestPropsValues.getCompanyId(), TestPropsValues.getGroupId(),
			dsEnvelope.getDSEnvelopeId());

		Assert.assertTrue(ArrayUtil.isNotEmpty(dsDocumentsAsBytes));

		_dsEnvelopeManager.deleteDSEnvelopes(
			TestPropsValues.getCompanyId(), TestPropsValues.getGroupId(),
			dsEnvelope.getDSEnvelopeId());
	}

	@Inject
	private DSDocumentManager _dsDocumentManager;

	@Inject
	private DSEnvelopeManager _dsEnvelopeManager;

}