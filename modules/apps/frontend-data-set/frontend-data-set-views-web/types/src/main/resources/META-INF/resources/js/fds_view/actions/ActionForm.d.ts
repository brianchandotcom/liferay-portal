/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {FDSViewType} from '../../FDSViews';
import {IFDSAction} from '../Actions';
interface IFDSActionFormProps {
	activeTab: number;
	editing?: boolean;
	fdsView: FDSViewType;
	initialValues?: IFDSAction;
	namespace: string;
	onCancel: () => void;
	onSave: () => void;
	spritemap: string;
}
declare const ActionForm: ({
	activeTab,
	editing,
	fdsView,
	initialValues,
	namespace,
	onCancel,
	onSave,
	spritemap,
}: IFDSActionFormProps) => JSX.Element;
export default ActionForm;
