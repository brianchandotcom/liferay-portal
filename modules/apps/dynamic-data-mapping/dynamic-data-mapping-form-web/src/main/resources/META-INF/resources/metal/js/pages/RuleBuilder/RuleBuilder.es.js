import {Config} from 'metal-state';
import dom from 'metal-dom';
import {EventHandler} from 'metal-events';
import Component from 'metal-jsx';
import RuleList from '../../components/RuleList/index.es';
import RuleEditor from '../../components/RuleEditor/index.es';

/**
 * Builder.
 * @extends Component
 */

class RuleBuilder extends Component {
	static PROPS = {
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

		pages: Config.array().required(),

		rolesURL: Config.string().required(),

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

		/**
		 * @default
		 * @instance
		 * @memberof RuleBuilder
		 *
		 */

		mode: Config.oneOf(['view', 'edit', 'create']).value('view')
	};

	/**
	 * Continues the propagation of event.
	 * @param {!Event} event
	 * @private
	 */

	created() {
		this._eventHandler = new EventHandler();
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
				mode: 'create'
			}
		);
	}

	/**
	 * Show the rule screen to edit an existing rule. For now, this method does not receive the rule data for edition.
	 * @param {!Event} event
	 * @private
	 */

	_handleEditRuleClicked() {
		this._showRuleEdition();
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
				dom.on('#addFieldButton', 'click', this._handleAddRuleClick.bind(this)),
				dom.on('.rule-card-edit', 'click', this._handleEditRuleClicked.bind(this))
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
		const {pages, rules, spritemap} = this.props;

		return (
			<div class="container">
				{this.state.mode === 'create' && (
					<RuleEditor dataProviderUrl={this.props.dataProviderInstancesURL} functionsMetadata={this.props.functionsMetadata} key={'create'} pages={pages} rolesUrl={this.props.rolesURL} spritemap={spritemap} />
				)}
				{this.state.mode === 'edit' && (
					<RuleEditor key={'edit'} pages={pages} rules={rules} spritemap={spritemap} />
				)}
				{this.state.mode === 'view' && (
					<RuleList pages={pages} rules={rules} spritemap={spritemap} />
				)}
			</div>
		);
	}
}

export default RuleBuilder;
export {RuleBuilder};