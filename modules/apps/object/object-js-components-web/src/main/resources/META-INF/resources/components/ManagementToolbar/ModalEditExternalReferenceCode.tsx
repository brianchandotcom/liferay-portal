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
import React, {useState} from 'react';

import {FormError, useForm} from '../../hooks/useForm';
import {save} from '../../utils/api';
import {Input} from '../Input';
import {openToast} from '../SidePanelContent';
import {Entity} from './index';

interface ModalEditExternalReferenceCodeProps {
	externalReferenceCode: string;
	helpMessage: string;
	observer: Observer;
	onClose: () => void;
	onExternalReferenceCodeChange?: (value: string) => void;
	onGetEntity: () => Promise<Entity>;
	saveURL: string;
	setExternalReferenceCode: (value: string) => void;
}

type TInitialValues = {
	externalReferenceCode: string;
};

export function ModalEditExternalReferenceCode({
	externalReferenceCode,
	helpMessage,
	observer,
	onClose,
	onExternalReferenceCodeChange,
	onGetEntity,
	saveURL,
	setExternalReferenceCode,
}: ModalEditExternalReferenceCodeProps) {
	const [error, setError] = useState<string>('');
	const initialValues: TInitialValues = {
		externalReferenceCode,
	};

	const onSubmit = async ({externalReferenceCode}: TInitialValues) => {
		try {
			const entity = await onGetEntity();

			await save(`${saveURL}`, {
				...entity,
				externalReferenceCode,
			});

			setExternalReferenceCode(externalReferenceCode);

			if (onExternalReferenceCodeChange) {
				onExternalReferenceCodeChange(externalReferenceCode);
			}

			onClose();
			openToast({
				message: Liferay.Language.get(
					'your-request-completed-successfully'
				),
			});
		}
		catch (error) {
			setError((error as Error).message);
		}
	};

	const validate = ({externalReferenceCode}: TInitialValues) => {
		const errors: FormError<TInitialValues> = {};

		if (externalReferenceCode === '') {
			errors.externalReferenceCode = Liferay.Language.get('required');
		}

		return errors;
	};

	const {errors, handleChange, handleSubmit, values} = useForm({
		initialValues,
		onSubmit,
		validate,
	});

	return (
		<ClayModal center observer={observer}>
			<ClayForm onSubmit={handleSubmit}>
				<ClayModal.Header>
					{Liferay.Util.sub(
						Liferay.Language.get('edit-x'),
						Liferay.Language.get('external-reference-code')
					)}
				</ClayModal.Header>

				<ClayModal.Body>
					{error && (
						<ClayAlert displayType="danger">{error}</ClayAlert>
					)}

					<Input
						error={errors.externalReferenceCode}
						feedbackMessage={helpMessage}
						id="externalReferenceCode"
						label={Liferay.Language.get('external-reference-code')}
						name="externalReferenceCode"
						onChange={handleChange}
						required
						value={values.externalReferenceCode}
					/>
				</ClayModal.Body>

				<ClayModal.Footer
					last={
						<ClayButton.Group key={1} spaced>
							<ClayButton
								displayType="secondary"
								onClick={() => onClose()}
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
	);
}
