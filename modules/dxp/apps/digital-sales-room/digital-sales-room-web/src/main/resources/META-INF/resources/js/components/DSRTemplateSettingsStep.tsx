/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput} from '@clayui/form';
import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import React, {useCallback, useContext, useEffect} from 'react';

import '../../css/main.scss';

import ClayIcon from '@clayui/icon';

import {DSRContext} from './DSRTemplateInitializer';
import {TDSRContext, TDSRRoomDetailsStepProps} from './DSRTypes';
import FieldErrorMessage from './FieldErrorMessage';

function DSRTemplateSettingsStep({
	numberOfSteps,
	setHandleStepSubmit,
}: TDSRRoomDetailsStepProps) {
	const {dataContext, setDataContext} = useContext<TDSRContext>(DSRContext);

	const fieldValid = useCallback(
		(name: string, value: null | string | undefined): boolean => {
			if (['roomName'].includes(name)) {
				return !!value;
			}

			return true;
		},
		[]
	);

	const handleFieldChange = useCallback(
		({
			target: {name, value},
		}: {
			target: {
				name: string;
				value: string;
			};
		}) => {
			setDataContext((prevState) => ({
				...prevState,
				errors: {
					...prevState.errors,
					[name]: fieldValid(name, value)
						? ''
						: Liferay.Language.get('this-field-is-mandatory'),
				},
				[name]: value,
			}));
		},
		[setDataContext, fieldValid]
	);

	useEffect(() => {
		setHandleStepSubmit(() => async (event: Event): Promise<boolean> => {
			event.preventDefault();

			handleFieldChange({
				target: {name: 'roomName', value: dataContext.roomName},
			});
			setDataContext((prevState) => ({
				...prevState,
				errors: {
					...prevState.errors,
					banner: null,
					clientLogo: null,
				},
			}));

			if (
				!fieldValid('description', dataContext.description) ||
				!fieldValid('roomName', dataContext.roomName)
			) {
				return Promise.resolve(false);
			}

			return Promise.resolve(true);
		});
	}, [
		dataContext,
		fieldValid,
		handleFieldChange,
		setDataContext,
		setHandleStepSubmit,
	]);

	return (
		<>
			<div>
				<div className="mb-1 text-secondary" data-qa-id="stepLocator">
					{sub(Liferay.Language.get('step-x-of-x'), 1, numberOfSteps)}
				</div>

				<div
					className="mb-1 text-6 text-weight-bold"
					data-qa-id="stepTitle"
				>
					{Liferay.Language.get('template-settings')}
				</div>

				<div className="text-secondary">
					{Liferay.Language.get('create-a-new-template-from-scratch')}
				</div>
			</div>
			<div className="mt-4">
				<ClayForm.Group
					className={classNames({
						'has-error': !!dataContext.errors.roomName,
					})}
				>
					<label className="d-block" htmlFor="dsr-room-name">
						{Liferay.Language.get('name')}

						<span className="c-ml-2 reference-mark">
							<ClayIcon symbol="asterisk" />
						</span>
					</label>

					<ClayInput
						aria-label={Liferay.Language.get('name')}
						data-qa-id="roomNameInput"
						id="dsr-room-name"
						name="roomName"
						onChange={handleFieldChange}
						required={true}
						type="text"
						value={dataContext.roomName || ''}
					/>

					<FieldErrorMessage
						error={dataContext.errors.roomName}
						name="roomName"
					/>
				</ClayForm.Group>
			</div>
			<div>
				<ClayForm.Group
					className={classNames({
						'has-error': !!dataContext.errors.description,
					})}
				>
					<label className="d-block" htmlFor="dsr-description">
						{Liferay.Language.get('description')}
					</label>

					<ClayInput
						aria-label={Liferay.Language.get('description')}
						component="textarea"
						data-qa-id="descriptionInput"
						id="dsr-description"
						name="description"
						onChange={handleFieldChange}
						type="text"
						value={dataContext.description || ''}
					/>

					<FieldErrorMessage
						error={dataContext.errors.description}
						name="description"
					/>
				</ClayForm.Group>
			</div>
		</>
	);
}

export default DSRTemplateSettingsStep;
