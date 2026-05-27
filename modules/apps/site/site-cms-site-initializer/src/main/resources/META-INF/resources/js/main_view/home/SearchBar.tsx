/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import {serializeFDSConfig} from '@liferay/frontend-data-set-web';
import React, {ChangeEvent, useState} from 'react';

import '../../../css/home/SearchBar.scss';

export default function SearchBar({
	searchResultsURL,
	userFirstName,
}: {
	searchResultsURL: string;
	userFirstName: string;
}) {
	const [term, setTerm] = useState('');

	const handleOnChange = (event: ChangeEvent<HTMLInputElement>): void => {
		setTerm(event.target.value);
	};

	const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
		event.preventDefault();

		window.location.href =
			searchResultsURL +
			'?com.liferay.site.cms.site.initializer-allSection_fdsConfig=' +
			serializeFDSConfig({q: term});
	};

	return (
		<div className="align-items-center d-flex flex-column home-section p-2 p-sm-3">
			<div aria-level={2} className="h1" role="heading">
				Welcome, {userFirstName}!
			</div>

			<div className="container mt-5">
				<div className="justify-content-center row">
					<div className="col-10 position-relative">
						<form onSubmit={handleSubmit}>
							<ClayInput
								className="search-bar"
								id="search"
								onChange={handleOnChange}
								placeholder={Liferay.Language.get('search')}
								type="text"
							/>

							<ClayButtonWithIcon
								aria-label={Liferay.Language.get('search')}
								className="position-absolute search-button"
								displayType="unstyled"
								monospaced
								symbol="search"
								title={Liferay.Language.get('search-for')}
								type="submit"
							/>
						</form>
					</div>
				</div>
			</div>
		</div>
	);
}
