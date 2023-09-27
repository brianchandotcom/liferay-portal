/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import React, {useState} from 'react';
import {useNavigate} from 'react-router-dom';
import {Dropdown} from '~/atoms';
import {BuildStatuses} from '~/util/statuses';

import Form from '../../../../components/Form';
import Tooltip from '../../../../components/Tooltip';
import SearchBuilder from '../../../../core/SearchBuilder';
import useDebounce from '../../../../hooks/useDebounce';
import {useFetch} from '../../../../hooks/useFetch';
import i18n from '../../../../i18n';
import {APIResponse, TestrayBuild} from '../../../../services/rest';
import {testrayBuildImpl} from '../../../../services/rest/TestrayBuild';

type BuildAddButtonProps = {
	routineId: string;
};

const dropDownItems: Dropdown = [
	{
		items: [
			{
				label: i18n.translate('new-build'),
				path: './create',
			},
			{
				label: i18n.sub('new-x', 'template'),
				path: './create?template=true',
			},
		],
		title: i18n.translate('create'),
	},
];

const BuildAddButton: React.FC<BuildAddButtonProps> = ({routineId}) => {
	const navigate = useNavigate();

	const [active, setActive] = useState(false);
	const [searchTemplate, setSearchTemplate] = useState('');

	const debouncedValue = useDebounce(searchTemplate, 1000);
	const searchBuilder = new SearchBuilder();

	const baseFilter = searchBuilder
		.eq('routineId', routineId)
		.and()
		.eq('template', true)
		.and()
		.eq('dueStatus', BuildStatuses.ACTIVATED)
		.and();

	const totalFilter = baseFilter.build();

	const searchFilter = baseFilter.contains('name', debouncedValue).build();

	const {data: buildResponseWithSearch} = useFetch<APIResponse<TestrayBuild>>(
		testrayBuildImpl.resource,
		{
			params: {
				filter: searchFilter,
			},
		}
	);

	const {data: buildResponse} = useFetch<APIResponse<TestrayBuild>>(
		testrayBuildImpl.resource,
		{
			params: {
				fields: 'id',
				filter: totalFilter,
			},
		}
	);

	const buildTemplates = buildResponseWithSearch?.items || [];
	const templatesCount = buildResponse?.totalCount || 0;

	return (
		<ClayDropDown
			active={active}
			alignmentPosition={Align.BottomLeft}
			onActiveChange={setActive}
			trigger={
				<div>
					<Tooltip position="bottom" title={i18n.translate('manage')}>
						<div className="testray-sidebar-item">
							<ClayButtonWithIcon
								aria-label={i18n.translate('manage')}
								className="nav-btn nav-btn-monospaced"
								symbol="plus"
							/>
						</div>
					</Tooltip>
				</div>
			}
		>
			<ClayDropDown.ItemList>
				{dropDownItems.map((section, index) => (
					<ClayDropDown.Group header={section.title} key={index}>
						{section.items.map(({label, path}, itemIndex) => (
							<React.Fragment key={itemIndex}>
								<ClayDropDown.Item
									onClick={() => {
										setActive(false);

										navigate(path as string);
									}}
								>
									<div className="align-items-center d-flex testray-sidebar-item text-dark">
										<span className="ml-1 tr-sidebar__text">
											{label}
										</span>
									</div>
								</ClayDropDown.Item>
							</React.Fragment>
						))}

						{!!templatesCount && (
							<>
								<ClayDropDown.Divider />

								<ClayDropDown.Group
									header={i18n.translate('templates')}
								>
									<ClayDropDown.Item>
										<Form.Input
											name="search-filter"
											onChange={(event) => {
												setSearchTemplate(
													event.target.value
												);
											}}
											placeholder={i18n.sub(
												'search-x',
												'templates'
											)}
											value={searchTemplate}
										/>
									</ClayDropDown.Item>

									{!!buildTemplates.length && (
										<div className="dropdown-scrollbar">
											<ClayDropDown.ItemList>
												{buildTemplates.map(
													(build, index) => (
														<ClayDropDown.Item
															key={index}
															onClick={() =>
																navigate(
																	`./create/${build.id}`
																)
															}
														>
															<li
																style={{
																	listStyle:
																		'none',
																}}
															>
																{build.name}
															</li>
														</ClayDropDown.Item>
													)
												)}
											</ClayDropDown.ItemList>
										</div>
									)}
								</ClayDropDown.Group>
							</>
						)}
					</ClayDropDown.Group>
				))}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
};

export default BuildAddButton;
