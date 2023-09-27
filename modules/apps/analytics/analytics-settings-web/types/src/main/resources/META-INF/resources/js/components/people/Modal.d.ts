/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {TEmptyState} from '../table/StateRenderer';
import {TColumn, TTableRequestParams} from '../table/types';
import {EPeople} from './People';
export interface ICommonModalProps {
	observer: any;
	onCloseModal: () => void;
	syncAllAccounts: boolean;
	syncAllContacts: boolean;
	syncedIds: {
		[key in EPeople]: string[];
	};
}
interface IModalProps {
	columns: TColumn[];
	emptyState: TEmptyState;
	name: EPeople;
	observer: any;
	onCloseModal: () => void;
	requestFn: (params: TTableRequestParams) => Promise<any>;
	syncAllAccounts: boolean;
	syncAllContacts: boolean;
	syncedIds: {
		[key in EPeople]: string[];
	};
	title: string;
}
declare const Modal: React.FC<IModalProps>;
export default Modal;
