/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.plugins.wsrp.usecase.demo1;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.util.BaseTestCase;

import com.liferay.portalweb.portal.util.TestPropsValues;

/**
 * @author Brian Wing Shun Chan
 */
public class EvaluateWSRPVCFFileTest extends BaseTestCase {

	public void testEvaluateWSRPVCFFile() throws Exception {
		assertTrue(evaluateWSRPVCFFile("vcard1DP.vcf"));
		assertTrue(evaluateWSRPVCFFile("vcard1RDP.vcf"));
	}

	private boolean evaluateWSRPVCFFile(String vcfFile) throws Exception {
		String fileName = TestPropsValues.OUTPUT_DIR + vcfFile;

		String xml = FileUtil.read(fileName);

		if (!xml.contains("BEGIN:VCARD")) {
			return false;
		}

		return true;
	}

}