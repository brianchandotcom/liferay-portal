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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayModal from '@clayui/modal';
import {Observer} from '@clayui/modal/lib/types';
import {
	API,
	FormError,
	Input,
	useForm,
} from '@liferay/object-js-components-web';
import React, {useState} from 'react';

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

interface IProps extends React.HTMLAttributes<HTMLElement> {
	apiURL: string;
	inputId: string;
	label: string;
	observer: Observer;
	onClose: () => void;
}

type TInitialValues = {
	name: LocalizedValue<string>;
};

export function ModalBasicWithFieldName({
	apiURL,
	inputId,
	label,
	observer,
	onClose,
}: IProps) {
	const initialValues: TInitialValues = {
		name: {[defaultLanguageId]: ''},
	};
	const [error, setError] = useState<string>('');

	const onSubmit = async ({name}: TInitialValues) => {
		try {
			await API.save(apiURL, {name: {[defaultLanguageId]: name}}, 'POST');

			onClose();
			window.location.reload();
		}
		catch (error) {
			setError((error as Error).message);
		}
	};

	const validate = ({name}: TInitialValues) => {
		const errors: FormError<TInitialValues> = {};

		if (name[defaultLanguageId] === '') {
			errors.name = Liferay.Language.get('required');
		}

		return errors;
	};

	const {errors, handleChange, handleSubmit, values} = useForm({
		initialValues,
		onSubmit,
		validate,
	});

	return (
		<>
			<ClayModal observer={observer}>
				<ClayForm onSubmit={handleSubmit}>
					<ClayModal.Header>{label}</ClayModal.Header>

					<ClayModal.Body>
						{error && (
							<ClayAlert displayType="danger">{error}</ClayAlert>
						)}

						<Input
							error={errors.name}
							id={inputId}
							label={Liferay.Language.get('name')}
							name="name"
							onChange={handleChange}
							required
							value={values.name[defaultLanguageId]}
						/>
					</ClayModal.Body>

					<ClayModal.Footer
						last={
							<ClayButton.Group key={1} spaced>
								<ClayButton
									displayType="secondary"
									onClick={onClose}
								>
									{Liferay.Language.get('cancel')}
								</ClayButton>

								<ClayButton displayType="primary" type="submit">
									{Liferay.Language.get('save')}
								</ClayButton>
							</ClayButton.Group>
						}
					/>
				</ClayForm>
			</ClayModal>
		</>
	);
}

export default ModalBasicWithFieldName;
