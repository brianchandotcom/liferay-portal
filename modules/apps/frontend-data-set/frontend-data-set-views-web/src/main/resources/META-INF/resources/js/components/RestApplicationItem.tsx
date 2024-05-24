/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import fuzzy from 'fuzzy';
import React from 'react';

import '../../css/FDSEntries.scss';
import {FUZZY_OPTIONS} from '../utils/constants';

interface IRESTApplicationItemProps {
	query: string;
	restApplication: string;
}

function RESTApplicationItem({
	query,
	restApplication,
}: IRESTApplicationItemProps) {
	const fuzzyMatch = fuzzy.match(query, restApplication, FUZZY_OPTIONS);

	return (
		<ClayLayout.ContentRow>
			{fuzzyMatch ? (
				<span
					dangerouslySetInnerHTML={{
						__html: fuzzyMatch.rendered,
					}}
				/>
			) : (
				<span>{restApplication}</span>
			)}
		</ClayLayout.ContentRow>
	);
}

export default RESTApplicationItem;
