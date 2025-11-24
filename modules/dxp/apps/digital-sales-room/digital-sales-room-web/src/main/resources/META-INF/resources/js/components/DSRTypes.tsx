/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {SetStateAction} from 'react';

export type TDSRDataContext = {
	banner: {
		base64?: string;
		name?: string;
		size?: number;
	};
	clientLogo: {
		base64?: string;
	};
	clientName: string;
	errors: {
		banner?: null | string;
		clientLogo?: null | string;
		clientName?: null | string;
		friendlyURL?: null | string;
		primaryColor?: null | string;
		roomName?: null | string;
		secondaryColor?: null | string;
	};
	friendlyURL: string;
	primaryColor: string;
	roomName: string;
	secondaryColor: string;
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
