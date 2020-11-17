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

import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import {
	RulesSupport,
	getFieldProperty,
	maxPageIndex,
	pageOptions,
} from 'dynamic-data-mapping-form-builder';
import {PagesVisitor} from 'dynamic-data-mapping-form-renderer';
import React from 'react';

/**
 * RuleList.
 * @extends React.Component
 */

class RuleList extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			pages: props.pages,
			rules: props.rules,
		};
	}

	componentDidMount() {
		document.addEventListener(
			'mouseup',
			this._handleDocumentMouseUp.bind(this),
			true
		);

		this.setState((state) => {
			const rules = RulesSupport.formatRules(
				this.state.pages,
				this._setDataProviderNames(state)
			);

			return {
				...state,
				rules: rules.map((rule) => {
					let logicalOperator;
					let invalidRule = false;

					if (rule['logical-operator']) {
						logicalOperator = rule[
							'logical-operator'
						].toLowerCase();
					}
					else if (rule.logicalOperator) {
						logicalOperator = rule.logicalOperator.toLowerCase();
					}

					invalidRule = RulesSupport.findInvalidRule(rule);

					return {
						...rule,
						actions: rule.actions.map((action) => {
							let newAction;

							if (action.action === 'auto-fill') {
								const {inputs, outputs} = action;

								const inputLabel = Object.values(
									inputs
								).map((input) => this._getFieldLabel(input));
								const outputLabel = Object.values(
									outputs
								).map((output) => this._getFieldLabel(output));

								newAction = {
									...action,
									inputLabel,
									outputLabel,
								};
							}
							else if (action.action == 'jump-to-page') {
								newAction = {
									...action,
									label: this._getJumpToPageLabel(
										rule,
										action
									),
								};
							}
							else {
								newAction = {
									...action,
									label: this._getFieldLabel(action.target),
								};
							}

							return newAction;
						}),
						conditions: rule.conditions.map((condition) => {
							return {
								...condition,
								operands: condition.operands.map(
									(operand, index) => {
										const label = this._getOperandLabel(
											condition.operands,
											index
										);

										return {
											...operand,
											label,
											value: label,
										};
									}
								),
							};
						}),
						invalidRule,
						logicalOperator,
						rulesCardOptions: this._getRulesCardOptions(rule),
					};
				}),
			};
		});
	}

	componentWillUnmount() {
		document.removeEventListener(
			'mouseup',
			this._handleDocumentMouseUp.bind(this)
		);
	}

	render(props) {
		return (
			<div className="form-builder-rule-builder-container">
				<h1 className="form-builder-section-title text-default">
					{Liferay.Language.get('rule-builder')}
				</h1>

				<div className="liferay-ddm-form-rule-rules-list-container">
					<RuleList.List {...props} />
				</div>
			</div>
		);
	}

	_getDataProviderName(id) {
		const {dataProvider} = this.state;

		return dataProvider.find((data) => data.uuid == id).label;
	}

	_getFieldLabel(fieldName) {
		const {pages} = this.state;

		return getFieldProperty(pages, fieldName, 'label') || fieldName;
	}

	_getJumpToPageLabel(rule, action) {
		const {pages} = this.state;
		let pageLabel = '';

		const fieldTarget = (parseInt(action.target, 10) + 1).toString();
		const maxPageIndexRes = maxPageIndex(rule.conditions, pages);
		const pageOptionsList = pageOptions(pages, maxPageIndexRes);
		const selectedPage = pageOptionsList.find((option) => {
			return option.value == fieldTarget;
		});

		if (selectedPage) {
			pageLabel = selectedPage.label;
		}

		return pageLabel;
	}

	_getOperandLabel(operands, index) {
		let label = '';
		const operand = operands[index];

		if (operand.type === 'field') {
			label = this._getFieldLabel(operand.value);
		}
		else if (operand.type === 'user') {
			label = Liferay.Language.get('user');
		}
		else if (operand.type !== 'field') {
			const fieldType = RulesSupport.getFieldType(
				operands[0].value,
				this.state.pages
			);

			if (
				fieldType === 'checkbox_multiple' ||
				fieldType === 'radio' ||
				fieldType === 'select'
			) {
				label = this._getOptionLabel(operands[0].value, operand.value);
			}
			else if (operand.type === 'json') {
				label = '';

				const operandValueJSON = JSON.parse(operand.value);

				for (const key in operandValueJSON) {
					const keyLabel = this._getPropertyLabel(
						operands[0].value,
						'rows',
						key
					);

					const valueLabel = this._getPropertyLabel(
						operands[0].value,
						'columns',
						operandValueJSON[key]
					);

					label += keyLabel + ':' + valueLabel + ', ';
				}

				const lastCommaPosition = label.lastIndexOf(', ');

				if (lastCommaPosition != -1) {
					label = label.substr(0, lastCommaPosition);
				}
			}
			else {
				label = operand.value;
			}
		}
		else {
			label = operand.value;
		}

		return label;
	}

	_getOptionLabel(fieldName, optionValue) {
		return this._getPropertyLabel(fieldName, 'options', optionValue);
	}

	_getPropertyLabel(fieldName, propertyName, propertyValue) {
		const {pages} = this.state;

		let fieldLabel = null;

		if (pages && propertyValue) {
			const visitor = new PagesVisitor(pages);

			visitor.findField((field) => {
				let found = false;

				if (field.fieldName === fieldName && field.options) {
					field[propertyName].some((property) => {
						if (property.value == propertyValue) {
							fieldLabel = property.label;

							found = true;
						}

						return found;
					});
				}

				return found;
			});
		}

		return fieldLabel ? fieldLabel : propertyValue;
	}

	_getRulesCardOptions(rule) {
		const hasNestedCondition = this._hasNestedCondition(rule);

		const rulesCardOptions = [
			{
				disabled: hasNestedCondition,
				label: Liferay.Language.get('edit'),
				settingsItem: 'edit',
			},
			{
				confirm: hasNestedCondition,
				label: Liferay.Language.get('delete'),
				settingsItem: 'delete',
			},
		];

		return rulesCardOptions;
	}

	_handleDocumentMouseUp({target}) {
		const dropdownSettings = target.closest('.ddm-rule-list-settings');

		if (dropdownSettings) {
			return;
		}

		this.setState({
			dropdownExpandedIndex: -1,
		});
	}

	_handleDropdownClicked(event) {
		event.preventDefault();

		const {dropdownExpandedIndex} = this.state;
		const ruleNode = event.delegateTarget.closest('.component-action');

		let ruleIndex = parseInt(ruleNode.dataset.ruleIndex, 10);

		if (ruleIndex === dropdownExpandedIndex) {
			ruleIndex = -1;
		}

		this.setState({
			dropdownExpandedIndex: ruleIndex,
		});
	}

	_handleRuleCardClicked({data, target}) {
		const cardElement = target.element.closest('[data-card-id]');
		const ruleId = parseInt(cardElement.getAttribute('data-card-id'), 10);

		if (data.item.settingsItem === 'edit' && this.props.onRuleEdited) {
			this.props.onRuleEdited({
				ruleId,
			});
		}
		else if (
			data.item.settingsItem === 'delete' &&
			this.props.onRuleDeleted
		) {
			if (
				!data.item.confirm ||
				confirm(
					Liferay.Language.get(
						'you-cannot-create-rules-with-nested-functions.-are-you-sure-you-want-to-delete-this-rule'
					)
				)
			) {
				this.props.onRuleDeleted({
					ruleId,
				});
			}
		}
	}

	_hasNestedCondition(rule) {
		return (
			rule.conditions.find((condition) =>
				condition.operands.find((operand) =>
					operand.value.match(/[aA-zZ]+[(].*[,]+.*[)]/)
				)
			) !== undefined
		);
	}

	_setDataProviderNames({rules}) {
		if (this.state.dataProvider) {
			for (let rule = 0; rule < rules.length; rule++) {
				const actions = rules[rule].actions;

				actions.forEach((action) => {
					if (action.action === 'auto-fill') {
						const dataProviderName = this._getDataProviderName(
							action.ddmDataProviderInstanceUUID
						);

						action.dataProviderName = dataProviderName;
					}
				});
			}
		}

		return rules;
	}
}

/**
 * Prints the DDM form card rule.
 */
const EmptyList = ({message}) => {
	return (
		<div className="main-content-body">
			<div className="sheet taglib-empty-result-message">
				<div className="taglib-empty-result-message-header"></div>

				{message && (
					<div className="sheet-text text-center text-muted">
						<p className="text-default">{message}</p>
					</div>
				)}
			</div>
		</div>
	);
};

/**
 * Prints the DDM form card rule.
 */
const Label = ({content}) => {
	return (
		<span
			className="label label-lg label-secondary"
			data-original-title={content}
			title={content}
		>
			<span className="text-truncate-inline">
				<span className="text-truncate">{content}</span>
			</span>
		</span>
	);
};

/**
 * Prints Rules Conditions.
 */
const Condition = ({
	label: operandLabel,
	type: operandType,
	value: operandValue,
}) => {
	const SubCondition = ({operandType}) => {
		const translationMap = new Map([
			['user', Liferay.Language.get('user')],
			['field', Liferay.Language.get('field')],
			['list', Liferay.Language.get('list')],
		]);

		if (
			[
				'double',
				'integer',
				'json',
				'option',
				'string',
				'text',
				'numeric',
			].includes(operandType)
		) {
			return <span>{Liferay.Language.get('value')} </span>;
		}

		if (['user', 'field', 'list'].includes(operandType)) {
			return <span>{translationMap.get(operandType)} </span>;
		}

		return null;
	};

	return (
		<div className="ddm-condition-container">
			<SubCondition operandType={operandType} />

			<Label content={operandLabel || operandValue} />
		</div>
	);
};

/**
 * Prints the show action.
 */
const Action = ({action, dataProviderName, expression, label, outputLabel}) => {
	if (action === 'auto-fill') {
		const dataProviderOutputFields = outputLabel.map(
			(output, outputIndex) => {
				<>
					<Label content={output} />
					{outputIndex > outputLabel.length - 1 ? ',' : ''}
				</>;
			}
		);

		return (
			<>
				{outputLabel.map((output, outputIndex) => {
					<>
						<Label content={output} />
						{outputIndex > outputLabel.length - 1 ? ',' : ''}
					</>;
				})}

				<span>
					<b>
						{Liferay.Util.sub(
							Liferay.Language.get(
								'autofill-x-from-data-provider-x'
							),
							[dataProviderOutputFields, dataProviderName]
						)}
					</b>
				</span>
			</>
		);
	}

	if (action === 'calculate') {
		const expressionLabel = () => <Label content={expression} />;
		const targetLabel = () => <Label content={label} />;

		return (
			<span>
				<b>
					{Liferay.Util.sub(
						Liferay.Language.get('calculate-field-x-as-x'),
						[expressionLabel, targetLabel]
					)}
				</b>
			</span>
		);
	}

	const SubLabel = ({label, text}) => {
		return (
			<>
				<span>
					<b>{text}</b>
				</span>

				<Label content={label} />
			</>
		);
	};

	if (action === 'enable') {
		return <SubLabel label={label} text={Liferay.Language.get(`enable`)} />;
	}

	if (action === 'jump-to-page') {
		return (
			<SubLabel
				label={label}
				text={Liferay.Language.get(`jump-to-page`)}
			/>
		);
	}

	if (action === 'require') {
		return (
			<SubLabel label={label} text={Liferay.Language.get(`require`)} />
		);
	}

	if (action === 'show') {
		return <SubLabel label={label} text={Liferay.Language.get(`show`)} />;
	}

	return null;
};

const List = ({

	// dropdownExpandedIndex,

	onDropdownClicked,

	// onRuleCardClicked,

	rules,
	spritemap,
	strings,
}) => {
	if (typeof rules === 'undefined' || !rules.length) {
		return (
			<EmptyList
				message={Liferay.Language.get(
					'there-are-no-rules-yet-click-on-plus-icon-below-to-add-the-first'
				)}
			/>
		);
	}

	const InvalidRule = ({spritemap}) => {
		const titleMessage = Liferay.Language.get('due-to-missing-fields');

		const labelMessage = Liferay.Language.get('broken-rule');

		return (
			<div className="invalid-rule" title={titleMessage}>
				<ClayLabel displayType="danger" spritemap={spritemap}>
					{labelMessage}
				</ClayLabel>
			</div>
		);
	};

	const andStr = Liferay.Language.get('and');
	const orStr = Liferay.Language.get('or');

	return (
		<div className="ddm-rule-list-container form-builder-rule-list">
			<ul className="ddm-form-body-content form-builder-rule-builder-rules-list tabular-list-group">
				{rules.map((rule, ruleIndex) => (
					<li className="list-group-item" key={ruleIndex}>
						<div className="clamp-horizontal list-group-item-content">
							<p className="form-builder-rule-builder-rule-description text-default">
								<b> {Liferay.Language.get('if')}</b>
								{rule.conditions.map(
									({operands, operator}, conditionIndex) => (
										<>
											<Condition {...operands[0]} />

											<b className="text-lowercase">
												<em> {strings[operator]} </em>
											</b>

											{operands[1] && (
												<Condition {...operands[1]} />
											)}

											{conditionIndex >
												rule.conditions.length - 1 && (
												<>
													<br />
													<b>
														{` ${
															rule.logicalOperator ===
															'and'
																? andStr
																: orStr
														} `}
													</b>
												</>
											)}
										</>
									)
								)}

								<br />

								{rule.actions.map((action, actionIndex) => (
									<>
										<Action {...action} />
										{actionIndex <
											rule.actions.length - 1 && (
											<>
												, <br />
												<b>
													{` ${Liferay.Language.get(
														'and'
													)} `}
												</b>
											</>
										)}
									</>
								))}
							</p>
						</div>

						<div className="list-group-item-field">
							<div className="card-col-field">
								{rule.invalidRule && (
									<InvalidRule spritemap={spritemap} />
								)}

								<div className="dropdown dropdown-action">
									<div
										className="ddm-rule-list-settings"
										data-card-id={ruleIndex}
									>
										{/* <ClayActionsDropdown 
                                                items={rule.rulesCardOptions}
                                                expanded={dropdownExpandedIndex === ruleIndex} 
                                                itemClicked={onRuleCardClicked}
                                                spritemap={spritemap}
                                            /> */}

										<button
											className="component-action cursor-pointer dropdown-toggle"
											data-rule-index={ruleIndex}
											onClick={onDropdownClicked}
										>
											<ClayIcon
												spritemap={spritemap}
												symbol="ellipsis-v"
											/>
										</button>
									</div>
								</div>
							</div>
						</div>
					</li>
				))}
			</ul>
		</div>
	);
};

RuleList.List = List;

RuleList.displayName = 'RuleList';

export default RuleList;
