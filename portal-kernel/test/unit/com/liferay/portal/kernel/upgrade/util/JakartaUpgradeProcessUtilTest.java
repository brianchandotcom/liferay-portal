/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade.util;

import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Luis Ortiz
 */
public class JakartaUpgradeProcessUtilTest {

	@Test
	public void testReplace() {
		_testReplacements(
			HashMapBuilder.put(
				"javax-batch-operations", "jakarta-batch-operations"
			).put(
				"javax.portlet.Portlet", "jakarta.portlet.Portlet"
			).put(
				"javax/persistence/cache", "jakarta/persistence/cache"
			).build());
	}

	@Test
	public void testReplaceWithCustomSeparators() {
		_testReplacements(
			HashMapBuilder.put(
				"javax$persistence$cache", "jakarta$persistence$cache"
			).put(
				"javax.portlet.Portlet", "jakarta.portlet.Portlet"
			).put(
				"javax@batch@operations", "jakarta@batch@operations"
			).build(),
			new HashSet<>(Arrays.asList('@', '$')));
	}

	@Test
	public void testReplaceWithFixupSubpackage() {
		_testReplacements(
			HashMapBuilder.put(
				"javax-transaction-xa-XAResource",
				"javax-transaction-xa-XAResource"
			).put(
				"javax.annotation.processing.Processor",
				"javax.annotation.processing.Processor"
			).build());
	}

	@Test
	public void testReplaceWithFixupSubpackageAndCustomSeparator() {
		_testReplacements(
			HashMapBuilder.put(
				"javax$transaction$xa$XAResource",
				"javax$transaction$xa$XAResource"
			).put(
				"javax@annotation@processing@Processor",
				"javax@annotation@processing@Processor"
			).build(),
			new HashSet<>(Arrays.asList('@', '$')));
	}

	@Test
	public void testReplaceWithMultipleSubpackages() {
		_testReplacements(
			HashMapBuilder.put(
				"import javax.portlet.Portlet;\nimport javax.batch.operations;",
				"import jakarta.portlet.Portlet;\nimport " +
					"jakarta.batch.operations;"
			).build());
	}

	@Test
	public void testReplaceWithMultipleSubpackagesAndCustomSeparator() {
		_testReplacements(
			HashMapBuilder.put(
				"import javax@portlet@Portlet;\nimport javax$batch$operations;",
				"import jakarta@portlet@Portlet;\nimport " +
					"jakarta$batch$operations;"
			).build(),
			new HashSet<>(Arrays.asList('@', '$')));
	}

	@Test
	public void testReplaceWithNoMatch() {
		_testReplacements(
			HashMapBuilder.put(
				"com.liferay.portal.kernel.util.StringUtil",
				"com.liferay.portal.kernel.util.StringUtil"
			).build());
	}

	@Test
	public void testReplaceWithNoMatchAndCustomSeparator() {
		_testReplacements(
			HashMapBuilder.put(
				"com@liferay@portal@kernel@util@StringUtil",
				"com@liferay@portal@kernel@util@StringUtil"
			).put(
				"javax$activity$ActivityCompletedException",
				"javax$activity$ActivityCompletedException"
			).build(),
			new HashSet<>(Arrays.asList('@', '$')));
	}

	@Test
	public void testReplaceWithXJavaxPortletNamespacedResponse() {
		_testReplacements(
			HashMapBuilder.put(
				"X-JAVAX-PORTLET-NAMESPACED-RESPONSE",
				"X-JAKARTA-PORTLET-NAMESPACED-RESPONSE"
			).build());
	}

	private void _testReplacements(Map<String, String> replacements) {
		for (Map.Entry<String, String> entry : replacements.entrySet()) {
			Assert.assertEquals(
				entry.getValue(),
				JakartaUpgradeProcessUtil.replace(entry.getKey()));
		}
	}

	private void _testReplacements(
		Map<String, String> replacements, Set<Character> customSeparators) {

		for (Map.Entry<String, String> entry : replacements.entrySet()) {
			Assert.assertEquals(
				entry.getValue(),
				JakartaUpgradeProcessUtil.replace(
					entry.getKey(), customSeparators));
		}
	}

}