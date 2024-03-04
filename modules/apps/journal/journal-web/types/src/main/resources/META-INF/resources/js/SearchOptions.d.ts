/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option} from '@clayui/core';
import {Key} from 'react';
declare type Option = {
	label: string;
	value: string;
};
declare type Props = {
	portletNamespace: string;
	searchIn: Key;
	searchInCommentsURL: string;
	searchInOptions: Option[];
	searchLocation: Key;
	searchLocationOptions: Option[];
	searchResults: Key;
	searchURL: string;
};
declare const SearchOptions: ({
	portletNamespace: namespace,
	searchIn: initialSearchIn,
	searchInCommentsURL,
	searchInOptions,
	searchLocation: initialLocation,
	searchLocationOptions,
	searchResults: initialResults,
	searchURL,
}: Props) => JSX.Element;
export default SearchOptions;
