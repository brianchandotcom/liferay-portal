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
import {Config} from 'metal-state';

import FieldActionsDropDown from './FieldActionsDropDown.es';
import formBuilderProps from './props.es';

const _CSS_HOVERED = 'hovered';

const ACTIONABLE_FIELDS_CONTAINER = 'ddm-actionable-fields-container';

const withActionableFields = ChildComponent => {
	class ActionableFields extends Component {
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

		created() {
			if (!this._actionableFieldsContainer) {
				const container = document.createElement('div');

				container.id = ACTIONABLE_FIELDS_CONTAINER;

				document.body.appendChild(container);

				this._actionableFieldsContainer = container;
			}
		}

		disposeInternal() {
			super.disposeInternal();

			this._eventHandler.removeAllListeners();

			if (this._actionableFieldsContainer) {
				dom.exitDocument(this._actionableFieldsContainer);
			}
		}

		isActionsEnabled() {
			const {defaultLanguageId, editingLanguageId} = this.props;

			return defaultLanguageId === editingLanguageId;
		}

		hasFocusedField() {
			const {focusedField} = this.props;

			return Object.keys(focusedField).length > 0;
		}

		hideActions(actions) {
			actions.close();
			actions.setState({fieldName: null});

			if (actions === this.refs.hoveredFieldActions) {
				this.setState({hoveredFieldActionsVisible: false});
			}
		}

		render() {
			const {hoveredFieldActionsVisible} = this.state;
			const {fieldActions, fieldTypes, pages, spritemap} = this.props;

			return (
				<div>
					<ChildComponent {...this.props} />

					<FieldActionsDropDown
						disabled={!this.isActionsEnabled()}
						events={{
							mouseLeave: this._handleMouseLeaveActions.bind(
								this
							),
						}}
						fieldTypes={fieldTypes}
						items={fieldActions}
						pages={pages}
						portalElement={this._actionableFieldsContainer}
						ref="selectedFieldActions"
						spritemap={spritemap}
						visible={this.hasFocusedField()}
					/>

					<FieldActionsDropDown
						disabled={!this.isActionsEnabled()}
						events={{
							mouseLeave: this._handleMouseLeaveActions.bind(
								this
							),
						}}
						fieldTypes={fieldTypes}
						items={fieldActions}
						pages={pages}
						portalElement={this._actionableFieldsContainer}
						ref="hoveredFieldActions"
						spritemap={spritemap}
						visible={hoveredFieldActionsVisible}
					/>
				</div>
			);
		}

		rendered() {
			const {focusedField} = this.props;
			const {selectedFieldActions} = this.refs;

			if (this.hasFocusedField()) {
				const {fieldName} = focusedField;

				this.showActions(selectedFieldActions, fieldName);
			}
			else {
				this.hideActions(selectedFieldActions);
			}
		}

		showActions(actions, fieldName) {
			if (actions.state.fieldName !== fieldName) {
				actions.close();
			}

			actions.setState({fieldName});

			if (actions === this.refs.hoveredFieldActions) {
				this.setState({hoveredFieldActionsVisible: true});
			}
		}

		_getClosestParent(node) {
			return dom.closest(node.parentElement, `.ddm-field-container`);
		}

		_handleMouseEnterField(event) {
			const {delegateTarget} = event;

			this._handleMouseLeaveField(event);

			let closestParent = this._getClosestParent(delegateTarget);

			while (closestParent) {
				closestParent.classList.remove(_CSS_HOVERED);

				closestParent = this._getClosestParent(closestParent);
			}

			delegateTarget.classList.add(_CSS_HOVERED);

			const {fieldName} = delegateTarget.dataset;
			const {hoveredFieldActions, selectedFieldActions} = this.refs;

			if (selectedFieldActions.state.fieldName !== fieldName) {
				this.showActions(hoveredFieldActions, fieldName);
			}

			event.stopPropagation();
		}

		_handleMouseLeaveActions(event) {
			const {hoveredFieldActions} = this.refs;
			const {relatedTarget} = event;

			const closestRelatedParent = this._getClosestParent(relatedTarget);

			if (
				closestRelatedParent &&
				closestRelatedParent.dataset.fieldName ===
					hoveredFieldActions.state.fieldName
			) {
				return;
			}

			this._handleMouseLeaveField({
				delegateTarget: event.container,
				relatedTarget,
			});
		}

		_handleMouseLeaveField(event) {
			const {delegateTarget} = event;
			const {hoveredFieldActions, selectedFieldActions} = this.refs;

			if (
				hoveredFieldActions.expanded ||
				!this._hasLeftField(event.relatedTarget)
			) {
				return;
			}

			delegateTarget.classList.remove(_CSS_HOVERED);

			this.hideActions(hoveredFieldActions);

			const closestParent = this._getClosestParent(delegateTarget);

			if (closestParent) {
				closestParent.classList.add(_CSS_HOVERED);

				const {fieldName} = closestParent.dataset;

				if (selectedFieldActions.state.fieldName !== fieldName) {
					this.showActions(hoveredFieldActions, fieldName);
				}
			}

			if (event.stopPropagation) {
				event.stopPropagation();
			}
		}

		_hasLeftField(relatedTarget) {
			return !dom.closest(
				relatedTarget,
				'.dropdown-menu,.ddm-field-actions-container'
			);
		}
	}

	ActionableFields.PROPS = {
		...formBuilderProps,
	};

	ActionableFields.STATE = {
		hoveredFieldActionsVisible: Config.bool().value(false),
	};

	return ActionableFields;
};

export default withActionableFields;
