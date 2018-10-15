import {Align} from 'metal-position';
import autobind from 'autobind-decorator';
import Component, {Config} from 'metal-jsx';
import dom from 'metal-dom';
import {EventHandler} from 'metal-events';
import getCN from 'classnames';
import PopoverBase from './PopoverBase.es';

const POSITIONS = ['top', 'left', 'right', 'bottom'];

const getAlignPosition = (source, target, suggestedPosition) => {
	if (!suggestedPosition) {
		suggestedPosition = Align.TopCenter;
	}

	const position = Align.align(source, target, suggestedPosition);

	let returnedPositon = POSITIONS[position];

	if (!returnedPositon) {
		returnedPositon = 'left';
	}

	return returnedPositon;
};

const CLASSNAME = 'ddm-share-url-popover';

class Popover extends Component {
	static PROPS = {
		alignElement: Config.object(),
		content: Config.string(),
		placement: Config.number(),
		title: Config.string(),
		visible: Config.bool()
	};

	static STATE = {
		displayed: Config.bool().valueFn('_visibleValueFn'),
		position: Config.string(),
		width: Config.number().value(240)
	};

	attached() {
		const {alignElement} = this.props;

		this._eventHandler = new EventHandler();

		this._eventHandler.add(
			dom.on(document, 'mousedown', this._handleDocumentMouseDown, true),
			dom.on(alignElement, 'click', this._handleAlignElementClicked)
		);

		return this.setPopoverWidth();
	}

	willReceiveProps({visible}) {
		if (visible) {
			this.setState(
				{
					displayed: !!visible.newVal
				}
			);
		}
	}

	_visibleValueFn() {
		return this.props.visible;
	}

	disposeInternal() {
		super.disposeInternal();

		this._eventHandler.removeAllListeners();
	}

	@autobind
	_handleAlignElementClicked() {
		const {displayed} = this.state;

		if (displayed) {
			this.emit('popoverClosed');
		}

		this.setState(
			{
				displayed: !displayed
			}
		);
	}

	/**
	 * Handle a click on the document in order to close the popover
	 * @param {Event} event
	 * @protected
	 */
	@autobind
	_handleDocumentMouseDown({target}) {
		const {alignElement} = this.props;
		const {displayed} = this.state;

		if ((!this.element.contains(target) && !alignElement.contains(target)) && displayed) {
			this.setState(
				{
					displayed: false
				}
			);

			this.emit('popoverClosed');
		}
	}

	setPopoverWidth() {
		const {element} = this;

		element.style.visibility = 'hidden';
		element.style.display = 'block';

		const width = element.offsetHeight;

		element.style.visibility = 'visible';
		element.style.display = 'none';

		this.setState(
			{
				width
			}
		);
	}

	render() {
		const {
			alignElement,
			children,
			content,
			placement,
			title
		} = this.props;
		const {
			displayed,
			position,
			width
		} = this.state;

		const classes = getCN(
			CLASSNAME,
			{
				'popover-large': width > 600
			}
		);

		return (
			<PopoverBase
				elementClasses={classes}
				events={{
					stateSynced: () => {
						if (!displayed) {
							return;
						}
						this.setState(
							{
								position: getAlignPosition(
									this.refs.popover.element,
									alignElement,
									placement
								)
							}
						);
					}
				}}
				placement={position}
				ref="popover"
				visible={displayed}
			>
				{title && (
					<PopoverBase.Header>{title}</PopoverBase.Header>
				)}

				<PopoverBase.Body>
					{content && (
						<span class="text-secondary">{content}</span>
					)}

					{children.length && children}
				</PopoverBase.Body>
			</PopoverBase>
		);
	}
}

export default Popover;