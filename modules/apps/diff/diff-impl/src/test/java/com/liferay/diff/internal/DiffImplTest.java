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

package com.liferay.diff.internal;

import com.liferay.diff.Diff;
import com.liferay.diff.DiffResult;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.Reader;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Bruno Farache
 */
public class DiffImplTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testEight() {
		Reader reader1 = new UnsyncStringReader("add\nbbb\nccc");
		Reader reader2 = new UnsyncStringReader("bbb\nccc\naee");

		List<DiffResult> expectedSource = new ArrayList<>();
		List<DiffResult> expectedTarget = new ArrayList<>();

		expectedSource.add(
			new DiffResult(0, Diff.OPEN_DEL + "add" + Diff.CLOSE_DEL));

		expectedTarget.add(new DiffResult(0, Diff.CONTEXT_LINE));

		List<String> changedLines = new ArrayList<>();

		changedLines.add("bbb");
		changedLines.add("ccc");
		changedLines.add(Diff.CONTEXT_LINE);

		expectedSource.add(new DiffResult(2, changedLines));

		changedLines = new ArrayList<>();

		changedLines.add("bbb");
		changedLines.add("ccc");
		changedLines.add(Diff.OPEN_INS + "aee" + Diff.CLOSE_INS);

		expectedTarget.add(new DiffResult(2, changedLines));

		List<DiffResult>[] actual = _diffImpl.diff(reader1, reader2);

		Assert.assertEquals(expectedSource, actual[0]);
		Assert.assertEquals(expectedTarget, actual[1]);
	}

	@Test
	public void testEleven() {
		Reader reader1 = new UnsyncStringReader("aaa\nbbb\nfff");
		Reader reader2 = new UnsyncStringReader("ccc\nada\nbeb");

		List<DiffResult> expectedSource = new ArrayList<>();
		List<DiffResult> expectedTarget = new ArrayList<>();

		expectedSource.add(new DiffResult(0, Diff.CONTEXT_LINE));

		expectedTarget.add(
			new DiffResult(0, Diff.OPEN_INS + "ccc" + Diff.CLOSE_INS));

		expectedSource.add(
			new DiffResult(
				0,
				StringBundler.concat(
					"a", Diff.OPEN_DEL, "a", Diff.CLOSE_DEL, "a")));

		expectedTarget.add(
			new DiffResult(
				1,
				StringBundler.concat(
					"a", Diff.OPEN_INS, "d", Diff.CLOSE_INS, "a")));

		expectedSource.add(
			new DiffResult(
				1,
				StringBundler.concat(
					"b", Diff.OPEN_DEL, "b", Diff.CLOSE_DEL, "b")));

		expectedTarget.add(
			new DiffResult(
				2,
				StringBundler.concat(
					"b", Diff.OPEN_INS, "e", Diff.CLOSE_INS, "b")));

		expectedSource.add(
			new DiffResult(2, Diff.OPEN_DEL + "fff" + Diff.CLOSE_DEL));

		expectedTarget.add(new DiffResult(2, Diff.CONTEXT_LINE));

		List<DiffResult>[] actual = _diffImpl.diff(reader1, reader2);

		Assert.assertEquals(expectedSource, actual[0]);
		Assert.assertEquals(expectedTarget, actual[1]);
	}

	@Test
	public void testFive() {
		Reader reader1 = new UnsyncStringReader("aaa\nbbb");
		Reader reader2 = new UnsyncStringReader("ccc\naaa");

		List<DiffResult> expectedSource = new ArrayList<>();
		List<DiffResult> expectedTarget = new ArrayList<>();

		expectedSource.add(new DiffResult(0, Diff.CONTEXT_LINE));

		expectedTarget.add(
			new DiffResult(0, Diff.OPEN_INS + "ccc" + Diff.CLOSE_INS));

		List<String> changedLines = new ArrayList<>();

		changedLines.add("aaa");
		changedLines.add(Diff.OPEN_DEL + "bbb" + Diff.CLOSE_DEL);

		expectedSource.add(new DiffResult(1, changedLines));

		changedLines = new ArrayList<>();

		changedLines.add("aaa");
		changedLines.add(Diff.CONTEXT_LINE);

		expectedTarget.add(new DiffResult(1, changedLines));

		List<DiffResult>[] actual = _diffImpl.diff(reader1, reader2);

		Assert.assertEquals(expectedSource, actual[0]);
		Assert.assertEquals(expectedTarget, actual[1]);
	}

	@Test
	public void testFour() {
		Reader reader1 = new UnsyncStringReader("rahab");
		Reader reader2 = new UnsyncStringReader("boaz");

		List<DiffResult> expectedSource = new ArrayList<>();
		List<DiffResult> expectedTarget = new ArrayList<>();

		expectedSource.add(new DiffResult(0, Diff.CONTEXT_LINE));

		expectedTarget.add(
			new DiffResult(0, Diff.OPEN_INS + "boaz" + Diff.CLOSE_INS));

		expectedSource.add(
			new DiffResult(0, Diff.OPEN_DEL + "rahab" + Diff.CLOSE_DEL));

		expectedTarget.add(new DiffResult(0, Diff.CONTEXT_LINE));

		List<DiffResult>[] actual = _diffImpl.diff(reader1, reader2);

		Assert.assertEquals(expectedSource, actual[0]);
		Assert.assertEquals(expectedTarget, actual[1]);
	}

	@Test
	public void testNine() {
		Reader reader1 = new UnsyncStringReader("abcd");
		Reader reader2 = new UnsyncStringReader("abcdee");

		List<DiffResult> expectedSource = new ArrayList<>();
		List<DiffResult> expectedTarget = new ArrayList<>();

		expectedSource.add(new DiffResult(0, "abcd"));

		expectedTarget.add(
			new DiffResult(
				0,
				StringBundler.concat(
					"abcd", Diff.OPEN_INS, "ee", Diff.CLOSE_INS)));

		List<DiffResult>[] actual = _diffImpl.diff(reader1, reader2);

		Assert.assertEquals(expectedSource, actual[0]);
		Assert.assertEquals(expectedTarget, actual[1]);
	}

	@Test
	public void testOne() {
		Reader reader1 = new UnsyncStringReader("liferay");
		Reader reader2 = new UnsyncStringReader("liferay");

		List<DiffResult> expectedSource = new ArrayList<>();
		List<DiffResult> expectedTarget = new ArrayList<>();

		List<DiffResult>[] actual = _diffImpl.diff(reader1, reader2);

		Assert.assertEquals(expectedSource, actual[0]);
		Assert.assertEquals(expectedTarget, actual[1]);
	}

	@Test
	public void testSeven() {
		Reader reader1 = new UnsyncStringReader("ccc\naaa\nbbe");
		Reader reader2 = new UnsyncStringReader("aaa\nbbb");

		List<DiffResult> expectedSource = new ArrayList<>();
		List<DiffResult> expectedTarget = new ArrayList<>();

		expectedSource.add(
			new DiffResult(0, Diff.OPEN_DEL + "ccc" + Diff.CLOSE_DEL));

		expectedTarget.add(new DiffResult(0, Diff.CONTEXT_LINE));

		List<String> changedLines = new ArrayList<>();

		changedLines.add(
			StringBundler.concat("bb", Diff.OPEN_DEL, "e", Diff.CLOSE_DEL));

		expectedSource.add(new DiffResult(2, changedLines));

		changedLines = new ArrayList<>();

		changedLines.add(
			StringBundler.concat("bb", Diff.OPEN_INS, "b", Diff.CLOSE_INS));

		expectedTarget.add(new DiffResult(1, changedLines));

		List<DiffResult>[] actual = _diffImpl.diff(reader1, reader2);

		Assert.assertEquals(expectedSource, actual[0]);
		Assert.assertEquals(expectedTarget, actual[1]);
	}

	@Test
	public void testSix() {
		Reader reader1 = new UnsyncStringReader("ccc\naaa");
		Reader reader2 = new UnsyncStringReader("aaa\nbbb");

		List<DiffResult> expectedSource = new ArrayList<>();
		List<DiffResult> expectedTarget = new ArrayList<>();

		expectedSource.add(
			new DiffResult(0, Diff.OPEN_DEL + "ccc" + Diff.CLOSE_DEL));

		expectedTarget.add(new DiffResult(0, Diff.CONTEXT_LINE));

		List<String> changedLines = new ArrayList<>();

		changedLines.add("aaa");
		changedLines.add(Diff.CONTEXT_LINE);

		expectedSource.add(new DiffResult(1, changedLines));

		changedLines = new ArrayList<>();

		changedLines.add("aaa");
		changedLines.add(Diff.OPEN_INS + "bbb" + Diff.CLOSE_INS);

		expectedTarget.add(new DiffResult(1, changedLines));

		List<DiffResult>[] actual = _diffImpl.diff(reader1, reader2);

		Assert.assertEquals(expectedSource, actual[0]);
		Assert.assertEquals(expectedTarget, actual[1]);
	}

	@Test
	public void testTen() {
		Reader reader1 = new UnsyncStringReader("abcd");
		Reader reader2 = new UnsyncStringReader("abcdeee");

		List<DiffResult> expectedSource = new ArrayList<>();
		List<DiffResult> expectedTarget = new ArrayList<>();

		expectedSource.add(new DiffResult(0, Diff.CONTEXT_LINE));

		expectedTarget.add(
			new DiffResult(0, Diff.OPEN_INS + "abcdeee" + Diff.CLOSE_INS));

		expectedSource.add(
			new DiffResult(0, Diff.OPEN_DEL + "abcd" + Diff.CLOSE_DEL));

		expectedTarget.add(new DiffResult(0, Diff.CONTEXT_LINE));

		List<DiffResult>[] actual = _diffImpl.diff(reader1, reader2);

		Assert.assertEquals(expectedSource, actual[0]);
		Assert.assertEquals(expectedTarget, actual[1]);
	}

	@Test
	public void testThree() {
		Reader reader1 = new UnsyncStringReader("aaa");
		Reader reader2 = new UnsyncStringReader("bbb");

		List<DiffResult> expectedSource = new ArrayList<>();
		List<DiffResult> expectedTarget = new ArrayList<>();

		expectedSource.add(new DiffResult(0, Diff.CONTEXT_LINE));

		expectedTarget.add(
			new DiffResult(0, Diff.OPEN_INS + "bbb" + Diff.CLOSE_INS));

		expectedSource.add(
			new DiffResult(0, Diff.OPEN_DEL + "aaa" + Diff.CLOSE_DEL));

		expectedTarget.add(new DiffResult(0, Diff.CONTEXT_LINE));

		List<DiffResult>[] actual = _diffImpl.diff(reader1, reader2);

		Assert.assertEquals(expectedSource, actual[0]);
		Assert.assertEquals(expectedTarget, actual[1]);
	}

	@Test
	public void testTwelve() {
		Reader reader1 = new UnsyncStringReader("ada");
		Reader reader2 = new UnsyncStringReader("aaa\nccc");

		List<DiffResult> expectedSource = new ArrayList<>();
		List<DiffResult> expectedTarget = new ArrayList<>();

		expectedSource.add(
			new DiffResult(
				0,
				StringBundler.concat(
					"a", Diff.OPEN_DEL, "d", Diff.CLOSE_DEL, "a")));

		expectedTarget.add(
			new DiffResult(
				0,
				StringBundler.concat(
					"a", Diff.OPEN_INS, "a", Diff.CLOSE_INS, "a")));

		expectedSource.add(new DiffResult(1, Diff.CONTEXT_LINE));

		expectedTarget.add(
			new DiffResult(1, Diff.OPEN_INS + "ccc" + Diff.CLOSE_INS));

		List<DiffResult>[] actual = _diffImpl.diff(reader1, reader2);

		Assert.assertEquals(expectedSource, actual[0]);
		Assert.assertEquals(expectedTarget, actual[1]);
	}

	@Test
	public void testTwo() {
		Reader reader1 = new UnsyncStringReader("liferay");
		Reader reader2 = new UnsyncStringReader("LifeRay");

		List<DiffResult> expectedSource = new ArrayList<>();
		List<DiffResult> expectedTarget = new ArrayList<>();

		List<String> changedLines = new ArrayList<>();

		changedLines.add(
			StringBundler.concat(
				Diff.OPEN_DEL, "l", Diff.CLOSE_DEL, "ife", Diff.OPEN_DEL, "r",
				Diff.CLOSE_DEL, "ay"));

		expectedSource.add(new DiffResult(0, changedLines));

		changedLines = new ArrayList<>();

		changedLines.add(
			StringBundler.concat(
				Diff.OPEN_INS, "L", Diff.CLOSE_INS, "ife", Diff.OPEN_INS, "R",
				Diff.CLOSE_INS, "ay"));

		expectedTarget.add(new DiffResult(0, changedLines));

		List<DiffResult>[] actual = _diffImpl.diff(reader1, reader2);

		Assert.assertEquals(expectedSource, actual[0]);
		Assert.assertEquals(expectedTarget, actual[1]);
	}

	private final DiffImpl _diffImpl = new DiffImpl();

}