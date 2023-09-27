/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.counter.service.persistence;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;

import java.io.Serializable;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

/**
 * @author Patrick Brady
 */
public class IDGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(
		SharedSessionContractImplementor sharedSessionContractImplementor, Object object) {

		Class<?> clazz = object.getClass();

		String name = clazz.getName();

		int currentId = (int)CounterLocalServiceUtil.increment(name);

		return new Integer(currentId);
	}

}