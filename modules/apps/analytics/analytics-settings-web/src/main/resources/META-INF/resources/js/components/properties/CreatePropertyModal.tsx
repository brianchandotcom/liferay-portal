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
import ClayForm, {ClayInput} from '@clayui/form';
import ClayModal from '@clayui/modal';
import React, {useState} from 'react';

import {createProperty} from '../../utils/api';
import {SUCCESS_MESSAGE} from '../../utils/constants';
import Loading from '../Loading';

interface IModalProps {
	observer: any;
	onCloseModal: (updateProperty: boolean) => void;
}

const CreatePropertyModal: React.FC<IModalProps> = ({
	observer,
	onCloseModal,
}) => {
	const [propertyName, setPropertyName] = useState('');
	const [submitting, setSubmitting] = useState(false);

	const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
		event.preventDefault();

		const request = async () => {
			setSubmitting(true);

			const {ok} = await createProperty(propertyName);

			setSubmitting(false);

			if (ok) {
				Liferay.Util.openToast({
					message: SUCCESS_MESSAGE,
				});

				onCloseModal(true);
			}
		};

		request();
	};

	return (
		<ClayModal center observer={observer}>
			<ClayForm onSubmit={handleSubmit}>
				<ClayModal.Header>
					{Liferay.Language.get('new-property')}
				</ClayModal.Header>

				<ClayModal.Body className="pb-0 pt-3">
					<ClayForm.Group className="mb-3">
						<label htmlFor="basicInputText">
							{Liferay.Language.get('property-name')}
						</label>

						<ClayInput
							id="inputPropertyName"
							onChange={({target: {value}}) =>
								setPropertyName(value)
							}
							type="text"
							value={propertyName}
						/>
					</ClayForm.Group>
				</ClayModal.Body>

				<ClayModal.Footer
					last={
						<ClayButton.Group spaced>
							<ClayButton
								displayType="secondary"
								onClick={() => onCloseModal(false)}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton
								disabled={!propertyName || submitting}
								type="submit"
							>
								{submitting && <Loading inline />}

								{Liferay.Language.get('create')}
							</ClayButton>
						</ClayButton.Group>
					}
				/>
			</ClayForm>
		</ClayModal>
	);
};

export default CreatePropertyModal;
