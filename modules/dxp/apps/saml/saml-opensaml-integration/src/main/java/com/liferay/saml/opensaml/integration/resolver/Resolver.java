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

package com.liferay.saml.opensaml.integration.resolver;

import com.liferay.saml.opensaml.integration.internal.resolver.SAMLCommands;

/**
 * * @author Carlos Sierra Andrés
 */
public interface Resolver {

	public interface SAMLCommand<T, R extends Resolver> {
	}

	public interface SAMLContext<R extends Resolver> {

		public <T> T resolve(SAMLCommand<T, ? super R> samlCommand);

		public default String resolvePeerEntityId() {
			return resolve(SAMLCommands.peerEntityId);
		}

		public default String resolveSubjectNameFormat() {
			return resolve(SAMLCommands.subjectNameFormat);
		}

		public default String resolveSubjectNameIdentifier() {
			return resolve(SAMLCommands.subjectNameIdentifier);
		}

	}

}