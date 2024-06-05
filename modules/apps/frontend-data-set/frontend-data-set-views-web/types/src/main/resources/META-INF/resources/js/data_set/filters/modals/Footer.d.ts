/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

export interface IFilterModalFooterProps {
	closeModal: Function;
	onSave: Function;
	saveButtonDisabled: boolean;
}
declare function FilterModalFooter({
	closeModal,
	onSave,
	saveButtonDisabled,
}: IFilterModalFooterProps): JSX.Element;
export default FilterModalFooter;
