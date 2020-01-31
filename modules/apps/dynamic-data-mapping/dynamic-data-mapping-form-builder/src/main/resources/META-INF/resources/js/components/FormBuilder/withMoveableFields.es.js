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

import {FormSupport} from 'dynamic-data-mapping-form-renderer';
import {DragDrop} from 'metal-drag-drop';
import Component from 'metal-jsx';
import {Config} from 'metal-state';

import {focusedFieldStructure, pageStructure} from '../../util/config.es';

const withMoveableFields = ChildComponent => {
	class MoveableFields extends Component {
		createDragAndDrop() {
			this._dragAndDrop = new DragDrop({
				sources: '.moveable .ddm-drag',
				targets: '.moveable .ddm-target',
				useShim: false
			});

			this._dragAndDrop.on(
				DragDrop.Events.END,
				this._handleDragAndDropEnd.bind(this)
			);

			this._dragAndDrop.on(
				DragDrop.Events.DRAG,
				this._handleDragStarted.bind(this)
			);
		}

		disposeDragAndDrop() {
			if (this._dragAndDrop) {
				this._dragAndDrop.dispose();
			}
		}

		disposeInternal() {
			super.disposeInternal();

			this.disposeDragAndDrop();
		}

		isDragEnabled() {
			const {defaultLanguageId, editingLanguageId} = this.props;

			return defaultLanguageId === editingLanguageId;
		}

		render() {
			return (
				<div class={this.isDragEnabled() ? 'moveable' : ''}>
					<ChildComponent {...this.props} />
				</div>
			);
		}

		rendered() {
			this._refreshDragAndDrop();
		}

		_handleDragAndDropEnd({source, target}) {
			const lastParent = document.querySelector('.dragging');

			if (lastParent) {
				lastParent.classList.remove('dragging');
				lastParent.removeAttribute('style');
			}

			if (!target) {
				target = document.querySelector(
					'.ddm-form-builder .ddm-target.targetOver'
				);
			}

			if (target) {
				source.innerHTML = '';

				const sourceIndexes = FormSupport.getIndexes(
					source.parentElement.parentElement
				);

				const targetColumn = target.parentElement;
				const targetIndexes = FormSupport.getIndexes(targetColumn);

				const addedToPlaceholder = targetColumn.parentElement.classList.contains(
					'placeholder'
				);

				this._handleFieldMoved({
					addedToPlaceholder,
					source: sourceIndexes,
					target: targetIndexes
				});
			}
		}

		_handleDragStarted({source}) {
			const {height} = source.getBoundingClientRect();
			const {parentElement} = source;

			parentElement.setAttribute(
				'style',
				`height: ${height}px !important;`
			);
			parentElement.classList.add('dragging');
		}

		_handleFieldMoved(event) {
			const {store} = this.context;

			store.emit('fieldMoved', event);
		}

		_refreshDragAndDrop() {
			this.disposeDragAndDrop();
			this.createDragAndDrop();
		}
	}

	MoveableFields.PROPS = {
		/**
		 * @default
		 * @instance
		 * @memberof FormBuilder
		 * @type {?number}
		 */

		activePage: Config.number().value(0),

		/**
		 * @default undefined
		 * @instance
		 * @memberof FormBuilder
		 * @type {?string}
		 */

		defaultLanguageId: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof FormBuilder
		 * @type {?string}
		 */

		editingLanguageId: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof FormBuilder
		 * @type {?string}
		 */

		fieldSetDefinitionURL: Config.string(),

		/**
		 * @default []
		 * @instance
		 * @memberof FormBuilder
		 * @type {?(array|undefined)}
		 */

		fieldSets: Config.array().value([]),

		/**
		 * @default []
		 * @instance
		 * @memberof FormBuilder
		 * @type {?(array|undefined)}
		 */

		fieldTypes: Config.array().value([]),

		/**
		 * @default {}
		 * @instance
		 * @memberof FormBuilder
		 * @type {?object}
		 */

		focusedField: focusedFieldStructure.value({}),

		/**
		 * @default []
		 * @instance
		 * @memberof FormBuilder
		 * @type {?array<object>}
		 */

		pages: Config.arrayOf(pageStructure).value([]),

		/**
		 * @instance
		 * @memberof FormBuilder
		 * @type {string}
		 */

		paginationMode: Config.string().required(),

		/**
		 * @instance
		 * @memberof FormBuilder
		 * @type {string}
		 */

		portletNamespace: Config.string().required(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof FormBuilder
		 * @type {!string}
		 */

		spritemap: Config.string().required(),

		/**
		 * @instance
		 * @memberof FormBuilder
		 * @type {object}
		 */

		successPageSettings: Config.shapeOf({
			body: Config.object(),
			enabled: Config.bool(),
			title: Config.object()
		}).value({}),

		/**
		 * @default undefined
		 * @instance
		 * @memberof FormBuilder
		 * @type {?string}
		 */

		view: Config.string()
	};

	return MoveableFields;
};

export default withMoveableFields;
