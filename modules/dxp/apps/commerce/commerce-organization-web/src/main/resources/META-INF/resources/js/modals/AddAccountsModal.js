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
import classNames from 'classnames';
import {openToast} from 'frontend-js-web';
import React, {useContext, useEffect, useMemo, useState} from 'react';

import ChartContext from '../ChartContext';
import {createAccount, getAccounts, updateAccount} from '../data/accounts';

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
	const [errors, setErrors] = useState([]);
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
		if (newAccountMode) {
			createAccount(newAccountName, [parentData.id])
				.then((accountData) => {
					openToast({
						message: Liferay.Util.sub(
							Liferay.Language.get('1-account-was-added-to-x'),
							parentData.name
						),
						type: 'success',
					});

					chartInstanceRef.current.addNodes(
						[accountData],
						'account',
						parentData
					);

					chartInstanceRef.current.updateNodeContent({
						...parentData,
						numberOfAccounts: parentData.numberOfAccounts + 1,
					});

					closeModal();
				})
				.catch((error) => {
					setErrors([error.title]);
				});
		}
		else {
			if (accountsQuery) {
				showNotFoundError(accountsQuery);
			}

			if (selectedAccounts.length) {
				Promise.allSettled(
					selectedAccounts.map((account) =>
						updateAccount(account.id, {
							organizationIds: [
								...account.organizationIds,
								parentData.id,
							],
						})
					)
				).then((results) => {
					const nodeChildren = [];
					const newErrors = [];

					results.forEach((result) => {
						if (result.status === 'fulfilled') {
							nodeChildren.push(result.value);
						}
						else {
							openToast({
								message: result.value,
								type: 'danger',
							});
							newErrors.push(result.value);
						}
					});

					setErrors(newErrors);

					const message =
						nodeChildren.length === 1
							? Liferay.Util.sub(
									Liferay.Language.get(
										'1-account-was-added-to-x'
									),
									parentData.name
							  )
							: Liferay.Util.sub(
									Liferay.Language.get(
										'x-accounts-were-added-to-x'
									),
									nodeChildren.length,
									parentData.name
							  );

					openToast({
						message,
						type: 'success',
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

	const errorsContainer = !!errors.length && (
		<ClayForm.FeedbackGroup>
			{errors.map((error, i) => (
				<ClayForm.FeedbackItem key={i}>
					<ClayForm.FeedbackIndicator symbol="info-circle" />
					{error}
				</ClayForm.FeedbackItem>
			))}
		</ClayForm.FeedbackGroup>
	);

	return (
		<ClayModal center observer={observer} size="md">
			<ClayModal.Header>
				{Liferay.Language.get('add-accounts')}
			</ClayModal.Header>

			<ClayModal.Body>
				<ClayRadioGroup
					className="mb-4"
					onSelectedValueChange={updateNewAccountMode}
					selectedValue={newAccountMode}
				>
					<ClayRadio
						label={Liferay.Language.get('select-accounts')}
						value={false}
					/>

					<ClayRadio
						label={Liferay.Language.get('create-new-account')}
						value={true}
					/>
				</ClayRadioGroup>

				{newAccountMode ? (
					<ClayForm.Group
						className={classNames(!!errors.length && 'has-error')}
					>
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

						{errorsContainer}
					</ClayForm.Group>
				) : (
					<ClayForm.Group
						className={classNames(!!errors.length && 'has-error')}
					>
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

						{errorsContainer}
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
