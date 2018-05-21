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

package com.liferay.vldap.server.internal.directory;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Minhchau Dang
 */
public class FilterConstraintTest {

	@Test
	public void testAddAttribute() {
		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute(null, "test1");

		Assert.assertTrue(filterConstraint.isEmpty());

		filterConstraint.addAttribute("cn", "test1");

		Assert.assertFalse(filterConstraint.isEmpty());
		Assert.assertEquals("test1", filterConstraint.getValue("cn"));

		filterConstraint.addAttribute("description", "test2");

		Assert.assertEquals("test2", filterConstraint.getValue("description"));
	}

	@Test
	public void testDifferentNameMerge() throws Exception {
		FilterConstraint leftFilterConstraint = getFilterConstraint(
			"cn", "test1");
		FilterConstraint rightFilterConstraint = getFilterConstraint(
			"description", "test2");

		Assert.assertFalse(leftFilterConstraint.merge(rightFilterConstraint));

		Assert.assertEquals("test1", leftFilterConstraint.getValue("cn"));
		Assert.assertEquals(
			"test2", leftFilterConstraint.getValue("description"));
	}

	@Test
	public void testDifferentValueMerge() throws Exception {
		FilterConstraint leftFilterConstraint = getFilterConstraint(
			"cn", "test1");
		FilterConstraint rightFilterConstraint = getFilterConstraint(
			"cn", "test2");

		Assert.assertTrue(leftFilterConstraint.merge(rightFilterConstraint));
	}

	@Test
	public void testEquals() {
		FilterConstraint leftFilterConstraint = new FilterConstraint();
		FilterConstraint rightFilterConstraint = new FilterConstraint();

		Assert.assertTrue(leftFilterConstraint.equals(rightFilterConstraint));

		leftFilterConstraint = getFilterConstraint("cn", "test1");
		rightFilterConstraint = getFilterConstraint("cn", "test1");

		Assert.assertTrue(leftFilterConstraint.equals(leftFilterConstraint));
		Assert.assertTrue(leftFilterConstraint.equals(rightFilterConstraint));

		rightFilterConstraint = getFilterConstraint("description", "test2");

		Assert.assertFalse(leftFilterConstraint.equals(rightFilterConstraint));

		Assert.assertFalse(leftFilterConstraint.equals(null));
	}

	@Test
	public void testGetCombinations() {
		List<FilterConstraint> leftFilterConstraint = new ArrayList<>();
		List<FilterConstraint> rightFilterConstraint = new ArrayList<>();

		leftFilterConstraint.add(getFilterConstraint("cn", "test1"));
		rightFilterConstraint.add(getFilterConstraint("description", "test2"));

		List<FilterConstraint> filterConstraints =
			FilterConstraint.getCombinations(
				leftFilterConstraint, rightFilterConstraint);

		Assert.assertEquals(
			filterConstraints.toString(), 1, filterConstraints.size());

		FilterConstraint filterConstraint = filterConstraints.get(0);

		Assert.assertEquals("test1", filterConstraint.getValue("cn"));
		Assert.assertEquals("test2", filterConstraint.getValue("description"));
	}

	@Test
	public void testGetCombinationsWithCollision() {
		List<FilterConstraint> leftFilterConstraint = new ArrayList<>();
		List<FilterConstraint> rightFilterConstraint = new ArrayList<>();

		leftFilterConstraint.add(getFilterConstraint("cn", "test1"));
		rightFilterConstraint.add(getFilterConstraint("cn", "test2"));

		List<FilterConstraint> filterConstraints =
			FilterConstraint.getCombinations(
				leftFilterConstraint, rightFilterConstraint);

		Assert.assertEquals(
			filterConstraints.toString(), 0, filterConstraints.size());
	}

	@Test
	public void testGetCombinationsWithEmptyFilters() {
		List<FilterConstraint> leftFilterConstraint = new ArrayList<>();
		List<FilterConstraint> rightFilterConstraint = new ArrayList<>();

		leftFilterConstraint.add(new FilterConstraint());
		rightFilterConstraint.add(new FilterConstraint());

		List<FilterConstraint> filterConstraints =
			FilterConstraint.getCombinations(
				leftFilterConstraint, rightFilterConstraint);

		Assert.assertEquals(
			filterConstraints.toString(), 0, filterConstraints.size());
	}

	@Test
	public void testGetCombinationsWithNullLeftFilter() {
		List<FilterConstraint> leftFilterConstraint = new ArrayList<>();
		List<FilterConstraint> rightFilterConstraint = new ArrayList<>();

		rightFilterConstraint.add(getFilterConstraint("description", "test2"));

		List<FilterConstraint> filterConstraints =
			FilterConstraint.getCombinations(
				leftFilterConstraint, rightFilterConstraint);

		Assert.assertEquals(
			filterConstraints.toString(), 0, filterConstraints.size());
	}

	@Test
	public void testGetCombinationsWithNullRightFilter() {
		List<FilterConstraint> leftFilterConstraint = new ArrayList<>();
		List<FilterConstraint> rightFilterConstraint = new ArrayList<>();

		leftFilterConstraint.add(getFilterConstraint("cn", "test1"));

		List<FilterConstraint> filterConstraints =
			FilterConstraint.getCombinations(
				leftFilterConstraint, rightFilterConstraint);

		Assert.assertEquals(
			filterConstraints.toString(), 0, filterConstraints.size());
	}

	@Test
	public void testLeftAsteriskMerge() throws Exception {
		FilterConstraint leftFilterConstraint = getFilterConstraint("cn", "*");
		FilterConstraint rightFilterConstraint = getFilterConstraint(
			"cn", "test");

		Assert.assertFalse(leftFilterConstraint.merge(rightFilterConstraint));

		Assert.assertEquals("test", leftFilterConstraint.getValue("cn"));
	}

	@Test
	public void testRightAsteriskMerge() throws Exception {
		FilterConstraint leftFilterConstraint = getFilterConstraint(
			"cn", "test");
		FilterConstraint rightFilterConstraint = getFilterConstraint("cn", "*");

		Assert.assertFalse(leftFilterConstraint.merge(rightFilterConstraint));

		Assert.assertEquals("test", leftFilterConstraint.getValue("cn"));
	}

	@Test
	public void testSameValueMerge() throws Exception {
		FilterConstraint leftFilterConstraint = getFilterConstraint(
			"cn", "test");
		FilterConstraint rightFilterConstraint = getFilterConstraint(
			"cn", "test");

		Assert.assertFalse(leftFilterConstraint.merge(rightFilterConstraint));

		Assert.assertEquals("test", leftFilterConstraint.getValue("cn"));
	}

	protected FilterConstraint getFilterConstraint(String name, String value) {
		FilterConstraint filterConstraint = new FilterConstraint();

		filterConstraint.addAttribute(name, value);

		return filterConstraint;
	}

}