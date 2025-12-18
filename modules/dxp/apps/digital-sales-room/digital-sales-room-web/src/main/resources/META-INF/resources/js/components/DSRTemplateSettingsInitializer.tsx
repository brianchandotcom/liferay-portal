/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {openToast} from 'frontend-js-components-web';
import React, {SetStateAction, useCallback, useEffect, useState} from 'react';

import DigitalSalesRoomService, {
	TDSRTemplatePayload,
} from '../commons/DigitalSalesRoomService';
import {getColor} from './DSRInitializer';
import {getImageURL} from './DSRRoomSelectTemplateStep';
import DSRTemplateDetailsStep from './DSRTemplateDetailsStep';
import {DSRContext} from './DSRTemplateInitializer';
import DSRTemplateSettingsStep from './DSRTemplateSettingsStep';
import {TDSRDataContext} from './DSRTypes';

const DEFAULT_DATA_CONTEXT: TDSRDataContext = {
	banner: {},
	clientLogo: {},
	clientName: '',
	errors: {},
	friendlyURL: '',
	primaryColor: '0B5FFF',
	roomName: '',
	secondaryColor: 'FFFFFF',
	share: {
		emailAddresses: [],
	},
};

const STEPS_DEFINITION = [
	{
		step: DSRTemplateSettingsStep,
		title: Liferay.Language.get('general'),
		type: 'general',
	},
	{
		step: DSRTemplateDetailsStep,
		title: Liferay.Language.get('look-and-feel'),
		type: 'lookAndFeel',
	},
];

function StepLoader({
	setHandleStepSubmit,
	step,
}: {
	setHandleStepSubmit: (
		callback: SetStateAction<(event: Event) => Promise<boolean>>
	) => void;
	step: string;
}) {
	const stepDefinition = STEPS_DEFINITION.find((item) => item.type === step);

	if (stepDefinition) {
		const Component = stepDefinition.step;

		return (
			<>
				<h3 className="mb-1 text-6 text-weight-bold">
					{stepDefinition.title}
				</h3>

				<Component
					numberOfSteps={1}
					setHandleStepSubmit={setHandleStepSubmit}
					showHeader={false}
					step={1}
				></Component>
			</>
		);
	}

	return <div></div>;
}

function DSRTemplateSettingsInitializer({
	cancelURL,
	digitalSalesRoomTemplateId,
	step,
}: {
	cancelURL: string;
	digitalSalesRoomTemplateId: number;
	step: string;
}) {
	const [dataContext, setDataContext] = useState(DEFAULT_DATA_CONTEXT);
	const [handleStepSubmit, setHandleStepSubmit] = useState(
		() =>
			async (event: Event): Promise<boolean> => {
				event.preventDefault();

				return false;
			}
	);
	const [loading, setLoading] = useState(false);

	const handleCancel = useCallback(() => {
		window.location.href = cancelURL;
	}, [cancelURL]);

	const handleSave = useCallback(
		async (event: any) => {
			setLoading(true);

			try {
				const stepValid = await handleStepSubmit(event);

				if (stepValid) {
					let payload = {};

					if (step === 'general') {
						payload = {
							description: dataContext.description,
							name: dataContext.roomName,
						};
					}
					else {
						payload = {
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
							primaryColor: getColor(dataContext.primaryColor),
							secondaryColor: getColor(
								dataContext.secondaryColor
							),
						};
					}

					await DigitalSalesRoomService.patchDigitalSalesRoomTemplate(
						digitalSalesRoomTemplateId,
						payload as TDSRTemplatePayload
					);

					openToast({
						message: Liferay.Language.get(
							'your-request-completed-successfully'
						),
						type: 'success',
					});
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
		[dataContext, digitalSalesRoomTemplateId, handleStepSubmit, step]
	);

	useEffect(() => {
		setLoading(true);

		DigitalSalesRoomService.getDigitalSalesRoomTemplate(
			digitalSalesRoomTemplateId
		)
			.then((template) => {
				setDataContext((prevState) => ({
					...prevState,
					banner: {
						base64: getImageURL(template.banner?.fileBase64 || ''),
					},
					clientLogo: {
						base64: getImageURL(
							template.clientLogo?.fileBase64 || ''
						),
					},
					description: template.description || '',
					errors: {},
					primaryColor: (template.primaryColor || '').replace(
						/^#/,
						''
					),
					roomName: template.name || '',
					secondaryColor: (template.secondaryColor || '').replace(
						/^#/,
						''
					),
				}));
			})
			.catch((error) => {
				openToast({
					message: (error as Error).message,
					type: 'danger',
				});
			})
			.finally(() => {
				setLoading(false);
			});
	}, [digitalSalesRoomTemplateId]);

	return (
		<DSRContext.Provider value={{dataContext, loading, setDataContext}}>
			<div className="p-3">
				<div>
					<StepLoader
						setHandleStepSubmit={setHandleStepSubmit}
						step={step}
					/>
				</div>

				<div className="d-flex justify-content-end">
					<ClayButton
						className="btn-secondary mr-3"
						data-testid="button-cancel"
						disabled={loading}
						onClick={handleCancel}
					>
						{Liferay.Language.get('cancel')}
					</ClayButton>

					<ClayButton
						data-testid="button-save"
						disabled={loading}
						onClick={handleSave}
					>
						{Liferay.Language.get('save')}
					</ClayButton>
				</div>
			</div>
		</DSRContext.Provider>
	);
}

export default DSRTemplateSettingsInitializer;
