/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {IClientExtensionRenderer} from 'frontend-js-web';
declare function Header(): JSX.Element;
interface IBodyProps {
	fdsFilterClientExtensions: IClientExtensionRenderer[];
	namespace: string;
	onSelectedClientExtensionChange: (val: IClientExtensionRenderer) => void;
	selectedClientExtension?: IClientExtensionRenderer;
}
declare function Body({
	fdsFilterClientExtensions,
	namespace,
	onSelectedClientExtensionChange,
	selectedClientExtension,
}: IBodyProps): JSX.Element;
declare const _default: {
	Body: typeof Body;
	Header: typeof Header;
};
export default _default;
