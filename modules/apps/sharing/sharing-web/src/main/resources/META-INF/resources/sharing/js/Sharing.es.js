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
	ClayRadioGroup
} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayModal from '@clayui/modal';
import ClayMultiSelect from '@clayui/multi-select';
import ClaySticker from '@clayui/sticker';
import {fetch, objectToFormData} from 'frontend-js-web';
import React, {useState} from 'react';

const Sharing = ({
	classNameId,
	classPK,
	dialogId,
	portletNamespace,
	shareActionURL,
	sharingEntryPermissionDisplayActionId,
	sharingEntryPermissionDisplays,
	sharingUserAutocompleteURL,
	sharingVerifyEmailAddressURL
}) => {
	const [emailAddressErrorMessage, setEmailAddressErrorMessage] = useState();
	const [selectedItems, setSelectedItems] = useState([]);
	const [multiSelectValue, setMultiSelectValue] = useState('');
	const [allowSharingChecked, setAllowSharingChecked] = useState(true);
	const [sharingPermission, setSharingPermission] = useState('VIEW');

	const closeDialog = () => {
		const sharingDialog = Liferay.Util.getWindow(dialogId);

		if (sharingDialog && sharingDialog.hide) {
			sharingDialog.hide();
		}
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

	const handleSubmit = event => {
		event.preventDefault();

		const data = {
			[`${portletNamespace}classNameId`]: classNameId,
			[`${portletNamespace}classPK`]: classPK,
			[`${portletNamespace}shareable`]: allowSharingChecked,
			[`${portletNamespace}sharingEntryPermissionDisplayActionId`]: sharingPermission,
			[`${portletNamespace}userEmailAddress`]: selectedItems
				.map(({value}) => value)
				.join(',')
		};

		const formData = objectToFormData(data);

		fetch(shareActionURL, {
			body: formData,
			method: 'POST'
		})
			.then(response => {
				const jsonResponse = response.json();

				return response.ok
					? jsonResponse
					: jsonResponse.then(json => {
							const error = new Error(
								json.errorMessage || response.statusText
							);
							throw Object.assign(error, {response});
					  });
			})
			.then(response => {
				parent.Liferay.fire('sharing:changed', {
					classNameId,
					classPK
				});
				showNotification(response.successMessage);
			})
			.catch(error => {
				showNotification(error.message, true);
			});
	};

	const onModalClose = () => {
		const sharingDialog = Liferay.Util.getWindow(dialogId);

		if (sharingDialog && sharingDialog.hide) {
			sharingDialog.hide();
		}
	};

	const isEmailAddressValid = email => {
		const emailRegex = /.+@.+\..+/i;

		return emailRegex.test(email);
	};

	const handleItemsChange = items => {
		if (items.length) {
			const newestItemValue = items[items.length - 1].value;

			items.map((item, index) => {
				if (index) {
					if (items[index - 1].emailAddress === newestItemValue) {
						items.pop();
					}
				}
			});

			if (isEmailAddressValid(newestItemValue)) {
				fetch(sharingVerifyEmailAddressURL, {
					body: objectToFormData({
						[`${portletNamespace}emailAddress`]: newestItemValue
					}),
					method: 'POST'
				})
					.then(response => response.json())
					.then(result => {
						const {userExists} = result;

						if (!userExists) {
							setEmailAddressErrorMessage(
								Liferay.Util.sub(
									Liferay.Language.get(
										'user-x-does-not-exist'
									),
									newestItemValue
								)
							);
							items.pop();
						}
					})
					.then(() => {
						setSelectedItems(items);
					});
			} else {
				setEmailAddressErrorMessage(
					Liferay.Language.get('please-enter-a-valid-email-address')
				);

				items.pop();

				setSelectedItems(items);
			}
		} else {
			setSelectedItems(items);
		}
	};

	const resource = useResource({
		link: sharingUserAutocompleteURL
	});

	const users = resource.resource;

	return (
		<ClayForm className="sharing-modal-content" onSubmit={handleSubmit}>
			<div className="inline-scroller modal-body">
				<ClayForm.Group
					className={emailAddressErrorMessage ? 'has-error' : ''}
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
								onChange={setMultiSelectValue}
								onItemsChange={handleItemsChange}
								placeholder={Liferay.Language.get(
									'enter-name-or-email-address'
								)}
								sourceItems={
									users
										? users.map(user => {
												return {
													emailAddress:
														user.emailAddress,
													fullName: user.fullName,
													id: user.userId,
													label: user.fullName,
													portraitURL:
														user.portraitURL,
													value: user.emailAddress
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

							{emailAddressErrorMessage && (
								<ClayForm.FeedbackGroup>
									<ClayForm.FeedbackItem>
										{emailAddressErrorMessage}
									</ClayForm.FeedbackItem>
								</ClayForm.FeedbackGroup>
							)}
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</ClayForm.Group>

				<ClayForm.Group>
					<ClayCheckbox
						checked={allowSharingChecked}
						label={Liferay.Language.get(
							'allow-the-document-to-be-shared-with-other-users'
						)}
						name={`${portletNamespace}shareable`}
						onChange={() => setAllowSharingChecked(allow => !allow)}
					/>
				</ClayForm.Group>

				<h4 className="sheet-tertiary-title">
					{Liferay.Language.get('sharing-permissions')}
				</h4>

				<ClayForm.Group>
					<ClayRadioGroup
						name={`${portletNamespace}sharingEntryPermissionDisplayActionId`}
						onSelectedValueChange={permission =>
							setSharingPermission(permission)
						}
						selectedValue={sharingPermission}
					>
						{sharingEntryPermissionDisplays.map(display => (
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
							onClick={onModalClose}
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

const SharingAutocomplete = ({
	inputValue,
	onItemClick = () => {},
	sourceItems
}) => {
	return (
		<ClayDropDown.ItemList>
			{sourceItems
				.filter(item => inputValue && item.value.match(inputValue))
				.map(item => (
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

export default function(props) {
	return <Sharing {...props} />;
}
