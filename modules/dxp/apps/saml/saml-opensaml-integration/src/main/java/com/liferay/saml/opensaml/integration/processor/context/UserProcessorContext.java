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