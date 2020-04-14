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
import ReactDOM from 'react-dom';

/**
 * DOM node which will be manually updated and injects
 * React.portals into it.
 */
export default class UnsafeHTML extends React.PureComponent {
	constructor(props) {
		super(props);
		this.state = {portals: [], ref: null};
	}

	componentDidUpdate(prevProps) {
		if (this.state.ref) {
			this._syncRefProps();

			if (
				!this.state.ref.innerHTML ||
				prevProps.markup !== this.props.markup
			) {
				this._syncRefContent();
			}
		}
	}

	/**
	 * Syncs ref innerHTML and recreates portals.
	 *
	 * Everytime that markup property is updated ref innerHTML
	 * needs to be updated and portals need to be recreated because
	 * DOM nodes references will change.
	 */
	_syncRefContent() {
		const ref = this.state.ref;

		ref.innerHTML = this.props.markup;

		this.setState(
			{
				portals: this.props.getPortals(ref),
			},
			() => {
				globalEval.runScriptsInElement(ref);
				this.props.onRender(ref);
			}
		);
	}

	/**
	 * Syncs non-critical properties to ref.
	 *
	 * If there is some property change we can safely update
	 * ref DOM properties without making more changes.
	 */
	_syncRefProps() {
		const ref = this.state.ref;
		ref.className = this.props.className;
	}

	/**
	 * Updates internal state.ref and reset state.portals.
	 *
	 * If the ref changes for any reason we need to remove all our
	 * portals to prevent them from failing because their DOM nodes
	 * are not linked to the document anymore.
	 */
	_updateRef = nextRef => {
		this.setState(({ref: prevRef}) => {
			if (prevRef !== nextRef) {
				if (typeof this.props.contentRef === 'function') {
					this.props.contentRef(nextRef);
				}
				else if (this.props.contentRef) {
					this.props.contentRef.current = nextRef;
				}

				return {
					portals: [],
					ref: nextRef,
				};
			}

			return null;
		});
	};

	render() {
		return (
			<>
				<RawDOM
					elementRef={this._updateRef}
					TagName={this.props.TagName}
				/>

				{this.state.portals.map(({Component, element}) =>
					ReactDOM.createPortal(<Component />, element)
				)}
			</>
		);
	}
}

UnsafeHTML.defaultProps = {
	TagName: 'div',
	className: '',
	contentRef: null,
	getPortals: () => [],
	markup: '',
	onRender: () => {},
};

UnsafeHTML.propTypes = {
	TagName: PropTypes.string,
	className: PropTypes.string,
	contentRef: PropTypes.oneOfType([
		PropTypes.func,
		PropTypes.shape({current: PropTypes.instanceOf(Element)}),
	]),
	getPortals: PropTypes.func,
	markup: PropTypes.string,
	onRender: PropTypes.func,
};

/**
 * Creates a DOM node that will be kept forever
 * to allow manipulating the DOM manually.
 */
class RawDOM extends React.Component {
	shouldComponentUpdate() {
		return false;
	}

	render() {
		const TagName = this.props.TagName;

		return <TagName ref={this.props.elementRef} />;
	}
}

RawDOM.defaultProps = {
	TagName: 'div',
};

RawDOM.propTypes = {
	TagName: PropTypes.string,
	elementRef: PropTypes.func.isRequired,
};
