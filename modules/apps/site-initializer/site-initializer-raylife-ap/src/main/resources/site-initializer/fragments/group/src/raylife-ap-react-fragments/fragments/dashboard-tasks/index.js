/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import React, {useEffect, useState} from 'react';

import SectionList from '../../../common/components/section-list';
import ClayIconProvider from '../../../common/context/ClayIconProvider';
import {
	getApplicationsStatus,
	getClaimsStatus,
	getPoliciesStatus,
	getReportsStatus,
} from '../../../common/services';
import {CONSTANTS} from '../../../common/utils/constants';

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
					link: 'Applications',
					name: 'Applications',
					subSections: [
						{
							active: true,
							name: 'Review Eligibility Reports',
							value: getTotalCount(incompleteApplicationsResult),
						},
						{
							active: true,
							name: 'Review Underwriting Questions',
							value: getTotalCount(quotedApplicationsResult),
						},
						{
							active: true,
							name: 'Policy Ready to Bind',
							value: getTotalCount(reviewedApplicationsResult),
						},
					],
				},
				{
					active: true,
					index: 2,
					link: 'Claims',
					name: 'Claims',
					subSections: [
						{
							active: true,
							name: 'Request Additional Information',
							value: getTotalCount(inInvestigationClaimsResult),
						},
						{
							active: true,
							name: 'Notify Repair Options',
							value: getTotalCount(approvedClaimsResult),
						},
					],
				},
				{
					active: true,
					index: 1,
					link: 'Policies',
					name: 'Policies',
					subSections: [
						{
							active: true,
							name: 'Contact Expiring Policies',
							value: getTotalCount(firstPoliciesResult),
						},
						{
							active: true,
							name: 'Contact Past Due Payment',
							value: getTotalCount(secondPoliciesResult),
						},
					],
				},
				{
					active: false,
					index: 3,
					link: 'Reports',
					name: 'Reports',
					subSections: [
						{
							active: true,
							name: 'Renew expiring policies',
							value: getTotalCount(firstReportsResult),
						},
						{
							active: true,
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
		<ClayIconProvider>
			<div className="dashboard-tasks-container flex-shrink-0 pb-4 pt-3 px-3">
				<div className="align-items-center d-flex dashboard-tasks-header justify-content-between">
					<div className="dashboard-tasks-title font-weight-bolder h4 mb-0">
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

				<SectionList sections={sections} />
			</div>
		</ClayIconProvider>
	);
}
