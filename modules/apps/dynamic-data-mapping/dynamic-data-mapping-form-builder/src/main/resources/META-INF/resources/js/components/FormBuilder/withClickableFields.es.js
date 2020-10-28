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

import {EventHandler} from 'metal-events';
import Component from 'metal-jsx';

import formBuilderProps from './props.es';

const withClickableFields = (ChildComponent) => {
	class ClickableFields extends Component {
		attached() {
			this._eventHandler = new EventHandler();

			this._eventHandler.add(
				this.delegate(
					'click',
					'.ddm-field-container',
					this._handleFieldClicked.bind(this)
				)
			);
		}

		disposed() {
			this._eventHandler.removeAllListeners();
		}

		render() {
			return <ChildComponent {...this.props} />;
		}

		_handleFieldClicked(event) {
			const {delegateTarget} = event;
			const {dispatch} = this.context;
			const {fieldName} = delegateTarget.dataset;
			let {activePage} = this.context.store.state;

			event.stopPropagation();
			if (
				!event.delegateTarget.children[1].hasAttribute('aria-grabbed')
			) {
				activePage = parseInt(
					event.delegateTarget.closest('[data-ddm-page]').dataset
						.ddmPage,
					10
				);
			}

			dispatch('fieldClicked', {activePage, fieldName});
		}
	}

	ClickableFields.PROPS = {
		...formBuilderProps,
	};

	return ClickableFields;
};

export default withClickableFields;
