/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

export declare function EditClientOAuth2ModalContent({
	alertText,
	baseResourceURL,
	closeModal,
	handleSetInputValue,
	id,
	initialValue,
	isSecret,
	label,
	title,
	tooltip,
}: {
	alertText: string;
	baseResourceURL: string;
	closeModal: () => void;
	handleSetInputValue: (newInputValue: string) => void;
	id: string;
	initialValue: string;
	isSecret: boolean;
	label: string;
	title: string;
	tooltip: string;
}): JSX.Element;
