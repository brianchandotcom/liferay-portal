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

package com.liferay.document.library.opener.uad.test;

import com.liferay.document.library.opener.model.DLOpenerFileEntryReference;

import java.util.List;

import org.junit.Assume;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true, service = DLOpenerFileEntryReferenceUADTestHelper.class
)
public class DLOpenerFileEntryReferenceUADTestHelper {

	/**
	 * Implement addDLOpenerFileEntryReference() to enable some UAD tests.
	 *
	 * <p>
	 * Several UAD tests depend on creating one or more valid DLOpenerFileEntryReferences with a specified user ID in order to execute correctly. Implement addDLOpenerFileEntryReference() such that it creates a valid DLOpenerFileEntryReference with the specified user ID value and returns it in order to enable the UAD tests that depend on it.
	 * </p>
	 */
	public DLOpenerFileEntryReference addDLOpenerFileEntryReference(long userId)
		throws Exception {

		Assume.assumeTrue(false);

		return null;
	}

	/**
	 * Implement cleanUpDependencies(List<DLOpenerFileEntryReference> dlOpenerFileEntryReferences) if tests require additional tear down logic.
	 *
	 * <p>
	 * Several UAD tests depend on creating one or more valid DLOpenerFileEntryReferences with specified user ID and status by user ID in order to execute correctly. Implement cleanUpDependencies(List<DLOpenerFileEntryReference> dlOpenerFileEntryReferences) such that any additional objects created during the construction of dlOpenerFileEntryReferences are safely removed.
	 * </p>
	 */
	public void cleanUpDependencies(
			List<DLOpenerFileEntryReference> dlOpenerFileEntryReferences)
		throws Exception {
	}

}