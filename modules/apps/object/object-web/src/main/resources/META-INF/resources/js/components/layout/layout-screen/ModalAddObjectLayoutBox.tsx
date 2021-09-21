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
import React, {useContext} from 'react';

import useForm from '../../../hooks/useForm';
import {normalizeLanguageId} from '../../../utils/string';
import Input from '../../form/Input';
import LayoutContext, {TYPES} from '../context';

const defaultLanguageId = normalizeLanguageId(
	Liferay.ThemeDisplay.getDefaultLanguageId()
);

type TInitialValues = {
	name: string;
};

interface IModalAddObjectLayoutBoxProps
	extends React.HTMLAttributes<HTMLElement> {
	observer: any;
	onClose: () => void;
}

const ModalAddObjectLayoutBox: React.FC<IModalAddObjectLayoutBoxProps> = ({
	observer,
	onClose,
	tabIndex,
}) => {
	const [, dispatch] = useContext(LayoutContext);

	const initialValues: TInitialValues = {
		name: '',
	};

	const onSubmit = (values: any) => {
		dispatch({
			payload: {
				name: {
					[defaultLanguageId]: values.name,
				},
				tabIndex,
			},
			type: TYPES.ADD_OBJECT_LAYOUT_BOX,
		});

		onClose();
	};

	const onValidate = (values: TInitialValues) => {
		const errors: any = {};

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
};

export default ModalAddObjectLayoutBox;
