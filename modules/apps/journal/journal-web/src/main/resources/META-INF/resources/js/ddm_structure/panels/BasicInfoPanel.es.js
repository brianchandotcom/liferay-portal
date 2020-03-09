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

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useIsMounted} from 'frontend-js-react-web';
import {fetch} from 'frontend-js-web';
import {globalEval} from 'metal-dom';
import React, {useEffect, useState} from 'react';

export default function BasicInfoPanel({url}) {
	const [loading, setLoading] = useState(true);
	const [content, setContent] = useState('');
	const isMounted = useIsMounted();

	useEffect(() => {
		fetch(url)
			.then(response => response.text())
			.then(content => {
				if (isMounted()) {
					setContent(content);
					setLoading(false);
				}
			})
			.catch(error => {
				if (process.env.NODE_ENV === 'development') {
					console.error(error);
				}
			});
	}, [isMounted, url]);

	return (
		<div className="sidebar-sm">
			<div className="sidebar-header">
				<p className="component-title h4">
					{Liferay.Language.get('properties')}
				</p>
			</div>
			<div className="sidebar-body">
				{loading ? (
					<ClayLoadingIndicator />
				) : (
					<BasicInfoPanelBody content={content} />
				)}
			</div>
		</div>
	);
}

class BasicInfoPanelBody extends React.Component {
	constructor(props) {
		super(props);

		this._ref = React.createRef();
	}

	componentDidMount() {
		if (this._ref.current) {
			globalEval.runScriptsInElement(this._ref.current);

			this._ref.current.addEventListener('change', this._handleOnChange);
		}
	}
	shouldComponentUpdate() {
		return false;
	}

	render() {
		return (
			<div
				dangerouslySetInnerHTML={{__html: this.props.content}}
				ref={this._ref}
			/>
		);
	}
}
