/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAutocomplete from '@clayui/autocomplete';
import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayForm, {ClayCheckbox} from '@clayui/form';
import React, {useEffect, useState} from 'react';

import {titleCase} from '../../../../../util/utils';

const BaseRoleType = ({
	accountRoles,
	autoCreate = false,
	buttonName,
	errors,
	identifier,
	index,
	inputLabel,
	networkStatus,
	resource,
	roleKey,
	roleName,
	roleType = '',
	sectionsLength,
	setErrors,
	setSections,
	notificationIndex,
	updateSelectedItem = () => {},
}) => {
	const [filterRoleName, setFilterRoleName] = useState(true);
	const [filterRoleType, setFilterRoleType] = useState(true);
	const [roleNameDropdownActive, setRoleNameDropdownActive] = useState(false);
	const [roleTypeDropdownActive, setRoleTypeDropdownActive] = useState(false);
	const [selectedRoleName, setSelectedRoleName] = useState(
		roleName || roleKey
	);
	const [selectedRoleType, setSelectedRoleType] = useState(
		titleCase(roleType)
	);
	if (autoCreate === 'false') {
		autoCreate = false;
	}

	const [checked, setChecked] = useState(autoCreate);

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
		const roles = {};

		resource.items.forEach((item) => {
			const roleType = titleCase(item.roleType);

			if (!roles[roleType]) {
				roles[roleType] = [];
			}

			roles[roleType].push({
				roleKey: item.externalReferenceCode,
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

	const roleNameItemUpdate = (item) => {
		setSelectedRoleName(item.roleName);
		setRoleNameDropdownActive(false);

		setSections((prev) => {
			prev[index] = {
				...prev[index],
				...item,
			};

			updateSelectedItem(prev);

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
		<>
			<ClayForm.Group>
				<ClayAutocomplete>
					<label htmlFor="role-type">{inputLabel}</label>

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

					<ClayAutocomplete.Input
						autoComplete="off"
						disabled={!selectedRoleType}
						id="role-name"
						onBlur={(event) => {
							const roleName = titleCase(event.target.value);

							if (selectedRoleName !== '') {
								roleNameItemUpdate({
									autoCreate: checked,
									roleKey: filteredRoleNames().find(
										(item) => item.roleName === roleName
									).roleKey,
									roleName,
									roleType: selectedRoleType.toLowerCase(),
								});
							}
							setErrors(
								checkRoleTypeErrors(errors, selectedRoleName)
							);
						}}
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
										onMouseDown={() =>
											roleNameItemUpdate({
												autoCreate: checked,
												roleKey: item.roleKey,
												roleName: item.roleName,
												roleType: item.roleType.toLowerCase(),
											})
										}
										value={item.roleName}
									/>
								))}
						</ClayDropDown.ItemList>
					</ClayAutocomplete.DropDown>

					{loading && <ClayAutocomplete.LoadingIndicator />}
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
										prev[index] = {
											...prev[index],
											autoCreate: !value,
											roleName: selectedRoleName,
											roleType: selectedRoleType,
										};

										updateSelectedItem(prev);

										return prev;
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
