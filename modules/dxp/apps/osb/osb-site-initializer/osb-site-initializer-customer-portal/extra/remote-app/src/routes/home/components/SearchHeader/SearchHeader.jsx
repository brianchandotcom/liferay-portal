/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useState} from 'react';
import i18n from '../../../../common/I18n';
import Skeleton from '../../../../common/components/Skeleton';
import SearchBar from './components/SearchBar/SearchBar';

const SearchHeader = ({count, loading, onSearchSubmit, search}) => {
	const [hasTerm, setHasTerm] = useState(false);

	const getCounter = () => {
		return `${count} ${
			hasTerm
				? i18n.pluralize(count, 'result')
				: i18n.pluralize(count, 'project')
		}`;
	};

	return (
		<div className="align-items-center d-flex justify-content-between mb-4 pb-2">
			<SearchBar
				onSearchSubmit={(term) => {
					setHasTerm(!!term);
					onSearchSubmit(term);
				}}
				search={search}
			/>

			{loading ? (
				<Skeleton height={22} width={85} />
			) : (
				<h5 className="m-0 text-neutral-7">{getCounter()}</h5>
			)}
		</div>
	);
};

export default SearchHeader;
