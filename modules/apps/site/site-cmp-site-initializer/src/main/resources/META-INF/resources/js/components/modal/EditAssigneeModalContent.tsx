/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';
import {
	Assignee,
	AssigneeValue,
} from '@liferay/object-dynamic-data-mapping-form-field-type';
import React, {useState} from 'react';

import {patchTaskById} from '../../utils/api';

type Props = {
	closeModal: () => void;
	loadData: () => void;
	taskId: string;
	value: AssigneeValue | {} | null;
};

export default function EditAssigneeModalContent({
	closeModal,
	loadData,
	taskId,
	value: initialValue,
}: Props) {
	const [value, setValue] = useState(initialValue);

	const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
		event.preventDefault();

		const response = await patchTaskById({
			body: {assignTo: value},
			taskId,
		});

		if (response.ok) {
			closeModal();

			loadData();
		}
		else {
			throw new Error('Unable to update assignee');
		}
	};

	return (
		<form onSubmit={handleSubmit}>
			<ClayModal.Header>
				{Liferay.Language.get('assign-to-...')}
			</ClayModal.Header>

			<ClayModal.Body>
				<Assignee
					customClasses="form-control"
					name="assignee"
					onChange={(value: AssigneeValue | {}) => {
						setValue(value);
					}}
					searchURL={
						Liferay.ThemeDisplay.getPortalURL() +
						'/o/cmp/assignee-context/'
					}
					showLabel={false}
					value={value}
					visible={true}
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
