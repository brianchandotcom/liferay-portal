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
		pages: Config.array().required(),

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

		mode: Config.oneOf(['view', 'edit']).value('view')
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
		this._showRuleEdition();

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

	/**
	 * Continues the propagation of event.
	 * @param {!Event} event
	 * @private
	 */

	attached() {
		this._eventHandler.add(
			dom.on('#addFieldButton', 'click', this._handleAddRuleClick.bind(this)),
			dom.on('.rule-card-edit', 'click', this._handleEditRuleClicked.bind(this))
		);
	}

	/**
	 * Continues the propagation of event.
	 * @param {!Event} event
	 * @private
	 */

	dispose() {
		this._eventHandler.removeAllListeners();
	}

	/**
	 * Continues the propagation of event.
	 * @param {!Event} event
	 * @private
	 */

	render() {
		const {spritemap} = this.props;

		let ruleScreen;

		if (this.state.mode === 'edit') {
			ruleScreen = <RuleEditor />;
		}
		else {
			ruleScreen = <RuleList pages={this.props.pages} rules={this.props.rules} spritemap={spritemap} />;
		}

		return (
			<div class="container">
				{ruleScreen}
			</div>
		);
	}
}

export default RuleBuilder;
export {RuleBuilder};