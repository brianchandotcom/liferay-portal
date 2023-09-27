/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {OBJECT_RELATIONSHIP} from '../Constants';
import {IFDSViewSectionProps} from '../FDSView';
interface IFDSAction {
	[OBJECT_RELATIONSHIP.FDS_VIEW_FDS_ACTION]: any;
	actions: {
		delete: {
			href: string;
			method: string;
		};
	};
	confirmationMessage: string;
	confirmationMessageType: string;
	confirmationMessage_i18n: {
		[key: string]: string;
	};
	icon: string;
	id: number;
	label: string;
	label_i18n: {
		[key: string]: string;
	};
	permissionKey: string;
	type: string;
	url: string;
}
declare const Actions: ({
	fdsView,
	namespace,
	spritemap,
}: IFDSViewSectionProps) => JSX.Element;
export {IFDSAction};
export default Actions;
