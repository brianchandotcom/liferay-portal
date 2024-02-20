/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface Props {
	buttonLabel: string;
	descriptionInputValue: string;
	formSubmitURL: string;
	heading: string;
	nameInputValue: string;
	onCloseModal: () => void;
	portletNamespace: string;
}
declare function CreationModal({
	buttonLabel,
	descriptionInputValue: initialDescriptionInputValue,
	formSubmitURL,
	heading,
	nameInputValue: initialNameInputValue,
	onCloseModal,
	portletNamespace,
}: Props): JSX.Element;
export default CreationModal;
