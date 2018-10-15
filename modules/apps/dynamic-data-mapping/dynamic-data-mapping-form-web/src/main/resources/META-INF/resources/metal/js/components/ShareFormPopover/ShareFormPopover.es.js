import {Align} from 'metal-position';
import {EventHandler} from 'metal-events';
import autobind from 'autobind-decorator';
import Clipboard from 'metal-clipboard';
import Component, {Config} from 'metal-jsx';
import {selectText} from '../../util/dom.es';
import Popover from '../Popover/Popover.es';
import getCN from 'classnames';

class ShareFormPopover extends Component {
	static PROPS = {

		/**
		 * Map of translated strings
		 * @default ""
		 * @instance
		 * @memberof ShareFormPopover
		 * @type {spritemap}
		 */

		spritemap: Config.string().required(),

		/**
		 * Map of translated strings
		 * @default ""
		 * @instance
		 * @memberof ShareFormPopover
		 * @type {string}
		 */

		url: Config.string().required(),

		/**
		 * Map of translated strings
		 * @default {}
		 * @instance
		 * @memberof ShareFormPopover
		 * @type {!object}
		 */

		strings: Config.object().value({})
	};

	static STATE = {
		success: Config.bool().value(false)
	};

	attached() {
		this._clipboard = new Clipboard(
			{
				selector: '.ddm-copy-clipboard'
			}
		);
		this._eventHandler = new EventHandler();

		this._eventHandler.add(
			this._clipboard.on('success', this._handleClipboardSuccess)
		);
	}

	rendered() {
		const {success} = this.state;

		if (success) {
			setTimeout(
				() => {
					const shareFieldURL = this.element.querySelector('input');

					selectText(shareFieldURL);
				},
				30
			);
		}
	}

	disposeInternal() {
		super.disposeInternal();

		this._clipboard.dispose();
		this._eventHandler.removeAllListeners();
	}

	@autobind
	_handleClipboardSuccess() {
		this.setState(
			{
				success: true
			}
		);
	}

	@autobind
	_handlePopoverClosed() {
		this.setState(
			{
				success: false
			}
		);
	}

	render() {
		const shareFormIcon = document.querySelector('.share-form-icon');
		const {spritemap, strings, url} = this.props;
		const {success} = this.state;

		const buttonClasses = getCN(
			'btn ddm-copy-clipboard',
			{
				'btn-default': true,
				'btn-success': success
			}
		);

		const formClasses = getCN(
			'form-group m-0',
			{
				'has-success': success
			}
		);

		return (
			<Popover
				alignElement={shareFormIcon}
				events={{
					popoverClosed: this._handlePopoverClosed
				}}
				placement={Align.LeftCenter}
				portalElement={document.body}
				ref={'popover'}
				title={'copy-url'}
			>
				<div class={formClasses}>
					<div class="input-group">
						<div class="input-group-item input-group-prepend">
							<input class="form-control" ref="shareFieldURL" type="text" value={url} />
							{success && (
								<div class="form-feedback-group">
									<div class="form-feedback-item">{strings['copied-to-clipboard']}</div>
								</div>
							)}
						</div>
						<span class="input-group-append input-group-item input-group-item-shrink">
							<button class={buttonClasses} data-clipboard data-text={url} type="button">
								{success ? (
									<span class="publish-button-success-icon pl-2 pr-2">
										<svg
											aria-hidden="true"
											class={'lexicon-icon lexicon-icon-check'}
										>
											<use
												xlink:href={`${spritemap}#check`}
											/>
										</svg>
									</span>
								) : (
									<span class="publish-button-text">{'copy'}</span>
								)}
							</button>
						</span>
					</div>
				</div>
			</Popover>
		);
	}
}

export default ShareFormPopover;