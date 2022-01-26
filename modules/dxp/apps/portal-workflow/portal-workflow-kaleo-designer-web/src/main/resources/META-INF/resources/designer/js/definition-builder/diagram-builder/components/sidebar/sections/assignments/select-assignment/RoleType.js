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

import ClayAutocomplete from '@clayui/autocomplete';
import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {useResource} from '@clayui/data-provider';
import ClayDropDown from '@clayui/drop-down';
import ClayForm, {ClayCheckbox} from '@clayui/form';
import React, {useContext, useEffect, useState} from 'react';

import {headers, retrieveAccountRoles} from '../../../../../../util/fetchUtil';
import {titleCase} from '../../../../../../util/utils';
import {DiagramBuilderContext} from '../../../../../DiagramBuilderContext';
import SidebarPanel from '../../../SidebarPanel';

const RoleType = ({displayDelete, identifier, index, setSections}) => {
	const {setSelectedItem} = useContext(DiagramBuilderContext);
	const [checked, setChecked] = useState(true);
	const [accountRoles, setAccountRoles] = useState([]);
	const [filterRoleName, setFilterRoleName] = useState(true);
	const [filterRoleType, setFilterRoleType] = useState(true);
	const [networkStatus, setNetworkStatus] = useState(4);
	const [roleTypeDropdownActive, setRoleTypeDropdownActive] = useState(false);
	const [roleNameDropdownActive, setRoleNameDropdownActive] = useState(false);
	const [selectedRoleName, setSelectedRoleName] = useState('');
	const [selectedRoleType, setSelectedRoleType] = useState('');

	const {resource} = useResource({
		fetchOptions: {
			headers: {
				...headers,
				'accept': `application/json`,
				'x-csrf-token': Liferay.authToken,
			},
		},
		fetchPolicy: 'cache-first',
		link: `${window.location.origin}/o/headless-admin-user/v1.0/roles`,
		onNetworkStatusChange: setNetworkStatus,
	});

	const userId = Liferay.ThemeDisplay.getUserId();

	useEffect(() => {
		retrieveAccountRoles(userId)
			.then((response) => response.json())
			.then(({items}) => {
				const roles = items.map((item) => {
					return {roleName: item.displayName, roleType: 'Account'};
				});

				setAccountRoles(roles);
			});

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const serializer = (values) => {
		setSelectedItem((previousItem) => ({
			...previousItem,
			data: {
				...previousItem.data,
				assignments: {
					assignmentType: ['roleType'],
					autoCreate: values.map(({autoCreate}) => autoCreate),
					roleName: values.map(({roleName}) => roleName),
					roleType: values.map(({roleType}) => roleType),
				},
			},
		}));
	};

	const deleteSection = () => {
		setSections((prevSections) => {
			const newSections = prevSections.filter(
				(prevSection) => prevSection.identifier !== identifier
			);

			serializer(newSections);

			return newSections;
		});
	};

	const getRolesInfo = () => {
		const roles = {};

		resource.items.forEach((item) => {
			let roleType = titleCase(item.roleType);

			if (roleType === 'Depot') {
				roleType = 'Asset Library';
			}

			if (!roles[roleType]) {
				roles[roleType] = [];
			}

			roles[roleType].push({
				roleName: item.name,
				roleType,
			});
		});

		roles['Account'] = accountRoles;

		return roles;
	};

	const filteredRoleNames = () => {
		if (!selectedRoleType) {
			return [];
		}

		return getRolesInfo()[selectedRoleType]
			? getRolesInfo()[selectedRoleType].filter((item) =>
					!filterRoleName
						? item
						: item?.roleName
								.toLowerCase()
								.match(selectedRoleName?.toLowerCase())
			  )
			: [];
	};

	const filteredRoleTypes = () =>
		Object.keys(getRolesInfo()).filter((item) =>
			!filterRoleType
				? item
				: item?.toLowerCase().match(selectedRoleType?.toLowerCase())
		);
	const roleNameInputFocus = () => {
		setFilterRoleName(selectedRoleName === '');
		setRoleNameDropdownActive(true);
	};

	const roleNameInputChange = (event) => {
		event.persist();

		setFilterRoleName(true);
		setSelectedRoleName(event.target.value);
	};

	const roleNameItemClick = (item) => {
		setSelectedRoleName(item.roleName);
		setRoleNameDropdownActive(false);

		setSections((prev) => {
			prev[index] = {
				...prev[index],
				...item,
			};

			serializer(prev);

			return prev;
		});
	};

	const roleTypeInputFocus = () => {
		setFilterRoleType(selectedRoleType === '');
		setRoleTypeDropdownActive(true);
	};

	const roleTypeInputChange = (event) => {
		event.persist();

		setFilterRoleType(true);
		setSelectedRoleType(event.target.value);
		setSelectedRoleName('');
	};

	const roleTypeItemClick = (item) => {
		setSelectedRoleType(item);
		setRoleTypeDropdownActive(false);
		setSelectedRoleName('');
	};

	const initialLoading = networkStatus === 1;
	const loading = networkStatus < 4;
	const error = networkStatus === 5;

	return (
		<SidebarPanel panelTitle={Liferay.Language.get('select-role')}>
			<ClayForm.Group>
				<ClayAutocomplete>
					<label htmlFor="role-type">
						{Liferay.Language.get('role')}
					</label>

					<ClayAutocomplete.Input
						autoComplete="off"
						id="role-type"
						onChange={(event) => roleTypeInputChange(event)}
						onFocus={() => roleTypeInputFocus()}
						value={selectedRoleType}
					/>

					<ClayAutocomplete.DropDown
						active={
							(!!resource && roleTypeDropdownActive) ||
							initialLoading
						}
						closeOnClickOutside
						onSetActive={setRoleTypeDropdownActive}
					>
						<ClayDropDown.ItemList>
							{(error || (resource && resource.error)) && (
								<ClayDropDown.Item className="disabled">
									{Liferay.Language.get('no-results-found')}
								</ClayDropDown.Item>
							)}

							{!error &&
								resource?.items &&
								filteredRoleTypes().map((item, index) => (
									<ClayAutocomplete.Item
										key={index}
										onClickCapture={() =>
											roleTypeItemClick(item)
										}
										value={item}
									/>
								))}
						</ClayDropDown.ItemList>
					</ClayAutocomplete.DropDown>

					{loading && <ClayAutocomplete.LoadingIndicator />}
				</ClayAutocomplete>
			</ClayForm.Group>

			<ClayForm.Group>
				<ClayAutocomplete>
					<label htmlFor="role-name">
						{Liferay.Language.get('role-name')}

						<span className="ml-1 mr-1 text-warning">*</span>
					</label>

					<ClayAutocomplete.Input
						autoComplete="off"
						disabled={!selectedRoleType}
						id="role-name"
						onChange={(event) => roleNameInputChange(event)}
						onFocus={() => roleNameInputFocus()}
						value={selectedRoleName}
					/>

					<ClayAutocomplete.DropDown
						active={
							(!!resource && roleNameDropdownActive) ||
							initialLoading
						}
						closeOnClickOutside
						onSetActive={setRoleNameDropdownActive}
					>
						<ClayDropDown.ItemList>
							{(error || (resource && resource.error)) && (
								<ClayDropDown.Item className="disabled">
									{Liferay.Language.get('no-results-found')}
								</ClayDropDown.Item>
							)}

							{!error &&
								resource?.items &&
								filteredRoleNames().map((item, index) => (
									<ClayAutocomplete.Item
										key={index}
										onClickCapture={() =>
											roleNameItemClick({
												autoCreate: checked,
												roleName: item.roleName,
												roleType: item.roleType,
											})
										}
										value={item.roleName}
									/>
								))}
						</ClayDropDown.ItemList>
					</ClayAutocomplete.DropDown>

					{loading && <ClayAutocomplete.LoadingIndicator />}
				</ClayAutocomplete>
			</ClayForm.Group>

			<ClayForm.Group>
				<div className="spaced-items">
					<div className="auto-create">
						<ClayCheckbox
							className="mt-2"
							defaultChecked={checked}
							onClick={() => setChecked(!checked)}
						/>

						<span className="ml-2">
							{Liferay.Language.get('auto-create')}
						</span>
					</div>

					{displayDelete && (
						<ClayButtonWithIcon
							className="delete-button"
							displayType="unstyled"
							onClick={deleteSection}
							symbol="trash"
						/>
					)}
				</div>
			</ClayForm.Group>

			<div className="section-buttons-area">
				<ClayButton
					className="mr-3"
					displayType="secondary"
					onClick={() =>
						setSections((prev) => {
							return [
								...prev,
								{identifier: `${Date.now()}-${prev.length}`},
							];
						})
					}
				>
					{Liferay.Language.get('new-section')}
				</ClayButton>
			</div>
		</SidebarPanel>
	);
};

export default RoleType;
