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
import com.liferay.petra.string.StringPool;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Matthew Kong
 */
public class SalesforceIndividualsDataCreator extends DataCreator {

	public SalesforceIndividualsDataCreator(
		ContactsEngineClient contactsEngineClient, FaroProject faroProject,
		String dataSourceId) {

		super(
			contactsEngineClient, faroProject, "osbasahsalesforceraw",
			"individuals");

		_dataSourceId = dataSourceId;

		_salesforceAuditEventsDataCreator =
			new SalesforceAuditEventsDataCreator(
				contactsEngineClient, faroProject, "individuals");
	}

	@Override
	public void execute() {
		super.execute();

		_salesforceAuditEventsDataCreator.execute();
	}

	@Override
	protected Map<String, Object> doCreate(Object[] params) {
		Map<String, Object> salesforceIndividual = new HashMap<>();

		salesforceIndividual.put("dataSourceId", _dataSourceId);

		Map<String, Object> fields = new HashMap<>();

		Map<String, Object> liferayUser = new HashMap<>();
		Map<String, Object> salesforceAccount = new HashMap<>();

		if (params != null) {
			liferayUser = (Map<String, Object>)params[0];
			salesforceAccount = (Map<String, Object>)params[1];
		}

		Object accountPKs = salesforceAccount.get("id");

		if (accountPKs != null) {
			fields.put("accountPKs", Collections.singletonList(accountPKs));
		}

		fields.put(
			"birthDate",
			liferayUser.getOrDefault(
				"birthday", dateAndTime.past(18250, TimeUnit.DAYS)));
		fields.put("city", address.city());
		fields.put(
			"company", salesforceAccount.getOrDefault("Name", company.name()));

		if (!salesforceAccount.isEmpty()) {
			fields.put("contactId", number.randomNumber(8, false));
		}

		fields.put("country", address.country());
		fields.put("department", commerce.department());
		fields.put("description", company.buzzword());
		fields.put("doNotCall", bool.bool());

		String firstName = (String)liferayUser.getOrDefault(
			"firstName", name.firstName());
		String lastName = (String)liferayUser.getOrDefault(
			"lastName", name.lastName());

		fields.put(
			"email",
			liferayUser.getOrDefault(
				"emailAddress",
				internet.emailAddress(
					firstName + StringPool.PERIOD + lastName)));

		fields.put("fax", phoneNumber.phoneNumber());
		fields.put("firstName", firstName);
		fields.put("fullName", firstName + StringPool.SPACE + lastName);
		fields.put(
			"industry",
			salesforceAccount.getOrDefault("Industry", company.industry()));
		fields.put("lastName", lastName);
		fields.put("middleName", name.firstName());
		fields.put("mobilePhone", phoneNumber.cellPhone());
		fields.put("modifiedDate", formatDate(new Date()));
		fields.put("phone", phoneNumber.phoneNumber());
		fields.put("postalCode", address.zipCode());
		fields.put("state", address.state());
		fields.put("street", address.streetAddress());
		fields.put("suffix", name.suffix());
		fields.put("title", name.title());

		salesforceIndividual.put("fields", fields);
		salesforceIndividual.put("id", internet.uuid());

		_salesforceAuditEventsDataCreator.create(
			new Object[] {salesforceIndividual});

		return salesforceIndividual;
	}

	private final String _dataSourceId;
	private final SalesforceAuditEventsDataCreator
		_salesforceAuditEventsDataCreator;

}