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
import ClayForm from '@clayui/form';
import ClayModal from '@clayui/modal';
import {Observer} from '@clayui/modal/lib/types';
import {FormError, Input, useForm} from '@liferay/object-js-components-web';
import React from 'react';

import {TYPES, useLayoutContext} from '../objectLayoutContext';

type TInitialValues = {
	name: string;
};

interface IModalAddObjectLayoutBoxProps
	extends React.HTMLAttributes<HTMLElement> {
	observer: Observer;
	onClose: () => void;
}

export function ModalAddObjectLayoutBox({
	observer,
	onClose,
	tabIndex,
}: IModalAddObjectLayoutBoxProps) {
	const [, dispatch] = useLayoutContext();

	const initialValues: TInitialValues = {
		name: '',
	};

	const onSubmit = (values: TInitialValues) => {
		dispatch({
			payload: {
				name: {
					[Liferay.ThemeDisplay.getDefaultLanguageId()]: values.name,
				},
				tabIndex,
				type: 'regular',
			},
			type: TYPES.ADD_OBJECT_LAYOUT_BOX,
		});

		onClose();
	};

	const onValidate = (values: TInitialValues) => {
		const errors: FormError<TInitialValues> = {};

		if (!values.name) {
			errors.name = Liferay.Language.get('required');
		}

		return errors;
	};

	const {errors, handleChange, handleSubmit, values} = useForm({
		initialValues,
		onSubmit,
		validate: onValidate,
	});

	return (
		<ClayModal observer={observer}>
			<ClayForm onSubmit={handleSubmit}>
				<ClayModal.Header>
					{Liferay.Language.get('add-block')}
				</ClayModal.Header>

				<ClayModal.Body>
					<Input
						error={errors.name}
						id="inputName"
						label={Liferay.Language.get('label')}
						name="name"
						onChange={handleChange}
						required
						value={values.name}
					/>
				</ClayModal.Body>

				<ClayModal.Footer
					last={
						<ClayButton.Group spaced>
							<ClayButton
								displayType="secondary"
								onClick={onClose}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton type="submit">
								{Liferay.Language.get('save')}
							</ClayButton>
						</ClayButton.Group>
					}
				/>
			</ClayForm>
		</ClayModal>
	);
}
