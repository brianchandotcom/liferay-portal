/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		public default String resolveSubjectNameQualifier() {
			return resolve(SAMLCommands.subjectNameQualifier);
		}

	}

}