import * as FormSupport from '../Form/FormSupport.es';
import ClayButton from 'clay-button';
import ClayModal from 'clay-modal';
import Component from 'metal-jsx';
import dom from 'metal-dom';
import {Config} from 'metal-state';
import {EventHandler} from 'metal-events';

class Actions extends Component {

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

		_handleFieldDuplicated(indexes) {
			const {store} = this.context;

			store.emit('fieldDuplicated', indexes);
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

		_handleMouseEnterField({delegateTarget}) {
			if (this.isActionsEnabled()) {
				dom.append(delegateTarget, this.refs.actions.element);
			}
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

		showDeleteConfirmationModal() {
			const {deleteModal} = this.refs;

			deleteModal.show();
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
	}

	return ActionableFields;
};

export default withActionableFields;