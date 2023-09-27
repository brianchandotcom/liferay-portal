/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.url;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Julius Lee
 */
public class URLBuilderTest {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		Mockito.when(
			_portal.stripURLAnchor(Mockito.anyString(), Mockito.anyString())
		).thenAnswer(
			input -> _testStripURLAnchor(
				(String)input.getArguments()[0],
				(String)input.getArguments()[1])
		);

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(_portal);
	}

	@Test
	public void testAddParameter() {
		URLBuilder urlBuilder = URLBuilder.create(
			"http://test.com"
		).addParameter(
			"testKey", "testValue"
		);

		Assert.assertEquals(
			"http://test.com?testKey=testValue", urlBuilder.build());

		urlBuilder.addParameter("testKey", "testValue");

		Assert.assertEquals(
			"http://test.com?testKey=testValue&testKey=testValue",
			urlBuilder.build());
	}

	@Test
	public void testAddParameterWithAnchor() {
		URLBuilder urlBuilder = URLBuilder.create(
			"http://test.com#TestAnchor"
		).addParameter(
			"testKey", "testValue"
		);

		Assert.assertEquals(
			"http://test.com?testKey=testValue#TestAnchor", urlBuilder.build());

		urlBuilder.addParameter(
			"testKey", "testValue"
		).build();

		Assert.assertEquals(
			"http://test.com?testKey=testValue&testKey=testValue#TestAnchor",
			urlBuilder.build());
	}

	@Test
	public void testAddParameterWithNullInput() {
		Assert.assertNull(
			URLBuilder.create(
				null
			).addParameter(
				null, null
			).setParameter(
				null, null
			).build());

		Assert.assertEquals(
			"http://test.com?null=null",
			URLBuilder.create(
				"http://test.com"
			).addParameter(
				null, null
			).build());

		Assert.assertEquals(
			"http://test.com",
			URLBuilder.create(
				"http://test.com"
			).setParameter(
				null, null
			).build());
	}

	@Test
	public void testInOrderMultipleOperations() {
		URLBuilder urlBuilder = URLBuilder.create("http://test.com");

		StringBundler sb = new StringBundler(32);

		sb.append("http://test.com");
		sb.append(StringPool.QUESTION);

		for (int i = 1; i <= 5; i++) {
			urlBuilder.addParameter("testKey" + i, "testValue" + i);

			sb.append("testKey");
			sb.append(i);
			sb.append(StringPool.EQUAL);
			sb.append("testValue");
			sb.append(i);
			sb.append(StringPool.AMPERSAND);
		}

		sb.setIndex(sb.index() - 1);

		Assert.assertEquals(sb.toString(), urlBuilder.build());
	}

	@Test
	public void testRemoveParameter() {
		Assert.assertEquals(
			"http://test.com",
			URLBuilder.create(
				"http://test.com?testKey=testValue"
			).removeParameter(
				"testKey"
			).build());
	}

	@Test
	public void testRemoveParameterWithAnchor() {
		Assert.assertEquals(
			"http://test.com#TestAnchor",
			URLBuilder.create(
				"http://test.com?testKey=testValue#TestAnchor"
			).removeParameter(
				"testKey"
			).build());
	}

	@Test
	public void testSetParameter() {
		Assert.assertEquals(
			"http://test.com?testKey=testValue",
			URLBuilder.create(
				"http://test.com"
			).setParameter(
				"testKey", "testValue"
			).build());

		Assert.assertEquals(
			"http://test.com?testKey=testValueReplaced",
			URLBuilder.create(
				"http://test.com"
			).setParameter(
				"testKey", "testValue"
			).setParameter(
				"testKey", "testValueReplaced"
			).build());
	}

	@Test
	public void testSetParameterWithAnchor() {
		Assert.assertEquals(
			"http://test.com?testKey=testValue#TestAnchor",
			URLBuilder.create(
				"http://test.com#TestAnchor"
			).setParameter(
				"testKey", "testValue"
			).build());

		Assert.assertEquals(
			"http://test.com?testKey=testValueReplaced#TestAnchor",
			URLBuilder.create(
				"http://test.com#TestAnchor"
			).setParameter(
				"testKey", "testValue"
			).setParameter(
				"testKey", "testValueReplaced"
			).build());
	}

	/**
	 * @see com.liferay.portal.util.PortalImpl
	 *
	 * _testStripURLAnchor is copied from PortalImpl for ease of testing
	 */
	private String[] _testStripURLAnchor(String url, String separator) {
		String anchor = StringPool.BLANK;

		int pos = url.indexOf(separator);

		if (pos != -1) {
			anchor = url.substring(pos);
			url = url.substring(0, pos);
		}

		return new String[] {url, anchor};
	}

	@Mock
	private Portal _portal;

}