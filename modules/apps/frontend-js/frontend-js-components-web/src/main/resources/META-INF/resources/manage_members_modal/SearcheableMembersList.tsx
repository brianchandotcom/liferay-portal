/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import {debounce} from 'frontend-js-web';
import React, {useMemo, useState} from 'react';

import {MembersSelectOptions} from './MembersSelectOptions';
import {MemberType} from './types';

interface SearcheableMembersListProps {
	className?: string;
	onSearch: (value: string) => void;
	onSelectChange: (value: MemberType) => void;
	selectValue: MemberType;
}

export function SearcheableMembersList({
	className,
	onSearch,
	onSelectChange,
	selectValue,
}: SearcheableMembersListProps) {
	const [search, setSearch] = useState('');

	const debouncedOnSearch = useMemo(
		() => debounce(onSearch, 400),
		[onSearch]
	);

	return (
		<MembersSelectOptions
			className={className}
			onSelectChange={onSelectChange}
			selectValue={selectValue}
		>
			<ClayInput
				aria-label={Liferay.Language.get('search-for-name-or-email')}
				className="form-control input-group-inset input-group-inset-after"
				name="search"
				onChange={(event) => {
					setSearch(event.target.value);

					debouncedOnSearch(event.target.value);
				}}
				placeholder={Liferay.Language.get('search-for-name-or-email')}
				type="text"
				value={search}
			/>
		</MembersSelectOptions>
	);
}
