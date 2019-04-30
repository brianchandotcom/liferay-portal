import {Config} from 'metal-state';
import Component from 'metal-component';
import {Store} from '../../store/store.es';

import {DEFAULT_LANGUAGE_ID_KEY} from '../../utils/constants';
import FragmentEditableFieldTooltip from './FragmentEditableFieldTooltip.es';
import getConnectedComponent from '../../store/ConnectedComponent.es';

/**
 * FragmentEditableBackgroundImage
 */
class FragmentEditableBackgroundImage extends Component {

	/**
	 * Returns the list of buttons to be shown inside the tooltip.
	 * @param {boolean} showMapping
	 * @return {Array<{id: string, label: string}>}
	 * @review
	 */
	static getButtons(showMapping) {
		const buttons = [
			{
				icon: 'pencil',
				id: 'select',
				label: Liferay.Language.get('select-background')
			}
		];

		if (showMapping) {
			buttons.push(
				{
					icon: 'bolt',
					id: 'map',
					label: Liferay.Language.get('map-background')
				}
			);
		}

		return buttons;
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	created() {
		this._handleClick = this._handleClick.bind(this);
		this._handleOutsideTooltipClick = this._handleOutsideTooltipClick.bind(this);
		this._handleSelectBackgroundImage = this._handleSelectBackgroundImage.bind(this);
		this._handleTooltipButtonClick = this._handleTooltipButtonClick.bind(this);

		this.element.addEventListener('click', this._handleClick);
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	disposed() {
		this.element.removeEventListener('click', this._handleClick);
		this._disposeTooltip();
	}

	/**
	 * @private
	 * @review
	 */
	_disposeTooltip() {
		if (this._tooltip) {
			this._tooltip.dispose();
			this._tooltip = null;
		}
	}

	/**
	 * @private
	 * @review
	 */
	_handleClick() {
		if (this._tooltip) {
			this._disposeTooltip();
		}
		else {
			this._tooltip = new FragmentEditableFieldTooltip(
				{
					alignElement: this.element,
					buttons: FragmentEditableBackgroundImage.getButtons(
						this.showMapping
					),
					store: this.store
				}
			);

			this._tooltip.on('buttonClick', this._handleTooltipButtonClick);
			this._tooltip.on('outsideTooltipClick', this._handleOutsideTooltipClick);
		}
	}

	/**
	 * @private
	 * @review
	 */
	_handleOutsideTooltipClick() {
		this._disposeTooltip();
	}

	/**
	 * @param {string} backgroundImageURL
	 * @private
	 * @review
	 */
	_handleSelectBackgroundImage(backgroundImageURL) {
		const defaultSegmentsExperienceId = prefixSegmentsExperienceId(this.defaultSegmentsExperienceId);
		const segmentsExperienceId = prefixSegmentsExperienceId(this.segmentsExperienceId);

		this.store.dispatch(
			updateEditableValueAction(
				this.fragmentEntryLinkId,
				this.editableId,
				this.languageId || DEFAULT_LANGUAGE_ID_KEY,
				backgroundImageURL,
				segmentsExperienceId || defaultSegmentsExperienceId
			)
		);
	}

	/**
	 * Handles click events for tooltip buttons.
	 * @param {object} event The tooltip button click.
	 */
	_handleTooltipButtonClick(event) {
		EditableBackgroundImageProcessor.init(
			this._handleSelectBackgroundImage,
			this.imageSelectorURL,
			this.portletNamespace
		);
	}
}

/**
 * State definition.
 * @static
 * @type {!Object}
 */
FragmentEditableBackgroundImage.STATE = {

	/**
	 * FragmentEntryLink id
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableBackgroundImage
	 * @review
	 * @type {!string}
	 */
	fragmentEntryLinkId: Config
		.string()
		.required(),

	/**
	 * Editable ID
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableBackgroundImage
	 * @review
	 * @type {!string}
	 */
	editableId: Config
		.string()
		.required(),

	/**
	 * Editable values that should be used instead of the default ones inside
	 * editable fields.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableBackgroundImage
	 * @type {!Object}
	 */
	editableValues: Config.object().required(),

	/**
	 * If <code>true</code>, the mapping is activated.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableBackgroundImage
	 * @type {!boolean}
	 */
	showMapping: Config.bool().required(),

	/**
	 * Store instance.
	 * @default undefined
	 * @instance
	 * @memberOf FragmentEditableBackgroundImage
	 * @type {Store}
	 */
	store: Config.instanceOf(Store)

};

const ConnectedFragmentEditableBackgroundImage = getConnectedComponent(
	FragmentEditableBackgroundImage,
	[
		'defaultLanguageId',
		'defaultSegmentsExperienceId',
		'imageSelectorURL',
		'languageId',
		'portletNamespace',
		'segmentsExperienceId'
	]
);

export {ConnectedFragmentEditableBackgroundImage, FragmentEditableBackgroundImage};
export default ConnectedFragmentEditableBackgroundImage;