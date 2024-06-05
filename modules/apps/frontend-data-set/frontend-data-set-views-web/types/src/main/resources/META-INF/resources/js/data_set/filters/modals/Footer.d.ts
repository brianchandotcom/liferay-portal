/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

export interface IFooterProps {
	closeModal: Function;
	onSave: Function;
	saveButtonDisabled: boolean;
}
declare function Footer({
	closeModal,
	onSave,
	saveButtonDisabled,
}: IFooterProps): JSX.Element;
export default Footer;
