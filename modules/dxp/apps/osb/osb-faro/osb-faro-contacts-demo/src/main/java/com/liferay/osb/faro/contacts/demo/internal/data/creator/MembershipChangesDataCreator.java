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
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Time;

import java.util.Date;
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
		IndividualSegmentMembershipChange individualSegmentMembershipChange =
			(IndividualSegmentMembershipChange)params[0];

		return HashMapBuilder.<String, Object>put(
			"dateChanged",
			() -> {
				Date dateChanged =
					individualSegmentMembershipChange.getDateChanged();

				return formatDate(new Date(dateChanged.getTime() - Time.MONTH));
			}
		).put(
			"dateFirst",
			() -> {
				Date dateFirst =
					individualSegmentMembershipChange.getDateFirst();

				return formatDate(new Date(dateFirst.getTime() - Time.MONTH));
			}
		).put(
			"id", individualSegmentMembershipChange.getId()
		).put(
			"individualDeleted",
			individualSegmentMembershipChange.isIndividualDeleted()
		).put(
			"individualEmail",
			individualSegmentMembershipChange.getIndividualEmail()
		).put(
			"individualId", individualSegmentMembershipChange.getIndividualId()
		).put(
			"individualName",
			individualSegmentMembershipChange.getIndividualName()
		).put(
			"individualsCount",
			individualSegmentMembershipChange.getIndividualsCount()
		).put(
			"individualSegmentId",
			individualSegmentMembershipChange.getIndividualSegmentId()
		).put(
			"operation", individualSegmentMembershipChange.getOperation()
		).build();
	}

}