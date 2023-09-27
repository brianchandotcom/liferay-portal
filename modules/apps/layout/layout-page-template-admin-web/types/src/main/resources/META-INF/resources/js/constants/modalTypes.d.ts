/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export declare const MODAL_TYPES: {
	readonly create: 'create';
	readonly edit: 'edit';
};
export declare type ModalType = typeof MODAL_TYPES[keyof typeof MODAL_TYPES];
