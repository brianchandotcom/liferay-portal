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

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.fasterxml.jackson.core.type.TypeReference;

import com.liferay.osb.faro.engine.client.model.IndividualSegmentMembershipChange;
import com.liferay.osb.faro.web.internal.util.JSONUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class IndividualSegmentMembershipChangeDisplay {

	public IndividualSegmentMembershipChangeDisplay() {
	}

	@SuppressWarnings("unchecked")
	public IndividualSegmentMembershipChangeDisplay(
		IndividualSegmentMembershipChange individualSegmentMembershipChange) {

		Map<String, Object> embeddedResources =
			individualSegmentMembershipChange.getEmbeddedResources();

		if (MapUtil.isNotEmpty(embeddedResources)) {
			_accountNames = JSONUtil.convertValue(
				embeddedResources.get("account-names"),
				new TypeReference<List<String>>() {
				});
		}

		_dateChanged = individualSegmentMembershipChange.getDateChanged();
		_dateFirst = individualSegmentMembershipChange.getDateFirst();
		_id = individualSegmentMembershipChange.getId();
		_individualDeleted =
			individualSegmentMembershipChange.isIndividualDeleted();
		_individualEmail =
			individualSegmentMembershipChange.getIndividualEmail();
		_individualId = individualSegmentMembershipChange.getIndividualId();
		_individualName = individualSegmentMembershipChange.getIndividualName();
		_individualsCount =
			individualSegmentMembershipChange.getIndividualsCount();
		_individualSegmentId =
			individualSegmentMembershipChange.getIndividualSegmentId();
		_operation = individualSegmentMembershipChange.getOperation();
	}

	private List<String> _accountNames;
	private Date _dateChanged;
	private Date _dateFirst;
	private String _id;
	private boolean _individualDeleted;
	private String _individualEmail;
	private String _individualId;
	private String _individualName;
	private Long _individualsCount;
	private String _individualSegmentId;
	private String _operation;

}