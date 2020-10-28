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

import {Drag} from 'metal-drag-drop';
import Component from 'metal-jsx';

import formBuilderProps from './props.es';

const MAX_COLUMNS = 12;

const withResizeableColumns = (ChildComponent) => {
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

		render() {
			return (
				<div class="resizeable">
					<ChildComponent {...this.props} />
				</div>
			);
		}

		_createResizeDrag() {
			this._resizeDrag = new Drag({
				axis: 'x',
				container: this.element,
				sources: '.resizeable .ddm-resize-handle',
				useShim: true,
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

			this._currentRow = null;
		}

		_handleDragStart() {
			this._lastResizeColumn = -1;
		}

		_handleDragMove(event) {
			const {source} = event;
			const {store} = this.context;

			if (!this._currentRow) {
				this._currentRow = source.closest('.row');
			}

			const container = this._currentRow.querySelector(
				[
					'.col-ddm',
					`[data-ddm-field-column="${source.dataset.ddmFieldColumn}"]`,
					`[data-ddm-field-page="${source.dataset.ddmFieldPage}"]`,
					`[data-ddm-field-row="${source.dataset.ddmFieldRow}"]`,
					'> .ddm-field-container',
				].join('')
			);

			if (container) {
				container.classList.add('dragging');
			}

			let column = Math.floor(
				((event.x - this._currentRow.getBoundingClientRect().left) *
					(MAX_COLUMNS * 10)) /
					this._currentRow.clientWidth /
					10
			);

			if (column > MAX_COLUMNS - 1) {
				column = MAX_COLUMNS - 1;
			}

			if (column >= 0) {
				const direction = source.classList.contains(
					'ddm-resize-handle-left'
				)
					? 'left'
					: 'right';

				if (this._lastResizeColumn !== column) {
					this._lastResizeColumn = column;

					store.emit('columnResized', {
						column,
						container,
						direction,
						source,
					});
				}
			}
		}
	}

	ResizeableColumns.PROPS = {
		...formBuilderProps,
	};

	return ResizeableColumns;
};

export default withResizeableColumns;
