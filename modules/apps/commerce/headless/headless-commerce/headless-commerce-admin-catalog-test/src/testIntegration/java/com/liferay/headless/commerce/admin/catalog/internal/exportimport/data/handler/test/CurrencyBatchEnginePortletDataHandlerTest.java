/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.exportimport.test.rule.ExportImportScopes;
import com.liferay.exportimport.test.util.exportimport.data.handler.BaseBatchEnginePortletDataHandlerTestCase;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate.Scope;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.CurrencyResource;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Date;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Alberto Javier Moreno Lage
 */
@ExportImportScopes(Scope.COMPANY)
@RunWith(Arquillian.class)
public class CurrencyBatchEnginePortletDataHandlerTest
	extends BaseBatchEnginePortletDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected String addEntry(long groupId, long userId, Date dateModified)
		throws Exception {

		CommerceCurrency commerceCurrency =
			CommerceCurrencyTestUtil.addCommerceCurrency(
				_getCompanyId(groupId));

		commerceCurrency.setModifiedDate(dateModified);

		commerceCurrency = _commerceCurrencyLocalService.updateCommerceCurrency(
			commerceCurrency);

		return commerceCurrency.getExternalReferenceCode();
	}

	@Override
	protected void addStagedModels() throws Exception {
	}

	@Override
	protected void deleteEntry(long groupId, String externalReferenceCode)
		throws Exception {

		_commerceCurrencyLocalService.deleteCommerceCurrency(
			_getCommerceCurrency(groupId, externalReferenceCode));
	}

	@Override
	protected long getCreatorUserId(long groupId, String externalReferenceCode)
		throws Exception {

		CommerceCurrency commerceCurrency = _getCommerceCurrency(
			groupId, externalReferenceCode);

		return commerceCurrency.getUserId();
	}

	@Override
	protected Object getEntryValue(long groupId, String externalReferenceCode)
		throws Exception {

		CommerceCurrency commerceCurrency = _getCommerceCurrency(
			groupId, externalReferenceCode);

		return commerceCurrency.getSymbol();
	}

	@Override
	protected ExportImportVulcanBatchEngineTaskItemDelegate<?>
		getExportImportVulcanBatchEngineTaskItemDelegate() {

		return getExportImportVulcanBatchEngineTaskItemDelegate(
			CurrencyResource.class);
	}

	@Override
	protected List<String> getExternalReferenceCodes(long groupId)
		throws Exception {

		return TransformUtil.transform(
			_commerceCurrencyLocalService.getCommerceCurrencies(
				_getCompanyId(groupId), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null),
			CommerceCurrency::getExternalReferenceCode);
	}

	@Override
	protected long getPrimaryKey(long groupId, String externalReferenceCode)
		throws Exception {

		CommerceCurrency commerceCurrency = _getCommerceCurrency(
			groupId, externalReferenceCode);

		return commerceCurrency.getCommerceCurrencyId();
	}

	@Override
	protected boolean supportsComments() {
		return false;
	}

	@Override
	protected boolean supportsEmptyEntries() {
		return false;
	}

	@Override
	protected boolean supportsPermissions() {
		return false;
	}

	@Override
	protected void updateEntry(long groupId, String externalReferenceCode)
		throws Exception {

		CommerceCurrency commerceCurrency = _getCommerceCurrency(
			groupId, externalReferenceCode);

		commerceCurrency.setSymbol(RandomTestUtil.randomString());

		_commerceCurrencyLocalService.updateCommerceCurrency(commerceCurrency);
	}

	private CommerceCurrency _getCommerceCurrency(
			long groupId, String externalReferenceCode)
		throws Exception {

		return _commerceCurrencyLocalService.
			fetchCommerceCurrencyByExternalReferenceCode(
				externalReferenceCode, _getCompanyId(groupId));
	}

	private long _getCompanyId(long groupId) throws Exception {
		Group group = _groupLocalService.getGroup(groupId);

		return group.getCompanyId();
	}

	@Inject
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

}