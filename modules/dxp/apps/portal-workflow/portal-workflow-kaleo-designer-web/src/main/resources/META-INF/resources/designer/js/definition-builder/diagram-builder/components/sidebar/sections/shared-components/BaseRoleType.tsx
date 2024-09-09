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

import type {ChangeEvent} from 'react';

import type {NodeInformationError} from '../utils';

interface AccountRole {
	accountId: number;
	description: string;
	displayName: string;
	externalReferenceCode: string;
	id: number;
	name: string;
	roleId: number;
}

interface AccountRoleItem {
	roleName: string;
	roleType: string;
}

type AssignmentsSectionDefaultSection = {
	identifier: string;
};

type AssignmentsSectionUserSection = {
	emailAddress: string;
	identifier: string;
	name: string;
	screenName: string;
	userId: number;
};

type AssignmentsSectionRoleTypeSection = {
	autoCreate: boolean;
	identifier: string;
	roleName: string;
	roleType: string;
};

type AssignmentsSections =
	| AssignmentsSectionDefaultSection[]
	| AssignmentsSectionUserSection[]
	| AssignmentsSectionRoleTypeSection[];

interface BaseRoleTypeErrors extends NodeInformationError {
	roleName: boolean[][];
}

interface BaseRoleTypeProps {
	autoCreate: boolean;
	buttonName: string;
	errors: BaseRoleTypeErrors;
	identifier: string;
	index: number;
	inputLabel: string;
	notificationIndex: number;
	roleName: string;
	roleType: string;
	sectionsLength: number;
	setErrors: (value: BaseRoleTypeErrors) => void;
	setSections: React.Dispatch<React.SetStateAction<AssignmentsSections>>;
	updateSelectedItem: (value: AssignmentsSections) => void;
}

interface Role {
	availableLanguages: Liferay.Language.Locale[];
	dateCreated: string;
	dateModified: string;
	description: string;
	description_i18n: Liferay.Language.FullyLocalizedValue<string>;
	externalReferenceCode: string;
	id: number;
	name: string;
	name_i18n: Liferay.Language.FullyLocalizedValue<string>;
	roleType: string;
}

export function BaseRoleType({
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
}: BaseRoleTypeProps) {
	const {accountEntryId} = useContext(DefinitionBuilderContext);
	const [accountRoles, setAccountRoles] = useState<AccountRoleItem[]>([]);
	const [filterRoleName, setFilterRoleName] = useState(true);
	const [filterRoleType, setFilterRoleType] = useState(true);
	const [loading, setLoading] = useState(false);
	const [roleNameDropdownActive, setRoleNameDropdownActive] = useState(false);
	const [roleTypeDropdownActive, setRoleTypeDropdownActive] = useState(false);
	const [roles, setRoles] = useState<Role[]>([]);
	const [selectedRoleName, setSelectedRoleName] = useState<string>(roleName);
	const [selectedRoleType, setSelectedRoleType] = useState<string>(
		titleCase(roleType)
	);
	const [checked, setChecked] = useState(stringToBoolean(String(autoCreate)));

	const checkRoleTypeErrors = (
		errors: BaseRoleTypeErrors,
		selectedRoleName: string
	) => {
		const temp = errors?.roleName ? [...errors.roleName] : [];

		if (!temp[notificationIndex]) {
			temp[notificationIndex] = [];
		}

		if (!temp[notificationIndex][index]) {
			temp[notificationIndex][index] = false;
		}

		temp[notificationIndex][index] = selectedRoleName === '';

		return {...errors, roleName: temp};
	};

	const deleteSection = () => {
		setSections((prevSections: AssignmentsSections) => {
			const newSections = prevSections.filter(
				(prevSection) => prevSection.identifier !== identifier
			);

			updateSelectedItem(newSections);

			return newSections;
		});
	};

	const getRolesInfo = () => {
		const rolesInfo = {} as {
			[key: string]: {
				roleName: string;
				roleType: string;
			}[];
		};

		roles.forEach((item) => {
			const roleType = titleCase(item.roleType) as string;

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

	const roleNameInputChange = (event: ChangeEvent<HTMLInputElement>) => {
		event.persist();

		setFilterRoleName(true);
		setSelectedRoleName(event.target.value);
	};

	const roleNameItemUpdate = (item: {
		autoCreate: boolean;
		roleName: string;
		roleType: string;
	}) => {
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

	const roleTypeInputChange = (event: ChangeEvent<HTMLInputElement>) => {
		event.persist();

		setFilterRoleType(true);
		setSelectedRoleType(event.target.value);
		setSelectedRoleName('');
	};

	const roleTypeItemClick = (item: string) => {
		setSelectedRoleType(item);
		setRoleTypeDropdownActive(false);
		setSelectedRoleName('');
	};

	useEffect(() => {
		if (selectedRoleName !== null) {
			setErrors(checkRoleTypeErrors(errors, selectedRoleName));
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedRoleName]);

	useEffect(() => {
		setChecked(stringToBoolean(String(autoCreate)));
		setSelectedRoleName(roleName);
		setSelectedRoleType(titleCase(roleType));

		roleNameItemUpdate({
			autoCreate,
			roleName,
			roleType: roleType.toLowerCase(),
		});

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [autoCreate, roleName, roleType]);

	useEffect(() => {
		const makeFetch = async () => {
			setLoading(true);

			const accountRolesResponse = await retrieveAccountRoles(
				Number(accountEntryId)
			);

			const accountRolesResponseJSON =
				(await accountRolesResponse.json()) as {items: AccountRole[]};

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

			const {items} = (await rolesResponse.json()) as {items: Role[]};

			setRoles(items);

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
								onChange={roleTypeInputChange}
								onFocus={() => roleTypeInputFocus()}
								value={selectedRoleType}
							/>

							<ClayAutocomplete.DropDown
								active={roleTypeDropdownActive}
								closeOnClickOutside
								onSetActive={setRoleTypeDropdownActive}
							>
								<ClayDropDown.ItemList>
									{!roles.length && (
										<ClayDropDown.Item className="disabled">
											{Liferay.Language.get(
												'no-results-found'
											)}
										</ClayDropDown.Item>
									)}

									{!!roles &&
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
								onChange={roleNameInputChange}
								onFocus={() => roleNameInputFocus()}
								value={selectedRoleName}
							/>

							<ClayAutocomplete.DropDown
								active={roleNameDropdownActive}
								closeOnClickOutside
								onSetActive={setRoleNameDropdownActive}
							>
								<ClayDropDown.ItemList>
									{!roles.length && (
										<ClayDropDown.Item className="disabled">
											{Liferay.Language.get(
												'no-results-found'
											)}
										</ClayDropDown.Item>
									)}

									{!!roles &&
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
								setChecked((value: boolean) => {
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
							aria-label={Liferay.Language.get('delete')}
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
					aria-label={buttonName}
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
}
