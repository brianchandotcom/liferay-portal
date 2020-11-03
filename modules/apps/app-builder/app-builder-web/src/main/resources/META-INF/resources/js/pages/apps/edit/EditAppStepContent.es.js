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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import {SearchInput} from 'data-engine-taglib';
import React, {useEffect, useState} from 'react';

import useResource from '../../../hooks/useResource.es';
import {getLocalizedValue} from '../../../utils/lang.es';
import ListItems from './ListItems.es';

const EditAppStepContent = ({
	alertProps,
	defaultLanguageId,
	endpoint,
	emptyState,
	itemId,
	onSelect,
	params = {},
	parseItems = (items) => items,
	shortCutButtonProps,
	staticItems = [],
	stepHeader,
}) => {
	const [searchText, setSearchText] = useState('');
	const [showAlert, setShowAlert] = useState(false);

	useEffect(() => {
		setSearchText('');
	}, [stepHeader]);

	const {isLoading, refetch, response} = useResource({endpoint, params});

	const items = response?.items ?? [];

	const filteredItems = [
		...staticItems,
		...parseItems(items),
	].filter((item) =>
		new RegExp(searchText, 'ig').test(
			getLocalizedValue(defaultLanguageId, item.name)
		)
	);

	return (
		<>
			<ClayLayout.ContentRow className="mb-4 pl-4 pr-4">
				<ClayLayout.ContentCol expand>
					<StepHeader {...stepHeader} />
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>

			<ClayLayout.ContentRow className="mb-4 pl-4 pr-4">
				<ClayLayout.ContentCol className="mr-2" expand>
					<SearchInput
						onChange={(searchText) => setSearchText(searchText)}
						searchText={searchText}
					/>
				</ClayLayout.ContentCol>

				{shortCutButtonProps && (
					<ClayButton
						{...shortCutButtonProps}
						onClick={() =>
							shortCutButtonProps.onClick({setShowAlert})
						}
					>
						{shortCutButtonProps.label}
						<ClayIcon className="ml-2" symbol="shortcut" />
					</ClayButton>
				)}
			</ClayLayout.ContentRow>

			{showAlert && alertProps && (
				<ClayLayout.ContentRow className="pl-4 pr-4">
					<ClayAlert
						className="w-100"
						displayType={alertProps.displayType}
						onClose={() => setShowAlert(false)}
						title={alertProps.title}
					>
						{alertProps.content({refetch})}
					</ClayAlert>
				</ClayLayout.ContentRow>
			)}

			<ClayLayout.ContentRow className="pl-4 pr-4 scrollable-container">
				<ClayLayout.ContentCol expand>
					<ListItems
						defaultLanguageId={defaultLanguageId}
						emptyState={emptyState}
						isEmpty={filteredItems.length === 0}
						isLoading={isLoading}
						itemId={itemId}
						items={filteredItems}
						keywords={searchText}
						onChange={onSelect}
					/>
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>
		</>
	);
};

const StepHeader = ({description, label, title}) => {
	return (
		<>
			<h2>
				{title} {label}
			</h2>

			{description}
		</>
	);
};

export default EditAppStepContent;
