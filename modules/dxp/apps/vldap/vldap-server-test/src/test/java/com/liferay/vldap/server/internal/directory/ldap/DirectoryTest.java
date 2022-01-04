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

package com.liferay.vldap.server.internal.directory.ldap;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.comparator.UserScreenNameComparator;
import com.liferay.vldap.server.internal.BaseVLDAPTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.directory.api.ldap.model.entry.Attribute;
import org.apache.directory.api.ldap.model.entry.Entry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Jonathan McCann
 */
@RunWith(PowerMockRunner.class)
public class DirectoryTest extends BaseVLDAPTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_directory = new TopDirectory();

		_directory.addAttribute("testAttribute", "test");
		_directory.addAttribute("binaryAttribute", new byte[0]);
	}

	@Test
	public void testAddMemberAttributes() throws Exception {
		_setUpUser();

		_directory.addMemberAttributes(
			"Liferay", company, new LinkedHashMap<String, Object>());

		List<com.liferay.vldap.server.internal.directory.ldap.Attribute>
			attributes = _directory.getAttributes("member");

		com.liferay.vldap.server.internal.directory.ldap.Attribute attribute =
			attributes.get(0);

		Assert.assertEquals(
			"cn=testScreenName,ou=Users,ou=liferay.com,o=Liferay",
			attribute.getValue());
	}

	@Test
	public void testContainsIgnoreCase() {
		List<String> searchRequestAttributes = null;

		Assert.assertFalse(
			_directory.containsIgnoreCase(searchRequestAttributes, "Liferay"));

		searchRequestAttributes = new ArrayList<>();

		searchRequestAttributes.add("Liferay");

		Assert.assertTrue(
			_directory.containsIgnoreCase(searchRequestAttributes, "liferay"));
		Assert.assertTrue(
			_directory.containsIgnoreCase(searchRequestAttributes, "Liferay"));
		Assert.assertTrue(
			_directory.containsIgnoreCase(searchRequestAttributes, "LIFERAY"));
		Assert.assertFalse(
			_directory.containsIgnoreCase(searchRequestAttributes, "test"));
	}

	@Test
	public void testGetAttributes() {
		List<com.liferay.vldap.server.internal.directory.ldap.Attribute>
			attributes = _directory.getAttributes();

		Assert.assertEquals(attributes.toString(), 5, attributes.size());

		attributes = _directory.getAttributes("testAttribute");

		com.liferay.vldap.server.internal.directory.ldap.Attribute attribute =
			attributes.get(0);

		Assert.assertEquals("testAttribute", attribute.getAttributeId());
		Assert.assertEquals("test", attribute.getValue());

		attributes = _directory.getAttributes("invalidAttribute");

		Assert.assertEquals(attributes.toString(), 0, attributes.size());
	}

	@Test
	public void testHasAttribute() {
		Assert.assertTrue(_directory.hasAttribute("testAttribute"));
		Assert.assertTrue(_directory.hasAttribute("testAttribute", "test"));
		Assert.assertFalse(_directory.hasAttribute("invalidAttribute"));
		Assert.assertFalse(
			_directory.hasAttribute("testAttribute", "invalidValue"));
		Assert.assertFalse(
			_directory.hasAttribute("invalidAttribute", "invalidValue"));
	}

	@Test
	public void testToEntryEmptySearchRequestAttributes() throws Exception {
		List<String> searchRequestAttributes = new ArrayList<>();

		Entry entry = _directory.toEntry(searchRequestAttributes);

		Collection<Attribute> attributes = entry.getAttributes();

		Assert.assertEquals(attributes.toString(), 4, attributes.size());
	}

	@Test
	public void testToEntryStarSearchRequestAttribute() throws Exception {
		Entry entry = _directory.toEntry(Arrays.asList("*"));

		Collection<Attribute> attributes = entry.getAttributes();

		Assert.assertEquals(attributes.toString(), 4, attributes.size());
	}

	@Test
	public void testToEntryWithHasSubordinates() throws Exception {
		Entry entry = _directory.toEntry(Arrays.asList("hassubordinates"));

		Collection<Attribute> attributes = entry.getAttributes();

		Assert.assertEquals(attributes.toString(), 1, attributes.size());
	}

	@Test
	public void testToEntryWithInvalidSearchRequestAttribute()
		throws Exception {

		Entry entry = _directory.toEntry(Arrays.asList("test"));

		Collection<Attribute> attributes = entry.getAttributes();

		Assert.assertEquals(attributes.toString(), 0, attributes.size());
	}

	@Test
	public void testToEntryWithValidSearchRequestAttribute() throws Exception {
		Entry entry = _directory.toEntry(Arrays.asList("testAttribute"));

		Collection<Attribute> attributes = entry.getAttributes();

		Assert.assertEquals(attributes.toString(), 1, attributes.size());
	}

	private void _setUpUser() {
		User user = mock(User.class);

		when(
			user.getScreenName()
		).thenReturn(
			"testScreenName"
		);

		when(
			userLocalService.search(
				Mockito.anyLong(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyInt(), Mockito.any(LinkedHashMap.class),
				Mockito.anyBoolean(), Mockito.anyInt(), Mockito.anyInt(),
				Mockito.any(UserScreenNameComparator.class))
		).thenReturn(
			Arrays.asList(user)
		);
	}

	private static Directory _directory;

}