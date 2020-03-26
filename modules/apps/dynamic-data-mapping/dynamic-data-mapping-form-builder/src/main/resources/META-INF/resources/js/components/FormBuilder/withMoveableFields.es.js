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

import {FormSupport, PagesVisitor} from 'dynamic-data-mapping-form-renderer';
import dom from 'metal-dom';
import {DragDrop} from 'metal-drag-drop';
import Component from 'metal-jsx';

import {CSS_DDM_FIELDSET} from '../../util/cssClasses.es';
import {
	disableFieldSetDragSources,
	disableFieldSetDropTargets,
} from '../../util/dragAndDrop.es';
import {getParentFieldSet} from '../../util/fieldSupport.es';
import formBuilderProps from './props.es';

const withMoveableFields = ChildComponent => {
	class MoveableFields extends Component {
		createDragAndDrop() {
			this._dragAndDrop = new DragDrop({
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
			const {pages} = this.props;

			disableFieldSetDragSources(this.element, pages);
			disableFieldSetDropTargets(this.element, pages);

			this._decorateFieldSets();
			this._refreshDragAndDrop();
		}

		_decorateFieldSets() {
			const {pages} = this.props;
			const visitor = new PagesVisitor(pages);

			visitor.visitFields(field => {
				const parentFieldSet = getParentFieldSet(
					pages,
					field.fieldName
				);

				if (parentFieldSet) {
					const parentFieldSetNode = this.element.querySelector(
						`.ddm-field-container[data-field-name="${parentFieldSet.fieldName}"]`
					);

					parentFieldSetNode.classList.add(CSS_DDM_FIELDSET);
				}
			});
		}

		_getClosestParent(node) {
			return dom.closest(node.parentElement, `.ddm-field-container`);
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
				const sourceFieldNode = dom.closest(
					source,
					'.ddm-field-container'
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
