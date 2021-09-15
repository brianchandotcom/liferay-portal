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
import {useIsMounted} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import {fetch, runScriptsInElement} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useState} from 'react';

import {AppContext} from './AppContext';

export default function PropertiesSidebarPanel({className}) {
	const [loading, setLoading] = useState(true);
	const [content, setContent] = useState('');
	const isMounted = useIsMounted();

	const {propertiesViewURL} = useContext(AppContext);

	useEffect(() => {
		if (!propertiesViewURL) {
			return;
		}

		fetch(propertiesViewURL)
			.then((response) => response.text())
			.then((content) => {
				if (isMounted()) {
					setContent(content);
					setLoading(false);
				}
			})
			.catch((error) => {
				if (process.env.NODE_ENV === 'development') {
					console.error(error);
				}
			});
	}, [isMounted, propertiesViewURL]);

	return (
		<div className={classNames(className, 'px-3')}>
			<h1 className="ddm_template_editor__App-sidebar-title my-3">
				{Liferay.Language.get('properties')}
			</h1>
			<div>
				{loading ? (
					<ClayLoadingIndicator />
				) : (
					<PropertiesSidebarPanelBody content={content} />
				)}
			</div>
		</div>
	);
}

PropertiesSidebarPanel.propTypes = {
	className: PropTypes.string,
};

class PropertiesSidebarPanelBody extends React.Component {
	constructor(props) {
		super(props);

		this._ref = React.createRef();
	}

	componentDidMount() {
		if (this._ref.current) {
			runScriptsInElement(this._ref.current);

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
