/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {IClientExtensionRenderer} from '@liferay/frontend-data-set-web';
import {IField, IFilter} from '../../../utils/types';
declare function Header(): JSX.Element;
interface IBodyProps {
	closeModal: Function;
	fdsFilterClientExtensions: IClientExtensionRenderer[];
	fieldNames?: string[];
	fields: IField[];
	filter?: IFilter;
	handleSave: Function;
	namespace: string;
}
declare function Body({
	closeModal,
	fdsFilterClientExtensions,
	fieldNames,
	fields,
	filter,
	handleSave,
	namespace,
}: IBodyProps): JSX.Element;
declare const _default: {
	Body: typeof Body;
	Header: typeof Header;
};
export default _default;
