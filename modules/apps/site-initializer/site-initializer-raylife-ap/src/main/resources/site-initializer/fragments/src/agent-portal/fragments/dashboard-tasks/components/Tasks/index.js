/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayIcon from '@clayui/icon';
import React, {useEffect, useState} from 'react';

import {
	getApplicationsStatus,
	getClaimsStatus,
	getPoliciesStatus,
	getReportsStatus,
} from '../../../../../common/services';
import {CONSTANTS} from '../../../../../common/utils/constants';
import Sections from '../Section';

export default function () {
	const [sections, setSections] = useState([]);

	useEffect(() => {
		Promise.allSettled([
			getApplicationsStatus(CONSTANTS.STATUS.INCOMPLETE),
			getApplicationsStatus(CONSTANTS.STATUS.QUOTED),
			getApplicationsStatus(CONSTANTS.STATUS.REVIEWED),
			getClaimsStatus(CONSTANTS.STATUS.ININVESTIGATION),
			getClaimsStatus(CONSTANTS.STATUS.APPROVED),
			getPoliciesStatus(4),
			getPoliciesStatus(5),
			getReportsStatus(1),
			getReportsStatus(7),
		]).then((results) => {
			const getTotalCount = (result) => {
				return result?.value?.data?.totalCount || 0;
			};

			const [
				incompleteApplicationsResult,
				quotedApplicationsResult,
				reviewedApplicationsResult,
				inInvestigationClaimsResult,
				approvedClaimsResult,
				firstPoliciesResult,
				secondPoliciesResult,
				firstReportsResult,
				secondReportsResult,
			] = results;

			const loadSections = [
				{
					active: true,
					index: 0,
					name: 'Applications',
					subSections: [
						{
							name: 'Review Eligibility Reports',
							value: getTotalCount(incompleteApplicationsResult),
						},
						{
							name: 'Review Underwriting Questions',
							value: getTotalCount(quotedApplicationsResult),
						},
						{
							name: 'Policy Ready to Bind',
							value: getTotalCount(reviewedApplicationsResult),
						},
					],
				},
				{
					active: true,
					index: 2,
					name: 'Claims',
					subSections: [
						{
							name: 'Request Additional Information',

							value: getTotalCount(inInvestigationClaimsResult),
						},
						{
							name: 'Notify Repair Options',

							value: getTotalCount(approvedClaimsResult),
						},
					],
				},
				{
					active: true,
					index: 1,
					name: 'Policies',
					subSections: [
						{
							name: 'Contact Expiring Policies',
							value: getTotalCount(firstPoliciesResult),
						},
						{
							name: 'Contact Past Due Payment',
							value: getTotalCount(secondPoliciesResult),
						},
					],
				},
				{
					active: false,
					index: 3,
					name: 'Reports',
					subSections: [
						{
							name: 'Renew expiring policies',
							value: getTotalCount(firstReportsResult),
						},
						{
							name: 'Review Quote',
							value: getTotalCount(secondReportsResult),
						},
					],
				},
			].sort((a, b) =>
				a.index < b.index ? -1 : a.index > b.index ? 1 : 0
			);

			setSections(loadSections);
		});
	}, []);

	const settingsOnClick = () => {
		const EVENT_OPTION = {
			async: true,
			fireOn: true,
		};

		const eventUserAccount = Liferay.publish('open-settings', EVENT_OPTION);

		eventUserAccount.fire({
			modalName: 'tasks-settings-modal',
		});
	};

	return (
		<div className="dashboard-tasks-container flex-shrink-0 pb-4 pt-3 px-3">
			<div className="align-items-center d-flex dashboard-tasks-header justify-content-between">
				<div className="dashboard-tasks-title font-weight-bolder h4">
					Tasks
				</div>

				<div className="mr-2 settings-icon">
					<ClayIcon
						className="text-neutral-5"
						onClick={settingsOnClick}
						symbol="cog"
					/>
				</div>
			</div>

			<div className="d-flex dashboard-tasks-content flex-column mx-3">
				{sections.map((section, index) => {
					if (section.active) {
						return <Sections index={index} section={section} />;
					}
				})}
			</div>
		</div>
	);
}
