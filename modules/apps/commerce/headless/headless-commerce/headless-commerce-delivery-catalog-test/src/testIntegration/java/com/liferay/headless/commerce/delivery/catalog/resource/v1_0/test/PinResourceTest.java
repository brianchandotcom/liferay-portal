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

package com.liferay.headless.commerce.delivery.catalog.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramPin;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramPinLocalService;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.headless.commerce.delivery.catalog.client.dto.v1_0.Pin;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Andrea Sbarra
 */
@RunWith(Arquillian.class)
public class PinResourceTest extends BasePinResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			testGroup.getGroupId(), RandomTestUtil.randomString());
		_cpDefinition = CPTestUtil.addCPDefinition(
			testGroup.getGroupId(), "simple", true, false);
	}

	@Override
	protected Pin testGetChannelProductPinsPage_addPin(
			Long channelId, Long productId, Pin pin)
		throws Exception {

		CSDiagramPin csDiagramPin = _csDiagramPinLocalService.addCSDiagramPin(
			_user.getUserId(), _cpDefinition.getCPDefinitionId(),
			pin.getPositionX(), pin.getPositionY(), pin.getSequence());

		_csDiagramPins.add(csDiagramPin);

		return new Pin() {
			{
				id = csDiagramPin.getCSDiagramPinId();
				positionX = csDiagramPin.getPositionX();
				positionY = csDiagramPin.getPositionY();
				sequence = csDiagramPin.getSequence();
			}
		};
	}

	@Override
	protected Long testGetChannelProductPinsPage_getChannelId()
		throws Exception {

		return _commerceChannel.getCommerceChannelId();
	}

	@Override
	protected Long testGetChannelProductPinsPage_getProductId()
		throws Exception {

		return _cpDefinition.getCProductId();
	}

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@DeleteAfterTestRun
	private CPDefinition _cpDefinition;

	@Inject
	private CSDiagramPinLocalService _csDiagramPinLocalService;

	@DeleteAfterTestRun
	private final List<CSDiagramPin> _csDiagramPins = new ArrayList<>();

	@DeleteAfterTestRun
	private User _user;

}