import Component from 'metal-jsx';
import Position from 'metal-position';
import {Drag} from 'metal-drag-drop';

const withResizeableColumns = ChildComponent => {
	class ResizeableColumns extends Component {
		attached() {
			this._createResizeDrag();
		}

		disposeInternal() {
			super.disposeInternal();

			if (this._resizeDrag) {
				this._resizeDrag.dispose();
			}
		}

		_createResizeDrag() {
			this._resizeDrag = new Drag(
				{
					axis: 'x',
					sources: '.resizeable .ddm-resize-handle',
					useShim: true
				}
			);

			this._resizeDrag.on(Drag.Events.START, this._handleResizeDragStartEvent.bind(this));
			this._resizeDrag.on(Drag.Events.DRAG, this._handleResizeDragEvent.bind(this));
		}

		_handleResizeDragEvent(event) {
			const columnNodes = Object.keys(this.refs)
				.filter(key => key.indexOf('resizeColumn') === 0)
				.map(key => this.refs[key]);
			const {source, x} = event;
			const {store} = this.context;

			let distance = Infinity;
			let nearest;

			columnNodes.forEach(
				node => {
					const region = Position.getRegion(node);

					const currentDistance = Math.abs(x - region.left);

					if (currentDistance < distance) {
						distance = currentDistance;
						nearest = node;
					}
				}
			);

			if (nearest) {
				const column = Number(nearest.dataset.resizeColumn);
				const direction = source.classList.contains('ddm-resize-handle-left') ? 'left' : 'right';

				if (this._lastResizeColumn !== column) {
					this._lastResizeColumn = column;

					store.emit(
						'columnResized',
						{
							column,
							direction,
							source
						}
					);
				}
			}
		}

		_handleResizeDragStartEvent() {
			this._lastResizeColumn = -1;
		}

		isResizeEnabled() {
			const {defaultLanguageId, editingLanguageId} = this.props;

			return defaultLanguageId === editingLanguageId;
		}

		renderResizeReferences() {
			return [...Array(12)].map(
				(element, index) => {
					return (
						<div
							class="ddm-resize-column"
							data-resize-column={index}
							key={index}
							ref={`resizeColumn${index}`}
						/>
					);
				}
			);
		}

		render() {
			return (
				<div class={this.isResizeEnabled() ? 'resizeable' : ''}>
					{this.renderResizeReferences()}

					<ChildComponent {...this.props} />
				</div>
			);
		}
	}

	return ResizeableColumns;
};

export default withResizeableColumns;