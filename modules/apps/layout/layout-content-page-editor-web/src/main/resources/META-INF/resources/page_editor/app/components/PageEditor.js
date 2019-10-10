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

import React from 'react';

import {ConfigContext} from '../config/index';
import {StoreContext} from '../store/index';

const {useContext} = React;

export default function PageEditor() {
	const config = useContext(ConfigContext);
	const state = useContext(StoreContext);

	const {fragmentEntryLinks, layoutData} = state;

	return (
		<>
			<Body
				fragmentEntryLinks={fragmentEntryLinks}
				layoutData={layoutData}
			/>
			<DebugInfo config={config} state={state} />
		</>
	);
}

function Body({layoutData, fragmentEntryLinks}) {
	return layoutData.structure.map((row, index) => {
		return (
			<Row
				columns={row.columns}
				fragmentEntryLinks={fragmentEntryLinks}
				key={index}
			/>
		);
	});
}

function Row({columns, fragmentEntryLinks}) {
	return (
		<div className="row">
			{columns.map((column, index) => {
				const {fragmentEntryLinkIds, size} = column;

				const links = fragmentEntryLinkIds.map(id => {
					return fragmentEntryLinks[id];
				});

				return (
					<Column
						fragmentEntryLinks={links}
						key={index}
						size={size}
					/>
				);
			})}
		</div>
	);
}

function Column({fragmentEntryLinks, size}) {
	return (
		<div className={`col-md-${size}`}>
			{fragmentEntryLinks.map(({content, fragmentEntryLinkId}) => {
				if (content.value.contentKind === 'HTML') {
					return (
						<HTML
							key={fragmentEntryLinkId}
							markup={content.value.content}
						/>
					);
				} else {
					return (
						// TODO: actually handle other `contentKind`s
						<pre key={fragmentEntryLinkId}>
							{JSON.stringify(content, null, 2)}
						</pre>
					);
				}
			})}
		</div>
	);
}

function HTML({markup}) {
	return <div dangerouslySetInnerHTML={{__html: markup}} />;
}

let DebugInfo;

if (process.env.NODE_ENV === 'development') {
	DebugInfo = ({config, state}) => (
		<>
			<h2>Debug Information</h2>
			<h3>Config</h3>
			<pre>{JSON.stringify(config, null, 2)}</pre>
			<h3>Store state</h3>
			<pre>{JSON.stringify(state, null, 2)}</pre>
		</>
	);
} else {
	DebugInfo = () => null;
}
