import * as FormSupport from '../Form/FormSupport.es';
import ClayButton from 'clay-button';
import ClayModal from 'clay-modal';
import Component from 'metal-jsx';
import dom from 'metal-dom';
import {Config} from 'metal-state';
import {EventHandler} from 'metal-events';
import {focusedFieldStructure, pageStructure, ruleStructure} from '../../util/config.es';

class Actions extends Component {
	render() {
		const {spritemap} = this.props;

		return (
			<div class="ddm-field-actions-container" ref="actionsContainer">
				<ClayButton
					editable={true}
					events={{
						click: this._handleDuplicateButtonClicked.bind(this)
					}}
					icon="paste"
					monospaced={true}
					size="sm"
					spritemap={spritemap}
					style="secondary"
				/>

				<ClayButton
					editable={true}
					events={{
						click: this._handleDeleteButtonClicked.bind(this)
					}}
					icon="trash"
					monospaced={true}
					size="sm"
					spritemap={spritemap}
					style="secondary"
				/>
			</div>
		);
	}

	_handleDeleteButtonClicked(event) {
		const indexes = FormSupport.getIndexes(
			dom.closest(event.target, '.col-ddm')
		);

		this.emit('fieldDeleted', {indexes});
	}

	_handleDuplicateButtonClicked(event) {
		const indexes = FormSupport.getIndexes(
			dom.closest(event.target, '.col-ddm')
		);

		this.emit('fieldDuplicated', indexes);
	}
}

const withActionableFields = ChildComponent => {
	class ActionableFields extends Component {
		static STATE = {

			/**
			 * @default undefined
			 * @instance
			 * @memberof ActionableFields
			 * @type {?array<string>}
			 */

			indexes: Config.object()
		}

		static PROPS = {

			/**
			 * @default
			 * @instance
			 * @memberof FormBuilder
			 * @type {?number}
			 */

			activePage: Config.number().value(0),

			/**
			 * @default undefined
			 * @instance
			 * @memberof FormBuilder
			 * @type {?string}
			 */

			defaultLanguageId: Config.string(),

			/**
			 * @default undefined
			 * @instance
			 * @memberof FormBuilder
			 * @type {?string}
			 */

			editingLanguageId: Config.string(),

			/**
			 * @default []
			 * @instance
			 * @memberof Sidebar
			 * @type {?(array|undefined)}
			 */

			fieldTypes: Config.array().value([]),

			/**
			 * @default {}
			 * @instance
			 * @memberof FormBuilder
			 * @type {?object}
			 */

			focusedField: focusedFieldStructure.value({}),

			/**
			 * @default []
			 * @instance
			 * @memberof FormBuilder
			 * @type {?array<object>}
			 */

			pages: Config.arrayOf(pageStructure).value([]),

			/**
			 * @instance
			 * @memberof FormBuilder
			 * @type {string}
			 */

			paginationMode: Config.string().required(),

			/**
			 * @instance
			 * @memberof FormBuilder
			 * @type {string}
			 */

			rules: Config.arrayOf(ruleStructure).required(),

			/**
			 * @default undefined
			 * @instance
			 * @memberof FormRenderer
			 * @type {!string}
			 */

			spritemap: Config.string().required()
		}

		attached() {
			this._eventHandler = new EventHandler();

			this._eventHandler.add(
				this.delegate('mouseenter', '.ddm-field-container', this._handleMouseEnterField.bind(this))
			);
		}

		disposeInternal() {
			super.disposeInternal();

			this._eventHandler.removeAllListeners();
		}

		getEvents() {
			return {
				fieldDeleted: this._handleDeleteRequest.bind(this),
				fieldDuplicated: this._handleDuplicateRequest.bind(this)
			};
		}

		isActionsEnabled() {
			const {defaultLanguageId, editingLanguageId} = this.props;

			return defaultLanguageId === editingLanguageId;
		}

		render() {
			const {spritemap} = this.props;

			return (
				<div>
					<ClayModal
						body={Liferay.Language.get('are-you-sure-you-want-to-delete-this-field')}
						events={{
							clickButton: this._handleDeleteConfirmationModalButtonClicked.bind(this)
						}}
						footerButtons={[
							{
								alignment: 'right',
								label: Liferay.Language.get('dismiss'),
								style: 'primary',
								type: 'close'
							},
							{
								alignment: 'right',
								label: Liferay.Language.get('delete'),
								style: 'primary',
								type: 'button'
							}
						]}
						ref="deleteModal"
						size="sm"
						spritemap={spritemap}
						title={Liferay.Language.get('delete-field-dialog-title')}
					/>

					<ChildComponent {...this.props} events={this.getEvents()} />

					{this.isActionsEnabled() && (
						<Actions
							events={this.getEvents()}
							portalElement={this.element}
							ref="actions"
							spritemap={spritemap}
						/>
					)}
				</div>
			);
		}

		showDeleteConfirmationModal() {
			const {deleteModal} = this.refs;

			deleteModal.show();
		}

		_handleDeleteConfirmationModalButtonClicked(event) {
			const {store} = this.context;
			const {target} = event;
			const {deleteModal} = this.refs;
			const {indexes} = this.state;

			event.stopPropagation();

			deleteModal.emit('hide');

			if (!target.classList.contains('close-modal')) {
				store.emit('fieldDeleted', {indexes});
			}
		}

		_handleDeleteRequest({indexes}) {
			this.setState(
				{
					indexes
				}
			);

			this.showDeleteConfirmationModal();
		}

		_handleDuplicateRequest(indexes) {
			this._handleFieldDuplicated(indexes);
		}

		_handleFieldDuplicated(indexes) {
			const {store} = this.context;

			store.emit('fieldDuplicated', indexes);
		}

		_handleMouseEnterField({delegateTarget}) {
			if (this.isActionsEnabled()) {
				dom.append(delegateTarget, this.refs.actions.element);
			}
		}
	}

	return ActionableFields;
};

export default withActionableFields;