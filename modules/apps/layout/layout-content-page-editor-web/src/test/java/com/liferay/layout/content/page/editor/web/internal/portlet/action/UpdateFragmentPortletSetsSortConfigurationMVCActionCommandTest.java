/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Rubén Pulido
 */
public class UpdateFragmentPortletSetsSortConfigurationMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		Class<?> clazz =
			UpdateFragmentPortletSetsSortConfigurationMVCActionCommand.class;

		Method[] methods = clazz.getDeclaredMethods();

		_mergeFragmentCollectionKeysMethod = null;

		for (Method method : methods) {
			if (Objects.equals(
					method.getName(), "_mergeFragmentCollectionKeys")) {

				_mergeFragmentCollectionKeysMethod = method;

				break;
			}
		}

		_mergeFragmentCollectionKeysMethod.setAccessible(true);

		Constructor<?> constructor = clazz.getConstructor();

		_updateFragmentPortletSetsSortConfigurationMVCActionCommand =
			constructor.newInstance();
	}

	@Test
	public void testMergeFragmentCollectionKeysOldSize0NewSize1Add1()
		throws Exception {

		Assert.assertArrayEquals(
			new String[] {"A"},
			_mergeFragmentCollectionKeys(
				ListUtil.fromArray("A"), Collections.emptyList()));
	}

	@Test
	public void testMergeFragmentCollectionKeysOldSize2NewSize2Add3And4()
		throws Exception {

		Assert.assertArrayEquals(
			new String[] {"a", "b", "C", "D"},
			_mergeFragmentCollectionKeys(
				ListUtil.fromArray("C", "D"), ListUtil.fromArray("a", "b")));
	}

	@Test
	public void testMergeFragmentCollectionKeysOldSize2NewSize2Swap1And2()
		throws Exception {

		Assert.assertArrayEquals(
			new String[] {"b", "a"},
			_mergeFragmentCollectionKeys(
				ListUtil.fromArray("b", "a"), ListUtil.fromArray("a", "b")));
	}

	@Test
	public void testMergeFragmentCollectionKeysOldSize2NewSize2SwapNone()
		throws Exception {

		Assert.assertArrayEquals(
			new String[] {"a", "b"},
			_mergeFragmentCollectionKeys(
				ListUtil.fromArray("a", "b"), ListUtil.fromArray("a", "b")));
	}

	@Test
	public void testMergeFragmentCollectionKeysOldSize2NewSize3Add1()
		throws Exception {

		Assert.assertArrayEquals(
			new String[] {"A", "b", "c"},
			_mergeFragmentCollectionKeys(
				ListUtil.fromArray("A", "b", "c"),
				ListUtil.fromArray("b", "c")));
	}

	@Test
	public void testMergeFragmentCollectionKeysOldSize2NewSize3Add2()
		throws Exception {

		Assert.assertArrayEquals(
			new String[] {"a", "B", "c"},
			_mergeFragmentCollectionKeys(
				ListUtil.fromArray("a", "B", "c"),
				ListUtil.fromArray("a", "c")));
	}

	@Test
	public void testMergeFragmentCollectionKeysOldSize2NewSize3Add3()
		throws Exception {

		Assert.assertArrayEquals(
			new String[] {"a", "b", "C"},
			_mergeFragmentCollectionKeys(
				ListUtil.fromArray("a", "b", "C"),
				ListUtil.fromArray("a", "b")));
	}

	@Test
	public void testMergeFragmentCollectionKeysOldSize2NewSize5Add1And3And5()
		throws Exception {

		Assert.assertArrayEquals(
			new String[] {"A", "b", "C", "d", "E"},
			_mergeFragmentCollectionKeys(
				ListUtil.fromArray("A", "b", "C", "d", "E"),
				ListUtil.fromArray("b", "d")));
	}

	@Test
	public void testMergeFragmentCollectionKeysOldSize2NewSize5Add1And3And5Swap2And4()
		throws Exception {

		Assert.assertArrayEquals(
			new String[] {"A", "d", "C", "b", "E"},
			_mergeFragmentCollectionKeys(
				ListUtil.fromArray("A", "d", "C", "b", "E"),
				ListUtil.fromArray("b", "d")));
	}

	@Test
	public void testMergeFragmentCollectionKeysOldSize2NewSize5Swap2And4()
		throws Exception {

		Assert.assertArrayEquals(
			new String[] {"a", "d", "c", "b", "e"},
			_mergeFragmentCollectionKeys(
				ListUtil.fromArray("d", "b"),
				ListUtil.fromArray("a", "b", "c", "d", "e")));
	}

	@Test
	public void testMergeFragmentCollectionKeysOldSize3NewSize2Swap1And2()
		throws Exception {

		Assert.assertArrayEquals(
			new String[] {"b", "a", "c"},
			_mergeFragmentCollectionKeys(
				ListUtil.fromArray("b", "a"),
				ListUtil.fromArray("a", "b", "c")));
	}

	@Test
	public void testMergeFragmentCollectionKeysOldSize3NewSize2Swap1And3()
		throws Exception {

		Assert.assertArrayEquals(
			new String[] {"c", "b", "a"},
			_mergeFragmentCollectionKeys(
				ListUtil.fromArray("c", "a"),
				ListUtil.fromArray("a", "b", "c")));
	}

	@Test
	public void testMergeFragmentCollectionKeysOldSize3NewSize2Swap2And3()
		throws Exception {

		Assert.assertArrayEquals(
			new String[] {"a", "c", "b"},
			_mergeFragmentCollectionKeys(
				ListUtil.fromArray("c", "b"),
				ListUtil.fromArray("a", "b", "c")));
	}

	@Test
	public void testMergeFragmentCollectionKeysOldSize4NewSize3Add5Swap2And4()
		throws Exception {

		Assert.assertArrayEquals(
			new String[] {"a", "d", "c", "b", "E"},
			_mergeFragmentCollectionKeys(
				ListUtil.fromArray("d", "b", "E"),
				ListUtil.fromArray("a", "b", "c", "d")));
	}

	@Test
	public void testMergeFragmentCollectionKeysOldSize5NewSize3Swap1And3And5()
		throws Exception {

		Assert.assertArrayEquals(
			new String[] {"c", "b", "e", "d", "a"},
			_mergeFragmentCollectionKeys(
				ListUtil.fromArray("c", "e", "a"),
				ListUtil.fromArray("a", "b", "c", "d", "e")));
	}

	private static String[] _mergeFragmentCollectionKeys(
			List<String> newFragmentCollectionKeys,
			List<String> oldFragmentCollectionKeys)
		throws Exception {

		return (String[])_mergeFragmentCollectionKeysMethod.invoke(
			_updateFragmentPortletSetsSortConfigurationMVCActionCommand,
			newFragmentCollectionKeys, oldFragmentCollectionKeys);
	}

	private static Method _mergeFragmentCollectionKeysMethod;
	private static Object
		_updateFragmentPortletSetsSortConfigurationMVCActionCommand;

}