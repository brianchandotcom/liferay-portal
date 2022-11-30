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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import React, {useState} from 'react';
import {useNavigate} from 'react-router-dom';

import Form from '../../../../components/Form';
import Tooltip from '../../../../components/Tooltip';
import {Dropdown} from '../../../../context/HeaderContext';
import useDebounce from '../../../../hooks/useDebounce';
import {useFetch} from '../../../../hooks/useFetch';
import i18n from '../../../../i18n';
import {APIResponse, TestrayBuild} from '../../../../services/rest';
import {testrayBuildImpl} from '../../../../services/rest/TestrayBuild';
import {SearchBuilder} from '../../../../util/search';

type BuildAddButtonProps = {
	routineId: string;
};

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
		.eq('active', true)
		.and();

	const totalFilter = baseFilter.build();

	const searchFilter = baseFilter.contains('name', debouncedValue).build();

	const {data: buildResponseWithSearch} = useFetch<APIResponse<TestrayBuild>>(
		`${testrayBuildImpl.resource}&filter=${searchFilter}`
	);

	const {data: buildResponse} = useFetch<APIResponse<TestrayBuild>>(
		`${testrayBuildImpl.resource}&filter=${totalFilter}&fields=id`
	);

	const buildTemplates = buildResponseWithSearch?.items || [];
	const templatesCount = buildResponse?.totalCount || 0;

	const dropDownItems: Dropdown = [
		{
			items: [
				{
					label: i18n.translate('new-build'),
					path: './create',
				},
				{
					label: i18n.sub('new-x', 'template'),
					path: './create/template/true',
				},
			],

			title: i18n.translate('create'),
		},
	];

	return (
		<ClayDropDown
			active={active}
			alignmentPosition={Align.BottomLeft}
			onActiveChange={setActive}
			trigger={
				<div>
					<Tooltip position="down" title={i18n.translate('manage')}>
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
										<span className="ml-1 testray-sidebar-text">
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
