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
import ClayModal from '@clayui/modal';
import {Observer} from '@clayui/modal/lib/types';
import {Input} from '@liferay/object-js-components-web';
import React, {useState} from 'react';

export default function DangerModal({
	children,
	errorMessage,
	observer,
	onClose,
	onDelete,
	placeholder,
	title,
	token,
}: IProps) {
	const [value, setValue] = useState<string>();

	return (
		<ClayModal observer={observer} status="danger">
			<ClayModal.Header>{title}</ClayModal.Header>

			<ClayModal.Body>
				{children}

				<Input
					error={
						value !== undefined &&
						value.toLowerCase() !== token.toLowerCase()
							? errorMessage
							: ''
					}
					onChange={({target: {value}}) => {
						setValue(value);
					}}
					placeholder={placeholder}
					value={value}
				/>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton displayType="secondary" onClick={onClose}>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							disabled={
								token.toLocaleLowerCase() !==
								value?.toLocaleLowerCase()
							}
							displayType="danger"
							onClick={onDelete}
						>
							{Liferay.Language.get('delete')}
						</ClayButton>
					</ClayButton.Group>
				}
			></ClayModal.Footer>
		</ClayModal>
	);
}

interface IProps {
	children?: React.ReactNode;
	errorMessage: string;
	observer: Observer;
	onClose: () => void;
	onDelete: (event: React.MouseEvent<HTMLButtonElement>) => void;
	placeholder?: string;
	title: string;
	token: string;
}
