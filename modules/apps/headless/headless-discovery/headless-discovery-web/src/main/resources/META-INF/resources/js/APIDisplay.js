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

import ClayTabs from '@clayui/tabs';
import React, {useState} from 'react';

import APIForm from './APIForm';
import JavascriptExample from './JavascriptExample';
import MethodBadge from './MethodBadge';
import ResponseDisplay from './ResponseDisplay';
import {useAppState} from './hooks/appState';

const badgeStyle = {
	outline: 'none',
};

const APIDisplay = () => {
	const [state, dispatch] = useAppState();

	const {
		apiResponse,
		apiURL,
		contentType,
		method,
		path,
		paths,
		requestBodyData,
	} = state;

	const [tabIndex, setTabIndex] = useState(0);

	const pathData = paths[path];

	const methodData = pathData[method];

	return (
		<div>
			<div className="mb-2 sheet-header">
				<h2 className="mb-2 sheet-title">{path}</h2>

				<div className="align-items-center d-flex sheet-text">
					{Object.keys(pathData).map((key) => (
						<button
							className="btn-unstyled d-flex mr-2 text-light"
							key={key}
							onClick={() => {
								dispatch({
									method: key,
									type: 'SELECT_METHOD',
								});
							}}
							style={badgeStyle}
						>
							<MethodBadge
								className={'flex-shrink-0'}
								displayType={
									key !== method ? 'secondary' : null
								}
								method={key}
							/>
						</button>
					))}
				</div>

				{methodData.description && (
					<div className="sheet-text">{methodData.description}</div>
				)}
			</div>

			<APIForm />

			{apiResponse && (
				<div className="sheet-section">
					<h3 className="sheet-subtitle">{'Response'}</h3>

					<ClayTabs>
						<ClayTabs.Item
							active={tabIndex === 0}
							innerProps={{
								'aria-controls': `tabpanel-1`,
							}}
							key={0}
							onClick={() => setTabIndex(0)}
						>
							{Liferay.Language.get('response')}
						</ClayTabs.Item>
						<ClayTabs.Item
							active={tabIndex === 1}
							innerProps={{
								'aria-controls': `tabpanel-2`,
							}}
							key={1}
							onClick={() => setTabIndex(1)}
						>
							{Liferay.Language.get('javascript-example')}
						</ClayTabs.Item>
					</ClayTabs>

					<ClayTabs.Content activeIndex={tabIndex}>
						<ClayTabs.TabPane aria-labelledby={`tab-1`} key={0}>
							<ResponseDisplay response={apiResponse} />
						</ClayTabs.TabPane>
						<ClayTabs.TabPane aria-labelledby={`tab-2`} key={1}>
							<JavascriptExample
								contentType={contentType}
								data={requestBodyData}
								method={method}
								url={apiURL}
							/>
						</ClayTabs.TabPane>
					</ClayTabs.Content>
				</div>
			)}
		</div>
	);
};

export default APIDisplay;
