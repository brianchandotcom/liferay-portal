/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';
import React, {SetStateAction, useCallback, useState} from 'react';

import DSRRoomDetailsStep from './DSRRoomDetailsStep';
import {TDSRContext, TDSRDataContext} from './DSRTypes';

type TProps = {
	closeModal: () => void;
	numberOfSteps: number;
};

const DEFAULT_DATA_CONTEXT: TDSRDataContext = {
	banner: {},
	clientLogo: {},
	clientName: '',
	errors: {},
	friendlyURL: '',
	primaryColor: '0B5FFF',
	roomName: '',
	secondaryColor: 'FFFFFF',
};

export const DSRContext = React.createContext<TDSRContext>({
	dataContext: DEFAULT_DATA_CONTEXT,
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
			<DSRRoomDetailsStep
				numberOfSteps={numberOfSteps}
				setHandleStepSubmit={setHandleStepSubmit}
			></DSRRoomDetailsStep>
		);
	}

	return <div></div>;
}

function DSRInitializer({closeModal, numberOfSteps = 3}: TProps) {
	const [dataContext, setDataContext] = useState(DEFAULT_DATA_CONTEXT);
	const [handleStepSubmit, setHandleStepSubmit] = useState(
		() =>
			async (event: Event): Promise<boolean> => {
				event.preventDefault();

				return false;
			}
	);
	const [loading] = useState(false);
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
			const stepValid = await handleStepSubmit(event);

			if (stepValid) {
				setStep((prevState) => {
					return prevState + 1;
				});
			}
		},
		[handleStepSubmit]
	);

	const handleSave = useCallback(
		async (event: any) => {
			const stepValid = await handleStepSubmit(event);

			if (stepValid) {
				closeModal();
			}
		},
		[closeModal, handleStepSubmit]
	);

	return (
		<DSRContext.Provider value={{dataContext, setDataContext}}>
			<ClayModal.Header
				closeButtonAriaLabel={Liferay.Language.get('close')}
			>
				<>{Liferay.Language.get('create-new-digital-sales-room')}</>
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

export default DSRInitializer;
