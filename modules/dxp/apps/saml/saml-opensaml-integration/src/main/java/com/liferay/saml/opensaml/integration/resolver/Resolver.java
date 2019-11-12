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

package com.liferay.saml.opensaml.integration.resolver;

import com.liferay.saml.opensaml.integration.internal.resolver.SAMLCommands;

/**
 * * @author Carlos Sierra Andrés
 *
 * @author Brian Wing Shun Chan
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