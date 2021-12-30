/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton from '@clayui/button';
import {ClayCheckbox, ClayToggle} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayList from '@clayui/list';
import getCN from 'classnames';
import React, {useEffect, useState} from 'react';

import {
	ACTIVE,
	ALL,
	ASCENDING,
	DESCENDING,
	INACTIVE,
} from '../../utils/constants';
import {BASELINE_CLAUSE_CONTRIBUTORS_CONFIGURATION} from '../../utils/data';
import {fetchData} from '../../utils/fetch';
import {
	getClauseContributorsConfig,
	getClauseContributorsState,
} from '../../utils/utils';
import ManagementToolbar from './ManagementToolbar';

/**
 * Converts a class name "com.liferay.account.internal.search.spi.model.query.contributor.AccountEntryKeywordQueryContributor"
 * to "Account Entry Keyword Query Contributor".
 * @param {String} className
 * @returns
 */
const getClassDisplayName = (className) => {
	if (!className) {
		return '';
	}

	return className
		.split('.')
		.slice(-1)[0]
		.split(/([A-Z][a-z]+)/g)
		.join(' ');
};

function ClauseContributorsSidebar({
	onToggle,
	visible,
	frameworkConfig,
	initialClauseContributorsList = [],
	onFrameworkConfigChange,
}) {
	const [category, setCategory] = useState(ALL);
	const [contributors, setContributors] = useState(
		initialClauseContributorsList
	);
	const [enabled, setEnabled] = useState(
		getClauseContributorsState(frameworkConfig)
	);
	const [keyword, setKeyword] = useState('');
	const [selected, setSelected] = useState([]);
	const [status, setStatus] = useState(ALL);
	const [sortDirection, setSortDirection] = useState(DESCENDING);

	const filterItems = [
		{
			items: [
				{
					active: status === ALL,
					label: ALL,
					onClick: () => setStatus(ALL),
				},
				{
					active: status === ACTIVE,
					label: ACTIVE,
					onClick: () => setStatus(ACTIVE),
				},
				{
					active: status === INACTIVE,
					label: INACTIVE,
					onClick: () => setStatus(INACTIVE),
				},
			],
			label: Liferay.Language.get('filter-by-status'),
			name: 'filter-by-status',
			type: 'group',
		},
		{
			items: [
				{
					active: category === ALL,
					label: ALL,
					onClick: () => setCategory(ALL),
				},
				...initialClauseContributorsList.map((contributor) => ({
					active: category === contributor.label,
					label: contributor.label,
					onClick: () => setCategory(contributor.label),
				})),
			],
			label: Liferay.Language.get('filter-by-category'),
			name: 'filter-by-category',
			type: 'group',
		},
	];

	useEffect(() => {
		const _isSearchVisible = (item, keyword) => {
			if (keyword) {
				return keyword
					.split(' ')
					.every((word) =>
						item.toLowerCase().includes(word.toLowerCase())
					);
			}
			else {
				return true;
			}
		};

		const _isStatusVisible = (item, status, enabled) => {
			if (status === ALL) {
				return true;
			}

			if (status === ACTIVE) {
				return enabled[item];
			}

			if (status === INACTIVE) {
				return !enabled[item];
			}
		};

		setContributors(
			initialClauseContributorsList
				.filter(({label}) => category === ALL || category === label)
				.map(({label, value}) => ({
					label,
					value: value
						.filter(
							(item) =>
								_isSearchVisible(item, keyword) &&
								_isStatusVisible(item, status, enabled)
						)
						.sort((a, b) =>
							sortDirection === DESCENDING
								? a.localeCompare(b)
								: b.localeCompare(a)
						),
				}))
		);
	}, [
		enabled,
		category,
		keyword,
		sortDirection,
		status,
		initialClauseContributorsList,
	]);

	const _handleSelectChange = (className) => () => {
		setSelected(
			selected.includes(className)
				? selected.filter(
						(preselectedClassName) =>
							preselectedClassName !== className
				  )
				: [...selected, className]
		);
	};

	const _handleToggle = (className) => () => {
		const newEnabled = {
			...enabled,
			[className]: !enabled[className],
		};

		onFrameworkConfigChange(getClauseContributorsConfig(newEnabled));

		setEnabled(newEnabled);
	};

	const _handleUpdateEnabled = (value) => {
		const newEnabled = {};

		selected.forEach((item) => {
			newEnabled[item] = value;
		});

		onFrameworkConfigChange(
			getClauseContributorsConfig({
				...enabled,
				...newEnabled,
			})
		);

		setEnabled({...enabled, ...newEnabled});
		setSelected([]);
	};

	return (
		<div
			className={getCN(
				'clause-contributors-sidebar',
				'sidebar',
				'sidebar-light',
				{
					open: visible,
				}
			)}
		>
			<div className="sidebar-header">
				<h4 className="component-title">
					<span className="text-truncate-inline">
						<span className="text-truncate">
							{Liferay.Language.get('clause-contributors')}
						</span>
					</span>
				</h4>

				<span>
					<ClayButton
						aria-label={Liferay.Language.get('close')}
						borderless
						displayType="secondary"
						monospaced
						onClick={() => onToggle(false)}
						small
					>
						<ClayIcon symbol="times" />
					</ClayButton>
				</span>
			</div>

			<ClayLayout.ContainerFluid className="clause-contributors-list">
				<ManagementToolbar
					allItems={contributors.reduce(
						(acc, curr) => [...curr.value, ...acc],
						[]
					)}
					category={category}
					filterItems={filterItems}
					keyword={keyword}
					onClearCategory={() => setCategory(ALL)}
					onClearStatus={() => setStatus(ALL)}
					onReverseSort={() =>
						setSortDirection(
							sortDirection === ASCENDING ? DESCENDING : ASCENDING
						)
					}
					onUpdateEnabled={_handleUpdateEnabled}
					selected={selected}
					setKeyword={setKeyword}
					setSelected={setSelected}
					sortDirection={sortDirection}
					status={status}
				/>

				<ClayList>
					{contributors.map((contributor) => (
						<React.Fragment key={contributor.label}>
							<ClayList.Header>
								{contributor.label}
							</ClayList.Header>

							{contributor.value.map((className) => (
								<ClayList.Item
									active={selected.includes(className)}
									flex
									key={className}
								>
									<ClayList.ItemField>
										<ClayCheckbox
											aria-label={Liferay.Language.get(
												'checkbox'
											)}
											checked={selected.includes(
												className
											)}
											onChange={_handleSelectChange(
												className
											)}
										/>
									</ClayList.ItemField>

									<ClayList.ItemField expand>
										<ClayList.ItemTitle>
											{getClassDisplayName(className)}
										</ClayList.ItemTitle>

										<ClayList.ItemText>
											{className}
										</ClayList.ItemText>
									</ClayList.ItemField>

									<ClayList.ItemField>
										<ClayToggle
											label={
												enabled[className]
													? Liferay.Language.get('on')
													: Liferay.Language.get(
															'off'
													  )
											}
											onToggle={_handleToggle(className)}
											toggled={
												enabled[className] || false
											}
										/>
									</ClayList.ItemField>
								</ClayList.Item>
							))}
						</React.Fragment>
					))}
				</ClayList>
			</ClayLayout.ContainerFluid>
		</div>
	);
}

export default function ({
	frameworkConfig,
	onFrameworkConfigChange,
	onToggle,
	visible,
}) {
	const [keywordQueryContributors, setKeywordQueryContributors] = useState(
		null
	);
	const [
		modelPrefilterContributors,
		setModelPrefilterContributors,
	] = useState(null);
	const [
		queryPrefilterContributors,
		setQueryPrefilterContributors,
	] = useState(null);

	useEffect(() => {
		[
			{
				label: 'KeywordQueryContributor',
				setProperty: setKeywordQueryContributors,
				url:
					'/o/search-experiences-rest/v1.0/keyword-query-contributors',
			},
			{
				label: 'ModelPrefilterContributor',
				setProperty: setModelPrefilterContributors,
				url:
					'/o/search-experiences-rest/v1.0/model-prefilter-contributors',
			},
			{
				label: 'QueryPrefilterContributor',
				setProperty: setQueryPrefilterContributors,
				url:
					'/o/search-experiences-rest/v1.0/query-prefilter-contributors',
			},
		].forEach(({label, setProperty, url}) =>
			fetchData(
				url,
				{method: 'GET'},
				(responseContent) =>
					setProperty({
						label,
						value: responseContent.items
							.map(({className}) => className)
							.filter((item) => item)
							.sort(),
					}),
				() => setProperty({label, value: []})
			)
		);
	}, []); //eslint-disable-line

	if (
		!keywordQueryContributors ||
		!modelPrefilterContributors ||
		!queryPrefilterContributors
	) {
		return null;
	}

	return (
		<ClauseContributorsSidebar
			frameworkConfig={frameworkConfig}
			initialClauseContributorsList={[
				keywordQueryContributors,
				modelPrefilterContributors,
				queryPrefilterContributors,
			]}
			onFrameworkConfigChange={onFrameworkConfigChange}
			onToggle={onToggle}
			visible={visible}
		/>
	);
}
