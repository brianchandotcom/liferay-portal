/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayAutocomplete from '@clayui/autocomplete';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import {openModal} from 'frontend-js-web';
import React from 'react';

const RequiredMark = () => (
	<>
		<span className="inline-item-after reference-mark text-warning">
			<ClayIcon symbol="asterisk" />
		</span>
		<span className="hide-accessible sr-only">
			{Liferay.Language.get('required')}
		</span>
	</>
);

type HeadlessResource = {
	internalClassName: string;
	label: string;
};
interface IFDSViewsProps {
	headlessResources: Array<HeadlessResource>;
	namespace: string;
}

const FDSViews = ({headlessResources, namespace}: IFDSViewsProps) => {
	const CreateFDSViewModalBody = () => (
		<>
			<ClayForm.Group>
				<label htmlFor={`${namespace}fdsViewNameInput`}>
					{Liferay.Language.get('name')}

					<RequiredMark />
				</label>

				<ClayInput id={`${namespace}fdsViewNameInput`} type="text" />
			</ClayForm.Group>

			<ClayForm.Group>
				<label
					htmlFor={`${namespace}fdsViewProviderAutocomplete`}
					id={`${namespace}fdsViewProviderAutocompleteLabel`}
				>
					{Liferay.Language.get('provider')}

					<RequiredMark />
				</label>

				<ClayAutocomplete
					aria-labelledby={`${namespace}fdsViewProviderAutocompleteLabel`}
					defaultItems={headlessResources.map(({label}) => label)}
					id={`${namespace}fdsViewProviderAutocomplete`}
					menuTrigger="focus"
					messages={{
						loading: '',
						notFound: Liferay.Language.get('no-results-found'),
					}}
					placeholder={Liferay.Language.get('choose-an-option')}
				>
					{headlessResources.map(({internalClassName, label}) => (
						<ClayAutocomplete.Item key={internalClassName}>
							{label}
						</ClayAutocomplete.Item>
					))}
				</ClayAutocomplete>
			</ClayForm.Group>
		</>
	);

	const creationMenu = {
		primaryItems: [
			{
				label: Liferay.Language.get('new-dataset'),
				onClick: () => {
					openModal({
						bodyComponent: CreateFDSViewModalBody,
						buttons: [
							{
								'aria-label': Liferay.Language.get('cancel'),
								'displayType': 'secondary',
								'label': Liferay.Language.get('cancel'),
								'type': 'cancel',
							},
							{
								'aria-label': Liferay.Language.get('save'),
								'label': Liferay.Language.get('save'),
							},
						],
						title: Liferay.Language.get('new-dataset'),
					});
				},
			},
		],
	};

	const views = [
		{
			contentRenderer: 'table',
			label: Liferay.Language.get('table'),
			name: 'viewsTable',
			schema: {
				fields: [
					{fieldName: 'name', label: Liferay.Language.get('name')},
					{
						fieldName: 'provider',
						label: Liferay.Language.get('provider'),
					},
					{
						fieldName: 'modifiedDate',
						label: Liferay.Language.get('modified-date'),
					},
				],
			},
			thumbnail: 'table',
		},
	];

	return (
		<FrontendDataSet
			creationMenu={creationMenu}
			id={`${namespace}FDSViews`}
			views={views}
		/>
	);
};

export default FDSViews;
