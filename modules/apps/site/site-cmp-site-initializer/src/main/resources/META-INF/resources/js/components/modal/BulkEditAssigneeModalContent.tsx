/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';
import {AssigneeValue} from '@liferay/object-dynamic-data-mapping-form-field-type';
import {
	IBulkActionFDSData,
	IBulkActionTaskStarterDTO,
	triggerAssetBulkAction,
} from '@liferay/site-cms-site-initializer';
import React, {useState} from 'react';

import {displayAssignSuccessToast} from '../../utils/toastUtil';
import CustomAssignee from '../CustomAssignee';

import './../AssigneeTrigger.scss';

import {openToast} from 'frontend-js-components-web';

type Props = {
	apiURL: string;
	closeModal: () => void;
	selectedData: IBulkActionFDSData;
	value: AssigneeValue | {} | null;
};
const displayErrorToast = (errorMessage?: string) => {
	openToast({
		message:
			errorMessage ||
			Liferay.Language.get('an-unexpected-error-occurred'),
		title: Liferay.Language.get('error'),
		type: 'danger',
	});
};
export default function BulkEditAssigneeModalContent({
	apiURL,
	closeModal,
	selectedData,
	value: initialValue,
}: Props) {
	const [value, setValue] = useState<AssigneeValue | null | {}>(initialValue);

	const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
		event.preventDefault();
		triggerAssetBulkAction({
			apiURL,
			keyValues: {
				className: (value as AssigneeValue)?.type,
				externalReferenceCode: (value as AssigneeValue)
					?.externalReferenceCode,
				name: (value as AssigneeValue)?.name,
			},
			onCreateError: ({error}) => {
				displayErrorToast(error as string);
			},
			onCreateSuccess: ({error = ''}) => {
				if (error) {
					displayErrorToast(error as string);

					return;
				}
				displayAssignSuccessToast(
					'Task',
					(value as AssigneeValue).name
				);
				closeModal();
			},
			overrideDefaultErrorToast: true,
			selectedData,
			type: 'AssignToBulkAction',
		} as IBulkActionTaskStarterDTO<'AssignToBulkAction'>);
	};

	return (
		<form onSubmit={handleSubmit}>
			<ClayModal.Header>
				{Liferay.Language.get('assign-tasks-to')}
			</ClayModal.Header>

			<ClayModal.Body>
				<CustomAssignee
					onChange={(value: AssigneeValue | {}) => {
						setValue(value);
					}}
					triggerClassName="form-control"
					value={value}
				/>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={closeModal}
							type="button"
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton displayType="primary" type="submit">
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</form>
	);
}
