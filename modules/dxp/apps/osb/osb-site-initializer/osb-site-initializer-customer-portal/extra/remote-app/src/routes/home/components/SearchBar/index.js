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

import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {useEffect, useState} from 'react';
import i18n from '../../../../common/I18n';
import useDebounce from '../../../../common/hooks/useDebounce';

const SearchBar = ({onSearchSubmit}) => {
	const [term, setTerm] = useState('');
	const debouncedTerm = useDebounce(term, 500);

	useEffect(() => {
		if (debouncedTerm) {
			onSearchSubmit(debouncedTerm);
		}
	}, [debouncedTerm, onSearchSubmit]);

	return (
		<div className="position-relative">
			<ClayInput
				className="cp-search-project font-weight-semi-bold h5 rounded-pill shadow-lg"
				onChange={(event) => setTerm(event.target.value)}
				placeholder={i18n.translate('find-a-project')}
				type="text"
				value={term}
			/>

			<ClayIcon
				className="position-absolute text-brand-primary"
				symbol="search"
			/>
		</div>
	);
};

export default SearchBar;
