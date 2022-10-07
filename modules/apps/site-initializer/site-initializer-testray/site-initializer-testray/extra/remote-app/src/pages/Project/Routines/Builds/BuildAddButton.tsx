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
import React, {useCallback, useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';

import Form from '../../../../components/Form';
import Tooltip from '../../../../components/Tooltip';
import {Dropdown} from '../../../../context/HeaderContext';
import useDebounce from '../../../../hooks/useDebounce';
import {useFetch} from '../../../../hooks/useFetch';
import i18n from '../../../../i18n';
import {APIResponse, TestrayBuild} from '../../../../services/rest';
import {testrayBuildImpl} from '../../../../services/rest/TestrayBuild';
import {searchUtil} from '../../../../util/search';

type BuildAddButtonProps = {
	routineId: string;
};

const BuildAddButton: React.FC<BuildAddButtonProps> = ({routineId}) => {
	const navigate = useNavigate();
	const [value, setValue] = useState('');
	const [state, setState] = useState([]);
	const [active, setActive] = useState(false);

	const debouncedValue = useDebounce(value, 100);

	const {data} = useFetch<APIResponse<TestrayBuild>>(
		`${testrayBuildImpl.resource}&filter=${searchUtil.eq(
			'routineId',
			routineId
		)} and ${searchUtil.eq('template', true)} and ${searchUtil.eq(
			'active',
			true
		)} and ${searchUtil.contains('name', debouncedValue)} `
	);

	const getBuildTemplate = useCallback(() => {
		setState(data?.items as any);
	}, []);

	useEffect(() => {
		getBuildTemplate();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const items: Dropdown = [
		{
			items: [
				{
					label: 'New Build',
					path: './create',
				},
				{
					label: 'New Template',
					path: `./create/template/${true}`,
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
								className="nav-btn nav-btn-monospaced"
								symbol="plus"
							/>
						</div>
					</Tooltip>
				</div>
			}
		>
			<ClayDropDown.ItemList>
				{items.map((section, index) => (
					<div key={index}>
						<ClayDropDown.Group header={section.title}>
							{section.items.map(
								({label, onClick, path}, itemIndex) => (
									<React.Fragment key={itemIndex}>
										<ClayDropDown.Item
											onClick={() => {
												if (onClick) {
													setActive(false);

													return onClick();
												}

												if (!path) {
													return;
												}

												const isHttpUrl = path.startsWith(
													'http'
												);

												if (isHttpUrl) {
													window.location.href = path;

													return;
												}

												setActive(false);

												navigate(path);
											}}
										>
											<div className="align-items-center d-flex testray-sidebar-item text-dark">
												<span className="ml-1 testray-sidebar-text">
													{label}
												</span>
											</div>
										</ClayDropDown.Item>
									</React.Fragment>
								)
							)}

							{state?.length > 0 && (
								<>
									<ClayDropDown.Divider />

									<ClayDropDown.Group
										header={i18n.translate('templates')}
									>
										<ClayDropDown.Item>
											<Form.Input
												name="search-filter"
												onChange={(event) => {
													setValue(
														event.target.value
													);
												}}
												placeholder="Search Templates"
												value={value}
											/>
										</ClayDropDown.Item>

										{data?.items.length !== 0 && (
											<div className="dropdown-scrollbar">
												<ClayDropDown.ItemList>
													{data?.items.map(
														(build, index) => (
															<ClayDropDown.Item
																key={index}
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
					</div>
				))}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
};

export default BuildAddButton;
