/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {OBJECT_RELATIONSHIP} from '../../utils/constants';
import {IDataSetSectionProps} from '../DataSet';
import '../../../css/Actions.scss';
import {IOrderable} from '../../utils/types';
declare const SECTIONS: {
	CREATION_ACTIONS: string;
	EDIT_CREATION_ACTION: string;
	EDIT_ITEM_ACTION: string;
	ITEM_ACTIONS: string;
	NEW_CREATION_ACTION: string;
	NEW_ITEM_ACTION: string;
};
interface IAction extends IOrderable {
	[OBJECT_RELATIONSHIP.DATA_SET_CREATION_ACTION]?: any;
	[OBJECT_RELATIONSHIP.DATA_SET_ITEM_ACTION]?: any;
	actions: {
		delete: {
			href: string;
			method: string;
		};
	};
	confirmationMessage?: string;
	confirmationMessageType?: string;
	confirmationMessage_i18n?: {
		[key: string]: string;
	};
	errorMessage?: string;
	errorMessage_i18n?: {
		[key: string]: string;
	};
	icon: string;
	label: string;
	label_i18n: {
		[key: string]: string;
	};
	method?: string;
	modalSize?: string;
	permissionKey: string;
	successMessage?: string;
	successMessage_i18n?: {
		[key: string]: string;
	};
	title?: string;
	title_i18n?: {
		[key: string]: string;
	};
	type: string;
	url: string;
}
declare const Actions: ({
	dataSet,
	namespace,
	spritemap,
}: IDataSetSectionProps) => JSX.Element;
export {IAction, SECTIONS};
export default Actions;
