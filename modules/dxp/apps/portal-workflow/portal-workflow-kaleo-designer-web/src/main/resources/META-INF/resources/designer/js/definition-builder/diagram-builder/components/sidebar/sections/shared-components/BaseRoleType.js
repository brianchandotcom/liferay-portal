/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAutocomplete from '@clayui/autocomplete';
import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayForm, {ClayCheckbox} from '@clayui/form';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import React, {useContext, useEffect, useState} from 'react';

import {DefinitionBuilderContext} from '../../../../../DefinitionBuilderContext';
import {
	retrieveAccountRoles,
	retrieveRoles,
} from '../../../../../util/fetchUtil';
import {stringToBoolean, titleCase} from '../../../../../util/utils';

const BaseRoleType = ({
	autoCreate = false,
	buttonName,
	errors,
	identifier,
	index,
	inputLabel,
	roleName,
	roleType = '',
	sectionsLength,
	setErrors,
	setSections,
	notificationIndex,
	updateSelectedItem = () => {},
}) => {
	const {accountEntryId} = useContext(DefinitionBuilderContext);
	const [accountRoles, setAccountRoles] = useState([]);
	const [filterRoleName, setFilterRoleName] = useState(true);
	const [filterRoleType, setFilterRoleType] = useState(true);
	const [loading, setLoading] = useState(false);
	const [roleNameDropdownActive, setRoleNameDropdownActive] = useState(false);
	const [roleTypeDropdownActive, setRoleTypeDropdownActive] = useState(false);
	const [roles, setRoles] = useState([]);
	const [selectedRoleName, setSelectedRoleName] = useState(roleName);
	const [selectedRoleType, setSelectedRoleType] = useState(
		titleCase(roleType)
	);
	const [checked, setChecked] = useState(stringToBoolean(autoCreate));

	const checkRoleTypeErrors = (errors, selectedRoleName) => {
		const temp = errors?.roleName ? [...errors.roleName] : [];

		if (!temp[notificationIndex]) {
			temp[notificationIndex] = [];
		}
		if (!temp[notificationIndex][index]) {
			temp[notificationIndex][index] = [];
		}
		temp[notificationIndex][index] = selectedRoleName === '';

		return {...errors, roleName: temp};
	};

	useEffect(() => {
		if (selectedRoleName !== null) {
			setErrors(checkRoleTypeErrors(errors, selectedRoleName));
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedRoleName]);

	useEffect(() => {
		setChecked(stringToBoolean(autoCreate));
		setSelectedRoleName(roleName);
		setSelectedRoleType(titleCase(roleType));

		roleNameItemUpdate({
			autoCreate,
			roleName,
			roleType: roleType.toLowerCase(),
		});

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [autoCreate, roleName, roleType]);

	const deleteSection = () => {
		setSections((prevSections) => {
			const newSections = prevSections.filter(
				(prevSection) => prevSection.identifier !== identifier
			);

			updateSelectedItem(newSections);

			return newSections;
		});
	};

	const getRolesInfo = () => {
		const rolesInfo = {};

		roles.items.forEach((item) => {
			const roleType = titleCase(item.roleType);

			if (!rolesInfo[roleType]) {
				rolesInfo[roleType] = [];
			}

			rolesInfo[roleType].push({
				roleName: item.name,
				roleType,
			});
		});

		rolesInfo['Account'] = accountRoles;

		return rolesInfo;
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

	const roleNameItemUpdate = (item) => {
		if (item.roleName) {
			setSelectedRoleName(item.roleName);
			setRoleNameDropdownActive(false);

			setSections((prev) => {
				const newSections = [...prev];

				newSections[index] = {
					...newSections[index],
					...item,
				};

				updateSelectedItem(newSections);

				return newSections;
			});
		}
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

	useEffect(() => {
		const makeFetch = async () => {
			setLoading(true);

			const accountRolesResponse =
				await retrieveAccountRoles(accountEntryId);

			const accountRolesResponseJSON = await accountRolesResponse.json();

			const accountRoleItems = accountRolesResponseJSON.items.map(
				({name}) => {
					return {
						roleName: name,
						roleType: 'Account',
					};
				}
			);

			setAccountRoles(accountRoleItems);

			const rolesResponse = await retrieveRoles();

			const rolesResponseJSON = await rolesResponse.json();

			setRoles(rolesResponseJSON);

			setLoading(false);
		};

		makeFetch();
	}, [accountEntryId]);

	return (
		<>
			<ClayForm.Group>
				<ClayAutocomplete>
					<label htmlFor="role-type">{inputLabel}</label>

					{loading ? (
						<ClayLoadingIndicator
							displayType="secondary"
							size="sm"
						/>
					) : (
						<>
							<ClayAutocomplete.Input
								autoComplete="off"
								id="role-type"
								onChange={(event) => roleTypeInputChange(event)}
								onFocus={() => roleTypeInputFocus()}
								value={selectedRoleType}
							/>

							<ClayAutocomplete.DropDown
								active={roleTypeDropdownActive}
								closeOnClickOutside
								onSetActive={setRoleTypeDropdownActive}
							>
								<ClayDropDown.ItemList>
									{roles && roles.error && (
										<ClayDropDown.Item className="disabled">
											{Liferay.Language.get(
												'no-results-found'
											)}
										</ClayDropDown.Item>
									)}

									{!!roles.items &&
										filteredRoleTypes().map(
											(item, index) => (
												<ClayAutocomplete.Item
													key={index}
													onClickCapture={() =>
														roleTypeItemClick(item)
													}
													value={item}
												/>
											)
										)}
								</ClayDropDown.ItemList>
							</ClayAutocomplete.DropDown>
						</>
					)}
				</ClayAutocomplete>
			</ClayForm.Group>
			<ClayForm.Group
				className={
					errors?.roleName?.[notificationIndex]?.[index]
						? 'has-error'
						: ''
				}
			>
				<ClayAutocomplete>
					<label htmlFor="role-name">
						{Liferay.Language.get('role-name')}

						<span className="ml-1 mr-1 text-warning">*</span>
					</label>

					{loading ? (
						<ClayLoadingIndicator
							displayType="secondary"
							size="sm"
						/>
					) : (
						<>
							<ClayAutocomplete.Input
								autoComplete="off"
								disabled={!selectedRoleType}
								id="role-name"
								onBlur={(event) => {
									const roleName = titleCase(
										event.target.value
									);

									if (selectedRoleName !== '') {
										roleNameItemUpdate({
											autoCreate: checked,
											roleName,
											roleType:
												selectedRoleType.toLowerCase(),
										});
									}
									setErrors(
										checkRoleTypeErrors(
											errors,
											selectedRoleName
										)
									);
								}}
								onChange={(event) => roleNameInputChange(event)}
								onFocus={() => roleNameInputFocus()}
								value={selectedRoleName}
							/>

							<ClayAutocomplete.DropDown
								active={roleNameDropdownActive}
								closeOnClickOutside
								onSetActive={setRoleNameDropdownActive}
							>
								<ClayDropDown.ItemList>
									{roles && roles.error && (
										<ClayDropDown.Item className="disabled">
											{Liferay.Language.get(
												'no-results-found'
											)}
										</ClayDropDown.Item>
									)}

									{!!roles.items &&
										filteredRoleNames().map(
											(item, index) => (
												<ClayAutocomplete.Item
													key={index}
													onMouseDown={() =>
														roleNameItemUpdate({
															autoCreate: checked,
															roleName:
																item.roleName,
															roleType:
																item.roleType.toLowerCase(),
														})
													}
													value={item.roleName}
												/>
											)
										)}
								</ClayDropDown.ItemList>
							</ClayAutocomplete.DropDown>
						</>
					)}
				</ClayAutocomplete>

				<ClayForm.FeedbackItem>
					{errors?.roleName?.[notificationIndex]?.[index] && (
						<>
							<ClayForm.FeedbackIndicator symbol="exclamation-full" />

							{Liferay.Language.get('this-field-is-required')}
						</>
					)}
				</ClayForm.FeedbackItem>
			</ClayForm.Group>
			<ClayForm.Group>
				<div className="spaced-items">
					<div className="auto-create">
						<ClayCheckbox
							checked={checked}
							className="mt-2"
							onChange={() => {
								setChecked((value) => {
									setSections((prev) => {
										const newSections = [...prev];

										newSections[index] = {
											...newSections[index],
											autoCreate: !value,
											roleName: selectedRoleName,
											roleType:
												selectedRoleType.toLowerCase(),
										};

										updateSelectedItem(newSections);

										return newSections;
									});

									return !value;
								});
							}}
						/>

						<span className="ml-2">
							{Liferay.Language.get('auto-create')}
						</span>
					</div>

					{sectionsLength > 1 && (
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
					{buttonName}
				</ClayButton>
			</div>
		</>
	);
};

export default BaseRoleType;
