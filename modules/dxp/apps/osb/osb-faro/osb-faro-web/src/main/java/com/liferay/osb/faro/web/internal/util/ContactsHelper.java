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

package com.liferay.osb.faro.web.internal.util;

import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.engine.client.constants.FieldMappingConstants;
import com.liferay.osb.faro.engine.client.model.Individual;
import com.liferay.osb.faro.engine.client.model.IndividualSegment;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.web.internal.constants.FaroConstants;
import com.liferay.osb.faro.web.internal.model.display.contacts.IndividualDisplay;
import com.liferay.osb.faro.web.internal.model.display.contacts.IndividualSegmentDisplay;
import com.liferay.osb.faro.web.internal.model.display.main.FaroEntityDisplay;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shinn Lok
 */
@Component(service = ContactsHelper.class)
public class ContactsHelper {

	public FaroEntityDisplay getContactsEntityDisplay(
		FaroProject faroProject, String contactsEntityId, int type) {

		if (type == FaroConstants.TYPE_INDIVIDUAL) {
			Individual individual = _contactsEngineClient.getIndividual(
				faroProject, contactsEntityId, null);

			return new IndividualDisplay(individual);
		}

		IndividualSegment individualSegment =
			_contactsEngineClient.getIndividualSegment(
				faroProject, contactsEntityId, false);

		return new IndividualSegmentDisplay(individualSegment);
	}

	public String getOwnerType(int type) {
		if (type == FaroConstants.TYPE_ACCOUNT) {
			return FieldMappingConstants.OWNER_TYPE_ACCOUNT;
		}
		else if (type == FaroConstants.TYPE_INDIVIDUAL) {
			return FieldMappingConstants.OWNER_TYPE_INDIVIDUAL;
		}

		return FieldMappingConstants.OWNER_TYPE_INDIVIDUAL_SEGMENT;
	}

	@Reference
	private ContactsEngineClient _contactsEngineClient;

}