/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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