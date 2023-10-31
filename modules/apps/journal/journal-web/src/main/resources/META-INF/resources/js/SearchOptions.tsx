/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker} from '@clayui/core';
import DropDown from '@clayui/drop-down';
import ClayForm from '@clayui/form';
import ClayLayout from '@clayui/layout';
import {addParams, navigate} from 'frontend-js-web';
import React, {Key} from 'react';

type Option = {
	label: string;
	value: string;
};

type Props = {
	portletNamespace: string;
	searchIn: Key;
	searchInOptions: Option[];
	searchLocation: Key;
	searchLocationOptions: Option[];
	searchResults: Key;
	searchResultsOptions: Option[];
	searchURL: string;
};

const SearchOptions = ({
	portletNamespace: namespace,
	searchIn: initialSearchIn,
	searchInOptions,
	searchLocation: initialLocation,
	searchLocationOptions,
	searchResults: initialResults,
	searchResultsOptions,
	searchURL,
}: Props) => {
	const onChange = ({
		location,
		results,
		searchIn,
	}: {
		location?: Key;
		results?: Key;
		searchIn?: Key;
	}) => {
		const url = addParams(
			{
				[`${namespace}searchIn`]: searchIn || initialSearchIn,
				[`${namespace}searchLocation`]: location || initialLocation,
				[`${namespace}tab`]: results || initialResults,
			},
			searchURL
		);

		navigate(url);
	};

	return (
		<ClayLayout.Row>
			<ClayLayout.Col>
				<ClayForm.Group className="c-mr-2 d-inline-flex">
					<Picker
						id={`${namespace}searchResults`}
						onSelectionChange={(key: Key) =>
							onChange({results: key})
						}
						selectedKey={initialResults}
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
							id={`${namespace}searchLocation`}
							onSelectionChange={(key: Key) =>
								onChange({location: key})
							}
							selectedKey={initialLocation}
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
						id={`${namespace}searchIn`}
						onSelectionChange={(key: Key) =>
							onChange({searchIn: key})
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
