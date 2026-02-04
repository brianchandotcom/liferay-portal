/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';
import {
	IBulkActionFDSData,
	IBulkActionTaskStarterDTO,
	triggerAssetBulkAction,
} from '@liferay/site-cms-site-initializer';
import moment from 'moment';
import React, {useCallback, useId, useState} from 'react';

import {displayErrorToast} from '../../utils/toastUtil';
import DateField, {dateConfig} from '../DateField';

export default function BulkEditDueDateModalContent({
	apiURL,
	closeModal,
	selectedData,
}: {
	apiURL?: string;
	closeModal: () => void;
	selectedData: IBulkActionFDSData;
}) {
	const [dueDate, setDueDate] = useState<string>('');
	const [submitDisabled, setSubmitDisabled] = useState<boolean>(false);

	const doBulkSubmit = useCallback(async () => {
		if (!dueDate) {
			return;
		}

		const formattedDate = moment(dueDate, dateConfig.momentFormat).format(
			'YYYY-MM-DD'
		);

		setSubmitDisabled(true);

		triggerAssetBulkAction({
			apiURL,
			keyValues: {dueDate: formattedDate},
			onCreateError: ({error}) => {
				setSubmitDisabled(false);

				displayErrorToast(error as string);
			},
			onCreateSuccess: ({error = ''}) => {
				if (error) {
					setSubmitDisabled(false);

					displayErrorToast(error as string);

					return;
				}

				closeModal();
			},
			overrideDefaultErrorToast: true,
			selectedData,
			type: 'DueDateBulkAction',
		} as IBulkActionTaskStarterDTO<'DueDateBulkAction'>);
	}, [apiURL, closeModal, selectedData, dueDate, setSubmitDisabled]);

	return (
		<>
			<ClayModal.Header
				closeButtonAriaLabel={Liferay.Language.get('close')}
			>
				{Liferay.Language.get('update-due-date')}
			</ClayModal.Header>

			<ClayModal.Body>
				<label>{Liferay.Language.get('new-due-date')}</label>

				<DateField
					id={useId()}
					onChange={async (value: string) => {
						setDueDate(value);
					}}
				/>
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

						<ClayButton
							disabled={submitDisabled}
							displayType="primary"
							onClick={doBulkSubmit}
							type="button"
						>
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</>
	);
}
