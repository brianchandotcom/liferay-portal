/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import DropDown from '@clayui/drop-down';
import ClayForm from '@clayui/form';
import MultiSelect from '@clayui/multi-select';
import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import React, {useCallback, useContext, useEffect, useState} from 'react';

import '../../css/main.scss';

import Button from '@clayui/button';

import {DSRContext} from './DSRInitializer';
import {TDSRContext, TDSRRoomDetailsStepProps} from './DSRTypes';
import FieldErrorMessage from './FieldErrorMessage';

const DSR_SITE_ROLES = [
	{
		key: '',
		label: Liferay.Language.get('view'),
	},
	{
		key: 'Site Administrator',
		label: Liferay.Language.get('edit'),
	},
];

function isEmailAddressValid(email: string) {
	const emailRegex = /.+@.+\..+/i;

	return emailRegex.test(email);
}

function DSRShareRoomStep({
	numberOfSteps,
	setHandleStepSubmit,
	step = 3,
}: TDSRRoomDetailsStepProps) {
	const {dataContext, setDataContext} = useContext<TDSRContext>(DSRContext);

	const [emailAddresses, setEmailAddresses] = useState<
		Array<{label: string; value: string}>
	>(
		(dataContext.share?.emailAddresses || []).map((item) => {
			return {label: item, value: item};
		})
	);
	const [roleKey, setRoleKey] = useState<string>(
		dataContext.share?.roleKey || ''
	);

	const handleEmailsFieldChange = useCallback(
		({emails}: {emails: Array<{label: string; value: string}>}) => {
			emails = emails.filter((email) => isEmailAddressValid(email.value));

			setDataContext((prevState) => ({
				...prevState,
				share: {
					emailAddresses: emails.map((email) => email.value.trim()),
					roleKey: prevState.share?.roleKey || '',
				},
			}));
			setEmailAddresses(emails);
		},
		[setDataContext]
	);

	const handleRoleKeyChange = useCallback(
		(key: string) => {
			setDataContext((prevState) => ({
				...prevState,
				share: {
					emailAddresses: prevState.share?.emailAddresses || [],
					roleKey: key,
				},
			}));
			setRoleKey(key);
		},
		[setDataContext]
	);

	useEffect(() => {
		setHandleStepSubmit(() => async (event: Event): Promise<boolean> => {
			event.preventDefault();

			return Promise.resolve(true);
		});
	}, [setHandleStepSubmit]);

	return (
		<>
			<div>
				<div className="mb-1 text-secondary" data-qa-id="stepLocator">
					{sub(
						Liferay.Language.get('step-x-of-x'),
						step,
						numberOfSteps
					)}
				</div>

				<div
					className="mb-1 text-6 text-weight-bold"
					data-qa-id="stepTitle"
				>
					{Liferay.Language.get('share-the-room')}
				</div>

				<div className="text-secondary">
					{Liferay.Language.get('share-the-room-with-your-teammates')}
				</div>
			</div>
			<div className="mt-4 row">
				<ClayForm.Group
					className={classNames('col-12 dsr-site-role-input', {
						'has-error': !!dataContext.errors.share,
					})}
				>
					<label
						className="d-block"
						htmlFor="dsr-users-email-addresses"
					>
						{Liferay.Language.get('emails')}
					</label>

					<MultiSelect
						allowDuplicateValues={false}
						autoFocus={true}
						data-qa-id="emailAddressesInput"
						inputName="dsr-users-email-addresses"
						items={emailAddresses}
						onItemsChange={(emails: Array<any>) => {
							handleEmailsFieldChange({emails});
						}}
						placeholder={Liferay.Language.get(
							'type-a-comma-or-press-enter-to-input-email-addresses'
						)}
					/>

					<DropDown
						closeOnClick={true}
						trigger={
							<Button
								className="dsr-site-role-trigger-button"
								data-qa-id="roleKeyButton"
								displayType="secondary"
								size="xs"
							>
								{
									DSR_SITE_ROLES.find(
										(item) => item.key === roleKey
									)?.label
								}
							</Button>
						}
						triggerIcon="caret-bottom"
					>
						<DropDown.ItemList items={DSR_SITE_ROLES}>
							{(item: any) => (
								<DropDown.Item
									data-qa-id={`roleKeyItem_${item.label}`}
									key={item.key}
									onClick={() => {
										handleRoleKeyChange(item.key);
									}}
								>
									{item.label}
								</DropDown.Item>
							)}
						</DropDown.ItemList>
					</DropDown>

					<FieldErrorMessage
						error={dataContext.errors.share}
						name="usersEmailAddresses"
					/>
				</ClayForm.Group>
			</div>
		</>
	);
}

export default DSRShareRoomStep;
