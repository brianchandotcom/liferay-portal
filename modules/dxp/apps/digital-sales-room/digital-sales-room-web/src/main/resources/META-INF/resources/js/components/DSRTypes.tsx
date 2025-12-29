/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {SetStateAction} from 'react';

export type TDSRDataContext = {
	accountId?: number;
	accountName?: string;
	banner: {
		base64?: string;
		name?: string;
		size?: number;
	};
	channelId?: number;
	channelName?: string;
	clientLogo: {
		base64?: string;
	};
	clientName: string;
	description?: string;
	errors: {
		accountId?: null | string;
		banner?: null | string;
		channelId?: null | string;
		channelName?: null | string;
		clientLogo?: null | string;
		clientName?: null | string;
		description?: null | string;
		friendlyURL?: null | string;
		primaryColor?: null | string;
		roomName?: null | string;
		secondaryColor?: null | string;
		share?: null | string;
	};
	friendlyURL: string;
	primaryColor: string;
	roomName: string;
	secondaryColor: string;
	share?: {
		emailAddresses: Array<string>;
		roleKey?: string;
	};
};

export type TDSRContext = {
	dataContext: TDSRDataContext;
	setDataContext: React.Dispatch<React.SetStateAction<TDSRDataContext>>;
};

export type TDSRRoomDetailsStepProps = {
	setHandleStepSubmit(
		callback: SetStateAction<(event: Event) => Promise<boolean>>
	): void;
	numberOfSteps: number;
};

export type TDSRInitializerProps = {
	closeModal: () => void;
	numberOfSteps: number;
};

export type TDSRRoomSaveAsTemplateProps = {
	closeModal: () => void;
	digitalSalesRoomId: number;
};
