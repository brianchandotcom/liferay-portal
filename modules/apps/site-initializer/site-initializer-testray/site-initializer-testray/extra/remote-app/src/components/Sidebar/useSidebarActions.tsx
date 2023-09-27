/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useContext} from 'react';

import {TestrayContext} from '../../context/TestrayContext';
import useModalContext from '../../hooks/useModalContext';
import i18n from '../../i18n';
import CaseTypeModal from '../../pages/Standalone/CaseType/CaseTypeModal';
import FactorCategoryModal from '../../pages/Standalone/FactorCategory/FactorCategoryModal';
import OptionsModal from '../../pages/Standalone/FactorOptions/FactorOptionsModal';
import {LIFERAY_URLS} from '../../services/liferay';
import {DispatchTriggerStatuses} from '../../util/statuses';
import JobSchedulerModal from '../JobSchedulerModal';

const useSidebarActions = () => {
	const {onOpenModal} = useModalContext();
	const [{testrayDispatchTriggers}] = useContext(TestrayContext);

	const jobsInProgress =
		testrayDispatchTriggers?.facets[0]?.facetValues
			.filter((facetValue) =>
				([
					DispatchTriggerStatuses.INPROGRESS,
					DispatchTriggerStatuses.SCHEDULED,
				] as string[]).includes(facetValue.term)
			)
			.map((facetValue) => facetValue.numberOfOccurrences)
			.reduce(
				(previousValue, currentValue) => previousValue + currentValue,
				0
			) ?? 0;

	const MANAGE_DROPDOWN = [
		{
			items: [
				{
					icon: 'plus',
					label: i18n.translate('add-project'),
					path: '/project/create',
				},
				{
					icon: 'cog',
					label: i18n.translate('case-types'),
					onClick: () =>
						onOpenModal({
							body: <CaseTypeModal />,
							size: 'full-screen',
							title: i18n.translate('case-types'),
						}),
				},
			],
			title: i18n.translate('system'),
		},
		{
			items: [
				{
					icon: 'cog',
					label: i18n.translate('categories'),
					onClick: () =>
						onOpenModal({
							body: <FactorCategoryModal />,
							size: 'full-screen',
							title: i18n.translate('categories'),
						}),
				},
				{
					icon: 'cog',
					label: i18n.translate('options'),
					onClick: () =>
						onOpenModal({
							body: <OptionsModal />,
							size: 'full-screen',
							title: i18n.translate('options'),
						}),
				},
			],
			title: i18n.translate('environment-factors'),
		},
		{
			items: [
				{
					icon: 'pencil',
					label: i18n.translate('manage-users'),
					path: '/manage/user',
				},
				{
					icon: 'pencil',
					label: i18n.translate('manage-user-groups'),
					path: LIFERAY_URLS.manage_user_groups,
				},
				{
					icon: 'pencil',
					label: i18n.translate('manage-roles'),
					path: LIFERAY_URLS.manage_roles,
				},
			],
			title: '',
		},
		{
			items: [
				{
					icon: 'pencil',
					label: i18n.translate('manage-server'),
					path: LIFERAY_URLS.manage_server,
				},
			],
			title: '',
		},
		{
			items: [
				{
					icon: 'bell-on',
					label: i18n.sub('job-scheduler-x', `[ ${jobsInProgress} ]`),
					onClick: () =>
						onOpenModal({
							body: (
								<JobSchedulerModal
									testrayDispatchTriggers={
										testrayDispatchTriggers
									}
								/>
							),
							size: 'lg',
							title: i18n.translate('job-scheduler'),
						}),
				},
			],
		},
	];

	return MANAGE_DROPDOWN;
};

export default useSidebarActions;
