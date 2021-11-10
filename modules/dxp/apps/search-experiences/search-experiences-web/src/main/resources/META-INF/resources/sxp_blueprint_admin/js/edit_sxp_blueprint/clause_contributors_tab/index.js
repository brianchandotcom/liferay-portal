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

import {ClayCheckbox, ClayToggle} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import ClaySticker from '@clayui/sticker';
import ClayTable from '@clayui/table';
import {ClayTooltipProvider} from '@clayui/tooltip';
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

function ClauseContributorsTab({
	applyIndexerClauses,
	frameworkConfig,
	initialClauseContributorsList = [],
	onFrameworkConfigChange,
	onApplyIndexerClausesChange,
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
		{
			items: [
				{
					active: true,
					label: Liferay.Language.get('name'),
				},
			],
			label: Liferay.Language.get('order-by'),
			name: 'order-by',
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

	const _handleApplyBaseline = () => {
		const baselineEnabledState = getClauseContributorsState(
			BASELINE_CLAUSE_CONTRIBUTORS_CONFIGURATION
		);

		onFrameworkConfigChange(
			getClauseContributorsConfig(baselineEnabledState)
		);

		onApplyIndexerClausesChange(true);

		setEnabled(baselineEnabledState);
	};

	const _handleApplyIndexerClausesChange = () => {
		onApplyIndexerClausesChange(!applyIndexerClauses);
	};

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
		<div className="clause-contributors-tab">
			<div className="container-fluid container-fluid-max-xl">
				<div className="container-view">
					<div className="clause-content-shift">
						<ClayList>
							<ClayList.Item flex>
								<ClayList.ItemField expand>
									<ClayList.ItemTitle>
										{Liferay.Language.get(
											'liferay-indexer-clauses'
										)}

										<ClayTooltipProvider>
											<ClaySticker
												displayType="unstyled"
												size="sm"
												title={Liferay.Language.get(
													'liferay-indexer-clauses-help'
												)}
											>
												<ClayIcon
													data-tooltip-align="top"
													symbol="info-circle"
												/>
											</ClaySticker>
										</ClayTooltipProvider>
									</ClayList.ItemTitle>
								</ClayList.ItemField>

								<ClayList.ItemField className="toggle-item">
									<ClayToggle
										label={
											applyIndexerClauses
												? Liferay.Language.get('on')
												: Liferay.Language.get('off')
										}
										onToggle={
											_handleApplyIndexerClausesChange
										}
										toggled={applyIndexerClauses || false}
									/>
								</ClayList.ItemField>
							</ClayList.Item>
						</ClayList>

						<ManagementToolbar
							allItems={contributors.reduce(
								(acc, curr) => [...curr.value, ...acc],
								[]
							)}
							category={category}
							filterItems={filterItems}
							keyword={keyword}
							onApplyBaseline={_handleApplyBaseline}
							onClearCategory={() => setCategory(ALL)}
							onClearStatus={() => setStatus(ALL)}
							onReverseSort={() =>
								setSortDirection(
									sortDirection === ASCENDING
										? DESCENDING
										: ASCENDING
								)
							}
							onUpdateEnabled={_handleUpdateEnabled}
							selected={selected}
							setKeyword={setKeyword}
							setSelected={setSelected}
							sortDirection={sortDirection}
							status={status}
						/>

						<ClayTable hover={false}>
							<ClayTable.Head>
								<ClayTable.Row>
									<ClayTable.Cell headingCell />

									<ClayTable.Cell
										className="table-cell-expand-small"
										expanded
										headingCell
									>
										{Liferay.Language.get('title')}
									</ClayTable.Cell>

									<ClayTable.Cell expanded headingCell>
										{Liferay.Language.get('class-name')}
									</ClayTable.Cell>

									<ClayTable.Cell
										className="table-cell-expand-smallest"
										headingCell
									>
										{Liferay.Language.get('enabled')}
									</ClayTable.Cell>
								</ClayTable.Row>
							</ClayTable.Head>

							<ClayTable.Body>
								{contributors.map((contributor) => (
									<React.Fragment key={contributor.label}>
										<ClayTable.Row
											divider
											key={contributor.label}
										>
											<ClayTable.Cell colSpan="9">
												{contributor.label}
											</ClayTable.Cell>
										</ClayTable.Row>

										{contributor.value.map((className) => (
											<ClayTable.Row
												active={selected.includes(
													className
												)}
												key={className}
											>
												<ClayTable.Cell>
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
												</ClayTable.Cell>

												<ClayTable.Cell
													expanded
													headingTitle
												>
													{getClassDisplayName(
														className
													)}
												</ClayTable.Cell>

												<ClayTable.Cell expanded>
													{className}
												</ClayTable.Cell>

												<ClayTable.Cell className="table-cell-expand-smallest">
													<ClayToggle
														label={
															enabled[className]
																? Liferay.Language.get(
																		'on'
																  )
																: Liferay.Language.get(
																		'off'
																  )
														}
														onToggle={_handleToggle(
															className
														)}
														toggled={
															enabled[
																className
															] || false
														}
													/>
												</ClayTable.Cell>
											</ClayTable.Row>
										))}
									</React.Fragment>
								))}
							</ClayTable.Body>
						</ClayTable>
					</div>
				</div>
			</div>
		</div>
	);
}

export default function ({
	applyIndexerClauses,
	frameworkConfig,
	onApplyIndexerClausesChange,
	onFrameworkConfigChange,
}) {
	const [keywordQuery, setKeywordQuery] = useState(null);
	const [modelPrefilter, setModelPrefilter] = useState(null);
	const [queryPrefilter, setQueryPrefilter] = useState(null);

	useEffect(() => {
		[
			{
				label: 'KeywordQueryContributor',
				setProperty: setKeywordQuery,
				url:
					'/o/search-experiences-rest/v1.0/keyword-query-contributors',
			},
			{
				label: 'ModelPrefilterContributor',
				setProperty: setModelPrefilter,
				url:
					'/o/search-experiences-rest/v1.0/model-prefilter-contributors',
			},
			{
				label: 'QueryPrefilterContributor',
				setProperty: setQueryPrefilter,
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

	if (!keywordQuery || !modelPrefilter || !queryPrefilter) {
		return null;
	}

	return (
		<ClauseContributorsTab
			applyIndexerClauses={applyIndexerClauses}
			frameworkConfig={frameworkConfig}
			initialClauseContributorsList={[
				keywordQuery,
				modelPrefilter,
				queryPrefilter,
			]}
			onApplyIndexerClausesChange={onApplyIndexerClausesChange}
			onFrameworkConfigChange={onFrameworkConfigChange}
		/>
	);
}
