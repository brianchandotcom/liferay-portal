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
import dom from 'metal-dom';
import {EventHandler} from 'metal-events';
import Component from 'metal-jsx';
import {Config} from 'metal-state';

import {isFieldSetChild} from '../../util/fieldSupport.es';
import FieldActionsDropDown from './FieldActionsDropDown.es';
import formBuilderProps from './props.es';

const _CSS_HOVERED = 'hovered';

const ACTIONABLE_FIELDS_CONTAINER = 'ddm-actionable-fields-container';

const withActionableFields = (ChildComponent) => {
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
			const {activePage, hoveredFieldActionsVisible} = this.state;
			const {fieldActions, fieldTypes, pages, spritemap} = this.props;

			return (
				<div>
					<ChildComponent {...this.props} />

					<FieldActionsDropDown
						activePage={activePage}
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
						activePage={activePage}
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
			const {focusedField, pages} = this.props;
			const {hoveredFieldActions, selectedFieldActions} = this.refs;

			if (this.hasFocusedField()) {
				const {fieldName} = focusedField;

				this.showActions(selectedFieldActions, fieldName);
			}
			else {
				this.hideActions(selectedFieldActions);
			}

			const hoveredFieldName = hoveredFieldActions.state.fieldName;

			if (!FormSupport.findFieldByFieldName(pages, hoveredFieldName)) {
				this.hideActions(hoveredFieldActions);

				const hoveredNode = this._getHoveredNode();

				if (hoveredNode) {
					hoveredNode.classList.remove(_CSS_HOVERED);
				}
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

		_getHoveredNode() {
			return this.element.querySelector(
				`.ddm-field-container.${_CSS_HOVERED}`
			);
		}

		_handleMouseEnterField(event) {
			const {pages} = this.props;
			const {delegateTarget} = event;
			const {dispatch} = this.context;
			const {fieldName} = delegateTarget.dataset;
			const {hoveredFieldActions, selectedFieldActions} = this.refs;
			const activePage = parseInt(
				dom.closest(event.delegateTarget, '[data-ddm-page]').dataset
					.ddmPage,
				10
			);
			this.setState({activePage});

			if (
				selectedFieldActions.state.fieldName === fieldName ||
				isFieldSetChild(pages, fieldName)
			) {
				this._handleMouseLeaveField(event);

				return;
			}

			if (fieldName !== selectedFieldActions.state.fieldName) {
				selectedFieldActions.close();
			}

			const hoveredNode = this._getHoveredNode();

			if (hoveredNode) {
				hoveredNode.classList.remove(_CSS_HOVERED);

				dispatch('fieldBlurred', {});
			}

			delegateTarget.classList.add(_CSS_HOVERED);

			dispatch('fieldHovered', {fieldName});

			this.showActions(hoveredFieldActions, fieldName);

			event.stopPropagation();
		}

		_handleMouseLeaveActions(event) {
			const {delegateTarget, relatedTarget} = event;
			const {fieldName} = delegateTarget.dataset;
			const {hoveredFieldActions} = this.refs;
			const {pages} = this.props;

			if (isFieldSetChild(pages, fieldName)) {
				return;
			}

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

			this._handleClosestParent({
				delegateTarget,
				hoveredFieldActions,
				selectedFieldActions,
			});

			if (event.stopPropagation) {
				event.stopPropagation();
			}
		}

		_handleClosestParent({
			delegateTarget,
			hoveredFieldActions,
			selectedFieldActions,
		}) {
			const {pages} = this.props;
			const closestParent = this._getClosestParent(delegateTarget);

			if (closestParent) {
				const {fieldName} = closestParent.dataset;

				if (
					selectedFieldActions.state.fieldName !== fieldName &&
					!isFieldSetChild(pages, fieldName)
				) {
					closestParent.classList.add(_CSS_HOVERED);

					this.showActions(hoveredFieldActions, fieldName);
				}
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
		activePage: Config.number(),
		hoveredFieldActionsVisible: Config.bool().value(false),
	};

	return ActionableFields;
};

export default withActionableFields;
