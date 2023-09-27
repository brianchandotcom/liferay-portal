/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.processor.context;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;

import java.util.function.Function;

/**
 * @author Stian Sigvartsen
 */
public interface UserProcessorContext extends ProcessorContext<User> {

	@Override
	public <T extends BaseModel<T>> UserBind<T> bind(
		Function<User, T> modelGetterFunction, int processingIndex,
		String publicIdentifier, UpdateFunction<T> updateFunction);

	@Override
	public UserBind<User> bind(
		int processingIndex, UpdateFunction<User> updateFunction);

	public interface UserBind<T extends BaseModel<T>> extends Bind<T> {
	}

}