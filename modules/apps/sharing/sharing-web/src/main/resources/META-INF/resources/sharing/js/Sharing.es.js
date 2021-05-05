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

import ClayButton from '@clayui/button';
import {useResource} from '@clayui/data-provider';
import ClayDropDown from '@clayui/drop-down';
import ClayForm, {
	ClayCheckbox,
	ClayInput,
	ClayRadio,
	ClayRadioGroup,
} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayModal from '@clayui/modal';
import ClayMultiSelect from '@clayui/multi-select';
import ClaySticker from '@clayui/sticker';
import {fetch, objectToFormData} from 'frontend-js-web';
import React, {useCallback, useRef, useState} from 'react';

function filterDuplicateItems(items) {
	return items.filter(
		(item, index) =>
			items.findIndex(
				(newItem) =>
					newItem.value.toLowerCase() === item.value.toLowerCase()
			) === index
	);
}

const SharingAutocomplete = ({onItemClick = () => {}, sourceItems}) => {
	return (
		<ClayDropDown.ItemList>
			{sourceItems.map((item) => (
				<ClayDropDown.Item
					key={item.id}
					onClick={() => onItemClick(item)}
				>
					<div className="autofit-row autofit-row-center">
						<div className="autofit-col mr-3">
							<ClaySticker
								className={`sticker-user-icon ${
									item.portraitURL ? '' : item.userId % 10
								}`}
								size="lg"
							>
								{item.portraitURL ? (
									<div className="sticker-overlay">
										<img
											className="sticker-img"
											src={item.portraitURL}
										/>
									</div>
								) : (
									<ClayIcon symbol="user" />
								)}
							</ClaySticker>
						</div>

						<div className="autofit-col">
							<strong>{item.fullName}</strong>

							<span>{item.emailAddress}</span>
						</div>
					</div>
				</ClayDropDown.Item>
			))}
		</ClayDropDown.ItemList>
	);
};

const Sharing = ({
	autocompleteUserURL,
	classNameId,
	classPK,
	portletNamespace,
	shareActionURL,
	sharingEntryPermissionDisplayActionId,
	sharingEntryPermissionDisplays,
	sharingVerifyEmailAddressURL,
}) => {
	const [emailAddressErrorMessages, setEmailAddressErrorMessages] = useState(
		[]
	);
	const [selectedItems, setSelectedItems] = useState([]);
	const [multiSelectValue, setMultiSelectValue] = useState('');
	const [allowSharingChecked, setAllowSharingChecked] = useState(true);
	const [sharingPermission, setSharingPermission] = useState('VIEW');
	const emailValidationInProgress = useRef(false);

	const closeDialog = () => {
		Liferay.Util.getOpener().Liferay.fire('closeModal', {
			id: 'sharingDialog',
		});
	};

	const showNotification = (message, error) => {
		const parentOpenToast = Liferay.Util.getOpener().Liferay.Util.openToast;

		const openToastParams = {message};

		if (error) {
			openToastParams.title = Liferay.Language.get('error');
			openToastParams.type = 'danger';
		}

		closeDialog();

		parentOpenToast(openToastParams);
	};

	const handleSubmit = (event) => {
		event.preventDefault();

		const data = {
			[`${portletNamespace}classNameId`]: classNameId,
			[`${portletNamespace}classPK`]: classPK,
			[`${portletNamespace}shareable`]: allowSharingChecked,
			[`${portletNamespace}sharingEntryPermissionDisplayActionId`]: sharingPermission,
			[`${portletNamespace}userEmailAddress`]: selectedItems
				.map(({value}) => value)
				.join(','),
		};

		const formData = objectToFormData(data);

		fetch(shareActionURL, {
			body: formData,
			method: 'POST',
		})
			.then((response) => {
				const jsonResponse = response.json();

				return response.ok
					? jsonResponse
					: jsonResponse.then((json) => {
							const error = new Error(
								json.errorMessage || response.statusText
							);
							throw Object.assign(error, {response});
					  });
			})
			.then((response) => {
				parent.Liferay.fire('sharing:changed', {
					classNameId,
					classPK,
				});
				showNotification(response.successMessage);
			})
			.catch((error) => {
				showNotification(error.message, true);
			});
	};

	const isEmailAddressValid = (email) => {
		const emailRegex = /.+@.+\..+/i;

		return emailRegex.test(email);
	};

	const handleItemsChange = useCallback(
		(items) => {
			emailValidationInProgress.current = true;

			Promise.all(
				items.map((item) => {
					if (
						item.id ||
						selectedItems.some(({value}) => item.value === value)
					) {
						return Promise.resolve({item});
					}

					if (!isEmailAddressValid(item.value)) {
						return Promise.resolve({
							error: Liferay.Util.sub(
								Liferay.Language.get(
									'x-is-not-a-valid-email-address'
								),
								item.value
							),
							item,
						});
					}

					return fetch(sharingVerifyEmailAddressURL, {
						body: objectToFormData({
							[`${portletNamespace}emailAddress`]: item.value,
						}),
						method: 'POST',
					})
						.then((response) => response.json())
						.then(({userExists}) => ({
							error: !userExists
								? Liferay.Util.sub(
										Liferay.Language.get(
											'user-x-does-not-exist'
										),
										item.value
								  )
								: undefined,
							item,
						}));
				})
			).then((results) => {
				emailValidationInProgress.current = false;

				const erroredResults = results.filter(({error}) => !!error);

				setEmailAddressErrorMessages(
					erroredResults.map(({error}) => error)
				);

				if (erroredResults.length === 0) {
					setMultiSelectValue('');
				}

				if (erroredResults.length === 1) {
					setMultiSelectValue(erroredResults[0].item.value);
				}

				setSelectedItems(
					filterDuplicateItems(
						results
							.filter(({error}) => !error)
							.map(({item}) => item)
					)
				);
			});
		},
		[portletNamespace, selectedItems, sharingVerifyEmailAddressURL]
	);

	const handleChange = useCallback((value) => {
		if (!emailValidationInProgress.current) {
			setMultiSelectValue(value);
		}
	}, []);

	const {resource} = useResource({
		fetchOptions: {
			credentials: 'include',
			headers: new Headers({'x-csrf-token': Liferay.authToken}),
			method: 'GET',
		},
		fetchRetry: {
			attempts: 0,
		},
		link: autocompleteUserURL,
		variables: {
			[`${portletNamespace}query`]: multiSelectValue,
		},
	});

	const users = resource;

	return (
		<ClayForm className="sharing-modal-content" onSubmit={handleSubmit}>
			<div className="inline-scroller modal-body">
				<ClayForm.Group
					className={
						emailAddressErrorMessages.length ? 'has-error' : ''
					}
				>
					<ClayInput.Group>
						<ClayInput.GroupItem>
							<label htmlFor="userEmailAddress">
								{Liferay.Language.get('invite-to-collaborate')}
							</label>

							<ClayMultiSelect
								inputName={`${portletNamespace}userEmailAddress`}
								inputValue={multiSelectValue}
								items={selectedItems}
								menuRenderer={SharingAutocomplete}
								onChange={handleChange}
								onItemsChange={handleItemsChange}
								placeholder={Liferay.Language.get(
									'enter-name-or-email-address'
								)}
								sourceItems={
									multiSelectValue && users
										? users.map((user) => {
												return {
													emailAddress:
														user.emailAddress,
													fullName: user.fullName,
													id: user.userId,
													label: user.fullName,
													portraitURL:
														user.portraitURL,
													value: user.emailAddress,
												};
										  })
										: []
								}
							/>
							<ClayForm.FeedbackGroup>
								<ClayForm.Text>
									{Liferay.Language.get(
										'you-can-use-a-comma-to-enter-multiple-collaborators'
									)}
								</ClayForm.Text>
							</ClayForm.FeedbackGroup>

							{emailAddressErrorMessages.length > 0 && (
								<ClayForm.FeedbackGroup>
									{emailAddressErrorMessages.map(
										(emailAddressErrorMessage) => (
											<ClayForm.FeedbackItem
												key={emailAddressErrorMessage}
											>
												{emailAddressErrorMessage}
											</ClayForm.FeedbackItem>
										)
									)}
								</ClayForm.FeedbackGroup>
							)}
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</ClayForm.Group>

				<ClayForm.Group>
					<ClayCheckbox
						checked={allowSharingChecked}
						label={Liferay.Language.get(
							'allow-the-item-to-be-shared-with-other-users'
						)}
						name={`${portletNamespace}shareable`}
						onChange={() =>
							setAllowSharingChecked((allow) => !allow)
						}
					/>
				</ClayForm.Group>

				<h4 className="sheet-tertiary-title">
					{Liferay.Language.get('sharing-permissions')}
				</h4>

				<ClayForm.Group>
					<ClayRadioGroup
						name={`${portletNamespace}sharingEntryPermissionDisplayActionId`}
						onSelectedValueChange={(permission) =>
							setSharingPermission(permission)
						}
						selectedValue={sharingPermission}
					>
						{sharingEntryPermissionDisplays.map((display) => (
							<ClayRadio
								checked={
									sharingEntryPermissionDisplayActionId ===
									display.sharingEntryPermissionDisplayActionId
								}
								disabled={!display.enabled}
								key={
									display.sharingEntryPermissionDisplayActionId
								}
								label={display.title}
								value={
									display.sharingEntryPermissionDisplayActionId
								}
							>
								<div className="form-text">
									{display.description}
								</div>
							</ClayRadio>
						))}
					</ClayRadioGroup>
				</ClayForm.Group>
			</div>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={closeDialog}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton displayType="primary" type="submit">
							{Liferay.Language.get('share')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayForm>
	);
};

export default Sharing;
