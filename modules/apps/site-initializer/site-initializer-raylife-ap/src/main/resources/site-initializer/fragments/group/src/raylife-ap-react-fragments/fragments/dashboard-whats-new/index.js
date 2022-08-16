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

import SectionList from '../../../common/components/section-list';
import ClayIconProvider from '../../../common/context/ClayIconProvider';
import {getApplicationsStatus} from '../../../common/services/Application';
import {CONSTANTS} from '../../../common/utils/constants';

const settingsOnClick = () => {
	const EVENT_OPTION = {
		async: true,
		fireOn: true,
	};

	const eventPublish = Liferay.publish(
		'openSettingsModalEvent',
		EVENT_OPTION
	);

	eventPublish.fire({
		show: true,
	});
};

export default function () {
	const [sections, setSections] = useState([]);

	const getTotalCount = (result) => {
		return result?.value?.data?.totalCount || 0;
	};

	const loadData = () => {
		Promise.allSettled([
			getApplicationsStatus(CONSTANTS.STATUS.INCOMPLETE),
			getApplicationsStatus(CONSTANTS.STATUS.QUOTED),
			getApplicationsStatus(CONSTANTS.STATUS.OPEN),
		]).then((results) => {
			const [
				incompleteApplicationsResult,
				quotedApplicationsResult,
				openApplicationsResults,
			] = results;

			const loadSections = [
				{
					active: true,
					index: 0,
					link: 'Applications',
					name: 'Leads',
					subSections: [
						{
							active: true,
							name: 'Unassigned',
							value: getTotalCount(quotedApplicationsResult),
						},
						{
							active: true,
							name: 'Abandoned',
							value: getTotalCount(incompleteApplicationsResult),
						},
						{
							active: true,
							name: 'Open',
							value: getTotalCount(openApplicationsResults),
						},
					],
				},
			];

			setSections(loadSections);
		});
	};

	useEffect(() => {
		loadData();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<ClayIconProvider>
			<div className="dashboard-whats-new-container flex-shrink-0 pb-4 pt-3 px-3">
				<div className="align-items-center d-flex dashboard-whats-new-header justify-content-between">
					<div className="dashboard-whats-new-title font-weight-bolder h4 mb-0">
						What&apos;s New
					</div>

					<div className="mr-2 settings-icon">
						<ClayIcon
							className="text-neutral-5"
							onClick={settingsOnClick}
							symbol="cog"
						/>
					</div>
				</div>

				<div className="dashboard-whats-new-subtext mb-3 mx-3 text-neutral-9">
					Since the end of last business day
				</div>

				<SectionList sections={sections} />
			</div>
		</ClayIconProvider>
	);
}
