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
import com.liferay.osb.faro.engine.client.model.IndividualSegmentMembershipChange;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.portal.kernel.util.Time;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Matthew Kong
 */
public class MembershipChangesDataCreator extends DataCreator {

	public MembershipChangesDataCreator(
		ContactsEngineClient contactsEngineClient, FaroProject faroProject) {

		super(
			contactsEngineClient, faroProject, "osbasahfaroinfo",
			"membership-changes");
	}

	@Override
	protected Map<String, Object> doCreate(Object[] params) {
		Map<String, Object> membershipChange = new HashMap<>();

		IndividualSegmentMembershipChange individualSegmentMembershipChange =
			(IndividualSegmentMembershipChange)params[0];

		Date dateChanged = individualSegmentMembershipChange.getDateChanged();

		membershipChange.put(
			"dateChanged",
			formatDate(new Date(dateChanged.getTime() - Time.MONTH)));

		Date dateFirst = individualSegmentMembershipChange.getDateFirst();

		membershipChange.put(
			"dateFirst",
			formatDate(new Date(dateFirst.getTime() - Time.MONTH)));

		membershipChange.put("id", individualSegmentMembershipChange.getId());
		membershipChange.put(
			"individualDeleted",
			individualSegmentMembershipChange.isIndividualDeleted());
		membershipChange.put(
			"individualEmail",
			individualSegmentMembershipChange.getIndividualEmail());
		membershipChange.put(
			"individualId",
			individualSegmentMembershipChange.getIndividualId());
		membershipChange.put(
			"individualName",
			individualSegmentMembershipChange.getIndividualName());
		membershipChange.put(
			"individualsCount",
			individualSegmentMembershipChange.getIndividualsCount());
		membershipChange.put(
			"individualSegmentId",
			individualSegmentMembershipChange.getIndividualSegmentId());
		membershipChange.put(
			"operation", individualSegmentMembershipChange.getOperation());

		return membershipChange;
	}

}