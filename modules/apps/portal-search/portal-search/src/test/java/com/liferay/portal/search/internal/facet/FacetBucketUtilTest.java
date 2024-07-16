/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.facet;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.MultiValueFacet;
import com.liferay.portal.kernel.search.facet.RangeFacet;
import com.liferay.portal.kernel.search.facet.SimpleFacet;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class FacetBucketUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testFacetImpl() {
		Field field = new Field(_FIELD_NAME, new String[] {"foo", "bar"});

		Facet facet = new FacetImpl(_FIELD_NAME, null);

		Assert.assertTrue(FacetBucketUtil.isFieldInBucket(field, "foo", facet));
		Assert.assertTrue(FacetBucketUtil.isFieldInBucket(field, "bar", facet));
	}

	@Test
	public void testMultiValueFacet() {
		Field field = new Field(_FIELD_NAME, new String[] {"foo", "bar"});

		Facet facet = new MultiValueFacet(null);

		Assert.assertTrue(FacetBucketUtil.isFieldInBucket(field, "bar", facet));
	}

	@Test
	public void testNestedFacet() {
		NestedFacetImpl nestedFacetImpl = new NestedFacetImpl(
			_FIELD_NAME, null);

		nestedFacetImpl.setFilterField(_FIELD_NAME + ".fieldName");

		String filterValue = RandomTestUtil.randomString();

		nestedFacetImpl.setFilterValue(filterValue);

		nestedFacetImpl.setPath(_FIELD_NAME);

		Field field = new Field(
			_FIELD_NAME,
			new String[] {
				_createFieldValue(filterValue, "a"),
				_createFieldValue(RandomTestUtil.randomString(), "b"),
				_createFieldValue(filterValue, "c")
			});

		Assert.assertTrue(
			FacetBucketUtil.isFieldInBucket(field, "a", nestedFacetImpl));
		Assert.assertFalse(
			FacetBucketUtil.isFieldInBucket(field, "b", nestedFacetImpl));
		Assert.assertTrue(
			FacetBucketUtil.isFieldInBucket(field, "c", nestedFacetImpl));
	}

	@Test
	public void testRangeFacet() {
		Field field = new Field(_FIELD_NAME, "007");

		Facet facet = new RangeFacet(null);

		Assert.assertFalse(
			FacetBucketUtil.isFieldInBucket(field, "[001 TO 006]", facet));
		Assert.assertFalse(
			FacetBucketUtil.isFieldInBucket(field, "[008 TO 999]", facet));
		Assert.assertFalse(
			FacetBucketUtil.isFieldInBucket(field, "undefined", facet));
		Assert.assertTrue(
			FacetBucketUtil.isFieldInBucket(field, "[001 TO 999]", facet));
		Assert.assertTrue(
			FacetBucketUtil.isFieldInBucket(field, "[001 TO 007]", facet));
		Assert.assertTrue(
			FacetBucketUtil.isFieldInBucket(field, "[007 TO 007]", facet));
		Assert.assertTrue(
			FacetBucketUtil.isFieldInBucket(field, "[007 TO 999]", facet));
	}

	@Test
	public void testSimpleFacet() {
		Field field = new Field(_FIELD_NAME, "foo");

		Facet facet = new SimpleFacet(null);

		Assert.assertTrue(FacetBucketUtil.isFieldInBucket(field, "foo", facet));
	}

	private String _createFieldValue(String filterValue, String term) {
		return StringBundler.concat(
			"{fieldName=", filterValue, StringPool.COMMA_AND_SPACE, _FIELD_NAME,
			"=", term, "}");
	}

	private static final String _FIELD_NAME = RandomTestUtil.randomString();

}