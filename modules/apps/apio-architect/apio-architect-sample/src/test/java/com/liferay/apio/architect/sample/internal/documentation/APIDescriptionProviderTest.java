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

package com.liferay.apio.architect.sample.internal.documentation;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.core.Is.is;

import com.liferay.apio.architect.documentation.APIDescription;
import com.liferay.apio.architect.provider.Provider;

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class APIDescriptionProviderTest {

	@Test
	public void testAPIDescriptionIsNotEmpty() {
		Provider<APIDescription> provider = new APIDescriptionProvider();

		APIDescription apiDescription = provider.createContext(null);

		assertThat(apiDescription.get(), is(not(emptyString())));
	}

}