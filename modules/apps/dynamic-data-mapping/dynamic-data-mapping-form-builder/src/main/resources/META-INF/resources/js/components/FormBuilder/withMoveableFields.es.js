import * as FormSupport from '../Form/FormSupport.es';
import Component from 'metal-jsx';
import {Config} from 'metal-state';
import {DragDrop} from 'metal-drag-drop';
import {focusedFieldStructure, pageStructure, ruleStructure} from '../../util/config.es';

const withMoveableFields = ChildComponent => {
	class MoveableFields extends Component {
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
			this._createDragAndDrop();
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

		willReceiveProps() {
			this._dragAndDrop.setState(
				{
					targets: this._dragAndDrop.setterTargetsFn_('.moveable .ddm-target')
				}
			);
		}

		_createDragAndDrop() {
			this._dragAndDrop = new DragDrop(
				{
					sources: '.moveable .ddm-drag',
					targets: '.moveable .ddm-target',
					useShim: false
				}
			);

			this._dragAndDrop.on(
				DragDrop.Events.END,
				this._handleDragAndDropEnd.bind(this)
			);

			this._dragAndDrop.on(DragDrop.Events.DRAG, this._handleDragStarted.bind(this));
		}

		_handleDragAndDropEnd({source, target}) {
			const lastParent = document.querySelector('.ddm-parent-dragging');

			if (lastParent) {
				lastParent.classList.remove('ddm-parent-dragging');
				lastParent.removeAttribute('style');
			}

			if (target) {
				const sourceIndex = FormSupport.getIndexes(
					source.parentElement.parentElement
				);
				const targetIndex = FormSupport.getIndexes(target.parentElement);

				source.innerHTML = '';

				const addedToPlaceholder = ![...target.parentElement.parentElement.classList].includes('position-relative');

				this._handleFieldMoved(
					{
						addedToPlaceholder,
						source: sourceIndex,
						target: targetIndex
					}
				);
			}
		}

		_handleDragStarted({source}) {
			const {height} = source.getBoundingClientRect();
			const {parentElement} = source;

			parentElement.setAttribute('style', `height: ${height}px !important;`);
			parentElement.classList.add('ddm-parent-dragging');
		}

		_handleFieldMoved(event) {
			const {store} = this.context;

			store.emit('fieldMoved', event);
		}
	}

	return MoveableFields;
};

export default withMoveableFields;