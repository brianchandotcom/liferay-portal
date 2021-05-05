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

import {useResource} from '@clayui/data-provider';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayMultiSelect, {itemLabelFilter} from '@clayui/multi-select';
import React, {useState} from 'react';

function formatAutocompleteValue(data) {
	return `${data.fullName} (${data.emailAddress})`;
}

function isEmailAddressValid(email) {
	const emailRegex = /.+@.+\..+/i;

	return emailRegex.test(email);
}

function formatAutocompleteUsersFromRequest(resource, inputValue) {
	if (resource.length) {
		return itemLabelFilter(
			resource.map((data) => {
				return {
					label: data.emailAddress,
					value: formatAutocompleteValue(data),
				};
			}),
			inputValue
		);
	}

	return resource;
}

const Email = ({
	addresses,
	autocompleteUserURL,
	emailContent,
	message,
	onMessageChanged,
	onMultiSelectItemsChanged,
	onSubjectChanged,
	subject,
}) => {
	const [multiSelectValue, setMultiSelectValue] = useState('');

	const [networkStatus, setNetworkStatus] = useState(4);

	const {resource} = useResource({
		fetchPolicy: 'cache-first',
		link: autocompleteUserURL,
		onNetworkStatusChange: setNetworkStatus,
	});

	const error = networkStatus === 5;

	return (
		<div className="share-form-modal-item-email">
			<ClayForm.Group>
				<label>{Liferay.Language.get('to')}</label>
				<ClayInput.Group small stacked>
					<ClayInput.GroupItem>
						{!error && (
							<>
								<ClayMultiSelect
									closeButtonAriaLabel={Liferay.Language.get(
										'remove'
									)}
									inputValue={multiSelectValue}
									items={addresses}
									onChange={setMultiSelectValue}
									onClearAllButtonClick={() => {
										emailContent.current.addresses = [];

										onMultiSelectItemsChanged([]);
									}}
									onItemsChange={(newItems) => {
										if (!newItems.length) {
											emailContent.current.addresses = newItems;

											return onMultiSelectItemsChanged(
												newItems
											);
										}

										if (
											newItems.length &&
											isEmailAddressValid(
												newItems[newItems.length - 1]
													?.label
											)
										) {
											emailContent.current.addresses = newItems;

											return onMultiSelectItemsChanged(
												newItems
											);
										}
									}}
									placeholder={Liferay.Language.get(
										'enter-a-list-of-email-addresses'
									)}
									sourceItems={
										resource
											? formatAutocompleteUsersFromRequest(
													resource
											  )
											: []
									}
								/>
								<ClayForm.FeedbackGroup>
									<ClayForm.Text>
										{Liferay.Language.get(
											'you-can-use-a-comma-to-enter-multiple-emails'
										)}
									</ClayForm.Text>
								</ClayForm.FeedbackGroup>
							</>
						)}
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ClayForm.Group>

			<ClayForm.Group>
				<label htmlFor="subject">
					{Liferay.Language.get('subject')}
				</label>
				<ClayInput.Group>
					<ClayInput.GroupItem>
						<ClayInput
							className="form-control"
							id="subject"
							onChange={({target: {value: newSubject}}) => {
								emailContent.current.subject = newSubject;

								onSubjectChanged(newSubject);
							}}
							type="text"
							value={subject}
						/>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ClayForm.Group>

			<ClayForm.Group>
				<label htmlFor="message">
					{Liferay.Language.get('message')}
				</label>
				<ClayInput.Group>
					<ClayInput.GroupItem>
						<ClayInput
							className="form-control"
							component="textarea"
							id="message"
							onChange={({target: {value: newMessage}}) => {
								emailContent.current.message = newMessage;

								onMessageChanged(newMessage);
							}}
							type="text"
							value={message}
						/>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ClayForm.Group>
		</div>
	);
};

export default Email;
