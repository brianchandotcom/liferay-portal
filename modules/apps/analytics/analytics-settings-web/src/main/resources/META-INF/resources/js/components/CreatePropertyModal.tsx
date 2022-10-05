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
import ClayModal, {useModal} from '@clayui/modal';
import React, {useState} from 'react';

import {ESteps} from '../pages/wizard/WizardPage';
import {fetchConnectionModal} from '../utils/api';

interface IModalProps {
	observer: any;
}

const CreatePropertyModal: React.FC<IModalProps> = ({observer}) => {
	const {onClose, onOpenChange} = useModal();
	const [propertyName, setPropertyName] = useState('');
	const [submitting, setSubmitting] = useState(false);

	const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
		event.preventDefault();

		const request = async () => {
			setSubmitting(true);

			const result = await fetchConnectionModal(propertyName);
			setSubmitting(false);

			if (result) {
				observer(ESteps.Property);

				Liferay.Util.openToast({
					message: Liferay.Language.get('saved-successfully'),
				});

				onOpenChange(false);
			} else {
				Liferay.Util.openToast({
					message: Liferay.Language.get(
						'an-unexpected-system-error-occurred'
					),
					type: 'danger',
				});
			}
		};

		request();
	};

	return (
		<ClayForm onSubmit={handleSubmit}>
			<ClayModal center observer={observer}>
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
								onClick={() => onClose()}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton
								disabled={!propertyName || submitting}
								type="submit"
							>
								{submitting && (
									<span className="inline-item inline-item-before">
										<span
											aria-hidden="true"
											className="loading-animation"
										></span>
									</span>
								)}

								{Liferay.Language.get('create')}
							</ClayButton>
						</ClayButton.Group>
					}
				/>
			</ClayModal>
		</ClayForm>
	);
};

export default CreatePropertyModal;
