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

import {FormSupport} from 'data-engine-js-components-web';
import {DragDrop} from 'metal-drag-drop';
import Component from 'metal-jsx';

import {
	disableFieldDropTargets,
	disableFieldSetDragSources,
	disableFieldSetDropTargets,
} from '../../util/dragAndDrop.es';
import formBuilderProps from './props.es';

const withMoveableFields = (ChildComponent) => {
	class MoveableFields extends Component {
		createDragAndDrop() {
			this._dragAndDrop = new DragDrop({
				container: this.element,
				sources: '.moveable .ddm-drag:not([data-drag-disabled="true"])',
				targets:
					'.moveable .ddm-target:not([data-drop-disabled="true"])',
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

		render() {
			return (
				<div class="moveable">
					<ChildComponent {...this.props} />
				</div>
			);
		}

		rendered() {
			const {allowNestedFields = true, pages} = this.props;

			disableFieldSetDragSources(this.element, pages);
			disableFieldSetDropTargets(this.element, pages);

			if (!allowNestedFields) {
				disableFieldDropTargets(this.element, pages);
			}

			this._refreshDragAndDrop();
		}

		_getClosestParent(node) {
			return node.parentElement.closest('.ddm-field-container');
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
				const sourceFieldNode = source.closest('.ddm-field-container');

				const sourceFieldPage = parseInt(
					source.closest('[data-ddm-page]').dataset.ddmPage,
					10
				);

				let targetFieldName;

				if (target.classList.contains('ddm-field-container')) {
					targetFieldName = target.dataset.fieldName;
				}

				const sourceFieldName = sourceFieldNode.dataset.fieldName;

				if (sourceFieldName === targetFieldName) {
					return;
				}

				let targetParentFieldName;
				const targetParentFieldNode = this._getClosestParent(target);

				if (targetParentFieldNode) {
					targetParentFieldName =
						targetParentFieldNode.dataset.fieldName;
				}

				this._handleFieldMoved({
					sourceFieldName,
					sourceFieldPage,
					targetFieldName,
					targetIndexes: FormSupport.getIndexes(target.parentElement),
					targetParentFieldName,
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
			const {dispatch} = this.context;

			dispatch('fieldMoved', event);
		}

		_refreshDragAndDrop() {
			this.disposeDragAndDrop();
			this.createDragAndDrop();
		}
	}

	MoveableFields.PROPS = {
		...formBuilderProps,
	};

	return MoveableFields;
};

export default withMoveableFields;
