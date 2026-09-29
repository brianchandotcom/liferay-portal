/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.report.internal.exporter.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.test.util.CommerceAccountTestUtil;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.report.exporter.CommerceReportExporter;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.ByteArrayInputStream;

import java.math.BigDecimal;

import java.util.Collections;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian I. Kim
 */
@RunWith(Arquillian.class)
public class CommerceReportExporterImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		Group group = GroupTestUtil.addGroup();
		_user = UserTestUtil.addUser();

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			group.getCompanyId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			group.getGroupId(), _commerceCurrency.getCode());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_commerceChannel.getSiteGroupId());

		_accountEntry = CommerceAccountTestUtil.addBusinessAccountEntry(
			_user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString() + "@liferay.com",
			RandomTestUtil.randomString(), new long[] {_user.getUserId()},
			new long[0], _serviceContext);
	}

	@Test
	public void testExport() throws Exception {
		CommerceOrder commerceOrder = CommerceTestUtil.addB2BCommerceOrder(
			_commerceChannel.getSiteGroupId(), _user.getUserId(),
			_accountEntry.getAccountEntryId(),
			_commerceCurrency.getCommerceCurrencyId());

		CPInstance cpInstance = CPTestUtil.addCPInstance(
			_commerceChannel.getSiteGroupId());

		CommerceTestUtil.updateBackOrderCPDefinitionInventory(
			cpInstance.getCPDefinition());

		CommerceTestUtil.addCommerceOrderItem(
			commerceOrder.getCommerceOrderId(), cpInstance.getCPInstanceId(),
			BigDecimal.TEN);

		byte[] bytes = _commerceReportExporter.export(
			commerceOrder.getCommerceOrderItems(), null,
			HashMapBuilder.<String, Object>put(
				"language", _language
			).put(
				"locale", LocaleUtil.US
			).put(
				"shippingAmountMoney", commerceOrder.getShippingMoney()
			).put(
				"siteDefaultLocale", LocaleUtil.US
			).put(
				"skuOptions", Collections.emptyMap()
			).put(
				"totalMoney", commerceOrder.getTotalMoney()
			).put(
				"totalWithTaxAmountMoney",
				commerceOrder.getTotalWithTaxAmountMoney()
			).build());

		Assert.assertFalse(ArrayUtil.isEmpty(bytes));

		bytes = _commerceReportExporter.export(
			Collections.emptyList(),
			_addFileEntry(_getJRXML(StringPool.BLANK, "\"a\".toUpperCase()")),
			new HashMap<>());

		Assert.assertFalse(ArrayUtil.isEmpty(bytes));

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME_COMMERCE_REPORT_EXPORTER_IMPL,
				LoggerTestUtil.OFF)) {

			bytes = _commerceReportExporter.export(
				Collections.emptyList(),
				_addFileEntry(
					_getJRXML(
						StringPool.BLANK,
						"java.util.UUID.randomUUID().toString()")),
				new HashMap<>());

			Assert.assertTrue(ArrayUtil.isEmpty(bytes));
		}

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.commerce.report.internal.configuration." +
						"CommerceReportExporterConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"allowedClasses", new String[] {"java.util.UUID"}
					).build())) {

			bytes = _commerceReportExporter.export(
				Collections.emptyList(),
				_addFileEntry(
					_getJRXML(
						StringPool.BLANK,
						"java.util.UUID.randomUUID().toString()")),
				new HashMap<>());

			Assert.assertFalse(ArrayUtil.isEmpty(bytes));
		}
	}

	@Test
	public void testIsValidJRXMLTemplate() throws Exception {
		Assert.assertTrue(
			_commerceReportExporter.isValidJRXMLTemplate(
				new ByteArrayInputStream(
					_getJRXML(StringPool.BLANK, "\"a\""))));

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME_COMMERCE_REPORT_EXPORTER_IMPL,
				LoggerTestUtil.OFF)) {

			Assert.assertFalse(
				_commerceReportExporter.isValidJRXMLTemplate(
					new ByteArrayInputStream(
						_getJRXML(
							"formatFactoryClass=\"net.sf.jasperreports." +
								"engine.util.DefaultFormatFactory\"",
							"\"a\""))));
			Assert.assertFalse(
				_commerceReportExporter.isValidJRXMLTemplate(
					new ByteArrayInputStream(
						_getJRXML("language=\"groovy\"", "\"a\""))));
			Assert.assertFalse(
				_commerceReportExporter.isValidJRXMLTemplate(
					new ByteArrayInputStream(
						_getJRXML(
							"scriptletClass=\"net.sf.jasperreports.engine." +
								"JRDefaultScriptlet\"",
							"\"a\""))));
		}
	}

	private FileEntry _addFileEntry(byte[] bytes) throws Exception {
		return _dlAppLocalService.addFileEntry(
			null, _user.getUserId(), _commerceChannel.getSiteGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".jrxml", ContentTypes.TEXT_XML,
			bytes, null, null, null, _serviceContext);
	}

	private byte[] _getJRXML(String attributes, String expression) {
		String jrxml = StringBundler.concat(
			"<jasperReport ", attributes, " columnWidth=\"555\" name=\"",
			RandomTestUtil.randomString(), "\" pageHeight=\"842\" pageWidth=",
			"\"595\" whenNoDataType=\"AllSectionsNoDetail\"><title height=\"40",
			"\"><element height=\"30\" kind=\"textField\" width=\"555\" x=\"0",
			"\" y=\"0\"><expression><![CDATA[", expression,
			"]]></expression></element></title></jasperReport>");

		return jrxml.getBytes();
	}

	private static final String _CLASS_NAME_COMMERCE_REPORT_EXPORTER_IMPL =
		"com.liferay.commerce.report.internal.exporter." +
			"CommerceReportExporterImpl";

	private AccountEntry _accountEntry;
	private CommerceChannel _commerceChannel;
	private CommerceCurrency _commerceCurrency;

	@Inject
	private CommerceReportExporter _commerceReportExporter;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private Language _language;

	private ServiceContext _serviceContext;
	private User _user;

}