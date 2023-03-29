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

package com.liferay.osb.faro.contacts.demo.internal.data.creator;

import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.model.FaroProject;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Matthew Kong
 */
public class SalesforceAccountsDataCreator extends DataCreator {

	public SalesforceAccountsDataCreator(
		ContactsEngineClient contactsEngineClient, FaroProject faroProject,
		String dataSourceId) {

		super(
			contactsEngineClient, faroProject, "osbasahsalesforceraw",
			"Account");

		_dataSourceId = dataSourceId;

		_salesforceAuditEventsDataCreator =
			new SalesforceAuditEventsDataCreator(
				contactsEngineClient, faroProject, "Account");
	}

	@Override
	public void execute() {
		super.execute();

		_salesforceAuditEventsDataCreator.execute();
	}

	@Override
	protected Map<String, Object> doCreate(Object[] params) {
		Map<String, Object> salesforceAccount = new HashMap<>();

		salesforceAccount.put("dataSourceId", _dataSourceId);

		Map<String, Object> fields = new HashMap<>();

		fields.put("AnnualRevenue", number.numberBetween(0, 1000) * 1000);
		fields.put("BillingCity", address.city());
		fields.put("BillingCountry", address.country());
		fields.put("BillingPostalCode", address.zipCode());
		fields.put("BillingState", address.state());
		fields.put("BillingStreet", address.streetAddress());
		fields.put(
			"CurrencyIsoCode",
			_currencyIsoCodes.get(random.nextInt(_currencyIsoCodes.size())));
		fields.put("Description", company.catchPhrase());
		fields.put("Fax", phoneNumber.phoneNumber());
		fields.put("Industry", company.industry());
		fields.put("LastModifiedDate", formatDate(new Date()));
		fields.put("Name", company.name());
		fields.put("NumberOfEmployees", number.numberBetween(1, 100000));
		fields.put("Ownership", "Private");
		fields.put("Phone", phoneNumber.phoneNumber());
		fields.put("ShippingCity", address.city());
		fields.put("ShippingCountry", address.country());
		fields.put("ShippingPostalCode", address.zipCode());
		fields.put("ShippingState", address.state());
		fields.put("ShippingStreet", address.streetAddress());
		fields.put("Type", "Customer");
		fields.put("Website", "https://" + internet.url());
		fields.put("YearStarted", number.numberBetween(1900, 2019));

		salesforceAccount.put("fields", fields);

		salesforceAccount.put("id", internet.uuid());

		_salesforceAuditEventsDataCreator.create(
			new Object[] {salesforceAccount});

		return salesforceAccount;
	}

	private static final List<String> _currencyIsoCodes = Arrays.asList(
		"CNY", "EUR", "GBP", "USD");

	private final String _dataSourceId;
	private final SalesforceAuditEventsDataCreator
		_salesforceAuditEventsDataCreator;

}