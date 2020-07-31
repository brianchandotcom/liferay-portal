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
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useState} from 'react';

import {StyleBookContext} from './StyleBookContext';
import {config} from './config';

export default function PagesTree({showPrivatePages}) {
	const [loading, setLoading] = useState(true);
	const [content, setContent] = useState('');
	const isMounted = useIsMounted();
	const {setPreviewPage} = useContext(StyleBookContext);

	useEffect(() => {
		setLoading(true);

		Liferay.destroyComponent(`${config.namespace}pagesTree`);

		const url = new URL(config.pagesTreeURL);

		url.searchParams.set(
			`${config.namespace}privateLayout`,
			showPrivatePages
		);

		fetch(url.href)
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
	}, [isMounted, showPrivatePages]);

	return loading ? (
		<ClayLoadingIndicator />
	) : (
		<PagesTreeContent content={content} onPageClick={setPreviewPage} />
	);
}

class PagesTreeContent extends React.Component {
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
				className="style-book-editor__page-tree"
				dangerouslySetInnerHTML={{__html: this.props.content}}
				onClick={(event) => {
					const target = event.nativeEvent?.target;

					if (target?.dataset?.label && target?.dataset?.url) {
						this.props.onPageClick({
							pageName: target.dataset.label,
							pageURL: target.dataset.url,
						});
					}
				}}
				ref={this._ref}
			/>
		);
	}
}

PagesTree.propTypes = {
	showPrivatePages: PropTypes.bool.isRequired,
};
