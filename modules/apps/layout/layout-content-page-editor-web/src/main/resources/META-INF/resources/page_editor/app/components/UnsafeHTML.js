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

import {ReactPortal} from '@liferay/frontend-js-react-web';
import PropTypes from 'prop-types';
import React from 'react';

import RawDOM from '../../common/components/RawDOM';
import isNullOrUndefined from '../utils/isNullOrUndefined';

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
				(!this.state.ref.childNodes.length && this.props.markup) ||
				prevProps.markup !== this.props.markup
			) {
				this._syncRefContent();
			}
		}
	}

	/**
	 * Looks for script tags inside ref and executes
	 * them inside this.props.globalContext.
	 */
	_runRefScripts() {
		const doc = this.props.globalContext.document;

		const scriptElements = Array.from(
			this.state.ref.querySelectorAll('script')
		).filter(
			(script) =>
				!script.type ||
				script.type === 'module' ||
				script.type === 'text/javascript'
		);

		const runNextScript = () => {
			if (scriptElements.length) {
				const nextScriptElement = doc.createElement('script');
				const prevScriptElement = scriptElements.shift();

				nextScriptElement.type = prevScriptElement.type;

				if (prevScriptElement.src) {
					nextScriptElement.src = prevScriptElement.src;
				}
				else {
					nextScriptElement.appendChild(
						doc.createTextNode(prevScriptElement.innerHTML)
					);
				}

				prevScriptElement.parentNode.replaceChild(
					nextScriptElement,
					prevScriptElement
				);

				requestAnimationFrame(runNextScript);
			}
		};

		runNextScript();
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

		const portals = this.props.getPortals(ref);

		if (portals.length || portals.length !== this.state.portals.length) {
			this.setState({portals}, () => {
				this._runRefScripts();
				this.props.onRender(ref);
			});
		}
		else {
			this._runRefScripts();
			this.props.onRender(ref);
		}
	}

	/**
	 * Syncs non-critical properties to ref.
	 *
	 * If there is some property change we can safely update
	 * ref DOM properties without making more changes.
	 */
	_syncRefProps() {
		const ref = this.state.ref;

		if (this.props.className) {
			ref.className = this.props.className;
		}
		else {
			ref.removeAttribute('class');
		}

		if (this.props.data) {
			Object.entries(this.props.data).forEach(([key, value]) => {
				ref.dataset[key] = value;
			});
		}

		if (this.props.id) {
			ref.id = this.props.id;
		}
		else {
			ref.removeAttribute('id');
		}

		if (this.props.id) {
			ref.id = this.props.id;
		}
		else {
			ref.removeAttribute('id');
		}

		if (this.props.hideFromAccessibilityTree) {
			ref.setAttribute('aria-hidden', 'true');
		}
		else {
			ref.removeAttribute('aria-hidden');
		}

		ref.removeAttribute('style');

		Object.entries(this.props.style).forEach(([key, value]) => {
			if (!isNullOrUndefined(value)) {
				if (isCSSVariable(key)) {
					ref.style.setProperty(key, value);
				}
				else {
					ref.style[key] = value;
				}
			}
		});
	}

	/**
	 * Updates internal state.ref and reset state.portals.
	 *
	 * If the ref changes for any reason we need to remove all our
	 * portals to prevent them from failing because their DOM nodes
	 * are not linked to the document anymore.
	 */
	_updateRef = (nextRef) => {
		if (typeof this.props.contentRef === 'function') {
			this.props.contentRef(nextRef);
		}
		else if (this.props.contentRef) {
			this.props.contentRef.current = nextRef;
		}

		this.setState(({ref: prevRef}) => {
			if (prevRef !== nextRef) {
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
					TagName={this.props.TagName}
					elementRef={this._updateRef}
				/>

				{this.state.portals.map(({Component, element}, i) => (
					<ReactPortal container={element} key={i}>
						<Component />
					</ReactPortal>
				))}
			</>
		);
	}
}

UnsafeHTML.defaultProps = {
	TagName: 'div',
	className: '',
	contentRef: null,
	getPortals: () => [],
	globalContext: {
		document,
		window,
	},
	hideFromAccessibilityTree: true,
	id: '',
	markup: '',
	onRender: () => {},
	style: {},
};

UnsafeHTML.propTypes = {
	TagName: PropTypes.string,
	className: PropTypes.string,
	contentRef: PropTypes.oneOfType([
		PropTypes.func,
		PropTypes.shape({current: PropTypes.object}),
	]),
	getPortals: PropTypes.func,
	globalContext: PropTypes.shape({
		document: PropTypes.object,
		window: PropTypes.object,
	}),
	id: PropTypes.string,
	markup: PropTypes.string,
	onRender: PropTypes.func,
	style: PropTypes.object,
};

function isCSSVariable(styleName) {
	return styleName.startsWith('--');
}
