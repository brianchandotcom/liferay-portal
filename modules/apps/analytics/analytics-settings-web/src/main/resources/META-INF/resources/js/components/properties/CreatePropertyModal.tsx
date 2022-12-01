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
import ClayIcon from '@clayui/icon';
import ClayModal from '@clayui/modal';
import getCN from 'classnames';
import React, {useState} from 'react';

import {createProperty} from '../../utils/api';
import {MAX_LENGTH, MIN_LENGTH} from '../../utils/constants';
import Loading from '../Loading';

interface IModalProps {
	observer: any;
	onCancel: () => void;
	onSubmit: () => void;
}

const CreatePropertyModal: React.FC<IModalProps> = ({
	observer,
	onCancel,
	onSubmit,
}) => {
	const [propertyName, setPropertyName] = useState('');
	const [submitting, setSubmitting] = useState(false);

	const isValid = propertyName.length >= MIN_LENGTH;

	return (
		<ClayModal center observer={observer}>
			<ClayForm>
				<ClayModal.Header>
					{Liferay.Language.get('new-property')}
				</ClayModal.Header>

				<ClayModal.Body className="pb-0 pt-3">
					<ClayForm.Group
						className={getCN(
							{
								'has-error': propertyName && !isValid,
							},
							'mb-3'
						)}
					>
						<label htmlFor="basicInputText">
							{Liferay.Language.get('property-name')}
						</label>

						<ClayInput
							id="inputPropertyName"
							maxLength={MAX_LENGTH}
							onChange={({target: {value}}) =>
								setPropertyName(value)
							}
							type="text"
							value={propertyName}
						/>

						{propertyName && !isValid && (
							<ClayForm.FeedbackGroup>
								<ClayForm.FeedbackItem>
									<ClayIcon
										className="mr-1"
										symbol="warning-full"
									/>

									<span>
										{Liferay.Language.get(
											'property-name-does-not-meet-minimum-length-required'
										)}
									</span>
								</ClayForm.FeedbackItem>
							</ClayForm.FeedbackGroup>
						)}
					</ClayForm.Group>
				</ClayModal.Body>

				<ClayModal.Footer
					last={
						<ClayButton.Group spaced>
							<ClayButton
								displayType="secondary"
								onClick={onCancel}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton
								disabled={
									!propertyName || submitting || !isValid
								}
								onClick={async () => {
									setSubmitting(true);

									const {ok} = await createProperty(
										propertyName
									);

									setSubmitting(false);

									ok && onSubmit();
								}}
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
