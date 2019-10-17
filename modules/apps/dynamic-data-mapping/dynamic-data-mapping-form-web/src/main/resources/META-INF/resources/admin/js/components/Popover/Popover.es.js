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

import getCN from 'classnames';
import dom from 'metal-dom';
import {EventHandler} from 'metal-events';
import Component, {Config} from 'metal-jsx';
import {Align} from 'metal-position';

import PopoverBase from './PopoverBase.es';

const POSITIONS = ['top', 'left', 'right', 'bottom'];

const getAlignPosition = (source, target, suggestedPosition) => {
	if (!suggestedPosition) {
		suggestedPosition = Align.TopCenter;
	}

	const position = Align.align(source, target, suggestedPosition);

	let returnedPositon = POSITIONS[position];

	if (!returnedPositon) {
		returnedPositon = 'left';
	}

	return returnedPositon;
};

const CLASSNAME = 'ddm-share-url-popover';

class Popover extends Component {
	attached() {
		const {alignElement} = this.props;

		this._eventHandler = new EventHandler();

		this._eventHandler.add(
			dom.on(
				document,
				'mousedown',
				this._handleDocumentMouseDown.bind(this),
				true
			),
			dom.on(
				alignElement,
				'click',
				this._handleAlignElementClicked.bind(this)
			)
		);

		return this.setPopoverWidth();
	}

	disposeInternal() {
		super.disposeInternal();

		this._eventHandler.removeAllListeners();
	}

	render() {
		const {alignElement, children, content, placement, title} = this.props;
		const {displayed, position, width} = this.state;

		const classes = getCN(CLASSNAME, {
			'popover-large': width > 600
		});

		return (
			<PopoverBase
				elementClasses={classes}
				events={{
					stateSynced: () => {
						if (!displayed) {
							return;
						}
						this.setState({
							position: getAlignPosition(
								this.refs.popover.element,
								alignElement,
								placement
							)
						});
					}
				}}
				placement={position}
				ref="popover"
				visible={displayed}
			>
				{title && <PopoverBase.Header>{title}</PopoverBase.Header>}

				<PopoverBase.Body>
					{content && <span class="text-secondary">{content}</span>}

					{children.length && children}
				</PopoverBase.Body>
			</PopoverBase>
		);
	}

	setPopoverWidth() {
		const {element} = this;

		element.style.visibility = 'hidden';
		element.style.display = 'block';

		const width = element.offsetHeight;

		element.style.visibility = 'visible';
		element.style.display = 'none';

		this.setState({
			width
		});
	}

	willReceiveProps({visible}) {
		if (visible) {
			this.setState({
				displayed: !!visible.newVal
			});
		}
	}

	_handleAlignElementClicked() {
		const {displayed} = this.state;

		if (displayed) {
			this.emit('popoverClosed');
		}

		this.setState({
			displayed: !displayed
		});
	}

	_handleDocumentMouseDown({target}) {
		const {alignElement} = this.props;
		const {displayed} = this.state;

		if (
			!this.element.contains(target) &&
			!alignElement.contains(target) &&
			displayed
		) {
			this.setState({
				displayed: false
			});

			this.emit('popoverClosed');
		}
	}

	_visibleValueFn() {
		return this.props.visible;
	}
}

Popover.PROPS = {
	alignElement: Config.object(),
	content: Config.string(),
	placement: Config.number(),
	title: Config.string(),
	visible: Config.bool()
};

Popover.STATE = {
	displayed: Config.bool().valueFn('_visibleValueFn'),
	position: Config.string(),
	width: Config.number().value(240)
};

export default Popover;
