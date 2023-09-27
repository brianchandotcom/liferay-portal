/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.processor.factory;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.saml.opensaml.integration.field.expression.handler.UserFieldExpressionHandler;
import com.liferay.saml.opensaml.integration.field.expression.handler.registry.UserFieldExpressionHandlerRegistry;
import com.liferay.saml.opensaml.integration.internal.processor.BaseProcessorImpl;
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
		extends BaseProcessorImpl
			<User, UserProcessorContext, UserFieldExpressionHandler,
			 UserFieldExpressionHandlerRegistry> implements UserProcessor {

		public UserProcessorImpl(
			User user,
			UserFieldExpressionHandlerRegistry
				userFieldExpressionHandlerRegistry) {

			super(user, userFieldExpressionHandlerRegistry);
		}

		public class UserBindImpl<T extends BaseModel<T>>
			extends BindImpl<T> implements UserProcessorContext.UserBind<T> {

			public UserBindImpl(
				Function<User, T> modelGetterFunction, int processingIndex,
				ProcessorContext processorContext, String publicIdentifier,
				ProcessorContext.UpdateFunction<T> updateFunction) {

				super(
					modelGetterFunction, processingIndex, processorContext,
					publicIdentifier, updateFunction);
			}

		}

		public class UserProcessorContextImpl
			extends ProcessorContextImpl implements UserProcessorContext {

			public UserProcessorContextImpl(String prefix) {
				super(prefix);
			}

			@Override
			public <T extends BaseModel<T>> UserBind<T> bind(
				Function<User, T> modelGetterFunction, int processingIndex,
				String publicIdentifier, UpdateFunction<T> updateFunction) {

				return new UserBindImpl<>(
					modelGetterFunction, processingIndex, this,
					publicIdentifier, updateFunction);
			}

			@Override
			public UserBind<User> bind(
				int processingIndex, UpdateFunction<User> updateFunction) {

				return new UserBindImpl<>(
					Function.identity(), processingIndex, this, null,
					updateFunction);
			}

		}

		@Override
		protected UserProcessorContext getProcessorContext(String prefix) {
			return new UserProcessorContextImpl(prefix);
		}

		private static final Log _log = LogFactoryUtil.getLog(
			UserProcessorImpl.class);

	}

}