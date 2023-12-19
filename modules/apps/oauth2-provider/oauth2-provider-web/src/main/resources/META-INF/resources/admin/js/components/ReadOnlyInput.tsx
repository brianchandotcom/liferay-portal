/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayInput} from '@clayui/form';
import {FieldBase} from 'frontend-js-components-web';
import {openModal} from 'frontend-js-web';
import React, {useState} from 'react';

import {EditClientOAuth2ModalContent} from './modals/EditClientOAuth2ModalContent';

interface IReadOnlyInputProps extends React.HTMLAttributes<HTMLElement> {
	alertText: string;
	baseResourceURL?: string;
	id: string;
	initialValue: string;
	isSecret?: boolean;
	label: string;
	title: string;
	tooltip: string;
	type?: string;
}

const ReadOnlyInput: React.FC<IReadOnlyInputProps> = (props) => {
	const {
		alertText,
		baseResourceURL = '',
		id,
		initialValue,
		isSecret = false,
		label,
		title,
		tooltip,
		type = 'text',
	} = props;

	const [value, setValue] = useState(initialValue);

	const handleSetInputValue = (newInputValue: string) => {
		setValue(newInputValue);
	};

	return (
		<>
			<FieldBase id={id} label={label} required={true} tooltip={tooltip}>
				<ClayInput.Group>
					<ClayInput.GroupItem prepend>
						<ClayInput
							id={id}
							name={id}
							readOnly
							type={type}
							value={value}
						/>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem append shrink>
						<ClayButton
							displayType="secondary"
							onClick={() =>
								openModal({
									center: true,
									contentComponent: ({
										closeModal,
									}: {
										closeModal: () => void;
									}) =>
										EditClientOAuth2ModalContent({
											alertText,
											baseResourceURL,
											closeModal,
											handleSetInputValue,
											id,
											initialValue,
											isSecret,
											label,
											title,
											tooltip,
										}),
									id: 'editClientOAuth2Modal',
									size: 'md',
									status: 'warning',
								})
							}
						>
							{Liferay.Language.get('edit')}
						</ClayButton>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</FieldBase>
		</>
	);
};

export default ReadOnlyInput;
