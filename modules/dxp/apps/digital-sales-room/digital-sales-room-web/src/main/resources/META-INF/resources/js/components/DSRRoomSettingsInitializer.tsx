/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {openToast} from 'frontend-js-components-web';
import React, {SetStateAction, useCallback, useEffect, useState} from 'react';

import DigitalSalesRoomService, {
	TDSRPayload,
} from '../commons/DigitalSalesRoomService';
import {DSRContext, getColor, getFriendlyURL} from './DSRInitializer';
import DSRRoomDetailsStep from './DSRRoomDetailsStep';
import {getImageURL} from './DSRRoomSelectTemplateStep';
import DSRRoomSettingsStep from './DSRRoomSettingsStep';
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
		step: DSRRoomDetailsStep,
		title: Liferay.Language.get('general'),
		type: 'general',
	},
	{
		step: DSRRoomSettingsStep,
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

function DSRRoomSettingsInitializer({
	cancelURL,
	digitalSalesRoomId,
	step,
}: {
	cancelURL: string;
	digitalSalesRoomId: number;
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
							friendlyUrlPath: getFriendlyURL(
								dataContext.friendlyURL
							),
							name: dataContext.roomName,
							primaryColor: getColor(dataContext.primaryColor),
							secondaryColor: getColor(
								dataContext.secondaryColor
							),
						};
					}
					else {
						payload = {
							accountId: dataContext.accountId,
							channelId: dataContext.channelId,
							channelName: dataContext.channelName,
						};
					}

					await DigitalSalesRoomService.patchDigitalSalesRoom(
						digitalSalesRoomId,
						payload as TDSRPayload
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
		[dataContext, digitalSalesRoomId, handleStepSubmit, step]
	);

	useEffect(() => {
		setLoading(true);

		DigitalSalesRoomService.getDigitalSalesRoom(digitalSalesRoomId)
			.then((room) => {
				setDataContext((prevState) => ({
					...prevState,
					accountId: room.accountId || 0,
					banner: {
						base64: getImageURL(room.banner?.fileBase64 || ''),
					},
					channelId: room.channelId || 0,
					channelName: room.channelName || '',
					clientLogo: {
						base64: getImageURL(room.clientLogo?.fileBase64 || ''),
					},
					clientName: room.clientName || '',
					description: room.description || '',
					errors: {},
					friendlyURL: (room.friendlyUrlPath || '').replace(
						/^\//,
						''
					),
					primaryColor: (room.primaryColor || '').replace(/^#/, ''),
					roomName: room.name || '',
					secondaryColor: (room.secondaryColor || '').replace(
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
	}, [digitalSalesRoomId]);

	return (
		<DSRContext.Provider value={{dataContext, setDataContext}}>
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

export default DSRRoomSettingsInitializer;
