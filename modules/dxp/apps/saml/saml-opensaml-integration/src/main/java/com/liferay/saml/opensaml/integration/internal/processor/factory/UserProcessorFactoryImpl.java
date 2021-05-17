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

package com.liferay.saml.opensaml.integration.internal.processor.factory;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.saml.opensaml.integration.field.expression.handler.UserFieldExpressionHandler;
import com.liferay.saml.opensaml.integration.field.expression.handler.registry.UserFieldExpressionHandlerRegistry;
import com.liferay.saml.opensaml.integration.internal.processor.ProcessorImpl;
import com.liferay.saml.opensaml.integration.processor.UserProcessor;
import com.liferay.saml.opensaml.integration.processor.context.ProcessorContext;
import com.liferay.saml.opensaml.integration.processor.context.UserProcessorContext;
import com.liferay.saml.opensaml.integration.processor.factory.UserProcessorFactory;

import java.util.function.Function;

import org.osgi.service.component.annotations.Component;

/**
 * @author Stian Sigvartsen
 */
@Component(service = UserProcessorFactory.class)
public class UserProcessorFactoryImpl implements UserProcessorFactory {

	@Override
	public UserProcessor create(
		User user,
		UserFieldExpressionHandlerRegistry userFieldExpressionHandlerRegistry) {

		return new UserProcessorImpl(user, userFieldExpressionHandlerRegistry);
	}

	public static class UserProcessorImpl
		extends ProcessorImpl
			<User, UserProcessorContext, UserFieldExpressionHandler,
			 UserFieldExpressionHandlerRegistry> implements UserProcessor {

		public UserProcessorImpl(
			User model,
			UserFieldExpressionHandlerRegistry fieldExpressionHandlerRegistry) {

			super(model, fieldExpressionHandlerRegistry);
		}

		public class UserBindImpl<T extends BaseModel<T>>
			extends BindImpl<T> implements UserProcessorContext.UserBind<T> {

			public UserBindImpl(
				ProcessorContext processorContext, String publicIdentifier,
				Function<User, T> modelGetter, int processingIndex,
				ProcessorContext.UpdateFunction<T> updateFunction) {

				super(
					processorContext, publicIdentifier, modelGetter,
					processingIndex, updateFunction);
			}

		}

		public class UserProcessorContextImpl
			extends ProcessorContextImpl implements UserProcessorContext {

			public UserProcessorContextImpl(String prefix) {
				super(prefix);
			}

			@Override
			public UserBind<User> bind(
				int processingIndex, UpdateFunction<User> updateFunction) {

				return new UserBindImpl<>(
					this, null, Function.identity(), processingIndex,
					updateFunction);
			}

			@Override
			public <T extends BaseModel<T>> UserBind<T> bind(
				String publicIdentifier, Function<User, T> modelGetter,
				int processingIndex, UpdateFunction<T> updateFunction) {

				return new UserBindImpl<>(
					this, publicIdentifier, modelGetter, processingIndex,
					updateFunction);
			}

		}

		@Override
		protected UserProcessorContext getProcessorContext(String prefix) {
			return new UserProcessorContextImpl(prefix);
		}

		private static final Log _log = LogFactoryUtil.getLog(
			ProcessorImpl.class);

	}

}