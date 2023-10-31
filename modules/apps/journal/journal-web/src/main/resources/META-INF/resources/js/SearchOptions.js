/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker} from '@clayui/core';
import DropDown from '@clayui/drop-down';
import ClayForm from '@clayui/form';
import ClayLayout from '@clayui/layout';
import {addParams, navigate} from 'frontend-js-web';
import React from 'react';

const SearchOptions = ({
	initialSearchIn,
	initialSearchLocation,
	initialSearchResults,
	portletNamespace,
	searchInOptions,
	searchLocationOptions,
	searchResultsOptions,
	searchURL,
}) => {
	const onSelectionChangeHandlder = (
		searchIn,
		searchLocation,
		searchResults
	) => {
		let url = addParams(
			`${portletNamespace}tab=${searchResults}`,
			searchURL
		);

		url = addParams(
			`${portletNamespace}searchLocation=${searchLocation}`,
			url
		);

		url = addParams(`${portletNamespace}searchIn=${searchIn}`, url);

		navigate(url);
	};

	return (
		<ClayLayout.Row>
			<ClayLayout.Col>
				<ClayForm.Group className="c-mr-2 d-inline-flex">
					<Picker
						id={`${portletNamespace}searchResults`}
						onSelectionChange={(key) =>
							onSelectionChangeHandlder(
								initialSearchIn,
								initialSearchLocation,
								key
							)
						}
						selectedKey={initialSearchResults}
					>
						<DropDown.Group
							header={Liferay.Language.get('results')}
							items={searchResultsOptions}
						>
							{(item) => (
								<Option key={item.value}>{item.label}</Option>
							)}
						</DropDown.Group>
					</Picker>
				</ClayForm.Group>

				{searchLocationOptions ? (
					<ClayForm.Group className="c-mr-2 d-inline-flex">
						<Picker
							id={`${portletNamespace}searchLocation`}
							onSelectionChange={(key) =>
								onSelectionChangeHandlder(
									initialSearchIn,
									key,
									initialSearchResults
								)
							}
							selectedKey={initialSearchLocation}
						>
							<DropDown.Group
								header={Liferay.Language.get('location')}
								items={searchLocationOptions}
							>
								{(item) => (
									<Option key={item.value}>
										{item.label}
									</Option>
								)}
							</DropDown.Group>
						</Picker>
					</ClayForm.Group>
				) : null}

				<ClayForm.Group className="d-inline-flex">
					<Picker
						id={`${portletNamespace}searchIn`}
						onSelectionChange={(key) =>
							onSelectionChangeHandlder(
								key,
								initialSearchLocation,
								initialSearchResults
							)
						}
						selectedKey={initialSearchIn}
					>
						<DropDown.Group
							header={Liferay.Language.get('search-in')}
							items={searchInOptions}
						>
							{(item) => (
								<Option key={item.value}>{item.label}</Option>
							)}
						</DropDown.Group>
					</Picker>
				</ClayForm.Group>
			</ClayLayout.Col>
		</ClayLayout.Row>
	);
};

export default SearchOptions;
