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
import {fetch} from 'frontend-js-web';
import React, {useState} from 'react';

import useForm from '../hooks/useForm';
import Input from './Form/Input';

interface IProps extends React.HTMLAttributes<HTMLElement> {
	apiURL: string;
	inputId: string;
	label: string;
	observer: any;
	onClose: () => void;
}

type TInitialValues = {
	name: LocalizedValue<string>;
};

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId() as Locale;

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
		const response = await fetch(apiURL, {
			body: JSON.stringify({
				name: {
					[defaultLanguageId]: name,
				},
			}),
			headers: new Headers({
				'Accept': 'application/json',
				'Content-Type': 'application/json',
			}),
			method: 'POST',
		});

		if (response.status === 401) {
			window.location.reload();
		}
		else if (response.ok) {
			onClose();

			window.location.reload();
		}
		else {
			const {
				title = Liferay.Language.get('an-error-occurred'),
			} = (await response.json()) as {title?: string};

			setError(title);
		}
	};

	const validate = ({name}: any) => {
		const errors: any = {};

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
