/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.service.persistence.impl;

import com.liferay.osb.faro.model.FaroChannel;
import com.liferay.osb.faro.service.persistence.FaroChannelPersistence;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

/**
 * @author Matthew Kong
 * @generated
 */
public class FaroChannelFinderBaseImpl
	extends BasePersistenceImpl<FaroChannel> {

	public FaroChannelFinderBaseImpl() {
		setModelClass(FaroChannel.class);
	}

	/**
	 * Returns the faro channel persistence.
	 *
	 * @return the faro channel persistence
	 */
	public FaroChannelPersistence getFaroChannelPersistence() {
		return faroChannelPersistence;
	}

	/**
	 * Sets the faro channel persistence.
	 *
	 * @param faroChannelPersistence the faro channel persistence
	 */
	public void setFaroChannelPersistence(
		FaroChannelPersistence faroChannelPersistence) {

		this.faroChannelPersistence = faroChannelPersistence;
	}

	@BeanReference(type = FaroChannelPersistence.class)
	protected FaroChannelPersistence faroChannelPersistence;

}