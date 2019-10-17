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
import {Drag} from 'metal-drag-drop';
import Component from 'metal-jsx';
import Position from 'metal-position';
import {Config} from 'metal-state';

import {focusedFieldStructure, pageStructure} from '../../util/config.es';

const withResizeableColumns = ChildComponent => {
	class ResizeableColumns extends Component {
		attached() {
			this._createResizeDrag();
		}

		disposeInternal() {
			super.disposeInternal();

			if (this._resizeDrag) {
				this._resizeDrag.dispose();
			}
		}

		isResizeEnabled() {
			const {defaultLanguageId, editingLanguageId} = this.props;

			return defaultLanguageId === editingLanguageId;
		}

		render() {
			return (
				<div class={this.isResizeEnabled() ? 'resizeable' : ''}>
					{this.renderResizeReferences()}

					<ChildComponent {...this.props} />
				</div>
			);
		}

		renderResizeReferences() {
			return [...Array(12)].map((element, index) => {
				return (
					<div
						class="ddm-resize-column"
						data-resize-column={index}
						key={index}
						ref={`resizeColumn${index}`}
					/>
				);
			});
		}

		_createResizeDrag() {
			this._resizeDrag = new Drag({
				axis: 'x',
				sources: '.resizeable .ddm-resize-handle',
				useShim: true
			});

			this._resizeDrag.on(
				Drag.Events.END,
				this._handleDragEnd.bind(this)
			);

			this._resizeDrag.on(
				Drag.Events.DRAG,
				this._handleDragMove.bind(this)
			);

			this._resizeDrag.on(
				Drag.Events.START,
				this._handleDragStart.bind(this)
			);
		}

		_handleDragEnd({source}) {
			const {parentElement} = source;

			if (parentElement) {
				parentElement.classList.remove('dragging');
			}
		}

		_handleDragStart() {
			this._lastResizeColumn = -1;
		}

		_handleDragMove(event) {
			const columnNodes = Object.keys(this.refs)
				.filter(key => key.indexOf('resizeColumn') === 0)
				.map(key => this.refs[key]);
			const {source, x} = event;
			const {store} = this.context;

			const container = dom.closest(source, '.ddm-field-container');

			if (container) {
				container.classList.add('dragging');
			}

			let distance = Infinity;
			let nearest;

			columnNodes.forEach(node => {
				const region = Position.getRegion(node);

				const currentDistance = Math.abs(x - region.left);

				if (currentDistance < distance) {
					distance = currentDistance;
					nearest = node;
				}
			});

			if (nearest) {
				const column = Number(nearest.dataset.resizeColumn);
				const direction = source.classList.contains(
					'ddm-resize-handle-left'
				)
					? 'left'
					: 'right';

				if (this._lastResizeColumn !== column) {
					this._lastResizeColumn = column;

					store.emit('columnResized', {
						column,
						direction,
						source
					});
				}
			}
		}
	}

	ResizeableColumns.PROPS = {
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

	return ResizeableColumns;
};

export default withResizeableColumns;
