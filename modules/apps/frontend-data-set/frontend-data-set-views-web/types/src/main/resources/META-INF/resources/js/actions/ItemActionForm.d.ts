/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {FDSViewType} from '../FDSViews';
interface IFDSItemActionFormProps {
	fdsView: FDSViewType;
	loadFDSActions: () => void;
	namespace: string;
	sections: {
		ACTIONS: string;
		NEW_ITEM_ACTION: string;
	};
	setActiveSection: (arg: string) => void;
	spritemap: string;
}
declare const ItemActionForm: ({
	fdsView,
	loadFDSActions,
	namespace,
	sections,
	setActiveSection,
	spritemap,
}: IFDSItemActionFormProps) => JSX.Element;
export default ItemActionForm;
