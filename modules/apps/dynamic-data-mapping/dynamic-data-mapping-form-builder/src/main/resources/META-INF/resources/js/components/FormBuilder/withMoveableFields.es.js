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

import {DragDrop} from 'metal-drag-drop';
import Component from 'metal-jsx';

import formBuilderProps from './props.es';

const withMoveableFields = ChildComponent => {
	class MoveableFields extends Component {
		createDragAndDrop() {
			this._dragAndDrop = new DragDrop({
				sources: '.moveable .ddm-drag',
				targets: '.moveable .ddm-target',
				useShim: false,
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

				const targetColumn = target.parentElement;

				const addedToPlaceholder = targetColumn.parentElement.classList.contains(
					'placeholder'
				);

				this._handleFieldMoved({
					addedToPlaceholder,
					source: source.parentElement.parentElement,
					target: targetColumn,
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
		...formBuilderProps
	};

	return MoveableFields;
};

export default withMoveableFields;
