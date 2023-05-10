/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {useState} from 'react';
import i18n from '../../../../common/I18n';
import Skeleton from '../../../../common/components/Skeleton';
import SearchBar from './components/SearchBar/SearchBar';

const SearchHeader = ({count, loading, onSearchSubmit}) => {
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
