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
import ClayForm, {ClayInput, ClaySelect} from '@clayui/form';
import React, {useEffect} from 'react';

import APIDisplay from './APIDisplay';
import {spritemap} from './Icon';
import PathList from './PathList';
import SchemaExplorer from './SchemaExplorer';
import {useAppState} from './hooks/appState';
import apiFetch from './util/apiFetch';
import {setSearchParam} from './util/params';
import {getCategoryURL} from './util/url';

const APIDisplayStyle = {
	height: 'calc(100% - 190px)',
	overflowY: 'scroll',
};

const APIGUI = () => {
	const [state, dispatch] = useAppState();

	const {
		categories,
		categoryKey,
		filter,
		method,
		path,
		paths,
		schemas,
		showSchemas,
	} = state;

	useEffect(() => {
		setSearchParam('category', categoryKey);
	}, [categoryKey]);

	useEffect(() => {
		setSearchParam('path', path);
	}, [path]);

	useEffect(() => {
		setSearchParam('method', method);
	}, [method]);

	useEffect(() => {
		setSearchParam('filter', filter);
	}, [filter]);

	useEffect(() => {
		setSearchParam('show-schemas', showSchemas);
	}, [showSchemas]);

	useEffect(() => {
		let current = true;

		apiFetch('/o/openapi', 'get', {}).then((res) => {
			if (current) {
				var categories = {};

				Object.keys(res).forEach((key) => {
					categories[key] = res[key].map((url) =>
						url.replace('openapi.yaml', 'openapi.json')
					);
				});

				dispatch({
					categories,
					type: 'LOAD_CATEGORIES',
				});
			}
		});

		return () => {
			current = false;
		};
	}, [dispatch]);

	useEffect(() => {
		let current = true;

		const categoryURL = getCategoryURL(categories, categoryKey);

		if (categoryURL) {
			apiFetch(categoryURL).then((category) => {
				if (current) {
					dispatch({
						category,
						type: 'LOAD_CATEGORY',
					});
				}
			});
		}

		return () => {
			current = false;
		};
	}, [categoryKey, categories, dispatch]);

	return (
		<div className="api-gui-root">
			<div className="container container-fluid">
				<div className="row">
					<div className="border col col-md-5 overflow-auto p-0 vh-100">
						<ClayForm.Group className="pt-3 px-3">
							<label
								className="d-flex justify-content-between"
								htmlFor="categorySelect"
							>
								<span>
									{Liferay.Language.get(
										'select-api-category'
									)}
								</span>

								{schemas && (
									<button
										onClick={() => {
											dispatch({
												type: 'TOGGLE_SCHEMAS',
											});
										}}
									>
										{showSchemas
											? Liferay.Language.get(
													'hide-schemas'
											  )
											: Liferay.Language.get(
													'show-schemas'
											  )}
									</button>
								)}
							</label>
							<ClaySelect
								aria-label="Select API Category"
								onChange={(e) => {
									dispatch({
										categoryKey: e.currentTarget.value,
										type: 'SELECT_CATEGORY',
									});
								}}
								value={categoryKey}
							>
								{categories &&
									Object.keys(categories).map((key) => (
										<ClaySelect.Option
											key={key}
											label={key}
											value={key}
										/>
									))}
							</ClaySelect>
						</ClayForm.Group>

						<ClayForm.Group className="pt-0 px-3">
							<label htmlFor="filter">{'Filter'}</label>

							<ClayInput
								name="filter"
								onChange={(event) => {
									dispatch({
										filter: event.target.value,
										type: 'SET_FILTER',
									});
								}}
								type="text"
								value={filter}
							/>
						</ClayForm.Group>

						<div
							className="api-list border-top p-3"
							style={APIDisplayStyle}
						>
							{paths && (
								<PathList
									baseURL={categoryKey}
									curPath={path}
									filter={filter}
									onClick={(selPath) => {
										dispatch({
											path: selPath,
											type: 'SELECT_PATH',
										});
									}}
									paths={paths}
								/>
							)}
						</div>
					</div>

					<div className="border col col-md-7 overflow-auto p-3 vh-100">
						{paths && path && method && !showSchemas && (
							<APIDisplay />
						)}

						{!path && (
							<ClayAlert
								displayType="info"
								spritemap={spritemap}
								title="Info"
							>
								{Liferay.Language.get(
									'please-select-an-api-from-the-list-on-the-left'
								)}
							</ClayAlert>
						)}

						{showSchemas && schemas && (
							<SchemaExplorer
								category={categoryKey}
								schemas={schemas}
							/>
						)}
					</div>
				</div>
			</div>
		</div>
	);
};

export default APIGUI;
