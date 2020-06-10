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
import ClayForm, {ClayInput, ClaySelect} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import ClayModal, {useModal} from '@clayui/modal';
import GraphiQL from 'graphiql';
import React, {useCallback, useEffect, useState} from 'react';
import SwaggerUI from 'swagger-ui-react';

import APIDisplay from './APIDisplay';
import Icon, {spritemap} from './Icon';
import PathList from './PathList';
import SchemaExplorer from './SchemaExplorer';
import {useAppState} from './hooks/appState';
import apiFetch from './util/apiFetch';
import {setSearchParam} from './util/params';
import {getCategoryURL} from './util/url';

import 'graphiql/graphiql.css';

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
		info,
		method,
		path,
		paths,
		schemas,
		showSchemas,
	} = state;

	const [showGraphQL, setShowGraphQL] = useState(false);
	const [showHeaders, setShowHeaders] = useState(false);
	const [showSwagger, setShowSwagger] = useState(false);
	const {observer, onClose} = useModal({
		onClose: () => setShowHeaders(false),
	});
	const [headers, setHeaders] = useState([{key: '', value: ''}]);

	const handleInputChange = (index, event) => {
		const values = [...headers];
		values[index][event.target.name] = event.target.value;
		setHeaders(values);
	};

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

	window.global = window;

	const mutationObserver = new MutationObserver((mutations) => {
		mutations.forEach(() => {
			document
				.querySelectorAll('.btn.try-out__btn:not(.cancel)')
				.forEach((div) => {
					div.click();
					div.style.display = 'none';
				});
		});
	});

	mutationObserver.observe(document.documentElement, {
		attributes: true,
		childList: true,
		subtree: true,
	});

	const graphQLFetcher = useCallback(
		(graphQLParams) =>
			apiFetch(
				'/o/graphql',
				'post',
				graphQLParams,
				'application/json',
				headers
			),
		[headers]
	);

	const requestInterceptor = (req) => {
		req.headers['x-csrf-token'] = document.querySelector(
			'meta[name="csrf-token"]'
		).content;

		for (let i = 0; i < headers.length; i++) {
			const header = headers[i];
			if (header.key && header.value) {
				req.headers[header.key] = header.value;
			}
		}

		return req;
	};

	return (
		<div className="api-gui-root">
			<ClayLayout.ContainerFluid>
				{showHeaders && (
					<ClayModal observer={observer} size="lg" status="info">
						<ClayModal.Header>{'Headers'}</ClayModal.Header>

						<ClayModal.Body>
							<h1>
								Add, edit, and remove headers in your request.
							</h1>

							{headers.map((header, i) => (
								<div className="form-group-autofit" key={i}>
									<div className="form-group-item">
										<input
											className="form-control"
											name="key"
											onChange={(event) =>
												handleInputChange(i, event)
											}
											placeholder="Header Key"
											type="text"
											value={header.key}
										/>
									</div>

									<div className="form-group-item">
										<input
											className="form-control"
											name="value"
											onChange={(event) =>
												handleInputChange(i, event)
											}
											placeholder="Header Value"
											type="text"
											value={header.value}
										/>
									</div>

									<ClayButton
										className="btn btn-warning"
										displayType={'secondary'}
										onClick={() => {
											const values = [...headers];
											values.splice(i, 1);
											setHeaders(values);
										}}
									>
										<Icon symbol="minus-circle" />
									</ClayButton>
								</div>
							))}
						</ClayModal.Body>

						<ClayModal.Footer
							first={
								<ClayButton.Group spaced>
									<ClayButton
										displayType="secondary"
										onClick={() => {
											setHeaders([
												...headers,
												{key: '', value: ''},
											]);
										}}
									>
										Add Header
									</ClayButton>
								</ClayButton.Group>
							}
							last={
								<ClayButton
									onClick={() => {
										dispatch({
											headers,
											type: 'ADD_HEADERS',
										});
										onClose();
									}}
								>
									Save
								</ClayButton>
							}
						/>
					</ClayModal>
				)}

				<ClayLayout.Row>
					<ClayLayout.Col
						className="col-push-3"
						style={{textAlign: 'right'}}
					>
						<button
							onClick={() => {
								setShowHeaders(true);
							}}
						>
							Headers
						</button>

						<button
							onClick={() => {
								setShowGraphQL(false);
								setShowSwagger(!showSwagger);
							}}
						>
							{showSwagger ? 'Hide Swagger' : 'Show Swagger'}
						</button>

						<button
							onClick={() => {
								setShowSwagger(false);
								setShowGraphQL(!showGraphQL);
							}}
						>
							{showGraphQL ? 'Hide GraphQL' : 'Show GraphQL'}
						</button>
					</ClayLayout.Col>
				</ClayLayout.Row>

				{showSwagger && !showGraphQL && (
					<SwaggerUI
						displayOperationId={true}
						requestInterceptor={requestInterceptor}
						supportedSubmitMethods={[
							'get',
							'put',
							'post',
							'delete',
							'patch',
						]}
						url={`/o${new URLSearchParams(
							window.location.search
						).get('category')}/v1.0/openapi.json`}
					/>
				)}

				{showGraphQL && !showSwagger && (
					<ClayLayout.Row className="vh-100">
						<GraphiQL fetcher={graphQLFetcher} />
					</ClayLayout.Row>
				)}

				{!showGraphQL && !showSwagger && (
					<ClayLayout.Row>
						<ClayLayout.Col
							className="border overflow-auto p-0 vh-100"
							md="5"
						>
							<ClayForm.Group className="pt-3 px-3">
								<label
									className="d-flex justify-content-between"
									htmlFor="categorySelect"
								>
									<span>Select API Category</span>

									{schemas && (
										<button
											onClick={() => {
												dispatch({
													type: 'TOGGLE_SCHEMAS',
												});
											}}
										>
											{showSchemas
												? 'Hide Schemas'
												: 'Show Schemas'}
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

							<ClayForm.Group className="pt-3 px-3">
								<label
									className="d-flex justify-content-between"
									htmlFor="categorySelect"
								>
									Description
								</label>

								<p>{info && info.description}</p>
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
						</ClayLayout.Col>

						<ClayLayout.Col
							className="border overflow-auto p-3 vh-100"
							md="7"
						>
							{paths && path && method && !showSchemas && (
								<APIDisplay />
							)}

							{!path && (
								<ClayAlert
									displayType="info"
									spritemap={spritemap}
									title="Info"
								>
									Please select an API from the list on the
									left.
								</ClayAlert>
							)}

							{showSchemas && schemas && (
								<SchemaExplorer
									category={categoryKey}
									schemas={schemas}
								/>
							)}
						</ClayLayout.Col>
					</ClayLayout.Row>
				)}
			</ClayLayout.ContainerFluid>
		</div>
	);
};

export default APIGUI;
