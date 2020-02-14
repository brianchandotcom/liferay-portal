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

import {globalEval} from 'metal-dom';
import PropTypes from 'prop-types';
import React from 'react';

export default class UnsafeHTML extends React.Component {
	constructor(props) {
		super(props);

		this.state = {
			contentRef: null
		};
	}

	componentDidUpdate() {
		if (this.state.contentRef) {
			globalEval.runScriptsInElement(this.state.contentRef);

			if (this.props.onRender) {
				this.props.onRender(this.state.contentRef);
			}
		}
	}

	shouldComponentUpdate(nextProps) {
		return (
			this.props.TagName !== nextProps.TagName ||
			this.props.markup !== nextProps.markup
		);
	}

	_handleRef = element => {
		this.setState({contentRef: element});

		if (typeof this.props.contentRef === 'function') {
			this.props.contentRef(element);
		}
		else if (this.props.contentRef) {
			this.props.contentRef.current = element;
		}
	};

	render() {
		const {
			TagName = 'div',
			// We just want to remove this item from the
			// otherProps object.
			// eslint-disable-next-line no-unused-vars
			contentRef,
			markup,
			...otherProps
		} = this.props;

		return (
			<TagName
				{...otherProps}
				dangerouslySetInnerHTML={{__html: markup}}
				ref={this._handleRef}
			/>
		);
	}
}

UnsafeHTML.propTypes = {
	TagName: PropTypes.string,
	contentRef: PropTypes.oneOfType([
		PropTypes.func,
		PropTypes.shape({current: PropTypes.instanceOf(Element)})
	]),
	markup: PropTypes.string,
	onRender: PropTypes.func
};
