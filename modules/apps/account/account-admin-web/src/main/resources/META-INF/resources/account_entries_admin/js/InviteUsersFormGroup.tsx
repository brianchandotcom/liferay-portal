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

import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayMultiSelect from '@clayui/multi-select';
import React, {useState} from 'react';

import {InputGroup, MultiSelectItem, ValidatableMultiSelectItem} from './types';

type OnItemsChangeFn = (items: MultiSelectItem[]) => void;

interface IProps extends InputGroup {
	availableAccountRoles: MultiSelectItem[];
	index: number;
	onAccountRoleItemsChange: OnItemsChangeFn;
	onEmailAddressItemsChange: OnItemsChangeFn;
	portletNamespace: string;
}

const MultiSelect = ({
	autoFocus = false,
	errorMessages,
	helpText,
	inputName,
	items,
	label,
	onBlurFn = () => {},
	onItemsChangeFn,
	placeholder,
	required = false,
	sourceItems = [],
}: {
	autoFocus?: boolean;
	errorMessages: string[];
	helpText?: string;
	inputName: string;
	items: MultiSelectItem[];
	label: string;
	onBlurFn?: () => void;
	onItemsChangeFn: OnItemsChangeFn;
	placeholder?: string;
	required?: boolean;
	sourceItems?: MultiSelectItem[];
}) => (
	<ClayForm.Group className={errorMessages.length ? 'has-error' : ''}>
		<label htmlFor={inputName}>
			{label}

			{required && (
				<ClayIcon className="ml-1 reference-mark" symbol="asterisk" />
			)}
		</label>

		<ClayInput.Group>
			<ClayInput.GroupItem>
				<ClayMultiSelect
					autoFocus={autoFocus}
					id={`${inputName}MultiSelect`}
					inputName={inputName}
					items={items}
					loadingState={4}
					onBlur={onBlurFn}

					// @ts-ignore

					onItemsChange={onItemsChangeFn}
					placeholder={placeholder}
					sourceItems={sourceItems}
				/>

				<ClayForm.FeedbackGroup>
					{Boolean(helpText) && (
						<ClayForm.Text>{helpText}</ClayForm.Text>
					)}

					{errorMessages.map((errorMessage) => (
						<ClayForm.FeedbackItem key={errorMessage}>
							{errorMessage}
						</ClayForm.FeedbackItem>
					))}
				</ClayForm.FeedbackGroup>
			</ClayInput.GroupItem>
		</ClayInput.Group>
	</ClayForm.Group>
);

function getErrorMessages(items: ValidatableMultiSelectItem[]) {
	return items.map((item) => item.errorMessage).filter(Boolean) as string[];
}

const InviteUserFormGroup = ({
	accountRoles,
	availableAccountRoles,
	emailAddresses,
	index,
	onAccountRoleItemsChange,
	onEmailAddressItemsChange,
	portletNamespace,
}: IProps) => {
	const [showRequiredMessage, setShowRequiredMessage] = useState<boolean>(
		false
	);

	const emailAddressErrorMessages = [];

	if (showRequiredMessage && !emailAddresses.length) {
		emailAddressErrorMessages.push(
			Liferay.Language.get('this-field-is-required')
		);
	}

	for (const errorMessage of getErrorMessages(emailAddresses)) {
		emailAddressErrorMessages.push(errorMessage);
	}

	return (
		<ClayLayout.Sheet size="lg">
			<MultiSelect
				autoFocus={true}
				errorMessages={emailAddressErrorMessages}
				inputName={`${portletNamespace}emailAddresses${index}`}
				items={emailAddresses}
				label={Liferay.Language.get('email-addresses')}
				onBlurFn={() => setShowRequiredMessage(true)}
				onItemsChangeFn={onEmailAddressItemsChange}
				placeholder={Liferay.Language.get(
					'type-a-comma-or-press-enter-to-input-email-addresses'
				)}
				required={true}
			/>

			<MultiSelect
				errorMessages={getErrorMessages(accountRoles)}
				helpText={Liferay.Language.get(
					'roles-will-be-applied-to-all-of-the-users-above'
				)}
				inputName={`${portletNamespace}accountRoleIds${index}`}
				items={accountRoles}
				label={Liferay.Language.get('roles')}
				onItemsChangeFn={onAccountRoleItemsChange}
				placeholder={Liferay.Language.get(
					'type-a-comma-or-press-enter-to-input-roles'
				)}
				sourceItems={availableAccountRoles}
			/>
		</ClayLayout.Sheet>
	);
};

export default InviteUserFormGroup;
