import * as FormSupport from '../Form/FormSupport.es';
import Component from 'metal-jsx';
import {DragDrop} from 'metal-drag-drop';

const withMoveableFields = ChildComponent => {
	class MoveableFields extends Component {
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
					targets: '.moveable .ddm-target'
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

		/**
		 * @param {!Object} data
		 * @private
		 */

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

		/**
		 * @param {!Object} payload
		 * @private
		 */

		_handleFieldMoved(event) {
			const {store} = this.context;

			store.emit('fieldMoved', event);
		}
	}

	return MoveableFields;
};

export default withMoveableFields;