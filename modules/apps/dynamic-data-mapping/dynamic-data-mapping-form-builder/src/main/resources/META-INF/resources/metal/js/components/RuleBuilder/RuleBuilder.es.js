import {Config} from 'metal-state';
import dom from 'metal-dom';
import {EventHandler} from 'metal-events';
import Component from 'metal-jsx';
import RuleList from '../../components/RuleList/index.es';
import RuleEditor from '../../components/RuleEditor/index.es';
import {makeFetch} from '../../util/fetch.es';

/**
 * Builder.
 * @extends Component
 */

class RuleBuilder extends Component {
	static PROPS = {
		dataProviderInstanceParameterSettingsURL: Config.string().required(),

		dataProviderInstancesURL: Config.string().required(),

		functionsMetadata: Config.object(
			{
				number: Config.arrayOf(
					Config.shapeOf(
						{
							label: Config.string(),
							name: Config.string(),
							parameterTypes: Config.array(),
							returnType: Config.string()
						}
					)
				),
				text: Config.arrayOf(
					Config.shapeOf(
						{
							label: Config.string(),
							name: Config.string(),
							parameterTypes: Config.array(),
							returnType: Config.string()
						}
					)
				),
				user: Config.arrayOf(
					Config.shapeOf(
						{
							label: Config.string(),
							name: Config.string(),
							parameterTypes: Config.array(),
							returnType: Config.string()
						}
					)
				)
			}
		),

		functionsURL: Config.string().required(),

		pages: Config.array().required(),

		rolesURL: Config.string().required(),

		originalRule: Config.object(),

		rules: Config.arrayOf(
			Config.shapeOf(
				{
					actions: Config.arrayOf(
						Config.shapeOf(
							{
								action: Config.string(),
								label: Config.string(),
								target: Config.string()
							}
						)
					),
					conditions: Config.arrayOf(
						Config.shapeOf(
							{
								operands: Config.arrayOf(
									Config.shapeOf(
										{
											label: Config.string(),
											repeatable: Config.bool(),
											type: Config.string(),
											value: Config.string()
										}
									)
								),
								operator: Config.string()
							}
						)
					),
					logicalOperator: Config.string()
				}
			)
		),

		/**
		 * The path to the SVG spritemap file containing the icons.
		 * @default undefined
		 * @instance
		 * @memberof Form
		 * @type {!string}
		 */

		spritemap: Config.string().required()
	}

	static STATE = {
		dataProvider: Config.arrayOf(
			Config.shapeOf(
				{
					id: Config.string(),
					name: Config.string(),
					uuid: Config.string()
				}
			)
		).internal(),

		/**
		 * @default
		 * @instance
		 * @memberof RuleBuilder
		 *
		 */

		index: Config.number(),

		mode: Config.oneOf(['view', 'edit', 'create']).value('view'),

		roles: Config.arrayOf(
			Config.shapeOf(
				{
					id: Config.string(),
					name: Config.string()
				}
			)
		).internal(),

		rules: Config.arrayOf(
			Config.shapeOf(
				{
					actions: Config.arrayOf(
						Config.shapeOf(
							{
								action: Config.string(),
								label: Config.string(),
								target: Config.string()
							}
						)
					),
					conditions: Config.arrayOf(
						Config.shapeOf(
							{
								operands: Config.arrayOf(
									Config.shapeOf(
										{
											label: Config.string(),
											repeatable: Config.bool(),
											type: Config.string(),
											value: Config.string()
										}
									)
								),
								operator: Config.string()
							}
						)
					),
					logicalOperator: Config.string()
				}
			)
		)
	};

	/**
	 * Continues the propagation of event.
	 * @param {!Event} event
	 * @private
	 */

	created() {
		this._eventHandler = new EventHandler();

		this._fetchDataProvider();
		this._fetchRoles();
	}

	_fetchRoles() {
		const {rolesURL} = this.props;

		makeFetch(
			{
				method: 'GET',
				url: rolesURL
			}
		).then(
			responseData => {
				if (!this.isDisposed()) {
					this.setState(
						{
							roles: responseData.map(
								data => {
									return {
										...data,
										label: data.name,
										value: data.id
									};
								}
							)
						}
					);
				}
			}
		).catch(
			error => {
				throw new Error(error);
			}
		);
	}

	_fetchDataProvider() {
		const {dataProviderInstancesURL} = this.props;

		makeFetch(
			{
				method: 'GET',
				url: dataProviderInstancesURL
			}
		).then(
			responseData => {
				if (!this.isDisposed()) {
					this.setState(
						{
							dataProvider: responseData.map(
								data => {
									return {
										...data,
										label: data.name,
										value: data.id
									};
								}
							)
						}
					);
				}
			}
		).catch(
			error => {
				throw new Error(error);
			}
		);
	}

	/**
	 * Continues the propagation of event.
	 * @param {!Event} event
	 * @private
	 */

	_showRuleEdition() {
		this.setState(
			{
				mode: 'edit'
			}
		);
	}

	_showRuleCreation() {
		this.setState(
			{
				mode: 'create',
				rules: []
			}
		);
	}

	_showRuleList() {
		this.setState(
			{
				mode: 'view'
			}
		);
	}

	/**
	 * Show the rule screen to create a new rule
	 * @param {!Event} event
	 * @private
	 */

	_handleAddRuleClick(event) {
		this._showRuleCreation();

		this._hideAddRuleButton(event.delegateTarget);
	}

	_handleRuleAdded(event) {
		this.emit(
			'ruleAdded',
			{
				...event
			}
		);

		this._showRuleList();
	}

	_handleRuleCanceled(event) {
		const rules = this.props.rules.map(
			(rule, ruleIndex) => {
				return this.index === ruleIndex ? this.originalRule : rule;
			}
		);

		this.setState(
			{
				mode: 'view',
				rules
			}
		);
	}

	_handleRuleDeleted({ruleId}) {
		this.emit(
			'ruleDeleted',
			{
				ruleId
			}
		);
	}

	_handleRuleEdited({ruleId}) {
		const {rules} = this.props;

		ruleId = parseInt(ruleId, 10);

		this.setState(
			{
				index: ruleId,
				originalRule: rules[ruleId]
			}
		);

		this._showRuleEdition();
	}

	_handleRuleSaveEdition(event) {
		this.emit(
			'ruleSaveEdition',
			{
				...event,
				ruleId: event.ruleEditedIndex
			}
		);

		this._showRuleList();
	}

	/**
	 * Continues the propagation of event.
	 * @param {!Event} event
	 * @private
	 */

	_hideAddRuleButton(element) {
		dom.addClasses(element, 'hide');
	}

	syncVisible(visible) {
		super.syncVisible(visible);

		if (visible) {
			this._eventHandler.add(
				dom.on('#addFieldButton', 'click', this._handleAddRuleClick.bind(this))
			);
		}
		else {
			this._eventHandler.removeAllListeners();
		}
	}

	/**
	 * Continues the propagation of event.
	 * @param {!Event} event
	 * @private
	 */

	disposeInternal() {
		super.disposeInternal();

		this._eventHandler.removeAllListeners();
	}

	rendered() {
		const {mode} = this.state;
		const {visible} = this.props;

		if (visible) {
			const addButton = document.querySelector('#addFieldButton');

			if (mode === 'create' || mode === 'edit') {
				addButton.classList.add('hide');
			}
			else {
				addButton.classList.remove('hide');
			}
		}
	}

	/**
	 * Continues the propagation of event.
	 * @param {!Event} event
	 * @private
	 */

	render() {
		const RuleBuilderEvents = {
			ruleAdded: this._handleRuleAdded.bind(this),
			ruleCancel: this._handleRuleCanceled.bind(this),
			ruleDeleted: this._handleRuleDeleted.bind(this),
			ruleEdited: this._handleRuleEdited.bind(this)
		};

		const RuleEditionEvents = {
			ruleAdded: this._handleRuleSaveEdition.bind(this),
			ruleCancel: this._handleRuleCanceled.bind(this)
		};

		const {
			dataProviderInstanceParameterSettingsURL,
			dataProviderInstancesURL,
			functionsMetadata,
			functionsURL,
			pages,
			rules,
			spritemap
		} = this.props;

		const {
			dataProvider,
			roles
		} = this.state;

		return (
			<div class="container">
				{this.state.mode === 'create' && (
					<RuleEditor
						actions={[]}
						conditions={[]}
						dataProvider={dataProvider}
						dataProviderInstanceParameterSettingsURL={dataProviderInstanceParameterSettingsURL}
						dataProviderInstancesURL={dataProviderInstancesURL}
						events={RuleBuilderEvents}
						functionsMetadata={functionsMetadata}
						functionsURL={functionsURL}
						key={'create'}
						pages={pages}
						ref="RuleEditor"
						roles={roles}
						spritemap={spritemap}
					/>
				)}
				{this.state.mode === 'edit' && (
					<RuleEditor

						dataProviderInstanceParameterSettingsURL={dataProviderInstanceParameterSettingsURL}
						dataProviderInstancesURL={dataProviderInstancesURL}
						events={RuleEditionEvents}
						functionsMetadata={functionsMetadata}
						functionsURL={functionsURL}
						key={'edit'}
						pages={pages}
						ref="RuleEditor"
						roles={roles}
						rule={rules[this.state.index]}
						ruleEditedIndex={this.state.index}
						spritemap={spritemap}
					/>
				)}
				{this.state.mode === 'view' && (
					<RuleList
						dataProvider={dataProvider}
						events={RuleBuilderEvents}
						pages={pages}
						roles={roles}
						rules={rules}
						spritemap={spritemap}
					/>
				)}
			</div>
		);
	}
}

export default RuleBuilder;
export {RuleBuilder};