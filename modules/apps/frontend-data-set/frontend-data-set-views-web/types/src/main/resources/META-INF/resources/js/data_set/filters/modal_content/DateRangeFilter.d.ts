/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {IFilter} from '../../../utils/types';
declare function Header(): JSX.Element;
interface IBodyProps {
	filter?: IFilter;
	namespace: string;
	onChange: Function;
	onValidation: Function;
}
declare function Body({
	filter,
	namespace,
	onChange,
	onValidation,
}: IBodyProps): JSX.Element;
declare const _default: {
	Body: typeof Body;
	Header: typeof Header;
};
export default _default;
