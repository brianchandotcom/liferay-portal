/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import './ModalAddColumns.scss';
declare function ModalAddColumns<T extends ModalItem>(): JSX.Element | null;
export default ModalAddColumns;
interface ModalItem {
	checked?: boolean;
	id?: unknown;
	label: LocalizedValue<string>;
	required?: boolean;
}
