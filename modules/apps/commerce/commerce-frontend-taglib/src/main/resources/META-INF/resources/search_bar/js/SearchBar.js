/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import classNames from 'classnames';
import React, {useCallback, useEffect, useState} from 'react';

export default function SearchBar() {
	const [active, setActive] = useState(false);
	const [query, setQuery] = useState('');

	useEffect(() => {
		const toggles = Array.from(
			document.querySelectorAll('.js-toggle-search')
		);

		const onToggleClick = () => setActive(!active);

		for (const toggle of toggles) {
			toggle.addEventListener('click', onToggleClick);
		}

		return () => {
			for (const toggle of toggles) {
				toggle.removeEventListener('keydown', onToggleClick);
			}
		};
	}, [active, setActive]);

	useEffect(() => {
		Liferay.fire('search-bar-toggled', {active});
	}, [active]);

	useEffect(() => {
		Liferay.fire('search-term-update', {
			term: query,
		});
	}, [query]);

	const onSubmit = useCallback(
		(event) => {
			event.preventDefault();

			Liferay.fire('search-term-submit', {
				term: query,
			});
		},
		[query]
	);

	return (
		<form className="commerce-search" onSubmit={onSubmit}>
			<div className="commerce-search__input">
				<ClayInput
					autoComplete="off"
					onChange={(event) => {
						setQuery(event.target.value);
					}}
					placeholder={Liferay.Language.get('search')}
					value={query}
				/>
			</div>

			<ClayButtonWithIcon
				aria-label={Liferay.Language.get('clear-search')}
				className={classNames('commerce-search__button', {
					'is-ninja': !query.length,
				})}
				displayType="unstyled"
				onClick={() => setQuery('')}
				symbol="times"
			/>
		</form>
	);
}
