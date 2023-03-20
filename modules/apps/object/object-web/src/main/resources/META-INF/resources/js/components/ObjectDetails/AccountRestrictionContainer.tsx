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

import {ClayToggle} from '@clayui/form';
import ClayPanel from '@clayui/panel';
import {FormError, SingleSelect} from '@liferay/object-js-components-web';
import React, {useEffect, useState} from 'react';

import {defaultLanguageId} from '../../utils/constants';

interface AccountRestrictionContainerProps {
	errors: FormError<ObjectDefinition>;
	isApproved: boolean;
	objectFields: ObjectField[];
	setValues: (values: Partial<ObjectDefinition>) => void;
	values: Partial<ObjectDefinition>;
}

const STORAGE_TYPE_DEFAULT = 'default';

export function AccountRestrictionContainer({
	errors,
	isApproved,
	objectFields,
	setValues,
	values,
}: AccountRestrictionContainerProps) {
	const [accountRelationshipFields, setAccountRelationshipFields] = useState<
		LabelValueObject[]
	>([]);

	const [selectedAccount, setSelectedAccount] = useState<string>();
	const [disableAccountToggle, setDisableAccountToggle] = useState<boolean>(
		false
	);
	const [disableAccountSelect, setDisableAccountSelect] = useState<boolean>(
		false
	);

	useEffect(() => {
		const accountRelationshipFieldsResponse = objectFields.filter(
			(field) => {
				if (values.storageType !== STORAGE_TYPE_DEFAULT) {
					return (
						field.businessType === 'Text' ||
						field.businessType === 'Integer' ||
						field.businessType === 'LongInteger'
					);
				}

				return (
					field.businessType === 'Relationship' &&
					field.objectFieldSettings?.find(
						(fieldSetting) => fieldSetting.value === 'AccountEntry'
					)
				);
			}
		);

		if (accountRelationshipFieldsResponse.length) {
			setAccountRelationshipFields(
				accountRelationshipFieldsResponse.map(
					(accountRelationshipField) => {
						return {
							label: accountRelationshipField.label[
								defaultLanguageId
							] as string,
							value: accountRelationshipField.name,
						};
					}
				)
			);

			const currentAccountRelationship = accountRelationshipFieldsResponse.find(
				(relationshipField) =>
					relationshipField.name ===
					values.accountEntryRestrictedObjectFieldName
			);

			setSelectedAccount(
				currentAccountRelationship?.label[defaultLanguageId] ?? ''
			);

			if (isApproved && values.accountEntryRestricted) {
				setDisableAccountToggle(true);
			}

			if (isApproved && values.accountEntryRestrictedObjectFieldName) {
				setDisableAccountSelect(true);
			}
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [objectFields]);

	useEffect(() => {
		const selectedAccountLabel = accountRelationshipFields.find(
			(relationshipField) =>
				relationshipField.value ===
				values.accountEntryRestrictedObjectFieldName
		)?.label;

		setSelectedAccount(selectedAccountLabel ?? '');
	}, [
		values.accountEntryRestrictedObjectFieldName,
		accountRelationshipFields,
	]);

	return (
		<ClayPanel
			collapsable
			defaultExpanded
			displayTitle={Liferay.Language.get('account-restriction')}
			displayType="unstyled"
		>
			<ClayPanel.Body>
				<ClayToggle
					disabled={
						!accountRelationshipFields.length ||
						disableAccountToggle
					}
					label={Liferay.Language.get('active')}
					name="accountEntryRestricted"
					onToggle={() =>
						setValues({
							accountEntryRestricted: !values.accountEntryRestricted,
							accountEntryRestrictedObjectFieldName:
								!values.accountEntryRestricted === false
									? ''
									: values.accountEntryRestrictedObjectFieldName,
						})
					}
					toggled={values.accountEntryRestricted}
				/>

				<SingleSelect<LabelValueObject>
					disabled={
						!accountRelationshipFields.length ||
						!values.accountEntryRestricted ||
						disableAccountSelect
					}
					error={errors.accountEntryRestrictedObjectFieldName}
					label={Liferay.Language.get(
						'account-entry-restricted-object-field-id'
					)}
					onChange={({value}) => {
						setValues({
							accountEntryRestrictedObjectFieldName: value,
						});
					}}
					options={accountRelationshipFields}
					required={
						!!accountRelationshipFields.length &&
						values.accountEntryRestricted &&
						!disableAccountSelect
					}
					value={selectedAccount}
				/>
			</ClayPanel.Body>
		</ClayPanel>
	);
}
