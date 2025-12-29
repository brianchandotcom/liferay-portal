/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayModal from '@clayui/modal';
import classNames from 'classnames';
import {openToast} from 'frontend-js-components-web';
import React, {useCallback, useState} from 'react';

import DigitalSalesRoomService from '../commons/DigitalSalesRoomService';
import {TDSRDataContext, TDSRRoomSaveAsTemplateProps} from './DSRTypes';
import FieldErrorMessage from './FieldErrorMessage';

const DEFAULT_DATA_CONTEXT: Partial<TDSRDataContext> = {
	description: '',
	errors: {},
	roomName: '',
};

type TDSRPartialContext = {
	dataContext: Partial<TDSRDataContext>;
	setDataContext: React.Dispatch<
		React.SetStateAction<Partial<TDSRDataContext>>
	>;
};

export const DSRContext = React.createContext<TDSRPartialContext>({
	dataContext: DEFAULT_DATA_CONTEXT,
	setDataContext: () => {},
});

function DSRRoomSaveAsTemplate({
	closeModal,
	digitalSalesRoomId,
}: TDSRRoomSaveAsTemplateProps) {
	const [dataContext, setDataContext] = useState(DEFAULT_DATA_CONTEXT);
	const [loading, setLoading] = useState(false);

	const fieldValid = useCallback(
		(name: string, value: null | string | undefined): boolean => {
			if (['roomName'].includes(name)) {
				return !!value;
			}

			return true;
		},
		[]
	);

	const handleCancel = useCallback(() => {
		closeModal();
	}, [closeModal]);

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

	const handleSave = useCallback(async () => {
		setLoading(true);

		try {
			handleFieldChange({
				target: {
					name: 'roomName',
					value: dataContext.roomName || '',
				},
			});

			if (fieldValid('roomName', dataContext.roomName)) {
				const digitalSalesRoomTemplate =
					await DigitalSalesRoomService.postDigitalSalesRoomDigitalSalesRoomTemplate(
						digitalSalesRoomId,
						{
							description: dataContext.description,
							name: dataContext.roomName,
						}
					);

				window.location.href = `/web${digitalSalesRoomTemplate.friendlyUrlPath}?p_l_back_url=/web${digitalSalesRoomTemplate.friendlyUrlPath}&p_l_mode=edit`;
			}
		}
		catch (error) {
			openToast({
				message: (error as Error).message,
				type: 'danger',
			});
		}
		finally {
			setLoading(false);
		}
	}, [dataContext, digitalSalesRoomId, fieldValid, handleFieldChange]);

	return (
		<DSRContext.Provider value={{dataContext, setDataContext}}>
			<ClayModal.Header
				closeButtonAriaLabel={Liferay.Language.get('close')}
			>
				<>{Liferay.Language.get('save-as-template')}</>
			</ClayModal.Header>

			<ClayModal.Body>
				<>
					<div>
						<div className="text-secondary">
							{Liferay.Language.get(
								'this-action-creates-a-reusable-template-based-on-the-current-room-s-layout-and-settings'
							)}
						</div>
					</div>
					<div className="mt-4">
						<ClayForm.Group
							className={classNames({
								'has-error': !!dataContext.errors?.roomName,
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
								error={dataContext.errors?.roomName}
								name="roomName"
							/>
						</ClayForm.Group>
					</div>
					<div>
						<ClayForm.Group
							className={classNames({
								'has-error': !!dataContext.errors?.description,
							})}
						>
							<label
								className="d-block"
								htmlFor="dsr-description"
							>
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
								error={dataContext.errors?.description}
								name="description"
							/>
						</ClayForm.Group>
					</div>
				</>
			</ClayModal.Body>

			<ClayModal.Footer
				first={
					<ClayButton
						className="text-secondary"
						data-testid="button-cancel"
						disabled={loading}
						displayType="link"
						onClick={handleCancel}
					>
						{Liferay.Language.get('cancel')}
					</ClayButton>
				}
				last={
					<ClayButton.Group spaced>
						<ClayButton
							data-testid="button-save"
							disabled={loading}
							onClick={handleSave}
						>
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</DSRContext.Provider>
	);
}

export default DSRRoomSaveAsTemplate;
