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

import dom from 'metal-dom';
import {EventHandler} from 'metal-events';
import Component from 'metal-jsx';

import formBuilderProps from './props.es';

const _CSS_HOVERED = 'hovered';

const withHoverableFields = ChildComponent => {
	class HoverableFields extends Component {
		attached() {
			this._eventHandler = new EventHandler();

			this._eventHandler.add(
				this.delegate(
					'mouseenter',
					'.ddm-field-container',
					this._handleMouseEnterField.bind(this)
				)
			);

			this._eventHandler.add(
				this.delegate(
					'mouseleave',
					'.ddm-field-container',
					this._handleMouseLeaveField.bind(this)
				)
			);
		}

		disposeInternal() {
			super.disposeInternal();

			this._eventHandler.removeAllListeners();
		}

		render() {
			return (
				<div class="hoverable">
					<ChildComponent {...this.props} />
				</div>
			);
		}

		_getClosestParent(node) {
			return dom.closest(node.parentElement, `.ddm-field-container`);
		}

		_handleMouseEnterField(event) {
			const {delegateTarget} = event;

			let closestParent = this._getClosestParent(delegateTarget);

			while (closestParent) {
				closestParent.classList.remove(_CSS_HOVERED);

				closestParent = this._getClosestParent(closestParent);
			}

			delegateTarget.classList.add(_CSS_HOVERED);

			event.stopPropagation();
		}

		_handleMouseLeaveField(event) {
			const {delegateTarget} = event;

			const closestParent = this._getClosestParent(delegateTarget);

			if (closestParent) {
				closestParent.classList.add(_CSS_HOVERED);
			}

			delegateTarget.classList.remove(_CSS_HOVERED);

			event.stopPropagation();
		}
	}

	HoverableFields.PROPS = {
		...formBuilderProps
	};

	return HoverableFields;
};

export default withHoverableFields;
