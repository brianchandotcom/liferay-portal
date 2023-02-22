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

package com.liferay.source.formatter.processor;

import com.liferay.petra.string.StringBundler;
import com.liferay.source.formatter.SourceFormatterArgs;

import org.junit.Test;

/**
 * @author Qi Zhang
 */
public class LibrarySourceProcessorTest extends BaseSourceProcessorTestCase {

	@Test
	public void testLibraryVulnerabilities() throws Exception {
		test(
			"dependencies.testproperties",
			StringBundler.concat(
				"Library 'org.apache.pdfbox:pdfbox:2.0.14' contains known ",
				"vulnerabilities(Vulnerability that affects org.apache.",
				"pdfbox:pdfbox, https://github.com/advisories/GHSA-c9jj-3wvg-",
				"q65h)"));

		test(
			"ivy.testxml",
			StringBundler.concat(
				"Library 'org.springframework.security:spring-security-",
				"core:5.6.1' contains known vulnerabilities(Spring Security ",
				"authorization rules can be bypassed via forward or include ",
				"dispatcher types, https://github.com/advisories",
				"/GHSA-mmmh-wcxm-2wr4)"));

		test(
			"pom.testxml",
			StringBundler.concat(
				"Library 'org.springframework.security:spring-security-",
				"core:5.6.1' contains known vulnerabilities(Spring Security ",
				"authorization rules can be bypassed via forward or include ",
				"dispatcher types, https://github.com/advisories",
				"/GHSA-mmmh-wcxm-2wr4)"));

		test(
			"build.testgradle",
			new String[] {
				StringBundler.concat(
					"Library 'com.erudika:para-core:1.45.10' contains known ",
					"vulnerabilities(Cross-site Scripting in com.erudika:",
					"para-core, https://github.com/advisories/GHSA-phvw-r25p-",
					"8xv7)"),
				StringBundler.concat(
					"Library 'com.twelvemonkeys.imageio:imageio-metadata:",
					"3.7.0' contains known vulnerabilities(External Entity ",
					"Reference in TwelveMonkeys ImageIO, https://github.com",
					"/advisories/GHSA-pjch-4g28-fxx7)"),
				StringBundler.concat(
					"Library 'org.apache.jena:jena:",
					"3.1.0' contains known vulnerabilities(XML External ",
					"Entity Reference in apache jena, https://github.com",
					"/advisories/GHSA-gchv-364h-r896)"),
				StringBundler.concat(
					"Library 'org.springframework.security:spring-security-",
					"core:5.6.2' contains known vulnerabilities(Spring ",
					"Security authorization rules can be bypassed via forward ",
					"or include dispatcher types, https://github.com",
					"/advisories/GHSA-mmmh-wcxm-2wr4)")
			});

		test(
			"package.testjson",
			new String[] {
				StringBundler.concat(
					"Library 'eventsource:2.0.1' contains known ",
					"vulnerabilities(Exposure of Sensitive Information in ",
					"eventsource, https://github.com/advisories/GHSA-6h5x-",
					"7c5m-7cr7)"),
				StringBundler.concat(
					"Library 'workspace-tools:0.17' contains known ",
					"vulnerabilities(Command injection in workspace-tools, ",
					"https://github.com/advisories/GHSA-5875-m6jq-vf78)")
			});
	}

	@Override
	protected SourceFormatterArgs getSourceFormatterArgs() {
		SourceFormatterArgs sourceFormatterArgs =
			super.getSourceFormatterArgs();

		sourceFormatterArgs.setCheckVulnerabilities(true);

		return sourceFormatterArgs;
	}

}