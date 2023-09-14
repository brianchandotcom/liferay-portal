/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {FDSViewType} from '../FDSViews';
interface IFDSActionCreationFormInterface {
	fdsView: FDSViewType;
	getFDSActions: () => void;
	namespace: string;
	sections: {
		ACTIONS: string;
		NEW_ITEM_ACTION: string;
	};
	setActiveSection: (arg: string) => void;
	spritemap: string;
}
declare const ActionCreationForm: ({
	fdsView,
	getFDSActions,
	namespace,
	sections,
	setActiveSection,
	spritemap,
}: IFDSActionCreationFormInterface) => JSX.Element;
export default ActionCreationForm;
