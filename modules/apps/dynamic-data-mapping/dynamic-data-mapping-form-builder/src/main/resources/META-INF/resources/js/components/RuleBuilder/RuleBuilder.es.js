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

import {makeFetch} from 'dynamic-data-mapping-form-renderer/js/util/fetch.es';
import Component from 'metal-jsx';
import {Config} from 'metal-state';

import RuleEditor from '../../components/RuleEditor/RuleEditor.es';
import RuleList from '../../components/RuleList/RuleList.es';

/**
 * Builder.
 * @extends Component
 */

class RuleBuilder extends Component {
	created() {
		this._fetchDataProvider();
		this._fetchRoles();
	}

	isViewMode() {
		const {mode} = this.state;

		return mode === 'view';
	}

	render() {
		const {
			dataProviderInstanceParameterSettingsURL,
			dataProviderInstancesURL,
			functionsMetadata,
			functionsURL,
			pages,
			spritemap
		} = this.props;

		const {dataProvider, index, mode, roles, rules} = this.state;

		return (
			<div class="container">
				{mode === 'create' && (
					<RuleEditor
						actions={[]}
						conditions={[]}
						dataProvider={dataProvider}
						dataProviderInstanceParameterSettingsURL={
							dataProviderInstanceParameterSettingsURL
						}
						dataProviderInstancesURL={dataProviderInstancesURL}
						events={{
							ruleAdded: this._handleRuleAdded.bind(this),
							ruleCancelled: this._handleRuleCancelled.bind(this),
							ruleDeleted: this._handleRuleDeleted.bind(this),
							ruleEdited: this._handleRuleEdited.bind(this)
						}}
						functionsMetadata={functionsMetadata}
						functionsURL={functionsURL}
						key={'create'}
						pages={pages}
						ref="RuleEditor"
						roles={roles}
						spritemap={spritemap}
					/>
				)}
				{mode === 'edit' && (
					<RuleEditor
						dataProvider={dataProvider}
						dataProviderInstanceParameterSettingsURL={
							dataProviderInstanceParameterSettingsURL
						}
						dataProviderInstancesURL={dataProviderInstancesURL}
						events={{
							ruleAdded: this._handleRuleSaved.bind(this),
							ruleCancelled: this._handleRuleCancelled.bind(this)
						}}
						functionsMetadata={functionsMetadata}
						functionsURL={functionsURL}
						key={'edit'}
						pages={pages}
						ref="RuleEditor"
						roles={roles}
						rule={rules[index]}
						ruleEditedIndex={index}
						spritemap={spritemap}
					/>
				)}
				{mode === 'view' && (
					<RuleList
						dataProvider={dataProvider}
						events={{
							ruleAdded: this._handleRuleAdded.bind(this),
							ruleCancelled: this._handleRuleCancelled.bind(this),
							ruleDeleted: this._handleRuleDeleted.bind(this),
							ruleEdited: this._handleRuleEdited.bind(this)
						}}
						pages={pages}
						ref="RuleList"
						roles={roles}
						rules={rules}
						spritemap={spritemap}
					/>
				)}
			</div>
		);
	}

	showRuleCreation() {
		this.setState({
			mode: 'create'
		});
	}

	showRuleList() {
		this.setState({
			mode: 'view'
		});
	}

	willReceiveProps({rules}) {
		if (rules && rules.newVal) {
			this.setState({
				rules: rules.newVal
			});
		}
	}

	_fetchDataProvider() {
		const {dataProviderInstancesURL} = this.props;

		makeFetch({
			method: 'GET',
			url: dataProviderInstancesURL
		})
			.then(responseData => {
				if (!this.isDisposed()) {
					this.setState({
						dataProvider: responseData.map(data => {
							return {
								...data,
								label: data.name,
								value: data.id
							};
						})
					});
				}
			})
			.catch(error => {
				throw new Error(error);
			});
	}

	_fetchRoles() {
		const {rolesURL} = this.props;

		makeFetch({
			method: 'GET',
			url: rolesURL
		})
			.then(responseData => {
				if (!this.isDisposed()) {
					this.setState({
						roles: responseData.map(data => {
							return {
								...data,
								label: data.name,
								value: data.id
							};
						})
					});
				}
			})
			.catch(error => {
				throw new Error(error);
			});
	}

	_handleRuleAdded(event) {
		const {dispatch} = this.context;

		dispatch('ruleAdded', event);

		this.showRuleList();
	}

	_handleRuleCancelled() {
		const {dispatch} = this.context;
		const {index} = this.state;
		const rules = this.state.rules.map((rule, ruleIndex) => {
			return index === ruleIndex ? this.state.originalRule : rule;
		});

		this.setState({
			mode: 'view',
			rules
		});

		dispatch('ruleCancelled');
	}

	_handleRuleDeleted({ruleId}) {
		const {dispatch} = this.context;

		dispatch('ruleDeleted', {
			ruleId
		});
	}

	_handleRuleEdited({ruleId}) {
		const {rules} = this.state;

		ruleId = parseInt(ruleId, 10);

		this.setState({
			index: ruleId,
			mode: 'edit',
			originalRule: JSON.parse(JSON.stringify(rules[ruleId]))
		});
	}

	_handleRuleSaved(event) {
		const {dispatch} = this.context;

		dispatch('ruleSaved', {
			...event,
			ruleId: event.ruleEditedIndex
		});

		this.showRuleList();
	}

	_setRulesValueFn() {
		return this.props.rules;
	}
}

RuleBuilder.PROPS = {
	dataProviderInstanceParameterSettingsURL: Config.string().required(),

	dataProviderInstancesURL: Config.string().required(),

	functionsMetadata: Config.object({
		number: Config.arrayOf(
			Config.shapeOf({
				label: Config.string(),
				name: Config.string(),
				parameterTypes: Config.array(),
				returnType: Config.string()
			})
		),
		text: Config.arrayOf(
			Config.shapeOf({
				label: Config.string(),
				name: Config.string(),
				parameterTypes: Config.array(),
				returnType: Config.string()
			})
		),
		user: Config.arrayOf(
			Config.shapeOf({
				label: Config.string(),
				name: Config.string(),
				parameterTypes: Config.array(),
				returnType: Config.string()
			})
		)
	}),

	functionsURL: Config.string().required(),

	pages: Config.array().required(),

	rolesURL: Config.string().required(),

	rules: Config.arrayOf(
		Config.shapeOf({
			actions: Config.arrayOf(
				Config.shapeOf({
					action: Config.string(),
					label: Config.string(),
					target: Config.string()
				})
			),
			conditions: Config.arrayOf(
				Config.shapeOf({
					operands: Config.arrayOf(
						Config.shapeOf({
							label: Config.string(),
							repeatable: Config.bool(),
							type: Config.string(),
							value: Config.string()
						})
					),
					operator: Config.string()
				})
			),
			logicalOperator: Config.string()
		})
	).value([]),

	/**
	 * The path to the SVG spritemap file containing the icons.
	 * @default undefined
	 * @instance
	 * @memberof Form
	 * @type {!string}
	 */

	spritemap: Config.string().required()
};

RuleBuilder.STATE = {
	dataProvider: Config.arrayOf(
		Config.shapeOf({
			id: Config.string(),
			name: Config.string(),
			uuid: Config.string()
		})
	).internal(),

	/**
	 * @default
	 * @instance
	 * @memberof RuleBuilder
	 *
	 */

	index: Config.number(),

	mode: Config.oneOf(['view', 'edit', 'create']).value('view'),

	originalRule: Config.object(),

	roles: Config.arrayOf(
		Config.shapeOf({
			id: Config.string(),
			name: Config.string()
		})
	).internal(),

	rules: Config.arrayOf(
		Config.shapeOf({
			actions: Config.arrayOf(
				Config.shapeOf({
					action: Config.string(),
					label: Config.string(),
					target: Config.string()
				})
			),
			conditions: Config.arrayOf(
				Config.shapeOf({
					operands: Config.arrayOf(
						Config.shapeOf({
							label: Config.string(),
							repeatable: Config.bool(),
							type: Config.string(),
							value: Config.string()
						})
					),
					operator: Config.string()
				})
			),
			logicalOperator: Config.string()
		})
	).valueFn('_setRulesValueFn')
};

export default RuleBuilder;
export {RuleBuilder};
