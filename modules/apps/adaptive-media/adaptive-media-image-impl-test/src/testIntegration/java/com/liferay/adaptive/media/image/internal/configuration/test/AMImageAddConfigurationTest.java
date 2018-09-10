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

package com.liferay.adaptive.media.image.internal.configuration.test;

import com.liferay.adaptive.media.exception.AMImageConfigurationException;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@RunWith(Arquillian.class)
public class AMImageAddConfigurationTest
	extends BaseAMImageConfigurationTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddConfigurationEntryWithAlphanumericCharactersUuid()
		throws Exception {

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		String uuid = "one-2-three_four";

		AMImageConfigurationEntry amImageConfigurationEntry =
			_amImageConfigurationHelper.addAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "onedesc", uuid,
				properties);

		Assert.assertEquals(uuid, amImageConfigurationEntry.getUUID());
	}

	@Test
	public void testAddConfigurationEntryWithBlankDescription()
		throws Exception {

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", StringPool.BLANK, "1",
			properties);

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertTrue(amImageConfigurationEntryOptional.isPresent());

		AMImageConfigurationEntry amImageConfigurationEntry =
			amImageConfigurationEntryOptional.get();

		Assert.assertEquals(
			StringPool.BLANK, amImageConfigurationEntry.getDescription());
	}

	@Test
	public void testAddConfigurationEntryWithBlankMaxHeight() throws Exception {
		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "");
		properties.put("max-width", "100");

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertTrue(amImageConfigurationEntryOptional.isPresent());

		AMImageConfigurationEntry amImageConfigurationEntry =
			amImageConfigurationEntryOptional.get();

		Map<String, String> actualProperties =
			amImageConfigurationEntry.getProperties();

		Assert.assertEquals("0", actualProperties.get("max-height"));
		Assert.assertEquals("100", actualProperties.get("max-width"));
	}

	@Test(
		expected = AMImageConfigurationException.RequiredWidthOrHeightException.class
	)
	public void testAddConfigurationEntryWithBlankMaxHeightAndBlankMaxWidth()
		throws Exception {

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "");
		properties.put("max-width", "");

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1", properties);
	}

	@Test(
		expected = AMImageConfigurationException.RequiredWidthOrHeightException.class
	)
	public void testAddConfigurationEntryWithBlankMaxHeightAndZeroMaxWidth()
		throws Exception {

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "");
		properties.put("max-width", "0");

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1", properties);
	}

	@Test(
		expected = AMImageConfigurationException.RequiredWidthOrHeightException.class
	)
	public void testAddConfigurationEntryWithBlankMaxHeightOnly()
		throws Exception {

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "");

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1", properties);
	}

	@Test
	public void testAddConfigurationEntryWithBlankMaxWidth() throws Exception {
		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "");

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertTrue(amImageConfigurationEntryOptional.isPresent());

		AMImageConfigurationEntry amImageConfigurationEntry =
			amImageConfigurationEntryOptional.get();

		Map<String, String> actualProperties =
			amImageConfigurationEntry.getProperties();

		Assert.assertEquals("100", actualProperties.get("max-height"));
		Assert.assertEquals("0", actualProperties.get("max-width"));
	}

	@Test(
		expected = AMImageConfigurationException.RequiredWidthOrHeightException.class
	)
	public void testAddConfigurationEntryWithBlankMaxWidthOnly()
		throws Exception {

		Map<String, String> properties = new HashMap<>();

		properties.put("max-width", "");

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1", properties);
	}

	@Test(expected = AMImageConfigurationException.InvalidNameException.class)
	public void testAddConfigurationEntryWithBlankName() throws Exception {
		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), StringPool.BLANK, "desc", "1",
			properties);
	}

	@Test(expected = AMImageConfigurationException.InvalidUuidException.class)
	public void testAddConfigurationEntryWithBlankUuid() throws Exception {
		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", StringPool.BLANK,
			properties);
	}

	@Test
	public void testAddConfigurationEntryWithColonSemicolonDescription()
		throws Exception {

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc:;desc", "1",
			properties);

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertTrue(amImageConfigurationEntryOptional.isPresent());

		AMImageConfigurationEntry amImageConfigurationEntry =
			amImageConfigurationEntryOptional.get();

		Assert.assertEquals(
			"desc:;desc", amImageConfigurationEntry.getDescription());
	}

	@Test
	public void testAddConfigurationEntryWithColonSemicolonName()
		throws Exception {

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one:;one", "desc", "1",
			properties);

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertTrue(amImageConfigurationEntryOptional.isPresent());

		AMImageConfigurationEntry amImageConfigurationEntry =
			amImageConfigurationEntryOptional.get();

		Assert.assertEquals("one:;one", amImageConfigurationEntry.getName());
	}

	@Test
	public void testAddConfigurationEntryWithExistingDisabledConfiguration()
		throws Exception {

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "onedesc", "1", properties);

		_amImageConfigurationHelper.disableAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "1");

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "two", "twodesc", "2", properties);

		Collection<AMImageConfigurationEntry> amImageConfigurationEntries =
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				TestPropsValues.getCompanyId(),
				amImageConfigurationEntry -> true);

		Assert.assertEquals(
			amImageConfigurationEntries.toString(), 2,
			amImageConfigurationEntries.size());

		Iterator<AMImageConfigurationEntry> iterator =
			amImageConfigurationEntries.iterator();

		AMImageConfigurationEntry amImageConfigurationEntry = iterator.next();

		Assert.assertEquals("1", amImageConfigurationEntry.getUUID());

		amImageConfigurationEntry = iterator.next();

		Assert.assertEquals("2", amImageConfigurationEntry.getUUID());
	}

	@Test
	public void testAddConfigurationEntryWithMaxHeightOnly() throws Exception {
		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertTrue(amImageConfigurationEntryOptional.isPresent());

		AMImageConfigurationEntry amImageConfigurationEntry =
			amImageConfigurationEntryOptional.get();

		Map<String, String> actualProperties =
			amImageConfigurationEntry.getProperties();

		Assert.assertEquals("100", actualProperties.get("max-height"));
		Assert.assertEquals("0", actualProperties.get("max-width"));
	}

	@Test
	public void testAddConfigurationEntryWithMaxWidthOnly() throws Exception {
		Map<String, String> properties = new HashMap<>();

		properties.put("max-width", "100");

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertTrue(amImageConfigurationEntryOptional.isPresent());

		AMImageConfigurationEntry amImageConfigurationEntry =
			amImageConfigurationEntryOptional.get();

		Map<String, String> actualProperties =
			amImageConfigurationEntry.getProperties();

		Assert.assertEquals("0", actualProperties.get("max-height"));
		Assert.assertEquals("100", actualProperties.get("max-width"));
	}

	@Test(expected = AMImageConfigurationException.InvalidHeightException.class)
	public void testAddConfigurationEntryWithNegativeNumberMaxHeight()
		throws Exception {

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "-10");

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1", properties);
	}

	@Test(expected = AMImageConfigurationException.InvalidWidthException.class)
	public void testAddConfigurationEntryWithNegativeNumberMaxWidth()
		throws Exception {

		Map<String, String> properties = new HashMap<>();

		properties.put("max-width", "-10");

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1", properties);
	}

	@Test(expected = AMImageConfigurationException.InvalidUuidException.class)
	public void testAddConfigurationEntryWithNonalphanumericCharactersUuid()
		throws Exception {

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "onedesc", "a3%&!2",
			properties);
	}

	@Test(expected = AMImageConfigurationException.InvalidHeightException.class)
	public void testAddConfigurationEntryWithNotNumberMaxHeight()
		throws Exception {

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "Invalid");

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1", properties);
	}

	@Test(expected = AMImageConfigurationException.InvalidWidthException.class)
	public void testAddConfigurationEntryWithNotNumberMaxWidth()
		throws Exception {

		Map<String, String> properties = new HashMap<>();

		properties.put("max-width", "Invalid");

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1", properties);
	}

	@Test(
		expected = AMImageConfigurationException.RequiredWidthOrHeightException.class
	)
	public void testAddConfigurationEntryWithoutMaxHeightNorMaxWidth()
		throws Exception {

		Map<String, String> properties = new HashMap<>();

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1", properties);
	}

	@Test
	public void testAddConfigurationEntryWithZeroMaxHeight() throws Exception {
		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "0");
		properties.put("max-width", "100");

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertTrue(amImageConfigurationEntryOptional.isPresent());

		AMImageConfigurationEntry amImageConfigurationEntry =
			amImageConfigurationEntryOptional.get();

		Map<String, String> actualProperties =
			amImageConfigurationEntry.getProperties();

		Assert.assertEquals("0", actualProperties.get("max-height"));
		Assert.assertEquals("100", actualProperties.get("max-width"));
	}

	@Test(
		expected = AMImageConfigurationException.RequiredWidthOrHeightException.class
	)
	public void testAddConfigurationEntryWithZeroMaxHeightAndBlankMaxWidth()
		throws Exception {

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "0");
		properties.put("max-width", "");

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1", properties);
	}

	@Test(
		expected = AMImageConfigurationException.RequiredWidthOrHeightException.class
	)
	public void testAddConfigurationEntryWithZeroMaxHeightAndZeroMaxWidth()
		throws Exception {

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "0");
		properties.put("max-width", "0");

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1", properties);
	}

	@Test(
		expected = AMImageConfigurationException.RequiredWidthOrHeightException.class
	)
	public void testAddConfigurationEntryWithZeroMaxHeightOnly()
		throws Exception {

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "0");

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1", properties);
	}

	@Test
	public void testAddConfigurationEntryWithZeroMaxWidth() throws Exception {
		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "0");

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1", properties);

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "1");

		Assert.assertTrue(amImageConfigurationEntryOptional.isPresent());

		AMImageConfigurationEntry amImageConfigurationEntry =
			amImageConfigurationEntryOptional.get();

		Map<String, String> actualProperties =
			amImageConfigurationEntry.getProperties();

		Assert.assertEquals("100", actualProperties.get("max-height"));
		Assert.assertEquals("0", actualProperties.get("max-width"));
	}

	@Test(
		expected = AMImageConfigurationException.RequiredWidthOrHeightException.class
	)
	public void testAddConfigurationEntryWithZeroMaxWidthOnly()
		throws Exception {

		Map<String, String> properties = new HashMap<>();

		properties.put("max-width", "0");

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "desc", "1", properties);
	}

	@Test(
		expected = AMImageConfigurationException.DuplicateAMImageConfigurationNameException.class
	)
	public void testAddDuplicateConfigurationEntryName() throws Exception {
		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "onedesc", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "onedesc", "2", properties);
	}

	@Test(
		expected = AMImageConfigurationException.DuplicateAMImageConfigurationUuidException.class
	)
	public void testAddDuplicateConfigurationEntryUuid() throws Exception {
		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "one", "onedesc", "1", properties);

		properties = new HashMap<>();

		properties.put("max-height", "200");
		properties.put("max-width", "200");

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			TestPropsValues.getCompanyId(), "two", "twodesc", "1", properties);
	}

	@Test
	public void testSendsAMessageToTheMessageBus() throws Exception {
		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", "100");
		properties.put("max-width", "100");

		List<Message> messages = collectConfigurationMessages(
			() -> _amImageConfigurationHelper.addAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(), "one", "onedesc", "1",
				properties));

		Assert.assertEquals(messages.toString(), 1, messages.size());

		Message message = messages.get(0);

		AMImageConfigurationEntry amImageConfigurationEntry =
			(AMImageConfigurationEntry)message.getPayload();

		Assert.assertEquals("one", amImageConfigurationEntry.getName());
		Assert.assertEquals(
			"onedesc", amImageConfigurationEntry.getDescription());
		Assert.assertEquals("1", amImageConfigurationEntry.getUUID());
		Assert.assertEquals(
			properties, amImageConfigurationEntry.getProperties());
	}

	@Override
	protected AMImageConfigurationHelper getAMImageConfigurationHelper() {
		return _amImageConfigurationHelper;
	}

	@Inject
	private AMImageConfigurationHelper _amImageConfigurationHelper;

}