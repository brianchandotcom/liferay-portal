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
import ClayForm, {ClayInput, ClayRadio, ClayRadioGroup} from '@clayui/form';
import ClayModal from '@clayui/modal';
import ClayMultiSelect from '@clayui/multi-select';
import {openToast} from 'frontend-js-web';
import React, {useContext, useEffect, useMemo, useState} from 'react';

import ChartContext from '../ChartContext';
import {getAccounts, updateAccountDetails} from '../data/accounts';
import {getUsers} from '../data/users';
import {ACCOUNTS_CREATION_ENABLED} from '../utils/flags';

function showNotFoundError(name) {
	openToast({
		message: Liferay.Util.sub(Liferay.Language.get('x-not-found'), name),
		type: 'danger',
	});
}

export default function AddOrganizationModal({
	closeModal,
	observer,
	parentData,
}) {
	const {chartInstanceRef} = useContext(ChartContext);

	const [accountsQuery, setAccountsQuery] = useState('');
	const [newAccountName, setNewAccountName] = useState('');
	const [fetchedAccounts, setFetchedAccounts] = useState([]);
	const [selectedAccounts, setSelectedAccounts] = useState([]);

	const [emailsQuery, setEmailsQuery] = useState('');
	const [fetchedUsers, setFetchedUsers] = useState([]);
	const [selectedUsers, setSelectedUsers] = useState([]);
	const [newAccountMode, updateNewAccountMode] = useState(false);

	useEffect(() => {
		if (accountsQuery) {
			getAccounts(accountsQuery).then((response) => {
				setFetchedAccounts(response.items);
			});
		}
		else {
			setFetchedAccounts([]);
		}
	}, [accountsQuery]);

	useEffect(() => {
		if (emailsQuery) {
			getUsers(emailsQuery).then((response) => {
				setFetchedUsers(response.items);
			});
		}
		else {
			setFetchedUsers([]);
		}
	}, [emailsQuery]);

	const accountOptions = useMemo(() => {
		const selectedAccountIds = new Set(
			selectedAccounts.map((account) => account.id)
		);

		return fetchedAccounts.filter((account) => {
			const alreadySelected = selectedAccountIds.has(account.id);
			const alreadyDefinedAsChild = account.organizationIds.some(
				(organizationId) =>
					Number(organizationId) === Number(parentData.id)
			);

			return !alreadySelected && !alreadyDefinedAsChild;
		});
	}, [parentData, selectedAccounts, fetchedAccounts]);

	function handleSave() {
		const errors = [];

		if (newAccountMode) {
			throw new Error('account-creation-un-handled');
		}
		else {
			if (accountsQuery) {
				showNotFoundError(accountsQuery);
			}

			if (selectedAccounts.length) {
				Promise.allSettled(
					selectedAccounts.map((account) =>
						updateAccountDetails(account.id, {
							organizationIds: [
								...account.organizationIds,
								parentData.id,
							],
						})
					)
				).then((results) => {
					const nodeChildren = [];

					results.forEach((result) => {
						if (result.status === 'fulfilled') {
							nodeChildren.push(result.value);
						}
						else {
							openToast({
								message: result.value,
								type: 'danger',
							});
							errors.push(result.value);
						}
					});

					chartInstanceRef.current.addNodes(
						nodeChildren,
						'account',
						parentData
					);

					chartInstanceRef.current.updateNodeContent({
						...parentData,
						numberOfAccounts:
							parentData.numberOfAccounts + nodeChildren.length,
					});

					if (!errors.length) {
						closeModal();
					}
				});
			}
		}
	}

	function handleItemsChange(items) {
		const filteredItems = items.filter((item) => {
			const newItem = item.name === item.id;

			if (newItem) {
				showNotFoundError(item.name);
			}

			return !newItem;
		});

		setSelectedAccounts(filteredItems);
	}

	return (
		<ClayModal center observer={observer} size="md">
			<ClayModal.Header>
				{Liferay.Language.get('add-accounts')}
			</ClayModal.Header>

			<ClayModal.Body>
				{ACCOUNTS_CREATION_ENABLED && (
					<ClayRadioGroup
						className="mb-4"
						onSelectedValueChange={updateNewAccountMode}
						selectedValue={newAccountMode}
					>
						<ClayRadio
							label={Liferay.Language.get(
								'select-existing-accounts'
							)}
							value={false}
						/>

						<ClayRadio
							label={Liferay.Language.get('create-new-account')}
							value={true}
						/>
					</ClayRadioGroup>
				)}
				{newAccountMode ? (
					<>
						<ClayForm.Group>
							<label htmlFor="newAccountNameInput">
								{Liferay.Language.get('name')}
							</label>

							<ClayInput
								id="newAccountNameInput"
								onChange={(event) =>
									setNewAccountName(event.target.value)
								}
								value={newAccountName}
							/>
						</ClayForm.Group>

						<ClayForm.Group>
							<label htmlFor="administratorEmailInput">
								{Liferay.Language.get('administrators-email')}
							</label>

							<ClayMultiSelect
								id="administratorEmailInput"
								inputValue={emailsQuery}
								items={selectedUsers}
								locator={{label: 'name', value: 'id'}}
								onChange={setEmailsQuery}
								onItemsChange={setSelectedUsers}
								sourceItems={fetchedUsers}
							/>
						</ClayForm.Group>
					</>
				) : (
					<ClayForm.Group>
						<label htmlFor="searchAccountInput">
							{Liferay.Language.get('search-account')}
						</label>

						<ClayMultiSelect
							id="searchAccountInput"
							inputValue={accountsQuery}
							items={selectedAccounts}
							locator={{label: 'name', value: 'id'}}
							onChange={setAccountsQuery}
							onItemsChange={handleItemsChange}
							sourceItems={accountOptions}
						/>
					</ClayForm.Group>
				)}
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={closeModal}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton displayType="primary" onClick={handleSave}>
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
}
