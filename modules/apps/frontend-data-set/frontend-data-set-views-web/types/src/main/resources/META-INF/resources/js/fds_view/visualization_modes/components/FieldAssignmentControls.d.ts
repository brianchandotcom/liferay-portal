/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {IField} from '../../../utils/types';
interface IFieldAssignmentControlsProps {
	field?: IField;
	label: string;
	onClearSelection: () => void;
	openSelectFieldModal: () => void;
}
declare function FieldAssignmentControls({
	field,
	label,
	onClearSelection,
	openSelectFieldModal,
}: IFieldAssignmentControlsProps): JSX.Element;
export default FieldAssignmentControls;
