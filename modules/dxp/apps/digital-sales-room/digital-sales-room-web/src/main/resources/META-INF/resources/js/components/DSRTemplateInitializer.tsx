/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';
import {openToast} from 'frontend-js-components-web';
import React, {SetStateAction, useCallback, useState} from 'react';

import DigitalSalesRoomService from '../commons/DigitalSalesRoomService';
import {getColor} from './DSRInitializer';
import DSRTemplateDetailsStep from './DSRTemplateDetailsStep';
import DSRTemplateSettingsStep from './DSRTemplateSettingsStep';
import {TDSRContext, TDSRDataContext, TDSRInitializerProps} from './DSRTypes';

const DEFAULT_DATA_CONTEXT: TDSRDataContext = {
	banner: {},
	clientLogo: {},
	clientName: '',
	description: '',
	errors: {},
	friendlyURL: '',
	primaryColor: '0B5FFF',
	roomName: '',
	secondaryColor: 'FFFFFF',
	share: {
		emailAddresses: [],
	},
};

export const DSRContext = React.createContext<TDSRContext>({
	dataContext: DEFAULT_DATA_CONTEXT,
	loading: false,
	setDataContext: () => {},
});

function StepLoader({
	numberOfSteps,
	setHandleStepSubmit,
	step,
}: {
	numberOfSteps: number;
	setHandleStepSubmit: (
		callback: SetStateAction<(event: Event) => Promise<boolean>>
	) => void;
	step: number;
}) {
	if (step === 1) {
		return (
			<DSRTemplateSettingsStep
				numberOfSteps={numberOfSteps}
				setHandleStepSubmit={setHandleStepSubmit}
			></DSRTemplateSettingsStep>
		);
	}
	else if (step === 2) {
		return (
			<DSRTemplateDetailsStep
				numberOfSteps={numberOfSteps}
				setHandleStepSubmit={setHandleStepSubmit}
			></DSRTemplateDetailsStep>
		);
	}

	return <div></div>;
}

function DSRTemplateInitializer({
	closeModal,
	numberOfSteps = 2,
}: TDSRInitializerProps) {
	const [dataContext, setDataContext] = useState(DEFAULT_DATA_CONTEXT);
	const [handleStepSubmit, setHandleStepSubmit] = useState(
		() =>
			async (event: Event): Promise<boolean> => {
				event.preventDefault();

				return false;
			}
	);
	const [loading, setLoading] = useState(false);
	const [step, setStep] = useState(1);

	const handleBack = useCallback(() => {
		setStep((prevState) => {
			return Math.max(prevState - 1, 1);
		});
	}, []);

	const handleCancel = useCallback(() => {
		closeModal();
	}, [closeModal]);

	const handleNext = useCallback(
		async (event: any) => {
			setLoading(true);

			try {
				const stepValid = await handleStepSubmit(event);

				if (stepValid) {
					setStep((prevState) => {
						return prevState + 1;
					});
				}
			}
			finally {
				setLoading(false);
			}
		},
		[handleStepSubmit]
	);

	const handleSave = useCallback(
		async (event: any) => {
			setLoading(true);

			try {
				const stepValid = await handleStepSubmit(event);

				if (stepValid) {
					const digitalSalesRoomTemplate =
						await DigitalSalesRoomService.postDigitalSalesRoomTemplate(
							{
								banner: {
									fileBase64:
										(dataContext.banner.base64 || '')
											.split(',')
											.pop() || '',
								},
								clientLogo: {
									fileBase64:
										(dataContext.clientLogo.base64 || '')
											.split(',')
											.pop() || '',
								},
								clientName: dataContext.clientName,
								description: dataContext.description,
								name: dataContext.roomName,
								primaryColor: getColor(
									dataContext.primaryColor
								),
								secondaryColor: getColor(
									dataContext.secondaryColor
								),
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
		},
		[dataContext, handleStepSubmit]
	);

	return (
		<DSRContext.Provider value={{dataContext, loading, setDataContext}}>
			<ClayModal.Header
				closeButtonAriaLabel={Liferay.Language.get('close')}
			>
				<>
					{Liferay.Language.get(
						'create-new-digital-sales-room-template'
					)}
				</>
			</ClayModal.Header>

			<ClayModal.Body>
				<StepLoader
					numberOfSteps={numberOfSteps}
					setHandleStepSubmit={setHandleStepSubmit}
					step={step}
				/>
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
						{step > 1 && (
							<ClayButton
								data-testid="button-back"
								disabled={loading}
								displayType="secondary"
								onClick={handleBack}
							>
								{Liferay.Language.get('back')}
							</ClayButton>
						)}

						{step < numberOfSteps && (
							<ClayButton
								data-testid="button-next"
								disabled={loading}
								onClick={handleNext}
							>
								{Liferay.Language.get('next')}
							</ClayButton>
						)}

						{step >= numberOfSteps && (
							<ClayButton
								data-testid="button-save"
								disabled={loading}
								onClick={handleSave}
							>
								{Liferay.Language.get('save')}
							</ClayButton>
						)}
					</ClayButton.Group>
				}
			/>
		</DSRContext.Provider>
	);
}

export default DSRTemplateInitializer;
