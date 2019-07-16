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

package com.liferay.fragment.internal.validator;

import com.liferay.fragment.exception.FragmentEntryConfigurationException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.util.FileImpl;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Rubén Pulido
 */
public class FragmentEntryValidatorImplTest {

	@Before
	public void setUp() {
		new FileUtil().setFile(new FileImpl());

		_fragmentEntryValidatorImpl = new FragmentEntryValidatorImpl();
	}

	@Test
	public void testValidateConfigurationInvalidFieldSetsMissing()
		throws Exception {

		expectedException.expect(FragmentEntryConfigurationException.class);
		expectedException.expectMessage(
			"org.everit.json.schema.ValidationException: #: required key " +
				"[fieldSets] not found");

		_fragmentEntryValidatorImpl.validateConfiguration(
			_getFileContent("configuration-invalid-field-sets-missing.json"));
	}

	@Test
	public void testValidateConfigurationInvalidMissingFieldSetName()
		throws Exception {

		expectedException.expect(FragmentEntryConfigurationException.class);
		expectedException.expectMessage(
			"org.everit.json.schema.ValidationException: #/fieldSets/0: " +
				"required key [name] not found");

		_fragmentEntryValidatorImpl.validateConfiguration(
			_getFileContent(
				"configuration-invalid-field-set-name-missing.json"));
	}

	@Test
	public void testValidateConfigurationValidComplete() throws Exception {
		_fragmentEntryValidatorImpl.validateConfiguration(
			_getFileContent("configuration-valid-complete.json"));
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private String _getFileContent(String fileName) throws Exception {
		return new String(
			FileUtil.getBytes(getClass(), "dependencies/" + fileName));
	}

	private FragmentEntryValidatorImpl _fragmentEntryValidatorImpl;

}